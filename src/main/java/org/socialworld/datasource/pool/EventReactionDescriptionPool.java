/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.core.EventType;
import org.socialworld.datasource.tablesPool.TablePoolERD;

/**
 * The event reaction rules, one description per pair of event type and reaction type, read from swpool_erd
 * (erd_regeln_konzept.md). Pairs without a row share one empty description: no reaction.
 * The test data in code (JSON and lines) are gone since 2026-11-18.
 */
public class EventReactionDescriptionPool extends DescriptionPool {

	
	private static EventReactionDescriptionPool instance;
	
	
	private EventReactionDescriptionPool () {
		
		super(EventType.MAX_EVENT_TYPE, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		
		this.descriptions = new EventReactionDescription[sizeDescriptionsArray];
		
		initialize();
	}
	
	public static EventReactionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventReactionDescriptionPool();
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
			// the rules in lines are gone (2026-11-18), they are in swpool_erd now
		}
	}


	protected  final DescriptionBase getNewDescription() {
		return new EventReactionDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new EventReactionDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return  CreateActionExpression.createActionExpression(lines4OneExpression);
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
	}
	
	private void initializeFromDatabase() {
		TablePoolERD table = new TablePoolERD();
		int countLoaded = 0;
		int countRejected = 0;

		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, reactionType");
		int rowCount = table.rowCount();

		for (int row = 0; row < rowCount; row++) {
			int eventType = table.getEventType(row);
			int reactionType = table.getReactionType(row);
			String json = table.getJsonERD(row);

			if (eventType <= 0 || eventType >= rangeMainIndex || reactionType < 0 || reactionType >= rangeSecondIndex) {
				System.err.println("EventReactionDescriptionPool: swpool_erd has the pair " + eventType + "/" + reactionType
						+ ", outside 1.." + (rangeMainIndex - 1) + " / 0.." + (rangeSecondIndex - 1) + "; the row is ignored");
				countRejected++;
				continue;
			}
			if (json == null || json.isEmpty()) continue;

			EventReactionDescription description = (EventReactionDescription) getDescription(json);
			EventType typeInJson = description.getEventType();
			if (typeInJson == null || typeInJson.getIndex() != eventType || description.getReactionType() != reactionType) {
				System.err.println("EventReactionDescriptionPool: the JSON of the pair " + eventType + "/" + reactionType
						+ " names " + typeInJson + "/" + description.getReactionType() + "; the row is ignored");
				countRejected++;
				continue;
			}

			description.setFunctions();
			descriptions[eventType * rangeSecondIndex + reactionType] = description;
			countLoaded++;
		}

		// pairs without a row share one empty description: no reaction, relevance threshold 100
		DescriptionBase empty = getNewDescription();
		for (int index = 0; index < sizeDescriptionsArray; index++) {
			if (descriptions[index] == null) descriptions[index] = empty;
		}

		if (countLoaded == 0) {
			System.err.println("EventReactionDescriptionPool: no rules in swpool_erd, so nobody reacts to an event.");
		}
		else {
			System.out.println("EventReactionDescriptionPool: " + countLoaded + " reaction rules loaded from swpool_erd"
					+ (countRejected > 0 ? ", " + countRejected + " rows ignored" : ""));
		}
	}

}
