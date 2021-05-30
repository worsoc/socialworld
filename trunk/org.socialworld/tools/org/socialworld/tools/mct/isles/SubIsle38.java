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

public class SubIsle38 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  
						9,  10, 11, 12, 13, 14, 
						18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32};

	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  
						10, 11, 12, 13, 14, 15, 
						19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33};

	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  
						11, 12, 13, 14, 15, 16, 
						20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34};

	private final static Integer at03[]  = {3,  4,  5,  6,  7,  8,  
						12, 13, 14, 15, 16, 17, 
						21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35};

	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 
						18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 
						19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 
						20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43};

	private final static Integer at12[]  = {12, 13, 14, 15, 16, 17, 
						21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 
						27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 
						28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 
						29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52};

	private final static Integer at21[]  = {21, 22, 23, 24, 25, 26, 
						30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53};

	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 
						36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59};

	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 
						37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60};

	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 
						38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61};

	private final static Integer at30[]  = {30, 31, 32, 33, 34, 35, 
						39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62};

	private final static Integer at36[]  = {36, 37, 38, 39, 40, 41, 
						45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59, 
						63, 64, 65, 66, 67, 68};

	private final static Integer at37[]  = {37, 38, 39, 40, 41, 42, 
						46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60, 
						64, 65, 66, 67, 68, 69};

	private final static Integer at38[]  = {38, 39, 40, 41, 42, 43, 
						47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61, 
						65, 66, 67, 68, 69, 70};

	private final static Integer at39[]  = {39, 40, 41, 42, 43, 44, 
						48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62, 
						66, 67, 68, 69, 70, 71};

	private final static Integer at45[]  = {45, 46, 47, 48, 49, 50, 
						54, 55, 56, 57, 58, 59, 
						63, 64, 65, 66, 67, 68, 
						72, 73, 74, 75, 76, 77};

	private final static Integer at46[]  = {46, 47, 48, 49, 50, 51, 
						55, 56, 57, 58, 59, 60, 
						64, 65, 66, 67, 68, 69, 
						73, 74, 75, 76, 77, 78};

	private final static Integer at47[]  = {47, 48, 49, 50, 51, 52, 
						56, 57, 58, 59, 60, 61, 
						65, 66, 67, 68, 69, 70, 
						74, 75, 76, 77, 78, 79};

	private final static Integer at48[]  = {48, 49, 50, 51, 52, 53, 
						57, 58, 59, 60, 61, 62, 
						66, 67, 68, 69, 70, 71, 
						75, 76, 77, 78, 79, 80};

	private List<Integer> nr38at00;
	private HeightIsle heightIsle38at00;

	private List<Integer> nr38at01;
	private HeightIsle heightIsle38at01;

	private List<Integer> nr38at02;
	private HeightIsle heightIsle38at02;

	private List<Integer> nr38at03;
	private HeightIsle heightIsle38at03;

	private List<Integer> nr38at09;
	private HeightIsle heightIsle38at09;

	private List<Integer> nr38at10;
	private HeightIsle heightIsle38at10;

	private List<Integer> nr38at11;
	private HeightIsle heightIsle38at11;

	private List<Integer> nr38at12;
	private HeightIsle heightIsle38at12;

	private List<Integer> nr38at18;
	private HeightIsle heightIsle38at18;

	private List<Integer> nr38at19;
	private HeightIsle heightIsle38at19;

	private List<Integer> nr38at20;
	private HeightIsle heightIsle38at20;

	private List<Integer> nr38at21;
	private HeightIsle heightIsle38at21;

	private List<Integer> nr38at27;
	private HeightIsle heightIsle38at27;

	private List<Integer> nr38at28;
	private HeightIsle heightIsle38at28;

	private List<Integer> nr38at29;
	private HeightIsle heightIsle38at29;

	private List<Integer> nr38at30;
	private HeightIsle heightIsle38at30;

	private List<Integer> nr38at36;
	private HeightIsle heightIsle38at36;

	private List<Integer> nr38at37;
	private HeightIsle heightIsle38at37;

	private List<Integer> nr38at38;
	private HeightIsle heightIsle38at38;

	private List<Integer> nr38at39;
	private HeightIsle heightIsle38at39;

	private List<Integer> nr38at45;
	private HeightIsle heightIsle38at45;

	private List<Integer> nr38at46;
	private HeightIsle heightIsle38at46;

	private List<Integer> nr38at47;
	private HeightIsle heightIsle38at47;

	private List<Integer> nr38at48;
	private HeightIsle heightIsle38at48;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle38() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr38at00 = Arrays.asList(at00);
		heightIsle38at00 = new HeightIsle(nr38at00, cornerMaxima);

		nr38at01 = Arrays.asList(at01);
		heightIsle38at01 = new HeightIsle(nr38at01, cornerMaxima);

		nr38at02 = Arrays.asList(at02);
		heightIsle38at02 = new HeightIsle(nr38at02, cornerMaxima);

		nr38at03 = Arrays.asList(at03);
		heightIsle38at03 = new HeightIsle(nr38at03, cornerMaxima);

		nr38at09 = Arrays.asList(at09);
		heightIsle38at09 = new HeightIsle(nr38at09, cornerMaxima);

		nr38at10 = Arrays.asList(at10);
		heightIsle38at10 = new HeightIsle(nr38at10, cornerMaxima);

		nr38at11 = Arrays.asList(at11);
		heightIsle38at11 = new HeightIsle(nr38at11, cornerMaxima);

		nr38at12 = Arrays.asList(at12);
		heightIsle38at12 = new HeightIsle(nr38at12, cornerMaxima);

		nr38at18 = Arrays.asList(at18);
		heightIsle38at18 = new HeightIsle(nr38at18, cornerMaxima);

		nr38at19 = Arrays.asList(at19);
		heightIsle38at19 = new HeightIsle(nr38at19, cornerMaxima);

		nr38at20 = Arrays.asList(at20);
		heightIsle38at20 = new HeightIsle(nr38at20, cornerMaxima);

		nr38at21 = Arrays.asList(at21);
		heightIsle38at21 = new HeightIsle(nr38at21, cornerMaxima);

		nr38at27 = Arrays.asList(at27);
		heightIsle38at27 = new HeightIsle(nr38at27, cornerMaxima);

		nr38at28 = Arrays.asList(at28);
		heightIsle38at28 = new HeightIsle(nr38at28, cornerMaxima);

		nr38at29 = Arrays.asList(at29);
		heightIsle38at29 = new HeightIsle(nr38at29, cornerMaxima);

		nr38at30 = Arrays.asList(at30);
		heightIsle38at30 = new HeightIsle(nr38at30, cornerMaxima);

		nr38at36 = Arrays.asList(at36);
		heightIsle38at36 = new HeightIsle(nr38at36, cornerMaxima);

		nr38at37 = Arrays.asList(at37);
		heightIsle38at37 = new HeightIsle(nr38at37, cornerMaxima);

		nr38at38 = Arrays.asList(at38);
		heightIsle38at38 = new HeightIsle(nr38at38, cornerMaxima);

		nr38at39 = Arrays.asList(at39);
		heightIsle38at39 = new HeightIsle(nr38at39, cornerMaxima);

		nr38at45 = Arrays.asList(at45);
		heightIsle38at45 = new HeightIsle(nr38at45, cornerMaxima);

		nr38at46 = Arrays.asList(at46);
		heightIsle38at46 = new HeightIsle(nr38at46, cornerMaxima);

		nr38at47 = Arrays.asList(at47);
		heightIsle38at47 = new HeightIsle(nr38at47, cornerMaxima);

		nr38at48 = Arrays.asList(at48);
		heightIsle38at48 = new HeightIsle(nr38at48, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle38();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 48) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr38at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr38at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr38at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr38at03; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr38at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr38at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr38at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr38at12; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr38at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr38at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr38at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr38at21; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr38at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr38at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr38at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr38at30; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr38at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr38at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr38at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr38at39; break;
		case 45:
			rasterIndizesDescribingTheIsle = nr38at45; break;
		case 46:
			rasterIndizesDescribingTheIsle = nr38at46; break;
		case 47:
			rasterIndizesDescribingTheIsle = nr38at47; break;
		case 48:
			rasterIndizesDescribingTheIsle = nr38at48; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle38at00;
		case 1:
			return heightIsle38at01;
		case 2:
			return heightIsle38at02;
		case 3:
			return heightIsle38at03;
		case 9:
			return heightIsle38at09;
		case 10:
			return heightIsle38at10;
		case 11:
			return heightIsle38at11;
		case 12:
			return heightIsle38at12;
		case 18:
			return heightIsle38at18;
		case 19:
			return heightIsle38at19;
		case 20:
			return heightIsle38at20;
		case 21:
			return heightIsle38at21;
		case 27:
			return heightIsle38at27;
		case 28:
			return heightIsle38at28;
		case 29:
			return heightIsle38at29;
		case 30:
			return heightIsle38at30;
		case 36:
			return heightIsle38at36;
		case 37:
			return heightIsle38at37;
		case 38:
			return heightIsle38at38;
		case 39:
			return heightIsle38at39;
		case 45:
			return heightIsle38at45;
		case 46:
			return heightIsle38at46;
		case 47:
			return heightIsle38at47;
		case 48:
			return heightIsle38at48;
		default:
			return null;
		}
	}
}
