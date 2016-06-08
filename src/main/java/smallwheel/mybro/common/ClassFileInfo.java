package smallwheel.mybro.common;

import java.util.ArrayList;
import java.util.List;

public class ClassFileInfo {
	private String name;
	private List<String> propertyPrimaryKeyNameList = new ArrayList<>();
	private List<PropertyInfo> propertyList = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPropertyPrimaryKeyNameList() {
		return propertyPrimaryKeyNameList;
	}
	public void setPropertyPrimaryKeyNameList(
			List<String> propertyPrimaryKeyNameList) {
		this.propertyPrimaryKeyNameList = propertyPrimaryKeyNameList;
	}
	public List<PropertyInfo> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<PropertyInfo> propertyList) {
		this.propertyList = propertyList;
	}
}
