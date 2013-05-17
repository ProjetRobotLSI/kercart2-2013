package com.kercar.osmandroid;

import java.util.ArrayList;

import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.PathOverlay;

import android.util.Log;

public class RoadThread extends Thread {

	private OSMAndroid osm;
	private GeoPoint start, end;
	
	public RoadThread(OSMAndroid osm, GeoPoint start, GeoPoint end) {
		this.osm = osm;
		this.start = start;
		this.end = end;
	}
	
	public void run() {
		 Log.e("osm", "ici 1");
			RoadManager roadManager = new MapQuestRoadManager();
	        Log.e("osm", "ici 2");
	        roadManager.addRequestOption("routeType=bicycle");
	        Log.e("osm", "ici 3");

			//Two points added
			ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
	        Log.e("osm", "ici 4");
			waypoints.add(start);
			waypoints.add(end);
	        Log.e("osm", "ici 5");
			Road road = roadManager.getRoad(waypoints);
	        Log.e("osm", "ici 6");
			// Let's go the mall !
			PathOverlay roadOverlay = RoadManager.buildRoadOverlay(road, osm.getContext());
	        Log.e("osm", "ici 7");
			osm.getOverlays().add(roadOverlay);
	        Log.e("osm", "ici 8");
			osm.getSparPathOberlay().append(osm.getIdRoad() - 1, roadOverlay);
	        Log.e("osm", "ici 9");
			osm.getSparRoad().append(osm.getIdRoad() - 1, road);
	        Log.e("osm", "ici 10");
	}
}
