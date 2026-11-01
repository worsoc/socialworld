package org.socialworld.tools.glblTerrainEditor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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

    private final MacroMapCell target;
    private final List<List<MacroMapCell>> areas = new ArrayList<>();
    private final List<Neighbour> neighbours = new ArrayList<>();

    /**
     * Bereitet die Prüfung für eine Zelle vor. Ein dort schon vorhandenes Raster zählt nicht mit,
     * sodass dieselbe Prüfung auch für das Ersetzen gilt.
     */
    public HeightRasterPlacement(MacroMap map, int cellX, int cellY) {
        this.target = map.getCell(cellX, cellY);

        int[][] areaOf = new int[map.getWidth()][map.getHeight()];
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
