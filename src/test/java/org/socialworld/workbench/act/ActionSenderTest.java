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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.workbench.act.ActionRequest.Problem;

/**
 * Covers what works without a running simulation: creating an action needs one,
 * because every action fetches the simulation when it is constructed.
 */
public class ActionSenderTest {

	private static final long NOW = 1234567L;

	private static ValueArrayList properties() {
		return ActionSender.createActionProperties(new ActionRequest(ActionMode.sayScream, 7, 60f, 120, 2500), NOW);
	}

	private static Object read(String name, Type type) {
		return properties().getValue(name).getObject(type);
	}

	@Test
	void setsTypeAndMode() {
		assertThat(read(Value.VALUE_BY_NAME_ACTION_TYPE, Type.actionType)).isEqualTo(ActionType.say);
		assertThat(read(Value.VALUE_BY_NAME_ACTION_MODE, Type.actionMode)).isEqualTo(ActionMode.sayScream);
	}

	@Test
	void setsIntensityAndPriority() {
		assertThat(read(Value.VALUE_BY_NAME_ACTION_INTENSITY, Type.floatingpoint)).isEqualTo(60f);
		assertThat(read(Value.VALUE_BY_NAME_ACTION_PRIORITY, Type.integer)).isEqualTo(120);
	}

	@Test
	void setsTimesAndDuration() {
		assertThat(read(Value.VALUE_BY_NAME_ACTION_MINTIME, Type.longinteger)).isEqualTo(NOW);
		assertThat(read(Value.VALUE_BY_NAME_ACTION_MAXTIME, Type.longinteger))
				.isEqualTo(NOW + ActionSender.START_WINDOW_IN_MILLISECONDS);
		assertThat(read(Value.VALUE_BY_NAME_ACTION_DURATION, Type.integer)).isEqualTo(2500);
	}

	@Test
	void coversAllStandardPropertiesOfAnAction() {
		ValueArrayList properties = properties();

		for (String name : ActionType.getStandardPropertyNames()) {
			assertThat(properties.getValue(name).isValid()).as(name).isTrue();
		}
	}

	@Test
	void doesNotSendAnInvalidRequest() {
		assertThat(ActionSender.send(new ActionRequest(ActionMode.walk, 1, 10f, 100, 1000)))
				.isEqualTo(Problem.MODE_NOT_SUPPORTED);
		assertThat(ActionSender.send(new ActionRequest(ActionMode.sayNormal, 0, 10f, 100, 1000)))
				.isEqualTo(Problem.INVALID_CAUSER);
		assertThat(ActionSender.send(new ActionRequest(ActionMode.sayNormal, 1, -1f, 100, 1000)))
				.isEqualTo(Problem.INVALID_INTENSITY);
		assertThat(ActionSender.send(new ActionRequest(ActionMode.sayNormal, 1, 10f, 999, 1000)))
				.isEqualTo(Problem.INVALID_PRIORITY);
		assertThat(ActionSender.send(new ActionRequest(ActionMode.sayNormal, 1, 10f, 100, -1)))
				.isEqualTo(Problem.INVALID_DURATION);
	}

	@Test
	void doesNotStopForAnInvalidCauser() {
		assertThat(ActionSender.stop(0)).isEqualTo(Problem.INVALID_CAUSER);
		assertThat(ActionSender.stop(-1)).isEqualTo(Problem.INVALID_CAUSER);
	}
}
