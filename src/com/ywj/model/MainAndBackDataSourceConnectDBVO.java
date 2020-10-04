package com.ywj.model;

public class MainAndBackDataSourceConnectDBVO {
	DataSourceConnectDBVO maindb;
	DataSourceConnectDBVO bakdb;
	public DataSourceConnectDBVO getMaindb() {
		return maindb;
	}
	
	public void setMaindb(DataSourceConnectDBVO maindb) {
		this.maindb = maindb;
	}
	public DataSourceConnectDBVO getBakdb() {
		return bakdb;
	}
	public void setBakdb(DataSourceConnectDBVO bakdb) {
		this.bakdb = bakdb;
	}
	public MainAndBackDataSourceConnectDBVO(DataSourceConnectDBVO maindb, DataSourceConnectDBVO bakdb) {
		super();
		this.maindb = maindb;
		this.bakdb = bakdb;
	}
	public MainAndBackDataSourceConnectDBVO() {
		super();
	}
}
