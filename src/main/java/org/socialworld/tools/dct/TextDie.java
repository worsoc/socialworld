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

/**
 * A natural text used as a die: every third letter gives its position in the alphabet
 * as a share between 0 (a) and 1 (z). The letters are not evenly distributed, and that
 * is intended: the language shapes the values.
 */
public class TextDie {

	public static final int STEP = 3;

	private final String letters;
	private int position;

	public TextDie(String text) {
		this.letters = onlyLetters(text);
		this.position = 0;
	}

	/**
	 * Lower case, umlauts and ß folded to a, o, u and s, everything else dropped.
	 */
	static String onlyLetters(String text) {
		StringBuilder result = new StringBuilder(text.length());
		for (char c : text.toLowerCase().toCharArray()) {
			switch (c) {
			case 'ä': result.append('a'); break;
			case 'ö': result.append('o'); break;
			case 'ü': result.append('u'); break;
			case 'ß': result.append('s'); break;
			default:
				if (c >= 'a' && c <= 'z') result.append(c);
			}
		}
		return result.toString();
	}

	/**
	 * How many shares the text can still give.
	 */
	public int countRemaining() {
		if (position >= letters.length()) return 0;
		return (letters.length() - position + STEP - 1) / STEP;
	}

	public double next() {
		if (position >= letters.length()) {
			throw new IllegalStateException("the text is used up");
		}
		char letter = letters.charAt(position);
		position += STEP;
		return (letter - 'a') / 25.0;
	}
}
