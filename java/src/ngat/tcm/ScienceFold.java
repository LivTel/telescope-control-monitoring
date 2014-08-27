/**
 * 
 */
package ngat.tcm;

import java.rmi.RemoteException;

import ngat.eip.EIPHandle;
import ngat.eip.EIPPLC;
import ngat.net.cil.CilService;
import ngat.util.logging.*;

/** This is an implementation of science fold using EIP to control the deployment.
 * The port rotation is not supported and is done via an external controller.
 * @author eng
 * 
 */
public class ScienceFold extends AuxilliaryMechanism {

	// TODO may need to split sci-fold into 3 or more submechs
	// scifold-deploy, scifold-port, scifold-dichroic

	private transient EIPPLC plc;

	private transient LogGenerator logger;

	private transient CilService cil;
	
	/**
	 * @param name
	 */
	public ScienceFold() {
		super("SCI");
		// TODO Auto-generated constructor stub
	}

	protected static final int PLC_TYPE = EIPPLC.PLC_TYPE_MICROLOGIX1100;

	protected static final String HOSTNAME = "ltlampplc";
	/**
	 * The backplane containing the PLC. Part of the Ethernet/IP addressing.
	 */
	protected static final int BACKPLANE = 1;
	/**
	 * The slot containing the PLC. Part of the Ethernet/IP addressing.
	 */
	protected static final int SLOT = 0;

	protected static final String PLC_CONTROL_ADDRESS = "N55:1/0";
	/**
	 * Control address for setting whether to stow/deploy.
	 */
	protected static final String DEPLOY_STATUS_ADDRESS = "N55:2/1";
	/**
	 * Control address for setting whether to stow/deploy.
	 */
	protected static final String STOW_STATUS_ADDRESS = "N55:2/0";

	protected static final int TIMEOUT_COUNT = 60;

	public static final int STOW_POSITION = 0; // or something more complex from
	// TCSTATSTSUS
	// ie public static final int TcsStatus.POSITION_STOWED = 351;

	public static final int DEPLOY_POSITION = 1; // or something more complex

	// from TCSTATSTSUS

	/** Goto a science-fold port position.*/
	public void go(int position) throws RemoteException {
		
		//prepare();
		
		// this just handles the rotation ie port number from the set
		// ScienceFold.PORT_1 = 1 ... etc
		
		// SomeHandler = new handler();
		
		//cil.sendMessage("MOVE_FOLD "+position, somehandler, 60000L);
		/** This command should not block so we need a reply handler to be monitored.*/
		
		// start a thread to monitor the handler and let us know if it failed
		// somehandler.wait(60000);
		// catch CilTimeoutException throw new TcmException("SFD", "go "+position, ctx);
	}
	
	public void stow() throws RemoteException {
		// go(STOW_POSITION);
		
		demandPosition = POSITION_STOWED;

		EIPHandle handle = null;
		boolean done, stowed, deployed;
		int timeoutIndex = 0;

		try {
			
			prepare();
			
			logger.create().info().level(3).msg("Requesting stow").send();

			handle = plc.createHandle(HOSTNAME, BACKPLANE, SLOT, PLC_TYPE);

			logger.create().info().level(3).msg("Opening handle").send();

			plc.open(handle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("", e);
		}
		
		try {
			// see if its already stowed
			stowed = plc.getBoolean(handle, STOW_STATUS_ADDRESS);
			deployed = plc.getBoolean(handle, DEPLOY_STATUS_ADDRESS);
			logger.create().info().level(4).msg(
					"Checked initial status flags: Stow: " + stowed + ", Deploy: " + deployed).send();
			if (stowed && (!deployed)) {
				logger.create().info().level(4).msg("Mechanism is already stowed, no action taken").send();
				currentPosition = POSITION_STOWED;
				return;
			}

			// set control demand
			logger.create().info().level(3).msg("Setting demand:Set " + PLC_CONTROL_ADDRESS + " to true.").send();

			plc.setBoolean(handle, PLC_CONTROL_ADDRESS, true);

			// see if its already stowed
			stowed = plc.getBoolean(handle, STOW_STATUS_ADDRESS);
			deployed = plc.getBoolean(handle, DEPLOY_STATUS_ADDRESS);
			logger.create().info().level(4).msg("Checked status flags: Stow: " + stowed + ", Deploy: " + deployed);

			done = false;

			// wait until science fold moved, or timeout
			timeoutIndex = 0;

			while (!done) {
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:Getting status:Get "+plcStowStatusAddress+".");
				stowed = plc.getBoolean(handle, STOW_STATUS_ADDRESS);
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:"+plcStowStatusAddress+" returned "+statusValue+".");
				if (stowed) {
					// fold is in position
					// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
					// ":stow:Science fold stowed.");
					done = true;
				} else {
					// check timeout and wait a bit
					timeoutIndex++;
					if (timeoutIndex > TIMEOUT_COUNT) {
						// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
						// ":stow:Science fold stow timed out.");
						throw new RemoteException(this.getClass().getName() + ":stow:Stow failed:timeout after "
								+ timeoutIndex + " seconds.");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ix) {
					}
				}
			}// end while
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("", e);
		} finally {
			// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
			// ":stow:Closing handle.");
			try {
				plc.close(handle);
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:Destroying handle.");
			} catch (Exception e) {
				e.printStackTrace();
				// not a real problem if it did stow
			}
			try {
				plc.destroyHandle(handle);
			} catch (Exception e) {
				e.printStackTrace();
				// not a real problem if it did stow
			}
		}
		// logger.log(Logging.VERBOSITY_INTERMEDIATE,this.getClass().getName()+":stow:Finished.");
	}

	public void deploy() throws RemoteException {
		// go(DEPLOY_POSITION);
	
		
		demandPosition = POSITION_INLINE;
		
		EIPHandle handle = null;
		boolean done, stowed, deployed;
		int timeoutIndex = 0;

		try {
			
			prepare();
			
			logger.create().info().level(3).msg("Requesting deploy").send();

			handle = plc.createHandle(HOSTNAME, BACKPLANE, SLOT, PLC_TYPE);

			logger.create().info().level(3).msg("Opening handle").send();

			plc.open(handle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("", e);
		}

		try {
			// see if its already deployed
			stowed = plc.getBoolean(handle, STOW_STATUS_ADDRESS);
			deployed = plc.getBoolean(handle, DEPLOY_STATUS_ADDRESS);
			logger.create().info().level(4).msg(
					"Checked initial status flags: Stow: " + stowed + ", Deploy: " + deployed).send();
			if ((!stowed) && deployed) {
				logger.create().info().level(4).msg("Mechanism is already deployed, no action taken").send();
				currentPosition = POSITION_INLINE; // TODO DUBIOUS !
				return;
			}

			// set control demand
			logger.create().info().level(3).msg("Setting demand:Set " + PLC_CONTROL_ADDRESS + " to false.").send();

			plc.setBoolean(handle, PLC_CONTROL_ADDRESS, false);

			// wait until science fold moved, or timeout
			timeoutIndex = 0;
			done = false;
			while (!done) {
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:Getting status:Get "+plcStowStatusAddress+".");
				deployed = plc.getBoolean(handle, DEPLOY_STATUS_ADDRESS);
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:"+plcStowStatusAddress+" returned "+statusValue+".");
				if (deployed) {
					// fold is in position
					// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
					// ":stow:Science fold stowed.");
					done = true;
				} else {
					// check timeout and wait a bit
					timeoutIndex++;
					if (timeoutIndex > TIMEOUT_COUNT) {
						// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
						// ":stow:Science fold stow timed out.");
						throw new RemoteException(this.getClass().getName() + "deploy:Deploy failed:timeout after "
								+ timeoutIndex + " seconds.");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ix) {
					}
				}
			}// end while
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("", e);
		} finally {
			// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
			// ":stow:Closing handle.");
			try {
				plc.close(handle);
				// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
				// ":stow:Destroying handle.");
			} catch (Exception e) {
				e.printStackTrace();
				// not a real problem if it did stow
			}
			try {
				plc.destroyHandle(handle);
			} catch (Exception e) {
				e.printStackTrace();
				// not a real problem if it did stow
			}
		}
		// logger.log(Logging.VERBOSITY_INTERMEDIATE,this.getClass().getName()+":stow:Finished.");

	}

/*	*//**
	 * TEMP method to allow an external client to keep polling for status.
	 *//*
	public void monitor(long interval) {

		// awkward initialization
		TcsStatusPacket.mapCodes();

		currentPosition = POSITION_UNKNOWN; // unless we know otherwise
		EIPHandle handle = null;

		try {
			logger.create().info().level(3).msg("Create handle...").send();
			handle = plc.createHandle(HOSTNAME, BACKPLANE, SLOT, PLC_TYPE);
			logger.create().info().level(3).msg("Opening handle...").send();
			plc.open(handle);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		for (int i = 0; i < 25; i++) {

			try {
				boolean stowed = plc.getBoolean(handle, STOW_STATUS_ADDRESS);
				boolean deployed = plc.getBoolean(handle, DEPLOY_STATUS_ADDRESS);

				if (stowed && (!deployed))
					currentPosition = POSITION_STOWED;
				else if (deployed && (!stowed))
					currentPosition = POSITION_INLINE;
				else
					currentPosition = POSITION_UNKNOWN;

				System.err.println("Stow status: " + stowed + ", Deploy status: " + deployed + " CP="
						+ TcsStatusPacket.codeString(currentPosition));

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException ix) {
			}
		}

		try {
			plc.close(handle);
			// logger.log(Logging.VERBOSITY_VERY_VERBOSE,this.getClass().getName()+
			// ":stow:Destroying handle.");
		} catch (Exception e) {
			e.printStackTrace();
			// not a real problem if it did stow
		}
		try {
			plc.destroyHandle(handle);
		} catch (Exception e) {
			e.printStackTrace();
			// not a real problem
		}
	}
*/
	
	private void prepare() throws Exception {
		plc = new EIPPLC();
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate().system("TCM")
			.subSystem("SciencePayload")
			.srcCompClass("ScienceFold")
			.srcCompId("SFD");
	}
	
	
}
