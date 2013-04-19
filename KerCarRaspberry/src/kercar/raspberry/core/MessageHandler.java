package kercar.raspberry.core;

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
	SerialManager serialManager;
	public MessageHandler(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public void handle(IMessage message)
	{
		if (message.getType() == Message.CMD_MOVE)
		{
			Core.Log("MessageHandler : CMD_MOVE");
			CMDMoveMessage move = new CMDMoveMessage((Message)message);
			if(move.isBackward()){
				Core.Log("MessageHandler : FORWARD");
				GoBackward arduinoMsg = new GoBackward();
				arduinoMsg.setVitesse(move.getSpeed());
				this.serialManager.write(arduinoMsg.toBytes());
			}
			else{
				Core.Log("MessageHandler : BACKWARD");
				GoForward arduinoMsg = new GoForward();
				arduinoMsg.setVitesse(move.getSpeed());
				this.serialManager.write(arduinoMsg.toBytes());
			}
		}
		else if (message.getType() == Message.CMD_TURN)
		{
			Core.Log("MessageHandler : CMD_TURN");
			CMDTurnMessage turn = new CMDTurnMessage((Message)message);
			IArduinoMessage arduinoMsg;
			if (turn.isTurningRight())
			{
				arduinoMsg = new TurnRight();
				((TurnRight)arduinoMsg).setDegree(turn.getAngle());
			}
			else {
				arduinoMsg = new TurnLeft();
				((TurnLeft)arduinoMsg).setDegree(turn.getAngle());
			}			
			this.serialManager.write(arduinoMsg.toBytes());
		}
		else if (message.getType() == Message.CMD_STOP)
		{				
			Core.Log("MessageHandler : CMD_TURN");
			IArduinoMessage arduinoMsg = new Stop();
			this.serialManager.write(arduinoMsg.toBytes());
		}
		
	}
}
