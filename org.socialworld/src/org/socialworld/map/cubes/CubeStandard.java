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

	
	
	private static List<Vector3[]> planes_bit0; 
	private static List<Vector3[]> planes_bit1; 
	private static List<Vector3[]> planes_bit2; 
	private static List<Vector3[]> planes_bit3; 

	private static List<Vector3[]> planes_noBit0; 
	private static List<Vector3[]> planes_noBit1; 
	private static List<Vector3[]> planes_noBit2; 
	private static List<Vector3[]> planes_noBit3; 
	
	private static List<Vector3[]> planes_bits_01; 
	private static List<Vector3[]> planes_bits_02; 
	private static List<Vector3[]> planes_bits_13; 
	private static List<Vector3[]> planes_bits_23; 

	public CubeStandard( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
		
		 
	}
	
    protected Cube getNewInstance(byte size,  byte heightOffset) {
    	return new CubeStandard(size, heightOffset);
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
   	
    	resultPoints_noBit1 = new Vector3[3];
    	resultPoints_noBit1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit1[0].z = 1 + this.heightOffset;

    	resultPoints_noBit1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit1[1].z = 1 + this.heightOffset;

    	resultPoints_noBit1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit1[2].z = 0 + this.heightOffset;

       	planes_noBit1 = new ArrayList<Vector3[]> ();
       	planes_noBit1.add(resultPoints_noBit1);
    	
    	resultPoints_noBit2 = new Vector3[3];
    	resultPoints_noBit2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
    	resultPoints_noBit2[0].z = 1 + this.heightOffset;

    	resultPoints_noBit2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit2[1].z = 1 + this.heightOffset;

    	resultPoints_noBit2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit2[2].z = 0 + this.heightOffset;

       	planes_noBit2 = new ArrayList<Vector3[]> ();
       	planes_noBit2.add(resultPoints_noBit2);
   	
    	resultPoints_noBit3 = new Vector3[3];
    	resultPoints_noBit3[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
    	resultPoints_noBit3[0].z = 1 + this.heightOffset;

    	resultPoints_noBit3[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
    	resultPoints_noBit3[1].z = 1 + this.heightOffset;

    	resultPoints_noBit3[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
    	resultPoints_noBit3[2].z = 0 + this.heightOffset;

       	planes_noBit3 = new ArrayList<Vector3[]> ();
       	planes_noBit3.add(resultPoints_noBit3);

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

    }
    

    
 
    
    protected List<Vector3[]> getPlanesForTile(byte bitsNumber) {
    	
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
