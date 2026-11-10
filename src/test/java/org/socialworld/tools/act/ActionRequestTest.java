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
package org.socialworld.tools.act;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.tools.act.ActionRequest.Problem;

public class ActionRequestTest {

	private static Problem check(ActionMode mode, int causer, float intensity, int priority) {
		return new ActionRequest(mode, causer, intensity, priority).check();
	}

	@Test
	void keepsItsValues() {
		ActionRequest request = new ActionRequest(ActionMode.sayScream, 12, 60f, 100);

		assertThat(request.getMode()).isEqualTo(ActionMode.sayScream);
		assertThat(request.getCauserObjectId()).isEqualTo(12);
		assertThat(request.getIntensity()).isEqualTo(60f);
		assertThat(request.getPriority()).isEqualTo(100);
	}

	@Test
	void acceptsAValidRequest() {
		assertThat(check(ActionMode.sayNormal, 1, 10f, 100)).isEqualTo(Problem.NONE);
	}

	@Test
	void acceptsTheLimits() {
		assertThat(check(ActionMode.sayNormal, 1, 0f, 0)).isEqualTo(Problem.NONE);
		assertThat(check(ActionMode.sayNormal, 1, 0f, 256)).isEqualTo(Problem.NONE);
	}

	@Test
	void rejectsAMissingMode() {
		assertThat(check(null, 1, 10f, 100)).isEqualTo(Problem.MODE_NOT_SUPPORTED);
	}

	@Test
	void rejectsAModeOfAnotherFamily() {
		assertThat(check(ActionMode.walk, 1, 10f, 100)).isEqualTo(Problem.MODE_NOT_SUPPORTED);
	}

	@Test
	void rejectsAnInvalidCauser() {
		assertThat(check(ActionMode.sayNormal, 0, 10f, 100)).isEqualTo(Problem.INVALID_CAUSER);
		assertThat(check(ActionMode.sayNormal, -1, 10f, 100)).isEqualTo(Problem.INVALID_CAUSER);
	}

	@Test
	void rejectsAnInvalidIntensity() {
		assertThat(check(ActionMode.sayNormal, 1, -0.1f, 100)).isEqualTo(Problem.INVALID_INTENSITY);
		assertThat(check(ActionMode.sayNormal, 1, Float.NaN, 100)).isEqualTo(Problem.INVALID_INTENSITY);
		assertThat(check(ActionMode.sayNormal, 1, Float.POSITIVE_INFINITY, 100)).isEqualTo(Problem.INVALID_INTENSITY);
	}

	@Test
	void rejectsAnInvalidPriority() {
		assertThat(check(ActionMode.sayNormal, 1, 10f, -1)).isEqualTo(Problem.INVALID_PRIORITY);
		assertThat(check(ActionMode.sayNormal, 1, 10f, 257)).isEqualTo(Problem.INVALID_PRIORITY);
	}
}
