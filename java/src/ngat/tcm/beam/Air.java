/**
 * 
 */
package ngat.tcm.beam;

/**
 * @author eng
 *
 */
public class Air implements OpticalElement {

	private String elementClass;
	
	private int elementClassId;
	
	private String elementName;

	
	
	
	/**
	 * @param elementClass
	 * @param elementClassId
	 * @param elementName
	 */
	public Air(String elementClass, int elementClassId, String elementName) {
		super();
		this.elementClass = elementClass;
		this.elementClassId = elementClassId;
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
	 * @return the elementClassId
	 */
	public int getElementClassId() {
		return elementClassId;
	}

	/**
	 * @param elementClassId the elementClassId to set
	 */
	public void setElementClassId(int elementClassId) {
		this.elementClassId = elementClassId;
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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTransmissive() {
		// TODO Auto-generated method stub
		return true;
	}

	public double getReflectionDefocus() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTransmissionDefocus() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return elementClass+",("+elementClassId+"),"+elementName;	
	}
	
	
}
