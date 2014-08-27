/**
 * 
 */
package ngat.tcm.beam;

import ngat.phase2.XOpticalSlideConfig;

/**
 * @author eng
 *
 */
public class Dichroic implements OpticalElement {

	private String elementClass;

	private String elementName;
	
	private double reflectionDefocus;
	
	private double transmissionDefocus;

	
	
	/** Create a Dichroic.
	 * @param elementClass
	 * @param elementName
	 */
	public Dichroic(String elementClass, String elementName) {
		super();
		this.elementClass = elementClass;	
		this.elementName = elementName;
	}

	/**
	 * @return the elementClass
	 */
	public String getElementClass() {
		return elementClass;
	}

	/**
	 * @param elementClass the elementClass to set
	 */
	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * @return the reflective
	 */
	public boolean isReflective() {
		return true;
	}

	
	/**
	 * @return the transmissive
	 */
	public boolean isTransmissive() {
		return true;
	}

	
	/**
	 * @return the refelectionDefocus
	 */
	public double getReflectionDefocus() {
		return reflectionDefocus;
	}

	/**
	 * @param refelectionDefocus the refelectionDefocus to set
	 */
	public void setReflectionDefocus(double refelectionDefocus) {
		this.reflectionDefocus = refelectionDefocus;
	}

	/**
	 * @return the transmissionDefocus
	 */
	public double getTransmissionDefocus() {
		return transmissionDefocus;
	}

	/**
	 * @param transmissionDefocus the transmissionDefocus to set
	 */
	public void setTransmissionDefocus(double transmissionDefocus) {
		this.transmissionDefocus = transmissionDefocus;
	}
	
	public String toString() {
		return elementClass+","+elementName;	
	}
}
