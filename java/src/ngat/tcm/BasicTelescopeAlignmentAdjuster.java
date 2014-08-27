package ngat.tcm;

import java.rmi.*;
import java.rmi.server.*;
import org.jdom.*;
import ngat.util.XmlConfigurable;
import ngat.util.XmlConfigurator;
import ngat.phase2.XPositionOffset;
import ngat.phase2.IMosaicOffset;
import ngat.net.cil.test.TestHandler;
import ngat.net.cil.CilService;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import ngat.util.CommandTokenizer;

public class BasicTelescopeAlignmentAdjuster extends UnicastRemoteObject implements TelescopeAlignmentAdjuster,
		TelescopeAlignmentMonitor, TelescopeStatusUpdateListener, XmlConfigurable {

	public static final int ADJUSTMENT_MODE_SINGLE = 1;

	public static final int ADJUSTMENT_MODE_INTERVAL = 2;

	public static final int ADJUSTMENT_MODE_TRIGGERED = 3;

    public static final double MINIMUM_ADJUSTMENT = 0.05;

	public static final String SHOW_MECH_MESSAGE = "SHOW MECHANISMS";

	public static final long SHOW_TIMEOUT = 10000L;

	public static final String TWEAK_COMMAND = "TWEAK";

	public static final long TWEAK_TIMEOUT = 10000L;

	private CilService cil;

	private Map<Double, XPositionOffset> tweaks;

	private List<TelescopeAlignmentAdjustmentListener> listeners;

	/** Track the rotator position. */
	private volatile double currentRotatorPosition;

	/** Track the latest rotator demand. */
	private volatile double currentRotatorDemand;

	private volatile boolean adjustmentsEnabled;

	private volatile boolean adjustmentInProgress;

	private long adjustmentInterval;

    private double minimumAdjustment = MINIMUM_ADJUSTMENT;

	private int adjustmentMode = ADJUSTMENT_MODE_SINGLE;

	public BasicTelescopeAlignmentAdjuster() throws RemoteException {

		tweaks = new HashMap<Double, XPositionOffset>();
		listeners = new Vector<TelescopeAlignmentAdjustmentListener>();

	}

	public void setCilService(CilService cil) {
		this.cil = cil;
	}

	public void addAlignmentAdjustmentListener(TelescopeAlignmentAdjustmentListener l) throws RemoteException {
		if (listeners.contains(l))
			return;
		listeners.add(l);
	
		// we could notify the new listener but watch threading here...
	}

	public void removeAlignmentAdjustmentListener(TelescopeAlignmentAdjustmentListener l) throws RemoteException {
		if (!listeners.contains(l))
			return;
		listeners.remove(l);
	}

	/**
	 * Handle an update of telescope status information.
	 * 
	 * @param status
	 *            The status to handle.rcs_lt.log
	 * @throws RemoteException
	 */
	public void telescopeStatusUpdate(TelescopeStatus status) throws RemoteException {
		// keep track of the rotator position
		if (status instanceof RotatorAxisStatus) {
			RotatorAxisStatus rotStatus = (RotatorAxisStatus) status;

			long time = rotStatus.getStatusTimeStamp();
			currentRotatorDemand = rotStatus.getDemandPosition();
			currentRotatorPosition = rotStatus.getCurrentPosition();

			System.err.println("TWK: Received status update: d=" + currentRotatorDemand + ", c="
					+ currentRotatorPosition);

			if (adjustmentInProgress && (adjustmentMode == ADJUSTMENT_MODE_TRIGGERED)) {

				// we need to make a single adjustment using the current demand
				SingleTweak stt = new SingleTweak(false);
				(new Thread(stt)).start();

			}

		}
	}

	/**
	 * Handle a notification of telescope comms failure.
	 * 
	 * @param messagercs_lt.log
	 *            An explanatory message.
	 * @throws RemoteException
	 */
	public void telescopeNetworkFailure(long timeStamp, String message) throws RemoteException {
		// ???
	}

	/**
	 * Start adjustments using the pre-configured mode unless disabled or an
	 * adjustment is already in progress.
	 */
	public void startAdjustments(String info) throws RemoteException {
		if (!adjustmentsEnabled)
			return;

		if (adjustmentInProgress)
			return;

		switch (adjustmentMode) {
		case ADJUSTMENT_MODE_SINGLE:
			adjustmentInProgress = true;
			SingleTweak stt = new SingleTweak(true);
			(new Thread(stt)).start();
			break;
		case ADJUSTMENT_MODE_INTERVAL:
			adjustmentInProgress = true;
			IntervalTweak it = new IntervalTweak();
			(new Thread(it)).start();
			break;
		case ADJUSTMENT_MODE_TRIGGERED:
			// this is handled by the updates arriving, we just need to let it
			// know
			adjustmentInProgress = true;
			break;
		}

		notifyListenersAdjustmentsStarting(System.currentTimeMillis(), info);

	}

	public void stopAdjustments() throws RemoteException {
		if (!adjustmentInProgress)
			return;
		adjustmentInProgress = false;
		notifyListenersAdjustmentsStopping(System.currentTimeMillis());
	}

	/** Make a single adjustment for the supplied rotator position. */
	public void makeAdjustment(double rot) throws RemoteException {

		// launch a thread which will do a single tweak and may also do a SHOW
		// MECH !

		SingleTweak stt = new SingleTweak(true);
		(new Thread(stt)).start();

	}

	public void enableAdjustmentsInterval(long interval) throws RemoteException {
		adjustmentsEnabled = true;
		adjustmentMode = ADJUSTMENT_MODE_INTERVAL;
		adjustmentInterval = interval;
		long time = System.currentTimeMillis();
		notifyListenersAdjustmentsEnabled(time, true, "Interval: "+adjustmentInterval);
	}

	public void enableAdjustmentsTrigger() throws RemoteException {
		adjustmentsEnabled = true;
		adjustmentMode = ADJUSTMENT_MODE_TRIGGERED;
		long time = System.currentTimeMillis();
		notifyListenersAdjustmentsEnabled(time, true, "Triggered");
	}

	public void enableAdjustmentsSingle() throws RemoteException {
		adjustmentsEnabled = true;
		adjustmentMode = ADJUSTMENT_MODE_SINGLE;
		long time = System.currentTimeMillis();
		notifyListenersAdjustmentsEnabled(time, true, "Single");
	}

	public void disableAdjustments() throws RemoteException {
		adjustmentsEnabled = false;
		long time = System.currentTimeMillis();
		notifyListenersAdjustmentsEnabled(time, false, "Disabled");
	}

	/** @return the position (tweak) offsets for a specified rotator position. */
	public IMosaicOffset getOffsets(double rot) throws RemoteException {
		return interpolate(rot);
	}

	/**
	 * Configure from a DOM tree node.
	 */
	public void configure(Element node) throws Exception {

		File offsetsFile = new File(node.getChildTextTrim("config"));

		configure(offsetsFile);

	}

	/** Load the offsets mapping. */
	public void configure(File file) throws Exception {

		// read lines: rot dx dy

		BufferedReader reader = new BufferedReader(new FileReader(file));

		int nl = 0;
		String line = null;
		while ((line = reader.readLine()) != null) {

			StringTokenizer st = new StringTokenizer(line);
			if (st.countTokens() < 3)
				throw new IllegalArgumentException("Offset line format: <rot> <dx> <dy> ");

			double rot = Double.parseDouble(st.nextToken());
			double dx = Double.parseDouble(st.nextToken());
			double dy = Double.parseDouble(st.nextToken());

			tweaks.put(new Double(rot), new XPositionOffset(false, dx, dy));

			nl++;
		}

		System.err.println("TWK: Configured tweak offset map with: " + nl + " entries");

	}

	/** Compute the accumulated correction at rot in degrees. */
	public XPositionOffset interpolate(double rot) {
		System.err.println("TWK: Interpolating offsets for rotator: "+rot);
		
		if (rot < -90.0 || rot > 90.0) {
			System.err.println("TWK: Unable to determine offsets, rotator is outside expected range");
			return new XPositionOffset(false, 0.0, 0.0);		
		}
		
		double rotlo = Math.floor(rot);
		double rothi = rotlo + 1.0;

		// x offset

		XPositionOffset lo = tweaks.get(new Double(rotlo));
		XPositionOffset hi = tweaks.get(new Double(rothi));

		double xlo = lo.getRAOffset();
		double xhi = hi.getRAOffset();

		double mx = (xhi - xlo) / (rothi - rotlo);
		double dx = mx * (rot - rotlo) + xlo;

		// y offset
		double ylo = lo.getDecOffset();
		double yhi = hi.getDecOffset();

		double my = (yhi - ylo) / (rothi - rotlo);
		double dy = my * (rot - rotlo) + ylo;

		return new XPositionOffset(false, dx, dy);

	}

	private void notifyListenersAdjustmentsStarting(long time, String info) {
		System.err.println("TWK: NotifyListeners Adjustments starting");
		Iterator<TelescopeAlignmentAdjustmentListener> il = listeners.iterator();
		while (il.hasNext()) {
			TelescopeAlignmentAdjustmentListener l = il.next();
			try {
				l.adjustmentsStarting(time, info);
			} catch (Exception e) {
				System.err.println("TWK: removing unresponsive listener: " + l);
				il.remove();
			}
		}

	}

	private void notifyListenersAdjustmentsStopping(long time) {
		System.err.println("TWK: NotifyListeners Adjustments stopping");
		Iterator<TelescopeAlignmentAdjustmentListener> il = listeners.iterator();
		while (il.hasNext()) {
			TelescopeAlignmentAdjustmentListener l = il.next();
			try {
				l.adjustmentsStopping(time);
			} catch (Exception e) {
				System.err.println("TWK: removing unresponsive listener: " + l);
				il.remove();
			}
		}

	}

	private void notifyListenersSingleTweak(long time, double rot, double dx, double dy) {
		System.err.println("TWK: NotifyListeners Single Tweak: " + rot + "  (" + dx + ", " + dy + ")");
		Iterator<TelescopeAlignmentAdjustmentListener> il = listeners.iterator();
		while (il.hasNext()) {
			TelescopeAlignmentAdjustmentListener l = il.next();
			try {
				l.adjustmentMade(time, rot, dx, dy);
			} catch (Exception e) {
				System.err.println("TWK: removing unresponsive listener: " + l);
				il.remove();
			}
		}

	}

	private void notifyListenersTweakFailed(long time, String message) {
		System.err.println("TWK: NotifyListeners Tweak Failed: " + message);
		Iterator<TelescopeAlignmentAdjustmentListener> il = listeners.iterator();
		while (il.hasNext()) {
			TelescopeAlignmentAdjustmentListener l = il.next();
			try {
				l.adjustmentFailed(time, message);
			} catch (Exception e) {
				System.err.println("TWK: removing unresponsive listener: " + l);
				il.remove();
			}
		}

	}
	
	
	private void notifyListenersAdjustmentsEnabled(long time, boolean enabled, String info) {
		System.err.println("TWK: NotifyListeners Adjustments "+(enabled ? "Enabled" : "Disabled"));
		Iterator<TelescopeAlignmentAdjustmentListener> il = listeners.iterator();
		while (il.hasNext()) {
			TelescopeAlignmentAdjustmentListener l = il.next();
			try {
				l.adjustmentsEnabled(time, enabled, info);
			} catch (Exception e) {
				System.err.println("TWK: removing unresponsive listener: " + l);
				il.remove();
			}
		}
	}

	public static void main(String args[]) {

		try {
			String host = args[0];

			CilService cil = (CilService) Naming.lookup("rmi://" + host + "/TCSCilService");

			BasicTelescopeAlignmentAdjuster bta = new BasicTelescopeAlignmentAdjuster();

			XmlConfigurator.use(new File("/occ/rcs/config/tweak.xml")).configure(bta);
			bta.setCilService(cil);

			TelescopeStatusProvider tel = (TelescopeStatusProvider) Naming.lookup("rmi://" + host + "/Telescope");
			tel.addTelescopeStatusUpdateListener(bta);

			Naming.rebind("rmi://localhost/TelescopeAlignmentAdjuster", bta);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// loop forever....
		while (true) {
			try {
				Thread.sleep(60000L);
			} catch (Exception e) {
			}
		}

	}

	private class SingleTweak implements Runnable {

		private boolean notify;

		private double rot;

		private SingleTweak(boolean notify) {
			this.notify = notify;
			this.rot = currentRotatorDemand;
		}

		public void run() {

			try {
				long time = System.currentTimeMillis();

				XPositionOffset tweakOffsets = interpolate(rot);
				double dx = tweakOffsets.getRAOffset();
				double dy = tweakOffsets.getDecOffset();

				TestHandler handler2 = new TestHandler();
				String tweakMessage = String.format("%s  %6.4f %6.4f", TWEAK_COMMAND, dx, dy);
				System.err.println("TWK: Sending: " + tweakMessage);

				cil.sendMessage(tweakMessage, handler2, TWEAK_TIMEOUT);
				handler2.waitNotification(TWEAK_TIMEOUT + 5000L);
				notifyListenersSingleTweak(time, rot, dx, dy);

				if (handler2.error) {
					System.err.println("TWK: TWEAK failed: " + handler2.errMsg);
					notifyListenersTweakFailed(time, handler2.errMsg);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// if we are to notify then we switch the adjustment flag off
			if (notify)
				adjustmentInProgress = false;
		}

	}

	private class IntervalTweak implements Runnable {

		double sumx = 0.0;
		double sumy = 0.0;

		public void run() {

			while (adjustmentInProgress) {
				try {
					long time = System.currentTimeMillis();

					double rot = currentRotatorDemand;

					// deterine accumulated offsets required
					XPositionOffset tweakOffsets = interpolate(rot);
					double accx = tweakOffsets.getRAOffset();
					double accy = tweakOffsets.getDecOffset();

					double dx = sumx - accx;
					double dy = sumy - accy;

					// check if the adjustment is enough to bother with...
					double adjustmentDistance = Math.sqrt(dx*dx + dy*dy);
					System.err.println("TWK: calculated adjustment distance: "+adjustmentDistance);

					if (adjustmentDistance > minimumAdjustment) {
					    
					    TestHandler handler2 = new TestHandler();
					    String tweakMessage = String.format("%s  %6.4f %6.4f", TWEAK_COMMAND, dx, dy);
					    System.err.println("TWK: Sending: " + tweakMessage);
					    
					    cil.sendMessage(tweakMessage, handler2, TWEAK_TIMEOUT);
					    handler2.waitNotification(TWEAK_TIMEOUT + 5000L);
					    notifyListenersSingleTweak(time, rot, dx, dy);
					    
					    if (handler2.error) {
						System.err.println("TWK: TWEAK failed: " + handler2.errMsg);
						notifyListenersTweakFailed(time, handler2.errMsg);
					    } else {
						// keep track of summed offsets
						sumx = accx;
						sumy = accy;
					    }
					    
					} else {
					    System.err.println("TWK: Adjustment distance is too small, no action");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {Thread.sleep(adjustmentInterval);} catch (Exception e) {}
				
			}

			System.err.println("TWK: Stopped interval tweaks");

		}

	}

	public boolean adjustmentsEnabled() throws RemoteException {
		return adjustmentsEnabled;
	}

}