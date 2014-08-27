package ngat.tcm;

import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

import org.jdom.*;

import ngat.message.RCS_TCS.TCS_Status;
import ngat.net.cil.CilService;
import ngat.tcm.beam.Beam;
import ngat.tcm.beam.BeamStatusCollator;
import ngat.net.cil.tcs.CilStatusCollator;
import ngat.net.cil.tcs.CollatorResponseListener;
import ngat.net.cil.tcs.TcsStatusPacket;
import ngat.util.XmlConfigurable;
import ngat.util.CommandTokenizer;
import ngat.util.ConfigurationProperties;
import ngat.util.XmlConfigurator;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;
import ngat.astrometry.*;

/**
 * An implementation of Telescope which uses the CIL service to collect most of
 * the status information.
 * 
 * @author eng
 * 
 */
public class BasicTelescope extends UnicastRemoteObject implements Telescope, TelescopeStatusProvider, XmlConfigurable,
		CollatorResponseListener {

	// String name;

	private transient CilService cil;

	private transient ISite site;

	private transient AstrometryCalculator astro;

	private BasicTelescopeSystem telescopeSystem;

	/** Collects mechanism status. */
	private transient CilStatusCollator mechCollator;

	/** Collects autoguider status. */
	private transient CilStatusCollator autoguiderCollator;

	/** Collects focal-stationstatus. */
	private transient CilStatusCollator focalStationCollator;

	/** Collects state status. */
	private transient CilStatusCollator stateCollator;

	/** Collects AG status. */
	private transient CilStatusCollator agCollator;

	/** Collects Limits status. */
	private transient CilStatusCollator limitsCollator;

    /** Collects src status.*/
    private transient CilStatusCollator sourceCollator;

	/** Collects Meteo status. */
	private transient CilStatusCollator meteoCollator;

	/** Collects Astro status. */
    private transient CilStatusCollator astroCollator;


    /** Collects Time status. */
    private transient CilStatusCollator timeCollator;



	private BeamStatusCollator beamStatusCollator;

	private AutoguiderStatusCollator agActiveStatusCollator;

	/** A list of TelescopeStatusUpdateListeners. */
	private List<TelescopeStatusUpdateListener> listeners;

	private transient LogGenerator logger;

	public BasicTelescope() throws RemoteException {
		super();

		TCS_Status.mapCodes();

		listeners = new Vector<TelescopeStatusUpdateListener>();

		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate().system("TCM").subSystem("Telescope").srcCompClass(getClass().getSimpleName())
				.srcCompId("Scope");
	}

	public BasicTelescope(ISite site, BasicTelescopeSystem telescopeSystem, CilService cil) throws RemoteException {
		this();
		this.site = site;
		this.telescopeSystem = telescopeSystem;
		this.cil = cil;

		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate().system("TCM").subSystem("Telescope").srcCompClass(getClass().getSimpleName())
				.srcCompId(site.getSiteName());
	}

	/**
	 * Handle an update to the AG active status.
	 * 
	 * @param hash
	 */
	public void updateAutoguiderActiveStatus(AutoguiderActiveStatus agActiveStatus) {

		logger.create().info().level(4).extractCallInfo().msg("Recieved Autoguider Active status packet").send();
		System.err.println("Recieved Autoguider Active status packet: " + agActiveStatus);
		try {
			notifyListeners(agActiveStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handle an update to the beam status.
	 * 
	 * @param hash
	 */
	public void updateBeamStatus(Hashtable hash) {

		logger.create().info().level(4).extractCallInfo().msg("Recieved beam status packet").send();

		try {
			Beam beam = telescopeSystem.getSciencePayload().getBeam();
			beam.updateBeamStatus(hash);
			notifyListenersBeamStatus(beam);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tcsStatusPacketFailure(String message) throws RemoteException {
		logger.create().info().level(4).extractCallInfo().msg("Recieved status packet failure message: " + message)
				.send();
		System.err.println("BT: CIl failure message received: " + message);
		// TODO do we need to signal this timestamp from CIL ?????
		notifyListenersCommsFailure(System.currentTimeMillis(), message);
	}

	/**
	 * Handle an update with a specific type of status segment
	 * 
	 * @param data
	 *            The status packet/segment.
	 */
	public void tcsStatusPacketUpdate(TcsStatusPacket.Segment data) {

		logger.create().info().level(4).extractCallInfo()
				.msg("Recieved status packet, class: " + data.getClass().getName()).send();

		if (data instanceof TcsStatusPacket.Mechanisms) {

			TcsStatusPacket.Mechanisms mechData = (TcsStatusPacket.Mechanisms) data;

			PrimaryAxisStatus azmStatus = new PrimaryAxisStatus();
			azmStatus.setMechanismName("AZM");
			azmStatus.setStatusTimeStamp(data.getTimeStamp());
			azmStatus.setCurrentPosition(((TcsStatusPacket.Mechanisms) data).azPos);
			azmStatus.setDemandPosition(((TcsStatusPacket.Mechanisms) data).azDemand);
			azmStatus.setMechanismState(((TcsStatusPacket.Mechanisms) data).azStatus);

			PrimaryAxisStatus altStatus = new PrimaryAxisStatus();
			altStatus.setMechanismName("ALT");
			altStatus.setStatusTimeStamp(data.getTimeStamp());
			altStatus.setCurrentPosition(((TcsStatusPacket.Mechanisms) data).altPos);
			altStatus.setDemandPosition(((TcsStatusPacket.Mechanisms) data).altDemand);
			altStatus.setMechanismState(((TcsStatusPacket.Mechanisms) data).altStatus);

			RotatorAxisStatus rotStatus = new RotatorAxisStatus();
			rotStatus.setMechanismName("ROT");
			rotStatus.setStatusTimeStamp(data.getTimeStamp());
			rotStatus.setCurrentPosition(((TcsStatusPacket.Mechanisms) data).rotPos);
			rotStatus.setDemandPosition(((TcsStatusPacket.Mechanisms) data).rotDemand);
			rotStatus.setMechanismState(((TcsStatusPacket.Mechanisms) data).rotStatus);
			rotStatus.setRotatorMode(((TcsStatusPacket.Mechanisms) data).rotMode);
			rotStatus.setSkyAngle(((TcsStatusPacket.Mechanisms) data).rotSkyAngle);

			FocusStatus focStatus = new FocusStatus();
			focStatus.setMechanismName("SMF");
			focStatus.setStatusTimeStamp(data.getTimeStamp());
			focStatus.setCurrentPosition(mechData.secMirrorPos);
			focStatus.setDemandPosition(mechData.secMirrorDemand);
			focStatus.setMechanismState(mechData.secMirrorStatus);
			focStatus.setFocusOffset(mechData.focusOffset);

			AuxilliaryMechanismStatus pmcStatus = new AuxilliaryMechanismStatus("PMC");
			pmcStatus.setStatusTimeStamp(data.getTimeStamp());
			pmcStatus.setCurrentPosition(mechData.primMirrorCoverPos);
			pmcStatus.setDemandPosition(mechData.primMirrorCoverDemand);
			pmcStatus.setMechanismState(mechData.primMirrorCoverStatus);

			// we dont have the ability to read PMS positions
			AuxilliaryMechanismStatus pmsStatus = new AuxilliaryMechanismStatus("PMS");
			pmsStatus.setStatusTimeStamp(data.getTimeStamp());
			pmsStatus.setCurrentPosition(TelescopeStatus.POSITION_UNKNOWN);
			pmsStatus.setDemandPosition(TelescopeStatus.POSITION_UNKNOWN);
			pmsStatus.setMechanismState(mechData.primMirrorSysStatus);

			AuxilliaryMechanismStatus enc1Status = new AuxilliaryMechanismStatus("EN1");
			enc1Status.setStatusTimeStamp(data.getTimeStamp());
			enc1Status.setCurrentPosition(mechData.encShutter1Pos);
			enc1Status.setDemandPosition(mechData.encShutter1Demand);
			enc1Status.setMechanismState(mechData.encShutter1Status);

			AuxilliaryMechanismStatus enc2Status = new AuxilliaryMechanismStatus("EN2");
			enc2Status.setStatusTimeStamp(data.getTimeStamp());
			enc2Status.setCurrentPosition(mechData.encShutter2Pos);
			enc2Status.setDemandPosition(mechData.encShutter2Demand);
			enc2Status.setMechanismState(mechData.encShutter2Status);

			telescopeSystem.getAzimuth().updateAxisStatus(azmStatus);
			telescopeSystem.getAltitude().updateAxisStatus(altStatus);
			telescopeSystem.getRotator().updateAxisStatus(rotStatus);
			telescopeSystem.getFocus().updateAxisStatus(focStatus);
			telescopeSystem.getMirrorCover().updateAxisStatus(pmcStatus);
			telescopeSystem.getMirrorSupport().updateAxisStatus(pmsStatus);
			telescopeSystem.getEnclosure().getShutter1().updateAxisStatus(enc1Status);
			telescopeSystem.getEnclosure().getShutter2().updateAxisStatus(enc2Status);

			notifyListeners(azmStatus);
			notifyListeners(altStatus);
			notifyListeners(rotStatus);
			notifyListeners(focStatus);
			notifyListeners(pmcStatus);
			notifyListeners(pmsStatus);
			notifyListeners(enc1Status);
			notifyListeners(enc2Status);

		} else if (data instanceof TcsStatusPacket.Limits) {
			// notifyListeners(data);

		} else if (data instanceof TcsStatusPacket.SourceBlock) {
		    // notifyListeners(data);                                                                                                        
		    
		    TcsStatusPacket.SourceBlock srcBlk = (TcsStatusPacket.SourceBlock)data;

		    SourceData srcData = new SourceData();
		    srcData.setStatusTimeStamp(data.getTimeStamp());
		    srcData.srcName = srcBlk.srcName;
		    srcData.srcRa = srcBlk.srcRa;
		    srcData.srcDec = srcBlk.srcDec;
		    srcData.srcEquinox = srcBlk.srcEquinox;
		    srcData.srcEpoch = srcBlk.srcEpoch;
		    srcData.srcPmRA = srcBlk.srcPmRA;
		    srcData.srcPmDec = srcBlk.srcPmDec;
		    srcData.srcNsTrackRA = srcBlk.srcNsTrackRA;
		    srcData.srcNsTrackDec = srcBlk.srcNsTrackDec;
		    srcData.srcParallax = srcBlk.srcParallax;
		    srcData.srcRadialVelocity = srcBlk.srcRadialVelocity;
		    srcData.srcActRa = srcBlk.srcActRa;
		    srcData.srcActDec = srcBlk.srcActDec;

		    notifyListeners(srcData);

		} else if (data instanceof TcsStatusPacket.Time) {

		    TcsStatusPacket.Time timeBlk = (TcsStatusPacket.Time) data;

		    TimeData timeData = new TimeData();
		    timeData.setStatusTimeStamp(data.getTimeStamp());
		    timeData.ut1 = timeBlk.ut1;
		    timeData.lst = timeBlk.lst;
		    timeData.mjd = timeBlk.mjd;

		    notifyListeners(timeData);

		} else if (data instanceof TcsStatusPacket.Astrometry) {
			
			TcsStatusPacket.Astrometry astro = (TcsStatusPacket.Astrometry) data;
			
			AstrometryData ad = new AstrometryData();
			
			ad.setStatusTimeStamp(data.getTimeStamp());
			ad.airmass = astro.airmass;
			ad.refractionHumidity = astro.refractionHumidity;
			ad.refractionTemperature = astro.refractionTemperature;
			ad.refractionWavelength = astro.refractionWavelength;
			ad.refractionPressure = astro.refractionPressure;
			
			ad.agwavelength = astro.agwavelength;
			
			notifyListeners(ad);
		    
		} else if (data instanceof TcsStatusPacket.State) {

			TcsStatusPacket.State stateData = (TcsStatusPacket.State) data;

			TelescopeControlSystemStatus tcsStatus = new TelescopeControlSystemStatus();
			tcsStatus.setStatusTimeStamp(data.getTimeStamp());
			tcsStatus.setTelescopeSystemState(stateData.telescopeState);
			tcsStatus.setTelescopeControlSystemState(stateData.tcsState);
			tcsStatus.setTelescopeNetworkControlState(stateData.networkControlState);
			tcsStatus.setTelescopeEngineeringControlState(stateData.engineeringOverrideState);

			telescopeSystem.getTelescopeControlSystem().updateStatus(tcsStatus);

			notifyListeners(tcsStatus);

		} else if (data instanceof TcsStatusPacket.Meteorology) {

			TcsStatusPacket.Meteorology meteoData = (TcsStatusPacket.Meteorology) data;

			TelescopeEnvironmentStatus envStatus = new TelescopeEnvironmentStatus();
			envStatus.setStatusTimeStamp(data.getTimeStamp());
			envStatus.setAgBoxTemperature(meteoData.agBoxTemperature);
			envStatus.setOilTemperature(meteoData.oilTemperature);
			envStatus.setSecondaryMirrorTemperature(meteoData.secMirrorTemperature);
			envStatus.setTrussTemperature(meteoData.serrurierTrussTemperature);
			envStatus.setPrimaryMirrorTemperature(meteoData.primMirrorTemperature);

			telescopeSystem.getTelescopeEnvironment().updateStatus(envStatus);

			notifyListeners(envStatus);

		} else if (data instanceof TcsStatusPacket.FocalStation) {

			TcsStatusPacket.FocalStation focalData = (TcsStatusPacket.FocalStation) data;

			String agName = focalData.ag;
			// telescopeSystem.getGuidanceSystem().getAutoguider(agName).setSelected(true);

			String focalName = focalData.station;

			String instName = focalData.instr;
			// notifyListeners(data);
		} else if (data instanceof TcsStatusPacket.Autoguider) {

			TcsStatusPacket.Autoguider agData = (TcsStatusPacket.Autoguider) data;

			// TODO note these are doubles for some reason they should be INTS
			AuxilliaryMechanismStatus agMirrorStatus = new AuxilliaryMechanismStatus("AGD");
			agMirrorStatus.setStatusTimeStamp(data.getTimeStamp());
			agMirrorStatus.setCurrentPosition((int) agData.agMirrorPos);
			agMirrorStatus.setDemandPosition((int) agData.agMirrorPos);
			agMirrorStatus.setMechanismState(agData.agMirrorStatus);

			AuxilliaryMechanismStatus agFilterStatus = new AuxilliaryMechanismStatus("AGI");
			agFilterStatus.setStatusTimeStamp(data.getTimeStamp());
			agFilterStatus.setCurrentPosition(agData.agFilterPos);
			agFilterStatus.setDemandPosition(agData.agFilterPos);
			agFilterStatus.setMechanismState(agData.agFilterStatus);

			FocusStatus agFocusStatus = new FocusStatus();
			agFocusStatus.setMechanismName("AGF");
			agFocusStatus.setStatusTimeStamp(data.getTimeStamp());
			agFocusStatus.setCurrentPosition(agData.agFocusPos);
			agFocusStatus.setDemandPosition(agData.agFocusDemand);
			agFocusStatus.setMechanismState(agData.agFocusStatus);

			AutoguiderStatus agStatus = new AutoguiderStatus();
			// agStatus.setMechanismName("AGG");
			agStatus.setAutoguiderName("AGG"); // TODO where do we get this info
												// from
			agStatus.setStatusTimeStamp(data.getTimeStamp());
			agStatus.setGuideState(agData.agStatus);
			agStatus.setSoftwareState(agData.agSwState);
			agStatus.setGuideMode(agData.agMode);
			agStatus.setGuideFwhm(agData.fwhm);
			agStatus.setGuideStarMagnitude(agData.guideStarMagnitude);

			telescopeSystem.getGuidanceSystem().getAutoguiderMirror().updateAxisStatus(agMirrorStatus);
			telescopeSystem.getGuidanceSystem().getAutoguiderFilter().updateAxisStatus(agFilterStatus);
			telescopeSystem.getGuidanceSystem().getAutoguiderFocus().updateAxisStatus(agFocusStatus);

			((BasicAutoguider) telescopeSystem.getGuidanceSystem().getDefaultAutoguider()).updateStatus(agStatus);

			notifyListeners(agFocusStatus);
			notifyListeners(agFilterStatus);
			notifyListeners(agMirrorStatus);
			notifyListeners(agStatus);
		}
	}

	public static void main(String args[]) {

		try {

			ConfigurationProperties cfg = CommandTokenizer.use("--").parse(args);

			// CilService cil = (CilService)
			// Naming.lookup("rmi://localhost:1099/TCSCilService");
			// System.err.println("BT::Located CIL Service: " + cil);

			String name = cfg.getProperty("name", "unknown");
			double latitude = Math.toRadians(cfg.getDoubleValue("latitude", 0.0));
			double longitude = Math.toRadians(cfg.getDoubleValue("longitude", 0.0));

			ISite site = new BasicSite(name, latitude, longitude);

			// TODO WE dont want to overload the existing CIL system yet
			CilService cil = (CilService) Naming.lookup("rmi://localhost/TCSCilService");
			System.err.println("BTel::OK found cil service: " + cil);

			BasicTelescope scope = new BasicTelescope();

			XmlConfigurator.use(new File("/occ/rcs/telescope.xml")).configure(scope);

			Naming.rebind("rmi://localhost:1099/Telescope", scope);
			System.err.println("BTel::OK Bound Telescope Controller: " + name);

			scope.startMonitoring(cil);

			// start beam status
			// start agactive status

			while (true) {
				System.err.print(".");
				try {
					Thread.sleep(60000L);
				} catch (InterruptedException ex) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Registers an instance of TelescopeStatusUpdateListener to receive
	 * notification when the telescope status changes.
	 * 
	 * @param listener
	 *            An instance of TelescopeStatusUpdateListener to register. If
	 *            listener is already registered this method returns silently.
	 * @throws RemoteException
	 *             If anything goes wrong. Telescope implementations may decide
	 *             to automatically de-register listeners which throw an
	 *             exeception on updating.
	 */
	public void addTelescopeStatusUpdateListener(TelescopeStatusUpdateListener listener) throws RemoteException {
		if (listeners.contains(listener))
			return;
		listeners.add(listener);

		logger.create().info().level(3).extractCallInfo().msg("Adding listener: " + listener).send();

	}

	/**
	 * De-registers an instance of TelescopeStatusUpdateListener from receiving
	 * notification when the telescope status changes.
	 * 
	 * @param listener
	 *            An instance of TelescopeStatusUpdateListener to de-register.
	 *            If listener is not-already registered this method returns
	 *            silently.
	 * @throws RemoteException
	 *             If anything goes wrong.
	 */
	public void removeTelescopeStatusUpdateListener(TelescopeStatusUpdateListener listener) throws RemoteException {
		if (!listeners.contains(listener))
			return;
		listeners.remove(listener);
		logger.create().info().level(4).extractCallInfo().msg("Removedlistener: " + listener).send();
	}

	/**
	 * Notify registered listeners that a status change has occcurred. Any
	 * listeners which fail to take the update are summarily removed from the
	 * listener list.
	 * 
	 * @param status
	 *            A status segment.
	 */
	public void notifyListeners(TelescopeStatus status) {

		// System.err.println("Notify listeners: "+status.getClass().getName().toUpperCase());
		TelescopeStatusUpdateListener tsul = null;
		Iterator ilist = listeners.iterator();
		while (ilist.hasNext()) {
			tsul = (TelescopeStatusUpdateListener) ilist.next();

			logger.create().info().level(4).extractCallInfo().msg("Notify listener: " + tsul).send();

			try {
				tsul.telescopeStatusUpdate(status);
			} catch (Exception e) {
				logger.create().info().level(1).extractCallInfo()
						.msg("Removing unresponsive listener: " + tsul + " due to: " + e).send();
				ilist.remove();
			}
		}

	}

	public void notifyListenersCommsFailure(long time, String message) {
		TelescopeStatusUpdateListener tsul = null;
		Iterator ilist = listeners.iterator();
		while (ilist.hasNext()) {
			tsul = (TelescopeStatusUpdateListener) ilist.next();

			logger.create().info().level(4).extractCallInfo().msg("Notify listener: " + tsul).send();

			System.err.println("BT: notifyListenersCommsFailure(): " + message);
			try {
				tsul.telescopeNetworkFailure(time, message);
			} catch (Exception e) {
				logger.create().info().level(1).extractCallInfo()
						.msg("Removing unresponsive listener: " + tsul + " due to: " + e).send();
				ilist.remove();
			}
		}

	}

	/**
	 * Notify registered listeners that a status change has occcurred. Any
	 * listeners which fail to take the update are summarily removed from the
	 * listener list.
	 * 
	 * @param status
	 *            A status segment.
	 */
	public void notifyListenersBeamStatus(Beam beam) {
		// loop over beam status listeners updating them
	}

	public TelescopeCalibrationProvider getTelescopeCalibrationProvider() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public TelescopeStatusProvider getTelescopeStatusProvider() throws RemoteException {
		return this;
	}
	
	
	public TelescopeSystem getTelescopeSystem() throws RemoteException {
		return telescopeSystem;
	}

	public ISite getSiteInfo() throws RemoteException {
		return site;
	}

	/**
	 * Configure the registry from a DOM tree node.
	 * 
	 * @param root
	 *            the root element node.
	 * @see ngat.util.XmlConfigurable#cpublic String toString() {
	    return "SRC " +srcName+onfigure(org.jdom.Element)
	 */
	public void configure(Element root) throws Exception {

		// SITE
		Element siteNode = root.getChild("site");
		String siteName = siteNode.getChildTextTrim("name");
		double lat = Math.toRadians(Double.parseDouble(siteNode.getChildTextTrim("latitude")));
		double lon = Math.toRadians(Double.parseDouble(siteNode.getChildTextTrim("longitude")));
		site = new BasicSite(siteName, lat, lon);

		// SYSTEM
		Element sysNode = root.getChild("system");
		telescopeSystem = new BasicTelescopeSystem();
		telescopeSystem.configure(sysNode);

	}

	/** Request a one-off update on all monitored streams. This method returns immediately, anything calling
	 * this method should allow time for the status updates to propagate back to where they are needed. */
	public void checkServices() {

		// Interrupt each service: this should force it to execute if its asleep, 
		// if its just done an update and is about to sleep it will have no effect 
		// which is ok as its just updated !
		
		mechCollator.interrupt();
		autoguiderCollator.interrupt();
		stateCollator.interrupt();
		meteoCollator.interrupt();
		focalStationCollator.interrupt();
		sourceCollator.interrupt();
		timeCollator.interrupt();
		astroCollator.interrupt();
		
	}

	/**
	 * Request telescope to start monitoring its various feeds. This call starts
	 * a seperate thread/threads and returns immediately.
	 */
	public void startMonitoring(CilService cil) {

		// try {
		// System.err.println("BT: StartMonitoring: Lookup local CIL service...");
		// cil = (CilService)Naming.lookup("rmi://localhost/TCSCilService");
		// logger.create().info().level(2).extractCallInfo()
		// .msg("Located CIL service: "+cil).send();
		System.err.println("BT: StartMonitoring: cil service; " + cil);
		// } catch (Exception e) {
		// System.err.println("BT: Failed to locate CIL service, stack trace follows...");
		// e.printStackTrace();
		// }

		// start MECH monitor
		mechCollator = new CilStatusCollator(this, "MECHANISMS", cil, 10000L);
		mechCollator.start();

		// start LIMIT monitor

		// start AG monitor
		autoguiderCollator = new CilStatusCollator(this, "AUTOGUIDER", cil, 10000L);
		autoguiderCollator.start();

		// start Focal station monitor
		focalStationCollator = new CilStatusCollator(this, "FOCAL_STATION", cil, 10000L);
		focalStationCollator.start();
	
		// start STATE monitor
		stateCollator = new CilStatusCollator(this, "STATE", cil, 10000L);
		stateCollator.start();

		// start METEO monitor (grabs various internal environment statii
		// such as truss temperature. It is not concerned with actual weather
		// data which is got from the EMS - it also uses a collator.
		// slow update (2 mins) we dont expect these env stats to change much
		meteoCollator = new CilStatusCollator(this, "METEOROLOGY", cil, 120000L);
		meteoCollator.start();

		sourceCollator = new CilStatusCollator(this, "SOURCE", cil, 10000L);
        sourceCollator.start();

		timeCollator = new CilStatusCollator(this, "TIME", cil, 10000L);
        timeCollator.start();

        astroCollator = new CilStatusCollator(this, "ASTROMETRY", cil, 10000L);
        astroCollator.start();
                
		// start AG temperature monitor
		// TODO MAYBE
		// telescopeSystem.getGuidanceSystem().getAutoguider("AAA").startTempMonitor(5000);

		// start SFD PLC monitor
		// telescopeSystem.getSciencePayload().getScienceFold().monitor(2000);

	}

	public void startMonitoringAutoguiderState(BasicAutoguider autoguider) throws Exception {
		startMonitoringAutoguiderState(autoguider.getAutoguiderName(), autoguider.getAgActiveHost(),
				autoguider.getAgActivePort());
	}

	public void startMonitoringAutoguiderState(String autoguiderName, String agStatusHost, int agStatusPort) {
		agActiveStatusCollator = new AutoguiderStatusCollator(this, autoguiderName, agStatusHost, agStatusPort, 60000L);
		agActiveStatusCollator.start();
	}

	/**
	 * Request telescope to start monitoring beam status. This call starts a
	 * seperate thread/threads and returns immediately.
	 */
	public void startMonitoringBeamStatus(String bssHost, int bssPort) {
		beamStatusCollator = new BeamStatusCollator(this, bssHost, bssPort, 30000L);
		beamStatusCollator.start();
	}

	public TelescopeController getTelescopeController() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
