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

public class SubIsle35 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at27[]  = {27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at28[]  = {28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at29[]  = {29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at30[]  = {30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at31[]  = {31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at32[]  = {32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at36[]  = {36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at37[]  = {37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at38[]  = {38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at39[]  = {39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at40[]  = {40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at41[]  = {41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr35at00;
	private HeightIsle heightIsle35at00;

	private List<Integer> nr35at01;
	private HeightIsle heightIsle35at01;

	private List<Integer> nr35at02;
	private HeightIsle heightIsle35at02;

	private List<Integer> nr35at03;
	private HeightIsle heightIsle35at03;

	private List<Integer> nr35at04;
	private HeightIsle heightIsle35at04;

	private List<Integer> nr35at05;
	private HeightIsle heightIsle35at05;

	private List<Integer> nr35at09;
	private HeightIsle heightIsle35at09;

	private List<Integer> nr35at10;
	private HeightIsle heightIsle35at10;

	private List<Integer> nr35at11;
	private HeightIsle heightIsle35at11;

	private List<Integer> nr35at12;
	private HeightIsle heightIsle35at12;

	private List<Integer> nr35at13;
	private HeightIsle heightIsle35at13;

	private List<Integer> nr35at14;
	private HeightIsle heightIsle35at14;

	private List<Integer> nr35at18;
	private HeightIsle heightIsle35at18;

	private List<Integer> nr35at19;
	private HeightIsle heightIsle35at19;

	private List<Integer> nr35at20;
	private HeightIsle heightIsle35at20;

	private List<Integer> nr35at21;
	private HeightIsle heightIsle35at21;

	private List<Integer> nr35at22;
	private HeightIsle heightIsle35at22;

	private List<Integer> nr35at23;
	private HeightIsle heightIsle35at23;

	private List<Integer> nr35at27;
	private HeightIsle heightIsle35at27;

	private List<Integer> nr35at28;
	private HeightIsle heightIsle35at28;

	private List<Integer> nr35at29;
	private HeightIsle heightIsle35at29;

	private List<Integer> nr35at30;
	private HeightIsle heightIsle35at30;

	private List<Integer> nr35at31;
	private HeightIsle heightIsle35at31;

	private List<Integer> nr35at32;
	private HeightIsle heightIsle35at32;

	private List<Integer> nr35at36;
	private HeightIsle heightIsle35at36;

	private List<Integer> nr35at37;
	private HeightIsle heightIsle35at37;

	private List<Integer> nr35at38;
	private HeightIsle heightIsle35at38;

	private List<Integer> nr35at39;
	private HeightIsle heightIsle35at39;

	private List<Integer> nr35at40;
	private HeightIsle heightIsle35at40;

	private List<Integer> nr35at41;
	private HeightIsle heightIsle35at41;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle35() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr35at00 = Arrays.asList(at00);
		heightIsle35at00 = new HeightIsle(nr35at00, cornerMaxima);

		nr35at01 = Arrays.asList(at01);
		heightIsle35at01 = new HeightIsle(nr35at01, cornerMaxima);

		nr35at02 = Arrays.asList(at02);
		heightIsle35at02 = new HeightIsle(nr35at02, cornerMaxima);

		nr35at03 = Arrays.asList(at03);
		heightIsle35at03 = new HeightIsle(nr35at03, cornerMaxima);

		nr35at04 = Arrays.asList(at04);
		heightIsle35at04 = new HeightIsle(nr35at04, cornerMaxima);

		nr35at05 = Arrays.asList(at05);
		heightIsle35at05 = new HeightIsle(nr35at05, cornerMaxima);

		nr35at09 = Arrays.asList(at09);
		heightIsle35at09 = new HeightIsle(nr35at09, cornerMaxima);

		nr35at10 = Arrays.asList(at10);
		heightIsle35at10 = new HeightIsle(nr35at10, cornerMaxima);

		nr35at11 = Arrays.asList(at11);
		heightIsle35at11 = new HeightIsle(nr35at11, cornerMaxima);

		nr35at12 = Arrays.asList(at12);
		heightIsle35at12 = new HeightIsle(nr35at12, cornerMaxima);

		nr35at13 = Arrays.asList(at13);
		heightIsle35at13 = new HeightIsle(nr35at13, cornerMaxima);

		nr35at14 = Arrays.asList(at14);
		heightIsle35at14 = new HeightIsle(nr35at14, cornerMaxima);

		nr35at18 = Arrays.asList(at18);
		heightIsle35at18 = new HeightIsle(nr35at18, cornerMaxima);

		nr35at19 = Arrays.asList(at19);
		heightIsle35at19 = new HeightIsle(nr35at19, cornerMaxima);

		nr35at20 = Arrays.asList(at20);
		heightIsle35at20 = new HeightIsle(nr35at20, cornerMaxima);

		nr35at21 = Arrays.asList(at21);
		heightIsle35at21 = new HeightIsle(nr35at21, cornerMaxima);

		nr35at22 = Arrays.asList(at22);
		heightIsle35at22 = new HeightIsle(nr35at22, cornerMaxima);

		nr35at23 = Arrays.asList(at23);
		heightIsle35at23 = new HeightIsle(nr35at23, cornerMaxima);

		nr35at27 = Arrays.asList(at27);
		heightIsle35at27 = new HeightIsle(nr35at27, cornerMaxima);

		nr35at28 = Arrays.asList(at28);
		heightIsle35at28 = new HeightIsle(nr35at28, cornerMaxima);

		nr35at29 = Arrays.asList(at29);
		heightIsle35at29 = new HeightIsle(nr35at29, cornerMaxima);

		nr35at30 = Arrays.asList(at30);
		heightIsle35at30 = new HeightIsle(nr35at30, cornerMaxima);

		nr35at31 = Arrays.asList(at31);
		heightIsle35at31 = new HeightIsle(nr35at31, cornerMaxima);

		nr35at32 = Arrays.asList(at32);
		heightIsle35at32 = new HeightIsle(nr35at32, cornerMaxima);

		nr35at36 = Arrays.asList(at36);
		heightIsle35at36 = new HeightIsle(nr35at36, cornerMaxima);

		nr35at37 = Arrays.asList(at37);
		heightIsle35at37 = new HeightIsle(nr35at37, cornerMaxima);

		nr35at38 = Arrays.asList(at38);
		heightIsle35at38 = new HeightIsle(nr35at38, cornerMaxima);

		nr35at39 = Arrays.asList(at39);
		heightIsle35at39 = new HeightIsle(nr35at39, cornerMaxima);

		nr35at40 = Arrays.asList(at40);
		heightIsle35at40 = new HeightIsle(nr35at40, cornerMaxima);

		nr35at41 = Arrays.asList(at41);
		heightIsle35at41 = new HeightIsle(nr35at41, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle35();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 41) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr35at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr35at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr35at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr35at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr35at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr35at05; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr35at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr35at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr35at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr35at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr35at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr35at14; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr35at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr35at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr35at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr35at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr35at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr35at23; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr35at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr35at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr35at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr35at30; break;
		case 31:
			rasterIndizesDescribingTheIsle = nr35at31; break;
		case 32:
			rasterIndizesDescribingTheIsle = nr35at32; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr35at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr35at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr35at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr35at39; break;
		case 40:
			rasterIndizesDescribingTheIsle = nr35at40; break;
		case 41:
			rasterIndizesDescribingTheIsle = nr35at41; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle35at00;
		case 1:
			return heightIsle35at01;
		case 2:
			return heightIsle35at02;
		case 3:
			return heightIsle35at03;
		case 4:
			return heightIsle35at04;
		case 5:
			return heightIsle35at05;
		case 9:
			return heightIsle35at09;
		case 10:
			return heightIsle35at10;
		case 11:
			return heightIsle35at11;
		case 12:
			return heightIsle35at12;
		case 13:
			return heightIsle35at13;
		case 14:
			return heightIsle35at14;
		case 18:
			return heightIsle35at18;
		case 19:
			return heightIsle35at19;
		case 20:
			return heightIsle35at20;
		case 21:
			return heightIsle35at21;
		case 22:
			return heightIsle35at22;
		case 23:
			return heightIsle35at23;
		case 27:
			return heightIsle35at27;
		case 28:
			return heightIsle35at28;
		case 29:
			return heightIsle35at29;
		case 30:
			return heightIsle35at30;
		case 31:
			return heightIsle35at31;
		case 32:
			return heightIsle35at32;
		case 36:
			return heightIsle35at36;
		case 37:
			return heightIsle35at37;
		case 38:
			return heightIsle35at38;
		case 39:
			return heightIsle35at39;
		case 40:
			return heightIsle35at40;
		case 41:
			return heightIsle35at41;
		default:
			return null;
		}
	}
}
