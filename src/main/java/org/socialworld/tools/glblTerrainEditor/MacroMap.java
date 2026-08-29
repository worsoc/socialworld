package org.socialworld.tools.glblTerrainEditor;


public class MacroMap {
    private final MacroMapCell[][] matrix;
    private final int width;
    private final int height;
    
    // Die harte physikalische Grenze deines feinen Topologie-Systems
    private static final double MAX_ALLOWED_DELTA = 720.0; 

    public MacroMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new MacroMapCell[width][height];
        
        // Initialisiere die Welt flach auf Meereshöhe
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = new MacroMapCell(x, y, 0.0, "GRAS");
            }
        }
    }

    /**
     * Prüft vor der Änderung, ob das Höhen-Delta zu allen 4 Nachbarn <= 720m bleibt.
     */
    public boolean validateElevationConstraint(int targetX, int targetY, double proposedElevation) {
        int[][] neighbors = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // Oben, Unten, Links, Rechts

        for (int[] dir : neighbors) {
            int nx = targetX + dir[0];
            int ny = targetY + dir[1];

            // Wenn Nachbar innerhalb der Kartengrenzen existiert
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                MacroMapCell neighbor = matrix[nx][ny];
                double delta = Math.abs(proposedElevation - neighbor.getReferenceElevation());
                if (delta > MAX_ALLOWED_DELTA) {
                    return false; // Stopp! Steigung wäre zu steil für dein Detail-System
                }
            }
        }
        return true; // Änderung ist sicher
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

