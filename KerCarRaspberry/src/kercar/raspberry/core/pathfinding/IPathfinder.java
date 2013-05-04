package kercar.raspberry.core.pathfinding;

import java.util.List;

public interface IPathfinder {

	public void setPath(List<Integer> path);
	public void goToNextPoint(int latitude, int longitude, int speed, int compass);
	public void stop();

	public void updateAngle(int gpsLatitude, int gpsLongitude, int compass);
	public boolean isArrived(int latitude, int longitude);
	public boolean isLastPoint();
}
