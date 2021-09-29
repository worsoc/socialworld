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

public class CubeStandard extends Cube {

	private static final Vector3[] coordinatesForBit_small = {
			new Vector3(CUBE_SIZE_TILE_SMALL, 0, 0),
			new Vector3(0, 0, 0),
			new Vector3(CUBE_SIZE_TILE_SMALL, CUBE_SIZE_TILE_SMALL, 0),
			new Vector3(0, CUBE_SIZE_TILE_SMALL, 0),
	};
			
	private static final Vector3[] coordinatesForBit_medium = {
			new Vector3(CUBE_SIZE_TILE_MEDIUM, 0, 0),
			new Vector3(0, 0, 0),
			new Vector3(CUBE_SIZE_TILE_MEDIUM, CUBE_SIZE_TILE_MEDIUM, 0),
			new Vector3(0, CUBE_SIZE_TILE_MEDIUM, 0),
	};

	private static final Vector3[] coordinatesForBit_large = {
			new Vector3(CUBE_SIZE_TILE_LARGE, 0, 0),
			new Vector3(0, 0, 0),
			new Vector3(CUBE_SIZE_TILE_LARGE, CUBE_SIZE_TILE_LARGE, 0),
			new Vector3(0, CUBE_SIZE_TILE_LARGE, 0),
	};

	
	private static Vector3[] resultPoints_bit0; 
	private static Vector3[] resultPoints_bit1; 
	private static Vector3[] resultPoints_bit2; 
	private static Vector3[] resultPoints_bit3; 

	private static Vector3[] resultPoints_noBit0; 
	private static Vector3[] resultPoints_noBit1; 
	private static Vector3[] resultPoints_noBit2; 
	private static Vector3[] resultPoints_noBit3; 
	
	
	public CubeStandard( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}
	
    protected Cube getNewInstance(byte size,  byte heightOffset) {
    	return new CubeStandard(size, heightOffset);
    }

    protected void initResultPoints() {
    	
    	resultPoints_bit0 = new Vector3[3];
    	resultPoints_bit0[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_bit0[0].z = 1 + this.heightOffset;

    	resultPoints_bit0[1] = new Vector3(0,0,0);
    	resultPoints_bit0[1].x = Math.abs(resultPoints_bit0[0].x - this.size);
    	resultPoints_bit0[1].y = resultPoints_bit0[0].y;
    	resultPoints_bit0[1].z = 0 + this.heightOffset;

    	resultPoints_bit0[2] = new Vector3(0,0,0);
    	resultPoints_bit0[2].x = resultPoints_bit0[0].x;
    	resultPoints_bit0[2].y = Math.abs(resultPoints_bit0[0].y - this.size);
    	resultPoints_bit0[2].z = 0 + this.heightOffset;

    	
    	resultPoints_bit1 = new Vector3[3];
    	resultPoints_bit1[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_bit1[0].z = 1 + this.heightOffset;

    	resultPoints_bit1[1] = new Vector3(0,0,0);
    	resultPoints_bit1[1].x = Math.abs(resultPoints_bit1[0].x - this.size);
    	resultPoints_bit1[1].y = resultPoints_bit1[0].y;
    	resultPoints_bit1[1].z = 0 + this.heightOffset;

    	resultPoints_bit1[2] = new Vector3(0,0,0);
    	resultPoints_bit1[2].x = resultPoints_bit1[0].x;
    	resultPoints_bit1[2].y = Math.abs(resultPoints_bit1[0].y - this.size);
    	resultPoints_bit1[2].z = 0 + this.heightOffset;

    	
       	resultPoints_bit2 = new Vector3[3];
    	resultPoints_bit2[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_bit2[0].z = 1 + this.heightOffset;

    	resultPoints_bit2[1] = new Vector3(0,0,0);
    	resultPoints_bit2[1].x = Math.abs(resultPoints_bit2[0].x - this.size);
    	resultPoints_bit2[1].y = resultPoints_bit2[0].y;
    	resultPoints_bit2[1].z = 0 + this.heightOffset;

    	resultPoints_bit2[2] = new Vector3(0,0,0);
    	resultPoints_bit2[2].x = resultPoints_bit2[0].x;
    	resultPoints_bit2[2].y = Math.abs(resultPoints_bit2[0].y - this.size);
    	resultPoints_bit2[2].z = 0 + this.heightOffset;

    	
       	resultPoints_bit3 = new Vector3[3];
    	resultPoints_bit3[0] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_bit3[0].z = 1 + this.heightOffset;

    	resultPoints_bit3[1] = new Vector3(0,0,0);
    	resultPoints_bit3[1].x = Math.abs(resultPoints_bit3[0].x - this.size);
    	resultPoints_bit3[1].y = resultPoints_bit3[0].y;
    	resultPoints_bit3[1].z = 0 + this.heightOffset;

    	resultPoints_bit3[2] = new Vector3(0,0,0);
    	resultPoints_bit3[2].x = resultPoints_bit3[0].x;
    	resultPoints_bit3[2].y = Math.abs(resultPoints_bit3[0].y - this.size);
    	resultPoints_bit3[2].z = 0 + this.heightOffset;

    	
    	///////////
    	
    	resultPoints_noBit0 = new Vector3[3];
    	resultPoints_noBit0[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit0[0].z = 1 + this.heightOffset;

    	resultPoints_noBit0[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit0[1].z = 1 + this.heightOffset;

    	resultPoints_noBit0[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit0[2].z = 0 + this.heightOffset;

    	
    	resultPoints_noBit1 = new Vector3[3];
    	resultPoints_noBit1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit1[0].z = 1 + this.heightOffset;

    	resultPoints_noBit1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit1[1].z = 1 + this.heightOffset;

    	resultPoints_noBit1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit1[2].z = 0 + this.heightOffset;

    	
    	resultPoints_noBit2 = new Vector3[3];
    	resultPoints_noBit2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit2[0].z = 1 + this.heightOffset;

    	resultPoints_noBit2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit2[1].z = 1 + this.heightOffset;

    	resultPoints_noBit2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit2[2].z = 0 + this.heightOffset;

    	
    	resultPoints_noBit3 = new Vector3[3];
    	resultPoints_noBit3[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit3[0].z = 1 + this.heightOffset;

    	resultPoints_noBit3[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit3[1].z = 1 + this.heightOffset;

    	resultPoints_noBit3[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit3[2].z = 0 + this.heightOffset;

    }
    
    protected  Vector3[] getCornerOffsets(byte depth) {
        Vector3[] corners = new Vector3[8];
        double stepSize = getMinimalStepForDepth(depth); // / this.size am 26.09.2021
        byte[] minAndMax = {0, 1};
        byte x;
        byte y;
        byte z;
        int i = 0;
        for (byte a = 0; a <= 1; a++) {
            for (byte b = 0; b <= 1; b++) {
                for (byte c = 0; c <= 1; c++) {
                	x = minAndMax[a];
                	y = minAndMax[b];
                	z = minAndMax[c];
                    // / 3 * level
                    corners[i] = new Vector3(x * stepSize, y * stepSize, z * stepSize  );
                    i++;
                }
            }
        }

        return corners;
    }

    
    protected Vector3 coordinatesOfBitsIndex(byte index) {
        // only the first and second values (the x value and the y value) are important 
       
    	switch(this.size) {
    	case CUBE_SIZE_TILE_SMALL:
    		return coordinatesForBit_small[index];
    	case CUBE_SIZE_TILE_MEDIUM:
    		return coordinatesForBit_medium[index];
    	case CUBE_SIZE_TILE_LARGE:
    		return coordinatesForBit_large[index];
    	}
    	
    	return coordinatesForBit_small[1];
        
    }

    
    protected Vector3[] getPlanePointsFromTile(byte bitsNumber) {
    	
        /* create bits from Number */
        Vector3 resultPoints[];
        
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
        
        byte count = countBits(bits);
        byte bit;
        
        switch (count) {
        case 1:
            bit = findIndexBits(true, bits);
            
            switch (bit) {
            case  0 : return resultPoints_bit0;
            case  1 : return resultPoints_bit1;
            case  2 : return resultPoints_bit2;
            case  3 : return resultPoints_bit3;
            }
            break;
        case 3:
            bit = findIndexBits(false, bits);
            
            switch (bit) {
            case  0 : return resultPoints_noBit0;
            case  1 : return resultPoints_noBit1;
            case  2 : return resultPoints_noBit2;
            case  3 : return resultPoints_noBit3;
            }
            break;
        default:
        	return getPlanePointsFromTile(bits);
        }

        return resultPoints_bit1;
    }
    
    private Vector3[] getPlanePointsFromTile(boolean bits[]) {
        /* create bits from Number */
        Vector3 resultPoints[] = new Vector3[3];
        byte count = countBits(bits);
        /* fill points -> 0,0,0 (default) */
        for (byte i = 0; i < 3; i++)
            resultPoints[i] = new Vector3(i, i, 0);

        switch (count) {
             case 2: {
                // get all positive indecies
                byte indecies[] = findAllIndeciesBits(true, bits);

                for (byte i = 0; i < 2; i++) {
                    resultPoints[i] = new Vector3(coordinatesOfBitsIndex(indecies[i]));
                    resultPoints[i].z = 1;
                }
                resultPoints[2] = new Vector3(resultPoints[0].x, resultPoints[1].y, 0);
                if ((bits[0] && bits[1]) || (bits[1] && bits[3]) || (bits[2] && bits[3]) || (bits[0] && bits[2]))
                    resultPoints[2] = new Vector3((resultPoints[0].x + 1) % 2, (resultPoints[1].y + 1) % 2, 0);
                break;
            }
          }

        /* if vector [] length = 1 -> there is only one Plane to calcualte */
        return resultPoints;

    }

    
    
}
