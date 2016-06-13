package smallwheel.mybro.common;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * 전역 환경정보
 * 
 * @author yeonhooo
 *
 */
public class ENV {

	private final static Logger LOGGER = Logger.getLogger(ENV.class);
	
	public static final String VER = "DAOMaker 20110822.0940"; // 서버 버전

	/** Type of Coupling */
	public static String couplingType = "MIDDLE";
	/** mapper type */
	public static String mapperType = Constants.Mapper.MYBATIS;
	/**
	 * 엔티티명에서 제외할 문자열
	 * <pre>
	 * e.g. 테이블명이 NQR_BILL_DETAIL 의 경우, 기본 엔티티명은 NqrBillDetail 이 된다
	 * 이 때 PREFIX_EXCEPT 을 "NQR_" 로 설정할 경우, 엔티티명은 NqrBillDetail 가 아닌 BillDetail가 된다.
	 * </pre>
	 */
	public static String prefixExcept;
	/** 데이터베이스 종류 */
	public static String dbms;
	/** 데이터베이스 아이피 */
	public static String serverIp;
	/** 데이터베이스 포트 */
	public static String port;
	/** 데이터베이스 아이디 */
	public static String userId;
	/** 데이터베이스 패스워드 */
	public static String userPass;
	/** 데이터베이스명 */
	public static String dbName;
	/** 테이블명 리스트 */
	public static String tableNameList; 
	/** 
	 * 자파 클래스 파일명 접미어
	 * <pre>
	 * e.g. 테이블명이 USER_INFO 인 경우, 기본 자바파일명은 UserInfo.java가 된다.
	 * 이 때 classNameSuffix를 "Dto"로 설정할 경우 자바파일명은 UserInfoDto.java가 되며,
	 * classNameSuffix를 "Vo"로 설정할 경우 자바파일명은 UserInfoVo.java가 된다.
	 * </pre>
	 */
	public static String classNameSuffix;


	/** 변수 생성 후, 프로퍼티 파일로부터 속성을 읽어 값을 변수에 저장한다(데이터베이스 관련). */
	public static void init() {

		if (checkNull("COUPLING_TYPE"))
			couplingType = ContextMaster.getString("COUPLING_TYPE");
		if (checkNull("DBMS"))
			dbms = ContextMaster.getString("DBMS");
		if (checkNull("SERVER_IP"))
			serverIp = ContextMaster.getString("SERVER_IP");
		if (checkNull("PORT"))
			port = ContextMaster.getString("PORT");
		if (checkNull("USER_ID"))
			userId = ContextMaster.getString("USER_ID");
		if (checkNull("USER_PASS"))
			userPass = ContextMaster.getString("USER_PASS");
		if (checkNull("DB_NAME"))
			dbName = ContextMaster.getString("DB_NAME");
		if (checkNull("TABLES"))
			tableNameList = ContextMaster.getString("TABLES");
		if (checkNull("MAPPER_TYPE"))
			mapperType = ContextMaster.getString("MAPPER_TYPE");
		if (checkNull("PREFIX_EXCEPT"))
			prefixExcept = ContextMaster.getString("PREFIX_EXCEPT");
		if (checkNull("CLASS_NAME_SUFFIX"))
			classNameSuffix = ContextMaster.getString("CLASS_NAME_SUFFIX");

		File dir = new File(Constants.Path.DTO_CLASS_DES_DIR);
		if (!dir.isDirectory()) {
			// 디렉토리가 존재하지 않는다면 디렉토리 생성
			dir.mkdirs();
		}
		dir = new File(Constants.Path.SQL_MAPPER_DES_DIR);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("############## [ Starting MyBro !!! ] ########################");
			LOGGER.info("#");
			LOGGER.info("#\tDTO class files directory:\t" + Constants.Path.DTO_CLASS_DES_DIR);
			LOGGER.info("#\tSql mapper files directory:\t" + Constants.Path.SQL_MAPPER_DES_DIR);
			LOGGER.info("#\tLog directory:\t" + Constants.Path.LOG_DIR);
			LOGGER.info("#\tCoupling type:\t" + couplingType);
			LOGGER.info("#\tMapper type:\t" + mapperType);
			LOGGER.info("#\tExclude prefix string from entity name:\t" + prefixExcept);
			LOGGER.info("#\tJava class name suffix: \t" + classNameSuffix);
			LOGGER.info("##############################################################\n");
		}
	}

	private static boolean checkNull(String name) {
		if (ContextMaster.getString(name) == null) {
			return false;
		}
		return true;
	}
}