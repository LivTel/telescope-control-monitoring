/**
 * 
 */
package ngat.tcm;

import java.io.File;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import org.jdom.*;

import ngat.icm.InstrumentDescriptor;
import ngat.tcm.beam.Beam;
import ngat.util.XmlConfigurable;

/**
 * Details the interaction between the telescope and instrument payload.
 * 
 * @author eng
 * 
 */
public class SciencePayload implements Serializable, XmlConfigurable {

	private ScienceFold scienceFold;
	
	//private Focus focus;
	
	//private autoguider ag;
	
	private Beam beam;
	
	/** Base offset for instrument rotator calculations.*/
	private double rotatorBaseOffset;

	/** Maps Instrument to science fold port. */
	private Map<InstrumentDescriptor, Integer> portMap;

	/** Maps Instrument to aperture number. */
	private Map<InstrumentDescriptor, Integer> apertureMap;

	/** Maps Instrument to file mount point. */
	private Map<InstrumentDescriptor, File> mountMap;

	/** Maps Instrument to reboot level. */
	private Map<InstrumentDescriptor, Integer> rebootMap;

	/** Maps Instrument to alias. */
	private Map<InstrumentDescriptor, String> aliasMap;

	public SciencePayload() {
		portMap = new HashMap<InstrumentDescriptor, Integer>();
		apertureMap = new HashMap<InstrumentDescriptor, Integer>();
		mountMap = new HashMap<InstrumentDescriptor, File>();
		rebootMap = new HashMap<InstrumentDescriptor, Integer>();
		aliasMap = new HashMap<InstrumentDescriptor, String>();
	}

	/**
	 * @return the scienceFold
	 */
	public ScienceFold getScienceFold() {
		return scienceFold;
	}

	/**
	 * @param scienceFold
	 *            the scienceFold to set
	 */
	public void setScienceFold(ScienceFold scienceFold) {
		this.scienceFold = scienceFold;
		// calls e.g scienceFold.getAuxMechanismState().getDemandPosition();
	}

	
	
	/**angrad
	 * @return the rotatorBaseOffset
	 */
	public double getRotatorBaseOffset() {
		return rotatorBaseOffset;
	}

	/**
	 * @param rotatorBaseOffset the rotatorBaseOffset to set
	 */
	public void setRotatorBaseOffset(double rotatorBaseOffset) {
		this.rotatorBaseOffset = rotatorBaseOffset;
	}

	/**
	 * @return the beam
	 */
	public Beam getBeam() {
		return beam;
	}

	/**
	 * @param beam the beam to set
	 */
	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	/**
	 * Find the instrument at the specified port or NULL if no instrument on
	 * that port.
	 * 
	 * @param port
	 *            The port to check.
	 * @return The instrument at port or NULL.
	 */
	public InstrumentDescriptor getInstrumentAtPort(int port) {
		Iterator<InstrumentDescriptor> ip = portMap.keySet().iterator();
		while (ip.hasNext()) {
			InstrumentDescriptor id = ip.next();
			int p = portMap.get(id);
			if (p == port)
				return id;
		}
		return null;
	}

	public int getPortForInstrument(InstrumentDescriptor id) {
		return portMap.get(id);
	}

	public void addPortMapping(InstrumentDescriptor id, int port) {// maybe
		// throw
		// exception
		// !
		portMap.put(id, port);
		// or do we fail if already mapped??
	}

	// ap number
	public InstrumentDescriptor getInstrumentWithAperture(int apNumber) {
		Iterator<InstrumentDescriptor> ip = apertureMap.keySet().iterator();
		while (ip.hasNext()) {
			InstrumentDescriptor id = ip.next();
			int a = apertureMap.get(id);
			if (a == apNumber)
				return id;
		}
		return null;
	}

	public int getApertureNumberForInstrument(InstrumentDescriptor id) {
		System.err.println("SCI:getAp("+id.getInstrumentName()+")");
		int ap = apertureMap.get(id);
		System.err.println("SCI:getAp returns: "+ap);
		return apertureMap.get(id);
	}

	public void addApertureMapping(InstrumentDescriptor id, int apNumber) {// maybe
		// throw
		// exception
		// !
		apertureMap.put(id, apNumber);
	}

	// mount point
	public File getMountPointForInstrument(InstrumentDescriptor id) {
		return mountMap.get(id);
	}

	public void addMountMapping(InstrumentDescriptor id, File file) {// maybe
		// throw
		// exception
		// !
		mountMap.put(id, file);
	}

	// reboot
	public int getRebootLevelForInstrument(InstrumentDescriptor id) {
		return rebootMap.get(id);
	}

	public void addRebootMapping(InstrumentDescriptor id, int level) {// maybe
		// throw
		// exception
		// !
		rebootMap.put(id, level);
	}

	// alias
	public String getAliasForInstrument(InstrumentDescriptor id) {
		return aliasMap.get(id);
	}

	public void addAliasMapping(InstrumentDescriptor id, String alias) {// maybe
		// throw
		// exception
		// !
		aliasMap.put(id, alias);
	}

	// TODO should we try something like... configure(Ireg) then we get the set of instruments...
	
	/**
	 * Configure the registry from a DOM tree node.
	 * 
	 * @param root
	 *            the root element node.
	 * @see ngat.util.XmlConfigurable#configure(org.jdom.Element)
	 */
	public void configure(Element root) throws Exception {

		scienceFold = new ScienceFold();
		//scienceFold.configure(scfnode);
		
		beam = new Beam("BEAM");
		//beam.configure(beamNode);
		
		// Rotator base offset
		Element rotNode = root.getChild("rotator");
		rotatorBaseOffset = Math.toRadians(Double.parseDouble(rotNode.getChildTextTrim("offset")));
				
		int aperture = 0;
		List rootList = root.getChildren("instrument");
		Iterator iroot = rootList.iterator();
		while (iroot.hasNext()) {
			Element inode = (Element) iroot.next();
			String iname = inode.getAttributeValue("name");
			InstrumentDescriptor iid = new InstrumentDescriptor(iname);

			String alias = inode.getChildTextTrim("alias");
			int reboot = Integer.parseInt(inode.getChildTextTrim("reboot"));
			String mount = inode.getChildTextTrim("mount");
			aperture++;
			int port = Integer.parseInt(inode.getChildTextTrim("port"));

			// TODO why dont we just create a storage item InstInfo to hold all these
			// bits of data and then a single mapping is all we need....???
			
			aliasMap.put(iid, alias);
			rebootMap.put(iid, reboot);
			mountMap.put(iid, new File(mount));
			System.err.println("SCI::Adding aperture  [" + aperture + "] for instrument: " + iname);
			apertureMap.put(iid, aperture);
			portMap.put(iid, port);

			System.err.println("SCI::Configured payload for instrument: [" + aperture + "] " + iname);

		}

	}

}
