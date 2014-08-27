/**
 * 
 */
package ngat.tcm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author snf
 *
 */
public class TelescopeCalibrationHistory implements Serializable {

	/** Time last TELFOCUS calibration was performed.*/
	long lastTelfocusCalibration;
	
	/** Time last POINTING calibration was performed.*/
	long lastPointingCalibration;

	/**
	 * 
	 */
	public TelescopeCalibrationHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the lastPointingCalibration
	 */
	public long getLastPointingCalibration() {
		return lastPointingCalibration;
	}

	/**
	 * @param lastPointingCalibration the lastPointingCalibration to set
	 */
	public void setLastPointingCalibration(long lastPointingCalibration) {
		this.lastPointingCalibration = lastPointingCalibration;
	}

	/**
	 * @return the lastTelfocusCalibration
	 */
	public long getLastTelfocusCalibration() {
		return lastTelfocusCalibration;
	}

	/**
	 * @param lastTelfocusCalibration the lastTelfocusCalibration to set
	 */
	public void setLastTelfocusCalibration(long lastTelfocusCalibration) {
		this.lastTelfocusCalibration = lastTelfocusCalibration;
	}
	
	/** Load a TelescopeCalibrationHistory from a file.
	 * @param file The file to load the serialized TelescopeCalibrationHistory from,
	 * @return An instance of TelescopeCalibrationHistory de-serialized from file.
	 * @throws Exception If anything goes wrong.
	 */
	public static TelescopeCalibrationHistory load(File file) throws Exception {
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream oin = new ObjectInputStream(fin);
		TelescopeCalibrationHistory tch = (TelescopeCalibrationHistory)oin.readObject();
		return tch;
	}
	
	/** Save a TelescopeCalibrationHistory to a file.
	 * @param tch The instance of TelescopeCalibrationHistory to save to file.
	 * @param file The file to serialize the TelescopeCalibrationHistory to.
	 * @throws Exception If anything goes wrong.
	 */
	public static void save(TelescopeCalibrationHistory tch, File file) throws Exception {
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream oout = new ObjectOutputStream(fout);
		oout.flush();
		oout.writeObject(tch);
		oout.flush();		
	}
	
	/** Returns a readable description of this tel calib history.*/
	public String toString() {
			return "TelescopeCalibHistory: LastTelfocus: "+
			(new Date(lastTelfocusCalibration)).toGMTString()+
			" Last pointing: "+(new Date(lastPointingCalibration)).toGMTString();
	}
	
}
