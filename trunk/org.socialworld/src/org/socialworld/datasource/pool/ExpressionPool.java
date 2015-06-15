/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.datasource.tablesPool.TablePoolExpression;

/**
 * @author Mathias Sikos
 *
 */
public class ExpressionPool {

	public static final int CAPACITY_EP_ARRAY = 1000;

	private static ExpressionPool instance;
	
	private static Expression expressions[];

	private ExpressionPool () {
		expressions = new Expression[CAPACITY_EP_ARRAY];
		
		initialize();
	}
	
	public static ExpressionPool getInstance() {
		if (instance == null) {
			instance = new ExpressionPool();
		}
		return instance;
	}

	private void setExpression(int index, Expression exp) {
		if (index >= 0)
			if (CAPACITY_EP_ARRAY > index ) 	expressions[index] = exp;
		
	}
	
	public Expression getExpression(int index) {
		if (index >= 0)
			if (CAPACITY_EP_ARRAY > index ) 	return expressions[index];
	   return null;
	}

	private void initialize() {
		
		loadFromDB();
		

	}
	
	private void loadFromDB() {
		TablePoolExpression tableExp;

		int rowCount;
		int row;
		
		int exp_id;
		
		int operation; 
		int condOp;
		int func_id;
		int value;
		int exp1_id;
		int exp2_id;
		int exp3_id;
		
		
		Expression exp;
		Expression expChild;

				
		tableExp = new TablePoolExpression();

		tableExp.select(tableExp.SELECT_ALL_COLUMNS, "", "ORDER BY exp_id");

		rowCount = tableExp.rowCount();
		
		if (rowCount > 0) {
				
			for (row = 0; row < rowCount; row++) {
				
				exp_id = tableExp.getExp(row);
				exp = new Expression();
				setExpression(exp_id, exp);
				
			}

			for (row = 0; row < rowCount; row++) {
				
				exp_id = tableExp.getExp(row);
				exp = getExpression(exp_id);
				
				operation = tableExp.getOperation(row);
				exp.setOperation(Expression_Function.getName(operation));
				
				condOp = tableExp.getConditionOperator(row);
				exp.setOperator(Expression_ConditionOperator.getName(condOp));
				
				// TODO set function
				func_id = tableExp.getFunction(row);
				
				// TODO set value
				value = tableExp.getValue(row);
				
				exp1_id = tableExp.getExp1(row);
				expChild = getExpression(exp1_id - 1);
				exp.setExpression1(expChild);
				
				exp2_id = tableExp.getExp2(row);
				expChild = getExpression(exp2_id - 1);
				exp.setExpression2(expChild);
				
				exp3_id = tableExp.getExp3(row);
				expChild = getExpression(exp3_id - 1);
				exp.setExpression3(expChild);
				
			}
		
		}
	}

}
