package org.socialworld.calculation.geometry;

public class VectorMapper {

	private static VectorMapper instance;

	private static int COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS = 26;
	private static Vector[] standardVisibleAreaPerpendiculars;
	
	private VectorMapper() {

		standardVisibleAreaPerpendiculars = new Vector[26];
		
		standardVisibleAreaPerpendiculars[0] = new Vector(1, 0, 0);
		standardVisibleAreaPerpendiculars[1] = new Vector(-1, 0, 0);
		standardVisibleAreaPerpendiculars[2] = new Vector(0, 1, 0);
		standardVisibleAreaPerpendiculars[3] = new Vector(0,-1, 0);
		standardVisibleAreaPerpendiculars[4] = new Vector(0, 0, 1);
		standardVisibleAreaPerpendiculars[5] = new Vector(0, 0, -1);
		
		standardVisibleAreaPerpendiculars[6] = new Vector(1, 1, 0);
		standardVisibleAreaPerpendiculars[7] = new Vector(-1, -1, 0);
		standardVisibleAreaPerpendiculars[8] = new Vector(1, -1, 0);
		standardVisibleAreaPerpendiculars[9] = new Vector(-1, 1, 0);

		standardVisibleAreaPerpendiculars[10] = new Vector(1, 0, 1);
		standardVisibleAreaPerpendiculars[11] = new Vector(-1, 0, 1);
		standardVisibleAreaPerpendiculars[12] = new Vector(0, 1, 1);
		standardVisibleAreaPerpendiculars[13] = new Vector(0, -1, 1);

		standardVisibleAreaPerpendiculars[14] = new Vector(1, 1, 1);
		standardVisibleAreaPerpendiculars[15] = new Vector(1, -1, 1);
		standardVisibleAreaPerpendiculars[16] = new Vector(-1, -1, 1);
		standardVisibleAreaPerpendiculars[17] = new Vector(-1, 1, 1);

		standardVisibleAreaPerpendiculars[18] = new Vector(1, 0, -1);
		standardVisibleAreaPerpendiculars[19] = new Vector(-1, 0, -1);
		standardVisibleAreaPerpendiculars[20] = new Vector(0, 1, -1);
		standardVisibleAreaPerpendiculars[21] = new Vector(0, -1, -1);

		standardVisibleAreaPerpendiculars[22] = new Vector(1, 1, -1);
		standardVisibleAreaPerpendiculars[23] = new Vector(1, -1, -1);
		standardVisibleAreaPerpendiculars[24] = new Vector(-1, -1, -1);
		standardVisibleAreaPerpendiculars[25] = new Vector(-1, 1, -1);

	}

	public static VectorMapper getInstance() {
		if (instance == null) {
			instance = new VectorMapper();
		}
		return instance;
	}

	public Vector getAbstract(Vector vector) {
		
		double cosPhi;
		double gretestCosPhi = 0;
		int nrStandardPerpendicular = COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS;
		
		for (int i = 0; i < COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS; i++) {
			
			cosPhi = vector.getCosPhi(standardVisibleAreaPerpendiculars[i]);
			if (cosPhi > gretestCosPhi) {
				gretestCosPhi = cosPhi;
				nrStandardPerpendicular = i;
			}
		}
		
		return standardVisibleAreaPerpendiculars[nrStandardPerpendicular];
	}
	
	public int getBestVisibleAreaPerpendicular(Vector vector) {
		
		double cosPhi;
		double smallestCosPhi = 1;
		int nrStandardPerpendicular = COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS;
		
		for (int i = 0; i < COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS; i++) {
			
			cosPhi = vector.getCosPhi(standardVisibleAreaPerpendiculars[i]);
			if (cosPhi < smallestCosPhi) {
				smallestCosPhi = cosPhi;
				nrStandardPerpendicular = i;
			}
		}
		
		return nrStandardPerpendicular;
	}

}
