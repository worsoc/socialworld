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

import java.util.List;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.tools.StringPair;

public class SVVector extends SavedValue {

	private Vector savedVector;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringPair[] propertiesMetaInfos = new StringPair[]{
 			new StringPair(Type.vector.getIndexWithSWTPraefix(), PropertyName.vector.name()),
 			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_x.name()),
 			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_y.name()),
 			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_z.name())
 			} ;

	public static List<StringPair> getPropertiesMetaInfos() {
		List<StringPair> listOfPropertyMetaInfo = SavedValue.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

///////////////////////////////////////////////////////////////////////////////////////////
////////////////// creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public SVVector (Vector vector, PropertyName propNameParent) {
		super();
		this.savedVector = vector;
	}
	
	private SVVector( SVVector original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		setPropertyName(original.getPropertyName());
		this.savedVector = original.getVector();
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////  implementing  SavedValues abstract methods  ///////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	public Vector getReleased(SimulationCluster cluster) {
		return getVector();
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		return new SVVector(this, getPropertyProtection(), cluster);
	}
	
	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		if (checkHasGetPermission(cluster)) {
			switch (propName) {
			case vector:
				return new ValueProperty(Type.vector, valueName, getVector());
			case vector_x:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getX());
			case vector_y:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getY());
			case vector_z:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getZ());
			default:	
				return ValueProperty.getInvalid();
			}
		}
		else {
			return ValueProperty.getInvalid();
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  Vector methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public int getX(SimulationCluster cluster) { 
		return (int) savedVector.getX(); 
	}
	
	public int getY(SimulationCluster cluster) {
		return (int) savedVector.getY(); 
	}
	
	public int getZ(SimulationCluster cluster) { 
		return (int) savedVector.getZ();
	}
	
	public Vector getDirectionFrom(SVVector vectorPosition) {
		Vector direction = this.savedVector.getDirectionFrom(vectorPosition.getVector());
		return direction;
	}

	public Vector getDirectionTo(SVVector vectorPosition) {
		Vector direction = vectorPosition.getVector().getDirectionFrom(this.savedVector);
		return direction;
	}

	public boolean equals(SVVector b) {
		return this.savedVector.equals(b.getVector());
	}
	
	private Vector getVector() {
		return new Vector(this.savedVector);
	}

	public String toString() {
		return this.savedVector.toString();
	}
}
