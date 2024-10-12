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

	public TileSearchTree(int base, int accuracy) {
		super(base, accuracy);
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
