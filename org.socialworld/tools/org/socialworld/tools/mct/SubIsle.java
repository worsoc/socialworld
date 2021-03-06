package org.socialworld.tools.mct;

import java.util.List;

public abstract class SubIsle {

	protected static SubIsle instance;
	
	protected abstract int checkForIsle(List<Integer> isleSubs);
	protected abstract HeightIsle getIsleAtRasterIndex(int rasterIndex);
	
	protected final  boolean checkForMatch(List<Integer> isleSubs, List<Integer> rasterIndizesDescripingTheIsle) {
		
		
		return true;

	}

}
