package com.kercar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MenuCreation extends Activity{

		private ListView liste= null;
		private Button btnCreation= null;
	
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    
		  /**ContentView*/
		    setContentView(R.layout.menu_creation);
		    
		  /**Initialisation des attributs*/
		    liste = (ListView) findViewById(R.id.lstListeMissions);
		    btnCreation = (Button)findViewById(R.id.send);
		    
		  /**Traitements sur lstListeMissions*/
		    List<String> exemple = new ArrayList<String>();
		    exemple.add("Item 1");
		    exemple.add("Item 2");
		    exemple.add("Item 3");
		         
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
		    liste.setAdapter(adapter);
		    
		  /**Traitement sur btnCreation*/
		    //Listener
		    btnCreation.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MenuCreation.this, CreationForm.class);
					startActivity(intent);
				}
			});
		  }
}
