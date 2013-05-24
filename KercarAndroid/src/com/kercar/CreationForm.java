package com.kercar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BaseDeDonnees.Mission;
import Client.ClientMissions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CreationForm extends Activity{
	
	//Attributs
	private TextView lblTitre = null;
	private TextView lblEmailError = null;
	private EditText txtNom = null;
	private EditText txtEmail = null;
	private CheckBox cbxRetourDepart = null;
	private CheckBox cbxPhotoArrivee = null;
	
	private Button btnSuivant = null;
	private Button btnSupprimer = null;
	private Button btnAnnuler = null;
	
	private ClientMissions clientMissions;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    //ContentView
	    setContentView(R.layout.creation_frm);

	    //Initialisation des Attributs
	    lblTitre = (TextView) findViewById(R.id.lblTitre);
	    lblEmailError = (TextView) findViewById(R.id.lblEmailError);
	    txtNom = (EditText) findViewById(R.id.txtNom);
	    txtEmail = (EditText) findViewById(R.id.txtEmail);
	    cbxRetourDepart = (CheckBox) findViewById(R.id.cbxRetourDepart);
	    cbxPhotoArrivee = (CheckBox) findViewById(R.id.cbxPhotoArrivee);
	    
	    btnSuivant = (Button) findViewById(R.id.btnSuivant);
	    btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
	    btnAnnuler = (Button) findViewById(R.id.btnAnnuler);

	    clientMissions = new ClientMissions(getApplicationContext());
	    
	    //Creation du bundle et reception des objets transferes
        Bundle receptionBundle = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent");        
    	final Mission newMission = (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle");
    	final String typeFonctionnalite = receptionBundle.getString("Titre");

    	//Menu Creation
	    if(typeFonctionnalite.equals("Creer")){
	    	//Titre
	    	lblTitre.setText(Html.fromHtml("Creer une mission"));
	    	btnSupprimer.setVisibility(4);
	    }
	    //Menu Edition
	    else if(typeFonctionnalite.equals("Editer")){
	    	//Titre
	    	lblTitre.setText(Html.fromHtml("Editer une mission"));
	    
	    	//Nom de la mission
	    	txtNom.setEnabled(false);
	    	txtNom.setTextColor(Color.parseColor("#888888"));
	    	txtNom.setText(newMission.getNom());
    	
	    	//Email de la mission
	    	txtEmail.setText(newMission.getEmail());
    	
	    	//Retour du Robot
	    	if(newMission.getRetourDepart() && !cbxRetourDepart.isChecked()){
	    		cbxRetourDepart.setChecked(true);
	    	}
	    	else if(!newMission.getRetourDepart() && cbxRetourDepart.isChecked()){
	    		cbxRetourDepart.setChecked(false);
	    	}
	    	
	    	//Prendre une photo		
	    	if(newMission.getPrendrePhotosArrivee() && !cbxPhotoArrivee.isChecked()){
	    		cbxPhotoArrivee.setChecked(true);
	    	}
	    	else if(!newMission.getPrendrePhotosArrivee() && cbxPhotoArrivee.isChecked()){
	    		cbxPhotoArrivee.setChecked(false);
	    	}
	    	
	    	//Bouton supprimer
	    	btnSupprimer.setVisibility(0);
		    btnSupprimer.setOnClickListener(new OnClickListener(){
		    	public void onClick(View arg0){
		    		try {
		    			Mission deleteMission= clientMissions.getListeMissions().getMissions(txtNom.getText().toString());
						clientMissions.supprimerMission(deleteMission);
						msbox("Information","Mission supprimee avec succes !");
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
		    });
    	}
	    //Sinon exception
	    else{
	    	throw new IllegalStateException("CreationForm: Exception !");
	    }		
		
	    //Bouton Suivant
	    btnSuivant.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {				
				String emptyNom= txtNom.getText().toString();
				String emptyEmail= txtEmail.getText().toString();
				
				if(!(emptyNom.equals("")) && !(emptyEmail.equals("")) && isEmailValid(txtEmail.getText().toString())){
					
					newMission.setNom(txtNom.getText().toString());
					newMission.setEmail(txtEmail.getText().toString());
					if(cbxRetourDepart.isChecked())	newMission.setRetourDepart(true);
					else newMission.setRetourDepart(false);
					if(cbxPhotoArrivee.isChecked())	newMission.setPrendrePhotosArrivee(true);
					else newMission.setPrendrePhotosArrivee(false);
					
					Bundle missionBundle = new Bundle();
					missionBundle.putSerializable("AjoutMissionDansBundle2", newMission);
					missionBundle.putString("Titre", typeFonctionnalite);
					
					Intent intent = new Intent(CreationForm.this, ChoixPointArrive.class);
					intent.putExtra("AjoutBundleDansIntent2", missionBundle);
					startActivity(intent);
				}
				
				if((emptyNom.equals(""))){					
					txtNom.setHint("Champs obligatoire");
					txtNom.setHintTextColor(Color.parseColor("#ff0000"));
				}
				
				if((emptyEmail.equals(""))){					
					txtEmail.setHint("Champs obligatoire");
					txtEmail.setHintTextColor(Color.parseColor("#ff0000"));
				}
				
				if(!isEmailValid(txtEmail.getText().toString())){					
					lblEmailError.setVisibility(BIND_IMPORTANT);
				}
			}
		});
	    
<<<<<<< HEAD
/**Traitement de btnAnnuler//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

	    	 btnAnnuler.setOnClickListener(new OnClickListener() {
	 			
	 			@Override
	 			public void onClick(View arg0) {
	 				
	 				Intent intent = new Intent(CreationForm.this, MenuCreation.class);
	 				startActivity(intent);
	 			}
	 		});

	    
	   
	 
/**Traitement de btnSupprimer//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/		    
	    
	    if(typeFonctionnalite.equals("Creer")){
	    	
	    	btnSupprimer.setVisibility(TRIM_MEMORY_UI_HIDDEN);
	    }
	    else{
	    	
	    	btnSupprimer.setEnabled(true);
		    btnSupprimer.setOnClickListener(new OnClickListener(){
		    	
		    	public void onClick(View arg0){
		    		
		    		try {
		    			
		    			msgboxConf("COnfirmation", "Voulez-vous vraiment effacer cette mission?");
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
		    	}
		    });
	    }
	    

	  }
	 

	 
=======
	    //Bouton Annuler
	    btnAnnuler.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {			
				Intent intent = new Intent(CreationForm.this, MenuCreation.class);
				startActivity(intent);
			}
	    });
	  } 
>>>>>>> c891a061ac7017a12c0844e08cf505f2b3d7e646
	 
	 //METHODES
	 
	 public boolean isEmailValid(String email) {
		 String regExpn =
	             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
	                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
	                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
	                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

	     CharSequence inputStr = email;
	     
	     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
	     Matcher matcher = pattern.matcher(inputStr);

	     if(matcher.matches()) return true;
	     else return false;
	}
	 
	 public void msbox(String titre,String message) {
	     AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	     dlgAlert.setTitle(titre); 
	     dlgAlert.setMessage(message); 
	     dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
				 Intent intent = new Intent(CreationForm.this, MenuCreation.class);
				 startActivity(intent);
	         }
	     });
	     
	     dlgAlert.setCancelable(true);
	     dlgAlert.create().show();
	 }
<<<<<<< HEAD
	 
	 public void msgboxConf(String titre,String message){
		 
		 final boolean answer;
	     AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	     dlgAlert.setTitle(titre); 
	     dlgAlert.setMessage(message); 
	     
	     //Suppression OK
	     dlgAlert.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {

				try {
					Mission deleteMission= clientMissions.getListeMissions().getMissions(txtNom.getText().toString());
					clientMissions.supprimerMission(deleteMission);
					msbox("Information","Mission supprimée avec succès !");
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	     });

	     
	     //Suppression KO
	     dlgAlert.setPositiveButton("Non",new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
				 
	        	 msbox("Information","Annulation de la suppression!");
	         }
	     });
	     
	     dlgAlert.setCancelable(true);
	     dlgAlert.create().show();

	 }
}
=======
}
>>>>>>> c891a061ac7017a12c0844e08cf505f2b3d7e646
