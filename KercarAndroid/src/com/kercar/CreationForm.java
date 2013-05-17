package com.kercar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BaseDeDonnees.Mission;
import android.app.Activity;
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

	private TextView lblTitre = null;
	private Button btnSuivant = null;
	private Button btnAnnuler = null;
	private EditText txtNom = null;
	private EditText txtEmail = null;
	private CheckBox cbxRetourDepart = null;
	private CheckBox cbxPhotoArrivee = null;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    /**ContentView*/
	    setContentView(R.layout.creation_frm);

	    /**Initialisation des attributs*/
	    lblTitre = (TextView) findViewById(R.id.lblTitre);
	    txtNom = (EditText) findViewById(R.id.txtNom);
	    txtEmail= (EditText) findViewById(R.id.txtEmail);
	    cbxRetourDepart = (CheckBox) findViewById(R.id.cbxRetourDepart);
	    cbxPhotoArrivee = (CheckBox) findViewById(R.id.cbxPhotoArrivee);
	    btnSuivant = (Button) findViewById(R.id.btnSuivant);
	    btnAnnuler = (Button) findViewById(R.id.btnAnnuler);
	    
	    /**Reception de bundles*/
	    //Creation du bundle et reception des objets transferes
        Bundle receptionBundle  = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent");        
    	final Mission newMission= (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle");
    	final String typeFonctionnalite= receptionBundle.getString("Titre");

    	/**Traitement sur lblTitre*/
	    lblTitre.setText(Html.fromHtml(typeFonctionnalite+" une mission"));
	    
/**Traitement sur txtNom////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
	    if(typeFonctionnalite.equals("Editer")){
	    	txtNom.setText(newMission.getNom());
	    }
	    else{
	    	System.err.println("Erreur transfert de donnees !");
	    }

		
/**Traitement sur txtEmail//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
		if(typeFonctionnalite.equals("Editer")){
			txtEmail.setText(newMission.getEmail());
		}
	    else{
	    	System.err.println("Erreur transfert de donnees !");
	    }
		
		
/**Traitement sur cbxRetourDepart////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
		if(typeFonctionnalite.equals("Editer")){			
			if(newMission.getRetourDepart() && !cbxRetourDepart.isChecked())
				cbxRetourDepart.setChecked(true);
			else if(!newMission.getRetourDepart() && cbxRetourDepart.isChecked())
				cbxRetourDepart.setChecked(false);
		}
	    else{
	    	System.err.println("Erreur transfert de donnees !");
	    }

/**Traitement sur cbxPhotoArrivee////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
		if(typeFonctionnalite.equals("Editer")){			
			if(newMission.getPrendrePhotosArrivee() && !cbxPhotoArrivee.isChecked())
				cbxPhotoArrivee.setChecked(true);
			else if(!newMission.getPrendrePhotosArrivee() && cbxPhotoArrivee.isChecked())
				cbxPhotoArrivee.setChecked(false);
		}
		else{
			System.err.println("Erreur transfert de donnees !");
		}		
		
/**Traitement de btnSuivant//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
		btnSuivant.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String emptyNom= txtNom.getText().toString();
				String emptyEmail= txtEmail.getText().toString();
				boolean emptyVerif= false;
				
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
				if(!isEmailValid(txtEmail.getText().toString())){
					
					txtEmail.setText("");
					txtEmail.setHint("Syntaxe de mail invalide");
					txtEmail.setHintTextColor(Color.parseColor("#ff0000"));
					System.out.println("Debug");
				}
			}
		});
	    
	    /**Traitement de btnAnnuler*/
	    btnAnnuler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(CreationForm.this, MenuCreation.class);
				startActivity(intent);
			}
		});
	  }	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 public boolean isEmailValid(String email)
	    {
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

	     if(matcher.matches())
	        return true;
	     else
	        return false;
	}
}