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

public class SubIsle18 extends SubIsle {

	private final static Integer at00[]  = {0,  1,  2,  3,  
						9, 10, 11, 12};

	private final static Integer at01[]  = {1,  2,  3,  4,  
						10, 11, 12, 13};

	private final static Integer at02[]  = {2,  3,  4,  5,  
						11, 12, 13, 14};

	private final static Integer at03[]  = {3,  4,  5,  6,  
						12, 13, 14, 15};

	private final static Integer at04[]  = {4,  5,  6,  7,  
						13, 14, 15, 16};

	private final static Integer at05[]  = {5,  6,  7,  8,  
						14, 15, 16, 17};

	private final static Integer at09[]  = {9,  10, 11, 12, 
						18, 19, 20, 21};

	private final static Integer at10[]  = {10, 11, 12, 13, 
						19, 20, 21, 22};

	private final static Integer at11[]  = {11, 12, 13, 14, 
						20, 21, 22, 23};

	private final static Integer at12[]  = {12, 13, 14, 15, 
						21, 22, 23, 24};

	private final static Integer at13[]  = {13, 14, 15, 16, 
						22, 23, 24, 25};

	private final static Integer at14[]  = {14, 15, 16, 17, 
						23, 24, 25, 26};

	private final static Integer at18[]  = {18, 19, 20, 21, 
						27, 28, 29, 30};

	private final static Integer at19[]  = {19, 20, 21, 22, 
						28, 29, 30, 31};

	private final static Integer at20[]  = {20, 21, 22, 23, 
						29, 30, 31, 32};

	private final static Integer at21[]  = {21, 22, 23, 24, 
						30, 31, 32, 33};

	private final static Integer at22[]  = {22, 23, 24, 25, 
						31, 32, 33, 34};

	private final static Integer at23[]  = {23, 24, 25, 26, 
						32, 33, 34, 35};

	private final static Integer at27[]  = {27, 28, 29, 30, 
						36, 37, 38, 39};

	private final static Integer at28[]  = {28, 29, 30, 31, 
						37, 38, 39, 40};

	private final static Integer at29[]  = {29, 30, 31, 32, 
						38, 39, 40, 41};

	private final static Integer at30[]  = {30, 31, 32, 33, 
						39, 40, 41, 42};

	private final static Integer at31[]  = {31, 32, 33, 34, 
						40, 41, 42, 43};

	private final static Integer at32[]  = {32, 33, 34, 35, 
						41, 42, 43, 44};

	private final static Integer at36[]  = {36, 37, 38, 39, 
						45, 46, 47, 48};

	private final static Integer at37[]  = {37, 38, 39, 40, 
						46, 47, 48, 49};

	private final static Integer at38[]  = {38, 39, 40, 41, 
						47, 48, 49, 50};

	private final static Integer at39[]  = {39, 40, 41, 42, 
						48, 49, 50, 51};

	private final static Integer at40[]  = {40, 41, 42, 43, 
						49, 50, 51, 52};

	private final static Integer at41[]  = {41, 42, 43, 44, 
						50, 51, 52, 53};

	private final static Integer at45[]  = {45, 46, 47, 48, 
						54, 55, 56, 57};

	private final static Integer at46[]  = {46, 47, 48, 49, 
						55, 56, 57, 58};

	private final static Integer at47[]  = {47, 48, 49, 50, 
						56, 57, 58, 59};

	private final static Integer at48[]  = {48, 49, 50, 51, 
						57, 58, 59, 60};

	private final static Integer at49[]  = {49, 50, 51, 52, 
						58, 59, 60, 61};

	private final static Integer at50[]  = {50, 51, 52, 53, 
						59, 60, 61, 62};

	private final static Integer at54[]  = {54, 55, 56, 57, 
						63, 64, 65, 66};

	private final static Integer at55[]  = {55, 56, 57, 58, 
						64, 65, 66, 67};

	private final static Integer at56[]  = {56, 57, 58, 59, 
						65, 66, 67, 68};

	private final static Integer at57[]  = {57, 58, 59, 60, 
						66, 67, 68, 69};

	private final static Integer at58[]  = {58, 59, 60, 61, 
						67, 68, 69, 70};

	private final static Integer at59[]  = {59, 60, 61, 62, 
						68, 69, 70, 71};

	private final static Integer at63[]  = {63, 64, 65, 66, 
						72, 73, 74, 75};

	private final static Integer at64[]  = {64, 65, 66, 67, 
						73, 74, 75, 76};

	private final static Integer at65[]  = {65, 66, 67, 68, 
						74, 75, 76, 77};

	private final static Integer at66[]  = {66, 67, 68, 69, 
						75, 76, 77, 78};

	private final static Integer at67[]  = {67, 68, 69, 70, 
						76, 77, 78, 79};

	private final static Integer at68[]  = {68, 69, 70, 71, 
						77, 78, 79, 80};

	private List<Integer> nr18at00;
	private HeightIsle heightIsle18at00;

	private List<Integer> nr18at01;
	private HeightIsle heightIsle18at01;

	private List<Integer> nr18at02;
	private HeightIsle heightIsle18at02;

	private List<Integer> nr18at03;
	private HeightIsle heightIsle18at03;

	private List<Integer> nr18at04;
	private HeightIsle heightIsle18at04;

	private List<Integer> nr18at05;
	private HeightIsle heightIsle18at05;

	private List<Integer> nr18at09;
	private HeightIsle heightIsle18at09;

	private List<Integer> nr18at10;
	private HeightIsle heightIsle18at10;

	private List<Integer> nr18at11;
	private HeightIsle heightIsle18at11;

	private List<Integer> nr18at12;
	private HeightIsle heightIsle18at12;

	private List<Integer> nr18at13;
	private HeightIsle heightIsle18at13;

	private List<Integer> nr18at14;
	private HeightIsle heightIsle18at14;

	private List<Integer> nr18at18;
	private HeightIsle heightIsle18at18;

	private List<Integer> nr18at19;
	private HeightIsle heightIsle18at19;

	private List<Integer> nr18at20;
	private HeightIsle heightIsle18at20;

	private List<Integer> nr18at21;
	private HeightIsle heightIsle18at21;

	private List<Integer> nr18at22;
	private HeightIsle heightIsle18at22;

	private List<Integer> nr18at23;
	private HeightIsle heightIsle18at23;

	private List<Integer> nr18at27;
	private HeightIsle heightIsle18at27;

	private List<Integer> nr18at28;
	private HeightIsle heightIsle18at28;

	private List<Integer> nr18at29;
	private HeightIsle heightIsle18at29;

	private List<Integer> nr18at30;
	private HeightIsle heightIsle18at30;

	private List<Integer> nr18at31;
	private HeightIsle heightIsle18at31;

	private List<Integer> nr18at32;
	private HeightIsle heightIsle18at32;

	private List<Integer> nr18at36;
	private HeightIsle heightIsle18at36;

	private List<Integer> nr18at37;
	private HeightIsle heightIsle18at37;

	private List<Integer> nr18at38;
	private HeightIsle heightIsle18at38;

	private List<Integer> nr18at39;
	private HeightIsle heightIsle18at39;

	private List<Integer> nr18at40;
	private HeightIsle heightIsle18at40;

	private List<Integer> nr18at41;
	private HeightIsle heightIsle18at41;

	private List<Integer> nr18at45;
	private HeightIsle heightIsle18at45;

	private List<Integer> nr18at46;
	private HeightIsle heightIsle18at46;

	private List<Integer> nr18at47;
	private HeightIsle heightIsle18at47;

	private List<Integer> nr18at48;
	private HeightIsle heightIsle18at48;

	private List<Integer> nr18at49;
	private HeightIsle heightIsle18at49;

	private List<Integer> nr18at50;
	private HeightIsle heightIsle18at50;

	private List<Integer> nr18at54;
	private HeightIsle heightIsle18at54;

	private List<Integer> nr18at55;
	private HeightIsle heightIsle18at55;

	private List<Integer> nr18at56;
	private HeightIsle heightIsle18at56;

	private List<Integer> nr18at57;
	private HeightIsle heightIsle18at57;

	private List<Integer> nr18at58;
	private HeightIsle heightIsle18at58;

	private List<Integer> nr18at59;
	private HeightIsle heightIsle18at59;

	private List<Integer> nr18at63;
	private HeightIsle heightIsle18at63;

	private List<Integer> nr18at64;
	private HeightIsle heightIsle18at64;

	private List<Integer> nr18at65;
	private HeightIsle heightIsle18at65;

	private List<Integer> nr18at66;
	private HeightIsle heightIsle18at66;

	private List<Integer> nr18at67;
	private HeightIsle heightIsle18at67;

	private List<Integer> nr18at68;
	private HeightIsle heightIsle18at68;

	private final static Integer[] cornerMaximaNrs = {};

	private List<Integer> cornerMaxima;

	private SubIsle18() {

		cornerMaxima = Arrays.asList(cornerMaximaNrs);

		nr18at00 = Arrays.asList(at00);
		heightIsle18at00 = new HeightIsle(nr18at00, cornerMaxima);

		nr18at01 = Arrays.asList(at01);
		heightIsle18at01 = new HeightIsle(nr18at01, cornerMaxima);

		nr18at02 = Arrays.asList(at02);
		heightIsle18at02 = new HeightIsle(nr18at02, cornerMaxima);

		nr18at03 = Arrays.asList(at03);
		heightIsle18at03 = new HeightIsle(nr18at03, cornerMaxima);

		nr18at04 = Arrays.asList(at04);
		heightIsle18at04 = new HeightIsle(nr18at04, cornerMaxima);

		nr18at05 = Arrays.asList(at05);
		heightIsle18at05 = new HeightIsle(nr18at05, cornerMaxima);

		nr18at09 = Arrays.asList(at09);
		heightIsle18at09 = new HeightIsle(nr18at09, cornerMaxima);

		nr18at10 = Arrays.asList(at10);
		heightIsle18at10 = new HeightIsle(nr18at10, cornerMaxima);

		nr18at11 = Arrays.asList(at11);
		heightIsle18at11 = new HeightIsle(nr18at11, cornerMaxima);

		nr18at12 = Arrays.asList(at12);
		heightIsle18at12 = new HeightIsle(nr18at12, cornerMaxima);

		nr18at13 = Arrays.asList(at13);
		heightIsle18at13 = new HeightIsle(nr18at13, cornerMaxima);

		nr18at14 = Arrays.asList(at14);
		heightIsle18at14 = new HeightIsle(nr18at14, cornerMaxima);

		nr18at18 = Arrays.asList(at18);
		heightIsle18at18 = new HeightIsle(nr18at18, cornerMaxima);

		nr18at19 = Arrays.asList(at19);
		heightIsle18at19 = new HeightIsle(nr18at19, cornerMaxima);

		nr18at20 = Arrays.asList(at20);
		heightIsle18at20 = new HeightIsle(nr18at20, cornerMaxima);

		nr18at21 = Arrays.asList(at21);
		heightIsle18at21 = new HeightIsle(nr18at21, cornerMaxima);

		nr18at22 = Arrays.asList(at22);
		heightIsle18at22 = new HeightIsle(nr18at22, cornerMaxima);

		nr18at23 = Arrays.asList(at23);
		heightIsle18at23 = new HeightIsle(nr18at23, cornerMaxima);

		nr18at27 = Arrays.asList(at27);
		heightIsle18at27 = new HeightIsle(nr18at27, cornerMaxima);

		nr18at28 = Arrays.asList(at28);
		heightIsle18at28 = new HeightIsle(nr18at28, cornerMaxima);

		nr18at29 = Arrays.asList(at29);
		heightIsle18at29 = new HeightIsle(nr18at29, cornerMaxima);

		nr18at30 = Arrays.asList(at30);
		heightIsle18at30 = new HeightIsle(nr18at30, cornerMaxima);

		nr18at31 = Arrays.asList(at31);
		heightIsle18at31 = new HeightIsle(nr18at31, cornerMaxima);

		nr18at32 = Arrays.asList(at32);
		heightIsle18at32 = new HeightIsle(nr18at32, cornerMaxima);

		nr18at36 = Arrays.asList(at36);
		heightIsle18at36 = new HeightIsle(nr18at36, cornerMaxima);

		nr18at37 = Arrays.asList(at37);
		heightIsle18at37 = new HeightIsle(nr18at37, cornerMaxima);

		nr18at38 = Arrays.asList(at38);
		heightIsle18at38 = new HeightIsle(nr18at38, cornerMaxima);

		nr18at39 = Arrays.asList(at39);
		heightIsle18at39 = new HeightIsle(nr18at39, cornerMaxima);

		nr18at40 = Arrays.asList(at40);
		heightIsle18at40 = new HeightIsle(nr18at40, cornerMaxima);

		nr18at41 = Arrays.asList(at41);
		heightIsle18at41 = new HeightIsle(nr18at41, cornerMaxima);

		nr18at45 = Arrays.asList(at45);
		heightIsle18at45 = new HeightIsle(nr18at45, cornerMaxima);

		nr18at46 = Arrays.asList(at46);
		heightIsle18at46 = new HeightIsle(nr18at46, cornerMaxima);

		nr18at47 = Arrays.asList(at47);
		heightIsle18at47 = new HeightIsle(nr18at47, cornerMaxima);

		nr18at48 = Arrays.asList(at48);
		heightIsle18at48 = new HeightIsle(nr18at48, cornerMaxima);

		nr18at49 = Arrays.asList(at49);
		heightIsle18at49 = new HeightIsle(nr18at49, cornerMaxima);

		nr18at50 = Arrays.asList(at50);
		heightIsle18at50 = new HeightIsle(nr18at50, cornerMaxima);

		nr18at54 = Arrays.asList(at54);
		heightIsle18at54 = new HeightIsle(nr18at54, cornerMaxima);

		nr18at55 = Arrays.asList(at55);
		heightIsle18at55 = new HeightIsle(nr18at55, cornerMaxima);

		nr18at56 = Arrays.asList(at56);
		heightIsle18at56 = new HeightIsle(nr18at56, cornerMaxima);

		nr18at57 = Arrays.asList(at57);
		heightIsle18at57 = new HeightIsle(nr18at57, cornerMaxima);

		nr18at58 = Arrays.asList(at58);
		heightIsle18at58 = new HeightIsle(nr18at58, cornerMaxima);

		nr18at59 = Arrays.asList(at59);
		heightIsle18at59 = new HeightIsle(nr18at59, cornerMaxima);

		nr18at63 = Arrays.asList(at63);
		heightIsle18at63 = new HeightIsle(nr18at63, cornerMaxima);

		nr18at64 = Arrays.asList(at64);
		heightIsle18at64 = new HeightIsle(nr18at64, cornerMaxima);

		nr18at65 = Arrays.asList(at65);
		heightIsle18at65 = new HeightIsle(nr18at65, cornerMaxima);

		nr18at66 = Arrays.asList(at66);
		heightIsle18at66 = new HeightIsle(nr18at66, cornerMaxima);

		nr18at67 = Arrays.asList(at67);
		heightIsle18at67 = new HeightIsle(nr18at67, cornerMaxima);

		nr18at68 = Arrays.asList(at68);
		heightIsle18at68 = new HeightIsle(nr18at68, cornerMaxima);

	}

	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle18();
		}
		return instance;

	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {

	List<Integer> copy = new ArrayList<Integer>(isleSubs);

	List<Integer>  rasterIndizesDescribingTheIsle;

	for (int sub : copy) {

		if (sub > 68) return -1;

		switch (sub) {
		case 0:
			rasterIndizesDescribingTheIsle = nr18at00; break;
		case 1:
			rasterIndizesDescribingTheIsle = nr18at01; break;
		case 2:
			rasterIndizesDescribingTheIsle = nr18at02; break;
		case 3:
			rasterIndizesDescribingTheIsle = nr18at03; break;
		case 4:
			rasterIndizesDescribingTheIsle = nr18at04; break;
		case 5:
			rasterIndizesDescribingTheIsle = nr18at05; break;
		case 9:
			rasterIndizesDescribingTheIsle = nr18at09; break;
		case 10:
			rasterIndizesDescribingTheIsle = nr18at10; break;
		case 11:
			rasterIndizesDescribingTheIsle = nr18at11; break;
		case 12:
			rasterIndizesDescribingTheIsle = nr18at12; break;
		case 13:
			rasterIndizesDescribingTheIsle = nr18at13; break;
		case 14:
			rasterIndizesDescribingTheIsle = nr18at14; break;
		case 18:
			rasterIndizesDescribingTheIsle = nr18at18; break;
		case 19:
			rasterIndizesDescribingTheIsle = nr18at19; break;
		case 20:
			rasterIndizesDescribingTheIsle = nr18at20; break;
		case 21:
			rasterIndizesDescribingTheIsle = nr18at21; break;
		case 22:
			rasterIndizesDescribingTheIsle = nr18at22; break;
		case 23:
			rasterIndizesDescribingTheIsle = nr18at23; break;
		case 27:
			rasterIndizesDescribingTheIsle = nr18at27; break;
		case 28:
			rasterIndizesDescribingTheIsle = nr18at28; break;
		case 29:
			rasterIndizesDescribingTheIsle = nr18at29; break;
		case 30:
			rasterIndizesDescribingTheIsle = nr18at30; break;
		case 31:
			rasterIndizesDescribingTheIsle = nr18at31; break;
		case 32:
			rasterIndizesDescribingTheIsle = nr18at32; break;
		case 36:
			rasterIndizesDescribingTheIsle = nr18at36; break;
		case 37:
			rasterIndizesDescribingTheIsle = nr18at37; break;
		case 38:
			rasterIndizesDescribingTheIsle = nr18at38; break;
		case 39:
			rasterIndizesDescribingTheIsle = nr18at39; break;
		case 40:
			rasterIndizesDescribingTheIsle = nr18at40; break;
		case 41:
			rasterIndizesDescribingTheIsle = nr18at41; break;
		case 45:
			rasterIndizesDescribingTheIsle = nr18at45; break;
		case 46:
			rasterIndizesDescribingTheIsle = nr18at46; break;
		case 47:
			rasterIndizesDescribingTheIsle = nr18at47; break;
		case 48:
			rasterIndizesDescribingTheIsle = nr18at48; break;
		case 49:
			rasterIndizesDescribingTheIsle = nr18at49; break;
		case 50:
			rasterIndizesDescribingTheIsle = nr18at50; break;
		case 54:
			rasterIndizesDescribingTheIsle = nr18at54; break;
		case 55:
			rasterIndizesDescribingTheIsle = nr18at55; break;
		case 56:
			rasterIndizesDescribingTheIsle = nr18at56; break;
		case 57:
			rasterIndizesDescribingTheIsle = nr18at57; break;
		case 58:
			rasterIndizesDescribingTheIsle = nr18at58; break;
		case 59:
			rasterIndizesDescribingTheIsle = nr18at59; break;
		case 63:
			rasterIndizesDescribingTheIsle = nr18at63; break;
		case 64:
			rasterIndizesDescribingTheIsle = nr18at64; break;
		case 65:
			rasterIndizesDescribingTheIsle = nr18at65; break;
		case 66:
			rasterIndizesDescribingTheIsle = nr18at66; break;
		case 67:
			rasterIndizesDescribingTheIsle = nr18at67; break;
		case 68:
			rasterIndizesDescribingTheIsle = nr18at68; break;
		default:
			continue;
		}
		if (checkForMatch(copy, rasterIndizesDescribingTheIsle)) return sub;

	}

	@Override
	protected HeightIsle getIsleAtRasterIndex(int rasterIndex) {

		switch (rasterIndex) {
		case 0:
			return heightIsle18at00;
		case 1:
			return heightIsle18at01;
		case 2:
			return heightIsle18at02;
		case 3:
			return heightIsle18at03;
		case 4:
			return heightIsle18at04;
		case 5:
			return heightIsle18at05;
		case 9:
			return heightIsle18at09;
		case 10:
			return heightIsle18at10;
		case 11:
			return heightIsle18at11;
		case 12:
			return heightIsle18at12;
		case 13:
			return heightIsle18at13;
		case 14:
			return heightIsle18at14;
		case 18:
			return heightIsle18at18;
		case 19:
			return heightIsle18at19;
		case 20:
			return heightIsle18at20;
		case 21:
			return heightIsle18at21;
		case 22:
			return heightIsle18at22;
		case 23:
			return heightIsle18at23;
		case 27:
			return heightIsle18at27;
		case 28:
			return heightIsle18at28;
		case 29:
			return heightIsle18at29;
		case 30:
			return heightIsle18at30;
		case 31:
			return heightIsle18at31;
		case 32:
			return heightIsle18at32;
		case 36:
			return heightIsle18at36;
		case 37:
			return heightIsle18at37;
		case 38:
			return heightIsle18at38;
		case 39:
			return heightIsle18at39;
		case 40:
			return heightIsle18at40;
		case 41:
			return heightIsle18at41;
		case 45:
			return heightIsle18at45;
		case 46:
			return heightIsle18at46;
		case 47:
			return heightIsle18at47;
		case 48:
			return heightIsle18at48;
		case 49:
			return heightIsle18at49;
		case 50:
			return heightIsle18at50;
		case 54:
			return heightIsle18at54;
		case 55:
			return heightIsle18at55;
		case 56:
			return heightIsle18at56;
		case 57:
			return heightIsle18at57;
		case 58:
			return heightIsle18at58;
		case 59:
			return heightIsle18at59;
		case 63:
			return heightIsle18at63;
		case 64:
			return heightIsle18at64;
		case 65:
			return heightIsle18at65;
		case 66:
			return heightIsle18at66;
		case 67:
			return heightIsle18at67;
		case 68:
			return heightIsle18at68;
		default:
			return null;
		}
	}
}
