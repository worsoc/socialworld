package org.socialworld.tools.glblTerrainEditor;


public class MacroMap {
    private final MacroMapCell[][] matrix;
    private final int width;
    private final int height;
    
 
    public MacroMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new MacroMapCell[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = new MacroMapCell(x, y, GTERenderColorPalette.getTerrainNameFromCode((byte)0 /*saltwater*/));
            }
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

