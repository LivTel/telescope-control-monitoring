/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;
import java.rmi.RemoteException;

import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * @author eng
 *
 */
public class AuxilliaryMechanism implements AuxilliaryMechanismController, AxisCapabilities, DiscreteMechanism, Serializable {

	private long statusTimeStamp;
	
	protected double lowLimit;
	
	protected double highLimit;
	
	protected double slewRate;
	
	protected int currentPosition;
	
	protected int demandPosition;
	
	protected int mechanismState;

	private String mechanismName;
	
	private transient LogGenerator logger;
	
	/**
	 * @param mechanismName
	 */
	public AuxilliaryMechanism(String name) {
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
	public int getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * @return the demandPosition
	 */
	public int getDemandPosition() {
		return demandPosition;
	}

	/**
	 * @param demandPosition the demandPosition to set
	 */
	public void setDemandPosition(int demandPosition) {
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
	
	 /**
	 * @param status
	 */
	public void updateAxisStatus(AuxilliaryMechanismStatus status) {
	
		logger.create().info().level(2).extractCallInfo()
			.msg("Received mechanism status update: "+status).send();
			
		statusTimeStamp = status.getStatusTimeStamp();
		currentPosition = status.getCurrentPosition();
		demandPosition = status.getDemandPosition();
		mechanismState = status.getMechanismState();
		
	 }
	public void go(int position) throws RemoteException {
		// TODO actually implement this		
	}

	public void stop() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getCategoryName() {
		return mechanismName;
	}
	
	
}
