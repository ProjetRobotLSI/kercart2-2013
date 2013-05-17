package com.kercar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;

import com.kercar.AsyncTask.AsyncGetEtatDeuxPoints;
import com.kercar.AsyncTask.AsyncLancerMission;
import com.kercar.osmandroid.OSMAndroid;

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
	private OSMAndroid OSM;
	
	private List<Integer> list;
	
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
	    OSM = (OSMAndroid) findViewById(R.id.OSM_choix_point);
	    
	    list = new LinkedList<Integer>();
	    
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
		
		//Initialisation de la carte OSM
//		OSM.addPoint(newMission.getM_fin()[0], newMission.getM_fin()[1], "Point Arrivee", "");
//		new AsyncGetEtatDeuxPoints(list, com, OSM);
		
		/**Traitement de btnOK*/
		btnOK.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				int[] ints = newMission.get
//			    List<Integer> intList = new ArrayList<Integer>();
//			    for (int i= 0 ; i<ints.length ; i++){
//			        intList.add(ints[i]);
//			    }
				//Envoi de la mission au robot //VERIFIER LIST INTEGER
				new AsyncLancerMission(new LinkedList<Integer>(), newMission.getPrendrePhotosArrivee(), newMission.getRetourDepart(), newMission.getEmail(), com).execute();
				
				//Demarrage de l'activite
				Intent intent = new Intent(MenuRecapitulatif.this, MenuMissionEnCours.class);
				startActivity(intent);
			}
		});
	  }
}