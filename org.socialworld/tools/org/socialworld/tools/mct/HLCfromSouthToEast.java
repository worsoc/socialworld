package org.socialworld.tools.mct;

import java.util.List;

class HLCfromSouthToEast extends SubHLC {

	private HLCfromSouthToEast() {
	}
	
	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromSouthToEast();
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
