package com.example.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	  private TextView coucou = null;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	         
	    coucou = new TextView(this);
	    coucou.setText("Bonjour, vous me devez 1 000 000€.");
	    setContentView(coucou);
	  }
	
	
	
	
}
