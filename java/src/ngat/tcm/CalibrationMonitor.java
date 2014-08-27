package ngat.tcm;

import java.rmi.*;

/** Provides a registry for classes which wish to recieve notification of calibration updates. 
 * .
 * @author snf
 *
 */

public interface CalibrationMonitor extends Remote {


    /** Register a listener for calibration update notification.
     * @param l A listener to add to the list of registered calibration update listeners.
     */
    public void addCalibrationUpdateListener(CalibrationUpdateListener l) throws RemoteException;
    
    /** Remove a listener from the list of listeners registered for calibration update notification.
     * @param l A listener to remove from the list of registered calibration update listeners.
     */
    public void removeCalibrationUpdateListener(CalibrationUpdateListener l) throws RemoteException;

}
