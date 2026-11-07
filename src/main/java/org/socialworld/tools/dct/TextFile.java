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

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reads a text file for the text die. Most texts (e.g. from Project Gutenberg) are UTF-8;
 * older files are often ISO-8859-1, so that is the fallback when the file is no valid UTF-8.
 */
final class TextFile {

	private TextFile() {
	}

	static String read(Path file) throws IOException {
		try {
			return Files.readString(file, StandardCharsets.UTF_8);
		} catch (CharacterCodingException e) {
			return Files.readString(file, StandardCharsets.ISO_8859_1);
		}
	}
}
