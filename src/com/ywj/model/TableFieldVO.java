package com.ywj.model;

public class TableFieldVO {
	private String columnName;
	private int columnType;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public TableFieldVO(String columnName, int columnType) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
	}
	public TableFieldVO() {
		super();
	}
	
	
}
