package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Dimension;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.datasource.tablesSimulation.propertySets.TableColourSet;
import org.socialworld.objects.concrete.StateAppearance;

public class TableStateAppearance extends Table {



	public final  String 	ALL_COLUMNS 		=	" id, width_m, width_mm, height_m, height_mm, depth_m, depth_mm, "
			+ "colour_set_id_1, colour_set_id_2, colour_set_id_3, colour_set_id_4, colour_set_id_5, colour_set_id_6, colour_set_id_7, "
			+ "colour_set_id_8, colour_set_id_9, colour_set_id_10, colour_set_id_11, colour_set_id_12, colour_set_id_13, colour_set_id_14, "
			+ "colour_set_id_15 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	private final int STATE_APPEARANCE_ARRAY_MAX_COUNT = 100000;
	
	private static TableStateAppearance instanceMain;
	private int[] mapId2Index;

	public static TableStateAppearance getInstance() {
		if (instanceMain == null) {
				instanceMain = new TableStateAppearance();
		}
		return instanceMain;
	}

	private TableStateAppearance() {
			mapId2Index = new int[STATE_APPEARANCE_ARRAY_MAX_COUNT];
			load();
	}
	
	int id[];
	short width_m[];
	short width_mm[];
	short height_m[];
	short height_mm[];
	short depth_m[];
	short depth_mm[];
	int colour_set_id_1[];
	int colour_set_id_2[];
	int colour_set_id_3[];
	int colour_set_id_4[];
	int colour_set_id_5[];
	int colour_set_id_6[];
	int colour_set_id_7[];
	int colour_set_id_8[];
	int colour_set_id_9[];
	int colour_set_id_10[];
	int colour_set_id_11[];
	int colour_set_id_12[];
	int colour_set_id_13[];
	int colour_set_id_14[];
	int colour_set_id_15[];

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
		width_mm = new short[rowCount];
		height_m = new short[rowCount];
		height_mm = new short[rowCount];
		depth_m = new short[rowCount];
		depth_mm = new short[rowCount];
		colour_set_id_1 = new int[rowCount];
		colour_set_id_2 = new int[rowCount];
		colour_set_id_3 = new int[rowCount];
		colour_set_id_4 = new int[rowCount];
		colour_set_id_5 = new int[rowCount];
		colour_set_id_6 = new int[rowCount];
		colour_set_id_7 = new int[rowCount];
		colour_set_id_8 = new int[rowCount];
		colour_set_id_9 = new int[rowCount];
		colour_set_id_10 = new int[rowCount];
		colour_set_id_11 = new int[rowCount];
		colour_set_id_12 = new int[rowCount];
		colour_set_id_13 = new int[rowCount];
		colour_set_id_14 = new int[rowCount];
		colour_set_id_15 = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				width_m[row] = rs.getShort(2);
				width_mm[row] = rs.getShort(3);
				height_m[row] = rs.getShort(4);
				height_mm[row] = rs.getShort(5);
				depth_m[row] = rs.getShort(6);
				depth_mm[row] = rs.getShort(7);
				colour_set_id_1[row] = rs.getInt(8);
				colour_set_id_2[row] = rs.getInt(9);
				colour_set_id_3[row] = rs.getInt(10);
				colour_set_id_4[row] = rs.getInt(11);
				colour_set_id_5[row] = rs.getInt(12);
				colour_set_id_6[row] = rs.getInt(13);
				colour_set_id_7[row] = rs.getInt(14);
				colour_set_id_8[row] = rs.getInt(15);
				colour_set_id_9[row] = rs.getInt(16);
				colour_set_id_10[row] = rs.getInt(17);
				colour_set_id_11[row] = rs.getInt(18);
				colour_set_id_12[row] = rs.getInt(19);
				colour_set_id_13[row] = rs.getInt(20);
				colour_set_id_14[row] = rs.getInt(21);
				colour_set_id_15[row] = rs.getInt(22);
				
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
			
		if (id > 0 && colourSetColumnNr > 0 && colourSetColumnNr <= StateAppearance.COUNT_COLOUR_SETS) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"colour_set_id_" + colourSetColumnNr + " = " + coloursetid  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}
	
	public void updateHeight(int id, short height_m, short height_mm) {
		String statement;
			
		if (id > 0) {
			statement 	= "UPDATE " + getTableName() + " SET " +
					"height_m = " + height_m  + ", height_mm = " + height_mm + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateWidth(int id, short width_m, short width_mm) {
		String statement;
			
		if (id > 0) {
			statement 	= "UPDATE " + getTableName() + " SET " +
					"width_m = " + width_m  + ", width_mm = " + width_mm + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateDepth(int id, short depth_m, short depth_mm) {
		String statement;
			
		if (id > 0) {
			statement 	= "UPDATE " + getTableName() + " SET " +
					"depth_m = " + depth_m  + ", depth_mm = " + depth_mm + " " +
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

	private int getColourSetID(int index, int colourSetColumnNumber) {
		if (index < 0  || index >= id.length) return 0;

		switch (colourSetColumnNumber) {
		case 1: return colour_set_id_1[index];
		case 2: return colour_set_id_2[index];
		case 3: return colour_set_id_3[index];
		case 4: return colour_set_id_4[index];
		case 5: return colour_set_id_5[index];
		case 6: return colour_set_id_6[index];
		case 7: return colour_set_id_7[index];
		case 8: return colour_set_id_8[index];
		case 9: return colour_set_id_9[index];
		case 10: return colour_set_id_10[index];
		case 11: return colour_set_id_11[index];
		case 12: return colour_set_id_12[index];
		case 13: return colour_set_id_13[index];
		case 14: return colour_set_id_14[index];
		case 15: return colour_set_id_15[index];
		default:
			return 0;
		}
		
	}
/*	
	public short getWidthInMeters(int index) {
		if (index >= 0 && index < id.length) {
			return width_m[index];
		}
		else return 0;
	}

	public short getWidthMilliMeters(int index) {
		if (index >= 0 && index < id.length) {
			return width_mm[index];
		}
		else return 0;
	}

	public short getHeightInMeters(int index) {
		if (index >= 0 && index < id.length) {
			return height_m[index];
		}
		else return 0;
	}
	
	public short getHeightInMilliMeters(int index) {
		if (index >= 0 && index < id.length) {
			return height_mm[index];
		}
		else return 0;
	}

	public short getDepthInMeters(int index) {
		if (index >= 0 && index < id.length) {
			return depth_m[index];
		}
		else return 0;
	}
	
	public short getDepthInMilliMeters(int index) {
		if (index >= 0 && index < id.length) {
			return depth_mm[index];
		}
		else return 0;
	}

	public int loadForObjectID(int objectID) {
		
			select(SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
	
			int row = getIndexFor1PK(objectID);
			return row;
		
	}
*/
	public ColourSet getColourSetFromRow (int row, int colourSetColumnNumber ) {
		
		int setID;
		ColourSet set = ColourSet.getObjectNothing();
		if (row >= 0 && row < id.length) {
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > TableStateAppearance.getColourSetFromRow() Start " + ActualTime.asTime().toString());
			TableColourSet tableSet = TableColourSet.getInstance();
			setID = getColourSetID(row, colourSetColumnNumber);
			if (setID > 0) set = tableSet.getColourSet(setID);
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > TableStateAppearance.getColourSetFromRow() Ende " + ActualTime.asTime().toString());
		}
		return set;
	}
	
	public Dimension getDimensionFromRow(int row) {
		Dimension dimension = Dimension.getObjectNothing();
		if (row >= 0 && row < id.length) {
			dimension = new Dimension(PropertyName.stateAppearance_dimension, 
				height_m[row], height_mm[row], width_m[row], width_mm[row], depth_m[row], depth_mm[row]);
		}
		return dimension;
	}
	
	
	private void load() {
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableStateAppearance.load() Start " + ActualTime.asTime().toString());
			long lockingID = lockWithWait();
			
			select(SELECT_ALL_COLUMNS, "", " ORDER BY id"); 
			
			if (this.id.length > 0) {
				for (int index = 0; index < this.id.length; index++) {
					mapId2Index[this.id[index]] = index;
				}	
			}
			
			unlock(lockingID);
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableStateAppearance.load() Ende " + ActualTime.asTime().toString());
	}
	
	public int getRowForID(int id) {
		if (mapId2Index.length >  id) {
			return mapId2Index[id];
		}
		else {
			return -1;
		}
	}

}
