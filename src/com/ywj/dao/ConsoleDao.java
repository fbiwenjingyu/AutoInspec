package com.ywj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ywj.model.Console;
import com.ywj.utils.FileUtils;
import com.ywj.utils.JsonUtils;

public class ConsoleDao {
	public static final  String consoleFileName = "data\\console.json";
	
	public Console getConsoleById(int id) {
		List<Console> consoles = getAllConsoles();
		for(Console c : consoles) {
			if(c.getId() == id) {
				return c;
			}
		}
		return null;
	}
	
	public void insertConsole(Console console) {
		List<Console> consoles = getAllConsoles();
		if(consoles.size() == 0) {
			console.setId(1);
		}else {
			Console lastConsole = consoles.get(consoles.size() -1);
			console.setId(lastConsole.getId() + 1);
		}
		
		consoles.add(console);
		JSONArray array = (JSONArray) JSONArray.toJSON(consoles);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(consoleFileName, jsonString);
	}
	
	public void deleteConsoleById(int id) {
		List<Console> consoles = getAllConsoles();
		Console console = getConsoleById(id);
		if(console == null) return;
		List<Console> consoleList = new ArrayList<Console>();
		for(Console c : consoles) {
			if(c.getId() != console.getId()) {
				consoleList.add(c);
			}
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(consoleList);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(consoleFileName, jsonString);
	}
	
	public void update(int id,Console dto) {
		Console console = getConsoleById(id);
		if(console == null) return;
		//if(StringUtils.isNotEmpty(dto.getInnerurl())) {
			console.setInnerurl(dto.getInnerurl());
		//}
		//if(StringUtils.isNoneEmpty(dto.getLoginName1())) {
			console.setLoginName1(dto.getLoginName1());
		//}
		//if(StringUtils.isNotEmpty(dto.getLoginName2())) {
			console.setLoginName2(dto.getLoginName2());
		//}
		//if(StringUtils.isNotEmpty(dto.getLoginPassword1())) {
			console.setLoginPassword1(dto.getLoginPassword1());
		//}
		//if(StringUtils.isNotEmpty(dto.getLoginPassword2())) {
			console.setLoginPassword2(dto.getLoginPassword2());
		//}
		//if(StringUtils.isNotEmpty(dto.getName())) {
			console.setName(dto.getName());
		//}
		//if(StringUtils.isNoneEmpty(dto.getOuturl())) {
			console.setOuturl(dto.getOuturl());
		//}
		//if(StringUtils.isNotEmpty(dto.getWebBrowser())) {
			console.setWebBrowser(dto.getWebBrowser());
		//}
		//if(StringUtils.isNotEmpty(dto.getInnerUserTagName())) {
			console.setInnerUserTagName(dto.getInnerUserTagName());
		//}
		//if(StringUtils.isNotEmpty(dto.getInnerPasswordTagName())) {
			console.setInnerPasswordTagName(dto.getInnerPasswordTagName());
		//}
		//if(StringUtils.isNoneEmpty(dto.getOutUserTagName())) {
			console.setOutUserTagName(dto.getOutUserTagName());
		//}
		//if(StringUtils.isNoneEmpty(dto.getOutPasswordTagName())) {
			console.setOutPasswordTagName(dto.getOutPasswordTagName());
		//}
		List<Console> consoles = getAllConsoles();
		List<Console> updateConsoles = new ArrayList<>();
		for(Console c : consoles) {
			if(c.getId() != console.getId()) {
				updateConsoles.add(c);
			}else {
				updateConsoles.add(console);
			}
		}
	
		JSONArray array = (JSONArray) JSONArray.toJSON(updateConsoles);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(consoleFileName, jsonString);
	}
	
	public Console findConsleByName(String name) {
		List<Console> consoles = getAllConsoles();
		for(Console c : consoles) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public List<Console> getAllConsoles(){
		 List<Console> consoles = JsonUtils.getAllResourceByType(consoleFileName, Console.class);
		 if(consoles == null) {
			 return new ArrayList<Console>();
		 }
		 return consoles;
	}
	
	public static void main(String[] args) {
		
		Console console = new Console();
		console.setName("法人库");
		console.setInnerurl("http://10.168.5.147:8080/security/login/sysCenterMain.html");
		console.setWebBrowser("IE");
		console.setLoginName1("admin");
		console.setLoginName2("tmfrk");
		
		ConsoleDao dao = new ConsoleDao();
		dao.insertConsole(console);
	}

	public String[] getColumnNames() {
		return  new String[] {"序号","系统名称","内网地址","政务网地址","推荐浏览器","用户名","操作"};
	}
	
	public String[][] getData(){
		List<Console> allConsoles = getAllConsoles();
		String data[][] = new String[allConsoles.size()][getColumnNames().length];
		for(int i=0;i<allConsoles.size();i++) {
			Console console = allConsoles.get(i);
			data[i][0] = "" + console.getId();
			data[i][1] = console.getName();
			data[i][2] = console.getInnerurl();
			data[i][3] = console.getOuturl();
			data[i][4] = console.getWebBrowser();
			data[i][5] = "";
			if(StringUtils.isNotEmpty(console.getLoginName1())) {
				data[i][5] += console.getLoginName1();
			}
			if(StringUtils.isNoneEmpty(console.getLoginName2())) {
				data[i][5] += console.getLoginName2();
			}
			if(StringUtils.isNotEmpty(console.getLoginName1()) && StringUtils.isNoneEmpty(console.getLoginName2())){
				data[i][5] = data[i][5].substring(0, console.getLoginName1().length()) + "/" + data[i][5].substring(console.getLoginName1().length());
			}
			data[i][6] = "登录";
		}
		return data;
	} 
	
	public String[][] getDataByName(String name){
		List<Console> allConsoles = searchByName(name);
		String data[][] = new String[allConsoles.size()][getColumnNames().length];
		for(int i=0;i<allConsoles.size();i++) {
			Console console = allConsoles.get(i);
			data[i][0] = "" + console.getId();
			data[i][1] = console.getName();
			data[i][2] = console.getInnerurl();
			data[i][3] = console.getOuturl();
			data[i][4] = console.getWebBrowser();
			data[i][5] = "";
			if(StringUtils.isNotEmpty(console.getLoginName1())) {
				data[i][5] += console.getLoginName1();
			}
			if(StringUtils.isNoneEmpty(console.getLoginName2())) {
				data[i][5] += console.getLoginName2();
			}
			if(StringUtils.isNotEmpty(console.getLoginName1()) && StringUtils.isNoneEmpty(console.getLoginName2())){
				data[i][5] = data[i][5].substring(0, console.getLoginName1().length()) + "/" + data[i][5].substring(console.getLoginName1().length());
			}
			data[i][6] = "登录";
		}
		return data;
	} 
	
	public List<Console> searchByName(String name){
		if(StringUtils.isEmpty(name)) return getAllConsoles();
		List<Console> allConsoles = getAllConsoles();
		List<Console> resultConsoles = new ArrayList<Console>();
		for(Console c : allConsoles) {
			if(c != null) {
				String consoleName = c.getName();
				if(StringUtils.isNotEmpty(consoleName)) {
					if(consoleName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
						resultConsoles.add(c);
					}
				}
			}
		}
		return resultConsoles;
	}



}
