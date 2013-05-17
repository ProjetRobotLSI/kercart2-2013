package com.kercar;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;

import com.kercar.AsyncTask.AsyncStop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MenuMissionEnCours extends Activity{
	//Attributs
	private Button arreter;
	private IComAndroid com;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
   
        //ContentView
        setContentView(R.layout.menu_mission_en_cours);
		
		//Initialisation des attributs
		arreter = (Button)findViewById(R.id.button_arreter);
		com = ComAndroid.getManager();
		
		//Listeners
		arreter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncStop(com).execute();
				Intent intent = new Intent(MenuMissionEnCours.this, MenuSelection.class);
				startActivity(intent);
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