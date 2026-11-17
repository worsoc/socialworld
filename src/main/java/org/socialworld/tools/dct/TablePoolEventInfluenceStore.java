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

import org.socialworld.datasource.tablesPool.TablePoolEID;

/**
 * Writes the event influence rules into the table swpool_eid.
 */
public class TablePoolEventInfluenceStore implements EventInfluenceStore {

	private final TablePoolEID table = new TablePoolEID();

	@Override
	public void clear() {
		table.clear();
	}

	@Override
	public void insert(int eventType, int influenceType, String json) {
		table.insert(eventType, influenceType, json);
	}

	@Override
	public String getDescription() {
		return "MariaDB, table swpool_eid";
	}
}
