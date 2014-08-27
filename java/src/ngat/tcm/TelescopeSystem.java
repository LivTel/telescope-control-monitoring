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
public interface TelescopeSystem extends Remote{

	//public int getSystemState() 
	
	public PrimaryAxis getAzimuth() throws RemoteException;
	
	public PrimaryAxis getAltitude() throws RemoteException;
	
	public Rotator getRotator() throws RemoteException;
	
	public Focus getFocus() throws RemoteException;
	
	public AuxilliaryMechanism getMirrorCover() throws RemoteException;
	
	public AuxilliaryMechanism getMirrorSupport() throws RemoteException;
	
	public Enclosure getEnclosure() throws RemoteException;
	
	public GuidanceSystem getGuidanceSystem() throws RemoteException;
	
	public SciencePayload getSciencePayload() throws RemoteException;
	
	public TelescopeControlSystem getTelescopeControlSystem() throws RemoteException;
	
	// environmental monitoring - eg ag, truss, oil temperatures etc.
	public TelescopeEnvironment getTelescopeEnvironment() throws RemoteException;
}
