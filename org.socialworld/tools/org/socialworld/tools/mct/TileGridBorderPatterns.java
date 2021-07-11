package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileGridBorderPatterns {

	private static final List<int[]> corner_0_Patterns = new ArrayList<int[]>(13000);
	private static List<Integer> corner_0_PatternsIndices;
	
	private Random random = new Random();
	
	private int firstIndexCorner0North_0_0;
	private int firstIndexCorner0North_0_1;
	private int firstIndexCorner0North_0_2;
	private int firstIndexCorner0North_0_3;
	private int firstIndexCorner0North_0_4;
	private int firstIndexCorner0North_0_5;
	private int firstIndexCorner0North_0_6;
	private int firstIndexCorner0North_0_7;
	private int firstIndexCorner0North_1_0;
	private int firstIndexCorner0North_2_0;
	private int firstIndexCorner0North_3_0;
	private int firstIndexCorner0North_4_0;
	private int firstIndexCorner0North_5_0;
	private int firstIndexCorner0North_6_0;
	private int firstIndexCorner0North_7_0;
	
	private int firstIndexCorner0South_0_0;
	private int firstIndexCorner0South_0_1;
	private int firstIndexCorner0South_0_2;
	private int firstIndexCorner0South_0_3;
	private int firstIndexCorner0South_0_4;
	private int firstIndexCorner0South_0_5;
	private int firstIndexCorner0South_0_6;
	private int firstIndexCorner0South_0_7;
	private int firstIndexCorner0South_1_0;
	private int firstIndexCorner0South_2_0;
	private int firstIndexCorner0South_3_0;
	private int firstIndexCorner0South_4_0;
	private int firstIndexCorner0South_5_0;
	private int firstIndexCorner0South_6_0;
	private int firstIndexCorner0South_7_0;

	private int firstIndexCorner0West_0_0;
	private int firstIndexCorner0West_0_1;
	private int firstIndexCorner0West_0_2;
	private int firstIndexCorner0West_0_3;
	private int firstIndexCorner0West_0_4;
	private int firstIndexCorner0West_0_5;
	private int firstIndexCorner0West_0_6;
	private int firstIndexCorner0West_0_7;
	private int firstIndexCorner0West_1_0;
	private int firstIndexCorner0West_2_0;
	private int firstIndexCorner0West_3_0;
	private int firstIndexCorner0West_4_0;
	private int firstIndexCorner0West_5_0;
	private int firstIndexCorner0West_6_0;
	private int firstIndexCorner0West_7_0;
	
	private int firstIndexCorner0East_0_0;
	private int firstIndexCorner0East_0_1;
	private int firstIndexCorner0East_0_2;
	private int firstIndexCorner0East_0_3;
	private int firstIndexCorner0East_0_4;
	private int firstIndexCorner0East_0_5;
	private int firstIndexCorner0East_0_6;
	private int firstIndexCorner0East_0_7;
	private int firstIndexCorner0East_1_0;
	private int firstIndexCorner0East_2_0;
	private int firstIndexCorner0East_3_0;
	private int firstIndexCorner0East_4_0;
	private int firstIndexCorner0East_5_0;
	private int firstIndexCorner0East_6_0;
	private int firstIndexCorner0East_7_0;
	
	
	static TileGridBorderPatterns instance;
	
	TileGridBorderPatterns getInstance() {
		if (instance == null) {
			instance = new TileGridBorderPatterns();
		}
		return instance;
	}
	
	private TileGridBorderPatterns() {
		
		corner_0_PatternsIndices = new ArrayList<Integer>();

		/*********** north 0 -> 0 ***********/
		
		firstIndexCorner0North_0_0 = 0;	
		
		corner_0_Patterns.set(0, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(0);
		corner_0_Patterns.set(2, new int[] {0,1,3,2,0,0,0,0,0});corner_0_PatternsIndices.add(2);
		corner_0_Patterns.set(3, new int[] {0,0,1,3,2,0,0,0,0});corner_0_PatternsIndices.add(3);
		corner_0_Patterns.set(4, new int[] {0,0,0,1,3,2,0,0,0});corner_0_PatternsIndices.add(4);
		corner_0_Patterns.set(5, new int[] {0,0,0,0,1,3,2,0,0});corner_0_PatternsIndices.add(5);
		corner_0_Patterns.set(6, new int[] {0,0,0,0,0,1,3,2,0});corner_0_PatternsIndices.add(6);
		corner_0_Patterns.set(12, new int[] {0,1,3,3,3,3,3,2,0});corner_0_PatternsIndices.add(12);
		corner_0_Patterns.set(13, new int[] {0,0,1,3,3,3,2,0,0});corner_0_PatternsIndices.add(13);
		corner_0_Patterns.set(22, new int[] {0,1,3,2,0,1,3,2,0});corner_0_PatternsIndices.add(22);
		corner_0_Patterns.set(23, new int[] {0,0,1,3,2,1,3,2,0});corner_0_PatternsIndices.add(23);
		corner_0_Patterns.set(24, new int[] {0,1,3,2,1,3,2,0,0});corner_0_PatternsIndices.add(24);
		corner_0_Patterns.set(102, new int[] {0,1,18,17,2,0,0,0,0});corner_0_PatternsIndices.add(102);
		corner_0_Patterns.set(103, new int[] {0,0,1,18,17,2,0,0,0});corner_0_PatternsIndices.add(103);
		corner_0_Patterns.set(104, new int[] {0,0,0,1,18,17,2,0,0});corner_0_PatternsIndices.add(104);
		corner_0_Patterns.set(105, new int[] {0,0,0,0,1,18,17,2,0});corner_0_PatternsIndices.add(105);
		corner_0_Patterns.set(112, new int[] {0,1,18,3,17,2,0,0,0});corner_0_PatternsIndices.add(112);
		corner_0_Patterns.set(113, new int[] {0,0,1,18,3,17,2,0,0});corner_0_PatternsIndices.add(113);
		corner_0_Patterns.set(114, new int[] {0,0,0,1,18,3,17,2,0});corner_0_PatternsIndices.add(114);
		corner_0_Patterns.set(122, new int[] {0,1,18,3,3,3,17,2,0});corner_0_PatternsIndices.add(122);
		corner_0_Patterns.set(133, new int[] {0,0,1,18,3,17,2,0,0});corner_0_PatternsIndices.add(133);
		corner_0_Patterns.set(202, new int[] {0,1,18,18,17,17,2,0,0});corner_0_PatternsIndices.add(202);
		corner_0_Patterns.set(203, new int[] {0,0,1,18,18,17,17,2,0});corner_0_PatternsIndices.add(203);
		corner_0_Patterns.set(212, new int[] {0,1,18,18,3,17,17,2,0});corner_0_PatternsIndices.add(212);

		
		/*********** north 0 -> n ***********/
		
		firstIndexCorner0North_0_1 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1003, new int[] {0,0,0,0,0,0,4,13,0});corner_0_PatternsIndices.add(1003);
		corner_0_Patterns.set(1004, new int[] {0,0,0,0,0,4,13,0,0});corner_0_PatternsIndices.add(1004);
		corner_0_Patterns.set(1005, new int[] {0,0,0,0,4,13,0,0,0});corner_0_PatternsIndices.add(1005);
		corner_0_Patterns.set(1006, new int[] {0,0,0,4,13,0,0,0,0});corner_0_PatternsIndices.add(1006);
		corner_0_Patterns.set(1007, new int[] {0,0,4,13,0,0,0,0,0});corner_0_PatternsIndices.add(1007);
		corner_0_Patterns.set(1008, new int[] {0,4,13,0,0,0,0,0,0});corner_0_PatternsIndices.add(1008);
		corner_0_Patterns.set(1012, new int[] {0,0,0,0,0,0,0,5,0});corner_0_PatternsIndices.add(1012);
		corner_0_Patterns.set(1013, new int[] {0,0,0,0,0,0,5,0,0});corner_0_PatternsIndices.add(1013);
		corner_0_Patterns.set(1014, new int[] {0,0,0,0,0,5,0,0,0});corner_0_PatternsIndices.add(1014);
		corner_0_Patterns.set(1015, new int[] {0,0,0,0,5,0,0,0,0});corner_0_PatternsIndices.add(1015);
		corner_0_Patterns.set(1016, new int[] {0,0,0,5,0,0,0,0,0});corner_0_PatternsIndices.add(1016);
		corner_0_Patterns.set(1017, new int[] {0,0,5,0,0,0,0,0,0});corner_0_PatternsIndices.add(1017);
		corner_0_Patterns.set(1018, new int[] {0,5,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(1018);
		corner_0_Patterns.set(1022, new int[] {0,0,0,0,0,0,1,7,0});corner_0_PatternsIndices.add(1022);
		corner_0_Patterns.set(1023, new int[] {0,0,0,0,0,1,7,0,0});corner_0_PatternsIndices.add(1023);
		corner_0_Patterns.set(1024, new int[] {0,0,0,0,1,7,0,0,0});corner_0_PatternsIndices.add(1024);
		corner_0_Patterns.set(1025, new int[] {0,0,0,1,7,0,0,0,0});corner_0_PatternsIndices.add(1025);
		corner_0_Patterns.set(1026, new int[] {0,0,1,7,0,0,0,0,0});corner_0_PatternsIndices.add(1026);
		corner_0_Patterns.set(1027, new int[] {0,1,7,0,0,0,0,0,0});corner_0_PatternsIndices.add(1027);

		firstIndexCorner0North_0_2 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1103, new int[] {0,0,0,0,0,4,16,13,0});corner_0_PatternsIndices.add(1103);
		corner_0_Patterns.set(1104, new int[] {0,0,0,0,4,16,13,0,0});corner_0_PatternsIndices.add(1104);
		corner_0_Patterns.set(1105, new int[] {0,0,0,4,16,13,0,0,0});corner_0_PatternsIndices.add(1105);
		corner_0_Patterns.set(1106, new int[] {0,0,4,16,13,0,0,0,0});corner_0_PatternsIndices.add(1106);
		corner_0_Patterns.set(1107, new int[] {0,4,16,13,0,0,0,0,0});corner_0_PatternsIndices.add(1107);
		corner_0_Patterns.set(1112, new int[] {0,0,0,0,0,0,5,5,0});corner_0_PatternsIndices.add(1112);
		corner_0_Patterns.set(1113, new int[] {0,0,0,0,0,5,5,0,0});corner_0_PatternsIndices.add(1113);
		corner_0_Patterns.set(1114, new int[] {0,0,0,0,5,5,0,0,0});corner_0_PatternsIndices.add(1114);
		corner_0_Patterns.set(1115, new int[] {0,0,0,5,5,0,0,0,0});corner_0_PatternsIndices.add(1115);
		corner_0_Patterns.set(1116, new int[] {0,0,5,5,0,0,0,0,0});corner_0_PatternsIndices.add(1116);
		corner_0_Patterns.set(1117, new int[] {0,5,5,0,0,0,0,0,0});corner_0_PatternsIndices.add(1117);
		corner_0_Patterns.set(1122, new int[] {0,0,0,0,0,1,18,7,0});corner_0_PatternsIndices.add(1122);
		corner_0_Patterns.set(1123, new int[] {0,0,0,0,1,18,7,0,0});corner_0_PatternsIndices.add(1123);
		corner_0_Patterns.set(1124, new int[] {0,0,0,1,18,7,0,0,0});corner_0_PatternsIndices.add(1124);
		corner_0_Patterns.set(1125, new int[] {0,0,1,18,7,0,0,0,0});corner_0_PatternsIndices.add(1125);
		corner_0_Patterns.set(1126, new int[] {0,1,18,7,0,0,0,0,0});corner_0_PatternsIndices.add(1126);

		firstIndexCorner0North_0_3 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1203, new int[] {0,0,0,0,4,16,16,13,0});corner_0_PatternsIndices.add(1203);
		corner_0_Patterns.set(1204, new int[] {0,0,0,4,16,16,13,0,0});corner_0_PatternsIndices.add(1204);
		corner_0_Patterns.set(1205, new int[] {0,0,4,16,16,13,0,0,0});corner_0_PatternsIndices.add(1205);
		corner_0_Patterns.set(1206, new int[] {0,4,16,16,13,0,0,0,0});corner_0_PatternsIndices.add(1206);
		corner_0_Patterns.set(1212, new int[] {0,0,0,0,0,5,5,5,0});corner_0_PatternsIndices.add(1212);
		corner_0_Patterns.set(1213, new int[] {0,0,0,0,5,5,5,0,0});corner_0_PatternsIndices.add(1213);
		corner_0_Patterns.set(1214, new int[] {0,0,0,5,5,5,0,0,0});corner_0_PatternsIndices.add(1214);
		corner_0_Patterns.set(1215, new int[] {0,0,5,5,5,0,0,0,0});corner_0_PatternsIndices.add(1215);
		corner_0_Patterns.set(1216, new int[] {0,5,5,5,0,0,0,0,0});corner_0_PatternsIndices.add(1216);
		corner_0_Patterns.set(1222, new int[] {0,0,0,0,1,18,18,7,0});corner_0_PatternsIndices.add(1222);
		corner_0_Patterns.set(1223, new int[] {0,0,0,1,18,18,7,0,0});corner_0_PatternsIndices.add(1223);
		corner_0_Patterns.set(1224, new int[] {0,0,1,18,18,7,0,0,0});corner_0_PatternsIndices.add(1224);
		corner_0_Patterns.set(1225, new int[] {0,1,18,18,7,0,0,0,0});corner_0_PatternsIndices.add(1225);

		firstIndexCorner0North_0_4 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1303, new int[] {0,0,0,4,16,16,16,13,0});corner_0_PatternsIndices.add(1303);
		corner_0_Patterns.set(1304, new int[] {0,0,4,16,16,16,13,0,0});corner_0_PatternsIndices.add(1304);
		corner_0_Patterns.set(1305, new int[] {0,4,16,16,16,13,0,0,0});corner_0_PatternsIndices.add(1305);
		corner_0_Patterns.set(1312, new int[] {0,0,0,0,5,5,5,5,0});corner_0_PatternsIndices.add(1312);
		corner_0_Patterns.set(1313, new int[] {0,0,0,5,5,5,5,0,0});corner_0_PatternsIndices.add(1313);
		corner_0_Patterns.set(1314, new int[] {0,0,5,5,5,5,0,0,0});corner_0_PatternsIndices.add(1314);
		corner_0_Patterns.set(1322, new int[] {0,0,0,1,18,18,18,7,0});corner_0_PatternsIndices.add(1322);
		corner_0_Patterns.set(1323, new int[] {0,0,1,18,18,18,7,0,0});corner_0_PatternsIndices.add(1323);
		corner_0_Patterns.set(1324, new int[] {0,1,18,18,18,7,0,0,0});corner_0_PatternsIndices.add(1324);

		firstIndexCorner0North_0_5 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1403, new int[] {0,0,4,16,16,16,16,13,0});corner_0_PatternsIndices.add(1403);
		corner_0_Patterns.set(1404, new int[] {0,4,16,16,16,16,13,0,0});corner_0_PatternsIndices.add(1404);
		corner_0_Patterns.set(1412, new int[] {0,0,0,5,5,5,5,5,0});corner_0_PatternsIndices.add(1412);
		corner_0_Patterns.set(1413, new int[] {0,0,5,5,5,5,5,0,0});corner_0_PatternsIndices.add(1413);
		corner_0_Patterns.set(1414, new int[] {0,5,5,5,5,5,0,0,0});corner_0_PatternsIndices.add(1414);
		corner_0_Patterns.set(1422, new int[] {0,0,1,18,18,18,18,7,0});corner_0_PatternsIndices.add(1422);
		corner_0_Patterns.set(1423, new int[] {0,1,18,18,18,18,7,0,0});corner_0_PatternsIndices.add(1423);

		firstIndexCorner0North_0_6 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1503, new int[] {0,4,16,16,16,16,16,13,0});corner_0_PatternsIndices.add(1503);
		corner_0_Patterns.set(1512, new int[] {0,0,5,5,5,5,5,5,0});corner_0_PatternsIndices.add(1512);
		corner_0_Patterns.set(1513, new int[] {0,5,5,5,5,5,5,0,0});corner_0_PatternsIndices.add(1513);
		corner_0_Patterns.set(1522, new int[] {0,1,18,18,18,18,18,7,0});corner_0_PatternsIndices.add(1522);

		firstIndexCorner0North_0_7 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(1612, new int[] {0,5,5,5,5,5,5,5,0});corner_0_PatternsIndices.add(1612);
	
		/*********** north n -> 0 ***********/

		firstIndexCorner0North_1_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2003, new int[] {0,14,8,0,0,0,0,0,0});corner_0_PatternsIndices.add(2003);
		corner_0_Patterns.set(2004, new int[] {0,0,14,8,0,0,0,0,0});corner_0_PatternsIndices.add(2004);
		corner_0_Patterns.set(2005, new int[] {0,0,0,14,8,0,0,0,0});corner_0_PatternsIndices.add(2005);
		corner_0_Patterns.set(2006, new int[] {0,0,0,0,14,8,0,0,0});corner_0_PatternsIndices.add(2006);
		corner_0_Patterns.set(2007, new int[] {0,0,0,0,0,14,8,0,0});corner_0_PatternsIndices.add(2007);
		corner_0_Patterns.set(2008, new int[] {0,0,0,0,0,0,14,8,0});corner_0_PatternsIndices.add(2008);
		corner_0_Patterns.set(2012, new int[] {0,10,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(2012);
		corner_0_Patterns.set(2013, new int[] {0,0,10,0,0,0,0,0,0});corner_0_PatternsIndices.add(2013);
		corner_0_Patterns.set(2014, new int[] {0,0,0,10,0,0,0,0,0});corner_0_PatternsIndices.add(2014);
		corner_0_Patterns.set(2015, new int[] {0,0,0,0,10,0,0,0,0});corner_0_PatternsIndices.add(2015);
		corner_0_Patterns.set(2016, new int[] {0,0,0,0,0,10,0,0,0});corner_0_PatternsIndices.add(2016);
		corner_0_Patterns.set(2017, new int[] {0,0,0,0,0,0,10,0,0});corner_0_PatternsIndices.add(2017);
		corner_0_Patterns.set(2018, new int[] {0,0,0,0,0,0,0,10,0});corner_0_PatternsIndices.add(2018);
		corner_0_Patterns.set(2022, new int[] {0,11,2,0,0,0,0,0,0});corner_0_PatternsIndices.add(2022);
		corner_0_Patterns.set(2023, new int[] {0,0,11,2,0,0,0,0,0});corner_0_PatternsIndices.add(2023);
		corner_0_Patterns.set(2024, new int[] {0,0,0,11,2,0,0,0,0});corner_0_PatternsIndices.add(2024);
		corner_0_Patterns.set(2025, new int[] {0,0,0,0,11,2,0,0,0});corner_0_PatternsIndices.add(2025);
		corner_0_Patterns.set(2026, new int[] {0,0,0,0,0,11,2,0,0});corner_0_PatternsIndices.add(2026);
		corner_0_Patterns.set(2027, new int[] {0,0,0,0,0,0,11,2,0});corner_0_PatternsIndices.add(2027);

		firstIndexCorner0North_2_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2103, new int[] {0,14,19,8,0,0,0,0,0});corner_0_PatternsIndices.add(2103);
		corner_0_Patterns.set(2104, new int[] {0,0,14,19,8,0,0,0,0});corner_0_PatternsIndices.add(2104);
		corner_0_Patterns.set(2105, new int[] {0,0,0,14,19,8,0,0,0});corner_0_PatternsIndices.add(2105);
		corner_0_Patterns.set(2106, new int[] {0,0,0,0,14,19,8,0,0});corner_0_PatternsIndices.add(2106);
		corner_0_Patterns.set(2107, new int[] {0,0,0,0,0,14,19,8,0});corner_0_PatternsIndices.add(2107);
		corner_0_Patterns.set(2112, new int[] {0,10,10,0,0,0,0,0,0});corner_0_PatternsIndices.add(2112);
		corner_0_Patterns.set(2113, new int[] {0,0,10,10,0,0,0,0,0});corner_0_PatternsIndices.add(2113);
		corner_0_Patterns.set(2114, new int[] {0,0,0,10,10,0,0,0,0});corner_0_PatternsIndices.add(2114);
		corner_0_Patterns.set(2115, new int[] {0,0,0,0,10,10,0,0,0});corner_0_PatternsIndices.add(2115);
		corner_0_Patterns.set(2116, new int[] {0,0,0,0,0,10,10,0,0});corner_0_PatternsIndices.add(2116);
		corner_0_Patterns.set(2117, new int[] {0,0,0,0,0,0,10,10,0});corner_0_PatternsIndices.add(2117);
		corner_0_Patterns.set(2122, new int[] {0,11,17,2,0,0,0,0,0});corner_0_PatternsIndices.add(2122);
		corner_0_Patterns.set(2123, new int[] {0,0,11,17,2,0,0,0,0});corner_0_PatternsIndices.add(2123);
		corner_0_Patterns.set(2124, new int[] {0,0,0,11,17,2,0,0,0});corner_0_PatternsIndices.add(2124);
		corner_0_Patterns.set(2125, new int[] {0,0,0,0,11,17,2,0,0});corner_0_PatternsIndices.add(2125);
		corner_0_Patterns.set(2126, new int[] {0,0,0,0,0,11,17,2,0});corner_0_PatternsIndices.add(2126);

		firstIndexCorner0North_3_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2203, new int[] {0,14,19,19,8,0,0,0,0});corner_0_PatternsIndices.add(2203);
		corner_0_Patterns.set(2204, new int[] {0,0,14,19,19,8,0,0,0});corner_0_PatternsIndices.add(2204);
		corner_0_Patterns.set(2205, new int[] {0,0,0,14,19,19,8,0,0});corner_0_PatternsIndices.add(2205);
		corner_0_Patterns.set(2206, new int[] {0,0,0,0,14,19,19,8,0});corner_0_PatternsIndices.add(2206);
		corner_0_Patterns.set(2212, new int[] {0,10,10,10,0,0,0,0,0});corner_0_PatternsIndices.add(2212);
		corner_0_Patterns.set(2213, new int[] {0,0,10,10,10,0,0,0,0});corner_0_PatternsIndices.add(2213);
		corner_0_Patterns.set(2214, new int[] {0,0,0,10,10,10,0,0,0});corner_0_PatternsIndices.add(2214);
		corner_0_Patterns.set(2215, new int[] {0,0,0,0,10,10,10,0,0});corner_0_PatternsIndices.add(2215);
		corner_0_Patterns.set(2216, new int[] {0,0,0,0,0,10,10,10,0});corner_0_PatternsIndices.add(2216);
		corner_0_Patterns.set(2222, new int[] {0,11,17,17,2,0,0,0,0});corner_0_PatternsIndices.add(2222);
		corner_0_Patterns.set(2223, new int[] {0,0,11,17,17,2,0,0,0});corner_0_PatternsIndices.add(2223);
		corner_0_Patterns.set(2224, new int[] {0,0,0,11,17,17,2,0,0});corner_0_PatternsIndices.add(2224);
		corner_0_Patterns.set(2225, new int[] {0,0,0,0,11,17,17,2,0});corner_0_PatternsIndices.add(2225);

		firstIndexCorner0North_4_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2303, new int[] {0,14,19,19,19,8,0,0,0});corner_0_PatternsIndices.add(2303);
		corner_0_Patterns.set(2304, new int[] {0,0,14,19,19,19,8,0,0});corner_0_PatternsIndices.add(2304);
		corner_0_Patterns.set(2305, new int[] {0,0,0,14,19,19,19,8,0});corner_0_PatternsIndices.add(2305);
		corner_0_Patterns.set(2312, new int[] {0,10,10,10,10,0,0,0,0});corner_0_PatternsIndices.add(2312);
		corner_0_Patterns.set(2313, new int[] {0,0,10,10,10,10,0,0,0});corner_0_PatternsIndices.add(2313);
		corner_0_Patterns.set(2314, new int[] {0,0,0,10,10,10,10,0,0});corner_0_PatternsIndices.add(2314);
		corner_0_Patterns.set(2315, new int[] {0,0,0,0,10,10,10,10,0});corner_0_PatternsIndices.add(2315);
		corner_0_Patterns.set(2322, new int[] {0,11,17,17,17,2,0,0,0});corner_0_PatternsIndices.add(2322);
		corner_0_Patterns.set(2323, new int[] {0,0,11,17,17,17,2,0,0});corner_0_PatternsIndices.add(2323);
		corner_0_Patterns.set(2324, new int[] {0,0,0,11,17,17,17,2,0});corner_0_PatternsIndices.add(2324);

		firstIndexCorner0North_5_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2403, new int[] {0,14,19,19,19,19,8,0,0});corner_0_PatternsIndices.add(2403);
		corner_0_Patterns.set(2404, new int[] {0,0,14,19,19,19,19,8,0});corner_0_PatternsIndices.add(2404);
		corner_0_Patterns.set(2412, new int[] {0,10,10,10,10,10,0,0,0});corner_0_PatternsIndices.add(2412);
		corner_0_Patterns.set(2413, new int[] {0,0,10,10,10,10,10,0,0});corner_0_PatternsIndices.add(2413);
		corner_0_Patterns.set(2414, new int[] {0,0,0,10,10,10,10,10,0});corner_0_PatternsIndices.add(2414);
		corner_0_Patterns.set(2422, new int[] {0,11,17,17,17,17,2,0,0});corner_0_PatternsIndices.add(2422);
		corner_0_Patterns.set(2423, new int[] {0,0,11,17,17,17,17,2,0});corner_0_PatternsIndices.add(2423);

		firstIndexCorner0North_6_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2503, new int[] {0,14,19,19,19,19,19,8,0});corner_0_PatternsIndices.add(2503);
		corner_0_Patterns.set(2512, new int[] {0,10,10,10,10,10,10,0,0});corner_0_PatternsIndices.add(2512);
		corner_0_Patterns.set(2513, new int[] {0,0,10,10,10,10,10,10,0});corner_0_PatternsIndices.add(2513);
		corner_0_Patterns.set(2522, new int[] {0,11,17,17,17,17,17,2,0});corner_0_PatternsIndices.add(2522);

		firstIndexCorner0North_7_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(2612, new int[] {0,10,10,10,10,10,10,10,0});corner_0_PatternsIndices.add(2612);

		/*********** south 0 -> 0 ***********/
		
		firstIndexCorner0South_0_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(3000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(3000);
		corner_0_Patterns.set(3002, new int[] {0,4,12,8,0,0,0,0,0});corner_0_PatternsIndices.add(3002);
		corner_0_Patterns.set(3003, new int[] {0,0,4,12,8,0,0,0,0});corner_0_PatternsIndices.add(3003);
		corner_0_Patterns.set(3004, new int[] {0,0,0,4,12,8,0,0,0});corner_0_PatternsIndices.add(3004);
		corner_0_Patterns.set(3005, new int[] {0,0,0,0,4,12,8,0,0});corner_0_PatternsIndices.add(3005);
		corner_0_Patterns.set(3006, new int[] {0,0,0,0,0,4,12,8,0});corner_0_PatternsIndices.add(3006);
		corner_0_Patterns.set(3012, new int[] {0,4,12,12,12,12,12,8,0});corner_0_PatternsIndices.add(3012);
		corner_0_Patterns.set(3013, new int[] {0,0,4,12,12,12,2,0,0});corner_0_PatternsIndices.add(3013);
		corner_0_Patterns.set(3022, new int[] {0,4,12,8,0,4,12,8,0});corner_0_PatternsIndices.add(3022);
		corner_0_Patterns.set(3023, new int[] {0,0,4,12,8,4,12,8,0});corner_0_PatternsIndices.add(3023);
		corner_0_Patterns.set(3024, new int[] {0,4,12,8,4,12,8,0,0});corner_0_PatternsIndices.add(3024);
		corner_0_Patterns.set(3102, new int[] {0,4,16,19,8,0,0,0,0});corner_0_PatternsIndices.add(3102);
		corner_0_Patterns.set(3103, new int[] {0,0,4,16,19,8,0,0,0});corner_0_PatternsIndices.add(3103);
		corner_0_Patterns.set(3104, new int[] {0,0,0,4,16,19,8,0,0});corner_0_PatternsIndices.add(3104);
		corner_0_Patterns.set(3105, new int[] {0,0,0,0,4,16,19,8,0});corner_0_PatternsIndices.add(3105);
		corner_0_Patterns.set(3112, new int[] {0,4,16,12,19,8,0,0,0});corner_0_PatternsIndices.add(3112);
		corner_0_Patterns.set(3113, new int[] {0,0,4,16,12,19,8,0,0});corner_0_PatternsIndices.add(3113);
		corner_0_Patterns.set(3114, new int[] {0,0,0,4,16,12,19,8,0});corner_0_PatternsIndices.add(3114);
		corner_0_Patterns.set(3122, new int[] {0,9,16,12,12,12,19,8,0});corner_0_PatternsIndices.add(3122);
		corner_0_Patterns.set(3133, new int[] {0,0,4,16,12,19,8,0,0});corner_0_PatternsIndices.add(3133);
		corner_0_Patterns.set(3202, new int[] {0,4,16,16,19,19,8,0,0});corner_0_PatternsIndices.add(3202);
		corner_0_Patterns.set(3203, new int[] {0,0,4,16,16,19,19,8,0});corner_0_PatternsIndices.add(3203);
		corner_0_Patterns.set(3212, new int[] {0,4,16,16,12,19,19,8,0});corner_0_PatternsIndices.add(3212);

		/*********** south 0 -> n ***********/

		firstIndexCorner0South_0_1 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4003, new int[] {0,0,0,0,0,0,1,7,0});corner_0_PatternsIndices.add(4003);
		corner_0_Patterns.set(4004, new int[] {0,0,0,0,0,1,7,0,0});corner_0_PatternsIndices.add(4004);
		corner_0_Patterns.set(4005, new int[] {0,0,0,0,1,7,0,0,0});corner_0_PatternsIndices.add(4005);
		corner_0_Patterns.set(4006, new int[] {0,0,0,1,7,0,0,0,0});corner_0_PatternsIndices.add(4006);
		corner_0_Patterns.set(4007, new int[] {0,0,1,7,0,0,0,0,0});corner_0_PatternsIndices.add(4007);
		corner_0_Patterns.set(4008, new int[] {0,1,7,0,0,0,0,0,0});corner_0_PatternsIndices.add(4008);
		corner_0_Patterns.set(4012, new int[] {0,0,0,0,0,0,0,5,0});corner_0_PatternsIndices.add(4012);
		corner_0_Patterns.set(4013, new int[] {0,0,0,0,0,0,5,0,0});corner_0_PatternsIndices.add(4013);
		corner_0_Patterns.set(4014, new int[] {0,0,0,0,0,5,0,0,0});corner_0_PatternsIndices.add(4014);
		corner_0_Patterns.set(4015, new int[] {0,0,0,0,5,0,0,0,0});corner_0_PatternsIndices.add(4015);
		corner_0_Patterns.set(4016, new int[] {0,0,0,5,0,0,0,0,0});corner_0_PatternsIndices.add(4016);
		corner_0_Patterns.set(4017, new int[] {0,0,5,0,0,0,0,0,0});corner_0_PatternsIndices.add(4017);
		corner_0_Patterns.set(4018, new int[] {0,5,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(4018);
		corner_0_Patterns.set(4022, new int[] {0,0,0,0,0,0,4,13,0});corner_0_PatternsIndices.add(4022);
		corner_0_Patterns.set(4023, new int[] {0,0,0,0,0,4,13,0,0});corner_0_PatternsIndices.add(4023);
		corner_0_Patterns.set(4024, new int[] {0,0,0,0,4,13,0,0,0});corner_0_PatternsIndices.add(4024);
		corner_0_Patterns.set(4025, new int[] {0,0,0,4,13,0,0,0,0});corner_0_PatternsIndices.add(4025);
		corner_0_Patterns.set(4026, new int[] {0,0,4,13,0,0,0,0,0});corner_0_PatternsIndices.add(4026);
		corner_0_Patterns.set(4027, new int[] {0,4,13,0,0,0,0,0,0});corner_0_PatternsIndices.add(4027);

		firstIndexCorner0South_0_2 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4103, new int[] {0,0,0,0,0,1,18,7,0});corner_0_PatternsIndices.add(4103);
		corner_0_Patterns.set(4104, new int[] {0,0,0,0,1,18,7,0,0});corner_0_PatternsIndices.add(4104);
		corner_0_Patterns.set(4105, new int[] {0,0,0,1,18,7,0,0,0});corner_0_PatternsIndices.add(4105);
		corner_0_Patterns.set(4106, new int[] {0,0,1,18,7,0,0,0,0});corner_0_PatternsIndices.add(4106);
		corner_0_Patterns.set(4107, new int[] {0,1,18,7,0,0,0,0,0});corner_0_PatternsIndices.add(4107);
		corner_0_Patterns.set(4112, new int[] {0,0,0,0,0,0,5,5,0});corner_0_PatternsIndices.add(4112);
		corner_0_Patterns.set(4113, new int[] {0,0,0,0,0,5,5,0,0});corner_0_PatternsIndices.add(4113);
		corner_0_Patterns.set(4114, new int[] {0,0,0,0,5,5,0,0,0});corner_0_PatternsIndices.add(4114);
		corner_0_Patterns.set(4115, new int[] {0,0,0,5,5,0,0,0,0});corner_0_PatternsIndices.add(4115);
		corner_0_Patterns.set(4116, new int[] {0,0,5,5,0,0,0,0,0});corner_0_PatternsIndices.add(4116);
		corner_0_Patterns.set(4117, new int[] {0,5,5,0,0,0,0,0,0});corner_0_PatternsIndices.add(4117);
		corner_0_Patterns.set(4122, new int[] {0,0,0,0,0,4,16,13,0});corner_0_PatternsIndices.add(4122);
		corner_0_Patterns.set(4123, new int[] {0,0,0,0,4,16,13,0,0});corner_0_PatternsIndices.add(4123);
		corner_0_Patterns.set(4124, new int[] {0,0,0,4,16,13,0,0,0});corner_0_PatternsIndices.add(4124);
		corner_0_Patterns.set(4125, new int[] {0,0,4,16,13,0,0,0,0});corner_0_PatternsIndices.add(4125);
		corner_0_Patterns.set(4126, new int[] {0,4,16,13,0,0,0,0,0});corner_0_PatternsIndices.add(4126);

		firstIndexCorner0South_0_3 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4203, new int[] {0,0,0,0,1,18,18,7,0});corner_0_PatternsIndices.add(4203);
		corner_0_Patterns.set(4204, new int[] {0,0,0,1,18,18,7,0,0});corner_0_PatternsIndices.add(4204);
		corner_0_Patterns.set(4205, new int[] {0,0,1,18,18,7,0,0,0});corner_0_PatternsIndices.add(4205);
		corner_0_Patterns.set(4206, new int[] {0,1,18,18,7,0,0,0,0});corner_0_PatternsIndices.add(4206);
		corner_0_Patterns.set(4212, new int[] {0,0,0,0,0,5,5,5,0});corner_0_PatternsIndices.add(4212);
		corner_0_Patterns.set(4213, new int[] {0,0,0,0,5,5,5,0,0});corner_0_PatternsIndices.add(4213);
		corner_0_Patterns.set(4214, new int[] {0,0,0,5,5,5,0,0,0});corner_0_PatternsIndices.add(4214);
		corner_0_Patterns.set(4215, new int[] {0,0,5,5,5,0,0,0,0});corner_0_PatternsIndices.add(4215);
		corner_0_Patterns.set(4216, new int[] {0,5,5,5,0,0,0,0,0});corner_0_PatternsIndices.add(4216);
		corner_0_Patterns.set(4222, new int[] {0,0,0,0,4,16,16,13,0});corner_0_PatternsIndices.add(4222);
		corner_0_Patterns.set(4223, new int[] {0,0,0,4,16,16,13,0,0});corner_0_PatternsIndices.add(4223);
		corner_0_Patterns.set(4224, new int[] {0,0,4,16,16,13,0,0,0});corner_0_PatternsIndices.add(4224);
		corner_0_Patterns.set(4225, new int[] {0,4,16,16,13,0,0,0,0});corner_0_PatternsIndices.add(4225);

		firstIndexCorner0South_0_4 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4303, new int[] {0,0,0,1,18,18,18,7,0});corner_0_PatternsIndices.add(4303);
		corner_0_Patterns.set(4304, new int[] {0,0,1,18,18,18,7,0,0});corner_0_PatternsIndices.add(4304);
		corner_0_Patterns.set(4305, new int[] {0,1,18,18,18,7,0,0,0});corner_0_PatternsIndices.add(4305);
		corner_0_Patterns.set(4312, new int[] {0,0,0,0,5,5,5,5,0});corner_0_PatternsIndices.add(4312);
		corner_0_Patterns.set(4313, new int[] {0,0,0,5,5,5,5,0,0});corner_0_PatternsIndices.add(4313);
		corner_0_Patterns.set(4314, new int[] {0,0,5,5,5,5,0,0,0});corner_0_PatternsIndices.add(4314);
		corner_0_Patterns.set(4315, new int[] {0,5,5,5,5,0,0,0,0});corner_0_PatternsIndices.add(4315);
		corner_0_Patterns.set(4322, new int[] {0,0,0,4,16,16,16,13,0});corner_0_PatternsIndices.add(4322);
		corner_0_Patterns.set(4323, new int[] {0,0,4,16,16,16,13,0,0});corner_0_PatternsIndices.add(4323);
		corner_0_Patterns.set(4324, new int[] {0,4,16,16,16,13,0,0,0});corner_0_PatternsIndices.add(4324);

		firstIndexCorner0South_0_5 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4403, new int[] {0,0,1,18,18,18,18,7,0});corner_0_PatternsIndices.add(4403);
		corner_0_Patterns.set(4404, new int[] {0,1,18,18,18,18,7,0,0});corner_0_PatternsIndices.add(4404);
		corner_0_Patterns.set(4412, new int[] {0,0,0,5,5,5,5,5,0});corner_0_PatternsIndices.add(4412);
		corner_0_Patterns.set(4413, new int[] {0,0,5,5,5,5,5,0,0});corner_0_PatternsIndices.add(4413);
		corner_0_Patterns.set(4414, new int[] {0,5,5,5,5,5,0,0,0});corner_0_PatternsIndices.add(4414);
		corner_0_Patterns.set(4422, new int[] {0,0,4,16,16,16,16,13,0});corner_0_PatternsIndices.add(4422);
		corner_0_Patterns.set(4423, new int[] {0,4,16,16,16,16,13,0,0});corner_0_PatternsIndices.add(4423);

		firstIndexCorner0South_0_6 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4503, new int[] {0,1,18,18,18,18,18,7,0});corner_0_PatternsIndices.add(4503);
		corner_0_Patterns.set(4512, new int[] {0,0,5,5,5,5,5,5,0});corner_0_PatternsIndices.add(4512);
		corner_0_Patterns.set(4513, new int[] {0,5,5,5,5,5,5,0,0});corner_0_PatternsIndices.add(4513);
		corner_0_Patterns.set(4522, new int[] {0,4,16,16,16,16,16,13,0});corner_0_PatternsIndices.add(4522);

		firstIndexCorner0South_0_7 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(4612, new int[] {0,5,5,5,5,5,5,5,0});corner_0_PatternsIndices.add(4612);

		/*********** south n -> 0 ***********/
		
		firstIndexCorner0South_1_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5003, new int[] {0,11,2,0,0,0,0,0,0});corner_0_PatternsIndices.add(5003);
		corner_0_Patterns.set(5004, new int[] {0,0,11,2,0,0,0,0,0});corner_0_PatternsIndices.add(5004);
		corner_0_Patterns.set(5005, new int[] {0,0,0,11,2,0,0,0,0});corner_0_PatternsIndices.add(5005);
		corner_0_Patterns.set(5006, new int[] {0,0,0,0,11,2,0,0,0});corner_0_PatternsIndices.add(5006);
		corner_0_Patterns.set(5007, new int[] {0,0,0,0,0,11,2,0,0});corner_0_PatternsIndices.add(5007);
		corner_0_Patterns.set(5008, new int[] {0,0,0,0,0,0,11,2,0});corner_0_PatternsIndices.add(5008);
		corner_0_Patterns.set(5012, new int[] {0,10,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(5012);
		corner_0_Patterns.set(5013, new int[] {0,0,10,0,0,0,0,0,0});corner_0_PatternsIndices.add(5013);
		corner_0_Patterns.set(5014, new int[] {0,0,0,10,0,0,0,0,0});corner_0_PatternsIndices.add(5014);
		corner_0_Patterns.set(5015, new int[] {0,0,0,0,10,0,0,0,0});corner_0_PatternsIndices.add(5015);
		corner_0_Patterns.set(5016, new int[] {0,0,0,0,0,10,0,0,0});corner_0_PatternsIndices.add(5016);
		corner_0_Patterns.set(5017, new int[] {0,0,0,0,0,0,10,0,0});corner_0_PatternsIndices.add(5017);
		corner_0_Patterns.set(5018, new int[] {0,0,0,0,0,0,0,10,0});corner_0_PatternsIndices.add(5018);
		corner_0_Patterns.set(5022, new int[] {0,14,8,0,0,0,0,0,0});corner_0_PatternsIndices.add(5022);
		corner_0_Patterns.set(5023, new int[] {0,0,14,8,0,0,0,0,0});corner_0_PatternsIndices.add(5023);
		corner_0_Patterns.set(5024, new int[] {0,0,0,14,8,13,0,0,0});corner_0_PatternsIndices.add(5024);
		corner_0_Patterns.set(5025, new int[] {0,0,0,0,14,8,0,0,0});corner_0_PatternsIndices.add(5025);
		corner_0_Patterns.set(5026, new int[] {0,0,0,0,0,14,8,0,0});corner_0_PatternsIndices.add(5026);
		corner_0_Patterns.set(5027, new int[] {0,0,0,0,0,0,14,8,0});corner_0_PatternsIndices.add(5027);

		firstIndexCorner0South_2_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5103, new int[] {0,11,17,2,0,0,0,0,0});corner_0_PatternsIndices.add(5103);
		corner_0_Patterns.set(5104, new int[] {0,0,11,17,2,0,0,0,0});corner_0_PatternsIndices.add(5104);
		corner_0_Patterns.set(5105, new int[] {0,0,0,11,17,2,0,0,0});corner_0_PatternsIndices.add(5105);
		corner_0_Patterns.set(5106, new int[] {0,0,0,0,11,17,2,0,0});corner_0_PatternsIndices.add(5106);
		corner_0_Patterns.set(5107, new int[] {0,0,0,0,0,11,17,2,0});corner_0_PatternsIndices.add(5107);
		corner_0_Patterns.set(5112, new int[] {0,10,10,0,0,0,0,0,0});corner_0_PatternsIndices.add(5112);
		corner_0_Patterns.set(5113, new int[] {0,0,10,10,0,0,0,0,0});corner_0_PatternsIndices.add(5113);
		corner_0_Patterns.set(5114, new int[] {0,0,0,10,10,0,0,0,0});corner_0_PatternsIndices.add(5114);
		corner_0_Patterns.set(5115, new int[] {0,0,0,0,10,10,0,0,0});corner_0_PatternsIndices.add(5115);
		corner_0_Patterns.set(5116, new int[] {0,0,0,0,0,10,10,0,0});corner_0_PatternsIndices.add(5116);
		corner_0_Patterns.set(5117, new int[] {0,0,0,0,0,0,10,10,0});corner_0_PatternsIndices.add(5117);
		corner_0_Patterns.set(5122, new int[] {0,14,19,8,0,0,0,0,0});corner_0_PatternsIndices.add(5122);
		corner_0_Patterns.set(5123, new int[] {0,0,14,19,8,0,0,0,0});corner_0_PatternsIndices.add(5123);
		corner_0_Patterns.set(5124, new int[] {0,0,0,14,19,8,0,0,0});corner_0_PatternsIndices.add(5124);
		corner_0_Patterns.set(5125, new int[] {0,0,0,0,14,19,8,0,0});corner_0_PatternsIndices.add(5125);
		corner_0_Patterns.set(5126, new int[] {0,0,0,0,0,14,19,8,0});corner_0_PatternsIndices.add(5126);

		firstIndexCorner0South_3_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5203, new int[] {0,11,17,17,2,0,0,0,0});corner_0_PatternsIndices.add(5203);
		corner_0_Patterns.set(5204, new int[] {0,0,11,17,17,2,0,0,0});corner_0_PatternsIndices.add(5204);
		corner_0_Patterns.set(5205, new int[] {0,0,0,11,17,17,2,0,0});corner_0_PatternsIndices.add(5205);
		corner_0_Patterns.set(5206, new int[] {0,0,0,0,11,17,17,2,0});corner_0_PatternsIndices.add(5206);
		corner_0_Patterns.set(5212, new int[] {0,10,10,10,0,0,0,0,0});corner_0_PatternsIndices.add(5212);
		corner_0_Patterns.set(5213, new int[] {0,0,10,10,10,0,0,0,0});corner_0_PatternsIndices.add(5213);
		corner_0_Patterns.set(5214, new int[] {0,0,0,10,10,10,0,0,0});corner_0_PatternsIndices.add(5214);
		corner_0_Patterns.set(5215, new int[] {0,0,0,0,10,10,10,0,0});corner_0_PatternsIndices.add(5215);
		corner_0_Patterns.set(5216, new int[] {0,0,0,0,0,10,10,10,0});corner_0_PatternsIndices.add(5216);
		corner_0_Patterns.set(5222, new int[] {0,14,19,19,8,0,0,0,0});corner_0_PatternsIndices.add(5222);
		corner_0_Patterns.set(5223, new int[] {0,0,14,19,19,8,0,0,0});corner_0_PatternsIndices.add(5223);
		corner_0_Patterns.set(5224, new int[] {0,0,0,14,19,19,8,0,0});corner_0_PatternsIndices.add(5224);
		corner_0_Patterns.set(5225, new int[] {0,0,0,0,14,19,19,8,0});corner_0_PatternsIndices.add(5225);

		firstIndexCorner0South_4_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5303, new int[] {0,11,17,17,17,2,0,0,0});corner_0_PatternsIndices.add(5303);
		corner_0_Patterns.set(5304, new int[] {0,0,11,17,17,17,2,0,0});corner_0_PatternsIndices.add(5304);
		corner_0_Patterns.set(5305, new int[] {0,0,0,11,17,17,17,2,0});corner_0_PatternsIndices.add(5305);
		corner_0_Patterns.set(5312, new int[] {0,10,10,10,10,0,0,0,0});corner_0_PatternsIndices.add(5312);
		corner_0_Patterns.set(5313, new int[] {0,0,10,10,10,10,0,0,0});corner_0_PatternsIndices.add(5313);
		corner_0_Patterns.set(5314, new int[] {0,0,0,10,10,10,10,0,0});corner_0_PatternsIndices.add(5314);
		corner_0_Patterns.set(5315, new int[] {0,0,0,0,10,10,10,10,0});corner_0_PatternsIndices.add(5315);
		corner_0_Patterns.set(5322, new int[] {0,14,19,19,19,8,0,0,0});corner_0_PatternsIndices.add(5322);
		corner_0_Patterns.set(5323, new int[] {0,0,14,19,19,19,8,0,0});corner_0_PatternsIndices.add(5323);
		corner_0_Patterns.set(5324, new int[] {0,0,0,14,19,19,19,8,0});corner_0_PatternsIndices.add(5324);

		firstIndexCorner0South_5_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5403, new int[] {0,11,17,17,17,17,2,0,0});corner_0_PatternsIndices.add(5403);
		corner_0_Patterns.set(5404, new int[] {0,0,11,17,17,17,17,2,0});corner_0_PatternsIndices.add(5404);
		corner_0_Patterns.set(5412, new int[] {0,10,10,10,10,10,0,0,0});corner_0_PatternsIndices.add(5412);
		corner_0_Patterns.set(5413, new int[] {0,0,10,10,10,10,10,0,0});corner_0_PatternsIndices.add(5413);
		corner_0_Patterns.set(5414, new int[] {0,0,0,10,10,10,10,10,0});corner_0_PatternsIndices.add(5414);
		corner_0_Patterns.set(5422, new int[] {0,14,19,19,19,19,8,0,0});corner_0_PatternsIndices.add(5422);
		corner_0_Patterns.set(5423, new int[] {0,0,14,19,19,19,19,8,0});corner_0_PatternsIndices.add(5423);
		
		firstIndexCorner0South_6_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5503, new int[] {0,11,17,17,17,17,17,2,0});corner_0_PatternsIndices.add(5503);
		corner_0_Patterns.set(5512, new int[] {0,10,10,10,10,10,10,0,0});corner_0_PatternsIndices.add(5512);
		corner_0_Patterns.set(5513, new int[] {0,0,10,10,10,10,10,10,0});corner_0_PatternsIndices.add(5513);
		corner_0_Patterns.set(5522, new int[] {0,14,19,19,19,19,19,8,0});corner_0_PatternsIndices.add(5522);

		firstIndexCorner0South_7_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(5612, new int[] {0,10,10,10,10,10,10,10,0});corner_0_PatternsIndices.add(5612);

		/*********** west 0 -> 0 ***********/
		
		firstIndexCorner0West_0_0 = corner_0_PatternsIndices.size();
		
		corner_0_Patterns.set(6000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(6000);
		corner_0_Patterns.set(6002, new int[] {0,1,5,4,0,0,0,0,0});corner_0_PatternsIndices.add(6002);
		corner_0_Patterns.set(6003, new int[] {0,0,1,5,4,0,0,0,0});corner_0_PatternsIndices.add(6003);
		corner_0_Patterns.set(6004, new int[] {0,0,0,1,5,4,0,0,0});corner_0_PatternsIndices.add(6004);
		corner_0_Patterns.set(6005, new int[] {0,0,0,0,1,5,4,0,0});corner_0_PatternsIndices.add(6005);
		corner_0_Patterns.set(6006, new int[] {0,0,0,0,0,1,5,4,0});corner_0_PatternsIndices.add(6006);
		corner_0_Patterns.set(6012, new int[] {0,1,5,5,5,5,5,4,0});corner_0_PatternsIndices.add(6012);
		corner_0_Patterns.set(6013, new int[] {0,0,1,5,5,5,4,0,0});corner_0_PatternsIndices.add(6013);
		corner_0_Patterns.set(6022, new int[] {0,1,5,4,0,1,5,4,0});corner_0_PatternsIndices.add(6022);
		corner_0_Patterns.set(6023, new int[] {0,0,1,5,4,1,5,4,0});corner_0_PatternsIndices.add(6023);
		corner_0_Patterns.set(6024, new int[] {0,1,5,4,1,5,4,0,0});corner_0_PatternsIndices.add(6024);
		corner_0_Patterns.set(6102, new int[] {0,1,18,16,4,0,0,0,0});corner_0_PatternsIndices.add(6102);
		corner_0_Patterns.set(6103, new int[] {0,0,1,18,16,4,0,0,0});corner_0_PatternsIndices.add(6103);
		corner_0_Patterns.set(6104, new int[] {0,0,0,1,18,16,4,0,0});corner_0_PatternsIndices.add(6104);
		corner_0_Patterns.set(6105, new int[] {0,0,0,0,1,18,16,4,0});corner_0_PatternsIndices.add(6105);
		corner_0_Patterns.set(6112, new int[] {0,1,18,5,16,4,0,0,0});corner_0_PatternsIndices.add(6112);
		corner_0_Patterns.set(6113, new int[] {0,0,1,18,5,16,4,0,0});corner_0_PatternsIndices.add(6113);
		corner_0_Patterns.set(6114, new int[] {0,0,0,1,18,5,16,4,0});corner_0_PatternsIndices.add(6114);
		corner_0_Patterns.set(6122, new int[] {0,1,18,5,5,5,16,4,0});corner_0_PatternsIndices.add(6122);
		corner_0_Patterns.set(6133, new int[] {0,0,1,18,5,16,4,0,0});corner_0_PatternsIndices.add(6133);
		corner_0_Patterns.set(6202, new int[] {0,1,18,18,16,16,4,0,0});corner_0_PatternsIndices.add(6202);
		corner_0_Patterns.set(6203, new int[] {0,0,1,18,18,16,16,4,0});corner_0_PatternsIndices.add(6203);
		corner_0_Patterns.set(6212, new int[] {0,1,18,18,5,16,16,4,0});corner_0_PatternsIndices.add(6212);

		/*********** west 0 -> n ***********/
		
		firstIndexCorner0West_0_1 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7003, new int[] {0,0,0,0,0,0,2,11,0});corner_0_PatternsIndices.add(7003);
		corner_0_Patterns.set(7004, new int[] {0,0,0,0,0,2,11,0,0});corner_0_PatternsIndices.add(7004);
		corner_0_Patterns.set(7005, new int[] {0,0,0,0,2,11,0,0,0});corner_0_PatternsIndices.add(7005);
		corner_0_Patterns.set(7006, new int[] {0,0,0,2,11,0,0,0,0});corner_0_PatternsIndices.add(7006);
		corner_0_Patterns.set(7007, new int[] {0,0,2,11,0,0,0,0,0});corner_0_PatternsIndices.add(7007);
		corner_0_Patterns.set(7008, new int[] {0,2,11,0,0,0,0,0,0});corner_0_PatternsIndices.add(7008);
		corner_0_Patterns.set(7012, new int[] {0,0,0,0,0,0,0,3,0});corner_0_PatternsIndices.add(7012);
		corner_0_Patterns.set(7013, new int[] {0,0,0,0,0,0,3,0,0});corner_0_PatternsIndices.add(7013);
		corner_0_Patterns.set(7014, new int[] {0,0,0,0,0,3,0,0,0});corner_0_PatternsIndices.add(7014);
		corner_0_Patterns.set(7015, new int[] {0,0,0,0,3,0,0,0,0});corner_0_PatternsIndices.add(7015);
		corner_0_Patterns.set(7016, new int[] {0,0,0,3,0,0,0,0,0});corner_0_PatternsIndices.add(7016);
		corner_0_Patterns.set(7017, new int[] {0,0,3,0,0,0,0,0,0});corner_0_PatternsIndices.add(7017);
		corner_0_Patterns.set(7018, new int[] {0,3,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(7018);
		corner_0_Patterns.set(7022, new int[] {0,0,0,0,0,0,1,7,0});corner_0_PatternsIndices.add(7022);
		corner_0_Patterns.set(7023, new int[] {0,0,0,0,0,1,7,0,0});corner_0_PatternsIndices.add(7023);
		corner_0_Patterns.set(7024, new int[] {0,0,0,0,1,7,0,0,0});corner_0_PatternsIndices.add(7024);
		corner_0_Patterns.set(7025, new int[] {0,0,0,1,7,0,0,0,0});corner_0_PatternsIndices.add(7025);
		corner_0_Patterns.set(7026, new int[] {0,0,1,7,0,0,0,0,0});corner_0_PatternsIndices.add(7026);
		corner_0_Patterns.set(7027, new int[] {0,1,7,0,0,0,0,0,0});corner_0_PatternsIndices.add(7027);

		firstIndexCorner0West_0_2 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7103, new int[] {0,0,0,0,0,2,17,11,0});corner_0_PatternsIndices.add(7103);
		corner_0_Patterns.set(7104, new int[] {0,0,0,0,2,17,11,0,0});corner_0_PatternsIndices.add(7104);
		corner_0_Patterns.set(7105, new int[] {0,0,0,2,17,11,0,0,0});corner_0_PatternsIndices.add(7105);
		corner_0_Patterns.set(7106, new int[] {0,0,2,17,11,0,0,0,0});corner_0_PatternsIndices.add(7106);
		corner_0_Patterns.set(7107, new int[] {0,2,17,11,0,0,0,0,0});corner_0_PatternsIndices.add(7107);
		corner_0_Patterns.set(7112, new int[] {0,0,0,0,0,0,3,3,0});corner_0_PatternsIndices.add(7112);
		corner_0_Patterns.set(7113, new int[] {0,0,0,0,0,3,3,0,0});corner_0_PatternsIndices.add(7113);
		corner_0_Patterns.set(7114, new int[] {0,0,0,0,3,3,0,0,0});corner_0_PatternsIndices.add(7114);
		corner_0_Patterns.set(7115, new int[] {0,0,0,3,3,0,0,0,0});corner_0_PatternsIndices.add(7115);
		corner_0_Patterns.set(7116, new int[] {0,0,3,3,0,0,0,0,0});corner_0_PatternsIndices.add(7116);
		corner_0_Patterns.set(7117, new int[] {0,3,3,0,0,0,0,0,0});corner_0_PatternsIndices.add(7117);
		corner_0_Patterns.set(7122, new int[] {0,0,0,0,0,1,18,7,0});corner_0_PatternsIndices.add(7122);
		corner_0_Patterns.set(7123, new int[] {0,0,0,0,1,18,7,0,0});corner_0_PatternsIndices.add(7123);
		corner_0_Patterns.set(7124, new int[] {0,0,0,1,18,7,0,0,0});corner_0_PatternsIndices.add(7124);
		corner_0_Patterns.set(7125, new int[] {0,0,1,18,7,0,0,0,0});corner_0_PatternsIndices.add(7125);
		corner_0_Patterns.set(7126, new int[] {0,1,18,7,0,0,0,0,0});corner_0_PatternsIndices.add(7126);

		firstIndexCorner0West_0_3 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7203, new int[] {0,0,0,0,2,17,17,11,0});corner_0_PatternsIndices.add(7203);
		corner_0_Patterns.set(7204, new int[] {0,0,0,2,17,17,11,0,0});corner_0_PatternsIndices.add(7204);
		corner_0_Patterns.set(7205, new int[] {0,0,2,17,17,11,0,0,0});corner_0_PatternsIndices.add(7205);
		corner_0_Patterns.set(7206, new int[] {0,2,17,17,11,0,0,0,0});corner_0_PatternsIndices.add(7206);
		corner_0_Patterns.set(7212, new int[] {0,0,0,0,0,3,3,3,0});corner_0_PatternsIndices.add(7212);
		corner_0_Patterns.set(7213, new int[] {0,0,0,0,3,3,3,0,0});corner_0_PatternsIndices.add(7213);
		corner_0_Patterns.set(7214, new int[] {0,0,0,3,3,3,0,0,0});corner_0_PatternsIndices.add(7214);
		corner_0_Patterns.set(7215, new int[] {0,0,3,3,3,0,0,0,0});corner_0_PatternsIndices.add(7215);
		corner_0_Patterns.set(7216, new int[] {0,3,3,3,0,0,0,0,0});corner_0_PatternsIndices.add(7216);
		corner_0_Patterns.set(7222, new int[] {0,0,0,0,1,18,18,7,0});corner_0_PatternsIndices.add(7222);
		corner_0_Patterns.set(7223, new int[] {0,0,0,1,18,18,7,0,0});corner_0_PatternsIndices.add(7223);
		corner_0_Patterns.set(7224, new int[] {0,0,1,18,18,7,0,0,0});corner_0_PatternsIndices.add(7224);
		corner_0_Patterns.set(7225, new int[] {0,1,18,18,7,0,0,0,0});corner_0_PatternsIndices.add(7225);

		firstIndexCorner0West_0_4 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7303, new int[] {0,0,0,2,17,17,17,11,0});corner_0_PatternsIndices.add(7303);
		corner_0_Patterns.set(7304, new int[] {0,0,2,17,17,17,11,0,0});corner_0_PatternsIndices.add(7304);
		corner_0_Patterns.set(7305, new int[] {0,2,17,17,17,11,0,0,0});corner_0_PatternsIndices.add(7305);
		corner_0_Patterns.set(7312, new int[] {0,0,0,0,3,3,3,3,0});corner_0_PatternsIndices.add(7312);
		corner_0_Patterns.set(7313, new int[] {0,0,0,3,3,3,3,0,0});corner_0_PatternsIndices.add(7313);
		corner_0_Patterns.set(7314, new int[] {0,0,3,3,3,3,0,0,0});corner_0_PatternsIndices.add(7314);
		corner_0_Patterns.set(7315, new int[] {0,3,3,3,3,0,0,0,0});corner_0_PatternsIndices.add(7315);
		corner_0_Patterns.set(7322, new int[] {0,0,0,1,18,18,18,7,0});corner_0_PatternsIndices.add(7322);
		corner_0_Patterns.set(7323, new int[] {0,0,1,18,18,18,7,0,0});corner_0_PatternsIndices.add(7323);
		corner_0_Patterns.set(7324, new int[] {0,1,18,18,18,7,0,0,0});corner_0_PatternsIndices.add(7324);

		firstIndexCorner0West_0_5 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7403, new int[] {0,0,2,17,17,17,17,11,0});corner_0_PatternsIndices.add(7403);
		corner_0_Patterns.set(7404, new int[] {0,2,17,17,17,17,11,0,0});corner_0_PatternsIndices.add(7404);
		corner_0_Patterns.set(7412, new int[] {0,0,0,3,3,3,3,3,0});corner_0_PatternsIndices.add(7412);
		corner_0_Patterns.set(7413, new int[] {0,0,3,3,3,3,3,0,0});corner_0_PatternsIndices.add(7413);
		corner_0_Patterns.set(7414, new int[] {0,3,3,3,3,3,0,0,0});corner_0_PatternsIndices.add(7414);
		corner_0_Patterns.set(7422, new int[] {0,0,1,18,18,18,18,7,0});corner_0_PatternsIndices.add(7422);
		corner_0_Patterns.set(7423, new int[] {0,1,18,18,18,18,7,0,0});corner_0_PatternsIndices.add(7423);

		firstIndexCorner0West_0_6 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7503, new int[] {0,2,17,17,17,17,17,11,0});corner_0_PatternsIndices.add(7503);
		corner_0_Patterns.set(7512, new int[] {0,0,3,3,3,3,3,3,0});corner_0_PatternsIndices.add(7512);
		corner_0_Patterns.set(7513, new int[] {0,3,3,3,3,3,3,0,0});corner_0_PatternsIndices.add(7513);
		corner_0_Patterns.set(7522, new int[] {0,1,18,18,18,18,18,7,0});corner_0_PatternsIndices.add(7522);

		firstIndexCorner0West_0_7 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(7612, new int[] {0,3,3,3,3,3,3,3,0});corner_0_PatternsIndices.add(7612);
		
		/*********** west n -> 0 ***********/
		
		firstIndexCorner0West_1_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8003, new int[] {0,13,4,0,0,0,0,0,0});corner_0_PatternsIndices.add(8003);
		corner_0_Patterns.set(8004, new int[] {0,0,13,4,0,0,0,0,0});corner_0_PatternsIndices.add(8004);
		corner_0_Patterns.set(8005, new int[] {0,0,0,13,4,0,0,0,0});corner_0_PatternsIndices.add(8005);
		corner_0_Patterns.set(8006, new int[] {0,0,0,0,13,4,0,0,0});corner_0_PatternsIndices.add(8006);
		corner_0_Patterns.set(8007, new int[] {0,0,0,0,0,13,4,0,0});corner_0_PatternsIndices.add(8007);
		corner_0_Patterns.set(8008, new int[] {0,0,0,0,0,0,13,4,0});corner_0_PatternsIndices.add(8008);
		corner_0_Patterns.set(8012, new int[] {0,12,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(8012);
		corner_0_Patterns.set(8013, new int[] {0,0,12,0,0,0,0,0,0});corner_0_PatternsIndices.add(8013);
		corner_0_Patterns.set(8014, new int[] {0,0,0,12,0,0,0,0,0});corner_0_PatternsIndices.add(8014);
		corner_0_Patterns.set(8015, new int[] {0,0,0,0,12,0,0,0,0});corner_0_PatternsIndices.add(8015);
		corner_0_Patterns.set(8016, new int[] {0,0,0,0,0,12,0,0,0});corner_0_PatternsIndices.add(8016);
		corner_0_Patterns.set(8017, new int[] {0,0,0,0,0,0,12,0,0});corner_0_PatternsIndices.add(8017);
		corner_0_Patterns.set(8018, new int[] {0,0,0,0,0,0,0,12,0});corner_0_PatternsIndices.add(8018);
		corner_0_Patterns.set(8022, new int[] {0,14,8,0,0,0,0,0,0});corner_0_PatternsIndices.add(8022);
		corner_0_Patterns.set(8023, new int[] {0,0,14,8,0,0,0,0,0});corner_0_PatternsIndices.add(8023);
		corner_0_Patterns.set(8024, new int[] {0,0,0,14,8,0,0,0,0});corner_0_PatternsIndices.add(8024);
		corner_0_Patterns.set(8025, new int[] {0,0,0,0,14,8,0,0,0});corner_0_PatternsIndices.add(8025);
		corner_0_Patterns.set(8026, new int[] {0,0,0,0,0,14,8,0,0});corner_0_PatternsIndices.add(8026);
		corner_0_Patterns.set(8027, new int[] {0,0,0,0,0,0,14,8,0});corner_0_PatternsIndices.add(8027);

		firstIndexCorner0West_2_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8103, new int[] {0,13,16,4,0,0,0,0,0});corner_0_PatternsIndices.add(8103);
		corner_0_Patterns.set(8104, new int[] {0,0,13,16,4,0,0,0,0});corner_0_PatternsIndices.add(8104);
		corner_0_Patterns.set(8105, new int[] {0,0,0,13,16,4,0,0,0});corner_0_PatternsIndices.add(8105);
		corner_0_Patterns.set(8106, new int[] {0,0,0,0,13,16,4,0,0});corner_0_PatternsIndices.add(8106);
		corner_0_Patterns.set(8107, new int[] {0,0,0,0,0,13,16,4,0});corner_0_PatternsIndices.add(8107);
		corner_0_Patterns.set(8112, new int[] {0,12,12,0,0,0,0,0,0});corner_0_PatternsIndices.add(8112);
		corner_0_Patterns.set(8113, new int[] {0,0,12,12,0,0,0,0,0});corner_0_PatternsIndices.add(8113);
		corner_0_Patterns.set(8114, new int[] {0,0,0,12,12,0,0,0,0});corner_0_PatternsIndices.add(8114);
		corner_0_Patterns.set(8115, new int[] {0,0,0,0,12,12,0,0,0});corner_0_PatternsIndices.add(8115);
		corner_0_Patterns.set(8116, new int[] {0,0,0,0,0,12,12,0,0});corner_0_PatternsIndices.add(8116);
		corner_0_Patterns.set(8117, new int[] {0,0,0,0,0,0,12,12,0});corner_0_PatternsIndices.add(8117);
		corner_0_Patterns.set(8122, new int[] {0,14,19,8,0,0,0,0,0});corner_0_PatternsIndices.add(8122);
		corner_0_Patterns.set(8123, new int[] {0,0,14,19,8,0,0,0,0});corner_0_PatternsIndices.add(8123);
		corner_0_Patterns.set(8124, new int[] {0,0,0,14,19,8,0,0,0});corner_0_PatternsIndices.add(8124);
		corner_0_Patterns.set(8125, new int[] {0,0,0,0,14,19,8,0,0});corner_0_PatternsIndices.add(8125);
		corner_0_Patterns.set(8126, new int[] {0,0,0,0,0,14,19,8,0});corner_0_PatternsIndices.add(8126);

		firstIndexCorner0West_3_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8203, new int[] {0,13,16,16,4,0,0,0,0});corner_0_PatternsIndices.add(8203);
		corner_0_Patterns.set(8204, new int[] {0,0,13,16,16,4,0,0,0});corner_0_PatternsIndices.add(8204);
		corner_0_Patterns.set(8205, new int[] {0,0,0,13,16,16,4,0,0});corner_0_PatternsIndices.add(8205);
		corner_0_Patterns.set(8206, new int[] {0,0,0,0,13,16,16,4,0});corner_0_PatternsIndices.add(8206);
		corner_0_Patterns.set(8212, new int[] {0,12,12,12,0,0,0,0,0});corner_0_PatternsIndices.add(8212);
		corner_0_Patterns.set(8213, new int[] {0,0,12,12,12,0,0,0,0});corner_0_PatternsIndices.add(8213);
		corner_0_Patterns.set(8214, new int[] {0,0,0,12,12,12,0,0,0});corner_0_PatternsIndices.add(8214);
		corner_0_Patterns.set(8215, new int[] {0,0,0,0,12,12,12,0,0});corner_0_PatternsIndices.add(8215);
		corner_0_Patterns.set(8216, new int[] {0,0,0,0,0,12,12,12,0});corner_0_PatternsIndices.add(8216);
		corner_0_Patterns.set(8222, new int[] {0,14,19,19,8,0,0,0,0});corner_0_PatternsIndices.add(8222);
		corner_0_Patterns.set(8223, new int[] {0,0,14,19,19,8,0,0,0});corner_0_PatternsIndices.add(8223);
		corner_0_Patterns.set(8224, new int[] {0,0,0,14,19,19,8,0,0});corner_0_PatternsIndices.add(8224);
		corner_0_Patterns.set(8225, new int[] {0,0,0,0,14,19,19,8,0});corner_0_PatternsIndices.add(8225);

		firstIndexCorner0West_4_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8303, new int[] {0,13,16,16,16,4,0,0,0});corner_0_PatternsIndices.add(8303);
		corner_0_Patterns.set(8304, new int[] {0,0,13,16,16,16,4,0,0});corner_0_PatternsIndices.add(8304);
		corner_0_Patterns.set(8305, new int[] {0,0,0,13,16,16,16,4,0});corner_0_PatternsIndices.add(8305);
		corner_0_Patterns.set(8312, new int[] {0,12,12,12,12,0,0,0,0});corner_0_PatternsIndices.add(8312);
		corner_0_Patterns.set(8313, new int[] {0,0,12,12,12,12,0,0,0});corner_0_PatternsIndices.add(8313);
		corner_0_Patterns.set(8314, new int[] {0,0,0,12,12,12,12,0,0});corner_0_PatternsIndices.add(8314);
		corner_0_Patterns.set(8315, new int[] {0,0,0,0,12,12,12,12,0});corner_0_PatternsIndices.add(8315);
		corner_0_Patterns.set(8322, new int[] {0,14,19,19,19,8,0,0,0});corner_0_PatternsIndices.add(8322);
		corner_0_Patterns.set(8323, new int[] {0,0,14,19,19,19,8,0,0});corner_0_PatternsIndices.add(8323);
		corner_0_Patterns.set(8324, new int[] {0,0,0,14,19,19,19,8,0});corner_0_PatternsIndices.add(8324);

		firstIndexCorner0West_5_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8403, new int[] {0,13,16,16,16,16,4,0,0});corner_0_PatternsIndices.add(8403);
		corner_0_Patterns.set(8404, new int[] {0,0,13,16,16,16,16,4,0});corner_0_PatternsIndices.add(8404);
		corner_0_Patterns.set(8412, new int[] {0,12,12,12,12,12,0,0,0});corner_0_PatternsIndices.add(8412);
		corner_0_Patterns.set(8413, new int[] {0,0,12,12,12,12,12,0,0});corner_0_PatternsIndices.add(8413);
		corner_0_Patterns.set(8414, new int[] {0,0,0,12,12,12,12,12,0});corner_0_PatternsIndices.add(8414);
		corner_0_Patterns.set(8422, new int[] {0,14,19,19,19,19,8,0,0});corner_0_PatternsIndices.add(8422);
		corner_0_Patterns.set(8423, new int[] {0,0,14,19,19,19,19,8,0});corner_0_PatternsIndices.add(8423);

		firstIndexCorner0West_6_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8503, new int[] {0,13,16,16,16,16,16,4,0});corner_0_PatternsIndices.add(8503);
		corner_0_Patterns.set(8512, new int[] {0,12,12,12,12,12,12,0,0});corner_0_PatternsIndices.add(8512);
		corner_0_Patterns.set(8513, new int[] {0,0,12,12,12,12,12,12,0});corner_0_PatternsIndices.add(8513);
		corner_0_Patterns.set(8522, new int[] {0,14,19,19,19,19,19,8,0});corner_0_PatternsIndices.add(8522);

		firstIndexCorner0West_7_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(8612, new int[] {0,12,12,12,12,12,12,12,0});corner_0_PatternsIndices.add(8612);
		
		/*********** east 0 -> 0 ***********/
		
		corner_0_Patterns.set(9000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(9000);
		corner_0_Patterns.set(9002, new int[] {0,2,10,8,0,0,0,0,0});corner_0_PatternsIndices.add(9002);
		corner_0_Patterns.set(9003, new int[] {0,0,2,10,8,0,0,0,0});corner_0_PatternsIndices.add(9003);
		corner_0_Patterns.set(9004, new int[] {0,0,0,2,10,8,0,0,0});corner_0_PatternsIndices.add(9004);
		corner_0_Patterns.set(9005, new int[] {0,0,0,0,2,10,8,0,0});corner_0_PatternsIndices.add(9005);
		corner_0_Patterns.set(9006, new int[] {0,0,0,0,0,2,10,8,0});corner_0_PatternsIndices.add(9006);
		corner_0_Patterns.set(9012, new int[] {0,2,10,10,10,10,10,8,0});corner_0_PatternsIndices.add(9012);
		corner_0_Patterns.set(9013, new int[] {0,0,2,10,10,10,8,0,0});corner_0_PatternsIndices.add(9013);
		corner_0_Patterns.set(9022, new int[] {0,2,10,8,0,2,10,8,0});corner_0_PatternsIndices.add(9022);
		corner_0_Patterns.set(9023, new int[] {0,0,2,10,8,2,10,8,0});corner_0_PatternsIndices.add(9023);
		corner_0_Patterns.set(9024, new int[] {0,2,10,8,2,10,8,0,0});corner_0_PatternsIndices.add(9024);
		corner_0_Patterns.set(9102, new int[] {0,2,17,19,8,0,0,0,0});corner_0_PatternsIndices.add(9102);
		corner_0_Patterns.set(9103, new int[] {0,0,2,17,19,8,0,0,0});corner_0_PatternsIndices.add(9103);
		corner_0_Patterns.set(9104, new int[] {0,0,0,2,17,19,8,0,0});corner_0_PatternsIndices.add(9104);
		corner_0_Patterns.set(9105, new int[] {0,0,0,0,2,17,19,8,0});corner_0_PatternsIndices.add(9105);
		corner_0_Patterns.set(9112, new int[] {0,2,17,10,19,8,0,0,0});corner_0_PatternsIndices.add(9112);
		corner_0_Patterns.set(9113, new int[] {0,0,2,17,10,19,8,0,0});corner_0_PatternsIndices.add(9113);
		corner_0_Patterns.set(9114, new int[] {0,0,0,2,17,10,19,8,0});corner_0_PatternsIndices.add(9114);
		corner_0_Patterns.set(9122, new int[] {0,2,17,10,10,10,19,8,0});corner_0_PatternsIndices.add(9122);
		corner_0_Patterns.set(9133, new int[] {0,0,2,17,10,19,8,0,0});corner_0_PatternsIndices.add(9133);
		corner_0_Patterns.set(9202, new int[] {0,2,17,17,19,19,8,0,0});corner_0_PatternsIndices.add(9202);
		corner_0_Patterns.set(9203, new int[] {0,0,2,17,17,19,19,8,0});corner_0_PatternsIndices.add(9203);
		corner_0_Patterns.set(9212, new int[] {0,2,17,17,10,19,19,8,0});corner_0_PatternsIndices.add(9212);

		/*********** east 0 -> n ***********/

		firstIndexCorner0East_0_1 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10003, new int[] {0,0,0,0,0,0,1,7,0});corner_0_PatternsIndices.add(10003);
		corner_0_Patterns.set(10004, new int[] {0,0,0,0,0,1,7,0,0});corner_0_PatternsIndices.add(10004);
		corner_0_Patterns.set(10005, new int[] {0,0,0,0,1,7,0,0,0});corner_0_PatternsIndices.add(10005);
		corner_0_Patterns.set(10006, new int[] {0,0,0,1,7,0,0,0,0});corner_0_PatternsIndices.add(10006);
		corner_0_Patterns.set(10007, new int[] {0,0,1,7,0,0,0,0,0});corner_0_PatternsIndices.add(10007);
		corner_0_Patterns.set(10008, new int[] {0,1,7,0,0,0,0,0,0});corner_0_PatternsIndices.add(10008);
		corner_0_Patterns.set(10012, new int[] {0,0,0,0,0,0,0,3,0});corner_0_PatternsIndices.add(10012);
		corner_0_Patterns.set(10013, new int[] {0,0,0,0,0,0,3,0,0});corner_0_PatternsIndices.add(10013);
		corner_0_Patterns.set(10014, new int[] {0,0,0,0,0,3,0,0,0});corner_0_PatternsIndices.add(10014);
		corner_0_Patterns.set(10015, new int[] {0,0,0,0,3,0,0,0,0});corner_0_PatternsIndices.add(10015);
		corner_0_Patterns.set(10016, new int[] {0,0,0,3,0,0,0,0,0});corner_0_PatternsIndices.add(10016);
		corner_0_Patterns.set(10017, new int[] {0,0,3,0,0,0,0,0,0});corner_0_PatternsIndices.add(10017);
		corner_0_Patterns.set(10018, new int[] {0,3,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(10018);
		corner_0_Patterns.set(10022, new int[] {0,0,0,0,0,0,2,11,0});corner_0_PatternsIndices.add(10022);
		corner_0_Patterns.set(10023, new int[] {0,0,0,0,0,2,11,0,0});corner_0_PatternsIndices.add(10023);
		corner_0_Patterns.set(10024, new int[] {0,0,0,0,2,11,0,0,0});corner_0_PatternsIndices.add(10024);
		corner_0_Patterns.set(10025, new int[] {0,0,0,2,11,0,0,0,0});corner_0_PatternsIndices.add(10025);
		corner_0_Patterns.set(10026, new int[] {0,0,2,11,0,0,0,0,0});corner_0_PatternsIndices.add(10026);
		corner_0_Patterns.set(10027, new int[] {0,2,11,0,0,0,0,0,0});corner_0_PatternsIndices.add(10027);

		firstIndexCorner0East_0_2 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10103, new int[] {0,0,0,0,0,1,18,7,0});corner_0_PatternsIndices.add(10103);
		corner_0_Patterns.set(10104, new int[] {0,0,0,0,1,18,7,0,0});corner_0_PatternsIndices.add(10104);
		corner_0_Patterns.set(10105, new int[] {0,0,0,1,18,7,0,0,0});corner_0_PatternsIndices.add(10105);
		corner_0_Patterns.set(10106, new int[] {0,0,1,18,7,0,0,0,0});corner_0_PatternsIndices.add(10106);
		corner_0_Patterns.set(10107, new int[] {0,1,18,7,0,0,0,0,0});corner_0_PatternsIndices.add(10107);
		corner_0_Patterns.set(10112, new int[] {0,0,0,0,0,0,3,3,0});corner_0_PatternsIndices.add(10112);
		corner_0_Patterns.set(10113, new int[] {0,0,0,0,0,3,3,0,0});corner_0_PatternsIndices.add(10113);
		corner_0_Patterns.set(10114, new int[] {0,0,0,0,3,3,0,0,0});corner_0_PatternsIndices.add(10114);
		corner_0_Patterns.set(10115, new int[] {0,0,0,3,3,0,0,0,0});corner_0_PatternsIndices.add(10115);
		corner_0_Patterns.set(10116, new int[] {0,0,3,3,0,0,0,0,0});corner_0_PatternsIndices.add(10116);
		corner_0_Patterns.set(10117, new int[] {0,3,3,0,0,0,0,0,0});corner_0_PatternsIndices.add(10117);
		corner_0_Patterns.set(10122, new int[] {0,0,0,0,0,2,17,11,0});corner_0_PatternsIndices.add(10122);
		corner_0_Patterns.set(10123, new int[] {0,0,0,0,2,17,11,0,0});corner_0_PatternsIndices.add(10123);
		corner_0_Patterns.set(10124, new int[] {0,0,0,2,17,11,0,0,0});corner_0_PatternsIndices.add(10124);
		corner_0_Patterns.set(10125, new int[] {0,0,2,17,11,0,0,0,0});corner_0_PatternsIndices.add(10125);
		corner_0_Patterns.set(10126, new int[] {0,2,17,11,0,0,0,0,0});corner_0_PatternsIndices.add(10126);

		firstIndexCorner0East_0_3 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10203, new int[] {0,0,0,0,1,18,18,7,0});corner_0_PatternsIndices.add(10203);
		corner_0_Patterns.set(10204, new int[] {0,0,0,1,18,18,7,0,0});corner_0_PatternsIndices.add(10204);
		corner_0_Patterns.set(10205, new int[] {0,0,1,18,18,7,0,0,0});corner_0_PatternsIndices.add(10205);
		corner_0_Patterns.set(10206, new int[] {0,1,18,18,7,0,0,0,0});corner_0_PatternsIndices.add(10206);
		corner_0_Patterns.set(10212, new int[] {0,0,0,0,0,3,3,3,0});corner_0_PatternsIndices.add(10212);
		corner_0_Patterns.set(10213, new int[] {0,0,0,0,3,3,3,0,0});corner_0_PatternsIndices.add(10213);
		corner_0_Patterns.set(10214, new int[] {0,0,0,3,3,3,0,0,0});corner_0_PatternsIndices.add(10214);
		corner_0_Patterns.set(10215, new int[] {0,0,3,3,3,0,0,0,0});corner_0_PatternsIndices.add(10215);
		corner_0_Patterns.set(10216, new int[] {0,3,3,3,0,0,0,0,0});corner_0_PatternsIndices.add(10216);
		corner_0_Patterns.set(10222, new int[] {0,0,0,0,2,17,17,11,0});corner_0_PatternsIndices.add(10222);
		corner_0_Patterns.set(10223, new int[] {0,0,0,2,17,17,11,0,0});corner_0_PatternsIndices.add(10223);
		corner_0_Patterns.set(10224, new int[] {0,0,2,17,17,11,0,0,0});corner_0_PatternsIndices.add(10224);
		corner_0_Patterns.set(10225, new int[] {0,2,17,17,11,0,0,0,0});corner_0_PatternsIndices.add(10225);

		firstIndexCorner0East_0_4 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10303, new int[] {0,0,0,1,18,18,18,7,0});corner_0_PatternsIndices.add(10303);
		corner_0_Patterns.set(10304, new int[] {0,0,1,18,18,18,7,0,0});corner_0_PatternsIndices.add(10304);
		corner_0_Patterns.set(10305, new int[] {0,1,18,18,18,7,0,0,0});corner_0_PatternsIndices.add(10305);
		corner_0_Patterns.set(10312, new int[] {0,0,0,0,3,3,3,3,0});corner_0_PatternsIndices.add(10312);
		corner_0_Patterns.set(10313, new int[] {0,0,0,3,3,3,3,0,0});corner_0_PatternsIndices.add(10313);
		corner_0_Patterns.set(10314, new int[] {0,0,3,3,3,3,0,0,0});corner_0_PatternsIndices.add(10314);
		corner_0_Patterns.set(10315, new int[] {0,3,3,3,3,0,0,0,0});corner_0_PatternsIndices.add(10315);
		corner_0_Patterns.set(10322, new int[] {0,0,0,2,17,17,17,11,0});corner_0_PatternsIndices.add(10322);
		corner_0_Patterns.set(10323, new int[] {0,0,2,17,17,17,11,0,0});corner_0_PatternsIndices.add(10323);
		corner_0_Patterns.set(10324, new int[] {0,2,17,17,17,11,0,0,0});corner_0_PatternsIndices.add(10324);

		firstIndexCorner0East_0_5 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10403, new int[] {0,0,1,18,18,18,18,7,0});corner_0_PatternsIndices.add(10403);
		corner_0_Patterns.set(10404, new int[] {0,1,18,18,18,18,7,0,0});corner_0_PatternsIndices.add(10404);
		corner_0_Patterns.set(10412, new int[] {0,0,0,3,3,3,3,3,0});corner_0_PatternsIndices.add(10412);
		corner_0_Patterns.set(10413, new int[] {0,0,3,3,3,3,3,0,0});corner_0_PatternsIndices.add(10413);
		corner_0_Patterns.set(10414, new int[] {0,3,3,3,3,3,0,0,0});corner_0_PatternsIndices.add(10414);
		corner_0_Patterns.set(10422, new int[] {0,0,2,17,17,17,17,11,0});corner_0_PatternsIndices.add(10422);
		corner_0_Patterns.set(10423, new int[] {0,2,17,17,17,17,11,0,0});corner_0_PatternsIndices.add(10423);

		firstIndexCorner0East_0_6 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10503, new int[] {0,1,18,18,18,18,18,7,0});corner_0_PatternsIndices.add(10503);
		corner_0_Patterns.set(10512, new int[] {0,0,3,3,3,3,3,3,0});corner_0_PatternsIndices.add(10512);
		corner_0_Patterns.set(10513, new int[] {0,3,3,3,3,3,3,0,0});corner_0_PatternsIndices.add(10513);
		corner_0_Patterns.set(10522, new int[] {0,2,17,17,17,17,17,11,0});corner_0_PatternsIndices.add(10522);

		firstIndexCorner0East_0_7 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(10612, new int[] {0,3,3,3,3,3,3,3,0});corner_0_PatternsIndices.add(10612);

		/*********** east n -> 0 ***********/

		firstIndexCorner0East_1_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11003, new int[] {0,14,8,0,0,0,0,0,0});corner_0_PatternsIndices.add(11003);
		corner_0_Patterns.set(11004, new int[] {0,0,14,8,0,0,0,0,0});corner_0_PatternsIndices.add(11004);
		corner_0_Patterns.set(11005, new int[] {0,0,0,14,8,0,0,0,0});corner_0_PatternsIndices.add(11005);
		corner_0_Patterns.set(11006, new int[] {0,0,0,0,14,8,0,0,0});corner_0_PatternsIndices.add(11006);
		corner_0_Patterns.set(11007, new int[] {0,0,0,0,0,14,8,0,0});corner_0_PatternsIndices.add(11007);
		corner_0_Patterns.set(11008, new int[] {0,0,0,0,0,0,14,8,0});corner_0_PatternsIndices.add(11008);
		corner_0_Patterns.set(11012, new int[] {0,12,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(11012);
		corner_0_Patterns.set(11013, new int[] {0,0,12,0,0,0,0,0,0});corner_0_PatternsIndices.add(11013);
		corner_0_Patterns.set(11014, new int[] {0,0,0,12,0,0,0,0,0});corner_0_PatternsIndices.add(11014);
		corner_0_Patterns.set(11015, new int[] {0,0,0,0,12,0,0,0,0});corner_0_PatternsIndices.add(11015);
		corner_0_Patterns.set(11016, new int[] {0,0,0,0,0,12,0,0,0});corner_0_PatternsIndices.add(11016);
		corner_0_Patterns.set(11017, new int[] {0,0,0,0,0,0,12,0,0});corner_0_PatternsIndices.add(11017);
		corner_0_Patterns.set(11018, new int[] {0,0,0,0,0,0,0,12,0});corner_0_PatternsIndices.add(11018);
		corner_0_Patterns.set(11022, new int[] {0,13,4,0,0,0,0,0,0});corner_0_PatternsIndices.add(11022);
		corner_0_Patterns.set(11023, new int[] {0,0,13,4,0,0,0,0,0});corner_0_PatternsIndices.add(11023);
		corner_0_Patterns.set(11024, new int[] {0,0,0,13,4,0,0,0,0});corner_0_PatternsIndices.add(11024);
		corner_0_Patterns.set(11025, new int[] {0,0,0,0,13,4,0,0,0});corner_0_PatternsIndices.add(11025);
		corner_0_Patterns.set(11026, new int[] {0,0,0,0,0,13,4,0,0});corner_0_PatternsIndices.add(11026);
		corner_0_Patterns.set(11027, new int[] {0,0,0,0,0,0,13,4,0});corner_0_PatternsIndices.add(11027);

		firstIndexCorner0East_2_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11103, new int[] {0,14,19,8,0,0,0,0,0});corner_0_PatternsIndices.add(11103);
		corner_0_Patterns.set(11104, new int[] {0,0,14,19,8,0,0,0,0});corner_0_PatternsIndices.add(11104);
		corner_0_Patterns.set(11105, new int[] {0,0,0,14,19,8,0,0,0});corner_0_PatternsIndices.add(11105);
		corner_0_Patterns.set(11106, new int[] {0,0,0,0,14,19,8,0,0});corner_0_PatternsIndices.add(11106);
		corner_0_Patterns.set(11107, new int[] {0,0,0,0,0,14,19,8,0});corner_0_PatternsIndices.add(11107);
		corner_0_Patterns.set(11112, new int[] {0,12,12,0,0,0,0,0,0});corner_0_PatternsIndices.add(11112);
		corner_0_Patterns.set(11113, new int[] {0,0,12,12,0,0,0,0,0});corner_0_PatternsIndices.add(11113);
		corner_0_Patterns.set(11114, new int[] {0,0,0,12,12,0,0,0,0});corner_0_PatternsIndices.add(11114);
		corner_0_Patterns.set(11115, new int[] {0,0,0,0,12,12,0,0,0});corner_0_PatternsIndices.add(11115);
		corner_0_Patterns.set(11116, new int[] {0,0,0,0,0,12,12,0,0});corner_0_PatternsIndices.add(11116);
		corner_0_Patterns.set(11117, new int[] {0,0,0,0,0,0,12,12,0});corner_0_PatternsIndices.add(11117);
		corner_0_Patterns.set(11122, new int[] {0,13,16,4,0,0,0,0,0});corner_0_PatternsIndices.add(11122);
		corner_0_Patterns.set(11123, new int[] {0,0,13,16,4,0,0,0,0});corner_0_PatternsIndices.add(11123);
		corner_0_Patterns.set(11124, new int[] {0,0,0,13,16,4,0,0,0});corner_0_PatternsIndices.add(11124);
		corner_0_Patterns.set(11125, new int[] {0,0,0,0,13,16,4,0,0});corner_0_PatternsIndices.add(11125);
		corner_0_Patterns.set(11126, new int[] {0,0,0,0,0,13,16,4,0});corner_0_PatternsIndices.add(11126);

		firstIndexCorner0East_3_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11203, new int[] {0,14,19,19,8,0,0,0,0});corner_0_PatternsIndices.add(11203);
		corner_0_Patterns.set(11204, new int[] {0,0,14,19,19,8,0,0,0});corner_0_PatternsIndices.add(11204);
		corner_0_Patterns.set(11205, new int[] {0,0,0,14,19,19,8,0,0});corner_0_PatternsIndices.add(11205);
		corner_0_Patterns.set(11206, new int[] {0,0,0,0,14,19,19,8,0});corner_0_PatternsIndices.add(11206);
		corner_0_Patterns.set(11212, new int[] {0,12,12,12,0,0,0,0,0});corner_0_PatternsIndices.add(11212);
		corner_0_Patterns.set(11213, new int[] {0,0,12,12,12,0,0,0,0});corner_0_PatternsIndices.add(11213);
		corner_0_Patterns.set(11214, new int[] {0,0,0,12,12,12,0,0,0});corner_0_PatternsIndices.add(11214);
		corner_0_Patterns.set(11215, new int[] {0,0,0,0,12,12,12,0,0});corner_0_PatternsIndices.add(11215);
		corner_0_Patterns.set(11216, new int[] {0,0,0,0,0,12,12,12,0});corner_0_PatternsIndices.add(11216);
		corner_0_Patterns.set(11222, new int[] {0,13,16,16,4,0,0,0,0});corner_0_PatternsIndices.add(11222);
		corner_0_Patterns.set(11223, new int[] {0,0,13,16,16,4,0,0,0});corner_0_PatternsIndices.add(11223);
		corner_0_Patterns.set(11224, new int[] {0,0,0,13,16,16,4,0,0});corner_0_PatternsIndices.add(11224);
		corner_0_Patterns.set(11225, new int[] {0,0,0,0,13,16,16,4,0});corner_0_PatternsIndices.add(11225);

		firstIndexCorner0East_4_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11303, new int[] {0,14,19,19,19,8,0,0,0});corner_0_PatternsIndices.add(11303);
		corner_0_Patterns.set(11304, new int[] {0,0,14,19,19,19,8,0,0});corner_0_PatternsIndices.add(11304);
		corner_0_Patterns.set(11305, new int[] {0,0,0,14,19,19,19,8,0});corner_0_PatternsIndices.add(11305);
		corner_0_Patterns.set(11312, new int[] {0,12,12,12,12,0,0,0,0});corner_0_PatternsIndices.add(11312);
		corner_0_Patterns.set(11313, new int[] {0,0,12,12,12,12,0,0,0});corner_0_PatternsIndices.add(11313);
		corner_0_Patterns.set(11314, new int[] {0,0,0,12,12,12,12,0,0});corner_0_PatternsIndices.add(11314);
		corner_0_Patterns.set(11315, new int[] {0,0,0,0,12,12,12,12,0});corner_0_PatternsIndices.add(11315);
		corner_0_Patterns.set(11322, new int[] {0,13,16,16,16,4,0,0,0});corner_0_PatternsIndices.add(11322);
		corner_0_Patterns.set(11323, new int[] {0,0,13,16,16,16,4,0,0});corner_0_PatternsIndices.add(11323);
		corner_0_Patterns.set(11324, new int[] {0,0,0,13,16,16,16,4,0});corner_0_PatternsIndices.add(11324);

		firstIndexCorner0East_5_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11403, new int[] {0,14,19,19,19,19,8,0,0});corner_0_PatternsIndices.add(11403);
		corner_0_Patterns.set(11404, new int[] {0,0,14,19,19,19,19,8,0});corner_0_PatternsIndices.add(11404);
		corner_0_Patterns.set(11412, new int[] {0,12,12,12,12,12,0,0,0});corner_0_PatternsIndices.add(11412);
		corner_0_Patterns.set(11413, new int[] {0,0,12,12,12,12,12,0,0});corner_0_PatternsIndices.add(11413);
		corner_0_Patterns.set(11414, new int[] {0,0,0,12,12,12,12,12,0});corner_0_PatternsIndices.add(11414);
		corner_0_Patterns.set(11422, new int[] {0,13,16,16,16,16,4,0,0});corner_0_PatternsIndices.add(11422);
		corner_0_Patterns.set(11423, new int[] {0,0,13,16,16,16,16,4,0});corner_0_PatternsIndices.add(11423);

		firstIndexCorner0East_6_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11503, new int[] {0,14,19,19,19,19,19,8,0});corner_0_PatternsIndices.add(11503);
		corner_0_Patterns.set(11512, new int[] {0,12,12,12,12,12,12,0,0});corner_0_PatternsIndices.add(11512);
		corner_0_Patterns.set(11513, new int[] {0,0,12,12,12,12,12,12,0});corner_0_PatternsIndices.add(11513);
		corner_0_Patterns.set(11522, new int[] {0,13,16,16,16,16,16,4,0});corner_0_PatternsIndices.add(11522);

		firstIndexCorner0East_7_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(11612, new int[] {0,12,12,12,12,12,12,12,0});corner_0_PatternsIndices.add(11612);


		
	}
	
	int[] getBorderPattern(int north_1__south_2__west_3__east_4, int deltaLevel) {
		switch (north_1__south_2__west_3__east_4) {
		case 1: 
			switch (deltaLevel) {
			case 0: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_0, firstIndexCorner0North_0_1)));
			case 1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_1, firstIndexCorner0North_0_2)));
			case 2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_2, firstIndexCorner0North_0_3)));
			case 3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_3, firstIndexCorner0North_0_4)));
			case 4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_4, firstIndexCorner0North_0_5)));
			case 5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_5, firstIndexCorner0North_0_6)));
			case 6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_6, firstIndexCorner0North_0_7)));
			case 7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_7, firstIndexCorner0North_1_0)));
			case -1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_1_0, firstIndexCorner0North_2_0)));
			case -2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_2_0, firstIndexCorner0North_3_0)));
			case -3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_3_0, firstIndexCorner0North_4_0)));
			case -4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_4_0, firstIndexCorner0North_5_0)));
			case -5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_5_0, firstIndexCorner0North_6_0)));
			case -6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_6_0, firstIndexCorner0North_7_0)));
			case -7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_7_0, firstIndexCorner0South_0_0)));
			}
		case 2: 
			switch (deltaLevel) {
			case 0: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_0, firstIndexCorner0South_0_1)));
			case 1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_1, firstIndexCorner0South_0_2)));
			case 2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_2, firstIndexCorner0South_0_3)));
			case 3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_3, firstIndexCorner0South_0_4)));
			case 4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_4, firstIndexCorner0South_0_5)));
			case 5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_5, firstIndexCorner0South_0_6)));
			case 6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_6, firstIndexCorner0South_0_7)));
			case 7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_7, firstIndexCorner0South_1_0)));
			case -1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_1_0, firstIndexCorner0South_2_0)));
			case -2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_2_0, firstIndexCorner0South_3_0)));
			case -3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_3_0, firstIndexCorner0South_4_0)));
			case -4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_4_0, firstIndexCorner0South_5_0)));
			case -5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_5_0, firstIndexCorner0South_6_0)));
			case -6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_6_0, firstIndexCorner0South_7_0)));
			case -7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_7_0, firstIndexCorner0West_0_0)));
			}
		case 3: 
			switch (deltaLevel) {
			case 0: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_0, firstIndexCorner0West_0_1)));
			case 1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_1, firstIndexCorner0West_0_2)));
			case 2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_2, firstIndexCorner0West_0_3)));
			case 3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_3, firstIndexCorner0West_0_4)));
			case 4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_4, firstIndexCorner0West_0_5)));
			case 5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_5, firstIndexCorner0West_0_6)));
			case 6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_6, firstIndexCorner0West_0_7)));
			case 7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_7, firstIndexCorner0West_1_0)));
			case -1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_1_0, firstIndexCorner0West_2_0)));
			case -2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_2_0, firstIndexCorner0West_3_0)));
			case -3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_3_0, firstIndexCorner0West_4_0)));
			case -4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_4_0, firstIndexCorner0West_5_0)));
			case -5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_5_0, firstIndexCorner0West_6_0)));
			case -6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_6_0, firstIndexCorner0West_7_0)));
			case -7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_7_0, firstIndexCorner0East_0_0)));
			}
		case 4: 
			switch (deltaLevel) {
			case 0: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_0, firstIndexCorner0East_0_1)));
			case 1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_1, firstIndexCorner0East_0_2)));
			case 2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_2, firstIndexCorner0East_0_3)));
			case 3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_3, firstIndexCorner0East_0_4)));
			case 4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_4, firstIndexCorner0East_0_5)));
			case 5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_5, firstIndexCorner0East_0_6)));
			case 6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_6, firstIndexCorner0East_0_7)));
			case 7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_7, firstIndexCorner0East_1_0)));
			case -1: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_1_0, firstIndexCorner0East_2_0)));
			case -2: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_2_0, firstIndexCorner0East_3_0)));
			case -3: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_3_0, firstIndexCorner0East_4_0)));
			case -4: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_4_0, firstIndexCorner0East_5_0)));
			case -5: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_5_0, firstIndexCorner0East_6_0)));
			case -6: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_6_0, firstIndexCorner0East_7_0)));
			case -7: return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_7_0, corner_0_PatternsIndices.size())));
			}
		}
		return corner_0_Patterns.get(0);
	}
	
	private int getRandomIntBetween(int a, int b) {
		int value = random.nextInt((b - a) + a) ;
		return value;
	}
	
}
