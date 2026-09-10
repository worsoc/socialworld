package org.socialworld.tools.mct;

import java.util.*;

public class MapSkeletonGenerator {

    private static final Random RANDOM = new Random();

    /**
     * Erzeugt den Inhalt einer onlyLs-Datei (9x9 Grid) basierend auf vorgegebenen Rändern.
     * 
     * @param targetNorth Die 10 Höhenpunkte des Nordrands (z.B. [0, 0, 0, ...]) oder null/leer
     * @param targetEast  Die 10 Höhenpunkte des Ostrands oder null/leer
     * @param targetSouth Die 10 Höhenpunkte des Südrands oder null/leer
     * @param targetWest  Die 10 Höhenpunkte des Westrands oder null/leer
     * @return Ein String im Format sub(L_X_Y, L_X_Y, ...) mit 81 Elementen
     */
    public static String generateSkeleton(int[] targetNorth, int[] targetEast, int[] targetSouth, int[] targetWest) {
        // Ein 9x9 Grid für die Ausgabe-Tokens initialisieren (9 Zeilen, 9 Spalten)
        String[][] grid = new String[9][9];
        
        // Alle Felder standardmäßig mit "TODO" vorbelegen
        for (int r = 0; r < 9; r++) {
            Arrays.fill(grid[r], "TODO");
        }

        // 1. NORDRAND vorbefüllen (Zeile 0, Spalten 0-8)
        if (targetNorth != null && targetNorth.length == 10) {
            for (int c = 0; c < 9; c++) {
                int hLeft = targetNorth[c];
                int hRight = targetNorth[c + 1];
                grid[0][c] = getRandomTileForEdge("horizontal", hLeft, hRight);
            }
        }

        // 2. SÜDRAND vorbefüllen (Zeile 8, Spalten 0-8)
        if (targetSouth != null && targetSouth.length == 10) {
            for (int c = 0; c < 9; c++) {
                int hLeft = targetSouth[c];
                int hRight = targetSouth[c + 1];
                // Beim Südrand beschreiben die Punkte 'sw' und 'so' die Kante von West nach Ost
                grid[8][c] = getRandomTileForEdge("horizontal", hLeft, hRight);
            }
        }

        // 3. WESTRAND vorbefüllen (Spalte 0, Zeilen 0-8)
        if (targetWest != null && targetWest.length == 10) {
            for (int r = 0; r < 9; r++) {
                int hTop = targetWest[r];
                int hBottom = targetWest[r + 1];
                // Wenn die Ecke durch den Nord-/Südrand schon belegt ist, überschreiben wir sie nur,
                // falls dort noch TODO steht, um Konflikte an den echten Ecken zu vermeiden.
                if (grid[r][0].equals("TODO")) {
                    grid[r][0] = getRandomTileForEdge("vertical", hTop, hBottom);
                }
            }
        }

        // 4. OSTRAND vorbefüllen (Spalte 8, Zeilen 0-8)
        if (targetEast != null && targetEast.length == 10) {
            for (int r = 0; r < 9; r++) {
                int hTop = targetEast[r];
                int hBottom = targetEast[r + 1];
                if (grid[r][8].equals("TODO")) {
                    grid[r][8] = getRandomTileForEdge("vertical", hTop, hBottom);
                }
            }
        }

        // 5. Grid zu einem einzigen sub(...)-String zusammenbauen
        StringBuilder sb = new StringBuilder();
        sb.append("sub(");
        
        List<String> allTokens = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                allTokens.add(grid[r][c]);
            }
        }
        
        sb.append(String.join(", ", allTokens));
        sb.append(")");
        
        return sb.toString();
    }

    /**
     * Ermittelt eine mathematisch korrekte Kachel ("L_TYP_HÖHE"), die eine bestimmte Höhendifferenz
     * zwischen zwei nebeneinander oder untereinander liegenden Ecken erzeugt.
     */
    private static String getRandomTileForEdge(String direction, int hFirst, int hSecond) {
        List<String> validTiles = new ArrayList<>();
        int diff = hSecond - hFirst;

        if ("horizontal".equals(direction)) {
            // Wir suchen Kombinationen, bei denen (BaseHeight + OffsetRight) - (BaseHeight + OffsetLeft) == diff
            // Also: OffsetRight - OffsetLeft == diff
            // Für den Nordrand: 'no' - 'nw' == diff. Für den Südrand: 'so' - 'sw' == diff.
            for (int type = 0; type <= 19; type++) {
                int offsetFirst = ("horizontal".equals(direction)) ? TileGeometry.getOffset(type, "nw") : TileGeometry.getOffset(type, "nw");
                int offsetSecond = ("horizontal".equals(direction)) ? TileGeometry.getOffset(type, "no") : TileGeometry.getOffset(type, "sw");
                
                // Am Südrand verwenden die Berechnungsmethoden 'sw' und 'so'
                // Da getOffset die Differenzen universell berechnet, prüfen wir die Kacheltypen:
                if (offsetSecond - offsetFirst == diff) {
                    int baseHeight = hFirst - offsetFirst;
                    validTiles.add("L_" + type + "_" + baseHeight);
                }
            }
        } else { // vertical
            // Für den West- und Ostrand von oben nach unten: 'sw' - 'nw' == diff (bzw. 'so' - 'no' == diff)
            for (int type = 0; type <= 19; type++) {
                int offsetFirst = TileGeometry.getOffset(type, "nw");
                int offsetSecond = TileGeometry.getOffset(type, "sw");
                if (offsetSecond - offsetFirst == diff) {
                    int baseHeight = hFirst - offsetFirst;
                    validTiles.add("L_" + type + "_" + baseHeight);
                }
            }
        }

        // Fallback, falls eine unmögliche Höhendifferenz übergeben wurde (z.B. Sprung von 5 Stufen)
        if (validTiles.isEmpty()) {
            return "L_0_" + hFirst; // Standardmäßig flache Kachel auf der Ausgangshöhe
        }

        // Zufällige Auswahl aus den mathematisch passenden Kacheln
        return validTiles.get(RANDOM.nextInt(validTiles.size()));
    }
}
