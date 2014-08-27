/**
 * 
 */
package ngat.tcm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.jdom.*;

import ngat.util.XmlConfigurable;

/**
 * @author eng
 * 
 */
public class BasicTelescopeSystem extends UnicastRemoteObject implements TelescopeSystem, XmlConfigurable {

	private PrimaryAxis azimuth;

	private PrimaryAxis altitude;

	private Rotator rotator;

	private Focus focus;

	private AuxilliaryMechanism mirrorCover;

	private AuxilliaryMechanism mirrorSupport;

	private Enclosure enclosure;

	private GuidanceSystem guidanceSystem;

	private SciencePayload sciencePayload;
	
	private BasicTelescopeControlSystem tcs;

	private TelescopeEnvironment env;
	
	private BasicTelescopeAlignmentAdjuster adjuster;
	
	/**
	 * @throws RemoteException
	 */
	public BasicTelescopeSystem() throws RemoteException {
		super();
	}

	/**
	 * @return the azimuth
	 */
	public PrimaryAxis getAzimuth() {
		return azimuth;
	}

	/**
	 * @param azimuth
	 *            the azimuth to set
	 */
	public void setAzimuth(PrimaryAxis azimuth) {
		this.azimuth = azimuth;
	}

	/**
	 * @return the altitude
	 */
	public PrimaryAxis getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude
	 *            the altitude to set
	 */
	public void setAltitude(PrimaryAxis altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the rotator
	 */
	public Rotator getRotator() {
		return rotator;
	}

	/**
	 * @param rotator
	 *            the rotator to set
	 */
	public void setRotator(Rotator rotator) {
		this.rotator = rotator;
	}

	/**
	 * @return the focus
	 */
	public Focus getFocus() {
		return focus;
	}

	/**
	 * @param focus
	 *            the focus to set
	 */
	public void setFocus(Focus focus) {
		this.focus = focus;
	}

	/**
	 * @return the mirrorCover
	 */
	public AuxilliaryMechanism getMirrorCover() {
		return mirrorCover;
	}

	/**
	 * @param mirrorCover
	 *            the mirrorCover to set
	 */
	public void setMirrorCover(AuxilliaryMechanism mirrorCover) {
		this.mirrorCover = mirrorCover;
	}

	/**
	 * @return the mirrorSupport
	 */
	public AuxilliaryMechanism getMirrorSupport() {
		return mirrorSupport;
	}

	/**
	 * @param mirrorSupport
	 *            the mirrorSupport to set
	 */
	public void setMirrorSupport(AuxilliaryMechanism mirrorSupport) {
		this.mirrorSupport = mirrorSupport;
	}

	/**
	 * @return the enclosure
	 */
	public Enclosure getEnclosure() {
		return enclosure;
	}

	/**
	 * @param enclosure
	 *            the enclosure to set
	 */
	public void setEnclosure(Enclosure enclosure) {
		this.enclosure = enclosure;
	}

	/**
	 * @return the guidanceSystem
	 */
	public GuidanceSystem getGuidanceSystem() {
		return guidanceSystem;
	}

	/**
	 * @param guidanceSystem
	 *            the guidanceSystem to set
	 */
	public void setGuidanceSystem(GuidanceSystem guidanceSystem) {
		this.guidanceSystem = guidanceSystem;
	}

	/**
	 * @return the sciencePayload
	 */
	public SciencePayload getSciencePayload() {
		return sciencePayload;
	}

	/**
	 * @param sciencePayload
	 *            the sciencePayload to set
	 */
	public void setSciencePayload(SciencePayload sciencePayload) {
		this.sciencePayload = sciencePayload;
	}

	
	
	
	
	// SOME HANDLY UPDATE METHODS TO TRANSFER TELEMETRY INTO THE STORED OBJECT
	// public void updateAzimuth(PrimaryAxisState azimuthState) {
	// azimuth.setAxisState(azimuthState);
	// }

	// private TelescopeEnvironment telescopeEnvironment;

	
	// environmental monitoring - eg ag, truss, oil temperatures etc.
	// TODO public TelescopeEnvironment get/setEnvironment() throws
	// RemoteException;


	/**
	 * Configure the registry from a DOM tree node.
	 * 
	 * @param root
	 *            the root element node.
	 * @see ngat.util.XmlConfigurable#configure(org.jdom.Element)
	 */
	public void configure(Element root) throws Exception {

		// azimuth
		azimuth = new PrimaryAxis("AZM");
		// Element azNode = root.getChild("azimuth");
		// azimuth.configure(azNode);

		// altitude;
		altitude = new PrimaryAxis("ALT");
		// Element altNode = root.getChild("altitude");
		// altitude.configure(altNode);
		// Extract max slew rate, low limit (=dome limit), hi limit (=zaz limit)

		// rotator;
		rotator = new Rotator();

		// focus;
		focus = new Focus("SMF");

		// mirrorCover;
		mirrorCover = new AuxilliaryMechanism("PMC");

		// mirrorSupport;
		mirrorSupport = new AuxilliaryMechanism("PMS");
		
		// enclosure;
		enclosure = new Enclosure();
		
		// guidanceSystem;
		Element guideNode = root.getChild("guidance");
		guidanceSystem = new GuidanceSystem();
		guidanceSystem.configure(guideNode);
		// TODO configure this

		// sciencePayload;

		Element sciNode = root.getChild("payload");
		sciencePayload = new SciencePayload();
		sciencePayload.configure(sciNode);
		
		// TCS
		tcs = new BasicTelescopeControlSystem();
		
		// ENV
		env = new TelescopeEnvironment();
		
		// ADJ
		Element adjNode = root.getChild("adjuster");
		adjuster = new BasicTelescopeAlignmentAdjuster();
		adjuster.configure(adjNode);
		
	}

	public BasicTelescopeControlSystem getTelescopeControlSystem() {
		return tcs;
	}
	
	/**
	 * @param tcs the tcs to set
	 */
	public void setTelescopeControlSystem(BasicTelescopeControlSystem tcs) {
		this.tcs = tcs;
	}

	
	/**
	 * @param tcs the tcs to set
	 */
	public void setTelescopeEnvironment(TelescopeEnvironment env) {
		this.env = env;
	}

	public TelescopeEnvironment getTelescopeEnvironment() {
		return env;
	}

	/**
	 * @return the adjuster
	 */
	public BasicTelescopeAlignmentAdjuster getAdjuster() {
		return adjuster;
	}

	/**
	 * @param adjuster the adjuster to set
	 */
	public void setAdjuster(BasicTelescopeAlignmentAdjuster adjuster) {
		this.adjuster = adjuster;
	}
	
	
	
	
}
