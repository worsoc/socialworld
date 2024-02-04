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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.StringTupel;

public abstract class SavedValue implements ISavedValue {

	private boolean isObjectNothing = false;
	
	private PropertyName propertyName = PropertyName.unknown;
	private PropertyProtection protection;

	protected SavedValue() {
		this.protection = PropertyProtection.getProtection(this);
	}

	protected SavedValue(PropertyProtection protectionOriginal, SimulationCluster clusterNew) {
		this.protection = PropertyProtection.getProtection(protectionOriginal, clusterNew, this);
	}

	
	public final void setPropertyName(PropertyName prop) {
		if (this.propertyName == PropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public final PropertyName getPropertyName() {
		return this.propertyName;
	}

	public abstract Object getReleased(SimulationCluster cluster);
	
	protected final void setToObjectNothing() {
		if (checkIsObjectNothing()) {
			isObjectNothing = true;
		}
	}

	public final boolean isObjectNothing() {
		return this.isObjectNothing;
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////  meta information   //////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[] {} ;
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = new ArrayList<StringTupel>();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}
	
	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = new ArrayList<StringTupel>();
		for (int indexAdd = 0; indexAdd < propMethodsMetaInfos.length; indexAdd++) {
			listOfPropMethodMetaInfo.add(propMethodsMetaInfos[indexAdd]);
		}
		return listOfPropMethodMetaInfo;
	}
	
	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {};
	
	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = new ArrayList<KnowledgeFact_Criterion>();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
			listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	public final boolean isProtected() {
		if (this.protection == null) return false;
		if (!this.protection.hasRestrictions()) return false;
		if (this.protection.hasUnknownCluster()) return false;
		return true;
	}

	public final boolean hasPropertyProtection() {
		
		if (this.protection == null) {
			return false;
		}
		
		if (this.protection.hasUnknownCluster()) {
			return false;
		}
		
		return true;
		
	}

	public final PropertyProtection getPropertyProtection() {
		return PropertyProtection.getProtection(this.protection);
	};
	
	public final void setPropertyProtection(PropertyProtection propertyProtection) {
		this.protection = propertyProtection;
	}

	public final boolean checkHasGetPermission(SimulationCluster cluster) {
		return this.protection.checkHasGetPermission(cluster);
	}

	public final boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		return this.protection.checkHasUseAsPermission(useAsPermission);
	}

	public final PropertyUsingAs[] getReducedUseAsPermissions(PropertyUsingAs[] useAsPermissions) {
		PropertyUsingAs[] result = null;
		// TODO implement getReducedUseAsPermissions
		return result;
	}
	
	public final boolean checkUseAsPermissionsReductionNecessary(PropertyUsingAs[] useAsPermissions) {
		// TODO implement checkUseAsPermissionsReductionNecessary
		return true;
	}

	
	
	public final  ValueProperty getAsValue(SimulationCluster cluster) {
		if (protection.checkHasGetPermission(cluster)) {
			Type propertyType;
			propertyType = this.propertyName.getType();
			return new ValueProperty(propertyType,   this.propertyName.toString(), copyForProperty(cluster));
		}
		else {
			return ValueProperty.getInvalid();
		}
	}
	
	public final ValueProperty getAsValue(SimulationCluster cluster, String valueName) {
		if (protection.checkHasGetPermission(cluster)) {
			Type propertyType;
			propertyType = this.propertyName.getType();
			return new ValueProperty(propertyType,   valueName, copyForProperty(cluster));
		}
		else {
			return ValueProperty.getInvalid();
		}
	}

	// ISavedValue copyForProperty(SimulationCluster cluster) will be implemented in inherited classes

	
	
	
	public ValueProperty getProperty(PropertyName propName, String valueName) {
		if (hasPropertyProtection()) {
			SimulationCluster cluster = this.protection.getCluster();
			return getProperty(cluster, propName, valueName);
		}
		else {
			return ValueProperty.getInvalid();
		}
				
	}

	public ValueProperty getPropertyFromMethod(String methodName, String valueName) {
		if (hasPropertyProtection()) {
			SimulationCluster cluster = this.protection.getCluster();
			return getPropertyFromMethod(cluster, methodName, valueName);
		}
		else {
			return ValueProperty.getInvalid();
		}
				
	}
	
	
	

	/*	03.06.2020
	 // TODO implement getProperty in sub classes
	 // TEMP_SOLUTION wieder freigeben
	 // -->
		public  ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
			return ValueProperty.getInvalid();
		}
		
		public  ValueProperty getPropertyFromMethod(SimulationCluster cluster, String methodName, String valueName){
			return ValueProperty.getInvalid();
		}
	*/

	public final ValueProperty getPropertyFromMethod(SimulationCluster cluster, String methodName, String valueName) {
		
		Object got = null;
		ValueProperty result = ValueProperty.getInvalid();
		Method method;
		method = getMethod(methodName);
		
		if (method != null) {
			try {
				method.setAccessible(true);
				got = method.invoke(this);

			// Handle any exceptions thrown by method to be invoked.
			}
			catch (InvocationTargetException ite) {
			    Throwable cause = ite.getCause();
			    System.out.println( cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				iae.printStackTrace();
			} 
			catch (IllegalArgumentException iarge) {
				iarge.printStackTrace();
			}
			
			if (got != null) {
				
				if (got instanceof ValueProperty) {  
					
					result = ( ValueProperty ) got ;
					result.setName(valueName);
// TODO		result.setCluster(cluster);
				}
				
			}
		}

		return result;
		
	}
	

//////////////////////////////////////////////////////////////////////////////
	
	private final Method getMethod(String method) {
		Method[] allMethods = this.getClass().getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.equals(method)) return m;
		}
		return null;
	}


}
