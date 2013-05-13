package com.kercar;

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
	
	private ClientMissions clientMissions;

	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_creation);

		//Initialisation des attributs d'affichage
		listeMissions = (ListView) findViewById(R.id.lstListeMissions);
		btnCreation = (Button)findViewById(R.id.send);
		
		// r�cup�ration des missions dans la base de donn�es
		clientMissions = new ClientMissions(getApplicationContext());
		
		// Affichage des missions cr��es		
		ArrayAdapter<Mission> adapter = new ArrayAdapter<Mission>(this, android.R.layout.simple_list_item_1, clientMissions.getListeMissions().getListe());
		listeMissions.setAdapter(adapter);

		/**Listener de cr�ation d'une nouvelle mission*/
		btnCreation.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				//On creee une mission
				Mission newMission= new Mission(null, null, false, false);
				
				//On creee le bundle qui contiendra la mission puis on la met dans celui-ci
				Bundle missionBundle = new Bundle();
				missionBundle.putSerializable("AjoutMissionDansBundle", newMission);
				missionBundle.putString("Titre", "Creer");
				
				//On cree un intent, celui-ci va transmettre le bundle et aussi de passer a CreationForm activity
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtra("AjoutBundleDansIntent", missionBundle);
				startActivity(intent);
			}
		});
		
		/**
		 * Listener d'edition des missions deja cr��es
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
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtra("AjoutBundleDansIntent", missionBundle);
				startActivity(intent);
				
				/*Bundle bundle = new Bundle();
				
				//R�cup�ration de la mission selectionn�e
				Mission mission = clientMissions.getListeMissions().getListe().get(position);
				bundle.putSerializable("mission", (Serializable) mission);
				bundle.putString("Titre", "Editer");
				
				//Passage � la page d'�dition (qui est la m�me que la cr�ation)
				Intent intent = new Intent(MenuCreation.this, CreationForm.class);
				intent.putExtras(bundle);
				
				startActivity(intent);*/
			}
		});
	}
}