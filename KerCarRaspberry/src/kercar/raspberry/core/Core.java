package kercar.raspberry.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import kercar.comAPI.IMessage;
import kercar.raspberry.arduino.SerialManager;

public class Core extends Thread {

	BlockingQueue<IMessage> messageQueue;
	SerialManager serialManager;
	
	public synchronized void messageReceived(IMessage message)
	{
		messageQueue.add(message);
	}
	
	
	public void run()
	{
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
}
