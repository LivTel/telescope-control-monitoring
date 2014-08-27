/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class AuxilliaryMechanismStatus implements DiscreteMechanism, Serializable {

	private long statusTimeStamp;
	
	private int currentPosition;
	
	private int demandPosition;
	
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
	 * 
	 */
	public AuxilliaryMechanismStatus() {}

	/**
	 * @param mechanismName
	 */
	public AuxilliaryMechanismStatus(String mechanismName) {
		this();
		this.mechanismName = mechanismName;
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

	
	public String toString() {
			return mechanismName+", pos: "+currentPosition+", dmd: "+demandPosition+", state: "+mechanismState;
	}


	public String getCategoryName() {
		return mechanismName;
	}
	
	

}
