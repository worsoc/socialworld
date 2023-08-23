package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;

public class HeightLevelChanger {

	int nrHLC;
	int nrNeighbourCluster;
	int neighbourIndex;
	int start;
	int end;
	
	private List<Integer> rasterIndices;
	private List<Integer> cornerMaximaNrs;
	
	boolean isValid = false;
	
	HeightLevelChanger(int nrHLC, int start, int end, List<Integer> rasterIndices, List<Integer> cornerMaximaNrs) {
		this.nrHLC = nrHLC;
		this.nrNeighbourCluster = (int) (nrHLC % 1000) / 10;
		this.neighbourIndex = nrHLC %  10;
		this.start = start;
		this.end = end;
		this.rasterIndices = rasterIndices;
		this.cornerMaximaNrs = cornerMaximaNrs;
		
		isValid = rasterIndices.size() == cornerMaximaNrs.size();
	}
	
	List<Integer> getRasterIndices() {
		return new ArrayList<Integer>(this.rasterIndices);
	}
	
	List<Integer> getCornerMaximaNrs() {
		return new ArrayList<Integer>(this.cornerMaximaNrs);
	}

	boolean isValid() {
		return isValid;
	}
}
