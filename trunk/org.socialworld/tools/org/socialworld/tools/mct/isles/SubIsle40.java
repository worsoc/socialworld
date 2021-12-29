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

public class SubIsle40 extends SubIsle {

	private static SubIsle40 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  6,  
						9,  10, 11, 12, 13, 14, 15, 
						18, 19, 20, 21, 22, 23, 24, 
						27, 28, 29, 30, 31, 32, 33};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  7,  
						10, 11, 12, 13, 14, 15, 16, 
						19, 20, 21, 22, 23, 24, 25, 
						28, 29, 30, 31, 32, 33, 34};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  8,  
						11, 12, 13, 14, 15, 16, 17, 
						20, 21, 22, 23, 24, 25, 26, 
						29, 30, 31, 32, 33, 34, 35};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 15, 
						18, 19, 20, 21, 22, 23, 24, 
						27, 28, 29, 30, 31, 32, 33, 
						36, 37, 38, 39, 40, 41, 42};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 16, 
						19, 20, 21, 22, 23, 24, 25, 
						28, 29, 30, 31, 32, 33, 34, 
						37, 38, 39, 40, 41, 42, 43};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 17, 
						20, 21, 22, 23, 24, 25, 26, 
						29, 30, 31, 32, 33, 34, 35, 
						38, 39, 40, 41, 42, 43, 44};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 24, 
						27, 28, 29, 30, 31, 32, 33, 
						36, 37, 38, 39, 40, 41, 42, 
						45, 46, 47, 48, 49, 50, 51};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 25, 
						28, 29, 30, 31, 32, 33, 34, 
						37, 38, 39, 40, 41, 42, 43, 
						46, 47, 48, 49, 50, 51, 52};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 26, 
						29, 30, 31, 32, 33, 34, 35, 
						38, 39, 40, 41, 42, 43, 44, 
						47, 48, 49, 50, 51, 52, 53};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 33, 
						36, 37, 38, 39, 40, 41, 42, 
						45, 46, 47, 48, 49, 50, 51, 
						54, 55, 56, 57, 58, 59, 60};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 34, 
						37, 38, 39, 40, 41, 42, 43, 
						46, 47, 48, 49, 50, 51, 52, 
						55, 56, 57, 58, 59, 60, 61};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 35, 
						38, 39, 40, 41, 42, 43, 44, 
						47, 48, 49, 50, 51, 52, 53, 
						56, 57, 58, 59, 60, 61, 62};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 42, 
						45, 46, 47, 48, 49, 50, 51, 
						54, 55, 56, 57, 58, 59, 60, 
						63, 64, 65, 66, 67, 68, 69};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 43, 
						46, 47, 48, 49, 50, 51, 52, 
						55, 56, 57, 58, 59, 60, 61, 
						64, 65, 66, 67, 68, 69, 70};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 44, 
						47, 48, 49, 50, 51, 52, 53, 
						56, 57, 58, 59, 60, 61, 62, 
						65, 66, 67, 68, 69, 70, 71};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 50, 51, 
						54, 55, 56, 57, 58, 59, 60, 
						63, 64, 65, 66, 67, 68, 69, 
						72, 73, 74, 75, 76, 77, 78};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 51, 52, 
						55, 56, 57, 58, 59, 60, 61, 
						64, 65, 66, 67, 68, 69, 70, 
						73, 74, 75, 76, 77, 78, 79};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 52, 53, 
						56, 57, 58, 59, 60, 61, 62, 
						65, 66, 67, 68, 69, 70, 71, 
						74, 75, 76, 77, 78, 79, 80};

	private List<Integer> nr40at00;
	private HeightIsle heightIsle40at00;

	private List<Integer> nr40at01;
	private HeightIsle heightIsle40at01;

	private List<Integer> nr40at02;
	private HeightIsle heightIsle40at02;

	private List<Integer> nr40at09;
	private HeightIsle heightIsle40at09;

	private List<Integer> nr40at10;
	private HeightIsle heightIsle40at10;

	private List<Integer> nr40at11;
	private HeightIsle heightIsle40at11;

	private List<Integer> nr40at18;
	private HeightIsle heightIsle40at18;

	private List<Integer> nr40at19;
	private HeightIsle heightIsle40at19;

	private List<Integer> nr40at20;
	private HeightIsle heightIsle40at20;

	private List<Integer> nr40at27;
	private HeightIsle heightIsle40at27;

	private List<Integer> nr40at28;
	private HeightIsle heightIsle40at28;

	private List<Integer> nr40at29;
	private HeightIsle heightIsle40at29;

	private List<Integer> nr40at36;
	private HeightIsle heightIsle40at36;

	private List<Integer> nr40at37;
	private HeightIsle heightIsle40at37;

	private List<Integer> nr40at38;
	private HeightIsle heightIsle40at38;

	private List<Integer> nr40at45;
	private HeightIsle heightIsle40at45;

	private List<Integer> nr40at46;
	private HeightIsle heightIsle40at46;

	private List<Integer> nr40at47;
	private HeightIsle heightIsle40at47;

	private final static Integer[] cornerMaximaNrs = 
		{
				11914, 11994, 11994, 11994, 11994, 11994, 11194,	
				19914, 11914, 11994, 11994, 11994, 11194, 91194,
				19914, 19114, 99114, 99114, 99114, 91114, 91194,
				19114, 99114, 99114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle40() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr40at00 = Arrays.asList(at00);
		heightIsle40at00 = new HeightIsle(nr40at00, cornerMaxima);

		nr40at01 = Arrays.asList(at01);
		heightIsle40at01 = new HeightIsle(nr40at01, cornerMaxima);

		nr40at02 = Arrays.asList(at02);
		heightIsle40at02 = new HeightIsle(nr40at02, cornerMaxima);

		nr40at09 = Arrays.asList(at09);
		heightIsle40at09 = new HeightIsle(nr40at09, cornerMaxima);

		nr40at10 = Arrays.asList(at10);
		heightIsle40at10 = new HeightIsle(nr40at10, cornerMaxima);

		nr40at11 = Arrays.asList(at11);
		heightIsle40at11 = new HeightIsle(nr40at11, cornerMaxima);

		nr40at18 = Arrays.asList(at18);
		heightIsle40at18 = new HeightIsle(nr40at18, cornerMaxima);

		nr40at19 = Arrays.asList(at19);
		heightIsle40at19 = new HeightIsle(nr40at19, cornerMaxima);

		nr40at20 = Arrays.asList(at20);
		heightIsle40at20 = new HeightIsle(nr40at20, cornerMaxima);

		nr40at27 = Arrays.asList(at27);
		heightIsle40at27 = new HeightIsle(nr40at27, cornerMaxima);

		nr40at28 = Arrays.asList(at28);
		heightIsle40at28 = new HeightIsle(nr40at28, cornerMaxima);

		nr40at29 = Arrays.asList(at29);
		heightIsle40at29 = new HeightIsle(nr40at29, cornerMaxima);

		nr40at36 = Arrays.asList(at36);
		heightIsle40at36 = new HeightIsle(nr40at36, cornerMaxima);

		nr40at37 = Arrays.asList(at37);
		heightIsle40at37 = new HeightIsle(nr40at37, cornerMaxima);

		nr40at38 = Arrays.asList(at38);
		heightIsle40at38 = new HeightIsle(nr40at38, cornerMaxima);

		nr40at45 = Arrays.asList(at45);
		heightIsle40at45 = new HeightIsle(nr40at45, cornerMaxima);

		nr40at46 = Arrays.asList(at46);
		heightIsle40at46 = new HeightIsle(nr40at46, cornerMaxima);

		nr40at47 = Arrays.asList(at47);
		heightIsle40at47 = new HeightIsle(nr40at47, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle40();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 47) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr40at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr40at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr40at02; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr40at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr40at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr40at11; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr40at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr40at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr40at20; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr40at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr40at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr40at29; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr40at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr40at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr40at38; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr40at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr40at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr40at47; break;
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
			return heightIsle40at00;
		case 1:
			return heightIsle40at01;
		case 2:
			return heightIsle40at02;
		case 9:
			return heightIsle40at09;
		case 10:
			return heightIsle40at10;
		case 11:
			return heightIsle40at11;
		case 18:
			return heightIsle40at18;
		case 19:
			return heightIsle40at19;
		case 20:
			return heightIsle40at20;
		case 27:
			return heightIsle40at27;
		case 28:
			return heightIsle40at28;
		case 29:
			return heightIsle40at29;
		case 36:
			return heightIsle40at36;
		case 37:
			return heightIsle40at37;
		case 38:
			return heightIsle40at38;
		case 45:
			return heightIsle40at45;
		case 46:
			return heightIsle40at46;
		case 47:
			return heightIsle40at47;
		default:
			return null;
		}
	}
}
