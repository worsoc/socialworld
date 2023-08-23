/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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

import java.util.List;

/**
 * 
 * @author Mathias Sikos
 *
 * The class SubIsle is the abstract parent class for 49 sub classes (see in package org.socialworld.mct.isles).
 * Every sub class describes how a concrete subset of tiles  with tile type SUB 
 *  (arranged as a filled rectangle, that means without holes (tiles not with TileType.sub))
 *  may be positioned in the total 9x9 tile raster.
 *  The number of possible positionings depends on the rectangle size.
 *  
 *  Using the Map Creation Tool there is a concrete set of sub tiles in the 9x9 raster.
 *  The set of sub tiles is checked against the different sub isle descriptions (the SubIsle... classes) till there is found a match.
 *  
 *  Finding a concrete matching sub isle there are more possibilities for conditions according tile neighbourhood and heights.
 *  Single sub tiles ("rectangles" with size 1x1) must be strictly arranged to all neighbour tiles, using a border adapter tile set.
 *  Between two adjacent sub tiles there is no need of using a border adapter tile set. 
 *  That means there are more possibilities and freedoms for creations.
 *  
 *  Here is a list of all used sub isles and the according sub isle class SubIsle... (see in package org.socialworld.mct.isles):
 *  7x7 --> SubIsle49
 *  6x7 --> SubIsle48
 *  7x6 --> SubIsle47
 *  6x6 --> SubIsle46
 *  5x7 --> SubIsle45
 *  7x5 --> SubIsle44
 *  5x6 --> SubIsle43
 *  6x5 --> SubIsle42
 *  5x5 --> SubIsle41
 *  4x7 --> SubIsle40
 *  7x4 --> SubIsle39
 *  4x6 --> SubIsle38
 *  6x4 --> SubIsle37
 *  4x5 --> SubIsle36
 *  5x4 --> SubIsle35
 *  4x4 --> SubIsle34
 *  3x7 --> SubIsle33
 *  7x3 --> SubIsle32
 *  3x6 --> SubIsle31
 *  6x3 --> SubIsle30
 *  3x5 --> SubIsle29
 *  5x3 --> SubIsle28
 *  3x4 --> SubIsle27
 *  4x3 --> SubIsle26
 *  3x3 --> SubIsle25
 *  2x7 --> SubIsle24
 *  7x2 --> SubIsle23
 *  2x6 --> SubIsle22
 *  6x2 --> SubIsle21
 *  2x5 --> SubIsle20
 *  5x2 --> SubIsle19
 *  2x4 --> SubIsle18
 *  4x2 --> SubIsle17
 *  2x3 --> SubIsle16
 *  3x2 --> SubIsle15
 *  2x2 --> SubIsle14
*/
public abstract class SubIsle {

	
	protected abstract int checkForIsle(List<Integer> isleSubs);
	protected abstract HeightIsle getIsleAtRasterIndex(int rasterIndex);
	
	protected final  boolean checkForMatch(List<Integer> isleSubs, List<Integer> rasterIndizesDescribingTheIsle) {
		
		int sub;
		int indexSubs;
		int startIndex = 0;
		int size = isleSubs.size();
		
		// assumption: both lists are sorted -->  possibility for optimized iteration 
		for (int mustHaveForBeingTheSubIsle : rasterIndizesDescribingTheIsle) {
			
			if (startIndex == size) return false;
			
			for (indexSubs = startIndex; indexSubs < size; indexSubs++) {
				
				sub = isleSubs.get(indexSubs);
				if (sub == mustHaveForBeingTheSubIsle) {
					startIndex = indexSubs + 1;
					break;
				}
				
				if (sub > mustHaveForBeingTheSubIsle) {
					return false;
				}
				
				// there is no match fo a single sub --> the whole set doesn't match the isle definition
				if (indexSubs == (size - 1)) return false; 
			}
		}

		return true;

	}

}
