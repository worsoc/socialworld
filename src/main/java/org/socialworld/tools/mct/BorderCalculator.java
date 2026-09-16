package org.socialworld.tools.mct;


import java.util.List;

public class BorderCalculator {

    // Hilfsmethode: Holt Kachel aus dem 9x9 Grid (0-indexed)
    private static VisualTile getTile(List<VisualTile> grid, int row, int col) {
        return grid.get(row * 9 + col);
    }

    public static int[] calculateNorth(List<VisualTile> grid) {
        int[] border = new int[10];
        for (int c = 0; c < 9; c++) {
            VisualTile t = getTile(grid, 0, c);
            border[c] = t.baseHeight + TileInfo.getOffset(t.type, "nw");
        }
        VisualTile last = getTile(grid, 0, 8);
        border[9] = last.baseHeight + TileInfo.getOffset(last.type, "no");
        return border;
    }

    public static int[] calculateEast(List<VisualTile> grid) {
        int[] border = new int[10];
        for (int r = 0; r < 9; r++) {
            VisualTile t = getTile(grid, r, 8);
            border[r] = t.baseHeight + TileInfo.getOffset(t.type, "no");
        }
        VisualTile last = getTile(grid, 8, 8);
        border[9] = last.baseHeight + TileInfo.getOffset(last.type, "so");
        return border;
    }

    public static int[] calculateSouth(List<VisualTile> grid) {
        int[] border = new int[10];
        for (int c = 0; c < 9; c++) {
            VisualTile t = getTile(grid, 8, c);
            border[c] = t.baseHeight + TileInfo.getOffset(t.type, "sw");
        }
        VisualTile last = getTile(grid, 8, 8);
        border[9] = last.baseHeight + TileInfo.getOffset(last.type, "so");
        return border;
    }

    public static int[] calculateWest(List<VisualTile> grid) {
        int[] border = new int[10];
        for (int r = 0; r < 9; r++) {
            VisualTile t = getTile(grid, r, 0);
            border[r] = t.baseHeight + TileInfo.getOffset(t.type, "nw");
        }
        VisualTile last = getTile(grid, 8, 0);
        border[9] = last.baseHeight + TileInfo.getOffset(last.type, "sw");
        return border;
    }
}
