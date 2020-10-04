package com.ywj.model;

import java.io.Serializable;

public class DataTransferVO implements Serializable{
	private int id;//Ψһid
	private String deptName;
	private String mainIpAddress;
	private int mainPort;
	private String mainUserName;
	private String mainPassword;
	private String mainDatabaseName;
	
	private String backIpAddress;
	private int backPort;
	private String backUserName;
	private String backPassword;
	private String backDatabaseName;
	
	public DataTransferVO(int id, String deptName, String mainIpAddress, int mainPort, String mainUserName,
			String mainPassword, String mainDatabaseName, String backIpAddress, int backPort, String backUserName,
			String backPassword, String backDatabaseName) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.mainIpAddress = mainIpAddress;
		this.mainPort = mainPort;
		this.mainUserName = mainUserName;
		this.mainPassword = mainPassword;
		this.mainDatabaseName = mainDatabaseName;
		this.backIpAddress = backIpAddress;
		this.backPort = backPort;
		this.backUserName = backUserName;
		this.backPassword = backPassword;
		this.backDatabaseName = backDatabaseName;
	}
	public DataTransferVO() {
		super();
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getMainIpAddress() {
		return mainIpAddress;
	}
	public void setMainIpAddress(String mainIpAddress) {
		this.mainIpAddress = mainIpAddress;
	}
	public int getMainPort() {
		return mainPort;
	}
	public void setMainPort(int mainPort) {
		this.mainPort = mainPort;
	}
	public String getMainUserName() {
		return mainUserName;
	}
	public void setMainUserName(String mainUserName) {
		this.mainUserName = mainUserName;
	}
	public String getMainPassword() {
		return mainPassword;
	}
	public void setMainPassword(String mainPassword) {
		this.mainPassword = mainPassword;
	}
	public String getMainDatabaseName() {
		return mainDatabaseName;
	}
	public void setMainDatabaseName(String mainDatabaseName) {
		this.mainDatabaseName = mainDatabaseName;
	}
	public String getBackIpAddress() {
		return backIpAddress;
	}
	public void setBackIpAddress(String backIpAddress) {
		this.backIpAddress = backIpAddress;
	}
	public int getBackPort() {
		return backPort;
	}
	public void setBackPort(int backPort) {
		this.backPort = backPort;
	}
	public String getBackUserName() {
		return backUserName;
	}
	public void setBackUserName(String backUserName) {
		this.backUserName = backUserName;
	}
	public String getBackPassword() {
		return backPassword;
	}
	public void setBackPassword(String backPassword) {
		this.backPassword = backPassword;
	}
	public String getBackDatabaseName() {
		return backDatabaseName;
	}
	public void setBackDatabaseName(String backDatabaseName) {
		this.backDatabaseName = backDatabaseName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MainAndBackDataSourceConnectDBVO convertToMainAndBackDataSourceConnectDBVO() {
		MainAndBackDataSourceConnectDBVO mainAndBack = new MainAndBackDataSourceConnectDBVO();
		DataSourceConnectDBVO main = new DataSourceConnectDBVO();
		main.setDataSourceAddress(this.getMainIpAddress());
		main.setDataSourcePort(this.getMainPort());
		main.setAccountName(this.getMainUserName());
		main.setAccountPwd(this.getMainPassword());	
		main.setDatabaseName(this.getMainDatabaseName());
		
		DataSourceConnectDBVO back = new DataSourceConnectDBVO();
		back.setDataSourceAddress(this.getBackIpAddress());
		back.setDataSourcePort(this.getBackPort());
		back.setAccountName(this.getBackUserName());
		back.setAccountPwd(this.getBackPassword());
		back.setDatabaseName(this.getBackDatabaseName());
		
		mainAndBack.setMaindb(main);
		mainAndBack.setBakdb(back);
		
		return mainAndBack;
	}
	@Override
	public String toString() {
		return "DataTransferVO [id=" + id + ", deptName=" + deptName + ", mainIpAddress=" + mainIpAddress
				+ ", mainPort=" + mainPort + ", mainUserName=" + mainUserName + ", mainPassword=" + mainPassword
				+ ", mainDatabaseName=" + mainDatabaseName + ", backIpAddress=" + backIpAddress + ", backPort="
				+ backPort + ", backUserName=" + backUserName + ", backPassword=" + backPassword + ", backDatabaseName="
				+ backDatabaseName + "]";
	}
	
	
	
	
	
	

}
