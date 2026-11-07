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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Keeps the attribute arrays in memory and notes every call, for the tests.
 * Like the tables it refuses to insert a record that is already there, and like the
 * foreign key of swgauss_aa it refuses a gauss index without attributes and the deletion
 * of attributes a gauss index still refers to.
 */
final class InMemoryAttributeArrayStore implements AttributeArrayStore {

	private final Map<String, Integer> attributes = new TreeMap<>();
	private final Map<Integer, Integer> gaussIndices = new TreeMap<>();
	private final List<String> calls = new ArrayList<>();

	private static String key(int aaID, int attribNr) {
		return aaID + "/" + attribNr;
	}

	@Override
	public void deleteAttribute(int aaID, int attribNr) {
		calls.add("deleteAttribute " + key(aaID, attribNr));
		if (gaussIndices.containsValue(aaID)) {
			throw new IllegalStateException("aa_id " + aaID + " is still used in swgauss_aa");
		}
		attributes.remove(key(aaID, attribNr));
	}

	@Override
	public void insertAttribute(int aaID, int attribNr, int value) {
		calls.add("insertAttribute " + key(aaID, attribNr));
		if (attributes.putIfAbsent(key(aaID, attribNr), value) != null) {
			throw new IllegalStateException("attribute " + key(aaID, attribNr) + " exists already");
		}
	}

	@Override
	public void deleteGaussIndex(int gaussIndex) {
		calls.add("deleteGaussIndex " + gaussIndex);
		gaussIndices.remove(gaussIndex);
	}

	@Override
	public void insertGaussIndex(int gaussIndex, int aaID) {
		calls.add("insertGaussIndex " + gaussIndex);
		if (!attributes.keySet().stream().anyMatch(k -> k.startsWith(aaID + "/"))) {
			throw new IllegalStateException("aa_id " + aaID + " has no attributes in swpool_attribute");
		}
		if (gaussIndices.putIfAbsent(gaussIndex, aaID) != null) {
			throw new IllegalStateException("gauss_index " + gaussIndex + " exists already");
		}
	}

	Integer getAttribute(int aaID, int attribNr) {
		return attributes.get(key(aaID, attribNr));
	}

	Integer getAaID(int gaussIndex) {
		return gaussIndices.get(gaussIndex);
	}

	int countAttributes() {
		return attributes.size();
	}

	int countGaussIndices() {
		return gaussIndices.size();
	}

	List<String> getCalls() {
		return Collections.unmodifiableList(calls);
	}

	@Override
	public String getDescription() {
		return "memory, not the database";
	}
}
