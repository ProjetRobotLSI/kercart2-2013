package com.kercar.raspberry.wifi.api;

import java.util.ArrayList;
import java.util.List;

public class Network {

	private List<String> m_ssid;
	private List<Integer> m_signal;
	
	public Network() {
		this.m_ssid = new ArrayList<String>();
		this.m_signal = new ArrayList<Integer>();
	}
	
	public void add(int cell, String ssid, int signal) {
		this.m_ssid.add(cell, ssid);
		this.m_signal.add(cell, signal);
	}
	
	public void remove(int cell) {
		this.m_ssid.remove(cell);
		this.m_signal.remove(cell);
	}
	
	public String getSsid(int cell) {
		return this.m_ssid.get(cell);
	}
	
	public int getSignal(int cell) {
		return this.m_signal.get(cell);
	}
}