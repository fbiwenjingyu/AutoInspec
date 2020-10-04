package com.ywj.model;

import java.io.Serializable;
import java.util.List;

public class Machine implements Serializable{
	private int id;//Ψһ���
	private String machineName;//������������
	private String englishName;//����Ӣ������
	private String osTypeName;//ϵͳ����
	private String ipAddress;//����ip��ַ
	List<String> ports;//�������ŵĶ˿��б�
	private String pingResult;//Ϊ1��ʾ���ߣ�Ϊ0��ʾ������
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public List<String> getPorts() {
		return ports;
	}
	public void setPorts(List<String> ports) {
		this.ports = ports;
	}
	public String getPingResult() {
		return pingResult;
	}
	public void setPingResult(String pingResult) {
		this.pingResult = pingResult;
	}
	
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getOsTypeName() {
		return osTypeName;
	}
	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}
	public Machine() {
		super();
	}
	public Machine(int id, String machineName, String englishName, String osTypeName, String ipAddress,
			List<String> ports, String pingResult) {
		super();
		this.id = id;
		this.machineName = machineName;
		this.englishName = englishName;
		this.osTypeName = osTypeName;
		this.ipAddress = ipAddress;
		this.ports = ports;
		this.pingResult = pingResult;
	}
	@Override
	public String toString() {
		return "Machine [id=" + id + ", machineName=" + machineName + ", englishName=" + englishName + ", osTypeName="
				+ osTypeName + ", ipAddress=" + ipAddress + ", ports=" + ports + ", pingResult=" + pingResult + "]";
	}
	
	
	
	
	
	 

}
