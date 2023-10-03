package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.datasource.tablesSimulation.propertySets.TableColourSet;

public class TableStateAppearance extends Table {

	private static List<TableStateAppearance> instances = new ArrayList<TableStateAppearance>();

	public static TableStateAppearance getInstance() {
		for ( TableStateAppearance instance : instances) {
			if (!instance.isLocked()) {
				return instance;
			}
		}
		TableStateAppearance newInstance = new TableStateAppearance();
		instances.add(newInstance);
		return newInstance;
	}
	
	public final  String 	ALL_COLUMNS 		=	" id, width_m, width_cm, height_m, height_cm, colour_set_id_1, colour_set_id_2, colour_set_id_3, colour_set_id_4, colour_set_id_5, colour_set_id_6, colour_set_id_7 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	short width_m[];
	byte width_cm[];
	short height_m[];
	byte height_cm[];
	int colour_set_id_1[];
	int colour_set_id_2[];
	int colour_set_id_3[];
	int colour_set_id_4[];
	int colour_set_id_5[];
	int colour_set_id_6[];
	int colour_set_id_7[];

	@Override
	protected String getTableName() {
		return "swstate_appearance";
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
			selectAllColumns(rs);

			break;
		default:
			selectAllColumns(rs);
		}

		setPK1(id);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		width_m = new short[rowCount];
		width_cm = new byte[rowCount];
		height_m = new short[rowCount];
		height_cm = new byte[rowCount];
		colour_set_id_1 = new int[rowCount];
		colour_set_id_2 = new int[rowCount];
		colour_set_id_3 = new int[rowCount];
		colour_set_id_4 = new int[rowCount];
		colour_set_id_5 = new int[rowCount];
		colour_set_id_6 = new int[rowCount];
		colour_set_id_7 = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				width_m[row] = rs.getShort(2);
				width_cm[row] = rs.getByte(3);
				height_m[row] = rs.getShort(4);
				height_cm[row] = rs.getByte(5);
				colour_set_id_1[row] = rs.getInt(6);
				colour_set_id_2[row] = rs.getInt(7);
				colour_set_id_3[row] = rs.getInt(8);
				colour_set_id_4[row] = rs.getInt(9);
				colour_set_id_5[row] = rs.getInt(10);
				colour_set_id_6[row] = rs.getInt(11);
				colour_set_id_7[row] = rs.getInt(12);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (id) VALUES (" + id + ")";
			
			insert(statement);
		}
	}

	public void updateColourSetID(int id, int colourSetColumnNr, int coloursetid) {
		String statement;
			
		if (id > 0 && colourSetColumnNr > 0 && colourSetColumnNr < 8) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"colour_set_id_" + colourSetColumnNr + " = " + coloursetid  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}
	

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM " + getTableName() + " WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	public int getColourSetID(int index, int colourSetColumnNumber) {
		switch (colourSetColumnNumber) {
		case 1: return colour_set_id_1[index];
		case 2: return colour_set_id_2[index];
		case 3: return colour_set_id_3[index];
		case 4: return colour_set_id_4[index];
		case 5: return colour_set_id_5[index];
		case 6: return colour_set_id_6[index];
		case 7: return colour_set_id_7[index];
		default:
			return 0;
		}
		
	}
	
	public short getWidthInMeters(int index) {
			return width_m[index];
	}

	public byte getWidthInCentiMeters(int index) {
		return width_cm[index];
	}

	public short getHeightInMeters(int index) {
		return height_m[index];
	}
	
	public byte getHeightInCentiMeters(int index) {
		return height_cm[index];
	}

	public int loadForObjectID(int objectID) {

		select(SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");

		int row = getIndexFor1PK(objectID);
		return row;
		
	}

	public ColourSet getColourSetFromRow (int row, int colourSetColumnNumber ) {
		
		int setID;
		ColourSet set = ColourSet.getObjectNothing();
		if (row >= 0) {
			TableColourSet tableSet = new TableColourSet();
			setID = getColourSetID(row, colourSetColumnNumber);
			if (setID > 0) set = tableSet.getColourSet(setID);
		}
		return set;
	}
	
	
	
}
