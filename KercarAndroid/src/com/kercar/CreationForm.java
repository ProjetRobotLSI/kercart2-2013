package com.kercar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CreationForm extends Activity{

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
	    
	    /**ContentView*/
	    setContentView(R.layout.creation_frm);

	    /**Initialisation des attributs*/
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
	    
	    /**Traitement sur lblNom*/
	    lblNom.setText(Html.fromHtml("<p style='color:green'> Nom de la mission: </p>"));
	    lblNom.setTextSize(30);
	    
	    /**Traitement sur lblEmail*/
	    lblEmail.setText(Html.fromHtml("Adresse e-mail:"));
	    lblEmail.setTextSize(30);
	    
	    /**Traitement sur lblRetourDepart*/
	    lblRetourDepart.setText(Html.fromHtml("Retour du robot:"));
	    lblRetourDepart.setTextSize(30);
	    
	    /**Traitement sur lblPhotoArrivee*/
	    lblPhotoArrivee.setText(Html.fromHtml("Photo Arrivee:"));
	    lblPhotoArrivee.setTextSize(30);
	    
	    /**Traitement sur txtNom*/
		txtNom.setHint(R.string.editNom);
		txtNom.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		txtNom.setLines(5);
		
	    /**Traitement sur txtNom*/
		txtEmail.setHint(R.string.editEmail);
		txtEmail.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		txtEmail.setLines(5);
		
		/**Traitement sur cbxRetourDepart*/
		cbxRetourDepart.setText(R.string.cbxRetour);
		cbxRetourDepart.setChecked(true);
		if(cbxRetourDepart.isChecked())
			System.out.println("Checked !");
		
		/**Traitement sur cbxPhotoArrivee*/
		cbxPhotoArrivee.setText(R.string.cbxRetour);
		cbxPhotoArrivee.setChecked(true);
		if(cbxPhotoArrivee.isChecked())
			System.out.println("Checked !");
		
	    /**Traitement de btnSuivant*/
	    btnSuivant.setText(Html.fromHtml("Suivant >"));
	    btnSuivant.setTextSize(30);
	    
	    /**Traitement de btnSuivant*/
	    btnAnnuler.setText(Html.fromHtml("Annuler"));
	    btnAnnuler.setTextSize(30);
	    
	  }
}
