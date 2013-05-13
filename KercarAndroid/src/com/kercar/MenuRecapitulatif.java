package com.kercar;

import BaseDeDonnees.Mission;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MenuRecapitulatif extends Activity{

	private Button btnOK;
	private TextView txtNom;
	private TextView txtEmail;
	private CheckBox cbxRetourDepart;
	private CheckBox cbxPhotoArrivee;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    /**ContentView*/
	    setContentView(R.layout.menu_recapitulatif);

	    /**Initialisation des attributs*/
	    txtNom = (TextView) findViewById(R.id.txtNom);
	    txtEmail= (TextView) findViewById(R.id.txtEmail);
	    cbxRetourDepart = (CheckBox) findViewById(R.id.cbxRetourDepart);
	    cbxPhotoArrivee = (CheckBox) findViewById(R.id.cbxPhotoArrivee);
	    btnOK = (Button) findViewById(R.id.btnOK);
	    
	    /**Reception de bundles*/
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
				editor.putBoolean("missionLancee", true);
				editor.commit();
				
				Intent intent = new Intent(MenuRecapitulatif.this, MenuSelection.class);
				startActivity(intent);
			}
		});
	  }
}