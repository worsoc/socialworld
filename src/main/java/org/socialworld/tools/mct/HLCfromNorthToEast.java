/*
* Social World
* Copyright (C) 2021  Damian Zapadka
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HLCfromNorthToEast extends SubHLC {

	private static int[] startAt0 = 
		{};
	private static int[] startAt1 = 
		{3001};
	private static int[] startAt2 = 
		{3002};
	private static int[] startAt3 = 
		{3003};
	private static int[] startAt4 = 
		{3004};
	private static int[] startAt5 = 
		{3005};
	private static int[] startAt6 = 
		{3006};
	private static int[] startAt7 = 
		{3007};
	private static int[] startAt8 = 
		{};

	private static int[] endAt8 = 
		{};
	private static int[] endAt17 = 
		{3007};
	private static int[] endAt26 = 
		{3006};
	private static int[] endAt35 = 
		{3005};
	private static int[] endAt44 = 
		{3004};
	private static int[] endAt53 = 
		{3003};
	private static int[] endAt62 = 
		{3002};
	private static int[] endAt71 = 
		{3001};
	private static int[] endAt80 = 
		{};

	private static int[] rasterIndicesHLC_3001 = {1,10,19,28,37,46,55,64,65,66,67,68,69,70,71};
	private static Integer[] cornerMaximaNrsHLC_3001 =
		{19914, 19914, 19914, 19914, 19914, 19914, 19914, 19114, 99114, 99114, 99114, 99114, 99114, 99114, 99114};
	private List<Integer> rasterIndices_3001At01;
	private List<Integer> cornerMaximaNrs_3001;

	private static int[] rasterIndicesHLC_3002 = {2,11,20,29,38,47,56,57,58,59,60,61,62};
	private static Integer[] cornerMaximaNrsHLC_3002 =
		{19914, 19914, 19914, 19914, 19914, 19914, 19114, 99114, 99114, 99114, 99114, 99114, 99114};
	private List<Integer> rasterIndices_3002At02;
	private List<Integer> cornerMaximaNrs_3002;

	private static int[] rasterIndicesHLC_3003 = {3,12,21,30,39,48,49,50,51,52,53};
	private static Integer[] cornerMaximaNrsHLC_3003 =
		{19914, 19914, 19914, 19914, 19914, 19114, 99114, 99114, 99114, 99114, 99114};
	private List<Integer> rasterIndices_3003At03;
	private List<Integer> cornerMaximaNrs_3003;

	private static int[] rasterIndicesHLC_3004 = {4,13,22,31,40,41,42,43,44};
	private static Integer[] cornerMaximaNrsHLC_3004 =
		{19914, 19914, 19914, 19914, 19114, 99114, 99114, 99114, 99114};
	private List<Integer> rasterIndices_3004At04;
	private List<Integer> cornerMaximaNrs_3004;

	private static int[] rasterIndicesHLC_3005 = {5,14,23,32,33,34,35};
	private static Integer[] cornerMaximaNrsHLC_3005 =
		{19914, 19914, 19914, 19114, 99114, 99114, 99114};
	private List<Integer> rasterIndices_3005At05;
	private List<Integer> cornerMaximaNrs_3005;

	private static int[] rasterIndicesHLC_3006 = {6,15,24,25,26};
	private static Integer[] cornerMaximaNrsHLC_3006 =
		{19914, 19914, 19114, 99114, 99114};
	private List<Integer> rasterIndices_3006At06;
	private List<Integer> cornerMaximaNrs_3006;

	private static int[] rasterIndicesHLC_3007 = {7,16,17};
	private static Integer[] cornerMaximaNrsHLC_3007 =
		{19914, 19114, 99114};
	private List<Integer> rasterIndices_3007At07;
	private List<Integer> cornerMaximaNrs_3007;

	private HLCfromNorthToEast() {
	rasterIndices_3001At01 = getRasterIndicesList(1, rasterIndicesHLC_3001);
	cornerMaximaNrs_3001 = Arrays.asList(cornerMaximaNrsHLC_3001);

	rasterIndices_3002At02 = getRasterIndicesList(2, rasterIndicesHLC_3002);
	cornerMaximaNrs_3002 = Arrays.asList(cornerMaximaNrsHLC_3002);

	rasterIndices_3003At03 = getRasterIndicesList(3, rasterIndicesHLC_3003);
	cornerMaximaNrs_3003 = Arrays.asList(cornerMaximaNrsHLC_3003);

	rasterIndices_3004At04 = getRasterIndicesList(4, rasterIndicesHLC_3004);
	cornerMaximaNrs_3004 = Arrays.asList(cornerMaximaNrsHLC_3004);

	rasterIndices_3005At05 = getRasterIndicesList(5, rasterIndicesHLC_3005);
	cornerMaximaNrs_3005 = Arrays.asList(cornerMaximaNrsHLC_3005);

	rasterIndices_3006At06 = getRasterIndicesList(6, rasterIndicesHLC_3006);
	cornerMaximaNrs_3006 = Arrays.asList(cornerMaximaNrsHLC_3006);

	rasterIndices_3007At07 = getRasterIndicesList(7, rasterIndicesHLC_3007);
	cornerMaximaNrs_3007 = Arrays.asList(cornerMaximaNrsHLC_3007);

}

	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromNorthToEast();
		}
		return instance;

	}

	@Override
	boolean checkExistAllowedHLC(int start, int ende) {
		int[] starts;
		int[] ends = new int[0];
		switch (start) {
		case 0:
			starts = startAt0; break;
		case 1:
			starts = startAt1; break;
		case 2:
			starts = startAt2; break;
		case 3:
			starts = startAt3; break;
		case 4:
			starts = startAt4; break;
		case 5:
			starts = startAt5; break;
		case 6:
			starts = startAt6; break;
		case 7:
			starts = startAt7; break;
		case 8:
			starts = startAt8; break;
		default:
			starts = new int[0];
		}
		switch (ende) {
		case 8:
			ends = endAt8; break;
		case 17:
			ends = endAt17; break;
		case 26:
			ends = endAt26; break;
		case 35:
			ends = endAt35; break;
		case 44:
			ends = endAt44; break;
		case 53:
			ends = endAt53; break;
		case 62:
			ends = endAt62; break;
		case 71:
			ends = endAt71; break;
		case 80:
			ends = endAt80; break;
		default:
			ends = new int[0];
		}

		return checkForIntersection(starts, ends);
	}

	@Override
	List<HeightLevelChanger> getAllowedHLC(int start, int end, List<Integer> subClusterSorted) {

		List<HeightLevelChanger> result = new ArrayList<HeightLevelChanger>();
		List<Integer> nrsHLCwithStartAndEnd ;
		int[] starts;
		int[] ends;
		switch (start) {
		case 0:
			starts = startAt0; break;
		case 1:
			starts = startAt1; break;
		case 2:
			starts = startAt2; break;
		case 3:
			starts = startAt3; break;
		case 4:
			starts = startAt4; break;
		case 5:
			starts = startAt5; break;
		case 6:
			starts = startAt6; break;
		case 7:
			starts = startAt7; break;
		case 8:
			starts = startAt8; break;
		default:
			starts = new int[0];
		}
		switch (end) {
		case 8:
			ends = endAt8; break;
		case 17:
			ends = endAt17; break;
		case 26:
			ends = endAt26; break;
		case 35:
			ends = endAt35; break;
		case 44:
			ends = endAt44; break;
		case 53:
			ends = endAt53; break;
		case 62:
			ends = endAt62; break;
		case 71:
			ends = endAt71; break;
		case 80:
			ends = endAt80; break;
		default:
			ends = new int[0];
		}

		nrsHLCwithStartAndEnd = getIntersection(starts, ends);
		for (Integer nrHLC  : nrsHLCwithStartAndEnd) {
			List<Integer> rasterIndices = getHLCRasterIndices(nrHLC, start);
			List<Integer> cornerMaximaNrs = getHLCCornerMaximaNrs(nrHLC);
			HeightLevelChanger hlc = new HeightLevelChanger(nrHLC, start, end, rasterIndices, cornerMaximaNrs);
			result.add(hlc);
		}

		return result;
	}

	private List<Integer> getHLCRasterIndices(int nrHLC, int start) {

		List<Integer> result;

		switch (start) {
		case 0:
			switch (nrHLC) {
			}
		case 1:
			switch (nrHLC) {
			case 3001: result = rasterIndices_3001At01; break;
			}
		case 2:
			switch (nrHLC) {
			case 3002: result = rasterIndices_3002At02; break;
			}
		case 3:
			switch (nrHLC) {
			case 3003: result = rasterIndices_3003At03; break;
			}
		case 4:
			switch (nrHLC) {
			case 3004: result = rasterIndices_3004At04; break;
			}
		case 5:
			switch (nrHLC) {
			case 3005: result = rasterIndices_3005At05; break;
			}
		case 6:
			switch (nrHLC) {
			case 3006: result = rasterIndices_3006At06; break;
			}
		case 7:
			switch (nrHLC) {
			case 3007: result = rasterIndices_3007At07; break;
			}
		case 8:
			switch (nrHLC) {
			}

		default:
			result = new ArrayList<Integer>();
		}

		return result;
	}

	private List<Integer> getHLCCornerMaximaNrs(int nrHLC) {
		List<Integer> result;
		switch (nrHLC) {
		case 3001: result = cornerMaximaNrs_3001; break;
		case 3002: result = cornerMaximaNrs_3002; break;
		case 3003: result = cornerMaximaNrs_3003; break;
		case 3004: result = cornerMaximaNrs_3004; break;
		case 3005: result = cornerMaximaNrs_3005; break;
		case 3006: result = cornerMaximaNrs_3006; break;
		case 3007: result = cornerMaximaNrs_3007; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}
