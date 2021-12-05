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

public class SubIsle22 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  
						9, 10, 11, 12, 13, 14};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  
						10, 11, 12, 13, 14, 15};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  
						11, 12, 13, 14, 15, 16};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  8,  
						12, 13, 14, 15, 16, 17};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 
						18, 19, 20, 21, 22, 23};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 
						19, 20, 21, 22, 23, 24};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 
						20, 21, 22, 23, 24, 25};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 17, 
						21, 22, 23, 24, 25, 26};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62};

	private final static Integer at54[]  = {54, 55, 56, 57, 58, 59, 
						63, 64, 65, 66, 67, 68};

	private final static Integer at55[]  = {55, 56, 57, 58, 59, 60, 
						64, 65, 66, 67, 68, 69};

	private final static Integer at56[]  = {56, 57, 58, 59, 60, 61, 
						65, 66, 67, 68, 69, 70};

	private final static Integer at57[]  = {57, 58, 59, 60, 61, 62, 
						66, 67, 68, 69, 70, 71};

	private final static Integer at63[]  = {63, 64, 65, 66, 67, 68, 
						72, 73, 74, 75, 76, 77};

	private final static Integer at64[]  = {64, 65, 66, 67, 68, 69, 
						73, 74, 75, 76, 77, 78};

	private final static Integer at65[]  = {65, 66, 67, 68, 69, 70, 
						74, 75, 76, 77, 78, 79};

	private final static Integer at66[]  = {66, 67, 68, 69, 70, 71, 
						75, 76, 77, 78, 79, 80};

	private List<Integer> nr22at00;
	private HeightIsle heightIsle22at00;

	private List<Integer> nr22at01;
	private HeightIsle heightIsle22at01;

	private List<Integer> nr22at02;
	private HeightIsle heightIsle22at02;

	private List<Integer> nr22at03;
	private HeightIsle heightIsle22at03;

	private List<Integer> nr22at09;
	private HeightIsle heightIsle22at09;

	private List<Integer> nr22at10;
	private HeightIsle heightIsle22at10;

	private List<Integer> nr22at11;
	private HeightIsle heightIsle22at11;

	private List<Integer> nr22at12;
	private HeightIsle heightIsle22at12;

	private List<Integer> nr22at18;
	private HeightIsle heightIsle22at18;

	private List<Integer> nr22at19;
	private HeightIsle heightIsle22at19;

	private List<Integer> nr22at20;
	private HeightIsle heightIsle22at20;

	private List<Integer> nr22at21;
	private HeightIsle heightIsle22at21;

	private List<Integer> nr22at27;
	private HeightIsle heightIsle22at27;

	private List<Integer> nr22at28;
	private HeightIsle heightIsle22at28;

	private List<Integer> nr22at29;
	private HeightIsle heightIsle22at29;

	private List<Integer> nr22at30;
	private HeightIsle heightIsle22at30;

	private List<Integer> nr22at36;
	private HeightIsle heightIsle22at36;

	private List<Integer> nr22at37;
	private HeightIsle heightIsle22at37;

	private List<Integer> nr22at38;
	private HeightIsle heightIsle22at38;

	private List<Integer> nr22at39;
	private HeightIsle heightIsle22at39;

	private List<Integer> nr22at45;
	private HeightIsle heightIsle22at45;

	private List<Integer> nr22at46;
	private HeightIsle heightIsle22at46;

	private List<Integer> nr22at47;
	private HeightIsle heightIsle22at47;

	private List<Integer> nr22at48;
	private HeightIsle heightIsle22at48;

	private List<Integer> nr22at54;
	private HeightIsle heightIsle22at54;

	private List<Integer> nr22at55;
	private HeightIsle heightIsle22at55;

	private List<Integer> nr22at56;
	private HeightIsle heightIsle22at56;

	private List<Integer> nr22at57;
	private HeightIsle heightIsle22at57;

	private List<Integer> nr22at63;
	private HeightIsle heightIsle22at63;

	private List<Integer> nr22at64;
	private HeightIsle heightIsle22at64;

	private List<Integer> nr22at65;
	private HeightIsle heightIsle22at65;

	private List<Integer> nr22at66;
	private HeightIsle heightIsle22at66;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11994, 11194,	
			19114, 99114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle22() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr22at00 = Arrays.asList(at00);
		heightIsle22at00 = new HeightIsle(nr22at00, cornerMaxima);

		nr22at01 = Arrays.asList(at01);
		heightIsle22at01 = new HeightIsle(nr22at01, cornerMaxima);

		nr22at02 = Arrays.asList(at02);
		heightIsle22at02 = new HeightIsle(nr22at02, cornerMaxima);

		nr22at03 = Arrays.asList(at03);
		heightIsle22at03 = new HeightIsle(nr22at03, cornerMaxima);

		nr22at09 = Arrays.asList(at09);
		heightIsle22at09 = new HeightIsle(nr22at09, cornerMaxima);

		nr22at10 = Arrays.asList(at10);
		heightIsle22at10 = new HeightIsle(nr22at10, cornerMaxima);

		nr22at11 = Arrays.asList(at11);
		heightIsle22at11 = new HeightIsle(nr22at11, cornerMaxima);

		nr22at12 = Arrays.asList(at12);
		heightIsle22at12 = new HeightIsle(nr22at12, cornerMaxima);

		nr22at18 = Arrays.asList(at18);
		heightIsle22at18 = new HeightIsle(nr22at18, cornerMaxima);

		nr22at19 = Arrays.asList(at19);
		heightIsle22at19 = new HeightIsle(nr22at19, cornerMaxima);

		nr22at20 = Arrays.asList(at20);
		heightIsle22at20 = new HeightIsle(nr22at20, cornerMaxima);

		nr22at21 = Arrays.asList(at21);
		heightIsle22at21 = new HeightIsle(nr22at21, cornerMaxima);

		nr22at27 = Arrays.asList(at27);
		heightIsle22at27 = new HeightIsle(nr22at27, cornerMaxima);

		nr22at28 = Arrays.asList(at28);
		heightIsle22at28 = new HeightIsle(nr22at28, cornerMaxima);

		nr22at29 = Arrays.asList(at29);
		heightIsle22at29 = new HeightIsle(nr22at29, cornerMaxima);

		nr22at30 = Arrays.asList(at30);
		heightIsle22at30 = new HeightIsle(nr22at30, cornerMaxima);

		nr22at36 = Arrays.asList(at36);
		heightIsle22at36 = new HeightIsle(nr22at36, cornerMaxima);

		nr22at37 = Arrays.asList(at37);
		heightIsle22at37 = new HeightIsle(nr22at37, cornerMaxima);

		nr22at38 = Arrays.asList(at38);
		heightIsle22at38 = new HeightIsle(nr22at38, cornerMaxima);

		nr22at39 = Arrays.asList(at39);
		heightIsle22at39 = new HeightIsle(nr22at39, cornerMaxima);

		nr22at45 = Arrays.asList(at45);
		heightIsle22at45 = new HeightIsle(nr22at45, cornerMaxima);

		nr22at46 = Arrays.asList(at46);
		heightIsle22at46 = new HeightIsle(nr22at46, cornerMaxima);

		nr22at47 = Arrays.asList(at47);
		heightIsle22at47 = new HeightIsle(nr22at47, cornerMaxima);

		nr22at48 = Arrays.asList(at48);
		heightIsle22at48 = new HeightIsle(nr22at48, cornerMaxima);

		nr22at54 = Arrays.asList(at54);
		heightIsle22at54 = new HeightIsle(nr22at54, cornerMaxima);

		nr22at55 = Arrays.asList(at55);
		heightIsle22at55 = new HeightIsle(nr22at55, cornerMaxima);

		nr22at56 = Arrays.asList(at56);
		heightIsle22at56 = new HeightIsle(nr22at56, cornerMaxima);

		nr22at57 = Arrays.asList(at57);
		heightIsle22at57 = new HeightIsle(nr22at57, cornerMaxima);

		nr22at63 = Arrays.asList(at63);
		heightIsle22at63 = new HeightIsle(nr22at63, cornerMaxima);

		nr22at64 = Arrays.asList(at64);
		heightIsle22at64 = new HeightIsle(nr22at64, cornerMaxima);

		nr22at65 = Arrays.asList(at65);
		heightIsle22at65 = new HeightIsle(nr22at65, cornerMaxima);

		nr22at66 = Arrays.asList(at66);
		heightIsle22at66 = new HeightIsle(nr22at66, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle22();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 66) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr22at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr22at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr22at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr22at03; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr22at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr22at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr22at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr22at12; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr22at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr22at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr22at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr22at21; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr22at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr22at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr22at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr22at30; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr22at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr22at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr22at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr22at39; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr22at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr22at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr22at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr22at48; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr22at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr22at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr22at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr22at57; break;
			case 63:
				rasterIndizesDescribingTheIsle = nr22at63; break;
			case 64:
				rasterIndizesDescribingTheIsle = nr22at64; break;
			case 65:
				rasterIndizesDescribingTheIsle = nr22at65; break;
			case 66:
				rasterIndizesDescribingTheIsle = nr22at66; break;
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
			return heightIsle22at00;
		case 1:
			return heightIsle22at01;
		case 2:
			return heightIsle22at02;
		case 3:
			return heightIsle22at03;
		case 9:
			return heightIsle22at09;
		case 10:
			return heightIsle22at10;
		case 11:
			return heightIsle22at11;
		case 12:
			return heightIsle22at12;
		case 18:
			return heightIsle22at18;
		case 19:
			return heightIsle22at19;
		case 20:
			return heightIsle22at20;
		case 21:
			return heightIsle22at21;
		case 27:
			return heightIsle22at27;
		case 28:
			return heightIsle22at28;
		case 29:
			return heightIsle22at29;
		case 30:
			return heightIsle22at30;
		case 36:
			return heightIsle22at36;
		case 37:
			return heightIsle22at37;
		case 38:
			return heightIsle22at38;
		case 39:
			return heightIsle22at39;
		case 45:
			return heightIsle22at45;
		case 46:
			return heightIsle22at46;
		case 47:
			return heightIsle22at47;
		case 48:
			return heightIsle22at48;
		case 54:
			return heightIsle22at54;
		case 55:
			return heightIsle22at55;
		case 56:
			return heightIsle22at56;
		case 57:
			return heightIsle22at57;
		case 63:
			return heightIsle22at63;
		case 64:
			return heightIsle22at64;
		case 65:
			return heightIsle22at65;
		case 66:
			return heightIsle22at66;
		default:
			return null;
		}
	}
}
