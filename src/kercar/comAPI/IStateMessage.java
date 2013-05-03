package kercar.comAPI;

/**
 * Interface du message d'état du robot
 * @author itooh
 */
public interface IStateMessage {
	
	/** @return Longitude GPS du robot */
	public long getLongitude();
	/** @return Latitude GPS du robot */
	public long getLatitude();
	public void setLongitude(long longitude);
	public void setLatitude(long latitude);
	
	/**
	 * @return Orientation de la boussole
	 */
	public long getOrientation();
	public void setOrientation(long orientation);
	
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
