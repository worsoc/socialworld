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

import java.sql.*;

/**
 * @author Mathias Sikos
 *
 */
public class MariaDBConnection {

	final int ER_DUP_ENTRY = -1062;
	final int ER_DUP_KEY = -1022;
	final int ER_WRONG_VALUE_COUNT = -1058;
	final int ER_BAD_NULL_ERROR = -1048;
	final int ER_KEY_NOT_FOUND = -1032;
	final int ER_BAD_FIELD_ERROR = -1054;
	final int ER_NO_SUCH_TABLE = -1146;
	
	Connection connection;
	Statement stmt;
	
	public MariaDBConnection() {
		
		try {		
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sw1", "sw", "sw");	
		}
		catch (Exception e) {
			e.printStackTrace();
			return;	
		}
	
		try 		{
			stmt = connection.createStatement();
		}
		catch (SQLException e) {
			
			e.printStackTrace();
			return;
		}

	}
	
	public void close() {
		
		try 		{
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace(); 
			return;
		}

		
		try 		{
			connection.close();
		}
		catch (SQLException e) { 
			e.printStackTrace(); 
			return;
		}
		
	}

	public ResultSet executeQuery(String selectStatement) {
		ResultSet rs;
		
		try 		{	
			rs = stmt.executeQuery(selectStatement); 
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return rs;
	}

	public int executeUpdate(String updateStatement) {
		int rowsAffected = 0;
		try 		{	
			rowsAffected = stmt.executeUpdate(updateStatement); 
		}
		catch (SQLException e) {
			e.printStackTrace();
			rowsAffected = -e.getErrorCode();
		}
		
		return rowsAffected;
	}

	boolean checkIsDuplicateError(int errorCode) {
		return (errorCode == ER_DUP_ENTRY | errorCode == ER_DUP_KEY) ;
	}
	
	boolean checkIsColumnCountDoesntMatchValueCount(int errorCode) {
		return (errorCode == ER_WRONG_VALUE_COUNT);
	}
	
	boolean checkIsKeyNotFound(int errorCode) {
		return (errorCode == ER_KEY_NOT_FOUND);
	}

	boolean checkIsBadTable(int errorCode) {
		return (errorCode == ER_NO_SUCH_TABLE);
	}

	boolean checkIsBadColumns(int errorCode) {
		return (errorCode == ER_BAD_FIELD_ERROR);
	}
	

}
