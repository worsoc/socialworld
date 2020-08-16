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
package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.DescriptionBase;

public abstract class DescriptionPool {

	protected int rangeMainIndex;
	protected int rangeSecondIndex;
	protected int sizeDescriptionsArray;

	protected DescriptionBase _descriptions[];
	protected List<List<FunctionByExpression>> _expressions;

	protected DescriptionPool(int rangeMainIndex, int rangeSecondIndex) {
		
		this.rangeMainIndex = rangeMainIndex;
		this.rangeSecondIndex = rangeSecondIndex;
		sizeDescriptionsArray = rangeMainIndex * rangeSecondIndex;

		_expressions = new ArrayList<List<FunctionByExpression>>(sizeDescriptionsArray);
		ArrayList<FunctionByExpression> nothing;
		nothing = new  ArrayList<FunctionByExpression>();
		for (int i = 0; i < sizeDescriptionsArray; i++) {
			_expressions.add(nothing);
		}


	}
	
	protected abstract DescriptionBase getNewDescription();
	protected abstract Expression getStartExpression(List<String> lines4OneExpression);
//	protected abstract void initialize();
	protected abstract void initializeWithTestData_FunctionByExpression();

	protected void initialize() {

		int index;
		
		DescriptionBase description;
		List<FunctionByExpression> oneDescriptionExpressions;
		
		initializeWithTestData_FunctionByExpression();
//		initializeFromFile();

		for (int mainIndex = 0; mainIndex < rangeMainIndex; mainIndex++) {
		
			for (int secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				
				index = mainIndex * rangeSecondIndex + secondIndex;
				oneDescriptionExpressions = _expressions.get(index);
				
				description = getNewDescription();
				
				if (oneDescriptionExpressions != null) {
					
					for (int i = 0; i < oneDescriptionExpressions.size(); i++) {
						description.addFunction(oneDescriptionExpressions.get(i));
					}
					
				}
				
				_descriptions[index] = description;
		
			}
			
		}
			
	}

	public DescriptionBase getDescription(int mainIndex,	int secondIndex) {
		int index;

		DescriptionBase description = null;
		
		index = mainIndex *  rangeSecondIndex + secondIndex ;
		
		if (index >= 0 & sizeDescriptionsArray > index) 	
			description = _descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = getNewDescription();
			
		return description;
	}
	
	protected final void bla(List<Lines> allLines) {
		
		int indexExpressions;
		int i;
		Expression startExpression;
		List<FunctionByExpression> oneDescriptionExpressions;
		List<List<String>> lines4OneDescriptionExpressions;
		Lines lines4EventType;
		int secondIndex;
		
		for (int index = 0; index < allLines.size(); index++ ) {
			lines4EventType = allLines.get(index);
			indexExpressions = lines4EventType.getEventType().getIndex() * rangeSecondIndex;
			for ( secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				lines4OneDescriptionExpressions = lines4EventType.getLines(secondIndex);
				oneDescriptionExpressions = new ArrayList<FunctionByExpression>();
				for (i = 0; i < lines4OneDescriptionExpressions.size(); i++) {
					startExpression = getStartExpression(lines4OneDescriptionExpressions.get(i));
					oneDescriptionExpressions.add(new FunctionByExpression(startExpression));
				}
				this._expressions.set(indexExpressions + secondIndex, oneDescriptionExpressions);
			}
		}
	
	}
	
/*
	protected void addExpression(Expression expression) {
		int ID;
	
		ID = expression.getID();
		if (ID > sizeExpressionsArray) ensureCapacity(ID);
		
		expressions.set(ID - 1, expression);
	}
	
	protected void ensureCapacity(int capacity) {
		if (capacity > sizeExpressionsArray) {
			for (int i = 1; i <= capacity - sizeExpressionsArray; i++)
				expressions.add(null);
			sizeExpressionsArray = capacity;
		}
	}

	protected void setTrueAndFalseExpressions() {
		ListIterator<Expression> iterator;
		Expression expression;

		int IDTrue;
		int IDFalse;
		
		iterator = expressions.listIterator();
		
		while (iterator.hasNext()) {
			expression = iterator.next();
			
			if (expression.getID() > 0 ) {
				IDTrue = expression.get_ID_Exp2();
				IDFalse = expression.get_ID_Exp3();
				
				if (IDTrue > 0) {
					expression.setTrueExpression(expressions.get(IDTrue - 1));
				}
				if (IDFalse > 0) {
					expression.setFalseExpression(expressions.get(IDFalse - 1));
				}
			}
		}
	}
*/
}
