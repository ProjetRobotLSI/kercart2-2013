package com.kercar;

import java.io.Serializable;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MenuCreation extends Activity{
	private ListView listeMissions;
	private Button btnCreation;
	
	private ClientMissions gestionMissions;

	public void onCreate(Bundle savedInstanceState) {
		//setTheme(android.R.style.Theme_Black);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_creation);
		
		//Initialisation des attributs d'affichage
		listeMissions = (ListView) findViewById(R.id.lstListeMissions);
		btnCreation = (Button)findViewById(R.id.send);
		
		// r�cup�ration des missions dans la base de donn�es
		gestionMissions = new ClientMissions(getApplicationContext());

		// Affichage des missions cr��es		
		/*ArrayAdapter<Mission> adapter = new ArrayAdapter<Mission>(this, android.R.layout.simple_list_item_1, gestionMissions.getListeMissions().getListe()){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				view.set
			}
		};
		listeMissions.setAdapter(adapter);
		//TODO modif couleur
		for(int i=0; i<adapter.getCount(); i++){
			adapter.
		}*/
		//adapter.
		/**Listener de cr�ation d'une nouvelle mission*/
		btnCreation.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				startActivity(intent);
			}
		});
		
		/**
		 * Listener d'�dition des missions d�j� cr��es
		 */
		listeMissions.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				
				//R�cup�ration de la mission selectionn�e
				Mission mission = gestionMissions.getListeMissions().getListe().get(position);
				bundle.putSerializable("mission", (Serializable) mission);
				
				//Passage � la page d'�dition (qui est la m�me que la cr�ation)
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
		});
	}
}