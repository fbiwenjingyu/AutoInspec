package com.ywj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ywj.model.Console;
import com.ywj.model.Machine;
import com.ywj.model.Machine;
import com.ywj.utils.FileUtils;
import com.ywj.utils.JsonUtils;

public class MachineDao {
	public static final  String machineFileName = "data\\machines.json";
	public Machine getMachineById(int id) {
		List<Machine> machines = getAllMachines();
		for(Machine m : machines) {
			if(m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public void insertMachine(Machine machine) {
		List<Machine> machines = getAllMachines();
		if(machines.size()==0) {
			machine.setId(1);
		}else {
			Machine lastMachine = machines.get(machines.size() -1);
			machine.setId(lastMachine.getId() + 1);
		}
		machine.setId(machines.size() + 1);
		machines.add(machine);
		JSONArray array = (JSONArray) JSONArray.toJSON(machines);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(machineFileName, jsonString);
	}
	
	public void deleteMachineById(int id) {
		List<Machine> machines = getAllMachines();
		Machine machine = getMachineById(id);
		if(machine == null) return;
		List<Machine> machineList = new ArrayList<Machine>();
		for(Machine m : machines) {
			if(m.getId() != machine.getId()) {
				machineList.add(m);
			}
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(machineList);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(machineFileName, jsonString);
	}
	
	public void update(int id,Machine dto) {
		Machine machine = getMachineById(id);
		if(machine == null) return;
		if(StringUtils.isNotEmpty(dto.getEnglishName())) {
			machine.setEnglishName(dto.getEnglishName());
		}
		if(StringUtils.isNotEmpty(dto.getIpAddress())) {
			machine.setIpAddress(dto.getIpAddress());
		}
		if(StringUtils.isNotEmpty(dto.getMachineName())) {
			machine.setMachineName(dto.getMachineName());
		}
		if(StringUtils.isNotEmpty(dto.getOsTypeName())) {
			machine.setOsTypeName(dto.getOsTypeName());
		}
		//BeanUtils.copyProperties(machine, dto);
		List<Machine> machines = getAllMachines();
		List<Machine> updateMachines = new ArrayList<>();
		for(Machine m : machines) {
			if(m.getId() != machine.getId()) {
				updateMachines.add(m);
			}else {
				updateMachines.add(machine);
			}
		}
	
		JSONArray array = (JSONArray) JSONArray.toJSON(updateMachines);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(machineFileName, jsonString);
		
	}
	
	
	public Machine findByMachineName(String machineName) {
		List<Machine> machines = getAllMachines();
		for(Machine m : machines) {
			if(m.getMachineName().equals(machineName)) {
				return m;
			}
		}
		return null;
	}
	
	public Machine findByIpAddress(String ipAddress) {
		List<Machine> machines = getAllMachines();
		for(Machine m : machines) {
			if(m.getIpAddress().equals(ipAddress)) {
				return m;
			}
		}
		return null;
	}
	
	public List<Machine> getAllMachines(){
		List<Machine> machines = JsonUtils.getAllResourceByType(machineFileName, Machine.class);
		if(machines == null) {
			return new ArrayList<Machine>();
		}
		return machines;
	}
	
	public static void main(String[] args) {
		MachineDao dao = new MachineDao();
		Machine machine = new Machine();
		machine.setMachineName("宏观经济库-可视化平台1");
		machine.setEnglishName("hgjjk-ksh1");
		machine.setOsTypeName("CentOS-7.6-64bit");
		machine.setIpAddress("10.168.5.122");
		dao.insertMachine(machine);
	}

	public String[] getColumnNames() {
		return  new String[] {"序号","中文名称","英文名称","系统类型","IP地址","ping的情况"};
	}
	
	public String[][] getData(){
		List<Machine> allMachines = getAllMachines();
		String data[][] = new String[allMachines.size()][getColumnNames().length];
		for(int i=0;i<allMachines.size();i++) {
			Machine machine = allMachines.get(i);
			data[i][0] = "" + machine.getId();
			data[i][1] = machine.getMachineName();
			data[i][2] = machine.getEnglishName();
			data[i][3] = machine.getOsTypeName();
			data[i][4] = machine.getIpAddress();
			data[i][5] = machine.getPingResult();
		}
		return data;
	}

	public String[][] getDataBySearch(String name, String ipAddress) {
		List<Machine> allMachines = searchByNameAndIp(name,ipAddress);
		String data[][] = new String[allMachines.size()][getColumnNames().length];
		for(int i=0;i<allMachines.size();i++) {
			Machine machine = allMachines.get(i);
			data[i][0] = "" + machine.getId();
			data[i][1] = machine.getMachineName();
			data[i][2] = machine.getEnglishName();
			data[i][3] = machine.getOsTypeName();
			data[i][4] = machine.getIpAddress();
			data[i][5] = machine.getPingResult();
		}
		return data;
		
	} 
	
	public List<Machine> searchByNameAndIp(String name,String ipAddress){
		if(StringUtils.isEmpty(name) && StringUtils.isEmpty(ipAddress)) return getAllMachines();
		if(name == null) name = " ";
		if(ipAddress == null) ipAddress = " ";
		List<Machine> allMachines = getAllMachines();
		List<Machine> resultMachines = new ArrayList<Machine>();
		for(Machine m : allMachines) {
			if(m != null) {
				if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(ipAddress)) {
					if((m.getMachineName().toLowerCase().indexOf(name.toLowerCase()) != -1) && (m.getIpAddress().indexOf(ipAddress) != -1 )) {
						resultMachines.add(m);
					}
				}else if(StringUtils.isNotEmpty(name)) {
					if(m.getMachineName().toLowerCase().indexOf(name.toLowerCase()) != -1){
						resultMachines.add(m);
					}
				}else if(StringUtils.isNotEmpty(ipAddress)) {
					if(m.getIpAddress().indexOf(ipAddress) != -1 ){
						resultMachines.add(m);
					}
				}
			}
		}
		return resultMachines;
	}


}
