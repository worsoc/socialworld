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


public class SubIsle44 extends SubIsle{

	
	private final static Integer at00[]  = {0,  1,  2,  3,  4, 
											9,  10, 11, 12, 13,
											18, 19, 20, 21, 22,
											27, 28, 29, 30, 31,
											36, 37, 38, 39, 40,
											45, 46, 47, 48, 49,
											54, 55, 56, 57, 58};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,
											10, 11, 12, 13, 14,
											19, 20, 21, 22, 23,
											28, 29, 30, 31, 32,
											37, 38, 39, 40, 41,
											46, 47, 48, 49, 50,
											55, 56, 57, 58, 59};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,
											11, 12, 13, 14, 15,
											20, 21, 22, 23, 24,
											29, 30, 31, 32, 33,
											38, 39, 40, 41, 42,
											47, 48, 49, 50, 51,
											56, 57, 58, 59, 60};
	
	private final static Integer at03[]  = {3,  4,  5,  6,  7,
											12, 13, 14, 15, 16,
											21, 22, 23, 24, 25,
											30, 31, 32, 33, 34,
											39, 40, 41, 42, 43,
											48, 49, 50, 51, 52,
											57, 58, 59, 60, 61};
	
	private final static Integer at04[]  = {4,  5,  6,  7,  8,
											13, 14, 15, 16, 17,
											22, 23, 24, 25, 26,
											31, 32, 33, 34, 35,
											40, 41, 42, 43, 44,
											49, 50, 51, 52, 53,
											58, 59, 60, 61, 62};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13,
											18, 19, 20, 21, 22,
											27, 28, 29, 30, 31,
											36, 37, 38, 39, 40,
											45, 46, 47, 48, 49,
											54, 55, 56, 57, 58,
											63, 64, 65, 66, 67};

	private final static Integer at10[]  = {10, 11, 12, 13, 14,
											19, 20, 21, 22, 23,
											28, 29, 30, 31, 32,
											37, 38, 39, 40, 41,
											46, 47, 48, 49, 50,
											55, 56, 57, 58, 59,
											64, 65, 66, 67, 68};

	private final static Integer at11[]  = {11, 12, 13, 14, 15,
											20, 21, 22, 23, 24,
											29, 30, 31, 32, 33,
											38, 39, 40, 41, 42,
											47, 48, 49, 50, 51, 
											56, 57, 58, 59, 60, 
											65, 66, 67, 68, 69};
	
	private final static Integer at12[]  = {12, 13, 14, 15, 16,
											21, 22, 23, 24, 25,
											30, 31, 32, 33, 34,
											39, 40, 41, 42, 43,
											48, 49, 50, 51, 52,
											57, 58, 59, 60, 61, 
											66, 67, 68, 69, 70};
	
	private final static Integer at13[]  = {13, 14, 15, 16, 17,
											22, 23, 24, 25, 26,
											31, 32, 33, 34, 35,
											40, 41, 42, 43, 44,
											49, 50, 51, 52, 53,
											58, 59, 60, 61, 62,
											67, 68, 69, 70, 71};
	
	private final static Integer at18[]  = {18, 19, 20, 21, 22,
											27, 28, 29, 30, 31, 
											36, 37, 38, 39, 40,
											45, 46, 47, 48, 49,
											54, 55, 56, 57, 58,
											63, 64, 65, 66, 67,
											72, 73, 74, 75, 76};

	private final static Integer at19[]  = {19, 20, 21, 22, 23,
											28, 29, 30, 31, 32,
											37, 38, 39, 40, 41,
											46, 47, 48, 49, 50,
											55, 56, 57, 58, 59,
											64, 65, 66, 67, 68,
											73, 74, 75, 76, 77};

	private final static Integer at20[]  = {20, 21, 22, 23, 24,
											29, 30, 31, 32, 33,
											38, 39, 40, 41, 42,
											47, 48, 49, 50, 51,
											56, 57, 58, 59, 60,
											65, 66, 67, 68, 69,
											74, 75, 76, 77, 78};
	
	private final static Integer at21[]  = {21, 22, 23, 24, 25,
											30, 31, 32, 33, 34,
											39, 40, 41, 42, 43,
											48, 49, 50, 51, 52,
											57, 58, 59, 60, 61,
											66, 67, 68, 69, 70,
											75, 76, 77, 78, 79};

	private final static Integer at22[]  = {22, 23, 24, 25, 26,
											31, 32, 33, 34, 35,
											40, 41, 42, 43, 44,
											49, 50, 51, 52, 53,
											58, 59, 60, 61, 62,
											67, 68, 69, 70, 71,
											76, 77, 78, 79, 80};
		
	private List<Integer> nr44at00;
	private HeightIsle heightIsle44at00;

	private List<Integer> nr44at01;
	private HeightIsle heightIsle44at01;

	private List<Integer> nr44at02;
	private HeightIsle heightIsle44at02;
	
	private List<Integer> nr44at03;
	private HeightIsle heightIsle44at03;
	
	private List<Integer> nr44at04;
	private HeightIsle heightIsle44at04;

	private List<Integer> nr44at09;
	private HeightIsle heightIsle44at09;

	private List<Integer> nr44at10;
	private HeightIsle heightIsle44at10;

	private List<Integer> nr44at11;
	private HeightIsle heightIsle44at11;
	
	private List<Integer> nr44at12;
	private HeightIsle heightIsle44at12;
	
	private List<Integer> nr44at13;
	private HeightIsle heightIsle44at13;

	private List<Integer> nr44at18;
	private HeightIsle heightIsle44at18;

	private List<Integer> nr44at19;
	private HeightIsle heightIsle44at19;

	private List<Integer> nr44at20;
	private HeightIsle heightIsle44at20;
	
	private List<Integer> nr44at21;
	private HeightIsle heightIsle44at21;
	
	private List<Integer> nr44at22;
	private HeightIsle heightIsle44at22;



	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11194, 91194,
			19914, 19914, 11114, 91194, 91194,
			19914, 19914, 11114, 91194, 91194,
			19914, 19914, 11114, 91194, 91194,
			19914, 19114, 99114, 91114, 91194,
			19114, 99114, 99114, 99114, 91114
		};
	
	private List<Integer> cornerMaxima;

	private SubIsle44() {
		
		cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr44at00 = Arrays.asList(at00);
		heightIsle44at00 = new HeightIsle(nr44at00, cornerMaxima);

		nr44at01 = Arrays.asList(at01);
		heightIsle44at01 = new HeightIsle(nr44at01, cornerMaxima);

		nr44at02 = Arrays.asList(at02);
		heightIsle44at02 = new HeightIsle(nr44at02, cornerMaxima);
		
		nr44at03 = Arrays.asList(at03);
		heightIsle44at03 = new HeightIsle(nr44at03, cornerMaxima);
		
		nr44at04 = Arrays.asList(at04);
		heightIsle44at04 = new HeightIsle(nr44at04, cornerMaxima);

		nr44at09 = Arrays.asList(at09);
		heightIsle44at09 = new HeightIsle(nr44at09, cornerMaxima);

		nr44at10 = Arrays.asList(at10);
		heightIsle44at10 = new HeightIsle(nr44at10, cornerMaxima);

		nr44at11 = Arrays.asList(at11);
		heightIsle44at11 = new HeightIsle(nr44at11, cornerMaxima);
		
		nr44at12 = Arrays.asList(at12);
		heightIsle44at12 = new HeightIsle(nr44at12, cornerMaxima);
		
		nr44at13 = Arrays.asList(at13);
		heightIsle44at13 = new HeightIsle(nr44at13, cornerMaxima);

		nr44at18 = Arrays.asList(at18);
		heightIsle44at18 = new HeightIsle(nr44at18, cornerMaxima);

		nr44at19 = Arrays.asList(at19);
		heightIsle44at19 = new HeightIsle(nr44at19, cornerMaxima);

		nr44at20 = Arrays.asList(at20);
		heightIsle44at20 = new HeightIsle(nr44at20, cornerMaxima);
		
		nr44at21 = Arrays.asList(at21);
		heightIsle44at21 = new HeightIsle(nr44at21, cornerMaxima);
		
		nr44at22 = Arrays.asList(at22);
		heightIsle44at22 = new HeightIsle(nr44at22, cornerMaxima);

	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle44();
		}
		return instance;
		
	}
	
	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 22) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr44at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr44at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr44at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr44at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr44at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr44at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr44at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr44at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr44at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr44at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr44at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr44at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr44at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr44at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr44at22; break;
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
			return heightIsle44at00;
		case 1:
			return heightIsle44at01;
		case 2:
			return heightIsle44at02;
		case 3:
			return heightIsle44at03;
		case 4:
			return heightIsle44at04;
		case 9:
			return heightIsle44at09;
		case 10:
			return heightIsle44at10;
		case 11:
			return heightIsle44at11;
		case 12:
			return heightIsle44at12;
		case 13:
			return heightIsle44at13;
		case 18:
			return heightIsle44at18;
		case 19:
			return heightIsle44at19;
		case 20:
			return heightIsle44at20;
		case 21:
			return heightIsle44at21;
		case 22:
			return heightIsle44at22;
		default:
			return null;
		}
		
	}
	

}
