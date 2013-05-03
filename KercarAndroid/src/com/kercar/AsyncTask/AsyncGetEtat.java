package com.kercar.AsyncTask;

import java.util.LinkedList;

import kercar.android.IComAndroid;
import kercar.comAPI.IStateMessage;
import android.os.AsyncTask;

public class AsyncGetEtat extends AsyncTask<Void, Void, LinkedList<Integer>> {
	//Attributs
	private int latitude;
	private int longitude;
	private int orientation;
	private IComAndroid comAndroid;
	
	private LinkedList<Integer> result;
	private IStateMessage stateMessage;
	
	//Constructeurs
	public AsyncGetEtat(IComAndroid comAndroid) {
		this.latitude = 0;
		this.longitude = 0;
		this.orientation = 0;
		this.comAndroid = comAndroid;
		
		result = new  LinkedList<Integer>();
	}
	
	@Override
	protected LinkedList<Integer> doInBackground(Void... params) {
		try {
//			stateMessage = this.comAndroid.demanderEtat();
//			latitude = stateMessage.getLatitude();
//			longitude = stateMessage.getLongitude();
//			orientation = stateMessage.getOrientation();
//			
//			result.add(latitude);
//			result.add(longitude);
//			result.add(orientation);
			result.add(48120002);
			result.add(-1635540);
			result.add(1);
		} catch (Exception e) {e.printStackTrace();}
		return result;
	}
}