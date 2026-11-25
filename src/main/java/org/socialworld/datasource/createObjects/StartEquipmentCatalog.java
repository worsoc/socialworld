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

import org.socialworld.attributes.properties.Material;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;

/**
 * The start equipment of the 100 characters (inventory concept V3, 2026-11-23), read from equipment.csv next to
 * characters.csv (later from the database, D6):
 * <ul>
 * <li>{@code kit;<name>;<clothes>} a set of clothes; a shoe, sock or glove stands for a pair</li>
 * <li>{@code <no>;<kit>;<more clothes>;<items>;<flags>} the equipment of character no; {@code Container(Liquid)} is
 * a container with its content; flag {@code barefoot} leaves out shoes and socks</li>
 * </ul>
 * The more clothes come first: they take their place, and a piece of the kit for the same place is left out
 * (a fur cloak instead of the cloak of the kit). Names are the simple names of the item classes.
 * A piece of clothing may name its material in brackets, {@code Apron[leather]} (item class or composition, step 2,
 * 2026-11-25): it gets a variant whose main material that is; composition.csv must have such an alternative.
 */
public final class StartEquipmentCatalog {

	public static final String RESOURCE = "/org/socialworld/tools/dct/equipment.csv";

	private static final String BASE = "org.socialworld.objects.concrete.";
	private static final String[] PACKAGES = {
		"clothes.caps", "clothes.shirts", "clothes.trousers", "clothes.dresses", "clothes.overgarments",
		"clothes.shoes", "clothes.gloves",
		"clothes",	// since 2026-11-25 Cap, Shirt, Shoe, Sock, Glove and Dress are concrete themselves
		"tools", "weapons", "containers", "valuables", "devotional", "writing", "pastime",
		"eatable.fruits", "eatable.veggies", "eatable.meat", "eatable.fish", "eatable.bakery", "eatable.dairy",
		"eatable.sweets", "eatable.liquids", "eatable",
		"plantparts", "animalparts", "solids"
	};

	public enum Side { none, left, right }

	/** One piece of clothing with its side (left and right for the pairs) and the material asked for, if any. */
	public static final class Piece {
		public final String className;
		public final Side side;
		/** the main material of the variant, Material.nothing if the piece does not name one */
		public final Material material;
		Piece(String className, Side side, Material material) {
			this.className = className;
			this.side = side;
			this.material = material;
		}
		@Override
		public String toString() {
			return className.substring(className.lastIndexOf('.') + 1)
					+ (material == Material.nothing ? "" : "[" + material.name() + "]")
					+ (side == Side.none ? "" : "(" + side + ")");
		}
	}

	/** The equipment of one character. */
	public static final class Entry {
		public final int number;
		public final List<Piece> clothes;
		public final List<String> items;
		public final List<String> contents;		// the liquids in the containers (they come with V5)
		public final boolean barefoot;
		Entry(int number, List<Piece> clothes, List<String> items, List<String> contents, boolean barefoot) {
			this.number = number;
			this.clothes = Collections.unmodifiableList(clothes);
			this.items = Collections.unmodifiableList(items);
			this.contents = Collections.unmodifiableList(contents);
			this.barefoot = barefoot;
		}
	}

	private static StartEquipmentCatalog instance;

	private final Map<Integer, Entry> entries = new HashMap<>();
	private final List<String> problems = new ArrayList<>();

	public static synchronized StartEquipmentCatalog getInstance() {
		if (instance == null) instance = load();
		return instance;
	}

	static StartEquipmentCatalog load() {
		List<String> lines = new ArrayList<>();
		try (InputStream in = StartEquipmentCatalog.class.getResourceAsStream(RESOURCE)) {
			if (in == null) {
				StartEquipmentCatalog empty = new StartEquipmentCatalog();
				empty.problems.add("resource " + RESOURCE + " not found");
				return empty;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) lines.add(line);
		} catch (IOException e) {
			StartEquipmentCatalog empty = new StartEquipmentCatalog();
			empty.problems.add("resource " + RESOURCE + " not readable: " + e.getMessage());
			return empty;
		}
		return parse(lines);
	}

	static StartEquipmentCatalog parse(List<String> lines) {
		StartEquipmentCatalog catalog = new StartEquipmentCatalog();
		Map<String, List<String>> kits = new HashMap<>();
		List<String[]> characters = new ArrayList<>();

		for (String raw : lines) {
			String line = raw.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			String[] f = line.split(";", -1);
			if (f[0].equals("kit")) {
				if (f.length < 3) { catalog.problems.add("kit line without clothes: " + line); continue; }
				kits.put(f[1].trim(), names(f[2]));
			} else {
				characters.add(f);
			}
		}
		for (String[] f : characters) {
			int number;
			try {
				number = Integer.parseInt(f[0].trim());
			} catch (NumberFormatException e) {
				catalog.problems.add("no character number: " + String.join(";", f));
				continue;
			}
			String kit = f.length > 1 ? f[1].trim() : "";
			List<String> kitClothes = kits.get(kit);
			if (kitClothes == null) {
				catalog.problems.add("character " + number + ": unknown kit '" + kit + "'");
				kitClothes = new ArrayList<>();
			}
			List<String> more = f.length > 2 ? names(f[2]) : new ArrayList<>();
			List<String> itemNames = f.length > 3 ? names(f[3]) : new ArrayList<>();
			boolean barefoot = f.length > 4 && names(f[4]).contains("barefoot");

			List<Piece> clothes = new ArrayList<>();
			for (String name : more) catalog.addPieces(clothes, name, barefoot, number);
			for (String name : kitClothes) catalog.addPieces(clothes, name, barefoot, number);

			List<String> items = new ArrayList<>();
			List<String> contents = new ArrayList<>();
			for (String name : itemNames) {
				String container = name;
				int open = name.indexOf('(');
				if (open > 0 && name.endsWith(")")) {
					container = name.substring(0, open);
					String content = catalog.resolve(name.substring(open + 1, name.length() - 1), number);
					if (content != null) contents.add(content);
				}
				String className = catalog.resolve(container, number);
				if (className != null) items.add(className);
			}
			if (catalog.entries.containsKey(number)) catalog.problems.add("character " + number + " twice");
			catalog.entries.put(number, new Entry(number, clothes, items, contents, barefoot));
		}
		return catalog;
	}

	private void addPieces(List<Piece> clothes, String name, boolean barefoot, int number) {
		Material material = Material.nothing;
		int open = name.indexOf('[');
		if (open > 0 && name.endsWith("]")) {
			String materialName = name.substring(open + 1, name.length() - 1).trim();
			name = name.substring(0, open).trim();
			material = Material.fromName(materialName);
			if (material == Material.nothing) {
				problems.add("character " + number + ": unknown material '" + materialName + "' for " + name);
				return;
			}
			if (!hasAlternativeOf(name, material)) {
				problems.add("character " + number + ": composition.csv has no alternative of " + name + " made of " + materialName);
				return;
			}
		}
		String className = resolve(name, number);
		if (className == null) return;
		Class<?> c = classOf(className);
		boolean pair = Shoe.class.isAssignableFrom(c) || Sock.class.isAssignableFrom(c) || Glove.class.isAssignableFrom(c);
		boolean foot = Shoe.class.isAssignableFrom(c) || Sock.class.isAssignableFrom(c);
		if (barefoot && foot) return;
		if (pair) {
			clothes.add(new Piece(className, Side.left, material));
			clothes.add(new Piece(className, Side.right, material));
		} else {
			clothes.add(new Piece(className, Side.none, material));
		}
	}

	/** @return whether one of the alternatives of the class in composition.csv has this main material */
	private static boolean hasAlternativeOf(String simpleName, Material material) {
		for (CompositionCatalog.Recipe recipe : CompositionCatalog.getInstance().get(simpleName)) {
			if (recipe.parts.get(recipe.indexOfMain()).material == material) return true;
		}
		return false;
	}

	private String resolve(String simpleName, int number) {
		String full = fullClassName(simpleName);
		if (full == null) problems.add("character " + number + ": unknown item class '" + simpleName + "'");
		return full;
	}

	private static List<String> names(String field) {
		List<String> result = new ArrayList<>();
		for (String part : field.split(",")) {
			String name = part.trim();
			if (!name.isEmpty()) result.add(name);
		}
		return result;
	}

	private static Class<?> classOf(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return Object.class;
		}
	}

	/**
	 * @return the full class name of an item class given by its simple name, null if there is none
	 */
	public static String fullClassName(String simpleName) {
		for (String pkg : PACKAGES) {
			String candidate = BASE + pkg + "." + simpleName;
			try {
				Class.forName(candidate, false, StartEquipmentCatalog.class.getClassLoader());
				return candidate;
			} catch (ClassNotFoundException e) {
				// next package
			}
		}
		return null;
	}

	/**
	 * @return the equipment of the character, null if it has none
	 */
	public Entry get(int characterNumber) {
		return entries.get(characterNumber);
	}

	public int size() {
		return entries.size();
	}

	/**
	 * @return what could not be read (unknown kits or classes); empty if all is well
	 */
	public List<String> getProblems() {
		return Collections.unmodifiableList(problems);
	}
}
