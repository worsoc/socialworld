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

public class SubIsle26 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  
						9,  10, 11, 
						18, 19, 20, 
						27, 28, 29};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12, 
						19, 20, 21, 
						28, 29, 30};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13, 
						20, 21, 22, 
						29, 30, 31};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14, 
						21, 22, 23, 
						30, 31, 32};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15, 
						22, 23, 24, 
						31, 32, 33};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16, 
						23, 24, 25, 
						32, 33, 34};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17, 
						24, 25, 26, 
						33, 34, 35};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53};

	private final static Integer at27[]  = {27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56};

	private final static Integer at28[]  = {28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57};

	private final static Integer at29[]  = {29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58};

	private final static Integer at30[]  = {30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59};

	private final static Integer at31[]  = {31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60};

	private final static Integer at32[]  = {32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61};

	private final static Integer at33[]  = {33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62};

	private final static Integer at36[]  = {36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65};

	private final static Integer at37[]  = {37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66};

	private final static Integer at38[]  = {38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67};

	private final static Integer at39[]  = {39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68};

	private final static Integer at40[]  = {40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69};

	private final static Integer at41[]  = {41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70};

	private final static Integer at42[]  = {42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71};

	private final static Integer at45[]  = {45, 46, 47, 
						54, 55, 56, 
						63, 64, 65, 
						72, 73, 74};

	private final static Integer at46[]  = {46, 47, 48, 
						55, 56, 57, 
						64, 65, 66, 
						73, 74, 75};

	private final static Integer at47[]  = {47, 48, 49, 
						56, 57, 58, 
						65, 66, 67, 
						74, 75, 76};

	private final static Integer at48[]  = {48, 49, 50, 
						57, 58, 59, 
						66, 67, 68, 
						75, 76, 77};

	private final static Integer at49[]  = {49, 50, 51, 
						58, 59, 60, 
						67, 68, 69, 
						76, 77, 78};

	private final static Integer at50[]  = {50, 51, 52, 
						59, 60, 61, 
						68, 69, 70, 
						77, 78, 79};

	private final static Integer at51[]  = {51, 52, 53, 
						60, 61, 62, 
						69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr26at00;
	private HeightIsle heightIsle26at00;

	private List<Integer> nr26at01;
	private HeightIsle heightIsle26at01;

	private List<Integer> nr26at02;
	private HeightIsle heightIsle26at02;

	private List<Integer> nr26at03;
	private HeightIsle heightIsle26at03;

	private List<Integer> nr26at04;
	private HeightIsle heightIsle26at04;

	private List<Integer> nr26at05;
	private HeightIsle heightIsle26at05;

	private List<Integer> nr26at06;
	private HeightIsle heightIsle26at06;

	private List<Integer> nr26at09;
	private HeightIsle heightIsle26at09;

	private List<Integer> nr26at10;
	private HeightIsle heightIsle26at10;

	private List<Integer> nr26at11;
	private HeightIsle heightIsle26at11;

	private List<Integer> nr26at12;
	private HeightIsle heightIsle26at12;

	private List<Integer> nr26at13;
	private HeightIsle heightIsle26at13;

	private List<Integer> nr26at14;
	private HeightIsle heightIsle26at14;

	private List<Integer> nr26at15;
	private HeightIsle heightIsle26at15;

	private List<Integer> nr26at18;
	private HeightIsle heightIsle26at18;

	private List<Integer> nr26at19;
	private HeightIsle heightIsle26at19;

	private List<Integer> nr26at20;
	private HeightIsle heightIsle26at20;

	private List<Integer> nr26at21;
	private HeightIsle heightIsle26at21;

	private List<Integer> nr26at22;
	private HeightIsle heightIsle26at22;

	private List<Integer> nr26at23;
	private HeightIsle heightIsle26at23;

	private List<Integer> nr26at24;
	private HeightIsle heightIsle26at24;

	private List<Integer> nr26at27;
	private HeightIsle heightIsle26at27;

	private List<Integer> nr26at28;
	private HeightIsle heightIsle26at28;

	private List<Integer> nr26at29;
	private HeightIsle heightIsle26at29;

	private List<Integer> nr26at30;
	private HeightIsle heightIsle26at30;

	private List<Integer> nr26at31;
	private HeightIsle heightIsle26at31;

	private List<Integer> nr26at32;
	private HeightIsle heightIsle26at32;

	private List<Integer> nr26at33;
	private HeightIsle heightIsle26at33;

	private List<Integer> nr26at36;
	private HeightIsle heightIsle26at36;

	private List<Integer> nr26at37;
	private HeightIsle heightIsle26at37;

	private List<Integer> nr26at38;
	private HeightIsle heightIsle26at38;

	private List<Integer> nr26at39;
	private HeightIsle heightIsle26at39;

	private List<Integer> nr26at40;
	private HeightIsle heightIsle26at40;

	private List<Integer> nr26at41;
	private HeightIsle heightIsle26at41;

	private List<Integer> nr26at42;
	private HeightIsle heightIsle26at42;

	private List<Integer> nr26at45;
	private HeightIsle heightIsle26at45;

	private List<Integer> nr26at46;
	private HeightIsle heightIsle26at46;

	private List<Integer> nr26at47;
	private HeightIsle heightIsle26at47;

	private List<Integer> nr26at48;
	private HeightIsle heightIsle26at48;

	private List<Integer> nr26at49;
	private HeightIsle heightIsle26at49;

	private List<Integer> nr26at50;
	private HeightIsle heightIsle26at50;

	private List<Integer> nr26at51;
	private HeightIsle heightIsle26at51;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11194,	
			19914, 11114, 91194,
			19914, 11114, 91194,
			19114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle26() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr26at00 = Arrays.asList(at00);
		heightIsle26at00 = new HeightIsle(nr26at00, cornerMaxima);

		nr26at01 = Arrays.asList(at01);
		heightIsle26at01 = new HeightIsle(nr26at01, cornerMaxima);

		nr26at02 = Arrays.asList(at02);
		heightIsle26at02 = new HeightIsle(nr26at02, cornerMaxima);

		nr26at03 = Arrays.asList(at03);
		heightIsle26at03 = new HeightIsle(nr26at03, cornerMaxima);

		nr26at04 = Arrays.asList(at04);
		heightIsle26at04 = new HeightIsle(nr26at04, cornerMaxima);

		nr26at05 = Arrays.asList(at05);
		heightIsle26at05 = new HeightIsle(nr26at05, cornerMaxima);

		nr26at06 = Arrays.asList(at06);
		heightIsle26at06 = new HeightIsle(nr26at06, cornerMaxima);

		nr26at09 = Arrays.asList(at09);
		heightIsle26at09 = new HeightIsle(nr26at09, cornerMaxima);

		nr26at10 = Arrays.asList(at10);
		heightIsle26at10 = new HeightIsle(nr26at10, cornerMaxima);

		nr26at11 = Arrays.asList(at11);
		heightIsle26at11 = new HeightIsle(nr26at11, cornerMaxima);

		nr26at12 = Arrays.asList(at12);
		heightIsle26at12 = new HeightIsle(nr26at12, cornerMaxima);

		nr26at13 = Arrays.asList(at13);
		heightIsle26at13 = new HeightIsle(nr26at13, cornerMaxima);

		nr26at14 = Arrays.asList(at14);
		heightIsle26at14 = new HeightIsle(nr26at14, cornerMaxima);

		nr26at15 = Arrays.asList(at15);
		heightIsle26at15 = new HeightIsle(nr26at15, cornerMaxima);

		nr26at18 = Arrays.asList(at18);
		heightIsle26at18 = new HeightIsle(nr26at18, cornerMaxima);

		nr26at19 = Arrays.asList(at19);
		heightIsle26at19 = new HeightIsle(nr26at19, cornerMaxima);

		nr26at20 = Arrays.asList(at20);
		heightIsle26at20 = new HeightIsle(nr26at20, cornerMaxima);

		nr26at21 = Arrays.asList(at21);
		heightIsle26at21 = new HeightIsle(nr26at21, cornerMaxima);

		nr26at22 = Arrays.asList(at22);
		heightIsle26at22 = new HeightIsle(nr26at22, cornerMaxima);

		nr26at23 = Arrays.asList(at23);
		heightIsle26at23 = new HeightIsle(nr26at23, cornerMaxima);

		nr26at24 = Arrays.asList(at24);
		heightIsle26at24 = new HeightIsle(nr26at24, cornerMaxima);

		nr26at27 = Arrays.asList(at27);
		heightIsle26at27 = new HeightIsle(nr26at27, cornerMaxima);

		nr26at28 = Arrays.asList(at28);
		heightIsle26at28 = new HeightIsle(nr26at28, cornerMaxima);

		nr26at29 = Arrays.asList(at29);
		heightIsle26at29 = new HeightIsle(nr26at29, cornerMaxima);

		nr26at30 = Arrays.asList(at30);
		heightIsle26at30 = new HeightIsle(nr26at30, cornerMaxima);

		nr26at31 = Arrays.asList(at31);
		heightIsle26at31 = new HeightIsle(nr26at31, cornerMaxima);

		nr26at32 = Arrays.asList(at32);
		heightIsle26at32 = new HeightIsle(nr26at32, cornerMaxima);

		nr26at33 = Arrays.asList(at33);
		heightIsle26at33 = new HeightIsle(nr26at33, cornerMaxima);

		nr26at36 = Arrays.asList(at36);
		heightIsle26at36 = new HeightIsle(nr26at36, cornerMaxima);

		nr26at37 = Arrays.asList(at37);
		heightIsle26at37 = new HeightIsle(nr26at37, cornerMaxima);

		nr26at38 = Arrays.asList(at38);
		heightIsle26at38 = new HeightIsle(nr26at38, cornerMaxima);

		nr26at39 = Arrays.asList(at39);
		heightIsle26at39 = new HeightIsle(nr26at39, cornerMaxima);

		nr26at40 = Arrays.asList(at40);
		heightIsle26at40 = new HeightIsle(nr26at40, cornerMaxima);

		nr26at41 = Arrays.asList(at41);
		heightIsle26at41 = new HeightIsle(nr26at41, cornerMaxima);

		nr26at42 = Arrays.asList(at42);
		heightIsle26at42 = new HeightIsle(nr26at42, cornerMaxima);

		nr26at45 = Arrays.asList(at45);
		heightIsle26at45 = new HeightIsle(nr26at45, cornerMaxima);

		nr26at46 = Arrays.asList(at46);
		heightIsle26at46 = new HeightIsle(nr26at46, cornerMaxima);

		nr26at47 = Arrays.asList(at47);
		heightIsle26at47 = new HeightIsle(nr26at47, cornerMaxima);

		nr26at48 = Arrays.asList(at48);
		heightIsle26at48 = new HeightIsle(nr26at48, cornerMaxima);

		nr26at49 = Arrays.asList(at49);
		heightIsle26at49 = new HeightIsle(nr26at49, cornerMaxima);

		nr26at50 = Arrays.asList(at50);
		heightIsle26at50 = new HeightIsle(nr26at50, cornerMaxima);

		nr26at51 = Arrays.asList(at51);
		heightIsle26at51 = new HeightIsle(nr26at51, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle26();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 51) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr26at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr26at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr26at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr26at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr26at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr26at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr26at06; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr26at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr26at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr26at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr26at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr26at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr26at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr26at15; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr26at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr26at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr26at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr26at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr26at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr26at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr26at24; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr26at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr26at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr26at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr26at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr26at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr26at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr26at33; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr26at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr26at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr26at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr26at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr26at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr26at41; break;
			case 42:
				rasterIndizesDescribingTheIsle = nr26at42; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr26at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr26at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr26at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr26at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr26at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr26at50; break;
			case 51:
				rasterIndizesDescribingTheIsle = nr26at51; break;
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
			return heightIsle26at00;
		case 1:
			return heightIsle26at01;
		case 2:
			return heightIsle26at02;
		case 3:
			return heightIsle26at03;
		case 4:
			return heightIsle26at04;
		case 5:
			return heightIsle26at05;
		case 6:
			return heightIsle26at06;
		case 9:
			return heightIsle26at09;
		case 10:
			return heightIsle26at10;
		case 11:
			return heightIsle26at11;
		case 12:
			return heightIsle26at12;
		case 13:
			return heightIsle26at13;
		case 14:
			return heightIsle26at14;
		case 15:
			return heightIsle26at15;
		case 18:
			return heightIsle26at18;
		case 19:
			return heightIsle26at19;
		case 20:
			return heightIsle26at20;
		case 21:
			return heightIsle26at21;
		case 22:
			return heightIsle26at22;
		case 23:
			return heightIsle26at23;
		case 24:
			return heightIsle26at24;
		case 27:
			return heightIsle26at27;
		case 28:
			return heightIsle26at28;
		case 29:
			return heightIsle26at29;
		case 30:
			return heightIsle26at30;
		case 31:
			return heightIsle26at31;
		case 32:
			return heightIsle26at32;
		case 33:
			return heightIsle26at33;
		case 36:
			return heightIsle26at36;
		case 37:
			return heightIsle26at37;
		case 38:
			return heightIsle26at38;
		case 39:
			return heightIsle26at39;
		case 40:
			return heightIsle26at40;
		case 41:
			return heightIsle26at41;
		case 42:
			return heightIsle26at42;
		case 45:
			return heightIsle26at45;
		case 46:
			return heightIsle26at46;
		case 47:
			return heightIsle26at47;
		case 48:
			return heightIsle26at48;
		case 49:
			return heightIsle26at49;
		case 50:
			return heightIsle26at50;
		case 51:
			return heightIsle26at51;
		default:
			return null;
		}
	}
}
