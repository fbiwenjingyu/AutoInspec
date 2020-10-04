package com.ywj.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ywj.model.DataSourceConnectDBVO;
import com.ywj.model.MainAndBackDataSourceConnectDBVO;
import com.ywj.model.TableFieldVO;

public class SynchMain {
	public static final int synchrows = 10000;

	public void synch(MainAndBackDataSourceConnectDBVO mainAndBackDataSourceVO, String synchtablename)
			throws Exception {
		DataSourceConnectDBVO maindb = mainAndBackDataSourceVO.getMaindb();
		DataSourceConnectDBVO bakdb = mainAndBackDataSourceVO.getBakdb();

		int currows = synchrows;// 每次同步的记录数
		// 同步表名.
		if (StringUtils.isEmpty(synchtablename)) {
			return;
		}

		String tableName = synchtablename;
		// 获取同步字段名
		List<TableFieldVO> list = MyDataBaseUtil.getFieldsForTable(tableName, maindb);
		if(list.isEmpty()) return;
		
		String fields = "" + list.get(0).getColumnName() + "";
		String values = "?";
		String idfield = "";
		if ("id".equals(list.get(0).getColumnName())) {
			idfield = list.get(0).getColumnName();
		}
		int paraCount = list.size();
		for (int i = 1; i < paraCount; i++) {
			String columnName = list.get(i).getColumnName();
			fields = fields + "," + columnName + "";
			values = values + ",?";
			if ("id".equals(columnName.toLowerCase())) {
				idfield = columnName;
			}
		}

		Connection mainconn = null;
		PreparedStatement mainps = null;
		ResultSet mainrs = null;
		Connection bakconn = null;
		bakconn = MyDataBaseUtil.getMySQLConnect(bakdb);
		truncateBackTable(bakconn,tableName);
		PreparedStatement bakps = null;

		int curpage = 1;// 第几页,默认显示第1页
		long startTime = System.currentTimeMillis();
		String mainQuerySQL = "";
		String bakInsertSQL = "";
		while (true) {
			try {
				String orderby = "".equals(idfield) ? "" : " order by " + idfield + " ";
				String limit = orderby + " limit " + (curpage - 1) * currows + "," + currows;
				mainQuerySQL = "select * from " + tableName + limit;
				bakInsertSQL = "insert ignore into " + tableName + " (" + fields + ") values (" + values + ")";
				System.out.println("bakInsertSQL=" + bakInsertSQL);
				// 主库
				mainconn = MyDataBaseUtil.getMySQLConnect(maindb);
				mainps = mainconn.prepareStatement(mainQuerySQL);
				mainrs = mainps.executeQuery();
				// 被库
				bakconn = MyDataBaseUtil.getMySQLConnect(bakdb);
				bakconn.setAutoCommit(false);
				bakps = bakconn.prepareStatement(bakInsertSQL);
				int querySize = 0;
				while (mainrs.next()) {
					querySize++;
					for (int i = 0; i < paraCount; i++) {
						bakps.setObject(i + 1, mainrs.getObject(list.get(i).getColumnName()));
					}
					bakps.addBatch();
				}
				if (querySize == 0) {
					break;
				}
				curpage++;
				bakps.executeBatch();
				bakconn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			} finally {
				// 主库连接释放
				if (mainrs != null) {
					try {
						mainrs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (mainps != null) {
					try {
						mainps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (mainconn != null) {
					mainconn.close();
				}
				// 备库连接释放
				if (bakps != null) {
					try {
						bakps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bakconn != null) {
					bakconn.close();
				}
			}
		}
		System.out.println(tableName + "表" + tableName + "耗时："
				+ (System.currentTimeMillis() - startTime) + "毫秒");
	}

	private void truncateBackTable(Connection bakconn, String synchtablename) throws SQLException {
		String sql = "truncate table " + synchtablename;
		PreparedStatement statement = bakconn.prepareStatement(sql);
		statement.executeUpdate();
		bakconn.close();
	}

	public static void main(String[] args) throws Exception {
		MainAndBackDataSourceConnectDBVO mainAndBackDataSourceVO = new MainAndBackDataSourceConnectDBVO ();
		DataSourceConnectDBVO maindb = new DataSourceConnectDBVO();
		maindb.setDataSourceAddress("192.168.1.7");
		maindb.setAccountName("root");
		maindb.setAccountPwd("123456");
		maindb.setDataSourcePort(3306);
		maindb.setDatabaseName("mall");
		
		DataSourceConnectDBVO backdb = new DataSourceConnectDBVO();
		backdb.setDataSourceAddress("192.168.1.5");
		backdb.setAccountName("root");
		backdb.setAccountPwd("123456");
		backdb.setDataSourcePort(3306);
		backdb.setDatabaseName("mall");
		
		mainAndBackDataSourceVO.setMaindb(maindb);
		mainAndBackDataSourceVO.setBakdb(backdb);
		List<String> tableNames = MyDataBaseUtil.getMysqlTableNames(maindb);
		for(String tableName : tableNames) {
			new SynchThread(mainAndBackDataSourceVO,tableName).start();
		}
	}

}