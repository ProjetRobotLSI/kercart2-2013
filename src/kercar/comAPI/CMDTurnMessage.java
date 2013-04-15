package kercar.comAPI;

public class CMDTurnMessage extends Message implements ICMDTurnMessage {
	
	public static final int INDEX_ANGLE = 0;
	public static final int INDEX_RIGHT = 1;
	
	/**
	 * Création d'une commande de virage
	 * @param angle : Angle du virage
	 * @param right : Vrai pour un virage à droite, faux pour à gauche
	 */
	public CMDTurnMessage(int angle, boolean right) {
		super(Message.CMD_TURN);
		
		this.params.add(INDEX_ANGLE, Integer.toString(angle));
		this.params.add(INDEX_RIGHT, Boolean.toString(right));
	}
	
	public CMDTurnMessage(Message m){
		super(Message.CMD_TURN);
		this.params = m.getParams();
	}

	@Override
	public int getAngle() {
		return Integer.parseInt(this.params.get(INDEX_ANGLE));
	}

	@Override
	public void setAngle(int angle) {
		this.params.set(INDEX_ANGLE, Integer.toString(angle));
	}

	@Override
	public boolean isTurningRight() {
		return Boolean.parseBoolean(this.params.get(INDEX_RIGHT));
	}

	@Override
	public void turnRight() {
		this.params.set(INDEX_RIGHT, Boolean.toString(true));
	}

	@Override
	public void turnLeft() {
		this.params.set(INDEX_RIGHT, Boolean.toString(false));
	}

}
