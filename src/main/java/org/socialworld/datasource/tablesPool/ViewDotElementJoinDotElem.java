package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class ViewDotElementJoinDotElem extends Table {

	public final  String 	ALL_COLUMNS 		=	" dot_element_id, dotelem_function, dotelem_value_name, dotelem_addon, dotelem_addon_intarg, dotelem_addon_charsarg, dot_elem_line_id, lfd_nr ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int dot_element_id[];
	int dotelem_function[]; 		
	String dotelem_value_name[]; 
	int dotelem_addon[]; 
	int dotelem_addon_intarg[]; 
	String dotelem_addon_charsarg[]; 
	int dot_elem_line_id[];
	int lfd_nr[]; 		

	@Override
	protected String getTableName() {
		return "sw_v_dotelemline";
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

	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		

		dot_element_id = new int[rowCount];
		dotelem_function = new int[rowCount];
		dotelem_value_name = new String[rowCount];
		dotelem_addon = new int[rowCount];
		dotelem_addon_intarg = new int[rowCount];
		dotelem_addon_charsarg = new String[rowCount];
		dot_elem_line_id = new int[rowCount];
		lfd_nr = new int[rowCount];

		try {
			while (rs.next()) {
				
				dot_element_id[row] = rs.getInt(1);
				dotelem_function[row] = rs.getInt(2);
				dotelem_value_name[row] = rs.getString(3);
				dotelem_addon[row] = rs.getInt(4);
				dotelem_addon_intarg[row] = rs.getInt(5);
				dotelem_addon_charsarg[row] = rs.getString(6);
				dot_elem_line_id[row] = rs.getInt(7);
				lfd_nr[row] = rs.getInt(8);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}


	public int getDotElementID(int index) {
		return dot_element_id[index];
	}

	public int getFunction(int index) {
		return dotelem_function[index];
	}

	public String getValueName(int index) {
		return dotelem_value_name[index];
	}
	
	public int getAddon(int index) {
		return dotelem_addon[index];
	}

	public int getAddon_IntArg(int index) {
		return dotelem_addon_intarg[index];
	}
	
	public String getAddon_CharsArg(int index) {
		return dotelem_addon_charsarg[index];
	}

	public int getDotElemLineID(int index) {
		return dot_elem_line_id[index];
	}

	public int getLfdNr(int index) {
		return lfd_nr[index];
	}
	

}
