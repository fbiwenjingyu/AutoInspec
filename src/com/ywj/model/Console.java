package com.ywj.model;

import java.io.Serializable;

public class Console implements Serializable{
	private int id;//唯一id
	private String name;//系统名称
	private String innerurl;//内网地址
	private String outurl;//政务网地址
	private String loginName1;
	private String loginName2;
	private String loginPassword1;
	private String loginPassword2;
	private String webBrowser;
	private String innerUserTagName;
	private String innerPasswordTagName;
	private String outUserTagName;
	private String outPasswordTagName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInnerurl() {
		return innerurl;
	}
	public void setInnerurl(String innerurl) {
		this.innerurl = innerurl;
	}
	public String getOuturl() {
		return outurl;
	}
	public void setOuturl(String outurl) {
		this.outurl = outurl;
	}
	public String getLoginName1() {
		return loginName1;
	}
	public void setLoginName1(String loginName1) {
		this.loginName1 = loginName1;
	}
	public String getLoginName2() {
		return loginName2;
	}
	public void setLoginName2(String loginName2) {
		this.loginName2 = loginName2;
	}
	public String getLoginPassword1() {
		return loginPassword1;
	}
	public void setLoginPassword1(String loginPassword1) {
		this.loginPassword1 = loginPassword1;
	}
	public String getLoginPassword2() {
		return loginPassword2;
	}
	public void setLoginPassword2(String loginPassword2) {
		this.loginPassword2 = loginPassword2;
	}
	
	
	public String getWebBrowser() {
		return webBrowser;
	}
	public void setWebBrowser(String webBrowser) {
		this.webBrowser = webBrowser;
	}
	
	public String getInnerUserTagName() {
		return innerUserTagName;
	}
	public void setInnerUserTagName(String innerUserTagName) {
		this.innerUserTagName = innerUserTagName;
	}
	public String getInnerPasswordTagName() {
		return innerPasswordTagName;
	}
	public void setInnerPasswordTagName(String innerPasswordTagName) {
		this.innerPasswordTagName = innerPasswordTagName;
	}
	public String getOutUserTagName() {
		return outUserTagName;
	}
	public void setOutUserTagName(String outUserTagName) {
		this.outUserTagName = outUserTagName;
	}
	public String getOutPasswordTagName() {
		return outPasswordTagName;
	}
	public void setOutPasswordTagName(String outPasswordTagName) {
		this.outPasswordTagName = outPasswordTagName;
	}
	public Console() {
		super();
	}
	public Console(int id, String name, String innerurl, String outurl, String loginName1, String loginName2,
			String loginPassword1, String loginPassword2, String webBrowser) {
		super();
		this.id = id;
		this.name = name;
		this.innerurl = innerurl;
		this.outurl = outurl;
		this.loginName1 = loginName1;
		this.loginName2 = loginName2;
		this.loginPassword1 = loginPassword1;
		this.loginPassword2 = loginPassword2;
		this.webBrowser = webBrowser;
	}
	@Override
	public String toString() {
		return "Console [id=" + id + ", name=" + name + ", innerurl=" + innerurl + ", outurl=" + outurl
				+ ", loginName1=" + loginName1 + ", loginName2=" + loginName2 + ", loginPassword1=" + loginPassword1
				+ ", loginPassword2=" + loginPassword2 + ", webBrowser=" + webBrowser + ", innerUserTagName="
				+ innerUserTagName + ", innerPasswordTagName=" + innerPasswordTagName + ", outUserTagName="
				+ outUserTagName + ", outPasswordTagName=" + outPasswordTagName + "]";
	}
	
}
