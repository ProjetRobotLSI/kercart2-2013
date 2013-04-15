package kercar.raspberry.arduino.message;

public class GetBlocked extends IArduinoMessage {

	public GetBlocked(){
		super(IArduinoMessage.RECEIVE_BLOCK);
	}

}
