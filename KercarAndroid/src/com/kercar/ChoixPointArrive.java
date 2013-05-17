package com.kercar;

import java.util.LinkedList;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kercar.osmandroid.OSMAndroid;

public class ChoixPointArrive extends Activity{
	
	//Attributs
	private Button enregistrerMission;
	private OSMAndroid OSM;
	private int[] arrive;
	private ClientMissions clientMissions;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	   
	        /**Initialisation du gestionnaire des missions*/
	        clientMissions = new ClientMissions(getApplicationContext());
	        arrive = new int[2];
	        
	        
		    /**Reception de bundles*/
		    //Creation du bundle et reception des objets transferes
	        Bundle receptionBundle = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent2");        
	    	final Mission newMission = (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle2");
	    	final String typeFonctionnalite = receptionBundle.getString("Titre");
	    	
	        //ContentView
	        setContentView(R.layout.choix_point_arrive);
	        
	        enregistrerMission = (Button)findViewById(R.id.buttonEnregistrerMission);
	        OSM = (OSMAndroid)findViewById(R.id.OSM_choix_point);
	        
	        if(typeFonctionnalite.equals("Editer")){
	        	try {
					clientMissions.changerMissionEnCours(newMission);
		        	arrive = clientMissions.getPointArriveeMissionEnCours();
		        	OSM.addPoint(arrive[0], arrive[1], "Point Arrivee", "");
				} catch (Exception e) {
					e.printStackTrace();
				}

	        }
	        
			//Listeners
			enregistrerMission.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
					if(typeFonctionnalite.equals("Creer")){
						
						try {
					        //Enregistrement du point d'arrive du Robot
					        int id = OSM.getLastStartPoint();
					        int latitude = OSM.getPointLatitude(id);
					        int longitude = OSM.getPointLongitude(id);
					        arrive[0] = latitude;
					        arrive[1] = longitude;

//					        newMission.setM_fin(arrive);							
							clientMissions.creerMission(newMission);
							clientMissions.changerMissionEnCours(newMission);
							clientMissions.setPointArriveeMissionsEnCours(arrive);
//							int[] a = clientMissions.getPointArriveeMissionEnCours();
					        
							msbox("Information","Mission ajoutee avec succes !");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else if(typeFonctionnalite.equals("Editer")){
						
						try {
							//ENREGISTRER ROUTE DANS BASE DE DONNEE
							clientMissions.setEMailMissionEnCours(newMission.getEmail());
							clientMissions.setRetourDepartMissionEnCours(newMission.getRetourDepart());
							clientMissions.setPrendrePhotosArriveeMissionEnCours(newMission.getPrendrePhotosArrivee());
							
							msbox("Information", "Mission modifiee avec succes !");
						} catch (Exception e) {
							
							e.printStackTrace();
						}

					}
					else
						throw new IllegalStateException("Exception ! Type de fonctionnalite inexistant !");
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
				 Intent intent = new Intent(ChoixPointArrive.this, MenuSelection.class);
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