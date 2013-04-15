package kercar.raspberry.arduino.message;

public class GoToPos extends IArduinoMessage {

	public GoToPos(){
		super(IArduinoMessage.GOTOPOS);
	}
	
	public GoToPos(int longitude, int latitude){
		super(IArduinoMessage.GOTOPOS);
		setLongitude(longitude);
		setLatitude(latitude);
	}
	
	public void setLongitude(int longitude){
		this.setParam(0, longitude);
	}
	
	public void setLatitude(int latitude){
		this.setParam(1, latitude);
	}
}
