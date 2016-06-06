package smallwheel.mybro.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import smallwheel.mybro.support.builder.DtoClassBuilder;

/**
 * 테이블 정보 메모리 클래스
 * 
 * <pre>
 * DB에서 테이블 정보를 조회하여 메모리에 저장
 * </pre>
 * 
 * @author yeonhooo@gmail.com
 */
public class SharedInfo {
	private final Logger logger = Logger.getLogger(DtoClassBuilder.class);

	private static SharedInfo sharedInfo = new SharedInfo();
	private List<TableInfo> tableInfoList = new ArrayList<>();
	private List<ClassFileInfo> classFileInfoList = new ArrayList<>();
	private List<MapperInterfaceInfo> mapperInterfaceInfoList = new ArrayList<>();

	// 테이블 정보 관련 변수
	private final String tables = ContextMaster.getString("TABLES");

	// singleton
	private SharedInfo() {
	}

	public static SharedInfo getInstance() {
		return sharedInfo;
	}

	public void load() {

		// DB 연동
		DBManager dbm = new DBManager();
		dbm.checkConnection(ENV.dbms);
		Connection con = dbm.getConnection(ENV.dbms);
		PreparedStatement pstmt;
		DatabaseMetaData databaseMetaData;
		ResultSet rs;
		ResultSetMetaData rm;

		try {
			for (String tableName : tables.split(",")) {

				tableName = tableName.trim();
				TableInfo tableInfo = new TableInfo();
				ClassFileInfo classInfo = new ClassFileInfo();
				tableInfo.setName(tableName);

				pstmt = con.prepareStatement("select * from " + tableInfo.getName() + " where 1=0");
				databaseMetaData = con.getMetaData();
				rs = pstmt.executeQuery();
				rm = rs.getMetaData();

				// Table의 Column 정보를 가져온다.
				for (int i = 1; i <= rm.getColumnCount(); i++) {
					tableInfo.getColumnInfoList().add(new ColumnInfo(rm.getColumnName(i), rm.getColumnTypeName(i)));
				}

				// Table의 PK 정보를 가져온다.
				ResultSet keys = databaseMetaData.getPrimaryKeys(null, null, tableInfo.getName());
				while (keys.next()) {
					tableInfo.getPrimaryKeyColumnNameList().add(keys.getString("COLUMN_NAME"));
					classInfo.getPropertyPrimaryKeyNameList().add(makePropertyName(keys.getString("COLUMN_NAME")));
				}

				logger.info("[Table Name: " + tableInfo.getName() + " / Column Count: " + rm.getColumnCount() + "]");
				logger.info("PK Columns");
				for (String key : tableInfo.getPrimaryKeyColumnNameList()) {
					logger.info("\t" + key);
				}

				// EntityName 을 만든다
				tableInfo.setEntityName(makeEntityName(tableInfo.getName(), ENV.prefixExcept));

				// ClassName 을 만든다.
				classInfo.setName(makeClassName(tableInfo.getEntityName()));

				for (int i = 0; i < rm.getColumnCount(); i++) {
					classInfo.getPropertyList().add(
							new PropertyInfo(makePropertyName(tableInfo.getColumnInfoList().get(i).getName()), makePropertyType(tableInfo
									.getColumnInfoList().get(i).getType())));
				}

				tableInfoList.add(tableInfo);
				classFileInfoList.add(classInfo);

				rs.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 엔티티명을 만든다.
	 * 
	 * @param prefixExcept 엔티티명에서 제외할 문자열
	 */
	private String makeEntityName(String tableName, String prefixExcept) {

		// prefixExcept을 엔티티명에서 제외한다.
		tableName = tableName.replaceAll(prefixExcept, "");
		tableName = tableName.toLowerCase(Locale.ENGLISH);

		while (true) {
			if (tableName.indexOf("_") > -1) {
				tableName = (tableName.substring(0, tableName.indexOf("_"))
						+ tableName.substring(tableName.indexOf("_") + 1, tableName.indexOf("_") + 2).toUpperCase() + tableName
						.substring(tableName.indexOf("_") + 2)).trim();
			} else {
				break;
			}
		}

		// 첫 글자를 대문자로 시작한다.
		tableName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
		return tableName;
	}

	/** 클래스명을 만든다. */
	private String makeClassName(String entityName) {
		return entityName + ENV.classNameSuffix;
	}

	/**
	 * 클래스내의 프로퍼티명(property)을 만든다.
	 * 
	 * @param 변환 될 실제 DB 컬럼명
	 * @return 변환 된 프로퍼티명(DB 컬럼명과 매칭)
	 * */
	private String makePropertyName(String columnName) {
		columnName = columnName.toLowerCase(Locale.ENGLISH);

		while (true) {
			if (columnName.indexOf("_") > -1) {
				columnName = (columnName.substring(0, columnName.indexOf("_"))
						+ columnName.substring(columnName.indexOf("_") + 1, columnName.indexOf("_") + 2).toUpperCase() + columnName
						.substring(columnName.indexOf("_") + 2)).trim();
			} else {
				break;
			}
		}

//		// 변경된 프로퍼티명이 자바 예약어이거나, 비정상적일 경우에 대한 처리		
//		if (columnName.equals("continue")) {
//			columnName = "continues";
//		} else if (columnName.equals("r")) {
//			columnName = "run";
//		} else if (columnName.equals("w")) {
//			columnName = "win";
//		} else if (columnName.equals("l")) {
//			columnName = "lose";
//		} else if (columnName.equals("d")) {
//			columnName = "draw";
//		} else if (columnName.equals("s")) {
//			columnName = "save";
//		}

		return columnName;
	}

	/**
	 * 환경 설정 파일에 설정된 결합도에 따라 클래스의 프로퍼티 타입을 만든다.<br />
	 * <br />
	 * <strong>결합도 타입</strong><br />
	 * 
	 * 강함(<code>HIGH</code>): DB와 동일한 타입의 자바 프로퍼티 타입 반환<br />
	 * 보통(<code>MIDDLE</code>): DB 타입 중 숫자형과 날짜형만 변경. 그 외 타입은 문자열 타입으로 변환<br />
	 * 약함(<code>LOW</code>): DB 타입 중 숫자형만 변환. 그 외 타입은 문자열 타입으로 변환<br />
	 * 없음(<code>NO</code>): 모든 DB 타입을 문자열 타입으로 변환
	 * 
	 * @param 변환 될 실제 DB 컬럼 타입
	 * @return 변환 된 프로퍼티 타입(DB 컬럼 타입과 매칭)
	 * */
	private String makePropertyType(String columnType) {
		String propertyType = columnType.toUpperCase();

		if ("HIGH".equals(ENV.couplingType)) {
			// 결합도 강함
			if (propertyType.equals("TINYINT") || propertyType.equals("SMALLINT") || propertyType.equals("MEDIUMINT")
					|| propertyType.equals("INT") || propertyType.equals("BIGINT")) {
				propertyType = "int";
			} else if (propertyType.equals("FLOAT")) {
				propertyType = "float";
			} else if (propertyType.equals("DOUBLE") || propertyType.equals("DECIMAL")) {
				propertyType = "double";
			} else if (propertyType.equals("CHAR") || propertyType.equals("VARCHAR") || propertyType.equals("TEXT")
					|| propertyType.indexOf("BLOB") > -1) {
				propertyType = "String";
			} else if (propertyType.equals("DATE") || propertyType.equals("DATETIME") || propertyType.equals("TIMESTAMP")) {
				propertyType = "Date";
			} else {
				propertyType = "String";
			}

		} else if ("MIDDLE".equals(ENV.couplingType)) {
			// 결합도 보통
			if (propertyType.equals("TINYINT") || propertyType.equals("SMALLINT") || propertyType.equals("MEDIUMINT")
					|| propertyType.equals("INT") || propertyType.equals("BIGINT")) {
				propertyType = "int";
			} else if (propertyType.equals("DATE") || propertyType.equals("DATETIME") || propertyType.equals("TIMESTAMP")) {
				propertyType = "Date";
			} else {
				propertyType = "String";
			}

		} else if ("LOW".equals(ENV.couplingType)) {
			// 결합도 약함
			if (propertyType.equals("TINYINT") || propertyType.equals("SMALLINT") || propertyType.equals("MEDIUMINT")
					|| propertyType.equals("INT") || propertyType.equals("BIGINT")) {
				propertyType = "int";
			} else {
				propertyType = "String";
			}

		} else if ("NO".equals(ENV.couplingType)) {
			// 결합도 없음
			propertyType = "String";
		}

		return propertyType;
	}

	public List<TableInfo> getTableInfoList() {
		return tableInfoList;
	}

	public List<ClassFileInfo> getClassFileInfoList() {
		return classFileInfoList;
	}

	public List<MapperInterfaceInfo> getMapperInterfaceInfoList() {
		return mapperInterfaceInfoList;
	}

}