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
public interface AutoguiderMonitorStateListener extends Remote {

	/** Notification that the autoguider monitor's enablement state was changed.
	 * @param enabled
	 * @throws RemoteException
	 */
	public void autoguiderMonitorEnabled(boolean enabled) throws RemoteException;
	
	/** Notification that the autoguider monitor was reset.
	 * @throws RemoteException
	 */
	public void autoguiderMonitorWasReset() throws RemoteException;
	
	
}
