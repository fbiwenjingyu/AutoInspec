package com.ywj.utils;

import java.util.concurrent.Callable;

import com.ywj.model.Machine;

public class PingTask implements Callable<Boolean> {
        private String ipAddress = "";
        private CommandExecuteUtils commandExecuteUtils = new CommandExecuteUtils();

        public PingTask(Machine machine) {
            this.ipAddress = machine.getIpAddress();
        }

        public String getIpAddress() {
			return ipAddress;
		}



		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}



		@Override
        public Boolean call() throws Exception {
        	String command = "ping " + this.getIpAddress();
			boolean result = commandExecuteUtils.ping(command);
            //Thread.sleep(30000);
            return result;
        }
    }