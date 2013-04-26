package kercar.comAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Message donnant les informations sur le robot
 * Du Raspberry vers l'Android
 * @author itooh
 */
public class StateMessage extends Message implements IStateMessage {

	public static final int INDEX_POSITION = 0;
	public static final int INDEX_ORIENTATION = 1;
	public static final int INDEX_TELEMETRE = 2;
	public static final int INDEX_BATTERY = 3;
	
	public StateMessage(List<Integer> position, int orientation, int telemetre, int battery) {
		super(Message.STATE);
		
		this.setPosition(position);
		this.params.add(INDEX_ORIENTATION, Integer.toString(orientation));
		this.params.add(INDEX_TELEMETRE, Integer.toString(telemetre));
		this.params.add(INDEX_BATTERY, Integer.toString(battery));
	}
	
	public StateMessage(Message m) {
		super(Message.STATE);
		this.params = m.getParams();
	}

	@Override
	public List<Integer> getPosition() {
		String[] posStr = this.params.get(INDEX_POSITION).split(".");
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (String pos : posStr)
			positions.add(Integer.parseInt(pos));
		return positions;
	}

	@Override
	public void setPosition(List<Integer> position) {
		String posStr = "";
		Iterator<Integer> i = position.iterator();
		while (i.hasNext()) {
			posStr += i.next();
			if (i.hasNext()) posStr += ".";
		}
		this.params.add(INDEX_POSITION, posStr);
	}

	@Override
	public int getOrientation() {
		return Integer.parseInt(this.params.get(INDEX_ORIENTATION));
	}

	@Override
	public void setOrientation(int orientation) {
		this.params.set(INDEX_ORIENTATION, Integer.toString(orientation));
	}

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

}
