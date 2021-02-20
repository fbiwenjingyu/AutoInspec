package com.ywj.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ywj.model.DataBase;
import com.ywj.model.Machine;
import com.ywj.utils.DataBaseUtils;
import com.ywj.utils.FileUtils;
import com.ywj.utils.JsonUtils;

public class DataBaseDao {
	public static final  String dataBaseFileName = "data\\dataBase.json";
	public static final  String dataBaseNameMappings = "data\\dataBaseNameMappings.txt";
	public static final  String tableNameMappings = "data\\tableNameMappings.txt";
	//private AtomicInteger index = new AtomicInteger();
	
	public DataBase getDataBaseById(int id) {
		List<DataBase> databases = getAllDataBases();
		for(DataBase d : databases) {
			if(d.getId() == id) {
				return d;
			}
		}
		return null;
	}
	
	public void insertDataBase(DataBase database) {
		List<DataBase> databases = getAllDataBases();
		if(databases.size()==0) {
			database.setId(1);
		}else {
			DataBase lastDatabase = databases.get(databases.size() -1);
			database.setId(lastDatabase.getId() + 1);
		}
		
		databases.add(database);
		JSONArray array = (JSONArray) JSONArray.toJSON(databases);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataBaseFileName, jsonString);
	}
	
	public void deleteDataBaseById(int id) {
		List<DataBase> databases = getAllDataBases();
		DataBase database = getDataBaseById(id);
		if(database == null) return;
		List<DataBase> databaseList = new ArrayList<>();
		for(DataBase d : databases) {
			if(d.getId() != database.getId()) {
				databaseList.add(d);
			}
		}
		JSONArray array = (JSONArray) JSONArray.toJSON(databaseList);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataBaseFileName, jsonString);
	}
	
	public void update(int id,DataBase dto) {
		DataBase database = getDataBaseById(id);
		if(database == null) return;
		if(StringUtils.isNotEmpty(dto.getDeptName())) {
			database.setDeptName(dto.getDeptName());
		}
		if(StringUtils.isNotEmpty(dto.getDatabaseName())) {
			database.setDatabaseName(dto.getDatabaseName());
		}
		if(StringUtils.isNotEmpty(dto.getUsername())) {
			database.setUsername(dto.getUsername());
		}
		if(StringUtils.isNotEmpty(dto.getPassword())) {
			database.setPassword(dto.getPassword());
		}
		if(StringUtils.isNotEmpty(dto.getIpaddress())) {
			database.setIpaddress(dto.getIpaddress());
		}
		if(StringUtils.isNotEmpty(dto.getPort())) {
			database.setPort(dto.getPort());
		}
		List<DataBase> databases = getAllDataBases();
		List<DataBase> updateDataBases = new ArrayList<>();
		for(DataBase d : databases) {
			if(d.getId() != database.getId()) {
				updateDataBases.add(d);
			}else {
				updateDataBases.add(database);
			}
		}
	
		JSONArray array = (JSONArray) JSONArray.toJSON(updateDataBases);
		String jsonString = array.toJSONString();
		FileUtils.writeJsonStrToFile(dataBaseFileName, jsonString);
	}
	
	public String[] getColumnNames() {
		return  new String[] {"序号","部门名称","数据库名","数据总数"};
	}
	
	public Vector<String> getUpdateTimeColumnNames() {
		String[] titles = new String[] {"序号","库英文名称","库中文名称","表英文名称","表中文名称","最新更新时间"};
		Vector<String> v = new Vector<String>();
		for(String s: titles) {
			v.add(s);
		}
		return v;
	}
	
	public String[][] getData(){
		List<DataBase> allDataBases = getAllDataBases();
		String data[][] = new String[allDataBases.size()][getColumnNames().length];
		for(int i=0;i<allDataBases.size();i++) {
			DataBase database = allDataBases.get(i);
			data[i][0] = "" + database.getId();
			data[i][1] = database.getDeptName();
			data[i][2] = database.getDatabaseName();
//			data[i][3] = "" + getDataNums(database);
			data[i][3] = "" + database.getDataNums();
		}
		return data;
	}
	
	public Vector<Vector<String>> getUpdateTimeData(){
		AtomicInteger index = new AtomicInteger();
		Vector<Vector<String>> data = new Vector<>();
		List<DataBase> allDataBases = getAllDataBases();
		for(DataBase dataBase : allDataBases) {
			DataBaseUtils dataBaseUtils = new DataBaseUtils(dataBase);
			try {
				List<String> tableNames =  dataBaseUtils.getMysqlTableNames();
				for(String tableName : tableNames) {
					Vector<String> row = new Vector<String>();
					row.add("" +index.incrementAndGet());
					row.add(dataBase.getDatabaseName());
					row.add(getDataBaseCNName(dataBase.getDatabaseName()));
					row.add(tableName);
					row.add(getTableCNName(tableName));
					row.add(getLastUpdateTime(dataBase,tableName));
					data.add(row);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
		
	}
	
	public Vector<Vector<String>> getDataBySearch(String databaseName, String tableEnName) {
		AtomicInteger index = new AtomicInteger();
		if(StringUtils.isEmpty(databaseName) && StringUtils.isEmpty(tableEnName)) return getUpdateTimeData();
		Vector<Vector<String>> data = new Vector<>();
		List<DataBase> allDataBases = getAllDataBases();
		for(DataBase dataBase : allDataBases) {
			DataBaseUtils dataBaseUtils = new DataBaseUtils(dataBase);
			try {
				List<String> tableNames =  dataBaseUtils.getMysqlTableNames();
				for(String tableName : tableNames) {
					Vector<String> row = new Vector<String>();
					
					if(StringUtils.isNotEmpty(databaseName) && StringUtils.isNotEmpty(tableEnName)) {
						if((getDataBaseCNName(dataBase.getDatabaseName()).toLowerCase().indexOf(databaseName.toLowerCase()) != -1) && (tableName.indexOf(tableEnName) != -1 )) {
							row.add("" +index.incrementAndGet());
							row.add(dataBase.getDatabaseName());
							row.add(getDataBaseCNName(dataBase.getDatabaseName()));
							row.add(tableName);
							row.add(getTableCNName(tableName));
							row.add(getLastUpdateTime(dataBase,tableName));
							data.add(row);
						}
						
					}else if(StringUtils.isNotEmpty(databaseName)) {
						if(getDataBaseCNName(dataBase.getDatabaseName()).toLowerCase().indexOf(databaseName.toLowerCase()) != -1) {
							row.add("" +index.incrementAndGet());
							row.add(dataBase.getDatabaseName());
							row.add(getDataBaseCNName(dataBase.getDatabaseName()));
							row.add(tableName);
							row.add(getTableCNName(tableName));
							row.add(getLastUpdateTime(dataBase,tableName));
							data.add(row);
						}
					}else if(StringUtils.isNotEmpty(tableEnName)) {
						if(tableName.indexOf(tableEnName) != -1 ) {
							row.add("" +index.incrementAndGet());
							row.add(dataBase.getDatabaseName());
							row.add(getDataBaseCNName(dataBase.getDatabaseName()));
							row.add(tableName);
							row.add(getTableCNName(tableName));
							row.add(getLastUpdateTime(dataBase,tableName));
							data.add(row);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return data;
		
	} 

	
	private String getLastUpdateTime(DataBase dataBase, String tableName) {
		DataBaseUtils dataBaseUtils = new DataBaseUtils(dataBase);
		try {
			return dataBaseUtils.getTableLastUpdateTime(tableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String getDataBaseCNName(String databaseENName) {
		Map<String, String> map = FileUtils.fileToMap(dataBaseNameMappings);
		return map.getOrDefault(databaseENName, "");
	}
	
	private String getTableCNName(String tableENName) {
		Map<String, String> map = FileUtils.fileToMap(tableNameMappings);
		return map.getOrDefault(tableENName, "");
	}

	public long getDataNums(DataBase database) {
		DataBaseUtils utils = new DataBaseUtils(database);
		try {
			return utils.getDataNums();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String[][] getDataBySearch(String deptName) {
		List<DataBase> allDataBases = searchByDeptName(deptName);
		String data[][] = new String[allDataBases.size()][getColumnNames().length];
		for(int i=0;i<allDataBases.size();i++) {
			DataBase database = allDataBases.get(i);
			data[i][0] = "" + database.getId();
			data[i][1] = database.getDeptName();
			data[i][2] = database.getDatabaseName();
			data[i][3] = "" + database.getDataNums();
		}
		return data;
		
	} 
	
	public List<DataBase> searchByDeptName(String deptName){
		if(StringUtils.isEmpty(deptName)) return getAllDataBases();
		List<DataBase> allDataBases = getAllDataBases();
		List<DataBase> resultDataBases = new ArrayList<DataBase>();
		for(DataBase d : allDataBases) {
			if(d != null) {
				String dName = d.getDeptName();
				if(StringUtils.isNotEmpty(dName)) {
					if(dName.toLowerCase().indexOf(deptName.toLowerCase()) != -1) {
						resultDataBases.add(d);
					}
				}
			}
		}
		return resultDataBases;
	}
	

	public List<DataBase> getAllDataBases(){
		List<DataBase> databases = JsonUtils.getAllResourceByType(dataBaseFileName, DataBase.class);
		if(databases == null) {
			return new ArrayList<DataBase>();
		}
		return databases;
	}
	
	public static void main(String[] args) {
		DataBaseDao dao = new DataBaseDao();
		DataBase database = new DataBase();
		database.setIpaddress("127.0.0.1");
		database.setPort("3306");
		database.setDatabaseName("摩锟斤拷锟教筹拷-dip");
		database.setUsername("root");
		database.setPassword("123456");
		long dataNums = dao.getDataNums(database);
		System.out.println("dataNums = " + dataNums);
	}
}
