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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.Animal;
import org.socialworld.objects.SimulationObject;

/**
 * What the inventory window shows of one human (2026-11-24): per place the item as text and, for a container, what
 * lies inside (nested containers indented). Only data, without Swing; it is read with the token of the display
 * (cluster visualize), which may read the places and the contents of containers.
 */
final class InventoryView {

	/** Containers inside containers are listed down to this depth. */
	static final int MAX_DEPTH = 3;
	static final String INDENT = "  ";
	static final String EMPTY = "\u2013";

	/** One place: the item as text (null if the place is empty) and the contents of a container. */
	static final class Slot {
		final String item;
		final List<String> contents;

		Slot(String item, List<String> contents) {
			this.item = item;
			this.contents = (contents == null) ? Collections.<String>emptyList() : Collections.unmodifiableList(contents);
		}

		boolean isEmpty() {
			return item == null;
		}

		/** The text in the slot: the item, for a container with the number of what is in it at the first level. */
		String text() {
			if (isEmpty()) return EMPTY;
			int count = 0;
			for (String line : contents) if (!line.startsWith(INDENT)) count++;
			return (count > 0) ? item + " (" + count + ")" : item;
		}
	}

	private static final Slot EMPTY_SLOT = new Slot(null, null);

	private final int humanID;
	private final String message;
	private final Map<InventoryPlace, Slot> slots;

	private InventoryView(int humanID, String message, Map<InventoryPlace, Slot> slots) {
		this.humanID = humanID;
		this.message = message;
		this.slots = slots;
	}

	static InventoryView none() {
		return new InventoryView(VisualModel.NO_OBJECT, "kein Mensch ausgew\u00E4hlt", new EnumMap<InventoryPlace, Slot>(InventoryPlace.class));
	}

	static InventoryView missing(int humanID) {
		return new InventoryView(humanID, "Mensch " + humanID + " gibt es nicht", new EnumMap<InventoryPlace, Slot>(InventoryPlace.class));
	}

	static InventoryView of(int humanID, Map<InventoryPlace, Slot> slots) {
		Map<InventoryPlace, Slot> copy = new EnumMap<InventoryPlace, Slot>(InventoryPlace.class);
		copy.putAll(slots);
		return new InventoryView(humanID, null, copy);
	}

	/** Reads every place of the human and the contents of the containers on them. */
	static InventoryView read(int humanID, Animal human, IAccessToken token) {
		Map<InventoryPlace, Slot> slots = new EnumMap<InventoryPlace, Slot>(InventoryPlace.class);
		for (InventoryPlace place : InventoryPlace.values()) {
			if (place == InventoryPlace.noWhere) continue;
			SimulationObject object = human.getInventoryItem(token, place);
			if (!object.isSimulationObject()) continue;
			List<String> contents = new ArrayList<String>();
			addContents(object, token, "", 1, contents);
			slots.put(place, new Slot(describe(object), contents));
		}
		return new InventoryView(humanID, null, slots);
	}

	private static void addContents(SimulationObject container, IAccessToken token, String indent, int depth, List<String> lines) {
		if (depth > MAX_DEPTH) return;
		for (SimulationObject content : container.getContents(token)) {
			lines.add(indent + describe(content));
			addContents(content, token, indent + INDENT, depth + 1, lines);
		}
	}

	/** The item as in the Action Creation Tool: ID and class name. */
	static String describe(SimulationObject object) {
		return object.isSimulationObject() ? object.getObjectID() + " " + object.getClass().getSimpleName() : EMPTY;
	}

	int getHumanID() {
		return humanID;
	}

	/** Not null if there is nothing to show (no human selected, the human does not exist). */
	String getMessage() {
		return message;
	}

	Slot slot(InventoryPlace place) {
		Slot slot = slots.get(place);
		return (slot == null) ? EMPTY_SLOT : slot;
	}

	/** An empty upper body or legs place is taken by a dress all the same. */
	boolean isCoveredByDress(InventoryPlace place) {
		return (place == InventoryPlace.upperBody || place == InventoryPlace.legs)
				&& slot(place).isEmpty() && !slot(InventoryPlace.dress).isEmpty();
	}

	int countOccupied() {
		return slots.size();
	}

	/** Everything inside containers, at every depth. */
	int countInContainers() {
		int count = 0;
		for (Slot slot : slots.values()) count += slot.contents.size();
		return count;
	}

	static int countPlaces() {
		return InventoryPlace.values().length - 1;	// without noWhere
	}

	String title() {
		return (humanID == VisualModel.NO_OBJECT) ? "Inventar" : "Inventar Mensch " + humanID;
	}

	String summary() {
		if (message != null) return message;
		return countOccupied() + " von " + countPlaces() + " Pl\u00E4tzen belegt, " + countInContainers() + " Items in Beh\u00E4ltern";
	}

	/** One line per container with its contents, for the list below the figure. */
	List<String> contentLines() {
		List<String> lines = new ArrayList<String>();
		for (Map.Entry<InventoryPlace, Slot> entry : slots.entrySet()) {
			Slot slot = entry.getValue();
			if (slot.contents.isEmpty()) continue;
			lines.add(InventorySlotLayout.label(entry.getKey()) + ": " + slot.item);
			for (String content : slot.contents) lines.add(INDENT + INDENT + content);
		}
		return lines;
	}
}
