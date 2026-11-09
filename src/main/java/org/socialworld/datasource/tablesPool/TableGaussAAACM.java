/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

/**
 * The mapping from the two drawn Gaussian indices (attribute array and matrix)
 * to the Gaussian index of the matrix that is really used.
 *
 * @author Mathias Sikos
 *
 */
public class TableGaussAAACM extends Table {

	public final  String 	ALL_COLUMNS 		=	" gauss_index_aa, gauss_index_acm_drawn, gauss_index_acm ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int gauss_index_aa[];
	int gauss_index_acm_drawn[];
	int gauss_index_acm[];

	protected String getTableName() {
		return "swgauss_aa_acm";
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
		setPK1(gauss_index_aa);
		setPK2(gauss_index_acm_drawn);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		gauss_index_aa = new int[rowCount];
		gauss_index_acm_drawn = new int[rowCount];
		gauss_index_acm = new int[rowCount];

		try {
			while (rs.next()) {
				
				gauss_index_aa[row] = rs.getInt(1);
				gauss_index_acm_drawn[row] = rs.getInt(2);
				gauss_index_acm[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int gauss_index_aa, int gauss_index_acm_drawn, int gauss_index_acm) {
		String statement;
			
		statement 	= "INSERT INTO swgauss_aa_acm (gauss_index_aa, gauss_index_acm_drawn, gauss_index_acm) VALUES (" + 
				gauss_index_aa + ", " + gauss_index_acm_drawn + ", " + gauss_index_acm + ")";
		
		insert(statement);
	}

	public void update(int gauss_index_aa, int gauss_index_acm_drawn, int gauss_index_acm) {
		String statement;
			
		statement 	= "UPDATE swgauss_aa_acm SET gauss_index_acm = " + gauss_index_acm + 
				" WHERE gauss_index_aa = " + gauss_index_aa + " AND gauss_index_acm_drawn = " + gauss_index_acm_drawn;
		
		update(statement);
	}

	public void delete(int gauss_index_aa, int gauss_index_acm_drawn) {
		String statement;
			
		statement 	= "DELETE FROM swgauss_aa_acm " + 
				" WHERE gauss_index_aa = " + gauss_index_aa + " AND gauss_index_acm_drawn = " + gauss_index_acm_drawn;
		
		delete(statement);
	}

	public int getGaussIndexAA(int index) {
		return this.gauss_index_aa[index];
	}

	public int getGaussIndexACMDrawn(int index) {
		return this.gauss_index_acm_drawn[index];
	}

	public int getGaussIndexACM(int index) {
		return this.gauss_index_acm[index];
	}
	
}
