package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.*;
import android.os.AsyncTask;

public class AsyncGauche extends AsyncTask<Void, Void, Void> {

	//Attributs
	private int angle;
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncGauche(int angle, IComAndroid comAndroid) {
		this.angle = angle;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDTurnMessage cmdCommand = new CMDTurnMessage(angle, false);
			this.comAndroid.envoyerMessage(cmdCommand);
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}