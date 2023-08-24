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

public class CubeAdapterSouth extends Cube {

	
	// 161 ... 169
	private static List<Vector3[]> planes_sw_to_se_00__1; 
	private static List<Vector3[]> planes_sw_to_se_00__2; 
	private static List<Vector3[]> planes_sw_to_se_00__3; 
	private static List<Vector3[]> planes_sw_to_se_00__4; 
	private static List<Vector3[]> planes_sw_to_se_00__5; 
	private static List<Vector3[]> planes_sw_to_se_00__6; 
	private static List<Vector3[]> planes_sw_to_se_00__7; 
	private static List<Vector3[]> planes_sw_to_se_00__8; 
	private static List<Vector3[]> planes_sw_to_se_00__9; 
	
	// 361 ... 369
	private static List<Vector3[]> planes_sw_to_se_11__1; 
	private static List<Vector3[]> planes_sw_to_se_11__2; 
	private static List<Vector3[]> planes_sw_to_se_11__3; 
	private static List<Vector3[]> planes_sw_to_se_11__4; 
	private static List<Vector3[]> planes_sw_to_se_11__5; 
	private static List<Vector3[]> planes_sw_to_se_11__6; 
	private static List<Vector3[]> planes_sw_to_se_11__7; 
	private static List<Vector3[]> planes_sw_to_se_11__8; 
	private static List<Vector3[]> planes_sw_to_se_11__9; 

	// 179 ... 171
	private static List<Vector3[]> planes_se_to_sw_00__9; 
	private static List<Vector3[]> planes_se_to_sw_00__8; 
	private static List<Vector3[]> planes_se_to_sw_00__7; 
	private static List<Vector3[]> planes_se_to_sw_00__6; 
	private static List<Vector3[]> planes_se_to_sw_00__5; 
	private static List<Vector3[]> planes_se_to_sw_00__4; 
	private static List<Vector3[]> planes_se_to_sw_00__3; 
	private static List<Vector3[]> planes_se_to_sw_00__2; 
	private static List<Vector3[]> planes_se_to_sw_00__1; 
	
	// 379 ... 371
	private static List<Vector3[]> planes_se_to_sw_11__9; 
	private static List<Vector3[]> planes_se_to_sw_11__8; 
	private static List<Vector3[]> planes_se_to_sw_11__7; 
	private static List<Vector3[]> planes_se_to_sw_11__6; 
	private static List<Vector3[]> planes_se_to_sw_11__5; 
	private static List<Vector3[]> planes_se_to_sw_11__4; 
	private static List<Vector3[]> planes_se_to_sw_11__3; 
	private static List<Vector3[]> planes_se_to_sw_11__2; 
	private static List<Vector3[]> planes_se_to_sw_11__1; 

	
	public CubeAdapterSouth( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}

	
	@Override
	protected Cube getNewInstance(byte size, byte heightOffset) {
    	return new CubeAdapterSouth(size, heightOffset);
	}

	@Override
	protected void initPlanes() {

		initPlanes_SW_2_SE_0_0();
		initPlanes_SW_2_SE_1_1();
		initPlanes_SE_2_SW_0_0();
		initPlanes_SE_2_SW_1_1();

	}

	@Override
	protected List<Vector3[]> getPlanesForTile(int bitsNumber) {
		switch ( bitsNumber ) {
		case 161 :
			return planes_sw_to_se_00__1;
		case 162 :
			return planes_sw_to_se_00__2;
		case 163 :
			return planes_sw_to_se_00__3;
		case 164 :
			return planes_sw_to_se_00__4;
		case 165 :
			return planes_sw_to_se_00__5;
		case 166 :
			return planes_sw_to_se_00__6;
		case 167 :
			return planes_sw_to_se_00__7;
		case 168 :
			return planes_sw_to_se_00__8;
		case 169 :
			return planes_sw_to_se_00__9;
		case 361 : 
			return planes_sw_to_se_11__1;
		case 362 :
			return planes_sw_to_se_11__2;
		case 363 :
			return planes_sw_to_se_11__3;
		case 364 : 
			return planes_sw_to_se_11__4;
		case 365 :
			return planes_sw_to_se_11__5;
		case 366 : 
			return planes_sw_to_se_11__6;
		case 367 : 
			return planes_sw_to_se_11__7;
		case 368 :
			return planes_sw_to_se_11__8;
		case 369 :
			return planes_sw_to_se_11__9;
		case 171 :
			return planes_se_to_sw_00__1;
		case 172 :
			return planes_se_to_sw_00__2;
		case 173 :
			return planes_se_to_sw_00__3;
		case 174 :
			return planes_se_to_sw_00__4;
		case 175 : 
			return planes_se_to_sw_00__5;
		case 176 :
			return planes_se_to_sw_00__6;
		case 177 :
			return planes_se_to_sw_00__7;
		case 178 :
			return planes_se_to_sw_00__8;
		case 179 :
			return planes_se_to_sw_00__9;
		case 371 :
			return planes_se_to_sw_11__1;
		case 372 :
			return planes_se_to_sw_11__2;
		case 373 :
			return planes_se_to_sw_11__3;
		case 374 :
			return planes_se_to_sw_11__4;
		case 375 :
			return planes_se_to_sw_11__5;
		case 376 :
			return planes_se_to_sw_11__6;
		case 377 :
			return planes_se_to_sw_11__7;
		case 378 :
			return planes_se_to_sw_11__8;
		case 379 :
			return planes_se_to_sw_11__9;
	}
	
	

	return planes_sw_to_se_00__1;
	}

	private void initPlanes_SW_2_SE_0_0() {

		Vector3[] resultPoints_sw_to_se_plane1; 
		Vector3[] resultPoints_sw_to_se_plane2; 

		resultPoints_sw_to_se_plane1 = new Vector3[3];
		resultPoints_sw_to_se_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_se_plane1[0].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_se_plane1[1].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_se_plane1[2].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane2 = new Vector3[3];
		resultPoints_sw_to_se_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_se_plane2[0].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_se_plane2[1].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_se_plane2[2].z = (1/9) + this.heightOffset;

		
		// TileNumber 161
		planes_sw_to_se_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_1_pln1 = new Vector3[3];
		pts_sw_2_se_1_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_1_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_1_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_sw_2_se_1_pln2 = new Vector3[3];
		pts_sw_2_se_1_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_1_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_1_pln2[1].addToZ((double)0/(double)9);
		pts_sw_2_se_1_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_sw_to_se_00__1.add(pts_sw_2_se_1_pln1);
		planes_sw_to_se_00__1.add(pts_sw_2_se_1_pln2);

		
		// TileNumber 162
		planes_sw_to_se_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_2_pln1 = new Vector3[3];
		pts_sw_2_se_2_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_2_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_2_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_sw_2_se_2_pln2 = new Vector3[3];
		pts_sw_2_se_2_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_2_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_2_pln2[1].addToZ((double)1/(double)9);
		pts_sw_2_se_2_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_sw_to_se_00__2.add(pts_sw_2_se_2_pln1);
		planes_sw_to_se_00__2.add(pts_sw_2_se_2_pln2);
		
		
		
		// TileNumber 163
		planes_sw_to_se_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_3_pln1 = new Vector3[3];
		pts_sw_2_se_3_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_3_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_3_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_sw_2_se_3_pln2 = new Vector3[3];
		pts_sw_2_se_3_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_3_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_3_pln2[1].addToZ((double)2/(double)9);
		pts_sw_2_se_3_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_sw_to_se_00__3.add(pts_sw_2_se_3_pln1);
		planes_sw_to_se_00__3.add(pts_sw_2_se_3_pln2);
		
		
		// TileNumber 164
		planes_sw_to_se_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_4_pln1 = new Vector3[3];
		pts_sw_2_se_4_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_4_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_4_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_sw_2_se_4_pln2 = new Vector3[3];
		pts_sw_2_se_4_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_4_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_4_pln2[1].addToZ((double)3/(double)9);
		pts_sw_2_se_4_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_sw_to_se_00__4.add(pts_sw_2_se_4_pln1);
		planes_sw_to_se_00__4.add(pts_sw_2_se_4_pln2);
		
		
		// TileNumber 165
		planes_sw_to_se_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_5_pln1 = new Vector3[3];
		pts_sw_2_se_5_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_5_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_5_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_sw_2_se_5_pln2 = new Vector3[3];
		pts_sw_2_se_5_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_5_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_5_pln2[1].addToZ((double)4/(double)9);
		pts_sw_2_se_5_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_sw_to_se_00__5.add(pts_sw_2_se_5_pln1);
		planes_sw_to_se_00__5.add(pts_sw_2_se_5_pln2);
		
		
		// TileNumber 166
		planes_sw_to_se_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_6_pln1 = new Vector3[3];
		pts_sw_2_se_6_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_6_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_6_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_sw_2_se_6_pln2 = new Vector3[3];
		pts_sw_2_se_6_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_6_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_6_pln2[1].addToZ((double)5/(double)9);
		pts_sw_2_se_6_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_sw_to_se_00__6.add(pts_sw_2_se_6_pln1);
		planes_sw_to_se_00__6.add(pts_sw_2_se_6_pln2);
		
		
		// TileNumber 167
		planes_sw_to_se_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_7_pln1 = new Vector3[3];
		pts_sw_2_se_7_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_7_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_7_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_sw_2_se_7_pln2 = new Vector3[3];
		pts_sw_2_se_7_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_7_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_7_pln2[1].addToZ((double)6/(double)9);
		pts_sw_2_se_7_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_sw_to_se_00__7.add(pts_sw_2_se_7_pln1);
		planes_sw_to_se_00__7.add(pts_sw_2_se_7_pln2);
		
		// TileNumber 168
		planes_sw_to_se_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_8_pln1 = new Vector3[3];
		pts_sw_2_se_8_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_8_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_8_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_sw_2_se_8_pln2 = new Vector3[3];
		pts_sw_2_se_8_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_8_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_8_pln2[1].addToZ((double)7/(double)9);
		pts_sw_2_se_8_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_sw_to_se_00__8.add(pts_sw_2_se_8_pln1);
		planes_sw_to_se_00__8.add(pts_sw_2_se_8_pln2);
		
		
		// TileNumber 169
		planes_sw_to_se_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_9_pln1 = new Vector3[3];
		pts_sw_2_se_9_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_9_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_9_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_sw_2_se_9_pln2 = new Vector3[3];
		pts_sw_2_se_9_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_9_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_9_pln2[1].addToZ((double)8/(double)9);
		pts_sw_2_se_9_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_sw_to_se_00__9.add(pts_sw_2_se_9_pln1);
		planes_sw_to_se_00__9.add(pts_sw_2_se_9_pln2);
		
	}
	
	private void initPlanes_SW_2_SE_1_1() {
		Vector3[] resultPoints_sw_to_se_plane1; 
		Vector3[] resultPoints_sw_to_se_plane2; 

		resultPoints_sw_to_se_plane1 = new Vector3[3];
		resultPoints_sw_to_se_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_se_plane1[0].z = 1 + this.heightOffset;

		resultPoints_sw_to_se_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_sw_to_se_plane1[1].z = 1 + this.heightOffset;

		resultPoints_sw_to_se_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_se_plane1[2].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane2 = new Vector3[3];
		resultPoints_sw_to_se_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_sw_to_se_plane2[0].z = 1 + this.heightOffset;

		resultPoints_sw_to_se_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_sw_to_se_plane2[1].z = 0 + this.heightOffset;

		resultPoints_sw_to_se_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_sw_to_se_plane2[2].z = (1/9) + this.heightOffset;
	
		
		// TileNumber 361
		planes_sw_to_se_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_1_pln1 = new Vector3[3];
		pts_sw_2_se_1_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_1_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_1_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_sw_2_se_1_pln2 = new Vector3[3];
		pts_sw_2_se_1_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_1_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_1_pln2[1].addToZ((double)0/(double)9);
		pts_sw_2_se_1_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_sw_to_se_11__1.add(pts_sw_2_se_1_pln1);
		planes_sw_to_se_11__1.add(pts_sw_2_se_1_pln2);

		
		// TileNumber 362
		planes_sw_to_se_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_2_pln1 = new Vector3[3];
		pts_sw_2_se_2_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_2_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_2_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_sw_2_se_2_pln2 = new Vector3[3];
		pts_sw_2_se_2_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_2_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_2_pln2[1].addToZ((double)1/(double)9);
		pts_sw_2_se_2_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_sw_to_se_11__2.add(pts_sw_2_se_2_pln1);
		planes_sw_to_se_11__2.add(pts_sw_2_se_2_pln2);
		
		
		// TileNumber 363
		planes_sw_to_se_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_3_pln1 = new Vector3[3];
		pts_sw_2_se_3_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_3_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_3_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_sw_2_se_3_pln2 = new Vector3[3];
		pts_sw_2_se_3_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_3_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_3_pln2[1].addToZ((double)2/(double)9);
		pts_sw_2_se_3_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_sw_to_se_11__3.add(pts_sw_2_se_3_pln1);
		planes_sw_to_se_11__3.add(pts_sw_2_se_3_pln2);
		
		
		// TileNumber 364
		planes_sw_to_se_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_4_pln1 = new Vector3[3];
		pts_sw_2_se_4_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_4_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_4_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_sw_2_se_4_pln2 = new Vector3[3];
		pts_sw_2_se_4_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_4_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_4_pln2[1].addToZ((double)3/(double)9);
		pts_sw_2_se_4_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_sw_to_se_11__4.add(pts_sw_2_se_4_pln1);
		planes_sw_to_se_11__4.add(pts_sw_2_se_4_pln2);
		
		
		// TileNumber 365
		planes_sw_to_se_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_5_pln1 = new Vector3[3];
		pts_sw_2_se_5_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_5_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_5_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_sw_2_se_5_pln2 = new Vector3[3];
		pts_sw_2_se_5_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_5_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_5_pln2[1].addToZ((double)4/(double)9);
		pts_sw_2_se_5_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_sw_to_se_11__5.add(pts_sw_2_se_5_pln1);
		planes_sw_to_se_11__5.add(pts_sw_2_se_5_pln2);
		
		
		// TileNumber 366
		planes_sw_to_se_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_6_pln1 = new Vector3[3];
		pts_sw_2_se_6_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_6_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_6_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_sw_2_se_6_pln2 = new Vector3[3];
		pts_sw_2_se_6_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_6_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_6_pln2[1].addToZ((double)5/(double)9);
		pts_sw_2_se_6_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_sw_to_se_11__6.add(pts_sw_2_se_6_pln1);
		planes_sw_to_se_11__6.add(pts_sw_2_se_6_pln2);
		
		
		// TileNumber 367
		planes_sw_to_se_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_7_pln1 = new Vector3[3];
		pts_sw_2_se_7_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_7_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_7_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_sw_2_se_7_pln2 = new Vector3[3];
		pts_sw_2_se_7_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_7_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_7_pln2[1].addToZ((double)6/(double)9);
		pts_sw_2_se_7_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_sw_to_se_11__7.add(pts_sw_2_se_7_pln1);
		planes_sw_to_se_11__7.add(pts_sw_2_se_7_pln2);

		
		// TileNumber 368
		planes_sw_to_se_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_8_pln1 = new Vector3[3];
		pts_sw_2_se_8_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_8_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_8_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_sw_2_se_8_pln2 = new Vector3[3];
		pts_sw_2_se_8_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_8_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_8_pln2[1].addToZ((double)7/(double)9);
		pts_sw_2_se_8_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_sw_to_se_11__8.add(pts_sw_2_se_8_pln1);
		planes_sw_to_se_11__8.add(pts_sw_2_se_8_pln2);
		
		
		// TileNumber 369
		planes_sw_to_se_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_sw_2_se_9_pln1 = new Vector3[3];
		pts_sw_2_se_9_pln1[0] = resultPoints_sw_to_se_plane1[0];
		pts_sw_2_se_9_pln1[1] = resultPoints_sw_to_se_plane1[1];
		pts_sw_2_se_9_pln1[2] = new Vector3(resultPoints_sw_to_se_plane1[2]);
		pts_sw_2_se_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_sw_2_se_9_pln2 = new Vector3[3];
		pts_sw_2_se_9_pln2[0] = resultPoints_sw_to_se_plane2[0];
		pts_sw_2_se_9_pln2[1] = new Vector3(resultPoints_sw_to_se_plane2[1]);
		pts_sw_2_se_9_pln2[1].addToZ((double)8/(double)9);
		pts_sw_2_se_9_pln2[2] = new Vector3(resultPoints_sw_to_se_plane2[2]);
		pts_sw_2_se_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_sw_to_se_11__9.add(pts_sw_2_se_9_pln1);
		planes_sw_to_se_11__9.add(pts_sw_2_se_9_pln2);
		
	}
	
	private void initPlanes_SE_2_SW_0_0() {
		
		Vector3[] resultPoints_se_to_sw_plane1; 
		Vector3[] resultPoints_se_to_sw_plane2; 

		resultPoints_se_to_sw_plane1 = new Vector3[3];
		resultPoints_se_to_sw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_sw_plane1[0].z = 0 + this.heightOffset;

		resultPoints_se_to_sw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_se_to_sw_plane1[1].z = 0 + this.heightOffset;

		resultPoints_se_to_sw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_sw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_se_to_sw_plane2 = new Vector3[3];
		resultPoints_se_to_sw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_sw_plane2[0].z = 0 + this.heightOffset;

		resultPoints_se_to_sw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_sw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_se_to_sw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_se_to_sw_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 171
		planes_se_to_sw_00__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_1_pln1 = new Vector3[3];
		pts_se_2_sw_1_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_1_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_1_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_se_2_sw_1_pln2 = new Vector3[3];
		pts_se_2_sw_1_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_1_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_1_pln2[1].addToZ((double)0/(double)9);
		pts_se_2_sw_1_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_se_to_sw_00__1.add(pts_se_2_sw_1_pln1);
		planes_se_to_sw_00__1.add(pts_se_2_sw_1_pln2);

		
		// TileNumber 172
		planes_se_to_sw_00__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_2_pln1 = new Vector3[3];
		pts_se_2_sw_2_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_2_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_2_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_se_2_sw_2_pln2 = new Vector3[3];
		pts_se_2_sw_2_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_2_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_2_pln2[1].addToZ((double)1/(double)9);
		pts_se_2_sw_2_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_se_to_sw_00__2.add(pts_se_2_sw_2_pln1);
		planes_se_to_sw_00__2.add(pts_se_2_sw_2_pln2);
		
		
		// TileNumber 173
		planes_se_to_sw_00__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_3_pln1 = new Vector3[3];
		pts_se_2_sw_3_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_3_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_3_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_se_2_sw_3_pln2 = new Vector3[3];
		pts_se_2_sw_3_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_3_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_3_pln2[1].addToZ((double)2/(double)9);
		pts_se_2_sw_3_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_se_to_sw_00__3.add(pts_se_2_sw_3_pln1);
		planes_se_to_sw_00__3.add(pts_se_2_sw_3_pln2);
		
		
		// TileNumber 174
		planes_se_to_sw_00__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_4_pln1 = new Vector3[3];
		pts_se_2_sw_4_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_4_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_4_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_se_2_sw_4_pln2 = new Vector3[3];
		pts_se_2_sw_4_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_4_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_4_pln2[1].addToZ((double)3/(double)9);
		pts_se_2_sw_4_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_se_to_sw_00__4.add(pts_se_2_sw_4_pln1);
		planes_se_to_sw_00__4.add(pts_se_2_sw_4_pln2);
		

		// TileNumber 175
		planes_se_to_sw_00__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_5_pln1 = new Vector3[3];
		pts_se_2_sw_5_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_5_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_5_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_se_2_sw_5_pln2 = new Vector3[3];
		pts_se_2_sw_5_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_5_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_5_pln2[1].addToZ((double)4/(double)9);
		pts_se_2_sw_5_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_se_to_sw_00__5.add(pts_se_2_sw_5_pln1);
		planes_se_to_sw_00__5.add(pts_se_2_sw_5_pln2);
		
		
		// TileNumber 176
		planes_se_to_sw_00__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_6_pln1 = new Vector3[3];
		pts_se_2_sw_6_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_6_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_6_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_se_2_sw_6_pln2 = new Vector3[3];
		pts_se_2_sw_6_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_6_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_6_pln2[1].addToZ((double)5/(double)9);
		pts_se_2_sw_6_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_se_to_sw_00__6.add(pts_se_2_sw_6_pln1);
		planes_se_to_sw_00__6.add(pts_se_2_sw_6_pln2);
		
		
		// TileNumber 177
		planes_se_to_sw_00__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_7_pln1 = new Vector3[3];
		pts_se_2_sw_7_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_7_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_7_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_se_2_sw_7_pln2 = new Vector3[3];
		pts_se_2_sw_7_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_7_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_7_pln2[1].addToZ((double)6/(double)9);
		pts_se_2_sw_7_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_se_to_sw_00__7.add(pts_se_2_sw_7_pln1);
		planes_se_to_sw_00__7.add(pts_se_2_sw_7_pln2);
		

		// TileNumber 178
		planes_se_to_sw_00__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_8_pln1 = new Vector3[3];
		pts_se_2_sw_8_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_8_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_8_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_se_2_sw_8_pln2 = new Vector3[3];
		pts_se_2_sw_8_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_8_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_8_pln2[1].addToZ((double)7/(double)9);
		pts_se_2_sw_8_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_se_to_sw_00__8.add(pts_se_2_sw_8_pln1);
		planes_se_to_sw_00__8.add(pts_se_2_sw_8_pln2);
		
		
		// TileNumber 179
		planes_se_to_sw_00__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_9_pln1 = new Vector3[3];
		pts_se_2_sw_9_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_9_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_9_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_se_2_sw_9_pln2 = new Vector3[3];
		pts_se_2_sw_9_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_9_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_9_pln2[1].addToZ((double)8/(double)9);
		pts_se_2_sw_9_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_se_to_sw_00__9.add(pts_se_2_sw_9_pln1);
		planes_se_to_sw_00__9.add(pts_se_2_sw_9_pln2);

		
	}
	
	private void initPlanes_SE_2_SW_1_1() {
		
		Vector3[] resultPoints_se_to_sw_plane1; 
		Vector3[] resultPoints_se_to_sw_plane2; 

		resultPoints_se_to_sw_plane1 = new Vector3[3];
		resultPoints_se_to_sw_plane1[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_sw_plane1[0].z = 1 + this.heightOffset;

		resultPoints_se_to_sw_plane1[1] = new Vector3(coordinatesOfBitsIndex((byte)3));
		resultPoints_se_to_sw_plane1[1].z = 1 + this.heightOffset;

		resultPoints_se_to_sw_plane1[2] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_sw_plane1[2].z = (1/9) + this.heightOffset;

		resultPoints_se_to_sw_plane2 = new Vector3[3];
		resultPoints_se_to_sw_plane2[0] = new Vector3(coordinatesOfBitsIndex((byte)2));
		resultPoints_se_to_sw_plane2[0].z = 1 + this.heightOffset;

		resultPoints_se_to_sw_plane2[1] = new Vector3(coordinatesOfBitsIndex((byte)1));
		resultPoints_se_to_sw_plane2[1].z = (1/9) + this.heightOffset;

		resultPoints_se_to_sw_plane2[2] = new Vector3(coordinatesOfBitsIndex((byte)0));
		resultPoints_se_to_sw_plane2[2].z = 0 + this.heightOffset;

		
		// TileNumber 371
		planes_se_to_sw_11__1 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_1_pln1 = new Vector3[3];
		pts_se_2_sw_1_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_1_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_1_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_1_pln1[2].addToZ((double)0/(double)9);
		Vector3[] pts_se_2_sw_1_pln2 = new Vector3[3];
		pts_se_2_sw_1_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_1_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_1_pln2[1].addToZ((double)0/(double)9);
		pts_se_2_sw_1_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_1_pln2[2].addToZ((double)0/(double)9);
		
		planes_se_to_sw_11__1.add(pts_se_2_sw_1_pln1);
		planes_se_to_sw_11__1.add(pts_se_2_sw_1_pln2);

		
		// TileNumber 372
		planes_se_to_sw_11__2 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_2_pln1 = new Vector3[3];
		pts_se_2_sw_2_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_2_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_2_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_2_pln1[2].addToZ((double)1/(double)9);
		Vector3[] pts_se_2_sw_2_pln2 = new Vector3[3];
		pts_se_2_sw_2_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_2_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_2_pln2[1].addToZ((double)1/(double)9);
		pts_se_2_sw_2_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_2_pln2[2].addToZ((double)1/(double)9);
		
		planes_se_to_sw_11__2.add(pts_se_2_sw_2_pln1);
		planes_se_to_sw_11__2.add(pts_se_2_sw_2_pln2);
		
		
		// TileNumber 373
		planes_se_to_sw_11__3 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_3_pln1 = new Vector3[3];
		pts_se_2_sw_3_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_3_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_3_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_3_pln1[2].addToZ((double)2/(double)9);
		Vector3[] pts_se_2_sw_3_pln2 = new Vector3[3];
		pts_se_2_sw_3_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_3_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_3_pln2[1].addToZ((double)2/(double)9);
		pts_se_2_sw_3_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_3_pln2[2].addToZ((double)2/(double)9);
		
		planes_se_to_sw_11__3.add(pts_se_2_sw_3_pln1);
		planes_se_to_sw_11__3.add(pts_se_2_sw_3_pln2);
		
		
		// TileNumber 374
		planes_se_to_sw_11__4 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_4_pln1 = new Vector3[3];
		pts_se_2_sw_4_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_4_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_4_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_4_pln1[2].addToZ((double)3/(double)9);
		Vector3[] pts_se_2_sw_4_pln2 = new Vector3[3];
		pts_se_2_sw_4_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_4_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_4_pln2[1].addToZ((double)3/(double)9);
		pts_se_2_sw_4_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_4_pln2[2].addToZ((double)3/(double)9);
		
		planes_se_to_sw_11__4.add(pts_se_2_sw_4_pln1);
		planes_se_to_sw_11__4.add(pts_se_2_sw_4_pln2);
		
		
		// TileNumber 375
		planes_se_to_sw_11__5 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_5_pln1 = new Vector3[3];
		pts_se_2_sw_5_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_5_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_5_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_5_pln1[2].addToZ((double)4/(double)9);
		Vector3[] pts_se_2_sw_5_pln2 = new Vector3[3];
		pts_se_2_sw_5_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_5_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_5_pln2[1].addToZ((double)4/(double)9);
		pts_se_2_sw_5_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_5_pln2[2].addToZ((double)4/(double)9);
		
		planes_se_to_sw_11__5.add(pts_se_2_sw_5_pln1);
		planes_se_to_sw_11__5.add(pts_se_2_sw_5_pln2);
		
		
		// TileNumber 376
		planes_se_to_sw_11__6 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_6_pln1 = new Vector3[3];
		pts_se_2_sw_6_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_6_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_6_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_6_pln1[2].addToZ((double)5/(double)9);
		Vector3[] pts_se_2_sw_6_pln2 = new Vector3[3];
		pts_se_2_sw_6_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_6_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_6_pln2[1].addToZ((double)5/(double)9);
		pts_se_2_sw_6_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_6_pln2[2].addToZ((double)5/(double)9);
		
		planes_se_to_sw_11__6.add(pts_se_2_sw_6_pln1);
		planes_se_to_sw_11__6.add(pts_se_2_sw_6_pln2);
		
		
		// TileNumber 377
		planes_se_to_sw_11__7 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_7_pln1 = new Vector3[3];
		pts_se_2_sw_7_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_7_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_7_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_7_pln1[2].addToZ((double)6/(double)9);
		Vector3[] pts_se_2_sw_7_pln2 = new Vector3[3];
		pts_se_2_sw_7_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_7_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_7_pln2[1].addToZ((double)6/(double)9);
		pts_se_2_sw_7_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_7_pln2[2].addToZ((double)6/(double)9);
		
		planes_se_to_sw_11__7.add(pts_se_2_sw_7_pln1);
		planes_se_to_sw_11__7.add(pts_se_2_sw_7_pln2);
		
		
		// TileNumber 378
		planes_se_to_sw_11__8 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_8_pln1 = new Vector3[3];
		pts_se_2_sw_8_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_8_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_8_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_8_pln1[2].addToZ((double)7/(double)9);
		Vector3[] pts_se_2_sw_8_pln2 = new Vector3[3];
		pts_se_2_sw_8_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_8_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_8_pln2[1].addToZ((double)7/(double)9);
		pts_se_2_sw_8_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_8_pln2[2].addToZ((double)7/(double)9);
		
		planes_se_to_sw_11__8.add(pts_se_2_sw_8_pln1);
		planes_se_to_sw_11__8.add(pts_se_2_sw_8_pln2);
		
		
		// TileNumber 379
		planes_se_to_sw_11__9 = new ArrayList<Vector3[]> ();
		Vector3[] pts_se_2_sw_9_pln1 = new Vector3[3];
		pts_se_2_sw_9_pln1[0] = resultPoints_se_to_sw_plane1[0];
		pts_se_2_sw_9_pln1[1] = resultPoints_se_to_sw_plane1[1];
		pts_se_2_sw_9_pln1[2] = new Vector3(resultPoints_se_to_sw_plane1[2]);
		pts_se_2_sw_9_pln1[2].addToZ((double)8/(double)9);
		Vector3[] pts_se_2_sw_9_pln2 = new Vector3[3];
		pts_se_2_sw_9_pln2[0] = resultPoints_se_to_sw_plane2[0];
		pts_se_2_sw_9_pln2[1] = new Vector3(resultPoints_se_to_sw_plane2[1]);
		pts_se_2_sw_9_pln2[1].addToZ((double)8/(double)9);
		pts_se_2_sw_9_pln2[2] = new Vector3(resultPoints_se_to_sw_plane2[2]);
		pts_se_2_sw_9_pln2[2].addToZ((double)8/(double)9);
		
		planes_se_to_sw_11__9.add(pts_se_2_sw_9_pln1);
		planes_se_to_sw_11__9.add(pts_se_2_sw_9_pln2);

		
	}
	
}
