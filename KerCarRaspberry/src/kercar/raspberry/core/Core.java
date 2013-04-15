package kercar.raspberry.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import kercar.comAPI.IMessage;

public class Core extends Thread {

	BlockingQueue<IMessage> messageQueue;
	
	public synchronized void messageReceived(IMessage message)
	{
		messageQueue.add(message);
	}
	
	
	public void run()
	{
		messageQueue = new LinkedBlockingDeque<IMessage>();
		MessageHandler handler = new MessageHandler();
		
		while(true)
		{
			if (!messageQueue.isEmpty())
				handler.handle(messageQueue.poll());
		}
	}
}
