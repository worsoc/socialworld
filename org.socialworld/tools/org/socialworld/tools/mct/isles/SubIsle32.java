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

public class SubIsle32 extends SubIsle {

	private static SubIsle32 instance;

	private final static Integer at00[]  = {0,  1,  2,  
						9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29, 
						36, 37, 38, 
						45, 46, 47, 
						54, 55, 56, 
						63, 64, 65, 
						72, 73, 74};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30, 
						37, 38, 39, 
						46, 47, 48, 
						55, 56, 57, 
						64, 65, 66, 
						73, 74, 75};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31, 
						38, 39, 40, 
						47, 48, 49, 
						56, 57, 58, 
						65, 66, 67, 
						74, 75, 76};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32, 
						39, 40, 41, 
						48, 49, 50, 
						57, 58, 59, 
						66, 67, 68, 
						75, 76, 77};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33, 
						40, 41, 42, 
						49, 50, 51, 
						58, 59, 60, 
						67, 68, 69, 
						76, 77, 78};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34, 
						41, 42, 43, 
						50, 51, 52, 
						59, 60, 61, 
						68, 69, 70, 
						77, 78, 79};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35, 
						42, 43, 44, 
						51, 52, 53, 
						60, 61, 62, 
						69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr32at00;
	private HeightIsle heightIsle32at00;

	private List<Integer> nr32at01;
	private HeightIsle heightIsle32at01;

	private List<Integer> nr32at02;
	private HeightIsle heightIsle32at02;

	private List<Integer> nr32at03;
	private HeightIsle heightIsle32at03;

	private List<Integer> nr32at04;
	private HeightIsle heightIsle32at04;

	private List<Integer> nr32at05;
	private HeightIsle heightIsle32at05;

	private List<Integer> nr32at06;
	private HeightIsle heightIsle32at06;

	private List<Integer> nr32at09;
	private HeightIsle heightIsle32at09;

	private List<Integer> nr32at10;
	private HeightIsle heightIsle32at10;

	private List<Integer> nr32at11;
	private HeightIsle heightIsle32at11;

	private List<Integer> nr32at12;
	private HeightIsle heightIsle32at12;

	private List<Integer> nr32at13;
	private HeightIsle heightIsle32at13;

	private List<Integer> nr32at14;
	private HeightIsle heightIsle32at14;

	private List<Integer> nr32at15;
	private HeightIsle heightIsle32at15;

	private List<Integer> nr32at18;
	private HeightIsle heightIsle32at18;

	private List<Integer> nr32at19;
	private HeightIsle heightIsle32at19;

	private List<Integer> nr32at20;
	private HeightIsle heightIsle32at20;

	private List<Integer> nr32at21;
	private HeightIsle heightIsle32at21;

	private List<Integer> nr32at22;
	private HeightIsle heightIsle32at22;

	private List<Integer> nr32at23;
	private HeightIsle heightIsle32at23;

	private List<Integer> nr32at24;
	private HeightIsle heightIsle32at24;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11994, 11194,	
			19914, 11114, 91194,
			19914, 11114, 91194,
			19914, 11114, 91194,
			19914, 11114, 91194,
			19914, 11114, 91194,
			19114, 99114, 91114
		};

	private final static Integer[] isleRingNrs = {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1
		};

	private List<Integer> cornerMaxima;

	private SubIsle32() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr32at00 = Arrays.asList(at00);
		heightIsle32at00 = new HeightIsle(nr32at00, cornerMaxima);

		nr32at01 = Arrays.asList(at01);
		heightIsle32at01 = new HeightIsle(nr32at01, cornerMaxima);

		nr32at02 = Arrays.asList(at02);
		heightIsle32at02 = new HeightIsle(nr32at02, cornerMaxima);

		nr32at03 = Arrays.asList(at03);
		heightIsle32at03 = new HeightIsle(nr32at03, cornerMaxima);

		nr32at04 = Arrays.asList(at04);
		heightIsle32at04 = new HeightIsle(nr32at04, cornerMaxima);

		nr32at05 = Arrays.asList(at05);
		heightIsle32at05 = new HeightIsle(nr32at05, cornerMaxima);

		nr32at06 = Arrays.asList(at06);
		heightIsle32at06 = new HeightIsle(nr32at06, cornerMaxima);

		nr32at09 = Arrays.asList(at09);
		heightIsle32at09 = new HeightIsle(nr32at09, cornerMaxima);

		nr32at10 = Arrays.asList(at10);
		heightIsle32at10 = new HeightIsle(nr32at10, cornerMaxima);

		nr32at11 = Arrays.asList(at11);
		heightIsle32at11 = new HeightIsle(nr32at11, cornerMaxima);

		nr32at12 = Arrays.asList(at12);
		heightIsle32at12 = new HeightIsle(nr32at12, cornerMaxima);

		nr32at13 = Arrays.asList(at13);
		heightIsle32at13 = new HeightIsle(nr32at13, cornerMaxima);

		nr32at14 = Arrays.asList(at14);
		heightIsle32at14 = new HeightIsle(nr32at14, cornerMaxima);

		nr32at15 = Arrays.asList(at15);
		heightIsle32at15 = new HeightIsle(nr32at15, cornerMaxima);

		nr32at18 = Arrays.asList(at18);
		heightIsle32at18 = new HeightIsle(nr32at18, cornerMaxima);

		nr32at19 = Arrays.asList(at19);
		heightIsle32at19 = new HeightIsle(nr32at19, cornerMaxima);

		nr32at20 = Arrays.asList(at20);
		heightIsle32at20 = new HeightIsle(nr32at20, cornerMaxima);

		nr32at21 = Arrays.asList(at21);
		heightIsle32at21 = new HeightIsle(nr32at21, cornerMaxima);

		nr32at22 = Arrays.asList(at22);
		heightIsle32at22 = new HeightIsle(nr32at22, cornerMaxima);

		nr32at23 = Arrays.asList(at23);
		heightIsle32at23 = new HeightIsle(nr32at23, cornerMaxima);

		nr32at24 = Arrays.asList(at24);
		heightIsle32at24 = new HeightIsle(nr32at24, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle32();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 24) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr32at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr32at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr32at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr32at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr32at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr32at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr32at06; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr32at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr32at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr32at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr32at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr32at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr32at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr32at15; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr32at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr32at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr32at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr32at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr32at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr32at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr32at24; break;
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
			return heightIsle32at00;
		case 1:
			return heightIsle32at01;
		case 2:
			return heightIsle32at02;
		case 3:
			return heightIsle32at03;
		case 4:
			return heightIsle32at04;
		case 5:
			return heightIsle32at05;
		case 6:
			return heightIsle32at06;
		case 9:
			return heightIsle32at09;
		case 10:
			return heightIsle32at10;
		case 11:
			return heightIsle32at11;
		case 12:
			return heightIsle32at12;
		case 13:
			return heightIsle32at13;
		case 14:
			return heightIsle32at14;
		case 15:
			return heightIsle32at15;
		case 18:
			return heightIsle32at18;
		case 19:
			return heightIsle32at19;
		case 20:
			return heightIsle32at20;
		case 21:
			return heightIsle32at21;
		case 22:
			return heightIsle32at22;
		case 23:
			return heightIsle32at23;
		case 24:
			return heightIsle32at24;
		default:
			return null;
		}
	}
}
