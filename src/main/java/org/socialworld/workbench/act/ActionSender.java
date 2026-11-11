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
package org.socialworld.workbench.act;

import org.socialworld.actions.ActionType;
import org.socialworld.actions.say.ActionSay;
import org.socialworld.attributes.ActualTime;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;

/**
 * Gives a human an action into the normal pipeline of the simulation: the action is
 * inserted into the human's action list, and from there the action handler and the
 * action master treat it like any other action, with its duration, its interruption,
 * its events and the report to the display.
 *
 * Only actions that are going to be performed in principle are given: the human has
 * to exist, must not perform an action at the moment (sleeping included) and must not
 * have the same action planned already.
 */
public final class ActionSender {

	/** The time after now within which the action has to start. */
	static final int START_WINDOW_IN_MILLISECONDS = 1000;

	private ActionSender() {
	}

	/**
	 * Checks the request and, if it is fine, inserts the action into the action list of the causer.
	 * Only to be called after the simulation has created its objects.
	 *
	 * @return {@code Problem.NONE} if the action was inserted, else the reason why not
	 */
	public static ActionRequest.Problem send(ActionRequest request) {
		ActionRequest.Problem problem = request.check();
		if (problem != ActionRequest.Problem.NONE) return problem;

		Human causer = Simulation.getInstance().getObjectMaster().getHumans().get(request.getCauserObjectId());
		if (causer == null) return ActionRequest.Problem.UNKNOWN_CAUSER;

		AccessTokenAct token = AccessTokenAct.getValid();
		if (causer.isActing(token)) return ActionRequest.Problem.CAUSER_BUSY;

		ActionSay action = new ActionSay(createActionProperties(request, ActualTime.inMilliseconds()));
		if (!causer.insertAction(token, action)) return ActionRequest.Problem.ALREADY_PLANNED;
		return ActionRequest.Problem.NONE;
	}

	/** Builds the properties under the names and with the types the actions expect. */
	static ValueArrayList createActionProperties(ActionRequest request, long nowInMilliseconds) {
		ValueArrayList properties = new ValueArrayList();
		properties.add(new Value(Type.actionType, Value.VALUE_BY_NAME_ACTION_TYPE, ActionType.say));
		properties.add(new Value(Type.actionMode, Value.VALUE_BY_NAME_ACTION_MODE, request.getMode()));
		properties.add(new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_INTENSITY, request.getIntensity()));
		properties.add(new Value(Type.longinteger, Value.VALUE_BY_NAME_ACTION_MINTIME, nowInMilliseconds));
		properties.add(new Value(Type.longinteger, Value.VALUE_BY_NAME_ACTION_MAXTIME, nowInMilliseconds + START_WINDOW_IN_MILLISECONDS));
		properties.add(new Value(Type.integer, Value.VALUE_BY_NAME_ACTION_PRIORITY, request.getPriority()));
		properties.add(new Value(Type.integer, Value.VALUE_BY_NAME_ACTION_DURATION, request.getDurationInMilliseconds()));
		return properties;
	}
}
