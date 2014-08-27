/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ngat.astrometry.ISite;

/**
 * @author snf
 *
 */
public interface SiteInfoProvider extends Remote {

	/** 
	 * @return SiteInfomation details.
	 * @throws RemoteException
	 */
	public ISite getSiteInfo() throws RemoteException;
	
}
