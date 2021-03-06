package org.socialworld.tools.mct.isles;

import java.util.List;

import org.socialworld.tools.mct.HeightIsle;
import org.socialworld.tools.mct.SubIsle;

public class SubIsle47 extends SubIsle {

	private SubIsle47() {
	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle47();
		}
		return instance;
		
	}
	
	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
