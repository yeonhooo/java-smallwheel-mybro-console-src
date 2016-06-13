# smallwheel.mybro (console mode)

smallwheel.mybro-console는 mybatis 및 ibatis를 위한 코드 생성용 콘솔 프로그램입니다. <br />
mybatis 및 ibatis를 사용하는 프로젝트 초기에 반드시 거쳐야하지만 귀찮은 작업들을 줄이기 위한 목적으로 작성되었으며, 데이터베이스 접속 정보 및 테이블명을 입력받아, 관련 된 코드들을 자동으로 생성합니다. 현재는 콘솔 프로그램으로 작성되어 있으며, 차후 GUI로도 개발할 예정입니다.

자동으로 생성되는 코드 목록:
 1. domain class file
     * (DTO, VO 등으로 불리우는) 도메인 클래스 파일
     * DB와의 결합 정도를 설정할 수 있으며, 설정 단계에 따라 도메인 클래스의 프로퍼티 타입이 결정됩니다.
       * HIGH: DB의 컬럼타입과 동일
       * MIDDLE(기본값): 숫자형(int)과 날짜형(Date) 타입만 변경되며, 나머지는 String 타입으로 결정됩니다.
       * LOW: 숫자형(int) 타입만 변경되며, 나머지는 String 타입으로 결정됩니다.
       * NO: 모든 프로퍼티 타입은 String 타입으로 결정됩니다.
       
 2. sql mapper xml
     * 간단한 CRUD를 포함하는 sql mapper 파일
       * typeAlias
       * resultMap
       * insert
       * select by primary key
       * select list by dynamic where clause
       * update by primary key
       * delete by primary key
       * dynamic where clause
       
       
 3. java mapper interface 
     * mybatis 전용 파일. myabtis에서 Mapper interface를 사용할 경우에 사용합니다.
       * (2)에서 생성된 sql mapper xml에 해당하는 java mapper interface를 생성합니다.
 
<br />
------------------------
<br />


덧1) 2011년부터 개인적으로 작성해서 사용하다가; 소스도 자꾸 잃어버리고 관리자 잘 안되서 github에 올려봅니다;
필요하신 분들은 편히 사용하시고(정작 사용방법은 편하지 않지만), 관련 문의는 아래 메일로 부탁드려요~*

덧2) 비슷한 기능을 하는 mybatis generator(http://mybatis.github.io/generator/configreference/xmlconfig.html) 라는 툴도 있습니다~ mybro와 비슷하지만 더 강력하고 세밀한 설정이 가능해 보이구요~
다만, 개인적으로 설정 부분이 조금 어려운 감은 있어요~


관련 문의: yeonhooo@gmail.com
