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
package org.socialworld.calculation.expressions;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.Event;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateSimulationObject;

public class GetProperty extends Expression {

	
	

	public GetProperty(SimulationCluster cluster, GetPropertyMode mode, String name, String propertyName) {
		
		super();
		
		setOperation(Expression_Function.property);
		
		switch(mode) {
		case property:
			setValue(new Value(Type.simPropName, PropertyName.forString(name)));
			break;
		case method:
			setValue(new Value(Type.simPropName, PropertyName.method));
			break;
		case isA:
			setValue(new Value(Type.simPropName, PropertyName.isA));
			break;
		case check:
			setValue(new Value(Type.simPropName, PropertyName.check));
			break;
		case isElem:
			setValue(new Value(Type.simPropName, PropertyName.isElem));
			break;
		}
		
		Expression exp1 = new Constant(new Value(Type.integer, cluster.getIndex()));
		Expression exp2 = new Constant(new Value(Type.string, name));
		Expression exp3 = new Constant(new Value(Type.string, propertyName));
		
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(exp3);
		setValid();
			
	}
	
	protected ValueProperty getProperty(Value valueObject, SimulationCluster cluster, PropertyName simPropName, String methodName, String valueName) {
		
		ValueProperty result;
		result = ValueProperty.getInvalid();
		Object object = valueObject.getValue();
		
		switch (simPropName) {
		case method:
			
			if (methodName.length() > 0) {
				
				// use reflection for calling the method
				
				if (object instanceof ISavedValues) {
					ISavedValues savedValue;
					savedValue = (ISavedValues) object;
					result = savedValue.getPropertyFromMethod(cluster, methodName, valueName);
				}
				
			}
			break;
			
		case isA:
			
			if (methodName.length() > 0) {
				
				
				if (object instanceof SimulationObject) {
					SimulationObject simObj;
					simObj = (SimulationObject) object;
					result = simObj.isA(methodName, valueName);
				}
				
			}
			break;

		case check:
			
			if (methodName.length() > 0) {
				
				
				if (object instanceof SimulationObject) {
					SimulationObject simObj;
					simObj = (SimulationObject) object;
					result = simObj.check(methodName, valueName);
				}
				
			}
			break;

		case isElem:
			
			if (methodName.length() > 0) {
				
				
				if (object instanceof SimulationObject) {
					SimulationObject simObj;
					simObj = (SimulationObject) object;
					// the methodName variable holds the setNumber as string
					result = simObj.isElementOf(methodName, valueName);
				}
				
			}
			break;
			
		case unknown:
			
			break;
			
		default:
			
			// call getProperty()
			
			if (object instanceof SimulationObject) {
				SimulationObject simObj;
				simObj = (SimulationObject) object;
				result = simObj.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof StateSimulationObject) {
				StateSimulationObject stateSimObj;
				stateSimObj = (StateSimulationObject) object;
				result = stateSimObj.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof ISavedValues) {
				ISavedValues savedValue;
				savedValue = (ISavedValues) object;
				result = savedValue.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof Event) {
				
			}
			else if (object instanceof AbstractAction) {
				
			}
			break;
			
		}

		return result;
		
	}

	

}
