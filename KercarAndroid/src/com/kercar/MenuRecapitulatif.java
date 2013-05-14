package com.kercar;

import java.util.LinkedList;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;

import com.kercar.AsyncTask.AsyncLancerMission;

import BaseDeDonnees.Mission;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MenuRecapitulatif extends Activity{

	//Attributs
	private Button btnOK;
	private TextView txtNom;
	private TextView txtEmail;
	private CheckBox cbxRetourDepart;
	private CheckBox cbxPhotoArrivee;
	
	private IComAndroid com;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.menu_recapitulatif);

	    /**Initialisation des attributs*/
	    txtNom = (TextView) findViewById(R.id.txtNom);
	    txtEmail= (TextView) findViewById(R.id.txtEmail);
	    cbxRetourDepart = (CheckBox) findViewById(R.id.cbxRetourDepart);
	    cbxPhotoArrivee = (CheckBox) findViewById(R.id.cbxPhotoArrivee);
	    btnOK = (Button) findViewById(R.id.btnOK);
	    
	    com = ComAndroid.getManager();
	    
	    //Creation du bundle et reception des objets transferes
        Bundle receptionBundle  = this.getIntent().getExtras().getBundle("AjoutBundleDansIntent");        
    	final Mission newMission= (Mission) receptionBundle.getSerializable("AjoutMissionDansBundle");
	    
    	/**Traitement sur txtNom*/
	    txtNom.setText(newMission.getNom());
	   		
	    /**Traitement sur txtEmail*/
		txtEmail.setText(newMission.getEmail());		
		
		/**Traitement sur cbxRetourDepart*/
		cbxRetourDepart.setChecked(newMission.getRetourDepart());	

		/**Traitement sur cbxPhotoArrivee*/
		cbxPhotoArrivee.setChecked(newMission.getPrendrePhotosArrivee());
		
		/**Traitement de btnOK*/
		SharedPreferences missionLancee = PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor editor = missionLancee.edit();

	    btnOK.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Edition des preferences : mission en cours
				editor.putBoolean("missionLancee", true);
				editor.commit();
				
				//Envoi de la mission au robot //VERIFIER LIST INTEGER
				new AsyncLancerMission(new LinkedList<Integer>(), newMission.getPrendrePhotosArrivee(), newMission.getRetourDepart(), newMission.getEmail(), com).execute();
				
				//Demarrage de l'activite
				Intent intent = new Intent(MenuRecapitulatif.this, MenuSelection.class);
				startActivity(intent);
			}
		});
	  }
}