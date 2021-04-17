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


public class SubIsle47 extends SubIsle{

	
	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5, 
											9,  10, 11, 12, 13, 14,
											18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,
											10, 11, 12, 13, 14, 15,
											19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,
											11, 12, 13, 14, 15, 16,
											20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61;
	
	private final static Integer at03[]  = {3,  4,  5,  6,  7,  8,
											12, 13, 14, 15, 16, 17,
											21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14,
											18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59,
											63, 64, 65, 66, 67, 68};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15,
											19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60,
											64, 65, 66, 67, 68, 69};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16,
											20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61,
											65, 66, 67, 68, 69, 70};
	
	private final static Integer at12[]  = {12, 13, 14, 15, 16, 17,
											21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62,
											66, 67, 68, 69, 70, 71};
	
	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23,
											27, 28, 29, 30, 31, 32,
											36, 37, 38, 39, 40, 41,
											45, 46, 47, 48, 49, 50,
											54, 55, 56, 57, 58, 59,
											63, 64, 65, 66, 67, 68,
											72, 73, 74, 75, 76, 77};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24,
											28, 29, 30, 31, 32, 33,
											37, 38, 39, 40, 41, 42,
											46, 47, 48, 49, 50, 51,
											55, 56, 57, 58, 59, 60,
											64, 65, 66, 67, 68, 69,
											73, 74, 75, 76, 77, 78};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25,
											29, 30, 31, 32, 33, 34,
											38, 39, 40, 41, 42, 43,
											47, 48, 49, 50, 51, 52,
											56, 57, 58, 59, 60, 61,
											65, 66, 67, 68, 69, 70,
											74, 75, 76, 77, 78, 79};
	
	private final static Integer at21[]  = {21, 22, 23, 24, 25, 26,
											30, 31, 32, 33, 34, 35,
											39, 40, 41, 42, 43, 44,
											48, 49, 50, 51, 52, 53,
											57, 58, 59, 60, 61, 62,
											66, 67, 68, 69, 70, 71,
											75, 76, 77, 78, 79, 80};
	
	private List<Integer> nr47at00;
	private HeightIsle heightIsle47at00;

	private List<Integer> nr47at01;
	private HeightIsle heightIsle47at01;

	private List<Integer> nr47at02;
	private HeightIsle heightIsle47at02;
	
	private List<Integer> nr47at03;
	private HeightIsle heightIsle47at03;

	private List<Integer> nr47at09;
	private HeightIsle heightIsle47at09;

	private List<Integer> nr47at10;
	private HeightIsle heightIsle47at10;

	private List<Integer> nr47at11;
	private HeightIsle heightIsle47at11;
	
	private List<Integer> nr47at12;
	private HeightIsle heightIsle47at12;

	private List<Integer> nr47at18;
	private HeightIsle heightIsle47at18;

	private List<Integer> nr47at19;
	private HeightIsle heightIsle47at19;

	private List<Integer> nr47at20;
	private HeightIsle heightIsle47at20;
	
	private List<Integer> nr47at21;
	private HeightIsle heightIsle47at21;


	private final static Integer[] cornerMaximaNrs = {
		11914, 11994, 11994, 11994, 11994, 11994,	
		19914, 11914, 11994, 11994, 11994, 11194,
		19914, 19914, 11914, 11994, 11194, 91194,
		19914, 19914, 19914, 11114, 91194, 91194,
		19914, 19914, 19114, 99114, 91114, 91194,
		19914, 19114, 99114, 99114, 99114, 91114,
		19114, 99114, 99114, 99114, 99114, 99114
	};
	
	private List<Integer> cornerMaxima;

	private SubIsle47() {
		
		cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr47at00 = Arrays.asList(at00);
		heightIsle47at00 = new HeightIsle(nr47at00, cornerMaxima);

		nr47at01 = Arrays.asList(at01);
		heightIsle47at01 = new HeightIsle(nr47at01, cornerMaxima);

		nr47at02 = Arrays.asList(at02);
		heightIsle47at02 = new HeightIsle(nr47at02, cornerMaxima);
		
		nr47at03 = Arrays.asList(at03);
		heightIsle47at03 = new HeightIsle(nr47at03, cornerMaxima);

		nr47at09 = Arrays.asList(at09);
		heightIsle47at09 = new HeightIsle(nr47at09, cornerMaxima);

		nr47at10 = Arrays.asList(at10);
		heightIsle47at10 = new HeightIsle(nr47at10, cornerMaxima);

		nr47at11 = Arrays.asList(at11);
		heightIsle47at11 = new HeightIsle(nr47at11, cornerMaxima);
		
		nr47at12 = Arrays.asList(at12);
		heightIsle47at12 = new HeightIsle(nr47at12, cornerMaxima);

		nr47at18 = Arrays.asList(at18);
		heightIsle47at18 = new HeightIsle(nr47at18, cornerMaxima);

		nr47at19 = Arrays.asList(at19);
		heightIsle47at19 = new HeightIsle(nr47at19, cornerMaxima);

		nr47at20 = Arrays.asList(at20);
		heightIsle47at20 = new HeightIsle(nr47at20, cornerMaxima);
		
		nr47at21 = Arrays.asList(at21);
		heightIsle47at21 = new HeightIsle(nr47at21, cornerMaxima);

	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle47();
		}
		return instance;
		
	}
	
	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 21) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr47at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr47at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr47at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr47at03; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr47at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr47at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr47at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr47at12; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr47at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr47at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr47at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr47at21; break;
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
			return heightIsle47at00;
		case 1:
			return heightIsle47at01;
		case 2:
			return heightIsle47at02;
		case 3:
			return heightIsle47at03;
		case 9:
			return heightIsle47at09;
		case 10:
			return heightIsle47at10;
		case 11:
			return heightIsle47at11;
		case 12:
			return heightIsle47at12;
		case 18:
			return heightIsle47at18;
		case 19:
			return heightIsle47at19;
		case 20:
			return heightIsle47at20;
		case 21:
			return heightIsle47at21;
		default:
			return null;
		}
		
	}
	

}
