package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.*;
import android.os.AsyncTask;

public class AsyncReculer extends AsyncTask<Void, Void, Void> {

	//Attributs
	private int vitesse;
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncReculer(int vitesse, IComAndroid comAndroid) {
		this.vitesse = vitesse;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDMoveMessage cmdCommand = new CMDMoveMessage(this.vitesse, true);
			this.comAndroid.envoyerMessage(cmdCommand);
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}