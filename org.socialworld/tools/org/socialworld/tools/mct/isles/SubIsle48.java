package org.socialworld.tools.mct.isles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.socialworld.tools.mct.HeightIsle;
import org.socialworld.tools.mct.SubIsle;

public class SubIsle48 extends SubIsle {
	
	private final static Integer at00[]  = {0,  1,  2,  3,  4,  5,  6, 
											9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51};
	
	private final static Integer at01[]  = {1,  2,  3,  4,  5,  6,  7,
											10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52};
	
	private final static Integer at02[]  = {2,  3,  4,  5,  6,  7,  8,
											11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53};
	
	private final static Integer at09[]  = {9,  10, 11, 12, 13, 14, 15,
											18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60};

	private final static Integer at10[]  = {10, 11, 12, 13, 14, 15, 16,
											19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61};

	private final static Integer at11[]  = {11, 12, 13, 14, 15, 16, 17,
											20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62};

	private final static Integer at18[]  = {18, 19, 20, 21, 22, 23, 24,
											27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69};

	private final static Integer at19[]  = {19, 20, 21, 22, 23, 24, 25,
											28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70};

	private final static Integer at20[]  = {20, 21, 22, 23, 24, 25, 26,
											29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71};
	
	private final static Integer at27[]  = {27, 28, 29, 30, 31, 32, 33,
											36, 37, 38, 39, 40, 41, 42,
											45, 46, 47, 48, 49, 50, 51,
											54, 55, 56, 57, 58, 59, 60,
											63, 64, 65, 66, 67, 68, 69,
											72, 73, 74, 75, 76, 77, 78};
	
	private final static Integer at28[]  = {28, 29, 30, 31, 32, 33, 34,
											37, 38, 39, 40, 41, 42, 43,
											46, 47, 48, 49, 50, 51, 52,
											55, 56, 57, 58, 59, 60, 61,
											64, 65, 66, 67, 68, 69, 70,
											73, 74, 75, 76, 77, 78, 79};
	
	private final static Integer at29[]  = {29, 30, 31, 32, 33, 34, 35,
											38, 39, 40, 41, 42, 43, 44,
											47, 48, 49, 50, 51, 52, 53,
											56, 57, 58, 59, 60, 61, 62,
											65, 66, 67, 68, 69, 70, 71,
											74, 75, 76, 77, 78, 79, 80};
	
	private List<Integer> nr48at00;
	private HeightIsle heightIsle48at00;

	private List<Integer> nr48at01;
	private HeightIsle heightIsle48at01;

	private List<Integer> nr48at02;
	private HeightIsle heightIsle48at02;

	private List<Integer> nr48at09;
	private HeightIsle heightIsle48at09;

	private List<Integer> nr48at10;
	private HeightIsle heightIsle48at10;

	private List<Integer> nr48at11;
	private HeightIsle heightIsle48at11;

	private List<Integer> nr48at18;
	private HeightIsle heightIsle48at18;

	private List<Integer> nr48at19;
	private HeightIsle heightIsle48at19;

	private List<Integer> nr48at20;
	private HeightIsle heightIsle48at20;
	
	private List<Integer> nr48at27;
	private HeightIsle heightIsle48at27;

	private List<Integer> nr48at28;
	private HeightIsle heightIsle48at28;

	private List<Integer> nr48at29;
	private HeightIsle heightIsle48at29;
	
	private final static Integer[] cornerMaximaNrs = 
		{
			11914, 11994, 11994, 11994, 11994, 11994, 11194,	
			19914, 11914, 11994, 11994, 11994, 11194, 91194,
			19914, 19914, 11914, 11994, 11194, 91194, 91194,
			19914, 19914, 19914, 11114, 91194, 91194, 91194,
			19914, 19914, 19114, 99114, 91114, 91194, 91194,
			19914, 19114, 99114, 99114, 99114, 91114, 91194
		};
	
	private List<Integer> cornerMaxima;

	private SubIsle48() {
		
cornerMaxima = Arrays.asList(cornerMaximaNrs);
		
		nr48at00 = Arrays.asList(at00);
		heightIsle48at00 = new HeightIsle(nr48at00, cornerMaxima);

		nr48at01 = Arrays.asList(at01);
		heightIsle48at01 = new HeightIsle(nr48at01, cornerMaxima);

		nr48at02 = Arrays.asList(at02);
		heightIsle48at02 = new HeightIsle(nr48at02, cornerMaxima);

		nr48at09 = Arrays.asList(at09);
		heightIsle48at09 = new HeightIsle(nr48at09, cornerMaxima);

		nr48at10 = Arrays.asList(at10);
		heightIsle48at10 = new HeightIsle(nr48at10, cornerMaxima);

		nr48at11 = Arrays.asList(at11);
		heightIsle48at11 = new HeightIsle(nr48at11, cornerMaxima);

		nr48at18 = Arrays.asList(at18);
		heightIsle48at18 = new HeightIsle(nr48at18, cornerMaxima);

		nr48at19 = Arrays.asList(at19);
		heightIsle48at19 = new HeightIsle(nr48at19, cornerMaxima);

		nr48at20 = Arrays.asList(at20);
		heightIsle48at20 = new HeightIsle(nr48at20, cornerMaxima);
		
		nr48at27 = Arrays.asList(at27);
		heightIsle48at27 = new HeightIsle(nr48at27, cornerMaxima);

		nr48at28 = Arrays.asList(at28);
		heightIsle48at28 = new HeightIsle(nr48at28, cornerMaxima);

		nr48at29 = Arrays.asList(at29);
		heightIsle48at29 = new HeightIsle(nr48at29, cornerMaxima);
		
	}
	
	public static SubIsle getInstance() {
		if (instance == null) {
			instance = new SubIsle48();
		}
		return instance;
		
	}

	@Override
	protected int checkForIsle(List<Integer> isleSubs) {
		
		List<Integer> copy = new ArrayList<Integer>(isleSubs);
		
		List<Integer>  rasterIndizesDescribingTheIsle;
		
		for (int sub : copy) {
			
			if (sub > 29) return -1;
			
			switch (sub) {
			case 0:
				rasterIndizesDescribingTheIsle = nr48at00; break;
			case 1:
				rasterIndizesDescribingTheIsle = nr48at01; break;
			case 2:
				rasterIndizesDescribingTheIsle = nr48at02; break;
			case 9:
				rasterIndizesDescribingTheIsle = nr48at09; break;
			case 10:
				rasterIndizesDescribingTheIsle = nr48at10; break;
			case 11:
				rasterIndizesDescribingTheIsle = nr48at11; break;
			case 18:
				rasterIndizesDescribingTheIsle = nr48at18; break;
			case 19:
				rasterIndizesDescribingTheIsle = nr48at19; break;
			case 20:
				rasterIndizesDescribingTheIsle = nr48at20; break;
			case 27:
				rasterIndizesDescribingTheIsle = nr48at27; break;
			case 28:
				rasterIndizesDescribingTheIsle = nr48at28; break;
			case 29:
				rasterIndizesDescribingTheIsle = nr48at29; break;
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
			return heightIsle48at00;
		case 1:
			return heightIsle48at01;
		case 2:
			return heightIsle48at02;
		case 9:
			return heightIsle48at09;
		case 10:
			return heightIsle48at10;
		case 11:
			return heightIsle48at11;
		case 18:
			return heightIsle48at18;
		case 19:
			return heightIsle48at19;
		case 20:
			return heightIsle48at20;
		case 27:
			return heightIsle48at27;
		case 28:
			return heightIsle48at28;
		case 29:
			return heightIsle48at29;
		default:
			return null;
		}
	}

}
