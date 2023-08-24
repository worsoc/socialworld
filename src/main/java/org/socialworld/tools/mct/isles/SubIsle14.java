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

public class SubIsle14 extends SubIsle {

	private static SubIsle14 instance;

	private final static Integer at00[]  = {0,  1,  
						9, 10};

	private final static Integer at01[]  = {1,  2,  
						10, 11};

	private final static Integer at02[]  = {2,  3,  
						11, 12};

	private final static Integer at03[]  = {3,  4,  
						12, 13};

	private final static Integer at04[]  = {4,  5,  
						13, 14};

	private final static Integer at05[]  = {5,  6,  
						14, 15};

	private final static Integer at06[]  = {6,  7,  
						15, 16};

	private final static Integer at07[]  = {7,  8,  
						16, 17};

	private final static Integer at09[]  = {9,  10, 
						18, 19};

	private final static Integer at10[]  = {10, 11, 
						19, 20};

	private final static Integer at11[]  = {11, 12, 
						20, 21};

	private final static Integer at12[]  = {12, 13, 
						21, 22};

	private final static Integer at13[]  = {13, 14, 
						22, 23};

	private final static Integer at14[]  = {14, 15, 
						23, 24};

	private final static Integer at15[]  = {15, 16, 
						24, 25};

	private final static Integer at16[]  = {16, 17, 
						25, 26};

	private final static Integer at18[]  = {18, 19, 
						27, 28};

	private final static Integer at19[]  = {19, 20, 
						28, 29};

	private final static Integer at20[]  = {20, 21, 
						29, 30};

	private final static Integer at21[]  = {21, 22, 
						30, 31};

	private final static Integer at22[]  = {22, 23, 
						31, 32};

	private final static Integer at23[]  = {23, 24, 
						32, 33};

	private final static Integer at24[]  = {24, 25, 
						33, 34};

	private final static Integer at25[]  = {25, 26, 
						34, 35};

	private final static Integer at27[]  = {27, 28, 
						36, 37};

	private final static Integer at28[]  = {28, 29, 
						37, 38};

	private final static Integer at29[]  = {29, 30, 
						38, 39};

	private final static Integer at30[]  = {30, 31, 
						39, 40};

	private final static Integer at31[]  = {31, 32, 
						40, 41};

	private final static Integer at32[]  = {32, 33, 
						41, 42};

	private final static Integer at33[]  = {33, 34, 
						42, 43};

	private final static Integer at34[]  = {34, 35, 
						43, 44};

	private final static Integer at36[]  = {36, 37, 
						45, 46};

	private final static Integer at37[]  = {37, 38, 
						46, 47};

	private final static Integer at38[]  = {38, 39, 
						47, 48};

	private final static Integer at39[]  = {39, 40, 
						48, 49};

	private final static Integer at40[]  = {40, 41, 
						49, 50};

	private final static Integer at41[]  = {41, 42, 
						50, 51};

	private final static Integer at42[]  = {42, 43, 
						51, 52};

	private final static Integer at43[]  = {43, 44, 
						52, 53};

	private final static Integer at45[]  = {45, 46, 
						54, 55};

	private final static Integer at46[]  = {46, 47, 
						55, 56};

	private final static Integer at47[]  = {47, 48, 
						56, 57};

	private final static Integer at48[]  = {48, 49, 
						57, 58};

	private final static Integer at49[]  = {49, 50, 
						58, 59};

	private final static Integer at50[]  = {50, 51, 
						59, 60};

	private final static Integer at51[]  = {51, 52, 
						60, 61};

	private final static Integer at52[]  = {52, 53, 
						61, 62};

	private final static Integer at54[]  = {54, 55, 
						63, 64};

	private final static Integer at55[]  = {55, 56, 
						64, 65};

	private final static Integer at56[]  = {56, 57, 
						65, 66};

	private final static Integer at57[]  = {57, 58, 
						66, 67};

	private final static Integer at58[]  = {58, 59, 
						67, 68};

	private final static Integer at59[]  = {59, 60, 
						68, 69};

	private final static Integer at60[]  = {60, 61, 
						69, 70};

	private final static Integer at61[]  = {61, 62, 
						70, 71};

	private final static Integer at63[]  = {63, 64, 
						72, 73};

	private final static Integer at64[]  = {64, 65, 
						73, 74};

	private final static Integer at65[]  = {65, 66, 
						74, 75};

	private final static Integer at66[]  = {66, 67, 
						75, 76};

	private final static Integer at67[]  = {67, 68, 
						76, 77};

	private final static Integer at68[]  = {68, 69, 
						77, 78};

	private final static Integer at69[]  = {69, 70, 
						78, 79};

	private final static Integer at70[]  = {70, 71, 
						79, 80};

	private List<Integer> nr14at00;
	private HeightIsle heightIsle14at00;

	private List<Integer> nr14at01;
	private HeightIsle heightIsle14at01;

	private List<Integer> nr14at02;
	private HeightIsle heightIsle14at02;

	private List<Integer> nr14at03;
	private HeightIsle heightIsle14at03;

	private List<Integer> nr14at04;
	private HeightIsle heightIsle14at04;

	private List<Integer> nr14at05;
	private HeightIsle heightIsle14at05;

	private List<Integer> nr14at06;
	private HeightIsle heightIsle14at06;

	private List<Integer> nr14at07;
	private HeightIsle heightIsle14at07;

	private List<Integer> nr14at09;
	private HeightIsle heightIsle14at09;

	private List<Integer> nr14at10;
	private HeightIsle heightIsle14at10;

	private List<Integer> nr14at11;
	private HeightIsle heightIsle14at11;

	private List<Integer> nr14at12;
	private HeightIsle heightIsle14at12;

	private List<Integer> nr14at13;
	private HeightIsle heightIsle14at13;

	private List<Integer> nr14at14;
	private HeightIsle heightIsle14at14;

	private List<Integer> nr14at15;
	private HeightIsle heightIsle14at15;

	private List<Integer> nr14at16;
	private HeightIsle heightIsle14at16;

	private List<Integer> nr14at18;
	private HeightIsle heightIsle14at18;

	private List<Integer> nr14at19;
	private HeightIsle heightIsle14at19;

	private List<Integer> nr14at20;
	private HeightIsle heightIsle14at20;

	private List<Integer> nr14at21;
	private HeightIsle heightIsle14at21;

	private List<Integer> nr14at22;
	private HeightIsle heightIsle14at22;

	private List<Integer> nr14at23;
	private HeightIsle heightIsle14at23;

	private List<Integer> nr14at24;
	private HeightIsle heightIsle14at24;

	private List<Integer> nr14at25;
	private HeightIsle heightIsle14at25;

	private List<Integer> nr14at27;
	private HeightIsle heightIsle14at27;

	private List<Integer> nr14at28;
	private HeightIsle heightIsle14at28;

	private List<Integer> nr14at29;
	private HeightIsle heightIsle14at29;

	private List<Integer> nr14at30;
	private HeightIsle heightIsle14at30;

	private List<Integer> nr14at31;
	private HeightIsle heightIsle14at31;

	private List<Integer> nr14at32;
	private HeightIsle heightIsle14at32;

	private List<Integer> nr14at33;
	private HeightIsle heightIsle14at33;

	private List<Integer> nr14at34;
	private HeightIsle heightIsle14at34;

	private List<Integer> nr14at36;
	private HeightIsle heightIsle14at36;

	private List<Integer> nr14at37;
	private HeightIsle heightIsle14at37;

	private List<Integer> nr14at38;
	private HeightIsle heightIsle14at38;

	private List<Integer> nr14at39;
	private HeightIsle heightIsle14at39;

	private List<Integer> nr14at40;
	private HeightIsle heightIsle14at40;

	private List<Integer> nr14at41;
	private HeightIsle heightIsle14at41;

	private List<Integer> nr14at42;
	private HeightIsle heightIsle14at42;

	private List<Integer> nr14at43;
	private HeightIsle heightIsle14at43;

	private List<Integer> nr14at45;
	private HeightIsle heightIsle14at45;

	private List<Integer> nr14at46;
	private HeightIsle heightIsle14at46;

	private List<Integer> nr14at47;
	private HeightIsle heightIsle14at47;

	private List<Integer> nr14at48;
	private HeightIsle heightIsle14at48;

	private List<Integer> nr14at49;
	private HeightIsle heightIsle14at49;

	private List<Integer> nr14at50;
	private HeightIsle heightIsle14at50;

	private List<Integer> nr14at51;
	private HeightIsle heightIsle14at51;

	private List<Integer> nr14at52;
	private HeightIsle heightIsle14at52;

	private List<Integer> nr14at54;
	private HeightIsle heightIsle14at54;

	private List<Integer> nr14at55;
	private HeightIsle heightIsle14at55;

	private List<Integer> nr14at56;
	private HeightIsle heightIsle14at56;

	private List<Integer> nr14at57;
	private HeightIsle heightIsle14at57;

	private List<Integer> nr14at58;
	private HeightIsle heightIsle14at58;

	private List<Integer> nr14at59;
	private HeightIsle heightIsle14at59;

	private List<Integer> nr14at60;
	private HeightIsle heightIsle14at60;

	private List<Integer> nr14at61;
	private HeightIsle heightIsle14at61;

	private List<Integer> nr14at63;
	private HeightIsle heightIsle14at63;

	private List<Integer> nr14at64;
	private HeightIsle heightIsle14at64;

	private List<Integer> nr14at65;
	private HeightIsle heightIsle14at65;

	private List<Integer> nr14at66;
	private HeightIsle heightIsle14at66;

	private List<Integer> nr14at67;
	private HeightIsle heightIsle14at67;

	private List<Integer> nr14at68;
	private HeightIsle heightIsle14at68;

	private List<Integer> nr14at69;
	private HeightIsle heightIsle14at69;

	private List<Integer> nr14at70;
	private HeightIsle heightIsle14at70;

	private final static Integer[] cornerMaximaNrs = {
			11914, 11194,	
			19114, 91114
		};

	private List<Integer> cornerMaxima;

	private SubIsle14() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr14at00 = Arrays.asList(at00);
		heightIsle14at00 = new HeightIsle(nr14at00, cornerMaxima);

		nr14at01 = Arrays.asList(at01);
		heightIsle14at01 = new HeightIsle(nr14at01, cornerMaxima);

		nr14at02 = Arrays.asList(at02);
		heightIsle14at02 = new HeightIsle(nr14at02, cornerMaxima);

		nr14at03 = Arrays.asList(at03);
		heightIsle14at03 = new HeightIsle(nr14at03, cornerMaxima);

		nr14at04 = Arrays.asList(at04);
		heightIsle14at04 = new HeightIsle(nr14at04, cornerMaxima);

		nr14at05 = Arrays.asList(at05);
		heightIsle14at05 = new HeightIsle(nr14at05, cornerMaxima);

		nr14at06 = Arrays.asList(at06);
		heightIsle14at06 = new HeightIsle(nr14at06, cornerMaxima);

		nr14at07 = Arrays.asList(at07);
		heightIsle14at07 = new HeightIsle(nr14at07, cornerMaxima);

		nr14at09 = Arrays.asList(at09);
		heightIsle14at09 = new HeightIsle(nr14at09, cornerMaxima);

		nr14at10 = Arrays.asList(at10);
		heightIsle14at10 = new HeightIsle(nr14at10, cornerMaxima);

		nr14at11 = Arrays.asList(at11);
		heightIsle14at11 = new HeightIsle(nr14at11, cornerMaxima);

		nr14at12 = Arrays.asList(at12);
		heightIsle14at12 = new HeightIsle(nr14at12, cornerMaxima);

		nr14at13 = Arrays.asList(at13);
		heightIsle14at13 = new HeightIsle(nr14at13, cornerMaxima);

		nr14at14 = Arrays.asList(at14);
		heightIsle14at14 = new HeightIsle(nr14at14, cornerMaxima);

		nr14at15 = Arrays.asList(at15);
		heightIsle14at15 = new HeightIsle(nr14at15, cornerMaxima);

		nr14at16 = Arrays.asList(at16);
		heightIsle14at16 = new HeightIsle(nr14at16, cornerMaxima);

		nr14at18 = Arrays.asList(at18);
		heightIsle14at18 = new HeightIsle(nr14at18, cornerMaxima);

		nr14at19 = Arrays.asList(at19);
		heightIsle14at19 = new HeightIsle(nr14at19, cornerMaxima);

		nr14at20 = Arrays.asList(at20);
		heightIsle14at20 = new HeightIsle(nr14at20, cornerMaxima);

		nr14at21 = Arrays.asList(at21);
		heightIsle14at21 = new HeightIsle(nr14at21, cornerMaxima);

		nr14at22 = Arrays.asList(at22);
		heightIsle14at22 = new HeightIsle(nr14at22, cornerMaxima);

		nr14at23 = Arrays.asList(at23);
		heightIsle14at23 = new HeightIsle(nr14at23, cornerMaxima);

		nr14at24 = Arrays.asList(at24);
		heightIsle14at24 = new HeightIsle(nr14at24, cornerMaxima);

		nr14at25 = Arrays.asList(at25);
		heightIsle14at25 = new HeightIsle(nr14at25, cornerMaxima);

		nr14at27 = Arrays.asList(at27);
		heightIsle14at27 = new HeightIsle(nr14at27, cornerMaxima);

		nr14at28 = Arrays.asList(at28);
		heightIsle14at28 = new HeightIsle(nr14at28, cornerMaxima);

		nr14at29 = Arrays.asList(at29);
		heightIsle14at29 = new HeightIsle(nr14at29, cornerMaxima);

		nr14at30 = Arrays.asList(at30);
		heightIsle14at30 = new HeightIsle(nr14at30, cornerMaxima);

		nr14at31 = Arrays.asList(at31);
		heightIsle14at31 = new HeightIsle(nr14at31, cornerMaxima);

		nr14at32 = Arrays.asList(at32);
		heightIsle14at32 = new HeightIsle(nr14at32, cornerMaxima);

		nr14at33 = Arrays.asList(at33);
		heightIsle14at33 = new HeightIsle(nr14at33, cornerMaxima);

		nr14at34 = Arrays.asList(at34);
		heightIsle14at34 = new HeightIsle(nr14at34, cornerMaxima);

		nr14at36 = Arrays.asList(at36);
		heightIsle14at36 = new HeightIsle(nr14at36, cornerMaxima);

		nr14at37 = Arrays.asList(at37);
		heightIsle14at37 = new HeightIsle(nr14at37, cornerMaxima);

		nr14at38 = Arrays.asList(at38);
		heightIsle14at38 = new HeightIsle(nr14at38, cornerMaxima);

		nr14at39 = Arrays.asList(at39);
		heightIsle14at39 = new HeightIsle(nr14at39, cornerMaxima);

		nr14at40 = Arrays.asList(at40);
		heightIsle14at40 = new HeightIsle(nr14at40, cornerMaxima);

		nr14at41 = Arrays.asList(at41);
		heightIsle14at41 = new HeightIsle(nr14at41, cornerMaxima);

		nr14at42 = Arrays.asList(at42);
		heightIsle14at42 = new HeightIsle(nr14at42, cornerMaxima);

		nr14at43 = Arrays.asList(at43);
		heightIsle14at43 = new HeightIsle(nr14at43, cornerMaxima);

		nr14at45 = Arrays.asList(at45);
		heightIsle14at45 = new HeightIsle(nr14at45, cornerMaxima);

		nr14at46 = Arrays.asList(at46);
		heightIsle14at46 = new HeightIsle(nr14at46, cornerMaxima);

		nr14at47 = Arrays.asList(at47);
		heightIsle14at47 = new HeightIsle(nr14at47, cornerMaxima);

		nr14at48 = Arrays.asList(at48);
		heightIsle14at48 = new HeightIsle(nr14at48, cornerMaxima);

		nr14at49 = Arrays.asList(at49);
		heightIsle14at49 = new HeightIsle(nr14at49, cornerMaxima);

		nr14at50 = Arrays.asList(at50);
		heightIsle14at50 = new HeightIsle(nr14at50, cornerMaxima);

		nr14at51 = Arrays.asList(at51);
		heightIsle14at51 = new HeightIsle(nr14at51, cornerMaxima);

		nr14at52 = Arrays.asList(at52);
		heightIsle14at52 = new HeightIsle(nr14at52, cornerMaxima);

		nr14at54 = Arrays.asList(at54);
		heightIsle14at54 = new HeightIsle(nr14at54, cornerMaxima);

		nr14at55 = Arrays.asList(at55);
		heightIsle14at55 = new HeightIsle(nr14at55, cornerMaxima);

		nr14at56 = Arrays.asList(at56);
		heightIsle14at56 = new HeightIsle(nr14at56, cornerMaxima);

		nr14at57 = Arrays.asList(at57);
		heightIsle14at57 = new HeightIsle(nr14at57, cornerMaxima);

		nr14at58 = Arrays.asList(at58);
		heightIsle14at58 = new HeightIsle(nr14at58, cornerMaxima);

		nr14at59 = Arrays.asList(at59);
		heightIsle14at59 = new HeightIsle(nr14at59, cornerMaxima);

		nr14at60 = Arrays.asList(at60);
		heightIsle14at60 = new HeightIsle(nr14at60, cornerMaxima);

		nr14at61 = Arrays.asList(at61);
		heightIsle14at61 = new HeightIsle(nr14at61, cornerMaxima);

		nr14at63 = Arrays.asList(at63);
		heightIsle14at63 = new HeightIsle(nr14at63, cornerMaxima);

		nr14at64 = Arrays.asList(at64);
		heightIsle14at64 = new HeightIsle(nr14at64, cornerMaxima);

		nr14at65 = Arrays.asList(at65);
		heightIsle14at65 = new HeightIsle(nr14at65, cornerMaxima);

		nr14at66 = Arrays.asList(at66);
		heightIsle14at66 = new HeightIsle(nr14at66, cornerMaxima);

		nr14at67 = Arrays.asList(at67);
		heightIsle14at67 = new HeightIsle(nr14at67, cornerMaxima);

		nr14at68 = Arrays.asList(at68);
		heightIsle14at68 = new HeightIsle(nr14at68, cornerMaxima);

		nr14at69 = Arrays.asList(at69);
		heightIsle14at69 = new HeightIsle(nr14at69, cornerMaxima);

		nr14at70 = Arrays.asList(at70);
		heightIsle14at70 = new HeightIsle(nr14at70, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle14();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

		List<Integer> copy = new ArrayList<Integer>(isleSubs);
	
		List<Integer>  rasterIndizesDescribingTheIsle;
	
		for (int sub : copy) {
	
			if (sub > 70) return -1;
	
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr14at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr14at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr14at02; break;
			case 3:
				rasterIndizesDescribingTheIsle = nr14at03; break;
			case 4:
				rasterIndizesDescribingTheIsle = nr14at04; break;
			case 5:
				rasterIndizesDescribingTheIsle = nr14at05; break;
			case 6:
				rasterIndizesDescribingTheIsle = nr14at06; break;
			case 7:
				rasterIndizesDescribingTheIsle = nr14at07; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr14at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr14at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr14at11; break;
			case 12:
				rasterIndizesDescribingTheIsle = nr14at12; break;
			case 13:
				rasterIndizesDescribingTheIsle = nr14at13; break;
			case 14:
				rasterIndizesDescribingTheIsle = nr14at14; break;
			case 15:
				rasterIndizesDescribingTheIsle = nr14at15; break;
			case 16:
				rasterIndizesDescribingTheIsle = nr14at16; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr14at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr14at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr14at20; break;
			case 21:
				rasterIndizesDescribingTheIsle = nr14at21; break;
			case 22:
				rasterIndizesDescribingTheIsle = nr14at22; break;
			case 23:
				rasterIndizesDescribingTheIsle = nr14at23; break;
			case 24:
				rasterIndizesDescribingTheIsle = nr14at24; break;
			case 25:
				rasterIndizesDescribingTheIsle = nr14at25; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr14at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr14at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr14at29; break;
			case 30:
				rasterIndizesDescribingTheIsle = nr14at30; break;
			case 31:
				rasterIndizesDescribingTheIsle = nr14at31; break;
			case 32:
				rasterIndizesDescribingTheIsle = nr14at32; break;
			case 33:
				rasterIndizesDescribingTheIsle = nr14at33; break;
			case 34:
				rasterIndizesDescribingTheIsle = nr14at34; break;
			case 36:
				rasterIndizesDescribingTheIsle = nr14at36; break;
			case 37:
				rasterIndizesDescribingTheIsle = nr14at37; break;
			case 38:
				rasterIndizesDescribingTheIsle = nr14at38; break;
			case 39:
				rasterIndizesDescribingTheIsle = nr14at39; break;
			case 40:
				rasterIndizesDescribingTheIsle = nr14at40; break;
			case 41:
				rasterIndizesDescribingTheIsle = nr14at41; break;
			case 42:
				rasterIndizesDescribingTheIsle = nr14at42; break;
			case 43:
				rasterIndizesDescribingTheIsle = nr14at43; break;
			case 45:
				rasterIndizesDescribingTheIsle = nr14at45; break;
			case 46:
				rasterIndizesDescribingTheIsle = nr14at46; break;
			case 47:
				rasterIndizesDescribingTheIsle = nr14at47; break;
			case 48:
				rasterIndizesDescribingTheIsle = nr14at48; break;
			case 49:
				rasterIndizesDescribingTheIsle = nr14at49; break;
			case 50:
				rasterIndizesDescribingTheIsle = nr14at50; break;
			case 51:
				rasterIndizesDescribingTheIsle = nr14at51; break;
			case 52:
				rasterIndizesDescribingTheIsle = nr14at52; break;
			case 54:
				rasterIndizesDescribingTheIsle = nr14at54; break;
			case 55:
				rasterIndizesDescribingTheIsle = nr14at55; break;
			case 56:
				rasterIndizesDescribingTheIsle = nr14at56; break;
			case 57:
				rasterIndizesDescribingTheIsle = nr14at57; break;
			case 58:
				rasterIndizesDescribingTheIsle = nr14at58; break;
			case 59:
				rasterIndizesDescribingTheIsle = nr14at59; break;
			case 60:
				rasterIndizesDescribingTheIsle = nr14at60; break;
			case 61:
				rasterIndizesDescribingTheIsle = nr14at61; break;
			case 63:
				rasterIndizesDescribingTheIsle = nr14at63; break;
			case 64:
				rasterIndizesDescribingTheIsle = nr14at64; break;
			case 65:
				rasterIndizesDescribingTheIsle = nr14at65; break;
			case 66:
				rasterIndizesDescribingTheIsle = nr14at66; break;
			case 67:
				rasterIndizesDescribingTheIsle = nr14at67; break;
			case 68:
				rasterIndizesDescribingTheIsle = nr14at68; break;
			case 69:
				rasterIndizesDescribingTheIsle = nr14at69; break;
			case 70:
				rasterIndizesDescribingTheIsle = nr14at70; break;
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
			return heightIsle14at00;
		case 1:
			return heightIsle14at01;
		case 2:
			return heightIsle14at02;
		case 3:
			return heightIsle14at03;
		case 4:
			return heightIsle14at04;
		case 5:
			return heightIsle14at05;
		case 6:
			return heightIsle14at06;
		case 7:
			return heightIsle14at07;
		case 9:
			return heightIsle14at09;
		case 10:
			return heightIsle14at10;
		case 11:
			return heightIsle14at11;
		case 12:
			return heightIsle14at12;
		case 13:
			return heightIsle14at13;
		case 14:
			return heightIsle14at14;
		case 15:
			return heightIsle14at15;
		case 16:
			return heightIsle14at16;
		case 18:
			return heightIsle14at18;
		case 19:
			return heightIsle14at19;
		case 20:
			return heightIsle14at20;
		case 21:
			return heightIsle14at21;
		case 22:
			return heightIsle14at22;
		case 23:
			return heightIsle14at23;
		case 24:
			return heightIsle14at24;
		case 25:
			return heightIsle14at25;
		case 27:
			return heightIsle14at27;
		case 28:
			return heightIsle14at28;
		case 29:
			return heightIsle14at29;
		case 30:
			return heightIsle14at30;
		case 31:
			return heightIsle14at31;
		case 32:
			return heightIsle14at32;
		case 33:
			return heightIsle14at33;
		case 34:
			return heightIsle14at34;
		case 36:
			return heightIsle14at36;
		case 37:
			return heightIsle14at37;
		case 38:
			return heightIsle14at38;
		case 39:
			return heightIsle14at39;
		case 40:
			return heightIsle14at40;
		case 41:
			return heightIsle14at41;
		case 42:
			return heightIsle14at42;
		case 43:
			return heightIsle14at43;
		case 45:
			return heightIsle14at45;
		case 46:
			return heightIsle14at46;
		case 47:
			return heightIsle14at47;
		case 48:
			return heightIsle14at48;
		case 49:
			return heightIsle14at49;
		case 50:
			return heightIsle14at50;
		case 51:
			return heightIsle14at51;
		case 52:
			return heightIsle14at52;
		case 54:
			return heightIsle14at54;
		case 55:
			return heightIsle14at55;
		case 56:
			return heightIsle14at56;
		case 57:
			return heightIsle14at57;
		case 58:
			return heightIsle14at58;
		case 59:
			return heightIsle14at59;
		case 60:
			return heightIsle14at60;
		case 61:
			return heightIsle14at61;
		case 63:
			return heightIsle14at63;
		case 64:
			return heightIsle14at64;
		case 65:
			return heightIsle14at65;
		case 66:
			return heightIsle14at66;
		case 67:
			return heightIsle14at67;
		case 68:
			return heightIsle14at68;
		case 69:
			return heightIsle14at69;
		case 70:
			return heightIsle14at70;
		default:
			return null;
		}
	}
}
