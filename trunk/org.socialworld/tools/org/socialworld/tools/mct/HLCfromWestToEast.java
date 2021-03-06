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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	List<HeightLevelChanger> getAllowedHLC(int start, int end, List<Integer> subClusterSorted) {
		// TODO Auto-generated method stub
		return null;
	}

}
