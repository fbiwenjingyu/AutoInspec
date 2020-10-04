package com.ywj.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;

import com.ywj.model.DataSourceConnectDBVO;
import com.ywj.model.MainAndBackDataSourceConnectDBVO;
import com.ywj.model.TableFieldVO;

public class SynchMain3 {
	public static final int synchrows = 100000;

	public void synch(MainAndBackDataSourceConnectDBVO mainAndBackDataSourceVO, String synchtablename)
			throws Exception {
		DataSourceConnectDBVO maindb = mainAndBackDataSourceVO.getMaindb();
		DataSourceConnectDBVO bakdb = mainAndBackDataSourceVO.getBakdb();

		int pageSize = synchrows;// 每次同步的记录数
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
		Connection bakconn = null;
		bakconn = MyDataBaseUtil.getMySQLConnect(bakdb);
		mainconn = MyDataBaseUtil.getMySQLConnect(maindb);
		truncateBackTable(bakconn,tableName);
		
		long startTime = System.currentTimeMillis();
		long totalCount = getTotalCount(mainconn,tableName); 
		long totalPage = getTotalPage(totalCount,synchrows);
		
		WorkThread[] workThreads = new WorkThread[(int) totalPage];
		for(int curpage=0;curpage<totalPage;curpage++) {
			workThreads[curpage] = new WorkThread(maindb, bakdb, tableName, curpage+1, pageSize, fields, values, idfield, list);
		}
		
		for(int curpage=0;curpage<totalPage;curpage++) {
			workThreads[curpage].start();
			//workThreads[curpage].join();
		}
	
		System.out.println("表" + tableName + " 耗时："
				+ (System.currentTimeMillis() - startTime) + "毫秒");
	}
	
	public static final class WorkThread extends Thread{
		private String tableName;
		private int curpage;
		private int pageSize;
		private String fields;
		private String values;
		private String idfield;
		DataSourceConnectDBVO maindb;
		DataSourceConnectDBVO bakdb;
		List<TableFieldVO> list;
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public int getCurpage() {
			return curpage;
		}
		public void setCurpage(int curpage) {
			this.curpage = curpage;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public String getFields() {
			return fields;
		}
		public void setFields(String fields) {
			this.fields = fields;
		}
		public String getValues() {
			return values;
		}
		public void setValues(String values) {
			this.values = values;
		}
		public String getIdfield() {
			return idfield;
		}
		public void setIdfield(String idfield) {
			this.idfield = idfield;
		}
		
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
		
		
		
		
		public List<TableFieldVO> getList() {
			return list;
		}
		public void setList(List<TableFieldVO> list) {
			this.list = list;
		}
		public WorkThread(DataSourceConnectDBVO maindb, DataSourceConnectDBVO bakdb,String tableName, int curpage, int pageSize, String fields, String values, String idfield,List<TableFieldVO> list
				) {
			super();
			this.tableName = tableName;
			this.curpage = curpage;
			this.pageSize = pageSize;
			this.fields = fields;
			this.values = values;
			this.idfield = idfield;
			this.maindb = maindb;
			this.bakdb = bakdb;
			this.list = list;
		}
		@Override
		public void run() {
			Connection mainconn = null;
			PreparedStatement mainps = null;
			ResultSet mainrs = null;
			Connection bakconn  = null;
			PreparedStatement bakps  = null;
			try {
				String orderby = "".equals(idfield) ? "" : " order by " + idfield + " ";
				String limit = orderby + " limit " + (curpage - 1) * pageSize + "," + pageSize;
				String mainQuerySQL = "select * from " + tableName + limit;
				String bakInsertSQL = "insert into " + tableName + " (" + fields + ") values (" + values + ")";
				System.out.println("bakInsertSQL=" + bakInsertSQL);
				// 主库
				mainconn = MyDataBaseUtil.getMySQLConnect(maindb);
				mainps = mainconn.prepareStatement(mainQuerySQL);
				mainrs = mainps.executeQuery();
				// 被库
				bakconn = MyDataBaseUtil.getMySQLConnect(bakdb);
				bakconn.setAutoCommit(false);
				bakps = bakconn.prepareStatement(bakInsertSQL);
				while (mainrs.next()) {
					for (int i = 0; i < list.size(); i++) {
						bakps.setObject(i + 1, mainrs.getObject(list.get(i).getColumnName()));
					}
					bakps.addBatch();
				}
				bakps.executeBatch();
				bakconn.commit();
			} catch (Exception e) {
				e.printStackTrace();
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
					try {
						mainconn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
					try {
						bakconn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		
	}

	private long getTotalPage(long totalCount, int pageSize) {
		if(totalCount%pageSize == 0) return totalCount / pageSize;
		return totalCount / pageSize + 1;
	}

	private long getTotalCount(Connection mainconn, String tableName) throws Exception{
		Statement stmt = mainconn.createStatement();  
        // 为每个线程分配结果集
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + tableName);  
        rs.next();  
        // 总共处理的数量
        long totalCount = rs.getLong(1);  
        mainconn.close();
        return totalCount;
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
		ExecutorService exector = Executors.newFixedThreadPool(tableNames.size());
		
		long startTime = System.currentTimeMillis();
		for(String tableName : tableNames) {
			SynchTask synchTask = new SynchTask(mainAndBackDataSourceVO,tableName);
			exector.submit(synchTask);
		}
		exector.shutdown();
		while(true) {
			if(exector.isTerminated()) {
				break;
			}
		}
		System.out.println("总共 耗时："
				+ (System.currentTimeMillis() - startTime) + "毫秒");
		SynchMain3 main = new SynchMain3();
		long totalPage = main.getTotalPage(100, 200);
		System.out.println(totalPage);
		totalPage = main.getTotalPage(101, 10);
		System.out.println(totalPage);
	}

}