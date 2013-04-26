package kercar.raspberry.core;

import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Map;

import kercar.comAPI.IMessage;
import kercar.raspberry.arduino.SerialManager;

import com.kercar.raspberry.wifi.WifiIA;

public class Core extends Thread {

	BlockingQueue<IMessage> messageQueue;
	SerialManager serialManager;
	private static String initPath;
	
	public Core(String initPath){
		System.out.println("Starting core...");
		initUSB0(initPath);
		Core.initPath = initPath;
		new WifiIA(initPath);	
	}
	
	public synchronized void messageReceived(IMessage message){
		messageQueue.add(message);
	}
	
	
	public void run(){
		System.out.println("Running core...");
		messageQueue = new LinkedBlockingDeque<IMessage>();
		serialManager = new SerialManager();
		serialManager.initialize();
		MessageHandler handler = new MessageHandler(serialManager);
		
		while(true)
		{
			if (!messageQueue.isEmpty())
				handler.handle(messageQueue.poll());
		}
	}
	
	public static void Log(String s){
		s = s.concat("\n");
		try{
			FileOutputStream fos = new FileOutputStream(initPath+"logs/"+"KerCar.log", true);
			fos.write(s.getBytes());
			fos.close();
		}
		catch(Exception e){
			
		}
	}
	
	public void initUSB0(String tomPath){
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "ln -s /dev/ttyACM0 /dev/ttyUSB0");
		Map<String, String> env = pb.environment();
		env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			System.out.println("Creation du lien symbolique...");
			Process p = pb.start();
			p.waitFor();
			System.out.println("Creation OK");
		} catch (Exception e) {
			System.out.println("Error binding : not present or already done");
			e.printStackTrace();
		}
	}
	
}
