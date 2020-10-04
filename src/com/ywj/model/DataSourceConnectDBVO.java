package com.ywj.model;

public class DataSourceConnectDBVO {
	private int dataSourceType;
	private String dataSourceName;
	private String dataSourceAddress;
	private int dataSourcePort;
	private String databaseName;
	private String accountName;
	private String accountPwd;
	public int getDataSourceType() {
		return dataSourceType;
	}
	public void setDataSourceType(int dataSourceType) {
		this.dataSourceType = dataSourceType;
	}
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getDataSourceAddress() {
		return dataSourceAddress;
	}
	public void setDataSourceAddress(String dataSourceAddress) {
		this.dataSourceAddress = dataSourceAddress;
	}
	public int getDataSourcePort() {
		return dataSourcePort;
	}
	public void setDataSourcePort(int dataSourcePort) {
		this.dataSourcePort = dataSourcePort;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	public DataSourceConnectDBVO(int dataSourceType, String dataSourceName, String dataSourceAddress,
			int dataSourcePort, String databaseName, String accountName, String accountPwd) {
		super();
		this.dataSourceType = dataSourceType;
		this.dataSourceName = dataSourceName;
		this.dataSourceAddress = dataSourceAddress;
		this.dataSourcePort = dataSourcePort;
		this.databaseName = databaseName;
		this.accountName = accountName;
		this.accountPwd = accountPwd;
	}
	public DataSourceConnectDBVO() {
		super();
	}

}
