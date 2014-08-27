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
public interface PrimaryAxisController extends MechanismController {

	/** Request mechanism to go to a position.
	 * @param position
	 * @throws RemoteException
	 */
	public void go(double position) throws RemoteException;
	
	/** Request tracking to be switched on or off for mechanism.
	 * @param on
	 * @throws RemoteException
	 */
	public void track(boolean on) throws RemoteException;
	
}
