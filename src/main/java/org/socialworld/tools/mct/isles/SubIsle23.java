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

public class SubIsle23 extends SubIsle {

	private static SubIsle23 instance;

	private final static Integer at00[]  = {0,  1,  
						9,  10, 
						18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55};

	private final static Integer at01[]  = {1,  2,  
						10, 11, 
						19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56};

	private final static Integer at02[]  = {2,  3,  
						11, 12, 
						20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57};

	private final static Integer at03[]  = {3,  4,  
						12, 13, 
						21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58};

	private final static Integer at04[]  = {4,  5,  
						13, 14, 
						22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59};

	private final static Integer at05[]  = {5,  6,  
						14, 15, 
						23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60};

	private final static Integer at06[]  = {6,  7,  
						15, 16, 
						24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61};

	private final static Integer at07[]  = {7,  8,  
						16, 17, 
						25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62};

	private final static Integer at09[]  = {9,  10, 
						18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55, 
						63, 64};

	private final static Integer at10[]  = {10, 11, 
						19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56, 
						64, 65};

	private final static Integer at11[]  = {11, 12, 
						20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57, 
						65, 66};

	private final static Integer at12[]  = {12, 13, 
						21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58, 
						66, 67};

	private final static Integer at13[]  = {13, 14, 
						22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59, 
						67, 68};

	private final static Integer at14[]  = {14, 15, 
						23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60, 
						68, 69};

	private final static Integer at15[]  = {15, 16, 
						24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61, 
						69, 70};

	private final static Integer at16[]  = {16, 17, 
						25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62, 
						70, 71};

	private final static Integer at18[]  = {18, 19, 
						27, 28, 
						36, 37, 
						45, 46, 
						54, 55, 
						63, 64, 
						72, 73};

	private final static Integer at19[]  = {19, 20, 
						28, 29, 
						37, 38, 
						46, 47, 
						55, 56, 
						64, 65, 
						73, 74};

	private final static Integer at20[]  = {20, 21, 
						29, 30, 
						38, 39, 
						47, 48, 
						56, 57, 
						65, 66, 
						74, 75};

	private final static Integer at21[]  = {21, 22, 
						30, 31, 
						39, 40, 
						48, 49, 
						57, 58, 
						66, 67, 
						75, 76};

	private final static Integer at22[]  = {22, 23, 
						31, 32, 
						40, 41, 
						49, 50, 
						58, 59, 
						67, 68, 
						76, 77};

	private final static Integer at23[]  = {23, 24, 
						32, 33, 
						41, 42, 
						50, 51, 
						59, 60, 
						68, 69, 
						77, 78};

	private final static Integer at24[]  = {24, 25, 
						33, 34, 
						42, 43, 
						51, 52, 
						60, 61, 
						69, 70, 
						78, 79};

	private final static Integer at25[]  = {25, 26, 
						34, 35, 
						43, 44, 
						52, 53, 
						61, 62, 
						70, 71, 
						79, 80};

	private List<Integer> nr23at00;
	private HeightIsle heightIsle23at00;

	private List<Integer> nr23at01;
	private HeightIsle heightIsle23at01;

	private List<Integer> nr23at02;
	private HeightIsle heightIsle23at02;

	private List<Integer> nr23at03;
	private HeightIsle heightIsle23at03;

	private List<Integer> nr23at04;
	private HeightIsle heightIsle23at04;

	private List<Integer> nr23at05;
	private HeightIsle heightIsle23at05;

	private List<Integer> nr23at06;
	private HeightIsle heightIsle23at06;

	private List<Integer> nr23at07;
	private HeightIsle heightIsle23at07;

	private List<Integer> nr23at09;
	private HeightIsle heightIsle23at09;

	private List<Integer> nr23at10;
	private HeightIsle heightIsle23at10;

	private List<Integer> nr23at11;
	private HeightIsle heightIsle23at11;

	private List<Integer> nr23at12;
	private HeightIsle heightIsle23at12;

	private List<Integer> nr23at13;
	private HeightIsle heightIsle23at13;

	private List<Integer> nr23at14;
	private HeightIsle heightIsle23at14;

	private List<Integer> nr23at15;
	private HeightIsle heightIsle23at15;

	private List<Integer> nr23at16;
	private HeightIsle heightIsle23at16;

	private List<Integer> nr23at18;
	private HeightIsle heightIsle23at18;

	private List<Integer> nr23at19;
	private HeightIsle heightIsle23at19;

	private List<Integer> nr23at20;
	private HeightIsle heightIsle23at20;

	private List<Integer> nr23at21;
	private HeightIsle heightIsle23at21;

	private List<Integer> nr23at22;
	private HeightIsle heightIsle23at22;

	private List<Integer> nr23at23;
	private HeightIsle heightIsle23at23;

	private List<Integer> nr23at24;
	private HeightIsle heightIsle23at24;

	private List<Integer> nr23at25;
	private HeightIsle heightIsle23at25;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11194,	
			19914, 91194,
			19914, 91194,
			19914, 91194,
			19914, 91194,
			19914, 91194,
			19114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle23() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr23at00 = Arrays.asList(at00);
		heightIsle23at00 = new HeightIsle(nr23at00, cornerMaxima);

		nr23at01 = Arrays.asList(at01);
		heightIsle23at01 = new HeightIsle(nr23at01, cornerMaxima);

		nr23at02 = Arrays.asList(at02);
		heightIsle23at02 = new HeightIsle(nr23at02, cornerMaxima);

		nr23at03 = Arrays.asList(at03);
		heightIsle23at03 = new HeightIsle(nr23at03, cornerMaxima);

		nr23at04 = Arrays.asList(at04);
		heightIsle23at04 = new HeightIsle(nr23at04, cornerMaxima);

		nr23at05 = Arrays.asList(at05);
		heightIsle23at05 = new HeightIsle(nr23at05, cornerMaxima);

		nr23at06 = Arrays.asList(at06);
		heightIsle23at06 = new HeightIsle(nr23at06, cornerMaxima);

		nr23at07 = Arrays.asList(at07);
		heightIsle23at07 = new HeightIsle(nr23at07, cornerMaxima);

		nr23at09 = Arrays.asList(at09);
		heightIsle23at09 = new HeightIsle(nr23at09, cornerMaxima);

		nr23at10 = Arrays.asList(at10);
		heightIsle23at10 = new HeightIsle(nr23at10, cornerMaxima);

		nr23at11 = Arrays.asList(at11);
		heightIsle23at11 = new HeightIsle(nr23at11, cornerMaxima);

		nr23at12 = Arrays.asList(at12);
		heightIsle23at12 = new HeightIsle(nr23at12, cornerMaxima);

		nr23at13 = Arrays.asList(at13);
		heightIsle23at13 = new HeightIsle(nr23at13, cornerMaxima);

		nr23at14 = Arrays.asList(at14);
		heightIsle23at14 = new HeightIsle(nr23at14, cornerMaxima);

		nr23at15 = Arrays.asList(at15);
		heightIsle23at15 = new HeightIsle(nr23at15, cornerMaxima);

		nr23at16 = Arrays.asList(at16);
		heightIsle23at16 = new HeightIsle(nr23at16, cornerMaxima);

		nr23at18 = Arrays.asList(at18);
		heightIsle23at18 = new HeightIsle(nr23at18, cornerMaxima);

		nr23at19 = Arrays.asList(at19);
		heightIsle23at19 = new HeightIsle(nr23at19, cornerMaxima);

		nr23at20 = Arrays.asList(at20);
		heightIsle23at20 = new HeightIsle(nr23at20, cornerMaxima);

		nr23at21 = Arrays.asList(at21);
		heightIsle23at21 = new HeightIsle(nr23at21, cornerMaxima);

		nr23at22 = Arrays.asList(at22);
		heightIsle23at22 = new HeightIsle(nr23at22, cornerMaxima);

		nr23at23 = Arrays.asList(at23);
		heightIsle23at23 = new HeightIsle(nr23at23, cornerMaxima);

		nr23at24 = Arrays.asList(at24);
		heightIsle23at24 = new HeightIsle(nr23at24, cornerMaxima);

		nr23at25 = Arrays.asList(at25);
		heightIsle23at25 = new HeightIsle(nr23at25, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle23();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 25) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr23at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr23at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr23at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr23at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr23at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr23at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr23at06; break;
			case 7:
				rasterIndizesDescribingTheIsle = nr23at07; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr23at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr23at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr23at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr23at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr23at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr23at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr23at15; break;
			case 16:
				rasterIndizesDescribingTheIsle = nr23at16; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr23at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr23at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr23at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr23at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr23at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr23at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr23at24; break;
			case 25:
				rasterIndizesDescribingTheIsle = nr23at25; break;
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
			return heightIsle23at00;
		case 1:
			return heightIsle23at01;
		case 2:
			return heightIsle23at02;
		case 3:
			return heightIsle23at03;
		case 4:
			return heightIsle23at04;
		case 5:
			return heightIsle23at05;
		case 6:
			return heightIsle23at06;
		case 7:
			return heightIsle23at07;
		case 9:
			return heightIsle23at09;
		case 10:
			return heightIsle23at10;
		case 11:
			return heightIsle23at11;
		case 12:
			return heightIsle23at12;
		case 13:
			return heightIsle23at13;
		case 14:
			return heightIsle23at14;
		case 15:
			return heightIsle23at15;
		case 16:
			return heightIsle23at16;
		case 18:
			return heightIsle23at18;
		case 19:
			return heightIsle23at19;
		case 20:
			return heightIsle23at20;
		case 21:
			return heightIsle23at21;
		case 22:
			return heightIsle23at22;
		case 23:
			return heightIsle23at23;
		case 24:
			return heightIsle23at24;
		case 25:
			return heightIsle23at25;
		default:
			return null;
		}
	}
}
