package ngat.tcm;

import java.io.Serializable;
import java.rmi.RemoteException;

import ngat.net.telemetry.UnknownStatusItemException;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

public class PrimaryAxis implements PrimaryAxisController, AxisCapabilities, ContinuousMechanism, Serializable {

	private long statusTimeStamp;
	
	private double lowLimit;
	
	private double highLimit;
	
	private double slewRate;
	
	private double currentPosition;
	
	private double demandPosition;
	
	private int mechanismState;

	private String mechanismName;

	private transient LogGenerator logger;
	
	/**
	 * @param mechanismName
	 */
	public PrimaryAxis(String name) {	
		this.mechanismName = name;	
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate()
		.system("TCM")
		.subSystem("Mechanisms")
		.srcCompClass(getClass().getSimpleName())
		.srcCompId(name);
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
	 * @return the mechanismName
	 */
	public String getMechanismName() {
		return mechanismName;
	}

	/**
	 * @param mechanismName the mechanismName to set
	 */
	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}

	
	/**
	 * @return the lowLimit
	 */
	public double getLowLimit() {
		return lowLimit;
	}

	/**
	 * @param lowLimit the lowLimit to set
	 */
	public void setLowLimit(double lowLimit) {
		this.lowLimit = lowLimit;
	}

	/**
	 * @return the highLimit
	 */
	public double getHighLimit() {
		return highLimit;
	}

	/**
	 * @param highLimit the highLimit to set
	 */
	public void setHighLimit(double highLimit) {
		this.highLimit = highLimit;
	}

	/**
	 * @return the slewRate
	 */
	public double getSlewRate() {
		return slewRate;
	}

	/**
	 * @param slewRate the slewRate to set
	 */
	public void setSlewRate(double slewRate) {
		this.slewRate = slewRate;
	}

	/**
	 * @return the currentPosition
	 */
	public double getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(double currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * @return the demandPosition
	 */
	public double getDemandPosition() {
		return demandPosition;
	}

	/**
	 * @param demandPosition the demandPosition to set
	 */
	public void setDemandPosition(double demandPosition) {
		this.demandPosition = demandPosition;
	}

	/**
	 * @return the mechanismState
	 */
	public int getMechanismState() {
		return mechanismState;
	}

	/**
	 * @param mechanismState the mechanismState to set
	 */
	public void setMechanismState(int mechanismState) {
		this.mechanismState = mechanismState;
	}
	
	//public AxisStatus getAxisStatus();
	 
	 /**
	 * @param status
	 */
	public void updateAxisStatus(PrimaryAxisStatus status) {
		
		logger.create().info().level(3).extractCallInfo()
			.msg("Received axis status update: "+status).send();
		
		statusTimeStamp = status.getStatusTimeStamp();
		currentPosition = status.getCurrentPosition();
		demandPosition = status.getDemandPosition();
		mechanismState = status.getMechanismState();
		
	 }

	public void go(double position) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void track(boolean on) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void stop() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getCategoryName() {
		return mechanismName;
	}

	 
}
