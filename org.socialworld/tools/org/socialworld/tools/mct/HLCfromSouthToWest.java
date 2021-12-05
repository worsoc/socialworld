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

class HLCfromSouthToWest extends SubHLC {

	private static int[] startAt72 = 
		{};
	private static int[] startAt73 = 
		{5001};
	private static int[] startAt74 = 
		{5002};
	private static int[] startAt75 = 
		{5003};
	private static int[] startAt76 = 
		{5004};
	private static int[] startAt77 = 
		{5005};
	private static int[] startAt78 = 
		{5006};
	private static int[] startAt79 = 
		{5007};
	private static int[] startAt80 = 
		{};

	private static int[] endAt0 = 
		{};
	private static int[] endAt9 = 
		{5007};
	private static int[] endAt18 = 
		{5006};
	private static int[] endAt27 = 
		{5005};
	private static int[] endAt36 = 
		{5004};
	private static int[] endAt45 = 
		{5003};
	private static int[] endAt54 = 
		{5002};
	private static int[] endAt63 = 
		{5001};
	private static int[] endAt72 = 
		{};

	private static int[] rasterIndicesHLC_5001 = {63,64,73};
	private static Integer[] cornerMaximaNrsHLC_5001 =
		{99114, 99914, 19914};
	private List<Integer> rasterIndices_5001At73;
	private List<Integer> cornerMaximaNrs_5001;

	private static int[] rasterIndicesHLC_5002 = {54,55,56,65,74};
	private static Integer[] cornerMaximaNrsHLC_5002 =
		{99114, 99114, 99914, 19914, 19914};
	private List<Integer> rasterIndices_5002At74;
	private List<Integer> cornerMaximaNrs_5002;

	private static int[] rasterIndicesHLC_5003 = {45,46,47,48,57,66,75};
	private static Integer[] cornerMaximaNrsHLC_5003 =
		{99114, 99114, 99114, 99914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_5003At75;
	private List<Integer> cornerMaximaNrs_5003;

	private static int[] rasterIndicesHLC_5004 = {36,37,38,39,40,49,58,67,76};
	private static Integer[] cornerMaximaNrsHLC_5004 =
		{99114, 99114, 99114, 99114, 99914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_5004At76;
	private List<Integer> cornerMaximaNrs_5004;

	private static int[] rasterIndicesHLC_5005 = {27,28,29,30,31,32,41,50,59,68,77};
	private static Integer[] cornerMaximaNrsHLC_5005 =
		{99114, 99114, 99114, 99114, 99114, 99914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_5005At77;
	private List<Integer> cornerMaximaNrs_5005;

	private static int[] rasterIndicesHLC_5006 = {18,19,20,21,22,23,24,33,42,51,60,69,78};
	private static Integer[] cornerMaximaNrsHLC_5006 =
		{99114, 99114, 99114, 99114, 99114, 99114, 99914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_5006At78;
	private List<Integer> cornerMaximaNrs_5006;

	private static int[] rasterIndicesHLC_5007 = {9,10,11,12,13,14,15,16,25,34,43,52,61,70,79};
	private static Integer[] cornerMaximaNrsHLC_5007 =
		{99114, 99114, 99114, 99114, 99114, 99114, 99114, 99914, 19914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_5007At79;
	private List<Integer> cornerMaximaNrs_5007;

	private HLCfromSouthToWest() {
	rasterIndices_5001At73 = getRasterIndicesList(1, rasterIndicesHLC_5001);
	cornerMaximaNrs_5001 = Arrays.asList(cornerMaximaNrsHLC_5001);

	rasterIndices_5002At74 = getRasterIndicesList(2, rasterIndicesHLC_5002);
	cornerMaximaNrs_5002 = Arrays.asList(cornerMaximaNrsHLC_5002);

	rasterIndices_5003At75 = getRasterIndicesList(3, rasterIndicesHLC_5003);
	cornerMaximaNrs_5003 = Arrays.asList(cornerMaximaNrsHLC_5003);

	rasterIndices_5004At76 = getRasterIndicesList(4, rasterIndicesHLC_5004);
	cornerMaximaNrs_5004 = Arrays.asList(cornerMaximaNrsHLC_5004);

	rasterIndices_5005At77 = getRasterIndicesList(5, rasterIndicesHLC_5005);
	cornerMaximaNrs_5005 = Arrays.asList(cornerMaximaNrsHLC_5005);

	rasterIndices_5006At78 = getRasterIndicesList(6, rasterIndicesHLC_5006);
	cornerMaximaNrs_5006 = Arrays.asList(cornerMaximaNrsHLC_5006);

	rasterIndices_5007At79 = getRasterIndicesList(7, rasterIndicesHLC_5007);
	cornerMaximaNrs_5007 = Arrays.asList(cornerMaximaNrsHLC_5007);

}

	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromSouthToWest();
		}
		return instance;

	}

	@Override
	boolean checkExistAllowedHLC(int start, int ende) {
		int[] starts;
		int[] ends = new int[0];
		switch (start) {
		case 72:
			starts = startAt72; break;
		case 73:
			starts = startAt73; break;
		case 74:
			starts = startAt74; break;
		case 75:
			starts = startAt75; break;
		case 76:
			starts = startAt76; break;
		case 77:
			starts = startAt77; break;
		case 78:
			starts = startAt78; break;
		case 79:
			starts = startAt79; break;
		case 80:
			starts = startAt80; break;
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
		case 72:
			starts = startAt72; break;
		case 73:
			starts = startAt73; break;
		case 74:
			starts = startAt74; break;
		case 75:
			starts = startAt75; break;
		case 76:
			starts = startAt76; break;
		case 77:
			starts = startAt77; break;
		case 78:
			starts = startAt78; break;
		case 79:
			starts = startAt79; break;
		case 80:
			starts = startAt80; break;
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
		case 72:
			switch (nrHLC) {
			}
		case 73:
			switch (nrHLC) {
			case 5001: result = rasterIndices_5001At73; break;
			}
		case 74:
			switch (nrHLC) {
			case 5002: result = rasterIndices_5002At74; break;
			}
		case 75:
			switch (nrHLC) {
			case 5003: result = rasterIndices_5003At75; break;
			}
		case 76:
			switch (nrHLC) {
			case 5004: result = rasterIndices_5004At76; break;
			}
		case 77:
			switch (nrHLC) {
			case 5005: result = rasterIndices_5005At77; break;
			}
		case 78:
			switch (nrHLC) {
			case 5006: result = rasterIndices_5006At78; break;
			}
		case 79:
			switch (nrHLC) {
			case 5007: result = rasterIndices_5007At79; break;
			}
		case 80:
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
		case 5001: result = cornerMaximaNrs_5001; break;
		case 5002: result = cornerMaximaNrs_5002; break;
		case 5003: result = cornerMaximaNrs_5003; break;
		case 5004: result = cornerMaximaNrs_5004; break;
		case 5005: result = cornerMaximaNrs_5005; break;
		case 5006: result = cornerMaximaNrs_5006; break;
		case 5007: result = cornerMaximaNrs_5007; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}

