package com.ywj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {
		// 连接驱动
		//private static final String DRIVER = "com.mysql.jdbc.Driver"; 
		// 连接驱动
		private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
		// 静态代码块
		static {
			try {
				// 加载驱动
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("数据库连接异常");
			}
		}
		
		/*
		 * 获取数据库连接
		 */
		public  static Connection getConn(String url,String username,String password) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, username, password);
				//System.out.println("数据库连接成功!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
		
		/*
		 * 关闭数据库连接，注意关闭的顺序
		 */
		public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
					ps = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

}
