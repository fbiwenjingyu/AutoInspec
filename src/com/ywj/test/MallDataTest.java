package com.ywj.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.ywj.model.DataSourceConnectDBVO;
import com.ywj.utils.MyDataBaseUtil;

public class MallDataTest {

	public static void main(String[] args) throws SQLException {
		DataSourceConnectDBVO vo = new DataSourceConnectDBVO();
		vo.setDatabaseName("mall");
		vo.setDataSourceAddress("192.168.1.7");
		vo.setDataSourcePort(3306);
		vo.setAccountName("root");
		vo.setAccountPwd("123456");
		Connection connect = MyDataBaseUtil.getMySQLConnect(vo);
		connect.setAutoCommit(false);
		String sql = "insert into test(id,code) values(?,?)";
		PreparedStatement ps = connect.prepareStatement(sql);
		for(int i=0;i<1000000;i++) {
			ps.setInt(1, i+1);
			ps.setString(2, getRandomCode(6));
			ps.addBatch();
		}
		ps.executeBatch();
		connect.commit();
	}

	private static String getRandomCode(int length) {
		String result = "";
		for(int i=0;i<length;i++) {
			Random r = new Random();
			result = result + r.nextInt(10);
		}
		return result;
	}

}
