package com.ywj.model;

import java.io.Serializable;

public class User implements Serializable{
	private int id;//Ψһ���
	private String username;//�û�����
	private String userLoginName;//�û���¼����
	private String password;//�û�����
	private String phoneNum;//�ֻ���
	private String roleId;//roleId=1��ʾ�����û���roleId=2��ʾѲ��Ա��roleId=3��ʾ��ͨ�û�
	private String bz;//��ע
	private String firstLogin = "1";//Ϊ1��ʾ��һ�ε�½��������ʾ���ǵ�һ�ε�½
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public String getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	public User() {
		super();
	}
	public User(int id, String username, String userLoginName, String password, String phoneNum, String roleId,
			String bz) {
		super();
		this.id = id;
		this.username = username;
		this.userLoginName = userLoginName;
		this.password = password;
		this.phoneNum = phoneNum;
		this.roleId = roleId;
		this.bz = bz;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userLoginName=" + userLoginName + ", password="
				+ password + ", phoneNum=" + phoneNum + ", roleId=" + roleId + ", bz=" + bz + "]";
	}
	
	
	
	

}
