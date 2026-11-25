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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.createObjects.CompositionCatalog.Part;
import org.socialworld.datasource.createObjects.CompositionCatalog.Recipe;

/**
 * The variants of the composition of an item class (composition concept Z2, K3, 2026-11-24): five material sets per
 * class, so that instances of one kind differ within what is typical for it. The alternatives of a class take turns
 * (three alternatives: 2, 2, 1 variants). Within a recipe every share except the main one is drawn in its spread;
 * the main material takes the rest to 100 and has to stay in its own spread, otherwise the draw is repeated. The
 * random numbers start from the class name and the number of the variant, so the same catalog gives the same sets.
 */
public final class CompositionVariants {

	public static final int VARIANTS = 5;
	static final int MAX_TRIES = 200;

	/** One material set: the materials with their shares in percent, in the order of the recipe, adding up to 100. */
	public static final class Variant {
		public final List<Material> materials;
		public final List<Integer> shares;

		Variant(List<Material> materials, List<Integer> shares) {
			this.materials = Collections.unmodifiableList(materials);
			this.shares = Collections.unmodifiableList(shares);
		}

		public int share(Material material) {
			int at = materials.indexOf(material);
			return (at < 0) ? 0 : shares.get(at);
		}

		@Override
		public boolean equals(Object other) {
			return other instanceof Variant && materials.equals(((Variant) other).materials) && shares.equals(((Variant) other).shares);
		}

		@Override
		public int hashCode() {
			return materials.hashCode() * 31 + shares.hashCode();
		}

		@Override
		public String toString() {
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < materials.size(); i++) {
				if (i > 0) text.append(", ");
				text.append(materials.get(i).name()).append(' ').append(shares.get(i));
			}
			return text.toString();
		}
	}

	private CompositionVariants() {}

	/** The five variants of the class; empty if it has no recipe. */
	public static List<Variant> of(String className, List<Recipe> recipes) {
		List<Variant> variants = new ArrayList<>();
		if (recipes.isEmpty()) return variants;
		for (int number = 0; number < VARIANTS; number++) {
			Recipe recipe = recipes.get(number % recipes.size());
			variants.add(draw(recipe, new Random(seed(className, number))));
		}
		return variants;
	}

	static long seed(String className, int number) {
		return className.hashCode() * 1_000_003L + number;	// String.hashCode is the same on every machine
	}

	static Variant draw(Recipe recipe, Random random) {
		List<Part> parts = recipe.parts;
		int main = recipe.indexOfMain();
		for (int tries = 0; tries < MAX_TRIES; tries++) {
			int[] shares = new int[parts.size()];
			int others = 0;
			for (int i = 0; i < parts.size(); i++) {
				if (i == main) continue;
				Part part = parts.get(i);
				shares[i] = Math.max(1, part.share + random.nextInt(2 * part.spread + 1) - part.spread);
				others += shares[i];
			}
			shares[main] = 100 - others;
			Part mainPart = parts.get(main);
			if (shares[main] >= 1 && Math.abs(shares[main] - mainPart.share) <= mainPart.spread) return variant(parts, shares);
		}
		// does not happen with the catalog of 2026-11-24; the typical shares are a valid variant
		int[] typical = new int[parts.size()];
		for (int i = 0; i < parts.size(); i++) typical[i] = parts.get(i).share;
		return variant(parts, typical);
	}

	private static Variant variant(List<Part> parts, int[] shares) {
		List<Material> materials = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < parts.size(); i++) {
			materials.add(parts.get(i).material);
			values.add(shares[i]);
		}
		return new Variant(materials, values);
	}
}
