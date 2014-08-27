/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public interface ContinuousMechanism extends Mechanism {

	/**
	 * @return mechanism current position.
	 */
	public double getCurrentPosition();
	
	/**
	 * @return mechanism demand position.
	 */
	public double getDemandPosition();
	
}
