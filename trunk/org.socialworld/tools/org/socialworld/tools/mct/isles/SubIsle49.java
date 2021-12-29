/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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


public class SubIsle49 extends SubIsle{

	
	private static SubIsle49 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  6, 
											9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  7,
											10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  8,
											11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71};
	
	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69,
											72, 73, 74, 75, 76, 77, 78};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70,
											73, 74, 75, 76, 77, 78, 79};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71,
											74, 75, 76, 77, 78, 79, 80};
	
	private List<Integer> nr49at00;
	private HeightIsle heightIsle49at00;

	private List<Integer> nr49at01;
	private HeightIsle heightIsle49at01;

	private List<Integer> nr49at02;
	private HeightIsle heightIsle49at02;

	private List<Integer> nr49at09;
	private HeightIsle heightIsle49at09;

	private List<Integer> nr49at10;
	private HeightIsle heightIsle49at10;

	private List<Integer> nr49at11;
	private HeightIsle heightIsle49at11;

	private List<Integer> nr49at18;
	private HeightIsle heightIsle49at18;

	private List<Integer> nr49at19;
	private HeightIsle heightIsle49at19;

	private List<Integer> nr49at20;
	private HeightIsle heightIsle49at20;


	private final static Integer[] cornerMaximaNrs = {
		11914, 11994, 11994, 11994, 11994, 11994, 11194,	
		19914, 11914, 11994, 11994, 11994, 11194, 91194,
		19914, 19914, 11914, 11994, 11194, 91194, 91194,
		19914, 19914, 19914, 11114, 91194, 91194, 91194,
		19914, 19914, 19114, 99114, 91114, 91194, 91194,
		19914, 19114, 99114, 99114, 99114, 91114, 91194,
		19114, 99114, 99114, 99114, 99114, 99114, 91114
	};
	
	private List<Integer> cornerMaxima;

	private SubIsle49() {
		
		cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr49at00 = Arrays.asList(at00);
		heightIsle49at00 = new HeightIsle(nr49at00, cornerMaxima);

		nr49at01 = Arrays.asList(at01);
		heightIsle49at01 = new HeightIsle(nr49at01, cornerMaxima);

		nr49at02 = Arrays.asList(at02);
		heightIsle49at02 = new HeightIsle(nr49at02, cornerMaxima);

		nr49at09 = Arrays.asList(at09);
		heightIsle49at09 = new HeightIsle(nr49at09, cornerMaxima);

		nr49at10 = Arrays.asList(at10);
		heightIsle49at10 = new HeightIsle(nr49at10, cornerMaxima);

		nr49at11 = Arrays.asList(at11);
		heightIsle49at11 = new HeightIsle(nr49at11, cornerMaxima);

		nr49at18 = Arrays.asList(at18);
		heightIsle49at18 = new HeightIsle(nr49at18, cornerMaxima);

		nr49at19 = Arrays.asList(at19);
		heightIsle49at19 = new HeightIsle(nr49at19, cornerMaxima);

		nr49at20 = Arrays.asList(at20);
		heightIsle49at20 = new HeightIsle(nr49at20, cornerMaxima);

	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle49();
		}
		return instance;
		
	}
	
	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 20) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr49at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr49at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr49at02; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr49at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr49at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr49at11; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr49at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr49at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr49at20; break;
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
			return heightIsle49at00;
		case 1:
			return heightIsle49at01;
		case 2:
			return heightIsle49at02;
		case 9:
			return heightIsle49at09;
		case 10:
			return heightIsle49at10;
		case 11:
			return heightIsle49at11;
		case 18:
			return heightIsle49at18;
		case 19:
			return heightIsle49at19;
		case 20:
			return heightIsle49at20;
		default:
			return null;
		}
		
	}
	

}
