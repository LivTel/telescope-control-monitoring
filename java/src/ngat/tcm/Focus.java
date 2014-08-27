/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public class Focus extends PrimaryAxis {

	public Focus(String name) {
		super(name);	
	}

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
	
	/**
	 * @see ngat.tcm.PrimaryAxis#updateAxisStatus(ngat.tcm.PrimaryAxisStatus)
	 */
	public void updateAxisStatus(FocusStatus status) {	
		super.updateAxisStatus(status);
		focusOffset = status.getFocusOffset();
	}

}
