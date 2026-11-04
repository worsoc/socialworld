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
import java.util.List;

public class CubeStandard extends Cube {

	
	
	private List<Vector3[]> planes_bit0; 
	private List<Vector3[]> planes_bit1; 
	private List<Vector3[]> planes_bit2; 
	private List<Vector3[]> planes_bit3; 

	private List<Vector3[]> planes_noBit0; 
	private List<Vector3[]> planes_noBit1; 
	private List<Vector3[]> planes_noBit2; 
	private List<Vector3[]> planes_noBit3; 
	
	private List<Vector3[]> planes_bits_01; 
	private List<Vector3[]> planes_bits_02; 
	private List<Vector3[]> planes_bits_13; 
	private List<Vector3[]> planes_bits_23;
	private List<Vector3[]> planes_bits_12;
	private List<Vector3[]> planes_bits_03;

	private List<Vector3[]> planes_flat;
	private List<Vector3[]> planes_flat_raised;

	private List<Vector3[]> planes_sw_to_no_2_levels;
	private List<Vector3[]> planes_no_to_sw_2_levels;
	private List<Vector3[]> planes_nw_to_so_2_levels;
	private List<Vector3[]> planes_so_to_nw_2_levels;

	public CubeStandard( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
		initPlanes();
		
		 
	}
	
    protected void initPlanes() {
    	
    	Vector3[] resultPoints_bit0; 
    	Vector3[] resultPoints_bit1; 
    	Vector3[] resultPoints_bit2; 
    	Vector3[] resultPoints_bit3; 

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

    	planes_bit0 = new ArrayList<Vector3[]> ();
    	planes_bit0.add(resultPoints_bit0);
    	
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

       	planes_bit1 = new ArrayList<Vector3[]> ();
    	planes_bit1.add(resultPoints_bit1);
   	
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

       	planes_bit2 = new ArrayList<Vector3[]> ();
    	planes_bit2.add(resultPoints_bit2);
   	
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

       	planes_bit3 = new ArrayList<Vector3[]> ();
    	planes_bit3.add(resultPoints_bit3);

 
    	///////////
    	
       	Vector3[] resultPoints_noBit0; 
    	Vector3[] resultPoints_noBit1; 
    	Vector3[] resultPoints_noBit2; 
    	Vector3[] resultPoints_noBit3; 


    	resultPoints_noBit0 = new Vector3[3];
    	resultPoints_noBit0[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit0[0].z = 1 + this.heightOffset;

    	resultPoints_noBit0[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit0[1].z = 1 + this.heightOffset;

    	resultPoints_noBit0[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit0[2].z = 0 + this.heightOffset;

       	planes_noBit0 = new ArrayList<Vector3[]> ();
       	planes_noBit0.add(resultPoints_noBit0);
       	planes_noBit0.add(getFlatPlaneAtHeightOne((byte)1, (byte)2, (byte)3));
   	
    	resultPoints_noBit1 = new Vector3[3];
    	resultPoints_noBit1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit1[0].z = 1 + this.heightOffset;

    	resultPoints_noBit1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit1[1].z = 1 + this.heightOffset;

    	resultPoints_noBit1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit1[2].z = 0 + this.heightOffset;

       	planes_noBit1 = new ArrayList<Vector3[]> ();
       	planes_noBit1.add(resultPoints_noBit1);
       	planes_noBit1.add(getFlatPlaneAtHeightOne((byte)0, (byte)3, (byte)2));
    	
    	resultPoints_noBit2 = new Vector3[3];
    	resultPoints_noBit2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit2[0].z = 1 + this.heightOffset;

    	resultPoints_noBit2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit2[1].z = 1 + this.heightOffset;

    	resultPoints_noBit2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit2[2].z = 0 + this.heightOffset;

       	planes_noBit2 = new ArrayList<Vector3[]> ();
       	planes_noBit2.add(resultPoints_noBit2);
       	planes_noBit2.add(getFlatPlaneAtHeightOne((byte)0, (byte)3, (byte)1));
   	
    	resultPoints_noBit3 = new Vector3[3];
    	resultPoints_noBit3[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit3[0].z = 1 + this.heightOffset;

    	resultPoints_noBit3[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit3[1].z = 1 + this.heightOffset;

    	resultPoints_noBit3[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit3[2].z = 0 + this.heightOffset;

       	planes_noBit3 = new ArrayList<Vector3[]> ();
       	planes_noBit3.add(resultPoints_noBit3);
       	planes_noBit3.add(getFlatPlaneAtHeightOne((byte)1, (byte)2, (byte)0));

    	///////////
      	
    	Vector3[] resultPoints_bits_01; 
    	Vector3[] resultPoints_bits_02; 
    	Vector3[] resultPoints_bits_13; 
    	Vector3[] resultPoints_bits_23; 
       	
       	resultPoints_bits_01 = new Vector3[3];
       	resultPoints_bits_01[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
       	resultPoints_bits_01[0].z = 1 + this.heightOffset;

       	resultPoints_bits_01[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
       	resultPoints_bits_01[1].z = 1 + this.heightOffset;
       	
       	resultPoints_bits_01[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
       	resultPoints_bits_01[2].z = 0 + this.heightOffset;

      	planes_bits_01 = new ArrayList<Vector3[]> ();
      	planes_bits_01.add(resultPoints_bits_01);

      	
       	resultPoints_bits_02 = new Vector3[3];
       	resultPoints_bits_02[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
       	resultPoints_bits_02[0].z = 1 + this.heightOffset;

       	resultPoints_bits_02[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
       	resultPoints_bits_02[1].z = 1 + this.heightOffset;
       	
       	resultPoints_bits_02[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
       	resultPoints_bits_02[2].z = 0 + this.heightOffset;

      	planes_bits_02 = new ArrayList<Vector3[]> ();
      	planes_bits_02.add(resultPoints_bits_02);
      	
      	
      	resultPoints_bits_13 = new Vector3[3];
       	resultPoints_bits_13[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
       	resultPoints_bits_13[0].z = 1 + this.heightOffset;

       	resultPoints_bits_13[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
       	resultPoints_bits_13[1].z = 1 + this.heightOffset;
       	
       	resultPoints_bits_13[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
       	resultPoints_bits_13[2].z = 0 + this.heightOffset;

      	planes_bits_13 = new ArrayList<Vector3[]> ();
      	planes_bits_13.add(resultPoints_bits_13);

      	
      	resultPoints_bits_23 = new Vector3[3];
      	resultPoints_bits_23[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
      	resultPoints_bits_23[0].z = 1 + this.heightOffset;

      	resultPoints_bits_23[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
      	resultPoints_bits_23[1].z = 1 + this.heightOffset;
       	
      	resultPoints_bits_23[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
      	resultPoints_bits_23[2].z = 0 + this.heightOffset;

      	planes_bits_23 = new ArrayList<Vector3[]> ();
      	planes_bits_23.add(resultPoints_bits_23);


      	// two opposite corners are raised: a ridge along the diagonal between them
      	planes_bits_12 = new ArrayList<Vector3[]> ();
      	planes_bits_12.add(resultPoints_noBit3);
      	planes_bits_12.add(resultPoints_noBit0);

      	planes_bits_03 = new ArrayList<Vector3[]> ();
      	planes_bits_03.add(resultPoints_noBit2);
      	planes_bits_03.add(resultPoints_noBit1);

      	
      	// the tiles 0 and 15 are flat
      	planes_flat = getPlaneThroughCorners(0, 0, 0);
      	planes_flat_raised = getPlaneThroughCorners(1, 1, 1);

      	// the tiles 16 ... 19 rise by 2 levels along a diagonal
      	planes_sw_to_no_2_levels = getPlaneThroughCorners(0, -1, 1);
      	planes_no_to_sw_2_levels = getPlaneThroughCorners(0, 1, -1);
      	planes_nw_to_so_2_levels = getPlaneThroughCorners(1, 0, 0);
      	planes_so_to_nw_2_levels = getPlaneThroughCorners(-1, 0, 0);

    }

    /*
     * With three raised corners the sloping plane would climb on to height 2 at the
     * fourth corner. The height change of a tile is always 1, so this flat plane
     * limits the surface there, independent of the tile size.
     */
    /*
     * The heights are relative to the height offset, as in the MapCreationTool. The
     * height of the corner of bit 3 (NW) follows, as all four corners are in one plane.
     */
    private List<Vector3[]> getPlaneThroughCorners(int heightBit0, int heightBit1, int heightBit2) {
    	Vector3[] points = new Vector3[3];
    	points[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	points[0].z = heightBit0 + this.heightOffset;
    	points[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	points[1].z = heightBit1 + this.heightOffset;
    	points[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	points[2].z = heightBit2 + this.heightOffset;
    	
    	List<Vector3[]> planes = new ArrayList<Vector3[]> ();
    	planes.add(points);
    	return planes;
    }
    
    static String describeTile(int tileNumber) {
    	switch (tileNumber) {
    	case 0 : return "eben";
    	case 15 : return "eben, ein Level höher";
    	case 16 : return "steigt um 2 Level von SW nach NO";
    	case 17 : return "steigt um 2 Level von NO nach SW";
    	case 18 : return "steigt um 2 Level von NW nach SO";
    	case 19 : return "steigt um 2 Level von SO nach NW";
    	}
    	
    	String[] corners = { "SO", "SW", "NO", "NW" };
    	StringBuilder raised = new StringBuilder();
    	for (int bit = 0; bit < corners.length; bit++) {
    		if (((tileNumber >> bit) & 1) == 1) {
    			raised.append(raised.length() == 0 ? "" : ", ").append(corners[bit]);
    		}
    	}
    	return "hoch: " + raised;
    }
    
    private boolean isTileWithTwoLevels(int tileNumber) {
    	return tileNumber >= 16 && tileNumber <= 19;
    }
    
    private void checkTileWithTwoLevelsIsPossible(int tileNumber) {
    	if (this.size == CUBE_SIZE_TILE_SMALL) {
    		throw new IllegalArgumentException("Tile " + tileNumber + " rises by 2 levels, which is impossible on a small tile.");
    	}
    	if (this.heightOffset < 1) {
    		throw new IllegalArgumentException("Tile " + tileNumber + " reaches 1 level below the height offset, so the height offset must be at least 1.");
    	}
    }
    
    @Override
    protected byte getGroundHeightForTile(int tileNumber) {
    	if (isTileWithTwoLevels(tileNumber)) {
    		checkTileWithTwoLevelsIsPossible(tileNumber);
    		return (byte) (this.heightOffset - 1);
    	}
    	return this.heightOffset;
    }
    
    private Vector3[] getFlatPlaneAtHeightOne(byte bitIndexA, byte bitIndexB, byte bitIndexC) {
    	Vector3[] points = new Vector3[3];
    	points[0] = new Vector3(coordinatesOfBitsIndex(bitIndexA));
    	points[0].z = 1 + this.heightOffset;
    	points[1] = new Vector3(coordinatesOfBitsIndex(bitIndexB));
    	points[1].z = 1 + this.heightOffset;
    	points[2] = new Vector3(coordinatesOfBitsIndex(bitIndexC));
    	points[2].z = 1 + this.heightOffset;
    	return points;
    }

    protected List<Vector3[]> getPlanesForTile(int bitsNumber) {
    	
    	if (bitsNumber == 0) return planes_flat;
    	if (bitsNumber == 15) return planes_flat_raised;
    	
    	if (isTileWithTwoLevels(bitsNumber)) {
    		checkTileWithTwoLevelsIsPossible(bitsNumber);
    		switch (bitsNumber) {
    		case 16 : return planes_sw_to_no_2_levels;
    		case 17 : return planes_no_to_sw_2_levels;
    		case 18 : return planes_nw_to_so_2_levels;
    		default : return planes_so_to_nw_2_levels;
    		}
    	}
    	
        /* create bits from Number */
        
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
            case  0 : return planes_bit0;
            case  1 : return planes_bit1;
            case  2 : return planes_bit2;
            case  3 : return planes_bit3;
            }
            break;
        case 2:
            // get all positive indices
            byte indices[] = findAllIndicesBits(true, bits);
       	
            if ((indices[0] == 0) && (indices[1] == 1))
            	return planes_bits_01;
            else if ((indices[0] == 0) && (indices[1] == 2))
            	return planes_bits_02;
            else if ((indices[0] == 1) && (indices[1] == 3))
            	return planes_bits_13;
            else if ((indices[0] == 2) && (indices[1] == 3))
            	return planes_bits_23;
            else if ((indices[0] == 1) && (indices[1] == 2))
            	return planes_bits_12;
            else if ((indices[0] == 0) && (indices[1] == 3))
            	return planes_bits_03;
            break;
        case 3:
            bit = findIndexBits(false, bits);
            
            switch (bit) {
            case  0 : return planes_noBit0;
            case  1 : return planes_noBit1;
            case  2 : return planes_noBit2;
            case  3 : return planes_noBit3;
            }
            break;
        default:
        	return null;
        }

        return null;
    }

   
    
}
