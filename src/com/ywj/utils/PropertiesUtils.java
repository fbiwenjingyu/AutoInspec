package com.ywj.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtils {
	
	public  static Map<String,String> getMappings(String filePath){
		Map<String,String> map = new HashMap<String, String>();
		try{
			Properties properties=new Properties();
			//直接写src 类路径下的文件名
			InputStream input=PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath);
			properties.load(new InputStreamReader(input,"utf-8"));
			
			//把key值转换成set 的形式，遍历set
			Set<String> names=properties.stringPropertyNames();
			Iterator<String> iterator=names.iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				String value=properties.getProperty(key);
				System.out.println(key+"="+value);
				map.put(key, value);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		getMappings("data\\");
	}

}
