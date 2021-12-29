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

public class SubIsle43 extends SubIsle {
	
	private static SubIsle43 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5, 
											9,  10, 11, 12, 13, 14,
											18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41};
	
	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,
											10, 11, 12, 13, 14, 15,
											19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42};
	
	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,
											11, 12, 13, 14, 15, 16,
											20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43};
	
	private final static Integer at03[]  = {3,  4,  5,  6,  7,  8,
											12, 13, 14, 15, 16, 17,
											21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14,
											18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15,
											19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16,
											20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52};
	
	private final static Integer at12[]  = {12, 13, 14, 15, 16, 17,
											21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43, 
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61};
	
	private final static Integer at21[]  = {21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62};
	
	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59,
											63, 64, 65, 66, 67, 68};
	
	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60,
											64, 65, 66, 67, 68, 69};
	
	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61,
											65, 66, 67, 68, 69, 70};
	
	private final static Integer at30[]  = {30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62,
											66, 67, 68, 69, 70, 71};
	
	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59,
											63, 64, 65, 66, 67, 68,
											72, 73, 74, 75, 76, 77};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60,
											64, 65, 66, 67, 68, 69,
											73, 74, 75, 76, 77, 78};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61,
											65, 66, 67, 68, 69, 70,
											74, 75, 76, 77, 78, 79};
	
	private final static Integer at39[]  = {39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62,
											66, 67, 68, 69, 70, 71,
											75, 76, 77, 78, 79, 80};

	
	private List<Integer> nr43at00;
	private HeightIsle heightIsle43at00;

	private List<Integer> nr43at01;
	private HeightIsle heightIsle43at01;

	private List<Integer> nr43at02;
	private HeightIsle heightIsle43at02;
	
	private List<Integer> nr43at03;
	private HeightIsle heightIsle43at03;

	private List<Integer> nr43at09;
	private HeightIsle heightIsle43at09;

	private List<Integer> nr43at10;
	private HeightIsle heightIsle43at10;

	private List<Integer> nr43at11;
	private HeightIsle heightIsle43at11;
	
	private List<Integer> nr43at12;
	private HeightIsle heightIsle43at12;

	private List<Integer> nr43at18;
	private HeightIsle heightIsle43at18;

	private List<Integer> nr43at19;
	private HeightIsle heightIsle43at19;

	private List<Integer> nr43at20;
	private HeightIsle heightIsle43at20;
	
	private List<Integer> nr43at21;
	private HeightIsle heightIsle43at21;
	
	private List<Integer> nr43at27;
	private HeightIsle heightIsle43at27;

	private List<Integer> nr43at28;
	private HeightIsle heightIsle43at28;

	private List<Integer> nr43at29;
	private HeightIsle heightIsle43at29;
	
	private List<Integer> nr43at30;
	private HeightIsle heightIsle43at30;
	
	private List<Integer> nr43at36;
	private HeightIsle heightIsle43at36;

	private List<Integer> nr43at37;
	private HeightIsle heightIsle43at37;

	private List<Integer> nr43at38;
	private HeightIsle heightIsle43at38;
	
	private List<Integer> nr43at39;
	private HeightIsle heightIsle43at39;
	
	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11994, 11194, 91194,
			19914, 19914, 11114, 11114, 91194, 91194,
			19914, 19114, 99114, 99114, 91114, 91194,
			19114, 99114, 99114, 99114, 99114, 91114
		};
	
	private List<Integer> cornerMaxima;

	private SubIsle43() {
		
cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr43at00 = Arrays.asList(at00);
		heightIsle43at00 = new HeightIsle(nr43at00, cornerMaxima);

		nr43at01 = Arrays.asList(at01);
		heightIsle43at01 = new HeightIsle(nr43at01, cornerMaxima);

		nr43at02 = Arrays.asList(at02);
		heightIsle43at02 = new HeightIsle(nr43at02, cornerMaxima);
		
		nr43at03 = Arrays.asList(at03);
		heightIsle43at03 = new HeightIsle(nr43at03, cornerMaxima);

		nr43at09 = Arrays.asList(at09);
		heightIsle43at09 = new HeightIsle(nr43at09, cornerMaxima);

		nr43at10 = Arrays.asList(at10);
		heightIsle43at10 = new HeightIsle(nr43at10, cornerMaxima);

		nr43at11 = Arrays.asList(at11);
		heightIsle43at11 = new HeightIsle(nr43at11, cornerMaxima);
		
		nr43at12 = Arrays.asList(at12);
		heightIsle43at12 = new HeightIsle(nr43at12, cornerMaxima);

		nr43at18 = Arrays.asList(at18);
		heightIsle43at18 = new HeightIsle(nr43at18, cornerMaxima);

		nr43at19 = Arrays.asList(at19);
		heightIsle43at19 = new HeightIsle(nr43at19, cornerMaxima);

		nr43at20 = Arrays.asList(at20);
		heightIsle43at20 = new HeightIsle(nr43at20, cornerMaxima);
		
		nr43at21 = Arrays.asList(at21);
		heightIsle43at21 = new HeightIsle(nr43at21, cornerMaxima);
		
		nr43at27 = Arrays.asList(at27);
		heightIsle43at27 = new HeightIsle(nr43at27, cornerMaxima);

		nr43at28 = Arrays.asList(at28);
		heightIsle43at28 = new HeightIsle(nr43at28, cornerMaxima);

		nr43at29 = Arrays.asList(at29);
		heightIsle43at29 = new HeightIsle(nr43at29, cornerMaxima);
		
		nr43at30 = Arrays.asList(at30);
		heightIsle43at30 = new HeightIsle(nr43at30, cornerMaxima);
		
		nr43at36 = Arrays.asList(at36);
		heightIsle43at36 = new HeightIsle(nr43at36, cornerMaxima);

		nr43at37 = Arrays.asList(at37);
		heightIsle43at37 = new HeightIsle(nr43at37, cornerMaxima);

		nr43at38 = Arrays.asList(at38);
		heightIsle43at38 = new HeightIsle(nr43at38, cornerMaxima);
		
		nr43at39 = Arrays.asList(at39);
		heightIsle43at39 = new HeightIsle(nr43at39, cornerMaxima);
		
	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle43();
		}
		return instance;
		
	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 39) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr43at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr43at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr43at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr43at03; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr43at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr43at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr43at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr43at12; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr43at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr43at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr43at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr43at21; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr43at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr43at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr43at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr43at30; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr43at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr43at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr43at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr43at39; break;
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
			return heightIsle43at00;
		case 1:
			return heightIsle43at01;
		case 2:
			return heightIsle43at02;
		case 3:
			return heightIsle43at03;
		case 9:
			return heightIsle43at09;
		case 10:
			return heightIsle43at10;
		case 11:
			return heightIsle43at11;
		case 12:
			return heightIsle43at12;
		case 18:
			return heightIsle43at18;
		case 19:
			return heightIsle43at19;
		case 20:
			return heightIsle43at20;
		case 21:
			return heightIsle43at21;
		case 27:
			return heightIsle43at27;
		case 28:
			return heightIsle43at28;
		case 29:
			return heightIsle43at29;
		case 30:
			return heightIsle43at30;
		case 36:
			return heightIsle43at36;
		case 37:
			return heightIsle43at37;
		case 38:
			return heightIsle43at38;
		case 39:
			return heightIsle43at39;
		default:
			return null;
		}
	}

}
