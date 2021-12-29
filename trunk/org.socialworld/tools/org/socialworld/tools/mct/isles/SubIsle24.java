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

public class SubIsle24 extends SubIsle {

	private static SubIsle24 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  6,  
						9, 10, 11, 12, 13, 14, 15};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  7,  
						10, 11, 12, 13, 14, 15, 16};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  8,  
						11, 12, 13, 14, 15, 16, 17};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 15, 
						18, 19, 20, 21, 22, 23, 24};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 16, 
						19, 20, 21, 22, 23, 24, 25};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 17, 
						20, 21, 22, 23, 24, 25, 26};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 24, 
						27, 28, 29, 30, 31, 32, 33};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 25, 
						28, 29, 30, 31, 32, 33, 34};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 26, 
						29, 30, 31, 32, 33, 34, 35};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 33, 
						36, 37, 38, 39, 40, 41, 42};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 34, 
						37, 38, 39, 40, 41, 42, 43};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 35, 
						38, 39, 40, 41, 42, 43, 44};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 42, 
						45, 46, 47, 48, 49, 50, 51};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 43, 
						46, 47, 48, 49, 50, 51, 52};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 44, 
						47, 48, 49, 50, 51, 52, 53};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 50, 51, 
						54, 55, 56, 57, 58, 59, 60};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 51, 52, 
						55, 56, 57, 58, 59, 60, 61};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 52, 53, 
						56, 57, 58, 59, 60, 61, 62};

	private final static Integer at54[]  = {54, 55, 56, 57, 58, 59, 60, 
						63, 64, 65, 66, 67, 68, 69};

	private final static Integer at55[]  = {55, 56, 57, 58, 59, 60, 61, 
						64, 65, 66, 67, 68, 69, 70};

	private final static Integer at56[]  = {56, 57, 58, 59, 60, 61, 62, 
						65, 66, 67, 68, 69, 70, 71};

	private final static Integer at63[]  = {63, 64, 65, 66, 67, 68, 69, 
						72, 73, 74, 75, 76, 77, 78};

	private final static Integer at64[]  = {64, 65, 66, 67, 68, 69, 70, 
						73, 74, 75, 76, 77, 78, 79};

	private final static Integer at65[]  = {65, 66, 67, 68, 69, 70, 71, 
						74, 75, 76, 77, 78, 79, 80};

	private List<Integer> nr24at00;
	private HeightIsle heightIsle24at00;

	private List<Integer> nr24at01;
	private HeightIsle heightIsle24at01;

	private List<Integer> nr24at02;
	private HeightIsle heightIsle24at02;

	private List<Integer> nr24at09;
	private HeightIsle heightIsle24at09;

	private List<Integer> nr24at10;
	private HeightIsle heightIsle24at10;

	private List<Integer> nr24at11;
	private HeightIsle heightIsle24at11;

	private List<Integer> nr24at18;
	private HeightIsle heightIsle24at18;

	private List<Integer> nr24at19;
	private HeightIsle heightIsle24at19;

	private List<Integer> nr24at20;
	private HeightIsle heightIsle24at20;

	private List<Integer> nr24at27;
	private HeightIsle heightIsle24at27;

	private List<Integer> nr24at28;
	private HeightIsle heightIsle24at28;

	private List<Integer> nr24at29;
	private HeightIsle heightIsle24at29;

	private List<Integer> nr24at36;
	private HeightIsle heightIsle24at36;

	private List<Integer> nr24at37;
	private HeightIsle heightIsle24at37;

	private List<Integer> nr24at38;
	private HeightIsle heightIsle24at38;

	private List<Integer> nr24at45;
	private HeightIsle heightIsle24at45;

	private List<Integer> nr24at46;
	private HeightIsle heightIsle24at46;

	private List<Integer> nr24at47;
	private HeightIsle heightIsle24at47;

	private List<Integer> nr24at54;
	private HeightIsle heightIsle24at54;

	private List<Integer> nr24at55;
	private HeightIsle heightIsle24at55;

	private List<Integer> nr24at56;
	private HeightIsle heightIsle24at56;

	private List<Integer> nr24at63;
	private HeightIsle heightIsle24at63;

	private List<Integer> nr24at64;
	private HeightIsle heightIsle24at64;

	private List<Integer> nr24at65;
	private HeightIsle heightIsle24at65;

	private final static Integer[] cornerMaximaNrs = 
		{
				11914, 11994, 11994, 11994, 11994, 11994, 11194,	
				19114, 99114, 99114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle24() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr24at00 = Arrays.asList(at00);
		heightIsle24at00 = new HeightIsle(nr24at00, cornerMaxima);

		nr24at01 = Arrays.asList(at01);
		heightIsle24at01 = new HeightIsle(nr24at01, cornerMaxima);

		nr24at02 = Arrays.asList(at02);
		heightIsle24at02 = new HeightIsle(nr24at02, cornerMaxima);

		nr24at09 = Arrays.asList(at09);
		heightIsle24at09 = new HeightIsle(nr24at09, cornerMaxima);

		nr24at10 = Arrays.asList(at10);
		heightIsle24at10 = new HeightIsle(nr24at10, cornerMaxima);

		nr24at11 = Arrays.asList(at11);
		heightIsle24at11 = new HeightIsle(nr24at11, cornerMaxima);

		nr24at18 = Arrays.asList(at18);
		heightIsle24at18 = new HeightIsle(nr24at18, cornerMaxima);

		nr24at19 = Arrays.asList(at19);
		heightIsle24at19 = new HeightIsle(nr24at19, cornerMaxima);

		nr24at20 = Arrays.asList(at20);
		heightIsle24at20 = new HeightIsle(nr24at20, cornerMaxima);

		nr24at27 = Arrays.asList(at27);
		heightIsle24at27 = new HeightIsle(nr24at27, cornerMaxima);

		nr24at28 = Arrays.asList(at28);
		heightIsle24at28 = new HeightIsle(nr24at28, cornerMaxima);

		nr24at29 = Arrays.asList(at29);
		heightIsle24at29 = new HeightIsle(nr24at29, cornerMaxima);

		nr24at36 = Arrays.asList(at36);
		heightIsle24at36 = new HeightIsle(nr24at36, cornerMaxima);

		nr24at37 = Arrays.asList(at37);
		heightIsle24at37 = new HeightIsle(nr24at37, cornerMaxima);

		nr24at38 = Arrays.asList(at38);
		heightIsle24at38 = new HeightIsle(nr24at38, cornerMaxima);

		nr24at45 = Arrays.asList(at45);
		heightIsle24at45 = new HeightIsle(nr24at45, cornerMaxima);

		nr24at46 = Arrays.asList(at46);
		heightIsle24at46 = new HeightIsle(nr24at46, cornerMaxima);

		nr24at47 = Arrays.asList(at47);
		heightIsle24at47 = new HeightIsle(nr24at47, cornerMaxima);

		nr24at54 = Arrays.asList(at54);
		heightIsle24at54 = new HeightIsle(nr24at54, cornerMaxima);

		nr24at55 = Arrays.asList(at55);
		heightIsle24at55 = new HeightIsle(nr24at55, cornerMaxima);

		nr24at56 = Arrays.asList(at56);
		heightIsle24at56 = new HeightIsle(nr24at56, cornerMaxima);

		nr24at63 = Arrays.asList(at63);
		heightIsle24at63 = new HeightIsle(nr24at63, cornerMaxima);

		nr24at64 = Arrays.asList(at64);
		heightIsle24at64 = new HeightIsle(nr24at64, cornerMaxima);

		nr24at65 = Arrays.asList(at65);
		heightIsle24at65 = new HeightIsle(nr24at65, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle24();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 65) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr24at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr24at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr24at02; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr24at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr24at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr24at11; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr24at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr24at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr24at20; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr24at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr24at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr24at29; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr24at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr24at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr24at38; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr24at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr24at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr24at47; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr24at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr24at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr24at56; break;
			case 63:
				rasterIndizesDescribingTheIsle = nr24at63; break;
			case 64:
				rasterIndizesDescribingTheIsle = nr24at64; break;
			case 65:
				rasterIndizesDescribingTheIsle = nr24at65; break;
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
			return heightIsle24at00;
		case 1:
			return heightIsle24at01;
		case 2:
			return heightIsle24at02;
		case 9:
			return heightIsle24at09;
		case 10:
			return heightIsle24at10;
		case 11:
			return heightIsle24at11;
		case 18:
			return heightIsle24at18;
		case 19:
			return heightIsle24at19;
		case 20:
			return heightIsle24at20;
		case 27:
			return heightIsle24at27;
		case 28:
			return heightIsle24at28;
		case 29:
			return heightIsle24at29;
		case 36:
			return heightIsle24at36;
		case 37:
			return heightIsle24at37;
		case 38:
			return heightIsle24at38;
		case 45:
			return heightIsle24at45;
		case 46:
			return heightIsle24at46;
		case 47:
			return heightIsle24at47;
		case 54:
			return heightIsle24at54;
		case 55:
			return heightIsle24at55;
		case 56:
			return heightIsle24at56;
		case 63:
			return heightIsle24at63;
		case 64:
			return heightIsle24at64;
		case 65:
			return heightIsle24at65;
		default:
			return null;
		}
	}
}
