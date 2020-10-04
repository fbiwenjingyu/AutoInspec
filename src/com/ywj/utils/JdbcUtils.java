package com.ywj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {
		// ��������
		//private static final String DRIVER = "com.mysql.jdbc.Driver"; 
		// ��������
		private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
		// ��̬�����
		static {
			try {
				// ��������
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("���ݿ������쳣");
			}
		}
		
		/*
		 * ��ȡ���ݿ�����
		 */
		public  static Connection getConn(String url,String username,String password) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, username, password);
				//System.out.println("���ݿ����ӳɹ�!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
		
		/*
		 * �ر����ݿ����ӣ�ע��رյ�˳��
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
