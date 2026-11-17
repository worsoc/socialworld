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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.socialworld.attributes.Attribute;

/**
 * The event influences per character and situation.
 * Read from event_influences.csv: character;situation;sentence;attribute;below;lowerHalf;upperHalf;above.
 * Lines starting with # are comments. A line with an empty attribute only records the sentence.
 */
public class EventInfluenceCatalog {

	public static final String RESOURCE = "event_influences.csv";

	/** The steps are given for: below the band, lower half, upper half, above the band. */
	public static final int COUNT_STEPS = 4;

	/** One line: how one attribute of a character changes in one situation. */
	public static final class Line {
		private final int character;
		private final int situation;
		private final String sentence;
		private final Attribute attribute;
		private final int[] steps;

		Line(int character, int situation, String sentence, Attribute attribute, int[] steps) {
			this.character = character;
			this.situation = situation;
			this.sentence = sentence;
			this.attribute = attribute;
			this.steps = steps;
		}

		public int getCharacter() {
			return character;
		}

		public int getSituation() {
			return situation;
		}

		public String getSentence() {
			return sentence;
		}

		/** @return the attribute, or null for a line that only records the sentence */
		public Attribute getAttribute() {
			return attribute;
		}

		/** @return the step for 0 = below the band, 1 = lower half, 2 = upper half, 3 = above the band */
		public int getStep(int tier) {
			return steps[tier];
		}
	}

	// character -> situation -> lines
	private final Map<Integer, Map<Integer, List<Line>>> lines;

	private EventInfluenceCatalog(Map<Integer, Map<Integer, List<Line>>> lines) {
		this.lines = lines;
	}

	public static EventInfluenceCatalog load() {
		try (InputStream in = EventInfluenceCatalog.class.getResourceAsStream(RESOURCE)) {
			if (in == null) throw new IllegalStateException(RESOURCE + " not found");
			return parse(new String(in.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static EventInfluenceCatalog parse(String content) {
		Map<Integer, Map<Integer, List<Line>>> lines = new TreeMap<>();
		int expectedFields = 4 + COUNT_STEPS;

		try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
			String text;
			while ((text = reader.readLine()) != null) {
				text = text.trim();
				if (text.isEmpty() || text.startsWith("#")) continue;

				String[] fields = text.split(";", -1);
				if (fields.length != expectedFields) {
					throw new IllegalArgumentException("expected " + expectedFields + " fields: " + text);
				}

				int character = Integer.parseInt(fields[0].trim());
				int situation = Integer.parseInt(fields[1].trim());
				String sentence = fields[2].trim();
				String attributeName = fields[3].trim();

				Attribute attribute = null;
				int[] steps = new int[COUNT_STEPS];
				if (!attributeName.isEmpty()) {
					if (!Attribute.isLowerAttributeName(attributeName)) {
						throw new IllegalArgumentException("unknown attribute '" + attributeName + "': " + text);
					}
					attribute = Attribute.fromName(attributeName);
					boolean positive = false;
					boolean negative = false;
					for (int i = 0; i < COUNT_STEPS; i++) {
						steps[i] = Integer.parseInt(fields[4 + i].trim());
						positive |= steps[i] > 0;
						negative |= steps[i] < 0;
					}
					if (positive && negative) {
						throw new IllegalArgumentException("steps with both signs: " + text);
					}
				}

				lines.computeIfAbsent(character, key -> new TreeMap<>())
						.computeIfAbsent(situation, key -> new ArrayList<>())
						.add(new Line(character, situation, sentence, attribute, steps));
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return new EventInfluenceCatalog(lines);
	}

	/** @return the numbers of the characters that have lines, in ascending order */
	public List<Integer> getCharacters() {
		return Collections.unmodifiableList(new ArrayList<>(lines.keySet()));
	}

	/** @return the lines of a character in a situation, empty if there are none */
	public List<Line> getLines(int character, int situation) {
		Map<Integer, List<Line>> perSituation = lines.get(character);
		if (perSituation == null) return Collections.emptyList();
		List<Line> result = perSituation.get(situation);
		return result == null ? Collections.emptyList() : Collections.unmodifiableList(result);
	}
}
