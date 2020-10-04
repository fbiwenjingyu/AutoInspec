package com.ywj.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {
	public static String fileToJsonStr(String fileName) {
		StringBuilder jsonStr = new StringBuilder();
		File f = new File(fileName);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			while((line = br.readLine()) != null) {
				jsonStr.append(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr.toString();
	}
	
	public static void writeJsonStrToFile(String fileName,String jsonStr) {
		File f = new File(fileName);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(f.isFile() && f.exists()) {
			try {
				FileWriter writer = new FileWriter(f,false);
				writer.write(jsonStr);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
