package org.socialworld.tools.glblTerrainEditor;



/**
 * Repräsentiert eine 729m x 729m Kachel auf der Editor-Ebene.
 * Verwaltet intern das fraktale 81x81 Meso-Grid für Terrain, Bäume und Sträucher.
 */
public class MacroMapCell {
    private final int gridX;
    private final int gridY;
    private double referenceElevation; // Makro-Höhe in Metern
    private String coverType;           // Globaler Gelände-Typ (z.B. "GRAS")
    
    // Die mathematische Auflösung für die Meso-Stufe (9 x 9 x 9 Prinzip = 729)
    public static final int MESO_GRID_SIZE = 81; 
    
    // Die hochauflösenden Layer für das detaillierte Zeichnen im Zoom
    private final String[][] mesoTerrain;
    private final String[][] mesoBaum;    // Neuer Layer für Baumarten
    private final String[][] mesoStrauch; // Neuer Layer für Straucharten

    public MacroMapCell(int gridX, int gridY, double initialElevation, String coverType) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.referenceElevation = initialElevation;
        this.coverType = coverType;
        
        this.mesoTerrain = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mesoBaum = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mesoStrauch = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        
        // Initialisierung aller Gitter-Ebenen
        for (int x = 0; x < MESO_GRID_SIZE; x++) {
            for (int y = 0; y < MESO_GRID_SIZE; y++) {
                mesoTerrain[x][y] = coverType; // Erbt standardmäßig das globale Makro-Terrain
                mesoBaum[x][y] = "KEIN_BAUM";   // Standardmäßig unbewachsen
                mesoStrauch[x][y] = "KEIN_STRAUCH";
            }
        }
    }

    // --- GETTER & SETTER FÜR DIE DETAIL-LAYER (WICHTIG FÜR CANVAS) ---

    public String getMesoTerrain(int mx, int my) {
        return mesoTerrain[mx][my];
    }

    public void setMesoTerrain(int mx, int my, String type) {
        this.mesoTerrain[mx][my] = type;
    }

    public String getMesoBaum(int mx, int my) {
        return mesoBaum[mx][my];
    }

    public void setMesoBaum(int mx, int my, String type) {
        this.mesoBaum[mx][my] = type;
    }

    public String getMesoStrauch(int mx, int my) {
        return mesoStrauch[mx][my];
    }

    public void setMesoStrauch(int mx, int my, String type) {
        this.mesoStrauch[mx][my] = type;
    }

    // --- GLOBALE ATTRIBUTE ---

    public double getReferenceElevation() { 
        return referenceElevation; 
    }
    
    public void setReferenceElevation(double elevation) { 
        this.referenceElevation = elevation; 
    }
    
    public String getCoverType() { 
        return coverType; 
    }
    
    public void setCoverType(String coverType) { 
        this.coverType = coverType; 
    }
    
    public int getGridX() { 
        return gridX; 
    }
    
    public int getGridY() { 
        return gridY; 
    }
}
