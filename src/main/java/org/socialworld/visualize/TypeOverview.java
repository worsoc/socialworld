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
package org.socialworld.visualize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Which human has which type, and how many humans each type has.
 * The type is the character number, influence type + 1 (EID concept A7).
 * Built once from what the simulation reports; the types do not change while the simulation runs.
 */
final class TypeOverview {

	/** One human and its type. */
	static final class Assignment {
		final int objID;
		final int type;
		final String name;

		Assignment(int objID, int type, String name) {
			this.objID = objID;
			this.type = type;
			this.name = name;
		}
	}

	/** One type that occurs, with the number of its humans. */
	static final class Count {
		final int type;
		final String name;
		final int humans;

		Count(int type, String name, int humans) {
			this.type = type;
			this.name = name;
			this.humans = humans;
		}
	}

	private final List<Assignment> assignments;
	private final List<Count> counts;

	/**
	 * @param influenceTypeByHuman influence type per object ID of a human
	 * @param nameByType name per type (character number); a missing type gets "?"
	 */
	TypeOverview(Map<Integer, Integer> influenceTypeByHuman, Map<Integer, String> nameByType) {
		List<Assignment> rows = new ArrayList<>();
		TreeMap<Integer, Integer> humansByType = new TreeMap<>();
		for (Map.Entry<Integer, Integer> entry : new TreeMap<>(influenceTypeByHuman).entrySet()) {
			int type = entry.getValue() + 1;
			rows.add(new Assignment(entry.getKey(), type, name(nameByType, type)));
			humansByType.merge(type, 1, Integer::sum);
		}
		List<Count> countRows = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : humansByType.entrySet()) {
			countRows.add(new Count(entry.getKey(), name(nameByType, entry.getKey()), entry.getValue()));
		}
		assignments = Collections.unmodifiableList(rows);
		counts = Collections.unmodifiableList(countRows);
	}

	private static String name(Map<Integer, String> nameByType, int type) {
		String name = nameByType.get(type);
		return name == null ? "?" : name;
	}

	/** Every human, ordered by object ID. */
	List<Assignment> getAssignments() {
		return assignments;
	}

	/** Every type that occurs, ordered by type. */
	List<Count> getCounts() {
		return counts;
	}

	int countHumans() {
		return assignments.size();
	}

	int countTypes() {
		return counts.size();
	}
}
