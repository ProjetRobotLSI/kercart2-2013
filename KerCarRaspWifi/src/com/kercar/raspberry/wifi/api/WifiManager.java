package com.kercar.raspberry.wifi.api;

public class WifiManager {

	private static WifiManager instance;
	
	private WifiManager() {;}
	
	public static WifiManager getInstance() {
		if(instance == null) {
			instance = new WifiManager();
		}
		return instance;
	}
	
	public boolean connection() {
		
		//TODO Try connection
		return false;
	}
	
	public boolean isConnected() {
		//TODO
		return false;
	}
}
