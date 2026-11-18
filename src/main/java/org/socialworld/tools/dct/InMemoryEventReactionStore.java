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
package org.socialworld.tools.dct;

import java.util.Map;
import java.util.TreeMap;

/**
 * Keeps the event reaction rules in memory, for the tests. Key: event type * 1000 + reaction type.
 */
final class InMemoryEventReactionStore implements EventReactionStore {

	private final Map<Integer, String> rules = new TreeMap<>();

	private static int key(int eventType, int reactionType) {
		return eventType * 1000 + reactionType;
	}

	@Override
	public void clear() {
		rules.clear();
	}

	@Override
	public void insert(int eventType, int reactionType, String json) {
		rules.put(key(eventType, reactionType), json);
	}

	@Override
	public String getDescription() {
		return "memory";
	}

	String get(int eventType, int reactionType) {
		return rules.get(key(eventType, reactionType));
	}

	int size() {
		return rules.size();
	}
}
