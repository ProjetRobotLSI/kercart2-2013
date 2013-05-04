package kercar.raspberry.core;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.List;
import java.util.Map;

import kercar.comAPI.IMessage;
import kercar.comAPI.IStateMessage;
import kercar.comAPI.PingMessage;
import kercar.comAPI.StateMessage;
import kercar.raspberry.arduino.SerialManager;
import kercar.raspberry.arduino.message.GetAngle;
import kercar.raspberry.arduino.message.GetGPSInfo;
import kercar.raspberry.arduino.message.GoBackward;
import kercar.raspberry.arduino.message.GoForward;
import kercar.raspberry.arduino.message.IArduinoMessage;
import kercar.raspberry.arduino.message.Stop;
import kercar.raspberry.arduino.message.TurnLeft;
import kercar.raspberry.arduino.message.TurnRight;
import kercar.raspberry.core.pathfinding.IPathfinder;
import kercar.raspberry.core.pathfinding.Pathfinder;

import com.kercar.raspberry.wifi.WifiIA;

public class Core extends Thread implements IIA {

	private BlockingQueue<IMessage> controlQueue;
	private BlockingQueue<IArduinoMessage> arduinoQueue;
	
	private IPathfinder pathfinder;
	private SerialManager serialManager;
	private static String initPath;
	private boolean inMission;
	private String mail;
	
	public Core(String initPath){
		System.out.println("Starting core...");
		initUSB0(initPath);
		Core.initPath = initPath;
		new WifiIA(initPath);	
	}
	
	public synchronized void messageReceived(IMessage message){
		controlQueue.add(message);
	}
	
	public synchronized void messageArduinoReceived(IArduinoMessage message){
		arduinoQueue.add(message);
	}
	
	public void run(){
		System.out.println("Running core...");
		this.inMission = false;
		this.pathfinder = new Pathfinder(this);
		controlQueue = new LinkedBlockingDeque<IMessage>();
		serialManager = new SerialManager();
		serialManager.initialize();
		MessageHandler handler = new MessageHandler(this);
		
		long startTime = 0;
		while(true)
		{
			if (!controlQueue.isEmpty())
				handler.handle(controlQueue.poll());
			
			if (!arduinoQueue.isEmpty())
				handler.handle(arduinoQueue.poll());
			
			if(inMission) {
				//TODO GET GPS COORDONNATES	
				//TODO GET COMPASS
				if(this.pathfinder.isArrived(0, 0)) {
					System.out.println("Core : ISARRIVED");
					Core.Log("Core : ISARRIVED");
					
					this.stopKercar();
					if(this.pathfinder.isLastPoint() ) {
						this.stopMission();
					} else {									
						this.pathfinder.goToNextPoint(0,0, 50, 0);
					}
				} else if((System.currentTimeMillis() - startTime) >= 2000){
					this.pathfinder.updateAngle(0, 0, 0);
					startTime = 0;
				}	
				
				if(startTime == 0)
					startTime = System.currentTimeMillis();
			}		
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
		ProcessBuilder pb = new ProcessBuilder(tomPath+"/create_link.sh");
		Map<String, String> env = pb.environment();
		env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			System.out.println("Creation du lien symbolique...");
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("Creation OK");
		} catch (Exception e) {
			System.out.println("Error binding : not present or already done");
			e.printStackTrace();
		}
	}

        private String getProcessOutput(Process p){

                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String tmp;
                StringBuffer bf = new StringBuffer();

                try{
                        while( (tmp = br.readLine()) != null ){
                                bf.append(tmp+System.getProperty("line.separator"));
                        }
                        br.close();
                } catch(Exception e){
                        e.printStackTrace();
                }
                System.out.println(bf.toString());
                return bf.toString();
        }
        
	public StateMessage getRobotState(){
		return new StateMessage(0,1,2);
	}
	
	public PingMessage getPing(){
		return new PingMessage();
	}

	@Override
	public void getGPSCoordonnate() {
		GetGPSInfo arduinoMsg = new GetGPSInfo();
		this.serialManager.write(arduinoMsg.toBytes());	
	}

	@Override
	public void getCompass() {
		GetAngle arduinoMsg = new GetAngle();
		this.serialManager.write(arduinoMsg.toBytes());	
	}

	@Override
	public void turnLeft(int angle) {
		TurnLeft arduinoMsg = new TurnLeft();
		arduinoMsg.setDegree(angle);
		this.serialManager.write(arduinoMsg.toBytes());	
	}

	@Override
	public void turnRight(int angle) {
		TurnRight arduinoMsg = new TurnRight();
		arduinoMsg.setDegree(angle);
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void forward(int speed) {
		GoForward arduinoMsg = new GoForward();
		arduinoMsg.setVitesse(speed);
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void backward(int speed) {
		GoBackward arduinoMsg = new GoBackward();
		arduinoMsg.setVitesse(speed);
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void stopKercar() {
		System.out.println("Core : stopCar");
		Core.Log("Core : stopCar");
		Stop arduinoMsg = new Stop();
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void nextSerialMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextControlMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takePicture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPicture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launchMission(List<Integer> points, String mail) {
		System.out.print("Launching mission...");
		this.mail = mail;
		this.inMission = true;
		this.pathfinder.setPath(points);
		
		this.getGPSCoordonnate();
		
		//TODO GET COORDONNATES AND COMPASS
		this.pathfinder.goToNextPoint(0, 0, 50, 0);
	}
	
	public void stopMission() {
		this.inMission = false;
	}
}
