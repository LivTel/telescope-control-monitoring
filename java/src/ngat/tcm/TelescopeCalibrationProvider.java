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
public interface TelescopeCalibrationProvider extends Remote {
   
	/**
	 * @return Calibration requirements for the telescope.
	 * @throws RemoteException
	 */
	public TelescopeCalibration getCalibrationRequirements() throws RemoteException;

    /**
     * @return Calibration history for the telescope.
     * @throws RemoteException
     */
    public TelescopeCalibrationHistory getCalibrationHistory() throws RemoteException;


}
