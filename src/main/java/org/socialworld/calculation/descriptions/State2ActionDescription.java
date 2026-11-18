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
package org.socialworld.calculation.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.datasource.parsing.JsonState2ActionDescription;

import com.google.gson.Gson;

/**
 * The rules by which an object acts because of its own state, for one state-to-action type.
 * The entries form one chain (CreateActionExpression): the first entry whose condition holds gives the action,
 * so the order of the entries is their precedence.
 */
public class State2ActionDescription extends DescriptionBase {

	/** The function tag that reads an event parameter; there is no event behind a state-to-action rule. */
	static final String TAG_EVENT_PARAMETER = "<GetEvParm>";

	private int state2ActionType;
	private List<State2ActionDescriptionEntry> entrys = new ArrayList<State2ActionDescriptionEntry>();

	public State2ActionDescription() {
		super();
	}

	/**
	 * @throws IllegalArgumentException if an entry reads an event parameter
	 */
	public State2ActionDescription(Gson gson, String json) {
		super();
		loadFromJson(gson, json);
	}

	private void loadFromJson(Gson gson, String json) {
		JsonState2ActionDescription jsonObject;
		jsonObject = gson.fromJson(json, JsonState2ActionDescription.class);

		this.state2ActionType = jsonObject.state2ActionType;
		if (jsonObject.entrys != null) {
			this.entrys = jsonObject.entrys;
		}

		for (State2ActionDescriptionEntry entry : this.entrys) {
			if (readsEventParameter(entry)) {
				throw new IllegalArgumentException("state-to-action type " + this.state2ActionType
						+ ": an entry uses " + TAG_EVENT_PARAMETER + ", but there is no event behind a state-to-action rule");
			}
		}
	}

	private static boolean readsEventParameter(ActionDescriptionEntry entry) {
		String[] values = {entry.conditions, entry.actionType, entry.actionMode, entry.minTime, entry.maxTime,
				entry.priority, entry.intensity, entry.duration, entry.direction, entry.target, entry.item,
				entry.inventoryPlace, entry.endPosition};
		for (String value : values) {
			if (value != null && value.contains(TAG_EVENT_PARAMETER)) return true;
		}
		return false;
	}

	@Override
	public void setFunctions() {
		if (this.entrys.isEmpty()) return;

		Expression startExpression = CreateActionExpression.createActionExpression(this.entrys, true /* dummy */);
		addFunction(new FunctionByExpression(startExpression));
	}

	public int getState2ActionType() {
		return this.state2ActionType;
	}

	public int countEntries() {
		return this.entrys.size();
	}

}
