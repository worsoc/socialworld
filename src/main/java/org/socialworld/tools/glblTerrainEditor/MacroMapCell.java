package org.socialworld.tools.glblTerrainEditor;



/**
 * Repräsentiert eine 729m x 729m Zelle auf der Editor-Ebene.
 * 1 "Pixel" im Mikro-Grid exakt 1m x 1m.
 */
public class MacroMapCell {
    private final int gridX;
    private final int gridY;
    private double referenceElevation; // Makro-Höhe in Metern
    private String coverType;           // "GRAS", "SAND", "WASSER", etc.
    
    // Die mathematische Auflösung für die Meso-Stufe (9 x 9 x 9 Prinzip = 729)
    public static final int MESO_GRID_SIZE = 81; 
    
    // Das hochauflösende Detail-Raster für Terrain und Fauna
    private final String[][] mesoTerrain;
    private final String[][] mesoFauna;

    public MacroMapCell(int gridX, int gridY, double initialElevation, String coverType) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.referenceElevation = initialElevation;
        this.coverType = coverType;
        
        this.mesoTerrain = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mesoFauna = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        
        // Initialisierung des hochauflösenden Gitters
        for (int x = 0; x < MESO_GRID_SIZE; x++) {
            for (int y = 0; y < MESO_GRID_SIZE; y++) {
                mesoTerrain[x][y] = coverType; // Erbt das globale Makro-Terrain
                mesoFauna[x][y] = "LEER";
            }
        }
    }

    // Getter und Setter
    public String getMesoTerrain(int mx, int my) { return mesoTerrain[mx][my]; }
    public void setMesoTerrain(int mx, int my, String type) { this.mesoTerrain[mx][my] = type; }
    
    public String getMesoFauna(int mx, int my) { return mesoFauna[mx][my]; }
    public void setMesoFauna(int mx, int my, String type) { this.mesoFauna[mx][my] = type; }

    public double getReferenceElevation() { return referenceElevation; }
    public void setReferenceElevation(double elevation) { this.referenceElevation = elevation; }
    public String getCoverType() { return coverType; }
    public void setCoverType(String coverType) { this.coverType = coverType; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
}
