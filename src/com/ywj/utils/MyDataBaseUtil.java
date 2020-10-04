package com.ywj.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ywj.model.DataSourceConnectDBVO;
import com.ywj.model.TableFieldVO;

public class MyDataBaseUtil {
	public static List<TableFieldVO> getFieldsForTable(String tableName,DataSourceConnectDBVO maindb) throws Exception{
		List<TableFieldVO> list = new ArrayList<TableFieldVO>();
		Connection connection = getMySQLConnect(maindb);
		PreparedStatement prepareStatement = connection.prepareStatement("select * from " + tableName + " limit 0,1");
		ResultSet rs = prepareStatement.executeQuery();
		if(rs.next()) {
			int columnCount = rs.getMetaData().getColumnCount();
			for(int i=1;i<=columnCount;i++) {
				String columnName = rs.getMetaData().getColumnName(i);
				int columnType = rs.getMetaData().getColumnType(i);
				TableFieldVO field = new TableFieldVO(columnName, columnType);
				list.add(field);
			}
		}
		connection.close();
		return list;
	}
	
	public static Connection getMySQLConnect(DataSourceConnectDBVO db) {
		String url = "jdbc:mysql://" + db.getDataSourceAddress() + ":" + db.getDataSourcePort() + "/" + db.getDatabaseName() +"?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
		return JdbcUtils.getConn(url, db.getAccountName(), db.getAccountPwd());
	}
	
	public static List<String> getMysqlTableNames(DataSourceConnectDBVO db) throws Exception {
		List<String> names = new ArrayList<String>();

		Connection con = getMySQLConnect(db);
		
		DatabaseMetaData metaData = con.getMetaData();
		ResultSet rs = metaData.getTables(db.getDatabaseName(), "", null, null);
		while(rs.next()) {
			names.add(rs.getString(3));  
		}
		con.close();
		return names;
	}

}
