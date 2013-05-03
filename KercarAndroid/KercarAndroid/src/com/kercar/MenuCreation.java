package com.kercar;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MenuCreation extends Activity{

	ListView liste= null;
	
	  public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.menu_creation);
		         
		    liste = (ListView) findViewById(R.id.listProg);
		    List<String> exemple = new ArrayList<String>();
		    exemple.add("Item 1");
		    exemple.add("Item 2");
		    exemple.add("Item 3");
		         
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
		    liste.setAdapter(adapter);
		  }
}
