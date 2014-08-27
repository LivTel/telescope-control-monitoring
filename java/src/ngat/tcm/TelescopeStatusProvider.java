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
public interface TelescopeStatusProvider extends Remote {

	/**
	 * Add a TelescopeStatusUpdateListener to the list of registered listeners.
	 * If the listener is already registered, return silently,
	 * 
	 * @param l The listener to add.
	 * @throws RemoteException
	 */
	public void addTelescopeStatusUpdateListener(TelescopeStatusUpdateListener l) throws RemoteException;

	/**
	 * Remove a TelescopeStatusUpdateListener from the list of registered
	 * listeners. If the listener is not registered, return silently.
	 * 
	 * @param l The listener to remove.
	 * @throws RemoteException
	 */
	public void removeTelescopeStatusUpdateListener(TelescopeStatusUpdateListener l) throws RemoteException;

}
