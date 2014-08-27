/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author eng
 *
 */
public interface TrackingMonitor extends Remote {
	
	/** Add an object as an tRACKINGStatusListener.
	 * @param Tsl The object to add to the list of statusListeners.
	 * @throws RemoteException
	 */
	public void addTrackingStatusListener(TrackingStatusListener tsl) throws RemoteException;
	
	/** Remove an object as an TrackingStatusListener.
	 * @param tsl The object to remove from the list of statusListeners.
	 * @throws RemoteException
	 */    
	public void removeTrackingStatusListener(TrackingStatusListener tsl) throws RemoteException;

	/** Force a tracking-lost trigger event.
	 * @throws RemoteException
	 */
	public void triggerTrackingLost() throws RemoteException;
	
	
}
