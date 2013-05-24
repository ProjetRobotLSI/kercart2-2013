package kercar.raspberry.core;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.Message;
import kercar.raspberry.arduino.message.GetAngle;
import kercar.raspberry.arduino.message.GetPos;
import kercar.raspberry.arduino.message.IArduinoMessage;

public class MessageHandler {
	private IIA ia;
	
	public MessageHandler(IIA ia) {
		this.ia = ia;
	}

	public void handle(IMessage message)
	{
		System.out.println("Message received (Handler)");
		this.ia.setBlocked(false);
		if (message.getType() == Message.CMD_MOVE)
		{
			Core.Log("MessageHandler : CMD_MOVE");
			CMDMoveMessage move = new CMDMoveMessage((Message)message);
			if(move.isBackward()){
				System.out.println("MessageHandler : BACKWARD");
				Core.Log("MessageHandler : BACKWARD");
				this.ia.stopMission();
				this.ia.backward(move.getSpeed());
			}
			else{
				System.out.println("Going forward (Handler)");
				Core.Log("MessageHandler : FORWARD");
				this.ia.stopMission();
				this.ia.forward(move.getSpeed());
			}
		}
		else if (message.getType() == Message.CMD_TURN)
		{
			Core.Log("MessageHandler : CMD_TURN");
			System.out.println("MessageHandler : CMD_TURN");
			CMDTurnMessage turn = new CMDTurnMessage((Message)message);
			this.ia.stopMission();
			if (turn.isTurningRight())
			{
				Core.Log("RIGHT");
				//TODO RECUP ANGLE
				this.ia.turnRight(50);
			}
			else {
				Core.Log("LEFT");
				//TODO RECUP ANGLE
				this.ia.turnLeft(50);
			}			
		}
		else if (message.getType() == Message.CMD_STOP)
		{		
			Core.Log("MessageHandler : CMD_STOP");
			System.out.println("MessageHandler : CMD_STOP");
			this.ia.stopMission();
			this.ia.stopKercar();
		}
		else if (message.getType() == Message.CMD_MISSION) {
			Core.Log("MessageHandler : CMD_MISSION");
			System.out.println("MessageHandler : CMD_MISSION");
			this.ia.stopMission();
			this.ia.stopKercar();
			CMDMissionMessage mission = new CMDMissionMessage((Message)message);
			//TODO GET SPEED
			this.ia.launchMission(mission.getCoordinates(), mission.getMailAddress(), 75, mission.getPhoto());
		}
	}
	
	public void handle(IArduinoMessage message) {
		Core.Log("MessageHandler : Message from Arduino");
		System.out.println("MessageHandler : Message from Arduino");
		System.out.println("ID " + message.getID());
		if(message.getID() == IArduinoMessage.RECEIVE_BLOCK) {
			Core.Log("MessageHandler : RECEIVE_BLOCK");
			System.out.println("MessageHandler : RECEIVE_BLOCK");
			this.ia.stopMission();
			this.ia.setBlocked(message.getParam(0) == 1);
			//this.ia.getServletQueue().add(new Stop());
		} 
		else if(message.getID() == IArduinoMessage.RECEIVE_ANGLE) {
			Core.Log("MessageHandler : RECEIVE_ANGLE");
			System.out.println("MessageHandler : RECEIVE_ANGLE");
		//	GetAngle angle = (GetAngle) message;
			this.ia.setAngle(message.getParam(0));
		}
		else if(message.getID() == IArduinoMessage.RECEIVE_POS) {
			Core.Log("MessageHandler : RECEIVE_POS");
			System.out.println("MessageHandler : RECEIVE_POS");
	//		GetPos pos = (GetPos) message;
			
			//TODO Traitement des coordonnées venant de l'arduino !
			
			this.ia.setLongitude(message.getParam(0));
			this.ia.setLatitude(message.getParam(1));
		}
	}
	
	private double toGPSCompatibleData(int data) {
		//2 chiffres les plus à gauche : degré
		//2 suivant : minutes
		//4 suivant : décimales minutes
		//le plus à droite : orientation : N = O, S = 1, E = 2, W = 3
		
		/*301043242
		
		30 = degré
		10,4324 = minutes
		2 = E*/
		
		String tmp = String.valueOf(data);
		double degree = Integer.parseInt(tmp.substring(0, 2));
		double minutes = Integer.parseInt(tmp.substring(2, 4));
		double sec = Integer.parseInt(tmp.substring(4, 9));
		
		double result = degree + (minutes / 60.0) + (sec / 3600.0); 
		
		//int degree
		
		return result;
	}
}
