package org.socialworld.tools.mct;

import java.util.List;

class HLCfromNorthToEast extends SubHLC {

	private HLCfromNorthToEast() {
	}
	
	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromNorthToEast();
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
