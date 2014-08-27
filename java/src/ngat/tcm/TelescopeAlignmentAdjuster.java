package ngat.tcm;

import ngat.phase2.IMosaicOffset;

import java.rmi.*;

public interface TelescopeAlignmentAdjuster extends Remote {

    public void startAdjustments(String info) throws RemoteException;

    public void stopAdjustments() throws RemoteException;

    /** Make a single adjustment for the supplied rotator position.*/
    public void makeAdjustment(double rot) throws RemoteException;

    public void enableAdjustmentsInterval(long interval) throws RemoteException;

    public void enableAdjustmentsTrigger() throws RemoteException;

    public void enableAdjustmentsSingle() throws RemoteException;

    public void disableAdjustments() throws RemoteException;

    public boolean adjustmentsEnabled() throws RemoteException;

    /** @return the position (tweak) offsets for a specified rotator position.*/
    public IMosaicOffset getOffsets(double rot) throws RemoteException;

}