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

public class SubIsle39 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30, 
						36, 37, 38, 39, 
						45, 46, 47, 48, 
						54, 55, 56, 57, 
						63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31, 
						37, 38, 39, 40, 
						46, 47, 48, 49, 
						55, 56, 57, 58, 
						64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32, 
						38, 39, 40, 41, 
						47, 48, 49, 50, 
						56, 57, 58, 59, 
						65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33, 
						39, 40, 41, 42, 
						48, 49, 50, 51, 
						57, 58, 59, 60, 
						66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34, 
						40, 41, 42, 43, 
						49, 50, 51, 52, 
						58, 59, 60, 61, 
						67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35, 
						41, 42, 43, 44, 
						50, 51, 52, 53, 
						59, 60, 61, 62, 
						68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr39at00;
	private HeightIsle heightIsle39at00;

	private List<Integer> nr39at01;
	private HeightIsle heightIsle39at01;

	private List<Integer> nr39at02;
	private HeightIsle heightIsle39at02;

	private List<Integer> nr39at03;
	private HeightIsle heightIsle39at03;

	private List<Integer> nr39at04;
	private HeightIsle heightIsle39at04;

	private List<Integer> nr39at05;
	private HeightIsle heightIsle39at05;

	private List<Integer> nr39at09;
	private HeightIsle heightIsle39at09;

	private List<Integer> nr39at10;
	private HeightIsle heightIsle39at10;

	private List<Integer> nr39at11;
	private HeightIsle heightIsle39at11;

	private List<Integer> nr39at12;
	private HeightIsle heightIsle39at12;

	private List<Integer> nr39at13;
	private HeightIsle heightIsle39at13;

	private List<Integer> nr39at14;
	private HeightIsle heightIsle39at14;

	private List<Integer> nr39at18;
	private HeightIsle heightIsle39at18;

	private List<Integer> nr39at19;
	private HeightIsle heightIsle39at19;

	private List<Integer> nr39at20;
	private HeightIsle heightIsle39at20;

	private List<Integer> nr39at21;
	private HeightIsle heightIsle39at21;

	private List<Integer> nr39at22;
	private HeightIsle heightIsle39at22;

	private List<Integer> nr39at23;
	private HeightIsle heightIsle39at23;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle39() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr39at00 = Arrays.asList(at00);
		heightIsle39at00 = new HeightIsle(nr39at00, cornerMaxima);

		nr39at01 = Arrays.asList(at01);
		heightIsle39at01 = new HeightIsle(nr39at01, cornerMaxima);

		nr39at02 = Arrays.asList(at02);
		heightIsle39at02 = new HeightIsle(nr39at02, cornerMaxima);

		nr39at03 = Arrays.asList(at03);
		heightIsle39at03 = new HeightIsle(nr39at03, cornerMaxima);

		nr39at04 = Arrays.asList(at04);
		heightIsle39at04 = new HeightIsle(nr39at04, cornerMaxima);

		nr39at05 = Arrays.asList(at05);
		heightIsle39at05 = new HeightIsle(nr39at05, cornerMaxima);

		nr39at09 = Arrays.asList(at09);
		heightIsle39at09 = new HeightIsle(nr39at09, cornerMaxima);

		nr39at10 = Arrays.asList(at10);
		heightIsle39at10 = new HeightIsle(nr39at10, cornerMaxima);

		nr39at11 = Arrays.asList(at11);
		heightIsle39at11 = new HeightIsle(nr39at11, cornerMaxima);

		nr39at12 = Arrays.asList(at12);
		heightIsle39at12 = new HeightIsle(nr39at12, cornerMaxima);

		nr39at13 = Arrays.asList(at13);
		heightIsle39at13 = new HeightIsle(nr39at13, cornerMaxima);

		nr39at14 = Arrays.asList(at14);
		heightIsle39at14 = new HeightIsle(nr39at14, cornerMaxima);

		nr39at18 = Arrays.asList(at18);
		heightIsle39at18 = new HeightIsle(nr39at18, cornerMaxima);

		nr39at19 = Arrays.asList(at19);
		heightIsle39at19 = new HeightIsle(nr39at19, cornerMaxima);

		nr39at20 = Arrays.asList(at20);
		heightIsle39at20 = new HeightIsle(nr39at20, cornerMaxima);

		nr39at21 = Arrays.asList(at21);
		heightIsle39at21 = new HeightIsle(nr39at21, cornerMaxima);

		nr39at22 = Arrays.asList(at22);
		heightIsle39at22 = new HeightIsle(nr39at22, cornerMaxima);

		nr39at23 = Arrays.asList(at23);
		heightIsle39at23 = new HeightIsle(nr39at23, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle39();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 23) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr39at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr39at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr39at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr39at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr39at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr39at05; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr39at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr39at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr39at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr39at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr39at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr39at14; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr39at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr39at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr39at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr39at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr39at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr39at23; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle39at00;
		case 1:
			return heightIsle39at01;
		case 2:
			return heightIsle39at02;
		case 3:
			return heightIsle39at03;
		case 4:
			return heightIsle39at04;
		case 5:
			return heightIsle39at05;
		case 9:
			return heightIsle39at09;
		case 10:
			return heightIsle39at10;
		case 11:
			return heightIsle39at11;
		case 12:
			return heightIsle39at12;
		case 13:
			return heightIsle39at13;
		case 14:
			return heightIsle39at14;
		case 18:
			return heightIsle39at18;
		case 19:
			return heightIsle39at19;
		case 20:
			return heightIsle39at20;
		case 21:
			return heightIsle39at21;
		case 22:
			return heightIsle39at22;
		case 23:
			return heightIsle39at23;
		default:
			return null;
		}
	}
}
