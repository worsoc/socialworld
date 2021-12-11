/*
* Social World
* Copyright (C) 2021  Jesper Springer, Mathias Sikos
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

public class CubeAdapterNorth extends Cube {

	// 101 ... 109
	private static List<Vector3[]> planes_nw_to_ne_00__1; 
	private static List<Vector3[]> planes_nw_to_ne_00__2; 
	private static List<Vector3[]> planes_nw_to_ne_00__3; 
	private static List<Vector3[]> planes_nw_to_ne_00__4; 
	private static List<Vector3[]> planes_nw_to_ne_00__5; 
	private static List<Vector3[]> planes_nw_to_ne_00__6; 
	private static List<Vector3[]> planes_nw_to_ne_00__7; 
	private static List<Vector3[]> planes_nw_to_ne_00__8; 
	private static List<Vector3[]> planes_nw_to_ne_00__9; 
	
	private static List<Vector3[]> planes_nw_to_ne_11__1; 
	private static List<Vector3[]> planes_nw_to_ne_11__2; 
	private static List<Vector3[]> planes_nw_to_ne_11__3; 
	private static List<Vector3[]> planes_nw_to_ne_11__4; 
	private static List<Vector3[]> planes_nw_to_ne_11__5; 
	private static List<Vector3[]> planes_nw_to_ne_11__6; 
	private static List<Vector3[]> planes_nw_to_ne_11__7; 
	private static List<Vector3[]> planes_nw_to_ne_11__8; 
	private static List<Vector3[]> planes_nw_to_ne_11__9; 
	
	private static List<Vector3[]> planes_ne_to_nw_00__1; 
	private static List<Vector3[]> planes_ne_to_nw_00__2; 
	private static List<Vector3[]> planes_ne_to_nw_00__3; 
	private static List<Vector3[]> planes_ne_to_nw_00__4; 
	private static List<Vector3[]> planes_ne_to_nw_00__5; 
	private static List<Vector3[]> planes_ne_to_nw_00__6; 
	private static List<Vector3[]> planes_ne_to_nw_00__7; 
	private static List<Vector3[]> planes_ne_to_nw_00__8; 
	private static List<Vector3[]> planes_ne_to_nw_00__9;
	
	private static List<Vector3[]> planes_ne_to_nw_11__1; 
	private static List<Vector3[]> planes_ne_to_nw_11__2; 
	private static List<Vector3[]> planes_ne_to_nw_11__3; 
	private static List<Vector3[]> planes_ne_to_nw_11__4; 
	private static List<Vector3[]> planes_ne_to_nw_11__5; 
	private static List<Vector3[]> planes_ne_to_nw_11__6; 
	private static List<Vector3[]> planes_ne_to_nw_11__7; 
	private static List<Vector3[]> planes_ne_to_nw_11__8; 
	private static List<Vector3[]> planes_ne_to_nw_11__9;
	
	
	
	public CubeAdapterNorth( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}

	@Override
	protected Cube getNewInstance(byte size, byte heightOffset) {
    	return new CubeAdapterNorth(size, heightOffset);
	}

	@Override
	protected void initPlanes() {

		initPlanes_NW_2_NE_0_0();
		initPlanes_NW_2_NE_1_1();
		initPlanes_NE_2_NW_0_0();
		initPlanes_NE_2_NW_1_1();
	}

	@Override
	protected List<Vector3[]> getPlanesForTile(int bitsNumber) {
		
		switch ( bitsNumber ) {
			case 101 :
				return planes_nw_to_ne_00__1;
			case 102 :
				return planes_nw_to_ne_00__2;
			case 103 :
				return planes_nw_to_ne_00__3;
			case 104 :
				return planes_nw_to_ne_00__4;
			case 105 :
				return planes_nw_to_ne_00__5;
			case 106 :
				return planes_nw_to_ne_00__6;
			case 107 :
				return planes_nw_to_ne_00__7;
			case 108 :
				return planes_nw_to_ne_00__8;
			case 109 :
				return planes_nw_to_ne_00__9;
			case 301 : 
				return planes_nw_to_ne_11__1;
			case 302 :
				return planes_nw_to_ne_11__2;
			case 303 :
				return planes_nw_to_ne_11__3;
			case 304 : 
				return planes_nw_to_ne_11__4;
			case 305 :
				return planes_nw_to_ne_11__5;
			case 306 : 
				return planes_nw_to_ne_11__6;
			case 307 : 
				return planes_nw_to_ne_11__7;
			case 308 :
				return planes_nw_to_ne_11__8;
			case 309 :
				return planes_nw_to_ne_11__9;
			case 131 :
				return planes_ne_to_nw_00__1;
			case 132 :
				return planes_ne_to_nw_00__2;
			case 133 :
				return planes_ne_to_nw_00__3;
			case 134 :
				return planes_ne_to_nw_00__4;
			case 135 : 
				return planes_ne_to_nw_00__5;
			case 136 :
				return planes_ne_to_nw_00__6;
			case 137 :
				return planes_ne_to_nw_00__7;
			case 138 :
				return planes_ne_to_nw_00__8;
			case 139 :
				return planes_ne_to_nw_00__9;
			case 331 :
				return planes_ne_to_nw_11__1;
			case 332 :
				return planes_ne_to_nw_11__2;
			case 333 :
				return planes_ne_to_nw_11__3;
			case 334 :
				return planes_ne_to_nw_11__4;
			case 335 :
				return planes_ne_to_nw_11__5;
			case 336 :
				return planes_ne_to_nw_11__6;
			case 337 :
				return planes_ne_to_nw_11__7;
			case 338 :
				return planes_ne_to_nw_11__8;
			case 339 :
				return planes_ne_to_nw_11__9;
		}
		
		
	
		return planes_nw_to_ne_00__1;
	}



	private void initPlanes_NW_2_NE_0_0() {

		Vector3[] resultPoints_nw_to_ne_plane1; 
		Vector3[] resultPoints_nw_to_ne_plane2; 

		resultPoints_nw_to_ne_plane1 = new Vector3[3];
		resultPoints_nw_to_ne_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_ne_plane1[0].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_nw_to_ne_plane1[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_ne_plane1[2].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane2 = new Vector3[3];
		resultPoints_nw_to_ne_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_ne_plane2[0].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_ne_plane2[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_nw_to_ne_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 101
		planes_nw_to_ne_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_1_pln1 = new Vector3[3];
		pts_nw_2_ne_1_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_1_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_1_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_nw_2_ne_1_pln2 = new Vector3[3];
		pts_nw_2_ne_1_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_1_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_1_pln2[1].addToZ((double)0/(double)9);
		pts_nw_2_ne_1_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_nw_to_ne_00__1.add(pts_nw_2_ne_1_pln1);
		planes_nw_to_ne_00__1.add(pts_nw_2_ne_1_pln2);

		
		// TileNumber 102
		planes_nw_to_ne_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_2_pln1 = new Vector3[3];
		pts_nw_2_ne_2_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_2_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_2_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_nw_2_ne_2_pln2 = new Vector3[3];
		pts_nw_2_ne_2_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_2_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_2_pln2[1].addToZ((double)1/(double)9);
		pts_nw_2_ne_2_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_nw_to_ne_00__2.add(pts_nw_2_ne_2_pln1);
		planes_nw_to_ne_00__2.add(pts_nw_2_ne_2_pln2);
		
		
		
		// TileNumber 103
		planes_nw_to_ne_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_3_pln1 = new Vector3[3];
		pts_nw_2_ne_3_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_3_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_3_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_nw_2_ne_3_pln2 = new Vector3[3];
		pts_nw_2_ne_3_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_3_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_3_pln2[1].addToZ((double)2/(double)9);
		pts_nw_2_ne_3_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_nw_to_ne_00__3.add(pts_nw_2_ne_3_pln1);
		planes_nw_to_ne_00__3.add(pts_nw_2_ne_3_pln2);
		
		
		// TileNumber 104
		planes_nw_to_ne_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_4_pln1 = new Vector3[3];
		pts_nw_2_ne_4_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_4_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_4_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_nw_2_ne_4_pln2 = new Vector3[3];
		pts_nw_2_ne_4_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_4_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_4_pln2[1].addToZ((double)3/(double)9);
		pts_nw_2_ne_4_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_nw_to_ne_00__4.add(pts_nw_2_ne_4_pln1);
		planes_nw_to_ne_00__4.add(pts_nw_2_ne_4_pln2);
		
		
		// TileNumber 105
		planes_nw_to_ne_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_5_pln1 = new Vector3[3];
		pts_nw_2_ne_5_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_5_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_5_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_nw_2_ne_5_pln2 = new Vector3[3];
		pts_nw_2_ne_5_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_5_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_5_pln2[1].addToZ((double)4/(double)9);
		pts_nw_2_ne_5_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_nw_to_ne_00__5.add(pts_nw_2_ne_5_pln1);
		planes_nw_to_ne_00__5.add(pts_nw_2_ne_5_pln2);
		
		
		// TileNumber 106
		planes_nw_to_ne_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_6_pln1 = new Vector3[3];
		pts_nw_2_ne_6_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_6_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_6_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_nw_2_ne_6_pln2 = new Vector3[3];
		pts_nw_2_ne_6_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_6_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_6_pln2[1].addToZ((double)5/(double)9);
		pts_nw_2_ne_6_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_nw_to_ne_00__6.add(pts_nw_2_ne_6_pln1);
		planes_nw_to_ne_00__6.add(pts_nw_2_ne_6_pln2);
		
		
		// TileNumber 107
		planes_nw_to_ne_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_7_pln1 = new Vector3[3];
		pts_nw_2_ne_7_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_7_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_7_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_nw_2_ne_7_pln2 = new Vector3[3];
		pts_nw_2_ne_7_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_7_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_7_pln2[1].addToZ((double)6/(double)9);
		pts_nw_2_ne_7_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_nw_to_ne_00__7.add(pts_nw_2_ne_7_pln1);
		planes_nw_to_ne_00__7.add(pts_nw_2_ne_7_pln2);
		
		// TileNumber 108
		planes_nw_to_ne_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_8_pln1 = new Vector3[3];
		pts_nw_2_ne_8_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_8_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_8_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_nw_2_ne_8_pln2 = new Vector3[3];
		pts_nw_2_ne_8_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_8_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_8_pln2[1].addToZ((double)7/(double)9);
		pts_nw_2_ne_8_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_nw_to_ne_00__8.add(pts_nw_2_ne_8_pln1);
		planes_nw_to_ne_00__8.add(pts_nw_2_ne_8_pln2);
		
		
		// TileNumber 109
		planes_nw_to_ne_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_9_pln1 = new Vector3[3];
		pts_nw_2_ne_9_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_9_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_9_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_nw_2_ne_9_pln2 = new Vector3[3];
		pts_nw_2_ne_9_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_9_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_9_pln2[1].addToZ((double)8/(double)9);
		pts_nw_2_ne_9_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_nw_to_ne_00__9.add(pts_nw_2_ne_9_pln1);
		planes_nw_to_ne_00__9.add(pts_nw_2_ne_9_pln2);

	}

	private void initPlanes_NW_2_NE_1_1() {
		Vector3[] resultPoints_nw_to_ne_plane1; 
		Vector3[] resultPoints_nw_to_ne_plane2; 

		resultPoints_nw_to_ne_plane1 = new Vector3[3];
		resultPoints_nw_to_ne_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_ne_plane1[0].z = 1 + this.heightOffset;

		resultPoints_nw_to_ne_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_nw_to_ne_plane1[1].z = 1 + this.heightOffset;

		resultPoints_nw_to_ne_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_ne_plane1[2].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane2 = new Vector3[3];
		resultPoints_nw_to_ne_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_nw_to_ne_plane2[0].z = 1 + this.heightOffset;

		resultPoints_nw_to_ne_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_nw_to_ne_plane2[1].z = 0 + this.heightOffset;

		resultPoints_nw_to_ne_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_nw_to_ne_plane2[2].z = (1/9) + this.heightOffset;
	
		
		// TileNumber 301
		planes_nw_to_ne_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_1_pln1 = new Vector3[3];
		pts_nw_2_ne_1_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_1_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_1_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_nw_2_ne_1_pln2 = new Vector3[3];
		pts_nw_2_ne_1_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_1_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_1_pln2[1].addToZ((double)0/(double)9);
		pts_nw_2_ne_1_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_nw_to_ne_11__1.add(pts_nw_2_ne_1_pln1);
		planes_nw_to_ne_11__1.add(pts_nw_2_ne_1_pln2);

		
		// TileNumber 302
		planes_nw_to_ne_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_2_pln1 = new Vector3[3];
		pts_nw_2_ne_2_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_2_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_2_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_nw_2_ne_2_pln2 = new Vector3[3];
		pts_nw_2_ne_2_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_2_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_2_pln2[1].addToZ((double)1/(double)9);
		pts_nw_2_ne_2_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_nw_to_ne_11__2.add(pts_nw_2_ne_2_pln1);
		planes_nw_to_ne_11__2.add(pts_nw_2_ne_2_pln2);
		
		
		// TileNumber 303
		planes_nw_to_ne_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_3_pln1 = new Vector3[3];
		pts_nw_2_ne_3_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_3_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_3_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_nw_2_ne_3_pln2 = new Vector3[3];
		pts_nw_2_ne_3_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_3_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_3_pln2[1].addToZ((double)2/(double)9);
		pts_nw_2_ne_3_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_nw_to_ne_11__3.add(pts_nw_2_ne_3_pln1);
		planes_nw_to_ne_11__3.add(pts_nw_2_ne_3_pln2);
		
		
		// TileNumber 304
		planes_nw_to_ne_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_4_pln1 = new Vector3[3];
		pts_nw_2_ne_4_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_4_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_4_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_nw_2_ne_4_pln2 = new Vector3[3];
		pts_nw_2_ne_4_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_4_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_4_pln2[1].addToZ((double)3/(double)9);
		pts_nw_2_ne_4_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_nw_to_ne_11__4.add(pts_nw_2_ne_4_pln1);
		planes_nw_to_ne_11__4.add(pts_nw_2_ne_4_pln2);
		
		
		// TileNumber 305
		planes_nw_to_ne_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_5_pln1 = new Vector3[3];
		pts_nw_2_ne_5_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_5_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_5_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_nw_2_ne_5_pln2 = new Vector3[3];
		pts_nw_2_ne_5_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_5_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_5_pln2[1].addToZ((double)4/(double)9);
		pts_nw_2_ne_5_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_nw_to_ne_11__5.add(pts_nw_2_ne_5_pln1);
		planes_nw_to_ne_11__5.add(pts_nw_2_ne_5_pln2);
		
		
		// TileNumber 306
		planes_nw_to_ne_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_6_pln1 = new Vector3[3];
		pts_nw_2_ne_6_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_6_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_6_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_nw_2_ne_6_pln2 = new Vector3[3];
		pts_nw_2_ne_6_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_6_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_6_pln2[1].addToZ((double)5/(double)9);
		pts_nw_2_ne_6_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_nw_to_ne_11__6.add(pts_nw_2_ne_6_pln1);
		planes_nw_to_ne_11__6.add(pts_nw_2_ne_6_pln2);
		
		
		// TileNumber 307
		planes_nw_to_ne_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_7_pln1 = new Vector3[3];
		pts_nw_2_ne_7_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_7_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_7_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_nw_2_ne_7_pln2 = new Vector3[3];
		pts_nw_2_ne_7_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_7_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_7_pln2[1].addToZ((double)6/(double)9);
		pts_nw_2_ne_7_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_nw_to_ne_11__7.add(pts_nw_2_ne_7_pln1);
		planes_nw_to_ne_11__7.add(pts_nw_2_ne_7_pln2);

		
		// TileNumber 308
		planes_nw_to_ne_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_8_pln1 = new Vector3[3];
		pts_nw_2_ne_8_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_8_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_8_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_nw_2_ne_8_pln2 = new Vector3[3];
		pts_nw_2_ne_8_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_8_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_8_pln2[1].addToZ((double)7/(double)9);
		pts_nw_2_ne_8_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_nw_to_ne_11__8.add(pts_nw_2_ne_8_pln1);
		planes_nw_to_ne_11__8.add(pts_nw_2_ne_8_pln2);
		
		
		// TileNumber 309
		planes_nw_to_ne_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_nw_2_ne_9_pln1 = new Vector3[3];
		pts_nw_2_ne_9_pln1[0] = resultPoints_nw_to_ne_plane1[0];
		pts_nw_2_ne_9_pln1[1] = resultPoints_nw_to_ne_plane1[1];
		pts_nw_2_ne_9_pln1[2] = new Vector3(resultPoints_nw_to_ne_plane1[2]);
		pts_nw_2_ne_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_nw_2_ne_9_pln2 = new Vector3[3];
		pts_nw_2_ne_9_pln2[0] = resultPoints_nw_to_ne_plane2[0];
		pts_nw_2_ne_9_pln2[1] = new Vector3(resultPoints_nw_to_ne_plane2[1]);
		pts_nw_2_ne_9_pln2[1].addToZ((double)8/(double)9);
		pts_nw_2_ne_9_pln2[2] = new Vector3(resultPoints_nw_to_ne_plane2[2]);
		pts_nw_2_ne_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_nw_to_ne_11__9.add(pts_nw_2_ne_9_pln1);
		planes_nw_to_ne_11__9.add(pts_nw_2_ne_9_pln2);
	}

	private void initPlanes_NE_2_NW_0_0() {
		
		Vector3[] resultPoints_ne_to_nw_plane1; 
		Vector3[] resultPoints_ne_to_nw_plane2; 

		resultPoints_ne_to_nw_plane1 = new Vector3[3];
		resultPoints_ne_to_nw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_nw_plane1[0].z = 0 + this.heightOffset;

		resultPoints_ne_to_nw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_nw_plane1[1].z = 0 + this.heightOffset;

		resultPoints_ne_to_nw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_nw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_ne_to_nw_plane2 = new Vector3[3];
		resultPoints_ne_to_nw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_nw_plane2[0].z = 0 + this.heightOffset;

		resultPoints_ne_to_nw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_nw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_ne_to_nw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_nw_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 131
		planes_ne_to_nw_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_1_pln1 = new Vector3[3];
		pts_ne_2_nw_1_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_1_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_1_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_ne_2_nw_1_pln2 = new Vector3[3];
		pts_ne_2_nw_1_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_1_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_1_pln2[1].addToZ((double)0/(double)9);
		pts_ne_2_nw_1_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_ne_to_nw_00__1.add(pts_ne_2_nw_1_pln1);
		planes_ne_to_nw_00__1.add(pts_ne_2_nw_1_pln2);

		
		// TileNumber 132
		planes_ne_to_nw_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_2_pln1 = new Vector3[3];
		pts_ne_2_nw_2_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_2_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_2_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_ne_2_nw_2_pln2 = new Vector3[3];
		pts_ne_2_nw_2_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_2_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_2_pln2[1].addToZ((double)1/(double)9);
		pts_ne_2_nw_2_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_ne_to_nw_00__2.add(pts_ne_2_nw_2_pln1);
		planes_ne_to_nw_00__2.add(pts_ne_2_nw_2_pln2);
		
		
		// TileNumber 133
		planes_ne_to_nw_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_3_pln1 = new Vector3[3];
		pts_ne_2_nw_3_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_3_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_3_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_ne_2_nw_3_pln2 = new Vector3[3];
		pts_ne_2_nw_3_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_3_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_3_pln2[1].addToZ((double)2/(double)9);
		pts_ne_2_nw_3_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_ne_to_nw_00__3.add(pts_ne_2_nw_3_pln1);
		planes_ne_to_nw_00__3.add(pts_ne_2_nw_3_pln2);
		
		
		// TileNumber 134
		planes_ne_to_nw_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_4_pln1 = new Vector3[3];
		pts_ne_2_nw_4_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_4_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_4_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_ne_2_nw_4_pln2 = new Vector3[3];
		pts_ne_2_nw_4_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_4_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_4_pln2[1].addToZ((double)3/(double)9);
		pts_ne_2_nw_4_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_ne_to_nw_00__4.add(pts_ne_2_nw_4_pln1);
		planes_ne_to_nw_00__4.add(pts_ne_2_nw_4_pln2);
		

		// TileNumber 135
		planes_ne_to_nw_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_5_pln1 = new Vector3[3];
		pts_ne_2_nw_5_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_5_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_5_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_ne_2_nw_5_pln2 = new Vector3[3];
		pts_ne_2_nw_5_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_5_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_5_pln2[1].addToZ((double)4/(double)9);
		pts_ne_2_nw_5_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_ne_to_nw_00__5.add(pts_ne_2_nw_5_pln1);
		planes_ne_to_nw_00__5.add(pts_ne_2_nw_5_pln2);
		
		
		// TileNumber 136
		planes_ne_to_nw_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_6_pln1 = new Vector3[3];
		pts_ne_2_nw_6_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_6_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_6_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_ne_2_nw_6_pln2 = new Vector3[3];
		pts_ne_2_nw_6_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_6_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_6_pln2[1].addToZ((double)5/(double)9);
		pts_ne_2_nw_6_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_ne_to_nw_00__6.add(pts_ne_2_nw_6_pln1);
		planes_ne_to_nw_00__6.add(pts_ne_2_nw_6_pln2);
		
		
		// TileNumber 137
		planes_ne_to_nw_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_7_pln1 = new Vector3[3];
		pts_ne_2_nw_7_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_7_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_7_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_ne_2_nw_7_pln2 = new Vector3[3];
		pts_ne_2_nw_7_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_7_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_7_pln2[1].addToZ((double)6/(double)9);
		pts_ne_2_nw_7_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_ne_to_nw_00__7.add(pts_ne_2_nw_7_pln1);
		planes_ne_to_nw_00__7.add(pts_ne_2_nw_7_pln2);
		

		// TileNumber 138
		planes_ne_to_nw_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_8_pln1 = new Vector3[3];
		pts_ne_2_nw_8_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_8_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_8_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_ne_2_nw_8_pln2 = new Vector3[3];
		pts_ne_2_nw_8_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_8_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_8_pln2[1].addToZ((double)7/(double)9);
		pts_ne_2_nw_8_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_ne_to_nw_00__8.add(pts_ne_2_nw_8_pln1);
		planes_ne_to_nw_00__8.add(pts_ne_2_nw_8_pln2);
		
		
		// TileNumber 139
		planes_ne_to_nw_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_9_pln1 = new Vector3[3];
		pts_ne_2_nw_9_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_9_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_9_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_ne_2_nw_9_pln2 = new Vector3[3];
		pts_ne_2_nw_9_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_9_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_9_pln2[1].addToZ((double)8/(double)9);
		pts_ne_2_nw_9_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_ne_to_nw_00__9.add(pts_ne_2_nw_9_pln1);
		planes_ne_to_nw_00__9.add(pts_ne_2_nw_9_pln2);

		
	}

	private void initPlanes_NE_2_NW_1_1() {
		
		Vector3[] resultPoints_ne_to_nw_plane1; 
		Vector3[] resultPoints_ne_to_nw_plane2; 

		resultPoints_ne_to_nw_plane1 = new Vector3[3];
		resultPoints_ne_to_nw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_nw_plane1[0].z = 1 + this.heightOffset;

		resultPoints_ne_to_nw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_nw_plane1[1].z = 1 + this.heightOffset;

		resultPoints_ne_to_nw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_nw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_ne_to_nw_plane2 = new Vector3[3];
		resultPoints_ne_to_nw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_nw_plane2[0].z = 1 + this.heightOffset;

		resultPoints_ne_to_nw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_nw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_ne_to_nw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_nw_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 331
		planes_ne_to_nw_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_1_pln1 = new Vector3[3];
		pts_ne_2_nw_1_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_1_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_1_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_ne_2_nw_1_pln2 = new Vector3[3];
		pts_ne_2_nw_1_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_1_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_1_pln2[1].addToZ((double)0/(double)9);
		pts_ne_2_nw_1_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_ne_to_nw_11__1.add(pts_ne_2_nw_1_pln1);
		planes_ne_to_nw_11__1.add(pts_ne_2_nw_1_pln2);

		
		// TileNumber 332
		planes_ne_to_nw_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_2_pln1 = new Vector3[3];
		pts_ne_2_nw_2_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_2_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_2_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_ne_2_nw_2_pln2 = new Vector3[3];
		pts_ne_2_nw_2_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_2_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_2_pln2[1].addToZ((double)1/(double)9);
		pts_ne_2_nw_2_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_ne_to_nw_11__2.add(pts_ne_2_nw_2_pln1);
		planes_ne_to_nw_11__2.add(pts_ne_2_nw_2_pln2);
		
		
		// TileNumber 333
		planes_ne_to_nw_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_3_pln1 = new Vector3[3];
		pts_ne_2_nw_3_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_3_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_3_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_ne_2_nw_3_pln2 = new Vector3[3];
		pts_ne_2_nw_3_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_3_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_3_pln2[1].addToZ((double)2/(double)9);
		pts_ne_2_nw_3_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_ne_to_nw_11__3.add(pts_ne_2_nw_3_pln1);
		planes_ne_to_nw_11__3.add(pts_ne_2_nw_3_pln2);
		
		
		// TileNumber 334
		planes_ne_to_nw_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_4_pln1 = new Vector3[3];
		pts_ne_2_nw_4_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_4_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_4_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_ne_2_nw_4_pln2 = new Vector3[3];
		pts_ne_2_nw_4_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_4_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_4_pln2[1].addToZ((double)3/(double)9);
		pts_ne_2_nw_4_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_ne_to_nw_11__4.add(pts_ne_2_nw_4_pln1);
		planes_ne_to_nw_11__4.add(pts_ne_2_nw_4_pln2);
		
		
		// TileNumber 335
		planes_ne_to_nw_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_5_pln1 = new Vector3[3];
		pts_ne_2_nw_5_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_5_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_5_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_ne_2_nw_5_pln2 = new Vector3[3];
		pts_ne_2_nw_5_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_5_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_5_pln2[1].addToZ((double)4/(double)9);
		pts_ne_2_nw_5_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_ne_to_nw_11__5.add(pts_ne_2_nw_5_pln1);
		planes_ne_to_nw_11__5.add(pts_ne_2_nw_5_pln2);
		
		
		// TileNumber 336
		planes_ne_to_nw_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_6_pln1 = new Vector3[3];
		pts_ne_2_nw_6_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_6_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_6_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_ne_2_nw_6_pln2 = new Vector3[3];
		pts_ne_2_nw_6_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_6_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_6_pln2[1].addToZ((double)5/(double)9);
		pts_ne_2_nw_6_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_ne_to_nw_11__6.add(pts_ne_2_nw_6_pln1);
		planes_ne_to_nw_11__6.add(pts_ne_2_nw_6_pln2);
		
		
		// TileNumber 337
		planes_ne_to_nw_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_7_pln1 = new Vector3[3];
		pts_ne_2_nw_7_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_7_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_7_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_ne_2_nw_7_pln2 = new Vector3[3];
		pts_ne_2_nw_7_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_7_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_7_pln2[1].addToZ((double)6/(double)9);
		pts_ne_2_nw_7_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_ne_to_nw_11__7.add(pts_ne_2_nw_7_pln1);
		planes_ne_to_nw_11__7.add(pts_ne_2_nw_7_pln2);
		
		
		// TileNumber 338
		planes_ne_to_nw_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_8_pln1 = new Vector3[3];
		pts_ne_2_nw_8_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_8_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_8_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_ne_2_nw_8_pln2 = new Vector3[3];
		pts_ne_2_nw_8_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_8_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_8_pln2[1].addToZ((double)7/(double)9);
		pts_ne_2_nw_8_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_ne_to_nw_11__8.add(pts_ne_2_nw_8_pln1);
		planes_ne_to_nw_11__8.add(pts_ne_2_nw_8_pln2);
		
		
		// TileNumber 339
		planes_ne_to_nw_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_nw_9_pln1 = new Vector3[3];
		pts_ne_2_nw_9_pln1[0] = resultPoints_ne_to_nw_plane1[0];
		pts_ne_2_nw_9_pln1[1] = resultPoints_ne_to_nw_plane1[1];
		pts_ne_2_nw_9_pln1[2] = new Vector3(resultPoints_ne_to_nw_plane1[2]);
		pts_ne_2_nw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_ne_2_nw_9_pln2 = new Vector3[3];
		pts_ne_2_nw_9_pln2[0] = resultPoints_ne_to_nw_plane2[0];
		pts_ne_2_nw_9_pln2[1] = new Vector3(resultPoints_ne_to_nw_plane2[1]);
		pts_ne_2_nw_9_pln2[1].addToZ((double)8/(double)9);
		pts_ne_2_nw_9_pln2[2] = new Vector3(resultPoints_ne_to_nw_plane2[2]);
		pts_ne_2_nw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_ne_to_nw_11__9.add(pts_ne_2_nw_9_pln1);
		planes_ne_to_nw_11__9.add(pts_ne_2_nw_9_pln2);

		
		
	}



}
