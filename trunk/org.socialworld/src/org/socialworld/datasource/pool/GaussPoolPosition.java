package org.socialworld.datasource.pool;


import org.socialworld.attributes.Position;
import org.socialworld.datasource.tablesPool.TableGaussPosition;

public class GaussPoolPosition {
	public static final int CAPACITY_GPPos_ARRAY = 10;

	private static GaussPoolPosition instance;
	
	private static Position positionsForPositiveIndex[];
	private static int capacityForPositiveIndex;
	private static Position positionsForNegativeIndex[];
	private static int capacityForNegativeIndex;

	private GaussPoolPosition () {
		positionsForPositiveIndex = new Position[CAPACITY_GPPos_ARRAY];
		capacityForPositiveIndex = CAPACITY_GPPos_ARRAY;

		positionsForNegativeIndex = new Position[CAPACITY_GPPos_ARRAY];
		capacityForNegativeIndex = CAPACITY_GPPos_ARRAY;
		
		
		loadFromDB();
	}

	public static GaussPoolPosition getInstance() {
		if (instance == null) {
			instance = new GaussPoolPosition();
		}
		return instance;
	}

	public Position getPosition(int index) {
		if (index >= 0)
			if (capacityForPositiveIndex > index ) 
				return positionsForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (capacityForNegativeIndex > index ) 
				return positionsForNegativeIndex[index];
			else return null;
		}	
	}
	
	private void setPosition(int index, Position position) {
		
		if (index >= 0) 
			if (index < capacityForPositiveIndex && positionsForPositiveIndex[index] == null ) 
				positionsForPositiveIndex[index] = position;
			else ;
		else {
			index = index * -1;
			if (index < capacityForNegativeIndex &&  positionsForNegativeIndex[index] == null ) 
					positionsForNegativeIndex[index] = position;
		}	
		
	}
	
	private void loadFromDB() {
		
			
			TableGaussPosition table;
			Position position;
			int rowCount;
			int row;
			
			int gauss_index;
			int pos_id;

			table = new TableGaussPosition();

			table.select(table.SELECT_ALL_COLUMNS, "", "");
			rowCount = table.rowCount();

			if (rowCount > 0) {
	
				for (row = 0; row < rowCount; row++) {
					
					gauss_index = table.getGaussIndex(row);
					pos_id = table.getPositionID(row);
					
					position = PositionPool.getInstance().getPosition(pos_id);
					setPosition(gauss_index, position);

				}		
					
			}
	}
	
}
