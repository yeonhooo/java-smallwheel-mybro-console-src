package smallwheel.mybro.common;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	private String name;
	private String entityName;
	private List<String> primaryKeyColumnNameList = new ArrayList<>();
	private List<ColumnInfo> columnInfoList = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public List<String> getPrimaryKeyColumnNameList() {
		return primaryKeyColumnNameList;
	}
	public void setPrimaryKeyColumnNameList(List<String> primaryKeyColumnNameList) {
		this.primaryKeyColumnNameList = primaryKeyColumnNameList;
	}
	public List<ColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}
	public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}
}
