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

import java.util.function.IntFunction;

import org.socialworld.attributes.BodyBuild;
import org.socialworld.attributes.properties.Material;

/**
 * Which material set a new instance gets (body build concept B3, 2026-11-25), derived from the template
 * swkind_materialset (class + variant -> set):
 * <ul>
 * <li>an item by its class (simple name)</li>
 * <li>a human by "Human." + the body build of its character</li>
 * <li>the variant is objectID % number of variants; with a material asked for (equipment.csv {@code Apron[leather]},
 * item class or composition step 2) only the variants whose main material it is count</li>
 * </ul>
 * The simulation saves the result per instance in swstate_composition.
 */
public final class CompositionAssignment {

	/** No set: the template has no variant for the name. */
	public static final int NO_SET = -1;

	private CompositionAssignment() {
	}

	/**
	 * @param simpleClassName the simple name of the class of the instance (e.g. Beret, Human)
	 * @param isHuman whether the instance is a human; then its body build chooses the template
	 * @param build the body build of the character of a human (not used for other objects)
	 * @return the name of the template in swkind_materialset
	 */
	public static String templateName(String simpleClassName, boolean isHuman, BodyBuild build) {
		return isHuman ? CompositionCatalog.humanKey(build) : simpleClassName;
	}

	/**
	 * @param variantSetIDs the set IDs of the variants of the template, in the order of the variants
	 * @param objectID the ID of the instance
	 * @return the set of variant objectID % number of variants, NO_SET if the template has none
	 */
	public static int setID(int[] variantSetIDs, int objectID) {
		if (variantSetIDs == null || variantSetIDs.length == 0 || objectID < 0) return NO_SET;
		return variantSetIDs[objectID % variantSetIDs.length];
	}

	/**
	 * @param variantSetIDs the set IDs of the variants of the template, in the order of the variants
	 * @param objectID the ID of the instance
	 * @param wanted the main material asked for; Material.nothing for none
	 * @param mainMaterialOf the main material (largest share) of a set ID
	 * @return among the variants with that main material the one of objectID % their number; without a material as
	 *         {@link #setID(int[], int)}; NO_SET if no variant has the material
	 */
	public static int setID(int[] variantSetIDs, int objectID, Material wanted, IntFunction<Material> mainMaterialOf) {
		if (wanted == null || wanted == Material.nothing) return setID(variantSetIDs, objectID);
		if (variantSetIDs == null) return NO_SET;
		int count = 0;
		int[] matching = new int[variantSetIDs.length];
		for (int id : variantSetIDs) {
			if (mainMaterialOf.apply(id) == wanted) matching[count++] = id;
		}
		if (count == 0) return NO_SET;
		int[] found = new int[count];
		System.arraycopy(matching, 0, found, 0, count);
		return setID(found, objectID);
	}

	/** @return the simple name of a full class name (the part after the last dot) */
	public static String simpleName(String fullClassName) {
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}
}
