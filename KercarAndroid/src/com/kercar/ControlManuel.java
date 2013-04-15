package com.kercar;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.kercar.AsyncTask.AsyncAvancer;
import com.kercar.AsyncTask.AsyncDroite;
import com.kercar.AsyncTask.AsyncGauche;
import com.kercar.AsyncTask.AsyncStop;

public class ControlManuel extends Activity{
	//Attributs
	Button Avance;
	Button Recule;
	Button Gauche;
	Button Droite;
	Button Photo;
	SeekBar vitesse;
	
	String URL; 
	IComAndroid com;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        //ContentView
        setContentView(R.layout.controle_manuel);
		
		//Initialisation des attributs
		Avance = (Button)findViewById(R.id.buttonAvance);
		Recule = (Button)findViewById(R.id.buttonRecule);
		Gauche = (Button)findViewById(R.id.buttonGauche);
		Droite = (Button)findViewById(R.id.buttonDroite);
		Photo = (Button)findViewById(R.id.buttonPhoto);
		vitesse = (SeekBar)findViewById(R.id.barVitesse);

		URL = "http://kercar2013.no-ip.biz:8080/KerCarCommunication/";
		
		com = ComAndroid.getManager();
		com.setURL(URL);
		
		//Listeners
		Avance.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncAvancer(vitesse.getProgress(), com).execute();
				System.out.println(vitesse.getProgress());
			}
		});
		
		Recule.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncStop(com).execute();
			}
		});
		
		Gauche.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncGauche(0, com).execute();
			}
		});
		
		Droite.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncDroite(0, com).execute();
			}
		});
		
		Photo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				new AsyncDroite().execute(com);
			}
		});
    }

    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}