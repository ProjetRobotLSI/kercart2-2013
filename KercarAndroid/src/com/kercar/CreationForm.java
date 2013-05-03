package com.kercar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CreationForm extends Activity{

	private TextView lblNom= null;
	private TextView lblEmail= null;
	private TextView lblRetourDepart= null;
	private TextView lblPhotoArrivee= null;
	private Button btnCreer= null;
	
	
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
	    //btnCreer = (Button) findViewById(R.id.btnCreer);
	    
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
	    
	    /**Traitement sur lblNom*/
/*	    btnCreer.setText(Html.fromHtml("Creer"));
	    btnCreer.setTextSize(30);*/
	    
	  }
}
