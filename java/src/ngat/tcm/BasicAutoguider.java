package ngat.tcm;

import java.rmi.RemoteException;

import ngat.phase2.IAutoguiderConfig;
import ngat.phase2.XAutoguiderConfig;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * @author eng
 * 
 */
public class BasicAutoguider implements Autoguider {

	/** Name of the autoguider. */
	private String autoguiderName;

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

	/** Temperature of the autoguider.*/
	private double autoguiderTemperature;
	
	private String agActiveHost;
	
	private int agActivePort;
	
	private LogGenerator logger;
	
	/**
	 * @param autoguiderName
	 */
	public BasicAutoguider(String autoguiderName) {
		super();
		this.autoguiderName = autoguiderName;
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate()
			.system("TCM")
			.subSystem("Guidance")
			.srcCompClass(getClass().getSimpleName())
			.srcCompId(autoguiderName);
	}

	/**
	 * @return the autoguiderName
	 */
	public String getAutoguiderName() throws RemoteException  {
		return autoguiderName;
	}

	/**
	 * @param autoguiderName
	 *            the autoguiderName to set
	 */
	public void setAutoguiderName(String autoguiderName) {
		this.autoguiderName = autoguiderName;
	}

	/**
	 * @return the guideState
	 */
	public int getGuideState() throws RemoteException {
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
	public int getSoftwareState() throws RemoteException  {
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
	public int getGuideMode() throws RemoteException  {
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
	public double getGuideStarMagnitude() throws RemoteException {
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
	public double getGuideFwhm() throws RemoteException {
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
	public double getAutoguiderTemperature() throws RemoteException {
		return autoguiderTemperature;
	}

	/**
	 * @param autoguiderTemperature the autoguiderTemperature to set
	 */
	public void setAutoguiderTemperature(double autoguiderTemperature) {
		this.autoguiderTemperature = autoguiderTemperature;
	}

	
	
	/**
	 * @return the agActiveHost
	 */
	public String getAgActiveHost() {
		return agActiveHost;
	}

	/**
	 * @param agActiveHost the agActiveHost to set
	 */
	public void setAgActiveHost(String agActiveHost) {
		this.agActiveHost = agActiveHost;
	}

	/**
	 * @return the agActivePort
	 */
	public int getAgActivePort() {
		return agActivePort;
	}

	/**
	 * @param agActivePort the agActivePort to set
	 */
	public void setAgActivePort(int agActivePort) {
		this.agActivePort = agActivePort;
	}

	/**
	 * @param status
	 */
	public void updateStatus(AutoguiderStatus status) {
	
		logger.create().info().level(3).extractCallInfo()
			.msg("Received autoguider status update: "+status).send();
		
		guideState = status.getGuideState();
		softwareState = status.getSoftwareState();
		guideMode = status.getGuideMode();
		guideStarMagnitude = status.getGuideStarMagnitude();
		guideFwhm = status.getGuideFwhm();
		autoguiderTemperature = status.getAutoguiderTemperature();
	}
	
}
