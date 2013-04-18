package com.kercar.raspberry.wifi;
public class WifiIA extends Thread {

	private WifiManager wifiManager;
	private boolean run;
	
	public static void main(String[] args){
		new WifiIA();
	}
	
	public WifiIA() {
		this.wifiManager = WifiManager.getInstance();
		this.run = true;
		this.start();
	}
	
	public void run() {
		while(this.run) {
			if(!this.wifiManager.isConnected()) {
				System.out.println("Connecting...");
				this.wifiManager.connection();
			} else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void end(){
		this.run = false;
	}
}
