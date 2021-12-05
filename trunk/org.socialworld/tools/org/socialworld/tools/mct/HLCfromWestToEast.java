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

class HLCfromWestToEast extends SubHLC {

	private static int[] startAt0 = 
		{2000, 2011, 2012, 2013, 2031, 2032, 2033,
		2101,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt9 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033,
		2101,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt18 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt27 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt36 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt45 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt54 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt63 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2041, 2042, 2043,
		2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] startAt72 = 
		{2000, 2021, 2022, 2023, 2041, 2042, 2043,
		2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207};

	private static int[] endAt8 = 
		{2000, 2011, 2012, 2013, 2031, 2032, 2033,
		2101,
		2201, 2202, 2203, 2204, 2205, 2206, 2207};
	private static int[] endAt17 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033,
		2101,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt26 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt35 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt44 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt53 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2101, 2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt62 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2031, 2032, 2033, 2041, 2042, 2043,
		2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt71 = 
		{2000, 2011, 2012, 2013, 2021, 2022, 2023, 2041, 2042, 2043,
		2111,
		2201, 2202, 2203, 2204, 2205, 2206, 2207,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};
	private static int[] endAt80 = 
		{2000, 2021, 2022, 2023, 2041, 2042, 2043,
		2111,
		2301, 2302, 2303, 2304, 2305, 2306, 2307};

	private static int[] rasterIndicesHLC_2000 = {0,1,2,3,4,5,6,7,8};
	private static Integer[] cornerMaximaNrsHLC_2000 =
		{11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2000At00;
	private List<Integer> rasterIndices_2000At09;
	private List<Integer> rasterIndices_2000At18;
	private List<Integer> rasterIndices_2000At27;
	private List<Integer> rasterIndices_2000At36;
	private List<Integer> rasterIndices_2000At45;
	private List<Integer> rasterIndices_2000At54;
	private List<Integer> rasterIndices_2000At63;
	private List<Integer> rasterIndices_2000At72;
	private List<Integer> cornerMaximaNrs_2000;

	private static int[] rasterIndicesHLC_2011 = {0,1,2,3,5,6,7,8,12,13,14};
	private static Integer[] cornerMaximaNrsHLC_2011 =
		{11994, 11994, 11994, 11194, 11914, 11994, 11994, 11994, 91994, 11994, 19994};
	private List<Integer> rasterIndices_2011At00;
	private List<Integer> rasterIndices_2011At09;
	private List<Integer> rasterIndices_2011At18;
	private List<Integer> rasterIndices_2011At27;
	private List<Integer> rasterIndices_2011At36;
	private List<Integer> rasterIndices_2011At45;
	private List<Integer> rasterIndices_2011At54;
	private List<Integer> rasterIndices_2011At63;
	private List<Integer> cornerMaximaNrs_2011;

	private static int[] rasterIndicesHLC_2012 = {0,1,2,6,7,8,11,12,13,14,15};
	private static Integer[] cornerMaximaNrsHLC_2012 =
		{11994, 11994, 11194, 11914, 11994, 11994, 91994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2012At00;
	private List<Integer> rasterIndices_2012At09;
	private List<Integer> rasterIndices_2012At18;
	private List<Integer> rasterIndices_2012At27;
	private List<Integer> rasterIndices_2012At36;
	private List<Integer> rasterIndices_2012At45;
	private List<Integer> rasterIndices_2012At54;
	private List<Integer> rasterIndices_2012At63;
	private List<Integer> cornerMaximaNrs_2012;

	private static int[] rasterIndicesHLC_2013 = {0,1,7,8,10,11,12,13,14,15,16};
	private static Integer[] cornerMaximaNrsHLC_2013 =
		{11994, 11194, 11914, 11994, 91994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2013At00;
	private List<Integer> rasterIndices_2013At09;
	private List<Integer> rasterIndices_2013At18;
	private List<Integer> rasterIndices_2013At27;
	private List<Integer> rasterIndices_2013At36;
	private List<Integer> rasterIndices_2013At45;
	private List<Integer> rasterIndices_2013At54;
	private List<Integer> rasterIndices_2013At63;
	private List<Integer> cornerMaximaNrs_2013;

	private static int[] rasterIndicesHLC_2021 = {1,2,3,4,5,6,7,9,10,16,17};
	private static Integer[] cornerMaximaNrsHLC_2021 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11194, 11994, 19994, 91994, 11994};
	private List<Integer> rasterIndices_2021At09;
	private List<Integer> rasterIndices_2021At18;
	private List<Integer> rasterIndices_2021At27;
	private List<Integer> rasterIndices_2021At36;
	private List<Integer> rasterIndices_2021At45;
	private List<Integer> rasterIndices_2021At54;
	private List<Integer> rasterIndices_2021At63;
	private List<Integer> rasterIndices_2021At72;
	private List<Integer> cornerMaximaNrs_2021;

	private static int[] rasterIndicesHLC_2022 = {2,3,4,5,6,9,10,11,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2022 =
		{11914, 11994, 11994, 11994, 11194, 11994, 11994, 19994, 91994, 11994, 11994};
	private List<Integer> rasterIndices_2022At09;
	private List<Integer> rasterIndices_2022At18;
	private List<Integer> rasterIndices_2022At27;
	private List<Integer> rasterIndices_2022At36;
	private List<Integer> rasterIndices_2022At45;
	private List<Integer> rasterIndices_2022At54;
	private List<Integer> rasterIndices_2022At63;
	private List<Integer> rasterIndices_2022At72;
	private List<Integer> cornerMaximaNrs_2022;

	private static int[] rasterIndicesHLC_2023 = {3,4,5,9,10,11,12,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2023 =
		{11914, 11994, 11194, 11994, 11994, 11994, 19994, 91994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2023At09;
	private List<Integer> rasterIndices_2023At18;
	private List<Integer> rasterIndices_2023At27;
	private List<Integer> rasterIndices_2023At36;
	private List<Integer> rasterIndices_2023At45;
	private List<Integer> rasterIndices_2023At54;
	private List<Integer> rasterIndices_2023At63;
	private List<Integer> rasterIndices_2023At72;
	private List<Integer> cornerMaximaNrs_2023;

	private static int[] rasterIndicesHLC_2031 = {0,1,2,3,5,6,7,8,12,14,21,22,23};
	private static Integer[] cornerMaximaNrsHLC_2031 =
		{11994, 11994, 11994, 11194, 11914, 11994, 11994, 11994, 91194, 19914, 91994, 11994, 19994};
	private List<Integer> rasterIndices_2031At00;
	private List<Integer> rasterIndices_2031At09;
	private List<Integer> rasterIndices_2031At18;
	private List<Integer> rasterIndices_2031At27;
	private List<Integer> rasterIndices_2031At36;
	private List<Integer> rasterIndices_2031At45;
	private List<Integer> rasterIndices_2031At54;
	private List<Integer> cornerMaximaNrs_2031;

	private static int[] rasterIndicesHLC_2032 = {0,1,2,6,7,8,11,15,20,21,22,23,24};
	private static Integer[] cornerMaximaNrsHLC_2032 =
		{11994, 11994, 11194, 11914, 11994, 11994, 91194, 19914, 91994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2032At00;
	private List<Integer> rasterIndices_2032At09;
	private List<Integer> rasterIndices_2032At18;
	private List<Integer> rasterIndices_2032At27;
	private List<Integer> rasterIndices_2032At36;
	private List<Integer> rasterIndices_2032At45;
	private List<Integer> rasterIndices_2032At54;
	private List<Integer> cornerMaximaNrs_2032;

	private static int[] rasterIndicesHLC_2033 = {0,1,7,8,10,16,19,20,21,22,23,24,25};
	private static Integer[] cornerMaximaNrsHLC_2033 =
		{11994, 11194, 11914, 11994, 91194, 19914, 91994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2033At00;
	private List<Integer> rasterIndices_2033At09;
	private List<Integer> rasterIndices_2033At18;
	private List<Integer> rasterIndices_2033At27;
	private List<Integer> rasterIndices_2033At36;
	private List<Integer> rasterIndices_2033At45;
	private List<Integer> rasterIndices_2033At54;
	private List<Integer> cornerMaximaNrs_2033;

	private static int[] rasterIndicesHLC_2041 = {1,2,3,4,5,6,7,10,16,18,19,25,26};
	private static Integer[] cornerMaximaNrsHLC_2041 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11194, 91194, 19914, 11994, 19994, 91994, 11994};
	private List<Integer> rasterIndices_2041At18;
	private List<Integer> rasterIndices_2041At27;
	private List<Integer> rasterIndices_2041At36;
	private List<Integer> rasterIndices_2041At45;
	private List<Integer> rasterIndices_2041At54;
	private List<Integer> rasterIndices_2041At63;
	private List<Integer> rasterIndices_2041At72;
	private List<Integer> cornerMaximaNrs_2041;

	private static int[] rasterIndicesHLC_2042 = {2,3,4,5,6,11,15,18,19,20,24,25,26};
	private static Integer[] cornerMaximaNrsHLC_2042 =
		{11914, 11994, 11994, 11994, 11194, 91194, 19914, 11994, 11994, 19994, 91994, 11994, 11994};
	private List<Integer> rasterIndices_2042At18;
	private List<Integer> rasterIndices_2042At27;
	private List<Integer> rasterIndices_2042At36;
	private List<Integer> rasterIndices_2042At45;
	private List<Integer> rasterIndices_2042At54;
	private List<Integer> rasterIndices_2042At63;
	private List<Integer> rasterIndices_2042At72;
	private List<Integer> cornerMaximaNrs_2042;

	private static int[] rasterIndicesHLC_2043 = {3,4,5,12,14,18,19,20,21,23,24,25,26};
	private static Integer[] cornerMaximaNrsHLC_2043 =
		{11914, 11994, 11194, 91194, 19914, 11994, 11994, 11994, 19994, 91994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2043At18;
	private List<Integer> rasterIndices_2043At27;
	private List<Integer> rasterIndices_2043At36;
	private List<Integer> rasterIndices_2043At45;
	private List<Integer> rasterIndices_2043At54;
	private List<Integer> rasterIndices_2043At63;
	private List<Integer> rasterIndices_2043At72;
	private List<Integer> cornerMaximaNrs_2043;

	private static int[] rasterIndicesHLC_2101 = {0,1,7,8,10,11,15,16,20,21,23,24,30,31,32};
	private static Integer[] cornerMaximaNrsHLC_2101 =
		{11994, 11194, 11914, 11994, 91994, 11194, 11914, 19994, 91994, 11194, 11914, 19994, 91994, 11994, 19994};
	private List<Integer> rasterIndices_2101At00;
	private List<Integer> rasterIndices_2101At09;
	private List<Integer> rasterIndices_2101At18;
	private List<Integer> rasterIndices_2101At27;
	private List<Integer> rasterIndices_2101At36;
	private List<Integer> rasterIndices_2101At45;
	private List<Integer> cornerMaximaNrs_2101;

	private static int[] rasterIndicesHLC_2111 = {3,4,5,11,12,14,15,19,20,24,25,27,28,34,35};
	private static Integer[] cornerMaximaNrsHLC_2111 =
		{11914, 11994, 11194, 11914, 19994, 91994, 11194, 11914, 19994, 91994, 11194, 11994, 19994, 91994, 11994};
	private List<Integer> rasterIndices_2111At27;
	private List<Integer> rasterIndices_2111At36;
	private List<Integer> rasterIndices_2111At45;
	private List<Integer> rasterIndices_2111At54;
	private List<Integer> rasterIndices_2111At63;
	private List<Integer> rasterIndices_2111At72;
	private List<Integer> cornerMaximaNrs_2111;

	private static int[] rasterIndicesHLC_2201 = {1,2,3,4,5,6,7,8,9,10};
	private static Integer[] cornerMaximaNrsHLC_2201 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2201At09;
	private List<Integer> rasterIndices_2201At18;
	private List<Integer> rasterIndices_2201At27;
	private List<Integer> rasterIndices_2201At36;
	private List<Integer> rasterIndices_2201At45;
	private List<Integer> rasterIndices_2201At54;
	private List<Integer> rasterIndices_2201At63;
	private List<Integer> rasterIndices_2201At72;
	private List<Integer> cornerMaximaNrs_2201;

	private static int[] rasterIndicesHLC_2202 = {2,3,4,5,6,7,8,9,10,11};
	private static Integer[] cornerMaximaNrsHLC_2202 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2202At09;
	private List<Integer> rasterIndices_2202At18;
	private List<Integer> rasterIndices_2202At27;
	private List<Integer> rasterIndices_2202At36;
	private List<Integer> rasterIndices_2202At45;
	private List<Integer> rasterIndices_2202At54;
	private List<Integer> rasterIndices_2202At63;
	private List<Integer> rasterIndices_2202At72;
	private List<Integer> cornerMaximaNrs_2202;

	private static int[] rasterIndicesHLC_2203 = {3,4,5,6,7,8,9,10,11,12};
	private static Integer[] cornerMaximaNrsHLC_2203 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2203At09;
	private List<Integer> rasterIndices_2203At18;
	private List<Integer> rasterIndices_2203At27;
	private List<Integer> rasterIndices_2203At36;
	private List<Integer> rasterIndices_2203At45;
	private List<Integer> rasterIndices_2203At54;
	private List<Integer> rasterIndices_2203At63;
	private List<Integer> rasterIndices_2203At72;
	private List<Integer> cornerMaximaNrs_2203;

	private static int[] rasterIndicesHLC_2204 = {4,5,6,7,8,9,10,11,12,13};
	private static Integer[] cornerMaximaNrsHLC_2204 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2204At09;
	private List<Integer> rasterIndices_2204At18;
	private List<Integer> rasterIndices_2204At27;
	private List<Integer> rasterIndices_2204At36;
	private List<Integer> rasterIndices_2204At45;
	private List<Integer> rasterIndices_2204At54;
	private List<Integer> rasterIndices_2204At63;
	private List<Integer> rasterIndices_2204At72;
	private List<Integer> cornerMaximaNrs_2204;

	private static int[] rasterIndicesHLC_2205 = {5,6,7,8,9,10,11,12,13,14};
	private static Integer[] cornerMaximaNrsHLC_2205 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2205At09;
	private List<Integer> rasterIndices_2205At18;
	private List<Integer> rasterIndices_2205At27;
	private List<Integer> rasterIndices_2205At36;
	private List<Integer> rasterIndices_2205At45;
	private List<Integer> rasterIndices_2205At54;
	private List<Integer> rasterIndices_2205At63;
	private List<Integer> rasterIndices_2205At72;
	private List<Integer> cornerMaximaNrs_2205;

	private static int[] rasterIndicesHLC_2206 = {6,7,8,9,10,11,12,13,14,15};
	private static Integer[] cornerMaximaNrsHLC_2206 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2206At09;
	private List<Integer> rasterIndices_2206At18;
	private List<Integer> rasterIndices_2206At27;
	private List<Integer> rasterIndices_2206At36;
	private List<Integer> rasterIndices_2206At45;
	private List<Integer> rasterIndices_2206At54;
	private List<Integer> rasterIndices_2206At63;
	private List<Integer> rasterIndices_2206At72;
	private List<Integer> cornerMaximaNrs_2206;

	private static int[] rasterIndicesHLC_2207 = {7,8,9,10,11,12,13,14,15,16};
	private static Integer[] cornerMaximaNrsHLC_2207 =
		{11914, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 11994, 19994};
	private List<Integer> rasterIndices_2207At09;
	private List<Integer> rasterIndices_2207At18;
	private List<Integer> rasterIndices_2207At27;
	private List<Integer> rasterIndices_2207At36;
	private List<Integer> rasterIndices_2207At45;
	private List<Integer> rasterIndices_2207At54;
	private List<Integer> rasterIndices_2207At63;
	private List<Integer> rasterIndices_2207At72;
	private List<Integer> cornerMaximaNrs_2207;

	private static int[] rasterIndicesHLC_2301 = {0,1,2,3,4,5,6,7,16,17};
	private static Integer[] cornerMaximaNrsHLC_2301 =
		{11994, 11994, 11994, 11994, 11994, 11994, 11994, 11194, 91994, 11994};
	private List<Integer> rasterIndices_2301At00;
	private List<Integer> rasterIndices_2301At09;
	private List<Integer> rasterIndices_2301At18;
	private List<Integer> rasterIndices_2301At27;
	private List<Integer> rasterIndices_2301At36;
	private List<Integer> rasterIndices_2301At45;
	private List<Integer> rasterIndices_2301At54;
	private List<Integer> rasterIndices_2301At63;
	private List<Integer> cornerMaximaNrs_2301;

	private static int[] rasterIndicesHLC_2302 = {0,1,2,3,4,5,6,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2302 =
		{11994, 11994, 11994, 11994, 11994, 11994, 11194, 91994, 11994, 11994};
	private List<Integer> rasterIndices_2302At00;
	private List<Integer> rasterIndices_2302At09;
	private List<Integer> rasterIndices_2302At18;
	private List<Integer> rasterIndices_2302At27;
	private List<Integer> rasterIndices_2302At36;
	private List<Integer> rasterIndices_2302At45;
	private List<Integer> rasterIndices_2302At54;
	private List<Integer> rasterIndices_2302At63;
	private List<Integer> cornerMaximaNrs_2302;

	private static int[] rasterIndicesHLC_2303 = {0,1,2,3,4,5,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2303 =
		{11994, 11994, 11994, 11994, 11994, 11194, 91994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2303At00;
	private List<Integer> rasterIndices_2303At09;
	private List<Integer> rasterIndices_2303At18;
	private List<Integer> rasterIndices_2303At27;
	private List<Integer> rasterIndices_2303At36;
	private List<Integer> rasterIndices_2303At45;
	private List<Integer> rasterIndices_2303At54;
	private List<Integer> rasterIndices_2303At63;
	private List<Integer> cornerMaximaNrs_2303;

	private static int[] rasterIndicesHLC_2304 = {0,1,2,3,4,13,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2304 =
		{11994, 11994, 11994, 11994, 11194, 91994, 11994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2304At00;
	private List<Integer> rasterIndices_2304At09;
	private List<Integer> rasterIndices_2304At18;
	private List<Integer> rasterIndices_2304At27;
	private List<Integer> rasterIndices_2304At36;
	private List<Integer> rasterIndices_2304At45;
	private List<Integer> rasterIndices_2304At54;
	private List<Integer> rasterIndices_2304At63;
	private List<Integer> cornerMaximaNrs_2304;

	private static int[] rasterIndicesHLC_2305 = {0,1,2,3,12,13,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2305 =
		{11994, 11994, 11994, 11194, 91994, 11994, 11994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2305At00;
	private List<Integer> rasterIndices_2305At09;
	private List<Integer> rasterIndices_2305At18;
	private List<Integer> rasterIndices_2305At27;
	private List<Integer> rasterIndices_2305At36;
	private List<Integer> rasterIndices_2305At45;
	private List<Integer> rasterIndices_2305At54;
	private List<Integer> rasterIndices_2305At63;
	private List<Integer> cornerMaximaNrs_2305;

	private static int[] rasterIndicesHLC_2306 = {0,1,2,11,12,13,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2306 =
		{11994, 11994, 11194, 91994, 11994, 11994, 11994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2306At00;
	private List<Integer> rasterIndices_2306At09;
	private List<Integer> rasterIndices_2306At18;
	private List<Integer> rasterIndices_2306At27;
	private List<Integer> rasterIndices_2306At36;
	private List<Integer> rasterIndices_2306At45;
	private List<Integer> rasterIndices_2306At54;
	private List<Integer> rasterIndices_2306At63;
	private List<Integer> cornerMaximaNrs_2306;

	private static int[] rasterIndicesHLC_2307 = {0,1,10,11,12,13,14,15,16,17};
	private static Integer[] cornerMaximaNrsHLC_2307 =
		{11994, 11194, 91994, 11994, 11994, 11994, 11994, 11994, 11994, 11994};
	private List<Integer> rasterIndices_2307At00;
	private List<Integer> rasterIndices_2307At09;
	private List<Integer> rasterIndices_2307At18;
	private List<Integer> rasterIndices_2307At27;
	private List<Integer> rasterIndices_2307At36;
	private List<Integer> rasterIndices_2307At45;
	private List<Integer> rasterIndices_2307At54;
	private List<Integer> rasterIndices_2307At63;
	private List<Integer> cornerMaximaNrs_2307;

	private HLCfromWestToEast() {
	rasterIndices_2000At00 = getRasterIndicesList(0, rasterIndicesHLC_2000);
	rasterIndices_2000At09 = getRasterIndicesList(9, rasterIndicesHLC_2000);
	rasterIndices_2000At18 = getRasterIndicesList(18, rasterIndicesHLC_2000);
	rasterIndices_2000At27 = getRasterIndicesList(27, rasterIndicesHLC_2000);
	rasterIndices_2000At36 = getRasterIndicesList(36, rasterIndicesHLC_2000);
	rasterIndices_2000At45 = getRasterIndicesList(45, rasterIndicesHLC_2000);
	rasterIndices_2000At54 = getRasterIndicesList(54, rasterIndicesHLC_2000);
	rasterIndices_2000At63 = getRasterIndicesList(63, rasterIndicesHLC_2000);
	rasterIndices_2000At72 = getRasterIndicesList(72, rasterIndicesHLC_2000);
	cornerMaximaNrs_2000 = Arrays.asList(cornerMaximaNrsHLC_2000);

	rasterIndices_2011At00 = getRasterIndicesList(0, rasterIndicesHLC_2011);
	rasterIndices_2011At09 = getRasterIndicesList(9, rasterIndicesHLC_2011);
	rasterIndices_2011At18 = getRasterIndicesList(18, rasterIndicesHLC_2011);
	rasterIndices_2011At27 = getRasterIndicesList(27, rasterIndicesHLC_2011);
	rasterIndices_2011At36 = getRasterIndicesList(36, rasterIndicesHLC_2011);
	rasterIndices_2011At45 = getRasterIndicesList(45, rasterIndicesHLC_2011);
	rasterIndices_2011At54 = getRasterIndicesList(54, rasterIndicesHLC_2011);
	rasterIndices_2011At63 = getRasterIndicesList(63, rasterIndicesHLC_2011);
	cornerMaximaNrs_2011 = Arrays.asList(cornerMaximaNrsHLC_2011);

	rasterIndices_2012At00 = getRasterIndicesList(0, rasterIndicesHLC_2012);
	rasterIndices_2012At09 = getRasterIndicesList(9, rasterIndicesHLC_2012);
	rasterIndices_2012At18 = getRasterIndicesList(18, rasterIndicesHLC_2012);
	rasterIndices_2012At27 = getRasterIndicesList(27, rasterIndicesHLC_2012);
	rasterIndices_2012At36 = getRasterIndicesList(36, rasterIndicesHLC_2012);
	rasterIndices_2012At45 = getRasterIndicesList(45, rasterIndicesHLC_2012);
	rasterIndices_2012At54 = getRasterIndicesList(54, rasterIndicesHLC_2012);
	rasterIndices_2012At63 = getRasterIndicesList(63, rasterIndicesHLC_2012);
	cornerMaximaNrs_2012 = Arrays.asList(cornerMaximaNrsHLC_2012);

	rasterIndices_2013At00 = getRasterIndicesList(0, rasterIndicesHLC_2013);
	rasterIndices_2013At09 = getRasterIndicesList(9, rasterIndicesHLC_2013);
	rasterIndices_2013At18 = getRasterIndicesList(18, rasterIndicesHLC_2013);
	rasterIndices_2013At27 = getRasterIndicesList(27, rasterIndicesHLC_2013);
	rasterIndices_2013At36 = getRasterIndicesList(36, rasterIndicesHLC_2013);
	rasterIndices_2013At45 = getRasterIndicesList(45, rasterIndicesHLC_2013);
	rasterIndices_2013At54 = getRasterIndicesList(54, rasterIndicesHLC_2013);
	rasterIndices_2013At63 = getRasterIndicesList(63, rasterIndicesHLC_2013);
	cornerMaximaNrs_2013 = Arrays.asList(cornerMaximaNrsHLC_2013);

	rasterIndices_2021At09 = getRasterIndicesList(1, rasterIndicesHLC_2021);
	rasterIndices_2021At18 = getRasterIndicesList(10, rasterIndicesHLC_2021);
	rasterIndices_2021At27 = getRasterIndicesList(19, rasterIndicesHLC_2021);
	rasterIndices_2021At36 = getRasterIndicesList(28, rasterIndicesHLC_2021);
	rasterIndices_2021At45 = getRasterIndicesList(37, rasterIndicesHLC_2021);
	rasterIndices_2021At54 = getRasterIndicesList(46, rasterIndicesHLC_2021);
	rasterIndices_2021At63 = getRasterIndicesList(55, rasterIndicesHLC_2021);
	rasterIndices_2021At72 = getRasterIndicesList(64, rasterIndicesHLC_2021);
	cornerMaximaNrs_2021 = Arrays.asList(cornerMaximaNrsHLC_2021);

	rasterIndices_2022At09 = getRasterIndicesList(2, rasterIndicesHLC_2022);
	rasterIndices_2022At18 = getRasterIndicesList(11, rasterIndicesHLC_2022);
	rasterIndices_2022At27 = getRasterIndicesList(20, rasterIndicesHLC_2022);
	rasterIndices_2022At36 = getRasterIndicesList(29, rasterIndicesHLC_2022);
	rasterIndices_2022At45 = getRasterIndicesList(38, rasterIndicesHLC_2022);
	rasterIndices_2022At54 = getRasterIndicesList(47, rasterIndicesHLC_2022);
	rasterIndices_2022At63 = getRasterIndicesList(56, rasterIndicesHLC_2022);
	rasterIndices_2022At72 = getRasterIndicesList(45, rasterIndicesHLC_2022);
	cornerMaximaNrs_2022 = Arrays.asList(cornerMaximaNrsHLC_2022);

	rasterIndices_2023At09 = getRasterIndicesList(3, rasterIndicesHLC_2023);
	rasterIndices_2023At18 = getRasterIndicesList(12, rasterIndicesHLC_2023);
	rasterIndices_2023At27 = getRasterIndicesList(21, rasterIndicesHLC_2023);
	rasterIndices_2023At36 = getRasterIndicesList(30, rasterIndicesHLC_2023);
	rasterIndices_2023At45 = getRasterIndicesList(39, rasterIndicesHLC_2023);
	rasterIndices_2023At54 = getRasterIndicesList(48, rasterIndicesHLC_2023);
	rasterIndices_2023At63 = getRasterIndicesList(57, rasterIndicesHLC_2023);
	rasterIndices_2023At72 = getRasterIndicesList(66, rasterIndicesHLC_2023);
	cornerMaximaNrs_2023 = Arrays.asList(cornerMaximaNrsHLC_2023);

	rasterIndices_2031At00 = getRasterIndicesList(0, rasterIndicesHLC_2031);
	rasterIndices_2031At09 = getRasterIndicesList(9, rasterIndicesHLC_2031);
	rasterIndices_2031At18 = getRasterIndicesList(18, rasterIndicesHLC_2031);
	rasterIndices_2031At27 = getRasterIndicesList(27, rasterIndicesHLC_2031);
	rasterIndices_2031At36 = getRasterIndicesList(36, rasterIndicesHLC_2031);
	rasterIndices_2031At45 = getRasterIndicesList(45, rasterIndicesHLC_2031);
	rasterIndices_2031At54 = getRasterIndicesList(54, rasterIndicesHLC_2031);
	cornerMaximaNrs_2031 = Arrays.asList(cornerMaximaNrsHLC_2031);

	rasterIndices_2032At00 = getRasterIndicesList(0, rasterIndicesHLC_2032);
	rasterIndices_2032At09 = getRasterIndicesList(9, rasterIndicesHLC_2032);
	rasterIndices_2032At18 = getRasterIndicesList(18, rasterIndicesHLC_2032);
	rasterIndices_2032At27 = getRasterIndicesList(27, rasterIndicesHLC_2032);
	rasterIndices_2032At36 = getRasterIndicesList(36, rasterIndicesHLC_2032);
	rasterIndices_2032At45 = getRasterIndicesList(45, rasterIndicesHLC_2032);
	rasterIndices_2032At54 = getRasterIndicesList(54, rasterIndicesHLC_2032);
	cornerMaximaNrs_2032 = Arrays.asList(cornerMaximaNrsHLC_2032);

	rasterIndices_2033At00 = getRasterIndicesList(0, rasterIndicesHLC_2033);
	rasterIndices_2033At09 = getRasterIndicesList(9, rasterIndicesHLC_2033);
	rasterIndices_2033At18 = getRasterIndicesList(18, rasterIndicesHLC_2033);
	rasterIndices_2033At27 = getRasterIndicesList(27, rasterIndicesHLC_2033);
	rasterIndices_2033At36 = getRasterIndicesList(36, rasterIndicesHLC_2033);
	rasterIndices_2033At45 = getRasterIndicesList(45, rasterIndicesHLC_2033);
	rasterIndices_2033At54 = getRasterIndicesList(54, rasterIndicesHLC_2033);
	cornerMaximaNrs_2033 = Arrays.asList(cornerMaximaNrsHLC_2033);

	rasterIndices_2041At18 = getRasterIndicesList(1, rasterIndicesHLC_2041);
	rasterIndices_2041At27 = getRasterIndicesList(10, rasterIndicesHLC_2041);
	rasterIndices_2041At36 = getRasterIndicesList(19, rasterIndicesHLC_2041);
	rasterIndices_2041At45 = getRasterIndicesList(28, rasterIndicesHLC_2041);
	rasterIndices_2041At54 = getRasterIndicesList(37, rasterIndicesHLC_2041);
	rasterIndices_2041At63 = getRasterIndicesList(46, rasterIndicesHLC_2041);
	rasterIndices_2041At72 = getRasterIndicesList(55, rasterIndicesHLC_2041);
	cornerMaximaNrs_2041 = Arrays.asList(cornerMaximaNrsHLC_2041);

	rasterIndices_2042At18 = getRasterIndicesList(2, rasterIndicesHLC_2042);
	rasterIndices_2042At27 = getRasterIndicesList(11, rasterIndicesHLC_2042);
	rasterIndices_2042At36 = getRasterIndicesList(20, rasterIndicesHLC_2042);
	rasterIndices_2042At45 = getRasterIndicesList(29, rasterIndicesHLC_2042);
	rasterIndices_2042At54 = getRasterIndicesList(38, rasterIndicesHLC_2042);
	rasterIndices_2042At63 = getRasterIndicesList(47, rasterIndicesHLC_2042);
	rasterIndices_2042At72 = getRasterIndicesList(56, rasterIndicesHLC_2042);
	cornerMaximaNrs_2042 = Arrays.asList(cornerMaximaNrsHLC_2042);

	rasterIndices_2043At18 = getRasterIndicesList(3, rasterIndicesHLC_2043);
	rasterIndices_2043At27 = getRasterIndicesList(12, rasterIndicesHLC_2043);
	rasterIndices_2043At36 = getRasterIndicesList(21, rasterIndicesHLC_2043);
	rasterIndices_2043At45 = getRasterIndicesList(30, rasterIndicesHLC_2043);
	rasterIndices_2043At54 = getRasterIndicesList(39, rasterIndicesHLC_2043);
	rasterIndices_2043At63 = getRasterIndicesList(48, rasterIndicesHLC_2043);
	rasterIndices_2043At72 = getRasterIndicesList(57, rasterIndicesHLC_2043);
	cornerMaximaNrs_2043 = Arrays.asList(cornerMaximaNrsHLC_2043);

	rasterIndices_2101At00 = getRasterIndicesList(0, rasterIndicesHLC_2101);
	rasterIndices_2101At09 = getRasterIndicesList(9, rasterIndicesHLC_2101);
	rasterIndices_2101At18 = getRasterIndicesList(18, rasterIndicesHLC_2101);
	rasterIndices_2101At27 = getRasterIndicesList(27, rasterIndicesHLC_2101);
	rasterIndices_2101At36 = getRasterIndicesList(36, rasterIndicesHLC_2101);
	rasterIndices_2101At45 = getRasterIndicesList(45, rasterIndicesHLC_2101);
	cornerMaximaNrs_2101 = Arrays.asList(cornerMaximaNrsHLC_2101);

	rasterIndices_2111At27 = getRasterIndicesList(3, rasterIndicesHLC_2111);
	rasterIndices_2111At36 = getRasterIndicesList(12, rasterIndicesHLC_2111);
	rasterIndices_2111At45 = getRasterIndicesList(21, rasterIndicesHLC_2111);
	rasterIndices_2111At54 = getRasterIndicesList(30, rasterIndicesHLC_2111);
	rasterIndices_2111At63 = getRasterIndicesList(39, rasterIndicesHLC_2111);
	rasterIndices_2111At72 = getRasterIndicesList(48, rasterIndicesHLC_2111);
	cornerMaximaNrs_2111 = Arrays.asList(cornerMaximaNrsHLC_2111);

	rasterIndices_2201At09 = getRasterIndicesList(1, rasterIndicesHLC_2201);
	rasterIndices_2201At18 = getRasterIndicesList(10, rasterIndicesHLC_2201);
	rasterIndices_2201At27 = getRasterIndicesList(19, rasterIndicesHLC_2201);
	rasterIndices_2201At36 = getRasterIndicesList(28, rasterIndicesHLC_2201);
	rasterIndices_2201At45 = getRasterIndicesList(37, rasterIndicesHLC_2201);
	rasterIndices_2201At54 = getRasterIndicesList(46, rasterIndicesHLC_2201);
	rasterIndices_2201At63 = getRasterIndicesList(55, rasterIndicesHLC_2201);
	rasterIndices_2201At72 = getRasterIndicesList(64, rasterIndicesHLC_2201);
	cornerMaximaNrs_2201 = Arrays.asList(cornerMaximaNrsHLC_2201);

	rasterIndices_2202At09 = getRasterIndicesList(2, rasterIndicesHLC_2202);
	rasterIndices_2202At18 = getRasterIndicesList(11, rasterIndicesHLC_2202);
	rasterIndices_2202At27 = getRasterIndicesList(20, rasterIndicesHLC_2202);
	rasterIndices_2202At36 = getRasterIndicesList(29, rasterIndicesHLC_2202);
	rasterIndices_2202At45 = getRasterIndicesList(38, rasterIndicesHLC_2202);
	rasterIndices_2202At54 = getRasterIndicesList(47, rasterIndicesHLC_2202);
	rasterIndices_2202At63 = getRasterIndicesList(56, rasterIndicesHLC_2202);
	rasterIndices_2202At72 = getRasterIndicesList(65, rasterIndicesHLC_2202);
	cornerMaximaNrs_2202 = Arrays.asList(cornerMaximaNrsHLC_2202);

	rasterIndices_2203At09 = getRasterIndicesList(3, rasterIndicesHLC_2203);
	rasterIndices_2203At18 = getRasterIndicesList(12, rasterIndicesHLC_2203);
	rasterIndices_2203At27 = getRasterIndicesList(21, rasterIndicesHLC_2203);
	rasterIndices_2203At36 = getRasterIndicesList(30, rasterIndicesHLC_2203);
	rasterIndices_2203At45 = getRasterIndicesList(39, rasterIndicesHLC_2203);
	rasterIndices_2203At54 = getRasterIndicesList(48, rasterIndicesHLC_2203);
	rasterIndices_2203At63 = getRasterIndicesList(57, rasterIndicesHLC_2203);
	rasterIndices_2203At72 = getRasterIndicesList(66, rasterIndicesHLC_2203);
	cornerMaximaNrs_2203 = Arrays.asList(cornerMaximaNrsHLC_2203);

	rasterIndices_2204At09 = getRasterIndicesList(4, rasterIndicesHLC_2204);
	rasterIndices_2204At18 = getRasterIndicesList(13, rasterIndicesHLC_2204);
	rasterIndices_2204At27 = getRasterIndicesList(22, rasterIndicesHLC_2204);
	rasterIndices_2204At36 = getRasterIndicesList(31, rasterIndicesHLC_2204);
	rasterIndices_2204At45 = getRasterIndicesList(40, rasterIndicesHLC_2204);
	rasterIndices_2204At54 = getRasterIndicesList(49, rasterIndicesHLC_2204);
	rasterIndices_2204At63 = getRasterIndicesList(58, rasterIndicesHLC_2204);
	rasterIndices_2204At72 = getRasterIndicesList(67, rasterIndicesHLC_2204);
	cornerMaximaNrs_2204 = Arrays.asList(cornerMaximaNrsHLC_2204);

	rasterIndices_2205At09 = getRasterIndicesList(5, rasterIndicesHLC_2205);
	rasterIndices_2205At18 = getRasterIndicesList(14, rasterIndicesHLC_2205);
	rasterIndices_2205At27 = getRasterIndicesList(23, rasterIndicesHLC_2205);
	rasterIndices_2205At36 = getRasterIndicesList(32, rasterIndicesHLC_2205);
	rasterIndices_2205At45 = getRasterIndicesList(41, rasterIndicesHLC_2205);
	rasterIndices_2205At54 = getRasterIndicesList(50, rasterIndicesHLC_2205);
	rasterIndices_2205At63 = getRasterIndicesList(59, rasterIndicesHLC_2205);
	rasterIndices_2205At72 = getRasterIndicesList(68, rasterIndicesHLC_2205);
	cornerMaximaNrs_2205 = Arrays.asList(cornerMaximaNrsHLC_2205);

	rasterIndices_2206At09 = getRasterIndicesList(6, rasterIndicesHLC_2206);
	rasterIndices_2206At18 = getRasterIndicesList(15, rasterIndicesHLC_2206);
	rasterIndices_2206At27 = getRasterIndicesList(24, rasterIndicesHLC_2206);
	rasterIndices_2206At36 = getRasterIndicesList(33, rasterIndicesHLC_2206);
	rasterIndices_2206At45 = getRasterIndicesList(42, rasterIndicesHLC_2206);
	rasterIndices_2206At54 = getRasterIndicesList(51, rasterIndicesHLC_2206);
	rasterIndices_2206At63 = getRasterIndicesList(60, rasterIndicesHLC_2206);
	rasterIndices_2206At72 = getRasterIndicesList(69, rasterIndicesHLC_2206);
	cornerMaximaNrs_2206 = Arrays.asList(cornerMaximaNrsHLC_2206);

	rasterIndices_2207At09 = getRasterIndicesList(7, rasterIndicesHLC_2207);
	rasterIndices_2207At18 = getRasterIndicesList(16, rasterIndicesHLC_2207);
	rasterIndices_2207At27 = getRasterIndicesList(25, rasterIndicesHLC_2207);
	rasterIndices_2207At36 = getRasterIndicesList(34, rasterIndicesHLC_2207);
	rasterIndices_2207At45 = getRasterIndicesList(43, rasterIndicesHLC_2207);
	rasterIndices_2207At54 = getRasterIndicesList(52, rasterIndicesHLC_2207);
	rasterIndices_2207At63 = getRasterIndicesList(61, rasterIndicesHLC_2207);
	rasterIndices_2207At72 = getRasterIndicesList(70, rasterIndicesHLC_2207);
	cornerMaximaNrs_2207 = Arrays.asList(cornerMaximaNrsHLC_2207);

	rasterIndices_2301At00 = getRasterIndicesList(0, rasterIndicesHLC_2301);
	rasterIndices_2301At09 = getRasterIndicesList(9, rasterIndicesHLC_2301);
	rasterIndices_2301At18 = getRasterIndicesList(18, rasterIndicesHLC_2301);
	rasterIndices_2301At27 = getRasterIndicesList(27, rasterIndicesHLC_2301);
	rasterIndices_2301At36 = getRasterIndicesList(36, rasterIndicesHLC_2301);
	rasterIndices_2301At45 = getRasterIndicesList(45, rasterIndicesHLC_2301);
	rasterIndices_2301At54 = getRasterIndicesList(54, rasterIndicesHLC_2301);
	rasterIndices_2301At63 = getRasterIndicesList(63, rasterIndicesHLC_2301);
	cornerMaximaNrs_2301 = Arrays.asList(cornerMaximaNrsHLC_2301);

	rasterIndices_2302At00 = getRasterIndicesList(0, rasterIndicesHLC_2302);
	rasterIndices_2302At09 = getRasterIndicesList(9, rasterIndicesHLC_2302);
	rasterIndices_2302At18 = getRasterIndicesList(18, rasterIndicesHLC_2302);
	rasterIndices_2302At27 = getRasterIndicesList(27, rasterIndicesHLC_2302);
	rasterIndices_2302At36 = getRasterIndicesList(36, rasterIndicesHLC_2302);
	rasterIndices_2302At45 = getRasterIndicesList(45, rasterIndicesHLC_2302);
	rasterIndices_2302At54 = getRasterIndicesList(54, rasterIndicesHLC_2302);
	rasterIndices_2302At63 = getRasterIndicesList(63, rasterIndicesHLC_2302);
	cornerMaximaNrs_2302 = Arrays.asList(cornerMaximaNrsHLC_2302);

	rasterIndices_2303At00 = getRasterIndicesList(0, rasterIndicesHLC_2303);
	rasterIndices_2303At09 = getRasterIndicesList(9, rasterIndicesHLC_2303);
	rasterIndices_2303At18 = getRasterIndicesList(18, rasterIndicesHLC_2303);
	rasterIndices_2303At27 = getRasterIndicesList(27, rasterIndicesHLC_2303);
	rasterIndices_2303At36 = getRasterIndicesList(36, rasterIndicesHLC_2303);
	rasterIndices_2303At45 = getRasterIndicesList(45, rasterIndicesHLC_2303);
	rasterIndices_2303At54 = getRasterIndicesList(54, rasterIndicesHLC_2303);
	rasterIndices_2303At63 = getRasterIndicesList(63, rasterIndicesHLC_2303);
	cornerMaximaNrs_2303 = Arrays.asList(cornerMaximaNrsHLC_2303);

	rasterIndices_2304At00 = getRasterIndicesList(0, rasterIndicesHLC_2304);
	rasterIndices_2304At09 = getRasterIndicesList(9, rasterIndicesHLC_2304);
	rasterIndices_2304At18 = getRasterIndicesList(18, rasterIndicesHLC_2304);
	rasterIndices_2304At27 = getRasterIndicesList(27, rasterIndicesHLC_2304);
	rasterIndices_2304At36 = getRasterIndicesList(36, rasterIndicesHLC_2304);
	rasterIndices_2304At45 = getRasterIndicesList(45, rasterIndicesHLC_2304);
	rasterIndices_2304At54 = getRasterIndicesList(54, rasterIndicesHLC_2304);
	rasterIndices_2304At63 = getRasterIndicesList(63, rasterIndicesHLC_2304);
	cornerMaximaNrs_2304 = Arrays.asList(cornerMaximaNrsHLC_2304);

	rasterIndices_2305At00 = getRasterIndicesList(0, rasterIndicesHLC_2305);
	rasterIndices_2305At09 = getRasterIndicesList(9, rasterIndicesHLC_2305);
	rasterIndices_2305At18 = getRasterIndicesList(18, rasterIndicesHLC_2305);
	rasterIndices_2305At27 = getRasterIndicesList(27, rasterIndicesHLC_2305);
	rasterIndices_2305At36 = getRasterIndicesList(36, rasterIndicesHLC_2305);
	rasterIndices_2305At45 = getRasterIndicesList(45, rasterIndicesHLC_2305);
	rasterIndices_2305At54 = getRasterIndicesList(54, rasterIndicesHLC_2305);
	rasterIndices_2305At63 = getRasterIndicesList(63, rasterIndicesHLC_2305);
	cornerMaximaNrs_2305 = Arrays.asList(cornerMaximaNrsHLC_2305);

	rasterIndices_2306At00 = getRasterIndicesList(0, rasterIndicesHLC_2306);
	rasterIndices_2306At09 = getRasterIndicesList(9, rasterIndicesHLC_2306);
	rasterIndices_2306At18 = getRasterIndicesList(18, rasterIndicesHLC_2306);
	rasterIndices_2306At27 = getRasterIndicesList(27, rasterIndicesHLC_2306);
	rasterIndices_2306At36 = getRasterIndicesList(36, rasterIndicesHLC_2306);
	rasterIndices_2306At45 = getRasterIndicesList(45, rasterIndicesHLC_2306);
	rasterIndices_2306At54 = getRasterIndicesList(54, rasterIndicesHLC_2306);
	rasterIndices_2306At63 = getRasterIndicesList(63, rasterIndicesHLC_2306);
	cornerMaximaNrs_2306 = Arrays.asList(cornerMaximaNrsHLC_2306);

	rasterIndices_2307At00 = getRasterIndicesList(0, rasterIndicesHLC_2307);
	rasterIndices_2307At09 = getRasterIndicesList(9, rasterIndicesHLC_2307);
	rasterIndices_2307At18 = getRasterIndicesList(18, rasterIndicesHLC_2307);
	rasterIndices_2307At27 = getRasterIndicesList(27, rasterIndicesHLC_2307);
	rasterIndices_2307At36 = getRasterIndicesList(36, rasterIndicesHLC_2307);
	rasterIndices_2307At45 = getRasterIndicesList(45, rasterIndicesHLC_2307);
	rasterIndices_2307At54 = getRasterIndicesList(54, rasterIndicesHLC_2307);
	rasterIndices_2307At63 = getRasterIndicesList(63, rasterIndicesHLC_2307);
	cornerMaximaNrs_2307 = Arrays.asList(cornerMaximaNrsHLC_2307);

}

	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromWestToEast();
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
		case 9:
			starts = startAt9; break;
		case 18:
			starts = startAt18; break;
		case 27:
			starts = startAt27; break;
		case 36:
			starts = startAt36; break;
		case 45:
			starts = startAt45; break;
		case 54:
			starts = startAt54; break;
		case 63:
			starts = startAt63; break;
		case 72:
			starts = startAt72; break;
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
		case 9:
			starts = startAt9; break;
		case 18:
			starts = startAt18; break;
		case 27:
			starts = startAt27; break;
		case 36:
			starts = startAt36; break;
		case 45:
			starts = startAt45; break;
		case 54:
			starts = startAt54; break;
		case 63:
			starts = startAt63; break;
		case 72:
			starts = startAt72; break;
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
			case 2000: result = rasterIndices_2000At00; break;
			case 2011: result = rasterIndices_2011At00; break;
			case 2012: result = rasterIndices_2012At00; break;
			case 2013: result = rasterIndices_2013At00; break;
			case 2031: result = rasterIndices_2031At00; break;
			case 2032: result = rasterIndices_2032At00; break;
			case 2033: result = rasterIndices_2033At00; break;
			case 2101: result = rasterIndices_2101At00; break;
			case 2301: result = rasterIndices_2301At00; break;
			case 2302: result = rasterIndices_2302At00; break;
			case 2303: result = rasterIndices_2303At00; break;
			case 2304: result = rasterIndices_2304At00; break;
			case 2305: result = rasterIndices_2305At00; break;
			case 2306: result = rasterIndices_2306At00; break;
			case 2307: result = rasterIndices_2307At00; break;
			}
		case 9:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At09; break;
			case 2011: result = rasterIndices_2011At09; break;
			case 2012: result = rasterIndices_2012At09; break;
			case 2013: result = rasterIndices_2013At09; break;
			case 2021: result = rasterIndices_2021At09; break;
			case 2022: result = rasterIndices_2022At09; break;
			case 2023: result = rasterIndices_2023At09; break;
			case 2031: result = rasterIndices_2031At09; break;
			case 2032: result = rasterIndices_2032At09; break;
			case 2033: result = rasterIndices_2033At09; break;
			case 2101: result = rasterIndices_2101At09; break;
			case 2201: result = rasterIndices_2201At09; break;
			case 2202: result = rasterIndices_2202At09; break;
			case 2203: result = rasterIndices_2203At09; break;
			case 2204: result = rasterIndices_2204At09; break;
			case 2205: result = rasterIndices_2205At09; break;
			case 2206: result = rasterIndices_2206At09; break;
			case 2207: result = rasterIndices_2207At09; break;
			case 2301: result = rasterIndices_2301At09; break;
			case 2302: result = rasterIndices_2302At09; break;
			case 2303: result = rasterIndices_2303At09; break;
			case 2304: result = rasterIndices_2304At09; break;
			case 2305: result = rasterIndices_2305At09; break;
			case 2306: result = rasterIndices_2306At09; break;
			case 2307: result = rasterIndices_2307At09; break;
			}
		case 18:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At18; break;
			case 2011: result = rasterIndices_2011At18; break;
			case 2012: result = rasterIndices_2012At18; break;
			case 2013: result = rasterIndices_2013At18; break;
			case 2021: result = rasterIndices_2021At18; break;
			case 2022: result = rasterIndices_2022At18; break;
			case 2023: result = rasterIndices_2023At18; break;
			case 2031: result = rasterIndices_2031At18; break;
			case 2032: result = rasterIndices_2032At18; break;
			case 2033: result = rasterIndices_2033At18; break;
			case 2041: result = rasterIndices_2041At18; break;
			case 2042: result = rasterIndices_2042At18; break;
			case 2043: result = rasterIndices_2043At18; break;
			case 2101: result = rasterIndices_2101At18; break;
			case 2201: result = rasterIndices_2201At18; break;
			case 2202: result = rasterIndices_2202At18; break;
			case 2203: result = rasterIndices_2203At18; break;
			case 2204: result = rasterIndices_2204At18; break;
			case 2205: result = rasterIndices_2205At18; break;
			case 2206: result = rasterIndices_2206At18; break;
			case 2207: result = rasterIndices_2207At18; break;
			case 2301: result = rasterIndices_2301At18; break;
			case 2302: result = rasterIndices_2302At18; break;
			case 2303: result = rasterIndices_2303At18; break;
			case 2304: result = rasterIndices_2304At18; break;
			case 2305: result = rasterIndices_2305At18; break;
			case 2306: result = rasterIndices_2306At18; break;
			case 2307: result = rasterIndices_2307At18; break;
			}
		case 27:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At27; break;
			case 2011: result = rasterIndices_2011At27; break;
			case 2012: result = rasterIndices_2012At27; break;
			case 2013: result = rasterIndices_2013At27; break;
			case 2021: result = rasterIndices_2021At27; break;
			case 2022: result = rasterIndices_2022At27; break;
			case 2023: result = rasterIndices_2023At27; break;
			case 2031: result = rasterIndices_2031At27; break;
			case 2032: result = rasterIndices_2032At27; break;
			case 2033: result = rasterIndices_2033At27; break;
			case 2041: result = rasterIndices_2041At27; break;
			case 2042: result = rasterIndices_2042At27; break;
			case 2043: result = rasterIndices_2043At27; break;
			case 2101: result = rasterIndices_2101At27; break;
			case 2111: result = rasterIndices_2111At27; break;
			case 2201: result = rasterIndices_2201At27; break;
			case 2202: result = rasterIndices_2202At27; break;
			case 2203: result = rasterIndices_2203At27; break;
			case 2204: result = rasterIndices_2204At27; break;
			case 2205: result = rasterIndices_2205At27; break;
			case 2206: result = rasterIndices_2206At27; break;
			case 2207: result = rasterIndices_2207At27; break;
			case 2301: result = rasterIndices_2301At27; break;
			case 2302: result = rasterIndices_2302At27; break;
			case 2303: result = rasterIndices_2303At27; break;
			case 2304: result = rasterIndices_2304At27; break;
			case 2305: result = rasterIndices_2305At27; break;
			case 2306: result = rasterIndices_2306At27; break;
			case 2307: result = rasterIndices_2307At27; break;
			}
		case 36:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At36; break;
			case 2011: result = rasterIndices_2011At36; break;
			case 2012: result = rasterIndices_2012At36; break;
			case 2013: result = rasterIndices_2013At36; break;
			case 2021: result = rasterIndices_2021At36; break;
			case 2022: result = rasterIndices_2022At36; break;
			case 2023: result = rasterIndices_2023At36; break;
			case 2031: result = rasterIndices_2031At36; break;
			case 2032: result = rasterIndices_2032At36; break;
			case 2033: result = rasterIndices_2033At36; break;
			case 2041: result = rasterIndices_2041At36; break;
			case 2042: result = rasterIndices_2042At36; break;
			case 2043: result = rasterIndices_2043At36; break;
			case 2101: result = rasterIndices_2101At36; break;
			case 2111: result = rasterIndices_2111At36; break;
			case 2201: result = rasterIndices_2201At36; break;
			case 2202: result = rasterIndices_2202At36; break;
			case 2203: result = rasterIndices_2203At36; break;
			case 2204: result = rasterIndices_2204At36; break;
			case 2205: result = rasterIndices_2205At36; break;
			case 2206: result = rasterIndices_2206At36; break;
			case 2207: result = rasterIndices_2207At36; break;
			case 2301: result = rasterIndices_2301At36; break;
			case 2302: result = rasterIndices_2302At36; break;
			case 2303: result = rasterIndices_2303At36; break;
			case 2304: result = rasterIndices_2304At36; break;
			case 2305: result = rasterIndices_2305At36; break;
			case 2306: result = rasterIndices_2306At36; break;
			case 2307: result = rasterIndices_2307At36; break;
			}
		case 45:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At45; break;
			case 2011: result = rasterIndices_2011At45; break;
			case 2012: result = rasterIndices_2012At45; break;
			case 2013: result = rasterIndices_2013At45; break;
			case 2021: result = rasterIndices_2021At45; break;
			case 2022: result = rasterIndices_2022At45; break;
			case 2023: result = rasterIndices_2023At45; break;
			case 2031: result = rasterIndices_2031At45; break;
			case 2032: result = rasterIndices_2032At45; break;
			case 2033: result = rasterIndices_2033At45; break;
			case 2041: result = rasterIndices_2041At45; break;
			case 2042: result = rasterIndices_2042At45; break;
			case 2043: result = rasterIndices_2043At45; break;
			case 2101: result = rasterIndices_2101At45; break;
			case 2111: result = rasterIndices_2111At45; break;
			case 2201: result = rasterIndices_2201At45; break;
			case 2202: result = rasterIndices_2202At45; break;
			case 2203: result = rasterIndices_2203At45; break;
			case 2204: result = rasterIndices_2204At45; break;
			case 2205: result = rasterIndices_2205At45; break;
			case 2206: result = rasterIndices_2206At45; break;
			case 2207: result = rasterIndices_2207At45; break;
			case 2301: result = rasterIndices_2301At45; break;
			case 2302: result = rasterIndices_2302At45; break;
			case 2303: result = rasterIndices_2303At45; break;
			case 2304: result = rasterIndices_2304At45; break;
			case 2305: result = rasterIndices_2305At45; break;
			case 2306: result = rasterIndices_2306At45; break;
			case 2307: result = rasterIndices_2307At45; break;
			}
		case 54:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At54; break;
			case 2011: result = rasterIndices_2011At54; break;
			case 2012: result = rasterIndices_2012At54; break;
			case 2013: result = rasterIndices_2013At54; break;
			case 2021: result = rasterIndices_2021At54; break;
			case 2022: result = rasterIndices_2022At54; break;
			case 2023: result = rasterIndices_2023At54; break;
			case 2031: result = rasterIndices_2031At54; break;
			case 2032: result = rasterIndices_2032At54; break;
			case 2033: result = rasterIndices_2033At54; break;
			case 2041: result = rasterIndices_2041At54; break;
			case 2042: result = rasterIndices_2042At54; break;
			case 2043: result = rasterIndices_2043At54; break;
			case 2111: result = rasterIndices_2111At54; break;
			case 2201: result = rasterIndices_2201At54; break;
			case 2202: result = rasterIndices_2202At54; break;
			case 2203: result = rasterIndices_2203At54; break;
			case 2204: result = rasterIndices_2204At54; break;
			case 2205: result = rasterIndices_2205At54; break;
			case 2206: result = rasterIndices_2206At54; break;
			case 2207: result = rasterIndices_2207At54; break;
			case 2301: result = rasterIndices_2301At54; break;
			case 2302: result = rasterIndices_2302At54; break;
			case 2303: result = rasterIndices_2303At54; break;
			case 2304: result = rasterIndices_2304At54; break;
			case 2305: result = rasterIndices_2305At54; break;
			case 2306: result = rasterIndices_2306At54; break;
			case 2307: result = rasterIndices_2307At54; break;
			}
		case 63:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At63; break;
			case 2011: result = rasterIndices_2011At63; break;
			case 2012: result = rasterIndices_2012At63; break;
			case 2013: result = rasterIndices_2013At63; break;
			case 2021: result = rasterIndices_2021At63; break;
			case 2022: result = rasterIndices_2022At63; break;
			case 2023: result = rasterIndices_2023At63; break;
			case 2041: result = rasterIndices_2041At63; break;
			case 2042: result = rasterIndices_2042At63; break;
			case 2043: result = rasterIndices_2043At63; break;
			case 2111: result = rasterIndices_2111At63; break;
			case 2201: result = rasterIndices_2201At63; break;
			case 2202: result = rasterIndices_2202At63; break;
			case 2203: result = rasterIndices_2203At63; break;
			case 2204: result = rasterIndices_2204At63; break;
			case 2205: result = rasterIndices_2205At63; break;
			case 2206: result = rasterIndices_2206At63; break;
			case 2207: result = rasterIndices_2207At63; break;
			case 2301: result = rasterIndices_2301At63; break;
			case 2302: result = rasterIndices_2302At63; break;
			case 2303: result = rasterIndices_2303At63; break;
			case 2304: result = rasterIndices_2304At63; break;
			case 2305: result = rasterIndices_2305At63; break;
			case 2306: result = rasterIndices_2306At63; break;
			case 2307: result = rasterIndices_2307At63; break;
			}
		case 72:
			switch (nrHLC) {
			case 2000: result = rasterIndices_2000At72; break;
			case 2021: result = rasterIndices_2021At72; break;
			case 2022: result = rasterIndices_2022At72; break;
			case 2023: result = rasterIndices_2023At72; break;
			case 2041: result = rasterIndices_2041At72; break;
			case 2042: result = rasterIndices_2042At72; break;
			case 2043: result = rasterIndices_2043At72; break;
			case 2111: result = rasterIndices_2111At72; break;
			case 2201: result = rasterIndices_2201At72; break;
			case 2202: result = rasterIndices_2202At72; break;
			case 2203: result = rasterIndices_2203At72; break;
			case 2204: result = rasterIndices_2204At72; break;
			case 2205: result = rasterIndices_2205At72; break;
			case 2206: result = rasterIndices_2206At72; break;
			case 2207: result = rasterIndices_2207At72; break;
			}

		default:
			result = new ArrayList<Integer>();
		}

		return result;
	}

	private List<Integer> getHLCCornerMaximaNrs(int nrHLC) {
		List<Integer> result;
		switch (nrHLC) {
		case 2000: result = cornerMaximaNrs_2000; break;
		case 2011: result = cornerMaximaNrs_2011; break;
		case 2012: result = cornerMaximaNrs_2012; break;
		case 2013: result = cornerMaximaNrs_2013; break;
		case 2021: result = cornerMaximaNrs_2021; break;
		case 2022: result = cornerMaximaNrs_2022; break;
		case 2023: result = cornerMaximaNrs_2023; break;
		case 2031: result = cornerMaximaNrs_2031; break;
		case 2032: result = cornerMaximaNrs_2032; break;
		case 2033: result = cornerMaximaNrs_2033; break;
		case 2041: result = cornerMaximaNrs_2041; break;
		case 2042: result = cornerMaximaNrs_2042; break;
		case 2043: result = cornerMaximaNrs_2043; break;
		case 2101: result = cornerMaximaNrs_2101; break;
		case 2111: result = cornerMaximaNrs_2111; break;
		case 2201: result = cornerMaximaNrs_2201; break;
		case 2202: result = cornerMaximaNrs_2202; break;
		case 2203: result = cornerMaximaNrs_2203; break;
		case 2204: result = cornerMaximaNrs_2204; break;
		case 2205: result = cornerMaximaNrs_2205; break;
		case 2206: result = cornerMaximaNrs_2206; break;
		case 2207: result = cornerMaximaNrs_2207; break;
		case 2301: result = cornerMaximaNrs_2301; break;
		case 2302: result = cornerMaximaNrs_2302; break;
		case 2303: result = cornerMaximaNrs_2303; break;
		case 2304: result = cornerMaximaNrs_2304; break;
		case 2305: result = cornerMaximaNrs_2305; break;
		case 2306: result = cornerMaximaNrs_2306; break;
		case 2307: result = cornerMaximaNrs_2307; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}
