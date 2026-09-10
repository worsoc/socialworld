package org.socialworld.tools.mct;


import java.util.List;
import org.socialworld.tools.mct.TileGeometry.NeighbourAnalyzerTile;

public class BorderCalculator {

    // Hilfsmethode: Holt Kachel aus dem 9x9 Grid (0-indexed)
    private static NeighbourAnalyzerTile getTile(List<NeighbourAnalyzerTile> grid, int row, int col) {
        return grid.get(row * 9 + col);
    }

    public static int[] calculateNorth(List<NeighbourAnalyzerTile> grid) {
        int[] border = new int[10];
        for (int c = 0; c < 9; c++) {
            NeighbourAnalyzerTile t = getTile(grid, 0, c);
            border[c] = t.baseHeight + TileGeometry.getOffset(t.type, "nw");
        }
        NeighbourAnalyzerTile last = getTile(grid, 0, 8);
        border[9] = last.baseHeight + TileGeometry.getOffset(last.type, "no");
        return border;
    }

    public static int[] calculateEast(List<NeighbourAnalyzerTile> grid) {
        int[] border = new int[10];
        for (int r = 0; r < 9; r++) {
            NeighbourAnalyzerTile t = getTile(grid, r, 8);
            border[r] = t.baseHeight + TileGeometry.getOffset(t.type, "no");
        }
        NeighbourAnalyzerTile last = getTile(grid, 8, 8);
        border[9] = last.baseHeight + TileGeometry.getOffset(last.type, "so");
        return border;
    }

    public static int[] calculateSouth(List<NeighbourAnalyzerTile> grid) {
        int[] border = new int[10];
        for (int c = 0; c < 9; c++) {
            NeighbourAnalyzerTile t = getTile(grid, 8, c);
            border[c] = t.baseHeight + TileGeometry.getOffset(t.type, "sw");
        }
        NeighbourAnalyzerTile last = getTile(grid, 8, 8);
        border[9] = last.baseHeight + TileGeometry.getOffset(last.type, "so");
        return border;
    }

    public static int[] calculateWest(List<NeighbourAnalyzerTile> grid) {
        int[] border = new int[10];
        for (int r = 0; r < 9; r++) {
            NeighbourAnalyzerTile t = getTile(grid, r, 0);
            border[r] = t.baseHeight + TileGeometry.getOffset(t.type, "nw");
        }
        NeighbourAnalyzerTile last = getTile(grid, 8, 0);
        border[9] = last.baseHeight + TileGeometry.getOffset(last.type, "sw");
        return border;
    }
}
