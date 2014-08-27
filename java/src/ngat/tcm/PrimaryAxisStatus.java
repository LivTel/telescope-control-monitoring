/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class PrimaryAxisStatus implements ContinuousMechanism, Serializable {

	private long statusTimeStamp;
	
	private double currentPosition;
	
	private double demandPosition;
	
	private int mechanismState;
	
	private String mechanismName;

	
	
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
	
	public String toString() {
			return mechanismName+", pos: "+currentPosition+", dmd: "+demandPosition+", state: "+mechanismState;
	}

	public String getCategoryName() {
		return mechanismName;
	}
}
