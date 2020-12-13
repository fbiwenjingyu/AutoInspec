package com.ywj.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ywj.model.DataBase;

public class DataBaseUtils {
	private static final Logger logger = LoggerFactory.getLogger(DataBaseUtils.class); 
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
		logger.debug("DataBaseUtils类的getDataNums方法查询数据库表数据总记录数开始");
		logger.debug("数据库名：" + getDataBaseName());
		long nums = 0;
		Connection con = getMysqlConnection();
		List<String> tableNames = getMysqlTableNames();
		
		for(String tableName : tableNames) {
			//logger.debug("表名 ：" + tableName);
			String countSql = "select count(*) from " +  "`" + tableName + "`";
			PreparedStatement prepareStatement = con.prepareStatement(countSql);
			ResultSet rs = prepareStatement.executeQuery();
			if(rs.next()) {
				long num = rs.getLong(1);
				logger.debug("表名 ：" + tableName + " 记录数 ：" + num);
				System.err.println("tableName = " + tableName + " table count = " + num);
				nums += num;
			}
			prepareStatement.close();
			rs.close();
		}
		con.close();
		logger.debug("总记录数为：" + nums);
		logger.debug("DataBaseUtils类的getDataNums方法查询数据库表数据总记录数结束");
		
		return nums;
	}
	
	public List<String> getMysqlTableNames() throws Exception {
		logger.debug("DataBaseUtils类的getMysqlTableNames方法获取数据库所有表名开始");
		List<String> names = new ArrayList<String>();
		try {
			Connection con = getMysqlConnection();
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet rs = metaData.getTables(getDataBaseName(), "", null, null);
			while(rs.next()) {
				if (rs.getString(4).equalsIgnoreCase("TABLE")) {
					names.add(rs.getString(3)); 
				}
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("DataBaseUtils类的getMysqlTableNames方法获取数据库所有表名出错，数据库名为：" + getDataBaseName() + " 错误信息为：" + e.toString() );
		}
		logger.debug("DataBaseUtils类的getMysqlTableNames方法获取数据库所有表名结束");
		return names;
	}

	private Connection getMysqlConnection() {
		logger.debug("DataBaseUtils类的getMysqlConnection方法获取数据库连接开始");
		String url = "jdbc:mysql://" + getIpaddress() + ":" + getPort() + "/" + getDataBaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		String username = getUserName();
		String password = getPassword();
		Connection con = null;
		try {
			con = JdbcUtils.getConn(url, username, password);
		}catch(Exception e) {
			logger.error("DataBaseUtils类的getMysqlConnection方法获取数据库连接出错，数据库名为：" + getDataBaseName() + " 错误信息为：" + e.toString());
		}
		logger.debug("DataBaseUtils类的getMysqlConnection方法获取数据库连接结束");
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
