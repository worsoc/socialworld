/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HeightChangeChecker {

	private static HeightChangeChecker instance;

	Set<Integer> east00;
	Set<Integer> east01;
	Set<Integer> east10;
	Set<Integer> east11;
	Set<Integer> east_10;
	Set<Integer> east0_1;

	Set<Integer> west00;
	Set<Integer> west01;
	Set<Integer> west10;
	Set<Integer> west11;
	Set<Integer> west_10;
	Set<Integer> west0_1;

	Set<Integer> south00;
	Set<Integer> south01;
	Set<Integer> south10;
	Set<Integer> south11;
	Set<Integer> south_10;
	Set<Integer> south0_1;

	Set<Integer> north00;
	Set<Integer> north01;
	Set<Integer> north10;
	Set<Integer> north11;
	Set<Integer> north_10;
	Set<Integer> north0_1;
	
	HeightChangeChecker() {
		
		init();
		
	}
	
	public static HeightChangeChecker getInstance() {
		if (instance == null) {
			instance = new HeightChangeChecker();
		}
		return instance;
	}

	
	boolean isEastIncrease(Tile west, Tile east) {
		
		int numberTileWest = getOffsetNumber(west);
		int numberTileEast = getOffsetNumber(east);
	
		return isEastIncrease(numberTileWest, numberTileEast);
		
	}
	
	boolean isEastDecrease(Tile west, Tile east) {
		
		int numberTileWest = getOffsetNumber(west);
		int numberTileEast = getOffsetNumber(east);
	
		return isEastDecrease(numberTileWest, numberTileEast);
		
	}

	boolean isSouthIncrease(Tile north, Tile south) {
		
		int numberTileNorth = getOffsetNumber(north);
		int numberTileSouth = getOffsetNumber(south);
	
		return isSouthIncrease(numberTileNorth, numberTileSouth);
		
	}
	
	boolean isSouthDecrease(Tile north, Tile south) {
		
		int numberTileNorth = getOffsetNumber(north);
		int numberTileSouth = getOffsetNumber(south);
	
		return isSouthDecrease(numberTileNorth, numberTileSouth);
		
	}
	
	private boolean isEastIncrease(int west, int east) {
		
		switch (getEdgeTypeEast(west)) {
		case 0 /* 00 */: return false;
		case 1 /* 01 */: return west_10.contains(east);
		case 2 /* 10 */: return west0_1.contains(east);
		case 3 /* 11 */: return west00.contains(east);
		case 4 /* -10 */: return false;
		case 5 /* 0-1 */: return false;
		}
		return false;
	}
	
	private boolean isEastDecrease(int west, int east) {
		
		switch (getEdgeTypeEast(west)) {
		case 0 /* 00 */: return west11.contains(east);
		case 1 /* 01 */: return false;
		case 2 /* 10 */: return false;
		case 3 /* 11 */: return false;
		case 4 /* -10 */: return west01.contains(east);
		case 5 /* 0-1 */: return west10.contains(east);
		}
		return false;
	}
	
	private int getEdgeTypeEast(int number) {
		
		if (east00.contains(number)) return 0;
		if (east01.contains(number)) return 1;
		if (east10.contains(number)) return 2;
		if (east11.contains(number)) return 3;
		if (east_10.contains(number)) return 4;
		if (east0_1.contains(number)) return 5;
		return 99;
	}
	
	private boolean isSouthIncrease(int north, int south) {
		
		switch (getEdgeTypeSouth(north)) {
		case 0 /* 00 */: return false;
		case 1 /* 01 */: return north_10.contains(south);
		case 2 /* 10 */: return north0_1.contains(south);
		case 3 /* 11 */: return north00.contains(south);
		case 4 /* -10 */: return false;
		case 5 /* 0-1 */: return false;
		}
		return false;
	}
	
	private boolean isSouthDecrease(int north, int south) {
		
		switch (getEdgeTypeSouth(north)) {
		case 0 /* 00 */: return north11.contains(south);
		case 1 /* 01 */: return false;
		case 2 /* 10 */: return false;
		case 3 /* 11 */: return false;
		case 4 /* -10 */: return north01.contains(south);
		case 5 /* 0-1 */: return north10.contains(south);
		}
		return false;
	}
	
	private int getEdgeTypeSouth(int number) {
		
		if (south00.contains(number)) return 0;
		if (south01.contains(number)) return 1;
		if (south10.contains(number)) return 2;
		if (south11.contains(number)) return 3;
		if (south_10.contains(number)) return 4;
		if (south0_1.contains(number)) return 5;
		return 99;
	}	
	
	private int getOffsetNumber(Tile tile) {
		
		int offsetNumber = tile.getNumber();
		
		switch (tile.getFeelAsTileType()) {
		case smallStandard: ;
		case mediumStandard: ;
		case largeStandard: offsetNumber = offsetNumber + 1000; break;
		case smallAdapter: ;
		case mediumAdapter: offsetNumber = offsetNumber + 2000; break;
		case smallSpecialAdapter: offsetNumber = offsetNumber + 3000; break;
		case smallSpecial: offsetNumber = offsetNumber + 4000; break;
		default: break;
		}
		
		return offsetNumber;
		
	}
	
	private void init() {
		
		Integer east00[] = {1000, 1002, 1008, 1010, /* standard tiles */
							2121, 2122, 2123, 2124, 2125, 2126, 2127, 2128, 2129, /* standard adapter alternative 1 */
							2141, 2142, 2143, 2144, 2145, 2146, 2147, 2148, 2149, /* standard adapter alternative 1 */
							3001, 3005, 3006, 3007, 3015, 3016,    /* special adapter (small) */
							4010     /* special (small) */
							};
		this.east00 = new HashSet<Integer>(Arrays.asList(east00));

		Integer east01[] = {1001, 1003, 1009, 1011, 1018, /* standard tiles */
							2221, 2222, 2223, 2224, 2225, 2226, 2227, 2228, 2229, /* standard adapter alternative 2 */
							2196, 2296, 2396, /* standard adapter corner SW (alternative 1, 2, 3): interpret (0, +1/9) as (0,1)  */
							3003, 3011, 3026    /* special adapter (small) */
							};
		this.east01 = new HashSet<Integer>(Arrays.asList(east01));

		Integer east10[] = {1004, 1006, 1012, 1014, 1016, /* standard tiles */
							2241, 2242, 2243, 2244, 2245, 2246, 2247, 2248, 2249, /* standard adapter alternative 2 */
							2191, 2291, 2391, /* standard adapter corner NW (alternative 1, 2, 3): interpret (+1/9 , 0) as (1,0)  */
							3004, 3017, 3025     /* special adapter (small) */
							};
		this.east10 = new HashSet<Integer>(Arrays.asList(east10));

		Integer east11[] = {1005, 1007, 1013, 1015, /* standard tiles */
							2321, 2322, 2323, 2324, 2325, 2326, 2327, 2328, 2329, /* standard adapter alternative 3 */
							2341, 2342, 2343, 2344, 2345, 2346, 2347, 2348, 2349, /* standard adapter alternative 3 */
							3013, 3014, 3021, 3023, 3024, 3027,    /* special adapter (small) */
							4005     /* special (small) */
							};
		this.east11 = new HashSet<Integer>(Arrays.asList(east11));

		Integer east_10[] = {1017, /* standard tiles */
				2195, 2295, 2395/* standard adapter corner SW (alternative 1, 2, 3): interpret (-1, -1/9) as (-1,0)  */
				};
		this.east_10 = new HashSet<Integer>(Arrays.asList(east_10));

		Integer east0_1[] = {1019, /* standard tiles */
				2192, 2292, 2392/* standard adapter corner NW (alternative 1, 2, 3): interpret (-1/9, -1) as (0,-1)  */
				};
		this.east0_1 = new HashSet<Integer>(Arrays.asList(east0_1));
		
		
		////////////////////////////////////////////////////////////////
	
		Integer west00[] = {1000, 1001, 1004, 1005, /* standard tiles */
							2111, 2112, 2113, 2114, 2115, 2116, 2117, 2118, 2119, /* standard adapter alternative 1 */
							2151, 2152, 2153, 2154, 2155, 2156, 2157, 2158, 2159, /* standard adapter alternative 1 */
							3002, 3003, 3004, 3008, 3013, 3014,    /* special adapter (small) */
							4005     /* special (small) */
							};
		this.west00 = new HashSet<Integer>(Arrays.asList(west00));
		
		Integer west01[] = {1002, 1003, 1006, 1007, 1017, /* standard tiles */
							2211, 2212, 2213, 2214, 2215, 2216, 2217, 2218, 2219, /* standard adapter alternative 2 */
							2198, 2298, 2398, /* standard adapter corner SE (alternative 1, 2, 3): interpret (0, +1/9) as (0,1)  */
							3005, 3012, 3024    /* special adapter (small) */
							};
		this.west01 = new HashSet<Integer>(Arrays.asList(west01));
		
		Integer west10[] = {1008, 1009, 1012, 1013, 1019, /* standard tiles */
							2251, 2252, 2253, 2254, 2255, 2256, 2257, 2258, 2259, /* standard adapter alternative 2 */
							2194, 2294, 2394, /* standard adapter corner NE (alternative 1, 2, 3): interpret (+1/9 , 0) as (1,0)  */
							3006, 3018, 3023     /* special adapter (small) */
							};
		this.west10 = new HashSet<Integer>(Arrays.asList(west10));
		
		Integer west11[] = {1010, 1011, 1014, 1015, /* standard tiles */
							2311, 2312, 2313, 2314, 2315, 2316, 2317, 2318, 2319, /* standard adapter alternative 3 */
							2351, 2352, 2353, 2354, 2355, 2356, 2357, 2358, 2359, /* standard adapter alternative 3 */
							3015, 3016, 3022, 3025, 3026, 3028,    /* special adapter (small) */
							4010     /* special (small) */
							};
		this.west11 = new HashSet<Integer>(Arrays.asList(west11));
		
		Integer west_10[] = {1018, /* standard tiles */
							2197, 2297, 2397/* standard adapter corner SE (alternative 1, 2, 3): interpret (-1, -1/9) as (-1,0)  */
							};
		this.west_10 = new HashSet<Integer>(Arrays.asList(west_10));
		
		Integer west0_1[] = {1016, /* standard tiles */
							2193, 2293, 2393/* standard adapter corner NE (alternative 1, 2, 3): interpret (-1/9, -1) as (0,-1)  */
							};
		this.west0_1 = new HashSet<Integer>(Arrays.asList(west0_1));

		////////////////////////////////////////////////////////////////
		
		Integer south00[] = {1000, 1004, 1008, 1012, /* standard tiles */
							2101, 2102, 2103, 2104, 2105, 2106, 2107, 2108, 2109, /* standard adapter alternative 1 */
							2131, 2132, 2133, 2134, 2135, 2136, 2137, 2138, 2139, /* standard adapter alternative 1 */
							3004, 3006, 3007, 3008, 3017, 3018,    /* special adapter (small) */
							4012     /* special (small) */
							};
		this.south00 = new HashSet<Integer>(Arrays.asList(south00));
		
		Integer south01[] = {1001, 1005, 1009, 1013, 1018, /* standard tiles */
							2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, /* standard adapter alternative 2 */
							2191, 2291, 2391, /* standard adapter corner NW (alternative 1, 2, 3): interpret (-1/9 , 0) as (0,1)  */
							3002, 3014, 3027    /* special adapter (small) */
							};
		this.south01 = new HashSet<Integer>(Arrays.asList(south01));
		
		Integer south10[] = {1002, 1006, 1010, 1014, 1017, /* standard tiles */
							2231, 2232, 2233, 2234, 2235, 2236, 2237, 2238, 2239, /* standard adapter alternative 2 */
							2194, 2294, 2394, /* standard adapter corner NE (alternative 1, 2, 3): interpret (0 , -1/9) as (1,0)  */
							3001, 3016, 3028     /* special adapter (small) */
							};
		this.south10 = new HashSet<Integer>(Arrays.asList(south10));
		
		Integer south11[] = {1003, 1007, 1011, 1015, /* standard tiles */
							2301, 2302, 2303, 2304, 2305, 2306, 2307, 2308, 2309, /* standard adapter alternative 3 */
							2331, 2332, 2333, 2334, 2335, 2336, 2337, 2338, 2339, /* standard adapter alternative 3 */
							3011, 3012, 3021, 3022, 3024, 3026,    /* special adapter (small) */
							4003     /* special (small) */
							};
		this.south11 = new HashSet<Integer>(Arrays.asList(south11));
		
		Integer south_10[] = {1016, /* standard tiles */
							  2193, 2293, 2393/* standard adapter corner NE (alternative 1, 2, 3): interpret (-1, 1/9) as (-1,0)  */
							  };
		this.south_10 = new HashSet<Integer>(Arrays.asList(south_10));
		
		Integer south0_1[] = {1019, /* standard tiles */
							  2192, 2292, 2392/* standard adapter corner NW (alternative 1, 2, 3): interpret (1/9, -1) as (0,-1)  */
							  };
		this.south0_1 = new HashSet<Integer>(Arrays.asList(south0_1));

		////////////////////////////////////////////////////////////////
		
		Integer north00[] = {1000, 1001, 1002, 1003, /* standard tiles */
							2161, 2162, 2163, 2164, 2165, 2166, 2167, 2168, 2169, /* standard adapter alternative 1 */
							2171, 2172, 2173, 2174, 2175, 2176, 2177, 2178, 2179, /* standard adapter alternative 1 */
							3001, 3002, 3003, 3005, 3011, 3012,    /* special adapter (small) */
							4003     /* special (small) */
							};
		this.north00 = new HashSet<Integer>(Arrays.asList(north00));
		
		Integer north01[] = {1004, 1005, 1006, 1007, 1016, /* standard tiles */
							2261, 2262, 2263, 2264, 2265, 2266, 2267, 2268, 2269, /* standard adapter alternative 2 */
							2196, 2296, 2396, /* standard adapter corner SW (alternative 1, 2, 3): interpret (-1/9 , 0) as (0,1)  */
							3008, 3013, 3021    /* special adapter (small) */
							};
		this.north01 = new HashSet<Integer>(Arrays.asList(north01));
		
		Integer north10[] = {1008, 1009, 1010, 1011, 1019, /* standard tiles */
							2271, 2272, 2273, 2274, 2275, 2276, 2277, 2278, 2279, /* standard adapter alternative 2 */
							2198, 2298, 2398, /* standard adapter corner SE (alternative 1, 2, 3): interpret (0 , -1/9) as (1,0)  */
							3007, 3015, 3022     /* special adapter (small) */
							};
		this.north10 = new HashSet<Integer>(Arrays.asList(north10));
		
		Integer north11[] = {1012, 1013, 1014, 1015, /* standard tiles */
							2361, 2362, 2363, 2364, 2365, 2366, 2367, 2368, 2369, /* standard adapter alternative 3 */
							2371, 2372, 2373, 2374, 2375, 2376, 2377, 2378, 2379, /* standard adapter alternative 3 */
							3017, 3018, 3023, 3025, 3027, 3028,    /* special adapter (small) */
							4012     /* special (small) */
							};
		this.north11 = new HashSet<Integer>(Arrays.asList(north11));
		
		Integer north_10[] = {1018, /* standard tiles */
							  2197, 2297, 2397/* standard adapter corner SE (alternative 1, 2, 3): interpret (-1, 1/9) as (-1,0)  */
							  };
		this.north_10 = new HashSet<Integer>(Arrays.asList(north_10));
		
		Integer north0_1[] = {1017, /* standard tiles */
							  2195, 2295, 2395/* standard adapter corner SW (alternative 1, 2, 3): interpret (1/9, -1) as (0,-1)  */
							  };
		this.north0_1 = new HashSet<Integer>(Arrays.asList(north0_1));

		
	}
}
