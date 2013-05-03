package com.kercar.osmandroid;

import java.util.ArrayList;

import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import android.os.AsyncTask;

/**
 * Create a road between two points. The road is created on a asynchronous task
 * @author F. Duros
 */
public class RoadAsyncTask extends AsyncTask<GeoPoint, Void, Road> {
	
	@Override
	protected Road doInBackground(GeoPoint... params) {
		
		RoadManager roadManager = new MapQuestRoadManager();
        roadManager.addRequestOption("routeType=bicycle");
		
		//Two points added
		ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
		waypoints.add(params[0]);
		waypoints.add(params[1]); 
		
		// Let's go the mall !
        return roadManager.getRoad(waypoints);
	}
}
