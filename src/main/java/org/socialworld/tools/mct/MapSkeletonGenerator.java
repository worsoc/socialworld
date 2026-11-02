package org.socialworld.tools.mct;

import java.util.*;

public class MapSkeletonGenerator {

    private static final Random RANDOM = new Random();
    private static List<Integer> types_0_15 = new ArrayList<>();
    private static List<Integer> types_16_19 = new ArrayList<>();

    public static String generateSkeleton(int[] targetNorth, int[] targetEast, int[] targetSouth, int[] targetWest) {
        if (isCompleteEdge(targetNorth) && isCompleteEdge(targetEast) && isCompleteEdge(targetSouth) && isCompleteEdge(targetWest)) {
            return generateCompleteRing(targetNorth, targetEast, targetSouth, targetWest);
        }

        String[][] grid = new String[9][9];
        String tile;
        
        // Kacheltypen durchmischen
        types_0_15 = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
        	types_0_15.add(i);
        }
        Collections.shuffle(types_0_15);
        types_16_19 = new ArrayList<>();
        for (int i = 16; i <= 19; i++) {
        	types_16_19.add(i);
        }
        Collections.shuffle(types_16_19);
      
        for (int r = 0; r < 9; r++) {
            Arrays.fill(grid[r], "TODO");
        }

        // 1. NORDRAND (Reihe 0, von West nach Ost)
        if (targetNorth != null && targetNorth.length == 10) {
            for (int c = 0; c < 9; c++) {
            	tile = getValidTileForGrid(grid, 0, c, "north", targetNorth[c], targetNorth[c + 1]);
            	if (tile.equals("ERROR")) {
 //           		System.out.println("Error Nordrand");
            		return "";
            	}
                grid[0][c] = tile;
            }
        }

        // 2. SÜDRAND (Reihe 8, von West nach Ost)
        if (targetSouth != null && targetSouth.length == 10) {
            for (int c = 0; c < 9; c++) {
            	tile = getValidTileForGrid(grid, 8, c, "south", targetSouth[c], targetSouth[c + 1]);
            	if (tile.equals("ERROR")) {
  //          		System.out.println("Error Südrand");
            		return "";
            	}
                grid[8][c] = tile;
            }
        }

        // 3. WESTRAND (Spalte 0, von Nord nach Süd)
        if (targetWest != null && targetWest.length == 10) {
            for (int r = 0; r < 9; r++) {
                if (grid[r][0].equals("TODO")) {
                	tile = getValidTileForGrid(grid, r, 0, "west", targetWest[r], targetWest[r + 1]);
                	if (tile.equals("ERROR")) {
  //             		System.out.println("Error Westrand");
                		return "";
                	}
                    grid[r][0] = tile;
                }
            }
        }

        // 4. OSTRAND (Spalte 8, von Nord nach Süd)
        if (targetEast != null && targetEast.length == 10) {
            for (int r = 0; r < 9; r++) {
                if (grid[r][8].equals("TODO")) {
                	tile = getValidTileForGrid(grid, r, 8, "east", targetEast[r], targetEast[r + 1]);
                	if (tile.equals("ERROR")) {
   //             		System.out.println("Error Ostrand");
                		return "";
                	}
                    grid[r][8] = tile;
                }
            }
        }

        return formatSkeleton(grid);
    }

    private static String formatSkeleton(String[][] grid) {
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

    private static boolean isCompleteEdge(int[] edge) {
        return edge != null && edge.length == 10;
    }

    // =====================================================================
    // RING ZU VIER VOLLSTÄNDIGEN RÄNDERN
    // =====================================================================

    private static final int NW = 0, NO = 1, SO = 2, SW = 3;
    private static final int NONE = -1;
    private static final int RING_LENGTH = 32;
    private static final int HEIGHT_MARGIN = 3;

    /**
     * Eine Kachel des Rings. Die Ecken am Außenrand stehen fest. Die Kette führt im Uhrzeigersinn
     * um das Raster: Die Innenecke, die eine Kachel mit der vorigen teilt, ist ihre Eingangsecke,
     * die mit der nächsten geteilte ihre Ausgangsecke. Eine Eckkachel des Rasters hat nur eine
     * freie Ecke und reicht deren Höhe an die nächste Kachel weiter.
     */
    private static final class RingTile {
        final int row;
        final int col;
        final int[] corners = new int[4];
        final int inCorner;
        final int outCorner;

        RingTile(int row, int col, int inCorner, int outCorner) {
            this.row = row;
            this.col = col;
            this.inCorner = inCorner;
            this.outCorner = outCorner;
        }

        RingTile set(int corner, int height) {
            corners[corner] = height;
            return this;
        }

        /** @return die Kachel als {Typ, Basishöhe} oder null, wenn es für diese Ecken keinen Typ gibt */
        int[] resolve(int in, int out) {
            int[] c = corners.clone();
            if (inCorner != NONE) c[inCorner] = in;
            if (outCorner != NONE) c[outCorner] = out;
            for (int type = 0; type <= 19; type++) {
                int baseHeight = c[NW] - TileInfo.getOffset(type, "nw");
                if (c[NO] == baseHeight + TileInfo.getOffset(type, "no")
                        && c[SO] == baseHeight + TileInfo.getOffset(type, "so")
                        && c[SW] == baseHeight + TileInfo.getOffset(type, "sw")) {
                    return new int[] {type, baseHeight};
                }
            }
            return null;
        }

        /** Eine Eckkachel des Rasters gibt ihre Eingangshöhe unverändert weiter. */
        boolean passesThrough() {
            return outCorner == NONE;
        }
    }

    /**
     * Berechnet die 32 Randkacheln gemeinsam, sodass sie zu allen vier Rändern und zueinander passen:
     * Vorwärts wird ermittelt, welche Höhen die Innenecken annehmen können, rückwärts wird zufällig
     * unter den Höhen gewählt, die sicher zu einem geschlossenen Ring führen. Gibt es einen Ring,
     * wird er also ohne Sackgasse gefunden.
     *
     * @return das Skelett oder einen leeren String, wenn es zu diesen Rändern keinen Ring gibt
     */
    private static String generateCompleteRing(int[] targetNorth, int[] targetEast, int[] targetSouth, int[] targetWest) {
        // Die Ränder über ihre gemeinsamen Eckpunkte auf eine Höhe bringen. Geht die Schleife
        // danach nicht auf, ist das Raster eine Escher-Treppe und damit unlösbar.
        int[] north = targetNorth;
        int[] east = shifted(targetEast, north[9] - targetEast[0]);
        int[] south = shifted(targetSouth, east[9] - targetSouth[9]);
        int[] west = shifted(targetWest, south[0] - targetWest[9]);
        if (west[0] != north[0]) {
            return "";
        }

        List<RingTile> ring = buildRing(north, east, south, west);

        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;
        for (int[] edge : new int[][] {north, east, south, west}) {
            for (int height : edge) {
                lowest = Math.min(lowest, height);
                highest = Math.max(highest, height);
            }
        }
        List<Integer> heights = new ArrayList<>();
        for (int height = lowest - HEIGHT_MARGIN; height <= highest + HEIGHT_MARGIN; height++) {
            heights.add(height);
        }

        Collections.shuffle(heights, RANDOM);
        for (int start : heights) {
            int[][] tiles = findRing(ring, start, heights);
            if (tiles != null) {
                String[][] grid = new String[9][9];
                for (int r = 0; r < 9; r++) {
                    Arrays.fill(grid[r], "TODO");
                }
                for (int i = 0; i < RING_LENGTH; i++) {
                    grid[ring.get(i).row][ring.get(i).col] = "L_" + tiles[i][0] + "_" + tiles[i][1];
                }
                return formatSkeleton(grid);
            }
        }
        return "";
    }

    private static int[] shifted(int[] edge, int shift) {
        int[] result = edge.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] += shift;
        }
        return result;
    }

    /** Legt die Kette im Uhrzeigersinn an, beginnend bei der NW-Kachel des Rasters. */
    private static List<RingTile> buildRing(int[] north, int[] east, int[] south, int[] west) {
        List<RingTile> ring = new ArrayList<>(RING_LENGTH);

        ring.add(new RingTile(0, 0, NONE, SO).set(NW, north[0]).set(NO, north[1]).set(SW, west[1]));
        for (int col = 1; col <= 7; col++) {
            ring.add(new RingTile(0, col, SW, SO).set(NW, north[col]).set(NO, north[col + 1]));
        }
        ring.add(new RingTile(0, 8, SW, NONE).set(NW, north[8]).set(NO, north[9]).set(SO, east[1]));
        for (int row = 1; row <= 7; row++) {
            ring.add(new RingTile(row, 8, NW, SW).set(NO, east[row]).set(SO, east[row + 1]));
        }
        ring.add(new RingTile(8, 8, NW, NONE).set(NO, east[8]).set(SO, east[9]).set(SW, south[8]));
        for (int col = 7; col >= 1; col--) {
            ring.add(new RingTile(8, col, NO, NW).set(SW, south[col]).set(SO, south[col + 1]));
        }
        ring.add(new RingTile(8, 0, NO, NONE).set(NW, west[8]).set(SW, west[9]).set(SO, south[1]));
        for (int row = 7; row >= 1; row--) {
            ring.add(new RingTile(row, 0, SO, NO).set(NW, west[row]).set(SW, west[row + 1]));
        }
        return ring;
    }

    /**
     * @param start die Höhe der SO-Ecke der NW-Kachel, an der sich die Kette schließen muss
     * @return die 32 Kacheln als {Typ, Basishöhe} oder null, wenn sich die Kette nicht schließen lässt
     */
    private static int[][] findRing(List<RingTile> ring, int start, List<Integer> heights) {
        if (ring.get(0).resolve(NONE, start) == null) return null;

        // Vorwärts: reachable[i] sind die Höhen, die die Kette nach Kachel i haben kann.
        List<Set<Integer>> reachable = new ArrayList<>(RING_LENGTH);
        reachable.add(new HashSet<>(Collections.singleton(start)));
        for (int i = 1; i < RING_LENGTH; i++) {
            RingTile tile = ring.get(i);
            Set<Integer> next = new HashSet<>();
            for (int in : reachable.get(i - 1)) {
                if (tile.passesThrough()) {
                    if (tile.resolve(in, NONE) != null) next.add(in);
                } else {
                    for (int out : heights) {
                        if (tile.resolve(in, out) != null) next.add(out);
                    }
                }
            }
            reachable.add(next);
        }
        if (!reachable.get(RING_LENGTH - 1).contains(start)) return null;

        // Rückwärts: Von den Höhen, die vorwärts erreichbar waren, führt jede sicher zum Anfang zurück.
        int[][] tiles = new int[RING_LENGTH][];
        tiles[0] = ring.get(0).resolve(NONE, start);
        int out = start;
        for (int i = RING_LENGTH - 1; i >= 1; i--) {
            RingTile tile = ring.get(i);
            List<Integer> candidates = new ArrayList<>();
            for (int in : reachable.get(i - 1)) {
                boolean fits = tile.passesThrough() ? (in == out && tile.resolve(in, NONE) != null) : tile.resolve(in, out) != null;
                if (fits) candidates.add(in);
            }
            int in = candidates.get(RANDOM.nextInt(candidates.size()));
            tiles[i] = tile.resolve(in, out);
            out = in;
        }
        return tiles;
    }

    /**
     * Sucht eine Kachel, die die Kanten-Bedingung erfüllt UND lückenlos an linke/obere Nachbarn andockt.
     * Nutzt ein 2-Stufen-Prioritätssystem, um komplexe Typen (16-19) zu vermeiden.
     */
    private static String getValidTileForGrid(String[][] grid, int row, int col, String edge, int hFirst, int hSecond) {
        List<String> validTiles = new ArrayList<>();
        int diff = hSecond - hFirst;

        // Nachbarn im Grid ermitteln
        String tileAbove = (row > 0) ? grid[row - 1][col] : "TODO";
        String tileLeft  = (col > 0) ? grid[row][col - 1] : "TODO";

        // SCHUTZFILTER: Bedingungen für die beiden südlichen Ecken definieren
        boolean isSouthWestCorner = (row == 8 && col == 0);
        boolean isSouthEastCorner = (row == 8 && col == 8);

        // =====================================================================
        // STUFE 1: Suche strictly nur in den einfachen Standard-Kacheln (0 bis 15)
        // =====================================================================
        for (int type : types_0_15) {
            // Filter für Südwest-Ecke (8 und 11 und 12 wieder freigegeben!)
            if (isSouthWestCorner) {
                if (type == 9 || type == 13) {
                    continue; 
                }
            }
            // Filter für Südost-Ecke
            if (isSouthEastCorner) {
                if (type == 14) {
                    continue;
                }
            }

            checkAndAddTileType(type, diff, hFirst, edge, tileAbove, tileLeft, validTiles);
        }

        // Spezielle Bevorzugung für komplett flache Kacheln bei Höhendifferenz 0 beibehalten
        if (diff == 0) {
            for (String tile : validTiles) {
                if (tile.startsWith("L_0_")) {
                    validTiles.clear();
                    validTiles.add(tile);
                    return validTiles.get(RANDOM.nextInt(validTiles.size()));
                }
            }
        }

        // =====================================================================
        // STUFE 2: Nur wenn Stufe 1 KEINE einzige Kachel gefunden hat
        // =====================================================================
        if (validTiles.isEmpty()) {
            for (int type : types_16_19)  {
                // Auch in Stufe 2 die Filter für die Ecken einhalten
                if (isSouthWestCorner) {
                    if (type == 9 || type == 13) {
                        continue;
                    }
                }
                if (isSouthEastCorner) {
                    if (type == 14) {
                        continue;
                    }
                }
                checkAndAddTileType(type, diff, hFirst, edge, tileAbove, tileLeft, validTiles);
            }
        }

        // Abbruch, falls die mathematischen Bedingungen absolut unlösbar sind
        if (validTiles.isEmpty()) {
            return "ERROR";
        }

        return validTiles.get(RANDOM.nextInt(validTiles.size()));
    }

    /**
     * Hilfsmethode, um die Geometrie- und Nachbarschaftsprüfung für einen Typ durchzuführen.
     */
    private static void checkAndAddTileType(int type, int diff, int hFirst, String edge, String tileAbove, String tileLeft, List<String> validTiles) {
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

               // doesn't work:  if ((nwCur - noCur) != (swAbove - soAbove)) {
 	            if (nwCur != swAbove || noCur != soAbove) {
                     return; 
                }
            }

            // --- 2. ABGLEICH MIT LINKER KACHEL ---
            if (!tileLeft.equals("TODO")) {
                int typeLeft = Integer.parseInt(tileLeft.split("_")[1]);
                int bhLeft = Integer.parseInt(tileLeft.split("_")[2]);
                
                int noLeft = bhLeft + TileInfo.getOffset(typeLeft, "no");
                int soLeft = bhLeft + TileInfo.getOffset(typeLeft, "so");

                // doesn't work: if ((nwCur - swCur) != (noLeft - soLeft)) {
                if (nwCur != noLeft || swCur != soLeft) {
                   return;
                }
            }

            validTiles.add("L_" + type + "_" + baseHeight);
        }
    }
}
