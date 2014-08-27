/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public interface Mechanism extends TelescopeStatus {

    /** Returns the name of the mechanism.*/
	public String getMechanismName();
    
	/** Return a mechanism state.*/
	public int getMechanismState();
	
}
