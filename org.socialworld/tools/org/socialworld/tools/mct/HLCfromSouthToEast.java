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

class HLCfromSouthToEast extends SubHLC {

	private static int[] startAt72 = 
		{};
	private static int[] startAt73 = 
		{6001};
	private static int[] startAt74 = 
		{6002};
	private static int[] startAt75 = 
		{6003};
	private static int[] startAt76 = 
		{6004};
	private static int[] startAt77 = 
		{6005};
	private static int[] startAt78 = 
		{6006};
	private static int[] startAt79 = 
		{6007};
	private static int[] startAt80 = 
		{};

	private static int[] endAt8 = 
		{};
	private static int[] endAt17 = 
		{6001};
	private static int[] endAt26 = 
		{6002};
	private static int[] endAt35 = 
		{6003};
	private static int[] endAt44 = 
		{6004};
	private static int[] endAt53 = 
		{6005};
	private static int[] endAt62 = 
		{6006};
	private static int[] endAt71 = 
		{6007};
	private static int[] endAt80 = 
		{};

	private static int[] rasterIndicesHLC_6001 = {10,11,12,13,14,15,16,17,19,28,37,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_6001 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6001At73;
	private List<Integer> cornerMaximaNrs_6001;

	private static int[] rasterIndicesHLC_6002 = {20,21,22,23,24,25,26,29,38,47,56,65,74};
	private static Integer[] cornerMaximaNrsHLC_6002 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6002At74;
	private List<Integer> cornerMaximaNrs_6002;

	private static int[] rasterIndicesHLC_6003 = {30,31,32,33,34,35,39,48,57,66,75};
	private static Integer[] cornerMaximaNrsHLC_6003 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6003At75;
	private List<Integer> cornerMaximaNrs_6003;

	private static int[] rasterIndicesHLC_6004 = {40,41,42,43,44,49,58,67,76};
	private static Integer[] cornerMaximaNrsHLC_6004 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6004At76;
	private List<Integer> cornerMaximaNrs_6004;

	private static int[] rasterIndicesHLC_6005 = {50,51,52,53,59,68,77};
	private static Integer[] cornerMaximaNrsHLC_6005 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6005At77;
	private List<Integer> cornerMaximaNrs_6005;

	private static int[] rasterIndicesHLC_6006 = {60,61,62,69,78};
	private static Integer[] cornerMaximaNrsHLC_6006 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6006At78;
	private List<Integer> cornerMaximaNrs_6006;

	private static int[] rasterIndicesHLC_6007 = {70,71,79};
	private static Integer[] cornerMaximaNrsHLC_6007 =
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_6007At79;
	private List<Integer> cornerMaximaNrs_6007;

	private HLCfromSouthToEast() {
	rasterIndices_6001At73 = getRasterIndicesList(1, rasterIndicesHLC_6001);
	cornerMaximaNrs_6001 = Arrays.asList(cornerMaximaNrsHLC_6001);

	rasterIndices_6002At74 = getRasterIndicesList(2, rasterIndicesHLC_6002);
	cornerMaximaNrs_6002 = Arrays.asList(cornerMaximaNrsHLC_6002);

	rasterIndices_6003At75 = getRasterIndicesList(3, rasterIndicesHLC_6003);
	cornerMaximaNrs_6003 = Arrays.asList(cornerMaximaNrsHLC_6003);

	rasterIndices_6004At76 = getRasterIndicesList(4, rasterIndicesHLC_6004);
	cornerMaximaNrs_6004 = Arrays.asList(cornerMaximaNrsHLC_6004);

	rasterIndices_6005At77 = getRasterIndicesList(5, rasterIndicesHLC_6005);
	cornerMaximaNrs_6005 = Arrays.asList(cornerMaximaNrsHLC_6005);

	rasterIndices_6006At78 = getRasterIndicesList(6, rasterIndicesHLC_6006);
	cornerMaximaNrs_6006 = Arrays.asList(cornerMaximaNrsHLC_6006);

	rasterIndices_6007At79 = getRasterIndicesList(7, rasterIndicesHLC_6007);
	cornerMaximaNrs_6007 = Arrays.asList(cornerMaximaNrsHLC_6007);

}

	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromSouthToEast();
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
		case 72:
			switch (nrHLC) {
			}
		case 73:
			switch (nrHLC) {
			case 6001: result = rasterIndices_6001At73; break;
			}
		case 74:
			switch (nrHLC) {
			case 6002: result = rasterIndices_6002At74; break;
			}
		case 75:
			switch (nrHLC) {
			case 6003: result = rasterIndices_6003At75; break;
			}
		case 76:
			switch (nrHLC) {
			case 6004: result = rasterIndices_6004At76; break;
			}
		case 77:
			switch (nrHLC) {
			case 6005: result = rasterIndices_6005At77; break;
			}
		case 78:
			switch (nrHLC) {
			case 6006: result = rasterIndices_6006At78; break;
			}
		case 79:
			switch (nrHLC) {
			case 6007: result = rasterIndices_6007At79; break;
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
		case 6001: result = cornerMaximaNrs_6001; break;
		case 6002: result = cornerMaximaNrs_6002; break;
		case 6003: result = cornerMaximaNrs_6003; break;
		case 6004: result = cornerMaximaNrs_6004; break;
		case 6005: result = cornerMaximaNrs_6005; break;
		case 6006: result = cornerMaximaNrs_6006; break;
		case 6007: result = cornerMaximaNrs_6007; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}
