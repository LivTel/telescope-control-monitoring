package ngat.tcm.cil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ngat.net.cil.CilResponseHandler;
import ngat.tcm.*;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * A CIL response handler which wraps a TelescopeResponseHandler. Notifications
 * passed back from the CIL service are passed to the embedded response handler
 * for actioning.
 * 
 * @author snf
 * 
 */
public class CilScopeHandler extends UnicastRemoteObject implements CilResponseHandler {

	/** A wrapped response handler. */
	private TelescopeResponseHandler handler;

	/** Error logging. */
	protected LogGenerator logger;

	/**
	 * Create a CilScopeHandler to wrap the supplied TelescopeResponseHandler.
	 * 
	 * @param handler
	 *            The TelescopeResponseHandler to wrap.
	 */
	public CilScopeHandler(TelescopeResponseHandler handler) throws RemoteException {
		super();
		this.handler = handler;
		Logger alogger = LogManager.getLogger("CIL");
		logger = alogger.generate().system("COMMS")
					.subSystem("CIL")
					.srcCompClass(this.getClass().getName())
					.srcCompId("Handler");
	}

	public void completed(String message) throws RemoteException {	
		logger.create().info().level(3).block("completed")
			.msg("Received DONE message from CIL service: " + message).send();
		handler.telescopeOperationCompleted();
	}

	public void actioned() throws RemoteException {		
		logger.create().info().level(3).block("actioned")
			.msg("Received ACTIONED message from CIL service").send();
	}

	public void error(int code, String message) throws RemoteException {
		
		logger.create().info().level(3).block("error")
		.msg("Received ERROR message from CIL service: " + code + " : "+message).send();
		handler.telescopeOperationFailed(code, message);
	}

	public void timedout(String message) throws RemoteException {
		logger.create().info().level(3).block("completed")
			.msg("Received TIMEOUT message from CIL service: " + message).send();
		handler.telescopeOperationFailed(444, "CIL Service Timed out");
	}

}