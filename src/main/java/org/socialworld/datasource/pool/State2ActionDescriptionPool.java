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
package org.socialworld.datasource.pool;

import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.datasource.tablesPool.TablePoolS2A;

/**
 * The state-to-action rules, one description per state-to-action type, read from swpool_s2a.
 * The type of a human or an animal is the number of its character - 1 (CreateAnimal).
 * The rules are written by the DCT (State2ActionGenerator).
 */
public class State2ActionDescriptionPool extends DescriptionPool {
	

	private static State2ActionDescriptionPool instance;
	
	
	private State2ActionDescriptionPool () {
		
		super(1, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		
		this.descriptions = new State2ActionDescription[sizeDescriptionsArray];
		
		initialize();
		
	}
	
	public static State2ActionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new State2ActionDescriptionPool();
		}
		return instance;
	}
	
	protected final void initialize() {
		initializeFromJson();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		
		switch (modus) {
		case json:
			initializeFromDatabase();
			break;
		default:
			// the rules in lines are gone (2026-11-18), they are in swpool_s2a now
		}
	}

	protected  final DescriptionBase getNewDescription() {
		return new State2ActionDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new State2ActionDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return CreateActionExpression.createActionExpression(lines4OneExpression);
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
	}
	
	private void initializeFromDatabase() {
		TablePoolS2A table = new TablePoolS2A();
		int countLoaded = 0;

		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY state2ActionType");
		int rowCount = table.rowCount();

		for (int row = 0; row < rowCount; row++) {
			int state2ActionType = table.getState2ActionType(row);
			String json = table.getJsonS2A(row);

			if (state2ActionType < 0 || state2ActionType >= rangeSecondIndex) {
				System.err.println("State2ActionDescriptionPool: swpool_s2a has type " + state2ActionType
						+ ", outside 0.." + (rangeSecondIndex - 1) + "; the row is ignored");
				continue;
			}
			if (json == null || json.isEmpty()) continue;

			DescriptionBase description = getDescription(json);
			description.setFunctions();
			descriptions[state2ActionType] = description;
			countLoaded++;
		}

		// types without a row get an empty description: no action from the state
		for (int index = 0; index < sizeDescriptionsArray; index++) {
			if (descriptions[index] == null) descriptions[index] = getNewDescription();
		}

		if (countLoaded == 0) {
			System.err.println("State2ActionDescriptionPool: no rules in swpool_s2a, so no object acts from its state."
					+ " Write them with the DCT (button 'S2A DB').");
		}
		else {
			System.out.println("State2ActionDescriptionPool: " + countLoaded + " of " + rangeSecondIndex
					+ " state-to-action types loaded from swpool_s2a");
		}
	}
	
}
