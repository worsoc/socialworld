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

public class SubIsle42 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  
						9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  
						10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  
						11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  
						12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52};

	private final static Integer at04[]  = {4,  5,  6,  7,  8,  
						13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61};

	private final static Integer at13[]  = {13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70};

	private final static Integer at22[]  = {22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67, 
						72, 73, 74, 75, 76};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68, 
						73, 74, 75, 76, 77};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69, 
						74, 75, 76, 77, 78};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70, 
						75, 76, 77, 78, 79};

	private final static Integer at31[]  = {31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71, 
						76, 77, 78, 79, 80};

	private List<Integer> nr42at00;
	private HeightIsle heightIsle42at00;

	private List<Integer> nr42at01;
	private HeightIsle heightIsle42at01;

	private List<Integer> nr42at02;
	private HeightIsle heightIsle42at02;

	private List<Integer> nr42at03;
	private HeightIsle heightIsle42at03;

	private List<Integer> nr42at04;
	private HeightIsle heightIsle42at04;

	private List<Integer> nr42at09;
	private HeightIsle heightIsle42at09;

	private List<Integer> nr42at10;
	private HeightIsle heightIsle42at10;

	private List<Integer> nr42at11;
	private HeightIsle heightIsle42at11;

	private List<Integer> nr42at12;
	private HeightIsle heightIsle42at12;

	private List<Integer> nr42at13;
	private HeightIsle heightIsle42at13;

	private List<Integer> nr42at18;
	private HeightIsle heightIsle42at18;

	private List<Integer> nr42at19;
	private HeightIsle heightIsle42at19;

	private List<Integer> nr42at20;
	private HeightIsle heightIsle42at20;

	private List<Integer> nr42at21;
	private HeightIsle heightIsle42at21;

	private List<Integer> nr42at22;
	private HeightIsle heightIsle42at22;

	private List<Integer> nr42at27;
	private HeightIsle heightIsle42at27;

	private List<Integer> nr42at28;
	private HeightIsle heightIsle42at28;

	private List<Integer> nr42at29;
	private HeightIsle heightIsle42at29;

	private List<Integer> nr42at30;
	private HeightIsle heightIsle42at30;

	private List<Integer> nr42at31;
	private HeightIsle heightIsle42at31;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle42() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr42at00 = Arrays.asList(at00);
		heightIsle42at00 = new HeightIsle(nr42at00, cornerMaxima);

		nr42at01 = Arrays.asList(at01);
		heightIsle42at01 = new HeightIsle(nr42at01, cornerMaxima);

		nr42at02 = Arrays.asList(at02);
		heightIsle42at02 = new HeightIsle(nr42at02, cornerMaxima);

		nr42at03 = Arrays.asList(at03);
		heightIsle42at03 = new HeightIsle(nr42at03, cornerMaxima);

		nr42at04 = Arrays.asList(at04);
		heightIsle42at04 = new HeightIsle(nr42at04, cornerMaxima);

		nr42at09 = Arrays.asList(at09);
		heightIsle42at09 = new HeightIsle(nr42at09, cornerMaxima);

		nr42at10 = Arrays.asList(at10);
		heightIsle42at10 = new HeightIsle(nr42at10, cornerMaxima);

		nr42at11 = Arrays.asList(at11);
		heightIsle42at11 = new HeightIsle(nr42at11, cornerMaxima);

		nr42at12 = Arrays.asList(at12);
		heightIsle42at12 = new HeightIsle(nr42at12, cornerMaxima);

		nr42at13 = Arrays.asList(at13);
		heightIsle42at13 = new HeightIsle(nr42at13, cornerMaxima);

		nr42at18 = Arrays.asList(at18);
		heightIsle42at18 = new HeightIsle(nr42at18, cornerMaxima);

		nr42at19 = Arrays.asList(at19);
		heightIsle42at19 = new HeightIsle(nr42at19, cornerMaxima);

		nr42at20 = Arrays.asList(at20);
		heightIsle42at20 = new HeightIsle(nr42at20, cornerMaxima);

		nr42at21 = Arrays.asList(at21);
		heightIsle42at21 = new HeightIsle(nr42at21, cornerMaxima);

		nr42at22 = Arrays.asList(at22);
		heightIsle42at22 = new HeightIsle(nr42at22, cornerMaxima);

		nr42at27 = Arrays.asList(at27);
		heightIsle42at27 = new HeightIsle(nr42at27, cornerMaxima);

		nr42at28 = Arrays.asList(at28);
		heightIsle42at28 = new HeightIsle(nr42at28, cornerMaxima);

		nr42at29 = Arrays.asList(at29);
		heightIsle42at29 = new HeightIsle(nr42at29, cornerMaxima);

		nr42at30 = Arrays.asList(at30);
		heightIsle42at30 = new HeightIsle(nr42at30, cornerMaxima);

		nr42at31 = Arrays.asList(at31);
		heightIsle42at31 = new HeightIsle(nr42at31, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle42();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 31) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr42at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr42at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr42at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr42at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr42at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr42at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr42at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr42at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr42at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr42at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr42at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr42at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr42at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr42at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr42at22; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr42at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr42at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr42at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr42at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr42at31; break;
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
			return heightIsle42at00;
		case 1:
			return heightIsle42at01;
		case 2:
			return heightIsle42at02;
		case 3:
			return heightIsle42at03;
		case 4:
			return heightIsle42at04;
		case 9:
			return heightIsle42at09;
		case 10:
			return heightIsle42at10;
		case 11:
			return heightIsle42at11;
		case 12:
			return heightIsle42at12;
		case 13:
			return heightIsle42at13;
		case 18:
			return heightIsle42at18;
		case 19:
			return heightIsle42at19;
		case 20:
			return heightIsle42at20;
		case 21:
			return heightIsle42at21;
		case 22:
			return heightIsle42at22;
		case 27:
			return heightIsle42at27;
		case 28:
			return heightIsle42at28;
		case 29:
			return heightIsle42at29;
		case 30:
			return heightIsle42at30;
		case 31:
			return heightIsle42at31;
		default:
			return null;
		}
	}
}
