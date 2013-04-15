package kercar.raspberry.core;

import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.Message;

public class MessageHandler {
	public void handle(IMessage message)
	{
		if (message.getType() == Message.CMD_MOVE)
		{
			CMDMoveMessage move = (CMDMoveMessage)message;
			System.out.println(move.getSpeed());
		}
		else if (message.getType() == Message.CMD_TURN)
		{
			CMDTurnMessage turn = (CMDTurnMessage)message;
			System.out.println(turn.getAngle());
		}
	}
}
