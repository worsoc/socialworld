package org.socialworld.tools.pct;

import java.util.Set;
import java.util.TreeSet;

/**
 * Reads which slots are meant: "3, 8, 13" names single slots, "5-7" a range and "0-39/5" every fifth one.
 */
final class PerceptionSlotList {

	private PerceptionSlotList() {
	}

	/**
	 * @throws IllegalArgumentException if a part cannot be read or lies outside of the slots
	 */
	static Set<Integer> parse(String text, int countSlots) {

		Set<Integer> slots = new TreeSet<Integer>();

		for (String part : text.trim().split("[,\\s]+")) {
			if (!part.isEmpty()) {
				addPart(part, countSlots, slots);
			}
		}
		return slots;
	}

	private static void addPart(String part, int countSlots, Set<Integer> slots) {

		String[] rangeAndStep = part.split("/", -1);
		String[] fromAndTo = rangeAndStep[0].split("-", -1);

		if (rangeAndStep.length > 2 || fromAndTo.length > 2 || (rangeAndStep.length == 2 && fromAndTo.length != 2)) {
			throw new IllegalArgumentException("Cannot read: " + part);
		}

		int from = number(fromAndTo[0], part);
		int to = fromAndTo.length == 2 ? number(fromAndTo[1], part) : from;
		int step = rangeAndStep.length == 2 ? number(rangeAndStep[1], part) : 1;

		if (from > to || step < 1) {
			throw new IllegalArgumentException("Cannot read: " + part);
		}
		if (to >= countSlots) {
			throw new IllegalArgumentException("There are only the slots 0 to " + (countSlots - 1) + ": " + part);
		}

		for (int slot = from; slot <= to; slot += step) {
			slots.add(slot);
		}
	}

	private static int number(String text, String part) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Cannot read: " + part);
		}
	}
}
