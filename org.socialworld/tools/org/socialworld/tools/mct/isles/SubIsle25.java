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

public class SubIsle25 extends SubIsle {

	private static SubIsle25 instance;

	private final static Integer at00[]  = {0,  1,  2,  
						9,  10, 11, 
						18, 19, 20};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12, 
						19, 20, 21};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13, 
						20, 21, 22};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14, 
						21, 22, 23};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15, 
						22, 23, 24};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16, 
						23, 24, 25};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17, 
						24, 25, 26};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20, 
						27, 28, 29};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21, 
						28, 29, 30};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22, 
						29, 30, 31};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23, 
						30, 31, 32};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24, 
						31, 32, 33};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25, 
						32, 33, 34};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26, 
						33, 34, 35};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29, 
						36, 37, 38};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30, 
						37, 38, 39};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31, 
						38, 39, 40};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32, 
						39, 40, 41};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33, 
						40, 41, 42};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34, 
						41, 42, 43};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35, 
						42, 43, 44};

	private final static Integer at27[]  = {27, 28, 29, 
						36, 37, 38, 
						45, 46, 47};

	private final static Integer at28[]  = {28, 29, 30, 
						37, 38, 39, 
						46, 47, 48};

	private final static Integer at29[]  = {29, 30, 31, 
						38, 39, 40, 
						47, 48, 49};

	private final static Integer at30[]  = {30, 31, 32, 
						39, 40, 41, 
						48, 49, 50};

	private final static Integer at31[]  = {31, 32, 33, 
						40, 41, 42, 
						49, 50, 51};

	private final static Integer at32[]  = {32, 33, 34, 
						41, 42, 43, 
						50, 51, 52};

	private final static Integer at33[]  = {33, 34, 35, 
						42, 43, 44, 
						51, 52, 53};

	private final static Integer at36[]  = {36, 37, 38, 
						45, 46, 47, 
						54, 55, 56};

	private final static Integer at37[]  = {37, 38, 39, 
						46, 47, 48, 
						55, 56, 57};

	private final static Integer at38[]  = {38, 39, 40, 
						47, 48, 49, 
						56, 57, 58};

	private final static Integer at39[]  = {39, 40, 41, 
						48, 49, 50, 
						57, 58, 59};

	private final static Integer at40[]  = {40, 41, 42, 
						49, 50, 51, 
						58, 59, 60};

	private final static Integer at41[]  = {41, 42, 43, 
						50, 51, 52, 
						59, 60, 61};

	private final static Integer at42[]  = {42, 43, 44, 
						51, 52, 53, 
						60, 61, 62};

	private final static Integer at45[]  = {45, 46, 47, 
						54, 55, 56, 
						63, 64, 65};

	private final static Integer at46[]  = {46, 47, 48, 
						55, 56, 57, 
						64, 65, 66};

	private final static Integer at47[]  = {47, 48, 49, 
						56, 57, 58, 
						65, 66, 67};

	private final static Integer at48[]  = {48, 49, 50, 
						57, 58, 59, 
						66, 67, 68};

	private final static Integer at49[]  = {49, 50, 51, 
						58, 59, 60, 
						67, 68, 69};

	private final static Integer at50[]  = {50, 51, 52, 
						59, 60, 61, 
						68, 69, 70};

	private final static Integer at51[]  = {51, 52, 53, 
						60, 61, 62, 
						69, 70, 71};

	private final static Integer at54[]  = {54, 55, 56, 
						63, 64, 65, 
						72, 73, 74};

	private final static Integer at55[]  = {55, 56, 57, 
						64, 65, 66, 
						73, 74, 75};

	private final static Integer at56[]  = {56, 57, 58, 
						65, 66, 67, 
						74, 75, 76};

	private final static Integer at57[]  = {57, 58, 59, 
						66, 67, 68, 
						75, 76, 77};

	private final static Integer at58[]  = {58, 59, 60, 
						67, 68, 69, 
						76, 77, 78};

	private final static Integer at59[]  = {59, 60, 61, 
						68, 69, 70, 
						77, 78, 79};

	private final static Integer at60[]  = {60, 61, 62, 
						69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr25at00;
	private HeightIsle heightIsle25at00;

	private List<Integer> nr25at01;
	private HeightIsle heightIsle25at01;

	private List<Integer> nr25at02;
	private HeightIsle heightIsle25at02;

	private List<Integer> nr25at03;
	private HeightIsle heightIsle25at03;

	private List<Integer> nr25at04;
	private HeightIsle heightIsle25at04;

	private List<Integer> nr25at05;
	private HeightIsle heightIsle25at05;

	private List<Integer> nr25at06;
	private HeightIsle heightIsle25at06;

	private List<Integer> nr25at09;
	private HeightIsle heightIsle25at09;

	private List<Integer> nr25at10;
	private HeightIsle heightIsle25at10;

	private List<Integer> nr25at11;
	private HeightIsle heightIsle25at11;

	private List<Integer> nr25at12;
	private HeightIsle heightIsle25at12;

	private List<Integer> nr25at13;
	private HeightIsle heightIsle25at13;

	private List<Integer> nr25at14;
	private HeightIsle heightIsle25at14;

	private List<Integer> nr25at15;
	private HeightIsle heightIsle25at15;

	private List<Integer> nr25at18;
	private HeightIsle heightIsle25at18;

	private List<Integer> nr25at19;
	private HeightIsle heightIsle25at19;

	private List<Integer> nr25at20;
	private HeightIsle heightIsle25at20;

	private List<Integer> nr25at21;
	private HeightIsle heightIsle25at21;

	private List<Integer> nr25at22;
	private HeightIsle heightIsle25at22;

	private List<Integer> nr25at23;
	private HeightIsle heightIsle25at23;

	private List<Integer> nr25at24;
	private HeightIsle heightIsle25at24;

	private List<Integer> nr25at27;
	private HeightIsle heightIsle25at27;

	private List<Integer> nr25at28;
	private HeightIsle heightIsle25at28;

	private List<Integer> nr25at29;
	private HeightIsle heightIsle25at29;

	private List<Integer> nr25at30;
	private HeightIsle heightIsle25at30;

	private List<Integer> nr25at31;
	private HeightIsle heightIsle25at31;

	private List<Integer> nr25at32;
	private HeightIsle heightIsle25at32;

	private List<Integer> nr25at33;
	private HeightIsle heightIsle25at33;

	private List<Integer> nr25at36;
	private HeightIsle heightIsle25at36;

	private List<Integer> nr25at37;
	private HeightIsle heightIsle25at37;

	private List<Integer> nr25at38;
	private HeightIsle heightIsle25at38;

	private List<Integer> nr25at39;
	private HeightIsle heightIsle25at39;

	private List<Integer> nr25at40;
	private HeightIsle heightIsle25at40;

	private List<Integer> nr25at41;
	private HeightIsle heightIsle25at41;

	private List<Integer> nr25at42;
	private HeightIsle heightIsle25at42;

	private List<Integer> nr25at45;
	private HeightIsle heightIsle25at45;

	private List<Integer> nr25at46;
	private HeightIsle heightIsle25at46;

	private List<Integer> nr25at47;
	private HeightIsle heightIsle25at47;

	private List<Integer> nr25at48;
	private HeightIsle heightIsle25at48;

	private List<Integer> nr25at49;
	private HeightIsle heightIsle25at49;

	private List<Integer> nr25at50;
	private HeightIsle heightIsle25at50;

	private List<Integer> nr25at51;
	private HeightIsle heightIsle25at51;

	private List<Integer> nr25at54;
	private HeightIsle heightIsle25at54;

	private List<Integer> nr25at55;
	private HeightIsle heightIsle25at55;

	private List<Integer> nr25at56;
	private HeightIsle heightIsle25at56;

	private List<Integer> nr25at57;
	private HeightIsle heightIsle25at57;

	private List<Integer> nr25at58;
	private HeightIsle heightIsle25at58;

	private List<Integer> nr25at59;
	private HeightIsle heightIsle25at59;

	private List<Integer> nr25at60;
	private HeightIsle heightIsle25at60;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11194,	
			19914, 11114, 91194,
			19114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle25() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr25at00 = Arrays.asList(at00);
		heightIsle25at00 = new HeightIsle(nr25at00, cornerMaxima);

		nr25at01 = Arrays.asList(at01);
		heightIsle25at01 = new HeightIsle(nr25at01, cornerMaxima);

		nr25at02 = Arrays.asList(at02);
		heightIsle25at02 = new HeightIsle(nr25at02, cornerMaxima);

		nr25at03 = Arrays.asList(at03);
		heightIsle25at03 = new HeightIsle(nr25at03, cornerMaxima);

		nr25at04 = Arrays.asList(at04);
		heightIsle25at04 = new HeightIsle(nr25at04, cornerMaxima);

		nr25at05 = Arrays.asList(at05);
		heightIsle25at05 = new HeightIsle(nr25at05, cornerMaxima);

		nr25at06 = Arrays.asList(at06);
		heightIsle25at06 = new HeightIsle(nr25at06, cornerMaxima);

		nr25at09 = Arrays.asList(at09);
		heightIsle25at09 = new HeightIsle(nr25at09, cornerMaxima);

		nr25at10 = Arrays.asList(at10);
		heightIsle25at10 = new HeightIsle(nr25at10, cornerMaxima);

		nr25at11 = Arrays.asList(at11);
		heightIsle25at11 = new HeightIsle(nr25at11, cornerMaxima);

		nr25at12 = Arrays.asList(at12);
		heightIsle25at12 = new HeightIsle(nr25at12, cornerMaxima);

		nr25at13 = Arrays.asList(at13);
		heightIsle25at13 = new HeightIsle(nr25at13, cornerMaxima);

		nr25at14 = Arrays.asList(at14);
		heightIsle25at14 = new HeightIsle(nr25at14, cornerMaxima);

		nr25at15 = Arrays.asList(at15);
		heightIsle25at15 = new HeightIsle(nr25at15, cornerMaxima);

		nr25at18 = Arrays.asList(at18);
		heightIsle25at18 = new HeightIsle(nr25at18, cornerMaxima);

		nr25at19 = Arrays.asList(at19);
		heightIsle25at19 = new HeightIsle(nr25at19, cornerMaxima);

		nr25at20 = Arrays.asList(at20);
		heightIsle25at20 = new HeightIsle(nr25at20, cornerMaxima);

		nr25at21 = Arrays.asList(at21);
		heightIsle25at21 = new HeightIsle(nr25at21, cornerMaxima);

		nr25at22 = Arrays.asList(at22);
		heightIsle25at22 = new HeightIsle(nr25at22, cornerMaxima);

		nr25at23 = Arrays.asList(at23);
		heightIsle25at23 = new HeightIsle(nr25at23, cornerMaxima);

		nr25at24 = Arrays.asList(at24);
		heightIsle25at24 = new HeightIsle(nr25at24, cornerMaxima);

		nr25at27 = Arrays.asList(at27);
		heightIsle25at27 = new HeightIsle(nr25at27, cornerMaxima);

		nr25at28 = Arrays.asList(at28);
		heightIsle25at28 = new HeightIsle(nr25at28, cornerMaxima);

		nr25at29 = Arrays.asList(at29);
		heightIsle25at29 = new HeightIsle(nr25at29, cornerMaxima);

		nr25at30 = Arrays.asList(at30);
		heightIsle25at30 = new HeightIsle(nr25at30, cornerMaxima);

		nr25at31 = Arrays.asList(at31);
		heightIsle25at31 = new HeightIsle(nr25at31, cornerMaxima);

		nr25at32 = Arrays.asList(at32);
		heightIsle25at32 = new HeightIsle(nr25at32, cornerMaxima);

		nr25at33 = Arrays.asList(at33);
		heightIsle25at33 = new HeightIsle(nr25at33, cornerMaxima);

		nr25at36 = Arrays.asList(at36);
		heightIsle25at36 = new HeightIsle(nr25at36, cornerMaxima);

		nr25at37 = Arrays.asList(at37);
		heightIsle25at37 = new HeightIsle(nr25at37, cornerMaxima);

		nr25at38 = Arrays.asList(at38);
		heightIsle25at38 = new HeightIsle(nr25at38, cornerMaxima);

		nr25at39 = Arrays.asList(at39);
		heightIsle25at39 = new HeightIsle(nr25at39, cornerMaxima);

		nr25at40 = Arrays.asList(at40);
		heightIsle25at40 = new HeightIsle(nr25at40, cornerMaxima);

		nr25at41 = Arrays.asList(at41);
		heightIsle25at41 = new HeightIsle(nr25at41, cornerMaxima);

		nr25at42 = Arrays.asList(at42);
		heightIsle25at42 = new HeightIsle(nr25at42, cornerMaxima);

		nr25at45 = Arrays.asList(at45);
		heightIsle25at45 = new HeightIsle(nr25at45, cornerMaxima);

		nr25at46 = Arrays.asList(at46);
		heightIsle25at46 = new HeightIsle(nr25at46, cornerMaxima);

		nr25at47 = Arrays.asList(at47);
		heightIsle25at47 = new HeightIsle(nr25at47, cornerMaxima);

		nr25at48 = Arrays.asList(at48);
		heightIsle25at48 = new HeightIsle(nr25at48, cornerMaxima);

		nr25at49 = Arrays.asList(at49);
		heightIsle25at49 = new HeightIsle(nr25at49, cornerMaxima);

		nr25at50 = Arrays.asList(at50);
		heightIsle25at50 = new HeightIsle(nr25at50, cornerMaxima);

		nr25at51 = Arrays.asList(at51);
		heightIsle25at51 = new HeightIsle(nr25at51, cornerMaxima);

		nr25at54 = Arrays.asList(at54);
		heightIsle25at54 = new HeightIsle(nr25at54, cornerMaxima);

		nr25at55 = Arrays.asList(at55);
		heightIsle25at55 = new HeightIsle(nr25at55, cornerMaxima);

		nr25at56 = Arrays.asList(at56);
		heightIsle25at56 = new HeightIsle(nr25at56, cornerMaxima);

		nr25at57 = Arrays.asList(at57);
		heightIsle25at57 = new HeightIsle(nr25at57, cornerMaxima);

		nr25at58 = Arrays.asList(at58);
		heightIsle25at58 = new HeightIsle(nr25at58, cornerMaxima);

		nr25at59 = Arrays.asList(at59);
		heightIsle25at59 = new HeightIsle(nr25at59, cornerMaxima);

		nr25at60 = Arrays.asList(at60);
		heightIsle25at60 = new HeightIsle(nr25at60, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle25();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 60) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr25at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr25at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr25at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr25at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr25at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr25at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr25at06; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr25at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr25at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr25at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr25at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr25at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr25at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr25at15; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr25at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr25at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr25at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr25at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr25at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr25at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr25at24; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr25at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr25at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr25at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr25at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr25at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr25at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr25at33; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr25at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr25at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr25at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr25at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr25at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr25at41; break;
			case 42:
				rasterIndizesDescribingTheIsle = nr25at42; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr25at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr25at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr25at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr25at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr25at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr25at50; break;
			case 51:
				rasterIndizesDescribingTheIsle = nr25at51; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr25at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr25at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr25at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr25at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr25at58; break;
			case 59:
				rasterIndizesDescribingTheIsle = nr25at59; break;
			case 60:
				rasterIndizesDescribingTheIsle = nr25at60; break;
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
			return heightIsle25at00;
		case 1:
			return heightIsle25at01;
		case 2:
			return heightIsle25at02;
		case 3:
			return heightIsle25at03;
		case 4:
			return heightIsle25at04;
		case 5:
			return heightIsle25at05;
		case 6:
			return heightIsle25at06;
		case 9:
			return heightIsle25at09;
		case 10:
			return heightIsle25at10;
		case 11:
			return heightIsle25at11;
		case 12:
			return heightIsle25at12;
		case 13:
			return heightIsle25at13;
		case 14:
			return heightIsle25at14;
		case 15:
			return heightIsle25at15;
		case 18:
			return heightIsle25at18;
		case 19:
			return heightIsle25at19;
		case 20:
			return heightIsle25at20;
		case 21:
			return heightIsle25at21;
		case 22:
			return heightIsle25at22;
		case 23:
			return heightIsle25at23;
		case 24:
			return heightIsle25at24;
		case 27:
			return heightIsle25at27;
		case 28:
			return heightIsle25at28;
		case 29:
			return heightIsle25at29;
		case 30:
			return heightIsle25at30;
		case 31:
			return heightIsle25at31;
		case 32:
			return heightIsle25at32;
		case 33:
			return heightIsle25at33;
		case 36:
			return heightIsle25at36;
		case 37:
			return heightIsle25at37;
		case 38:
			return heightIsle25at38;
		case 39:
			return heightIsle25at39;
		case 40:
			return heightIsle25at40;
		case 41:
			return heightIsle25at41;
		case 42:
			return heightIsle25at42;
		case 45:
			return heightIsle25at45;
		case 46:
			return heightIsle25at46;
		case 47:
			return heightIsle25at47;
		case 48:
			return heightIsle25at48;
		case 49:
			return heightIsle25at49;
		case 50:
			return heightIsle25at50;
		case 51:
			return heightIsle25at51;
		case 54:
			return heightIsle25at54;
		case 55:
			return heightIsle25at55;
		case 56:
			return heightIsle25at56;
		case 57:
			return heightIsle25at57;
		case 58:
			return heightIsle25at58;
		case 59:
			return heightIsle25at59;
		case 60:
			return heightIsle25at60;
		default:
			return null;
		}
	}
}
