package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;

public class TileGroundDescriptionPool {

	private static final String ZEROS_0 = "";
	private static final String ZEROS_1 = "0";
	private static final String ZEROS_2 = "00";
	private static final String ZEROS_3 = "000";
	private static final String ZEROS_4 = "0000";
	private static final String ZEROS_5 = "00000";
	private static final String ZEROS_6 = "000000";
	private static final String ZEROS_7 = "0000000";
	private static final String ZEROS_8 = "00000000";
	
	public static final String CentreCubeSign = "#";
	
	void getGroundDescription(
			int heightLevel, int[] heightLevelThresholds, List<String> praefixs, int numberOfZeros,
			TileGroundDescription groundDesciption) {
		
		List<String> praefixsNextDepth = new ArrayList<String>();
		int numberOfZerosNextDepth = numberOfZeros - 1;
		int[] heightLevelThresholdsNextDepth = new int[2];
		int thresholdRange = (int) (heightLevelThresholds[1] - heightLevelThresholds[0]) / 3;
		
		if (heightLevel > heightLevelThresholds[1]) {
			
			for (String praefix : praefixs) {
				
				groundDesciption.positionCubesForGrounds.add(praefix + "A" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "B" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "C" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "D" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "E" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "F" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "G" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "H" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "I" + getZeros(numberOfZeros));

				groundDesciption.positionCubesForGrounds.add(praefix + "J" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "K" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "L" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "M" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + CentreCubeSign + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "N" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "O" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "P" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "Q" + getZeros(numberOfZeros));
				
				praefixsNextDepth.add(praefix + "R");
				praefixsNextDepth.add(praefix + "S");
				praefixsNextDepth.add(praefix + "T");
				praefixsNextDepth.add(praefix + "U");
				praefixsNextDepth.add(praefix + "V");
				praefixsNextDepth.add(praefix + "W");
				praefixsNextDepth.add(praefix + "X");
				praefixsNextDepth.add(praefix + "Y");
				praefixsNextDepth.add(praefix + "Z");
				
				heightLevelThresholdsNextDepth[0] = heightLevelThresholds[1] + thresholdRange;
				heightLevelThresholdsNextDepth[1] = heightLevelThresholds[1] + (2 * thresholdRange);
				
				getGroundDescription(heightLevel, 
						heightLevelThresholdsNextDepth, praefixsNextDepth, numberOfZerosNextDepth,
						groundDesciption);
				
			}
		}
		else
		if (heightLevel > heightLevelThresholds[0]) {
				
			for (String praefix : praefixs) {
					
				groundDesciption.positionCubesForGrounds.add(praefix + "A" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "B" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "C" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "D" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "E" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "F" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "G" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "H" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForGrounds.add(praefix + "I" + getZeros(numberOfZeros));

				praefixsNextDepth.add(praefix + "J");
				praefixsNextDepth.add(praefix + "K");
				praefixsNextDepth.add(praefix + "L");
				praefixsNextDepth.add(praefix + "M");
				praefixsNextDepth.add(praefix + CentreCubeSign);
				praefixsNextDepth.add(praefix + "N");
				praefixsNextDepth.add(praefix + "O");
				praefixsNextDepth.add(praefix + "P");
				praefixsNextDepth.add(praefix + "Q");
				
				groundDesciption.positionCubesForNoGrounds.add(praefix + "R" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "S" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "T" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "U" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "V" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "W" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "X" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "Y" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "Z" + getZeros(numberOfZeros));
				
				
				heightLevelThresholdsNextDepth[0] = heightLevelThresholds[0] + thresholdRange;
				heightLevelThresholdsNextDepth[1] = heightLevelThresholds[0] + (2 * thresholdRange);
				
				getGroundDescription(heightLevel, 
						heightLevelThresholdsNextDepth, praefixsNextDepth, numberOfZerosNextDepth,
						groundDesciption);
				
			}
		}
		else {

			for (String praefix : praefixs) {

				praefixsNextDepth.add(praefix + "A");
				praefixsNextDepth.add(praefix + "B");
				praefixsNextDepth.add(praefix + "C");
				praefixsNextDepth.add(praefix + "D");
				praefixsNextDepth.add(praefix + "E");
				praefixsNextDepth.add(praefix + "F");
				praefixsNextDepth.add(praefix + "G");
				praefixsNextDepth.add(praefix + "H");
				praefixsNextDepth.add(praefix + "I");

				groundDesciption.positionCubesForNoGrounds.add(praefix + "J" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "K" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "L" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "M" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + CentreCubeSign + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "N" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "O" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "P" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "Q" + getZeros(numberOfZeros));
				
				groundDesciption.positionCubesForNoGrounds.add(praefix + "R" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "S" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "T" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "U" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "V" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "W" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "X" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "Y" + getZeros(numberOfZeros));
				groundDesciption.positionCubesForNoGrounds.add(praefix + "Z" + getZeros(numberOfZeros));
				
				
				heightLevelThresholdsNextDepth[0] = thresholdRange;
				heightLevelThresholdsNextDepth[1] = 2 * thresholdRange;
				
				getGroundDescription(heightLevel, 
						heightLevelThresholdsNextDepth, praefixsNextDepth, numberOfZerosNextDepth,
						groundDesciption);
				
			}
			
		}

	}

	private String getZeros(int numberOfZeros) {
		String result;
		
		switch (numberOfZeros) {
		case 0:
			result = ZEROS_0; break;
		case 1:
			result = ZEROS_1; break;
		case 2:
			result = ZEROS_2; break;
		case 3:
			result = ZEROS_3; break;
		case 4:
			result = ZEROS_4; break;
		case 5:
			result = ZEROS_5; break;
		default:
			result = ZEROS_0;
			
		}
		
		return result;
	}
}
