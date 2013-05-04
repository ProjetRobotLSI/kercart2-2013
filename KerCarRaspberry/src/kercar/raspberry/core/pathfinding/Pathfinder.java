package kercar.raspberry.core.pathfinding;

import java.util.Iterator;
import java.util.List;

import kercar.raspberry.core.Core;
import kercar.raspberry.core.IIA;


public class Pathfinder implements IPathfinder {

	private IIA iA;
	private Iterator<Integer> it;
	
	private int pointLatitude = 0, pointLongitude = 0;
	private int currentSpeed = 0;
	
	public Pathfinder(IIA iA) {
		this.iA = iA;
	}
	
	@Override
	public void setPath(List<Integer> path) {
		this.it = path.iterator();
	}

	@Override
	public void goToNextPoint(int gpsLatitude, int gpsLongitude, int speed, int compass) {
		System.out.println("PathFinder : NEXT POINT");
		Core.Log("PathFinder : NEXT POINT");
		
		this.pointLatitude = it.next();
		this.pointLongitude = it.next();
		this.currentSpeed = speed;
		int angle = this.calculateAngle(gpsLatitude, gpsLongitude, compass);
		
		if(angle < 180) 
			this.iA.turnRight(angle);
		else
			this.iA.turnLeft(360 - angle);
		
		this.iA.forward(speed);
	}
	
	public void updateAngle(int gpsLatitude, int gpsLongitude, int compass) {
		System.out.println("PathFinder : UPDATING ANGLE");
		Core.Log("PathFinder : UPDATING ANGLE");
		
		int angle = this.calculateAngle(gpsLatitude, gpsLongitude, compass);
		
		boolean turn = false;
		if((angle >= compass && ((100 * compass) / angle) >= 10) || (compass > angle && ((100 * angle) / compass) >= 10)){
				turn = true;
		} 
		
		if(turn) {
			if(angle < 180) 
				this.iA.turnRight(angle);
			else
				this.iA.turnLeft(360 - angle);
			//TODO Attendre que kercar tourne
		}	
				
		this.iA.forward(this.currentSpeed);
	}
	
	private int calculateAngle(int gpsLatitude, int gpsLongitude, int compass) {	
		//Calcul de a²
		double a = Math.pow(pointLongitude - gpsLongitude, 2) + Math.pow(pointLatitude - gpsLatitude, 2);
		System.out.println("a " + a);
		
		//Calcul de b²
		int YNord = gpsLatitude + 5;
		int XNord = gpsLongitude;
		double b = Math.pow(XNord - gpsLongitude, 2) + Math.pow(YNord - gpsLatitude, 2);
		System.out.println("c " + b);
		
		//Calcul de c²	
		double c = Math.pow(pointLongitude - XNord, 2) + Math.pow(pointLatitude - YNord, 2);
		System.out.println("b " + c);
		
		//Al kashi
		//TODO Attention si a +b  > c à voir si BOUMBOUM
		double angleBetaRadian =  Math.acos(Math.abs(a + b - c) / (2 * Math.sqrt(a) * Math.sqrt(b)));
		int angleBeta = (int) ((180 * angleBetaRadian) / Math.PI);
		System.out.println("angleBeta " + angleBeta);
		
		if(compass <= 180) {
			if(pointLongitude >= XNord)
				return compass + angleBeta;
			else if (angleBeta >= compass)
				return 360 - (angleBeta - compass);
			else
				return compass - angleBeta;
		} else {
			compass = 360 - compass;
			if(pointLongitude <= XNord)
				return 360 - (compass + angleBeta);
			else if (angleBeta >= compass)
				return angleBeta - compass;
			else
				return 360 - (compass - angleBeta);
		}			
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isArrived(int gpslatitude, int gpsLongitude) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isLastPoint() {
		return !it.hasNext(); 
	}

}
