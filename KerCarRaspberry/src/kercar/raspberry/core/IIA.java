package kercar.raspberry.core;

import java.util.List;


public interface IIA {
	public void turnLeft(int angle);
	public void turnRight(int angle);
	public void forward(int speed);
	public void backward(int speed);
	public void stopKercar();
	
	public void launchMission(List<Integer> points, String mail, int speed, boolean takePhoto);
	
	public void stopMission();
	public void setBlocked(boolean blocked);
	public void setAngle(int angle);
	public void setLongitude(int longitude);
	public void setLatitude(int latitude);
//	public MessageHandler getMessageHandler();
}
