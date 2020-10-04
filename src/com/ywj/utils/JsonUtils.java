package com.ywj.utils;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class JsonUtils {
  public static	<T> List<T> getAllResourceByType(String fileName,Class<T> clazz){
		String jsonStr = FileUtils.fileToJsonStr(fileName);
		return JSONArray.parseArray(jsonStr, clazz);
	}
}
