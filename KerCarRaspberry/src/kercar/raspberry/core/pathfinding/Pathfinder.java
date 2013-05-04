package kercar.raspberry.core.pathfinding;

import java.util.Iterator;
import java.util.List;

import kercar.raspberry.core.IIA;


public class Pathfinder implements IPathfinder {

	private IIA iA;
	private Iterator<Integer> it;
	
	private int pointLatitude, pointLongitude;
	
	public Pathfinder(IIA iA) {
		this.iA = iA;
	}
	
	@Override
	public void setPath(List<Integer> path) {
		this.it = path.iterator();
	}

	@Override
	public void goToNextPoint(int latitude, int longitude, int speed) {
		this.pointLatitude = it.next();
		this.pointLongitude = it.next();
		
		//TODO calcul angle 
		int angle = 0;
		
	/*	this.iA.turnLeft(angle);
		this.iA.turnRight(angle);*/
		this.iA.forward(speed);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isArrived(int latitude, int longitude) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isLastPoint() {
		return !it.hasNext(); 
	}

}
