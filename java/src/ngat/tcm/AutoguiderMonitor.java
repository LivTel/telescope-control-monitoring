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
public interface AutoguiderMonitor extends Remote {
	
	/** Add an object as an AutoguiderStatusListener.
	 * @param asl The object to add to the list of statusListeners.
	 * @throws RemoteException
	 */
	public void addGuideStatusListener(AutoguiderStatusListener asl) throws RemoteException;

	/** Remove an object as an AutoguiderStatusListener.
	 * @param asl The object to remove from the list of statusListeners.
	 * @throws RemoteException
	 */
	public void removeGuideStatusListener(AutoguiderStatusListener asl) throws RemoteException;

	/** Force a lock-lost trigger event.
	 * @throws RemoteException
	 */
	public void triggerLockLost() throws RemoteException;
	
	
	/** Add an object as an AutoguiderMonitorStateListener.
	 * @param agsl The object to add to the list of statusListeners.
	 * @throws RemoteException
	 */
	public void addAutoguiderMonitorStateListener(AutoguiderMonitorStateListener agsl) throws RemoteException;
	
	/** Remove an object as an AutoguiderMonitorStateListener.
	 * @param agsl The object to remove from the list of statusListeners.
	 * @throws RemoteException
	 */
	public void removeAutoguiderMonitorStateListener(AutoguiderMonitorStateListener agsl) throws RemoteException;
	
}
