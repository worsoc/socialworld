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

public class SubIsle21 extends SubIsle {

	private static SubIsle21 instance;

	private final static Integer at00[]  = {0,  1,  
						9,  10, 
						18, 19, 
						27, 28, 
						36, 37, 
						45, 46};

	private final static Integer at01[]  = {1,  2,  
						10, 11, 
						19, 20, 
						28, 29, 
						37, 38, 
						46, 47};

	private final static Integer at02[]  = {2,  3,  
						11, 12, 
						20, 21, 
						29, 30, 
						38, 39, 
						47, 48};

	private final static Integer at03[]  = {3,  4,  
						12, 13, 
						21, 22, 
						30, 31, 
						39, 40, 
						48, 49};

	private final static Integer at04[]  = {4,  5,  
						13, 14, 
						22, 23, 
						31, 32, 
						40, 41, 
						49, 50};

	private final static Integer at05[]  = {5,  6,  
						14, 15, 
						23, 24, 
						32, 33, 
						41, 42, 
						50, 51};

	private final static Integer at06[]  = {6,  7,  
						15, 16, 
						24, 25, 
						33, 34, 
						42, 43, 
						51, 52};

	private final static Integer at07[]  = {7,  8,  
						16, 17, 
						25, 26, 
						34, 35, 
						43, 44, 
						52, 53};

	private final static Integer at09[]  = {9,  10, 
						18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55};

	private final static Integer at10[]  = {10, 11, 
						19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56};

	private final static Integer at11[]  = {11, 12, 
						20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57};

	private final static Integer at12[]  = {12, 13, 
						21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58};

	private final static Integer at13[]  = {13, 14, 
						22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59};

	private final static Integer at14[]  = {14, 15, 
						23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60};

	private final static Integer at15[]  = {15, 16, 
						24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61};

	private final static Integer at16[]  = {16, 17, 
						25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62};

	private final static Integer at18[]  = {18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55, 
						63, 64};

	private final static Integer at19[]  = {19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56, 
						64, 65};

	private final static Integer at20[]  = {20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57, 
						65, 66};

	private final static Integer at21[]  = {21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58, 
						66, 67};

	private final static Integer at22[]  = {22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59, 
						67, 68};

	private final static Integer at23[]  = {23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60, 
						68, 69};

	private final static Integer at24[]  = {24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61, 
						69, 70};

	private final static Integer at25[]  = {25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62, 
						70, 71};

	private final static Integer at27[]  = {27, 28, 
						36, 37, 
						45, 46, 
						54, 55, 
						63, 64, 
						72, 73};

	private final static Integer at28[]  = {28, 29, 
						37, 38, 
						46, 47, 
						55, 56, 
						64, 65, 
						73, 74};

	private final static Integer at29[]  = {29, 30, 
						38, 39, 
						47, 48, 
						56, 57, 
						65, 66, 
						74, 75};

	private final static Integer at30[]  = {30, 31, 
						39, 40, 
						48, 49, 
						57, 58, 
						66, 67, 
						75, 76};

	private final static Integer at31[]  = {31, 32, 
						40, 41, 
						49, 50, 
						58, 59, 
						67, 68, 
						76, 77};

	private final static Integer at32[]  = {32, 33, 
						41, 42, 
						50, 51, 
						59, 60, 
						68, 69, 
						77, 78};

	private final static Integer at33[]  = {33, 34, 
						42, 43, 
						51, 52, 
						60, 61, 
						69, 70, 
						78, 79};

	private final static Integer at34[]  = {34, 35, 
						43, 44, 
						52, 53, 
						61, 62, 
						70, 71, 
						79, 80};

	private List<Integer> nr21at00;
	private HeightIsle heightIsle21at00;

	private List<Integer> nr21at01;
	private HeightIsle heightIsle21at01;

	private List<Integer> nr21at02;
	private HeightIsle heightIsle21at02;

	private List<Integer> nr21at03;
	private HeightIsle heightIsle21at03;

	private List<Integer> nr21at04;
	private HeightIsle heightIsle21at04;

	private List<Integer> nr21at05;
	private HeightIsle heightIsle21at05;

	private List<Integer> nr21at06;
	private HeightIsle heightIsle21at06;

	private List<Integer> nr21at07;
	private HeightIsle heightIsle21at07;

	private List<Integer> nr21at09;
	private HeightIsle heightIsle21at09;

	private List<Integer> nr21at10;
	private HeightIsle heightIsle21at10;

	private List<Integer> nr21at11;
	private HeightIsle heightIsle21at11;

	private List<Integer> nr21at12;
	private HeightIsle heightIsle21at12;

	private List<Integer> nr21at13;
	private HeightIsle heightIsle21at13;

	private List<Integer> nr21at14;
	private HeightIsle heightIsle21at14;

	private List<Integer> nr21at15;
	private HeightIsle heightIsle21at15;

	private List<Integer> nr21at16;
	private HeightIsle heightIsle21at16;

	private List<Integer> nr21at18;
	private HeightIsle heightIsle21at18;

	private List<Integer> nr21at19;
	private HeightIsle heightIsle21at19;

	private List<Integer> nr21at20;
	private HeightIsle heightIsle21at20;

	private List<Integer> nr21at21;
	private HeightIsle heightIsle21at21;

	private List<Integer> nr21at22;
	private HeightIsle heightIsle21at22;

	private List<Integer> nr21at23;
	private HeightIsle heightIsle21at23;

	private List<Integer> nr21at24;
	private HeightIsle heightIsle21at24;

	private List<Integer> nr21at25;
	private HeightIsle heightIsle21at25;

	private List<Integer> nr21at27;
	private HeightIsle heightIsle21at27;

	private List<Integer> nr21at28;
	private HeightIsle heightIsle21at28;

	private List<Integer> nr21at29;
	private HeightIsle heightIsle21at29;

	private List<Integer> nr21at30;
	private HeightIsle heightIsle21at30;

	private List<Integer> nr21at31;
	private HeightIsle heightIsle21at31;

	private List<Integer> nr21at32;
	private HeightIsle heightIsle21at32;

	private List<Integer> nr21at33;
	private HeightIsle heightIsle21at33;

	private List<Integer> nr21at34;
	private HeightIsle heightIsle21at34;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11194,	
			19914, 91194,
			19914, 91194,
			19914, 91194,
			19914, 91194,
			19114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle21() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr21at00 = Arrays.asList(at00);
		heightIsle21at00 = new HeightIsle(nr21at00, cornerMaxima);

		nr21at01 = Arrays.asList(at01);
		heightIsle21at01 = new HeightIsle(nr21at01, cornerMaxima);

		nr21at02 = Arrays.asList(at02);
		heightIsle21at02 = new HeightIsle(nr21at02, cornerMaxima);

		nr21at03 = Arrays.asList(at03);
		heightIsle21at03 = new HeightIsle(nr21at03, cornerMaxima);

		nr21at04 = Arrays.asList(at04);
		heightIsle21at04 = new HeightIsle(nr21at04, cornerMaxima);

		nr21at05 = Arrays.asList(at05);
		heightIsle21at05 = new HeightIsle(nr21at05, cornerMaxima);

		nr21at06 = Arrays.asList(at06);
		heightIsle21at06 = new HeightIsle(nr21at06, cornerMaxima);

		nr21at07 = Arrays.asList(at07);
		heightIsle21at07 = new HeightIsle(nr21at07, cornerMaxima);

		nr21at09 = Arrays.asList(at09);
		heightIsle21at09 = new HeightIsle(nr21at09, cornerMaxima);

		nr21at10 = Arrays.asList(at10);
		heightIsle21at10 = new HeightIsle(nr21at10, cornerMaxima);

		nr21at11 = Arrays.asList(at11);
		heightIsle21at11 = new HeightIsle(nr21at11, cornerMaxima);

		nr21at12 = Arrays.asList(at12);
		heightIsle21at12 = new HeightIsle(nr21at12, cornerMaxima);

		nr21at13 = Arrays.asList(at13);
		heightIsle21at13 = new HeightIsle(nr21at13, cornerMaxima);

		nr21at14 = Arrays.asList(at14);
		heightIsle21at14 = new HeightIsle(nr21at14, cornerMaxima);

		nr21at15 = Arrays.asList(at15);
		heightIsle21at15 = new HeightIsle(nr21at15, cornerMaxima);

		nr21at16 = Arrays.asList(at16);
		heightIsle21at16 = new HeightIsle(nr21at16, cornerMaxima);

		nr21at18 = Arrays.asList(at18);
		heightIsle21at18 = new HeightIsle(nr21at18, cornerMaxima);

		nr21at19 = Arrays.asList(at19);
		heightIsle21at19 = new HeightIsle(nr21at19, cornerMaxima);

		nr21at20 = Arrays.asList(at20);
		heightIsle21at20 = new HeightIsle(nr21at20, cornerMaxima);

		nr21at21 = Arrays.asList(at21);
		heightIsle21at21 = new HeightIsle(nr21at21, cornerMaxima);

		nr21at22 = Arrays.asList(at22);
		heightIsle21at22 = new HeightIsle(nr21at22, cornerMaxima);

		nr21at23 = Arrays.asList(at23);
		heightIsle21at23 = new HeightIsle(nr21at23, cornerMaxima);

		nr21at24 = Arrays.asList(at24);
		heightIsle21at24 = new HeightIsle(nr21at24, cornerMaxima);

		nr21at25 = Arrays.asList(at25);
		heightIsle21at25 = new HeightIsle(nr21at25, cornerMaxima);

		nr21at27 = Arrays.asList(at27);
		heightIsle21at27 = new HeightIsle(nr21at27, cornerMaxima);

		nr21at28 = Arrays.asList(at28);
		heightIsle21at28 = new HeightIsle(nr21at28, cornerMaxima);

		nr21at29 = Arrays.asList(at29);
		heightIsle21at29 = new HeightIsle(nr21at29, cornerMaxima);

		nr21at30 = Arrays.asList(at30);
		heightIsle21at30 = new HeightIsle(nr21at30, cornerMaxima);

		nr21at31 = Arrays.asList(at31);
		heightIsle21at31 = new HeightIsle(nr21at31, cornerMaxima);

		nr21at32 = Arrays.asList(at32);
		heightIsle21at32 = new HeightIsle(nr21at32, cornerMaxima);

		nr21at33 = Arrays.asList(at33);
		heightIsle21at33 = new HeightIsle(nr21at33, cornerMaxima);

		nr21at34 = Arrays.asList(at34);
		heightIsle21at34 = new HeightIsle(nr21at34, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle21();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 34) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr21at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr21at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr21at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr21at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr21at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr21at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr21at06; break;
			case 7:
				rasterIndizesDescribingTheIsle = nr21at07; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr21at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr21at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr21at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr21at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr21at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr21at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr21at15; break;
			case 16:
				rasterIndizesDescribingTheIsle = nr21at16; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr21at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr21at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr21at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr21at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr21at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr21at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr21at24; break;
			case 25:
				rasterIndizesDescribingTheIsle = nr21at25; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr21at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr21at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr21at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr21at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr21at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr21at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr21at33; break;
			case 34:
				rasterIndizesDescribingTheIsle = nr21at34; break;
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
			return heightIsle21at00;
		case 1:
			return heightIsle21at01;
		case 2:
			return heightIsle21at02;
		case 3:
			return heightIsle21at03;
		case 4:
			return heightIsle21at04;
		case 5:
			return heightIsle21at05;
		case 6:
			return heightIsle21at06;
		case 7:
			return heightIsle21at07;
		case 9:
			return heightIsle21at09;
		case 10:
			return heightIsle21at10;
		case 11:
			return heightIsle21at11;
		case 12:
			return heightIsle21at12;
		case 13:
			return heightIsle21at13;
		case 14:
			return heightIsle21at14;
		case 15:
			return heightIsle21at15;
		case 16:
			return heightIsle21at16;
		case 18:
			return heightIsle21at18;
		case 19:
			return heightIsle21at19;
		case 20:
			return heightIsle21at20;
		case 21:
			return heightIsle21at21;
		case 22:
			return heightIsle21at22;
		case 23:
			return heightIsle21at23;
		case 24:
			return heightIsle21at24;
		case 25:
			return heightIsle21at25;
		case 27:
			return heightIsle21at27;
		case 28:
			return heightIsle21at28;
		case 29:
			return heightIsle21at29;
		case 30:
			return heightIsle21at30;
		case 31:
			return heightIsle21at31;
		case 32:
			return heightIsle21at32;
		case 33:
			return heightIsle21at33;
		case 34:
			return heightIsle21at34;
		default:
			return null;
		}
	}
}
