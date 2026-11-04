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

	private final int[] north;
	private final int[] east;
	private final int[] south;
	private final int[] west;

	/** Die vier Eckhöhen jeder Kachel: je Kachel NW, NO, SO, SW. */
	private final int[] tileCorners;
	private final int lowestHeight;
	private final int highestHeight;

	private LTileRaster(int[] types, int[] baseHeights) {
		this.types = types;
		this.baseHeights = baseHeights;

		List<VisualTile> grid = new ArrayList<>(TILE_COUNT);
		for (int i = 0; i < TILE_COUNT; i++) {
			grid.add(new VisualTile(types[i], baseHeights[i]));
		}
		this.north = BorderCalculator.calculateNorth(grid);
		this.east = BorderCalculator.calculateEast(grid);
		this.south = BorderCalculator.calculateSouth(grid);
		this.west = BorderCalculator.calculateWest(grid);

		this.tileCorners = new int[TILE_COUNT * 4];
		int lowest = Integer.MAX_VALUE;
		int highest = Integer.MIN_VALUE;
		String[] cornerNames = {"nw", "no", "so", "sw"};
		for (int i = 0; i < TILE_COUNT; i++) {
			for (int corner = 0; corner < 4; corner++) {
				int height = baseHeights[i] + TileInfo.getOffset(types[i], cornerNames[corner]);
				tileCorners[i * 4 + corner] = height;
				lowest = Math.min(lowest, height);
				highest = Math.max(highest, height);
			}
		}
		this.lowestHeight = lowest;
		this.highestHeight = highest;
	}

	public int getLowestHeight() { return lowestHeight; }
	public int getHighestHeight() { return highestHeight; }

	/**
	 * Liefert die Höhe an einem Punkt der Fläche. Jede Kachel besteht aus zwei ebenen Dreiecken, die an
	 * der Diagonale zwischen den beiden Ecken mit dem kleineren Höhenunterschied aneinanderstoßen.
	 * Sind beide Unterschiede gleich, ist es die höhere Diagonale: Bei zwei gegenüberliegenden
	 * angehobenen Ecken (Kacheln 6 und 9) entsteht so ein Grat.
	 *
	 * @param x von West nach Ost in Kachelbreiten, 0 bis 9
	 * @param y von Nord nach Süd in Kachelbreiten, 0 bis 9
	 */
	public double heightAt(double x, double y) {
		int col = Math.min((int) x, 8);
		int row = Math.min((int) y, 8);
		double u = x - col;
		double v = y - row;

		int first = (row * 9 + col) * 4;
		int nw = tileCorners[first];
		int no = tileCorners[first + 1];
		int so = tileCorners[first + 2];
		int sw = tileCorners[first + 3];

		int differenceNoSw = Math.abs(no - sw);
		int differenceNwSo = Math.abs(nw - so);
		boolean foldFromNoToSw = differenceNoSw < differenceNwSo
				|| (differenceNoSw == differenceNwSo && no + sw >= nw + so);

		if (foldFromNoToSw) {
			if (u + v <= 1) {
				return nw + u * (no - nw) + v * (sw - nw);
			}
			return so + (1 - u) * (sw - so) + (1 - v) * (no - so);
		}
		if (u >= v) {
			return nw + u * (no - nw) + v * (so - no);
		}
		return nw + v * (sw - nw) + u * (so - sw);
	}

	/** Eckhöhen des Nordrands von West nach Ost (10 Werte). */
	public int[] getNorthEdge() { return north.clone(); }
	/** Eckhöhen des Ostrands von Nord nach Süd (10 Werte). */
	public int[] getEastEdge() { return east.clone(); }
	/** Eckhöhen des Südrands von West nach Ost (10 Werte). */
	public int[] getSouthEdge() { return south.clone(); }
	/** Eckhöhen des Westrands von Nord nach Süd (10 Werte). */
	public int[] getWestEdge() { return west.clone(); }

	/**
	 * Liefert das Raster als 9x9-Grid [Reihe][Spalte] für die Darstellung, zum Beispiel in MapPanel.
	 */
	public VisualTile[][] toGrid() {
		VisualTile[][] grid = new VisualTile[9][9];
		for (int i = 0; i < TILE_COUNT; i++) {
			grid[i / 9][i % 9] = new VisualTile(types[i], baseHeights[i]);
		}
		return grid;
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
