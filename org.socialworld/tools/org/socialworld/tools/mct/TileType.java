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

public enum TileType {
	smallStandard, smallAdapter, smallSpecial, smallSpecialAdapter, mediumStandard, mediumAdapter, largeStandard, sub, todo;
	
	static final int TILE_LEVEL_LARGE = 0;
	static final int TILE_LEVEL_MEDIUM = 1;
	static final int TILE_LEVEL_SMALL = 2;
	
	
	static final int OFFSET_LARGE_STANDARD = 100000;
	static final int OFFSET_MEDIUM_STANDARD = 10000;
	static final int OFFSET_MEDIUM_ADAPTER = 11000;
	static final int OFFSET_SMALL_STANDARD = 0;
	static final int OFFSET_SMALL_ADAPTER = 1000;
	static final int OFFSET_SMALL_SPECIAL = 2000;
	static final int OFFSET_SMALL_SPECIAL_ADAPTER = 3000;

	boolean isMedium() {
		if ( (this == mediumStandard) || (this == mediumAdapter) )  return true;
		return false;
	}
	boolean isSmall() {
		if ( (this == smallStandard) || (this == smallAdapter) || (this == smallSpecialAdapter) || (this == smallSpecial)) return true;
		return false;
	}
	boolean isLarge() {
		if ( this == largeStandard) return true;
		return false;
	}
	
	static int getTileTypeOffset(TileType type) {
		switch (type) {
			case smallStandard:
				return OFFSET_SMALL_STANDARD;
			case smallAdapter:
				return OFFSET_SMALL_ADAPTER;
			case smallSpecial:
				return OFFSET_SMALL_SPECIAL;
			case smallSpecialAdapter:
				return OFFSET_SMALL_SPECIAL_ADAPTER;
			case mediumStandard:
				return OFFSET_MEDIUM_STANDARD;
			case mediumAdapter:
				return OFFSET_MEDIUM_ADAPTER;
			case largeStandard:
				return OFFSET_LARGE_STANDARD;
		}
		return -1;
	}
	
	static TileType getTileType(int globalTileNumber) {
		if (globalTileNumber >= OFFSET_LARGE_STANDARD) return largeStandard;
		if (globalTileNumber >= OFFSET_MEDIUM_ADAPTER) return mediumAdapter;
		if (globalTileNumber >= OFFSET_MEDIUM_STANDARD) return mediumStandard;
		if (globalTileNumber >= OFFSET_SMALL_SPECIAL_ADAPTER) return smallSpecialAdapter;
		if (globalTileNumber >= OFFSET_SMALL_SPECIAL) return smallSpecial;
		if (globalTileNumber >= OFFSET_SMALL_ADAPTER) return smallAdapter;
		return smallStandard;
	}
	
	static int addGlobalNumberOffset(TileType type, int smallTileNumber) {
		switch (type) {
			case largeStandard: return smallTileNumber + OFFSET_LARGE_STANDARD;
			case mediumStandard: return smallTileNumber + OFFSET_MEDIUM_STANDARD;
			case mediumAdapter: return smallTileNumber + OFFSET_MEDIUM_ADAPTER;
			case smallStandard: return smallTileNumber;
			case smallAdapter: return smallTileNumber + OFFSET_SMALL_ADAPTER;
			case smallSpecial: return smallTileNumber + OFFSET_SMALL_SPECIAL;
			case smallSpecialAdapter: return smallTileNumber + OFFSET_SMALL_SPECIAL_ADAPTER;
		}
		return -2;
	}

	static int subGlobalNumberOffset(int globalTileNumber) {
		TileType type = getTileType(globalTileNumber);
		switch (type) {
			case largeStandard: return globalTileNumber - OFFSET_LARGE_STANDARD;
			case mediumStandard: return globalTileNumber - OFFSET_MEDIUM_STANDARD;
			case mediumAdapter: return globalTileNumber - OFFSET_MEDIUM_ADAPTER;
			case smallStandard: return globalTileNumber;
			case smallAdapter: return globalTileNumber - OFFSET_SMALL_ADAPTER;
			case smallSpecial: return globalTileNumber - OFFSET_SMALL_SPECIAL;
			case smallSpecialAdapter: return globalTileNumber - OFFSET_SMALL_SPECIAL_ADAPTER;
		}
		return -2;
	}

	static TileType getTileTypeForLevel(int level) {
		switch (level) {
			case TILE_LEVEL_LARGE: return largeStandard;
			case TILE_LEVEL_MEDIUM: return mediumStandard;
			case TILE_LEVEL_SMALL: return smallStandard;
		}
		return todo;
	}
	
	static TileType getTileTypeForLevelAndRandom(int level, int random) {
		int moduloResult;
		switch (level) {
			case TILE_LEVEL_LARGE: 
				return largeStandard;
			case TILE_LEVEL_MEDIUM: return mediumStandard;
			case TILE_LEVEL_SMALL:
				moduloResult = random % 4;
				switch (moduloResult) {
					case 0: return smallStandard;
					case 1: return smallStandard;
					case 2: return smallStandard;
					case 3: return smallStandard;
				}
				return smallStandard;
		}
		return todo;
	}
	
	static TileType getAdapterTileTypeForLevel(int level) {
		switch (level) {
			case TILE_LEVEL_LARGE: return todo;
			case TILE_LEVEL_MEDIUM: return mediumAdapter;
			case TILE_LEVEL_SMALL: return smallAdapter;
		}
		return todo;
	}

}