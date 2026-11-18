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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.socialworld.core.EventType;

/**
 * The event families of the reactions (erd_regeln_konzept.md, section 3): event types that mean the same for the one who reacts.
 * A human or an animal draws one reaction type per family, uniformly from 0 to CAPACITY_GPRT_ARRAY - 1;
 * all event types of the family get it. The reaction type does not follow the character.
 */
public enum EventReactionFamily {
	screamHeard(EventType.candidatesSayScream),
	sayHeard(EventType.candidatesSayNormal),
	whisperHeard(EventType.candidatesSayWhisper),
	lookedAt(EventType.targetExamineByLook),
	touched(EventType.targetTouchByHand);

	private final List<EventType> eventTypes;

	private EventReactionFamily(EventType... eventTypes) {
		this.eventTypes = Collections.unmodifiableList(Arrays.asList(eventTypes));
	}

	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	/**
	 * The reaction types of a human or an animal: one drawn type per family, the type of GaussPoolReactionType
	 * (index 0) for all other event types, which have no reaction rules.
	 *
	 * @param random the random generator of the creation
	 * @return one reaction type per event type
	 */
	public static int[] drawReactionTypes(Random random) {
		int[] types = GaussPoolReactionType.getInstance().getReactionTypes(0).clone();
		for (EventReactionFamily family : values()) {
			int type = random.nextInt(GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
			for (EventType eventType : family.eventTypes) {
				types[eventType.getIndex()] = type;
			}
		}
		return types;
	}
}
