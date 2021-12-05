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

public class SubIsle37 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at27[]  = {27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at28[]  = {28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at29[]  = {29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at30[]  = {30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at31[]  = {31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at32[]  = {32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr37at00;
	private HeightIsle heightIsle37at00;

	private List<Integer> nr37at01;
	private HeightIsle heightIsle37at01;

	private List<Integer> nr37at02;
	private HeightIsle heightIsle37at02;

	private List<Integer> nr37at03;
	private HeightIsle heightIsle37at03;

	private List<Integer> nr37at04;
	private HeightIsle heightIsle37at04;

	private List<Integer> nr37at05;
	private HeightIsle heightIsle37at05;

	private List<Integer> nr37at09;
	private HeightIsle heightIsle37at09;

	private List<Integer> nr37at10;
	private HeightIsle heightIsle37at10;

	private List<Integer> nr37at11;
	private HeightIsle heightIsle37at11;

	private List<Integer> nr37at12;
	private HeightIsle heightIsle37at12;

	private List<Integer> nr37at13;
	private HeightIsle heightIsle37at13;

	private List<Integer> nr37at14;
	private HeightIsle heightIsle37at14;

	private List<Integer> nr37at18;
	private HeightIsle heightIsle37at18;

	private List<Integer> nr37at19;
	private HeightIsle heightIsle37at19;

	private List<Integer> nr37at20;
	private HeightIsle heightIsle37at20;

	private List<Integer> nr37at21;
	private HeightIsle heightIsle37at21;

	private List<Integer> nr37at22;
	private HeightIsle heightIsle37at22;

	private List<Integer> nr37at23;
	private HeightIsle heightIsle37at23;

	private List<Integer> nr37at27;
	private HeightIsle heightIsle37at27;

	private List<Integer> nr37at28;
	private HeightIsle heightIsle37at28;

	private List<Integer> nr37at29;
	private HeightIsle heightIsle37at29;

	private List<Integer> nr37at30;
	private HeightIsle heightIsle37at30;

	private List<Integer> nr37at31;
	private HeightIsle heightIsle37at31;

	private List<Integer> nr37at32;
	private HeightIsle heightIsle37at32;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11194,	
			19914, 11914, 11194, 91194,
			19914, 19914, 91194, 91194,
			19914, 19914, 91194, 91194,
			19914, 19114, 91114, 91194,
			19114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle37() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr37at00 = Arrays.asList(at00);
		heightIsle37at00 = new HeightIsle(nr37at00, cornerMaxima);

		nr37at01 = Arrays.asList(at01);
		heightIsle37at01 = new HeightIsle(nr37at01, cornerMaxima);

		nr37at02 = Arrays.asList(at02);
		heightIsle37at02 = new HeightIsle(nr37at02, cornerMaxima);

		nr37at03 = Arrays.asList(at03);
		heightIsle37at03 = new HeightIsle(nr37at03, cornerMaxima);

		nr37at04 = Arrays.asList(at04);
		heightIsle37at04 = new HeightIsle(nr37at04, cornerMaxima);

		nr37at05 = Arrays.asList(at05);
		heightIsle37at05 = new HeightIsle(nr37at05, cornerMaxima);

		nr37at09 = Arrays.asList(at09);
		heightIsle37at09 = new HeightIsle(nr37at09, cornerMaxima);

		nr37at10 = Arrays.asList(at10);
		heightIsle37at10 = new HeightIsle(nr37at10, cornerMaxima);

		nr37at11 = Arrays.asList(at11);
		heightIsle37at11 = new HeightIsle(nr37at11, cornerMaxima);

		nr37at12 = Arrays.asList(at12);
		heightIsle37at12 = new HeightIsle(nr37at12, cornerMaxima);

		nr37at13 = Arrays.asList(at13);
		heightIsle37at13 = new HeightIsle(nr37at13, cornerMaxima);

		nr37at14 = Arrays.asList(at14);
		heightIsle37at14 = new HeightIsle(nr37at14, cornerMaxima);

		nr37at18 = Arrays.asList(at18);
		heightIsle37at18 = new HeightIsle(nr37at18, cornerMaxima);

		nr37at19 = Arrays.asList(at19);
		heightIsle37at19 = new HeightIsle(nr37at19, cornerMaxima);

		nr37at20 = Arrays.asList(at20);
		heightIsle37at20 = new HeightIsle(nr37at20, cornerMaxima);

		nr37at21 = Arrays.asList(at21);
		heightIsle37at21 = new HeightIsle(nr37at21, cornerMaxima);

		nr37at22 = Arrays.asList(at22);
		heightIsle37at22 = new HeightIsle(nr37at22, cornerMaxima);

		nr37at23 = Arrays.asList(at23);
		heightIsle37at23 = new HeightIsle(nr37at23, cornerMaxima);

		nr37at27 = Arrays.asList(at27);
		heightIsle37at27 = new HeightIsle(nr37at27, cornerMaxima);

		nr37at28 = Arrays.asList(at28);
		heightIsle37at28 = new HeightIsle(nr37at28, cornerMaxima);

		nr37at29 = Arrays.asList(at29);
		heightIsle37at29 = new HeightIsle(nr37at29, cornerMaxima);

		nr37at30 = Arrays.asList(at30);
		heightIsle37at30 = new HeightIsle(nr37at30, cornerMaxima);

		nr37at31 = Arrays.asList(at31);
		heightIsle37at31 = new HeightIsle(nr37at31, cornerMaxima);

		nr37at32 = Arrays.asList(at32);
		heightIsle37at32 = new HeightIsle(nr37at32, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle37();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 32) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr37at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr37at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr37at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr37at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr37at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr37at05; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr37at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr37at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr37at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr37at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr37at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr37at14; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr37at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr37at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr37at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr37at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr37at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr37at23; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr37at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr37at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr37at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr37at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr37at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr37at32; break;
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
			return heightIsle37at00;
		case 1:
			return heightIsle37at01;
		case 2:
			return heightIsle37at02;
		case 3:
			return heightIsle37at03;
		case 4:
			return heightIsle37at04;
		case 5:
			return heightIsle37at05;
		case 9:
			return heightIsle37at09;
		case 10:
			return heightIsle37at10;
		case 11:
			return heightIsle37at11;
		case 12:
			return heightIsle37at12;
		case 13:
			return heightIsle37at13;
		case 14:
			return heightIsle37at14;
		case 18:
			return heightIsle37at18;
		case 19:
			return heightIsle37at19;
		case 20:
			return heightIsle37at20;
		case 21:
			return heightIsle37at21;
		case 22:
			return heightIsle37at22;
		case 23:
			return heightIsle37at23;
		case 27:
			return heightIsle37at27;
		case 28:
			return heightIsle37at28;
		case 29:
			return heightIsle37at29;
		case 30:
			return heightIsle37at30;
		case 31:
			return heightIsle37at31;
		case 32:
			return heightIsle37at32;
		default:
			return null;
		}
	}
}
