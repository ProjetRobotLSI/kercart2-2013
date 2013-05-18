package kercar.raspberry.core;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.List;
import java.util.Map;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.PingMessage;
import kercar.comAPI.StateMessage;
import kercar.raspberry.arduino.SerialListener;
import kercar.raspberry.arduino.SerialManager;
import kercar.raspberry.arduino.message.AskAngle;
import kercar.raspberry.arduino.message.AskPos;
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
	private boolean takePhoto = false;
	private boolean blocked = false;
	
	private int angle = 0;
	private int longitude = 0;
	private int latitude = 0;
	private boolean gpsReady = false;
//	private CMDMissionMessage misssionPaused;
	
	public Core(String initPath){
		System.out.println("Starting core...");
		initUSB0(initPath);
		Core.initPath = initPath;
	//	new WifiIA(initPath);
		
		controlQueue = new LinkedBlockingDeque<IMessage>();
		arduinoQueue = new LinkedBlockingDeque<IArduinoMessage>();	
	}
	
	public synchronized void messageReceived(IMessage message){
		controlQueue.add(message);
	}
	
	public void run(){
		System.out.println("Running core...");
		this.inMission = false;
		this.pathfinder = new Pathfinder(this);
			
		handler = new MessageHandler(this);
		
		serialManager = new SerialManager();
		serialManager.setListener(this);
		serialManager.initialize();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		long startTimeUpdate = 0;
		
		this.askAngle();
		this.dodo(50);
		this.askCoordonnates();
		this.dodo(200);
		long startTimeAsk = System.currentTimeMillis();
		
		//Sale bouuuuuuuuu
		boolean first = true;
		while(true)
		{
			if (!controlQueue.isEmpty()) {
				handler.handle(controlQueue.poll());
			} else if((System.currentTimeMillis() - startTimeAsk) >= 5000) {
				this.askAngle();
				//Sinon port serial saturé
				this.dodo(50);			
				this.askCoordonnates();
				startTimeAsk = System.currentTimeMillis();
			}
			this.dodo(200);
			
			if (!arduinoQueue.isEmpty())
				handler.handle(arduinoQueue.poll());
						
			if(inMission && gpsReady) {
				if(first) {
					this.pathfinder.goToNextPoint(latitude, longitude, angle);
					first = false;
				}
			//	if(this.pathfinder.isArrived(1, 1)) {
				else if(this.pathfinder.isArrived(this.latitude, this.longitude)) {
					System.out.println("Core : ISARRIVED");
					Core.Log("Core : ISARRIVED");
					
					this.stopKercar();
					if(this.pathfinder.isLastPoint() ) {
						this.stopMission();
						first = true;
						if(takePhoto) {
							JapaneseTourist.takePhoto();
							JapaneseTourist.sendPhotos();
						}
					} else {					
			//			this.pathfinder.goToNextPoint(1,1, 10);
						this.pathfinder.goToNextPoint(this.latitude, this.longitude, this.angle);
					}
				} else if((System.currentTimeMillis() - startTimeUpdate) >= 2000){
		//			this.pathfinder.updateAngle(1, 1, 10);
					this.pathfinder.updateAngle(this.latitude, this.longitude, this.angle);
					startTimeUpdate = 0;
				}	
				
				if(startTimeUpdate == 0)
					startTimeUpdate = System.currentTimeMillis();
			}		
		}
	}
	
	private void dodo(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Core.Log("Core : GET_STATE");
		System.out.println("Core : GET_STATE");
		return new StateMessage(this.longitude, this.latitude, this.angle, this.blocked, this.gpsReady);
	}
	
	public PingMessage getPing(){
		return new PingMessage();
	}
	
	private void askAngle() {
	//	Core.Log("Core : ASK_ANGLE");
		System.out.println("Core : ASK_ANGLE");
		AskAngle arduinoMsg = new AskAngle();
		this.serialManager.write(arduinoMsg.toBytes());	
	}
	
	private void askCoordonnates() {
	//	Core.Log("Core : ASK_COORDONNATES");
		System.out.println("Core : ASK_COORDONNATES");
		AskPos arduinoMsg = new AskPos();
		this.serialManager.write(arduinoMsg.toBytes());	
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
	//	this.mail = mail;
		this.takePhoto = 
		this.inMission = true;
		this.pathfinder.setPath(points);
		this.pathfinder.setSpeed(speed);
		
	//	this.pathfinder.goToNextPoint(1, 1, 10);
	//	this.pathfinder.goToNextPoint(this.latitude, this.longitude, this.angle);
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
		System.out.println("SERIAL MESSAGE");
		this.arduinoQueue.add(IArduinoMessage.fromBytes(data));
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	public void setLatitude(int latitude) {
		this.latitude = latitude;
		
		if(latitude != 0)
			this.gpsReady = true;
		else
			this.gpsReady = false;
	}
	
	public void setLongitude(int longitude) {
		this.longitude = longitude;
		
		if(longitude != 0)
			this.gpsReady = true;
		else
			this.gpsReady = false;
	}
}
