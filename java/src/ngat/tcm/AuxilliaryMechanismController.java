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
public interface AuxilliaryMechanismController extends MechanismController {

	/** Request mechanism to go to a position.
	 * @param position
	 * @throws RemoteException
	 */
	public void go(int position) throws RemoteException;
			
}
