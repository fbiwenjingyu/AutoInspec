package com.ywj.model;

import java.io.Serializable;

public class DataBase implements Serializable{
	private int id;//Ψһid
	private String deptName;//��������
	private String databaseName;//���ݿ�����
	private String ipaddress;//ip
	private String port;//�˿ں�
	private String username;//�û���
	private String password;//����
	private long dataNums;//��������
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getDataNums() {
		return dataNums;
	}
	public void setDataNums(long dataNums) {
		this.dataNums = dataNums;
	}
	public DataBase(int id, String deptName, String databaseName, String ipaddress, String port, String username,
			String password, long dataNums) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.databaseName = databaseName;
		this.ipaddress = ipaddress;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dataNums = dataNums;
	}
	public DataBase() {
		super();
	}
	
	
	
	

}
