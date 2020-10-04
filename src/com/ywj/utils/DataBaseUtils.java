package com.ywj.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import com.ywj.model.DataBase;

public class DataBaseUtils {
	private DataBase database;
	public DataBaseUtils(DataBase database) {
		this.database = database;
	}
	
	public DataBase getDatabase() {
		return this.database;
	}

	public void setDatabase(DataBase database) {
		this.database = database;
	}

	public long getDataNums() throws Exception{
		long nums = 0;
		String sqlStr = "select sum(t.table_rows) from (select table_name,table_rows from information_schema.tables where " + 
				"TABLE_SCHEMA = '" + this.getDataBaseName() + "' order by table_rows desc) as t";
		String url = "jdbc:mysql://" + getIpaddress() + ":" + getPort() + "/" + getDataBaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		String username = getUserName();
		String password = getPassword();
		Connection con = JdbcUtils.getConn(url, username, password);
		java.sql.Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sqlStr);//执行查询语句，接收结果集
		if(rs.next()) {
			nums = rs.getLong(1);
		}
		return nums;
	}
	
	private String getUserName() {
		return this.getDatabase().getUsername();
	}
	
	private String getPassword() {
		return this.getDatabase().getPassword();
	}
	
	private String getIpaddress() {
		return this.getDatabase().getIpaddress();
	}
	
	private String getPort() {
		return this.getDatabase().getPort();
	}
	
	private String getDataBaseName() {
		String databasename = this.getDatabase().getDatabaseName();
		if(databasename.indexOf("-") != -1) {
			return databasename.substring(databasename.indexOf("-") + 1);
		}
		return databasename;
	}
	
	

}
