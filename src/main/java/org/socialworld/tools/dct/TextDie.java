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
	private int pass;
	private final int lastPass;

	public TextDie(String text) {
		this(text, 0, 0);
	}

	/**
	 * Reads the text in several passes: pass 0 takes letters 0, 3, 6 ..., pass 1 takes
	 * letters 1, 4, 7 ... So the matrices can use other letters than the attribute arrays.
	 */
	public TextDie(String text, int firstPass, int lastPass) {
		this.letters = onlyLetters(text);
		this.pass = firstPass;
		this.lastPass = lastPass;
		this.position = firstPass;
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
		int count = Math.max(0, (letters.length() - position + STEP - 1) / STEP);
		for (int later = pass + 1; later <= lastPass; later++) {
			count += Math.max(0, (letters.length() - later + STEP - 1) / STEP);
		}
		return count;
	}

	public double next() {
		while (position >= letters.length() && pass < lastPass) {
			pass++;
			position = pass;
		}
		if (position >= letters.length()) {
			throw new IllegalStateException("the text is used up");
		}
		char letter = letters.charAt(position);
		position += STEP;
		return (letter - 'a') / 25.0;
	}
}
