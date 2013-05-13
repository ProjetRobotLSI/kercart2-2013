package com.kercar;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuLancement extends Activity{
	
	private ListView listeMissions;
	
	private ClientMissions clientMissions;

	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_lancement);

		//Initialisation des attributs d'affichage
		listeMissions = (ListView) findViewById(R.id.lstListeMissions);
		
		// recuperation des missions dans la base de donnees
		clientMissions = new ClientMissions(getApplicationContext());
		
		// Affichage des missions crees		
		ArrayAdapter<Mission> adapter = new ArrayAdapter<Mission>(this, android.R.layout.simple_list_item_1, clientMissions.getListeMissions().getListe());
		listeMissions.setAdapter(adapter);
		
		/**
		 * Listener de lancement des missions
		 */
		listeMissions.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				//On charge la mission a partir de l'indice choisi dans le tableau
				Mission editMission = clientMissions.getListeMissions().getListe().get(position);
				
				//On creee le bundle qui contiendra la mission puis on la met dans celui-ci
				Bundle missionBundle = new Bundle();
				missionBundle.putSerializable("AjoutMissionDansBundle", editMission);
				missionBundle.putString("Titre", "Editer");
				
				//On cree un intent, celui-ci va transmettre le bundle et aussi de passer a CreationForm activity
				Intent intent = new Intent(MenuLancement.this, CreationForm.class);
				intent.putExtra("AjoutBundleDansIntent", missionBundle);
				startActivity(intent);
			}
		});
	}
}