package kercar.comAPI;

import java.util.List;

/**
 * Interface du message d'état du robot
 * @author itooh
 */
public interface IStateMessage {
	
	/**
	 * @return Position GPS du robot
	 */
	public List<Integer> getPosition();
	public void setPosition(List<Integer> position);
	
	/**
	 * @return Orientation de la boussole
	 */
	public int getOrientation();
	public void setOrientation(int orientation);
	
	/**
	 * @return informations du Télémètre
	 */
	public int getTelemetreInfos();
	public void setTelemetreInfos(int infos);
	
	/**
	 * @return Niveau de la batterie
	 */
	public int getBatteryLevel();
	public void setBatteryLevel(int level);
	
}
