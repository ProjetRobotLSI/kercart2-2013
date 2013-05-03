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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MenuCreation extends Activity{
	private ListView listeMissions;
	private Button btnCreation;
	
	private ClientMissions gestionMissions;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_creation);

		//Initialisation des attributs d'affichage
		listeMissions = (ListView) findViewById(R.id.lstListeMissions);
		btnCreation = (Button)findViewById(R.id.send);
		
		// récupération des missions dans la base de données
		gestionMissions = new ClientMissions(getApplicationContext());

		// Affichage des missions créées		
		ArrayAdapter<Mission> adapter = new ArrayAdapter<Mission>(this, android.R.layout.simple_list_item_1, gestionMissions.getListeMissions().getListe());
		listeMissions.setAdapter(adapter);

		/**Listener de création d'une nouvelle mission*/
		btnCreation.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				startActivity(intent);
			}
		});
		
		/**
		 * Listener d'édition des missions déjà créées
		 */
		listeMissions.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				
				//Récupération de la mission selectionnée
				Mission mission = gestionMissions.getListeMissions().getListe().get(position);
				bundle.putSerializable("mission", (Serializable) mission);
				
				//Passage à la page d'édition (qui est la même que la création)
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
		});
	}
}