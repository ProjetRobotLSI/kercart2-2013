package kercar.raspberry.core.pathfinding;

import java.util.List;

public interface IPathfinder {

	public void setPath(List<Integer> path);
	public void goToNextPoint(int latitude, int longitude, int speed);
	public void stop();
	public boolean isArrived(int latitude, int longitude);
	public boolean isLastPoint();
}
