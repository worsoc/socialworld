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

import org.socialworld.datasource.createObjects.CompositionVariants.Variant;

/**
 * What the button "Material-Sets aus Katalog" writes (composition concept Z3, 2026-11-24): the variants of every
 * class of composition.csv as material sets from ID 100 on, and the coupling class + variant -> set ID. Only the
 * plan, without the database; the PropertySetCreationTool writes it.
 */
public final class CompositionSets {

	/** The sets of the catalog start here; the IDs below stay free (the old sets 0 .. 5, the humans). */
	public static final int FIRST_SET_ID = 100;
	/** TableMaterialSet holds sets up to this ID (exclusive). */
	public static final int MAX_SET_ID = 1000;

	/** One row of swset_material. */
	public static final class SetRow {
		public final int setID;
		public final int lfdNr;
		public final int material;
		public final int share;

		SetRow(int setID, int lfdNr, int material, int share) {
			this.setID = setID;
			this.lfdNr = lfdNr;
			this.material = material;
			this.share = share;
		}
	}

	/** One row of swkind_materialset. */
	public static final class KindRow {
		public final String className;
		public final int variant;
		public final int setID;

		KindRow(String className, int variant, int setID) {
			this.className = className;
			this.variant = variant;
			this.setID = setID;
		}
	}

	public final List<SetRow> sets;
	public final List<KindRow> kinds;
	public final int lastSetID;

	private CompositionSets(List<SetRow> sets, List<KindRow> kinds, int lastSetID) {
		this.sets = Collections.unmodifiableList(sets);
		this.kinds = Collections.unmodifiableList(kinds);
		this.lastSetID = lastSetID;
	}

	/** The sets and couplings of the catalog, in the order of the file. */
	public static CompositionSets plan(CompositionCatalog catalog) {
		List<SetRow> sets = new ArrayList<>();
		List<KindRow> kinds = new ArrayList<>();
		int setID = FIRST_SET_ID;
		for (String className : catalog.classNames()) {
			List<Variant> variants = CompositionVariants.of(className, catalog.get(className));
			for (int number = 0; number < variants.size(); number++) {
				Variant variant = variants.get(number);
				for (int i = 0; i < variant.materials.size(); i++) {
					sets.add(new SetRow(setID, i + 1, variant.materials.get(i).getIndex(), variant.shares.get(i)));
				}
				kinds.add(new KindRow(className, number, setID));
				setID++;
			}
		}
		return new CompositionSets(sets, kinds, setID - 1);
	}

	/** false if the sets would not fit into TableMaterialSet. */
	public boolean fits() {
		return lastSetID < MAX_SET_ID;
	}
}
