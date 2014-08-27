/*   
    Copyright 2006, Astrophysics Research Institute, Liverpool John Moores University.

    This file is part of Robotic Control System.

     Robotic Control Systemis free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Robotic Control System is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Robotic Control System; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package ngat.tcm;

import java.util.*;
import ngat.message.RCS_TCS.*;

public interface AutoguiderStatusListener {

    /** Handle a guide-lock lost event.*/
    public void guideLockLost();

//     /** Handle a guide-lock acquired event.*/
//     public void guideAcquired();

//     /** Handle a failure to acquire event.*/
//     public void failedToAcquire(String message);

    
}
