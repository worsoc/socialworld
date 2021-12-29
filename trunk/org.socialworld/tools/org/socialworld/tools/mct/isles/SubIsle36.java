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

public class SubIsle36 extends SubIsle {

	private static SubIsle36 instance;

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  
						9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  
						10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  
						11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  
						12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34};

	private final static Integer at04[]  = {4,  5,  6,  7,  8,  
						13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 
						18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 
						19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 
						20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 
						21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43};

	private final static Integer at13[]  = {13, 14, 15, 16, 17, 
						22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 
						27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 
						28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 
						29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 
						30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52};

	private final static Integer at22[]  = {22, 23, 24, 25, 26, 
						31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 
						36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 
						37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 
						38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 
						39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61};

	private final static Integer at31[]  = {31, 32, 33, 34, 35, 
						40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 
						45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 
						46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 
						47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 
						48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70};

	private final static Integer at40[]  = {40, 41, 42, 43, 44, 
						49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 
						54, 55, 56, 57, 58, 
						63, 64, 65, 66, 67, 
						72, 73, 74, 75, 76};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 
						55, 56, 57, 58, 59, 
						64, 65, 66, 67, 68, 
						73, 74, 75, 76, 77};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 
						56, 57, 58, 59, 60, 
						65, 66, 67, 68, 69, 
						74, 75, 76, 77, 78};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 
						57, 58, 59, 60, 61, 
						66, 67, 68, 69, 70, 
						75, 76, 77, 78, 79};

	private final static Integer at49[]  = {49, 50, 51, 52, 53, 
						58, 59, 60, 61, 62, 
						67, 68, 69, 70, 71, 
						76, 77, 78, 79, 80};

	private List<Integer> nr36at00;
	private HeightIsle heightIsle36at00;

	private List<Integer> nr36at01;
	private HeightIsle heightIsle36at01;

	private List<Integer> nr36at02;
	private HeightIsle heightIsle36at02;

	private List<Integer> nr36at03;
	private HeightIsle heightIsle36at03;

	private List<Integer> nr36at04;
	private HeightIsle heightIsle36at04;

	private List<Integer> nr36at09;
	private HeightIsle heightIsle36at09;

	private List<Integer> nr36at10;
	private HeightIsle heightIsle36at10;

	private List<Integer> nr36at11;
	private HeightIsle heightIsle36at11;

	private List<Integer> nr36at12;
	private HeightIsle heightIsle36at12;

	private List<Integer> nr36at13;
	private HeightIsle heightIsle36at13;

	private List<Integer> nr36at18;
	private HeightIsle heightIsle36at18;

	private List<Integer> nr36at19;
	private HeightIsle heightIsle36at19;

	private List<Integer> nr36at20;
	private HeightIsle heightIsle36at20;

	private List<Integer> nr36at21;
	private HeightIsle heightIsle36at21;

	private List<Integer> nr36at22;
	private HeightIsle heightIsle36at22;

	private List<Integer> nr36at27;
	private HeightIsle heightIsle36at27;

	private List<Integer> nr36at28;
	private HeightIsle heightIsle36at28;

	private List<Integer> nr36at29;
	private HeightIsle heightIsle36at29;

	private List<Integer> nr36at30;
	private HeightIsle heightIsle36at30;

	private List<Integer> nr36at31;
	private HeightIsle heightIsle36at31;

	private List<Integer> nr36at36;
	private HeightIsle heightIsle36at36;

	private List<Integer> nr36at37;
	private HeightIsle heightIsle36at37;

	private List<Integer> nr36at38;
	private HeightIsle heightIsle36at38;

	private List<Integer> nr36at39;
	private HeightIsle heightIsle36at39;

	private List<Integer> nr36at40;
	private HeightIsle heightIsle36at40;

	private List<Integer> nr36at45;
	private HeightIsle heightIsle36at45;

	private List<Integer> nr36at46;
	private HeightIsle heightIsle36at46;

	private List<Integer> nr36at47;
	private HeightIsle heightIsle36at47;

	private List<Integer> nr36at48;
	private HeightIsle heightIsle36at48;

	private List<Integer> nr36at49;
	private HeightIsle heightIsle36at49;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11194, 91194,
			19914, 19114, 99114, 91114, 91194,
			19114, 99114, 99114, 99114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle36() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr36at00 = Arrays.asList(at00);
		heightIsle36at00 = new HeightIsle(nr36at00, cornerMaxima);

		nr36at01 = Arrays.asList(at01);
		heightIsle36at01 = new HeightIsle(nr36at01, cornerMaxima);

		nr36at02 = Arrays.asList(at02);
		heightIsle36at02 = new HeightIsle(nr36at02, cornerMaxima);

		nr36at03 = Arrays.asList(at03);
		heightIsle36at03 = new HeightIsle(nr36at03, cornerMaxima);

		nr36at04 = Arrays.asList(at04);
		heightIsle36at04 = new HeightIsle(nr36at04, cornerMaxima);

		nr36at09 = Arrays.asList(at09);
		heightIsle36at09 = new HeightIsle(nr36at09, cornerMaxima);

		nr36at10 = Arrays.asList(at10);
		heightIsle36at10 = new HeightIsle(nr36at10, cornerMaxima);

		nr36at11 = Arrays.asList(at11);
		heightIsle36at11 = new HeightIsle(nr36at11, cornerMaxima);

		nr36at12 = Arrays.asList(at12);
		heightIsle36at12 = new HeightIsle(nr36at12, cornerMaxima);

		nr36at13 = Arrays.asList(at13);
		heightIsle36at13 = new HeightIsle(nr36at13, cornerMaxima);

		nr36at18 = Arrays.asList(at18);
		heightIsle36at18 = new HeightIsle(nr36at18, cornerMaxima);

		nr36at19 = Arrays.asList(at19);
		heightIsle36at19 = new HeightIsle(nr36at19, cornerMaxima);

		nr36at20 = Arrays.asList(at20);
		heightIsle36at20 = new HeightIsle(nr36at20, cornerMaxima);

		nr36at21 = Arrays.asList(at21);
		heightIsle36at21 = new HeightIsle(nr36at21, cornerMaxima);

		nr36at22 = Arrays.asList(at22);
		heightIsle36at22 = new HeightIsle(nr36at22, cornerMaxima);

		nr36at27 = Arrays.asList(at27);
		heightIsle36at27 = new HeightIsle(nr36at27, cornerMaxima);

		nr36at28 = Arrays.asList(at28);
		heightIsle36at28 = new HeightIsle(nr36at28, cornerMaxima);

		nr36at29 = Arrays.asList(at29);
		heightIsle36at29 = new HeightIsle(nr36at29, cornerMaxima);

		nr36at30 = Arrays.asList(at30);
		heightIsle36at30 = new HeightIsle(nr36at30, cornerMaxima);

		nr36at31 = Arrays.asList(at31);
		heightIsle36at31 = new HeightIsle(nr36at31, cornerMaxima);

		nr36at36 = Arrays.asList(at36);
		heightIsle36at36 = new HeightIsle(nr36at36, cornerMaxima);

		nr36at37 = Arrays.asList(at37);
		heightIsle36at37 = new HeightIsle(nr36at37, cornerMaxima);

		nr36at38 = Arrays.asList(at38);
		heightIsle36at38 = new HeightIsle(nr36at38, cornerMaxima);

		nr36at39 = Arrays.asList(at39);
		heightIsle36at39 = new HeightIsle(nr36at39, cornerMaxima);

		nr36at40 = Arrays.asList(at40);
		heightIsle36at40 = new HeightIsle(nr36at40, cornerMaxima);

		nr36at45 = Arrays.asList(at45);
		heightIsle36at45 = new HeightIsle(nr36at45, cornerMaxima);

		nr36at46 = Arrays.asList(at46);
		heightIsle36at46 = new HeightIsle(nr36at46, cornerMaxima);

		nr36at47 = Arrays.asList(at47);
		heightIsle36at47 = new HeightIsle(nr36at47, cornerMaxima);

		nr36at48 = Arrays.asList(at48);
		heightIsle36at48 = new HeightIsle(nr36at48, cornerMaxima);

		nr36at49 = Arrays.asList(at49);
		heightIsle36at49 = new HeightIsle(nr36at49, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle36();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 49) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr36at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr36at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr36at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr36at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr36at04; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr36at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr36at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr36at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr36at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr36at13; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr36at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr36at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr36at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr36at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr36at22; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr36at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr36at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr36at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr36at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr36at31; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr36at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr36at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr36at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr36at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr36at40; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr36at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr36at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr36at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr36at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr36at49; break;
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
			return heightIsle36at00;
		case 1:
			return heightIsle36at01;
		case 2:
			return heightIsle36at02;
		case 3:
			return heightIsle36at03;
		case 4:
			return heightIsle36at04;
		case 9:
			return heightIsle36at09;
		case 10:
			return heightIsle36at10;
		case 11:
			return heightIsle36at11;
		case 12:
			return heightIsle36at12;
		case 13:
			return heightIsle36at13;
		case 18:
			return heightIsle36at18;
		case 19:
			return heightIsle36at19;
		case 20:
			return heightIsle36at20;
		case 21:
			return heightIsle36at21;
		case 22:
			return heightIsle36at22;
		case 27:
			return heightIsle36at27;
		case 28:
			return heightIsle36at28;
		case 29:
			return heightIsle36at29;
		case 30:
			return heightIsle36at30;
		case 31:
			return heightIsle36at31;
		case 36:
			return heightIsle36at36;
		case 37:
			return heightIsle36at37;
		case 38:
			return heightIsle36at38;
		case 39:
			return heightIsle36at39;
		case 40:
			return heightIsle36at40;
		case 45:
			return heightIsle36at45;
		case 46:
			return heightIsle36at46;
		case 47:
			return heightIsle36at47;
		case 48:
			return heightIsle36at48;
		case 49:
			return heightIsle36at49;
		default:
			return null;
		}
	}
}
