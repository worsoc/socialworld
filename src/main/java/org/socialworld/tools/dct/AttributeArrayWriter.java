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

import java.util.List;

import org.socialworld.tools.dct.AttributeArrayGenerator.GeneratedArray;

/**
 * Exchanges the generated attribute arrays record by record. Because swgauss_aa refers to
 * swpool_attribute, the order is, each step for all arrays:
 * 1. delete from swgauss_aa, 2. delete from swpool_attribute,
 * 3. insert into swpool_attribute, 4. insert into swgauss_aa.
 * Records of other aa_ids and gauss indices stay as they are.
 */
final class AttributeArrayWriter {

	private AttributeArrayWriter() {
	}

	static void write(List<GeneratedArray> arrays, AttributeArrayStore store) {

		for (GeneratedArray array : arrays) {
			store.deleteGaussIndex(array.getGaussIndex());
		}

		for (GeneratedArray array : arrays) {
			for (int attribNr = 1; attribNr <= array.getValues().length; attribNr++) {
				store.deleteAttribute(array.getAaID(), attribNr);
			}
		}

		for (GeneratedArray array : arrays) {
			int[] values = array.getValues();
			for (int i = 0; i < values.length; i++) {
				store.insertAttribute(array.getAaID(), i + 1, values[i]);
			}
		}

		for (GeneratedArray array : arrays) {
			store.insertGaussIndex(array.getGaussIndex(), array.getAaID());
		}
	}
}
