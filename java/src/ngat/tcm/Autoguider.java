/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ngat.phase2.IAutoguiderConfig;

/**
 * @author eng
 *
 */
public interface Autoguider extends Remote {
	
	/**
	 * @return The name of the autoguider.
	 * @throws RemoteException
	 */
	public String getAutoguiderName() throws RemoteException;
	
	/**
	 * @return The current guide state.
	 * @throws RemoteException
	 */
	public int getGuideState() throws RemoteException;
	
	/**
	 * @return The guider software state.
	 * @throws RemoteException
	 */
	public int getSoftwareState() throws RemoteException;
	
	/**
	 * @return Guidance mode.
	 * @throws RemoteException
	 */
	public int getGuideMode() throws RemoteException;
	
	/**
	 * @return Magnitude of the current guide star.
	 * @throws RemoteException
	 */
	public double getGuideStarMagnitude() throws RemoteException;
	
	/**
	 * @return FHWM of current guide star.
	 * @throws RemoteException
	 */
	public double getGuideFwhm() throws RemoteException;
	
	/**
	 * @return The temperature of the autoguider.
	 * @throws RemoteException
	 */
	public double getAutoguiderTemperature() throws RemoteException;

}
