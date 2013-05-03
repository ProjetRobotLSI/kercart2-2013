package com.kercar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MenuStart extends Activity{
	//Attributs
	private Button start;
	private Button about;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
   
        //ContentView
        setContentView(R.layout.menu_start);
		
		//Initialisation des attributs
		start = (Button)findViewById(R.id.buttonStart);
		about = (Button)findViewById(R.id.buttonAbout);
		
		//Listeners
		start.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuStart.this, MenuSelection.class);
				startActivity(intent);
			}
		});
		
		about.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuStart.this,CreationForm.class);
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