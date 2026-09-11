package org.socialworld.tools.mct;

import java.util.*;

public class MapSkeletonGenerator {

    private static final Random RANDOM = new Random();

    public static String generateSkeleton(int[] targetNorth, int[] targetEast, int[] targetSouth, int[] targetWest) {
        String[][] grid = new String[9][9];
        
        for (int r = 0; r < 9; r++) {
            Arrays.fill(grid[r], "TODO");
        }

        // 1. NORDRAND (Reihe 0, von West nach Ost)
        if (targetNorth != null && targetNorth.length == 10) {
            for (int c = 0; c < 9; c++) {
                grid[0][c] = getValidTileForGrid(grid, 0, c, "north", targetNorth[c], targetNorth[c + 1]);
            }
        }

        // 2. SÜDRAND (Reihe 8, von West nach Ost)
        if (targetSouth != null && targetSouth.length == 10) {
            for (int c = 0; c < 9; c++) {
                grid[8][c] = getValidTileForGrid(grid, 8, c, "south", targetSouth[c], targetSouth[c + 1]);
            }
        }

        // 3. WESTRAND (Spalte 0, von Nord nach Süd)
        if (targetWest != null && targetWest.length == 10) {
            for (int r = 0; r < 9; r++) {
                if (grid[r][0].equals("TODO")) {
                    grid[r][0] = getValidTileForGrid(grid, r, 0, "west", targetWest[r], targetWest[r + 1]);
                }
            }
        }

        // 4. OSTRAND (Spalte 8, von Nord nach Süd)
        if (targetEast != null && targetEast.length == 10) {
            for (int r = 0; r < 9; r++) {
                if (grid[r][8].equals("TODO")) {
                    grid[r][8] = getValidTileForGrid(grid, r, 8, "east", targetEast[r], targetEast[r + 1]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("sub(");
        List<String> allTokens = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                allTokens.add(grid[r][c]);
            }
        }
        sb.append(String.join(",", allTokens));
        sb.append(")");
        return sb.toString();
    }

    /**
     * Sucht eine Kachel, die die Kanten-Bedingung erfüllt UND lückenlos an linke/obere Nachbarn andockt.
     */
    private static String getValidTileForGrid(String[][] grid, int row, int col, String edge, int hFirst, int hSecond) {
        List<String> validTiles = new ArrayList<>();
        int diff = hSecond - hFirst;

        // Nachbarn im Grid ermitteln (falls vorhanden)
        String tileAbove = (row > 0) ? grid[row - 1][col] : "TODO";
        String tileLeft  = (col > 0) ? grid[row][col - 1] : "TODO";

        for (int type = 0; type <= 19; type++) {
            // Schutzfilter: Keine Grate (6,9)  an den Außenrändern
            if (type == 6 || type == 9) {
                continue;
            }

            int offsetFirst = 0;
            int offsetSecond = 0;

            switch (edge) {
                case "north": // (no - nw)
                    offsetFirst = TileInfo.getOffset(type, "nw");
                    offsetSecond = TileInfo.getOffset(type, "no");
                    break;
                case "east":  // (so - no)
                    offsetFirst = TileInfo.getOffset(type, "no");
                    offsetSecond = TileInfo.getOffset(type, "so");
                    break;
                case "south": // (so - sw)
                    offsetFirst = TileInfo.getOffset(type, "sw");
                    offsetSecond = TileInfo.getOffset(type, "so");
                    break;
                case "west":  // (sw - nw)
                    offsetFirst = TileInfo.getOffset(type, "nw");
                    offsetSecond = TileInfo.getOffset(type, "sw");
                    break;
            }

            // Prüfen, ob der Typ die geforderte Höhendifferenz an der Außenkante erzeugen kann
            if (offsetSecond - offsetFirst == diff) {
                int baseHeight = hFirst - offsetFirst;

                // Berechne die absoluten Eckenhöhen der potenziellen neuen Kachel
                int nwCur = baseHeight + TileInfo.getOffset(type, "nw");
                int noCur = baseHeight + TileInfo.getOffset(type, "no");
                int soCur = baseHeight + TileInfo.getOffset(type, "so");
                int swCur = baseHeight + TileInfo.getOffset(type, "sw");

                // --- 1. ABGLEICH MIT OBERER KACHEL ---
                if (!tileAbove.equals("TODO")) {
                    int typeAbove = Integer.parseInt(tileAbove.split("_")[1]);
                    int bhAbove = Integer.parseInt(tileAbove.split("_")[2]);
                    
                    int swAbove = bhAbove + TileInfo.getOffset(typeAbove, "sw");
                    int soAbove = bhAbove + TileInfo.getOffset(typeAbove, "so");

                    // Riss-Prüfung oben: neue Nord-Kante muss an obere Süd-Kante passen
                    if (nwCur != swAbove || noCur != soAbove) {
                        continue; 
                    }
                }

                // --- 2. ABGLEICH MIT LINKER KACHEL ---
                if (!tileLeft.equals("TODO")) {
                    int typeLeft = Integer.parseInt(tileLeft.split("_")[1]);
                    int bhLeft = Integer.parseInt(tileLeft.split("_")[2]);
                    
                    int noLeft = bhLeft + TileInfo.getOffset(typeLeft, "no");
                    int soLeft = bhLeft + TileInfo.getOffset(typeLeft, "so");

                    // Riss-Prüfung links: neue West-Kante muss an linke Ost-Kante passen
                    if (nwCur != noLeft || swCur != soLeft) {
                        continue;
                    }
                }

                // Bevorzugung für komplett flache Kacheln bei Höhendifferenz 0
                if (diff == 0 && type == 0) {
                    validTiles.clear();
                    validTiles.add("L_0_" + baseHeight);
                    break;
                }
                validTiles.add("L_" + type + "_" + baseHeight);
            }
        }

        // Fallback, falls die mathematischen Bedingungen absolut unlösbar sind
        if (validTiles.isEmpty()) {
            return "L_0_" + hFirst;
        }

        return validTiles.get(RANDOM.nextInt(validTiles.size()));
    }
}
