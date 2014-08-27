/**
 * 
 */
package ngat.tcm;

import ngat.net.telemetry.UnknownStatusItemException;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * @author eng
 * 
 */
public class BasicTelescopeControlSystem implements TelescopeControlSystem {

	private long statusTimeStamp;

	private int telescopeSystemState;

	private int telescopeControlSystemState;

	private int telescopeNetworkControlState;
	
	private int telescopeControlSystemCommsState;

	private int telescopeEngineeringControlState;

	private LogGenerator logger;

	/**
	 * 
	 */
	public BasicTelescopeControlSystem() {
		super();
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate().system("TCM").subSystem("System").srcCompClass(getClass().getSimpleName())
				.srcCompId("TelSys");
	}

	/**
	 * @return the telescope System State
	 */
	public int getTelescopeSystemState() {
		return telescopeSystemState;
	}

	/**
	 * @param telescopeSystemState
	 *            the telescope System State to set.
	 */
	public void setTelescopeSystemState(int telescopeSystemState) {
		this.telescopeSystemState = telescopeSystemState;
	}

	/**
	 * @return the telescope Control System State
	 */
	public int getTelescopeControlSystemState() {
		return telescopeControlSystemState;
	}

	/**
	 * @param telescopeControlSystemState
	 *            the telescope Control System State to set.
	 */
	public void setTelescopeControlSystemState(int telescopeControlSystemState) {
		this.telescopeControlSystemState = telescopeControlSystemState;
	}

	/**
	 * @return the telescope Network Control State
	 */
	public int getTelescopeNetworkControlState() {
		return telescopeNetworkControlState;
	}

	/**
	 * @param telescopeNetworkControlState
	 *            the telescope Network Control State to set.
	 */
	public void setTelescopeNetworkControlState(int telescopeNetworkControlState) {
		this.telescopeNetworkControlState = telescopeNetworkControlState;
	}

	
	
	
	/**
	 * @return the telescopeControlSystemCommsState
	 */
	public int getTelescopeControlSystemCommsState() {
		return telescopeControlSystemCommsState;
	}

	/**
	 * @param telescopeControlSystemCommsState the telescopeControlSystemCommsState to set
	 */
	public void setTelescopeControlSystemCommsState(int telescopeControlSystemCommsState) {
		this.telescopeControlSystemCommsState = telescopeControlSystemCommsState;
	}

	/**
	 * @return the telescope Engineering Control State.
	 */
	public int getTelescopeEngineeringControlState() {
		return telescopeEngineeringControlState;
	}

	/**
	 * @param telescopeEngineeringControlState
	 *            the telescope Engineering Control State to set.
	 */
	public void setTelescopeEngineeringControlState(int telescopeEngineeringControlState) {
		this.telescopeEngineeringControlState = telescopeEngineeringControlState;
	}

	/**
	 * @return the status Time Stamp
	 */
	public long getStatusTimeStamp() {
		return statusTimeStamp;
	}

	/**
	 * @param statusTimeStamp
	 *            the status Time Stamp to set
	 */
	public void setStatusTimeStamp(long statusTimeStamp) {
		this.statusTimeStamp = statusTimeStamp;
	}

	/**
	 * @param status
	 */
	public void updateStatus(TelescopeControlSystemStatus status) {

		logger.create().info().level(3).extractCallInfo()
			.msg("Received system status update: " + status).send();

		statusTimeStamp = status.getStatusTimeStamp();
		telescopeSystemState = status.getTelescopeSystemState();
		telescopeControlSystemState = status.getTelescopeControlSystemState();
		telescopeNetworkControlState = status.getTelescopeNetworkControlState();
		telescopeEngineeringControlState = status.getTelescopeEngineeringControlState();

	}

	public String getCategoryName() {
		return "TCS";
	}


}
