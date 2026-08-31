package org.socialworld.tools.glblTerrainEditor;


public class MacroMap {
    private final MacroMapCell[][] matrix;
    private final int width;
    private final int height;
    
    // Maximale Steigungsgrenze angepasst auf die neue Kachelgröße von 729m
    private static final double MAX_ALLOWED_DELTA = 729.0; 

    public MacroMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new MacroMapCell[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = new MacroMapCell(x, y, 0.0, GTERenderColorPalette.getTerrainNameFromCode((byte)1));
            }
        }
    }

    /**
     * Garantiert, dass der Höhenunterschied zu den Nachbarkacheln niemals 729m überschreitet.
     */
    public boolean validateElevationConstraint(int targetX, int targetY, double proposedElevation) {
        int[][] neighbors = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : neighbors) {
            int nx = targetX + dir[0];
            int ny = targetY + dir[1];

            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                MacroMapCell neighbor = matrix[nx][ny];
                double delta = Math.abs(proposedElevation - neighbor.getReferenceElevation());
                if (delta > MAX_ALLOWED_DELTA) {
                    return false; // Verhindert Löcher in deinem topologischen Detail-System
                }
            }
        }
        return true;
    }

    public void updateCellElevation(int x, int y, double newElevation) {
        if (validateElevationConstraint(x, y, newElevation)) {
            matrix[x][y].setReferenceElevation(newElevation);
        }
    }

    public MacroMapCell getCell(int x, int y) { return matrix[x][y]; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

