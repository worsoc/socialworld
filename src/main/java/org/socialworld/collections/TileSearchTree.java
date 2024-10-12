/*
* Social World
* Copyright (C) 2024  Mathias Sikos
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

package org.socialworld.collections;

import org.socialworld.map.MapPropTree;
import org.socialworld.tools.mct.Tile;

public class TileSearchTree extends MapPropTree {

	/*
	 *  a map for tiles
	 *  The tree's basis is 9 for 9 sectors similar to the number keypad
	 *      7    8    9
	 *      4    5    6
	 *      1    2    3
	 *    A tile position is described by a code that holds 9 sector numbers. 
	 *    (a number sequence with length 9) 
	 *    
	 *  level		  m
	 *   		 	6561
	 *   1 		 	2187
	 *   2		  	 729
	 *   3		  	 243
	 *   4		   	  81		large tiles
	 *   5		   	  27
	 *   6             9		medium tiles
	 *   7			   3
	 *   8			   1		small tiles
	 *   9		 	   0,333333333...
	 *   			  	
	 * tile search map always with base 9
	 * so we are able to address a base-27-tile (3D (3x3x3)) on a 3x3-raster (2D)
	 * a large tile must be addressed by location string with length 4
	 * a medium tile must be addressed by location string with length 6
	 * a small tile must be addressed by location string with length 8
*/	
	public TileSearchTree(int accuracy) {
		super(9, accuracy);
	}
	
	protected void createRoot() {
		setRoot(new TileSearchNode(this, 1));
	}
	
	protected  TileSearchNode insertTile(Tile tile, String location) {
		return ((TileSearchNode) getRoot()).insertTile(tile, location);
	}
	
	public TileSearchNode getSubTreeNode(String location) {
		return ((TileSearchNode) getRoot()).getSubTreeNode(location);
	}
	
	public Tile getTile(String location) {
		return ((TileSearchNode) getRoot()).getTile(location);
	}
	
	
}
