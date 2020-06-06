/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;

public class SVVector extends SavedValue {

	private Vector savedVector;
	
	private SVVector( SVVector original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		setPropertyName(original.getPropertyName());
		this.savedVector = original.getVector();
	}

	public ISavedValues copyForProperty(SimulationCluster cluster) {
		return new SVVector(this, getPropertyProtection(), cluster);
	}
	
	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case position_vector:
		case direction_vector:
			return new ValueProperty(Type.vector, valueName, getVector());
		default:	
			return ValueProperty.getInvalid();
		}
	}
	

	private Vector getVector() {
		return new Vector(this.savedVector);
	}

}
