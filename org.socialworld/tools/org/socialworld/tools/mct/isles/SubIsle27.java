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

public class SubIsle27 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9,  10, 11, 12, 
						18, 19, 20, 21};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13, 
						19, 20, 21, 22};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14, 
						20, 21, 22, 23};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15, 
						21, 22, 23, 24};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16, 
						22, 23, 24, 25};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17, 
						23, 24, 25, 26};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44};

	private final static Integer at27[]  = {27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48};

	private final static Integer at28[]  = {28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49};

	private final static Integer at29[]  = {29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50};

	private final static Integer at30[]  = {30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51};

	private final static Integer at31[]  = {31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52};

	private final static Integer at32[]  = {32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53};

	private final static Integer at36[]  = {36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at37[]  = {37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at38[]  = {38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at39[]  = {39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at40[]  = {40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at41[]  = {41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at45[]  = {45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at46[]  = {46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at47[]  = {47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at48[]  = {48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at49[]  = {49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at50[]  = {50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at54[]  = {54, 55, 56, 57, 
						63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at55[]  = {55, 56, 57, 58, 
						64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at56[]  = {56, 57, 58, 59, 
						65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at57[]  = {57, 58, 59, 60, 
						66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at58[]  = {58, 59, 60, 61, 
						67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at59[]  = {59, 60, 61, 62, 
						68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr27at00;
	private HeightIsle heightIsle27at00;

	private List<Integer> nr27at01;
	private HeightIsle heightIsle27at01;

	private List<Integer> nr27at02;
	private HeightIsle heightIsle27at02;

	private List<Integer> nr27at03;
	private HeightIsle heightIsle27at03;

	private List<Integer> nr27at04;
	private HeightIsle heightIsle27at04;

	private List<Integer> nr27at05;
	private HeightIsle heightIsle27at05;

	private List<Integer> nr27at09;
	private HeightIsle heightIsle27at09;

	private List<Integer> nr27at10;
	private HeightIsle heightIsle27at10;

	private List<Integer> nr27at11;
	private HeightIsle heightIsle27at11;

	private List<Integer> nr27at12;
	private HeightIsle heightIsle27at12;

	private List<Integer> nr27at13;
	private HeightIsle heightIsle27at13;

	private List<Integer> nr27at14;
	private HeightIsle heightIsle27at14;

	private List<Integer> nr27at18;
	private HeightIsle heightIsle27at18;

	private List<Integer> nr27at19;
	private HeightIsle heightIsle27at19;

	private List<Integer> nr27at20;
	private HeightIsle heightIsle27at20;

	private List<Integer> nr27at21;
	private HeightIsle heightIsle27at21;

	private List<Integer> nr27at22;
	private HeightIsle heightIsle27at22;

	private List<Integer> nr27at23;
	private HeightIsle heightIsle27at23;

	private List<Integer> nr27at27;
	private HeightIsle heightIsle27at27;

	private List<Integer> nr27at28;
	private HeightIsle heightIsle27at28;

	private List<Integer> nr27at29;
	private HeightIsle heightIsle27at29;

	private List<Integer> nr27at30;
	private HeightIsle heightIsle27at30;

	private List<Integer> nr27at31;
	private HeightIsle heightIsle27at31;

	private List<Integer> nr27at32;
	private HeightIsle heightIsle27at32;

	private List<Integer> nr27at36;
	private HeightIsle heightIsle27at36;

	private List<Integer> nr27at37;
	private HeightIsle heightIsle27at37;

	private List<Integer> nr27at38;
	private HeightIsle heightIsle27at38;

	private List<Integer> nr27at39;
	private HeightIsle heightIsle27at39;

	private List<Integer> nr27at40;
	private HeightIsle heightIsle27at40;

	private List<Integer> nr27at41;
	private HeightIsle heightIsle27at41;

	private List<Integer> nr27at45;
	private HeightIsle heightIsle27at45;

	private List<Integer> nr27at46;
	private HeightIsle heightIsle27at46;

	private List<Integer> nr27at47;
	private HeightIsle heightIsle27at47;

	private List<Integer> nr27at48;
	private HeightIsle heightIsle27at48;

	private List<Integer> nr27at49;
	private HeightIsle heightIsle27at49;

	private List<Integer> nr27at50;
	private HeightIsle heightIsle27at50;

	private List<Integer> nr27at54;
	private HeightIsle heightIsle27at54;

	private List<Integer> nr27at55;
	private HeightIsle heightIsle27at55;

	private List<Integer> nr27at56;
	private HeightIsle heightIsle27at56;

	private List<Integer> nr27at57;
	private HeightIsle heightIsle27at57;

	private List<Integer> nr27at58;
	private HeightIsle heightIsle27at58;

	private List<Integer> nr27at59;
	private HeightIsle heightIsle27at59;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle27() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr27at00 = Arrays.asList(at00);
		heightIsle27at00 = new HeightIsle(nr27at00, cornerMaxima);

		nr27at01 = Arrays.asList(at01);
		heightIsle27at01 = new HeightIsle(nr27at01, cornerMaxima);

		nr27at02 = Arrays.asList(at02);
		heightIsle27at02 = new HeightIsle(nr27at02, cornerMaxima);

		nr27at03 = Arrays.asList(at03);
		heightIsle27at03 = new HeightIsle(nr27at03, cornerMaxima);

		nr27at04 = Arrays.asList(at04);
		heightIsle27at04 = new HeightIsle(nr27at04, cornerMaxima);

		nr27at05 = Arrays.asList(at05);
		heightIsle27at05 = new HeightIsle(nr27at05, cornerMaxima);

		nr27at09 = Arrays.asList(at09);
		heightIsle27at09 = new HeightIsle(nr27at09, cornerMaxima);

		nr27at10 = Arrays.asList(at10);
		heightIsle27at10 = new HeightIsle(nr27at10, cornerMaxima);

		nr27at11 = Arrays.asList(at11);
		heightIsle27at11 = new HeightIsle(nr27at11, cornerMaxima);

		nr27at12 = Arrays.asList(at12);
		heightIsle27at12 = new HeightIsle(nr27at12, cornerMaxima);

		nr27at13 = Arrays.asList(at13);
		heightIsle27at13 = new HeightIsle(nr27at13, cornerMaxima);

		nr27at14 = Arrays.asList(at14);
		heightIsle27at14 = new HeightIsle(nr27at14, cornerMaxima);

		nr27at18 = Arrays.asList(at18);
		heightIsle27at18 = new HeightIsle(nr27at18, cornerMaxima);

		nr27at19 = Arrays.asList(at19);
		heightIsle27at19 = new HeightIsle(nr27at19, cornerMaxima);

		nr27at20 = Arrays.asList(at20);
		heightIsle27at20 = new HeightIsle(nr27at20, cornerMaxima);

		nr27at21 = Arrays.asList(at21);
		heightIsle27at21 = new HeightIsle(nr27at21, cornerMaxima);

		nr27at22 = Arrays.asList(at22);
		heightIsle27at22 = new HeightIsle(nr27at22, cornerMaxima);

		nr27at23 = Arrays.asList(at23);
		heightIsle27at23 = new HeightIsle(nr27at23, cornerMaxima);

		nr27at27 = Arrays.asList(at27);
		heightIsle27at27 = new HeightIsle(nr27at27, cornerMaxima);

		nr27at28 = Arrays.asList(at28);
		heightIsle27at28 = new HeightIsle(nr27at28, cornerMaxima);

		nr27at29 = Arrays.asList(at29);
		heightIsle27at29 = new HeightIsle(nr27at29, cornerMaxima);

		nr27at30 = Arrays.asList(at30);
		heightIsle27at30 = new HeightIsle(nr27at30, cornerMaxima);

		nr27at31 = Arrays.asList(at31);
		heightIsle27at31 = new HeightIsle(nr27at31, cornerMaxima);

		nr27at32 = Arrays.asList(at32);
		heightIsle27at32 = new HeightIsle(nr27at32, cornerMaxima);

		nr27at36 = Arrays.asList(at36);
		heightIsle27at36 = new HeightIsle(nr27at36, cornerMaxima);

		nr27at37 = Arrays.asList(at37);
		heightIsle27at37 = new HeightIsle(nr27at37, cornerMaxima);

		nr27at38 = Arrays.asList(at38);
		heightIsle27at38 = new HeightIsle(nr27at38, cornerMaxima);

		nr27at39 = Arrays.asList(at39);
		heightIsle27at39 = new HeightIsle(nr27at39, cornerMaxima);

		nr27at40 = Arrays.asList(at40);
		heightIsle27at40 = new HeightIsle(nr27at40, cornerMaxima);

		nr27at41 = Arrays.asList(at41);
		heightIsle27at41 = new HeightIsle(nr27at41, cornerMaxima);

		nr27at45 = Arrays.asList(at45);
		heightIsle27at45 = new HeightIsle(nr27at45, cornerMaxima);

		nr27at46 = Arrays.asList(at46);
		heightIsle27at46 = new HeightIsle(nr27at46, cornerMaxima);

		nr27at47 = Arrays.asList(at47);
		heightIsle27at47 = new HeightIsle(nr27at47, cornerMaxima);

		nr27at48 = Arrays.asList(at48);
		heightIsle27at48 = new HeightIsle(nr27at48, cornerMaxima);

		nr27at49 = Arrays.asList(at49);
		heightIsle27at49 = new HeightIsle(nr27at49, cornerMaxima);

		nr27at50 = Arrays.asList(at50);
		heightIsle27at50 = new HeightIsle(nr27at50, cornerMaxima);

		nr27at54 = Arrays.asList(at54);
		heightIsle27at54 = new HeightIsle(nr27at54, cornerMaxima);

		nr27at55 = Arrays.asList(at55);
		heightIsle27at55 = new HeightIsle(nr27at55, cornerMaxima);

		nr27at56 = Arrays.asList(at56);
		heightIsle27at56 = new HeightIsle(nr27at56, cornerMaxima);

		nr27at57 = Arrays.asList(at57);
		heightIsle27at57 = new HeightIsle(nr27at57, cornerMaxima);

		nr27at58 = Arrays.asList(at58);
		heightIsle27at58 = new HeightIsle(nr27at58, cornerMaxima);

		nr27at59 = Arrays.asList(at59);
		heightIsle27at59 = new HeightIsle(nr27at59, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle27();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 59) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr27at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr27at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr27at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr27at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr27at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr27at05; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr27at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr27at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr27at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr27at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr27at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr27at14; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr27at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr27at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr27at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr27at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr27at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr27at23; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr27at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr27at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr27at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr27at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr27at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr27at32; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr27at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr27at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr27at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr27at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr27at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr27at41; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr27at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr27at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr27at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr27at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr27at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr27at50; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr27at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr27at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr27at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr27at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr27at58; break;
			case 59:
				rasterIndizesDescribingTheIsle = nr27at59; break;
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
			return heightIsle27at00;
		case 1:
			return heightIsle27at01;
		case 2:
			return heightIsle27at02;
		case 3:
			return heightIsle27at03;
		case 4:
			return heightIsle27at04;
		case 5:
			return heightIsle27at05;
		case 9:
			return heightIsle27at09;
		case 10:
			return heightIsle27at10;
		case 11:
			return heightIsle27at11;
		case 12:
			return heightIsle27at12;
		case 13:
			return heightIsle27at13;
		case 14:
			return heightIsle27at14;
		case 18:
			return heightIsle27at18;
		case 19:
			return heightIsle27at19;
		case 20:
			return heightIsle27at20;
		case 21:
			return heightIsle27at21;
		case 22:
			return heightIsle27at22;
		case 23:
			return heightIsle27at23;
		case 27:
			return heightIsle27at27;
		case 28:
			return heightIsle27at28;
		case 29:
			return heightIsle27at29;
		case 30:
			return heightIsle27at30;
		case 31:
			return heightIsle27at31;
		case 32:
			return heightIsle27at32;
		case 36:
			return heightIsle27at36;
		case 37:
			return heightIsle27at37;
		case 38:
			return heightIsle27at38;
		case 39:
			return heightIsle27at39;
		case 40:
			return heightIsle27at40;
		case 41:
			return heightIsle27at41;
		case 45:
			return heightIsle27at45;
		case 46:
			return heightIsle27at46;
		case 47:
			return heightIsle27at47;
		case 48:
			return heightIsle27at48;
		case 49:
			return heightIsle27at49;
		case 50:
			return heightIsle27at50;
		case 54:
			return heightIsle27at54;
		case 55:
			return heightIsle27at55;
		case 56:
			return heightIsle27at56;
		case 57:
			return heightIsle27at57;
		case 58:
			return heightIsle27at58;
		case 59:
			return heightIsle27at59;
		default:
			return null;
		}
	}
}
