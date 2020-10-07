package com.ywj.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ywj.model.DataTransferVO;
import com.ywj.utils.FileUtils;
import com.ywj.utils.JsonUtils;

public class DataTransferDao {
	public static final  String dataTransferFileName = "data\\datatransfer.json";
	
	public DataTransferVO getDataTransferVOById(int id) {
		List<DataTransferVO> datas = getAllDataTransfer();
		for(DataTransferVO d : datas) {
			if(d.getId() == id) {
				return d;
			}
		}
		return null;
	}
	
	public void insertDataTransferVO(DataTransferVO data) {
		List<DataTransferVO> datas = getAllDataTransfer();
		if(datas.size()==0) {
			data.setId(1);
		}else {
			DataTransferVO lastDataTransferVO = datas.get(datas.size() -1);
			data.setId(lastDataTransferVO.getId() + 1);
		}
		
		datas.add(data);
		JSONArray array = (JSONArray) JSONArray.toJSON(datas);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataTransferFileName, jsonString);
	}
	
	public void deleteDataTransferVOById(int id) {
		List<DataTransferVO> datas = getAllDataTransfer();
		DataTransferVO data = getDataTransferVOById(id);
		if(data == null) return;
		List<DataTransferVO> dataList = new ArrayList<>();
		for(DataTransferVO u : datas) {
			if(u.getId() != data.getId()) {
				dataList.add(u);
			}
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(dataList);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataTransferFileName, jsonString);
	}
	
	public void update(int id,DataTransferVO dto) {
		DataTransferVO data = getDataTransferVOById(id);
		if(data == null) return;
		if(StringUtils.isNotEmpty(dto.getDeptName())) {
			data.setDeptName(dto.getDeptName());
		}
		if(StringUtils.isNoneEmpty(dto.getMainDatabaseName())) {
			data.setMainDatabaseName(dto.getMainDatabaseName());
		}
		if(StringUtils.isNotEmpty(dto.getMainIpAddress())) {
			data.setMainIpAddress(dto.getMainIpAddress());
		}
		if(StringUtils.isNotEmpty(String.valueOf(dto.getMainPort()))) {
			data.setMainPort(dto.getMainPort());
		}
		if(StringUtils.isNotEmpty(dto.getMainUserName())) {
			data.setMainUserName(dto.getMainUserName());
		}
		if(StringUtils.isNotEmpty(dto.getMainPassword())) {
			data.setMainPassword(dto.getMainPassword());
		}
		if(StringUtils.isNotEmpty(dto.getBackDatabaseName())) {
			data.setBackDatabaseName(dto.getBackDatabaseName());
		}
		if(StringUtils.isNotEmpty(dto.getBackIpAddress())) {
			data.setBackIpAddress(dto.getBackIpAddress());
		}
		if(StringUtils.isNotEmpty(String.valueOf(dto.getBackPort()))) {
			data.setBackPort(dto.getBackPort());
		}
		if(StringUtils.isNotEmpty(dto.getBackUserName())) {
			data.setBackUserName(dto.getBackUserName());
		}
		if(StringUtils.isNotEmpty(dto.getBackPassword())) {
			data.setBackPassword(dto.getBackPassword());
		}
		List<DataTransferVO> datas = getAllDataTransfer();
		List<DataTransferVO> updateDatas = new ArrayList<>();
		for(DataTransferVO u : datas) {
			if(u.getId() != data.getId()) {
				updateDatas.add(u);
			}else {
				updateDatas.add(data);
			}
		}
	
		JSONArray array = (JSONArray) JSONArray.toJSON(updateDatas);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataTransferFileName, jsonString);
	}
	
	public List<DataTransferVO> searchByDeptName(String deptName){
		if(StringUtils.isEmpty(deptName)) return getAllDataTransfer();
		List<DataTransferVO> allDatas = getAllDataTransfer();
		List<DataTransferVO> resultDatas = new ArrayList<DataTransferVO>();
		for(DataTransferVO d : allDatas) {
			if(d != null) {
				String dName = d.getDeptName();
				if(StringUtils.isNotEmpty(dName)) {
					if(dName.toLowerCase().indexOf(deptName.toLowerCase()) != -1) {
						resultDatas.add(d);
					}
				}
			}
		}
		return resultDatas;
	}
	
	public String[] getColumnNames() {
		return  new String[] {"序号","部门名称","源库名称","目标库名称","操作"};
	}
	
	public String[][] getData(){
		List<DataTransferVO> allDatas = getAllDataTransfer();
		String data[][] = new String[allDatas.size()][getColumnNames().length];
		for(int i=0;i<allDatas.size();i++) {
			DataTransferVO dataTransferVO = allDatas.get(i);
			data[i][0] = "" + dataTransferVO.getId();
			data[i][1] = dataTransferVO.getDeptName();
			data[i][2] = dataTransferVO.getMainDatabaseName();
			data[i][3] = dataTransferVO.getBackDatabaseName();
			data[i][4] = "数据传输";
		}
		return data;
	} 
	
	public String[][] getDataByDeptName(String deptName){
		List<DataTransferVO> allDatas = searchByDeptName(deptName);
		String data[][] = new String[allDatas.size()][getColumnNames().length];
		for(int i=0;i<allDatas.size();i++) {
			DataTransferVO dataTransferVO = allDatas.get(i);
			data[i][0] = "" + dataTransferVO.getId();
			data[i][1] = dataTransferVO.getDeptName();
			data[i][2] = dataTransferVO.getMainDatabaseName();
			data[i][3] = dataTransferVO.getBackDatabaseName();
			data[i][4] = "数据传输";
		}
		return data;
	} 
	
	
	public List<DataTransferVO> getAllDataTransfer(){
		List<DataTransferVO> datas = JsonUtils.getAllResourceByType(dataTransferFileName, DataTransferVO.class);
		if(datas == null) {
			return new ArrayList<DataTransferVO>();
		}
		return datas;
	}
}
