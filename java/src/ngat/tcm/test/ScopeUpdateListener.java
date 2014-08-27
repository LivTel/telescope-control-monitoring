/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ngat.tcm.Telescope;
import ngat.tcm.TelescopeStatus;
import ngat.tcm.TelescopeStatusProvider;
import ngat.tcm.TelescopeStatusUpdateListener;

/**
 * @author eng
 *
 */
public class ScopeUpdateListener extends UnicastRemoteObject implements TelescopeStatusUpdateListener {

	/**
	 * @throws RemoteException
	 */
	public ScopeUpdateListener() throws RemoteException {
		super();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			ScopeUpdateListener s = new ScopeUpdateListener();
			
			Telescope scope = (Telescope)Naming.lookup("rmi://ltsim1/Telescope");
			System.err.println("Found scope: "+scope);
			
			TelescopeStatusProvider tsp = scope.getTelescopeStatusProvider();
			System.err.println("Found TSP: "+tsp);
			
			tsp.addTelescopeStatusUpdateListener(s);
			System.err.println("Added self as listener");
			
			while (true) {try{Thread.sleep(60000L);} catch (InterruptedException x) {}}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	public void telescopeStatusUpdate(TelescopeStatus status) throws RemoteException {
		System.err.println("Recieved update: "+status);
	}

	public void telescopeNetworkFailure(long time, String message) throws RemoteException {
		System.err.println("Recieved notification of TCS comms failure: "+message);
	}

}
