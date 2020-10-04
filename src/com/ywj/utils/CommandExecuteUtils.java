package com.ywj.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecuteUtils {
	public static void main(String[] args) {
		String command = "ping www.baidu.com";
		CommandExecuteUtils obj = new CommandExecuteUtils();
		boolean output = obj.ping(command);
		System.out.println(output);
	}
	
	public  boolean ping(String command) {
		boolean output = executeCommand(command);
		return output;
	}
	/*
	 * ִ��dos����ķ���
	 * @param command ��Ҫִ�е�dos����
	 * @param file ָ����ʼִ�е��ļ�Ŀ¼
	 * 
	 * @return true ת���ɹ���false ת��ʧ��
	 */
	public  boolean executeCommand(String command) {
		boolean result = false;
		StringBuffer output = new StringBuffer();
		Process p;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			//p = Runtime.getRuntime().exec(command, null, file);
			p =Runtime.getRuntime().exec(command);
			p.waitFor();
			inputStreamReader = new InputStreamReader(p.getInputStream(), "GBK");
			reader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(output.toString());
		if (output.toString().indexOf("TTL") > 0) {   
            // ���糩ͨ    
            result = true;  
        } else {   
            // ���粻��ͨ    
        	result = false;  
        }   
		return result;
	}

}
