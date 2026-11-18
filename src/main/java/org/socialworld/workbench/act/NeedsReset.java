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

import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.Attribute;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.propertyChange.ListenedMap;

/**
 * A divine intervention for the dashboard: every human who sleeps or eats stops doing so,
 * and its tiredness and hunger are set low, so that it does not go back to bed or to the table at once.
 * Meant to see actions and reactions instead of sleeping and eating; it overrides tiredness and hunger.
 * The attribute matrix pulls both back to the band of the character later.
 */
public final class NeedsReset {

	public static final int TIREDNESS = 2000;
	public static final int HUNGER = 2000;
	/** Humans have the IDs of the first band of ObjectMaster. */
	static final int MAX_HUMAN_ID = 999;

	/** What was done. */
	public static final class Result {
		private final int countWoken;
		private final int countFromTheTable;

		Result(int countWoken, int countFromTheTable) {
			this.countWoken = countWoken;
			this.countFromTheTable = countFromTheTable;
		}

		public int getCountWoken() {
			return countWoken;
		}

		public int getCountFromTheTable() {
			return countFromTheTable;
		}

		@Override
		public String toString() {
			return countWoken + " geweckt, " + countFromTheTable + " vom Essen geholt";
		}
	}

	private NeedsReset() {
	}

	/** @return true for the modes this intervention ends: sleep and eat */
	static boolean isEnded(ActionMode mode) {
		return mode == ActionMode.sleep || mode == ActionMode.eat;
	}

	/**
	 * Ends sleeping and eating of all humans and sets their tiredness and hunger to 2000.
	 * Humans who do something else are not touched. Only to be called after the simulation has created its objects.
	 */
	public static Result endSleepingAndEating() {
		AccessTokenAct token = AccessTokenAct.getValid();
		ListenedMap<Human> humans = Simulation.getInstance().getObjectMaster().getHumans();

		int woken = 0;
		int fromTheTable = 0;

		for (int id = 1; id <= MAX_HUMAN_ID; id++) {
			Human human = humans.get(id);
			if (human == null) continue;

			ActionMode mode = human.getRunningActionMode(token);
			if (!isEnded(mode)) continue;

			// the action handler ends it at its next second, as with "Aktion beenden" in the ACT window
			if (!human.stopActualAction(token)) continue;
			human.setAttributeFromOutside(token, Attribute.tiredness, TIREDNESS);
			human.setAttributeFromOutside(token, Attribute.hunger, HUNGER);

			if (mode == ActionMode.sleep) woken++;
			else fromTheTable++;
		}
		return new Result(woken, fromTheTable);
	}
}
