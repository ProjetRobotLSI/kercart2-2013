package com.kercar;

import BaseDeDonnees.Mission;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CreationForm extends Activity{

	private TextView lblTitre= null;
	private TextView lblNom= null;
	private TextView lblEmail= null;
	private TextView lblRetourDepart= null;
	private TextView lblPhotoArrivee= null;
	private Button btnSuivant= null;
	private Button btnAnnuler= null;
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
	    lblNom = (TextView) findViewById(R.id.lblNom);
	    lblEmail = (TextView) findViewById(R.id.lblEmail);
	    lblRetourDepart = (TextView) findViewById(R.id.lblRetourDepart);
	    lblPhotoArrivee = (TextView) findViewById(R.id.lblPhotoArrivee);
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

//    	/**Traitement sur lblTitre*/
//	    lblTitre.setText(Html.fromHtml(typeFonctionnalite+" une mission"));
//	    lblTitre.setTextSize(50);
//	    lblTitre.setTextColor(Color.parseColor("#00ff00"));
//    	
//	    /**Traitement sur lblNom*/
//	    lblNom.setText(Html.fromHtml("<p style='color:green'> Nom de la mission: </p>"));
//	    lblNom.setTextSize(30);
//	    lblNom.setTextColor(Color.parseColor("#00ff00"));
//	    
//	    
//	    /**Traitement sur lblEmail*/
//	    lblEmail.setText(Html.fromHtml("Adresse e-mail:"));
//	    lblEmail.setTextSize(30);
//	    lblEmail.setTextColor(Color.parseColor("#00ff00"));
//	    
//	    /**Traitement sur lblRetourDepart*/
//	    lblRetourDepart.setText(Html.fromHtml("Retour du robot:"));
//	    lblRetourDepart.setTextSize(30);
//	    lblRetourDepart.setTextColor(Color.parseColor("#00ff00"));
//	    
//	    /**Traitement sur lblPhotoArrivee*/
//	    lblPhotoArrivee.setText(Html.fromHtml("Photo point d'arrivee :"));
//	    lblPhotoArrivee.setTextSize(30);
//	    lblPhotoArrivee.setTextColor(Color.parseColor("#00ff00"));
	    
/**Traitement sur txtNom////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
	    if(typeFonctionnalite.equals("Creer")){

//			txtNom.setHint(R.string.edit_nom);
//			txtNom.setHintTextColor(Color.parseColor("#000000"));
//			txtNom.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//			txtNom.setLines(5);
//			txtNom.setOnFocusChangeListener(new OnFocusChangeListener() {
//				
//				@Override
//				public void onFocusChange(View arg0, boolean arg1) {
//					
//					txtNom.setHintTextColor(Color.parseColor("#000000"));
//					txtNom.setHint(R.string.edit_nom);
//				}
//			});
	    }
	    else if(typeFonctionnalite.equals("Editer")){
//	    	txtNom.setEnabled(false);
	    	txtNom.setText(newMission.getNom());
	    }else
	    	System.err.println("Erreur transfert de donnees !");

		
/**Traitement sur txtEmail//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
		if(typeFonctionnalite.equals("Creer")){
		
//			txtEmail.setHint(R.string.edit_email);
//			txtEmail.setHintTextColor(Color.parseColor("#000000"));
//			txtEmail.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//			txtEmail.setLines(5);
//			txtEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
//				
//				@Override
//				public void onFocusChange(View arg0, boolean arg1) {
//					
//					txtEmail.setHintTextColor(Color.parseColor("#000000"));
//					txtEmail.setHint(R.string.edit_email);
//				}
//			});
		}
		else if(typeFonctionnalite.equals("Editer")){
			txtEmail.setText(newMission.getEmail());
		}
	    else
	    	System.err.println("Erreur transfert de donnees !");	    
		
		
/**Traitement sur cbxRetourDepart////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
//	    cbxRetourDepart.setTextColor(Color.parseColor("#00ff00"));
		
		if(typeFonctionnalite.equals("Creer")){

//			cbxRetourDepart.setText(R.string.cbx_retour);
//			cbxRetourDepart.setChecked(true);
		}
		else if(typeFonctionnalite.equals("Editer")){
			
			if(newMission.getRetourDepart() && !cbxRetourDepart.isChecked())
				cbxRetourDepart.setChecked(true);
			else if(!newMission.getRetourDepart() && cbxRetourDepart.isChecked())
				cbxRetourDepart.setChecked(false);
		}
	    else
	    	System.err.println("Erreur transfert de donnees !");	

/**Traitement sur cbxPhotoArrivee////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
//		cbxPhotoArrivee.setTextColor(Color.parseColor("#00ff00"));
		if(typeFonctionnalite.equals("Creer")){
			
//			cbxPhotoArrivee.setText(R.string.cbx_retour);
//			cbxPhotoArrivee.setChecked(true);			
		}
		else if(typeFonctionnalite.equals("Editer")){
			
			if(newMission.getPrendrePhotosArrivee() && !cbxPhotoArrivee.isChecked())
				cbxPhotoArrivee.setChecked(true);
			else if(!newMission.getPrendrePhotosArrivee() && cbxPhotoArrivee.isChecked())
				cbxPhotoArrivee.setChecked(false);
		}
		else
			System.err.println("Erreur transfert de donnees !");	
		
		
/**Traitement de btnSuivant//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
//	    btnSuivant.setText(Html.fromHtml("Suivant >"));
//	    btnSuivant.setTextSize(30);
		    
	    btnSuivant.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String emptyNom= txtNom.getText().toString();
				String emptyEmail= txtEmail.getText().toString();
				boolean emptyVerif= false;
				
				if(!(emptyNom.equals("")) && !(emptyEmail.equals(""))){
					
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
					
					txtNom.setHint("Champs obligatoire !");
					txtNom.setHintTextColor(Color.parseColor("#ff0000"));
				}
				if((emptyEmail.equals(""))){
					
					txtEmail.setHint("Champs obligatoire !");
					txtEmail.setHintTextColor(Color.parseColor("#ff0000"));
				}	/**/

			}
		});
	    
	    /**Traitement de btnAnnuler*/
//	    btnAnnuler.setText(Html.fromHtml("Annuler"));
//	    btnAnnuler.setTextSize(30);
	  }
}
