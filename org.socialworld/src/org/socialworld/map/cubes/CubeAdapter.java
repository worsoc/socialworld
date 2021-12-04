package org.socialworld.map.cubes;

import java.util.ArrayList;
import java.util.List;

public class CubeAdapter extends Cube {

	private static List<Vector3[]> planes_nw_to_ne_00__1; 
	private static List<Vector3[]> planes_nw_to_ne_00__2; 
	private static List<Vector3[]> planes_nw_to_ne_00__3; 
	private static List<Vector3[]> planes_nw_to_ne_00__4; 
	private static List<Vector3[]> planes_nw_to_ne_00__5; 
	private static List<Vector3[]> planes_nw_to_ne_00__6; 
	private static List<Vector3[]> planes_nw_to_ne_00__7; 
	private static List<Vector3[]> planes_nw_to_ne_00__8; 
	private static List<Vector3[]> planes_nw_to_ne_00__9; 
	
	
	
	public CubeAdapter( byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
		
		 
	}

	@Override
	protected Cube getNewInstance(byte size, byte heightOffset) {
    	return new CubeAdapter(size, heightOffset);
	}

	@Override
	protected List<Vector3[]> getPlanesForTile(byte bitsNumber) {
		
		return planes_nw_to_ne_00__9;
	}

	@Override
	protected void initPlanes() {

		initPlanes_NW_2_NE_0_0();
		initPlanes_NW_2_NE_1_1();
		initPlanes_NE_2_NW_0_0();
		initPlanes_NE_2_NW_1_1();
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
		
	}

	private void initPlanes_NE_2_NW_0_0() {
		
	}

	private void initPlanes_NE_2_NW_1_1() {
		
	}


}
