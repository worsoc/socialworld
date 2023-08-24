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
package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

/**
 * @author Mathias Sikos
 *
 */
public class TablePoolExpression extends Table {

	public final  String 	ALL_COLUMNS 		=	" exp_id, operation, condOp, func_id, value_id, exp1_id, exp2_id, exp3_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int exp_id[];
	int operation[];
	int condOp[];
	int func_id[];
	int value_id[];
	int exp1_id[];
	int exp2_id[];
	int exp3_id[];

	@Override
	protected String getTableName() {
		return "swpool_expression";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
		ResultSet rs;
		
		rs = connection.executeQuery(statement);
				
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			selectAllColumns( rs);
			break;
		default:
			selectAllColumns( rs);		
			
		}

		setPK1(exp_id);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		exp_id = new int[rowCount];
		operation = new int[rowCount];
		condOp = new int[rowCount];
		func_id = new int[rowCount];
		value_id = new int[rowCount];
		exp1_id = new int[rowCount];
		exp2_id = new int[rowCount];
		exp3_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				exp_id[row] = rs.getInt(1);
				operation[row] = rs.getInt(2);
				condOp[row] = rs.getInt(3);
				func_id[row] = rs.getInt(4);
				value_id[row] = rs.getInt(5);
				exp1_id[row] = rs.getInt(6);
				exp2_id[row] = rs.getInt(7);
				exp3_id[row] = rs.getInt(8);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int exp_id, int operation, int condOp, int func_id, int value_id, int exp1_id, int exp2_id, int exp3_id) {
		String statement;
			
		if (exp_id > 0)  {
			
			statement 	= "INSERT INTO swpool_expression (exp_id, operation, condOp, func_id, value_id, exp1_id, exp2_id, exp3_id) VALUES (" + 
					exp_id + ", " + operation + ", " + condOp + ", " + func_id + ", " + value_id + ", " + exp1_id + ", " + exp2_id + ", " + exp3_id +")";
			
			insert(statement);
		}
	}
	
	public void updateChildExpressions(int exp_id, int exp1_id, int exp2_id,int exp3_id) {
		String statement;
			
		if (exp_id > 0)  {
	

			statement 	= "UPDATE swpool_expression SET " +
					"exp1_id = " + exp1_id  + ", " +
					"exp2_id = " + exp2_id  + ", " +
					"exp3_id = " + exp3_id  + 
					" WHERE exp_id = " + exp_id ;
			
			update(statement);
		}
	}

	public void updateValue(int exp_id, int value_id) {
		String statement;
			
		if (exp_id > 0)  {
	

			statement 	= "UPDATE swpool_expression SET " +
					"value_id = " + value_id  + 
					" WHERE exp_id = " + exp_id ;
			
			update(statement);
		}
	}

	public void updateFunction(int exp_id, int func_id) {
		String statement;
			
		if (exp_id > 0)  {
	

			statement 	= "UPDATE swpool_expression SET " +
					"func_id = " + func_id  + 
					" WHERE exp_id = " + exp_id ;
			
			update(statement);
		}
	}

	public void updateOperation(int exp_id, int operation) {
		String statement;
			
		if (exp_id > 0)  {
	

			statement 	= "UPDATE swpool_expression SET " +
					"operation = " + operation  + 
					" WHERE exp_id = " + exp_id ;
			
			update(statement);
		}
	}

	public void updateConditionOperator(int exp_id, int condOp) {
		String statement;
			
		if (exp_id > 0)  {
	

			statement 	= "UPDATE swpool_expression SET " +
					"condOp = " + condOp  + 
					" WHERE exp_id = " + exp_id ;
			
			update(statement);
		}
	}

	public void delete(int exp_id) {
		String statement;
			
		if (exp_id > 0)  {
	
			statement 	= "DELETE FROM swpool_expression " +
					" WHERE exp_id = " + exp_id ;
						
			delete(statement);
		}
	}

	public int getExp(int index) {
		return exp_id[index];
	}

	public int getOperation(int index) {
		return this.operation[index];
	}

	public int getConditionOperator(int index) {
		return condOp[index];
	}

	public int getFunction(int index) {
		return func_id[index];
	}

	public int getValue(int index) {
		return value_id[index];
	}

	public int getExp1(int index) {
		return exp1_id[index];
	}

	public int getExp2(int index) {
		return exp2_id[index];
	}

	public int getExp3(int index) {
		return exp3_id[index];
	}

}
