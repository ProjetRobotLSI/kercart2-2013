package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.*;
import android.os.AsyncTask;

public class AsyncStop extends AsyncTask<Void, Void, Void> {

	//Attributs
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncStop(IComAndroid comAndroid) {
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDStopMessage cmdCommand = new CMDStopMessage();
			this.comAndroid.envoyerMessage(cmdCommand);
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}