package frontend.beans;

public class DomainAttributeBean {
	
	private DataType dataType;
	private Integer min;
	private Integer max;
	private String columnName;
	private boolean nullable;
	private String Pattern;
	
	public DomainAttributeBean() {
		this.columnName = "newName";
		this.dataType = DataType.String;
	}
	
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getPattern() {
		return Pattern;
	}
	public void setPattern(String pattern) {
		Pattern = pattern;
	}
	
	public String getDatatypeAsString() {
		return dataType.toString();
	}
}
