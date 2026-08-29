package org.socialworld.tools.glblTerrainEditor;


/**
 * Repräsentiert eine 720m x 720m Zelle auf der Editor-Ebene.
 * Verhindert Namenskonflikte mit deinem feinen Tile-System.
 */
public class MacroMapCell {
    private final int gridX;
    private final int gridY;
    private double referenceElevation; // Makro-Höhe in Metern
    private String coverType;           // z.B. "GRAS", "STEIN"
    
    // Schnittstelle zu deinem feinen System: Hier wird dein "sub[10000](...)" Datenstrom verankert
    private String detailTileStream; 

    public MacroMapCell(int gridX, int gridY, double initialElevation, String coverType) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.referenceElevation = initialElevation;
        this.coverType = coverType;
        this.detailTileStream = ""; // Wird mit deinem feinen System gefüllt
    }

    // Getter und Setter
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    public double getReferenceElevation() { return referenceElevation; }
    public void setReferenceElevation(double elevation) { this.referenceElevation = elevation; }
    public String getCoverType() { return coverType; }
    public void setCoverType(String coverType) { this.coverType = coverType; }
    public String getDetailTileStream() { return detailTileStream; }
    public void setDetailTileStream(String stream) { this.detailTileStream = stream; }
}

