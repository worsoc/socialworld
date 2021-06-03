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

public class SubIsle20 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  
						9, 10, 11, 12, 13};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  
						10, 11, 12, 13, 14};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  
						11, 12, 13, 14, 15};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  
						12, 13, 14, 15, 16};

	private final static Integer at04[]  = {4,  5,  6,  7,  8,  
						13, 14, 15, 16, 17};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25};

	private final static Integer at13[]  = {13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34};

	private final static Integer at22[]  = {22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43};

	private final static Integer at31[]  = {31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52};

	private final static Integer at40[]  = {40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61};

	private final static Integer at49[]  = {49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62};

	private final static Integer at54[]  = {54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67};

	private final static Integer at55[]  = {55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68};

	private final static Integer at56[]  = {56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69};

	private final static Integer at57[]  = {57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70};

	private final static Integer at58[]  = {58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71};

	private final static Integer at63[]  = {63, 64, 65, 66, 67, 
						72, 73, 74, 75, 76};

	private final static Integer at64[]  = {64, 65, 66, 67, 68, 
						73, 74, 75, 76, 77};

	private final static Integer at65[]  = {65, 66, 67, 68, 69, 
						74, 75, 76, 77, 78};

	private final static Integer at66[]  = {66, 67, 68, 69, 70, 
						75, 76, 77, 78, 79};

	private final static Integer at67[]  = {67, 68, 69, 70, 71, 
						76, 77, 78, 79, 80};

	private List<Integer> nr20at00;
	private HeightIsle heightIsle20at00;

	private List<Integer> nr20at01;
	private HeightIsle heightIsle20at01;

	private List<Integer> nr20at02;
	private HeightIsle heightIsle20at02;

	private List<Integer> nr20at03;
	private HeightIsle heightIsle20at03;

	private List<Integer> nr20at04;
	private HeightIsle heightIsle20at04;

	private List<Integer> nr20at09;
	private HeightIsle heightIsle20at09;

	private List<Integer> nr20at10;
	private HeightIsle heightIsle20at10;

	private List<Integer> nr20at11;
	private HeightIsle heightIsle20at11;

	private List<Integer> nr20at12;
	private HeightIsle heightIsle20at12;

	private List<Integer> nr20at13;
	private HeightIsle heightIsle20at13;

	private List<Integer> nr20at18;
	private HeightIsle heightIsle20at18;

	private List<Integer> nr20at19;
	private HeightIsle heightIsle20at19;

	private List<Integer> nr20at20;
	private HeightIsle heightIsle20at20;

	private List<Integer> nr20at21;
	private HeightIsle heightIsle20at21;

	private List<Integer> nr20at22;
	private HeightIsle heightIsle20at22;

	private List<Integer> nr20at27;
	private HeightIsle heightIsle20at27;

	private List<Integer> nr20at28;
	private HeightIsle heightIsle20at28;

	private List<Integer> nr20at29;
	private HeightIsle heightIsle20at29;

	private List<Integer> nr20at30;
	private HeightIsle heightIsle20at30;

	private List<Integer> nr20at31;
	private HeightIsle heightIsle20at31;

	private List<Integer> nr20at36;
	private HeightIsle heightIsle20at36;

	private List<Integer> nr20at37;
	private HeightIsle heightIsle20at37;

	private List<Integer> nr20at38;
	private HeightIsle heightIsle20at38;

	private List<Integer> nr20at39;
	private HeightIsle heightIsle20at39;

	private List<Integer> nr20at40;
	private HeightIsle heightIsle20at40;

	private List<Integer> nr20at45;
	private HeightIsle heightIsle20at45;

	private List<Integer> nr20at46;
	private HeightIsle heightIsle20at46;

	private List<Integer> nr20at47;
	private HeightIsle heightIsle20at47;

	private List<Integer> nr20at48;
	private HeightIsle heightIsle20at48;

	private List<Integer> nr20at49;
	private HeightIsle heightIsle20at49;

	private List<Integer> nr20at54;
	private HeightIsle heightIsle20at54;

	private List<Integer> nr20at55;
	private HeightIsle heightIsle20at55;

	private List<Integer> nr20at56;
	private HeightIsle heightIsle20at56;

	private List<Integer> nr20at57;
	private HeightIsle heightIsle20at57;

	private List<Integer> nr20at58;
	private HeightIsle heightIsle20at58;

	private List<Integer> nr20at63;
	private HeightIsle heightIsle20at63;

	private List<Integer> nr20at64;
	private HeightIsle heightIsle20at64;

	private List<Integer> nr20at65;
	private HeightIsle heightIsle20at65;

	private List<Integer> nr20at66;
	private HeightIsle heightIsle20at66;

	private List<Integer> nr20at67;
	private HeightIsle heightIsle20at67;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle20() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr20at00 = Arrays.asList(at00);
		heightIsle20at00 = new HeightIsle(nr20at00, cornerMaxima);

		nr20at01 = Arrays.asList(at01);
		heightIsle20at01 = new HeightIsle(nr20at01, cornerMaxima);

		nr20at02 = Arrays.asList(at02);
		heightIsle20at02 = new HeightIsle(nr20at02, cornerMaxima);

		nr20at03 = Arrays.asList(at03);
		heightIsle20at03 = new HeightIsle(nr20at03, cornerMaxima);

		nr20at04 = Arrays.asList(at04);
		heightIsle20at04 = new HeightIsle(nr20at04, cornerMaxima);

		nr20at09 = Arrays.asList(at09);
		heightIsle20at09 = new HeightIsle(nr20at09, cornerMaxima);

		nr20at10 = Arrays.asList(at10);
		heightIsle20at10 = new HeightIsle(nr20at10, cornerMaxima);

		nr20at11 = Arrays.asList(at11);
		heightIsle20at11 = new HeightIsle(nr20at11, cornerMaxima);

		nr20at12 = Arrays.asList(at12);
		heightIsle20at12 = new HeightIsle(nr20at12, cornerMaxima);

		nr20at13 = Arrays.asList(at13);
		heightIsle20at13 = new HeightIsle(nr20at13, cornerMaxima);

		nr20at18 = Arrays.asList(at18);
		heightIsle20at18 = new HeightIsle(nr20at18, cornerMaxima);

		nr20at19 = Arrays.asList(at19);
		heightIsle20at19 = new HeightIsle(nr20at19, cornerMaxima);

		nr20at20 = Arrays.asList(at20);
		heightIsle20at20 = new HeightIsle(nr20at20, cornerMaxima);

		nr20at21 = Arrays.asList(at21);
		heightIsle20at21 = new HeightIsle(nr20at21, cornerMaxima);

		nr20at22 = Arrays.asList(at22);
		heightIsle20at22 = new HeightIsle(nr20at22, cornerMaxima);

		nr20at27 = Arrays.asList(at27);
		heightIsle20at27 = new HeightIsle(nr20at27, cornerMaxima);

		nr20at28 = Arrays.asList(at28);
		heightIsle20at28 = new HeightIsle(nr20at28, cornerMaxima);

		nr20at29 = Arrays.asList(at29);
		heightIsle20at29 = new HeightIsle(nr20at29, cornerMaxima);

		nr20at30 = Arrays.asList(at30);
		heightIsle20at30 = new HeightIsle(nr20at30, cornerMaxima);

		nr20at31 = Arrays.asList(at31);
		heightIsle20at31 = new HeightIsle(nr20at31, cornerMaxima);

		nr20at36 = Arrays.asList(at36);
		heightIsle20at36 = new HeightIsle(nr20at36, cornerMaxima);

		nr20at37 = Arrays.asList(at37);
		heightIsle20at37 = new HeightIsle(nr20at37, cornerMaxima);

		nr20at38 = Arrays.asList(at38);
		heightIsle20at38 = new HeightIsle(nr20at38, cornerMaxima);

		nr20at39 = Arrays.asList(at39);
		heightIsle20at39 = new HeightIsle(nr20at39, cornerMaxima);

		nr20at40 = Arrays.asList(at40);
		heightIsle20at40 = new HeightIsle(nr20at40, cornerMaxima);

		nr20at45 = Arrays.asList(at45);
		heightIsle20at45 = new HeightIsle(nr20at45, cornerMaxima);

		nr20at46 = Arrays.asList(at46);
		heightIsle20at46 = new HeightIsle(nr20at46, cornerMaxima);

		nr20at47 = Arrays.asList(at47);
		heightIsle20at47 = new HeightIsle(nr20at47, cornerMaxima);

		nr20at48 = Arrays.asList(at48);
		heightIsle20at48 = new HeightIsle(nr20at48, cornerMaxima);

		nr20at49 = Arrays.asList(at49);
		heightIsle20at49 = new HeightIsle(nr20at49, cornerMaxima);

		nr20at54 = Arrays.asList(at54);
		heightIsle20at54 = new HeightIsle(nr20at54, cornerMaxima);

		nr20at55 = Arrays.asList(at55);
		heightIsle20at55 = new HeightIsle(nr20at55, cornerMaxima);

		nr20at56 = Arrays.asList(at56);
		heightIsle20at56 = new HeightIsle(nr20at56, cornerMaxima);

		nr20at57 = Arrays.asList(at57);
		heightIsle20at57 = new HeightIsle(nr20at57, cornerMaxima);

		nr20at58 = Arrays.asList(at58);
		heightIsle20at58 = new HeightIsle(nr20at58, cornerMaxima);

		nr20at63 = Arrays.asList(at63);
		heightIsle20at63 = new HeightIsle(nr20at63, cornerMaxima);

		nr20at64 = Arrays.asList(at64);
		heightIsle20at64 = new HeightIsle(nr20at64, cornerMaxima);

		nr20at65 = Arrays.asList(at65);
		heightIsle20at65 = new HeightIsle(nr20at65, cornerMaxima);

		nr20at66 = Arrays.asList(at66);
		heightIsle20at66 = new HeightIsle(nr20at66, cornerMaxima);

		nr20at67 = Arrays.asList(at67);
		heightIsle20at67 = new HeightIsle(nr20at67, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle20();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 67) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr20at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr20at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr20at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr20at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr20at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr20at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr20at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr20at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr20at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr20at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr20at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr20at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr20at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr20at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr20at22; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr20at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr20at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr20at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr20at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr20at31; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr20at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr20at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr20at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr20at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr20at40; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr20at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr20at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr20at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr20at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr20at49; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr20at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr20at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr20at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr20at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr20at58; break;
			case 63:
				rasterIndizesDescribingTheIsle = nr20at63; break;
			case 64:
				rasterIndizesDescribingTheIsle = nr20at64; break;
			case 65:
				rasterIndizesDescribingTheIsle = nr20at65; break;
			case 66:
				rasterIndizesDescribingTheIsle = nr20at66; break;
			case 67:
				rasterIndizesDescribingTheIsle = nr20at67; break;
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
			return heightIsle20at00;
		case 1:
			return heightIsle20at01;
		case 2:
			return heightIsle20at02;
		case 3:
			return heightIsle20at03;
		case 4:
			return heightIsle20at04;
		case 9:
			return heightIsle20at09;
		case 10:
			return heightIsle20at10;
		case 11:
			return heightIsle20at11;
		case 12:
			return heightIsle20at12;
		case 13:
			return heightIsle20at13;
		case 18:
			return heightIsle20at18;
		case 19:
			return heightIsle20at19;
		case 20:
			return heightIsle20at20;
		case 21:
			return heightIsle20at21;
		case 22:
			return heightIsle20at22;
		case 27:
			return heightIsle20at27;
		case 28:
			return heightIsle20at28;
		case 29:
			return heightIsle20at29;
		case 30:
			return heightIsle20at30;
		case 31:
			return heightIsle20at31;
		case 36:
			return heightIsle20at36;
		case 37:
			return heightIsle20at37;
		case 38:
			return heightIsle20at38;
		case 39:
			return heightIsle20at39;
		case 40:
			return heightIsle20at40;
		case 45:
			return heightIsle20at45;
		case 46:
			return heightIsle20at46;
		case 47:
			return heightIsle20at47;
		case 48:
			return heightIsle20at48;
		case 49:
			return heightIsle20at49;
		case 54:
			return heightIsle20at54;
		case 55:
			return heightIsle20at55;
		case 56:
			return heightIsle20at56;
		case 57:
			return heightIsle20at57;
		case 58:
			return heightIsle20at58;
		case 63:
			return heightIsle20at63;
		case 64:
			return heightIsle20at64;
		case 65:
			return heightIsle20at65;
		case 66:
			return heightIsle20at66;
		case 67:
			return heightIsle20at67;
		default:
			return null;
		}
	}
}
