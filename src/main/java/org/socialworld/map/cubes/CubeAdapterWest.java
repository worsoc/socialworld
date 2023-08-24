/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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

public class CubeAdapterWest extends Cube {

	// 121 ... 129
	private static List<Vector3[]> planes_nw_to_sw_00__1; 
	private static List<Vector3[]> planes_nw_to_sw_00__2; 
	private static List<Vector3[]> planes_nw_to_sw_00__3; 
	private static List<Vector3[]> planes_nw_to_sw_00__4; 
	private static List<Vector3[]> planes_nw_to_sw_00__5; 
	private static List<Vector3[]> planes_nw_to_sw_00__6; 
	private static List<Vector3[]> planes_nw_to_sw_00__7; 
	private static List<Vector3[]> planes_nw_to_sw_00__8; 
	private static List<Vector3[]> planes_nw_to_sw_00__9; 
	
	// 321 ... 329
	private static List<Vector3[]> planes_nw_to_sw_11__1; 
	private static List<Vector3[]> planes_nw_to_sw_11__2; 
	private static List<Vector3[]> planes_nw_to_sw_11__3; 
	private static List<Vector3[]> planes_nw_to_sw_11__4; 
	private static List<Vector3[]> planes_nw_to_sw_11__5; 
	private static List<Vector3[]> planes_nw_to_sw_11__6; 
	private static List<Vector3[]> planes_nw_to_sw_11__7; 
	private static List<Vector3[]> planes_nw_to_sw_11__8; 
	private static List<Vector3[]> planes_nw_to_sw_11__9; 

	// 149 ... 141
	private static List<Vector3[]> planes_sw_to_nw_00__9; 
	private static List<Vector3[]> planes_sw_to_nw_00__8; 
	private static List<Vector3[]> planes_sw_to_nw_00__7; 
	private static List<Vector3[]> planes_sw_to_nw_00__6; 
	private static List<Vector3[]> planes_sw_to_nw_00__5; 
	private static List<Vector3[]> planes_sw_to_nw_00__4; 
	private static List<Vector3[]> planes_sw_to_nw_00__3; 
	private static List<Vector3[]> planes_sw_to_nw_00__2; 
	private static List<Vector3[]> planes_sw_to_nw_00__1; 
	
	// 349 ... 341
	private static List<Vector3[]> planes_sw_to_nw_11__9; 
	private static List<Vector3[]> planes_sw_to_nw_11__8; 
	private static List<Vector3[]> planes_sw_to_nw_11__7; 
	private static List<Vector3[]> planes_sw_to_nw_11__6; 
	private static List<Vector3[]> planes_sw_to_nw_11__5; 
	private static List<Vector3[]> planes_sw_to_nw_11__4; 
	private static List<Vector3[]> planes_sw_to_nw_11__3; 
	private static List<Vector3[]> planes_sw_to_nw_11__2; 
	private static List<Vector3[]> planes_sw_to_nw_11__1; 

	public CubeAdapterWest( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}

	@Override
	protected Cube getNewInstance(byte size, byte heightOffset) {
    	return new CubeAdapterWest(size, heightOffset);
	}

	@Override
	protected void initPlanes() {

		initPlanes_NW_2_SW_0_0();
		initPlanes_NW_2_SW_1_1();
		initPlanes_SW_2_NW_0_0();
		initPlanes_SW_2_NW_1_1();

	}

	@Override
	protected List<Vector3[]> getPlanesForTile(int bitsNumber) {

		switch ( bitsNumber ) {
		case 121 :
			return planes_nw_to_sw_00__1;
		case 122 :
			return planes_nw_to_sw_00__2;
		case 123 :
			return planes_nw_to_sw_00__3;
		case 124 :
			return planes_nw_to_sw_00__4;
		case 125 :
			return planes_nw_to_sw_00__5;
		case 126 :
			return planes_nw_to_sw_00__6;
		case 127 :
			return planes_nw_to_sw_00__7;
		case 128 :
			return planes_nw_to_sw_00__8;
		case 129 :
			return planes_nw_to_sw_00__9;
		case 321 : 
			return planes_nw_to_sw_11__1;
		case 322 :
			return planes_nw_to_sw_11__2;
		case 323 :
			return planes_nw_to_sw_11__3;
		case 324 : 
			return planes_nw_to_sw_11__4;
		case 325 :
			return planes_nw_to_sw_11__5;
		case 326 : 
			return planes_nw_to_sw_11__6;
		case 327 : 
			return planes_nw_to_sw_11__7;
		case 328 :
			return planes_nw_to_sw_11__8;
		case 329 :
			return planes_nw_to_sw_11__9;
		case 141 :
			return planes_sw_to_nw_00__1;
		case 142 :
			return planes_sw_to_nw_00__2;
		case 143 :
			return planes_sw_to_nw_00__3;
		case 144 :
			return planes_sw_to_nw_00__4;
		case 145 : 
			return planes_sw_to_nw_00__5;
		case 146 :
			return planes_sw_to_nw_00__6;
		case 147 :
			return planes_sw_to_nw_00__7;
		case 148 :
			return planes_sw_to_nw_00__8;
		case 149 :
			return planes_sw_to_nw_00__9;
		case 341 :
			return planes_sw_to_nw_11__1;
		case 342 :
			return planes_sw_to_nw_11__2;
		case 343 :
			return planes_sw_to_nw_11__3;
		case 344 :
			return planes_sw_to_nw_11__4;
		case 345 :
			return planes_sw_to_nw_11__5;
		case 346 :
			return planes_sw_to_nw_11__6;
		case 347 :
			return planes_sw_to_nw_11__7;
		case 348 :
			return planes_sw_to_nw_11__8;
		case 349 :
			return planes_sw_to_nw_11__9;
		}
	
	

		return planes_nw_to_sw_00__1;
	}

	private void initPlanes_NW_2_SW_0_0() {

		Vector3[] resultPoints_nw_to_sw_plane1; 
		Vector3[] resultPoints_nw_to_sw_plane2; 

		resultPoints_nw_to_sw_plane1 = new Vector3[3];
		resultPoints_nw_to_sw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_sw_plane1[0].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_nw_to_sw_plane1[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_sw_plane1[2].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane2 = new Vector3[3];
		resultPoints_nw_to_sw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_sw_plane2[0].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_sw_plane2[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_nw_to_sw_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 121
		planes_nw_to_sw_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_1_pln1 = new Vector3[3];
		pts_nw_2_sw_1_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_1_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_1_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_nw_2_sw_1_pln2 = new Vector3[3];
		pts_nw_2_sw_1_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_1_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_1_pln2[1].addToZ((double)0/(double)9);
		pts_nw_2_sw_1_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_nw_to_sw_00__1.add(pts_nw_2_sw_1_pln1);
		planes_nw_to_sw_00__1.add(pts_nw_2_sw_1_pln2);

		// TileNumber 122
		planes_nw_to_sw_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_2_pln1 = new Vector3[3];
		pts_nw_2_sw_2_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_2_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_2_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_nw_2_sw_2_pln2 = new Vector3[3];
		pts_nw_2_sw_2_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_2_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_2_pln2[1].addToZ((double)1/(double)9);
		pts_nw_2_sw_2_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_nw_to_sw_00__2.add(pts_nw_2_sw_2_pln1);
		planes_nw_to_sw_00__2.add(pts_nw_2_sw_2_pln2);
		
		
		// TileNumber 123
		planes_nw_to_sw_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_3_pln1 = new Vector3[3];
		pts_nw_2_sw_3_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_3_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_3_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_nw_2_sw_3_pln2 = new Vector3[3];
		pts_nw_2_sw_3_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_3_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_3_pln2[1].addToZ((double)2/(double)9);
		pts_nw_2_sw_3_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_nw_to_sw_00__3.add(pts_nw_2_sw_3_pln1);
		planes_nw_to_sw_00__3.add(pts_nw_2_sw_3_pln2);
		
		
		// TileNumber 124
		planes_nw_to_sw_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_4_pln1 = new Vector3[3];
		pts_nw_2_sw_4_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_4_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_4_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_nw_2_sw_4_pln2 = new Vector3[3];
		pts_nw_2_sw_4_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_4_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_4_pln2[1].addToZ((double)3/(double)9);
		pts_nw_2_sw_4_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_nw_to_sw_00__4.add(pts_nw_2_sw_4_pln1);
		planes_nw_to_sw_00__4.add(pts_nw_2_sw_4_pln2);
		
		
		// TileNumber 125
		planes_nw_to_sw_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_5_pln1 = new Vector3[3];
		pts_nw_2_sw_5_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_5_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_5_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_nw_2_sw_5_pln2 = new Vector3[3];
		pts_nw_2_sw_5_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_5_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_5_pln2[1].addToZ((double)4/(double)9);
		pts_nw_2_sw_5_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_nw_to_sw_00__5.add(pts_nw_2_sw_5_pln1);
		planes_nw_to_sw_00__5.add(pts_nw_2_sw_5_pln2);
		
		
		// TileNumber 126
		planes_nw_to_sw_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_6_pln1 = new Vector3[3];
		pts_nw_2_sw_6_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_6_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_6_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_nw_2_sw_6_pln2 = new Vector3[3];
		pts_nw_2_sw_6_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_6_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_6_pln2[1].addToZ((double)5/(double)9);
		pts_nw_2_sw_6_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_nw_to_sw_00__6.add(pts_nw_2_sw_6_pln1);
		planes_nw_to_sw_00__6.add(pts_nw_2_sw_6_pln2);
		
		
		// TileNumber 127
		planes_nw_to_sw_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_7_pln1 = new Vector3[3];
		pts_nw_2_sw_7_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_7_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_7_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_nw_2_sw_7_pln2 = new Vector3[3];
		pts_nw_2_sw_7_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_7_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_7_pln2[1].addToZ((double)6/(double)9);
		pts_nw_2_sw_7_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_nw_to_sw_00__7.add(pts_nw_2_sw_7_pln1);
		planes_nw_to_sw_00__7.add(pts_nw_2_sw_7_pln2);
		
		// TileNumber 128
		planes_nw_to_sw_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_8_pln1 = new Vector3[3];
		pts_nw_2_sw_8_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_8_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_8_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_nw_2_sw_8_pln2 = new Vector3[3];
		pts_nw_2_sw_8_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_8_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_8_pln2[1].addToZ((double)7/(double)9);
		pts_nw_2_sw_8_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_nw_to_sw_00__8.add(pts_nw_2_sw_8_pln1);
		planes_nw_to_sw_00__8.add(pts_nw_2_sw_8_pln2);
		
		
		// TileNumber 129
		planes_nw_to_sw_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_9_pln1 = new Vector3[3];
		pts_nw_2_sw_9_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_9_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_9_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_nw_2_sw_9_pln2 = new Vector3[3];
		pts_nw_2_sw_9_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_9_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_9_pln2[1].addToZ((double)8/(double)9);
		pts_nw_2_sw_9_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_nw_to_sw_00__9.add(pts_nw_2_sw_9_pln1);
		planes_nw_to_sw_00__9.add(pts_nw_2_sw_9_pln2);
		
		
	}	
	
	private void initPlanes_NW_2_SW_1_1() {

		Vector3[] resultPoints_nw_to_sw_plane1; 
		Vector3[] resultPoints_nw_to_sw_plane2; 

		resultPoints_nw_to_sw_plane1 = new Vector3[3];
		resultPoints_nw_to_sw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_sw_plane1[0].z = 1 + this.heightOffset;

		resultPoints_nw_to_sw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_nw_to_sw_plane1[1].z = 1 + this.heightOffset;

		resultPoints_nw_to_sw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_sw_plane1[2].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane2 = new Vector3[3];
		resultPoints_nw_to_sw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_sw_plane2[0].z = 1 + this.heightOffset;

		resultPoints_nw_to_sw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_sw_plane2[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_sw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_nw_to_sw_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 321
		planes_nw_to_sw_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_1_pln1 = new Vector3[3];
		pts_nw_2_sw_1_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_1_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_1_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_nw_2_sw_1_pln2 = new Vector3[3];
		pts_nw_2_sw_1_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_1_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_1_pln2[1].addToZ((double)0/(double)9);
		pts_nw_2_sw_1_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_nw_to_sw_11__1.add(pts_nw_2_sw_1_pln1);
		planes_nw_to_sw_11__1.add(pts_nw_2_sw_1_pln2);

		// TileNumber 322
		planes_nw_to_sw_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_2_pln1 = new Vector3[3];
		pts_nw_2_sw_2_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_2_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_2_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_nw_2_sw_2_pln2 = new Vector3[3];
		pts_nw_2_sw_2_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_2_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_2_pln2[1].addToZ((double)1/(double)9);
		pts_nw_2_sw_2_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_nw_to_sw_11__2.add(pts_nw_2_sw_2_pln1);
		planes_nw_to_sw_11__2.add(pts_nw_2_sw_2_pln2);
		
		
		// TileNumber 323
		planes_nw_to_sw_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_3_pln1 = new Vector3[3];
		pts_nw_2_sw_3_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_3_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_3_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_nw_2_sw_3_pln2 = new Vector3[3];
		pts_nw_2_sw_3_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_3_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_3_pln2[1].addToZ((double)2/(double)9);
		pts_nw_2_sw_3_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_nw_to_sw_11__3.add(pts_nw_2_sw_3_pln1);
		planes_nw_to_sw_11__3.add(pts_nw_2_sw_3_pln2);
		
		
		// TileNumber 324
		planes_nw_to_sw_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_4_pln1 = new Vector3[3];
		pts_nw_2_sw_4_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_4_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_4_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_nw_2_sw_4_pln2 = new Vector3[3];
		pts_nw_2_sw_4_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_4_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_4_pln2[1].addToZ((double)3/(double)9);
		pts_nw_2_sw_4_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_nw_to_sw_11__4.add(pts_nw_2_sw_4_pln1);
		planes_nw_to_sw_11__4.add(pts_nw_2_sw_4_pln2);
		
		
		// TileNumber 325
		planes_nw_to_sw_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_5_pln1 = new Vector3[3];
		pts_nw_2_sw_5_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_5_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_5_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_nw_2_sw_5_pln2 = new Vector3[3];
		pts_nw_2_sw_5_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_5_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_5_pln2[1].addToZ((double)4/(double)9);
		pts_nw_2_sw_5_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_nw_to_sw_11__5.add(pts_nw_2_sw_5_pln1);
		planes_nw_to_sw_11__5.add(pts_nw_2_sw_5_pln2);
		
		
		// TileNumber 326
		planes_nw_to_sw_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_6_pln1 = new Vector3[3];
		pts_nw_2_sw_6_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_6_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_6_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_nw_2_sw_6_pln2 = new Vector3[3];
		pts_nw_2_sw_6_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_6_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_6_pln2[1].addToZ((double)5/(double)9);
		pts_nw_2_sw_6_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_nw_to_sw_11__6.add(pts_nw_2_sw_6_pln1);
		planes_nw_to_sw_11__6.add(pts_nw_2_sw_6_pln2);
		
		
		// TileNumber 327
		planes_nw_to_sw_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_7_pln1 = new Vector3[3];
		pts_nw_2_sw_7_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_7_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_7_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_nw_2_sw_7_pln2 = new Vector3[3];
		pts_nw_2_sw_7_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_7_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_7_pln2[1].addToZ((double)6/(double)9);
		pts_nw_2_sw_7_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_nw_to_sw_11__7.add(pts_nw_2_sw_7_pln1);
		planes_nw_to_sw_11__7.add(pts_nw_2_sw_7_pln2);

		
		// TileNumber 328
		planes_nw_to_sw_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_8_pln1 = new Vector3[3];
		pts_nw_2_sw_8_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_8_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_8_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_nw_2_sw_8_pln2 = new Vector3[3];
		pts_nw_2_sw_8_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_8_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_8_pln2[1].addToZ((double)7/(double)9);
		pts_nw_2_sw_8_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_nw_to_sw_11__8.add(pts_nw_2_sw_8_pln1);
		planes_nw_to_sw_11__8.add(pts_nw_2_sw_8_pln2);
		
		
		// TileNumber 329
		planes_nw_to_sw_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_sw_9_pln1 = new Vector3[3];
		pts_nw_2_sw_9_pln1[0] = resultPoints_nw_to_sw_plane1[0];
		pts_nw_2_sw_9_pln1[1] = resultPoints_nw_to_sw_plane1[1];
		pts_nw_2_sw_9_pln1[2] = new Vector3(resultPoints_nw_to_sw_plane1[2]);
		pts_nw_2_sw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_nw_2_sw_9_pln2 = new Vector3[3];
		pts_nw_2_sw_9_pln2[0] = resultPoints_nw_to_sw_plane2[0];
		pts_nw_2_sw_9_pln2[1] = new Vector3(resultPoints_nw_to_sw_plane2[1]);
		pts_nw_2_sw_9_pln2[1].addToZ((double)8/(double)9);
		pts_nw_2_sw_9_pln2[2] = new Vector3(resultPoints_nw_to_sw_plane2[2]);
		pts_nw_2_sw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_nw_to_sw_11__9.add(pts_nw_2_sw_9_pln1);
		planes_nw_to_sw_11__9.add(pts_nw_2_sw_9_pln2);
		
	}	

	private void initPlanes_SW_2_NW_0_0() {

		Vector3[] resultPoints_sw_to_nw_plane1; 
		Vector3[] resultPoints_sw_to_nw_plane2; 

		resultPoints_sw_to_nw_plane1 = new Vector3[3];
		resultPoints_sw_to_nw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_nw_plane1[0].z = 0 + this.heightOffset;

		resultPoints_sw_to_nw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_nw_plane1[1].z = 0 + this.heightOffset;

		resultPoints_sw_to_nw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_nw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_sw_to_nw_plane2 = new Vector3[3];
		resultPoints_sw_to_nw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_nw_plane2[0].z = 0 + this.heightOffset;

		resultPoints_sw_to_nw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_nw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_sw_to_nw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_nw_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 141
		planes_sw_to_nw_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_1_pln1 = new Vector3[3];
		pts_sw_2_nw_1_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_1_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_1_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_sw_2_nw_1_pln2 = new Vector3[3];
		pts_sw_2_nw_1_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_1_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_1_pln2[1].addToZ((double)0/(double)9);
		pts_sw_2_nw_1_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_sw_to_nw_00__1.add(pts_sw_2_nw_1_pln1);
		planes_sw_to_nw_00__1.add(pts_sw_2_nw_1_pln2);

		
		// TileNumber 142
		planes_sw_to_nw_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_2_pln1 = new Vector3[3];
		pts_sw_2_nw_2_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_2_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_2_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_sw_2_nw_2_pln2 = new Vector3[3];
		pts_sw_2_nw_2_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_2_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_2_pln2[1].addToZ((double)1/(double)9);
		pts_sw_2_nw_2_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_sw_to_nw_00__2.add(pts_sw_2_nw_2_pln1);
		planes_sw_to_nw_00__2.add(pts_sw_2_nw_2_pln2);
		
		
		// TileNumber 143
		planes_sw_to_nw_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_3_pln1 = new Vector3[3];
		pts_sw_2_nw_3_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_3_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_3_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_sw_2_nw_3_pln2 = new Vector3[3];
		pts_sw_2_nw_3_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_3_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_3_pln2[1].addToZ((double)2/(double)9);
		pts_sw_2_nw_3_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_sw_to_nw_00__3.add(pts_sw_2_nw_3_pln1);
		planes_sw_to_nw_00__3.add(pts_sw_2_nw_3_pln2);
		
		
		// TileNumber 144
		planes_sw_to_nw_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_4_pln1 = new Vector3[3];
		pts_sw_2_nw_4_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_4_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_4_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_sw_2_nw_4_pln2 = new Vector3[3];
		pts_sw_2_nw_4_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_4_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_4_pln2[1].addToZ((double)3/(double)9);
		pts_sw_2_nw_4_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_sw_to_nw_00__4.add(pts_sw_2_nw_4_pln1);
		planes_sw_to_nw_00__4.add(pts_sw_2_nw_4_pln2);
		

		// TileNumber 145
		planes_sw_to_nw_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_5_pln1 = new Vector3[3];
		pts_sw_2_nw_5_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_5_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_5_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_sw_2_nw_5_pln2 = new Vector3[3];
		pts_sw_2_nw_5_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_5_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_5_pln2[1].addToZ((double)4/(double)9);
		pts_sw_2_nw_5_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_sw_to_nw_00__5.add(pts_sw_2_nw_5_pln1);
		planes_sw_to_nw_00__5.add(pts_sw_2_nw_5_pln2);
		
		
		// TileNumber 146
		planes_sw_to_nw_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_6_pln1 = new Vector3[3];
		pts_sw_2_nw_6_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_6_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_6_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_sw_2_nw_6_pln2 = new Vector3[3];
		pts_sw_2_nw_6_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_6_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_6_pln2[1].addToZ((double)5/(double)9);
		pts_sw_2_nw_6_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_sw_to_nw_00__6.add(pts_sw_2_nw_6_pln1);
		planes_sw_to_nw_00__6.add(pts_sw_2_nw_6_pln2);
		
		
		// TileNumber 147
		planes_sw_to_nw_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_7_pln1 = new Vector3[3];
		pts_sw_2_nw_7_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_7_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_7_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_sw_2_nw_7_pln2 = new Vector3[3];
		pts_sw_2_nw_7_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_7_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_7_pln2[1].addToZ((double)6/(double)9);
		pts_sw_2_nw_7_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_sw_to_nw_00__7.add(pts_sw_2_nw_7_pln1);
		planes_sw_to_nw_00__7.add(pts_sw_2_nw_7_pln2);
		

		// TileNumber 148
		planes_sw_to_nw_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_8_pln1 = new Vector3[3];
		pts_sw_2_nw_8_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_8_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_8_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_sw_2_nw_8_pln2 = new Vector3[3];
		pts_sw_2_nw_8_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_8_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_8_pln2[1].addToZ((double)7/(double)9);
		pts_sw_2_nw_8_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_sw_to_nw_00__8.add(pts_sw_2_nw_8_pln1);
		planes_sw_to_nw_00__8.add(pts_sw_2_nw_8_pln2);
		
		
		// TileNumber 149
		planes_sw_to_nw_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_9_pln1 = new Vector3[3];
		pts_sw_2_nw_9_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_9_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_9_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_sw_2_nw_9_pln2 = new Vector3[3];
		pts_sw_2_nw_9_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_9_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_9_pln2[1].addToZ((double)8/(double)9);
		pts_sw_2_nw_9_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_sw_to_nw_00__9.add(pts_sw_2_nw_9_pln1);
		planes_sw_to_nw_00__9.add(pts_sw_2_nw_9_pln2);


	}	

	private void initPlanes_SW_2_NW_1_1() {

		Vector3[] resultPoints_sw_to_nw_plane1; 
		Vector3[] resultPoints_sw_to_nw_plane2; 

		resultPoints_sw_to_nw_plane1 = new Vector3[3];
		resultPoints_sw_to_nw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_nw_plane1[0].z = 1 + this.heightOffset;

		resultPoints_sw_to_nw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_nw_plane1[1].z = 1 + this.heightOffset;

		resultPoints_sw_to_nw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_nw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_sw_to_nw_plane2 = new Vector3[3];
		resultPoints_sw_to_nw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_nw_plane2[0].z = 1 + this.heightOffset;

		resultPoints_sw_to_nw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_nw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_sw_to_nw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_nw_plane2[2].z = 0 + this.heightOffset;

		
		
		// TileNumber 341
		planes_sw_to_nw_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_1_pln1 = new Vector3[3];
		pts_sw_2_nw_1_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_1_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_1_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_sw_2_nw_1_pln2 = new Vector3[3];
		pts_sw_2_nw_1_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_1_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_1_pln2[1].addToZ((double)0/(double)9);
		pts_sw_2_nw_1_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_sw_to_nw_11__1.add(pts_sw_2_nw_1_pln1);
		planes_sw_to_nw_11__1.add(pts_sw_2_nw_1_pln2);

		
		// TileNumber 342
		planes_sw_to_nw_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_2_pln1 = new Vector3[3];
		pts_sw_2_nw_2_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_2_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_2_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_sw_2_nw_2_pln2 = new Vector3[3];
		pts_sw_2_nw_2_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_2_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_2_pln2[1].addToZ((double)1/(double)9);
		pts_sw_2_nw_2_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_sw_to_nw_11__2.add(pts_sw_2_nw_2_pln1);
		planes_sw_to_nw_11__2.add(pts_sw_2_nw_2_pln2);
		
		
		// TileNumber 343
		planes_sw_to_nw_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_3_pln1 = new Vector3[3];
		pts_sw_2_nw_3_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_3_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_3_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_sw_2_nw_3_pln2 = new Vector3[3];
		pts_sw_2_nw_3_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_3_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_3_pln2[1].addToZ((double)2/(double)9);
		pts_sw_2_nw_3_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_sw_to_nw_11__3.add(pts_sw_2_nw_3_pln1);
		planes_sw_to_nw_11__3.add(pts_sw_2_nw_3_pln2);
		
		
		// TileNumber 344
		planes_sw_to_nw_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_4_pln1 = new Vector3[3];
		pts_sw_2_nw_4_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_4_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_4_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_sw_2_nw_4_pln2 = new Vector3[3];
		pts_sw_2_nw_4_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_4_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_4_pln2[1].addToZ((double)3/(double)9);
		pts_sw_2_nw_4_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_sw_to_nw_11__4.add(pts_sw_2_nw_4_pln1);
		planes_sw_to_nw_11__4.add(pts_sw_2_nw_4_pln2);
		
		
		// TileNumber 345
		planes_sw_to_nw_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_5_pln1 = new Vector3[3];
		pts_sw_2_nw_5_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_5_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_5_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_sw_2_nw_5_pln2 = new Vector3[3];
		pts_sw_2_nw_5_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_5_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_5_pln2[1].addToZ((double)4/(double)9);
		pts_sw_2_nw_5_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_sw_to_nw_11__5.add(pts_sw_2_nw_5_pln1);
		planes_sw_to_nw_11__5.add(pts_sw_2_nw_5_pln2);
		
		
		// TileNumber 346
		planes_sw_to_nw_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_6_pln1 = new Vector3[3];
		pts_sw_2_nw_6_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_6_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_6_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_sw_2_nw_6_pln2 = new Vector3[3];
		pts_sw_2_nw_6_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_6_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_6_pln2[1].addToZ((double)5/(double)9);
		pts_sw_2_nw_6_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_sw_to_nw_11__6.add(pts_sw_2_nw_6_pln1);
		planes_sw_to_nw_11__6.add(pts_sw_2_nw_6_pln2);
		
		
		// TileNumber 347
		planes_sw_to_nw_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_7_pln1 = new Vector3[3];
		pts_sw_2_nw_7_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_7_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_7_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_sw_2_nw_7_pln2 = new Vector3[3];
		pts_sw_2_nw_7_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_7_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_7_pln2[1].addToZ((double)6/(double)9);
		pts_sw_2_nw_7_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_sw_to_nw_11__7.add(pts_sw_2_nw_7_pln1);
		planes_sw_to_nw_11__7.add(pts_sw_2_nw_7_pln2);
		
		
		// TileNumber 348
		planes_sw_to_nw_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_8_pln1 = new Vector3[3];
		pts_sw_2_nw_8_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_8_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_8_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_sw_2_nw_8_pln2 = new Vector3[3];
		pts_sw_2_nw_8_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_8_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_8_pln2[1].addToZ((double)7/(double)9);
		pts_sw_2_nw_8_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_sw_to_nw_11__8.add(pts_sw_2_nw_8_pln1);
		planes_sw_to_nw_11__8.add(pts_sw_2_nw_8_pln2);
		
		
		// TileNumber 349
		planes_sw_to_nw_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_nw_9_pln1 = new Vector3[3];
		pts_sw_2_nw_9_pln1[0] = resultPoints_sw_to_nw_plane1[0];
		pts_sw_2_nw_9_pln1[1] = resultPoints_sw_to_nw_plane1[1];
		pts_sw_2_nw_9_pln1[2] = new Vector3(resultPoints_sw_to_nw_plane1[2]);
		pts_sw_2_nw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_sw_2_nw_9_pln2 = new Vector3[3];
		pts_sw_2_nw_9_pln2[0] = resultPoints_sw_to_nw_plane2[0];
		pts_sw_2_nw_9_pln2[1] = new Vector3(resultPoints_sw_to_nw_plane2[1]);
		pts_sw_2_nw_9_pln2[1].addToZ((double)8/(double)9);
		pts_sw_2_nw_9_pln2[2] = new Vector3(resultPoints_sw_to_nw_plane2[2]);
		pts_sw_2_nw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_sw_to_nw_11__9.add(pts_sw_2_nw_9_pln1);
		planes_sw_to_nw_11__9.add(pts_sw_2_nw_9_pln2);

		
		
	}	

}
