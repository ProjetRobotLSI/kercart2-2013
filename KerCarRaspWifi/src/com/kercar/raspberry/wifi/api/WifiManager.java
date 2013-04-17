package com.kercar.raspberry.wifi.api;

public class WifiManager {

	private static WifiManager instance;
	private static Network netWork;
	
	private WifiManager() {;}
	
	public static WifiManager getInstance() {
		if(instance == null) {
			instance = new WifiManager();
			netWork = new Network();
		}
		return instance;
	}
	
	public Network getAvailableNetwork() {
		
		//TODO récupérer la liste des réseaux puis remplit network
		return this.netWork;
	}
	
	public boolean connection(int cell) {
		
		//TODO connexion au réseau situé à l'indice cell
		return false;
	}
}
