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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.socialworld.attributes.Attribute;

/**
 * The characters, sorted from the most ordinary (no. 1) to the rarest.
 * Read from characters.csv: no;name;sketch;9 level codes;sleepHours;mealsPerDay. Lines starting with # are comments.
 * The last two fields may be left out, then the character has the default need (8 hours, 3 meals).
 */
public class AttributeCharacterCatalog {

	public static final String RESOURCE = "characters.csv";

	private final List<AttributeCharacter> characters;

	private AttributeCharacterCatalog(List<AttributeCharacter> characters) {
		this.characters = Collections.unmodifiableList(characters);
	}

	public static AttributeCharacterCatalog load() {
		try (InputStream in = AttributeCharacterCatalog.class.getResourceAsStream(RESOURCE)) {
			if (in == null) throw new IllegalStateException(RESOURCE + " not found");
			return parse(new String(in.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static AttributeCharacterCatalog parse(String content) {
		List<AttributeCharacter> characters = new ArrayList<>();
		int fieldsWithoutNeed = 3 + Attribute.NUMBER_OF_ATTRIBUTES;
		int fieldsWithNeed = fieldsWithoutNeed + 2;
		int fieldsWithIdeal = fieldsWithNeed + 4;

		try (BufferedReader reader = new BufferedReader(new java.io.StringReader(content))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#")) continue;

				String[] fields = line.split(";", -1);
				if (fields.length != fieldsWithoutNeed && fields.length != fieldsWithNeed && fields.length != fieldsWithIdeal) {
					throw new IllegalArgumentException("expected " + fieldsWithoutNeed + ", " + fieldsWithNeed + " or "
							+ fieldsWithIdeal + " fields: " + line);
				}

				int number = Integer.parseInt(fields[0].trim());
				if (number != characters.size() + 1) {
					throw new IllegalArgumentException("expected character " + (characters.size() + 1) + ": " + line);
				}

				AttributeLevel[] levels = new AttributeLevel[Attribute.NUMBER_OF_ATTRIBUTES];
				for (int i = 0; i < levels.length; i++) {
					levels[i] = AttributeLevel.fromCode(fields[3 + i].trim());
				}
				float sleepHours = AttributeCharacter.DEFAULT_SLEEP_HOURS;
				int mealsPerDay = AttributeCharacter.DEFAULT_MEALS_PER_DAY;
				if (fields.length >= fieldsWithNeed) {
					// decimal point, as everywhere in the rules
					sleepHours = Float.parseFloat(fields[fieldsWithoutNeed].trim());
					mealsPerDay = Integer.parseInt(fields[fieldsWithoutNeed + 1].trim());
				}
				int[] ideal = {AttributeCharacter.DEFAULT_TIRED_WAKE, AttributeCharacter.DEFAULT_TIRED_BED,
						AttributeCharacter.DEFAULT_HUNGER_FULL, AttributeCharacter.DEFAULT_HUNGER_EAT};
				if (fields.length == fieldsWithIdeal) {
					for (int i = 0; i < ideal.length; i++) {
						ideal[i] = Integer.parseInt(fields[fieldsWithNeed + i].trim());
					}
				}
				characters.add(new AttributeCharacter(number, fields[1].trim(), fields[2].trim(), levels, sleepHours, mealsPerDay,
						ideal[0], ideal[1], ideal[2], ideal[3]));
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return new AttributeCharacterCatalog(characters);
	}

	public List<AttributeCharacter> getCharacters() {
		return characters;
	}

	public int size() {
		return characters.size();
	}
}
