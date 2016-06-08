package smallwheel.mybro.support;

import smallwheel.mybro.common.Constants;
import smallwheel.mybro.support.builder.SqlMapperBuilder;
import smallwheel.mybro.support.builder.SqlMapperBuilderForIbatis;
import smallwheel.mybro.support.builder.SqlMapperBuilderForMybatis;

/**
 * SqlMapperBuilderFactory
 * 
 * @author yeonhooo
 */
public class SqlMapperBuilderFactory {

	/**
	 * mapperType에 해당하는 SqlMapperBuilder를 생성한다.
	 * 
	 * @param mapperType
	 * @return mapperType별 SqlMapperBuilder
	 */
	public SqlMapperBuilder createSqlMapperBuilder(String mapperType) {

		// 기본 맵퍼는 MyBatis로 설정한다
		SqlMapperBuilder builder = new SqlMapperBuilderForMybatis();

		if (mapperType.equalsIgnoreCase(Constants.Mapper.IBATIS)) {
			builder = new SqlMapperBuilderForIbatis();
		}

		return builder;
	}
}
