/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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
package org.socialworld.datasource.createObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.attributes.BodyBuild;

/**
 * The body build of the characters (body build concept B3, 2026-11-25), read from characters.csv for the running
 * simulation: only the number (first field) and the build (field 19). The simulation does not use the
 * AttributeCharacterCatalog of the DCT; this reader is its counterpart, like StartEquipmentCatalog for equipment.csv.
 * A line without the build column gives BodyBuild.DEFAULT, a line with an unknown build is named in the problems.
 */
public final class BodyBuildCatalog {

	public static final String RESOURCE = "/org/socialworld/tools/dct/characters.csv";

	/** no;name;sketch;9 levels;sleepHours;mealsPerDay;4 ideal values;build */
	static final int FIELDS_WITH_BUILD = 19;

	private static BodyBuildCatalog instance;

	private final Map<Integer, BodyBuild> builds = new HashMap<>();
	private final List<String> problems = new ArrayList<>();

	public static synchronized BodyBuildCatalog getInstance() {
		if (instance == null) instance = load();
		return instance;
	}

	static BodyBuildCatalog load() {
		List<String> lines = new ArrayList<>();
		try (InputStream in = BodyBuildCatalog.class.getResourceAsStream(RESOURCE)) {
			if (in == null) {
				BodyBuildCatalog empty = new BodyBuildCatalog();
				empty.problems.add("resource " + RESOURCE + " not found");
				return empty;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) lines.add(line);
		} catch (IOException e) {
			BodyBuildCatalog empty = new BodyBuildCatalog();
			empty.problems.add("resource " + RESOURCE + " not readable: " + e.getMessage());
			return empty;
		}
		return parse(lines);
	}

	static BodyBuildCatalog parse(List<String> lines) {
		BodyBuildCatalog catalog = new BodyBuildCatalog();
		for (String raw : lines) {
			String line = raw.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			String[] fields = line.split(";", -1);
			int number;
			try {
				number = Integer.parseInt(fields[0].trim());
			} catch (NumberFormatException e) {
				catalog.problems.add("no character number: " + line);
				continue;
			}
			BodyBuild build = BodyBuild.DEFAULT;
			if (fields.length == FIELDS_WITH_BUILD) {
				try {
					build = BodyBuild.fromName(fields[FIELDS_WITH_BUILD - 1].trim());
				} catch (IllegalArgumentException e) {
					catalog.problems.add("character " + number + ": " + e.getMessage());
					continue;
				}
			}
			catalog.builds.put(number, build);
		}
		return catalog;
	}

	/**
	 * @param characterNumber the number in characters.csv, 1 .. 100
	 * @return the body build of the character; BodyBuild.DEFAULT for a number the file does not have
	 */
	public BodyBuild get(int characterNumber) {
		BodyBuild build = builds.get(characterNumber);
		return (build == null) ? BodyBuild.DEFAULT : build;
	}

	/** @return how many characters were read */
	public int size() {
		return builds.size();
	}

	/** @return what could not be read; empty if all is well */
	public List<String> getProblems() {
		return Collections.unmodifiableList(problems);
	}
}
