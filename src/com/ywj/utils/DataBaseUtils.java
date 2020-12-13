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
//		ResultSet rs = statement.executeQuery(sqlStr);//ִ�в�ѯ��䣬���ս����
//		if(rs.next()) {
//			nums = rs.getLong(1);
//		}
//		return nums;
//	}
	
	public long getDataNums() throws Exception{
		logger.debug("DataBaseUtils���getDataNums������ѯ���ݿ�������ܼ�¼����ʼ");
		logger.debug("���ݿ�����" + getDataBaseName());
		long nums = 0;
		Connection con = getMysqlConnection();
		List<String> tableNames = getMysqlTableNames();
		
		for(String tableName : tableNames) {
			//logger.debug("���� ��" + tableName);
			String countSql = "select count(*) from " +  "`" + tableName + "`";
			PreparedStatement prepareStatement = con.prepareStatement(countSql);
			ResultSet rs = prepareStatement.executeQuery();
			if(rs.next()) {
				long num = rs.getLong(1);
				logger.debug("���� ��" + tableName + " ��¼�� ��" + num);
				System.err.println("tableName = " + tableName + " table count = " + num);
				nums += num;
			}
			prepareStatement.close();
			rs.close();
		}
		con.close();
		logger.debug("�ܼ�¼��Ϊ��" + nums);
		logger.debug("DataBaseUtils���getDataNums������ѯ���ݿ�������ܼ�¼������");
		
		return nums;
	}
	
	public List<String> getMysqlTableNames() throws Exception {
		logger.debug("DataBaseUtils���getMysqlTableNames������ȡ���ݿ����б�����ʼ");
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
			logger.error("DataBaseUtils���getMysqlTableNames������ȡ���ݿ����б����������ݿ���Ϊ��" + getDataBaseName() + " ������ϢΪ��" + e.toString() );
		}
		logger.debug("DataBaseUtils���getMysqlTableNames������ȡ���ݿ����б�������");
		return names;
	}

	private Connection getMysqlConnection() {
		logger.debug("DataBaseUtils���getMysqlConnection������ȡ���ݿ����ӿ�ʼ");
		String url = "jdbc:mysql://" + getIpaddress() + ":" + getPort() + "/" + getDataBaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		String username = getUserName();
		String password = getPassword();
		Connection con = null;
		try {
			con = JdbcUtils.getConn(url, username, password);
		}catch(Exception e) {
			logger.error("DataBaseUtils���getMysqlConnection������ȡ���ݿ����ӳ������ݿ���Ϊ��" + getDataBaseName() + " ������ϢΪ��" + e.toString());
		}
		logger.debug("DataBaseUtils���getMysqlConnection������ȡ���ݿ����ӽ���");
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
