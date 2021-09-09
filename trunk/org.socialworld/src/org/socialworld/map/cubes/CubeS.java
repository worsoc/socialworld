/*
* Social World
* Copyright (C) 2021  Philipp Haack
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.map.cubes;

public class CubeS extends Cube {

    protected Cube getNewInstance() {
    	return new CubeS();
    }

    protected  Vector3[] getCornerOffsets(short level) {
        Vector3[] corners = new Vector3[8];
        double stepSize = getMinimalStepOnLevel(level);
        int i = 0;
        for (short x = 0; x <= 1; x++) {
            for (short y = 0; y <= 1; y++) {
                for (short z = 0; z <= 1; z++) {
                    // / 3 * level
                    corners[i] = new Vector3(x * stepSize, y * stepSize, z * stepSize);
                    i++;
                }
            }
        }

        return corners;
    }


    protected Vector3 coordinatesOfBitsIndex(short index) {
        // only the first and second values (the x value and the y value) are important 
        switch (index) {
            case 0:
                return new Vector3(1, 0, 0);
            case 1:
                return new Vector3(0, 0, 0);
            case 2:
                return new Vector3(1, 1, 0);
            case 3:
                return new Vector3(0, 1, 0);
            default:
                return new Vector3(0, 0, 0);
        }
    }

}
