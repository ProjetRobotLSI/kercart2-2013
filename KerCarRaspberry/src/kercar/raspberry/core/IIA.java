package kercar.raspberry.core;

public interface IIA {
	public void nextSerialMessage();
	public void nextControlMessage();
	public void takePicture();
	public void sendPicture();	
}
