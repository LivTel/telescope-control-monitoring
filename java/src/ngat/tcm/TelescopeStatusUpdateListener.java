/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** A listener for status updates from telescope.
 * @author eng
 *
 */
public interface TelescopeStatusUpdateListener extends Remote {

	/** Handle an update of telescope status information.
	 * @param status The status to handle.
	 * @throws RemoteException
	 */
	public void telescopeStatusUpdate(TelescopeStatus status) throws RemoteException;
	
	
	/** Handle a notification of telescope comms failure.
	 * @param message An explanatory message.
	 * @throws RemoteException
	 */
	public void telescopeNetworkFailure(long timeStamp, String message) throws RemoteException;
	
}
