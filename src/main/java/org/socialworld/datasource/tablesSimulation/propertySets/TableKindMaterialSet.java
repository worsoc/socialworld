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
package org.socialworld.datasource.tablesSimulation.propertySets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.datasource.mariaDB.Table;

/**
 * The coupling of an item class and its variants to the material sets in swset_material (composition concept Z3,
 * 2026-11-24): swkind_materialset (class_name, variant, material_set_id). It holds no shares; the sets themselves
 * are in swset_material. Written by the PropertySetCreationTool ("Material-Sets aus Katalog"), read when an item
 * gets its composition.
 */
public class TableKindMaterialSet extends Table {

	public static final String TABLE_NAME = "swkind_materialset";
	public final String ALL_COLUMNS = " class_name, variant, material_set_id ";

	private static TableKindMaterialSet instance;

	String class_name[];
	int variant[];
	int material_set_id[];

	// class name -> the set IDs of its variants, in the order of the variants
	private final Map<String, int[]> setIDsByClass = new HashMap<>();

	/** The table with all couplings loaded, for reading while the simulation runs. */
	public static synchronized TableKindMaterialSet getInstance() {
		if (instance == null) {
			instance = new TableKindMaterialSet();
			instance.load();
		}
		return instance;
	}

	/** A table without loading, for writing (PropertySetCreationTool). */
	public TableKindMaterialSet() {
		super();
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String getSelectList(int selectList) {
		return ALL_COLUMNS;
	}

	@Override
	public void select(String statement) {
		ResultSet rs = connection.executeQuery(statement);
		int row = 0;
		class_name = new String[rowCount];
		variant = new int[rowCount];
		material_set_id = new int[rowCount];
		try {
			while (rs.next()) {
				class_name[row] = rs.getString(1);
				variant[row] = rs.getInt(2);
				material_set_id[row] = rs.getInt(3);
				row++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		long lockingID = lockWithWait();
		select(SELECT_ALL_COLUMNS, "", " ORDER BY class_name, variant");
		Map<String, List<Integer>> collected = new HashMap<>();
		for (int row = 0; row < class_name.length; row++) {
			collected.computeIfAbsent(class_name[row], name -> new ArrayList<>()).add(material_set_id[row]);
		}
		for (Map.Entry<String, List<Integer>> entry : collected.entrySet()) {
			int[] ids = new int[entry.getValue().size()];
			for (int i = 0; i < ids.length; i++) ids[i] = entry.getValue().get(i);
			setIDsByClass.put(entry.getKey(), ids);
		}
		unlock(lockingID);
	}

	/** The set IDs of the variants of the class (simple name), an empty array if it has none. */
	public int[] getMaterialSetIDs(String className) {
		int[] ids = setIDsByClass.get(className);
		return (ids == null) ? new int[0] : ids.clone();
	}

	/**
	 * The names come from composition.csv: the simple names of the item classes and "Human." + body build. Only
	 * letters, digits, _ and . are let into the SQL statement; until 2026-11-25 the dot was missing, so the humans
	 * were left out without a word.
	 */
	public static boolean isValidClassName(String className) {
		return className != null && className.matches("[A-Za-z0-9_.]+");
	}

	public void insert(String className, int variant, int materialSetID) {
		if (!isValidClassName(className)) return;
		insert("INSERT INTO " + TABLE_NAME + " (class_name, variant, material_set_id) VALUES ('" + className + "', "
				+ variant + ", " + materialSetID + ")");
	}
}
