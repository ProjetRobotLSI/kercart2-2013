package kercar.raspberry.core;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.Message;
import kercar.raspberry.arduino.SerialManager;
import kercar.raspberry.arduino.message.GoBackward;
import kercar.raspberry.arduino.message.GoForward;
import kercar.raspberry.arduino.message.IArduinoMessage;
import kercar.raspberry.arduino.message.Stop;
import kercar.raspberry.arduino.message.TurnLeft;
import kercar.raspberry.arduino.message.TurnRight;

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
			Core.Log("MessageHandler : CMD_TURN");
			this.ia.stopMission();
			this.ia.stopKercar();
		}
		else if (message.getType() == Message.CMD_MISSION) {
			Core.Log("MessageHandler : CMD_MISSION");
			this.ia.stopMission();
			this.ia.stopKercar();
			CMDMissionMessage mission = new CMDMissionMessage((Message)message);
			//TODO GET SPEED
			this.ia.launchMission(mission.getCoordinates(), mission.getMailAddress(), 50);
		}
		
	}
	
	public void handle(IArduinoMessage message)
	{
		
	}
}
