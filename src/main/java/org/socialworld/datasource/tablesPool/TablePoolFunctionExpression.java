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
public class TablePoolFunctionExpression extends Table {

	public final  String 	ALL_COLUMNS 		=	" func_id, exp_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int func_id[];
	int exp_id[]; 		
	
	@Override
	protected String getTableName() {
		return "swpool_function_expression";
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
		setPK1(func_id);

	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		func_id = new int[rowCount];
		exp_id = new int[rowCount];
	
		try {
			while (rs.next()) {
				
				func_id[row] = rs.getInt(1);
				exp_id[row] = rs.getInt(2);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void insert(int func_id, int exp_id) {
		String statement;
			
		if ((func_id > 0) & (exp_id > 0)) {
			
			statement 	= "INSERT INTO swpool_function_expression (func_id, exp_id) VALUES (" + 
							func_id + ", " + exp_id  + ")";
			
			insert(statement);
		}
	}
	
	public void update(int func_id, int exp_id) {
		String statement;
			
		if ((func_id > 0) & (exp_id > 0) ) {

			statement 	= "UPDATE swpool_function_expression SET " +
					"exp_id = " + exp_id  + 
					" WHERE func_id = " + func_id ;
			
			update(statement);
		}
	}


	public void delete(int func_id) {
		String statement;
			
		if ( func_id > 0)  {
	
			statement 	= "DELETE FROM swpool_function_expression " +
					" WHERE func_id = " + func_id ;
						
			delete(statement);
		}
	}

	public int getFunctionID(int index) {
		return this.func_id[index];
	}

	public int getExpressionID(int index) {
		return exp_id[index];
	}


}
