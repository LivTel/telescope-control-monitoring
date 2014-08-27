/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ngat.tcm.CalibrationMonitor;
import ngat.tcm.CalibrationUpdateListener;

/**
 * @author snf
 * 
 */
public class BasicCalibrationMonitor extends UnicastRemoteObject implements CalibrationMonitor {

	List listeners;

	/** Create a BasicCalibrationMonitor. */
	public BasicCalibrationMonitor() throws RemoteException {
		super();
		listeners = new Vector();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.tcm.CalibrationMonitor#addCalibrationUpdateListener(ngat.tcm.CalibrationUpdateListener)
	 */
	public void addCalibrationUpdateListener(CalibrationUpdateListener l) throws RemoteException {
		System.err.println("TCalMon.addCalibListener(): Check add "+l);
		if (listeners.contains(l))
			return;
		listeners.add(l);
		System.err.println("TCalMon.addCalibListener(): New listener: "+l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ngat.tcm.CalibrationMonitor#removeCalibrationUpdateListener(ngat.tcm.CalibrationUpdateListener)
	 */
	public void removeCalibrationUpdateListener(CalibrationUpdateListener l) throws RemoteException {
		System.err.println("TCalMon.removeCalibListener(): Check remove "+l);
		if (!(listeners.contains(l)))
			return;
		listeners.remove(l);
		System.err.println("TCalMon.addCalibListener(): Deleted listener: "+l);
	}

	public void notifyListenersStartingTelfocus(long time, double f1, double f2, double df, double snr, String instID, double vf, double alt, double temp) {

		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			CalibrationUpdateListener cl = (CalibrationUpdateListener) it.next();
			try {
				cl.startingTelfocusCalibration(time, f1, f2, df, snr, instID, vf, alt, temp);
			} catch (Exception e) {
				e.printStackTrace();
				it.remove();
				System.err.println("TCalMon:.StartingFocusRun():: Removing unresponsive listener: " + cl);
			}
		}
	}

	public void notifyListenersFocusUpdate(long time, double f, double fwhm) {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			CalibrationUpdateListener cl = (CalibrationUpdateListener) it.next();
			try {
				cl.telfocusDatapoint(time, f, fwhm);
			} catch (Exception e) {	
				e.printStackTrace();
				it.remove();
				System.err.println("TCalMon.FocusDatapoint()::Removing unresponsive listener: " + cl);
			}
		}
	}

	public void notifyListenersFocusCompleted(long time, double f0, double fwhm0, double a, double b, double  c, double  chi2) {
			Iterator it = listeners.iterator();
			while (it.hasNext()) {
				CalibrationUpdateListener cl = (CalibrationUpdateListener) it.next();
				try {
					cl.telfocusCompleted(time, f0, fwhm0, a, b, c, chi2);
				} catch (Exception e) {
					e.printStackTrace();
					it.remove();
					System.err.println("TCalMon.FocusCompleted()::Removing unresponsive listener: " + cl);
				}
			}
		}

}
