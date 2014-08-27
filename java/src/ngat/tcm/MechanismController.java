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
public interface MechanismController extends Remote {
	
	/** Request mechanism to stop.
	 * @throws RemoteException
	 */
	public void stop() throws RemoteException;
	
}
