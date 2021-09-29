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

public abstract class Cube {
	
	protected final static byte CUBE_SIZE_TILE_SMALL = 1;
	protected final static byte CUBE_SIZE_TILE_MEDIUM = 9;
	protected final static byte CUBE_SIZE_TILE_LARGE = 81;

	protected byte size;
	protected byte heightOffset;
	
	private static byte DIMENSION = 3;
    public static double epsilon = 0.000001;
    private byte depth = 0;
    private Cube innerCubes[][][] = new Cube[DIMENSION][DIMENSION][DIMENSION];
    private boolean hasChildren = false;
    private byte countValidChildren = 0;
    private boolean isFullyFilled = false;
    private boolean isValid = true;
    private String address = "";


    protected abstract Cube getNewInstance(byte size,  byte heightOffset);
    
    protected abstract Vector3[] getCornerOffsets(byte depth);
    
    protected abstract Vector3 coordinatesOfBitsIndex(byte index);
    
    protected abstract Vector3[] getPlanePointsFromTile(byte bitsNumber);

    protected abstract void initResultPoints();
    
    private void fillInnerCubes() {
        this.hasChildren = true;
        this.isValid = true;  // Matze
  // Matze     this.isFullyFilled = true;
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                for (byte z = 0; z < 3; z++) {
                    this.innerCubes[x][y][z] = getNewInstance(size, heightOffset);
                    this.innerCubes[x][y][z].depth = (byte) (this.depth + 1);
                    this.innerCubes[x][y][z].address = this.address + getLetter(x, y, z);
                }
            }
        }
    }

    protected static double getDelta(Vector3 pointA, Vector3 pointB, Vector3 pointC, Vector3 pointInCubeToCheck) {

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

        double delta;
        
        /* solve with x,y,z of PointToCheck */
        delta = (pointInCubeToCheck.x * x) + (pointInCubeToCheck.y * y) + (pointInCubeToCheck.z * z) + k;
        if (Math.abs(delta) < epsilon)
            return 0;

        return delta;
    }

    private String getLetter(byte xIndex, byte yIndex, byte zIndex) {
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

    private boolean isBeingSplit(Vector3 pointA, Vector3 pointB, Vector3 pointC, double absoluteX,
            double absoluteY, double absoluteZ, byte depth) {
        
    	// Matze 29.09.2021
    	if (this.address.equals("A")) {
    		int myBreak = 0;
    		myBreak++;
    	}
    	
    	
    	if (depth == 0)
            return true;

        Vector3 cornerOffsets[] = getCornerOffsets(depth);
        byte originDeltaSignum = (byte) Math
                .signum(getDelta(pointA, pointB, pointC, new Vector3(absoluteX, absoluteY, absoluteZ)));

        for (int i = 1; i < cornerOffsets.length; i++) {
            if (Math.signum(getDelta(pointA, pointB, pointC, new Vector3(absoluteX + cornerOffsets[i].x,
                    absoluteY + cornerOffsets[i].y, absoluteZ + cornerOffsets[i].z))) != originDeltaSignum) {
                return true;
            }
        }

        return false;
    }

   
    protected double getMinimalStepForDepth(byte depth) {
        return (this.size / (Math.pow(3, depth)));
    }

    
    protected byte findIndexBits(boolean valueToFind, boolean bits[]) {
        for (byte i = 0; i < bits.length; i++)
            if (bits[i] == valueToFind)
                return i;

        return -1;
    }

    protected byte[] findAllIndeciesBits(boolean valueToFind, boolean bits[]) {
        byte result[] = { 0, 0, 0, 0 };
        byte pointer = 0;
        for (byte i = 0; i < bits.length; i++)
            if (bits[i] == valueToFind) {
                result[pointer] = i;
                pointer++;
            }

        return result;
    }


    public void printCases(Vector3[] points, double x, double y, double z) {
        System.out.println("Delta of Point(" + x + ", " + y + ", " + z + ") is: "
                + getDelta(points[0], points[1], points[2], new Vector3(x, y, z)) + " and the cube is"
                + (isBeingSplit(points[0], points[1], points[2], x, y, z, (byte) 2) ? "" : " NOT ")
                + "being split! (at depth 2)");

    }

    public String toString() {
        if (this.isFullyFilled)
            return this.address;
        else
            return "-";
    }

    protected byte countBits(boolean bits[]) {
        byte count = 0;
        for (boolean bit : bits) {
            if (bit)
                count++;
        }
        return count;
    }

    public void splitCube(byte tileNumber, byte detailDepth, boolean keepPositiveDelta) {
       // Matze this.fillInnerCubes();
        if (tileNumber == 6 || tileNumber == 9) {
            // execute 2 splits
            return;
        }

        this.recursiveSplitting(tileNumber, detailDepth, (double) 0, (double) 0, (double) 0, keepPositiveDelta);
 
    }

    private void recursiveSplitting(byte tileNumber, byte detailDepth, double absX, double absY, double absZ,
            boolean keepPositiveDelta) {
    	
    	// Matze: the sub cube has already been visited and marked as fully filled
    	if (this.isFullyFilled ) return;
    	
        Vector3 planePoints[] = getPlanePointsFromTile(tileNumber);

        if (isBeingSplit(planePoints[0], planePoints[1], planePoints[2], absX, absY, absZ, this.depth)) {
            if (this.depth < detailDepth) {
                double minStep = getMinimalStepForDepth((byte)(this.depth + 1));
                if (!this.hasChildren) {  // Matze
                    this.fillInnerCubes();
                }
                else {
                	// Matze
                	this.isValid = true;
                }
                for (byte x = 0; x < 3; x++) {
                    for (byte y = 0; y < 3; y++) {
                        for (byte z = 0; z < 3; z++) {
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
            else {
            	// Matze am 29.09.2021, aber wird wohl nicht benötigt, raum auch immer?
            	// also auskommentiert
            //	this.isFullyFilled = true;
            //   this.isValid = true;
            }
                        
        }
        if (this.hasChildren) {

        	byte countValid = 0;
        	
            boolean temp = true;
            for (byte iterX = 0; iterX < 3; iterX++) {
                for (byte iterY = 0; iterY < 3; iterY++) {
                    for (byte iterZ = 0; iterZ < 3; iterZ++) {
                     	
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

    private void setFullyFilled(byte depth, byte threasholdChildren) {
        
    	if (this.depth < depth) {
	    	for (byte iterX = 0; iterX < 3; iterX++) {
	            for (byte iterY = 0; iterY < 3; iterY++) {
	                for (byte iterZ = 0; iterZ < 3; iterZ++) {
	                	if (this.innerCubes[iterX][iterY][iterZ] != null) {
	                		this.innerCubes[iterX][iterY][iterZ].setFullyFilled( depth, threasholdChildren);
	                	}
	                }
	            }
	        }
    	}
    	else {
    		if (this.countValidChildren >= threasholdChildren) {
    			this.isFullyFilled = true;
    			this.isValid = true;
    			this.hasChildren = true;
    			
     		}
    	}

    }
    
    public ArrayList<String> getEndAddresses() {
        ArrayList<String> addresses = new ArrayList<String>();

        recursiveAddressStep(addresses);

        return addresses;
    }

    private ArrayList<String> recursiveAddressStep(ArrayList<String> addresses) {
 
        if (this.isFullyFilled) {
            addresses.add(this.address);
            return addresses;
        }

    	for (byte iterX = 0; iterX < 3; iterX++) {
            for (byte iterY = 0; iterY < 3; iterY++) {
                for (byte iterZ = 0; iterZ < 3; iterZ++) {
                    if (this.hasChildren) {
                        if (!this.innerCubes[iterX][iterY][iterZ].hasChildren
                                && this.innerCubes[iterX][iterY][iterZ].isValid) {
                            addresses.add(this.innerCubes[iterX][iterY][iterZ].address);
                        } else if (this.innerCubes[iterX][iterY][iterZ].hasChildren && this.isValid) {
                        	this.innerCubes[iterX][iterY][iterZ].recursiveAddressStep( addresses);
                        }
                    }
                }
            }
        }
        return addresses;
    }
    
    private void fillTheGround(byte heightGround) {
    	byte max;
    	if (this.size == CUBE_SIZE_TILE_LARGE) {
    		max = 27;
    	}
    	else if (this.size == CUBE_SIZE_TILE_MEDIUM) {
    		max = 3;
    	}
    	else
    		return;
    	
    	fillTheGround( heightGround, max);
    }
    
    private void fillTheGround(byte heightGround, byte divideBy) {
    	
    	if (heightGround == 0 || divideBy == 0) {
    		this.hasChildren = false;
    		this.isValid = false;
    		return;
    	}
    	
    	byte rest = heightGround;
    	byte levelsToFill = (byte) (rest / divideBy);
    	rest = (byte) (rest % divideBy);
    	
 
	       this.hasChildren = true;
           for (byte x = 0; x < 3; x++) {
                for (byte y = 0; y < 3; y++) {
                    for (byte z = 0; z < 3; z++) {
                        this.innerCubes[x][y][z] = getNewInstance(size, this.heightOffset);
                        this.innerCubes[x][y][z].depth = (byte) (this.depth + 1);
                        this.innerCubes[x][y][z].address = this.address + getLetter(x, y, z);
                        this.innerCubes[x][y][z].isFullyFilled = true;
                        this.innerCubes[x][y][z].hasChildren = false;
                    }
                }
           }
           if (levelsToFill == 2) {
	              for (byte x = 0; x < 3; x++) {
	                  for (byte y = 0; y < 3; y++) {
                          this.innerCubes[x][y][2].isFullyFilled = false;
                          this.innerCubes[x][y][2].hasChildren = true;
                          this.innerCubes[x][y][2].fillTheGround(rest, (byte) (divideBy / 3));
	                  }
	              }
    		}
    		else if (levelsToFill == 1) {    
 	              for (byte x = 0; x < 3; x++) {
	                  for (byte y = 0; y < 3; y++) {
                          this.innerCubes[x][y][2].isFullyFilled = false;
                          this.innerCubes[x][y][2].hasChildren = false;
                          this.innerCubes[x][y][2].isValid = false;
                          this.innerCubes[x][y][1].isFullyFilled = false;
                          this.innerCubes[x][y][1].hasChildren = true;
                          this.innerCubes[x][y][1].fillTheGround(rest, (byte) (divideBy / 3));
	                  }
	              }
    		}
    		else if (levelsToFill == 0) {    
 	              for (byte x = 0; x < 3; x++) {
	                  for (byte y = 0; y < 3; y++) {
	                      for (byte z = 1; z < 3; z++) {
	                          this.innerCubes[x][y][z].isFullyFilled = false;
	                          this.innerCubes[x][y][z].hasChildren = false;
	                          this.innerCubes[x][y][z].isValid = false;
	                      }
                          this.innerCubes[x][y][0].isFullyFilled = false;
                          this.innerCubes[x][y][0].hasChildren = true;
                          this.innerCubes[x][y][0].fillTheGround(rest, (byte) (divideBy / 3));
	                  }
	              }
    		}
    }

    public static void main(String args[]) {
    	byte detailDepth = 5;
    	byte heightOffset = 10;
        Cube cube = new CubeStandard(CUBE_SIZE_TILE_LARGE, heightOffset);

        cube.initResultPoints();
        cube.fillTheGround(heightOffset);
        cube.splitCube((byte) 1, detailDepth, true);

        
  		if (cube.size < CUBE_SIZE_TILE_LARGE) {
  	        cube.setFullyFilled( (byte) (detailDepth - 1), (byte) 14); 
 	        cube.setFullyFilled( (byte) (detailDepth - 2), (byte) 26); 
 	        for (byte depth = (byte) (detailDepth - 3); depth > 0; depth--) {
 	            cube.setFullyFilled( depth, (byte) 27);
 	        }
 		}
 
        ArrayList<String> addr = cube.getEndAddresses();
        int countAdr = 0;
        for (String address : addr) {
        	countAdr++;
        	/*
        	if (address.equals("U")) {
        		int mybreak;
        		mybreak = 0;
        	}
        	*/
//	       	if (address.length() < detailDepth) {
	        		System.out.println(address);
//	       	}
        }
        System.out.println(countAdr);
    }

}



 




