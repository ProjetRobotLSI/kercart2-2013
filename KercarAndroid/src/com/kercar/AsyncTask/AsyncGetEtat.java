package com.kercar.AsyncTask;

import java.util.LinkedList;

import kercar.android.IComAndroid;
import kercar.comAPI.IStateMessage;
import android.os.AsyncTask;

public class AsyncGetEtat extends AsyncTask<Void, Integer, Void> {
	//Attributs
	private int latitude;
	private int longitude;
	private int orientation;
	private IComAndroid comAndroid;
	
	private IStateMessage stateMessage;
	private boolean stop;
	private LinkedList<Integer> list;
	
	//Constructeurs
	public AsyncGetEtat(LinkedList<Integer> list, IComAndroid comAndroid) {
		this.latitude = 0;
		this.longitude = 0;
		this.orientation = 0;
		this.comAndroid = comAndroid;
		
		stop = false;
		this.list = list;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		
		list.set(0, values[0]);
		list.set(1, values[1]);
		list.set(2, values[2]);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			while(!stop){
//				stateMessage = this.comAndroid.demanderEtat();
//				latitude = stateMessage.getLatitude();
//				longitude = stateMessage.getLongitude();
//				orientation = stateMessage.getOrientation();

//				publishProgress(new Integer[]{latitude,longitude,orientation});
				publishProgress(new Integer[]{48120002,-1635540,1});
				
				Thread.sleep(3000);
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	protected void StopActualisation(){
		stop = true;
	}
	
}