/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class AutoguiderActiveStatus implements TelescopeStatus, Serializable {

	public static final int AUTOGUIDER_TEMPERATURE_FAIL_LOW = 1;
	
	public static final int AUTOGUIDER_TEMPERATURE_FAIL_HIGH = 2;
	
	public static final int AUTOGUIDER_TEMPERATURE_WARN_LOW = 3;
	
	public static final int AUTOGUIDER_TEMPERATURE_WARN_HIGH = 4;
	
	public static final int AUTOGUIDER_TEMPERATURE_OKAY = 5;
	
	/** Name of this autoguider.*/
	private String autoguiderName;
	
	/** Timestamp for status information.*/
	private long statusTimeStamp;
	
	private boolean activeStatus;
	
	private int temperatureStatus;
	
	private double temperature;

	private boolean online;
	
	/**
	 * @return the autoguiderName
	 */
	public String getAutoguiderName() {
		return autoguiderName;
	}

	/**
	 * @param autoguiderName the autoguiderName to set
	 */
	public void setAutoguiderName(String autoguiderName) {
		this.autoguiderName = autoguiderName;
	}

	/**
	 * @return the statusTimeStamp
	 */
	public long getStatusTimeStamp() {
		return statusTimeStamp;
	}

	/**
	 * @param statusTimeStamp the statusTimeStamp to set
	 */
	public void setStatusTimeStamp(long statusTimeStamp) {
		this.statusTimeStamp = statusTimeStamp;
	}

	/**
	 * @return the activeStatus
	 */
	public boolean isActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * @return the temperatureStatus
	 */
	public int getTemperatureStatus() {
		return temperatureStatus;
	}

	/**
	 * @param temperatureStatus the temperatureStatus to set
	 */
	public void setTemperatureStatus(int temperatureStatus) {
		this.temperatureStatus = temperatureStatus;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	
	
	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	public String toString() {
		return "AGA "+autoguiderName+" active: " + activeStatus + 
				", Online: "+online +
				", Temp: " + temperature + 
				", Temp Status: "+temperatureStatus;
	}

	public String getCategoryName() {
		return autoguiderName;
	}
	
}
