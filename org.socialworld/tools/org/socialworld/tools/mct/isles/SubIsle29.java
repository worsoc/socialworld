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

public class SubIsle29 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  
						9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  
						10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  
						11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  
						12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25};

	private final static Integer at04[]  = {4,  5,  6,  7,  8,  
						13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34};

	private final static Integer at13[]  = {13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43};

	private final static Integer at22[]  = {22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52};

	private final static Integer at31[]  = {31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61};

	private final static Integer at40[]  = {40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70};

	private final static Integer at49[]  = {49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71};

	private final static Integer at54[]  = {54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67, 
						72, 73, 74, 75, 76};

	private final static Integer at55[]  = {55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68, 
						73, 74, 75, 76, 77};

	private final static Integer at56[]  = {56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69, 
						74, 75, 76, 77, 78};

	private final static Integer at57[]  = {57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70, 
						75, 76, 77, 78, 79};

	private final static Integer at58[]  = {58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71, 
						76, 77, 78, 79, 80};

	private List<Integer> nr29at00;
	private HeightIsle heightIsle29at00;

	private List<Integer> nr29at01;
	private HeightIsle heightIsle29at01;

	private List<Integer> nr29at02;
	private HeightIsle heightIsle29at02;

	private List<Integer> nr29at03;
	private HeightIsle heightIsle29at03;

	private List<Integer> nr29at04;
	private HeightIsle heightIsle29at04;

	private List<Integer> nr29at09;
	private HeightIsle heightIsle29at09;

	private List<Integer> nr29at10;
	private HeightIsle heightIsle29at10;

	private List<Integer> nr29at11;
	private HeightIsle heightIsle29at11;

	private List<Integer> nr29at12;
	private HeightIsle heightIsle29at12;

	private List<Integer> nr29at13;
	private HeightIsle heightIsle29at13;

	private List<Integer> nr29at18;
	private HeightIsle heightIsle29at18;

	private List<Integer> nr29at19;
	private HeightIsle heightIsle29at19;

	private List<Integer> nr29at20;
	private HeightIsle heightIsle29at20;

	private List<Integer> nr29at21;
	private HeightIsle heightIsle29at21;

	private List<Integer> nr29at22;
	private HeightIsle heightIsle29at22;

	private List<Integer> nr29at27;
	private HeightIsle heightIsle29at27;

	private List<Integer> nr29at28;
	private HeightIsle heightIsle29at28;

	private List<Integer> nr29at29;
	private HeightIsle heightIsle29at29;

	private List<Integer> nr29at30;
	private HeightIsle heightIsle29at30;

	private List<Integer> nr29at31;
	private HeightIsle heightIsle29at31;

	private List<Integer> nr29at36;
	private HeightIsle heightIsle29at36;

	private List<Integer> nr29at37;
	private HeightIsle heightIsle29at37;

	private List<Integer> nr29at38;
	private HeightIsle heightIsle29at38;

	private List<Integer> nr29at39;
	private HeightIsle heightIsle29at39;

	private List<Integer> nr29at40;
	private HeightIsle heightIsle29at40;

	private List<Integer> nr29at45;
	private HeightIsle heightIsle29at45;

	private List<Integer> nr29at46;
	private HeightIsle heightIsle29at46;

	private List<Integer> nr29at47;
	private HeightIsle heightIsle29at47;

	private List<Integer> nr29at48;
	private HeightIsle heightIsle29at48;

	private List<Integer> nr29at49;
	private HeightIsle heightIsle29at49;

	private List<Integer> nr29at54;
	private HeightIsle heightIsle29at54;

	private List<Integer> nr29at55;
	private HeightIsle heightIsle29at55;

	private List<Integer> nr29at56;
	private HeightIsle heightIsle29at56;

	private List<Integer> nr29at57;
	private HeightIsle heightIsle29at57;

	private List<Integer> nr29at58;
	private HeightIsle heightIsle29at58;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11194,	
			19914, 11114, 11114, 11114, 91194,
			19114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle29() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr29at00 = Arrays.asList(at00);
		heightIsle29at00 = new HeightIsle(nr29at00, cornerMaxima);

		nr29at01 = Arrays.asList(at01);
		heightIsle29at01 = new HeightIsle(nr29at01, cornerMaxima);

		nr29at02 = Arrays.asList(at02);
		heightIsle29at02 = new HeightIsle(nr29at02, cornerMaxima);

		nr29at03 = Arrays.asList(at03);
		heightIsle29at03 = new HeightIsle(nr29at03, cornerMaxima);

		nr29at04 = Arrays.asList(at04);
		heightIsle29at04 = new HeightIsle(nr29at04, cornerMaxima);

		nr29at09 = Arrays.asList(at09);
		heightIsle29at09 = new HeightIsle(nr29at09, cornerMaxima);

		nr29at10 = Arrays.asList(at10);
		heightIsle29at10 = new HeightIsle(nr29at10, cornerMaxima);

		nr29at11 = Arrays.asList(at11);
		heightIsle29at11 = new HeightIsle(nr29at11, cornerMaxima);

		nr29at12 = Arrays.asList(at12);
		heightIsle29at12 = new HeightIsle(nr29at12, cornerMaxima);

		nr29at13 = Arrays.asList(at13);
		heightIsle29at13 = new HeightIsle(nr29at13, cornerMaxima);

		nr29at18 = Arrays.asList(at18);
		heightIsle29at18 = new HeightIsle(nr29at18, cornerMaxima);

		nr29at19 = Arrays.asList(at19);
		heightIsle29at19 = new HeightIsle(nr29at19, cornerMaxima);

		nr29at20 = Arrays.asList(at20);
		heightIsle29at20 = new HeightIsle(nr29at20, cornerMaxima);

		nr29at21 = Arrays.asList(at21);
		heightIsle29at21 = new HeightIsle(nr29at21, cornerMaxima);

		nr29at22 = Arrays.asList(at22);
		heightIsle29at22 = new HeightIsle(nr29at22, cornerMaxima);

		nr29at27 = Arrays.asList(at27);
		heightIsle29at27 = new HeightIsle(nr29at27, cornerMaxima);

		nr29at28 = Arrays.asList(at28);
		heightIsle29at28 = new HeightIsle(nr29at28, cornerMaxima);

		nr29at29 = Arrays.asList(at29);
		heightIsle29at29 = new HeightIsle(nr29at29, cornerMaxima);

		nr29at30 = Arrays.asList(at30);
		heightIsle29at30 = new HeightIsle(nr29at30, cornerMaxima);

		nr29at31 = Arrays.asList(at31);
		heightIsle29at31 = new HeightIsle(nr29at31, cornerMaxima);

		nr29at36 = Arrays.asList(at36);
		heightIsle29at36 = new HeightIsle(nr29at36, cornerMaxima);

		nr29at37 = Arrays.asList(at37);
		heightIsle29at37 = new HeightIsle(nr29at37, cornerMaxima);

		nr29at38 = Arrays.asList(at38);
		heightIsle29at38 = new HeightIsle(nr29at38, cornerMaxima);

		nr29at39 = Arrays.asList(at39);
		heightIsle29at39 = new HeightIsle(nr29at39, cornerMaxima);

		nr29at40 = Arrays.asList(at40);
		heightIsle29at40 = new HeightIsle(nr29at40, cornerMaxima);

		nr29at45 = Arrays.asList(at45);
		heightIsle29at45 = new HeightIsle(nr29at45, cornerMaxima);

		nr29at46 = Arrays.asList(at46);
		heightIsle29at46 = new HeightIsle(nr29at46, cornerMaxima);

		nr29at47 = Arrays.asList(at47);
		heightIsle29at47 = new HeightIsle(nr29at47, cornerMaxima);

		nr29at48 = Arrays.asList(at48);
		heightIsle29at48 = new HeightIsle(nr29at48, cornerMaxima);

		nr29at49 = Arrays.asList(at49);
		heightIsle29at49 = new HeightIsle(nr29at49, cornerMaxima);

		nr29at54 = Arrays.asList(at54);
		heightIsle29at54 = new HeightIsle(nr29at54, cornerMaxima);

		nr29at55 = Arrays.asList(at55);
		heightIsle29at55 = new HeightIsle(nr29at55, cornerMaxima);

		nr29at56 = Arrays.asList(at56);
		heightIsle29at56 = new HeightIsle(nr29at56, cornerMaxima);

		nr29at57 = Arrays.asList(at57);
		heightIsle29at57 = new HeightIsle(nr29at57, cornerMaxima);

		nr29at58 = Arrays.asList(at58);
		heightIsle29at58 = new HeightIsle(nr29at58, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle29();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 58) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr29at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr29at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr29at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr29at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr29at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr29at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr29at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr29at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr29at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr29at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr29at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr29at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr29at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr29at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr29at22; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr29at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr29at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr29at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr29at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr29at31; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr29at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr29at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr29at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr29at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr29at40; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr29at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr29at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr29at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr29at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr29at49; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr29at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr29at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr29at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr29at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr29at58; break;
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
			return heightIsle29at00;
		case 1:
			return heightIsle29at01;
		case 2:
			return heightIsle29at02;
		case 3:
			return heightIsle29at03;
		case 4:
			return heightIsle29at04;
		case 9:
			return heightIsle29at09;
		case 10:
			return heightIsle29at10;
		case 11:
			return heightIsle29at11;
		case 12:
			return heightIsle29at12;
		case 13:
			return heightIsle29at13;
		case 18:
			return heightIsle29at18;
		case 19:
			return heightIsle29at19;
		case 20:
			return heightIsle29at20;
		case 21:
			return heightIsle29at21;
		case 22:
			return heightIsle29at22;
		case 27:
			return heightIsle29at27;
		case 28:
			return heightIsle29at28;
		case 29:
			return heightIsle29at29;
		case 30:
			return heightIsle29at30;
		case 31:
			return heightIsle29at31;
		case 36:
			return heightIsle29at36;
		case 37:
			return heightIsle29at37;
		case 38:
			return heightIsle29at38;
		case 39:
			return heightIsle29at39;
		case 40:
			return heightIsle29at40;
		case 45:
			return heightIsle29at45;
		case 46:
			return heightIsle29at46;
		case 47:
			return heightIsle29at47;
		case 48:
			return heightIsle29at48;
		case 49:
			return heightIsle29at49;
		case 54:
			return heightIsle29at54;
		case 55:
			return heightIsle29at55;
		case 56:
			return heightIsle29at56;
		case 57:
			return heightIsle29at57;
		case 58:
			return heightIsle29at58;
		default:
			return null;
		}
	}
}
