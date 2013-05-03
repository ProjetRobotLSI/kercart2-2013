package kercar.comAPI;

/**
 * Interface du message d'état du robot
 * @author itooh
 */
public interface IStateMessage {
	
	/** @return Longitude GPS du robot */
	public int getLongitude();
	/** @return Latitude GPS du robot */
	public int getLatitude();
	public void setLongitude(int longitude);
	public void setLatitude(int latitude);
	
	/**
	 * @return Orientation de la boussole
	 */
	public int getOrientation();
	public void setOrientation(int orientation);
	
	/*
	 * @return informations du Télémètre
	 *
	public int getTelemetreInfos();
	public void setTelemetreInfos(int infos);
	*/
	/*
	 * @return Niveau de la batterie
	 *
	public int getBatteryLevel();
	public void setBatteryLevel(int level);
	*/
}
