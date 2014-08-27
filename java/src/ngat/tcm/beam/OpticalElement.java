/**
 * 
 */
package ngat.tcm.beam;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public interface OpticalElement extends Serializable {
	
	public String getElementName();
	
	public String getElementClass();

	public boolean isReflective();
	
	public boolean isTransmissive();

	public double getReflectionDefocus();
	
	public double getTransmissionDefocus();
	
	
}
