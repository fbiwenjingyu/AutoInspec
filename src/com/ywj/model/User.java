package com.ywj.model;

import java.io.Serializable;

public class User implements Serializable{
	private int id;//唯一编号
	private String username;//用户姓名
	private String userLoginName;//用户登录名称
	private String password;//用户密码
	private String phoneNum;//手机号
	private String roleId;//roleId=1表示超级用户，roleId=2表示巡检员，roleId=3表示普通用户
	private String bz;//备注
	private String firstLogin = "1";//为1表示第一次登陆，其他表示不是第一次登陆
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
