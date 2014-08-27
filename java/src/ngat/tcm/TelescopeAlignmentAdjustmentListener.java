package ngat.tcm;

import java.rmi.*;

public interface TelescopeAlignmentAdjustmentListener extends Remote {

	public void adjustmentsEnabled(long time, boolean enabled, String info) throws RemoteException;
	
    public void adjustmentsStarting(long time, String info) throws RemoteException;

    public void adjustmentsStopping(long time) throws RemoteException;

    public void adjustmentMade(long time, double rot, double x, double y)  throws RemoteException;

    public void adjustmentFailed(long time, String message) throws RemoteException;

}