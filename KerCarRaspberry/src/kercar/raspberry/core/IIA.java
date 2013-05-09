package kercar.raspberry.core;

import java.util.List;

public interface IIA {

	public void getGPSCoordonnate();
	public void getCompass();
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
