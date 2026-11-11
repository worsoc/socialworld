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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.core.EventToCandidates;
import org.socialworld.core.EventType;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;
import org.socialworld.propertyChange.ListenedMap;

/**
 * Checks, only reading, whether the search by position finds every human at the place where it really is.
 * It asks the search the same way as the event master does, with an event of the smallest range
 * that is built but never sent. A human that is not found sits in a wrong sector of the search.
 */
final class SearchTreeCheck {

	private static final EventType PROBE = EventType.candidatesSayWhisper;

	/** The result of one check. */
	static final class Result {
		final int checked;
		final int withoutPosition;
		final List<Integer> notFound;

		Result(int checked, int withoutPosition, List<Integer> notFound) {
			this.checked = checked;
			this.withoutPosition = withoutPosition;
			this.notFound = notFound;
		}
	}

	private SearchTreeCheck() {
	}

	/** Only to be called after the simulation has created its objects. */
	static Result checkHumans(int humanCount) {
		AccessTokenAct token = AccessTokenAct.getValid();
		Simulation simulation = Simulation.getInstance();
		ListenedMap<Human> humans = simulation.getObjectMaster().getHumans();

		int checked = 0;
		int withoutPosition = 0;
		List<Integer> notFound = new ArrayList<Integer>();

		for (int id = 1; id <= humanCount; id++) {
			Human human = humans.get(id);
			if (human == null) continue;

			Position position = human.getPosition(token);
			if (position.checkIsObjectNothing()) {
				withoutPosition++;
				continue;
			}

			EventToCandidates probe = new EventToCandidates(PROBE.getIndex(), 0, human, ActualTime.asTime(), position, null);
			LinkedList<LinkedList<SimulationObject>> found = simulation.getObjectSearch().getObjects(probe);

			checked++;
			if (!containsHuman(found, human)) notFound.add(id);
		}
		return new Result(checked, withoutPosition, notFound);
	}

	/** The search returns humans, gods, animals and others in this order. */
	private static boolean containsHuman(LinkedList<LinkedList<SimulationObject>> found, Human human) {
		return !found.isEmpty() && found.get(0).contains(human);
	}
}
