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
package org.socialworld.datasource.mariaDB;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Mathias Sikos
 *
 */
public abstract class Table {


	
		protected ConnectionMariaDB connection;
		protected int selectList;
		protected int rowCount;
		protected int rowsAffected;
		
		boolean isLocked = false;
		long lockingID = 1;
		
		protected int pk1[];
		protected int pk2[];
		
		public Table() {
			connection = new ConnectionMariaDB();
		}
		
		
		protected  abstract String getTableName();
		protected  abstract String getSelectList(int selectList);
		public abstract void select(String statement);
		
		public void close() {
			connection.close();
		}

		public void open() {
			connection = new ConnectionMariaDB();
		}

		public long lock() {
			if (isLocked) {
				return 0;
			}
			else {
				lockingID++;
				isLocked = true;
				return lockingID;
			}
		}
		
		public boolean unlock(long lockingID) {
			if (lockingID == this.lockingID) {
				isLocked = false;
				return true;
			}
			else {
				return false;
			}
		}
		
		protected boolean isLocked() {
			return this.isLocked;
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
			select(false,  selectList,  where_clause,  orderby);
		}

		public void selectDistinct(int selectList, String where_clause, String orderby) {
			select(true,  selectList,  where_clause,  orderby);
		}

		private void select(boolean withDistinct, int selectList, String where_clause, String orderby) {
			String select;
			String count_select;
			String select_list;
			String from_clause;
			
			ResultSet rs;
			
			this.selectList = selectList;
			
			from_clause = " FROM " + getTableName();
			
			select_list = getSelectList(selectList);
			if (withDistinct) {
				count_select = "SELECT count(DISTINCT " + select_list +") " + from_clause + " " + where_clause ;
				select_list = "SELECT DISTINCT " + select_list  ;
			}
			else {
				count_select = "SELECT count(*) " + from_clause + " " + where_clause ;
				select_list = "SELECT " + select_list  ;
			}
			
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
		
		private int selectScalarInt(String completeSelect) {
			
			int result = 0;
			ResultSet rs;
			rs = connection.executeQuery(completeSelect);
			
			try 		{	
				if (rs.next())			result = rs.getInt(1); 
			}
			catch (SQLException e) {
				e.printStackTrace();
				rowCount = -1 ;
				
				return 0;
			}

			try 		{	
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		
			return result;
		}

		public int rowCount() { return rowCount; }
		
		public int getNewID(String tableName, String idName) {
			String selectGetNewId = "SELECT IFNULL(max(" + idName + "), 0) + 1 FROM " + tableName;
			return selectScalarInt(selectGetNewId);
		}

		public int getNewID(String idName) {
			String selectGetNewId = "SELECT IFNULL(max(" + idName + "), 0) + 1 FROM " + getTableName();
			return selectScalarInt(selectGetNewId);
		}

		protected void insert(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}
		
		protected void update(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}
		
		protected void delete(String statement) {
			rowsAffected = connection.executeUpdate(statement);
		}

		public void setPK1(int pk[]) {
			this.pk1 = pk;
		}
	
		public void setPK2(int pk[]) {
			this.pk2 = pk;
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

		public int[] getAllPK1() {
			int size;
			int index;
			
			int result[];
			
			size = this.pk1.length;
			
			result = new int[size];
			for (index = 0; index < size; index++) {
					result[index] = this.pk1[index];
			}
			
			return result;
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
}
