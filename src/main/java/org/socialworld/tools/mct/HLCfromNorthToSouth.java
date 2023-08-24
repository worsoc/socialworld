/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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

class HLCfromNorthToSouth extends SubHLC {

	private static int[] startAt0 = 
			{1000, 1011, 1012, 1013, 1031, 1032, 1033,
			1101,	
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt1 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033,
			1101,	
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt2 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
			1101,	
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt3 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
			1101, 1111, 	
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt4 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
			1101, 1111,	
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt5 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
			1101, 1111,
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt6 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
			1111,
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt7 = 
			{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1041, 1042, 1043,
			1111,
			1201, 1202, 1203, 1204, 1205, 1206, 1207,
			1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] startAt8 = 
			{1000, 1021, 1022, 1023, 1041, 1042, 1043,
			1111,
			1201, 1202, 1203, 1204, 1205, 1206, 1207};
	
	private static int[] endAt72 = 
		{1000, 1011, 1012, 1013, 1031, 1032, 1033,
		1101,	
		1201, 1202, 1203, 1204, 1205, 1206, 1207};
	private static int[] endAt73 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033,
		1101,	
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt74 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
		1101,	
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt75 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
		1101, 1111, 	
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt76 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
		1101, 1111,	
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt77 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
		1101, 1111,
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt78 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1031, 1032, 1033, 1041, 1042, 1043,
		1111,
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt79 = 
		{1000, 1011, 1012, 1013, 1021, 1022, 1023, 1041, 1042, 1043,
		1111,
		1201, 1202, 1203, 1204, 1205, 1206, 1207,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};
	private static int[] endAt80 = 
		{1000, 1021, 1022, 1023, 1041, 1042, 1043,
		1111,
		1301, 1302, 1303, 1304, 1305, 1306, 1307};


	private static int[] rasterIndicesHLC_1000 = {0,9,18,27,36,45,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1000 = 
		{19914, 19914, 19914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1000At00;
	private List<Integer> rasterIndices_1000At01;
	private List<Integer> rasterIndices_1000At02;
	private List<Integer> rasterIndices_1000At03;
	private List<Integer> rasterIndices_1000At04;
	private List<Integer> rasterIndices_1000At05;
	private List<Integer> rasterIndices_1000At06;
	private List<Integer> rasterIndices_1000At07;
	private List<Integer> rasterIndices_1000At08;
	private List<Integer> cornerMaximaNrs_1000;
	
	private static int[] rasterIndicesHLC_1011 = {0,9,18,27,28,37,45,46,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1011 = 
		{19914, 19914, 19914, 19114, 99914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1011At00;
	private List<Integer> rasterIndices_1011At01;
	private List<Integer> rasterIndices_1011At02;
	private List<Integer> rasterIndices_1011At03;
	private List<Integer> rasterIndices_1011At04;
	private List<Integer> rasterIndices_1011At05;
	private List<Integer> rasterIndices_1011At06;
	private List<Integer> rasterIndices_1011At07;
	private List<Integer> cornerMaximaNrs_1011;

	private static int[] rasterIndicesHLC_1012 = {0,9,18,19,28,37,46,54,55,63,72};
	private static Integer[] cornerMaximaNrsHLC_1012 = 
		{19914, 19914, 19114, 99914, 19914, 19914, 19914, 11914, 19994, 19914, 19914};
	private List<Integer> rasterIndices_1012At00;
	private List<Integer> rasterIndices_1012At01;
	private List<Integer> rasterIndices_1012At02;
	private List<Integer> rasterIndices_1012At03;
	private List<Integer> rasterIndices_1012At04;
	private List<Integer> rasterIndices_1012At05;
	private List<Integer> rasterIndices_1012At06;
	private List<Integer> rasterIndices_1012At07;
	private List<Integer> cornerMaximaNrs_1012;

	private static int[] rasterIndicesHLC_1013 = {0,9,10,19,28,37,46,55,63,64,72};
	private static Integer[] cornerMaximaNrsHLC_1013 = 
		{19914, 19114, 99914, 19914, 19914, 19914, 19914, 19914, 11914, 19994, 19914};
	private List<Integer> rasterIndices_1013At00;
	private List<Integer> rasterIndices_1013At01;
	private List<Integer> rasterIndices_1013At02;
	private List<Integer> rasterIndices_1013At03;
	private List<Integer> rasterIndices_1013At04;
	private List<Integer> rasterIndices_1013At05;
	private List<Integer> rasterIndices_1013At06;
	private List<Integer> rasterIndices_1013At07;
	private List<Integer> cornerMaximaNrs_1013;
	
	private static int[] rasterIndicesHLC_1021 = {1,9,10,18,27,36,45,54,63,64,73};
	private static Integer[] cornerMaximaNrsHLC_1021 = 
		{19914, 11914, 19994, 19914, 19914, 19914, 19914, 19914, 19114, 99914, 19914};
	private List<Integer> rasterIndices_1021At01;
	private List<Integer> rasterIndices_1021At02;
	private List<Integer> rasterIndices_1021At03;
	private List<Integer> rasterIndices_1021At04;
	private List<Integer> rasterIndices_1021At05;
	private List<Integer> rasterIndices_1021At06;
	private List<Integer> rasterIndices_1021At07;
	private List<Integer> rasterIndices_1021At08;
	private List<Integer> cornerMaximaNrs_1021;

	private static int[] rasterIndicesHLC_1022 = {1,10,18,19,27,36,45,54,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1022 = 
		{19914, 19914, 11914, 19994, 19914, 19914, 19914, 19114, 99914, 19914, 19914};
	private List<Integer> rasterIndices_1022At01;
	private List<Integer> rasterIndices_1022At02;
	private List<Integer> rasterIndices_1022At03;
	private List<Integer> rasterIndices_1022At04;
	private List<Integer> rasterIndices_1022At05;
	private List<Integer> rasterIndices_1022At06;
	private List<Integer> rasterIndices_1022At07;
	private List<Integer> rasterIndices_1022At08;
	private List<Integer> cornerMaximaNrs_1022;

	private static int[] rasterIndicesHLC_1023 = {1,10,19,27,28,36,45,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1023 = 
		{19914, 19914, 19914, 11914, 19994, 19914, 19114, 99914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1023At01;
	private List<Integer> rasterIndices_1023At02;
	private List<Integer> rasterIndices_1023At03;
	private List<Integer> rasterIndices_1023At04;
	private List<Integer> rasterIndices_1023At05;
	private List<Integer> rasterIndices_1023At06;
	private List<Integer> rasterIndices_1023At07;
	private List<Integer> rasterIndices_1023At08;
	private List<Integer> cornerMaximaNrs_1023;
	
	private static int[] rasterIndicesHLC_1031 = {0,9,18,27,28,29,38,45,46,47,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1031 = 
		{19914, 19914, 19914, 19114, 99114, 99914, 19914, 11914, 11994, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1031At00;
	private List<Integer> rasterIndices_1031At01;
	private List<Integer> rasterIndices_1031At02;
	private List<Integer> rasterIndices_1031At03;
	private List<Integer> rasterIndices_1031At04;
	private List<Integer> rasterIndices_1031At05;
	private List<Integer> rasterIndices_1031At06;
	private List<Integer> cornerMaximaNrs_1031;

	private static int[] rasterIndicesHLC_1032 = {0,9,18,19,20,29,38,47,54,55,56,63,72};
	private static Integer[] cornerMaximaNrsHLC_1032 = 
		{19914, 19914, 19114, 99114, 99914, 19914, 19914, 19914, 11914, 11994, 19994, 19914, 19914};
	private List<Integer> rasterIndices_1032At00;
	private List<Integer> rasterIndices_1032At01;
	private List<Integer> rasterIndices_1032At02;
	private List<Integer> rasterIndices_1032At03;
	private List<Integer> rasterIndices_1032At04;
	private List<Integer> rasterIndices_1032At05;
	private List<Integer> rasterIndices_1032At06;
	private List<Integer> cornerMaximaNrs_1032;

	private static int[] rasterIndicesHLC_1033 = {0,9,10,11,20,29,38,47,56,63,64,65,72};
	private static Integer[] cornerMaximaNrsHLC_1033 = 
		{19914, 19114, 99114, 99914, 19914, 19914, 19914, 19914, 19914, 11914, 11994, 19994, 19914};
	private List<Integer> rasterIndices_1033At00;
	private List<Integer> rasterIndices_1033At01;
	private List<Integer> rasterIndices_1033At02;
	private List<Integer> rasterIndices_1033At03;
	private List<Integer> rasterIndices_1033At04;
	private List<Integer> rasterIndices_1033At05;
	private List<Integer> rasterIndices_1033At06;
	private List<Integer> cornerMaximaNrs_1033;
	
	private static int[] rasterIndicesHLC_1041 = {2,9,10,11,18,27,36,45,54,63,64,65,74};
	private static Integer[] cornerMaximaNrsHLC_1041 = 
		{19914, 11914, 11994, 19994, 19914, 19914, 19914, 19914, 19914, 19114, 99114, 99914, 19914};
	private List<Integer> rasterIndices_1041At02;
	private List<Integer> rasterIndices_1041At03;
	private List<Integer> rasterIndices_1041At04;
	private List<Integer> rasterIndices_1041At05;
	private List<Integer> rasterIndices_1041At06;
	private List<Integer> rasterIndices_1041At07;
	private List<Integer> rasterIndices_1041At08;
	private List<Integer> cornerMaximaNrs_1041;

	private static int[] rasterIndicesHLC_1042 = {2,11,18,19,20,27,36,45,54,55,56,65,74};
	private static Integer[] cornerMaximaNrsHLC_1042 = 
		{19914, 19914, 11914, 11994, 19994, 19914, 19914, 19914, 19114, 99114, 99914, 19914, 19914};
	private List<Integer> rasterIndices_1042At02;
	private List<Integer> rasterIndices_1042At03;
	private List<Integer> rasterIndices_1042At04;
	private List<Integer> rasterIndices_1042At05;
	private List<Integer> rasterIndices_1042At06;
	private List<Integer> rasterIndices_1042At07;
	private List<Integer> rasterIndices_1042At08;
	private List<Integer> cornerMaximaNrs_1042;

	private static int[] rasterIndicesHLC_1043 = {2,11,20,27,28,29,36,45,46,47,56,65,74};
	private static Integer[] cornerMaximaNrsHLC_1043 = 
		{19914, 19914, 19914, 11914, 11994, 19994, 19914, 19114, 99114, 99914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1043At02;
	private List<Integer> rasterIndices_1043At03;
	private List<Integer> rasterIndices_1043At04;
	private List<Integer> rasterIndices_1043At05;
	private List<Integer> rasterIndices_1043At06;
	private List<Integer> rasterIndices_1043At07;
	private List<Integer> rasterIndices_1043At08;
	private List<Integer> cornerMaximaNrs_1043;
	
	private static int[] rasterIndicesHLC_1101 = {0,9,10,19,20,29,30,39,47,48,55,56,63,64,72};
	private static Integer[] cornerMaximaNrsHLC_1101 = 
		{19914, 19114, 99914, 19114, 99914, 19114, 99914, 19914, 11914, 19994, 11914, 19994, 11914, 19994, 19914};
	private List<Integer> rasterIndices_1101At00;
	private List<Integer> rasterIndices_1101At01;
	private List<Integer> rasterIndices_1101At02;
	private List<Integer> rasterIndices_1101At03;
	private List<Integer> rasterIndices_1101At04;
	private List<Integer> rasterIndices_1101At05;
	private List<Integer> cornerMaximaNrs_1101;
	
	private static int[] rasterIndicesHLC_1111 = {3,11,12,19,20,27,28,36,45,46,55,56,65,66,75};
	private static Integer[] cornerMaximaNrsHLC_1111 = 
		{19914, 11914, 19994, 11914, 19994, 11914, 19994, 19914, 19114, 99914, 19114, 99914, 19114, 99914, 19914};
	private List<Integer> rasterIndices_1111At03;
	private List<Integer> rasterIndices_1111At04;
	private List<Integer> rasterIndices_1111At05;
	private List<Integer> rasterIndices_1111At06;
	private List<Integer> rasterIndices_1111At07;
	private List<Integer> rasterIndices_1111At08;
	private List<Integer> cornerMaximaNrs_1111;
	
	private static int[] rasterIndicesHLC_1201 = {1,9,10,18,27,36,45,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1201 = 
		{19914, 11914, 19994, 19914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1201At01;
	private List<Integer> rasterIndices_1201At02;
	private List<Integer> rasterIndices_1201At03;
	private List<Integer> rasterIndices_1201At04;
	private List<Integer> rasterIndices_1201At05;
	private List<Integer> rasterIndices_1201At06;
	private List<Integer> rasterIndices_1201At07;
	private List<Integer> rasterIndices_1201At08;
	private List<Integer> cornerMaximaNrs_1201;

	private static int[] rasterIndicesHLC_1202 = {1,10,18,19,27,36,45,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1202 = 
		{19914, 19914, 11914, 19994, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1202At01;
	private List<Integer> rasterIndices_1202At02;
	private List<Integer> rasterIndices_1202At03;
	private List<Integer> rasterIndices_1202At04;
	private List<Integer> rasterIndices_1202At05;
	private List<Integer> rasterIndices_1202At06;
	private List<Integer> rasterIndices_1202At07;
	private List<Integer> rasterIndices_1202At08;
	private List<Integer> cornerMaximaNrs_1202;

	private static int[] rasterIndicesHLC_1203 = {1,10,19,27,28,36,45,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1203 = 
		{19914, 19914, 19914, 11914, 19994, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1203At01;
	private List<Integer> rasterIndices_1203At02;
	private List<Integer> rasterIndices_1203At03;
	private List<Integer> rasterIndices_1203At04;
	private List<Integer> rasterIndices_1203At05;
	private List<Integer> rasterIndices_1203At06;
	private List<Integer> rasterIndices_1203At07;
	private List<Integer> rasterIndices_1203At08;
	private List<Integer> cornerMaximaNrs_1203;

	private static int[] rasterIndicesHLC_1204 = {1,10,19,28,36,37,45,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1204 = 
		{19914, 19914, 19914, 19914, 11914, 19994, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1204At01;
	private List<Integer> rasterIndices_1204At02;
	private List<Integer> rasterIndices_1204At03;
	private List<Integer> rasterIndices_1204At04;
	private List<Integer> rasterIndices_1204At05;
	private List<Integer> rasterIndices_1204At06;
	private List<Integer> rasterIndices_1204At07;
	private List<Integer> rasterIndices_1204At08;
	private List<Integer> cornerMaximaNrs_1204;
	
	private static int[] rasterIndicesHLC_1205 = {1,10,19,28,37,45,46,54,63,72};
	private static Integer[] cornerMaximaNrsHLC_1205 = 
		{19914, 19914, 19914, 19914, 19914, 11914, 19994, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1205At01;
	private List<Integer> rasterIndices_1205At02;
	private List<Integer> rasterIndices_1205At03;
	private List<Integer> rasterIndices_1205At04;
	private List<Integer> rasterIndices_1205At05;
	private List<Integer> rasterIndices_1205At06;
	private List<Integer> rasterIndices_1205At07;
	private List<Integer> rasterIndices_1205At08;
	private List<Integer> cornerMaximaNrs_1205;
	
	private static int[] rasterIndicesHLC_1206 = {1,10,19,28,37,46,54,55,63,72};
	private static Integer[] cornerMaximaNrsHLC_1206 = 
		{19914, 19914, 19914, 19914, 19914, 19914, 11914, 19994, 19914, 19914};
	private List<Integer> rasterIndices_1206At01;
	private List<Integer> rasterIndices_1206At02;
	private List<Integer> rasterIndices_1206At03;
	private List<Integer> rasterIndices_1206At04;
	private List<Integer> rasterIndices_1206At05;
	private List<Integer> rasterIndices_1206At06;
	private List<Integer> rasterIndices_1206At07;
	private List<Integer> rasterIndices_1206At08;
	private List<Integer> cornerMaximaNrs_1206;
	
	private static int[] rasterIndicesHLC_1207 = {1,10,19,28,37,46,55,63,64,72};
	private static Integer[] cornerMaximaNrsHLC_1207 = 
		{19914, 19914, 19914, 19914, 19914, 19914, 19914, 11914, 19994, 19914};
	private List<Integer> rasterIndices_1207At01;
	private List<Integer> rasterIndices_1207At02;
	private List<Integer> rasterIndices_1207At03;
	private List<Integer> rasterIndices_1207At04;
	private List<Integer> rasterIndices_1207At05;
	private List<Integer> rasterIndices_1207At06;
	private List<Integer> rasterIndices_1207At07;
	private List<Integer> rasterIndices_1207At08;
	private List<Integer> cornerMaximaNrs_1207;
	
	private static int[] rasterIndicesHLC_1301 = {0,9,18,27,36,45,54,63,64,73};
	private static Integer[] cornerMaximaNrsHLC_1301 = 
		{19914, 19914, 19914, 19914, 19914, 19914, 19914, 19114, 99914, 19914};
	private List<Integer> rasterIndices_1301At00;
	private List<Integer> rasterIndices_1301At01;
	private List<Integer> rasterIndices_1301At02;
	private List<Integer> rasterIndices_1301At03;
	private List<Integer> rasterIndices_1301At04;
	private List<Integer> rasterIndices_1301At05;
	private List<Integer> rasterIndices_1301At06;
	private List<Integer> rasterIndices_1301At07;
	private List<Integer> cornerMaximaNrs_1301;

	private static int[] rasterIndicesHLC_1302 = {0,9,18,27,36,45,54,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1302 = 
		{19914, 19914, 19914, 19914, 19914, 19914, 19114, 99914, 19914, 19914};
	private List<Integer> rasterIndices_1302At00;
	private List<Integer> rasterIndices_1302At01;
	private List<Integer> rasterIndices_1302At02;
	private List<Integer> rasterIndices_1302At03;
	private List<Integer> rasterIndices_1302At04;
	private List<Integer> rasterIndices_1302At05;
	private List<Integer> rasterIndices_1302At06;
	private List<Integer> rasterIndices_1302At07;
	private List<Integer> cornerMaximaNrs_1302;

	private static int[] rasterIndicesHLC_1303 = {0,9,18,27,36,45,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1303 = 
		{19914, 19914, 19914, 19914, 19914, 19114, 99914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1303At00;
	private List<Integer> rasterIndices_1303At01;
	private List<Integer> rasterIndices_1303At02;
	private List<Integer> rasterIndices_1303At03;
	private List<Integer> rasterIndices_1303At04;
	private List<Integer> rasterIndices_1303At05;
	private List<Integer> rasterIndices_1303At06;
	private List<Integer> rasterIndices_1303At07;
	private List<Integer> cornerMaximaNrs_1303;
	
	private static int[] rasterIndicesHLC_1304 = {0,9,18,27,36,37,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1304 = 
		{19914, 19914, 19914, 19914, 19114, 99914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1304At00;
	private List<Integer> rasterIndices_1304At01;
	private List<Integer> rasterIndices_1304At02;
	private List<Integer> rasterIndices_1304At03;
	private List<Integer> rasterIndices_1304At04;
	private List<Integer> rasterIndices_1304At05;
	private List<Integer> rasterIndices_1304At06;
	private List<Integer> rasterIndices_1304At07;
	private List<Integer> cornerMaximaNrs_1304;

	private static int[] rasterIndicesHLC_1305 = {0,9,18,27,28,37,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1305 = 
		{19914, 19914, 19914, 19114, 99914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1305At00;
	private List<Integer> rasterIndices_1305At01;
	private List<Integer> rasterIndices_1305At02;
	private List<Integer> rasterIndices_1305At03;
	private List<Integer> rasterIndices_1305At04;
	private List<Integer> rasterIndices_1305At05;
	private List<Integer> rasterIndices_1305At06;
	private List<Integer> rasterIndices_1305At07;
	private List<Integer> cornerMaximaNrs_1305;
	
	private static int[] rasterIndicesHLC_1306 = {0,9,18,19,28,37,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1306 = 
		{19914, 19914, 19114, 99914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1306At00;
	private List<Integer> rasterIndices_1306At01;
	private List<Integer> rasterIndices_1306At02;
	private List<Integer> rasterIndices_1306At03;
	private List<Integer> rasterIndices_1306At04;
	private List<Integer> rasterIndices_1306At05;
	private List<Integer> rasterIndices_1306At06;
	private List<Integer> rasterIndices_1306At07;
	private List<Integer> cornerMaximaNrs_1306;
	
	private static int[] rasterIndicesHLC_1307 = {0,9,10,19,28,37,46,55,64,73};
	private static Integer[] cornerMaximaNrsHLC_1307 = 
		{19914, 19114, 99914, 19914, 19914, 19914, 19914, 19914, 19914, 19914};
	private List<Integer> rasterIndices_1307At00;
	private List<Integer> rasterIndices_1307At01;
	private List<Integer> rasterIndices_1307At02;
	private List<Integer> rasterIndices_1307At03;
	private List<Integer> rasterIndices_1307At04;
	private List<Integer> rasterIndices_1307At05;
	private List<Integer> rasterIndices_1307At06;
	private List<Integer> rasterIndices_1307At07;
	private List<Integer> cornerMaximaNrs_1307;
	
	private HLCfromNorthToSouth() {
		rasterIndices_1000At00 = getRasterIndicesList(0, rasterIndicesHLC_1000);
		rasterIndices_1000At01 = getRasterIndicesList(1, rasterIndicesHLC_1000);
		rasterIndices_1000At02 = getRasterIndicesList(2, rasterIndicesHLC_1000);
		rasterIndices_1000At03 = getRasterIndicesList(3, rasterIndicesHLC_1000);
		rasterIndices_1000At04 = getRasterIndicesList(4, rasterIndicesHLC_1000);
		rasterIndices_1000At05 = getRasterIndicesList(5, rasterIndicesHLC_1000);
		rasterIndices_1000At06 = getRasterIndicesList(6, rasterIndicesHLC_1000);
		rasterIndices_1000At07 = getRasterIndicesList(7, rasterIndicesHLC_1000);
		rasterIndices_1000At08 = getRasterIndicesList(8, rasterIndicesHLC_1000);
		cornerMaximaNrs_1000 = Arrays.asList(cornerMaximaNrsHLC_1000);
		
		rasterIndices_1011At00 = getRasterIndicesList(0, rasterIndicesHLC_1011);
		rasterIndices_1011At01 = getRasterIndicesList(1, rasterIndicesHLC_1011);
		rasterIndices_1011At02 = getRasterIndicesList(2, rasterIndicesHLC_1011);
		rasterIndices_1011At03 = getRasterIndicesList(3, rasterIndicesHLC_1011);
		rasterIndices_1011At04 = getRasterIndicesList(4, rasterIndicesHLC_1011);
		rasterIndices_1011At05 = getRasterIndicesList(5, rasterIndicesHLC_1011);
		rasterIndices_1011At06 = getRasterIndicesList(6, rasterIndicesHLC_1011);
		rasterIndices_1011At07 = getRasterIndicesList(7, rasterIndicesHLC_1011);
		cornerMaximaNrs_1011 = Arrays.asList(cornerMaximaNrsHLC_1011);

		rasterIndices_1012At00 = getRasterIndicesList(0, rasterIndicesHLC_1012);
		rasterIndices_1012At01 = getRasterIndicesList(1, rasterIndicesHLC_1012);
		rasterIndices_1012At02 = getRasterIndicesList(2, rasterIndicesHLC_1012);
		rasterIndices_1012At03 = getRasterIndicesList(3, rasterIndicesHLC_1012);
		rasterIndices_1012At04 = getRasterIndicesList(4, rasterIndicesHLC_1012);
		rasterIndices_1012At05 = getRasterIndicesList(5, rasterIndicesHLC_1012);
		rasterIndices_1012At06 = getRasterIndicesList(6, rasterIndicesHLC_1012);
		rasterIndices_1012At07 = getRasterIndicesList(7, rasterIndicesHLC_1012);
		cornerMaximaNrs_1012 = Arrays.asList(cornerMaximaNrsHLC_1012);

		rasterIndices_1013At00 = getRasterIndicesList(0, rasterIndicesHLC_1013);
		rasterIndices_1013At01 = getRasterIndicesList(1, rasterIndicesHLC_1013);
		rasterIndices_1013At02 = getRasterIndicesList(2, rasterIndicesHLC_1013);
		rasterIndices_1013At03 = getRasterIndicesList(3, rasterIndicesHLC_1013);
		rasterIndices_1013At04 = getRasterIndicesList(4, rasterIndicesHLC_1013);
		rasterIndices_1013At05 = getRasterIndicesList(5, rasterIndicesHLC_1013);
		rasterIndices_1013At06 = getRasterIndicesList(6, rasterIndicesHLC_1013);
		rasterIndices_1013At07 = getRasterIndicesList(7, rasterIndicesHLC_1013);
		cornerMaximaNrs_1013 = Arrays.asList(cornerMaximaNrsHLC_1013);
		
		rasterIndices_1021At01 = getRasterIndicesList(1, rasterIndicesHLC_1021);
		rasterIndices_1021At02 = getRasterIndicesList(2, rasterIndicesHLC_1021);
		rasterIndices_1021At03 = getRasterIndicesList(3, rasterIndicesHLC_1021);
		rasterIndices_1021At04 = getRasterIndicesList(4, rasterIndicesHLC_1021);
		rasterIndices_1021At05 = getRasterIndicesList(5, rasterIndicesHLC_1021);
		rasterIndices_1021At06 = getRasterIndicesList(6, rasterIndicesHLC_1021);
		rasterIndices_1021At07 = getRasterIndicesList(7, rasterIndicesHLC_1021);
		rasterIndices_1021At08 = getRasterIndicesList(8, rasterIndicesHLC_1021);
		cornerMaximaNrs_1021 = Arrays.asList(cornerMaximaNrsHLC_1021);
	
		rasterIndices_1022At01 = getRasterIndicesList(1, rasterIndicesHLC_1022);
		rasterIndices_1022At02 = getRasterIndicesList(2, rasterIndicesHLC_1022);
		rasterIndices_1022At03 = getRasterIndicesList(3, rasterIndicesHLC_1022);
		rasterIndices_1022At04 = getRasterIndicesList(4, rasterIndicesHLC_1022);
		rasterIndices_1022At05 = getRasterIndicesList(5, rasterIndicesHLC_1022);
		rasterIndices_1022At06 = getRasterIndicesList(6, rasterIndicesHLC_1022);
		rasterIndices_1022At07 = getRasterIndicesList(7, rasterIndicesHLC_1022);
		rasterIndices_1022At08 = getRasterIndicesList(8, rasterIndicesHLC_1022);
		cornerMaximaNrs_1022 = Arrays.asList(cornerMaximaNrsHLC_1022);

		rasterIndices_1023At01 = getRasterIndicesList(1, rasterIndicesHLC_1023);
		rasterIndices_1023At02 = getRasterIndicesList(2, rasterIndicesHLC_1023);
		rasterIndices_1023At03 = getRasterIndicesList(3, rasterIndicesHLC_1023);
		rasterIndices_1023At04 = getRasterIndicesList(4, rasterIndicesHLC_1023);
		rasterIndices_1023At05 = getRasterIndicesList(5, rasterIndicesHLC_1023);
		rasterIndices_1023At06 = getRasterIndicesList(6, rasterIndicesHLC_1023);
		rasterIndices_1023At07 = getRasterIndicesList(7, rasterIndicesHLC_1023);
		rasterIndices_1023At08 = getRasterIndicesList(8, rasterIndicesHLC_1023);
		cornerMaximaNrs_1023 = Arrays.asList(cornerMaximaNrsHLC_1023);
		
		rasterIndices_1031At00 = getRasterIndicesList(0, rasterIndicesHLC_1031);
		rasterIndices_1031At01 = getRasterIndicesList(1, rasterIndicesHLC_1031);
		rasterIndices_1031At02 = getRasterIndicesList(2, rasterIndicesHLC_1031);
		rasterIndices_1031At03 = getRasterIndicesList(3, rasterIndicesHLC_1031);
		rasterIndices_1031At04 = getRasterIndicesList(4, rasterIndicesHLC_1031);
		rasterIndices_1031At05 = getRasterIndicesList(5, rasterIndicesHLC_1031);
		rasterIndices_1031At06 = getRasterIndicesList(6, rasterIndicesHLC_1031);
		cornerMaximaNrs_1031 = Arrays.asList(cornerMaximaNrsHLC_1031);

		rasterIndices_1032At00 = getRasterIndicesList(0, rasterIndicesHLC_1032);
		rasterIndices_1032At01 = getRasterIndicesList(1, rasterIndicesHLC_1032);
		rasterIndices_1032At02 = getRasterIndicesList(2, rasterIndicesHLC_1032);
		rasterIndices_1032At03 = getRasterIndicesList(3, rasterIndicesHLC_1032);
		rasterIndices_1032At04 = getRasterIndicesList(4, rasterIndicesHLC_1032);
		rasterIndices_1032At05 = getRasterIndicesList(5, rasterIndicesHLC_1032);
		rasterIndices_1032At06 = getRasterIndicesList(6, rasterIndicesHLC_1032);
		cornerMaximaNrs_1032 = Arrays.asList(cornerMaximaNrsHLC_1032);

		rasterIndices_1033At00 = getRasterIndicesList(0, rasterIndicesHLC_1033);
		rasterIndices_1033At01 = getRasterIndicesList(1, rasterIndicesHLC_1033);
		rasterIndices_1033At02 = getRasterIndicesList(2, rasterIndicesHLC_1033);
		rasterIndices_1033At03 = getRasterIndicesList(3, rasterIndicesHLC_1033);
		rasterIndices_1033At04 = getRasterIndicesList(4, rasterIndicesHLC_1033);
		rasterIndices_1033At05 = getRasterIndicesList(5, rasterIndicesHLC_1033);
		rasterIndices_1033At06 = getRasterIndicesList(6, rasterIndicesHLC_1033);
		cornerMaximaNrs_1033 = Arrays.asList(cornerMaximaNrsHLC_1033);
		
		rasterIndices_1041At02 = getRasterIndicesList(2, rasterIndicesHLC_1041);
		rasterIndices_1041At03 = getRasterIndicesList(3, rasterIndicesHLC_1041);
		rasterIndices_1041At04 = getRasterIndicesList(4, rasterIndicesHLC_1041);
		rasterIndices_1041At05 = getRasterIndicesList(5, rasterIndicesHLC_1041);
		rasterIndices_1041At06 = getRasterIndicesList(6, rasterIndicesHLC_1041);
		rasterIndices_1041At07 = getRasterIndicesList(7, rasterIndicesHLC_1041);
		rasterIndices_1041At08 = getRasterIndicesList(8, rasterIndicesHLC_1041);
		cornerMaximaNrs_1041 = Arrays.asList(cornerMaximaNrsHLC_1041);
	
		rasterIndices_1042At02 = getRasterIndicesList(2, rasterIndicesHLC_1042);
		rasterIndices_1042At03 = getRasterIndicesList(3, rasterIndicesHLC_1042);
		rasterIndices_1042At04 = getRasterIndicesList(4, rasterIndicesHLC_1042);
		rasterIndices_1042At05 = getRasterIndicesList(5, rasterIndicesHLC_1042);
		rasterIndices_1042At06 = getRasterIndicesList(6, rasterIndicesHLC_1042);
		rasterIndices_1042At07 = getRasterIndicesList(7, rasterIndicesHLC_1042);
		rasterIndices_1042At08 = getRasterIndicesList(8, rasterIndicesHLC_1042);
		cornerMaximaNrs_1042 = Arrays.asList(cornerMaximaNrsHLC_1042);

		rasterIndices_1043At02 = getRasterIndicesList(2, rasterIndicesHLC_1043);
		rasterIndices_1043At03 = getRasterIndicesList(3, rasterIndicesHLC_1043);
		rasterIndices_1043At04 = getRasterIndicesList(4, rasterIndicesHLC_1043);
		rasterIndices_1043At05 = getRasterIndicesList(5, rasterIndicesHLC_1043);
		rasterIndices_1043At06 = getRasterIndicesList(6, rasterIndicesHLC_1043);
		rasterIndices_1043At07 = getRasterIndicesList(7, rasterIndicesHLC_1043);
		rasterIndices_1043At08 = getRasterIndicesList(8, rasterIndicesHLC_1043);
		cornerMaximaNrs_1043 = Arrays.asList(cornerMaximaNrsHLC_1043);
		
		rasterIndices_1101At00 = getRasterIndicesList(0, rasterIndicesHLC_1101);
		rasterIndices_1101At01 = getRasterIndicesList(1, rasterIndicesHLC_1101);
		rasterIndices_1101At02 = getRasterIndicesList(2, rasterIndicesHLC_1101);
		rasterIndices_1101At03 = getRasterIndicesList(3, rasterIndicesHLC_1101);
		rasterIndices_1101At04 = getRasterIndicesList(4, rasterIndicesHLC_1101);
		rasterIndices_1101At05 = getRasterIndicesList(5, rasterIndicesHLC_1101);
		cornerMaximaNrs_1101 = Arrays.asList(cornerMaximaNrsHLC_1101);
	
		rasterIndices_1111At03 = getRasterIndicesList(3, rasterIndicesHLC_1111);
		rasterIndices_1111At04 = getRasterIndicesList(4, rasterIndicesHLC_1111);
		rasterIndices_1111At05 = getRasterIndicesList(5, rasterIndicesHLC_1111);
		rasterIndices_1111At06 = getRasterIndicesList(6, rasterIndicesHLC_1111);
		rasterIndices_1111At07 = getRasterIndicesList(7, rasterIndicesHLC_1111);
		rasterIndices_1111At08 = getRasterIndicesList(8, rasterIndicesHLC_1111);
		cornerMaximaNrs_1111 = Arrays.asList(cornerMaximaNrsHLC_1111);
		
		rasterIndices_1201At01 = getRasterIndicesList(1, rasterIndicesHLC_1201);
		rasterIndices_1201At02 = getRasterIndicesList(2, rasterIndicesHLC_1201);
		rasterIndices_1201At03 = getRasterIndicesList(3, rasterIndicesHLC_1201);
		rasterIndices_1201At04 = getRasterIndicesList(4, rasterIndicesHLC_1201);
		rasterIndices_1201At05 = getRasterIndicesList(5, rasterIndicesHLC_1201);
		rasterIndices_1201At06 = getRasterIndicesList(6, rasterIndicesHLC_1201);
		rasterIndices_1201At07 = getRasterIndicesList(7, rasterIndicesHLC_1201);
		rasterIndices_1201At08 = getRasterIndicesList(8, rasterIndicesHLC_1201);
		cornerMaximaNrs_1201 = Arrays.asList(cornerMaximaNrsHLC_1201);
	
		rasterIndices_1202At01 = getRasterIndicesList(1, rasterIndicesHLC_1202);
		rasterIndices_1202At02 = getRasterIndicesList(2, rasterIndicesHLC_1202);
		rasterIndices_1202At03 = getRasterIndicesList(3, rasterIndicesHLC_1202);
		rasterIndices_1202At04 = getRasterIndicesList(4, rasterIndicesHLC_1202);
		rasterIndices_1202At05 = getRasterIndicesList(5, rasterIndicesHLC_1202);
		rasterIndices_1202At06 = getRasterIndicesList(6, rasterIndicesHLC_1202);
		rasterIndices_1202At07 = getRasterIndicesList(7, rasterIndicesHLC_1202);
		rasterIndices_1202At08 = getRasterIndicesList(8, rasterIndicesHLC_1202);
		cornerMaximaNrs_1202 = Arrays.asList(cornerMaximaNrsHLC_1202);

		rasterIndices_1203At01 = getRasterIndicesList(1, rasterIndicesHLC_1203);
		rasterIndices_1203At02 = getRasterIndicesList(2, rasterIndicesHLC_1203);
		rasterIndices_1203At03 = getRasterIndicesList(3, rasterIndicesHLC_1203);
		rasterIndices_1203At04 = getRasterIndicesList(4, rasterIndicesHLC_1203);
		rasterIndices_1203At05 = getRasterIndicesList(5, rasterIndicesHLC_1203);
		rasterIndices_1203At06 = getRasterIndicesList(6, rasterIndicesHLC_1203);
		rasterIndices_1203At07 = getRasterIndicesList(7, rasterIndicesHLC_1203);
		rasterIndices_1203At08 = getRasterIndicesList(8, rasterIndicesHLC_1203);
		cornerMaximaNrs_1203 = Arrays.asList(cornerMaximaNrsHLC_1203);

		rasterIndices_1204At01 = getRasterIndicesList(1, rasterIndicesHLC_1204);
		rasterIndices_1204At02 = getRasterIndicesList(2, rasterIndicesHLC_1204);
		rasterIndices_1204At03 = getRasterIndicesList(3, rasterIndicesHLC_1204);
		rasterIndices_1204At04 = getRasterIndicesList(4, rasterIndicesHLC_1204);
		rasterIndices_1204At05 = getRasterIndicesList(5, rasterIndicesHLC_1204);
		rasterIndices_1204At06 = getRasterIndicesList(6, rasterIndicesHLC_1204);
		rasterIndices_1204At07 = getRasterIndicesList(7, rasterIndicesHLC_1204);
		rasterIndices_1204At08 = getRasterIndicesList(8, rasterIndicesHLC_1204);
		cornerMaximaNrs_1204 = Arrays.asList(cornerMaximaNrsHLC_1204);
	
		rasterIndices_1205At01 = getRasterIndicesList(1, rasterIndicesHLC_1205);
		rasterIndices_1205At02 = getRasterIndicesList(2, rasterIndicesHLC_1205);
		rasterIndices_1205At03 = getRasterIndicesList(3, rasterIndicesHLC_1205);
		rasterIndices_1205At04 = getRasterIndicesList(4, rasterIndicesHLC_1205);
		rasterIndices_1205At05 = getRasterIndicesList(5, rasterIndicesHLC_1205);
		rasterIndices_1205At06 = getRasterIndicesList(6, rasterIndicesHLC_1205);
		rasterIndices_1205At07 = getRasterIndicesList(7, rasterIndicesHLC_1205);
		rasterIndices_1205At08 = getRasterIndicesList(8, rasterIndicesHLC_1205);
		cornerMaximaNrs_1205 = Arrays.asList(cornerMaximaNrsHLC_1205);

		rasterIndices_1206At01 = getRasterIndicesList(1, rasterIndicesHLC_1206);
		rasterIndices_1206At02 = getRasterIndicesList(2, rasterIndicesHLC_1206);
		rasterIndices_1206At03 = getRasterIndicesList(3, rasterIndicesHLC_1206);
		rasterIndices_1206At04 = getRasterIndicesList(4, rasterIndicesHLC_1206);
		rasterIndices_1206At05 = getRasterIndicesList(5, rasterIndicesHLC_1206);
		rasterIndices_1206At06 = getRasterIndicesList(6, rasterIndicesHLC_1206);
		rasterIndices_1206At07 = getRasterIndicesList(7, rasterIndicesHLC_1206);
		rasterIndices_1206At08 = getRasterIndicesList(8, rasterIndicesHLC_1206);
		cornerMaximaNrs_1206 = Arrays.asList(cornerMaximaNrsHLC_1206);
	
		rasterIndices_1207At01 = getRasterIndicesList(1, rasterIndicesHLC_1207);
		rasterIndices_1207At02 = getRasterIndicesList(2, rasterIndicesHLC_1207);
		rasterIndices_1207At03 = getRasterIndicesList(3, rasterIndicesHLC_1207);
		rasterIndices_1207At04 = getRasterIndicesList(4, rasterIndicesHLC_1207);
		rasterIndices_1207At05 = getRasterIndicesList(5, rasterIndicesHLC_1207);
		rasterIndices_1207At06 = getRasterIndicesList(6, rasterIndicesHLC_1207);
		rasterIndices_1207At07 = getRasterIndicesList(7, rasterIndicesHLC_1207);
		rasterIndices_1207At08 = getRasterIndicesList(8, rasterIndicesHLC_1207);
		cornerMaximaNrs_1207 = Arrays.asList(cornerMaximaNrsHLC_1207);
	
		rasterIndices_1301At00 = getRasterIndicesList(0, rasterIndicesHLC_1301);
		rasterIndices_1301At01 = getRasterIndicesList(1, rasterIndicesHLC_1301);
		rasterIndices_1301At02 = getRasterIndicesList(2, rasterIndicesHLC_1301);
		rasterIndices_1301At03 = getRasterIndicesList(3, rasterIndicesHLC_1301);
		rasterIndices_1301At04 = getRasterIndicesList(4, rasterIndicesHLC_1301);
		rasterIndices_1301At05 = getRasterIndicesList(5, rasterIndicesHLC_1301);
		rasterIndices_1301At06 = getRasterIndicesList(6, rasterIndicesHLC_1301);
		rasterIndices_1301At07 = getRasterIndicesList(7, rasterIndicesHLC_1301);
		cornerMaximaNrs_1301 = Arrays.asList(cornerMaximaNrsHLC_1301);
	
		rasterIndices_1302At00 = getRasterIndicesList(0, rasterIndicesHLC_1302);
		rasterIndices_1302At01 = getRasterIndicesList(1, rasterIndicesHLC_1302);
		rasterIndices_1302At02 = getRasterIndicesList(2, rasterIndicesHLC_1302);
		rasterIndices_1302At03 = getRasterIndicesList(3, rasterIndicesHLC_1302);
		rasterIndices_1302At04 = getRasterIndicesList(4, rasterIndicesHLC_1302);
		rasterIndices_1302At05 = getRasterIndicesList(5, rasterIndicesHLC_1302);
		rasterIndices_1302At06 = getRasterIndicesList(6, rasterIndicesHLC_1302);
		rasterIndices_1302At07 = getRasterIndicesList(7, rasterIndicesHLC_1302);
		cornerMaximaNrs_1302 = Arrays.asList(cornerMaximaNrsHLC_1302);

		rasterIndices_1303At00 = getRasterIndicesList(0, rasterIndicesHLC_1303);
		rasterIndices_1303At01 = getRasterIndicesList(1, rasterIndicesHLC_1303);
		rasterIndices_1303At02 = getRasterIndicesList(2, rasterIndicesHLC_1303);
		rasterIndices_1303At03 = getRasterIndicesList(3, rasterIndicesHLC_1303);
		rasterIndices_1303At04 = getRasterIndicesList(4, rasterIndicesHLC_1303);
		rasterIndices_1303At05 = getRasterIndicesList(5, rasterIndicesHLC_1303);
		rasterIndices_1303At06 = getRasterIndicesList(6, rasterIndicesHLC_1303);
		rasterIndices_1303At07 = getRasterIndicesList(7, rasterIndicesHLC_1303);
		cornerMaximaNrs_1303 = Arrays.asList(cornerMaximaNrsHLC_1303);

		rasterIndices_1304At00 = getRasterIndicesList(0, rasterIndicesHLC_1304);
		rasterIndices_1304At01 = getRasterIndicesList(1, rasterIndicesHLC_1304);
		rasterIndices_1304At02 = getRasterIndicesList(2, rasterIndicesHLC_1304);
		rasterIndices_1304At03 = getRasterIndicesList(3, rasterIndicesHLC_1304);
		rasterIndices_1304At04 = getRasterIndicesList(4, rasterIndicesHLC_1304);
		rasterIndices_1304At05 = getRasterIndicesList(5, rasterIndicesHLC_1304);
		rasterIndices_1304At06 = getRasterIndicesList(6, rasterIndicesHLC_1304);
		rasterIndices_1304At07 = getRasterIndicesList(7, rasterIndicesHLC_1304);
		cornerMaximaNrs_1304 = Arrays.asList(cornerMaximaNrsHLC_1304);
	
		rasterIndices_1305At00 = getRasterIndicesList(0, rasterIndicesHLC_1305);
		rasterIndices_1305At01 = getRasterIndicesList(1, rasterIndicesHLC_1305);
		rasterIndices_1305At02 = getRasterIndicesList(2, rasterIndicesHLC_1305);
		rasterIndices_1305At03 = getRasterIndicesList(3, rasterIndicesHLC_1305);
		rasterIndices_1305At04 = getRasterIndicesList(4, rasterIndicesHLC_1305);
		rasterIndices_1305At05 = getRasterIndicesList(5, rasterIndicesHLC_1305);
		rasterIndices_1305At06 = getRasterIndicesList(6, rasterIndicesHLC_1305);
		rasterIndices_1305At07 = getRasterIndicesList(7, rasterIndicesHLC_1305);
		cornerMaximaNrs_1305 = Arrays.asList(cornerMaximaNrsHLC_1305);

		rasterIndices_1306At00 = getRasterIndicesList(0, rasterIndicesHLC_1306);
		rasterIndices_1306At01 = getRasterIndicesList(1, rasterIndicesHLC_1306);
		rasterIndices_1306At02 = getRasterIndicesList(2, rasterIndicesHLC_1306);
		rasterIndices_1306At03 = getRasterIndicesList(3, rasterIndicesHLC_1306);
		rasterIndices_1306At04 = getRasterIndicesList(4, rasterIndicesHLC_1306);
		rasterIndices_1306At05 = getRasterIndicesList(5, rasterIndicesHLC_1306);
		rasterIndices_1306At06 = getRasterIndicesList(6, rasterIndicesHLC_1306);
		rasterIndices_1306At07 = getRasterIndicesList(7, rasterIndicesHLC_1306);
		cornerMaximaNrs_1306 = Arrays.asList(cornerMaximaNrsHLC_1306);
		
		rasterIndices_1307At00 = getRasterIndicesList(0, rasterIndicesHLC_1307);
		rasterIndices_1307At01 = getRasterIndicesList(1, rasterIndicesHLC_1307);
		rasterIndices_1307At02 = getRasterIndicesList(2, rasterIndicesHLC_1307);
		rasterIndices_1307At03 = getRasterIndicesList(3, rasterIndicesHLC_1307);
		rasterIndices_1307At04 = getRasterIndicesList(4, rasterIndicesHLC_1307);
		rasterIndices_1307At05 = getRasterIndicesList(5, rasterIndicesHLC_1307);
		rasterIndices_1307At06 = getRasterIndicesList(6, rasterIndicesHLC_1307);
		rasterIndices_1307At07 = getRasterIndicesList(7, rasterIndicesHLC_1307);
		cornerMaximaNrs_1307 = Arrays.asList(cornerMaximaNrsHLC_1307);
		
	}
	
	static SubHLC getInstance() {
		if (instance == null) {
			instance = new HLCfromNorthToSouth();
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
		case 72:
			ends = endAt72; break;
		case 73:
			ends = endAt73; break;
		case 74:
			ends = endAt74; break;
		case 75:
			ends = endAt75; break;
		case 76:
			ends = endAt76; break;
		case 77:
			ends = endAt77; break;
		case 78:
			ends = endAt78; break;
		case 79:
			ends = endAt79; break;
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
		case 72:
			ends = endAt72; break;
		case 73:
			ends = endAt73; break;
		case 74:
			ends = endAt74; break;
		case 75:
			ends = endAt75; break;
		case 76:
			ends = endAt76; break;
		case 77:
			ends = endAt77; break;
		case 78:
			ends = endAt78; break;
		case 79:
			ends = endAt79; break;
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
			case 1000: result = rasterIndices_1000At00; break;
			case 1011: result = rasterIndices_1011At00; break;
			case 1012: result = rasterIndices_1012At00; break;
			case 1013: result = rasterIndices_1013At00; break;
			case 1031: result = rasterIndices_1031At00; break;
			case 1032: result = rasterIndices_1032At00; break;
			case 1033: result = rasterIndices_1033At00; break;
			case 1101: result = rasterIndices_1101At00; break;
			case 1301: result = rasterIndices_1301At00; break;
			case 1302: result = rasterIndices_1302At00; break;
			case 1303: result = rasterIndices_1303At00; break;
			case 1304: result = rasterIndices_1304At00; break;
			case 1305: result = rasterIndices_1305At00; break;
			case 1306: result = rasterIndices_1306At00; break;
			case 1307: result = rasterIndices_1307At00; break;
			}
		case 1:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At01; break;
			case 1011: result = rasterIndices_1011At01; break;
			case 1012: result = rasterIndices_1012At01; break;
			case 1013: result = rasterIndices_1013At01; break;
			case 1021: result = rasterIndices_1021At01; break;
			case 1022: result = rasterIndices_1022At01; break;
			case 1023: result = rasterIndices_1023At01; break;
			case 1031: result = rasterIndices_1031At01; break;
			case 1032: result = rasterIndices_1032At01; break;
			case 1033: result = rasterIndices_1033At01; break;
			case 1101: result = rasterIndices_1101At01; break;
			case 1201: result = rasterIndices_1201At01; break;
			case 1202: result = rasterIndices_1202At01; break;
			case 1203: result = rasterIndices_1203At01; break;
			case 1204: result = rasterIndices_1204At01; break;
			case 1205: result = rasterIndices_1205At01; break;
			case 1206: result = rasterIndices_1206At01; break;
			case 1207: result = rasterIndices_1207At01; break;
			case 1301: result = rasterIndices_1301At01; break;
			case 1302: result = rasterIndices_1302At01; break;
			case 1303: result = rasterIndices_1303At01; break;
			case 1304: result = rasterIndices_1304At01; break;
			case 1305: result = rasterIndices_1305At01; break;
			case 1306: result = rasterIndices_1306At01; break;
			case 1307: result = rasterIndices_1307At01; break;
			}
		case 2:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At02; break;
			case 1011: result = rasterIndices_1011At02; break;
			case 1012: result = rasterIndices_1012At02; break;
			case 1013: result = rasterIndices_1013At02; break;
			case 1021: result = rasterIndices_1021At02; break;
			case 1022: result = rasterIndices_1022At02; break;
			case 1023: result = rasterIndices_1023At02; break;
			case 1031: result = rasterIndices_1031At02; break;
			case 1032: result = rasterIndices_1032At02; break;
			case 1033: result = rasterIndices_1033At02; break;
			case 1041: result = rasterIndices_1041At02; break;
			case 1042: result = rasterIndices_1042At02; break;
			case 1043: result = rasterIndices_1043At02; break;
			case 1101: result = rasterIndices_1101At02; break;
			case 1201: result = rasterIndices_1201At02; break;
			case 1202: result = rasterIndices_1202At02; break;
			case 1203: result = rasterIndices_1203At02; break;
			case 1204: result = rasterIndices_1204At02; break;
			case 1205: result = rasterIndices_1205At02; break;
			case 1206: result = rasterIndices_1206At02; break;
			case 1207: result = rasterIndices_1207At02; break;
			case 1301: result = rasterIndices_1301At02; break;
			case 1302: result = rasterIndices_1302At02; break;
			case 1303: result = rasterIndices_1303At02; break;
			case 1304: result = rasterIndices_1304At02; break;
			case 1305: result = rasterIndices_1305At02; break;
			case 1306: result = rasterIndices_1306At02; break;
			case 1307: result = rasterIndices_1307At02; break;
			}
		case 3:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At03; break;
			case 1011: result = rasterIndices_1011At03; break;
			case 1012: result = rasterIndices_1012At03; break;
			case 1013: result = rasterIndices_1013At03; break;
			case 1021: result = rasterIndices_1021At03; break;
			case 1022: result = rasterIndices_1022At03; break;
			case 1023: result = rasterIndices_1023At03; break;
			case 1031: result = rasterIndices_1031At03; break;
			case 1032: result = rasterIndices_1032At03; break;
			case 1033: result = rasterIndices_1033At03; break;
			case 1041: result = rasterIndices_1041At03; break;
			case 1042: result = rasterIndices_1042At03; break;
			case 1043: result = rasterIndices_1043At03; break;
			case 1101: result = rasterIndices_1101At03; break;
			case 1111: result = rasterIndices_1111At03; break;
			case 1201: result = rasterIndices_1201At03; break;
			case 1202: result = rasterIndices_1202At03; break;
			case 1203: result = rasterIndices_1203At03; break;
			case 1204: result = rasterIndices_1204At03; break;
			case 1205: result = rasterIndices_1205At03; break;
			case 1206: result = rasterIndices_1206At03; break;
			case 1207: result = rasterIndices_1207At03; break;
			case 1301: result = rasterIndices_1301At03; break;
			case 1302: result = rasterIndices_1302At03; break;
			case 1303: result = rasterIndices_1303At03; break;
			case 1304: result = rasterIndices_1304At03; break;
			case 1305: result = rasterIndices_1305At03; break;
			case 1306: result = rasterIndices_1306At03; break;
			case 1307: result = rasterIndices_1307At03; break;
			}
		case 4:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At04; break;
			case 1011: result = rasterIndices_1011At04; break;
			case 1012: result = rasterIndices_1012At04; break;
			case 1013: result = rasterIndices_1013At04; break;
			case 1021: result = rasterIndices_1021At04; break;
			case 1022: result = rasterIndices_1022At04; break;
			case 1023: result = rasterIndices_1023At04; break;
			case 1031: result = rasterIndices_1031At04; break;
			case 1032: result = rasterIndices_1032At04; break;
			case 1033: result = rasterIndices_1033At04; break;
			case 1041: result = rasterIndices_1041At04; break;
			case 1042: result = rasterIndices_1042At04; break;
			case 1043: result = rasterIndices_1043At04; break;
			case 1101: result = rasterIndices_1101At04; break;
			case 1111: result = rasterIndices_1111At04; break;
			case 1201: result = rasterIndices_1201At04; break;
			case 1202: result = rasterIndices_1202At04; break;
			case 1203: result = rasterIndices_1203At04; break;
			case 1204: result = rasterIndices_1204At04; break;
			case 1205: result = rasterIndices_1205At04; break;
			case 1206: result = rasterIndices_1206At04; break;
			case 1207: result = rasterIndices_1207At04; break;
			case 1301: result = rasterIndices_1301At04; break;
			case 1302: result = rasterIndices_1302At04; break;
			case 1303: result = rasterIndices_1303At04; break;
			case 1304: result = rasterIndices_1304At04; break;
			case 1305: result = rasterIndices_1305At04; break;
			case 1306: result = rasterIndices_1306At04; break;
			case 1307: result = rasterIndices_1307At04; break;
			}
		case 5:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At05; break;
			case 1011: result = rasterIndices_1011At05; break;
			case 1012: result = rasterIndices_1012At05; break;
			case 1013: result = rasterIndices_1013At05; break;
			case 1021: result = rasterIndices_1021At05; break;
			case 1022: result = rasterIndices_1022At05; break;
			case 1023: result = rasterIndices_1023At05; break;
			case 1031: result = rasterIndices_1031At05; break;
			case 1032: result = rasterIndices_1032At05; break;
			case 1033: result = rasterIndices_1033At05; break;
			case 1041: result = rasterIndices_1041At05; break;
			case 1042: result = rasterIndices_1042At05; break;
			case 1043: result = rasterIndices_1043At05; break;
			case 1101: result = rasterIndices_1101At05; break;
			case 1111: result = rasterIndices_1111At05; break;
			case 1201: result = rasterIndices_1201At05; break;
			case 1202: result = rasterIndices_1202At05; break;
			case 1203: result = rasterIndices_1203At05; break;
			case 1204: result = rasterIndices_1204At05; break;
			case 1205: result = rasterIndices_1205At05; break;
			case 1206: result = rasterIndices_1206At05; break;
			case 1207: result = rasterIndices_1207At05; break;
			case 1301: result = rasterIndices_1301At05; break;
			case 1302: result = rasterIndices_1302At05; break;
			case 1303: result = rasterIndices_1303At05; break;
			case 1304: result = rasterIndices_1304At05; break;
			case 1305: result = rasterIndices_1305At05; break;
			case 1306: result = rasterIndices_1306At05; break;
			case 1307: result = rasterIndices_1307At05; break;
			}
		case 6:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At06; break;
			case 1011: result = rasterIndices_1011At06; break;
			case 1012: result = rasterIndices_1012At06; break;
			case 1013: result = rasterIndices_1013At06; break;
			case 1021: result = rasterIndices_1021At06; break;
			case 1022: result = rasterIndices_1022At06; break;
			case 1023: result = rasterIndices_1023At06; break;
			case 1031: result = rasterIndices_1031At06; break;
			case 1032: result = rasterIndices_1032At06; break;
			case 1033: result = rasterIndices_1033At06; break;
			case 1041: result = rasterIndices_1041At06; break;
			case 1042: result = rasterIndices_1042At06; break;
			case 1043: result = rasterIndices_1043At06; break;
			case 1111: result = rasterIndices_1111At06; break;
			case 1201: result = rasterIndices_1201At06; break;
			case 1202: result = rasterIndices_1202At06; break;
			case 1203: result = rasterIndices_1203At06; break;
			case 1204: result = rasterIndices_1204At06; break;
			case 1205: result = rasterIndices_1205At06; break;
			case 1206: result = rasterIndices_1206At06; break;
			case 1207: result = rasterIndices_1207At06; break;
			case 1301: result = rasterIndices_1301At06; break;
			case 1302: result = rasterIndices_1302At06; break;
			case 1303: result = rasterIndices_1303At06; break;
			case 1304: result = rasterIndices_1304At06; break;
			case 1305: result = rasterIndices_1305At06; break;
			case 1306: result = rasterIndices_1306At06; break;
			case 1307: result = rasterIndices_1307At06; break;
			}
		case 7:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At07; break;
			case 1011: result = rasterIndices_1011At07; break;
			case 1012: result = rasterIndices_1012At07; break;
			case 1013: result = rasterIndices_1013At07; break;
			case 1021: result = rasterIndices_1021At07; break;
			case 1022: result = rasterIndices_1022At07; break;
			case 1023: result = rasterIndices_1023At07; break;
			case 1041: result = rasterIndices_1041At07; break;
			case 1042: result = rasterIndices_1042At07; break;
			case 1043: result = rasterIndices_1043At07; break;
			case 1111: result = rasterIndices_1111At07; break;
			case 1201: result = rasterIndices_1201At07; break;
			case 1202: result = rasterIndices_1202At07; break;
			case 1203: result = rasterIndices_1203At07; break;
			case 1204: result = rasterIndices_1204At07; break;
			case 1205: result = rasterIndices_1205At07; break;
			case 1206: result = rasterIndices_1206At07; break;
			case 1207: result = rasterIndices_1207At07; break;
			case 1301: result = rasterIndices_1301At07; break;
			case 1302: result = rasterIndices_1302At07; break;
			case 1303: result = rasterIndices_1303At07; break;
			case 1304: result = rasterIndices_1304At07; break;
			case 1305: result = rasterIndices_1305At07; break;
			case 1306: result = rasterIndices_1306At07; break;
			case 1307: result = rasterIndices_1307At07; break;
			}
		case 8:
			switch (nrHLC) {
			case 1000: result = rasterIndices_1000At08; break;
			case 1021: result = rasterIndices_1021At08; break;
			case 1022: result = rasterIndices_1022At08; break;
			case 1023: result = rasterIndices_1023At08; break;
			case 1041: result = rasterIndices_1041At08; break;
			case 1042: result = rasterIndices_1042At08; break;
			case 1043: result = rasterIndices_1043At08; break;
			case 1111: result = rasterIndices_1111At08; break;
			case 1201: result = rasterIndices_1201At08; break;
			case 1202: result = rasterIndices_1202At08; break;
			case 1203: result = rasterIndices_1203At08; break;
			case 1204: result = rasterIndices_1204At08; break;
			case 1205: result = rasterIndices_1205At08; break;
			case 1206: result = rasterIndices_1206At08; break;
			case 1207: result = rasterIndices_1207At08; break;
			}
		
		default:
			result = new ArrayList<Integer>();
		}
		
		return result;
	}

	private List<Integer> getHLCCornerMaximaNrs(int nrHLC) {
		List<Integer> result;
		switch (nrHLC) {
		case 1000: result = cornerMaximaNrs_1000; break;
		case 1011: result = cornerMaximaNrs_1011; break;
		case 1012: result = cornerMaximaNrs_1012; break;
		case 1013: result = cornerMaximaNrs_1013; break;
		case 1021: result = cornerMaximaNrs_1021; break;
		case 1022: result = cornerMaximaNrs_1022; break;
		case 1023: result = cornerMaximaNrs_1023; break;
		case 1031: result = cornerMaximaNrs_1031; break;
		case 1032: result = cornerMaximaNrs_1032; break;
		case 1033: result = cornerMaximaNrs_1033; break;
		case 1041: result = cornerMaximaNrs_1041; break;
		case 1042: result = cornerMaximaNrs_1042; break;
		case 1043: result = cornerMaximaNrs_1043; break;
		case 1101: result = cornerMaximaNrs_1101; break;
		case 1111: result = cornerMaximaNrs_1111; break;
		case 1201: result = cornerMaximaNrs_1201; break;
		case 1202: result = cornerMaximaNrs_1202; break;
		case 1203: result = cornerMaximaNrs_1203; break;
		case 1204: result = cornerMaximaNrs_1204; break;
		case 1205: result = cornerMaximaNrs_1205; break;
		case 1206: result = cornerMaximaNrs_1206; break;
		case 1207: result = cornerMaximaNrs_1207; break;
		case 1301: result = cornerMaximaNrs_1301; break;
		case 1302: result = cornerMaximaNrs_1302; break;
		case 1303: result = cornerMaximaNrs_1303; break;
		case 1304: result = cornerMaximaNrs_1304; break;
		case 1305: result = cornerMaximaNrs_1305; break;
		case 1306: result = cornerMaximaNrs_1306; break;
		case 1307: result = cornerMaximaNrs_1307; break;
		default: result = new ArrayList<Integer>();
		}
		return result;
	}

}
