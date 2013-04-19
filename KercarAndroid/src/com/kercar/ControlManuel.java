package com.kercar;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.kercar.AsyncTask.AsyncAvancer;
import com.kercar.AsyncTask.AsyncDroite;
import com.kercar.AsyncTask.AsyncGauche;
import com.kercar.AsyncTask.AsyncReculer;
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
		
		//Listeners				LES ANGLES SONT A MODIFIER
		Avance.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncAvancer(vitesse.getProgress(), com).execute();
	            	System.out.println("Avancer : " + vitesse.getProgress());
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com);
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		Recule.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncReculer(vitesse.getProgress(), com);
	            	System.out.println("Reculer : " + vitesse.getProgress());
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com);
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		Gauche.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncGauche(com);
	            	System.out.println("Gauche");
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com);
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		Droite.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncDroite(com);
	            	System.out.println("Droite");
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com);
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		Photo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
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