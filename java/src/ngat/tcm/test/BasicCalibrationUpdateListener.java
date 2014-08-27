/**
 * 
 */
package ngat.tcm.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import ngat.astrometry.AstroFormatter;
import ngat.tcm.CalibrationUpdateListener;

/**
 * @author snf
 *
 */
public class BasicCalibrationUpdateListener extends UnicastRemoteObject implements CalibrationUpdateListener {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	static SimpleTimeZone UTC = new SimpleTimeZone(0,"UTC");
	
	static {
		sdf.setTimeZone(UTC);
	}
	
	/** Output data stream.*/
	private PrintStream pout;
	
	/** Output file.*/
	private File outfile;
	
	
	/** Create a BasicCalibrationUpdateListener.
	 * @param outfile The file to dump output to.
	 */
	public BasicCalibrationUpdateListener(File outfile) throws RemoteException {
		this.outfile = outfile;
		try {
		pout = new PrintStream(new FileOutputStream(outfile,true));
		} catch (Exception e) {
			throw new RemoteException("Unable to open calibration logstream",e);
		}
	}

	/* (non-Javadoc)
	 * @see ngat.tcm.CalibrationUpdateListener#startingTelfocusCalibration(long, double, double, double, double, java.lang.String, double, double, double)
	 */
	public void startingTelfocusCalibration(long time, double f1, double f2, double df, double snr, String instID, double vf, double alt, double temp)
			throws RemoteException {
		pout.println("Start TELFOCUS calibration: "+sdf.format(new Date(time)));
		pout.println("FocusMechanism start: "+f1);
		pout.println("FocusMechanism end:   "+f2);
		pout.println("FocusMechanism inc:   "+df);
		pout.println("Instrument:  "+instID);
		pout.println("Virtual:     "+vf);
		pout.println("Altitude:    "+String.format("%4.2f", alt));
		pout.println("Truss temp:  "+temp+"C");
		
	}

	/* (non-Javadoc)
	 * @see ngat.tcm.CalibrationUpdateListener#telfocusCompleted(long, double, double, double, double, double, double)
	 */
	public void telfocusCompleted(long time, double f0, double fwhm0, double a, double b, double c, double chi2)
			throws RemoteException {
	
		pout.println("Completed TELFOCUS: "+sdf.format(new Date(time)));
		pout.println("F0: "+f0);
		pout.println("FWHM: "+fwhm0);
		pout.println("Quadratic A: "+a);
		pout.println("Quadratic B: "+b);
		pout.println("Quadratic C: "+c);
		pout.println("Chi2: "+chi2);
		
	}

	/* (non-Javadoc)
	 * @see ngat.tcm.CalibrationUpdateListener#telfocusDatapoint(long, double, double)
	 */
	public void telfocusDatapoint(long time, double f, double fwhm) throws RemoteException {
		// TODO Auto-generated method stub
		pout.println("Datapoint: "+sdf.format(new Date(time))+" "+f+" "+fwhm);
		
	}
	
	/** CLose the logging stream/file.*/
	public void close() throws Exception {
		if (pout != null)
		pout.close();	
	}

}
