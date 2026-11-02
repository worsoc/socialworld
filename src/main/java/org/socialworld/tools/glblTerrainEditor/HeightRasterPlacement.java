package org.socialworld.tools.glblTerrainEditor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import org.socialworld.tools.mct.EdgeProfile;
import org.socialworld.tools.mct.LTileRaster;

/**
 * Prüft, ob ein Raster in eine Makro-Zelle passt, und berechnet dessen Höhenversatz.
 *
 * Absolute Höhe = Höhe aus dem Raster + OFFSET der Zelle. Ein Punkt der Welt hat nur eine Höhe, deshalb gilt:
 * - Zellen mit gemeinsamer Kante müssen dort in allen 10 Eckhöhen übereinstimmen.
 * - Diagonal benachbarte Zellen müssen im gemeinsamen Eckpunkt übereinstimmen.
 *
 * Belegte Zellen, die sich berühren, bilden einen Bereich. Verbindet ein Raster mehrere Bereiche, werden diese
 * in der Höhe verschoben. Berührt es denselben Bereich mit widersprüchlichen Höhen, zum Beispiel beim Schließen
 * eines Rings um eine Lücke, passt es nicht.
 */
public final class HeightRasterPlacement {

    private static final int[][] DIRECTIONS = {
        {-1, -1}, {0, -1}, {1, -1},
        {-1,  0},          {1,  0},
        {-1,  1}, {0,  1}, {1,  1}
    };

    private static final int[][] ORTHOGONAL_DIRECTIONS = { {0, -1}, {1, 0}, {0, 1}, {-1, 0} };
    private static final int CORNER_NW = 0, CORNER_NO = 1, CORNER_SO = 2, CORNER_SW = 3;
    private static final int EDGE_STEPS = 9;
    private static final int[] FREE_EDGE_STEPS = {-1, 0, 0, 1};
    private static final int CORNER_PROPOSALS = 30;

    private static final class Neighbour {
        final int dx;
        final int dy;
        final MacroMapCell cell;
        final int area;

        Neighbour(int dx, int dy, MacroMapCell cell, int area) {
            this.dx = dx;
            this.dy = dy;
            this.cell = cell;
            this.area = area;
        }
    }

    /** Ein zulässiges Ergebnis für genau ein Raster. */
    public final class Result {
        private final LTileRaster raster;
        private final int offset;
        private final Integer[] areaShifts;

        private Result(LTileRaster raster, int offset, Integer[] areaShifts) {
            this.raster = raster;
            this.offset = offset;
            this.areaShifts = areaShifts;
        }

        public int getOffset() { return offset; }

        /**
         * Prüft, ob freie Zellen zwischen diesem Raster und Zellen zwei Felder weiter lösbar bleiben.
         * Eine Kante hat 9 Kacheln und ändert sich je Kachel um höchstens 1, ihre beiden
         * Eckpunkte dürfen also höchstens 9 Stufen auseinanderliegen. Das gilt auch für Zellen
         * eines Bereichs, der durch dieses Raster verschoben würde.
         */
        public boolean keepsCellsReachable() {
            return HeightRasterPlacement.this.keepsCellsReachable(absoluteCorners(raster, offset), areaShifts);
        }

        /** Ordnet das Raster zu und verschiebt alle Bereiche, die dadurch verbunden werden. */
        public void apply(String source) {
            for (int area = 0; area < areas.size(); area++) {
                if (areaShifts[area] != null && areaShifts[area] != 0) {
                    for (MacroMapCell cell : areas.get(area)) {
                        cell.setHeightRaster(cell.getHeightRaster(), cell.getHeightRasterSource(),
                                cell.getHeightRasterOffset() + areaShifts[area]);
                    }
                }
            }
            target.setHeightRaster(raster, source, offset);
        }
    }

    private final MacroMap map;
    private final int cellX;
    private final int cellY;
    private final int[][] areaOf;
    private final MacroMapCell target;
    private final List<List<MacroMapCell>> areas = new ArrayList<>();
    private final List<Neighbour> neighbours = new ArrayList<>();

    /**
     * Bereitet die Prüfung für eine Zelle vor. Ein dort schon vorhandenes Raster zählt nicht mit,
     * sodass dieselbe Prüfung auch für das Ersetzen gilt.
     */
    public HeightRasterPlacement(MacroMap map, int cellX, int cellY) {
        this.map = map;
        this.cellX = cellX;
        this.cellY = cellY;
        this.target = map.getCell(cellX, cellY);

        this.areaOf = new int[map.getWidth()][map.getHeight()];
        for (int[] column : areaOf) {
            java.util.Arrays.fill(column, -1);
        }

        for (int[] d : DIRECTIONS) {
            int x = cellX + d[0];
            int y = cellY + d[1];
            if (!isOccupied(map, x, y)) continue;
            if (areaOf[x][y] < 0) {
                collectArea(map, x, y, cellX, cellY, areaOf);
            }
            neighbours.add(new Neighbour(d[0], d[1], map.getCell(x, y), areaOf[x][y]));
        }
    }

    private void collectArea(MacroMap map, int startX, int startY, int cellX, int cellY, int[][] areaOf) {
        int area = areas.size();
        List<MacroMapCell> cells = new ArrayList<>();
        Deque<int[]> open = new ArrayDeque<>();

        areaOf[startX][startY] = area;
        open.add(new int[] {startX, startY});
        while (!open.isEmpty()) {
            int[] current = open.poll();
            cells.add(map.getCell(current[0], current[1]));
            for (int[] d : DIRECTIONS) {
                int x = current[0] + d[0];
                int y = current[1] + d[1];
                if ((x == cellX && y == cellY) || !isOccupied(map, x, y) || areaOf[x][y] >= 0) continue;
                areaOf[x][y] = area;
                open.add(new int[] {x, y});
            }
        }
        areas.add(cells);
    }

    /**
     * @return das Ergebnis für dieses Raster oder null, wenn es nicht in die Zelle passt
     */
    public Result evaluate(LTileRaster raster) {
        Integer offset = null;
        Integer[] areaShifts = new Integer[areas.size()];

        for (Neighbour neighbour : neighbours) {
            Integer required = requiredOffset(raster, neighbour.cell, neighbour.dx, neighbour.dy);
            if (required == null) return null;
            if (offset == null) offset = required;

            int shift = offset - required;
            if (areaShifts[neighbour.area] == null) {
                areaShifts[neighbour.area] = shift;
            } else if (areaShifts[neighbour.area] != shift) {
                return null;
            }
        }
        return new Result(raster, (offset != null) ? offset : 0, areaShifts);
    }

    /**
     * Prüft, ob eine belegte Zelle in ihrer jetzigen Höhe nicht zu einem Nachbarn passt.
     * Das kann nur bei geladenen Karten vorkommen, weil die Zuordnung im Editor solche Raster nicht anbietet.
     */
    public static boolean hasConflict(MacroMap map, int cellX, int cellY) {
        if (!isOccupied(map, cellX, cellY)) return false;
        MacroMapCell cell = map.getCell(cellX, cellY);

        for (int[] d : DIRECTIONS) {
            int x = cellX + d[0];
            int y = cellY + d[1];
            if (!isOccupied(map, x, y)) continue;
            Integer required = requiredOffset(cell.getHeightRaster(), map.getCell(x, y), d[0], d[1]);
            if (required == null || required != cell.getHeightRasterOffset()) return true;
        }
        return false;
    }

    /**
     * Schlägt die vier Ränder für ein neues Raster in dieser Zelle vor. Ränder belegter Nachbarn werden
     * übernommen, fehlende zufällig und sanft zwischen ihren Eckpunkten erzeugt. Eckpunkte diagonaler
     * Nachbarn zählen mit. Getrennte Bereiche werden so verschoben, dass sie zusammenpassen.
     *
     * @return {Nord, Ost, Süd, West}, jeweils in Richtung West nach Ost bzw. Nord nach Süd,
     *         oder null, wenn die Nachbarn sich widersprechen
     */
    public int[][] proposeEdges(Random random) {
        Integer[] fixedCorners = new Integer[4];
        Integer[] areaShifts = new Integer[areas.size()];

        for (int done = 0; done < areas.size(); done++) {
            int area = nextArea(areaShifts, fixedCorners);
            areaShifts[area] = shiftFor(area, fixedCorners);
            for (Neighbour neighbour : neighbours) {
                if (neighbour.area == area && !applyCorners(neighbour, areaShifts[area], fixedCorners)) {
                    return null;
                }
            }
        }

        // Freie Eckpunkte möglichst so wählen, dass Zellen zwei Felder weiter lösbar bleiben.
        // Liegen die Eckpunkte durch die Nachbarn fest, hilft Verwerfen nicht: Dann bleibt es beim Vorschlag.
        int[] corners = null;
        for (int attempt = 0; attempt < CORNER_PROPOSALS; attempt++) {
            int[] candidate = chooseCorners(fixedCorners, random);
            if (candidate == null) continue;
            corners = candidate;
            if (keepsCellsReachable(candidate, areaShifts)) break;
        }
        if (corners == null) return null;

        int[][] edges = {
            randomEdge(corners[CORNER_NW], corners[CORNER_NO], random),
            randomEdge(corners[CORNER_NO], corners[CORNER_SO], random),
            randomEdge(corners[CORNER_SW], corners[CORNER_SO], random),
            randomEdge(corners[CORNER_NW], corners[CORNER_SW], random)
        };

        for (Neighbour neighbour : neighbours) {
            if (neighbour.dx != 0 && neighbour.dy != 0) continue;
            int side = (neighbour.dy < 0) ? 0 : (neighbour.dx > 0) ? 1 : (neighbour.dy > 0) ? 2 : 3;
            edges[side] = absoluteFacingEdge(neighbour, areaShifts[neighbour.area]);
        }
        return edges;
    }

    /** @return die vier Eckpunkte oder null, wenn zwei benachbarte mehr als eine Kante auseinanderliegen */
    private static int[] chooseCorners(Integer[] fixedCorners, Random random) {
        Integer[] chosen = fixedCorners.clone();
        fillFreeCorners(chosen, random);
        int[] corners = new int[4];
        for (int corner = 0; corner < 4; corner++) {
            if (Math.abs(chosen[corner] - chosen[(corner + 1) % 4]) > EDGE_STEPS) return null;
            corners[corner] = chosen[corner];
        }
        return corners;
    }

    private boolean keepsCellsReachable(int[] targetCorners, Integer[] areaShifts) {
        if (!keepsReachableFrom(cellX, cellY, targetCorners, areaShifts)) return false;

        for (int area = 0; area < areas.size(); area++) {
            if (areaShifts[area] == null || areaShifts[area] == 0) continue;
            for (MacroMapCell cell : areas.get(area)) {
                int[] corners = absoluteCorners(cell.getHeightRaster(), cell.getHeightRasterOffset() + areaShifts[area]);
                if (!keepsReachableFrom(cell.getGridX(), cell.getGridY(), corners, areaShifts)) return false;
            }
        }
        return true;
    }

    private boolean keepsReachableFrom(int x, int y, int[] corners, Integer[] areaShifts) {
        for (int[] d : ORTHOGONAL_DIRECTIONS) {
            int otherX = x + 2 * d[0];
            int otherY = y + 2 * d[1];
            int betweenX = x + d[0];
            int betweenY = y + d[1];
            boolean betweenFree = !isOccupied(map, betweenX, betweenY) && !(betweenX == cellX && betweenY == cellY);
            if (!betweenFree || !isOccupied(map, otherX, otherY) || (otherX == cellX && otherY == cellY)) continue;

            int otherArea = areaOf[otherX][otherY];
            if (otherArea < 0) continue; // Ein nicht berührter Bereich lässt sich später noch verschieben.

            MacroMapCell other = map.getCell(otherX, otherY);
            int otherShift = (areaShifts[otherArea] != null) ? areaShifts[otherArea] : 0;
            int[] otherCorners = absoluteCorners(other.getHeightRaster(), other.getHeightRasterOffset() + otherShift);

            for (int side = -1; side <= 1; side += 2) {
                int ownCorner = cornerIndex((d[0] != 0) ? d[0] : side, (d[1] != 0) ? d[1] : side);
                int otherCorner = cornerIndex((d[0] != 0) ? -d[0] : side, (d[1] != 0) ? -d[1] : side);
                if (Math.abs(corners[ownCorner] - otherCorners[otherCorner]) > EDGE_STEPS) return false;
            }
        }
        return true;
    }

    private static int cornerIndex(int dx, int dy) {
        if (dy < 0) return (dx < 0) ? CORNER_NW : CORNER_NO;
        return (dx < 0) ? CORNER_SW : CORNER_SO;
    }

    private static int[] absoluteCorners(LTileRaster raster, int offset) {
        int[] north = raster.getNorthEdge();
        int[] south = raster.getSouthEdge();
        return new int[] {north[0] + offset, north[north.length - 1] + offset, south[south.length - 1] + offset, south[0] + offset};
    }

    /** Wählt einen noch nicht ausgerichteten Bereich, bevorzugt einen, der schon bekannte Eckpunkte berührt. */
    private int nextArea(Integer[] areaShifts, Integer[] corners) {
        int fallback = -1;
        for (Neighbour neighbour : neighbours) {
            if (areaShifts[neighbour.area] != null) continue;
            if (fallback < 0) fallback = neighbour.area;
            for (int corner : cornersOf(neighbour)) {
                if (corners[corner] != null) return neighbour.area;
            }
        }
        return fallback;
    }

    /** Ein Bereich ohne gemeinsamen Eckpunkt wird auf die Höhe eines bekannten Eckpunkts gelegt. */
    private int shiftFor(int area, Integer[] corners) {
        Integer known = null;
        for (Integer height : corners) {
            if (height != null) known = height;
        }
        if (known == null) return 0;

        Integer first = null;
        for (Neighbour neighbour : neighbours) {
            if (neighbour.area != area) continue;
            int[] touched = cornersOf(neighbour);
            for (int corner : touched) {
                if (corners[corner] != null) return corners[corner] - cornerHeight(neighbour, corner);
                if (first == null) first = cornerHeight(neighbour, corner);
            }
        }
        return known - first;
    }

    private boolean applyCorners(Neighbour neighbour, int shift, Integer[] corners) {
        for (int corner : cornersOf(neighbour)) {
            int height = cornerHeight(neighbour, corner) + shift;
            if (corners[corner] != null && corners[corner] != height) return false;
            corners[corner] = height;
        }
        return true;
    }

    /** @return die Eckpunkte der Zielzelle, die dieser Nachbar berührt */
    private static int[] cornersOf(Neighbour neighbour) {
        if (neighbour.dy < 0 && neighbour.dx == 0) return new int[] {CORNER_NW, CORNER_NO};
        if (neighbour.dx > 0 && neighbour.dy == 0) return new int[] {CORNER_NO, CORNER_SO};
        if (neighbour.dy > 0 && neighbour.dx == 0) return new int[] {CORNER_SW, CORNER_SO};
        if (neighbour.dx < 0 && neighbour.dy == 0) return new int[] {CORNER_NW, CORNER_SW};
        if (neighbour.dy < 0) return new int[] {(neighbour.dx < 0) ? CORNER_NW : CORNER_NO};
        return new int[] {(neighbour.dx < 0) ? CORNER_SW : CORNER_SO};
    }

    /** @return die Höhe, die dieser Nachbar (ohne Bereichsverschiebung) für den Eckpunkt der Zielzelle vorgibt */
    private static int cornerHeight(Neighbour neighbour, int corner) {
        LTileRaster raster = neighbour.cell.getHeightRaster();
        int offset = neighbour.cell.getHeightRasterOffset();
        if (neighbour.dx != 0 && neighbour.dy != 0) {
            return corner(raster, -neighbour.dx, -neighbour.dy) + offset;
        }
        int[] edge = edge(raster, -neighbour.dx, -neighbour.dy);
        boolean startOfEdge = corner == CORNER_NW || (corner == CORNER_NO && neighbour.dx > 0) || (corner == CORNER_SW && neighbour.dy > 0);
        return edge[startOfEdge ? 0 : edge.length - 1] + offset;
    }

    private static int[] absoluteFacingEdge(Neighbour neighbour, int shift) {
        int[] edge = edge(neighbour.cell.getHeightRaster(), -neighbour.dx, -neighbour.dy);
        for (int i = 0; i < edge.length; i++) {
            edge[i] += neighbour.cell.getHeightRasterOffset() + shift;
        }
        return edge;
    }

    private static void fillFreeCorners(Integer[] corners, Random random) {
        boolean anyKnown = false;
        for (Integer height : corners) {
            anyKnown |= height != null;
        }
        if (!anyKnown) corners[CORNER_NW] = 0;

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int corner = 0; corner < 4; corner++) {
                if (corners[corner] != null) continue;
                Integer next = corners[(corner + 1) % 4];
                Integer previous = corners[(corner + 3) % 4];
                Integer known = (next != null) ? next : previous;
                if (known != null) {
                    corners[corner] = known + random.nextInt(3) - 1;
                    changed = true;
                }
            }
        }
    }

    /** Erzeugt einen Rand aus Stufen von höchstens 1, der von Höhe from sicher zu Höhe to führt. Flache Stufen sind häufiger. */
    private static int[] randomEdge(int from, int to, Random random) {
        int[] edge = new int[EDGE_STEPS + 1];
        edge[0] = from;
        for (int i = 1; i <= EDGE_STEPS; i++) {
            int remaining = EDGE_STEPS - i;
            int step = FREE_EDGE_STEPS[random.nextInt(FREE_EDGE_STEPS.length)];
            if (Math.abs(to - (edge[i - 1] + step)) > remaining) {
                step = Integer.signum(to - edge[i - 1]);
            }
            edge[i] = edge[i - 1] + step;
        }
        return edge;
    }

    private static boolean isOccupied(MacroMap map, int x, int y) {
        return x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight() && map.getCell(x, y).hasHeightRaster();
    }

    /**
     * @return den Versatz, den das Raster gegenüber diesem Nachbarn braucht, oder null, wenn die Kante nicht passt
     */
    private static Integer requiredOffset(LTileRaster raster, MacroMapCell neighbour, int dx, int dy) {
        LTileRaster other = neighbour.getHeightRaster();
        int otherOffset = neighbour.getHeightRasterOffset();

        if (dx != 0 && dy != 0) {
            return corner(other, -dx, -dy) + otherOffset - corner(raster, dx, dy);
        }

        Integer shift = EdgeProfile.heightShift(edge(raster, dx, dy), edge(other, -dx, -dy));
        return (shift != null) ? shift + otherOffset : null;
    }

    private static int[] edge(LTileRaster raster, int dx, int dy) {
        if (dy < 0) return raster.getNorthEdge();
        if (dy > 0) return raster.getSouthEdge();
        return (dx < 0) ? raster.getWestEdge() : raster.getEastEdge();
    }

    private static int corner(LTileRaster raster, int dx, int dy) {
        int[] edge = (dy < 0) ? raster.getNorthEdge() : raster.getSouthEdge();
        return (dx < 0) ? edge[0] : edge[edge.length - 1];
    }
}
