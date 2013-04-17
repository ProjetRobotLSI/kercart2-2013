package com.kercar.raspberry.wifi;

import com.kercar.raspberry.wifi.api.WifiManager;

public class WifiIA extends Thread {

	private WifiManager wifiManager;
	
	public WifiIA() {
		this.wifiManager = WifiManager.getInstance();
	}
	
	public void run() {
		for(;;) {
			if(!this.wifiManager.isConnected()) {
				this.wifiManager.connection();
			}
		}
	}
}
