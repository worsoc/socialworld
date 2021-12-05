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

public class SubIsle15 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  
						9,  10, 
						18, 19};

	private final static Integer at01[]  = {1,  2,  
						10, 11, 
						19, 20};

	private final static Integer at02[]  = {2,  3,  
						11, 12, 
						20, 21};

	private final static Integer at03[]  = {3,  4,  
						12, 13, 
						21, 22};

	private final static Integer at04[]  = {4,  5,  
						13, 14, 
						22, 23};

	private final static Integer at05[]  = {5,  6,  
						14, 15, 
						23, 24};

	private final static Integer at06[]  = {6,  7,  
						15, 16, 
						24, 25};

	private final static Integer at07[]  = {7,  8,  
						16, 17, 
						25, 26};

	private final static Integer at09[]  = {9,  10, 
						18, 19, 
						27, 28};

	private final static Integer at10[]  = {10, 11, 
						19, 20, 
						28, 29};

	private final static Integer at11[]  = {11, 12, 
						20, 21, 
						29, 30};

	private final static Integer at12[]  = {12, 13, 
						21, 22, 
						30, 31};

	private final static Integer at13[]  = {13, 14, 
						22, 23, 
						31, 32};

	private final static Integer at14[]  = {14, 15, 
						23, 24, 
						32, 33};

	private final static Integer at15[]  = {15, 16, 
						24, 25, 
						33, 34};

	private final static Integer at16[]  = {16, 17, 
						25, 26, 
						34, 35};

	private final static Integer at18[]  = {18, 19, 
						27, 28, 
						36, 37};

	private final static Integer at19[]  = {19, 20, 
						28, 29, 
						37, 38};

	private final static Integer at20[]  = {20, 21, 
						29, 30, 
						38, 39};

	private final static Integer at21[]  = {21, 22, 
						30, 31, 
						39, 40};

	private final static Integer at22[]  = {22, 23, 
						31, 32, 
						40, 41};

	private final static Integer at23[]  = {23, 24, 
						32, 33, 
						41, 42};

	private final static Integer at24[]  = {24, 25, 
						33, 34, 
						42, 43};

	private final static Integer at25[]  = {25, 26, 
						34, 35, 
						43, 44};

	private final static Integer at27[]  = {27, 28, 
						36, 37, 
						45, 46};

	private final static Integer at28[]  = {28, 29, 
						37, 38, 
						46, 47};

	private final static Integer at29[]  = {29, 30, 
						38, 39, 
						47, 48};

	private final static Integer at30[]  = {30, 31, 
						39, 40, 
						48, 49};

	private final static Integer at31[]  = {31, 32, 
						40, 41, 
						49, 50};

	private final static Integer at32[]  = {32, 33, 
						41, 42, 
						50, 51};

	private final static Integer at33[]  = {33, 34, 
						42, 43, 
						51, 52};

	private final static Integer at34[]  = {34, 35, 
						43, 44, 
						52, 53};

	private final static Integer at36[]  = {36, 37, 
						45, 46, 
						54, 55};

	private final static Integer at37[]  = {37, 38, 
						46, 47, 
						55, 56};

	private final static Integer at38[]  = {38, 39, 
						47, 48, 
						56, 57};

	private final static Integer at39[]  = {39, 40, 
						48, 49, 
						57, 58};

	private final static Integer at40[]  = {40, 41, 
						49, 50, 
						58, 59};

	private final static Integer at41[]  = {41, 42, 
						50, 51, 
						59, 60};

	private final static Integer at42[]  = {42, 43, 
						51, 52, 
						60, 61};

	private final static Integer at43[]  = {43, 44, 
						52, 53, 
						61, 62};

	private final static Integer at45[]  = {45, 46, 
						54, 55, 
						63, 64};

	private final static Integer at46[]  = {46, 47, 
						55, 56, 
						64, 65};

	private final static Integer at47[]  = {47, 48, 
						56, 57, 
						65, 66};

	private final static Integer at48[]  = {48, 49, 
						57, 58, 
						66, 67};

	private final static Integer at49[]  = {49, 50, 
						58, 59, 
						67, 68};

	private final static Integer at50[]  = {50, 51, 
						59, 60, 
						68, 69};

	private final static Integer at51[]  = {51, 52, 
						60, 61, 
						69, 70};

	private final static Integer at52[]  = {52, 53, 
						61, 62, 
						70, 71};

	private final static Integer at54[]  = {54, 55, 
						63, 64, 
						72, 73};

	private final static Integer at55[]  = {55, 56, 
						64, 65, 
						73, 74};

	private final static Integer at56[]  = {56, 57, 
						65, 66, 
						74, 75};

	private final static Integer at57[]  = {57, 58, 
						66, 67, 
						75, 76};

	private final static Integer at58[]  = {58, 59, 
						67, 68, 
						76, 77};

	private final static Integer at59[]  = {59, 60, 
						68, 69, 
						77, 78};

	private final static Integer at60[]  = {60, 61, 
						69, 70, 
						78, 79};

	private final static Integer at61[]  = {61, 62, 
						70, 71, 
						79, 80};

	private List<Integer> nr15at00;
	private HeightIsle heightIsle15at00;

	private List<Integer> nr15at01;
	private HeightIsle heightIsle15at01;

	private List<Integer> nr15at02;
	private HeightIsle heightIsle15at02;

	private List<Integer> nr15at03;
	private HeightIsle heightIsle15at03;

	private List<Integer> nr15at04;
	private HeightIsle heightIsle15at04;

	private List<Integer> nr15at05;
	private HeightIsle heightIsle15at05;

	private List<Integer> nr15at06;
	private HeightIsle heightIsle15at06;

	private List<Integer> nr15at07;
	private HeightIsle heightIsle15at07;

	private List<Integer> nr15at09;
	private HeightIsle heightIsle15at09;

	private List<Integer> nr15at10;
	private HeightIsle heightIsle15at10;

	private List<Integer> nr15at11;
	private HeightIsle heightIsle15at11;

	private List<Integer> nr15at12;
	private HeightIsle heightIsle15at12;

	private List<Integer> nr15at13;
	private HeightIsle heightIsle15at13;

	private List<Integer> nr15at14;
	private HeightIsle heightIsle15at14;

	private List<Integer> nr15at15;
	private HeightIsle heightIsle15at15;

	private List<Integer> nr15at16;
	private HeightIsle heightIsle15at16;

	private List<Integer> nr15at18;
	private HeightIsle heightIsle15at18;

	private List<Integer> nr15at19;
	private HeightIsle heightIsle15at19;

	private List<Integer> nr15at20;
	private HeightIsle heightIsle15at20;

	private List<Integer> nr15at21;
	private HeightIsle heightIsle15at21;

	private List<Integer> nr15at22;
	private HeightIsle heightIsle15at22;

	private List<Integer> nr15at23;
	private HeightIsle heightIsle15at23;

	private List<Integer> nr15at24;
	private HeightIsle heightIsle15at24;

	private List<Integer> nr15at25;
	private HeightIsle heightIsle15at25;

	private List<Integer> nr15at27;
	private HeightIsle heightIsle15at27;

	private List<Integer> nr15at28;
	private HeightIsle heightIsle15at28;

	private List<Integer> nr15at29;
	private HeightIsle heightIsle15at29;

	private List<Integer> nr15at30;
	private HeightIsle heightIsle15at30;

	private List<Integer> nr15at31;
	private HeightIsle heightIsle15at31;

	private List<Integer> nr15at32;
	private HeightIsle heightIsle15at32;

	private List<Integer> nr15at33;
	private HeightIsle heightIsle15at33;

	private List<Integer> nr15at34;
	private HeightIsle heightIsle15at34;

	private List<Integer> nr15at36;
	private HeightIsle heightIsle15at36;

	private List<Integer> nr15at37;
	private HeightIsle heightIsle15at37;

	private List<Integer> nr15at38;
	private HeightIsle heightIsle15at38;

	private List<Integer> nr15at39;
	private HeightIsle heightIsle15at39;

	private List<Integer> nr15at40;
	private HeightIsle heightIsle15at40;

	private List<Integer> nr15at41;
	private HeightIsle heightIsle15at41;

	private List<Integer> nr15at42;
	private HeightIsle heightIsle15at42;

	private List<Integer> nr15at43;
	private HeightIsle heightIsle15at43;

	private List<Integer> nr15at45;
	private HeightIsle heightIsle15at45;

	private List<Integer> nr15at46;
	private HeightIsle heightIsle15at46;

	private List<Integer> nr15at47;
	private HeightIsle heightIsle15at47;

	private List<Integer> nr15at48;
	private HeightIsle heightIsle15at48;

	private List<Integer> nr15at49;
	private HeightIsle heightIsle15at49;

	private List<Integer> nr15at50;
	private HeightIsle heightIsle15at50;

	private List<Integer> nr15at51;
	private HeightIsle heightIsle15at51;

	private List<Integer> nr15at52;
	private HeightIsle heightIsle15at52;

	private List<Integer> nr15at54;
	private HeightIsle heightIsle15at54;

	private List<Integer> nr15at55;
	private HeightIsle heightIsle15at55;

	private List<Integer> nr15at56;
	private HeightIsle heightIsle15at56;

	private List<Integer> nr15at57;
	private HeightIsle heightIsle15at57;

	private List<Integer> nr15at58;
	private HeightIsle heightIsle15at58;

	private List<Integer> nr15at59;
	private HeightIsle heightIsle15at59;

	private List<Integer> nr15at60;
	private HeightIsle heightIsle15at60;

	private List<Integer> nr15at61;
	private HeightIsle heightIsle15at61;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11194,	
			19914, 91194,
			19114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle15() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr15at00 = Arrays.asList(at00);
		heightIsle15at00 = new HeightIsle(nr15at00, cornerMaxima);

		nr15at01 = Arrays.asList(at01);
		heightIsle15at01 = new HeightIsle(nr15at01, cornerMaxima);

		nr15at02 = Arrays.asList(at02);
		heightIsle15at02 = new HeightIsle(nr15at02, cornerMaxima);

		nr15at03 = Arrays.asList(at03);
		heightIsle15at03 = new HeightIsle(nr15at03, cornerMaxima);

		nr15at04 = Arrays.asList(at04);
		heightIsle15at04 = new HeightIsle(nr15at04, cornerMaxima);

		nr15at05 = Arrays.asList(at05);
		heightIsle15at05 = new HeightIsle(nr15at05, cornerMaxima);

		nr15at06 = Arrays.asList(at06);
		heightIsle15at06 = new HeightIsle(nr15at06, cornerMaxima);

		nr15at07 = Arrays.asList(at07);
		heightIsle15at07 = new HeightIsle(nr15at07, cornerMaxima);

		nr15at09 = Arrays.asList(at09);
		heightIsle15at09 = new HeightIsle(nr15at09, cornerMaxima);

		nr15at10 = Arrays.asList(at10);
		heightIsle15at10 = new HeightIsle(nr15at10, cornerMaxima);

		nr15at11 = Arrays.asList(at11);
		heightIsle15at11 = new HeightIsle(nr15at11, cornerMaxima);

		nr15at12 = Arrays.asList(at12);
		heightIsle15at12 = new HeightIsle(nr15at12, cornerMaxima);

		nr15at13 = Arrays.asList(at13);
		heightIsle15at13 = new HeightIsle(nr15at13, cornerMaxima);

		nr15at14 = Arrays.asList(at14);
		heightIsle15at14 = new HeightIsle(nr15at14, cornerMaxima);

		nr15at15 = Arrays.asList(at15);
		heightIsle15at15 = new HeightIsle(nr15at15, cornerMaxima);

		nr15at16 = Arrays.asList(at16);
		heightIsle15at16 = new HeightIsle(nr15at16, cornerMaxima);

		nr15at18 = Arrays.asList(at18);
		heightIsle15at18 = new HeightIsle(nr15at18, cornerMaxima);

		nr15at19 = Arrays.asList(at19);
		heightIsle15at19 = new HeightIsle(nr15at19, cornerMaxima);

		nr15at20 = Arrays.asList(at20);
		heightIsle15at20 = new HeightIsle(nr15at20, cornerMaxima);

		nr15at21 = Arrays.asList(at21);
		heightIsle15at21 = new HeightIsle(nr15at21, cornerMaxima);

		nr15at22 = Arrays.asList(at22);
		heightIsle15at22 = new HeightIsle(nr15at22, cornerMaxima);

		nr15at23 = Arrays.asList(at23);
		heightIsle15at23 = new HeightIsle(nr15at23, cornerMaxima);

		nr15at24 = Arrays.asList(at24);
		heightIsle15at24 = new HeightIsle(nr15at24, cornerMaxima);

		nr15at25 = Arrays.asList(at25);
		heightIsle15at25 = new HeightIsle(nr15at25, cornerMaxima);

		nr15at27 = Arrays.asList(at27);
		heightIsle15at27 = new HeightIsle(nr15at27, cornerMaxima);

		nr15at28 = Arrays.asList(at28);
		heightIsle15at28 = new HeightIsle(nr15at28, cornerMaxima);

		nr15at29 = Arrays.asList(at29);
		heightIsle15at29 = new HeightIsle(nr15at29, cornerMaxima);

		nr15at30 = Arrays.asList(at30);
		heightIsle15at30 = new HeightIsle(nr15at30, cornerMaxima);

		nr15at31 = Arrays.asList(at31);
		heightIsle15at31 = new HeightIsle(nr15at31, cornerMaxima);

		nr15at32 = Arrays.asList(at32);
		heightIsle15at32 = new HeightIsle(nr15at32, cornerMaxima);

		nr15at33 = Arrays.asList(at33);
		heightIsle15at33 = new HeightIsle(nr15at33, cornerMaxima);

		nr15at34 = Arrays.asList(at34);
		heightIsle15at34 = new HeightIsle(nr15at34, cornerMaxima);

		nr15at36 = Arrays.asList(at36);
		heightIsle15at36 = new HeightIsle(nr15at36, cornerMaxima);

		nr15at37 = Arrays.asList(at37);
		heightIsle15at37 = new HeightIsle(nr15at37, cornerMaxima);

		nr15at38 = Arrays.asList(at38);
		heightIsle15at38 = new HeightIsle(nr15at38, cornerMaxima);

		nr15at39 = Arrays.asList(at39);
		heightIsle15at39 = new HeightIsle(nr15at39, cornerMaxima);

		nr15at40 = Arrays.asList(at40);
		heightIsle15at40 = new HeightIsle(nr15at40, cornerMaxima);

		nr15at41 = Arrays.asList(at41);
		heightIsle15at41 = new HeightIsle(nr15at41, cornerMaxima);

		nr15at42 = Arrays.asList(at42);
		heightIsle15at42 = new HeightIsle(nr15at42, cornerMaxima);

		nr15at43 = Arrays.asList(at43);
		heightIsle15at43 = new HeightIsle(nr15at43, cornerMaxima);

		nr15at45 = Arrays.asList(at45);
		heightIsle15at45 = new HeightIsle(nr15at45, cornerMaxima);

		nr15at46 = Arrays.asList(at46);
		heightIsle15at46 = new HeightIsle(nr15at46, cornerMaxima);

		nr15at47 = Arrays.asList(at47);
		heightIsle15at47 = new HeightIsle(nr15at47, cornerMaxima);

		nr15at48 = Arrays.asList(at48);
		heightIsle15at48 = new HeightIsle(nr15at48, cornerMaxima);

		nr15at49 = Arrays.asList(at49);
		heightIsle15at49 = new HeightIsle(nr15at49, cornerMaxima);

		nr15at50 = Arrays.asList(at50);
		heightIsle15at50 = new HeightIsle(nr15at50, cornerMaxima);

		nr15at51 = Arrays.asList(at51);
		heightIsle15at51 = new HeightIsle(nr15at51, cornerMaxima);

		nr15at52 = Arrays.asList(at52);
		heightIsle15at52 = new HeightIsle(nr15at52, cornerMaxima);

		nr15at54 = Arrays.asList(at54);
		heightIsle15at54 = new HeightIsle(nr15at54, cornerMaxima);

		nr15at55 = Arrays.asList(at55);
		heightIsle15at55 = new HeightIsle(nr15at55, cornerMaxima);

		nr15at56 = Arrays.asList(at56);
		heightIsle15at56 = new HeightIsle(nr15at56, cornerMaxima);

		nr15at57 = Arrays.asList(at57);
		heightIsle15at57 = new HeightIsle(nr15at57, cornerMaxima);

		nr15at58 = Arrays.asList(at58);
		heightIsle15at58 = new HeightIsle(nr15at58, cornerMaxima);

		nr15at59 = Arrays.asList(at59);
		heightIsle15at59 = new HeightIsle(nr15at59, cornerMaxima);

		nr15at60 = Arrays.asList(at60);
		heightIsle15at60 = new HeightIsle(nr15at60, cornerMaxima);

		nr15at61 = Arrays.asList(at61);
		heightIsle15at61 = new HeightIsle(nr15at61, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle15();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 61) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr15at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr15at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr15at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr15at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr15at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr15at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr15at06; break;
			case 7:
				rasterIndizesDescribingTheIsle = nr15at07; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr15at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr15at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr15at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr15at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr15at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr15at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr15at15; break;
			case 16:
				rasterIndizesDescribingTheIsle = nr15at16; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr15at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr15at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr15at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr15at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr15at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr15at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr15at24; break;
			case 25:
				rasterIndizesDescribingTheIsle = nr15at25; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr15at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr15at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr15at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr15at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr15at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr15at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr15at33; break;
			case 34:
				rasterIndizesDescribingTheIsle = nr15at34; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr15at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr15at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr15at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr15at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr15at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr15at41; break;
			case 42:
				rasterIndizesDescribingTheIsle = nr15at42; break;
			case 43:
				rasterIndizesDescribingTheIsle = nr15at43; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr15at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr15at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr15at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr15at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr15at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr15at50; break;
			case 51:
				rasterIndizesDescribingTheIsle = nr15at51; break;
			case 52:
				rasterIndizesDescribingTheIsle = nr15at52; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr15at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr15at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr15at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr15at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr15at58; break;
			case 59:
				rasterIndizesDescribingTheIsle = nr15at59; break;
			case 60:
				rasterIndizesDescribingTheIsle = nr15at60; break;
			case 61:
				rasterIndizesDescribingTheIsle = nr15at61; break;
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
			return heightIsle15at00;
		case 1:
			return heightIsle15at01;
		case 2:
			return heightIsle15at02;
		case 3:
			return heightIsle15at03;
		case 4:
			return heightIsle15at04;
		case 5:
			return heightIsle15at05;
		case 6:
			return heightIsle15at06;
		case 7:
			return heightIsle15at07;
		case 9:
			return heightIsle15at09;
		case 10:
			return heightIsle15at10;
		case 11:
			return heightIsle15at11;
		case 12:
			return heightIsle15at12;
		case 13:
			return heightIsle15at13;
		case 14:
			return heightIsle15at14;
		case 15:
			return heightIsle15at15;
		case 16:
			return heightIsle15at16;
		case 18:
			return heightIsle15at18;
		case 19:
			return heightIsle15at19;
		case 20:
			return heightIsle15at20;
		case 21:
			return heightIsle15at21;
		case 22:
			return heightIsle15at22;
		case 23:
			return heightIsle15at23;
		case 24:
			return heightIsle15at24;
		case 25:
			return heightIsle15at25;
		case 27:
			return heightIsle15at27;
		case 28:
			return heightIsle15at28;
		case 29:
			return heightIsle15at29;
		case 30:
			return heightIsle15at30;
		case 31:
			return heightIsle15at31;
		case 32:
			return heightIsle15at32;
		case 33:
			return heightIsle15at33;
		case 34:
			return heightIsle15at34;
		case 36:
			return heightIsle15at36;
		case 37:
			return heightIsle15at37;
		case 38:
			return heightIsle15at38;
		case 39:
			return heightIsle15at39;
		case 40:
			return heightIsle15at40;
		case 41:
			return heightIsle15at41;
		case 42:
			return heightIsle15at42;
		case 43:
			return heightIsle15at43;
		case 45:
			return heightIsle15at45;
		case 46:
			return heightIsle15at46;
		case 47:
			return heightIsle15at47;
		case 48:
			return heightIsle15at48;
		case 49:
			return heightIsle15at49;
		case 50:
			return heightIsle15at50;
		case 51:
			return heightIsle15at51;
		case 52:
			return heightIsle15at52;
		case 54:
			return heightIsle15at54;
		case 55:
			return heightIsle15at55;
		case 56:
			return heightIsle15at56;
		case 57:
			return heightIsle15at57;
		case 58:
			return heightIsle15at58;
		case 59:
			return heightIsle15at59;
		case 60:
			return heightIsle15at60;
		case 61:
			return heightIsle15at61;
		default:
			return null;
		}
	}
}
