package com.ywj.utils;

import java.util.concurrent.Callable;

import com.ywj.model.DataBase;

public class CountTask implements Callable<Long> {
        private DataBase database;

        public CountTask(DataBase database) {
        	this.database = database;
        }

		@Override
        public Long call() throws Exception {
			DataBaseUtils utils = new DataBaseUtils(database);
			try {
				return utils.getDataNums();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0L;
		}
    }