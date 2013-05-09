package kercar.raspberry.core;

import java.util.List;

import kercar.raspberry.arduino.message.GetAngle;
import kercar.raspberry.arduino.message.GetPos;

public interface IIA {

	public GetPos getGPSCoordonnates();
	public GetAngle getAngle();
	public void turnLeft(int angle);
	public void turnRight(int angle);
	public void forward(int speed);
	public void backward(int speed);
	public void stopKercar();
	
	public void takePicture();
	public void sendPicture();
	public void launchMission(List<Integer> points, String mail, int speed);
	
	public void stopMission();
}
