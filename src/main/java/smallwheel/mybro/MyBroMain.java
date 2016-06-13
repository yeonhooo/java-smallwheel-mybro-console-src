package smallwheel.mybro;

import smallwheel.mybro.common.ENV;
import smallwheel.mybro.common.SharedInfo;
import smallwheel.mybro.support.SqlMapperBuilderFactory;
import smallwheel.mybro.support.builder.DtoClassBuilder;
import smallwheel.mybro.support.builder.MapperInterfaceBuilder;
import smallwheel.mybro.support.builder.SqlMapperBuilder;
import org.apache.log4j.Logger;

/**
 * MyBro 메인 클래스
 * 
 * TODO: DB Connection 관리 ==> Connection Pool
 * TODO: 패키지 경로 정보 입력받아, 패키지 생성 및 해당 패키지 경로에 파일 추가
 * TODO: Mapper.java 파일 생성 기능 
 *
 * @author yeonhooo@gmail.com
 */
public class MyBroMain {
	
	private final static Logger LOGGER = Logger.getLogger(MyBroMain.class);

	private DtoClassBuilder dtoClassBuilder;
	private SqlMapperBuilder sqlMapperBuilder;
	private MapperInterfaceBuilder mapperInterfaceBuilder;

	public static void main(String[] args) {
		MyBroMain main = new MyBroMain();
		main.run();
	}

	private void init() {
		// 환경초기화 , 환경파일에서 변수값 로딩
		ENV.init();
		
		// dtoClassBuilder 생성
		dtoClassBuilder = new DtoClassBuilder();
		
		// 타입별(ibatis, mybatis) sqlMapperBuilder 생성
		SqlMapperBuilderFactory factory = new SqlMapperBuilderFactory();
		sqlMapperBuilder = factory.createSqlMapperBuilder(ENV.mapperType);
		
		// mapperInterfaceBuilder 생성
		mapperInterfaceBuilder = new MapperInterfaceBuilder();
	}

	private void run() {
		
		init();
		SharedInfo.getInstance().load();

		// step1. 모델 클래스 파일을 생성한다.
		dtoClassBuilder.build();
		
		// step2. SqlMap.xml 파일을 생성한다.
		sqlMapperBuilder.build();
		
		// step3. mapper interface 파일을 생성한다.
		mapperInterfaceBuilder.build();
		
		LOGGER.info("### " + SharedInfo.getInstance().getTableInfoList().size() + "개 테이블에 대한 작업이 완료되었습니다." );
	}
}
