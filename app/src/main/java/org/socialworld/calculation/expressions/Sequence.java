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

import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class Sequence extends Expression {

	public Sequence(Expression[] expressions) {
		
		init(expressions);
		
	}

	public Sequence(List<Expression> expressions) {
		
		Expression[] expressionsAsArray = expressions.toArray(new Expression[0]);
		init(expressionsAsArray);
		
	}
	
	private void init(Expression[] expressions) {
		
		Expression[] expressionsA;
		Expression[] expressionsB;
		Expression[] expressionsC;
		
		int index;
		int count = expressions.length;
		
		
		if (count > 6) {
			
			expressionsA = new Expression[3];
			expressionsA[0] = expressions[0];
			expressionsA[1] = expressions[1];
			expressionsA[2] = expressions[2];
			
			expressionsB = new Expression[3];
			expressionsB[0] = expressions[3];
			expressionsB[1] = expressions[4];
			expressionsB[2] = expressions[5];
			
			expressionsC = new Expression[count - 6];
			for (index = 0; index < count - 6; index++ ) {
				expressionsC[index] = expressions[index + 6];
			}
			
			setExpression1(new Sequence(expressionsA));
			setExpression2(new Sequence(expressionsB));
			setExpression3(new Sequence(expressionsC));
			
		}
		else if (count > 3) {
			
			expressionsB = new Expression[3];
			expressionsB[0] = expressions[3];
			expressionsB[1] = expressions[4];
			expressionsB[2] = expressions[5];
			
			expressionsC = new Expression[count - 3];
			for (index = 0; index < count - 3; index++ ) {
				expressionsC[index] = expressions[index + 3];
			}
			
			setExpression1(Nothing.getInstance());
			setExpression2(new Sequence(expressionsB));
			setExpression3(new Sequence(expressionsC));
			
		}
		else {
			if (count == 3) {
				setExpression1(expressions[0]);
				setExpression2(expressions[1]);
				setExpression3(expressions[2]);
			}
			if (count == 2) {
				setExpression1(Nothing.getInstance());
				setExpression2(expressions[0]);
				setExpression3(expressions[1]);
			}
			if (count == 1) {
				setExpression1(Nothing.getInstance());
				setExpression2(Nothing.getInstance());
				setExpression3(expressions[0]);
			}
			if (count == 0) {
				setExpression1(Nothing.getInstance());
				setExpression2(Nothing.getInstance());
				setExpression3(Nothing.getInstance());
			}
		}
		
		
		setOperation(Expression_Function.sequence);
		setValid();
		
	}
	
	
	
}
