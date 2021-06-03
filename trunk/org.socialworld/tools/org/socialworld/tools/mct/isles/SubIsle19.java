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

public class SubIsle19 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  
						9,  10, 
						18, 19, 
						27, 28, 
						36, 37};

	private final static Integer at01[]  = {1,  2,  
						10, 11, 
						19, 20, 
						28, 29, 
						37, 38};

	private final static Integer at02[]  = {2,  3,  
						11, 12, 
						20, 21, 
						29, 30, 
						38, 39};

	private final static Integer at03[]  = {3,  4,  
						12, 13, 
						21, 22, 
						30, 31, 
						39, 40};

	private final static Integer at04[]  = {4,  5,  
						13, 14, 
						22, 23, 
						31, 32, 
						40, 41};

	private final static Integer at05[]  = {5,  6,  
						14, 15, 
						23, 24, 
						32, 33, 
						41, 42};

	private final static Integer at06[]  = {6,  7,  
						15, 16, 
						24, 25, 
						33, 34, 
						42, 43};

	private final static Integer at07[]  = {7,  8,  
						16, 17, 
						25, 26, 
						34, 35, 
						43, 44};

	private final static Integer at09[]  = {9,  10, 
						18, 19, 
						27, 28, 
						36, 37, 
						45, 46};

	private final static Integer at10[]  = {10, 11, 
						19, 20, 
						28, 29, 
						37, 38, 
						46, 47};

	private final static Integer at11[]  = {11, 12, 
						20, 21, 
						29, 30, 
						38, 39, 
						47, 48};

	private final static Integer at12[]  = {12, 13, 
						21, 22, 
						30, 31, 
						39, 40, 
						48, 49};

	private final static Integer at13[]  = {13, 14, 
						22, 23, 
						31, 32, 
						40, 41, 
						49, 50};

	private final static Integer at14[]  = {14, 15, 
						23, 24, 
						32, 33, 
						41, 42, 
						50, 51};

	private final static Integer at15[]  = {15, 16, 
						24, 25, 
						33, 34, 
						42, 43, 
						51, 52};

	private final static Integer at16[]  = {16, 17, 
						25, 26, 
						34, 35, 
						43, 44, 
						52, 53};

	private final static Integer at18[]  = {18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55};

	private final static Integer at19[]  = {19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56};

	private final static Integer at20[]  = {20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57};

	private final static Integer at21[]  = {21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58};

	private final static Integer at22[]  = {22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59};

	private final static Integer at23[]  = {23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60};

	private final static Integer at24[]  = {24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61};

	private final static Integer at25[]  = {25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62};

	private final static Integer at27[]  = {27, 28, 
						36, 37, 
						45, 46, 
						54, 55, 
						63, 64};

	private final static Integer at28[]  = {28, 29, 
						37, 38, 
						46, 47, 
						55, 56, 
						64, 65};

	private final static Integer at29[]  = {29, 30, 
						38, 39, 
						47, 48, 
						56, 57, 
						65, 66};

	private final static Integer at30[]  = {30, 31, 
						39, 40, 
						48, 49, 
						57, 58, 
						66, 67};

	private final static Integer at31[]  = {31, 32, 
						40, 41, 
						49, 50, 
						58, 59, 
						67, 68};

	private final static Integer at32[]  = {32, 33, 
						41, 42, 
						50, 51, 
						59, 60, 
						68, 69};

	private final static Integer at33[]  = {33, 34, 
						42, 43, 
						51, 52, 
						60, 61, 
						69, 70};

	private final static Integer at34[]  = {34, 35, 
						43, 44, 
						52, 53, 
						61, 62, 
						70, 71};

	private final static Integer at36[]  = {36, 37, 
						45, 46, 
						54, 55, 
						63, 64, 
						72, 73};

	private final static Integer at37[]  = {37, 38, 
						46, 47, 
						55, 56, 
						64, 65, 
						73, 74};

	private final static Integer at38[]  = {38, 39, 
						47, 48, 
						56, 57, 
						65, 66, 
						74, 75};

	private final static Integer at39[]  = {39, 40, 
						48, 49, 
						57, 58, 
						66, 67, 
						75, 76};

	private final static Integer at40[]  = {40, 41, 
						49, 50, 
						58, 59, 
						67, 68, 
						76, 77};

	private final static Integer at41[]  = {41, 42, 
						50, 51, 
						59, 60, 
						68, 69, 
						77, 78};

	private final static Integer at42[]  = {42, 43, 
						51, 52, 
						60, 61, 
						69, 70, 
						78, 79};

	private final static Integer at43[]  = {43, 44, 
						52, 53, 
						61, 62, 
						70, 71, 
						79, 80};

	private List<Integer> nr19at00;
	private HeightIsle heightIsle19at00;

	private List<Integer> nr19at01;
	private HeightIsle heightIsle19at01;

	private List<Integer> nr19at02;
	private HeightIsle heightIsle19at02;

	private List<Integer> nr19at03;
	private HeightIsle heightIsle19at03;

	private List<Integer> nr19at04;
	private HeightIsle heightIsle19at04;

	private List<Integer> nr19at05;
	private HeightIsle heightIsle19at05;

	private List<Integer> nr19at06;
	private HeightIsle heightIsle19at06;

	private List<Integer> nr19at07;
	private HeightIsle heightIsle19at07;

	private List<Integer> nr19at09;
	private HeightIsle heightIsle19at09;

	private List<Integer> nr19at10;
	private HeightIsle heightIsle19at10;

	private List<Integer> nr19at11;
	private HeightIsle heightIsle19at11;

	private List<Integer> nr19at12;
	private HeightIsle heightIsle19at12;

	private List<Integer> nr19at13;
	private HeightIsle heightIsle19at13;

	private List<Integer> nr19at14;
	private HeightIsle heightIsle19at14;

	private List<Integer> nr19at15;
	private HeightIsle heightIsle19at15;

	private List<Integer> nr19at16;
	private HeightIsle heightIsle19at16;

	private List<Integer> nr19at18;
	private HeightIsle heightIsle19at18;

	private List<Integer> nr19at19;
	private HeightIsle heightIsle19at19;

	private List<Integer> nr19at20;
	private HeightIsle heightIsle19at20;

	private List<Integer> nr19at21;
	private HeightIsle heightIsle19at21;

	private List<Integer> nr19at22;
	private HeightIsle heightIsle19at22;

	private List<Integer> nr19at23;
	private HeightIsle heightIsle19at23;

	private List<Integer> nr19at24;
	private HeightIsle heightIsle19at24;

	private List<Integer> nr19at25;
	private HeightIsle heightIsle19at25;

	private List<Integer> nr19at27;
	private HeightIsle heightIsle19at27;

	private List<Integer> nr19at28;
	private HeightIsle heightIsle19at28;

	private List<Integer> nr19at29;
	private HeightIsle heightIsle19at29;

	private List<Integer> nr19at30;
	private HeightIsle heightIsle19at30;

	private List<Integer> nr19at31;
	private HeightIsle heightIsle19at31;

	private List<Integer> nr19at32;
	private HeightIsle heightIsle19at32;

	private List<Integer> nr19at33;
	private HeightIsle heightIsle19at33;

	private List<Integer> nr19at34;
	private HeightIsle heightIsle19at34;

	private List<Integer> nr19at36;
	private HeightIsle heightIsle19at36;

	private List<Integer> nr19at37;
	private HeightIsle heightIsle19at37;

	private List<Integer> nr19at38;
	private HeightIsle heightIsle19at38;

	private List<Integer> nr19at39;
	private HeightIsle heightIsle19at39;

	private List<Integer> nr19at40;
	private HeightIsle heightIsle19at40;

	private List<Integer> nr19at41;
	private HeightIsle heightIsle19at41;

	private List<Integer> nr19at42;
	private HeightIsle heightIsle19at42;

	private List<Integer> nr19at43;
	private HeightIsle heightIsle19at43;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle19() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr19at00 = Arrays.asList(at00);
		heightIsle19at00 = new HeightIsle(nr19at00, cornerMaxima);

		nr19at01 = Arrays.asList(at01);
		heightIsle19at01 = new HeightIsle(nr19at01, cornerMaxima);

		nr19at02 = Arrays.asList(at02);
		heightIsle19at02 = new HeightIsle(nr19at02, cornerMaxima);

		nr19at03 = Arrays.asList(at03);
		heightIsle19at03 = new HeightIsle(nr19at03, cornerMaxima);

		nr19at04 = Arrays.asList(at04);
		heightIsle19at04 = new HeightIsle(nr19at04, cornerMaxima);

		nr19at05 = Arrays.asList(at05);
		heightIsle19at05 = new HeightIsle(nr19at05, cornerMaxima);

		nr19at06 = Arrays.asList(at06);
		heightIsle19at06 = new HeightIsle(nr19at06, cornerMaxima);

		nr19at07 = Arrays.asList(at07);
		heightIsle19at07 = new HeightIsle(nr19at07, cornerMaxima);

		nr19at09 = Arrays.asList(at09);
		heightIsle19at09 = new HeightIsle(nr19at09, cornerMaxima);

		nr19at10 = Arrays.asList(at10);
		heightIsle19at10 = new HeightIsle(nr19at10, cornerMaxima);

		nr19at11 = Arrays.asList(at11);
		heightIsle19at11 = new HeightIsle(nr19at11, cornerMaxima);

		nr19at12 = Arrays.asList(at12);
		heightIsle19at12 = new HeightIsle(nr19at12, cornerMaxima);

		nr19at13 = Arrays.asList(at13);
		heightIsle19at13 = new HeightIsle(nr19at13, cornerMaxima);

		nr19at14 = Arrays.asList(at14);
		heightIsle19at14 = new HeightIsle(nr19at14, cornerMaxima);

		nr19at15 = Arrays.asList(at15);
		heightIsle19at15 = new HeightIsle(nr19at15, cornerMaxima);

		nr19at16 = Arrays.asList(at16);
		heightIsle19at16 = new HeightIsle(nr19at16, cornerMaxima);

		nr19at18 = Arrays.asList(at18);
		heightIsle19at18 = new HeightIsle(nr19at18, cornerMaxima);

		nr19at19 = Arrays.asList(at19);
		heightIsle19at19 = new HeightIsle(nr19at19, cornerMaxima);

		nr19at20 = Arrays.asList(at20);
		heightIsle19at20 = new HeightIsle(nr19at20, cornerMaxima);

		nr19at21 = Arrays.asList(at21);
		heightIsle19at21 = new HeightIsle(nr19at21, cornerMaxima);

		nr19at22 = Arrays.asList(at22);
		heightIsle19at22 = new HeightIsle(nr19at22, cornerMaxima);

		nr19at23 = Arrays.asList(at23);
		heightIsle19at23 = new HeightIsle(nr19at23, cornerMaxima);

		nr19at24 = Arrays.asList(at24);
		heightIsle19at24 = new HeightIsle(nr19at24, cornerMaxima);

		nr19at25 = Arrays.asList(at25);
		heightIsle19at25 = new HeightIsle(nr19at25, cornerMaxima);

		nr19at27 = Arrays.asList(at27);
		heightIsle19at27 = new HeightIsle(nr19at27, cornerMaxima);

		nr19at28 = Arrays.asList(at28);
		heightIsle19at28 = new HeightIsle(nr19at28, cornerMaxima);

		nr19at29 = Arrays.asList(at29);
		heightIsle19at29 = new HeightIsle(nr19at29, cornerMaxima);

		nr19at30 = Arrays.asList(at30);
		heightIsle19at30 = new HeightIsle(nr19at30, cornerMaxima);

		nr19at31 = Arrays.asList(at31);
		heightIsle19at31 = new HeightIsle(nr19at31, cornerMaxima);

		nr19at32 = Arrays.asList(at32);
		heightIsle19at32 = new HeightIsle(nr19at32, cornerMaxima);

		nr19at33 = Arrays.asList(at33);
		heightIsle19at33 = new HeightIsle(nr19at33, cornerMaxima);

		nr19at34 = Arrays.asList(at34);
		heightIsle19at34 = new HeightIsle(nr19at34, cornerMaxima);

		nr19at36 = Arrays.asList(at36);
		heightIsle19at36 = new HeightIsle(nr19at36, cornerMaxima);

		nr19at37 = Arrays.asList(at37);
		heightIsle19at37 = new HeightIsle(nr19at37, cornerMaxima);

		nr19at38 = Arrays.asList(at38);
		heightIsle19at38 = new HeightIsle(nr19at38, cornerMaxima);

		nr19at39 = Arrays.asList(at39);
		heightIsle19at39 = new HeightIsle(nr19at39, cornerMaxima);

		nr19at40 = Arrays.asList(at40);
		heightIsle19at40 = new HeightIsle(nr19at40, cornerMaxima);

		nr19at41 = Arrays.asList(at41);
		heightIsle19at41 = new HeightIsle(nr19at41, cornerMaxima);

		nr19at42 = Arrays.asList(at42);
		heightIsle19at42 = new HeightIsle(nr19at42, cornerMaxima);

		nr19at43 = Arrays.asList(at43);
		heightIsle19at43 = new HeightIsle(nr19at43, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle19();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 43) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr19at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr19at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr19at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr19at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr19at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr19at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr19at06; break;
			case 7:
				rasterIndizesDescribingTheIsle = nr19at07; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr19at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr19at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr19at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr19at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr19at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr19at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr19at15; break;
			case 16:
				rasterIndizesDescribingTheIsle = nr19at16; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr19at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr19at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr19at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr19at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr19at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr19at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr19at24; break;
			case 25:
				rasterIndizesDescribingTheIsle = nr19at25; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr19at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr19at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr19at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr19at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr19at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr19at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr19at33; break;
			case 34:
				rasterIndizesDescribingTheIsle = nr19at34; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr19at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr19at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr19at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr19at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr19at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr19at41; break;
			case 42:
				rasterIndizesDescribingTheIsle = nr19at42; break;
			case 43:
				rasterIndizesDescribingTheIsle = nr19at43; break;
			default:
				continue;
			}
			if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;
	
		}
		
		return -1;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle19at00;
		case 1:
			return heightIsle19at01;
		case 2:
			return heightIsle19at02;
		case 3:
			return heightIsle19at03;
		case 4:
			return heightIsle19at04;
		case 5:
			return heightIsle19at05;
		case 6:
			return heightIsle19at06;
		case 7:
			return heightIsle19at07;
		case 9:
			return heightIsle19at09;
		case 10:
			return heightIsle19at10;
		case 11:
			return heightIsle19at11;
		case 12:
			return heightIsle19at12;
		case 13:
			return heightIsle19at13;
		case 14:
			return heightIsle19at14;
		case 15:
			return heightIsle19at15;
		case 16:
			return heightIsle19at16;
		case 18:
			return heightIsle19at18;
		case 19:
			return heightIsle19at19;
		case 20:
			return heightIsle19at20;
		case 21:
			return heightIsle19at21;
		case 22:
			return heightIsle19at22;
		case 23:
			return heightIsle19at23;
		case 24:
			return heightIsle19at24;
		case 25:
			return heightIsle19at25;
		case 27:
			return heightIsle19at27;
		case 28:
			return heightIsle19at28;
		case 29:
			return heightIsle19at29;
		case 30:
			return heightIsle19at30;
		case 31:
			return heightIsle19at31;
		case 32:
			return heightIsle19at32;
		case 33:
			return heightIsle19at33;
		case 34:
			return heightIsle19at34;
		case 36:
			return heightIsle19at36;
		case 37:
			return heightIsle19at37;
		case 38:
			return heightIsle19at38;
		case 39:
			return heightIsle19at39;
		case 40:
			return heightIsle19at40;
		case 41:
			return heightIsle19at41;
		case 42:
			return heightIsle19at42;
		case 43:
			return heightIsle19at43;
		default:
			return null;
		}
	}
}
