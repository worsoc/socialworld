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

public class SubIsle30 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  
						9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71};

	private final static Integer at27[]  = {27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65, 
						72, 73, 74};

	private final static Integer at28[]  = {28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66, 
						73, 74, 75};

	private final static Integer at29[]  = {29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67, 
						74, 75, 76};

	private final static Integer at30[]  = {30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68, 
						75, 76, 77};

	private final static Integer at31[]  = {31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69, 
						76, 77, 78};

	private final static Integer at32[]  = {32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70, 
						77, 78, 79};

	private final static Integer at33[]  = {33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr30at00;
	private HeightIsle heightIsle30at00;

	private List<Integer> nr30at01;
	private HeightIsle heightIsle30at01;

	private List<Integer> nr30at02;
	private HeightIsle heightIsle30at02;

	private List<Integer> nr30at03;
	private HeightIsle heightIsle30at03;

	private List<Integer> nr30at04;
	private HeightIsle heightIsle30at04;

	private List<Integer> nr30at05;
	private HeightIsle heightIsle30at05;

	private List<Integer> nr30at06;
	private HeightIsle heightIsle30at06;

	private List<Integer> nr30at09;
	private HeightIsle heightIsle30at09;

	private List<Integer> nr30at10;
	private HeightIsle heightIsle30at10;

	private List<Integer> nr30at11;
	private HeightIsle heightIsle30at11;

	private List<Integer> nr30at12;
	private HeightIsle heightIsle30at12;

	private List<Integer> nr30at13;
	private HeightIsle heightIsle30at13;

	private List<Integer> nr30at14;
	private HeightIsle heightIsle30at14;

	private List<Integer> nr30at15;
	private HeightIsle heightIsle30at15;

	private List<Integer> nr30at18;
	private HeightIsle heightIsle30at18;

	private List<Integer> nr30at19;
	private HeightIsle heightIsle30at19;

	private List<Integer> nr30at20;
	private HeightIsle heightIsle30at20;

	private List<Integer> nr30at21;
	private HeightIsle heightIsle30at21;

	private List<Integer> nr30at22;
	private HeightIsle heightIsle30at22;

	private List<Integer> nr30at23;
	private HeightIsle heightIsle30at23;

	private List<Integer> nr30at24;
	private HeightIsle heightIsle30at24;

	private List<Integer> nr30at27;
	private HeightIsle heightIsle30at27;

	private List<Integer> nr30at28;
	private HeightIsle heightIsle30at28;

	private List<Integer> nr30at29;
	private HeightIsle heightIsle30at29;

	private List<Integer> nr30at30;
	private HeightIsle heightIsle30at30;

	private List<Integer> nr30at31;
	private HeightIsle heightIsle30at31;

	private List<Integer> nr30at32;
	private HeightIsle heightIsle30at32;

	private List<Integer> nr30at33;
	private HeightIsle heightIsle30at33;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11194,	
			19914, 11114, 91194,
			19914, 11114, 91194,
			19914, 11114, 91194,
			19914, 11114, 91194,
			19114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle30() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr30at00 = Arrays.asList(at00);
		heightIsle30at00 = new HeightIsle(nr30at00, cornerMaxima);

		nr30at01 = Arrays.asList(at01);
		heightIsle30at01 = new HeightIsle(nr30at01, cornerMaxima);

		nr30at02 = Arrays.asList(at02);
		heightIsle30at02 = new HeightIsle(nr30at02, cornerMaxima);

		nr30at03 = Arrays.asList(at03);
		heightIsle30at03 = new HeightIsle(nr30at03, cornerMaxima);

		nr30at04 = Arrays.asList(at04);
		heightIsle30at04 = new HeightIsle(nr30at04, cornerMaxima);

		nr30at05 = Arrays.asList(at05);
		heightIsle30at05 = new HeightIsle(nr30at05, cornerMaxima);

		nr30at06 = Arrays.asList(at06);
		heightIsle30at06 = new HeightIsle(nr30at06, cornerMaxima);

		nr30at09 = Arrays.asList(at09);
		heightIsle30at09 = new HeightIsle(nr30at09, cornerMaxima);

		nr30at10 = Arrays.asList(at10);
		heightIsle30at10 = new HeightIsle(nr30at10, cornerMaxima);

		nr30at11 = Arrays.asList(at11);
		heightIsle30at11 = new HeightIsle(nr30at11, cornerMaxima);

		nr30at12 = Arrays.asList(at12);
		heightIsle30at12 = new HeightIsle(nr30at12, cornerMaxima);

		nr30at13 = Arrays.asList(at13);
		heightIsle30at13 = new HeightIsle(nr30at13, cornerMaxima);

		nr30at14 = Arrays.asList(at14);
		heightIsle30at14 = new HeightIsle(nr30at14, cornerMaxima);

		nr30at15 = Arrays.asList(at15);
		heightIsle30at15 = new HeightIsle(nr30at15, cornerMaxima);

		nr30at18 = Arrays.asList(at18);
		heightIsle30at18 = new HeightIsle(nr30at18, cornerMaxima);

		nr30at19 = Arrays.asList(at19);
		heightIsle30at19 = new HeightIsle(nr30at19, cornerMaxima);

		nr30at20 = Arrays.asList(at20);
		heightIsle30at20 = new HeightIsle(nr30at20, cornerMaxima);

		nr30at21 = Arrays.asList(at21);
		heightIsle30at21 = new HeightIsle(nr30at21, cornerMaxima);

		nr30at22 = Arrays.asList(at22);
		heightIsle30at22 = new HeightIsle(nr30at22, cornerMaxima);

		nr30at23 = Arrays.asList(at23);
		heightIsle30at23 = new HeightIsle(nr30at23, cornerMaxima);

		nr30at24 = Arrays.asList(at24);
		heightIsle30at24 = new HeightIsle(nr30at24, cornerMaxima);

		nr30at27 = Arrays.asList(at27);
		heightIsle30at27 = new HeightIsle(nr30at27, cornerMaxima);

		nr30at28 = Arrays.asList(at28);
		heightIsle30at28 = new HeightIsle(nr30at28, cornerMaxima);

		nr30at29 = Arrays.asList(at29);
		heightIsle30at29 = new HeightIsle(nr30at29, cornerMaxima);

		nr30at30 = Arrays.asList(at30);
		heightIsle30at30 = new HeightIsle(nr30at30, cornerMaxima);

		nr30at31 = Arrays.asList(at31);
		heightIsle30at31 = new HeightIsle(nr30at31, cornerMaxima);

		nr30at32 = Arrays.asList(at32);
		heightIsle30at32 = new HeightIsle(nr30at32, cornerMaxima);

		nr30at33 = Arrays.asList(at33);
		heightIsle30at33 = new HeightIsle(nr30at33, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle30();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 33) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr30at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr30at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr30at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr30at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr30at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr30at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr30at06; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr30at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr30at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr30at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr30at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr30at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr30at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr30at15; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr30at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr30at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr30at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr30at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr30at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr30at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr30at24; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr30at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr30at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr30at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr30at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr30at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr30at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr30at33; break;
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
			return heightIsle30at00;
		case 1:
			return heightIsle30at01;
		case 2:
			return heightIsle30at02;
		case 3:
			return heightIsle30at03;
		case 4:
			return heightIsle30at04;
		case 5:
			return heightIsle30at05;
		case 6:
			return heightIsle30at06;
		case 9:
			return heightIsle30at09;
		case 10:
			return heightIsle30at10;
		case 11:
			return heightIsle30at11;
		case 12:
			return heightIsle30at12;
		case 13:
			return heightIsle30at13;
		case 14:
			return heightIsle30at14;
		case 15:
			return heightIsle30at15;
		case 18:
			return heightIsle30at18;
		case 19:
			return heightIsle30at19;
		case 20:
			return heightIsle30at20;
		case 21:
			return heightIsle30at21;
		case 22:
			return heightIsle30at22;
		case 23:
			return heightIsle30at23;
		case 24:
			return heightIsle30at24;
		case 27:
			return heightIsle30at27;
		case 28:
			return heightIsle30at28;
		case 29:
			return heightIsle30at29;
		case 30:
			return heightIsle30at30;
		case 31:
			return heightIsle30at31;
		case 32:
			return heightIsle30at32;
		case 33:
			return heightIsle30at33;
		default:
			return null;
		}
	}
}
