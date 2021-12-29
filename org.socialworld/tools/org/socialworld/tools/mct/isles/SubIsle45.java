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

public class SubIsle45 extends SubIsle {
	
	private static SubIsle45 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  6, 
											9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42};
	
	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  7,
											10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43};
	
	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  8,
											11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62};
	
	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69};
	
	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70};
	
	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71};
	
	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69,
											72, 73, 74, 75, 76, 77, 78};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70,
											73, 74, 75, 76, 77, 78, 79};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71,
											74, 75, 76, 77, 78, 79, 80};

	
	private List<Integer> nr45at00;
	private HeightIsle heightIsle45at00;

	private List<Integer> nr45at01;
	private HeightIsle heightIsle45at01;

	private List<Integer> nr45at02;
	private HeightIsle heightIsle45at02;

	private List<Integer> nr45at09;
	private HeightIsle heightIsle45at09;

	private List<Integer> nr45at10;
	private HeightIsle heightIsle45at10;

	private List<Integer> nr45at11;
	private HeightIsle heightIsle45at11;

	private List<Integer> nr45at18;
	private HeightIsle heightIsle45at18;

	private List<Integer> nr45at19;
	private HeightIsle heightIsle45at19;

	private List<Integer> nr45at20;
	private HeightIsle heightIsle45at20;
	
	private List<Integer> nr45at27;
	private HeightIsle heightIsle45at27;

	private List<Integer> nr45at28;
	private HeightIsle heightIsle45at28;

	private List<Integer> nr45at29;
	private HeightIsle heightIsle45at29;
	
	private List<Integer> nr45at36;
	private HeightIsle heightIsle45at36;

	private List<Integer> nr45at37;
	private HeightIsle heightIsle45at37;

	private List<Integer> nr45at38;
	private HeightIsle heightIsle45at38;
	
	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11994, 11994, 11194, 91194,
			19914, 19914, 11114, 11114, 11114, 91194, 91194,
			19914, 19114, 99114, 99114, 99114, 91114, 91194,
			19114, 99114, 99114, 99114, 99114, 99114, 91114
		};
	
	private List<Integer> cornerMaxima;

	private SubIsle45() {
		
cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr45at00 = Arrays.asList(at00);
		heightIsle45at00 = new HeightIsle(nr45at00, cornerMaxima);

		nr45at01 = Arrays.asList(at01);
		heightIsle45at01 = new HeightIsle(nr45at01, cornerMaxima);

		nr45at02 = Arrays.asList(at02);
		heightIsle45at02 = new HeightIsle(nr45at02, cornerMaxima);

		nr45at09 = Arrays.asList(at09);
		heightIsle45at09 = new HeightIsle(nr45at09, cornerMaxima);

		nr45at10 = Arrays.asList(at10);
		heightIsle45at10 = new HeightIsle(nr45at10, cornerMaxima);

		nr45at11 = Arrays.asList(at11);
		heightIsle45at11 = new HeightIsle(nr45at11, cornerMaxima);

		nr45at18 = Arrays.asList(at18);
		heightIsle45at18 = new HeightIsle(nr45at18, cornerMaxima);

		nr45at19 = Arrays.asList(at19);
		heightIsle45at19 = new HeightIsle(nr45at19, cornerMaxima);

		nr45at20 = Arrays.asList(at20);
		heightIsle45at20 = new HeightIsle(nr45at20, cornerMaxima);
		
		nr45at27 = Arrays.asList(at27);
		heightIsle45at27 = new HeightIsle(nr45at27, cornerMaxima);

		nr45at28 = Arrays.asList(at28);
		heightIsle45at28 = new HeightIsle(nr45at28, cornerMaxima);

		nr45at29 = Arrays.asList(at29);
		heightIsle45at29 = new HeightIsle(nr45at29, cornerMaxima);
		
		nr45at36 = Arrays.asList(at36);
		heightIsle45at36 = new HeightIsle(nr45at36, cornerMaxima);

		nr45at37 = Arrays.asList(at37);
		heightIsle45at37 = new HeightIsle(nr45at37, cornerMaxima);

		nr45at38 = Arrays.asList(at38);
		heightIsle45at38 = new HeightIsle(nr45at38, cornerMaxima);
		
	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle45();
		}
		return instance;
		
	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 38) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr45at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr45at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr45at02; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr45at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr45at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr45at11; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr45at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr45at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr45at20; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr45at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr45at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr45at29; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr45at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr45at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr45at38; break;
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
			return heightIsle45at00;
		case 1:
			return heightIsle45at01;
		case 2:
			return heightIsle45at02;
		case 9:
			return heightIsle45at09;
		case 10:
			return heightIsle45at10;
		case 11:
			return heightIsle45at11;
		case 18:
			return heightIsle45at18;
		case 19:
			return heightIsle45at19;
		case 20:
			return heightIsle45at20;
		case 27:
			return heightIsle45at27;
		case 28:
			return heightIsle45at28;
		case 29:
			return heightIsle45at29;
		case 36:
			return heightIsle45at36;
		case 37:
			return heightIsle45at37;
		case 38:
			return heightIsle45at38;
		default:
			return null;
		}
	}

}
