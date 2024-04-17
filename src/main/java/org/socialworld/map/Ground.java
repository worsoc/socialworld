/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.map;

import org.socialworld.attributes.Position;

/**
 * @author Mathias Sikos
 *
 */
public class Ground {
	
	private static Ground instance;
	
	MapPropTree map_GroundHeight;
	MapPropTree map_GroundInclination;
	MapPropTree map_GroundMaterial;
	MapPropTree map_GroundConsistency;
	
	private Ground() {
		map_GroundHeight = new MapPropTree(25, 7, Ground_Height.getObjectNothing());
		map_GroundInclination = new MapPropTree(25, 7, Ground_Inclination.getObjectNothing());
		map_GroundMaterial = new MapPropTree(25, 7, Ground_Material.getObjectNothing());
		map_GroundConsistency = new MapPropTree(25, 7, Ground_Consistency.getObjectNothing());
	}
	
	public  static Ground getInstance() {
		if (instance == null) {
			instance = new Ground();
		}
		return instance;
	}
	
	public Ground_Height height(Position position) {
		
		return (Ground_Height) map_GroundHeight.getProperty(position.getLocationByBase25());
	}
	
	public Ground_Material material(Position position) {
		
		return (Ground_Material) map_GroundMaterial.getProperty(position.getLocationByBase25());
	}

	public Ground_Inclination inclination(Position position) {
		
		return (Ground_Inclination) map_GroundInclination.getProperty(position.getLocationByBase25());
	}

	public Ground_Consistency consistency(Position position) {
		
		return (Ground_Consistency) map_GroundConsistency.getProperty(position.getLocationByBase25());
	}

}
