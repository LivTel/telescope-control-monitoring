/**
 * 
 */
package ngat.tcm.beam.test;

import ngat.phase2.XOpticalSlideConfig;
import ngat.tcm.beam.Air;
import ngat.tcm.beam.Beam;
import ngat.tcm.beam.Dichroic;
import ngat.tcm.beam.Mirror;
import ngat.tcm.beam.OpticalSlideMechanism;

/**
 * @author eng
 *
 */
public class CreateSlide {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// first some elements
		
		Dichroic uRB = new Dichroic("Dichroic", "Di-RB10000-01");
		Mirror uMirr = new Mirror("Mirror", "Al01");
		Air uAir = new Air("Air", 2, "Clear");
		
		OpticalSlideMechanism upper = new OpticalSlideMechanism("upperslide");
		upper.addPositionMapping(2, uRB);
		upper.addPositionMapping(3, uMirr);
		upper.addPositionMapping(1, uAir);
		
		Dichroic lRB = new Dichroic("Dichroic", "Di-RB6700-01");
		Dichroic lBR = new Dichroic("Dichroic", "Di-BR6700-01");
		Mirror lMirr = new Mirror("Mirror", "Al02");
		
		OpticalSlideMechanism lower = new OpticalSlideMechanism("lowerslide");
		lower.addPositionMapping(1, lRB);
		lower.addPositionMapping(3, lMirr);
		lower.addPositionMapping(2, lBR);
	
		Beam beam = new Beam("LT_BEAM");
		beam.setLowerSlide(lower);
		beam.setUpperSlide(upper);
		
		System.err.println("Created: "+beam);
	}

}
