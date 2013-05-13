package com.kercar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuSelection extends Activity{
	
		//Attributs
		private Button comMan;
		private Button creerEditer;
		private Button lancer;
		private Button arreter;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	   
	        //ContentView
	        setContentView(R.layout.menu_selection);
			
			//Initialisation des attributs
			comMan = (Button)findViewById(R.id.button_com_man);
			creerEditer = (Button)findViewById(R.id.button_creer_editer);
			lancer = (Button)findViewById(R.id.button_lancer);
			arreter = (Button)findViewById(R.id.button_arreter);
			
			//Adaptation de la vue en fonction de si une mission est en cours
			final SharedPreferences missionLancee = PreferenceManager.getDefaultSharedPreferences(this);
			final SharedPreferences.Editor editor = missionLancee.edit();
			
			Boolean mission = missionLancee.getBoolean("missionLancee", false);
			
			lancer.setEnabled(!mission);
			arreter.setEnabled(mission);
			
			//Listeners
			comMan.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MenuSelection.this, ControlManuel.class);
					startActivity(intent);
				}
			});
			
			creerEditer.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MenuSelection.this, MenuCreation.class);
					startActivity(intent);
				}
			});
			
			lancer.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MenuSelection.this, MenuLancement.class);
					startActivity(intent);
				}
			});
			
			arreter.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					editor.putBoolean("missionLancee", false);
					editor.commit();
					
					Boolean mission = missionLancee.getBoolean("missionLancee", false);
					
					lancer.setEnabled(!mission);
					arreter.setEnabled(mission);
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