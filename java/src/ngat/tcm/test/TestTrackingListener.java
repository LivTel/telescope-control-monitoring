/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ngat.tcm.TrackingMonitor;
import ngat.tcm.TrackingStatusListener;

/**
 * @author eng
 *
 */
public class TestTrackingListener extends UnicastRemoteObject implements TrackingStatusListener {

	protected TestTrackingListener() throws RemoteException {
		super();
	}

	/* (non-Javadoc)
	 * @see ngat.tcm.TrackingStatusListener#trackingLost()
	 */
	public void trackingLost() throws RemoteException {
		System.err.println("TTL::Tracking was lost");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			String host = args[0];
			
			TestTrackingListener tsl = new TestTrackingListener();
			
			TrackingMonitor mon = (TrackingMonitor)Naming.lookup("rmi://"+host+"/TrackingMonitor");
			System.err.println("Found tracking monitor: "+mon);
			
			mon.addTrackingStatusListener(tsl);
			
			// busy wait
			while (true) {try{Thread.sleep(60000L);} catch (InterruptedException ix) {}}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
