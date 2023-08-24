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

public class TileSelectionPattern {

//	int positionLargeStandard[] = {1, 2, 3, 4, 10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31};
	int positionLargeStandard[] = {1, 2, 3, 4, 10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31, 46, 47, 48, 49};
	int positionMediumStandard[] = {1, 2, 3, 4, 10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31, 46, 47, 48, 49};
	int positionSmallStandard[] = {1, 2, 3, 4, 10, 11, 0, 13, 19, 0, 21, 22, 28, 29, 30, 31, 46, 47, 48, 49};
	
	int positionMediumAdapter[] = new int[80];
	int positionSmallSpecial[] = {0, 0, 0, 4, 0, 11, 0, 0, 0, 0, 21, 0, 28};
	
	int positionSmallSpecialAdapter[] = {1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 10, 11, 12, 13, 14, 15, 16, 17, 0, 0, 19, 20, 21, 22, 23, 24, 25, 26};

/*	int positionMediumAdapter[] = 
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27
											, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};
*/	
	TileSelectionPattern() {
		
		int indexTmp = 0;
		for (int j = 0; j <= 7; j++) {
			for (int k = 1; k <= 9; k++) {
				indexTmp++;
				positionMediumAdapter[10 * j + k - 1] = indexTmp;
			}
		}
	
		
	}
	
	int getPatternPositionLargeStandard(int tileNumber) {
		if (tileNumber >= positionLargeStandard.length) return 0;
		return positionLargeStandard[tileNumber];
	}
	
	int getPatternPositionMediumStandard(int tileNumber) {
		if (tileNumber >= positionMediumStandard.length) return 0;
		return positionMediumStandard[tileNumber];
	}

	int getPatternPositionAdapter( int tileNumberWithoutOffset) {
		if ((tileNumberWithoutOffset >= 1) && (tileNumberWithoutOffset <= 79))
			return positionMediumAdapter[tileNumberWithoutOffset  - 1];
		else
			return 0;
	}

	
	int getPatternPositionSmallStandard(int tileNumber) {
		if (tileNumber >= positionSmallStandard.length) return 0;
		return positionSmallStandard[tileNumber];
	}
	
	int getPatternPositionSmallSpecialAdapter(int tileNumberWithoutOffset) {
		if (tileNumberWithoutOffset - 1 >= positionSmallSpecialAdapter.length) return 0;
		return positionSmallSpecialAdapter[tileNumberWithoutOffset - 1];
	}
	
	int getPatternPositionSmallSpecial(int tileNumberWithoutOffset) {
		if (tileNumberWithoutOffset >= positionSmallSpecial.length) return 0;
		return positionSmallSpecial[tileNumberWithoutOffset];
	}
	
	
	
}