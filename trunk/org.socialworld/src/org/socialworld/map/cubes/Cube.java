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

import java.util.ArrayList;

class Vector3 {
    double x, y, z = 0;

    Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public static Vector3 crossProduct(Vector3 vect_A, Vector3 vect_B) {
        Vector3 cross_P = new Vector3(0, 0, 0);
        cross_P.x = vect_A.y * vect_B.z - vect_A.z * vect_B.y;
        cross_P.y = vect_A.z * vect_B.x - vect_A.x * vect_B.z;
        cross_P.z = vect_A.x * vect_B.y - vect_A.y * vect_B.x;
        return cross_P;
    }

 }

public class Cube {
	
    private static short SIZE = 3;
    public static double epsilon = 0.000001;
    private short level = 0;
    private Cube innerCubes[][][] = new Cube[SIZE][SIZE][SIZE];
    private boolean hasChildren = false;
    private short countValidChildren = 0;
    private boolean isFullyFilled = false;
    private boolean isValid = true;
    private String address = "";

    private static double getDelta(Vector3 pointA, Vector3 pointB, Vector3 pointC, Vector3 pointInCubeToCheck) {

        /*
         * Get two different vectors which are in the plane, such as B−A=(3,0,−3) and
         * C−A=(3,3,3) . Compute the cross product of the two obtained vectors:
         * (B−A)×(C−A)=(9,−18,9) . This is the normal vector of the plane, so we can
         * divide it by 9 and get (1,−2,1) . The equation of the plane is thus
         * x−2y+z+k=0 . To get k, substitute any point and solve; we get k=−6. The final
         * equation of the plane is x−2y+z−6=0.
         */

        /* calculate VectorAB & AC */
        Vector3 vecAB = new Vector3(pointB.x - pointA.x, pointB.y - pointA.y, pointB.z - pointA.z);
        Vector3 vecAC = new Vector3(pointC.x - pointA.x, pointC.y - pointA.y, pointC.z - pointA.z);
        /* take cross product of VectorAB & VectorAC */
        Vector3 cross_P = Vector3.crossProduct(vecAB, vecAC);
        /* deconstruct x,y,z -> for x+y+z+k=0 */
        double x = cross_P.x;
        double y = cross_P.y;
        double z = cross_P.z;
        /* solve for k (use any point) */
        double k = ((pointA.x * x) + (pointA.y * y) + (pointA.z * z)) * -1;

        /* solve with x,y,z of PointToCheck */
        if (Math.abs(
                ((pointInCubeToCheck.x * x) + (pointInCubeToCheck.y * y) + (pointInCubeToCheck.z * z) + k)) < epsilon)
            return 0;

        return ((pointInCubeToCheck.x * x) + (pointInCubeToCheck.y * y) + (pointInCubeToCheck.z * z) + k);
    }

    private void fillInnerCubes() {
        this.hasChildren = true;
        this.isFullyFilled = true;
        for (short x = 0; x < 3; x++) {
            for (short y = 0; y < 3; y++) {
                for (short z = 0; z < 3; z++) {
                    this.innerCubes[x][y][z] = new Cube();
                    this.innerCubes[x][y][z].level = (short) (this.level + 1);
                    this.innerCubes[x][y][z].address = this.address + getLetter(x, y, z);
                }
            }
        }
    }

    private static Vector3[] getCornerOffsets(short level) {
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

    private String getLetter(short xIndex, short yIndex, short zIndex) {
        if (zIndex == 0) {
            if (yIndex == 0) {
                switch (xIndex) {
                    case 0:
                        return "A";
                    case 1:
                        return "B";
                    case 2:
                        return "C";
                }
            } else if (yIndex == 1) {
                switch (xIndex) {
                    case 0:
                        return "D";
                    case 1:
                        return "E";
                    case 2:
                        return "F";
                }
            } else if (yIndex == 2) {
                switch (xIndex) {
                    case 0:
                        return "G";
                    case 1:
                        return "H";
                    case 2:
                        return "I";
                }
            }
        } else if (zIndex == 1) {
            if (yIndex == 0) {
                switch (xIndex) {
                    case 0:
                        return "J";
                    case 1:
                        return "K";
                    case 2:
                        return "L";
                }
            } else if (yIndex == 1) {
                switch (xIndex) {
                    case 0:
                        return "M";
                    case 1:
                        return "#";
                    case 2:
                        return "N";
                }
            } else if (yIndex == 2) {
                switch (xIndex) {
                    case 0:
                        return "O";
                    case 1:
                        return "P";
                    case 2:
                        return "Q";
                }
            }
        } else if (zIndex == 2) {
            if (yIndex == 0) {
                switch (xIndex) {
                    case 0:
                        return "R";
                    case 1:
                        return "S";
                    case 2:
                        return "T";
                }
            } else if (yIndex == 1) {
                switch (xIndex) {
                    case 0:
                        return "U";
                    case 1:
                        return "V";
                    case 2:
                        return "W";
                }
            } else if (yIndex == 2) {
                switch (xIndex) {
                    case 0:
                        return "X";
                    case 1:
                        return "Y";
                    case 2:
                        return "Z";
                }
            }
        }
        return ".";
    }

    public static boolean isBeingSplit(Vector3 pointA, Vector3 pointB, Vector3 pointC, double absoluteX,
            double absoluteY, double absoluteZ, short level) {
        if (level == 0)
            return true;

        Vector3 cornerOffsets[] = getCornerOffsets(level);
        short originDeltaSignum = (short) Math
                .signum(getDelta(pointA, pointB, pointC, new Vector3(absoluteX, absoluteY, absoluteZ)));

        for (int i = 1; i < cornerOffsets.length; i++) {
            if (Math.signum(getDelta(pointA, pointB, pointC, new Vector3(absoluteX + cornerOffsets[i].x,
                    absoluteY + cornerOffsets[i].y, absoluteZ + cornerOffsets[i].z))) != originDeltaSignum) {
                return true;
            }
        }

        return false;
    }

    private static double getMinimalStepOnLevel(int level) {
        return (1 / (Math.pow(3, level)));
    }

    private static short findIndexBits(boolean valueToFind, boolean bits[]) {
        for (short i = 0; i < bits.length; i++)
            if (bits[i] == valueToFind)
                return i;

        return -1;
    }

    private static short[] findAllIndeciesBits(boolean valueToFind, boolean bits[]) {
        short result[] = { 0, 0, 0, 0 };
        short pointer = 0;
        for (short i = 0; i < bits.length; i++)
            if (bits[i] == valueToFind) {
                result[pointer] = i;
                pointer++;
            }

        return result;
    }

    private static Vector3 coordinatesOfBitsIndex(short index) {
        /* only the first and second values are important */
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

    public static Vector3[] getPlanePointsFromTile(short bitsNumber) {
        /* create bits from Number */
        Vector3 resultPoints[] = new Vector3[3];
        boolean bits[] = new boolean[4];
        if (bitsNumber > 7) {
            bits[3] = true;
            bitsNumber -= 8;
        }
        if (bitsNumber > 3) {
            bits[2] = true;
            bitsNumber -= 4;
        }
        if (bitsNumber > 1) {
            bits[1] = true;
            bitsNumber -= 2;
        }
        if (bitsNumber > 0) {
            bits[0] = true;

        }
        short count = countBits(bits);
        /* fill points -> 0,0,0 (default) */
        for (short i = 0; i < 3; i++)
            resultPoints[i] = new Vector3(i, i, 0);

        switch (count) {
            case 1: {
                resultPoints[0] = coordinatesOfBitsIndex(findIndexBits(true, bits));
                resultPoints[0].z = 1;

                resultPoints[1].x = Math.abs(resultPoints[0].x - 1);
                resultPoints[1].y = resultPoints[0].y;
                resultPoints[1].z = 0;

                resultPoints[2].x = resultPoints[0].x;
                resultPoints[2].y = Math.abs(resultPoints[0].y - 1);
                resultPoints[2].z = 0;
                break;
            }
            case 2: {
                // get all positive indecies
                short indecies[] = findAllIndeciesBits(true, bits);

                for (short i = 0; i < 2; i++) {
                    resultPoints[i] = coordinatesOfBitsIndex(indecies[i]);
                    resultPoints[i].z = 1;
                }
                resultPoints[2] = new Vector3(resultPoints[0].x, resultPoints[1].y, 0);
                if ((bits[0] && bits[1]) || (bits[1] && bits[3]) || (bits[2] && bits[3]) || (bits[0] && bits[2]))
                    resultPoints[2] = new Vector3((resultPoints[0].x + 1) % 2, (resultPoints[1].y + 1) % 2, 0);
                break;
            }
            // two planes
            case 3: {
                Vector3 nonPositiveBitPosition = coordinatesOfBitsIndex(findIndexBits(false, bits));
                resultPoints[0] = new Vector3(nonPositiveBitPosition);
                resultPoints[1] = new Vector3(nonPositiveBitPosition);
                resultPoints[1].x = ((nonPositiveBitPosition.x + 1) % 2);
                resultPoints[1].z = 1;
                resultPoints[2] = new Vector3(nonPositiveBitPosition);
                resultPoints[2].y = ((nonPositiveBitPosition.y + 1) % 2);
                resultPoints[2].z = 1;

                // alt- >

                // create second plane's points
            }
        }

        /* if vector [] length = 1 -> there is only one Plane to calcualte */
        return resultPoints;

    }

    public void printCases(Vector3[] points, double x, double y, double z) {
        System.out.println("Delta of Point(" + x + ", " + y + ", " + z + ") is: "
                + getDelta(points[0], points[1], points[2], new Vector3(x, y, z)) + " and the cube is"
                + (isBeingSplit(points[0], points[1], points[2], x, y, z, (short) 2) ? "" : " NOT ")
                + "being split! (at level 2)");

    }

    public String toString() {
        if (this.isFullyFilled)
            return this.address;
        else
            return "-";
    }

    private static short countBits(boolean bits[]) {
        short count = 0;
        for (boolean bit : bits) {
            if (bit)
                count++;
        }
        return count;
    }

    public void splitCube(short tileNumber, short detailDepth, boolean keepPositiveDelta) {
        this.fillInnerCubes();
        if (tileNumber == 6 || tileNumber == 9) {
            // execute 2 splits
            return;
        }

        this.recursiveSplitting(tileNumber, detailDepth, (double) 0, (double) 0, (double) 0, keepPositiveDelta);

    }

    private void recursiveSplitting(short tileNumber, short detailDepth, double absX, double absY, double absZ,
            boolean keepPositiveDelta) {
        Vector3 planePoints[] = getPlanePointsFromTile(tileNumber);

        if (isBeingSplit(planePoints[0], planePoints[1], planePoints[2], absX, absY, absZ, this.level)) {
            if (this.level < detailDepth) {
                double minStep = getMinimalStepOnLevel(this.level + 1);
                this.fillInnerCubes();
                for (short x = 0; x < 3; x++) {
                    for (short y = 0; y < 3; y++) {
                        for (short z = 0; z < 3; z++) {
                            this.innerCubes[x][y][z].recursiveSplitting(tileNumber, detailDepth, absX + (minStep * x),
                                    absY + (minStep * y), absZ + (minStep * z), keepPositiveDelta);
                        }
                    }
                }
            } 
            /*
            else {
                this.isValid = keepPositiveDelta;
                this.hasChildren = false;
                this.isFullyFilled = false;
            }
            */

        } else {
            double delta = getDelta(planePoints[0], planePoints[1], planePoints[2], new Vector3(absX, absY, absZ));
            if ((delta > 0 && !keepPositiveDelta) || (delta < 0 && keepPositiveDelta)) {
                this.hasChildren = false;
                this.isFullyFilled = false;
                this.isValid = false;
            }
                        
        }
        if (this.hasChildren) {

        	short countValid = 0;
        	
            boolean temp = true;
            for (short iterX = 0; iterX < 3; iterX++) {
                for (short iterY = 0; iterY < 3; iterY++) {
                    for (short iterZ = 0; iterZ < 3; iterZ++) {
                    	/*
                        if (!this.innerCubes[iterX][iterY][iterZ].isValid) {
                            temp = false;
                        }
                        else {
                        	countValid++;
                        }
                        */
                    	
                        if (!this.innerCubes[iterX][iterY][iterZ].isFullyFilled) {
                            temp = false;
                        }
                        
                        if (this.innerCubes[iterX][iterY][iterZ].isValid) {
                        	countValid++;
                        }

                    }
                }
            }
            this.countValidChildren = countValid;
            this.isFullyFilled = temp;
        }

    }

    private static void setFullyFilled(Cube cube, short level, short threasholdChildren) {
        
    	if (cube.level < level) {
	    	for (short iterX = 0; iterX < 3; iterX++) {
	            for (short iterY = 0; iterY < 3; iterY++) {
	                for (short iterZ = 0; iterZ < 3; iterZ++) {
	                	if (cube.innerCubes[iterX][iterY][iterZ] != null) {
	                		setFullyFilled(cube.innerCubes[iterX][iterY][iterZ], level, threasholdChildren);
	                	}
	                }
	            }
	        }
    	}
    	else {
    		if (cube.countValidChildren >= threasholdChildren) {
    			cube.isFullyFilled = true;
    			cube.isValid = true;
    			cube.hasChildren = true;
    		}
    	}

    }
    
    public ArrayList<String> getEndAddresses() {
        ArrayList<String> addresses = new ArrayList<String>();

        recursiveAddressStep(this, addresses);

        return addresses;
    }

    private static ArrayList<String> recursiveAddressStep(Cube cube, ArrayList<String> addresses) {
 
        if (cube.isFullyFilled) {
            addresses.add(cube.address);
            return addresses;
        }

    	for (short iterX = 0; iterX < 3; iterX++) {
            for (short iterY = 0; iterY < 3; iterY++) {
                for (short iterZ = 0; iterZ < 3; iterZ++) {
                    if (cube.hasChildren) {
                    	/*
                        if (cube.isFullyFilled) {
                            addresses.add(cube.address);
                            return addresses;
                        }
                      */
                        if (!cube.innerCubes[iterX][iterY][iterZ].hasChildren
                                && cube.innerCubes[iterX][iterY][iterZ].isValid) {
                            addresses.add(cube.innerCubes[iterX][iterY][iterZ].address);
                        } else if (cube.innerCubes[iterX][iterY][iterZ].hasChildren && cube.isValid) {
                            recursiveAddressStep(cube.innerCubes[iterX][iterY][iterZ], addresses);
                        }
                    }
                }
            }
        }
        return addresses;
    }

    public static void main(String args[]) {
    	short detailDepth = 4;
        Cube cube = new Cube();
        // Vector3 points[][] = getPlanePointsFromTile((short) 11);

        cube.splitCube((short) 7, detailDepth, true);

        // cube.printCases(getPlanePointsFromTile((short) 8), 0.5, 0.5, 0.5);

        Cube lookAtCube = cube.innerCubes[0][2][2];
        // Cube lookAtCube = cube.innerCubes[1][2][0];
        // Cube lookAtCube = cube.innerCubes[0][0][0];
        System.out.println(lookAtCube.hasChildren);
        System.out.println(lookAtCube.isFullyFilled);
        
        setFullyFilled(cube, (short) (detailDepth - 1), (short) 14);
        ArrayList<String> addr = cube.getEndAddresses();
        int countAdr = 0;
        for (String address : addr) {
        	countAdr++;
        	/*
        	if (address.equals("Y")) {
        		int mybreak;
        		mybreak = 0;
        	}
        	*/
        	if (address.length() < detailDepth) {
        		System.out.println(address);
        	}
        }
        System.out.println(countAdr);
    }

}



 




