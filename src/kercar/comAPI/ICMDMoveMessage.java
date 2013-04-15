package kercar.comAPI;

public interface ICMDMoveMessage {
	
	public int getSpeed();
	public void setSpeed(int speed);
	
	public boolean isBackward();
	public void setBackward(boolean backward);

}
