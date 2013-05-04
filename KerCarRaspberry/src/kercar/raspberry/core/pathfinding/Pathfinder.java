package kercar.raspberry.core.pathfinding;

import java.util.Iterator;
import java.util.List;

import kercar.raspberry.core.IIAPathfinder;

public class Pathfinder implements IPathfinder {

	private IIAPathfinder iA;
	private Iterator<Integer> it;
	
	private int pointLatitude, pointLongitude;
	
	public Pathfinder(IIAPathfinder iA) {
		this.iA = iA;
	}
	
	@Override
	public void setPath(List<Integer> path) {
		this.it = path.iterator();
	}

	@Override
	public void goToNextPoint(int latitude, int longitude) {
		this.pointLatitude = it.next();
		this.pointLongitude = it.next();
		
		//TODO calcul angle 
		int angle = 0;
		
		this.iA.setAngle(angle);
		this.iA.forward();
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

}
