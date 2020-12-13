package com.ywj.utils;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ywj.model.DataBase;

public class CountTask implements Callable<Long> {
	private static final Logger logger = LoggerFactory.getLogger(CountTask.class);
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
				logger.debug("���ݿ���Ϊ�� " + database.getDatabaseName() + " ������ϢΪ�� " + e.toString());
				e.printStackTrace();
			}
			return 0L;
		}
    }