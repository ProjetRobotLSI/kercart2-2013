package kercar.raspberry.arduino;

import kercar.raspberry.arduino.message.AskGPSInfo;
import kercar.raspberry.arduino.message.IArduinoMessage;


public class ArduinoTest {

	public static void main(String[] args){
		
		// Initialisation de la classe de com
		SerialManager main = new SerialManager();
		main.initialize();
		System.out.println("Started");
			
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Init du message
		IArduinoMessage msg = new AskGPSInfo();
		System.out.println("ID : "+IArduinoMessage.extractID(msg.toBytes()));
		System.out.println(msg.toString());
		
		try {
			System.out.println("Envoi du message");
			main.write(msg.toBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
