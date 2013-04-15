package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDTurnMessage;
import android.os.AsyncTask;

public class AsyncDroite extends AsyncTask<Void, Void, Void> {

	//Attributs
	private int angle;
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncDroite(int angle, IComAndroid comAndroid) {
		this.angle = angle;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDTurnMessage cmdCommand = new CMDTurnMessage(angle, true);
			this.comAndroid.envoyerMessage(cmdCommand);
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}