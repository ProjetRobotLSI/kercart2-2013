package com.kercar;

import java.util.List;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kercar.osmandroid.OSMAndroid;

public class ChoixPointArrive extends Activity{
	
	//Attributs
	private Button enregistrerMission;
	private OSMAndroid OSM;
	private List<Integer> route;
	private ClientMissions clientMissions;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	   
	        /**Initialisation du gestionnaire des missions*/
	        clientMissions = new ClientMissions(getApplicationContext());
	        
		    /**Reception de bundles*/
		    //Creation du bundle et reception des objets transferes
	        Bundle receptionBundle  = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent2");        
	    	final Mission newMission= (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle2");
	    	final String typeFonctionnalite= receptionBundle.getString("Titre");
	    	
	        //ContentView
	        setContentView(R.layout.choix_point_arrive);
	        
	        enregistrerMission = (Button)findViewById(R.id.buttonEnregistrerMission);
	        OSM = (OSMAndroid)findViewById(R.id.OSM_choix_point);
	        
			//Listeners
			enregistrerMission.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
					if(typeFonctionnalite.equals("Creer")){
						
						try {
							//TODO A Revoir avec Guillaume
							route = OSM.getRoadStep(OSM.getLastRoad());
							//ENREGISTRER ROUTE DANS BASE DE DONNEE
							clientMissions.creerMission(newMission.getNom(), newMission.getEmail(), newMission.getRetourDepart(), newMission.getPrendrePhotosArrivee());
							msbox("Information","Mission ajoutee avec succes !");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(typeFonctionnalite.equals("Editer")){
						
						try {
							//TODO A Revoir avec Guillaume
							route = OSM.getRoadStep(OSM.getLastRoad());
							//ENREGISTRER ROUTE DANS BASE DE DONNEE
							clientMissions.changerMissionEnCours(newMission);
							clientMissions.setEMailMissionEnCours(newMission.getEmail());
							clientMissions.setRetourDepartMissionEnCours(newMission.getRetourDepart());
							clientMissions.setPrendrePhotosArriveeMissionEnCours(newMission.getPrendrePhotosArrivee());
							clientMissions.saveMissions(getApplicationContext());
							msbox("Information", "Mission modifiee avec succes !");
						} catch (Exception e) {
							
							e.printStackTrace();
						}

					}
					else
						throw new IllegalStateException("Exception ! Type de fonctionnalite inexistant !");
				}
			});
			
			OSM.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {

					//On ajoute les points choisis dans la map a la mission
					try {
						

						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			});
	    }
	 
	 public void msbox(String titre,String message)
	 {
	     AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	     dlgAlert.setTitle(titre); 
	     dlgAlert.setMessage(message); 
	     dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
	              
					Intent intent = new Intent(ChoixPointArrive.this, MenuSelectionSansArret.class);
					startActivity(intent);
	         }
	     });
	     
	     dlgAlert.setCancelable(true);
	     dlgAlert.create().show();
	 }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }
}