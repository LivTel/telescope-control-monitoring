package ngat.tcm;

import java.rmi.*;

public interface TelescopeResponseHandler extends Remote {

    public void telescopeOperationCompleted() throws RemoteException;

    public void telescopeOperationFailed(int errorCode, String errorMessage) throws RemoteException;

}
