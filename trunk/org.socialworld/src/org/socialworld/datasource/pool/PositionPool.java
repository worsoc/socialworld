package org.socialworld.datasource.pool;

import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.datasource.tablesPool.TablePoolPosition;;

public class PositionPool {

	public static final int CAPACITY_PP_ARRAY = 1000;

	private static PositionPool instance;
	
	private static Position positions[];
	
	private PositionPool () {
		positions = new Position[CAPACITY_PP_ARRAY];
		
		initialize();
	}

	public static PositionPool getInstance() {
		if (instance == null) {
			instance = new PositionPool();
		}
		return instance;
	}
	
	private void setPosition(int index, Position position) {
		if (index >= 0)
			if (CAPACITY_PP_ARRAY > index ) 	positions[index] = position;
		
	}
	
	public Position getPosition(int index) {
		if (index >= 0)
			if (CAPACITY_PP_ARRAY > index ) 	return positions[index];
	   return null;
	}
	
	private void initialize() {
		
		loadFromDB();
		
	}

	private void loadFromDB() {
		TablePoolPosition table;

		int rowCount;
		int row;
		
		int pos_id;
		int x;
		int y;
		int z;
		
		table = new TablePoolPosition();
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY pos_id");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			for (row = 0; row < rowCount; row++) {
				
				pos_id = table.getPositionID(row);
				x = table.getX(row);
				y = table.getY(row);
				z = table.getZ(row);
	
				setPosition(pos_id, new Position(PropertyName.unknown, new Vector (x, y, z)));

			}
		}

	}
	
}
