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

public class SubIsle28 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  
						9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62};

	private final static Integer at27[]  = {27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65};

	private final static Integer at28[]  = {28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66};

	private final static Integer at29[]  = {29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67};

	private final static Integer at30[]  = {30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68};

	private final static Integer at31[]  = {31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69};

	private final static Integer at32[]  = {32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70};

	private final static Integer at33[]  = {33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71};

	private final static Integer at36[]  = {36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65, 
						72, 73, 74};

	private final static Integer at37[]  = {37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66, 
						73, 74, 75};

	private final static Integer at38[]  = {38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67, 
						74, 75, 76};

	private final static Integer at39[]  = {39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68, 
						75, 76, 77};

	private final static Integer at40[]  = {40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69, 
						76, 77, 78};

	private final static Integer at41[]  = {41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70, 
						77, 78, 79};

	private final static Integer at42[]  = {42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr28at00;
	private HeightIsle heightIsle28at00;

	private List<Integer> nr28at01;
	private HeightIsle heightIsle28at01;

	private List<Integer> nr28at02;
	private HeightIsle heightIsle28at02;

	private List<Integer> nr28at03;
	private HeightIsle heightIsle28at03;

	private List<Integer> nr28at04;
	private HeightIsle heightIsle28at04;

	private List<Integer> nr28at05;
	private HeightIsle heightIsle28at05;

	private List<Integer> nr28at06;
	private HeightIsle heightIsle28at06;

	private List<Integer> nr28at09;
	private HeightIsle heightIsle28at09;

	private List<Integer> nr28at10;
	private HeightIsle heightIsle28at10;

	private List<Integer> nr28at11;
	private HeightIsle heightIsle28at11;

	private List<Integer> nr28at12;
	private HeightIsle heightIsle28at12;

	private List<Integer> nr28at13;
	private HeightIsle heightIsle28at13;

	private List<Integer> nr28at14;
	private HeightIsle heightIsle28at14;

	private List<Integer> nr28at15;
	private HeightIsle heightIsle28at15;

	private List<Integer> nr28at18;
	private HeightIsle heightIsle28at18;

	private List<Integer> nr28at19;
	private HeightIsle heightIsle28at19;

	private List<Integer> nr28at20;
	private HeightIsle heightIsle28at20;

	private List<Integer> nr28at21;
	private HeightIsle heightIsle28at21;

	private List<Integer> nr28at22;
	private HeightIsle heightIsle28at22;

	private List<Integer> nr28at23;
	private HeightIsle heightIsle28at23;

	private List<Integer> nr28at24;
	private HeightIsle heightIsle28at24;

	private List<Integer> nr28at27;
	private HeightIsle heightIsle28at27;

	private List<Integer> nr28at28;
	private HeightIsle heightIsle28at28;

	private List<Integer> nr28at29;
	private HeightIsle heightIsle28at29;

	private List<Integer> nr28at30;
	private HeightIsle heightIsle28at30;

	private List<Integer> nr28at31;
	private HeightIsle heightIsle28at31;

	private List<Integer> nr28at32;
	private HeightIsle heightIsle28at32;

	private List<Integer> nr28at33;
	private HeightIsle heightIsle28at33;

	private List<Integer> nr28at36;
	private HeightIsle heightIsle28at36;

	private List<Integer> nr28at37;
	private HeightIsle heightIsle28at37;

	private List<Integer> nr28at38;
	private HeightIsle heightIsle28at38;

	private List<Integer> nr28at39;
	private HeightIsle heightIsle28at39;

	private List<Integer> nr28at40;
	private HeightIsle heightIsle28at40;

	private List<Integer> nr28at41;
	private HeightIsle heightIsle28at41;

	private List<Integer> nr28at42;
	private HeightIsle heightIsle28at42;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle28() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr28at00 = Arrays.asList(at00);
		heightIsle28at00 = new HeightIsle(nr28at00, cornerMaxima);

		nr28at01 = Arrays.asList(at01);
		heightIsle28at01 = new HeightIsle(nr28at01, cornerMaxima);

		nr28at02 = Arrays.asList(at02);
		heightIsle28at02 = new HeightIsle(nr28at02, cornerMaxima);

		nr28at03 = Arrays.asList(at03);
		heightIsle28at03 = new HeightIsle(nr28at03, cornerMaxima);

		nr28at04 = Arrays.asList(at04);
		heightIsle28at04 = new HeightIsle(nr28at04, cornerMaxima);

		nr28at05 = Arrays.asList(at05);
		heightIsle28at05 = new HeightIsle(nr28at05, cornerMaxima);

		nr28at06 = Arrays.asList(at06);
		heightIsle28at06 = new HeightIsle(nr28at06, cornerMaxima);

		nr28at09 = Arrays.asList(at09);
		heightIsle28at09 = new HeightIsle(nr28at09, cornerMaxima);

		nr28at10 = Arrays.asList(at10);
		heightIsle28at10 = new HeightIsle(nr28at10, cornerMaxima);

		nr28at11 = Arrays.asList(at11);
		heightIsle28at11 = new HeightIsle(nr28at11, cornerMaxima);

		nr28at12 = Arrays.asList(at12);
		heightIsle28at12 = new HeightIsle(nr28at12, cornerMaxima);

		nr28at13 = Arrays.asList(at13);
		heightIsle28at13 = new HeightIsle(nr28at13, cornerMaxima);

		nr28at14 = Arrays.asList(at14);
		heightIsle28at14 = new HeightIsle(nr28at14, cornerMaxima);

		nr28at15 = Arrays.asList(at15);
		heightIsle28at15 = new HeightIsle(nr28at15, cornerMaxima);

		nr28at18 = Arrays.asList(at18);
		heightIsle28at18 = new HeightIsle(nr28at18, cornerMaxima);

		nr28at19 = Arrays.asList(at19);
		heightIsle28at19 = new HeightIsle(nr28at19, cornerMaxima);

		nr28at20 = Arrays.asList(at20);
		heightIsle28at20 = new HeightIsle(nr28at20, cornerMaxima);

		nr28at21 = Arrays.asList(at21);
		heightIsle28at21 = new HeightIsle(nr28at21, cornerMaxima);

		nr28at22 = Arrays.asList(at22);
		heightIsle28at22 = new HeightIsle(nr28at22, cornerMaxima);

		nr28at23 = Arrays.asList(at23);
		heightIsle28at23 = new HeightIsle(nr28at23, cornerMaxima);

		nr28at24 = Arrays.asList(at24);
		heightIsle28at24 = new HeightIsle(nr28at24, cornerMaxima);

		nr28at27 = Arrays.asList(at27);
		heightIsle28at27 = new HeightIsle(nr28at27, cornerMaxima);

		nr28at28 = Arrays.asList(at28);
		heightIsle28at28 = new HeightIsle(nr28at28, cornerMaxima);

		nr28at29 = Arrays.asList(at29);
		heightIsle28at29 = new HeightIsle(nr28at29, cornerMaxima);

		nr28at30 = Arrays.asList(at30);
		heightIsle28at30 = new HeightIsle(nr28at30, cornerMaxima);

		nr28at31 = Arrays.asList(at31);
		heightIsle28at31 = new HeightIsle(nr28at31, cornerMaxima);

		nr28at32 = Arrays.asList(at32);
		heightIsle28at32 = new HeightIsle(nr28at32, cornerMaxima);

		nr28at33 = Arrays.asList(at33);
		heightIsle28at33 = new HeightIsle(nr28at33, cornerMaxima);

		nr28at36 = Arrays.asList(at36);
		heightIsle28at36 = new HeightIsle(nr28at36, cornerMaxima);

		nr28at37 = Arrays.asList(at37);
		heightIsle28at37 = new HeightIsle(nr28at37, cornerMaxima);

		nr28at38 = Arrays.asList(at38);
		heightIsle28at38 = new HeightIsle(nr28at38, cornerMaxima);

		nr28at39 = Arrays.asList(at39);
		heightIsle28at39 = new HeightIsle(nr28at39, cornerMaxima);

		nr28at40 = Arrays.asList(at40);
		heightIsle28at40 = new HeightIsle(nr28at40, cornerMaxima);

		nr28at41 = Arrays.asList(at41);
		heightIsle28at41 = new HeightIsle(nr28at41, cornerMaxima);

		nr28at42 = Arrays.asList(at42);
		heightIsle28at42 = new HeightIsle(nr28at42, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle28();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 42) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr28at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr28at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr28at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr28at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr28at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr28at05; break;
		case 6:
			rasterIndizesDescribingTheIsle = nr28at06; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr28at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr28at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr28at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr28at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr28at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr28at14; break;
		case 15:
			rasterIndizesDescribingTheIsle = nr28at15; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr28at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr28at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr28at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr28at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr28at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr28at23; break;
		case 24:
			rasterIndizesDescribingTheIsle = nr28at24; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr28at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr28at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr28at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr28at30; break;
		case 31:
			rasterIndizesDescribingTheIsle = nr28at31; break;
		case 32:
			rasterIndizesDescribingTheIsle = nr28at32; break;
		case 33:
			rasterIndizesDescribingTheIsle = nr28at33; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr28at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr28at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr28at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr28at39; break;
		case 40:
			rasterIndizesDescribingTheIsle = nr28at40; break;
		case 41:
			rasterIndizesDescribingTheIsle = nr28at41; break;
		case 42:
			rasterIndizesDescribingTheIsle = nr28at42; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle28at00;
		case 1:
			return heightIsle28at01;
		case 2:
			return heightIsle28at02;
		case 3:
			return heightIsle28at03;
		case 4:
			return heightIsle28at04;
		case 5:
			return heightIsle28at05;
		case 6:
			return heightIsle28at06;
		case 9:
			return heightIsle28at09;
		case 10:
			return heightIsle28at10;
		case 11:
			return heightIsle28at11;
		case 12:
			return heightIsle28at12;
		case 13:
			return heightIsle28at13;
		case 14:
			return heightIsle28at14;
		case 15:
			return heightIsle28at15;
		case 18:
			return heightIsle28at18;
		case 19:
			return heightIsle28at19;
		case 20:
			return heightIsle28at20;
		case 21:
			return heightIsle28at21;
		case 22:
			return heightIsle28at22;
		case 23:
			return heightIsle28at23;
		case 24:
			return heightIsle28at24;
		case 27:
			return heightIsle28at27;
		case 28:
			return heightIsle28at28;
		case 29:
			return heightIsle28at29;
		case 30:
			return heightIsle28at30;
		case 31:
			return heightIsle28at31;
		case 32:
			return heightIsle28at32;
		case 33:
			return heightIsle28at33;
		case 36:
			return heightIsle28at36;
		case 37:
			return heightIsle28at37;
		case 38:
			return heightIsle28at38;
		case 39:
			return heightIsle28at39;
		case 40:
			return heightIsle28at40;
		case 41:
			return heightIsle28at41;
		case 42:
			return heightIsle28at42;
		default:
			return null;
		}
	}
}
