package kercar.comAPI;

/**
 * Message donnant les informations sur le robot
 * Du Raspberry vers l'Android
 * @author itooh
 */
public class StateMessage extends Message implements IStateMessage {

	public static final int INDEX_LONGITUDE = 0;
	public static final int INDEX_LATITUDE = 1;
	public static final int INDEX_ORIENTATION = 2;
	//public static final int INDEX_TELEMETRE = 2;
	//public static final int INDEX_BATTERY = 3;
	
	public StateMessage(long longitude, long latitude, long orientation) {
		super(Message.STATE);
		
		this.params.add(INDEX_LONGITUDE, Long.toString(longitude));
		this.params.add(INDEX_LATITUDE, Long.toString(latitude));
		this.params.add(INDEX_ORIENTATION, Long.toString(orientation));
		//this.params.add(INDEX_TELEMETRE, Integer.toString(telemetre));
		//this.params.add(INDEX_BATTERY, Integer.toString(battery));
	}
	
	public StateMessage(Message m) {
		super(Message.STATE);
		this.params = m.getParams();
	}

	@Override
	public long getLongitude() {
		return Long.parseLong(this.params.get(INDEX_LONGITUDE));
	}

	@Override
	public void setLongitude(long longitude) {
		this.params.set(INDEX_LONGITUDE, Long.toString(longitude));
	}
	
	@Override
	public long getLatitude() {
		return Long.parseLong(this.params.get(INDEX_LATITUDE));
	}
	
	@Override
	public void setLatitude(long latitude) {
		this.params.set(INDEX_LATITUDE, Long.toString(latitude));
	}

	@Override
	public long getOrientation() {
		return Long.parseLong(this.params.get(INDEX_ORIENTATION));
	}

	@Override
	public void setOrientation(long orientation) {
		this.params.set(INDEX_ORIENTATION, Long.toString(orientation));
	}

	/*
	@Override
	public int getTelemetreInfos() {
		return Integer.parseInt(this.params.get(INDEX_TELEMETRE));
	}

	@Override
	public void setTelemetreInfos(int infos) {
		this.params.set(INDEX_TELEMETRE, Integer.toString(infos));
	}

	@Override
	public int getBatteryLevel() {
		return Integer.parseInt(this.params.get(INDEX_BATTERY));
	}

	@Override
	public void setBatteryLevel(int level) {
		this.params.set(INDEX_BATTERY, Integer.toString(level));
	}
	*/

}
