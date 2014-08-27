/**
 * 
 */
package ngat.tcm.beam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ngat.phase2.XOpticalSlideConfig;
import ngat.tcm.Mechanism;

/**
 * @author eng
 *
 */
public class OpticalSlideMechanism implements Mechanism {

	private long statusTimeStamp;
	
	private String mechanismName;
	
	private int mechanismState;
	
	private OpticalElement currentElement;

	/** Maps the slide positions to specific elements.*/
	//private Map<Integer, OpticalElement> positionMap;
	
	/** Maps the element class name to specific element.*/
	//private Map<Integer, OpticalElement> classMap;
	
	
	/**
	 * @param mechanismName
	 */
	public OpticalSlideMechanism(String mechanismName) {
		super();
		this.mechanismName = mechanismName;
		//positionMap = new HashMap<Integer, OpticalElement>();
		//classMap = new HashMap<Integer, OpticalElement>();
	}

	public void addPositionMapping(int slidePosition, OpticalElement element) {
		//positionMap.put(slidePosition, element);
		//classMap.put(element.getElementClassId(), element);
	}
	
	/**
	 * @return the statusTimeStamp
	 */
	public long getStatusTimeStamp() {
		return statusTimeStamp;
	}

	/**
	 * @param statusTimeStamp the statusTimeStamp to set
	 */
	public void setStatusTimeStamp(long statusTimeStamp) {
		this.statusTimeStamp = statusTimeStamp;
	}

	/**
	 * @return the mechanismName
	 */
	public String getMechanismName() {
		return mechanismName;
	}

	/**
	 * @param mechanismName the mechanismName to set
	 */
	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}

	/**
	 * @return the mechanismState
	 */
	public int getMechanismState() {
		return mechanismState;
	}

	/**
	 * @param mechanismState the mechanismState to set
	 */
	public void setMechanismState(int mechanismState) {
		this.mechanismState = mechanismState;
	}

	/**	s.append(positionM)
	 * @return the currentElement
	 */
	public OpticalElement getCurrentElement() {
		return currentElement;
	}

	/**
	 * @param currentElement the currentElement to set
	 */
	public void setCurrentElement(OpticalElement currentElement) {
		this.currentElement = currentElement;
	}
	
	
	/** The element at the specified position.
	 * @param slidePosition 
	 * @return
	 */
	public OpticalElement getElementAt(int slidePosition) throws Exception {
		
		//if (! positionMap.containsKey(slidePosition))
			throw new Exception("Not implemented");
		
		//return positionMap.get(slidePosition);
		
	}
	
	/** The element corresponding to the supplied config.
	 * @param config
	 * @return
	 */
	public OpticalElement getElementFor(XOpticalSlideConfig config) throws Exception{
	return null;
		//if (! classMap.containsKey(config.getPosition()))
		//	throw new Exception("No elements of class: "+config.getElementName());
		
		//return classMap.get(config.getPosition());
		
	}
	
	public String toString() {
		
		StringBuffer s = new StringBuffer("OpticalSlide: "+mechanismName+" [");
		
		//Iterator<Integer> ik = positionMap.keySet().iterator();
		//while (ik.hasNext()) {
		//	int k = ik.next();
		//	OpticalElement element = positionMap.get(k);
		//	s.append(" ["+k+"]: ");
		//	s.append(element);
		//	if (ik.hasNext())
			//	s.append(",");
		//}
		s.append("not-impl]");
		//return s.toString();
	
		return s.toString();
	}

	public String getCategoryName() {
		return mechanismName;
	}
	
	
}
