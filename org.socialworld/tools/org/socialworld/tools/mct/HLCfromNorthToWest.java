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

class HLCfromNorthToWest extends SubHLC {

	private static int[] startAt0 = 
		{};
	private static int[] startAt1 = 
		{4001};
	private static int[] startAt2 = 
		{4002};
	private static int[] startAt3 = 
		{4003};
	private static int[] startAt4 = 
		{4004};
	private static int[] startAt5 = 
		{4005};
	private static int[] startAt6 = 
		{4006};
	private static int[] startAt7 = 
		{4007};
	private static int[] startAt8 = 
		{};

	private static int[] endAt0 = 
		{};
	private static int[] endAt9 = 
		{4001};
	private static int[] endAt18 = 
		{4002};
	private static int[] endAt27 = 
		{4003};
	private static int[] endAt36 = 
		{4004};
	private static int[] endAt45 = 
		{4005};
	private static int[] endAt54 = 
		{4006};
	private static int[] endAt63 = 
		{4007};
	private static int[] endAt72 = 
		{};

	private static int[] rasterIndicesHLC_4001 = {1,9,10};
	private static Integer[] cornerMaximaNrsHLC_4001 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4001At01;
	private List<Integer> cornerMaximaNrs_4001;

	private static int[] rasterIndicesHLC_4002 = {2,11,18,19,20};
	private static Integer[] cornerMaximaNrsHLC_4002 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4002At02;
	private List<Integer> cornerMaximaNrs_4002;

	private static int[] rasterIndicesHLC_4003 = {3,12,21,27,28,29,30};
	private static Integer[] cornerMaximaNrsHLC_4003 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4003At03;
	private List<Integer> cornerMaximaNrs_4003;

	private static int[] rasterIndicesHLC_4004 = {4,13,22,31,36,37,38,39,40};
	private static Integer[] cornerMaximaNrsHLC_4004 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4004At04;
	private List<Integer> cornerMaximaNrs_4004;

	private static int[] rasterIndicesHLC_4005 = {5,14,23,32,41,45,46,47,48,49,50};
	private static Integer[] cornerMaximaNrsHLC_4005 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4005At05;
	private List<Integer> cornerMaximaNrs_4005;

	private static int[] rasterIndicesHLC_4006 = {6,15,24,33,42,51,54,55,56,57,58,59,60};
	private static Integer[] cornerMaximaNrsHLC_4006 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4006At06;
	private List<Integer> cornerMaximaNrs_4006;

	private static int[] rasterIndicesHLC_4007 = {7,16,25,34,43,52,61,63,64,65,66,67,68,69,70};
	private static Integer[] cornerMaximaNrsHLC_4007 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_4007At07;
	private List<Integer> cornerMaximaNrs_4007;

	private HLCfromNorthToWest() {
	rasterIndices_4001At01 = getRasterIndicesList(1, rasterIndicesHLC_4001);
	cornerMaximaNrs_4001 = Arrays.asList(cornerMaximaNrsHLC_4001);

	rasterIndices_4002At02 = getRasterIndicesList(2, rasterIndicesHLC_4002);
	cornerMaximaNrs_4002 = Arrays.asList(cornerMaximaNrsHLC_4002);

	rasterIndices_4003At03 = getRasterIndicesList(3, rasterIndicesHLC_4003);
	cornerMaximaNrs_4003 = Arrays.asList(cornerMaximaNrsHLC_4003);

	rasterIndices_4004At04 = getRasterIndicesList(4, rasterIndicesHLC_4004);
	cornerMaximaNrs_4004 = Arrays.asList(cornerMaximaNrsHLC_4004);

	rasterIndices_4005At05 = getRasterIndicesList(5, rasterIndicesHLC_4005);
	cornerMaximaNrs_4005 = Arrays.asList(cornerMaximaNrsHLC_4005);

	rasterIndices_4006At06 = getRasterIndicesList(6, rasterIndicesHLC_4006);
	cornerMaximaNrs_4006 = Arrays.asList(cornerMaximaNrsHLC_4006);

	rasterIndices_4007At07 = getRasterIndicesList(7, rasterIndicesHLC_4007);
	cornerMaximaNrs_4007 = Arrays.asList(cornerMaximaNrsHLC_4007);

}

	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromNorthToWest();
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
		case 0:
			ends = endAt0; break;
		case 9:
			ends = endAt9; break;
		case 18:
			ends = endAt18; break;
		case 27:
			ends = endAt27; break;
		case 36:
			ends = endAt36; break;
		case 45:
			ends = endAt45; break;
		case 54:
			ends = endAt54; break;
		case 63:
			ends = endAt63; break;
		case 72:
			ends = endAt72; break;
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
		case 0:
			ends = endAt0; break;
		case 9:
			ends = endAt9; break;
		case 18:
			ends = endAt18; break;
		case 27:
			ends = endAt27; break;
		case 36:
			ends = endAt36; break;
		case 45:
			ends = endAt45; break;
		case 54:
			ends = endAt54; break;
		case 63:
			ends = endAt63; break;
		case 72:
			ends = endAt72; break;
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
			case 4001: result = rasterIndices_4001At01; break;
			}
		case 2:
			switch (nrHLC) {
			case 4002: result = rasterIndices_4002At02; break;
			}
		case 3:
			switch (nrHLC) {
			case 4003: result = rasterIndices_4003At03; break;
			}
		case 4:
			switch (nrHLC) {
			case 4004: result = rasterIndices_4004At04; break;
			}
		case 5:
			switch (nrHLC) {
			case 4005: result = rasterIndices_4005At05; break;
			}
		case 6:
			switch (nrHLC) {
			case 4006: result = rasterIndices_4006At06; break;
			}
		case 7:
			switch (nrHLC) {
			case 4007: result = rasterIndices_4007At07; break;
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
		case 4001: result = cornerMaximaNrs_4001; break;
		case 4002: result = cornerMaximaNrs_4002; break;
		case 4003: result = cornerMaximaNrs_4003; break;
		case 4004: result = cornerMaximaNrs_4004; break;
		case 4005: result = cornerMaximaNrs_4005; break;
		case 4006: result = cornerMaximaNrs_4006; break;
		case 4007: result = cornerMaximaNrs_4007; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}

