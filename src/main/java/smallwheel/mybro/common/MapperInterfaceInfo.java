package smallwheel.mybro.common;

import java.util.ArrayList;
import java.util.List;

public class MapperInterfaceInfo {
	
	private String name;
	private List<SqlMapInfo> sqlMapInfoList = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SqlMapInfo> getSqlMapInfoList() {
		return sqlMapInfoList;
	}
	public void setSqlMapInfoList(List<SqlMapInfo> sqlMapInfoList) {
		this.sqlMapInfoList = sqlMapInfoList;
	}
}