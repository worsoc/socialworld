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
package org.socialworld.tools.mct.isles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.socialworld.tools.mct.HeightIsle;
import org.socialworld.tools.mct.SubIsle;

public class SubIsle17 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  
						9,  10, 
						18, 19, 
						27, 28};

	private final static Integer at01[]  = {1,  2,  
						10, 11, 
						19, 20, 
						28, 29};

	private final static Integer at02[]  = {2,  3,  
						11, 12, 
						20, 21, 
						29, 30};

	private final static Integer at03[]  = {3,  4,  
						12, 13, 
						21, 22, 
						30, 31};

	private final static Integer at04[]  = {4,  5,  
						13, 14, 
						22, 23, 
						31, 32};

	private final static Integer at05[]  = {5,  6,  
						14, 15, 
						23, 24, 
						32, 33};

	private final static Integer at06[]  = {6,  7,  
						15, 16, 
						24, 25, 
						33, 34};

	private final static Integer at07[]  = {7,  8,  
						16, 17, 
						25, 26, 
						34, 35};

	private final static Integer at09[]  = {9,  10, 
						18, 19, 
						27, 28, 
						36, 37};

	private final static Integer at10[]  = {10, 11, 
						19, 20, 
						28, 29, 
						37, 38};

	private final static Integer at11[]  = {11, 12, 
						20, 21, 
						29, 30, 
						38, 39};

	private final static Integer at12[]  = {12, 13, 
						21, 22, 
						30, 31, 
						39, 40};

	private final static Integer at13[]  = {13, 14, 
						22, 23, 
						31, 32, 
						40, 41};

	private final static Integer at14[]  = {14, 15, 
						23, 24, 
						32, 33, 
						41, 42};

	private final static Integer at15[]  = {15, 16, 
						24, 25, 
						33, 34, 
						42, 43};

	private final static Integer at16[]  = {16, 17, 
						25, 26, 
						34, 35, 
						43, 44};

	private final static Integer at18[]  = {18, 19, 
						27, 28, 
						36, 37, 
						45, 46};

	private final static Integer at19[]  = {19, 20, 
						28, 29, 
						37, 38, 
						46, 47};

	private final static Integer at20[]  = {20, 21, 
						29, 30, 
						38, 39, 
						47, 48};

	private final static Integer at21[]  = {21, 22, 
						30, 31, 
						39, 40, 
						48, 49};

	private final static Integer at22[]  = {22, 23, 
						31, 32, 
						40, 41, 
						49, 50};

	private final static Integer at23[]  = {23, 24, 
						32, 33, 
						41, 42, 
						50, 51};

	private final static Integer at24[]  = {24, 25, 
						33, 34, 
						42, 43, 
						51, 52};

	private final static Integer at25[]  = {25, 26, 
						34, 35, 
						43, 44, 
						52, 53};

	private final static Integer at27[]  = {27, 28, 
						36, 37, 
						45, 46, 
						54, 55};

	private final static Integer at28[]  = {28, 29, 
						37, 38, 
						46, 47, 
						55, 56};

	private final static Integer at29[]  = {29, 30, 
						38, 39, 
						47, 48, 
						56, 57};

	private final static Integer at30[]  = {30, 31, 
						39, 40, 
						48, 49, 
						57, 58};

	private final static Integer at31[]  = {31, 32, 
						40, 41, 
						49, 50, 
						58, 59};

	private final static Integer at32[]  = {32, 33, 
						41, 42, 
						50, 51, 
						59, 60};

	private final static Integer at33[]  = {33, 34, 
						42, 43, 
						51, 52, 
						60, 61};

	private final static Integer at34[]  = {34, 35, 
						43, 44, 
						52, 53, 
						61, 62};

	private final static Integer at36[]  = {36, 37, 
						45, 46, 
						54, 55, 
						63, 64};

	private final static Integer at37[]  = {37, 38, 
						46, 47, 
						55, 56, 
						64, 65};

	private final static Integer at38[]  = {38, 39, 
						47, 48, 
						56, 57, 
						65, 66};

	private final static Integer at39[]  = {39, 40, 
						48, 49, 
						57, 58, 
						66, 67};

	private final static Integer at40[]  = {40, 41, 
						49, 50, 
						58, 59, 
						67, 68};

	private final static Integer at41[]  = {41, 42, 
						50, 51, 
						59, 60, 
						68, 69};

	private final static Integer at42[]  = {42, 43, 
						51, 52, 
						60, 61, 
						69, 70};

	private final static Integer at43[]  = {43, 44, 
						52, 53, 
						61, 62, 
						70, 71};

	private final static Integer at45[]  = {45, 46, 
						54, 55, 
						63, 64, 
						72, 73};

	private final static Integer at46[]  = {46, 47, 
						55, 56, 
						64, 65, 
						73, 74};

	private final static Integer at47[]  = {47, 48, 
						56, 57, 
						65, 66, 
						74, 75};

	private final static Integer at48[]  = {48, 49, 
						57, 58, 
						66, 67, 
						75, 76};

	private final static Integer at49[]  = {49, 50, 
						58, 59, 
						67, 68, 
						76, 77};

	private final static Integer at50[]  = {50, 51, 
						59, 60, 
						68, 69, 
						77, 78};

	private final static Integer at51[]  = {51, 52, 
						60, 61, 
						69, 70, 
						78, 79};

	private final static Integer at52[]  = {52, 53, 
						61, 62, 
						70, 71, 
						79, 80};

	private List<Integer> nr17at00;
	private HeightIsle heightIsle17at00;

	private List<Integer> nr17at01;
	private HeightIsle heightIsle17at01;

	private List<Integer> nr17at02;
	private HeightIsle heightIsle17at02;

	private List<Integer> nr17at03;
	private HeightIsle heightIsle17at03;

	private List<Integer> nr17at04;
	private HeightIsle heightIsle17at04;

	private List<Integer> nr17at05;
	private HeightIsle heightIsle17at05;

	private List<Integer> nr17at06;
	private HeightIsle heightIsle17at06;

	private List<Integer> nr17at07;
	private HeightIsle heightIsle17at07;

	private List<Integer> nr17at09;
	private HeightIsle heightIsle17at09;

	private List<Integer> nr17at10;
	private HeightIsle heightIsle17at10;

	private List<Integer> nr17at11;
	private HeightIsle heightIsle17at11;

	private List<Integer> nr17at12;
	private HeightIsle heightIsle17at12;

	private List<Integer> nr17at13;
	private HeightIsle heightIsle17at13;

	private List<Integer> nr17at14;
	private HeightIsle heightIsle17at14;

	private List<Integer> nr17at15;
	private HeightIsle heightIsle17at15;

	private List<Integer> nr17at16;
	private HeightIsle heightIsle17at16;

	private List<Integer> nr17at18;
	private HeightIsle heightIsle17at18;

	private List<Integer> nr17at19;
	private HeightIsle heightIsle17at19;

	private List<Integer> nr17at20;
	private HeightIsle heightIsle17at20;

	private List<Integer> nr17at21;
	private HeightIsle heightIsle17at21;

	private List<Integer> nr17at22;
	private HeightIsle heightIsle17at22;

	private List<Integer> nr17at23;
	private HeightIsle heightIsle17at23;

	private List<Integer> nr17at24;
	private HeightIsle heightIsle17at24;

	private List<Integer> nr17at25;
	private HeightIsle heightIsle17at25;

	private List<Integer> nr17at27;
	private HeightIsle heightIsle17at27;

	private List<Integer> nr17at28;
	private HeightIsle heightIsle17at28;

	private List<Integer> nr17at29;
	private HeightIsle heightIsle17at29;

	private List<Integer> nr17at30;
	private HeightIsle heightIsle17at30;

	private List<Integer> nr17at31;
	private HeightIsle heightIsle17at31;

	private List<Integer> nr17at32;
	private HeightIsle heightIsle17at32;

	private List<Integer> nr17at33;
	private HeightIsle heightIsle17at33;

	private List<Integer> nr17at34;
	private HeightIsle heightIsle17at34;

	private List<Integer> nr17at36;
	private HeightIsle heightIsle17at36;

	private List<Integer> nr17at37;
	private HeightIsle heightIsle17at37;

	private List<Integer> nr17at38;
	private HeightIsle heightIsle17at38;

	private List<Integer> nr17at39;
	private HeightIsle heightIsle17at39;

	private List<Integer> nr17at40;
	private HeightIsle heightIsle17at40;

	private List<Integer> nr17at41;
	private HeightIsle heightIsle17at41;

	private List<Integer> nr17at42;
	private HeightIsle heightIsle17at42;

	private List<Integer> nr17at43;
	private HeightIsle heightIsle17at43;

	private List<Integer> nr17at45;
	private HeightIsle heightIsle17at45;

	private List<Integer> nr17at46;
	private HeightIsle heightIsle17at46;

	private List<Integer> nr17at47;
	private HeightIsle heightIsle17at47;

	private List<Integer> nr17at48;
	private HeightIsle heightIsle17at48;

	private List<Integer> nr17at49;
	private HeightIsle heightIsle17at49;

	private List<Integer> nr17at50;
	private HeightIsle heightIsle17at50;

	private List<Integer> nr17at51;
	private HeightIsle heightIsle17at51;

	private List<Integer> nr17at52;
	private HeightIsle heightIsle17at52;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle17() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr17at00 = Arrays.asList(at00);
		heightIsle17at00 = new HeightIsle(nr17at00, cornerMaxima);

		nr17at01 = Arrays.asList(at01);
		heightIsle17at01 = new HeightIsle(nr17at01, cornerMaxima);

		nr17at02 = Arrays.asList(at02);
		heightIsle17at02 = new HeightIsle(nr17at02, cornerMaxima);

		nr17at03 = Arrays.asList(at03);
		heightIsle17at03 = new HeightIsle(nr17at03, cornerMaxima);

		nr17at04 = Arrays.asList(at04);
		heightIsle17at04 = new HeightIsle(nr17at04, cornerMaxima);

		nr17at05 = Arrays.asList(at05);
		heightIsle17at05 = new HeightIsle(nr17at05, cornerMaxima);

		nr17at06 = Arrays.asList(at06);
		heightIsle17at06 = new HeightIsle(nr17at06, cornerMaxima);

		nr17at07 = Arrays.asList(at07);
		heightIsle17at07 = new HeightIsle(nr17at07, cornerMaxima);

		nr17at09 = Arrays.asList(at09);
		heightIsle17at09 = new HeightIsle(nr17at09, cornerMaxima);

		nr17at10 = Arrays.asList(at10);
		heightIsle17at10 = new HeightIsle(nr17at10, cornerMaxima);

		nr17at11 = Arrays.asList(at11);
		heightIsle17at11 = new HeightIsle(nr17at11, cornerMaxima);

		nr17at12 = Arrays.asList(at12);
		heightIsle17at12 = new HeightIsle(nr17at12, cornerMaxima);

		nr17at13 = Arrays.asList(at13);
		heightIsle17at13 = new HeightIsle(nr17at13, cornerMaxima);

		nr17at14 = Arrays.asList(at14);
		heightIsle17at14 = new HeightIsle(nr17at14, cornerMaxima);

		nr17at15 = Arrays.asList(at15);
		heightIsle17at15 = new HeightIsle(nr17at15, cornerMaxima);

		nr17at16 = Arrays.asList(at16);
		heightIsle17at16 = new HeightIsle(nr17at16, cornerMaxima);

		nr17at18 = Arrays.asList(at18);
		heightIsle17at18 = new HeightIsle(nr17at18, cornerMaxima);

		nr17at19 = Arrays.asList(at19);
		heightIsle17at19 = new HeightIsle(nr17at19, cornerMaxima);

		nr17at20 = Arrays.asList(at20);
		heightIsle17at20 = new HeightIsle(nr17at20, cornerMaxima);

		nr17at21 = Arrays.asList(at21);
		heightIsle17at21 = new HeightIsle(nr17at21, cornerMaxima);

		nr17at22 = Arrays.asList(at22);
		heightIsle17at22 = new HeightIsle(nr17at22, cornerMaxima);

		nr17at23 = Arrays.asList(at23);
		heightIsle17at23 = new HeightIsle(nr17at23, cornerMaxima);

		nr17at24 = Arrays.asList(at24);
		heightIsle17at24 = new HeightIsle(nr17at24, cornerMaxima);

		nr17at25 = Arrays.asList(at25);
		heightIsle17at25 = new HeightIsle(nr17at25, cornerMaxima);

		nr17at27 = Arrays.asList(at27);
		heightIsle17at27 = new HeightIsle(nr17at27, cornerMaxima);

		nr17at28 = Arrays.asList(at28);
		heightIsle17at28 = new HeightIsle(nr17at28, cornerMaxima);

		nr17at29 = Arrays.asList(at29);
		heightIsle17at29 = new HeightIsle(nr17at29, cornerMaxima);

		nr17at30 = Arrays.asList(at30);
		heightIsle17at30 = new HeightIsle(nr17at30, cornerMaxima);

		nr17at31 = Arrays.asList(at31);
		heightIsle17at31 = new HeightIsle(nr17at31, cornerMaxima);

		nr17at32 = Arrays.asList(at32);
		heightIsle17at32 = new HeightIsle(nr17at32, cornerMaxima);

		nr17at33 = Arrays.asList(at33);
		heightIsle17at33 = new HeightIsle(nr17at33, cornerMaxima);

		nr17at34 = Arrays.asList(at34);
		heightIsle17at34 = new HeightIsle(nr17at34, cornerMaxima);

		nr17at36 = Arrays.asList(at36);
		heightIsle17at36 = new HeightIsle(nr17at36, cornerMaxima);

		nr17at37 = Arrays.asList(at37);
		heightIsle17at37 = new HeightIsle(nr17at37, cornerMaxima);

		nr17at38 = Arrays.asList(at38);
		heightIsle17at38 = new HeightIsle(nr17at38, cornerMaxima);

		nr17at39 = Arrays.asList(at39);
		heightIsle17at39 = new HeightIsle(nr17at39, cornerMaxima);

		nr17at40 = Arrays.asList(at40);
		heightIsle17at40 = new HeightIsle(nr17at40, cornerMaxima);

		nr17at41 = Arrays.asList(at41);
		heightIsle17at41 = new HeightIsle(nr17at41, cornerMaxima);

		nr17at42 = Arrays.asList(at42);
		heightIsle17at42 = new HeightIsle(nr17at42, cornerMaxima);

		nr17at43 = Arrays.asList(at43);
		heightIsle17at43 = new HeightIsle(nr17at43, cornerMaxima);

		nr17at45 = Arrays.asList(at45);
		heightIsle17at45 = new HeightIsle(nr17at45, cornerMaxima);

		nr17at46 = Arrays.asList(at46);
		heightIsle17at46 = new HeightIsle(nr17at46, cornerMaxima);

		nr17at47 = Arrays.asList(at47);
		heightIsle17at47 = new HeightIsle(nr17at47, cornerMaxima);

		nr17at48 = Arrays.asList(at48);
		heightIsle17at48 = new HeightIsle(nr17at48, cornerMaxima);

		nr17at49 = Arrays.asList(at49);
		heightIsle17at49 = new HeightIsle(nr17at49, cornerMaxima);

		nr17at50 = Arrays.asList(at50);
		heightIsle17at50 = new HeightIsle(nr17at50, cornerMaxima);

		nr17at51 = Arrays.asList(at51);
		heightIsle17at51 = new HeightIsle(nr17at51, cornerMaxima);

		nr17at52 = Arrays.asList(at52);
		heightIsle17at52 = new HeightIsle(nr17at52, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle17();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 52) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr17at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr17at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr17at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr17at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr17at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr17at05; break;
		case 6:
			rasterIndizesDescribingTheIsle = nr17at06; break;
		case 7:
			rasterIndizesDescribingTheIsle = nr17at07; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr17at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr17at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr17at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr17at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr17at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr17at14; break;
		case 15:
			rasterIndizesDescribingTheIsle = nr17at15; break;
		case 16:
			rasterIndizesDescribingTheIsle = nr17at16; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr17at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr17at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr17at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr17at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr17at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr17at23; break;
		case 24:
			rasterIndizesDescribingTheIsle = nr17at24; break;
		case 25:
			rasterIndizesDescribingTheIsle = nr17at25; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr17at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr17at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr17at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr17at30; break;
		case 31:
			rasterIndizesDescribingTheIsle = nr17at31; break;
		case 32:
			rasterIndizesDescribingTheIsle = nr17at32; break;
		case 33:
			rasterIndizesDescribingTheIsle = nr17at33; break;
		case 34:
			rasterIndizesDescribingTheIsle = nr17at34; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr17at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr17at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr17at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr17at39; break;
		case 40:
			rasterIndizesDescribingTheIsle = nr17at40; break;
		case 41:
			rasterIndizesDescribingTheIsle = nr17at41; break;
		case 42:
			rasterIndizesDescribingTheIsle = nr17at42; break;
		case 43:
			rasterIndizesDescribingTheIsle = nr17at43; break;
		case 45:
			rasterIndizesDescribingTheIsle = nr17at45; break;
		case 46:
			rasterIndizesDescribingTheIsle = nr17at46; break;
		case 47:
			rasterIndizesDescribingTheIsle = nr17at47; break;
		case 48:
			rasterIndizesDescribingTheIsle = nr17at48; break;
		case 49:
			rasterIndizesDescribingTheIsle = nr17at49; break;
		case 50:
			rasterIndizesDescribingTheIsle = nr17at50; break;
		case 51:
			rasterIndizesDescribingTheIsle = nr17at51; break;
		case 52:
			rasterIndizesDescribingTheIsle = nr17at52; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle17at00;
		case 1:
			return heightIsle17at01;
		case 2:
			return heightIsle17at02;
		case 3:
			return heightIsle17at03;
		case 4:
			return heightIsle17at04;
		case 5:
			return heightIsle17at05;
		case 6:
			return heightIsle17at06;
		case 7:
			return heightIsle17at07;
		case 9:
			return heightIsle17at09;
		case 10:
			return heightIsle17at10;
		case 11:
			return heightIsle17at11;
		case 12:
			return heightIsle17at12;
		case 13:
			return heightIsle17at13;
		case 14:
			return heightIsle17at14;
		case 15:
			return heightIsle17at15;
		case 16:
			return heightIsle17at16;
		case 18:
			return heightIsle17at18;
		case 19:
			return heightIsle17at19;
		case 20:
			return heightIsle17at20;
		case 21:
			return heightIsle17at21;
		case 22:
			return heightIsle17at22;
		case 23:
			return heightIsle17at23;
		case 24:
			return heightIsle17at24;
		case 25:
			return heightIsle17at25;
		case 27:
			return heightIsle17at27;
		case 28:
			return heightIsle17at28;
		case 29:
			return heightIsle17at29;
		case 30:
			return heightIsle17at30;
		case 31:
			return heightIsle17at31;
		case 32:
			return heightIsle17at32;
		case 33:
			return heightIsle17at33;
		case 34:
			return heightIsle17at34;
		case 36:
			return heightIsle17at36;
		case 37:
			return heightIsle17at37;
		case 38:
			return heightIsle17at38;
		case 39:
			return heightIsle17at39;
		case 40:
			return heightIsle17at40;
		case 41:
			return heightIsle17at41;
		case 42:
			return heightIsle17at42;
		case 43:
			return heightIsle17at43;
		case 45:
			return heightIsle17at45;
		case 46:
			return heightIsle17at46;
		case 47:
			return heightIsle17at47;
		case 48:
			return heightIsle17at48;
		case 49:
			return heightIsle17at49;
		case 50:
			return heightIsle17at50;
		case 51:
			return heightIsle17at51;
		case 52:
			return heightIsle17at52;
		default:
			return null;
		}
	}
}
