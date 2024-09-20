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
import java.util.LinkedList;

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
	
	public LinkedList<Ground_Height> height(Position position) {
		LinkedList<Ground_Height> result = new LinkedList<Ground_Height>();
		for (IMapProp prop : map_GroundHeight.getProperties(position.getLocationByBase25())) {
			result.add((Ground_Height) prop);
		}
		return result;
	}
	
	public LinkedList<Ground_Material> material(Position position) {
		LinkedList<Ground_Material> result = new LinkedList<Ground_Material>();
		for (IMapProp prop : map_GroundMaterial.getProperties(position.getLocationByBase25())) {
			result.add((Ground_Material) prop);
		}
		return result;
	}

	public LinkedList<Ground_Inclination> inclination(Position position) {
		LinkedList<Ground_Inclination> result = new LinkedList<Ground_Inclination>();
		for (IMapProp prop : map_GroundInclination.getProperties(position.getLocationByBase25())) {
			result.add((Ground_Inclination) prop);
		}
		return result;
	}

	public LinkedList<Ground_Consistency> consistency(Position position) {
		LinkedList<Ground_Consistency> result = new LinkedList<Ground_Consistency>();
		for (IMapProp prop : map_GroundConsistency.getProperties(position.getLocationByBase25())) {
			result.add((Ground_Consistency) prop);
		}
		return result;
	}

}
