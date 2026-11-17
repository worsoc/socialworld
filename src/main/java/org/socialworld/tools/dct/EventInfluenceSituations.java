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
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.socialworld.core.EventType;

/**
 * Which event types belong to a situation of the event influences, and with which factor
 * the steps of the situation are multiplied for that event type.
 * Read from event_situations.csv: situation;eventType;factor. Lines starting with # are comments.
 */
public class EventInfluenceSituations {

	public static final String RESOURCE = "event_situations.csv";

	/** The situation of an event type and the factor of its steps. */
	public static final class Entry {
		private final int situation;
		private final EventType eventType;
		private final float factor;

		Entry(int situation, EventType eventType, float factor) {
			this.situation = situation;
			this.eventType = eventType;
			this.factor = factor;
		}

		public int getSituation() {
			return situation;
		}

		public EventType getEventType() {
			return eventType;
		}

		public float getFactor() {
			return factor;
		}
	}

	private final Map<EventType, Entry> entries;

	private EventInfluenceSituations(Map<EventType, Entry> entries) {
		this.entries = Collections.unmodifiableMap(entries);
	}

	public static EventInfluenceSituations load() {
		try (InputStream in = EventInfluenceSituations.class.getResourceAsStream(RESOURCE)) {
			if (in == null) throw new IllegalStateException(RESOURCE + " not found");
			return parse(new String(in.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static EventInfluenceSituations parse(String content) {
		Map<EventType, Entry> entries = new EnumMap<>(EventType.class);

		try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#")) continue;

				String[] fields = line.split(";", -1);
				if (fields.length != 3) {
					throw new IllegalArgumentException("expected 3 fields: " + line);
				}

				int situation = Integer.parseInt(fields[0].trim());
				EventType eventType = EventType.fromName(fields[1].trim());
				if (eventType == EventType.nothing) {
					throw new IllegalArgumentException("unknown event type: " + line);
				}
				float factor = Float.parseFloat(fields[2].trim());
				if (factor < 0) {
					throw new IllegalArgumentException("negative factor: " + line);
				}
				if (entries.containsKey(eventType)) {
					throw new IllegalArgumentException("event type twice: " + line);
				}
				entries.put(eventType, new Entry(situation, eventType, factor));
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return new EventInfluenceSituations(entries);
	}

	/** @return the situation of the event type, or null if it has none */
	public Entry get(EventType eventType) {
		return entries.get(eventType);
	}

	public int size() {
		return entries.size();
	}
}
