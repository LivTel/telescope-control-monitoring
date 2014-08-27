/**
 * 
 */
package ngat.tcm.beam;

/**
 * @author eng
 *
 */
public class Mirror implements OpticalElement {

	private String elementClass;

	private String elementName;

	
	/**
	 * @param elementClass
	 * @param elementName
	 */
	public Mirror(String elementClass, String elementName) {
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

	public boolean isReflective() {
		return true;
	}

	public boolean isTransmissive() {
		return false;
	}

	public double getReflectionDefocus() {
		return 0;
	}

	public double getTransmissionDefocus() {
		return 0;
	}
	
	public String toString() {
		return elementClass+", "+elementName;
	}
	
}
