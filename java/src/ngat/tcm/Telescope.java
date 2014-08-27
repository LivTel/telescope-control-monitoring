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
public interface Telescope extends Remote, SiteInfoProvider {

	public TelescopeSystem getTelescopeSystem() throws RemoteException;
	
	public TelescopeCalibrationProvider getTelescopeCalibrationProvider() throws RemoteException;
	
	public TelescopeStatusProvider getTelescopeStatusProvider() throws RemoteException;
	
	public TelescopeController getTelescopeController() throws RemoteException;
	
	// TODO add in the various monitoring things.. agmon, trkmon, pmcmon?
	// TODO ireg will want the same or similar
}
