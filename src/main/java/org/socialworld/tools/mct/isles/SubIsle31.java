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

public class SubIsle31 extends SubIsle {

	private static SubIsle31 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  
						9,  10, 11, 12, 13, 14, 
						18, 19, 20, 21, 22, 23};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  
						10, 11, 12, 13, 14, 15, 
						19, 20, 21, 22, 23, 24};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  
						11, 12, 13, 14, 15, 16, 
						20, 21, 22, 23, 24, 25};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  8,  
						12, 13, 14, 15, 16, 17, 
						21, 22, 23, 24, 25, 26};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 
						18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 
						19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 
						20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 17, 
						21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59, 
						63, 64, 65, 66, 67, 68};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60, 
						64, 65, 66, 67, 68, 69};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61, 
						65, 66, 67, 68, 69, 70};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62, 
						66, 67, 68, 69, 70, 71};

	private final static Integer at54[]  = {54, 55, 56, 57, 58, 59, 
						63, 64, 65, 66, 67, 68, 
						72, 73, 74, 75, 76, 77};

	private final static Integer at55[]  = {55, 56, 57, 58, 59, 60, 
						64, 65, 66, 67, 68, 69, 
						73, 74, 75, 76, 77, 78};

	private final static Integer at56[]  = {56, 57, 58, 59, 60, 61, 
						65, 66, 67, 68, 69, 70, 
						74, 75, 76, 77, 78, 79};

	private final static Integer at57[]  = {57, 58, 59, 60, 61, 62, 
						66, 67, 68, 69, 70, 71, 
						75, 76, 77, 78, 79, 80};

	private List<Integer> nr31at00;
	private HeightIsle heightIsle31at00;

	private List<Integer> nr31at01;
	private HeightIsle heightIsle31at01;

	private List<Integer> nr31at02;
	private HeightIsle heightIsle31at02;

	private List<Integer> nr31at03;
	private HeightIsle heightIsle31at03;

	private List<Integer> nr31at09;
	private HeightIsle heightIsle31at09;

	private List<Integer> nr31at10;
	private HeightIsle heightIsle31at10;

	private List<Integer> nr31at11;
	private HeightIsle heightIsle31at11;

	private List<Integer> nr31at12;
	private HeightIsle heightIsle31at12;

	private List<Integer> nr31at18;
	private HeightIsle heightIsle31at18;

	private List<Integer> nr31at19;
	private HeightIsle heightIsle31at19;

	private List<Integer> nr31at20;
	private HeightIsle heightIsle31at20;

	private List<Integer> nr31at21;
	private HeightIsle heightIsle31at21;

	private List<Integer> nr31at27;
	private HeightIsle heightIsle31at27;

	private List<Integer> nr31at28;
	private HeightIsle heightIsle31at28;

	private List<Integer> nr31at29;
	private HeightIsle heightIsle31at29;

	private List<Integer> nr31at30;
	private HeightIsle heightIsle31at30;

	private List<Integer> nr31at36;
	private HeightIsle heightIsle31at36;

	private List<Integer> nr31at37;
	private HeightIsle heightIsle31at37;

	private List<Integer> nr31at38;
	private HeightIsle heightIsle31at38;

	private List<Integer> nr31at39;
	private HeightIsle heightIsle31at39;

	private List<Integer> nr31at45;
	private HeightIsle heightIsle31at45;

	private List<Integer> nr31at46;
	private HeightIsle heightIsle31at46;

	private List<Integer> nr31at47;
	private HeightIsle heightIsle31at47;

	private List<Integer> nr31at48;
	private HeightIsle heightIsle31at48;

	private List<Integer> nr31at54;
	private HeightIsle heightIsle31at54;

	private List<Integer> nr31at55;
	private HeightIsle heightIsle31at55;

	private List<Integer> nr31at56;
	private HeightIsle heightIsle31at56;

	private List<Integer> nr31at57;
	private HeightIsle heightIsle31at57;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11994, 11194,	
			19914, 11114, 11114, 11114, 11114, 91194,
			19114, 99114, 99114, 99114, 99114, 91114
		};

	private final static Integer[] isleRingNrs = {
			1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1
		};

	private List<Integer> cornerMaxima;

	private SubIsle31() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr31at00 = Arrays.asList(at00);
		heightIsle31at00 = new HeightIsle(nr31at00, cornerMaxima);

		nr31at01 = Arrays.asList(at01);
		heightIsle31at01 = new HeightIsle(nr31at01, cornerMaxima);

		nr31at02 = Arrays.asList(at02);
		heightIsle31at02 = new HeightIsle(nr31at02, cornerMaxima);

		nr31at03 = Arrays.asList(at03);
		heightIsle31at03 = new HeightIsle(nr31at03, cornerMaxima);

		nr31at09 = Arrays.asList(at09);
		heightIsle31at09 = new HeightIsle(nr31at09, cornerMaxima);

		nr31at10 = Arrays.asList(at10);
		heightIsle31at10 = new HeightIsle(nr31at10, cornerMaxima);

		nr31at11 = Arrays.asList(at11);
		heightIsle31at11 = new HeightIsle(nr31at11, cornerMaxima);

		nr31at12 = Arrays.asList(at12);
		heightIsle31at12 = new HeightIsle(nr31at12, cornerMaxima);

		nr31at18 = Arrays.asList(at18);
		heightIsle31at18 = new HeightIsle(nr31at18, cornerMaxima);

		nr31at19 = Arrays.asList(at19);
		heightIsle31at19 = new HeightIsle(nr31at19, cornerMaxima);

		nr31at20 = Arrays.asList(at20);
		heightIsle31at20 = new HeightIsle(nr31at20, cornerMaxima);

		nr31at21 = Arrays.asList(at21);
		heightIsle31at21 = new HeightIsle(nr31at21, cornerMaxima);

		nr31at27 = Arrays.asList(at27);
		heightIsle31at27 = new HeightIsle(nr31at27, cornerMaxima);

		nr31at28 = Arrays.asList(at28);
		heightIsle31at28 = new HeightIsle(nr31at28, cornerMaxima);

		nr31at29 = Arrays.asList(at29);
		heightIsle31at29 = new HeightIsle(nr31at29, cornerMaxima);

		nr31at30 = Arrays.asList(at30);
		heightIsle31at30 = new HeightIsle(nr31at30, cornerMaxima);

		nr31at36 = Arrays.asList(at36);
		heightIsle31at36 = new HeightIsle(nr31at36, cornerMaxima);

		nr31at37 = Arrays.asList(at37);
		heightIsle31at37 = new HeightIsle(nr31at37, cornerMaxima);

		nr31at38 = Arrays.asList(at38);
		heightIsle31at38 = new HeightIsle(nr31at38, cornerMaxima);

		nr31at39 = Arrays.asList(at39);
		heightIsle31at39 = new HeightIsle(nr31at39, cornerMaxima);

		nr31at45 = Arrays.asList(at45);
		heightIsle31at45 = new HeightIsle(nr31at45, cornerMaxima);

		nr31at46 = Arrays.asList(at46);
		heightIsle31at46 = new HeightIsle(nr31at46, cornerMaxima);

		nr31at47 = Arrays.asList(at47);
		heightIsle31at47 = new HeightIsle(nr31at47, cornerMaxima);

		nr31at48 = Arrays.asList(at48);
		heightIsle31at48 = new HeightIsle(nr31at48, cornerMaxima);

		nr31at54 = Arrays.asList(at54);
		heightIsle31at54 = new HeightIsle(nr31at54, cornerMaxima);

		nr31at55 = Arrays.asList(at55);
		heightIsle31at55 = new HeightIsle(nr31at55, cornerMaxima);

		nr31at56 = Arrays.asList(at56);
		heightIsle31at56 = new HeightIsle(nr31at56, cornerMaxima);

		nr31at57 = Arrays.asList(at57);
		heightIsle31at57 = new HeightIsle(nr31at57, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle31();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 57) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr31at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr31at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr31at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr31at03; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr31at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr31at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr31at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr31at12; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr31at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr31at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr31at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr31at21; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr31at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr31at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr31at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr31at30; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr31at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr31at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr31at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr31at39; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr31at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr31at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr31at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr31at48; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr31at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr31at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr31at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr31at57; break;
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
			return heightIsle31at00;
		case 1:
			return heightIsle31at01;
		case 2:
			return heightIsle31at02;
		case 3:
			return heightIsle31at03;
		case 9:
			return heightIsle31at09;
		case 10:
			return heightIsle31at10;
		case 11:
			return heightIsle31at11;
		case 12:
			return heightIsle31at12;
		case 18:
			return heightIsle31at18;
		case 19:
			return heightIsle31at19;
		case 20:
			return heightIsle31at20;
		case 21:
			return heightIsle31at21;
		case 27:
			return heightIsle31at27;
		case 28:
			return heightIsle31at28;
		case 29:
			return heightIsle31at29;
		case 30:
			return heightIsle31at30;
		case 36:
			return heightIsle31at36;
		case 37:
			return heightIsle31at37;
		case 38:
			return heightIsle31at38;
		case 39:
			return heightIsle31at39;
		case 45:
			return heightIsle31at45;
		case 46:
			return heightIsle31at46;
		case 47:
			return heightIsle31at47;
		case 48:
			return heightIsle31at48;
		case 54:
			return heightIsle31at54;
		case 55:
			return heightIsle31at55;
		case 56:
			return heightIsle31at56;
		case 57:
			return heightIsle31at57;
		default:
			return null;
		}
	}
}
