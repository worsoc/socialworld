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
package org.socialworld.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.ISavedValue;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.IObjectSender;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ReturnCode;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.StringTupel;


// TODO implement copy constructor in sub classes
// TODO implement getProperty() and setProperty() in sub classes

public abstract class State implements ISimProperty, ISavedValue, IObjectSender, IObjectReceiver {

	
	
	private boolean isObjectNothing = false;

	private PropertyName propertyName = PropertyName.unknown;
	private PropertyProtection protection;
	private SimulationObject object;
		
	protected ObjectRequester objectRequester = new ObjectRequester();
	
	protected State(SimulationObject object) {
		this.object = object;
		initPropertyName();
		this.protection =  PropertyProtection.getProtection(this);
		init();
	}

	protected State(PropertyProtection protectionOriginal, IAccessToken tokenNew) {
		initPropertyName();
		this.protection = PropertyProtection.getProtection(protectionOriginal, tokenNew, this);
	}

	protected State() {
		// object nothing
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	///////////// object nothing 									    ///////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	protected final void setToObjectNothing() {
		if (checkIsObjectNothing()) {
			isObjectNothing = true;
		}
	}

	protected final boolean isObjectNothing() {
		return this.isObjectNothing;
	}
	
	static final State objectNothing = new NoState();

	public static State getObjectNothing() {
		return objectNothing;
	}

	static class NoState extends State {
		public boolean checkIsObjectNothing() {return true;}
		
		public  ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
			return ValueProperty.getInvalid();
		}

		protected void initPropertyName() {};
		protected ReturnCode init() {return ReturnCode.ignore; };
		protected void setProperty(PropertyName propName, ValueProperty property) {};
		public ISavedValue copyForProperty(IAccessToken token) {return objectNothing; }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////		State				    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	protected abstract void initPropertyName();
	
	protected abstract ReturnCode init();

	protected abstract void setProperty(PropertyName propName, ValueProperty property);

	final protected SimulationObject getObject() {
		return this.object;
	}

	final protected int getObjectID() {
		return this.object.getObjectID();
	}

	
	protected ReturnCode returnFromInit(Table table, long lockingID, int tableRow) {
		if (table.unlock(lockingID)) {
			if (tableRow < 0) {
				return ReturnCode.noRowForObjectID;
			}
			else {
				return  ReturnCode.success;
			}
		}
		else {
			return ReturnCode.tableNotLockedFromCaller;
		}
	}
	
	protected ReturnCode returnFromInit(Table table,  int tableRow) {
			if (tableRow < 0) {
				return ReturnCode.noRowForObjectID;
			}
			else {
				return  ReturnCode.success;
			}
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////meta information    ////////////////////////////////////
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
	/////////////////////////  implementing  ISimProperty  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public final void setPropertyName(PropertyName prop) {
		if (this.propertyName == PropertyName.unknown ) {
			this.propertyName = prop;
		}
	}
	
	public final PropertyName getPropertyName() {
		return this.propertyName;
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	public final boolean isProtected() {
		if (this.protection == null) return false;
		if (!this.protection.hasRestrictions()) return false;
		return true;
	}

	public final boolean hasPropertyProtection() {
		
		if (this.protection == null) {
			return false;
		}
		
		
		return true;
		
	}

	public final PropertyProtection getPropertyProtection() {
		return  PropertyProtection.getProtection(this.protection);
	};
	
	public final void setPropertyProtection(PropertyProtection propertyProtection) {
		this.protection = propertyProtection;
	}
	
	public final boolean checkHasGetPermission(IAccessToken token) {
		return this.protection.checkHasGetPermission(token);
	}

	public final boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		return this.protection.checkHasUseAsPermission(useAsPermission);
	}

	public final List<PropertyUsingAs> getReducedUseAsPermissions(List<PropertyUsingAs> useAsPermissions) {
		List<PropertyUsingAs> result = useAsPermissions;
		// TODO implement getReducedUseAsPermissions
		return result;
	}
	
	public final boolean checkUseAsPermissionsReductionNecessary(List<PropertyUsingAs> useAsPermissions) {
		// TODO implement checkUseAsPermissionsReductionNecessary
		return true;
	}
	
	
	
	public final  ValueProperty getAsValue(IAccessToken token) {
		if (protection.checkHasGetPermission(token)) {
			Type propertyType;
			propertyType = this.propertyName.getType();
			return new ValueProperty(propertyType,   this.propertyName.toString(), copyForProperty(token));
		}
		else {
			return ValueProperty.getInvalid();
		}
	}
	
	public final ValueProperty getAsValue(IAccessToken token, String valueName) {
		if (protection.checkHasGetPermission(token)) {
			Type propertyType;
			propertyType = this.propertyName.getType();
			return new ValueProperty(propertyType,   valueName, copyForProperty(token));
		}
		else {
			return ValueProperty.getInvalid();
		}
	}

	// ISavedValue copyForProperty(IAccessToken token) will be implemented in inherited classes

	
	
	
	public ValueProperty getProperty(PropertyName propName, String valueName) {
		if (hasPropertyProtection()) {
			IAccessToken token = this.protection.getToken();
			return getProperty(token, propName, valueName);
		}
		else {
			return ValueProperty.getInvalid();
		}
				
	}

	public ValueProperty getPropertyFromMethod(String methodName, String valueName) {
		if (hasPropertyProtection()) {
			IAccessToken token  = this.protection.getToken();
			return getPropertyFromMethod(token, methodName, valueName);
		}
		else {
			return ValueProperty.getInvalid();
		}
				
	}
	
	
	

	/*	03.06.2020
	 // TODO implement getProperty() in sub classes
	 // TEMP_SOLUTION wieder freigeben
	 // -->
		public  ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
			return ValueProperty.getInvalid();
		}
		
		public  ValueProperty getPropertyFromMethod(IAccessToken token, String methodName, String valueName){
			return ValueProperty.getInvalid();
		}
	*/

	public final ValueProperty getPropertyFromMethod(IAccessToken token, String methodName, String valueName) {
		
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

	
	

	/////////////////////////////////////////////

	public final ValueProperty getProperty(IAccessToken token, PropertyName propName) {
		String valueName;
		valueName = propName.toString();
		return getProperty(token, propName, valueName);
	}
		

	public final Value getValue(String methodName, String name) {
		
		Object got = null;
		Value result = Value.getValueNothing();
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
				
				result = new Value(Type.object, name, got );
				
			}
		}

		return result;
		
	}

	protected final Method getMethod(String method) {
		Method[] allMethods = this.getClass().getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.equals(method)) return m;
		}
		return null;
	}

	
	final void setSomething(String methodName, Object something, StateSimulationObject mySimObjectsState, WriteAccessToSimulationObject guard) {
		if (mySimObjectsState.checkGuard(guard)) {
			
			Method method = this.getMethod(methodName);
			if (method != null) {
				try {
					method.setAccessible(true);
				    method.invoke(this, something);
	
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
			}
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectSender ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public int sendYourselfTo(IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	public int sendYourselfTo(IAccessToken token, IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	
	public IObjectSender copy() {
		return this;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int receiveObject(int requestID, Object object) {
		objectRequester.receive(requestID, object);
		return 0;
	}
	
}
