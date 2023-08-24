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

public class CubeAdapterEast extends Cube {

	// 111 ... 119
	private static List<Vector3[]> planes_ne_to_se_00__1; 
	private static List<Vector3[]> planes_ne_to_se_00__2; 
	private static List<Vector3[]> planes_ne_to_se_00__3; 
	private static List<Vector3[]> planes_ne_to_se_00__4; 
	private static List<Vector3[]> planes_ne_to_se_00__5; 
	private static List<Vector3[]> planes_ne_to_se_00__6; 
	private static List<Vector3[]> planes_ne_to_se_00__7; 
	private static List<Vector3[]> planes_ne_to_se_00__8; 
	private static List<Vector3[]> planes_ne_to_se_00__9; 
	
	// 311 ... 319
	private static List<Vector3[]> planes_ne_to_se_11__1; 
	private static List<Vector3[]> planes_ne_to_se_11__2; 
	private static List<Vector3[]> planes_ne_to_se_11__3; 
	private static List<Vector3[]> planes_ne_to_se_11__4; 
	private static List<Vector3[]> planes_ne_to_se_11__5; 
	private static List<Vector3[]> planes_ne_to_se_11__6; 
	private static List<Vector3[]> planes_ne_to_se_11__7; 
	private static List<Vector3[]> planes_ne_to_se_11__8; 
	private static List<Vector3[]> planes_ne_to_se_11__9; 

	// 159 ... 151
	private static List<Vector3[]> planes_se_to_ne_00__9; 
	private static List<Vector3[]> planes_se_to_ne_00__8; 
	private static List<Vector3[]> planes_se_to_ne_00__7; 
	private static List<Vector3[]> planes_se_to_ne_00__6; 
	private static List<Vector3[]> planes_se_to_ne_00__5; 
	private static List<Vector3[]> planes_se_to_ne_00__4; 
	private static List<Vector3[]> planes_se_to_ne_00__3; 
	private static List<Vector3[]> planes_se_to_ne_00__2; 
	private static List<Vector3[]> planes_se_to_ne_00__1; 

	// 359 ... 351
	private static List<Vector3[]> planes_se_to_ne_11__9; 
	private static List<Vector3[]> planes_se_to_ne_11__8; 
	private static List<Vector3[]> planes_se_to_ne_11__7; 
	private static List<Vector3[]> planes_se_to_ne_11__6; 
	private static List<Vector3[]> planes_se_to_ne_11__5; 
	private static List<Vector3[]> planes_se_to_ne_11__4; 
	private static List<Vector3[]> planes_se_to_ne_11__3; 
	private static List<Vector3[]> planes_se_to_ne_11__2; 
	private static List<Vector3[]> planes_se_to_ne_11__1; 

	public CubeAdapterEast( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}

	@Override
	protected Cube getNewInstance(byte size, byte heightOffset) {
    	return new CubeAdapterEast(size, heightOffset);
	}

	@Override
	protected void initPlanes() {
		
		initPlanes_NE_2_SE_0_0();
		initPlanes_NE_2_SE_1_1();
		initPlanes_SE_2_NE_0_0();
		initPlanes_SE_2_NE_1_1();

	}


	@Override
	protected List<Vector3[]> getPlanesForTile(int bitsNumber) {
		
		switch ( bitsNumber ) {
		case 111 :
			return planes_ne_to_se_00__1;
		case 112 :
			return planes_ne_to_se_00__2;
		case 113 :
			return planes_ne_to_se_00__3;
		case 114 :
			return planes_ne_to_se_00__4;
		case 115 :
			return planes_ne_to_se_00__5;
		case 116 :
			return planes_ne_to_se_00__6;
		case 117 :
			return planes_ne_to_se_00__7;
		case 118 :
			return planes_ne_to_se_00__8;
		case 119 :
			return planes_ne_to_se_00__9;
		case 311 : 
			return planes_ne_to_se_11__1;
		case 312 :
			return planes_ne_to_se_11__2;
		case 313 :
			return planes_ne_to_se_11__3;
		case 314 : 
			return planes_ne_to_se_11__4;
		case 315 :
			return planes_ne_to_se_11__5;
		case 316 : 
			return planes_ne_to_se_11__6;
		case 317 : 
			return planes_ne_to_se_11__7;
		case 318 :
			return planes_ne_to_se_11__8;
		case 319 :
			return planes_ne_to_se_11__9;
		case 151 :
			return planes_se_to_ne_00__1;
		case 152 :
			return planes_se_to_ne_00__2;
		case 153 :
			return planes_se_to_ne_00__3;
		case 154 :
			return planes_se_to_ne_00__4;
		case 155 : 
			return planes_se_to_ne_00__5;
		case 156 :
			return planes_se_to_ne_00__6;
		case 157 :
			return planes_se_to_ne_00__7;
		case 158 :
			return planes_se_to_ne_00__8;
		case 159 :
			return planes_se_to_ne_00__9;
		case 351 :
			return planes_se_to_ne_11__1;
		case 352 :
			return planes_se_to_ne_11__2;
		case 353 :
			return planes_se_to_ne_11__3;
		case 354 :
			return planes_se_to_ne_11__4;
		case 355 :
			return planes_se_to_ne_11__5;
		case 356 :
			return planes_se_to_ne_11__6;
		case 357 :
			return planes_se_to_ne_11__7;
		case 358 :
			return planes_se_to_ne_11__8;
		case 359 :
			return planes_se_to_ne_11__9;
		}
	
	

		return planes_ne_to_se_00__1;
	}

	
	
	private void initPlanes_NE_2_SE_0_0() {

		Vector3[] resultPoints_ne_to_se_plane1; 
		Vector3[] resultPoints_ne_to_se_plane2; 

		resultPoints_ne_to_se_plane1 = new Vector3[3];
		resultPoints_ne_to_se_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_se_plane1[0].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_se_plane1[1].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_se_plane1[2].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane2 = new Vector3[3];
		resultPoints_ne_to_se_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_se_plane2[0].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_se_plane2[1].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_se_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 111
		planes_ne_to_se_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_1_pln1 = new Vector3[3];
		pts_ne_2_se_1_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_1_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_1_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_ne_2_se_1_pln2 = new Vector3[3];
		pts_ne_2_se_1_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_1_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_1_pln2[1].addToZ((double)0/(double)9);
		pts_ne_2_se_1_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_ne_to_se_00__1.add(pts_ne_2_se_1_pln1);
		planes_ne_to_se_00__1.add(pts_ne_2_se_1_pln2);

		
		// TileNumber 112
		planes_ne_to_se_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_2_pln1 = new Vector3[3];
		pts_ne_2_se_2_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_2_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_2_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_ne_2_se_2_pln2 = new Vector3[3];
		pts_ne_2_se_2_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_2_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_2_pln2[1].addToZ((double)1/(double)9);
		pts_ne_2_se_2_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_ne_to_se_00__2.add(pts_ne_2_se_2_pln1);
		planes_ne_to_se_00__2.add(pts_ne_2_se_2_pln2);
		
		
		// TileNumber 113
		planes_ne_to_se_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_3_pln1 = new Vector3[3];
		pts_ne_2_se_3_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_3_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_3_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_ne_2_se_3_pln2 = new Vector3[3];
		pts_ne_2_se_3_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_3_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_3_pln2[1].addToZ((double)2/(double)9);
		pts_ne_2_se_3_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_ne_to_se_00__3.add(pts_ne_2_se_3_pln1);
		planes_ne_to_se_00__3.add(pts_ne_2_se_3_pln2);
		
		
		// TileNumber 114
		planes_ne_to_se_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_4_pln1 = new Vector3[3];
		pts_ne_2_se_4_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_4_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_4_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_ne_2_se_4_pln2 = new Vector3[3];
		pts_ne_2_se_4_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_4_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_4_pln2[1].addToZ((double)3/(double)9);
		pts_ne_2_se_4_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_ne_to_se_00__4.add(pts_ne_2_se_4_pln1);
		planes_ne_to_se_00__4.add(pts_ne_2_se_4_pln2);
		
		
		// TileNumber 115
		planes_ne_to_se_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_5_pln1 = new Vector3[3];
		pts_ne_2_se_5_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_5_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_5_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_ne_2_se_5_pln2 = new Vector3[3];
		pts_ne_2_se_5_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_5_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_5_pln2[1].addToZ((double)4/(double)9);
		pts_ne_2_se_5_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_ne_to_se_00__5.add(pts_ne_2_se_5_pln1);
		planes_ne_to_se_00__5.add(pts_ne_2_se_5_pln2);
		
		
		// TileNumber 116
		planes_ne_to_se_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_6_pln1 = new Vector3[3];
		pts_ne_2_se_6_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_6_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_6_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_ne_2_se_6_pln2 = new Vector3[3];
		pts_ne_2_se_6_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_6_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_6_pln2[1].addToZ((double)5/(double)9);
		pts_ne_2_se_6_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_ne_to_se_00__6.add(pts_ne_2_se_6_pln1);
		planes_ne_to_se_00__6.add(pts_ne_2_se_6_pln2);
		
		
		// TileNumber 117
		planes_ne_to_se_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_7_pln1 = new Vector3[3];
		pts_ne_2_se_7_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_7_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_7_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_ne_2_se_7_pln2 = new Vector3[3];
		pts_ne_2_se_7_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_7_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_7_pln2[1].addToZ((double)6/(double)9);
		pts_ne_2_se_7_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_ne_to_se_00__7.add(pts_ne_2_se_7_pln1);
		planes_ne_to_se_00__7.add(pts_ne_2_se_7_pln2);
		
		// TileNumber 118
		planes_ne_to_se_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_8_pln1 = new Vector3[3];
		pts_ne_2_se_8_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_8_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_8_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_ne_2_se_8_pln2 = new Vector3[3];
		pts_ne_2_se_8_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_8_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_8_pln2[1].addToZ((double)7/(double)9);
		pts_ne_2_se_8_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_ne_to_se_00__8.add(pts_ne_2_se_8_pln1);
		planes_ne_to_se_00__8.add(pts_ne_2_se_8_pln2);
		
		
		// TileNumber 119
		planes_ne_to_se_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_9_pln1 = new Vector3[3];
		pts_ne_2_se_9_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_9_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_9_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_ne_2_se_9_pln2 = new Vector3[3];
		pts_ne_2_se_9_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_9_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_9_pln2[1].addToZ((double)8/(double)9);
		pts_ne_2_se_9_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_ne_to_se_00__9.add(pts_ne_2_se_9_pln1);
		planes_ne_to_se_00__9.add(pts_ne_2_se_9_pln2);
		
	}	
	
	private void initPlanes_NE_2_SE_1_1() {

		Vector3[] resultPoints_ne_to_se_plane1; 
		Vector3[] resultPoints_ne_to_se_plane2; 

		resultPoints_ne_to_se_plane1 = new Vector3[3];
		resultPoints_ne_to_se_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_se_plane1[0].z = 1 + this.heightOffset;

		resultPoints_ne_to_se_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_ne_to_se_plane1[1].z = 1 + this.heightOffset;

		resultPoints_ne_to_se_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_se_plane1[2].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane2 = new Vector3[3];
		resultPoints_ne_to_se_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_ne_to_se_plane2[0].z = 1 + this.heightOffset;

		resultPoints_ne_to_se_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_ne_to_se_plane2[1].z = 0 + this.heightOffset;

		resultPoints_ne_to_se_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_ne_to_se_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 311
		planes_ne_to_se_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_1_pln1 = new Vector3[3];
		pts_ne_2_se_1_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_1_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_1_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_ne_2_se_1_pln2 = new Vector3[3];
		pts_ne_2_se_1_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_1_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_1_pln2[1].addToZ((double)0/(double)9);
		pts_ne_2_se_1_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_ne_to_se_11__1.add(pts_ne_2_se_1_pln1);
		planes_ne_to_se_11__1.add(pts_ne_2_se_1_pln2);

		// TileNumber 312
		planes_ne_to_se_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_2_pln1 = new Vector3[3];
		pts_ne_2_se_2_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_2_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_2_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_ne_2_se_2_pln2 = new Vector3[3];
		pts_ne_2_se_2_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_2_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_2_pln2[1].addToZ((double)1/(double)9);
		pts_ne_2_se_2_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_ne_to_se_11__2.add(pts_ne_2_se_2_pln1);
		planes_ne_to_se_11__2.add(pts_ne_2_se_2_pln2);
		
		
		// TileNumber 313
		planes_ne_to_se_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_3_pln1 = new Vector3[3];
		pts_ne_2_se_3_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_3_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_3_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_ne_2_se_3_pln2 = new Vector3[3];
		pts_ne_2_se_3_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_3_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_3_pln2[1].addToZ((double)2/(double)9);
		pts_ne_2_se_3_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_ne_to_se_11__3.add(pts_ne_2_se_3_pln1);
		planes_ne_to_se_11__3.add(pts_ne_2_se_3_pln2);
		
		
		// TileNumber 314
		planes_ne_to_se_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_4_pln1 = new Vector3[3];
		pts_ne_2_se_4_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_4_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_4_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_ne_2_se_4_pln2 = new Vector3[3];
		pts_ne_2_se_4_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_4_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_4_pln2[1].addToZ((double)3/(double)9);
		pts_ne_2_se_4_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_ne_to_se_11__4.add(pts_ne_2_se_4_pln1);
		planes_ne_to_se_11__4.add(pts_ne_2_se_4_pln2);
		
		
		// TileNumber 315
		planes_ne_to_se_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_5_pln1 = new Vector3[3];
		pts_ne_2_se_5_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_5_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_5_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_ne_2_se_5_pln2 = new Vector3[3];
		pts_ne_2_se_5_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_5_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_5_pln2[1].addToZ((double)4/(double)9);
		pts_ne_2_se_5_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_ne_to_se_11__5.add(pts_ne_2_se_5_pln1);
		planes_ne_to_se_11__5.add(pts_ne_2_se_5_pln2);
		
		
		// TileNumber 316
		planes_ne_to_se_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_6_pln1 = new Vector3[3];
		pts_ne_2_se_6_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_6_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_6_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_ne_2_se_6_pln2 = new Vector3[3];
		pts_ne_2_se_6_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_6_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_6_pln2[1].addToZ((double)5/(double)9);
		pts_ne_2_se_6_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_ne_to_se_11__6.add(pts_ne_2_se_6_pln1);
		planes_ne_to_se_11__6.add(pts_ne_2_se_6_pln2);
		
		
		// TileNumber 317
		planes_ne_to_se_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_7_pln1 = new Vector3[3];
		pts_ne_2_se_7_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_7_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_7_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_ne_2_se_7_pln2 = new Vector3[3];
		pts_ne_2_se_7_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_7_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_7_pln2[1].addToZ((double)6/(double)9);
		pts_ne_2_se_7_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_ne_to_se_11__7.add(pts_ne_2_se_7_pln1);
		planes_ne_to_se_11__7.add(pts_ne_2_se_7_pln2);

		
		// TileNumber 318
		planes_ne_to_se_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_8_pln1 = new Vector3[3];
		pts_ne_2_se_8_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_8_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_8_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_ne_2_se_8_pln2 = new Vector3[3];
		pts_ne_2_se_8_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_8_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_8_pln2[1].addToZ((double)7/(double)9);
		pts_ne_2_se_8_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_ne_to_se_11__8.add(pts_ne_2_se_8_pln1);
		planes_ne_to_se_11__8.add(pts_ne_2_se_8_pln2);
		
		
		// TileNumber 319
		planes_ne_to_se_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_ne_2_se_9_pln1 = new Vector3[3];
		pts_ne_2_se_9_pln1[0] = resultPoints_ne_to_se_plane1[0];
		pts_ne_2_se_9_pln1[1] = resultPoints_ne_to_se_plane1[1];
		pts_ne_2_se_9_pln1[2] = new Vector3(resultPoints_ne_to_se_plane1[2]);
		pts_ne_2_se_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_ne_2_se_9_pln2 = new Vector3[3];
		pts_ne_2_se_9_pln2[0] = resultPoints_ne_to_se_plane2[0];
		pts_ne_2_se_9_pln2[1] = new Vector3(resultPoints_ne_to_se_plane2[1]);
		pts_ne_2_se_9_pln2[1].addToZ((double)8/(double)9);
		pts_ne_2_se_9_pln2[2] = new Vector3(resultPoints_ne_to_se_plane2[2]);
		pts_ne_2_se_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_ne_to_se_11__9.add(pts_ne_2_se_9_pln1);
		planes_ne_to_se_11__9.add(pts_ne_2_se_9_pln2);
		
	}	
	
	private void initPlanes_SE_2_NE_0_0() {

		Vector3[] resultPoints_se_to_ne_plane1; 
		Vector3[] resultPoints_se_to_ne_plane2; 

		resultPoints_se_to_ne_plane1 = new Vector3[3];
		resultPoints_se_to_ne_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_ne_plane1[0].z = 0 + this.heightOffset;

		resultPoints_se_to_ne_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_se_to_ne_plane1[1].z = 0 + this.heightOffset;

		resultPoints_se_to_ne_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_ne_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_se_to_ne_plane2 = new Vector3[3];
		resultPoints_se_to_ne_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_ne_plane2[0].z = 0 + this.heightOffset;

		resultPoints_se_to_ne_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_ne_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_se_to_ne_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_se_to_ne_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 151
		planes_se_to_ne_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_1_pln1 = new Vector3[3];
		pts_se_2_ne_1_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_1_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_1_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_se_2_ne_1_pln2 = new Vector3[3];
		pts_se_2_ne_1_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_1_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_1_pln2[1].addToZ((double)0/(double)9);
		pts_se_2_ne_1_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_se_to_ne_00__1.add(pts_se_2_ne_1_pln1);
		planes_se_to_ne_00__1.add(pts_se_2_ne_1_pln2);

		// TileNumber 152
		planes_se_to_ne_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_2_pln1 = new Vector3[3];
		pts_se_2_ne_2_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_2_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_2_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_se_2_ne_2_pln2 = new Vector3[3];
		pts_se_2_ne_2_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_2_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_2_pln2[1].addToZ((double)1/(double)9);
		pts_se_2_ne_2_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_se_to_ne_00__2.add(pts_se_2_ne_2_pln1);
		planes_se_to_ne_00__2.add(pts_se_2_ne_2_pln2);
		
		
		// TileNumber 153
		planes_se_to_ne_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_3_pln1 = new Vector3[3];
		pts_se_2_ne_3_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_3_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_3_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_se_2_ne_3_pln2 = new Vector3[3];
		pts_se_2_ne_3_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_3_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_3_pln2[1].addToZ((double)2/(double)9);
		pts_se_2_ne_3_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_se_to_ne_00__3.add(pts_se_2_ne_3_pln1);
		planes_se_to_ne_00__3.add(pts_se_2_ne_3_pln2);
		
		
		// TileNumber 154
		planes_se_to_ne_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_4_pln1 = new Vector3[3];
		pts_se_2_ne_4_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_4_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_4_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_se_2_ne_4_pln2 = new Vector3[3];
		pts_se_2_ne_4_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_4_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_4_pln2[1].addToZ((double)3/(double)9);
		pts_se_2_ne_4_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_se_to_ne_00__4.add(pts_se_2_ne_4_pln1);
		planes_se_to_ne_00__4.add(pts_se_2_ne_4_pln2);
		

		// TileNumber 155
		planes_se_to_ne_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_5_pln1 = new Vector3[3];
		pts_se_2_ne_5_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_5_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_5_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_se_2_ne_5_pln2 = new Vector3[3];
		pts_se_2_ne_5_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_5_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_5_pln2[1].addToZ((double)4/(double)9);
		pts_se_2_ne_5_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_se_to_ne_00__5.add(pts_se_2_ne_5_pln1);
		planes_se_to_ne_00__5.add(pts_se_2_ne_5_pln2);
		
		
		// TileNumber 156
		planes_se_to_ne_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_6_pln1 = new Vector3[3];
		pts_se_2_ne_6_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_6_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_6_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_se_2_ne_6_pln2 = new Vector3[3];
		pts_se_2_ne_6_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_6_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_6_pln2[1].addToZ((double)5/(double)9);
		pts_se_2_ne_6_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_se_to_ne_00__6.add(pts_se_2_ne_6_pln1);
		planes_se_to_ne_00__6.add(pts_se_2_ne_6_pln2);
		
		
		// TileNumber 157
		planes_se_to_ne_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_7_pln1 = new Vector3[3];
		pts_se_2_ne_7_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_7_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_7_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_se_2_ne_7_pln2 = new Vector3[3];
		pts_se_2_ne_7_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_7_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_7_pln2[1].addToZ((double)6/(double)9);
		pts_se_2_ne_7_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_se_to_ne_00__7.add(pts_se_2_ne_7_pln1);
		planes_se_to_ne_00__7.add(pts_se_2_ne_7_pln2);
		

		// TileNumber 158
		planes_se_to_ne_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_8_pln1 = new Vector3[3];
		pts_se_2_ne_8_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_8_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_8_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_se_2_ne_8_pln2 = new Vector3[3];
		pts_se_2_ne_8_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_8_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_8_pln2[1].addToZ((double)7/(double)9);
		pts_se_2_ne_8_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_se_to_ne_00__8.add(pts_se_2_ne_8_pln1);
		planes_se_to_ne_00__8.add(pts_se_2_ne_8_pln2);
		
		
		// TileNumber 159
		planes_se_to_ne_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_9_pln1 = new Vector3[3];
		pts_se_2_ne_9_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_9_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_9_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_se_2_ne_9_pln2 = new Vector3[3];
		pts_se_2_ne_9_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_9_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_9_pln2[1].addToZ((double)8/(double)9);
		pts_se_2_ne_9_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_se_to_ne_00__9.add(pts_se_2_ne_9_pln1);
		planes_se_to_ne_00__9.add(pts_se_2_ne_9_pln2);

		
	}	

	private void initPlanes_SE_2_NE_1_1() {

		Vector3[] resultPoints_se_to_ne_plane1; 
		Vector3[] resultPoints_se_to_ne_plane2; 

		resultPoints_se_to_ne_plane1 = new Vector3[3];
		resultPoints_se_to_ne_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_ne_plane1[0].z = 1 + this.heightOffset;

		resultPoints_se_to_ne_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_se_to_ne_plane1[1].z = 1 + this.heightOffset;

		resultPoints_se_to_ne_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_ne_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_se_to_ne_plane2 = new Vector3[3];
		resultPoints_se_to_ne_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_ne_plane2[0].z = 1 + this.heightOffset;

		resultPoints_se_to_ne_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_ne_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_se_to_ne_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_se_to_ne_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 351
		planes_se_to_ne_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_1_pln1 = new Vector3[3];
		pts_se_2_ne_1_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_1_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_1_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_se_2_ne_1_pln2 = new Vector3[3];
		pts_se_2_ne_1_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_1_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_1_pln2[1].addToZ((double)0/(double)9);
		pts_se_2_ne_1_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_se_to_ne_11__1.add(pts_se_2_ne_1_pln1);
		planes_se_to_ne_11__1.add(pts_se_2_ne_1_pln2);

		
		// TileNumber 352
		planes_se_to_ne_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_2_pln1 = new Vector3[3];
		pts_se_2_ne_2_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_2_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_2_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_se_2_ne_2_pln2 = new Vector3[3];
		pts_se_2_ne_2_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_2_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_2_pln2[1].addToZ((double)1/(double)9);
		pts_se_2_ne_2_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_se_to_ne_11__2.add(pts_se_2_ne_2_pln1);
		planes_se_to_ne_11__2.add(pts_se_2_ne_2_pln2);
		
		
		// TileNumber 353
		planes_se_to_ne_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_3_pln1 = new Vector3[3];
		pts_se_2_ne_3_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_3_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_3_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_se_2_ne_3_pln2 = new Vector3[3];
		pts_se_2_ne_3_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_3_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_3_pln2[1].addToZ((double)2/(double)9);
		pts_se_2_ne_3_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_se_to_ne_11__3.add(pts_se_2_ne_3_pln1);
		planes_se_to_ne_11__3.add(pts_se_2_ne_3_pln2);
		
		
		// TileNumber 354
		planes_se_to_ne_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_4_pln1 = new Vector3[3];
		pts_se_2_ne_4_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_4_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_4_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_se_2_ne_4_pln2 = new Vector3[3];
		pts_se_2_ne_4_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_4_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_4_pln2[1].addToZ((double)3/(double)9);
		pts_se_2_ne_4_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_se_to_ne_11__4.add(pts_se_2_ne_4_pln1);
		planes_se_to_ne_11__4.add(pts_se_2_ne_4_pln2);
		
		
		// TileNumber 355
		planes_se_to_ne_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_5_pln1 = new Vector3[3];
		pts_se_2_ne_5_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_5_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_5_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_se_2_ne_5_pln2 = new Vector3[3];
		pts_se_2_ne_5_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_5_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_5_pln2[1].addToZ((double)4/(double)9);
		pts_se_2_ne_5_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_se_to_ne_11__5.add(pts_se_2_ne_5_pln1);
		planes_se_to_ne_11__5.add(pts_se_2_ne_5_pln2);
		
		
		// TileNumber 356
		planes_se_to_ne_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_6_pln1 = new Vector3[3];
		pts_se_2_ne_6_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_6_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_6_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_se_2_ne_6_pln2 = new Vector3[3];
		pts_se_2_ne_6_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_6_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_6_pln2[1].addToZ((double)5/(double)9);
		pts_se_2_ne_6_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_se_to_ne_11__6.add(pts_se_2_ne_6_pln1);
		planes_se_to_ne_11__6.add(pts_se_2_ne_6_pln2);
		
		
		// TileNumber 357
		planes_se_to_ne_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_7_pln1 = new Vector3[3];
		pts_se_2_ne_7_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_7_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_7_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_se_2_ne_7_pln2 = new Vector3[3];
		pts_se_2_ne_7_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_7_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_7_pln2[1].addToZ((double)6/(double)9);
		pts_se_2_ne_7_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_se_to_ne_11__7.add(pts_se_2_ne_7_pln1);
		planes_se_to_ne_11__7.add(pts_se_2_ne_7_pln2);
		
		
		// TileNumber 358
		planes_se_to_ne_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_8_pln1 = new Vector3[3];
		pts_se_2_ne_8_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_8_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_8_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_se_2_ne_8_pln2 = new Vector3[3];
		pts_se_2_ne_8_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_8_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_8_pln2[1].addToZ((double)7/(double)9);
		pts_se_2_ne_8_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_se_to_ne_11__8.add(pts_se_2_ne_8_pln1);
		planes_se_to_ne_11__8.add(pts_se_2_ne_8_pln2);
		
		
		// TileNumber 359
		planes_se_to_ne_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_ne_9_pln1 = new Vector3[3];
		pts_se_2_ne_9_pln1[0] = resultPoints_se_to_ne_plane1[0];
		pts_se_2_ne_9_pln1[1] = resultPoints_se_to_ne_plane1[1];
		pts_se_2_ne_9_pln1[2] = new Vector3(resultPoints_se_to_ne_plane1[2]);
		pts_se_2_ne_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_se_2_ne_9_pln2 = new Vector3[3];
		pts_se_2_ne_9_pln2[0] = resultPoints_se_to_ne_plane2[0];
		pts_se_2_ne_9_pln2[1] = new Vector3(resultPoints_se_to_ne_plane2[1]);
		pts_se_2_ne_9_pln2[1].addToZ((double)8/(double)9);
		pts_se_2_ne_9_pln2[2] = new Vector3(resultPoints_se_to_ne_plane2[2]);
		pts_se_2_ne_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_se_to_ne_11__9.add(pts_se_2_ne_9_pln1);
		planes_se_to_ne_11__9.add(pts_se_2_ne_9_pln2);

		
		
	}	


}
