package kercar.comAPI;

public interface ICMDTurnMessage {
	
	public int getAngle();
	public void setAngle(int angle);
	
	public boolean isTurningRight();
	public void turnRight();
	public void turnLeft();

}
