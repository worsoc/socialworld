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
package org.socialworld.calculation.expressions;

import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class GetProperty extends Expression {

	public GetProperty(SimulationCluster cluster, PropertyName simPropName, String propertyName) {
		
		super();
		
		setOperation(Expression_Function.property);
		setValue(new Value(Type.simPropName, simPropName));
		
		Expression exp1 = new Constant(new Value(Type.integer, cluster.getIndex()));
		Expression exp2 = new Constant(new Value(Type.string, ""));
		Expression exp3 = new Constant(new Value(Type.string, propertyName));
		
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(exp3);
		setValid();
			
			
	}

	public GetProperty(SimulationCluster cluster, String methodName, String propertyName) {
		
		super();
		
		setOperation(Expression_Function.property);
		setValue(new Value(Type.simPropName, PropertyName.unknown));
		
		Expression exp1 = new Constant(new Value(Type.integer, cluster.getIndex()));
		Expression exp2 = new Constant(new Value(Type.string, methodName));
		Expression exp3 = new Constant(new Value(Type.string, propertyName));
		
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(exp3);
		setValid();
			
	}

}
