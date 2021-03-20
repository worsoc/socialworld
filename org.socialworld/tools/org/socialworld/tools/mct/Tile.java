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

/**
 * 
 * @author Mathias Sikos
 *
 * The class Tile holds the information of one raster element and describes the part of the landscape.
 * The main informations are
 * - the tile number for assigning the physical form (inclination/declination, border adapter, specials ... 
 * 						(there will be a documentation soon, it's just analog on paper until now))
 * 			( the combination of tile type and tile number results in the global tile number (tile number + offset for the tile type) )
 * - the height
 * 			--	the all over height (according to the whole map)
 * 			--  the relative height difference to neighbored tiles in north and west 
 * 
 * Every tile grid is a tile itself (TileGrid is a sub class of Tile).
 * There are methods for interpretation a sub tile grid as a tile of the same tile type level.
 * Every sub tile grid must be fit in next to the other tiles. So a sub tile grid feels as a tile itself.
 * There are the methods getFeelAsTileType() and getNumber() for implementation of the feeling as a tile.
 * They abstract from the tile grid to a tile.
 * 
 * There are methods for getting the border adapter type, that is needed to a arrange a sub tile grid next to the tile.
 * 
*/
public class Tile {

	TileType type;
	int alternative = 0;
	int number;
	int globalNumber = -2;
	int heightLevel;
	
	int heightOffsetFromWest;
	int heightOffsetFromNorth;

	
    private static Tile nothing;
    
    private Tile() {	
    	this.type = TileType.todo;
    }
    
    public static Tile getInstance () {
	    if (Tile.nothing == null) {
	    	Tile.nothing = new Tile ();
	    }
	    return Tile.nothing;
	}

	Tile (TileType type) {
		this.type = type;
	}
	  
	Tile(String tile) {
		String tile_elements[] = tile.split("_");
		switch (tile_elements[0]) {
		case "L" : this.type = TileType.largeStandard; break;
		case "M" : this.type = TileType.mediumStandard; break;
		case "S" : this.type = TileType.smallStandard; break;
		case "MA" : this.type = TileType.mediumAdapter; break;
		case "SX" : this.type = TileType.smallSpecial; break;
		case "SXA" : this.type = TileType.smallSpecialAdapter; break;
		}
		
		this.number = Integer.parseInt(tile_elements[1]);
		this.globalNumber = this.number + TileType.getTileTypeOffset(this.type);
		this.heightLevel = Integer.parseInt(tile_elements[2]);
	}


	Tile(TileType type, int tileNumber, int heightLevel) {
		this.type = type;
		this.number = tileNumber;
		this.globalNumber = tileNumber + TileType.getTileTypeOffset(this.type);
		this.heightLevel = heightLevel;
	}

	Tile(int globalTileNumber, int heightLevel) {
		this.type = TileType.getTileType(globalTileNumber);
		this.globalNumber = globalTileNumber;
		this.number = globalTileNumber - TileType.getTileTypeOffset(this.type);
		this.heightLevel = heightLevel;
	}
	
	void setType(TileType type) {
		this.type = type;
	}

	void setAlternative(int alternative) {
		this.alternative = alternative;
	}

	void setNumber(int tileNumber) {
		this.number = tileNumber;
		this.globalNumber = tileNumber + TileType.getTileTypeOffset(this.type);
	}

	void setGlobalNumber(int globalTileNumber) {
		this.globalNumber = globalTileNumber;
		if (this.type != TileType.sub && this.type != TileType.todo) {
			this.number = globalTileNumber - TileType.getTileTypeOffset(this.type);
		}
	}

	void setHeightLevel(int heightLevel) {
		this.heightLevel = heightLevel;
	}


	
	void setSub() {
		type = TileType.sub;
		number = -1;
	}
	
	TileType getType() {
		return type;
	}

	TileType getFeelAsTileType() {
		TileType result;
		if (this.type == TileType.sub) {
			if (this.globalNumber >= 0) {
				result = TileType.getTileType(this.globalNumber);
			}
			else {
				result = this.type;
			}
		}
		else {
			result = this.type;
		}
		return result;
	}

	int getTileTypeAlternative() {
		if (alternative > 0) {
			return alternative;
		}
		else {
			switch (type) {
				case smallStandard: return 1;
				case smallAdapter:
					if (number < 200) return 1;
					else if (number < 300) return 2;
					else return 3;
				case smallSpecial:
					if (number < 200) return 1;
					else if (number < 300) return 2;
					else return 3;
				case smallSpecialAdapter:
					if (number < 200) return 1;
					else if (number < 300) return 2;
					else return 3;
				case mediumStandard: return 1;
				case mediumAdapter:
					if (number < 200) return 1;
					else if (number < 300) return 2;
					else return 3;
				case largeStandard: return 1;
				default: return 0;	
			}
		}
	}
	
	
	int getNumber() {
		if (type == TileType.todo) return -2;
		if (type == TileType.sub) {
			if (this.globalNumber > 0) {
				// sub with border appearence of a standard tile
				return TileType.subGlobalNumberOffset(this.globalNumber);
			}
			else {
				return -1;
			}
		};
		return this.number;
	}
	
	int getGlobalNumber() {
		return globalNumber;
	}

	int getHeightLevel() {
		return heightLevel;
	}
	
	TileGridBorderAdapterType getBorderAdapterTypeNorth() {
		return TileGridBorderAdapterType.getBorderAdapterTypeNorth(globalNumber);
	}

	TileGridBorderAdapterType getBorderAdapterTypeEast() {
		return TileGridBorderAdapterType.getBorderAdapterTypeEast(globalNumber);
	}

	TileGridBorderAdapterType getBorderAdapterTypeSouth() {
		return TileGridBorderAdapterType.getBorderAdapterTypeSouth(globalNumber);
	}

	TileGridBorderAdapterType getBorderAdapterTypeWest() {
		return TileGridBorderAdapterType.getBorderAdapterTypeWest(globalNumber);
	}

	public String toString() {
		String result;
		
		switch (type) {
			case smallStandard: result = "S"; break;
			case mediumStandard: result = "M";break;
			case largeStandard: result = "L"; break;
			case mediumAdapter: result = "MA";break;
			case smallSpecial: result = "SX"; break;
			case smallSpecialAdapter: result = "SXA"; break;
			default: return "TODO";
		}
		
		return result +"_" + number + "_" + heightLevel;
	}
}