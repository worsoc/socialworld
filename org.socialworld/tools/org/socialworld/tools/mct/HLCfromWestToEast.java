package org.socialworld.tools.mct;

import java.util.List;

class HLCfromWestToEast extends SubHLC {

	private HLCfromWestToEast() {
	}
	
	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromWestToEast();
		}
		return instance;
		
	}

	@Override
	boolean checkExistAllowedHLC(int start, int end) {
		// SUB_CLASS_IMPLEMENTATION
		// TEMP_SOLUTION
		return false;
	}

	@Override
	List<HeightLevelChanger> getAllowedHLC(int start, int end, List<Integer> subClusterSorted) {
		// SUB_CLASS_IMPLEMENTATION
		// TEMP_SOLUTION
		return null;
	}

}
