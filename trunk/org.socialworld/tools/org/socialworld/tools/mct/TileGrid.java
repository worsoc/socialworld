/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author Mathias Sikos
 *
 * The class TileGrid describes a 9x9-matrix of tiles.
 * Because of describing the map in different levels, a tile grid is a tile itself on the higher level.
 * There are supported 3 tile type levels:
 * large , medium and small
 * A large tile grid contains of large tiles or medium sub tile grids.
 * A medium tile grid contains of medium tiles or small sub tile grids.
 * A small tile grid contains of 81 small tiles. There aren't sub tile grid on small tile type level.
 * 
 * For the set of tiles that could be arranged in tile raster see class Tile.
 * 
 * While arranging or creating a raster filling there must be met edge and height conditions.
 * See the classes CalculatePossibleTiles, PossibleTiles and HeightChangeChecker for the implementation of the neighbourhood rules.
 * 
 * If there are tiles with different tile type levels in the same raster there must be met special edge conditions from a tile to a sub tile grid.
 * These edge conditions are solved by border adapters. That is a set of 9 special tiles that are arranged at the 9 border raster fields in north, east, south or west.
 * A border adapter on a sub tile grid manages that an inclination or a declination matches to the inclination/declination of the neighboured tile (with higher tile type level).
 * A border adapter arranges (for example) 9 small tiles next to one medium tile.
 * 
 * Furthermore a tile grid has a describing value, how the height is allowed to change over the complete grid.
 * This value is the integer variable cornerMaximaNr. 
 * It contains of 5 digits, that define the allowed height difference for
 *  the corner north west (first digit)
 *  the corner north east (second digit)
 *  the corner south east (third digit)
 *  the corner south west (forth digit)
 *  the center tile (fifth digit)
 *  The default cornerMaximaNr is 11114 and means that height may change by a difference of 4 from the corner tiles to the center tile.
 *  The allowed difference is always given as positive digit, but means positive or negative (absolute value).
 *  That means the value 11114 allows the center grid to lay 4 units higher or lower.
 *  
 *  There are different fill orders. The concrete tile grid fill order depends on whether and where are arranged adapter borders.
 *  The concrete fill order is given to the variable fillOrder. 
 *  That is an array that is iterated from 0 to to 80 an gives for every fill index the assigned tile grid raster index.
 *  
*/
public class TileGrid extends Tile {

	// the 9x9 matrix or 9x9 raster
	Tile tiles[];
	// a blocked raster element isn't allowed to be reseted in backtracking algorithms
	boolean blocked[] = new boolean[81];
	// the index of the tile raster, that will be calculated/generated currently
	int actualTileIndex = 0;
	// the reference to the parent tile grid 
	private TileGrid parent;

	// is set to true, if all raster elements are filled
	boolean isComplete = false;
	
	int tileTypeLevel; 
	// --> see class TileType
	// static final int TILE_LEVEL_LARGE = 0;
	// static final int TILE_LEVEL_MEDIUM = 1;
	// static final int TILE_LEVEL_SMALL = 2;

	// a string that is used for generation
	String inputForGeneration;
	// a list of integer values that a calculated from input string
	List<Integer> totalValuesForGeneration;
	int borderValueForGeneration = 0;
	
	int cornerMaximaNr = 11114;
	private TileGridBorderAdapterType borderAdapterTypeNorth = TileGridBorderAdapterType.noAdapter;
	private TileGridBorderAdapterType borderAdapterTypeEast = TileGridBorderAdapterType.noAdapter;
	private TileGridBorderAdapterType borderAdapterTypeSouth = TileGridBorderAdapterType.noAdapter;
	private TileGridBorderAdapterType borderAdapterTypeWest = TileGridBorderAdapterType.noAdapter;
	
	// filling the raster
	private static int fillOrderFromOutToIn[];
	private int fillOrder[];
	int fillIndex = -1;
	
	SubClusterCalculations subClusterCalculation = new SubClusterCalculations();
	
	HeightChangeChecker heightChangeChecker = HeightChangeChecker.getInstance();

	TileGrid(Tile parent) {
		super( TileType.sub);
		this.parent = (TileGrid) parent;
		tiles = new Tile[81];
	}
	
	TileGrid(Tile parent, int tileTypeLevel, int borderValue) {
		super( TileType.sub);
		this.parent = (TileGrid) parent;
		this.tileTypeLevel = tileTypeLevel;
		this.borderValueForGeneration = borderValue;
	}
	
	TileGrid(String input, int tileTypeLevel) {
		
		super(TileType.sub);
		this.tileTypeLevel = tileTypeLevel;
		this.inputForGeneration = input;
		while (this.inputForGeneration.length() < 300) {
			this.inputForGeneration = this.inputForGeneration + input;
		}
		
		String inputRest;
		inputRest = createFromString();
		
		
	}
	
	void setCornerMaximaNr(int cornerMaximaNr) {
		this.cornerMaximaNr = cornerMaximaNr;
	}
	
	void setBorderAdapterTypeNorth(TileGridBorderAdapterType type) {
		this.borderAdapterTypeNorth = type;
	}

	void setBorderAdapterTypeEast(TileGridBorderAdapterType type) {
		this.borderAdapterTypeEast = type;
	}

	void setBorderAdapterTypeSouth(TileGridBorderAdapterType type) {
		this.borderAdapterTypeSouth = type;
	}

	void setBorderAdapterTypeWest(TileGridBorderAdapterType type) {
		this.borderAdapterTypeWest = type;
	}

	private void setInputForGeneration(String input) {
		this.inputForGeneration = input;
	}
	
	private void initWithTodo() {
		int index;
		int index2;
		this.tiles = new Tile[81];
		
		for (index = 0; index < 81; index++) {
			addTile(Tile.getInstance(), index);
			blocked[index] = false;
		}

		// TEMP_SOLUTION
		// init outer raster fields with plane tiles
		for (index = 0; index < 9; index++) {
			addTile(
				new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel) , 0)	, heightLevel),
				index);
			addTile(
				new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel) , 0)	, heightLevel),
				index + 72);
			addTile(
				new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel) , 0)	, heightLevel),
				index * 9);
			addTile(
				new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel) , 0)	, heightLevel),
				index * 9 + 8);
		}
		
		
		
		if (fillOrderFromOutToIn == null) {
			fillOrderFromOutToIn = new int[81];
			for (index= 0; index < 9; index++) {
				fillOrderFromOutToIn[index] = index;
				fillOrderFromOutToIn[index + 9] = index + 72;
			}
			for (index= 0; index < 7; index++) {
				fillOrderFromOutToIn[index + 18] = (index + 1)  * 9;
				fillOrderFromOutToIn[index + 25] = (index + 1) * 9 + 8;
			}

			fillOrderFromOutToIn[32] = 10;
			fillOrderFromOutToIn[33] = 70;
			fillOrderFromOutToIn[34] = 16;
			fillOrderFromOutToIn[35] = 64;
			fillOrderFromOutToIn[36] = 11;
			fillOrderFromOutToIn[37] = 19;
			fillOrderFromOutToIn[38] = 61;
			fillOrderFromOutToIn[39] = 69;
			fillOrderFromOutToIn[40] = 15;
			fillOrderFromOutToIn[41] = 25;
			fillOrderFromOutToIn[42] = 55;
			fillOrderFromOutToIn[43] = 65;
			
			fillOrderFromOutToIn[44] = 12;
			fillOrderFromOutToIn[45] = 28;
			fillOrderFromOutToIn[46] = 20;
			fillOrderFromOutToIn[47] = 52;
			fillOrderFromOutToIn[48] = 68;
			fillOrderFromOutToIn[49] = 60;
			fillOrderFromOutToIn[50] = 14;
			fillOrderFromOutToIn[51] = 34;
			fillOrderFromOutToIn[52] = 24;
			fillOrderFromOutToIn[53] = 46;
			fillOrderFromOutToIn[54] = 66;
			fillOrderFromOutToIn[55] = 56;
			
			fillOrderFromOutToIn[56] = 13;
			fillOrderFromOutToIn[57] = 37;
			fillOrderFromOutToIn[58] = 21;
			fillOrderFromOutToIn[59] = 29;
			fillOrderFromOutToIn[60] = 43;
			fillOrderFromOutToIn[61] = 67;
			fillOrderFromOutToIn[62] = 51;
			fillOrderFromOutToIn[63] = 59;
			fillOrderFromOutToIn[64] = 23;
			fillOrderFromOutToIn[65] = 33;
			fillOrderFromOutToIn[66] = 47;
			fillOrderFromOutToIn[67] = 57;
			
			fillOrderFromOutToIn[68] = 22;
			fillOrderFromOutToIn[69] = 38;
			fillOrderFromOutToIn[70] = 30;
			fillOrderFromOutToIn[71] = 42;
			fillOrderFromOutToIn[72] = 58;
			fillOrderFromOutToIn[73] = 50;
			fillOrderFromOutToIn[74] = 32;
			fillOrderFromOutToIn[75] = 48;
			fillOrderFromOutToIn[76] = 31;
			fillOrderFromOutToIn[77] = 39;
			fillOrderFromOutToIn[78] = 41;
			fillOrderFromOutToIn[79] = 49;
			fillOrderFromOutToIn[80] = 40;
			
		}

	}
	
										

	
	
	private String createFromString() {

		String restFromInput;


		if (tileTypeLevel == TileType.TILE_LEVEL_LARGE) {
			char firstChar = inputForGeneration.charAt(0);
			int heigtValue = (int) firstChar;
			int heightLevel;
			heightLevel = heigtValue % 81; 
			setHeightLevel(heightLevel);
		}	
/*		
		switch (tileTypeLevel) {
		case TileType.TILE_LEVEL_LARGE:
			heightLevel = heigtValue % 81; 
			break;
		case TileType.TILE_LEVEL_MEDIUM:
			heightLevel = heigtValue % 9;
			 break;
		default:
			heightLevel = 0;
		}
		setHeightLevel(heightLevel);
*/	
		initWithTodo();
		setBorders();
		
		for (int index = 0; index < 81; index++) {
			if (tiles[index].getType() != TileType.todo) {
				blocked[index] = true;
			}
		}

		restFromInput = createTotalValuesList();
		
		int valueWithOffset;
		int offset;
		int value;
		Set<Integer> possibleTilesSet;
		List<Integer> possibleTilesArray;
		int numberOfPossibleTiles;
		int choosenTile;
		
		int IndexRepeatedStartAfterError = 0;
		int indexValueList;
		for ( indexValueList = 0; indexValueList < totalValuesForGeneration.size(); indexValueList++) {
			
			valueWithOffset = totalValuesForGeneration.get(indexValueList);
			
//			if (tileTypeLevel == 2) {
//				int mybreak;
//				mybreak = 1;
//			}
			if (valueWithOffset > 100000000) {
				possibleTilesSet = getPossibleTiles();
				numberOfPossibleTiles = possibleTilesSet.size(); 
				if (numberOfPossibleTiles > 0) {
				    possibleTilesArray = new ArrayList<Integer>(numberOfPossibleTiles); 
				    for (Integer x : possibleTilesSet) 
				    	possibleTilesArray.add(x); 
					value = valueWithOffset - 100000000;
					choosenTile = possibleTilesArray.get(value % numberOfPossibleTiles);
					choosenTile = TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel), choosenTile);
					setSubGrid(valueWithOffset, choosenTile);
				}
				else {
//					System.out.println("setError for big valueWithOffset (tileTypeLevel = " + tileTypeLevel + ")");
					setError();
//					setSubGrid(valueWithOffset);
				}
			}
			else {
				offset = TileType.getTileTypeOffset(TileType.getTileType(valueWithOffset));
				value = valueWithOffset - offset;
				possibleTilesSet = getPossibleTiles();
				numberOfPossibleTiles = possibleTilesSet.size(); 
				if (numberOfPossibleTiles > 0) {
				    possibleTilesArray = new ArrayList<Integer>(numberOfPossibleTiles); 
				    for (Integer x : possibleTilesSet) 
				    	possibleTilesArray.add(x); 
					choosenTile = possibleTilesArray.get(value % numberOfPossibleTiles);
					setTile(choosenTile + offset);
				}
				else {
//					System.out.println("setError for small valueWithOffset (tileTypeLevel = " + tileTypeLevel + ")");
					setError();
				}
			}
			
			
			
			if (isComplete) {
//				System.out.println("Index Valuelist: " + indexValueList + " / " + totalValuesForGeneration.size() );
					break;
			}

			if (indexValueList == (totalValuesForGeneration.size() - 1) && !isComplete) {
				indexValueList = IndexRepeatedStartAfterError;
				IndexRepeatedStartAfterError++;
//				indexValueList = 0;
			}
		}

		for (int index_ = 0; index_ < 81; index_++) {
			if (tiles[index_].globalNumber == -2) {
				System.out.println("Index Valuelist: " + indexValueList + " Index Raster: " + index_ + ": " + tiles[index_].globalNumber + ", " + IndexRepeatedStartAfterError + " / " + totalValuesForGeneration.size() );
				isComplete = false;
			}
		}

		subClusterCalculation.initWithTiles(this.tiles);
		subClusterCalculation.setBorderAdapters();
		
		setHeights();
		
		int inputLength;
		inputLength = inputForGeneration.length();
		restFromInput = restFromInput + inputForGeneration;
		restFromInput = restFromInput.substring(0, inputLength);
		String inputRest = restFromInput.substring(0);


		TileGrid sub;

		for (int index = 0; index < 81; index++) {
			if (this.tiles[index].getType() == TileType.sub) {
				sub = (TileGrid) this.tiles[index];
				System.out.println(tileTypeLevel + ": " + index);
				 sub.setInputForGeneration(inputRest);
				 inputRest = sub.createFromString();
			}
		}

		return restFromInput;
	}

	private void setSubGrid(int borderValue, int feelAsGridNumber) {
		addTile(new TileGrid(this, this.tileTypeLevel + 1, borderValue), actualTileIndex);
		tiles[actualTileIndex].setGlobalNumber(feelAsGridNumber);
		setNextToDoActualIndex();
	}

	private void setSubGrid(int borderValue) {
		addTile(new TileGrid(this, this.tileTypeLevel + 1, borderValue), actualTileIndex);
		setNextToDoActualIndex();
	}
	
	private void setTile(int globalTileNumber) {
		addTile(new Tile(globalTileNumber, heightLevel), actualTileIndex);
		setNextToDoActualIndex();
	}
	
	private void setError() {
		
//		if (fillDirection == 1) {
			if (actualTileIndex > 8) {
				addTile(Tile.getInstance(), actualTileIndex - 9);
				if ((actualTileIndex % 9) > 0) {
					addTile(Tile.getInstance(), actualTileIndex - 10);
				}
				if ((actualTileIndex % 9) > 1) {
					addTile(Tile.getInstance(), actualTileIndex - 11);
				}
				if ((actualTileIndex % 9) < 8) {
					addTile(Tile.getInstance(), actualTileIndex - 8);
				}
				if ((actualTileIndex % 9) < 7) {
					addTile(Tile.getInstance(), actualTileIndex - 7);
				}
			}
			if ((actualTileIndex % 9) > 0) {
				addTile(Tile.getInstance(), actualTileIndex - 1);
			}
			
			if (actualTileIndex > 17) {
				if ((actualTileIndex % 9) > 1) {
					addTile(Tile.getInstance(), actualTileIndex - 20);
					addTile(Tile.getInstance(), actualTileIndex - 19);
					addTile(Tile.getInstance(), actualTileIndex - 18);
				}
				if ((actualTileIndex % 9) < 7) {
					addTile(Tile.getInstance(), actualTileIndex - 17);
					addTile(Tile.getInstance(), actualTileIndex - 16);
				}
			}
			if ((actualTileIndex % 9) > 1) {
				addTile(Tile.getInstance(), actualTileIndex - 2);
			}
		
//		}
//		else if (fillDirection == 2) {
			if (actualTileIndex < 72) {
				addTile(Tile.getInstance(), actualTileIndex + 9);
				if ((actualTileIndex % 9) > 0) {
					addTile(Tile.getInstance(), actualTileIndex + 8);
				}
				if ((actualTileIndex % 9) > 1) {
					addTile(Tile.getInstance(), actualTileIndex + 7);
				}
				if ((actualTileIndex % 9) < 8) {
					addTile(Tile.getInstance(), actualTileIndex + 10);
				}
				if ((actualTileIndex % 9) < 7) {
					addTile(Tile.getInstance(), actualTileIndex + 11);
				}
			}
			if ((actualTileIndex % 9) < 8) {
				addTile(Tile.getInstance(), actualTileIndex + 1);
			}
			
			if (actualTileIndex < 62) {
				if ((actualTileIndex % 9) > 1) {
					addTile(Tile.getInstance(), actualTileIndex + 16);
					addTile(Tile.getInstance(), actualTileIndex + 17);
					addTile(Tile.getInstance(), actualTileIndex + 18);
				}
				if ((actualTileIndex % 9) < 7) {
					addTile(Tile.getInstance(), actualTileIndex + 19);
					addTile(Tile.getInstance(), actualTileIndex + 20);
				}
			}
			if ((actualTileIndex % 9) < 7) {
				addTile(Tile.getInstance(), actualTileIndex + 2);
			}

//		}
		
		// start from first index
		fillIndex = -1;
		setNextToDoActualIndex();
	}

	private void setNextToDoActualIndex() {
		fillIndex++;
		if (fillIndex >= 81) {
			isComplete = true;
/*
			if (tileTypeLevel == 2) {
				for (int index = 0; index < 81; index++) {
					if (tiles[index].globalNumber == -2) {
						addTile(Tile.getInstance(), index);
						isComplete = false;
					}
				}
				if (isComplete == false) {
					// start from beginning again
					fillIndex = -1;
					setNextToDoActualIndex();
				}
			}
*/
		}
		else {
			actualTileIndex = fillOrder[fillIndex];
			while (tiles[actualTileIndex].getType() != TileType.todo ){
				fillIndex++;
				if (fillIndex == 81) {
					isComplete = true;
					break;
				}
				actualTileIndex = fillOrder[fillIndex];
			}
		}
		
	}
	
//	private int rasterIndexForActualTileIndex()
	void setHeights() {
		
		int heightFromWest;
		int heightFromNorth;
		int height;
		
		for (int i = 0; i < 81; i++) {
			
			if ((i % 9) < 8) {
				// check height increase/decrease to east neighbour
				
					
					if (heightChangeChecker.isEastIncrease(tiles[i], tiles[i+1])) {
						tiles[i+1].heightOffsetFromWest = 1;
					}
					else if (heightChangeChecker.isEastDecrease(tiles[i], tiles[i+1])) {
						tiles[i+1].heightOffsetFromWest = -1;
					}
					else {
						tiles[i+1].heightOffsetFromWest = 0;
					}

			}
			
			if (i < 72) {
				// check height increase/decrease to south neighbour

					
					if (heightChangeChecker.isSouthIncrease(tiles[i], tiles[i+9])) {
						tiles[i+9].heightOffsetFromNorth = 1;
					}
					else if (heightChangeChecker.isSouthDecrease(tiles[i], tiles[i+9])) {
						tiles[i+9].heightOffsetFromNorth = -1;
					}
					else {
						tiles[i+9].heightOffsetFromNorth = 0;
					}
			
			}
			
		}
		
		for (int i = 0; i < 81; i++) {
			heightFromWest = -888;
			heightFromNorth = -777;
			if ((i % 9) > 0) {
				heightFromWest =  tiles[i-1].heightLevel + tiles[i].heightOffsetFromWest;
			}
			if (i > 8) {
				heightFromNorth =  tiles[i-9].heightLevel + tiles[i].heightOffsetFromNorth;
			}
			if ((heightFromWest == -888) && (heightFromNorth == -777) ) height = heightLevel;
			else if (heightFromNorth == -777) height =  heightFromWest;
			else if (heightFromWest == -888) height =  heightFromNorth;
			else {
				
				if (heightFromWest == heightFromNorth) height = heightFromWest;
				else {
					height = -999;
//					System.out.println("Fehler Höhenbestimmung bei Index: " + i + " --> Höhe von oben: " + heightFromNorth + " und Höhe von links: " + heightFromWest);
				}

			}
			
			tiles[i].heightLevel = height;
//			System.out.println("Level " + tileTypeLevel + " : Index " + i + " mit Höhe " + height);
			
		}
			
			
		
		
	}
	
	
	private Set<Integer> getPossibleTiles() {
		
		
		int index = actualTileIndex;
		int indexNorth;
		int indexEast;
		int indexSouth;
		int indexWest;
		
		if ((index % 9) > 0)	indexWest    = index - 1;
		else 			indexWest = -1;

		if ((index % 9) < 8)	indexEast    = index + 1;
		else 			indexEast = -1;

		if (index > 8)	indexNorth    = index - 9;
		else 			indexNorth = -1;
	
		if (index < 72)	indexSouth    = index + 9;
		else 			indexSouth = -1;

		Tile tileFromEast;
		if (indexEast >= 0) {
			tileFromEast = tiles[indexEast];
		}
		else {
			tileFromEast = Tile.getInstance();
		}
	
		Tile tileFromWest; 
		if (indexWest >= 0) {
			tileFromWest = tiles[indexWest];
		}
		else {
			tileFromWest = Tile.getInstance();
		}

		Tile tileFromNorth; 
		if (indexNorth >= 0) {
			tileFromNorth = tiles[indexNorth];
		}
		else {
			tileFromNorth = Tile.getInstance();
		}
		
		Tile tileFromSouth;
		if (indexSouth >= 0) {
			tileFromSouth = tiles[indexSouth];
		}
		else {
			tileFromSouth = Tile.getInstance();
		}
		
		
		Set<Integer> reducedSet = CalculatePossibleTiles.getReducedSet(
				TileType.getTileTypeForLevel(tileTypeLevel), 1, 
				 tileFromEast,  tileFromWest,  tileFromNorth,  tileFromSouth) ;
		
		
		return reducedSet;
	}
	
	
	
	TileType getTileType(int index) {
		if (tiles[index] != null) 
			return tiles[index].getType();
		else
			return TileType.todo;  
	}

	int getTileTypeAlternative(int index) {
		if (tiles[index] != null) 
			return tiles[index].getTileTypeAlternative();
		else
			return 0;  
	}
	
	
	int getTileNumber(int index) {
		if (tiles[index] != null) 
			return tiles[index].getNumber();
		else
			return -2;  
	}
	
	int getHeightLevel(int index) {
		if (tiles[index] != null) 
			return tiles[index].getHeightLevel();
		else
			return 0;
	}
	
	TileGrid getChildGrid(int index) {
		TileGrid returnTile;
		
		
		if ( (tiles[index] != null) && (tiles[index].getType() == TileType.sub) )
			returnTile = (TileGrid) tiles[index];
		else
			returnTile = new TileGrid(this);
		
		return returnTile;
	}
	
	TileGrid getParentGrid() {
		return parent;
	}
	
	void addTile(Tile tile, int index) {
		if (blocked[index] == false) {
			tiles[index] = tile;
		}
	}

	void setSub(int index) {
		tiles[index].setSub();
	}
	
	
	
///////////////////////////////////////////////////////////////////////////////
	
	private class Input  {
		
		private String input;
		private int index = 0;
		private int length;
		
		private RandomRasterIndexOrder  rrio = RandomRasterIndexOrder.getInstance();
		
		private Input(String input) {
			this.input = input;
			this.length = input.length();
		}
		
		private String getNextThreePack() {
			String result;
			if ((index + 3) > length) return "";
			result = input.substring(index, index + 3);
			index = index + 3;
			return result;
		}
		
		private String getRest( ) {
			return input.substring(index);
		}
		
		private int checkForZoomIn(String threePack, int tileTypeLevel) {

			int result = 0;
			if (threePack.length() != 3) return result;
			if (tileTypeLevel == 2) return result;
			
			char[] signs = threePack.toCharArray();
			int[] numbers = new int[3];
			String[] binaer = new String[3];
						
			numbers[0] = (int) signs[0];
			numbers[1] = (int) signs[1];
			numbers[2] = (int) signs[2];
			
			binaer[0] = String.format("%8s", Integer.toBinaryString(numbers[0])).replace(' ', '0');
			binaer[1] = String.format("%8s", Integer.toBinaryString(numbers[1])).replace(' ', '0');
			binaer[2] = String.format("%8s", Integer.toBinaryString(numbers[2])).replace(' ', '0');
			
			int count1_in_binaer1 = count1(binaer[0]);
			int count1_in_binaer2 = count1(binaer[1]);
			int count1_in_binaer3 = count1(binaer[2]);
			int count1 = count1_in_binaer1 + count1_in_binaer2 + count1_in_binaer3;
			
			if (tileTypeLevel == 0) {
				
				if (count1 > 8 && count1 < 16) {
					
					if (count1_in_binaer1 >= 4) {
						result += 4;
					}
					if (count1_in_binaer2 >= 4) {
						result += 2;
					}
					if (count1_in_binaer3 >= 4) {
						result += 1;
					}
					
				}
				
			}
			else {
				
				if (count1 > 9 && count1 < 15) {
					
					if (count1_in_binaer1 >= 4) {
						result += 4;
					}
					if (count1_in_binaer2 >= 4) {
						result += 2;
					}
					if (count1_in_binaer3 >= 4) {
						result += 1;
					}
					
				}
				
			}
			
			return result;
		}
		
		

		private List<Integer> get1Value(String threePack, int variante) {
			
			List<Integer> result = new ArrayList<Integer>();
			
			if (threePack.length() != 3) return result;
			
			String bigBinaer = "000000000000000000000000";
			int bigInt;
			
			bigBinaer = getBigBinaer(threePack, variante);
			bigInt = Integer.parseInt(bigBinaer, 2) + 100000000;
			
			result.add(bigInt);
			
			return result;
			
		}
		
		private List<Integer> get4Values(String threePack) {

			List<Integer> result = new ArrayList<Integer>();

			if (threePack.length() != 3) return result;
			
			TileType typeNew;
			String bigBinaer = "000000000000000000000000";
			String smallBinaer;
			int smallInt;
			
			bigBinaer = getBigBinaer(threePack, 1);
			
			//for (int index = 0; index < 4; index++) {
				int index  = rrio.getRandomInt(0, 3);
				smallBinaer = bigBinaer.substring(6*index, 6*index + 6);
				smallInt = Integer.parseInt(smallBinaer, 2);
				typeNew = TileType.getTileTypeForLevelAndRandom(tileTypeLevel, smallInt);
				result.add(TileType.addGlobalNumberOffset(typeNew, smallInt) );
			//}
			
			return result;
		}
		
		private List<Integer> get6Values(String threePack) {

			List<Integer> result = new ArrayList<Integer>();

			if (threePack.length() != 3) return result;
			
			TileType typeNew;
			String bigBinaer = "000000000000000000000000";
			String smallBinaer;
			int smallInt;
			
			bigBinaer = getBigBinaer(threePack, 1);
			
//			for (int index = 0; index < 6; index++) {
				int index  = rrio.getRandomInt(0, 5);
				smallBinaer = bigBinaer.substring(4*index, 4*index + 4);
				smallInt = Integer.parseInt(smallBinaer, 2);
				typeNew = TileType.getTileTypeForLevelAndRandom(tileTypeLevel, smallInt);
				result.add(TileType.addGlobalNumberOffset(typeNew, smallInt) );
//			}
			
			return result;
		}

		private List<Integer> get8Values(String threePack) {

			List<Integer> result = new ArrayList<Integer>();

			if (threePack.length() != 3) return result;

			TileType typeNew;
			String bigBinaer = "000000000000000000000000";
			String smallBinaer;
			int smallInt;
			
			bigBinaer = getBigBinaer(threePack, 1);
			
//			for (int index = 0; index < 8; index++) {
				int index  = rrio.getRandomInt(0, 7);
				smallBinaer = bigBinaer.substring(3*index, 3*index + 3);
				smallInt = Integer.parseInt(smallBinaer, 2);
				typeNew = TileType.getTileTypeForLevelAndRandom(tileTypeLevel, smallInt);
				result.add(TileType.addGlobalNumberOffset(typeNew, smallInt) );
//			}
			
			return result;
		}

		private String getBigBinaer(String threePack, int variante) {
			if (threePack.length() != 3) return "";
			
			char[] signs = threePack.toCharArray();
			int[] numbers = new int[3];
			String[] binaer = new String[3];
			
			String bigBinaer = "000000000000000000000000";
			
			numbers[0] = (int) signs[0];
			numbers[1] = (int) signs[1];
			numbers[2] = (int) signs[2];
			
			binaer[0] = String.format("%8s", Integer.toBinaryString(numbers[0])).replace(' ', '0');
			binaer[1] = String.format("%8s", Integer.toBinaryString(numbers[1])).replace(' ', '0');
			binaer[2] = String.format("%8s", Integer.toBinaryString(numbers[2])).replace(' ', '0');

			switch (variante) {
				case 1:
					bigBinaer = binaer[0] + binaer[1] + binaer[2]; break;
				case 2:
					bigBinaer = binaer[1] + binaer[2] + binaer[0]; break;
				case 3:
					bigBinaer = binaer[2] + binaer[0] + binaer[1]; break;
			}
			
			return bigBinaer;

		}
		
		private int count1(String binaer) {
			int result = 0;
			for (int i = 0; i < binaer.length(); i++) {
				if (binaer.charAt(i) == '1') {
					result++;
				}
			}
			return result;
		}

	}

	
///////////////////////////////////////////////////////////////////////////////
	
	
	private enum ThreePackPosition {
		left, center, right;
	}
	
	private String  createTotalValuesList() {

		List<Integer> valuesTotal = new ArrayList<Integer>();
		List<Integer> valuesPart;

		Input input = new Input(inputForGeneration);
		
		int numberOfChars = inputForGeneration.length();
		int numberThreePacks = (int) numberOfChars / 3;
		
		String threePack;
		int nrZoomIn;
		int valueZoomIn;
		boolean twoValues;
		
		
		int levelGeneration = tileTypeLevel;
		
		

		
		for (int indexThreepack = 0; indexThreepack < numberThreePacks; indexThreepack++) {
			
			threePack = input.getNextThreePack();
			if (threePack.equals("")) break;
			
			if (levelGeneration == 0 && valuesTotal.size() > 81 ) {
				levelGeneration = 1;
			}
			
			if (levelGeneration < 2) {
				
				nrZoomIn = input.checkForZoomIn(threePack, levelGeneration);
				if (nrZoomIn > 0) {
					
					switch(nrZoomIn) {
					case 1: 
						valuesPart = input.get1Value(threePack, 3);
						valuesTotal.addAll(valuesPart);
						break;
					case 2: 
						valuesPart = input.get1Value(threePack, 2);
						valuesTotal.addAll(valuesPart);
						break;
					case 3: 
						valuesPart = input.get1Value(threePack, 2);
						valuesTotal.addAll(valuesPart);
						valuesPart = input.get1Value(threePack, 3);
						valuesTotal.addAll(valuesPart);
						break;
					case 4: 
						valuesPart = input.get1Value(threePack, 1);
						valuesTotal.addAll(valuesPart);
						break;
					case 5: 
						valuesPart = input.get1Value(threePack, 1);
						valuesTotal.addAll(valuesPart);
						valuesPart = input.get1Value(threePack, 3);
						valuesTotal.addAll(valuesPart);
						break;
					case 6: 
						valuesPart = input.get1Value(threePack, 1);
						valuesTotal.addAll(valuesPart);
						valuesPart = input.get1Value(threePack, 2);
						valuesTotal.addAll(valuesPart);
						break;
					case 7: 
						valuesPart = input.get1Value(threePack, 1);
						valuesTotal.addAll(valuesPart);
						valuesPart = input.get1Value(threePack, 2);
						valuesTotal.addAll(valuesPart);
						valuesPart = input.get1Value(threePack, 3);
						valuesTotal.addAll(valuesPart);
						break;
						
					}
				}
				else {
					
					valuesPart = input.get8Values(threePack);
					valuesTotal.addAll(valuesPart);
					
				}
				
			}
			else {
				
				valuesPart = input.get4Values(threePack);
				valuesTotal.addAll(valuesPart);
				
			}
				
			if (valuesTotal.size() > 81) break;
		}
		
		for (int i = 0; i < valuesTotal.size(); i++) {
//			System.out.println(i + ": " + valuesTotal.get(i));
		}
		
		this.totalValuesForGeneration = valuesTotal;

		return input.getRest();
		
	}
	
//////////////////////////////////////////////////////////////////////////////
//  colors for height
//////////////////////////////////////////////////////////////////////////////
	
	void setColorsForHeight(MapDotsColors colors) {
		if (this.tileTypeLevel == TileType.TILE_LEVEL_LARGE) {
			for (int index = 0; index < 81; index++) {
				if (tiles[index].getType() == TileType.sub) {
					((TileGrid) tiles[index]).setColorsForHeight(colors, index);
				}
				else {
					setColorsForHeight(colors, index, -1, -1);
				}
			}
		}
	}
	
	void setColorsForHeight(MapDotsColors colors, int indexLarge) {
		if (this.tileTypeLevel == TileType.TILE_LEVEL_MEDIUM) {
			for (int index = 0; index < 81; index++) {
				if (tiles[index].getType() == TileType.sub) {
					((TileGrid) tiles[index]).setColorsForHeight(colors, indexLarge, index);
				}
				else {
					setColorsForHeight(colors, indexLarge, index, -1);
				}
			}
		}
	}

	void setColorsForHeight(MapDotsColors colors, int indexLarge, int indexMedium) {
		if (this.tileTypeLevel == TileType.TILE_LEVEL_SMALL) {
			for (int index = 0; index < 81; index++) {
				setColorsForHeight(colors, indexLarge, indexMedium, index);
			}
		}
	}

	private void setColorsForHeight(MapDotsColors colors, int indexLarge, int indexMedium, int indexSmall) {
		
		int height = 0;
		switch (this.tileTypeLevel) {
		case TileType.TILE_LEVEL_LARGE:
			height = tiles[indexLarge].getHeightLevel(); 
			break;
		case TileType.TILE_LEVEL_MEDIUM:
			height = tiles[indexMedium].getHeightLevel(); 
			break;
		case TileType.TILE_LEVEL_SMALL:
			height = tiles[indexSmall].getHeightLevel(); 
			break;
		}
		int colorRGB = colors.getColorForHeight(height);
		
		int yOffset;
		int xOffset;
		int y;
		int x;
		
		int i;
		int j;
		
		switch (this.tileTypeLevel) {
		case TileType.TILE_LEVEL_LARGE:
			yOffset = (int) (indexLarge / 9) * 81;
			xOffset = (int) (indexLarge % 9) * 81;
			for (i = 0; i < 81; i++) {
				y = yOffset + i;
				for (j = 0; j < 81; j++) {
					x = xOffset + j;
					colors.colorsForXY[x][y] = colorRGB;
				}
			}
			break;
		case TileType.TILE_LEVEL_MEDIUM:
			yOffset = (int) (indexLarge / 9) * 81 + (int) (indexMedium / 9) * 9;
			xOffset = (int) (indexLarge % 9) * 81 + (int) (indexMedium % 9) * 9;
			for (i = 0; i < 9; i++) {
				y = yOffset + i;
				for (j = 0; j < 9; j++) {
					x = xOffset + j;
					colors.colorsForXY[x][y] = colorRGB;
				}
			}
			break;
		case TileType.TILE_LEVEL_SMALL:
			y = (int) (indexLarge / 9) * 81 + (int) (indexMedium / 9) * 9 + (int) (indexSmall / 9);
			x = (int) (indexLarge % 9) * 81 + (int) (indexMedium % 9) * 9 + (int) (indexSmall % 9);
			colors.colorsForXY[x][y] = colorRGB;
			break;
		}
		
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////
//   adapter borders
//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void setBorders() {
		setBorderEdgeNorth();
		setBorderEdgeEast();
		setBorderEdgeSouth();
		setBorderEdgeWest();
		setBorderCornerNW();
		setBorderCornerNE();
		setBorderCornerSE();
		setBorderCornerSW();

		
		fillOrder = fillOrderFromOutToIn;
		
		fillIndex = -1;
		setNextToDoActualIndex();
/*		
		(borderAdapterTypeNorth != TileGridBorderAdapterType.from0To1 && 
				borderAdapterTypeNorth != TileGridBorderAdapterType.from1To0 &&
				borderAdapterTypeWest != TileGridBorderAdapterType.from0To1 && 
				borderAdapterTypeWest != TileGridBorderAdapterType.from1To0 &&
*/		
	}
	
	private void setBorderEdgeNorth() {
		int offsetAdapterVariante = 100;
		
		int globalTileNumberEdgeNorth_N01_Index1 = 2;
		globalTileNumberEdgeNorth_N01_Index1 +=  offsetAdapterVariante;
		globalTileNumberEdgeNorth_N01_Index1 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeNorth_N01_Index1);

		int globalTileNumberEdgeNorth_N10_Index1 = 38;
		globalTileNumberEdgeNorth_N10_Index1 +=  offsetAdapterVariante;
		globalTileNumberEdgeNorth_N10_Index1 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeNorth_N10_Index1);

		if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from0To1) {
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1, heightLevel), 1);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 1, heightLevel), 2);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 2, heightLevel), 3);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 3, heightLevel), 4);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 4, heightLevel), 5);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 5, heightLevel), 6);
			addTile(new Tile(globalTileNumberEdgeNorth_N01_Index1 + 6, heightLevel), 7);
		}
		else if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from1To0) {
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1, heightLevel), 1);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 1, heightLevel), 2);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 2, heightLevel), 3);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 3, heightLevel), 4);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 4, heightLevel), 5);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 5, heightLevel), 6);
			addTile(new Tile(globalTileNumberEdgeNorth_N10_Index1 - 6, heightLevel), 7);
		}
		else {
			int[] borderNorthPattern = TileGridBorderPatterns.getInstance().getBorderPattern(1, 0);
			for (int index = 1; index < 8; index++) {
				addTile(new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel),borderNorthPattern[index]), heightLevel), 
						index);
			}
		}
	}
	
	private void setBorderEdgeEast() {
		int offsetAdapterVariante = 100;

		int globalTileNumberEdgeEast_E01_Index17 = 12;
		globalTileNumberEdgeEast_E01_Index17 +=  offsetAdapterVariante;
		globalTileNumberEdgeEast_E01_Index17 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeEast_E01_Index17);

		int globalTileNumberEdgeEast_E10_Index17 = 58;
		globalTileNumberEdgeEast_E10_Index17 +=  offsetAdapterVariante;
		globalTileNumberEdgeEast_E10_Index17 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeEast_E10_Index17);

		if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from0To1) {
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17, heightLevel), 17);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 1, heightLevel), 26);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 2, heightLevel), 35);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 3, heightLevel), 44);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 4, heightLevel), 53);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 5, heightLevel), 62);
			addTile(new Tile(globalTileNumberEdgeEast_E01_Index17 + 6, heightLevel), 71);
		}
		else if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from1To0) {
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17, heightLevel), 17);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 1, heightLevel), 26);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 2, heightLevel), 35);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 3, heightLevel), 44);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 4, heightLevel), 53);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 5, heightLevel), 62);
			addTile(new Tile(globalTileNumberEdgeEast_E10_Index17 - 6, heightLevel), 71);
		}
		else {
			int[] borderEastPattern = TileGridBorderPatterns.getInstance().getBorderPattern(4, 0);
			for (int index = 1; index < 8; index++) {
				addTile(new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel),borderEastPattern[index]), heightLevel), 
						9 * index + 8);
			}
		}
	}
	
	private void setBorderEdgeSouth() {
		int offsetAdapterVariante = 100;

		int globalTileNumberEdgeSouth_S01_Index73 = 62;
		globalTileNumberEdgeSouth_S01_Index73 +=  offsetAdapterVariante;
		globalTileNumberEdgeSouth_S01_Index73 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeSouth_S01_Index73);

		int globalTileNumberEdgeSouth_S10_Index73 = 78;
		globalTileNumberEdgeSouth_S10_Index73 +=  offsetAdapterVariante;
		globalTileNumberEdgeSouth_S10_Index73 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeSouth_S10_Index73);

		if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from0To1) {
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73, heightLevel), 73);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 1, heightLevel), 74);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 2, heightLevel), 75);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 3, heightLevel), 76);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 4, heightLevel), 77);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 5, heightLevel), 78);
			addTile(new Tile(globalTileNumberEdgeSouth_S01_Index73 + 6, heightLevel), 79);
		}
		else if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from1To0) {
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73, heightLevel), 73);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 1, heightLevel), 74);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 2, heightLevel), 75);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 3, heightLevel), 76);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 4, heightLevel), 77);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 5, heightLevel), 78);
			addTile(new Tile(globalTileNumberEdgeSouth_S10_Index73 - 6, heightLevel), 79);
		}
		else {
			int[] borderSouthPattern = TileGridBorderPatterns.getInstance().getBorderPattern(2, 0);
			for (int index = 1; index < 8; index++) {
				addTile(new Tile(TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel),borderSouthPattern[index]), heightLevel),
						index + 72);
			}
		}
	}
	
	private void setBorderEdgeWest() {
		int offsetAdapterVariante = 100;

		int globalTileNumberEdgeWest_W01_Index9 = 22;
		globalTileNumberEdgeWest_W01_Index9 +=  offsetAdapterVariante;
		globalTileNumberEdgeWest_W01_Index9 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeWest_W01_Index9);

		int globalTileNumberEdgeWest_W10_Index9 = 48;
		globalTileNumberEdgeWest_W10_Index9 +=  offsetAdapterVariante;
		globalTileNumberEdgeWest_W10_Index9 =
				TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberEdgeWest_W10_Index9);

		if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from0To1) {
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9, heightLevel), 9);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 1, heightLevel), 18);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 2, heightLevel), 27);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 3, heightLevel), 36);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 4, heightLevel), 45);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 5, heightLevel), 54);
			addTile(new Tile(globalTileNumberEdgeWest_W01_Index9 + 6, heightLevel), 63);
		}
		else if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from1To0) {
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9, heightLevel), 9);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 1, heightLevel), 18);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 2, heightLevel), 27);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 3, heightLevel), 36);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 4, heightLevel), 45);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 5, heightLevel), 54);
			addTile(new Tile(globalTileNumberEdgeWest_W10_Index9 - 6, heightLevel), 63);
		}
		else {
			int[] borderWestPattern = TileGridBorderPatterns.getInstance().getBorderPattern(3, 0);
			for (int index = 1; index < 8; index++) {
				addTile(new Tile(	TileType.addGlobalNumberOffset(TileType.getTileTypeForLevel(tileTypeLevel), borderWestPattern[index]),	heightLevel),
						9 * index);
			}
		}
	}

	private void setBorderCornerNW() {
		int offsetAdapterVariante = 100;

		int globalTileNumberCornerNW_N01_W01 = 81;
		int globalTileNumberCornerNW_N01 = 1;
		int globalTileNumberCornerNW_W01 = 21;
		int globalTileNumberCornerNW_N10_W10 = 82;
		int globalTileNumberCornerNW_N10 = 39;
		int globalTileNumberCornerNW_W10 = 49;
		
		int globalTileNumberCornerNW = 0;
		
		if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from0To1) {
			if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from0To1) {
				globalTileNumberCornerNW = globalTileNumberCornerNW_N01_W01;
			}
			else {
				globalTileNumberCornerNW = globalTileNumberCornerNW_N01;
			}
		}
		else if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from1To0) {
			if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from1To0) {
				globalTileNumberCornerNW = globalTileNumberCornerNW_N10_W10;
			}
			else {
				globalTileNumberCornerNW = globalTileNumberCornerNW_N10;
			}
		}
		else if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from0To1) {
			globalTileNumberCornerNW = globalTileNumberCornerNW_W01;
		}
		else if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from1To0) {
			globalTileNumberCornerNW = globalTileNumberCornerNW_W10;
		}
		
		if (globalTileNumberCornerNW > 0) {
			globalTileNumberCornerNW +=  offsetAdapterVariante;
			globalTileNumberCornerNW =
					TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberCornerNW);
			addTile(new Tile(globalTileNumberCornerNW, heightLevel), 0);
		}

	}

	private void setBorderCornerNE() {
		int offsetAdapterVariante = 100;

		int globalTileNumberCornerNE_N01_E10 = 83;
		int globalTileNumberCornerNE_N01 = 9;
		int globalTileNumberCornerNE_E01 = 11;
		int globalTileNumberCornerNE_N10_E01 = 84;
		int globalTileNumberCornerNE_N10 = 31;
		int globalTileNumberCornerNE_E10 = 59;
		
		int globalTileNumberCornerNE = 0;
		
		if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from0To1) {
			if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from1To0) {
				globalTileNumberCornerNE = globalTileNumberCornerNE_N01_E10;
			}
			else {
				globalTileNumberCornerNE = globalTileNumberCornerNE_N01;
			}
		}
		else if (this.borderAdapterTypeNorth == TileGridBorderAdapterType.from1To0) {
			if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from0To1) {
				globalTileNumberCornerNE = globalTileNumberCornerNE_N10_E01;
			}
			else {
				globalTileNumberCornerNE = globalTileNumberCornerNE_N10;
			}
		}
		else if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from0To1) {
			globalTileNumberCornerNE = globalTileNumberCornerNE_E01;
		}
		else if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from1To0) {
			globalTileNumberCornerNE = globalTileNumberCornerNE_E10;
		}
		
		if (globalTileNumberCornerNE > 0) {
			globalTileNumberCornerNE +=  offsetAdapterVariante;
			globalTileNumberCornerNE =
					TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberCornerNE);
			addTile(new Tile(globalTileNumberCornerNE, heightLevel), 8);
		}

	}

	private void setBorderCornerSE() {
		int offsetAdapterVariante = 100;

		int globalTileNumberCornerSE_S01_E01 = 87;
		int globalTileNumberCornerSE_S01 = 69;
		int globalTileNumberCornerSE_E01 = 19;
		int globalTileNumberCornerSE_S10_E10 = 88;
		int globalTileNumberCornerSE_S10 = 71;
		int globalTileNumberCornerSE_E10 = 51;
		
		int globalTileNumberCornerSE = 0;

		if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from0To1) {
			if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from0To1) {
				globalTileNumberCornerSE = globalTileNumberCornerSE_S01_E01;
			}
			else {
				globalTileNumberCornerSE = globalTileNumberCornerSE_S01;
			}
		}
		else if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from1To0) {
			if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from1To0) {
				globalTileNumberCornerSE = globalTileNumberCornerSE_S10_E10;
			}
			else {
				globalTileNumberCornerSE = globalTileNumberCornerSE_S10;
			}
		}
		else if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from0To1) {
			globalTileNumberCornerSE = globalTileNumberCornerSE_E01;
		}
		else if (this.borderAdapterTypeEast == TileGridBorderAdapterType.from1To0) {
			globalTileNumberCornerSE = globalTileNumberCornerSE_E10;
		}
		
		if (globalTileNumberCornerSE > 0) {
			globalTileNumberCornerSE +=  offsetAdapterVariante;
			globalTileNumberCornerSE =
					TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberCornerSE);
			addTile(new Tile(globalTileNumberCornerSE, heightLevel), 80);
		}

	}

	private void setBorderCornerSW() {
		int offsetAdapterVariante = 100;

		int globalTileNumberCornerSW_S01_W10 = 86;
		int globalTileNumberCornerSW_S01 = 61;
		int globalTileNumberCornerSW_W01 = 29;
		int globalTileNumberCornerSW_S10_W01 = 85;
		int globalTileNumberCornerSW_S10 = 79;
		int globalTileNumberCornerSW_W10 = 41;
		
		int globalTileNumberCornerSW = 0;

		if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from0To1) {
			if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from1To0) {
				globalTileNumberCornerSW = globalTileNumberCornerSW_S01_W10;
			}
			else {
				globalTileNumberCornerSW = globalTileNumberCornerSW_S01;
			}
		}
		else if (this.borderAdapterTypeSouth == TileGridBorderAdapterType.from1To0) {
			if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from0To1) {
				globalTileNumberCornerSW = globalTileNumberCornerSW_S10_W01;
			}
			else {
				globalTileNumberCornerSW = globalTileNumberCornerSW_S10;
			}
		}
		else if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from0To1) {
			globalTileNumberCornerSW = globalTileNumberCornerSW_W01;
		}
		else if (this.borderAdapterTypeWest == TileGridBorderAdapterType.from1To0) {
			globalTileNumberCornerSW = globalTileNumberCornerSW_W10;
		}
		
		if (globalTileNumberCornerSW > 0) {
			globalTileNumberCornerSW +=  offsetAdapterVariante;
			globalTileNumberCornerSW =
					TileType.addGlobalNumberOffset(TileType.getAdapterTileTypeForLevel(tileTypeLevel), globalTileNumberCornerSW);
			addTile(new Tile(globalTileNumberCornerSW, heightLevel), 72);
		}

	}

///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		String result = "";
		String tileString = "";
		int i;
		
		if (tiles == null) {
			tiles = new Tile[81];
		}
		
		for (i = 0; i < 81; i++) {
			if (tiles[i] != null) {
				tileString = tiles[i].toString();
			}
			else tileString = "TODO";
			
			if (i > 0) result = result + ",";
			
			result = result + tileString;
		}
		
		result = "sub(" + result + ")";
		if (tileTypeLevel == 1) result = result + "\n";
		if (tileTypeLevel == 0) result = result + "\n\n";
		
		return result;
	}

}

/*

private void zoomIn(int border) {
	Tile[] borderTiles = getBorderTiles(border);
	TileGrid child = new TileGrid(this);
	addTile(borderTiles[0], 0);
	addTile(borderTiles[1], 1);
	addTile(borderTiles[2], 2);
	addTile(borderTiles[3], 3);
	addTile(borderTiles[4], 4);
	addTile(borderTiles[5], 5);
	addTile(borderTiles[6], 6);
	addTile(borderTiles[7], 7);
	addTile(borderTiles[8], 8);
	addTile(borderTiles[9], 9);
	addTile(borderTiles[10], 18);
	addTile(borderTiles[11], 27);
	addTile(borderTiles[12], 36);
	addTile(borderTiles[13], 45);
	addTile(borderTiles[14], 54);
	addTile(borderTiles[15], 63);
	addTile(borderTiles[16], 72);
	
	actualTileIndex = 10;

}
*/
	
/*
	private void fillSubCluster(List<Integer> cluster, int indexSubCluster) {
		
		while (indexSubCluster < cluster.size()) {
		
			List<Integer> neighbourSubs;
			neighbourSubs = getNeighbourSubs(cluster.get(indexSubCluster));
			
			int indexRaster;
			
			for (int i = 0; i < neighbourSubs.size(); i++) {
				
				indexRaster = neighbourSubs.get(i);
				if (cluster.indexOf(indexRaster) == -1) {
					// the cluster doesn't contain the neighbour
					// --> insert it
					cluster.add(indexRaster);
				}
			}
		
			indexSubCluster++;
		}
		
		return;
		
	}
	
	private List<Integer> getNeighbourSubs(int i) {
		
		List<Integer> result = new ArrayList();
		
		if (((i % 9) > 0) && (tiles[i-1].type == TileType.sub)) {
			result.add(i-1);
		}
		if (((i % 9) < 8) && (tiles[i+1].type == TileType.sub)) {
			result.add(i+1);
		}
		if (((int)(i / 9) > 0) && (tiles[i-9].type == TileType.sub)) {
			result.add(i-9);
		}
		if (((int)(i / 9) < 8) && (tiles[i+9].type == TileType.sub)) {
			result.add(i+9);
		}
		return result;
	}

	private boolean checkForBorderTileToNorth(List<Integer> clusterSorted) {
		int nr;
		int i,j;
		
		for (j = 0; j < clusterSorted.size(); j++) {
			nr = clusterSorted.get(j);
			if (nr > 8) return false;
			for (i = 0; i < 9; i++) {
				if (nr == i) return true;
			}
		}
		return false;
	}
	
	private boolean checkForBorderTileToSouth(List<Integer> clusterSorted) {
		int nr;
		int i,j;
		
		for (j = 0; j < clusterSorted.size(); j++) {
			nr = clusterSorted.get(j);
			if (nr < 72) continue;
			for (i = 72; i < 81; i++) {
				if (nr == i) return true;
			}
		}
		return false;
	}

	private boolean checkForBorderTileToWest(List<Integer> clusterSorted) {
		int nr;
		int j;
		
		for (j = 0; j < clusterSorted.size(); j++) {
			nr = clusterSorted.get(j);
			if (nr == 0 || nr == 9 || nr == 18 || nr == 27 || nr == 36 || nr == 45 || nr == 54 || nr == 63 || nr == 72) return true;
		}
		return false;
	}

	private boolean checkForBorderTileToEast(List<Integer> clusterSorted) {
		int nr;
		int j;
		
		for (j = 0; j < clusterSorted.size(); j++) {
			nr = clusterSorted.get(j);
			if (nr == 8 || nr == 17 || nr == 26 || nr == 35 || nr == 44 || nr == 53 || nr == 62 || nr == 71 || nr == 80) return true;
		}
		return false;
	}

	private List<Integer> getSortedCluster(List<Integer> clusterUnsorted) {
		List<Integer> result = new ArrayList<Integer>(clusterUnsorted.size());
		int steps = clusterUnsorted.size();
		int min;
		int i, j;
		for (i = 0; i < steps; i++) {
			min = 81;
			for (j = 0; j < clusterUnsorted.size(); j++) {
				if (clusterUnsorted.get(j) < min) {
					min = clusterUnsorted.get(j);
					clusterUnsorted.set(j,  clusterUnsorted.remove(clusterUnsorted.size() - 1));
				}
			}
			result.set(i,  min);
		}
		return result;
	}

 */








/*
twoValues = input.checkFirstTwoBitsForNumberOfValues(threePack, ThreePackPosition.left);
if (twoValues) {
	valuesPart = input.getTwoValues(threePack, ThreePackPosition.left);
}
else {
	valuesPart = input.getOneValue(threePack, ThreePackPosition.left);
}
valuesTotal.addAll(valuesPart);

twoValues = input.checkFirstTwoBitsForNumberOfValues(threePack, ThreePackPosition.center);
if (twoValues) {
	valuesPart = input.getTwoValues(threePack, ThreePackPosition.center);
}
else {
	valuesPart = input.getOneValue(threePack, ThreePackPosition.center);
}
valuesTotal.addAll(valuesPart);
*/
