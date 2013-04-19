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
		System.out.println("Message received (Handler)");
		if (message.getType() == Message.CMD_MOVE)
		{
			Core.Log("MessageHandler : CMD_MOVE");
			CMDMoveMessage move = new CMDMoveMessage((Message)message);
			if(move.isBackward()){
				Core.Log("MessageHandler : BACKWARD");
				GoBackward arduinoMsg = new GoBackward();
				arduinoMsg.setVitesse(move.getSpeed());
				this.serialManager.write(arduinoMsg.toBytes());
			}
			else{
				System.out.println("Going forward (Handler)");
				Core.Log("MessageHandler : FORWARD");
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
				Core.Log("RIGHT");
				arduinoMsg = new TurnRight();
			}
			else {
				Core.Log("LEFT");
				arduinoMsg = new TurnLeft();
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
