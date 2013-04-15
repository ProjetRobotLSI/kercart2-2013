package com.kercar;

import kercar.android.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity{

	//Attributs
	Button Avance;
	Button Recule;
	Button Gauche;
	Button Droite;
	Button Photo;
	
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

		URL = "http://kercar2013.no-ip.biz:8080/KerCarCommunication/";
		com = ComAndroid.getManager();
		com.setURL(URL);
		
		//Listeners
		Avance.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				new AsyncAvancer().execute(com);
			}
		});
		
		Recule.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				new AsyncReculer().execute(com);
			}
		});
		
		Gauche.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				new AsyncGauche().execute(com);
			}
		});
		
		Droite.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				new AsyncDroite().execute(com);
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