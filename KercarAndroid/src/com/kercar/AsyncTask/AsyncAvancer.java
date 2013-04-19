package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDMoveMessage;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncAvancer extends AsyncTask<Void, Void, Void> {

	//Attributs
	private int vitesse;
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncAvancer(int vitesse, IComAndroid comAndroid) {
		this.vitesse = vitesse;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			
			Log.v("AsyncAvancer", "Avancer");
			CMDMoveMessage cmdCommand = new CMDMoveMessage(this.vitesse, false);
			this.comAndroid.envoyerMessage(cmdCommand);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}