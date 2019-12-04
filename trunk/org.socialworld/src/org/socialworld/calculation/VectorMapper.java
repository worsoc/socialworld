package org.socialworld.calculation;


public class VectorMapper {

	private static VectorMapper instance;

	private static int COUNT_STANDARD_PERCIPIENCE_PERPENDICULARS = 26;
	private static Vector[] standardPercipiencePerpendiculars;
	
	private VectorMapper() {

		standardPercipiencePerpendiculars = new Vector[26];
		
		standardPercipiencePerpendiculars[0] = new Vector(1, 0, 0);
		standardPercipiencePerpendiculars[1] = new Vector(-1, 0, 0);
		standardPercipiencePerpendiculars[2] = new Vector(0, 1, 0);
		standardPercipiencePerpendiculars[3] = new Vector(0,-1, 0);
		standardPercipiencePerpendiculars[4] = new Vector(0, 0, 1);
		standardPercipiencePerpendiculars[5] = new Vector(0, 0, -1);
		
		standardPercipiencePerpendiculars[6] = new Vector(1, 1, 0);
		standardPercipiencePerpendiculars[7] = new Vector(-1, -1, 0);
		standardPercipiencePerpendiculars[8] = new Vector(1, -1, 0);
		standardPercipiencePerpendiculars[9] = new Vector(-1, 1, 0);

		standardPercipiencePerpendiculars[10] = new Vector(1, 0, 1);
		standardPercipiencePerpendiculars[11] = new Vector(-1, 0, 1);
		standardPercipiencePerpendiculars[12] = new Vector(0, 1, 1);
		standardPercipiencePerpendiculars[13] = new Vector(0, -1, 1);

		standardPercipiencePerpendiculars[14] = new Vector(1, 1, 1);
		standardPercipiencePerpendiculars[15] = new Vector(1, -1, 1);
		standardPercipiencePerpendiculars[16] = new Vector(-1, -1, 1);
		standardPercipiencePerpendiculars[17] = new Vector(-1, 1, 1);

		standardPercipiencePerpendiculars[18] = new Vector(1, 0, -1);
		standardPercipiencePerpendiculars[19] = new Vector(-1, 0, -1);
		standardPercipiencePerpendiculars[20] = new Vector(0, 1, -1);
		standardPercipiencePerpendiculars[21] = new Vector(0, -1, -1);

		standardPercipiencePerpendiculars[22] = new Vector(1, 1, -1);
		standardPercipiencePerpendiculars[23] = new Vector(1, -1, -1);
		standardPercipiencePerpendiculars[24] = new Vector(-1, -1, -1);
		standardPercipiencePerpendiculars[25] = new Vector(-1, 1, -1);

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
		int nrStandardPerpendicular = COUNT_STANDARD_PERCIPIENCE_PERPENDICULARS;
		
		for (int i = 0; i < COUNT_STANDARD_PERCIPIENCE_PERPENDICULARS; i++) {
			
			cosPhi = vector.getCosPhi(standardPercipiencePerpendiculars[i]);
			if (cosPhi > gretestCosPhi) {
				gretestCosPhi = cosPhi;
				nrStandardPerpendicular = i;
			}
		}
		
		return standardPercipiencePerpendiculars[nrStandardPerpendicular];
	}
	
	public int getBestPercipiencePerpendicular(Vector vector) {
		
		double cosPhi;
		double smallestCosPhi = 1;
		int nrStandardPerpendicular = COUNT_STANDARD_PERCIPIENCE_PERPENDICULARS;
		
		for (int i = 0; i < COUNT_STANDARD_PERCIPIENCE_PERPENDICULARS; i++) {
			
			cosPhi = vector.getCosPhi(standardPercipiencePerpendiculars[i]);
			if (cosPhi < smallestCosPhi) {
				smallestCosPhi = cosPhi;
				nrStandardPerpendicular = i;
			}
		}
		
		return nrStandardPerpendicular;
	}

}
