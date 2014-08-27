/**
 * 
 */
package ngat.tcm.beam;

import java.io.Serializable;
import java.util.Hashtable;

import ngat.phase2.XOpticalSlideConfig;

/**
 * @author eng
 *
 */
public class Beam implements Serializable {

	/** Name of the system.*/
	private String name;
	
	/** Upper slide mechanism.*/
	private OpticalSlideMechanism upperSlide;
	
	/** Lower slide mechanism.*/
	private OpticalSlideMechanism lowerSlide;

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the upperSlide
	 */
	public OpticalSlideMechanism getUpperSlide() {
		return upperSlide;
	}

	/**
	 * @param upperSlide the upperSlide to set
	 */
	public void setUpperSlide(OpticalSlideMechanism upperSlide) {
		this.upperSlide = upperSlide;
	}

	/**
	 * @return the lowerSlide
	 */
	public OpticalSlideMechanism getLowerSlide() {
		return lowerSlide;
	}

	/**
	 * @param lowerSlide the lowerSlide to set
	 */
	public void setLowerSlide(OpticalSlideMechanism lowerSlide) {
		this.lowerSlide = lowerSlide;
	}
	
	//private TiptiltMechanism lowerTiptilt;
	/** TODOthis wants a beamstatus object defining*/
	public void updateBeamStatus(Hashtable hash) throws Exception {
	
		
		upperSlide.setStatusTimeStamp(System.currentTimeMillis());
	
		// work out which of the 3 elements are currently selected, we use the value of
		// upper.slide.position (an INT) to determine the specific element.
		int upperElementNumber = Integer.parseInt((String)hash.get("upper.slide.position"));
		XOpticalSlideConfig uconfig = new XOpticalSlideConfig(XOpticalSlideConfig.SLIDE_UPPER);
		
		// now identify the element
		OpticalElement upperElement = upperSlide.getElementFor(uconfig);
		// now set it as current
		upperSlide.setCurrentElement(upperElement);
		
		
		lowerSlide.setStatusTimeStamp(System.currentTimeMillis());
		
		// work out which of the 3 elements are currently selected, we use the value of
		// upper.slide.position (an INT) to determine the specific element.
		int lowerElementNumber = Integer.parseInt((String)hash.get("lower.slide.position"));
		XOpticalSlideConfig lconfig = new XOpticalSlideConfig(XOpticalSlideConfig.SLIDE_LOWER);
		
		// now identify the element
		OpticalElement lowerElement = lowerSlide.getElementFor(lconfig);
		// now set it as current
		lowerSlide.setCurrentElement(lowerElement);
		
		
		
		
		
	}
	
	public String toString() {
		return "Beam: "+name+", Upper: "+upperSlide+", Lower: "+lowerSlide;
		
	}

	/**
	 * @param name
	 */
	public Beam(String name) {
		super();
		this.name = name;
	}
	
}
