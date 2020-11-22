package com.ywj.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ywj.model.DataBase;
import com.ywj.model.DataSourceConnectDBVO;

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

//	public long getDataNums() throws Exception{
//		long nums = 0;
//		String sqlStr = "select sum(t.table_rows) from (select table_name,table_rows from information_schema.tables where " + 
//				"TABLE_SCHEMA = '" + this.getDataBaseName() + "' order by table_rows desc) as t";
//		String url = "jdbc:mysql://" + getIpaddress() + ":" + getPort() + "/" + getDataBaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
//		String username = getUserName();
//		String password = getPassword();
//		Connection con = JdbcUtils.getConn(url, username, password);
//		java.sql.Statement statement = con.createStatement();
//		ResultSet rs = statement.executeQuery(sqlStr);//执行查询语句，接收结果集
//		if(rs.next()) {
//			nums = rs.getLong(1);
//		}
//		return nums;
//	}
	
	public long getDataNums() throws Exception{
		long nums = 0;
		Connection con = getMysqlConnection();
		List<String> tableNames = getMysqlTableNames();
		
		for(String tableName : tableNames) {
			String countSql = "select count(*) from " +  "`" + tableName + "`";
			PreparedStatement prepareStatement = con.prepareStatement(countSql);
			ResultSet rs = prepareStatement.executeQuery();
			if(rs.next()) {
				long num = rs.getLong(1);
				System.err.println("tableName = " + tableName + " table count = " + num);
				nums += num;
			}
			prepareStatement.close();
			rs.close();
		}
		con.close();
		return nums;
	}
	
	public List<String> getMysqlTableNames() throws Exception {
		List<String> names = new ArrayList<String>();
		Connection con = getMysqlConnection();
		DatabaseMetaData metaData = con.getMetaData();
		ResultSet rs = metaData.getTables(getDataBaseName(), "", null, null);
		while(rs.next()) {
			names.add(rs.getString(3));  
		}
		con.close();
		return names;
	}

	private Connection getMysqlConnection() {
		String url = "jdbc:mysql://" + getIpaddress() + ":" + getPort() + "/" + getDataBaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		String username = getUserName();
		String password = getPassword();
		Connection con = JdbcUtils.getConn(url, username, password);
		return con;
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
