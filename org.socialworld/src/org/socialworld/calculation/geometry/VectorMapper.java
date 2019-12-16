package org.socialworld.calculation.geometry;

public class VectorMapper {

	private static VectorMapper instance;

	public static int COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS = 26;
	private static Vector[] standardVisibleAreaPerpendiculars;
	private static Vector[] standardVisibleAreaANorm;
	private static Vector[] standardVisibleAreaBNorm;
	
	private static double sqrt2;
	private static float plus1DivSqrt2;
	private static float minus1DivSqrt2;
	private static double sqrt3;
	private static float plus1DivSqrt3;
	private static float minus1DivSqrt3;
	
	private VectorMapper() {

		sqrt2 = Math.sqrt(2);
		plus1DivSqrt2 = (float) (1 / sqrt2);
		plus1DivSqrt2 = (float) (-1 / sqrt2);
		sqrt3 = Math.sqrt(3);
		plus1DivSqrt3 = (float) (1 / sqrt3);
		plus1DivSqrt3 = (float) (-1 / sqrt3);
		
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

		
		standardVisibleAreaANorm = new Vector[26];
		standardVisibleAreaBNorm = new Vector[26];

		standardVisibleAreaANorm[0] = new Vector(0, 1, 0);
		standardVisibleAreaBNorm[0] = new Vector(0, 0, 1);
		standardVisibleAreaANorm[1] = new Vector(0, 1, 0);
		standardVisibleAreaBNorm[1] = new Vector(0, 0, -1);
		standardVisibleAreaANorm[2] = new Vector(-1, 0, 0);
		standardVisibleAreaBNorm[2] = new Vector(0, 0, -1);
		standardVisibleAreaANorm[3] = new Vector(1, 0, 0);
		standardVisibleAreaBNorm[3] = new Vector(0, 0, -1);
		standardVisibleAreaANorm[4] = new Vector(1, 0, 0);
		standardVisibleAreaBNorm[4] = new Vector(0, 1, 0);
		standardVisibleAreaANorm[5] = new Vector(-1, 0, 0);
		standardVisibleAreaBNorm[5] = new Vector(0, 1, 0);
		
		standardVisibleAreaANorm[6] = new Vector(minus1DivSqrt2, plus1DivSqrt2, 0);   // for perp (1, 1, 0)
		standardVisibleAreaBNorm[6] = new Vector(0, 0, 1);	 			// for perp (1, 1, 0)
		standardVisibleAreaANorm[7] = new Vector(plus1DivSqrt2, minus1DivSqrt2, 0);		// for perp (-1, -1, 0)
		standardVisibleAreaBNorm[7] = new Vector(0, 0, 1);		// for perp (-1, -1, 0)
		standardVisibleAreaANorm[8] = new Vector(plus1DivSqrt2, plus1DivSqrt2, 0);		// for perp (1, -1, 0)
		standardVisibleAreaBNorm[8] = new Vector(0, 0, 1);		// for perp (1, -1, 0)
		standardVisibleAreaANorm[9] = new Vector(minus1DivSqrt2, minus1DivSqrt2, 0);		// for perp (-1, 1, 0)
		standardVisibleAreaBNorm[9] = new Vector(0, 0, 1);		// for perp (-1, 1, 0)
		
		standardVisibleAreaANorm[10] = new Vector(0, 1, 0);				// for perp (1, 0, 1)
		standardVisibleAreaBNorm[10] = new Vector(minus1DivSqrt2, 0, plus1DivSqrt2); // for perp (1, 0, 1)
		standardVisibleAreaANorm[11] = new Vector(0, -1, 0);		// for perp (-1, 0, 1)
		standardVisibleAreaBNorm[11] = new Vector(plus1DivSqrt2, 0, plus1DivSqrt2);	// for perp (-1, 0, 1)
		standardVisibleAreaANorm[12] = new Vector(-1, 0, 0); // for perp (0, 1, 1)
		standardVisibleAreaBNorm[12] = new Vector(0, minus1DivSqrt2, plus1DivSqrt2);	// for perp (0, 1, 1)
		standardVisibleAreaANorm[13] = new Vector(1, 0, 0); // for perp (0, -1, 1)
		standardVisibleAreaBNorm[13] = new Vector(0, plus1DivSqrt2, plus1DivSqrt2); // for perp (0, -1, 1)
		
		standardVisibleAreaANorm[14] = new Vector(minus1DivSqrt2, plus1DivSqrt2, 0);  // for perp (1, 1, 1)
		standardVisibleAreaBNorm[14] = new Vector(minus1DivSqrt3, minus1DivSqrt3, plus1DivSqrt3);  // for perp (1, 1, 1)
		standardVisibleAreaANorm[15] = new Vector(plus1DivSqrt2, plus1DivSqrt2, 0); // for perp (1, -1, 1)
		standardVisibleAreaBNorm[15] = new Vector(minus1DivSqrt3, plus1DivSqrt3, plus1DivSqrt3); // for perp (1, -1, 1)
		standardVisibleAreaANorm[16] = new Vector(plus1DivSqrt2, minus1DivSqrt2, 0); // for perp (-1, -1, 1)
		standardVisibleAreaBNorm[16] = new Vector(plus1DivSqrt3, plus1DivSqrt3, plus1DivSqrt3); // for perp (-1, -1, 1)
		standardVisibleAreaANorm[17] = new Vector(minus1DivSqrt2, minus1DivSqrt2, 0); // for perp (-1, 1, 1)
		standardVisibleAreaBNorm[17] = new Vector(plus1DivSqrt3, minus1DivSqrt3, plus1DivSqrt3); // for perp (-1, 1, 1)
	
		standardVisibleAreaANorm[18] = new Vector(0, 1, 0); // for perp (1, 0, -1)
		standardVisibleAreaBNorm[18] = new Vector(plus1DivSqrt2, 0, plus1DivSqrt2); // for perp (1, 0, -1)
		standardVisibleAreaANorm[19] = new Vector(0, -1, 0); // for perp (-1, 0, -1)
		standardVisibleAreaBNorm[19] = new Vector(minus1DivSqrt2, 0, plus1DivSqrt2); // for perp (-1, 0, -1)
		standardVisibleAreaANorm[20] = new Vector(-1, 0, 0); // for perp (0, 1, -1)
		standardVisibleAreaBNorm[20] = new Vector(0, plus1DivSqrt2, plus1DivSqrt2); // for perp (0, 1, -1)
		standardVisibleAreaANorm[21] = new Vector(1, 0, 0); // for perp (0, -1, -1)
		standardVisibleAreaBNorm[21] = new Vector(0, minus1DivSqrt2, plus1DivSqrt2); // for perp (0, -1, -1)
		
		standardVisibleAreaANorm[22] = new Vector(minus1DivSqrt2, plus1DivSqrt2, 0); // for perp (1, 1, -1)
		standardVisibleAreaBNorm[22] = new Vector(plus1DivSqrt3, plus1DivSqrt3, plus1DivSqrt3); // for perp (1, 1, -1)
		standardVisibleAreaANorm[23] = new Vector(plus1DivSqrt2, plus1DivSqrt2, 0); // for perp (1, -1, -1)
		standardVisibleAreaBNorm[23] = new Vector(plus1DivSqrt3, minus1DivSqrt3, plus1DivSqrt3); // for perp (1, -1, -1)
		standardVisibleAreaANorm[24] = new Vector(plus1DivSqrt2, minus1DivSqrt2, 0); // for perp (-1, -1, -1)
		standardVisibleAreaBNorm[24] = new Vector(minus1DivSqrt3, minus1DivSqrt3, plus1DivSqrt3); // for perp (-1, -1, -1)
		standardVisibleAreaANorm[25] = new Vector(minus1DivSqrt2, minus1DivSqrt2, 0); // for perp (-1, 1, -1)
		standardVisibleAreaBNorm[25] = new Vector(minus1DivSqrt3, plus1DivSqrt3, plus1DivSqrt3); // for perp (-1, 1, -1)
		
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
	
	public Vector vectorProduct(Vector a, Vector b) {
		
		Vector result = new Vector( a.getY()*b.getZ() - a.getZ()*b.getY(), 
									a.getZ()*b.getX() - a.getX()*b.getZ(),
									a.getX()*b.getY() - a.getY()*b.getX());
		return result;
		
	}
	
	public Vector reverseVector(Vector a) {
		
		Vector result = new Vector(a);
		result.mul(-1);
		return result;
		
	}

	public Vector addition(Vector a, Vector b) {
		
		Vector result = new Vector(a);
		result.add(b);
		return result;
		
	}

	public Vector subtraction(Vector a, Vector b) {
		
		Vector result = new Vector(a);
		result.add(reverseVector(b));
		return result;
		
	}
	
	public Vector multiplicationWithScalar(Vector a, float scalarFactor) {
		
		Vector result = new Vector(a);
		result.mul(scalarFactor);
		return result;
		
	}

	public Vector getPerpendicular(int nrPerpendicular) {
		if ( (nrPerpendicular >= 0) && (nrPerpendicular < COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) ) {
			return new Vector(standardVisibleAreaPerpendiculars[nrPerpendicular]);
		}
		else {
			return new Vector();
		}
	}
	
	public Vector getRectangleAForPerpendicular(int nrPerpendicular) {
		if ( (nrPerpendicular >= 0) && (nrPerpendicular < COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) ) {
			return new Vector(standardVisibleAreaANorm[nrPerpendicular]);
		}
		else {
			return new Vector();
		}
	}
	
	public Vector getRectangleBForPerpendicular(int nrPerpendicular) {
		if ( (nrPerpendicular >= 0) && (nrPerpendicular < COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) ) {
			return new Vector(standardVisibleAreaBNorm[nrPerpendicular]);
		}
		else {
			return new Vector();
		}
	}

}
