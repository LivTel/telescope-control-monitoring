package ngat.tcm;

import java.rmi.*;

public interface TelescopeAlignmentMonitor extends Remote {

    public void addAlignmentAdjustmentListener(TelescopeAlignmentAdjustmentListener l) throws RemoteException;

    public void removeAlignmentAdjustmentListener(TelescopeAlignmentAdjustmentListener l) throws RemoteException;

}