package smallwheel.mybro.common;

public class ColumnInfo {
	private String name;
	private String type;
	private String comment;

	public ColumnInfo() {
		super();
	}

	public ColumnInfo(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public ColumnInfo(String name, String type, String comment) {
		this.name = name;
		this.type = type;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() { return comment; }
	public void setComment(String comment) { this.comment = comment; }

	public String toString() {
		return String.format("ColumnInfo [ name : %s, type : %s ]", name, type);
	}
}
