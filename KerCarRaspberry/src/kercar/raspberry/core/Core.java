package kercar.raspberry.core;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.List;
import java.util.Map;

import kercar.comAPI.IMessage;
import kercar.comAPI.PingMessage;
import kercar.comAPI.StateMessage;
import kercar.raspberry.arduino.SerialListener;
import kercar.raspberry.arduino.SerialManager;
import kercar.raspberry.arduino.message.AskAngle;
import kercar.raspberry.arduino.message.AskPos;
import kercar.raspberry.arduino.message.GetAngle;
import kercar.raspberry.arduino.message.GetPos;
import kercar.raspberry.arduino.message.GoBackward;
import kercar.raspberry.arduino.message.GoForward;
import kercar.raspberry.arduino.message.IArduinoMessage;
import kercar.raspberry.arduino.message.Stop;
import kercar.raspberry.arduino.message.TurnLeft;
import kercar.raspberry.arduino.message.TurnRight;
import kercar.raspberry.core.pathfinding.IPathfinder;
import kercar.raspberry.core.pathfinding.Pathfinder;

import com.kercar.raspberry.wifi.WifiIA;

public class Core extends Thread implements IIA, SerialListener {

	private BlockingQueue<IMessage> controlQueue;
	private BlockingQueue<IArduinoMessage> arduinoQueue;
	
	private MessageHandler handler;
	
	private IPathfinder pathfinder;
	private SerialManager serialManager;
	private static String initPath;
	private boolean inMission;
	private String mail;
	private boolean takePhoto = false;
	private boolean blocked = false;
	
	public Core(String initPath){
		System.out.println("Starting core...");
		initUSB0(initPath);
		Core.initPath = initPath;
		new WifiIA(initPath);
		
		controlQueue = new LinkedBlockingDeque<IMessage>();
		arduinoQueue = new LinkedBlockingDeque<IArduinoMessage>();		
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
		serialManager = new SerialManager();
		serialManager.initialize();
		serialManager.setListener(this);
		handler = new MessageHandler(this);
		
		long startTime = 0;
		while(true)
		{
			if (!controlQueue.isEmpty())
				handler.handle(controlQueue.poll());
			
			if (!arduinoQueue.isEmpty())
				handler.handle(arduinoQueue.poll());
			
			if(inMission) {
				GetPos pos = this.getGPSCoordonnates();
				GetAngle angle = this.getAngle();
			//	if(this.pathfinder.isArrived(1, 1)) {
				if(this.pathfinder.isArrived(pos.getLatitude(), pos.getLongitude())) {
					System.out.println("Core : ISARRIVED");
					Core.Log("Core : ISARRIVED");
					
					this.stopKercar();
					if(this.pathfinder.isLastPoint() ) {
						this.stopMission();
						if(takePhoto) {
							JapaneseTourist.takePhoto();
							JapaneseTourist.sendPhotos();
						}
					} else {					
			//			this.pathfinder.goToNextPoint(1,1, 10);
						this.pathfinder.goToNextPoint(pos.getLatitude(), pos.getLongitude(), angle.getDegree());
					}
				} else if((System.currentTimeMillis() - startTime) >= 2000){
		//			this.pathfinder.updateAngle(1, 1, 10);
					this.pathfinder.updateAngle(pos.getLatitude(), pos.getLongitude(), angle.getDegree());
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
		Core.Log("MessageHandler : GET_STATE");
		System.out.println("MessageHandler : GET_STATE");
		GetPos pos = this.getGPSCoordonnates();
		GetAngle angle = this.getAngle();
		return new StateMessage(pos.getLongitude(), pos.getLatitude(), angle.getDegree(), blocked);
	}
	
	public PingMessage getPing(){
		return new PingMessage();
	}

	@Override
	public GetAngle getAngle() {
		AskPos arduinoMsg = new AskPos();
		this.serialManager.write(arduinoMsg.toBytes());	
		
		GetAngle angle = null;
		while(angle == null) {	
			if(!this.arduinoQueue.isEmpty()) {
				IArduinoMessage message = this.arduinoQueue.poll();
				if(message.getID() != IArduinoMessage.RECEIVE_ANGLE) {
					this.handler.handle(message);
				} else {
					angle = (GetAngle) message;
				}
			}
		}
		return angle;
	}

	@Override
	public GetPos getGPSCoordonnates() {
		AskAngle arduinoMsg = new AskAngle();
		this.serialManager.write(arduinoMsg.toBytes());	
		
		GetPos pos = null;
		while(pos == null) {
			if(!this.arduinoQueue.isEmpty()) {
				IArduinoMessage message = this.arduinoQueue.poll();
				if(message.getID() != IArduinoMessage.RECEIVE_POS) {
					this.handler.handle(message);
				} else {
					pos = (GetPos) message;
				}
			}
		}
		return pos;
	}

	@Override
	public void turnLeft(int angle) {
		System.out.println("Core : Turn left");
		TurnLeft arduinoMsg = new TurnLeft();
		arduinoMsg.setDegree(angle);
		this.serialManager.write(arduinoMsg.toBytes());	
	}

	@Override
	public void turnRight(int angle) {
		System.out.println("Core : Turn Right");
		TurnRight arduinoMsg = new TurnRight();
		arduinoMsg.setDegree(angle);
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void forward(int speed) {
		System.out.println("Core : forward");
		GoForward arduinoMsg = new GoForward();
		arduinoMsg.setVitesse(speed);
		this.serialManager.write(arduinoMsg.toBytes());
	}

	@Override
	public void backward(int speed) {
		System.out.println("Core : backward");
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
	public void launchMission(List<Integer> points, String mail, int speed, boolean takePhoto) {
		System.out.println("Launching mission...");
		this.mail = mail;
		this.takePhoto = 
		this.inMission = true;
		this.pathfinder.setPath(points);
		this.pathfinder.setSpeed(speed);
		GetPos pos = this.getGPSCoordonnates();
		GetAngle angle = this.getAngle();
		
	//	this.pathfinder.goToNextPoint(1, 1, 10);
		this.pathfinder.goToNextPoint(pos.getLatitude(), pos.getLongitude(), angle.getDegree());
	}
	
	public void stopMission() {
		this.inMission = false;
	}
	
	@Override
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public void onSerialMessage(byte[] data) {
		arduinoQueue.add(IArduinoMessage.fromBytes(data));
	}
	
	public MessageHandler getMessageHandler() {
		return this.handler;
	}
}
