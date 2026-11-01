package org.socialworld.tools.mct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ein gültiges 9x9-Raster aus reinen L-Kacheln, wie es in den onlyLs-Dateien steht.
 * Die Klasse hängt nicht von Swing ab und kann deshalb auch von anderen Tools genutzt werden.
 *
 * Alles außer genau 81 gültigen L-Kacheln wird mit einer IllegalArgumentException abgelehnt.
 */
public final class LTileRaster {

	public static final int TILE_COUNT = 81;

	private static final int MAX_TILE_NUMBER = 19;
	private static final int INVALID_HEIGHT = -999;

	private static final Pattern WRAPPED_TERM = Pattern.compile("sub(?:\\[[^\\]]*\\])?\\((.*)\\)");
	private static final Pattern L_TILE = Pattern.compile("L_(\\d+)_(-?\\d+)");

	private final int[] types;
	private final int[] baseHeights;

	private LTileRaster(int[] types, int[] baseHeights) {
		this.types = types;
		this.baseHeights = baseHeights;
	}

	public static LTileRaster fromFile(File file) throws IOException {
		return parse(Files.readString(file.toPath()));
	}

	/**
	 * Liest den Inhalt einer onlyLs-Datei, also "sub(L_0_0,L_3_0,...)".
	 */
	public static LTileRaster parse(String content) {
		String term = content.replaceAll("\\s", "");
		Matcher wrapped = WRAPPED_TERM.matcher(term);
		if (wrapped.matches()) {
			term = wrapped.group(1);
		}
		if (term.contains("(")) {
			throw new IllegalArgumentException("Das Raster enthält Teilraster (sub), erlaubt sind nur L-Kacheln.");
		}
		return fromTokens(Arrays.asList(term.split(",", -1)));
	}

	public static LTileRaster fromTokens(List<String> tokens) {
		if (tokens.size() != TILE_COUNT) {
			throw new IllegalArgumentException("Erwartet werden " + TILE_COUNT + " Kacheln, gefunden: " + tokens.size() + ".");
		}

		int[] types = new int[TILE_COUNT];
		int[] baseHeights = new int[TILE_COUNT];

		for (int i = 0; i < TILE_COUNT; i++) {
			String token = tokens.get(i);
			Matcher tile = L_TILE.matcher(token);
			if (!tile.matches()) {
				throw new IllegalArgumentException("Kachel " + i + " ist keine gültige L-Kachel: " + token);
			}
			types[i] = Integer.parseInt(tile.group(1));
			baseHeights[i] = Integer.parseInt(tile.group(2));
			if (types[i] > MAX_TILE_NUMBER) {
				throw new IllegalArgumentException("Kachel " + i + " hat eine unbekannte Kachelnummer: " + token);
			}
			if (baseHeights[i] == INVALID_HEIGHT) {
				throw new IllegalArgumentException("Kachel " + i + " hat eine ungültige Höhe: " + token);
			}
		}
		return new LTileRaster(types, baseHeights);
	}

	/**
	 * Liefert die Kacheln zeilenweise von Nord nach Süd und je Zeile von West nach Ost.
	 */
	public List<String> toTokens() {
		List<String> tokens = new ArrayList<>(TILE_COUNT);
		for (int i = 0; i < TILE_COUNT; i++) {
			tokens.add("L_" + types[i] + "_" + baseHeights[i]);
		}
		return tokens;
	}
}
