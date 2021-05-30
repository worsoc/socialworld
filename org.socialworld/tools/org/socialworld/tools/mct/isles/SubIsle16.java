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

public class SubIsle16 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  
						9, 10, 11};

	private final static Integer at01[]  = {1,  2,  3,  
						10, 11, 12};

	private final static Integer at02[]  = {2,  3,  4,  
						11, 12, 13};

	private final static Integer at03[]  = {3,  4,  5,  
						12, 13, 14};

	private final static Integer at04[]  = {4,  5,  6,  
						13, 14, 15};

	private final static Integer at05[]  = {5,  6,  7,  
						14, 15, 16};

	private final static Integer at06[]  = {6,  7,  8,  
						15, 16, 17};

	private final static Integer at09[]  = {9,  10, 11, 
						18, 19, 20};

	private final static Integer at10[]  = {10, 11, 12, 
						19, 20, 21};

	private final static Integer at11[]  = {11, 12, 13, 
						20, 21, 22};

	private final static Integer at12[]  = {12, 13, 14, 
						21, 22, 23};

	private final static Integer at13[]  = {13, 14, 15, 
						22, 23, 24};

	private final static Integer at14[]  = {14, 15, 16, 
						23, 24, 25};

	private final static Integer at15[]  = {15, 16, 17, 
						24, 25, 26};

	private final static Integer at18[]  = {18, 19, 20, 
						27, 28, 29};

	private final static Integer at19[]  = {19, 20, 21, 
						28, 29, 30};

	private final static Integer at20[]  = {20, 21, 22, 
						29, 30, 31};

	private final static Integer at21[]  = {21, 22, 23, 
						30, 31, 32};

	private final static Integer at22[]  = {22, 23, 24, 
						31, 32, 33};

	private final static Integer at23[]  = {23, 24, 25, 
						32, 33, 34};

	private final static Integer at24[]  = {24, 25, 26, 
						33, 34, 35};

	private final static Integer at27[]  = {27, 28, 29, 
						36, 37, 38};

	private final static Integer at28[]  = {28, 29, 30, 
						37, 38, 39};

	private final static Integer at29[]  = {29, 30, 31, 
						38, 39, 40};

	private final static Integer at30[]  = {30, 31, 32, 
						39, 40, 41};

	private final static Integer at31[]  = {31, 32, 33, 
						40, 41, 42};

	private final static Integer at32[]  = {32, 33, 34, 
						41, 42, 43};

	private final static Integer at33[]  = {33, 34, 35, 
						42, 43, 44};

	private final static Integer at36[]  = {36, 37, 38, 
						45, 46, 47};

	private final static Integer at37[]  = {37, 38, 39, 
						46, 47, 48};

	private final static Integer at38[]  = {38, 39, 40, 
						47, 48, 49};

	private final static Integer at39[]  = {39, 40, 41, 
						48, 49, 50};

	private final static Integer at40[]  = {40, 41, 42, 
						49, 50, 51};

	private final static Integer at41[]  = {41, 42, 43, 
						50, 51, 52};

	private final static Integer at42[]  = {42, 43, 44, 
						51, 52, 53};

	private final static Integer at45[]  = {45, 46, 47, 
						54, 55, 56};

	private final static Integer at46[]  = {46, 47, 48, 
						55, 56, 57};

	private final static Integer at47[]  = {47, 48, 49, 
						56, 57, 58};

	private final static Integer at48[]  = {48, 49, 50, 
						57, 58, 59};

	private final static Integer at49[]  = {49, 50, 51, 
						58, 59, 60};

	private final static Integer at50[]  = {50, 51, 52, 
						59, 60, 61};

	private final static Integer at51[]  = {51, 52, 53, 
						60, 61, 62};

	private final static Integer at54[]  = {54, 55, 56, 
						63, 64, 65};

	private final static Integer at55[]  = {55, 56, 57, 
						64, 65, 66};

	private final static Integer at56[]  = {56, 57, 58, 
						65, 66, 67};

	private final static Integer at57[]  = {57, 58, 59, 
						66, 67, 68};

	private final static Integer at58[]  = {58, 59, 60, 
						67, 68, 69};

	private final static Integer at59[]  = {59, 60, 61, 
						68, 69, 70};

	private final static Integer at60[]  = {60, 61, 62, 
						69, 70, 71};

	private final static Integer at63[]  = {63, 64, 65, 
						72, 73, 74};

	private final static Integer at64[]  = {64, 65, 66, 
						73, 74, 75};

	private final static Integer at65[]  = {65, 66, 67, 
						74, 75, 76};

	private final static Integer at66[]  = {66, 67, 68, 
						75, 76, 77};

	private final static Integer at67[]  = {67, 68, 69, 
						76, 77, 78};

	private final static Integer at68[]  = {68, 69, 70, 
						77, 78, 79};

	private final static Integer at69[]  = {69, 70, 71, 
						78, 79, 80};

	private List<Integer> nr16at00;
	private HeightIsle heightIsle16at00;

	private List<Integer> nr16at01;
	private HeightIsle heightIsle16at01;

	private List<Integer> nr16at02;
	private HeightIsle heightIsle16at02;

	private List<Integer> nr16at03;
	private HeightIsle heightIsle16at03;

	private List<Integer> nr16at04;
	private HeightIsle heightIsle16at04;

	private List<Integer> nr16at05;
	private HeightIsle heightIsle16at05;

	private List<Integer> nr16at06;
	private HeightIsle heightIsle16at06;

	private List<Integer> nr16at09;
	private HeightIsle heightIsle16at09;

	private List<Integer> nr16at10;
	private HeightIsle heightIsle16at10;

	private List<Integer> nr16at11;
	private HeightIsle heightIsle16at11;

	private List<Integer> nr16at12;
	private HeightIsle heightIsle16at12;

	private List<Integer> nr16at13;
	private HeightIsle heightIsle16at13;

	private List<Integer> nr16at14;
	private HeightIsle heightIsle16at14;

	private List<Integer> nr16at15;
	private HeightIsle heightIsle16at15;

	private List<Integer> nr16at18;
	private HeightIsle heightIsle16at18;

	private List<Integer> nr16at19;
	private HeightIsle heightIsle16at19;

	private List<Integer> nr16at20;
	private HeightIsle heightIsle16at20;

	private List<Integer> nr16at21;
	private HeightIsle heightIsle16at21;

	private List<Integer> nr16at22;
	private HeightIsle heightIsle16at22;

	private List<Integer> nr16at23;
	private HeightIsle heightIsle16at23;

	private List<Integer> nr16at24;
	private HeightIsle heightIsle16at24;

	private List<Integer> nr16at27;
	private HeightIsle heightIsle16at27;

	private List<Integer> nr16at28;
	private HeightIsle heightIsle16at28;

	private List<Integer> nr16at29;
	private HeightIsle heightIsle16at29;

	private List<Integer> nr16at30;
	private HeightIsle heightIsle16at30;

	private List<Integer> nr16at31;
	private HeightIsle heightIsle16at31;

	private List<Integer> nr16at32;
	private HeightIsle heightIsle16at32;

	private List<Integer> nr16at33;
	private HeightIsle heightIsle16at33;

	private List<Integer> nr16at36;
	private HeightIsle heightIsle16at36;

	private List<Integer> nr16at37;
	private HeightIsle heightIsle16at37;

	private List<Integer> nr16at38;
	private HeightIsle heightIsle16at38;

	private List<Integer> nr16at39;
	private HeightIsle heightIsle16at39;

	private List<Integer> nr16at40;
	private HeightIsle heightIsle16at40;

	private List<Integer> nr16at41;
	private HeightIsle heightIsle16at41;

	private List<Integer> nr16at42;
	private HeightIsle heightIsle16at42;

	private List<Integer> nr16at45;
	private HeightIsle heightIsle16at45;

	private List<Integer> nr16at46;
	private HeightIsle heightIsle16at46;

	private List<Integer> nr16at47;
	private HeightIsle heightIsle16at47;

	private List<Integer> nr16at48;
	private HeightIsle heightIsle16at48;

	private List<Integer> nr16at49;
	private HeightIsle heightIsle16at49;

	private List<Integer> nr16at50;
	private HeightIsle heightIsle16at50;

	private List<Integer> nr16at51;
	private HeightIsle heightIsle16at51;

	private List<Integer> nr16at54;
	private HeightIsle heightIsle16at54;

	private List<Integer> nr16at55;
	private HeightIsle heightIsle16at55;

	private List<Integer> nr16at56;
	private HeightIsle heightIsle16at56;

	private List<Integer> nr16at57;
	private HeightIsle heightIsle16at57;

	private List<Integer> nr16at58;
	private HeightIsle heightIsle16at58;

	private List<Integer> nr16at59;
	private HeightIsle heightIsle16at59;

	private List<Integer> nr16at60;
	private HeightIsle heightIsle16at60;

	private List<Integer> nr16at63;
	private HeightIsle heightIsle16at63;

	private List<Integer> nr16at64;
	private HeightIsle heightIsle16at64;

	private List<Integer> nr16at65;
	private HeightIsle heightIsle16at65;

	private List<Integer> nr16at66;
	private HeightIsle heightIsle16at66;

	private List<Integer> nr16at67;
	private HeightIsle heightIsle16at67;

	private List<Integer> nr16at68;
	private HeightIsle heightIsle16at68;

	private List<Integer> nr16at69;
	private HeightIsle heightIsle16at69;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle16() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr16at00 = Arrays.asList(at00);
		heightIsle16at00 = new HeightIsle(nr16at00, cornerMaxima);

		nr16at01 = Arrays.asList(at01);
		heightIsle16at01 = new HeightIsle(nr16at01, cornerMaxima);

		nr16at02 = Arrays.asList(at02);
		heightIsle16at02 = new HeightIsle(nr16at02, cornerMaxima);

		nr16at03 = Arrays.asList(at03);
		heightIsle16at03 = new HeightIsle(nr16at03, cornerMaxima);

		nr16at04 = Arrays.asList(at04);
		heightIsle16at04 = new HeightIsle(nr16at04, cornerMaxima);

		nr16at05 = Arrays.asList(at05);
		heightIsle16at05 = new HeightIsle(nr16at05, cornerMaxima);

		nr16at06 = Arrays.asList(at06);
		heightIsle16at06 = new HeightIsle(nr16at06, cornerMaxima);

		nr16at09 = Arrays.asList(at09);
		heightIsle16at09 = new HeightIsle(nr16at09, cornerMaxima);

		nr16at10 = Arrays.asList(at10);
		heightIsle16at10 = new HeightIsle(nr16at10, cornerMaxima);

		nr16at11 = Arrays.asList(at11);
		heightIsle16at11 = new HeightIsle(nr16at11, cornerMaxima);

		nr16at12 = Arrays.asList(at12);
		heightIsle16at12 = new HeightIsle(nr16at12, cornerMaxima);

		nr16at13 = Arrays.asList(at13);
		heightIsle16at13 = new HeightIsle(nr16at13, cornerMaxima);

		nr16at14 = Arrays.asList(at14);
		heightIsle16at14 = new HeightIsle(nr16at14, cornerMaxima);

		nr16at15 = Arrays.asList(at15);
		heightIsle16at15 = new HeightIsle(nr16at15, cornerMaxima);

		nr16at18 = Arrays.asList(at18);
		heightIsle16at18 = new HeightIsle(nr16at18, cornerMaxima);

		nr16at19 = Arrays.asList(at19);
		heightIsle16at19 = new HeightIsle(nr16at19, cornerMaxima);

		nr16at20 = Arrays.asList(at20);
		heightIsle16at20 = new HeightIsle(nr16at20, cornerMaxima);

		nr16at21 = Arrays.asList(at21);
		heightIsle16at21 = new HeightIsle(nr16at21, cornerMaxima);

		nr16at22 = Arrays.asList(at22);
		heightIsle16at22 = new HeightIsle(nr16at22, cornerMaxima);

		nr16at23 = Arrays.asList(at23);
		heightIsle16at23 = new HeightIsle(nr16at23, cornerMaxima);

		nr16at24 = Arrays.asList(at24);
		heightIsle16at24 = new HeightIsle(nr16at24, cornerMaxima);

		nr16at27 = Arrays.asList(at27);
		heightIsle16at27 = new HeightIsle(nr16at27, cornerMaxima);

		nr16at28 = Arrays.asList(at28);
		heightIsle16at28 = new HeightIsle(nr16at28, cornerMaxima);

		nr16at29 = Arrays.asList(at29);
		heightIsle16at29 = new HeightIsle(nr16at29, cornerMaxima);

		nr16at30 = Arrays.asList(at30);
		heightIsle16at30 = new HeightIsle(nr16at30, cornerMaxima);

		nr16at31 = Arrays.asList(at31);
		heightIsle16at31 = new HeightIsle(nr16at31, cornerMaxima);

		nr16at32 = Arrays.asList(at32);
		heightIsle16at32 = new HeightIsle(nr16at32, cornerMaxima);

		nr16at33 = Arrays.asList(at33);
		heightIsle16at33 = new HeightIsle(nr16at33, cornerMaxima);

		nr16at36 = Arrays.asList(at36);
		heightIsle16at36 = new HeightIsle(nr16at36, cornerMaxima);

		nr16at37 = Arrays.asList(at37);
		heightIsle16at37 = new HeightIsle(nr16at37, cornerMaxima);

		nr16at38 = Arrays.asList(at38);
		heightIsle16at38 = new HeightIsle(nr16at38, cornerMaxima);

		nr16at39 = Arrays.asList(at39);
		heightIsle16at39 = new HeightIsle(nr16at39, cornerMaxima);

		nr16at40 = Arrays.asList(at40);
		heightIsle16at40 = new HeightIsle(nr16at40, cornerMaxima);

		nr16at41 = Arrays.asList(at41);
		heightIsle16at41 = new HeightIsle(nr16at41, cornerMaxima);

		nr16at42 = Arrays.asList(at42);
		heightIsle16at42 = new HeightIsle(nr16at42, cornerMaxima);

		nr16at45 = Arrays.asList(at45);
		heightIsle16at45 = new HeightIsle(nr16at45, cornerMaxima);

		nr16at46 = Arrays.asList(at46);
		heightIsle16at46 = new HeightIsle(nr16at46, cornerMaxima);

		nr16at47 = Arrays.asList(at47);
		heightIsle16at47 = new HeightIsle(nr16at47, cornerMaxima);

		nr16at48 = Arrays.asList(at48);
		heightIsle16at48 = new HeightIsle(nr16at48, cornerMaxima);

		nr16at49 = Arrays.asList(at49);
		heightIsle16at49 = new HeightIsle(nr16at49, cornerMaxima);

		nr16at50 = Arrays.asList(at50);
		heightIsle16at50 = new HeightIsle(nr16at50, cornerMaxima);

		nr16at51 = Arrays.asList(at51);
		heightIsle16at51 = new HeightIsle(nr16at51, cornerMaxima);

		nr16at54 = Arrays.asList(at54);
		heightIsle16at54 = new HeightIsle(nr16at54, cornerMaxima);

		nr16at55 = Arrays.asList(at55);
		heightIsle16at55 = new HeightIsle(nr16at55, cornerMaxima);

		nr16at56 = Arrays.asList(at56);
		heightIsle16at56 = new HeightIsle(nr16at56, cornerMaxima);

		nr16at57 = Arrays.asList(at57);
		heightIsle16at57 = new HeightIsle(nr16at57, cornerMaxima);

		nr16at58 = Arrays.asList(at58);
		heightIsle16at58 = new HeightIsle(nr16at58, cornerMaxima);

		nr16at59 = Arrays.asList(at59);
		heightIsle16at59 = new HeightIsle(nr16at59, cornerMaxima);

		nr16at60 = Arrays.asList(at60);
		heightIsle16at60 = new HeightIsle(nr16at60, cornerMaxima);

		nr16at63 = Arrays.asList(at63);
		heightIsle16at63 = new HeightIsle(nr16at63, cornerMaxima);

		nr16at64 = Arrays.asList(at64);
		heightIsle16at64 = new HeightIsle(nr16at64, cornerMaxima);

		nr16at65 = Arrays.asList(at65);
		heightIsle16at65 = new HeightIsle(nr16at65, cornerMaxima);

		nr16at66 = Arrays.asList(at66);
		heightIsle16at66 = new HeightIsle(nr16at66, cornerMaxima);

		nr16at67 = Arrays.asList(at67);
		heightIsle16at67 = new HeightIsle(nr16at67, cornerMaxima);

		nr16at68 = Arrays.asList(at68);
		heightIsle16at68 = new HeightIsle(nr16at68, cornerMaxima);

		nr16at69 = Arrays.asList(at69);
		heightIsle16at69 = new HeightIsle(nr16at69, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle16();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 69) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr16at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr16at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr16at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr16at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr16at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr16at05; break;
		case 6:
			rasterIndizesDescribingTheIsle = nr16at06; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr16at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr16at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr16at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr16at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr16at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr16at14; break;
		case 15:
			rasterIndizesDescribingTheIsle = nr16at15; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr16at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr16at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr16at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr16at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr16at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr16at23; break;
		case 24:
			rasterIndizesDescribingTheIsle = nr16at24; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr16at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr16at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr16at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr16at30; break;
		case 31:
			rasterIndizesDescribingTheIsle = nr16at31; break;
		case 32:
			rasterIndizesDescribingTheIsle = nr16at32; break;
		case 33:
			rasterIndizesDescribingTheIsle = nr16at33; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr16at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr16at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr16at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr16at39; break;
		case 40:
			rasterIndizesDescribingTheIsle = nr16at40; break;
		case 41:
			rasterIndizesDescribingTheIsle = nr16at41; break;
		case 42:
			rasterIndizesDescribingTheIsle = nr16at42; break;
		case 45:
			rasterIndizesDescribingTheIsle = nr16at45; break;
		case 46:
			rasterIndizesDescribingTheIsle = nr16at46; break;
		case 47:
			rasterIndizesDescribingTheIsle = nr16at47; break;
		case 48:
			rasterIndizesDescribingTheIsle = nr16at48; break;
		case 49:
			rasterIndizesDescribingTheIsle = nr16at49; break;
		case 50:
			rasterIndizesDescribingTheIsle = nr16at50; break;
		case 51:
			rasterIndizesDescribingTheIsle = nr16at51; break;
		case 54:
			rasterIndizesDescribingTheIsle = nr16at54; break;
		case 55:
			rasterIndizesDescribingTheIsle = nr16at55; break;
		case 56:
			rasterIndizesDescribingTheIsle = nr16at56; break;
		case 57:
			rasterIndizesDescribingTheIsle = nr16at57; break;
		case 58:
			rasterIndizesDescribingTheIsle = nr16at58; break;
		case 59:
			rasterIndizesDescribingTheIsle = nr16at59; break;
		case 60:
			rasterIndizesDescribingTheIsle = nr16at60; break;
		case 63:
			rasterIndizesDescribingTheIsle = nr16at63; break;
		case 64:
			rasterIndizesDescribingTheIsle = nr16at64; break;
		case 65:
			rasterIndizesDescribingTheIsle = nr16at65; break;
		case 66:
			rasterIndizesDescribingTheIsle = nr16at66; break;
		case 67:
			rasterIndizesDescribingTheIsle = nr16at67; break;
		case 68:
			rasterIndizesDescribingTheIsle = nr16at68; break;
		case 69:
			rasterIndizesDescribingTheIsle = nr16at69; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle16at00;
		case 1:
			return heightIsle16at01;
		case 2:
			return heightIsle16at02;
		case 3:
			return heightIsle16at03;
		case 4:
			return heightIsle16at04;
		case 5:
			return heightIsle16at05;
		case 6:
			return heightIsle16at06;
		case 9:
			return heightIsle16at09;
		case 10:
			return heightIsle16at10;
		case 11:
			return heightIsle16at11;
		case 12:
			return heightIsle16at12;
		case 13:
			return heightIsle16at13;
		case 14:
			return heightIsle16at14;
		case 15:
			return heightIsle16at15;
		case 18:
			return heightIsle16at18;
		case 19:
			return heightIsle16at19;
		case 20:
			return heightIsle16at20;
		case 21:
			return heightIsle16at21;
		case 22:
			return heightIsle16at22;
		case 23:
			return heightIsle16at23;
		case 24:
			return heightIsle16at24;
		case 27:
			return heightIsle16at27;
		case 28:
			return heightIsle16at28;
		case 29:
			return heightIsle16at29;
		case 30:
			return heightIsle16at30;
		case 31:
			return heightIsle16at31;
		case 32:
			return heightIsle16at32;
		case 33:
			return heightIsle16at33;
		case 36:
			return heightIsle16at36;
		case 37:
			return heightIsle16at37;
		case 38:
			return heightIsle16at38;
		case 39:
			return heightIsle16at39;
		case 40:
			return heightIsle16at40;
		case 41:
			return heightIsle16at41;
		case 42:
			return heightIsle16at42;
		case 45:
			return heightIsle16at45;
		case 46:
			return heightIsle16at46;
		case 47:
			return heightIsle16at47;
		case 48:
			return heightIsle16at48;
		case 49:
			return heightIsle16at49;
		case 50:
			return heightIsle16at50;
		case 51:
			return heightIsle16at51;
		case 54:
			return heightIsle16at54;
		case 55:
			return heightIsle16at55;
		case 56:
			return heightIsle16at56;
		case 57:
			return heightIsle16at57;
		case 58:
			return heightIsle16at58;
		case 59:
			return heightIsle16at59;
		case 60:
			return heightIsle16at60;
		case 63:
			return heightIsle16at63;
		case 64:
			return heightIsle16at64;
		case 65:
			return heightIsle16at65;
		case 66:
			return heightIsle16at66;
		case 67:
			return heightIsle16at67;
		case 68:
			return heightIsle16at68;
		case 69:
			return heightIsle16at69;
		default:
			return null;
		}
	}
}
