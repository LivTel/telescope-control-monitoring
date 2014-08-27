/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ngat.phase2.ITarget;

/** Any class wishing to privide telescope control facilities should implement this interface.
 * @author eng
 *
 */
public interface TelescopeController extends Remote {

	/** Request telescope to slew to specified target.
	 * @param target The target to slew to.
	 * @param handler A handler for the asynchronous completion response.
	 * @param timeout How long the caller is prepared to wait for a reply.
	 * @throws RemoteException If anything goes awry.
	 */
	public void slew(ITarget target, TelescopeResponseHandler handler, long timeout) throws RemoteException;
	
}
