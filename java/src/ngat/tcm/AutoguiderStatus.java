/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

import ngat.net.telemetry.UnknownStatusItemException;

/**
 * @author eng
 * 
 */
public class AutoguiderStatus implements TelescopeStatus {

	/** Name of this autoguider.*/
	private String autoguiderName;
	
	/** Timestamp for status information.*/
	private long statusTimeStamp;
	
	/** Current guide state. */
	private int guideState;

	/** Current state of guide software. */
	private int softwareState;

	/** Current guiding mode. */
	private int guideMode;

	/** Magnitude of current guide star. */
	private double guideStarMagnitude;

	/** FWHM of current guiding. */
	private double guideFwhm;

	/** Temperature of the autoguider (C). */
	private double autoguiderTemperature;

	
	
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
	 * @return the guideState
	 */
	public int getGuideState() {
		return guideState;
	}

	/**
	 * @param guideState
	 *            the guideState to set
	 */
	public void setGuideState(int guideState) {
		this.guideState = guideState;
	}

	/**
	 * @return the softwareState
	 */
	public int getSoftwareState() {
		return softwareState;
	}

	/**
	 * @param softwareState
	 *            the softwareState to set
	 */
	public void setSoftwareState(int softwareState) {
		this.softwareState = softwareState;
	}

	/**
	 * @return the guideMode
	 */
	public int getGuideMode() {
		return guideMode;
	}

	/**
	 * @param guideMode
	 *            the guideMode to set
	 */
	public void setGuideMode(int guideMode) {
		this.guideMode = guideMode;
	}

	/**
	 * @return the guideStarMagnitude
	 */
	public double getGuideStarMagnitude() {
		return guideStarMagnitude;
	}

	/**
	 * @param guideStarMagnitude
	 *            the guideStarMagnitude to set
	 */
	public void setGuideStarMagnitude(double guideStarMagnitude) {
		this.guideStarMagnitude = guideStarMagnitude;
	}

	/**
	 * @return the guideFwhm
	 */
	public double getGuideFwhm() {
		return guideFwhm;
	}

	/**
	 * @param guideFwhm
	 *            the guideFwhm to set
	 */
	public void setGuideFwhm(double guideFwhm) {
		this.guideFwhm = guideFwhm;
	}

	/**
	 * @return the autoguiderTemperature
	 */
	public double getAutoguiderTemperature() {
		return autoguiderTemperature;
	}

	/**
	 * @param autoguiderTemperature
	 *            the autoguiderTemperature to set
	 */
	public void setAutoguiderTemperature(double autoguiderTemperature) {
		this.autoguiderTemperature = autoguiderTemperature;
	}

	public String toString() {
		return "AGG "+autoguiderName+" state: " + guideState + ", s/w: " + softwareState + ", mode: " + guideMode + ", mag: "
				+ guideStarMagnitude + ", fwhm: " + guideFwhm + ", temp: " + autoguiderTemperature;
	}

	public String getCategoryName() {
		return autoguiderName;
	}



}
