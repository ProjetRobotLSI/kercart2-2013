package kercar.raspberry.core;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.Message;
import kercar.comAPI.StateMessage;
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
		else if(message.getType() == Message.GET_STATE) {
			Core.Log("MessageHandler : GET_STATE");
			System.out.println("MessageHandler : GET_STATE");
			GetPos pos = this.ia.getGPSCoordonnates();
			GetAngle angle = this.ia.getAngle();
			
			this.ia.getServletQueue().add(new StateMessage(pos.getLongitude(), pos.getLatitude(), angle.getDegree()));
		}
		
	}
	
	public void handle(IArduinoMessage message)
	{
		Core.Log("MessageHandler : Message from Arduino");
		System.out.println("MessageHandler : Message from Arduino");
		if(message.getID() == IArduinoMessage.RECEIVE_BLOCK) {
			Core.Log("MessageHandler : RECEIVE_BLOCK");
			System.out.println("MessageHandler : RECEIVE_BLOCK");
			this.ia.stopMission();
			
			//this.ia.getServletQueue().add(new Stop());
		}
	}
}
