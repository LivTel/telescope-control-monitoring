package ngat.tcm;
import java.io.Serializable;


/**
 * 
 */

/**
 * @author eng
 *
 */
public class TelescopeControlSystemStatus implements TelescopeStatus, Serializable {

	private long statusTimeStamp;
	
	private int telescopeSystemState;
	
	private int telescopeControlSystemState;
	
	private int telescopeNetworkControlState;
	
	private int telescopeEngineeringControlState;

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
	 * @return the telescopeSystemState
	 */
	public int getTelescopeSystemState() {
		return telescopeSystemState;
	}

	/**
	 * @param telescopeSystemState the telescopeSystemState to set
	 */
	public void setTelescopeSystemState(int telescopeSystemState) {
		this.telescopeSystemState = telescopeSystemState;
	}

	/**
	 * @return the telescopeControlSystemState
	 */
	public int getTelescopeControlSystemState() {
		return telescopeControlSystemState;
	}

	/**
	 * @param telescopeControlSystemState the telescopeControlSystemState to set
	 */
	public void setTelescopeControlSystemState(int telescopeControlSystemState) {
		this.telescopeControlSystemState = telescopeControlSystemState;
	}

	/**
	 * @return the telescopeNetworkControlState
	 */
	public int getTelescopeNetworkControlState() {
		return telescopeNetworkControlState;
	}

	/**
	 * @param telescopeNetworkControlState the telescopeNetworkControlState to set
	 */
	public void setTelescopeNetworkControlState(int telescopeNetworkControlState) {
		this.telescopeNetworkControlState = telescopeNetworkControlState;
	}

	/**
	 * @return the telescopeEngineeringControlState
	 */
	public int getTelescopeEngineeringControlState() {
		return telescopeEngineeringControlState;
	}

	/**
	 * @param telescopeEngineeringControlState the telescopeEngineeringControlState to set
	 */
	public void setTelescopeEngineeringControlState(int telescopeEngineeringControlState) {
		this.telescopeEngineeringControlState = telescopeEngineeringControlState;
	}

	public String toString() {
		return "SYS: "+telescopeSystemState+", tcs: "+telescopeControlSystemState+", net: "+telescopeNetworkControlState+", eng: "+telescopeEngineeringControlState;
	}

	public String getCategoryName() {
		return "TCS";
	}
	
}
