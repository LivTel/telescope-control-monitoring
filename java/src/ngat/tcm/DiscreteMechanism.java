/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public interface DiscreteMechanism extends Mechanism {

	/**
	 * @return mechanism current position.
	 */
	public int getCurrentPosition();
	
	/**
	 * @return mechanism demand position.
	 */
	public int getDemandPosition();
	
}
