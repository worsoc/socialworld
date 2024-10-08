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

import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.tools.StringTupel;
import org.socialworld.core.IAccessToken;

public class SVVector extends SavedValue {

	private Vector savedVector;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
 			new StringTupel(Type.vector.getIndexWithSWTPraefix(), PropertyName.vector.name()),
 			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_x.name()),
 			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_y.name()),
 			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.vector_z.name())
 			} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SavedValue.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISavedValue)     ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static SVVector objectNothing;
	
	public static SVVector getObjectNothing() {
		if (objectNothing == null) {
		objectNothing = new SVVector();
		objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}
	
	private SVVector() {
		super();
		this.savedVector =  Vector.getObjectNothing();
	}

///////////////////////////////////////////////////////////////////////////////////////////
////////////////// creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public SVVector (Vector vector, PropertyName propNameParent) {
		super();
		this.savedVector = vector;
	}
	
	private SVVector( SVVector original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
		setPropertyName(original.getPropertyName());
		this.savedVector = original.getVector();
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////  implementing  SavedValues abstract methods  ///////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	public Vector getReleased(IAccessToken token) {
		return getVector();
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public ISavedValue copyForProperty(IAccessToken token) {
		return new SVVector(this, getPropertyProtection(), token);
	}
	
	public  ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		if (checkHasGetPermission(token)) {
			switch (propName) {
			case vector:
				return new ValueProperty(Type.vector, valueName, getVector());
			case vector_x:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getX());
			case vector_y:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getY());
			case vector_z:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.getZ());
			case vector_length:
				return new ValueProperty(Type.floatingpoint, valueName, this.savedVector.length());
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
	
	public int getX(IAccessToken token) { 
		return (int) savedVector.getX(); 
	}
	
	public int getY(IAccessToken token) {
		return (int) savedVector.getY(); 
	}
	
	public int getZ(IAccessToken token) { 
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
