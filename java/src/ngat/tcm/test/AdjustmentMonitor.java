package ngat.tcm.test;

import ngat.tcm.*;
import java.rmi.*;
import java.rmi.server.*;

public class AdjustmentMonitor extends UnicastRemoteObject implements TelescopeAlignmentAdjustmentListener {

	public AdjustmentMonitor() throws RemoteException {

	}

	public static void main(String args[]) {

		try {
			String host = args[0];

			TelescopeAlignmentMonitor tam = (TelescopeAlignmentMonitor) Naming.lookup("rmi://" + host
					+ "/TelescopeAlignmentAdjuster");

			AdjustmentMonitor am = new AdjustmentMonitor();

			tam.addAlignmentAdjustmentListener(am);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adjustmentsStarting(long time, String info) throws RemoteException {
		System.err.printf("%tT Adjustment starting: %s \n", time, info);
	}

	public void adjustmentsStopping(long time) throws RemoteException {
		System.err.printf("%tT Adjustment stopping\n", time);
	}

	public void adjustmentMade(long time, double rot, double x, double y) throws RemoteException {
		System.err.printf("%tT Adjustment for %4.2f (%4.2f, %4.2f)\n", time, rot, x, y);
	}

	public void adjustmentFailed(long time, String message) throws RemoteException {
		System.err.printf("%tT Adjustment failed: %s\n", time, message);
	}

	public void adjustmentsEnabled(long time, boolean enabled, String info) throws RemoteException {
		System.err.printf("%tT Adjustments " + (enabled ? "Enabled" + info : "Disabled"));
	}

}