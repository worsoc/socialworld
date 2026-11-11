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
package org.socialworld.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Starting an action needs a simulation object and the display, so only the state
 * of a new handler is checked here.
 */
public class ActionHandlerTest {

	@Test
	void newHandler_hasNoRunningAction() {
		assertThat(new ActionHandler(null).isActionRunning()).isFalse();
	}

	@Test
	void newHandler_withoutAnAction_doesNothing() {
		ActionHandler handler = new ActionHandler(null);

		assertThat(handler.doActualAction((byte) 5)).isEqualTo(ActionHandler.ACTIONHANDLER_RETURN_NOACTION);
		assertThat(handler.isActionRunning()).isFalse();
	}
}
