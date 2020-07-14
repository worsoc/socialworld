/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class AddOrSetValuesToArguments extends Expression {

	public AddOrSetValuesToArguments(List<Expression> listExpressions) {
		
		super();
		
		List<String> names = new ArrayList<String>();
		
		for (int index = 0; index < listExpressions.size(); index++) {
			names.add("");
		}

		init("", listExpressions, names);
		
		setOperation(Expression_Function.oneExpression);
		setValid();
	}

	public AddOrSetValuesToArguments(String nameSubList, List<Expression> listExpressions) {
		
		super();
		
		List<String> names = new ArrayList<String>();
		
		for (int index = 0; index < listExpressions.size(); index++) {
			names.add("");
		}

		init(nameSubList, listExpressions, names);
		
		setOperation(Expression_Function.oneExpression);
		setValid();
	}

	public AddOrSetValuesToArguments(List<Expression> listExpressions, List<String> names) {
		
		super();
			
		init("", listExpressions, names);
		
		setOperation(Expression_Function.oneExpression);
		setValid();
		
	}

	private void init(String nameSubList,  List<Expression> listExpressions, List<String> names) {
		
		Expression expValue;
		String name;
		Expression expAddValue;
		Expression[] sequence = new Expression[2];
		
		Expression expSequence = Nothing.getInstance();
		
		for (int index = listExpressions.size() - 1; index >= 0; index--) {
			expValue = listExpressions.get(index);
			name = names.get(index);
			if (nameSubList.length() > 0) {
				expAddValue = new Replacement(nameSubList, name, expValue);
			}
			else {
				expAddValue = new Replacement(name, expValue);
			}
			sequence[0] = expAddValue;
			sequence[1] = expSequence;
			expSequence = new Sequence(sequence);
		}

		
		setExpression1(expSequence);

	}
	


}
