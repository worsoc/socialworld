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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.socialworld.attributes.BodyBuild;
import org.socialworld.attributes.properties.Material;

/**
 * The typical composition of the item classes (composition concept Z2, 2026-11-24), read from composition.csv next
 * to equipment.csv:
 * <ul>
 * <li>{@code <class>;<recipe>|<recipe>...} one line per item class (its simple name)</li>
 * <li>recipe = {@code <material>:<share>+-<spread>,...}: the shares in percent add up to 100, the spread is how far
 * a variant may differ; without spread the share is always the same</li>
 * <li>{@code |} separates alternatives: the thing is made of the one or the other (a bowl of wood or of clay)</li>
 * <li>{@code Human.<build>;<recipe>} one line per body build of the humans (body build concept B2, 2026-11-25);
 * the build is a name of the enum BodyBuild, the human of a character takes the recipe of its build</li>
 * </ul>
 * A line with a mistake is left out and named in the problems; the other lines are read all the same.
 */
public final class CompositionCatalog {

	public static final String RESOURCE = "/org/socialworld/tools/dct/composition.csv";

	/** The humans are in the catalog as "Human." + body build, next to the item classes. */
	public static final String HUMAN_PREFIX = "Human.";

	/** @return the name under which the catalog (and swkind_materialset) knows the humans of this build */
	public static String humanKey(BodyBuild build) {
		return HUMAN_PREFIX + build.name();
	}

	/** @return whether the name is that of a body build of the humans, not that of an item class */
	public static boolean isHumanKey(String className) {
		return className.startsWith(HUMAN_PREFIX);
	}

	/** One material of a recipe: its typical share and how far a variant may differ. */
	public static final class Part {
		public final Material material;
		public final int share;
		public final int spread;

		Part(Material material, int share, int spread) {
			this.material = material;
			this.share = share;
			this.spread = spread;
		}

		@Override
		public String toString() {
			return material.name() + ":" + share + (spread > 0 ? "+-" + spread : "");
		}
	}

	/** The materials of one way to make the thing, in the order of the line. */
	public static final class Recipe {
		public final List<Part> parts;

		Recipe(List<Part> parts) {
			this.parts = Collections.unmodifiableList(parts);
		}

		/** The part with the largest share (the first one if two are equal): it takes the rest of a variant. */
		public int indexOfMain() {
			int main = 0;
			for (int i = 1; i < parts.size(); i++) {
				if (parts.get(i).share > parts.get(main).share) main = i;
			}
			return main;
		}

		@Override
		public String toString() {
			return parts.toString();
		}
	}

	private static CompositionCatalog instance;

	private final Map<String, List<Recipe>> recipes = new LinkedHashMap<>();
	private final List<String> problems = new ArrayList<>();

	public static synchronized CompositionCatalog getInstance() {
		if (instance == null) instance = load();
		return instance;
	}

	static CompositionCatalog load() {
		List<String> lines = new ArrayList<>();
		try (InputStream in = CompositionCatalog.class.getResourceAsStream(RESOURCE)) {
			if (in == null) {
				CompositionCatalog empty = new CompositionCatalog();
				empty.problems.add("resource " + RESOURCE + " not found");
				return empty;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) lines.add(line);
		} catch (IOException e) {
			CompositionCatalog empty = new CompositionCatalog();
			empty.problems.add("resource " + RESOURCE + " not readable: " + e.getMessage());
			return empty;
		}
		return parse(lines);
	}

	static CompositionCatalog parse(List<String> lines) {
		CompositionCatalog catalog = new CompositionCatalog();
		int number = 0;
		for (String raw : lines) {
			number++;
			String line = raw.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			String where = "line " + number + " (" + line + ")";

			String[] fields = line.split(";", -1);
			if (fields.length != 2) {
				catalog.problems.add(where + ": expected <class>;<recipes>");
				continue;
			}
			String className = fields[0].trim();
			if (isHumanKey(className)) {
				String build = className.substring(HUMAN_PREFIX.length());
				try {
					BodyBuild.fromName(build);
				} catch (IllegalArgumentException e) {
					catalog.problems.add(where + ": unknown body build " + build);
					continue;
				}
			}
			else if (StartEquipmentCatalog.fullClassName(className) == null) {
				catalog.problems.add(where + ": unknown item class " + className);
				continue;
			}
			if (catalog.recipes.containsKey(className)) {
				catalog.problems.add(where + ": " + className + " twice");
				continue;
			}
			List<Recipe> alternatives = new ArrayList<>();
			boolean ok = true;
			for (String text : fields[1].split("\\|", -1)) {
				Recipe recipe = parseRecipe(text, where, catalog.problems);
				if (recipe == null) {
					ok = false;
					break;
				}
				alternatives.add(recipe);
			}
			if (ok) catalog.recipes.put(className, Collections.unmodifiableList(alternatives));
		}
		return catalog;
	}

	private static Recipe parseRecipe(String text, String where, List<String> problems) {
		List<Part> parts = new ArrayList<>();
		int sum = 0;
		for (String partText : text.split(",", -1)) {
			Part part = parsePart(partText.trim(), where, problems);
			if (part == null) return null;
			for (Part other : parts) {
				if (other.material == part.material) {
					problems.add(where + ": " + part.material + " twice in one recipe");
					return null;
				}
			}
			parts.add(part);
			sum += part.share;
		}
		if (sum != 100) {
			problems.add(where + ": the shares of " + parts + " add up to " + sum + ", not 100");
			return null;
		}
		return new Recipe(parts);
	}

	private static Part parsePart(String text, String where, List<String> problems) {
		String[] nameAndShare = text.split(":", -1);
		if (nameAndShare.length != 2) {
			problems.add(where + ": expected <material>:<share>, found '" + text + "'");
			return null;
		}
		String name = nameAndShare[0].trim();
		Material material = Material.fromName(name);
		if (material == Material.nothing) {
			problems.add(where + ": unknown material " + name);
			return null;
		}
		String shareText = nameAndShare[1].trim().replace("±", "+-");
		int share;
		int spread = 0;
		try {
			int at = shareText.indexOf("+-");
			if (at < 0) {
				share = Integer.parseInt(shareText);
			} else {
				share = Integer.parseInt(shareText.substring(0, at).trim());
				spread = Integer.parseInt(shareText.substring(at + 2).trim());
			}
		} catch (NumberFormatException e) {
			problems.add(where + ": share '" + nameAndShare[1].trim() + "' of " + name + " is no number");
			return null;
		}
		if (share < 1 || share > 100 || spread < 0) {
			problems.add(where + ": share " + share + " of " + name + " must be 1 .. 100, the spread not negative");
			return null;
		}
		return new Part(material, share, spread);
	}

	/** The alternatives of the class (simple name), an empty list if the catalog does not know it. */
	public List<Recipe> get(String className) {
		List<Recipe> found = recipes.get(className);
		return (found == null) ? Collections.<Recipe>emptyList() : found;
	}

	/** The simple names of the classes and the human keys ("Human." + build), in the order of the file. */
	public Set<String> classNames() {
		return Collections.unmodifiableSet(recipes.keySet());
	}

	/** @return what could not be read; empty if all is well */
	public List<String> getProblems() {
		return Collections.unmodifiableList(problems);
	}
}
