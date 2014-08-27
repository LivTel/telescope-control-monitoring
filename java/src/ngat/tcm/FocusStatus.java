/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public class FocusStatus extends PrimaryAxisStatus {

	/** An offset from nominal position.*/
	private double focusOffset;

	/**
	 * @return the focusOffset
	 */
	public double getFocusOffset() {
		return focusOffset;
	}

	/**
	 * @param focusOffset the focusOffset to set
	 */
	public void setFocusOffset(double focusOffset) {
		this.focusOffset = focusOffset;
	}
	
	public String toString() {
		return super.toString()+", offset: "+focusOffset;
	}
	
}
