package com.kercar.AsyncTask;

import java.util.LinkedList;

import com.kercar.osmandroid.OSMAndroid;

import kercar.android.IComAndroid;
import kercar.comAPI.IStateMessage;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class AsyncGetEtat extends AsyncTask<Void, Integer, Void> {
	//Attributs
	private int latitude;
	private int longitude;
	private int orientation;
	
	private TextView latitudeEdit;
	private TextView longitudeEdit;
	
	private IComAndroid comAndroid;
	private OSMAndroid OSM;
	
	private int emplacement;
	private int tmp;
	private boolean stop;

	private IStateMessage stateMessage;
	
	//Constructeurs
	public AsyncGetEtat(IComAndroid comAndroid, TextView latitude, TextView longitude, OSMAndroid OSM) {
		//Initialisation des attributs
		this.latitude = 0;
		this.longitude = 0;
		this.orientation = 0;
		
		this.latitudeEdit = latitude;
		this.longitudeEdit = longitude;
		
		this.comAndroid = comAndroid;
		this.OSM = OSM;
		
		this.emplacement = 0;
		this.tmp = 0;		
		this.stop = false;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		
		latitudeEdit.setText("salut");
		Log.e("coucou", "c "+values[0]);
		longitudeEdit.setText(values[1]);
		Log.e("coucou 3", "c "+values[1]);
		
		//Localisation du robot
		if(tmp != 0){
//			OSM.removePoint(emplacement);
		}
//		emplacement = OSM.addPoint(values[0], values[1], "Emplacement du robot", "");
//		OSM.invalidate();
		tmp = 1;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			while(!stop){
//				stateMessage = this.comAndroid.demanderEtat();
//				latitude = stateMessage.getLatitude();
//				longitude = stateMessage.getLongitude();
//				orientation = stateMessage.getOrientation();
//
//				publishProgress(new Integer[]{latitude,longitude,orientation});
				publishProgress(new Integer[]{48120002,-1635540,1});
	
				Thread.sleep(5000);
				publishProgress(new Integer[]{48120002,-1634000,1});
				Thread.sleep(3000);
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	protected void StopActualisation(){
		stop = true;
	}
}