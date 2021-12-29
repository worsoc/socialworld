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

public class SubIsle34 extends SubIsle {

	private static SubIsle34 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53};

	private final static Integer at27[]  = {27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at28[]  = {28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at29[]  = {29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at30[]  = {30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at31[]  = {31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at32[]  = {32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at36[]  = {36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at37[]  = {37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at38[]  = {38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at39[]  = {39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at40[]  = {40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at41[]  = {41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at45[]  = {45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at46[]  = {46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at47[]  = {47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at48[]  = {48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at49[]  = {49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at50[]  = {50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr34at00;
	private HeightIsle heightIsle34at00;

	private List<Integer> nr34at01;
	private HeightIsle heightIsle34at01;

	private List<Integer> nr34at02;
	private HeightIsle heightIsle34at02;

	private List<Integer> nr34at03;
	private HeightIsle heightIsle34at03;

	private List<Integer> nr34at04;
	private HeightIsle heightIsle34at04;

	private List<Integer> nr34at05;
	private HeightIsle heightIsle34at05;

	private List<Integer> nr34at09;
	private HeightIsle heightIsle34at09;

	private List<Integer> nr34at10;
	private HeightIsle heightIsle34at10;

	private List<Integer> nr34at11;
	private HeightIsle heightIsle34at11;

	private List<Integer> nr34at12;
	private HeightIsle heightIsle34at12;

	private List<Integer> nr34at13;
	private HeightIsle heightIsle34at13;

	private List<Integer> nr34at14;
	private HeightIsle heightIsle34at14;

	private List<Integer> nr34at18;
	private HeightIsle heightIsle34at18;

	private List<Integer> nr34at19;
	private HeightIsle heightIsle34at19;

	private List<Integer> nr34at20;
	private HeightIsle heightIsle34at20;

	private List<Integer> nr34at21;
	private HeightIsle heightIsle34at21;

	private List<Integer> nr34at22;
	private HeightIsle heightIsle34at22;

	private List<Integer> nr34at23;
	private HeightIsle heightIsle34at23;

	private List<Integer> nr34at27;
	private HeightIsle heightIsle34at27;

	private List<Integer> nr34at28;
	private HeightIsle heightIsle34at28;

	private List<Integer> nr34at29;
	private HeightIsle heightIsle34at29;

	private List<Integer> nr34at30;
	private HeightIsle heightIsle34at30;

	private List<Integer> nr34at31;
	private HeightIsle heightIsle34at31;

	private List<Integer> nr34at32;
	private HeightIsle heightIsle34at32;

	private List<Integer> nr34at36;
	private HeightIsle heightIsle34at36;

	private List<Integer> nr34at37;
	private HeightIsle heightIsle34at37;

	private List<Integer> nr34at38;
	private HeightIsle heightIsle34at38;

	private List<Integer> nr34at39;
	private HeightIsle heightIsle34at39;

	private List<Integer> nr34at40;
	private HeightIsle heightIsle34at40;

	private List<Integer> nr34at41;
	private HeightIsle heightIsle34at41;

	private List<Integer> nr34at45;
	private HeightIsle heightIsle34at45;

	private List<Integer> nr34at46;
	private HeightIsle heightIsle34at46;

	private List<Integer> nr34at47;
	private HeightIsle heightIsle34at47;

	private List<Integer> nr34at48;
	private HeightIsle heightIsle34at48;

	private List<Integer> nr34at49;
	private HeightIsle heightIsle34at49;

	private List<Integer> nr34at50;
	private HeightIsle heightIsle34at50;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11194,	
			19914, 11914, 11194, 91194,
			19914, 19114, 91114, 91194,
			19114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle34() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr34at00 = Arrays.asList(at00);
		heightIsle34at00 = new HeightIsle(nr34at00, cornerMaxima);

		nr34at01 = Arrays.asList(at01);
		heightIsle34at01 = new HeightIsle(nr34at01, cornerMaxima);

		nr34at02 = Arrays.asList(at02);
		heightIsle34at02 = new HeightIsle(nr34at02, cornerMaxima);

		nr34at03 = Arrays.asList(at03);
		heightIsle34at03 = new HeightIsle(nr34at03, cornerMaxima);

		nr34at04 = Arrays.asList(at04);
		heightIsle34at04 = new HeightIsle(nr34at04, cornerMaxima);

		nr34at05 = Arrays.asList(at05);
		heightIsle34at05 = new HeightIsle(nr34at05, cornerMaxima);

		nr34at09 = Arrays.asList(at09);
		heightIsle34at09 = new HeightIsle(nr34at09, cornerMaxima);

		nr34at10 = Arrays.asList(at10);
		heightIsle34at10 = new HeightIsle(nr34at10, cornerMaxima);

		nr34at11 = Arrays.asList(at11);
		heightIsle34at11 = new HeightIsle(nr34at11, cornerMaxima);

		nr34at12 = Arrays.asList(at12);
		heightIsle34at12 = new HeightIsle(nr34at12, cornerMaxima);

		nr34at13 = Arrays.asList(at13);
		heightIsle34at13 = new HeightIsle(nr34at13, cornerMaxima);

		nr34at14 = Arrays.asList(at14);
		heightIsle34at14 = new HeightIsle(nr34at14, cornerMaxima);

		nr34at18 = Arrays.asList(at18);
		heightIsle34at18 = new HeightIsle(nr34at18, cornerMaxima);

		nr34at19 = Arrays.asList(at19);
		heightIsle34at19 = new HeightIsle(nr34at19, cornerMaxima);

		nr34at20 = Arrays.asList(at20);
		heightIsle34at20 = new HeightIsle(nr34at20, cornerMaxima);

		nr34at21 = Arrays.asList(at21);
		heightIsle34at21 = new HeightIsle(nr34at21, cornerMaxima);

		nr34at22 = Arrays.asList(at22);
		heightIsle34at22 = new HeightIsle(nr34at22, cornerMaxima);

		nr34at23 = Arrays.asList(at23);
		heightIsle34at23 = new HeightIsle(nr34at23, cornerMaxima);

		nr34at27 = Arrays.asList(at27);
		heightIsle34at27 = new HeightIsle(nr34at27, cornerMaxima);

		nr34at28 = Arrays.asList(at28);
		heightIsle34at28 = new HeightIsle(nr34at28, cornerMaxima);

		nr34at29 = Arrays.asList(at29);
		heightIsle34at29 = new HeightIsle(nr34at29, cornerMaxima);

		nr34at30 = Arrays.asList(at30);
		heightIsle34at30 = new HeightIsle(nr34at30, cornerMaxima);

		nr34at31 = Arrays.asList(at31);
		heightIsle34at31 = new HeightIsle(nr34at31, cornerMaxima);

		nr34at32 = Arrays.asList(at32);
		heightIsle34at32 = new HeightIsle(nr34at32, cornerMaxima);

		nr34at36 = Arrays.asList(at36);
		heightIsle34at36 = new HeightIsle(nr34at36, cornerMaxima);

		nr34at37 = Arrays.asList(at37);
		heightIsle34at37 = new HeightIsle(nr34at37, cornerMaxima);

		nr34at38 = Arrays.asList(at38);
		heightIsle34at38 = new HeightIsle(nr34at38, cornerMaxima);

		nr34at39 = Arrays.asList(at39);
		heightIsle34at39 = new HeightIsle(nr34at39, cornerMaxima);

		nr34at40 = Arrays.asList(at40);
		heightIsle34at40 = new HeightIsle(nr34at40, cornerMaxima);

		nr34at41 = Arrays.asList(at41);
		heightIsle34at41 = new HeightIsle(nr34at41, cornerMaxima);

		nr34at45 = Arrays.asList(at45);
		heightIsle34at45 = new HeightIsle(nr34at45, cornerMaxima);

		nr34at46 = Arrays.asList(at46);
		heightIsle34at46 = new HeightIsle(nr34at46, cornerMaxima);

		nr34at47 = Arrays.asList(at47);
		heightIsle34at47 = new HeightIsle(nr34at47, cornerMaxima);

		nr34at48 = Arrays.asList(at48);
		heightIsle34at48 = new HeightIsle(nr34at48, cornerMaxima);

		nr34at49 = Arrays.asList(at49);
		heightIsle34at49 = new HeightIsle(nr34at49, cornerMaxima);

		nr34at50 = Arrays.asList(at50);
		heightIsle34at50 = new HeightIsle(nr34at50, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle34();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 50) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr34at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr34at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr34at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr34at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr34at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr34at05; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr34at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr34at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr34at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr34at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr34at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr34at14; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr34at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr34at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr34at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr34at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr34at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr34at23; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr34at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr34at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr34at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr34at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr34at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr34at32; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr34at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr34at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr34at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr34at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr34at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr34at41; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr34at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr34at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr34at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr34at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr34at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr34at50; break;
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
			return heightIsle34at00;
		case 1:
			return heightIsle34at01;
		case 2:
			return heightIsle34at02;
		case 3:
			return heightIsle34at03;
		case 4:
			return heightIsle34at04;
		case 5:
			return heightIsle34at05;
		case 9:
			return heightIsle34at09;
		case 10:
			return heightIsle34at10;
		case 11:
			return heightIsle34at11;
		case 12:
			return heightIsle34at12;
		case 13:
			return heightIsle34at13;
		case 14:
			return heightIsle34at14;
		case 18:
			return heightIsle34at18;
		case 19:
			return heightIsle34at19;
		case 20:
			return heightIsle34at20;
		case 21:
			return heightIsle34at21;
		case 22:
			return heightIsle34at22;
		case 23:
			return heightIsle34at23;
		case 27:
			return heightIsle34at27;
		case 28:
			return heightIsle34at28;
		case 29:
			return heightIsle34at29;
		case 30:
			return heightIsle34at30;
		case 31:
			return heightIsle34at31;
		case 32:
			return heightIsle34at32;
		case 36:
			return heightIsle34at36;
		case 37:
			return heightIsle34at37;
		case 38:
			return heightIsle34at38;
		case 39:
			return heightIsle34at39;
		case 40:
			return heightIsle34at40;
		case 41:
			return heightIsle34at41;
		case 45:
			return heightIsle34at45;
		case 46:
			return heightIsle34at46;
		case 47:
			return heightIsle34at47;
		case 48:
			return heightIsle34at48;
		case 49:
			return heightIsle34at49;
		case 50:
			return heightIsle34at50;
		default:
			return null;
		}
	}
}
