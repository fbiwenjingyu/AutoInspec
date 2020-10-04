package com.ywj.utils;

import org.apache.commons.lang3.StringUtils;

import com.ywj.model.MainAndBackDataSourceConnectDBVO;

public class SynchThread extends Thread {
	MainAndBackDataSourceConnectDBVO mainAndBackDataSourceVO;
    private String synchtablename;

    public SynchThread(MainAndBackDataSourceConnectDBVO mainAndBackDataSourceVO,String synchtablenames) {
    	this.mainAndBackDataSourceVO = mainAndBackDataSourceVO;
    	this.synchtablename = synchtablenames;
    }

    public void run() {
        if(StringUtils.isEmpty(synchtablename)){
            return;
        }
        SynchMain2 main = new SynchMain2();
        try {
            main.synch(mainAndBackDataSourceVO,synchtablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}