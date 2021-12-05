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

public class SubIsle41 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  
						9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  
						10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  
						11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  
						12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43};

	private final static Integer at04[]  = {4,  5,  6,  7,  8,  
						13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52};

	private final static Integer at13[]  = {13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61};

	private final static Integer at22[]  = {22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70};

	private final static Integer at31[]  = {31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67, 
						72, 73, 74, 75, 76};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68, 
						73, 74, 75, 76, 77};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69, 
						74, 75, 76, 77, 78};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70, 
						75, 76, 77, 78, 79};

	private final static Integer at40[]  = {40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71, 
						76, 77, 78, 79, 80};

	private List<Integer> nr41at00;
	private HeightIsle heightIsle41at00;

	private List<Integer> nr41at01;
	private HeightIsle heightIsle41at01;

	private List<Integer> nr41at02;
	private HeightIsle heightIsle41at02;

	private List<Integer> nr41at03;
	private HeightIsle heightIsle41at03;

	private List<Integer> nr41at04;
	private HeightIsle heightIsle41at04;

	private List<Integer> nr41at09;
	private HeightIsle heightIsle41at09;

	private List<Integer> nr41at10;
	private HeightIsle heightIsle41at10;

	private List<Integer> nr41at11;
	private HeightIsle heightIsle41at11;

	private List<Integer> nr41at12;
	private HeightIsle heightIsle41at12;

	private List<Integer> nr41at13;
	private HeightIsle heightIsle41at13;

	private List<Integer> nr41at18;
	private HeightIsle heightIsle41at18;

	private List<Integer> nr41at19;
	private HeightIsle heightIsle41at19;

	private List<Integer> nr41at20;
	private HeightIsle heightIsle41at20;

	private List<Integer> nr41at21;
	private HeightIsle heightIsle41at21;

	private List<Integer> nr41at22;
	private HeightIsle heightIsle41at22;

	private List<Integer> nr41at27;
	private HeightIsle heightIsle41at27;

	private List<Integer> nr41at28;
	private HeightIsle heightIsle41at28;

	private List<Integer> nr41at29;
	private HeightIsle heightIsle41at29;

	private List<Integer> nr41at30;
	private HeightIsle heightIsle41at30;

	private List<Integer> nr41at31;
	private HeightIsle heightIsle41at31;

	private List<Integer> nr41at36;
	private HeightIsle heightIsle41at36;

	private List<Integer> nr41at37;
	private HeightIsle heightIsle41at37;

	private List<Integer> nr41at38;
	private HeightIsle heightIsle41at38;

	private List<Integer> nr41at39;
	private HeightIsle heightIsle41at39;

	private List<Integer> nr41at40;
	private HeightIsle heightIsle41at40;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11194, 91194,
			19914, 19914, 11114, 91194, 91194,
			19914, 19114, 99114, 91114, 91194,
			19114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle41() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr41at00 = Arrays.asList(at00);
		heightIsle41at00 = new HeightIsle(nr41at00, cornerMaxima);

		nr41at01 = Arrays.asList(at01);
		heightIsle41at01 = new HeightIsle(nr41at01, cornerMaxima);

		nr41at02 = Arrays.asList(at02);
		heightIsle41at02 = new HeightIsle(nr41at02, cornerMaxima);

		nr41at03 = Arrays.asList(at03);
		heightIsle41at03 = new HeightIsle(nr41at03, cornerMaxima);

		nr41at04 = Arrays.asList(at04);
		heightIsle41at04 = new HeightIsle(nr41at04, cornerMaxima);

		nr41at09 = Arrays.asList(at09);
		heightIsle41at09 = new HeightIsle(nr41at09, cornerMaxima);

		nr41at10 = Arrays.asList(at10);
		heightIsle41at10 = new HeightIsle(nr41at10, cornerMaxima);

		nr41at11 = Arrays.asList(at11);
		heightIsle41at11 = new HeightIsle(nr41at11, cornerMaxima);

		nr41at12 = Arrays.asList(at12);
		heightIsle41at12 = new HeightIsle(nr41at12, cornerMaxima);

		nr41at13 = Arrays.asList(at13);
		heightIsle41at13 = new HeightIsle(nr41at13, cornerMaxima);

		nr41at18 = Arrays.asList(at18);
		heightIsle41at18 = new HeightIsle(nr41at18, cornerMaxima);

		nr41at19 = Arrays.asList(at19);
		heightIsle41at19 = new HeightIsle(nr41at19, cornerMaxima);

		nr41at20 = Arrays.asList(at20);
		heightIsle41at20 = new HeightIsle(nr41at20, cornerMaxima);

		nr41at21 = Arrays.asList(at21);
		heightIsle41at21 = new HeightIsle(nr41at21, cornerMaxima);

		nr41at22 = Arrays.asList(at22);
		heightIsle41at22 = new HeightIsle(nr41at22, cornerMaxima);

		nr41at27 = Arrays.asList(at27);
		heightIsle41at27 = new HeightIsle(nr41at27, cornerMaxima);

		nr41at28 = Arrays.asList(at28);
		heightIsle41at28 = new HeightIsle(nr41at28, cornerMaxima);

		nr41at29 = Arrays.asList(at29);
		heightIsle41at29 = new HeightIsle(nr41at29, cornerMaxima);

		nr41at30 = Arrays.asList(at30);
		heightIsle41at30 = new HeightIsle(nr41at30, cornerMaxima);

		nr41at31 = Arrays.asList(at31);
		heightIsle41at31 = new HeightIsle(nr41at31, cornerMaxima);

		nr41at36 = Arrays.asList(at36);
		heightIsle41at36 = new HeightIsle(nr41at36, cornerMaxima);

		nr41at37 = Arrays.asList(at37);
		heightIsle41at37 = new HeightIsle(nr41at37, cornerMaxima);

		nr41at38 = Arrays.asList(at38);
		heightIsle41at38 = new HeightIsle(nr41at38, cornerMaxima);

		nr41at39 = Arrays.asList(at39);
		heightIsle41at39 = new HeightIsle(nr41at39, cornerMaxima);

		nr41at40 = Arrays.asList(at40);
		heightIsle41at40 = new HeightIsle(nr41at40, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle41();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 40) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr41at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr41at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr41at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr41at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr41at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr41at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr41at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr41at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr41at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr41at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr41at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr41at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr41at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr41at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr41at22; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr41at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr41at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr41at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr41at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr41at31; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr41at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr41at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr41at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr41at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr41at40; break;
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
			return heightIsle41at00;
		case 1:
			return heightIsle41at01;
		case 2:
			return heightIsle41at02;
		case 3:
			return heightIsle41at03;
		case 4:
			return heightIsle41at04;
		case 9:
			return heightIsle41at09;
		case 10:
			return heightIsle41at10;
		case 11:
			return heightIsle41at11;
		case 12:
			return heightIsle41at12;
		case 13:
			return heightIsle41at13;
		case 18:
			return heightIsle41at18;
		case 19:
			return heightIsle41at19;
		case 20:
			return heightIsle41at20;
		case 21:
			return heightIsle41at21;
		case 22:
			return heightIsle41at22;
		case 27:
			return heightIsle41at27;
		case 28:
			return heightIsle41at28;
		case 29:
			return heightIsle41at29;
		case 30:
			return heightIsle41at30;
		case 31:
			return heightIsle41at31;
		case 36:
			return heightIsle41at36;
		case 37:
			return heightIsle41at37;
		case 38:
			return heightIsle41at38;
		case 39:
			return heightIsle41at39;
		case 40:
			return heightIsle41at40;
		default:
			return null;
		}
	}
}
