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
package org.socialworld.datasource.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathias Sikos
 *
 */
public abstract class Table {

		MariaDBConnection connection;
		int selectList;
		int rowCount;
		int rowsAffected;
		
		int pk1[];
		int pk2[];
		
		public Table() {
			connection = new MariaDBConnection();
		}
		
		
		protected  abstract String getTableName();
		protected  abstract String getSelectList(int selectList);
		public abstract void select(String statement);
		
		public void close() {
			connection.close();
		}

		public void open() {
			connection = new MariaDBConnection();
		}

		public boolean executionWasSuccessful() {
			return (rowsAffected >= 0);
		}
		
		public int countAffectedRows() {
			return rowsAffected;
		}

		public void select(int selectList) {
			select(selectList, "", "");
		}
		
		public void select(int selectList, String where_clause, String orderby) {
			String select;
			String count_select;
			String select_list;
			String from_clause;
			
			ResultSet rs;
			
			this.selectList = selectList;
			
			from_clause = " FROM " + getTableName();
			select_list = "SELECT " + getSelectList(selectList)  ;
			
			count_select = "SELECT count(*) " + from_clause + " " + where_clause ;
			rs = connection.executeQuery(count_select);
			
			try 		{	
				if (rs.next())			rowCount = rs.getInt(1); 
			}
			catch (SQLException e) {
				e.printStackTrace();
				rowCount = -1 ;
				
				return;
			}

			try 		{	
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		
			select = select_list + " " + from_clause  + " " + where_clause + " " + orderby;
			
			select(select);
		}
		
		public int rowCount() { return rowCount; }
		
		void insert(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}
		
		void update(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}
		
		void delete(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}

		public void setPK1(int pk[]) {
			this.pk1 = pk;
		}
	
		public void setPK2(int pk[]) {
			this.pk2 = pk;
		}

		public int getIndexFor1PK(int pk1) {
			int size;
			int index;
			
			size = this.pk1.length;
			
			for (index = 0; index < size; index++) {
				if (this.pk1[index] == pk1) return index;
			}
			
			return -1;
		}

		public int getIndexFor2PK(int pk1, int pk2) {
			int size;
			int index;
			
			size = this.pk1.length;
			
			for (index = 0; index < size; index++) {
				if (this.pk1[index] == pk1 &  this.pk2[index] == pk2 ) return index;
			}
			
			return -1;
		}

		public int[] getAllPK2ForPK1(int pk1) {
			int size;
			int index;
			
			int result[];
			int count = 0;
			int indexResult = 0;
			
			size = this.pk1.length;
			
			for (index = 0; index < size; index++) {
				if (this.pk1[index] == pk1) count++;
			}
			
			result = new int[count];
			for (index = 0; index < size; index++) {
				if (this.pk1[index] == pk1) {
					result[indexResult] = this.pk2[index];
					indexResult++;
				}
			}
			
			return result;
		}

}
