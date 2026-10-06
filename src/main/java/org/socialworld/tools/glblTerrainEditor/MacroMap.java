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
                matrix[x][y] = new MacroMapCell(x, y, 0.0, GTERenderColorPalette.getTerrainNameFromCode((byte)0 /*saltwater*/));
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
    
    /**
     * Ermittelt die zuständige MacroMapCell anhand von globalen Meso-Koordinaten.
     * 
     * @param mx Globale X-Koordinate auf Meso-Ebene (0 bis totalMesoW - 1)
     * @param my Globale Y-Koordinate auf Meso-Ebene (0 bis totalMesoH - 1)
     * @return Die MacroMapCell, in der diese Meso-Kachel liegt
     */
    public MacroMapCell getCellByMesoCoord(int mx, int my) {
        // Nutzt die MESO_GRID_SIZE Konstante (81) für die Umrechnung auf die Makro-Zelle
        int cx = mx / MacroMapCell.MESO_GRID_SIZE;
        int cy = my / MacroMapCell.MESO_GRID_SIZE;
        
        // Nutzt deine bestehende Methode getCell(x, y) der MacroMap
        return this.getCell(cx, cy);
    }

    /**
     * Holt das dominante Terrain-String-Label einer Meso-Kachel über globale Koordinaten.
     * 
     * @param mx Globale X-Koordinate auf Meso-Ebene
     * @param my Globale Y-Koordinate auf Meso-Ebene
     * @return Der Terrain-String (z.B. "WOODLAND", "PLAINS")
     */
    public String getMesoTerrainAtGlobal(int mx, int my) {
        MacroMapCell cell = getCellByMesoCoord(mx, my);
        
        // Berechnet die relativen Koordinaten (0 bis 80) innerhalb dieser einen Zelle
        int localX = mx % MacroMapCell.MESO_GRID_SIZE;
        int localY = my % MacroMapCell.MESO_GRID_SIZE;
        
        // Nutzt deine bestehende Methode getMesoTerrain(lx, ly) der MacroMapCell
        return cell.getMesoTerrain(localX, localY);
    }

}

