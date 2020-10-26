package com.ywj.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ywj.utils.MyDataBaseUtil;

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

	public void cancelForeinKey() throws SQLException {
		Connection connect = MyDataBaseUtil.getMySQLConnect(this.getBakdb());
		String cancelForeinKey = "SET foreign_key_checks=0";
		PreparedStatement prepareStatement = connect.prepareStatement(cancelForeinKey);
		prepareStatement.executeUpdate();
		prepareStatement.close();
		connect.close();
		System.out.println("cancelForeinKey " + cancelForeinKey);
	}

	public void setForeinKey() throws SQLException{
		Connection connect = MyDataBaseUtil.getMySQLConnect(this.getBakdb());
		String setForeinKey = "SET foreign_key_checks=1";
		PreparedStatement prepareStatement = connect.prepareStatement(setForeinKey);
		prepareStatement.executeUpdate();
		prepareStatement.close();
		connect.close();
		
	}
}
