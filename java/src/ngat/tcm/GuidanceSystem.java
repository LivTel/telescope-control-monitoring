/**
 * 
 */
package ngat.tcm;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jdom.Element;

import ngat.phase2.IAutoguiderConfig;
import ngat.util.XmlConfigurable;

/**
 * @author eng
 *
 */
public class GuidanceSystem implements XmlConfigurable {

	/** Autoguider filter mechanism.*/
	private AuxilliaryMechanism autoguiderFilter;
	
	/** Autoguider mirror mechanism.*/
	private AuxilliaryMechanism autoguiderMirror;
	
	/** Autoguider focus position.*/
	private Focus autoguiderFocus;

	/** A list of available autoguiders.*/
	private Map<String, Autoguider> autoguiders;

	/** The nominated default autoguider.*/
	private Autoguider defaultAutoguider;
	
	/**
	 * Create a guidance system.
	 */
	public GuidanceSystem() {
		autoguiders = new HashMap<String, Autoguider>();
	}

	/** Add a new autoguider. Returns silently if the specified autoguider name is already known.
	 * @param ag The autoguider to add.
	 * @throws Exception If the ag is null or its name is null.
	 */
	public void addAutoguider(Autoguider ag) throws Exception {
		if (ag == null)
			throw new IllegalArgumentException("GuidanceSystem:addAutoguider: Autoguider is null");
		
		if ((ag.getAutoguiderName() == null) || (ag.getAutoguiderName().equals("")))
				throw new IllegalArgumentException("GuidanceSystem:addAutoguider: Autoguider name is null");
		
		if (autoguiders.containsKey(ag.getAutoguiderName()))
			return;
		
		autoguiders.put(ag.getAutoguiderName(), ag);		
	}
	
	public List<Autoguider> listAutoguiders() {
		
		List<Autoguider> agList = new Vector<Autoguider>();
		
		Iterator<String> iagName = autoguiders.keySet().iterator();
		while (iagName.hasNext()) {
			String agName = iagName.next();
			Autoguider ag = autoguiders.get(agName);
			agList.add(ag);
		}
		
		return agList;
	}
	
	
	/** Find the named autoguider if available.
	 * @param name The name of the autoguider to locate.
	 * @return The named autoguider.
	 * @throws Exception If the autoguider is not known.
	 */
	public Autoguider getAutoguider(String name) throws Exception {
		if (autoguiders.containsKey(name))
			return autoguiders.get(name);
		throw new IllegalArgumentException("GuidanceSystem:getAutoguider: Unknown autoguider: "+name);
	}
	
	/**
	 * @return the autoguiderFilter
	 */
	public AuxilliaryMechanism getAutoguiderFilter() {
		return autoguiderFilter;
	}

	/**
	 * @param autoguiderFilter the autoguiderFilter to set
	 */
	public void setAutoguiderFilter(AuxilliaryMechanism autoguiderFilter) {
		this.autoguiderFilter = autoguiderFilter;
	}

	/**
	 * @return the autoguiderMirror
	 */
	public AuxilliaryMechanism getAutoguiderMirror() {
		return autoguiderMirror;
	}

	/**
	 * @param autoguiderMirror the autoguiderMirror to set
	 */
	public void setAutoguiderMirror(AuxilliaryMechanism autoguiderMirror) {
		this.autoguiderMirror = autoguiderMirror;
	}

	/**
	 * @return the autoguiderFocus
	 */
	public Focus getAutoguiderFocus() {
		return autoguiderFocus;
	}

	/**
	 * @param autoguiderFocus the autoguiderFocus to set
	 */
	public void setAutoguiderFocus(Focus autoguiderFocus) {
		this.autoguiderFocus = autoguiderFocus;
	}

	
	
	/**
	 * @return the defaultAutoguider
	 */
	public Autoguider getDefaultAutoguider() {
		return defaultAutoguider;
	}

	/**
	 * @param defaultAutoguider the defaultAutoguider to set
	 */
	public void setDefaultAutoguider(Autoguider ag) throws Exception {
		if (ag == null)
			throw new IllegalArgumentException("GuidanceSystem:setDefaultAutoguider: Autoguider is null");
		
		if ((ag.getAutoguiderName() == null) || (ag.getAutoguiderName().equals("")))
				throw new IllegalArgumentException("GuidanceSystem:setDefaultAutoguider: Autoguider name is null");
		
		if (! (autoguiders.containsKey(ag.getAutoguiderName())))
				throw new IllegalArgumentException("GuidanceSystem:setDefaultAutoguider: Unknown autoguider name: "+ag.getAutoguiderName());
		
		// finally if we actually find the ag in our list it is set to default
		this.defaultAutoguider = ag;
	}

	public void configure(Element root) throws Exception {
		autoguiderFilter = new AuxilliaryMechanism("AFI");
		autoguiderFocus = new Focus("AGF");
		autoguiderMirror = new AuxilliaryMechanism("AMD");
		
		List agList = root.getChildren("autoguider");
		Iterator iag = agList.iterator();
		while (iag.hasNext()) {
			Element agNode = (Element)iag.next();
			String agName = agNode.getAttributeValue("name");
			boolean agDefault = (agNode.getAttributeValue("default") != null);
		
			String agActiveHost = agNode.getChildTextTrim("host");
			int agActivePort = Integer.parseInt(agNode.getChildTextTrim("port"));
			
			BasicAutoguider ag = new BasicAutoguider(agName);
			ag.setAgActiveHost(agActiveHost);
			ag.setAgActivePort(agActivePort);
			addAutoguider(ag);
			System.err.println("GUI::Added autoguider: "+agName);
			if (agDefault) {
				setDefaultAutoguider(ag);
				System.err.println("GUI:: Set default autoguider to: "+agName);
			}
		}
		System.err.println("GUI::Configured guidance system");
	}
	
	
	
}
