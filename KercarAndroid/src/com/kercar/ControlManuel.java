package com.kercar;

import java.util.LinkedList;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.kercar.AsyncTask.AsyncAvancer;
import com.kercar.AsyncTask.AsyncDroite;
import com.kercar.AsyncTask.AsyncGauche;
import com.kercar.AsyncTask.AsyncGetEtat;
import com.kercar.AsyncTask.AsyncGetEtatUnPoint;
import com.kercar.AsyncTask.AsyncPrendrePhoto;
import com.kercar.AsyncTask.AsyncReculer;
import com.kercar.AsyncTask.AsyncStop;
import com.kercar.osmandroid.OSMAndroid;

public class ControlManuel extends Activity{
	//Attributs
	private Button avance;
	private Button recule;
	private Button gauche;
	private Button droite;
	private Button photo;
	private SeekBar vitesse;
	private OSMAndroid OSM;
	
	private String url;
	private IComAndroid com;
	
	private LinkedList<Integer> list;
	private AsyncGetEtatUnPoint get;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //ContentView
        setContentView(R.layout.controle_manuel);
             
		//Initialisation des attributs

        OSMAndroid osmAndroid = (OSMAndroid) findViewById(R.id.OSM);
        
		avance = (Button)findViewById(R.id.buttonAvance);
		recule = (Button)findViewById(R.id.buttonRecule);
		gauche = (Button)findViewById(R.id.buttonGauche);
		droite = (Button)findViewById(R.id.buttonDroite);
		photo = (Button)findViewById(R.id.buttonPhoto);
		vitesse = (SeekBar)findViewById(R.id.barVitesse);
		OSM = (OSMAndroid)findViewById(R.id.OSM);

		url = "http://kercar2013.no-ip.biz:8080/KerCarCommunication/";
		
		com = ComAndroid.getManager();
		com.setURL(url);
		
		list = new LinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		
		get = new AsyncGetEtatUnPoint(list, com, OSM);
		get.execute();
		
//		OSM.addPoint(list.get(0), list.get(1), "Emplacement du robot", "L'emplacement du Robot");
		
		//Listeners				LES ANGLES SONT A MODIFIER
		avance.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncAvancer(vitesse.getProgress(), com).execute();
	            	System.out.println("Avancer : " + vitesse.getProgress());
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		recule.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncReculer(vitesse.getProgress(), com).execute();
	            	System.out.println("Reculer : " + vitesse.getProgress());
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		gauche.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncGauche(com).execute();
	            	System.out.println("Gauche");
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		droite.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncDroite(com).execute();
	            	System.out.println("Droite");
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	            	System.out.println("Stop");
	                break;
	            }
				return false;
			}
		});
		
		photo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncPrendrePhoto("novalis@live.fr", com);
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