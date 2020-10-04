package com.ywj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ywj.model.Machine;
import com.ywj.model.User;
import com.ywj.utils.FileUtils;
import com.ywj.utils.JsonUtils;

public class UserDao {
	public static final  String userFileName = "data\\user.json";
	
	public User getUserById(int id) {
		List<User> users = getAllUsers();
		for(User u : users) {
			if(u.getId() == id) {
				return u;
			}
		}
		return null;
	}
	
	public void insertUser(User user) {
		List<User> users = getAllUsers();
		if(users.size()==0) {
			user.setId(1);
		}else {
			User lastUser = users.get(users.size() -1);
			user.setId(lastUser.getId() + 1);
		}
		
		users.add(user);
		JSONArray array = (JSONArray) JSONArray.toJSON(users);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(userFileName, jsonString);
	}
	
	public void deleteUserById(int id) {
		List<User> users = getAllUsers();
		User user = getUserById(id);
		if(user == null) return;
		List<User> userList = new ArrayList<>();
		for(User u : users) {
			if(u.getId() != user.getId()) {
				userList.add(u);
			}
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(userList);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(userFileName, jsonString);
	}
	
	public void update(int id,User dto) {
		User user = getUserById(id);
		if(user == null) return;
		if(StringUtils.isNotEmpty(dto.getBz())) {
			user.setBz(dto.getBz());
		}
		if(StringUtils.isNoneEmpty(dto.getPassword())) {
			user.setPassword(dto.getPassword());
		}
		if(StringUtils.isNotEmpty(dto.getPhoneNum())) {
			user.setPhoneNum(dto.getPhoneNum());
		}
		if(StringUtils.isNotEmpty(dto.getRoleId())) {
			user.setRoleId(dto.getRoleId());
		}
		if(StringUtils.isNotEmpty(dto.getUserLoginName())) {
			user.setUserLoginName(dto.getUserLoginName());
		}
		if(StringUtils.isNotEmpty(dto.getUsername())) {
			user.setUsername(dto.getUsername());
		}
		if(StringUtils.isNotEmpty(dto.getFirstLogin())) {
			user.setFirstLogin(dto.getFirstLogin());
		}
		List<User> users = getAllUsers();
		List<User> updateUsers = new ArrayList<>();
		for(User u : users) {
			if(u.getId() != user.getId()) {
				updateUsers.add(u);
			}else {
				updateUsers.add(user);
			}
		}
	
		JSONArray array = (JSONArray) JSONArray.toJSON(updateUsers);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(userFileName, jsonString);
	}
	
	
	public User findUserByUsername(String username) {
		List<User> users = getAllUsers();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}
	
	public User findUserByUserLoginName(String userLoginName) {
		List<User> users = getAllUsers();
		for(User u : users) {
			if(u.getUserLoginName().equals(userLoginName)) {
				return u;
			}
		}
		return null;
	}
	
	
	public List<User> getAllUsers(){
		List<User> users = JsonUtils.getAllResourceByType(userFileName, User.class);
		if(users == null) {
			return new ArrayList<User>();
		}
		return users;
	}
	
	public String[] getColumnNames() {
		return new String[] {"序号","用户名","姓名","手机号码","备注"};
	}
	
	public String[][] getData(){
		List<User> allUsers = getAllUsers();
		String data[][] = new String[allUsers.size()][getColumnNames().length];
		for(int i=0;i<allUsers.size();i++) {
			User user = allUsers.get(i);
			data[i][0] = "" + user.getId();
			data[i][1] = user.getUserLoginName();
			data[i][2] = user.getUsername();
			data[i][3] = user.getPhoneNum();
			data[i][4] = user.getBz();
		}
		return data;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("廖杭");
		user.setUserLoginName("admin");
		user.setPhoneNum("15527312372");
		user.setBz("系统管理员");
		user.setPassword("111111");
		UserDao dao = new UserDao();
		dao.insertUser(user);
//		dao.deleteUserById(1);
//		dao.update(1, user);
//		User u = dao.findUserByUsername("于文静");
//		System.out.println(u);
		User u = dao.findUserByUserLoginName("admin");
		System.out.println(u);
		
		
		
	}

	public String[][] getDataBySearch(String userLoginName, String userName) {
		List<User> allUsers = searchByUserLoginNameAndUserName(userLoginName,userName);
		String data[][] = new String[allUsers.size()][getColumnNames().length];
		for(int i=0;i<allUsers.size();i++) {
			User user = allUsers.get(i);
			data[i][0] = "" + user.getId();
			data[i][1] = user.getUserLoginName();
			data[i][2] = user.getUsername();
			data[i][3] = user.getPhoneNum();
			data[i][4] = user.getBz();
		}
		return data;
		
	} 
	
	public List<User> searchByUserLoginNameAndUserName(String userLoginName,String userName){
		if(StringUtils.isEmpty(userLoginName) && StringUtils.isEmpty(userName)) return getAllUsers();
		if(userLoginName == null) userLoginName = " ";
		if(userName == null) userName = " ";
		List<User> allUsers = getAllUsers();
		List<User> resultUsers = new ArrayList<User>();
		for(User u : allUsers) {
			if(u != null) {
				if(StringUtils.isNotEmpty(userLoginName) && StringUtils.isNotEmpty(userName)) {
					if((u.getUserLoginName().toLowerCase().indexOf(userLoginName.toLowerCase()) != -1) && (u.getUsername().indexOf(userName) != -1 )) {
						resultUsers.add(u);
					}
				}else if(StringUtils.isNotEmpty(userLoginName)) {
					if(u.getUserLoginName().toLowerCase().indexOf(userLoginName.toLowerCase()) != -1){
						resultUsers.add(u);
					}
				}else if(StringUtils.isNotEmpty(userName)) {
					if(u.getUsername().indexOf(userName) != -1 ){
						resultUsers.add(u);
					}
				}
			}
		}
		return resultUsers;
	}



}
