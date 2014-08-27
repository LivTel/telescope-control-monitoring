/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Represents the overall telescope control system (TCS).
 * @author eng
 *
 */
public interface TelescopeControlSystem extends TelescopeStatus {

	/**
	 * @return Overall state of the telescope system.
	 */
	public int getTelescopeSystemState();
	
	/**
	 * @return State of the TCS.
	 */
	public int getTelescopeControlSystemState();
	
	/**
	 * @return State of the network control interface to the TCS.
	 */
	public int getTelescopeNetworkControlState();
	

	/**
	 * @return State of the engineering (manual) override to the telescope system.
	 */
	public int getTelescopeEngineeringControlState();
	
}
