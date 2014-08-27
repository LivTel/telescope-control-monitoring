package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;


/** Classes which wish to receive notification of calibration events should
 * implement  CalibrationUpdateListener and provide mechanisms to handle the various
 * calibration events.
 */
public interface CalibrationUpdateListener extends Remote {

    
/**
 * Notification that a TELFOCUS run has started.
 * @param time When the TELFOCUS run started.
 * @param f1 FocusMechanism start position (mm),
 * @param f2 FocusMechanism stop position (mm).
 * @param df FocusMechanism step size (mm).
 * @param snr Required SNR.
 * @param instID ID of the instrument used for TELFOCUS operation.
 * @param vf current virtual focus position.
 * @param alt current altitude (rads).
 * @param temp current truss temperature (dC).
 */
    public void startingTelfocusCalibration(long time, double f1, double f2, double df, double snr, String instID, double vf, double alt, double temp) throws RemoteException;

    /**
     * Notification of a TELFOCUS data point.
     * @param time When the datapoint was generated.
     * @param f FocusMechanism value (mm).
     * @param fwhm FWHM at that focus (arcsec).
     */
    public void telfocusDatapoint(long time, double f, double fwhm) throws RemoteException;;
    /**
     *  Notification of results of a TELFOCUS run.
     * @param time When the run completed.
     * @param f0 The deduced optimal focus (mm).
     * @param fwhm0 The FWHM at the optimal focus (arcsec).
     * @param a Quadratic fit parameter <i>a</i> (x^2) term.
     * @param b Quadratic fit parameter <i>b</i> (x) term.
     * @param c Quadratic fit parameter <i>c</i> (const) term.
     * @param chi2 Chi<super>2</super> value.
     */
    public void telfocusCompleted(long time, double f0, double fwhm0, double a, double b, double c, double chi2) throws RemoteException;;
    
   
  
   
	    
}
