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

		case 311 :
			return planes_ne_to_se_11__1;

		case 151 :
			return planes_se_to_ne_00__1;

		case 351 :
			return planes_se_to_ne_11__1;
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

		
		
	}	


}
