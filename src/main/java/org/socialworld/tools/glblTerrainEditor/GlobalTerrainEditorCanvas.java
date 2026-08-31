package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Der zentrale Koordinator der Editor-Ansicht.
 * Hält den Zustand und delegiert Interaktion und Rendering an die GTE-Klassen.
 */
public class GlobalTerrainEditorCanvas extends JPanel {
    private  MacroMap macroMap;
    private final int cellSizeInPixels = 45; 

    // Zoom-Zustand und Fokus-Punkte
    public enum ZoomLevel { MACRO, MESO, MIKRO }
    private ZoomLevel currentZoom = ZoomLevel.MACRO;
    private MacroMapCell selectedMacroCell = null;
    private int selectedMesoChunkX = 0;
    private int selectedMesoChunkY = 0;

    // Aktuelle Pinsel- und Werkzeug-Einstellungen
    private String currentMode = "ZOOM"; 
    private double currentBrushElevation = 100.0;
    private String currentBrushTerrain = "GRAS";
    private String currentBrushBaum = "KEIN_BAUM";
    private String currentBrushStrauch = "KEIN_STRAUCH";
    private int currentBrushRadius = 1; 

    // Die neuen, spezialisierten GTE-Module
    private final GTECanvasRenderer renderer;

    public GlobalTerrainEditorCanvas(MacroMap macroMap) {
        this.macroMap = macroMap;
        this.renderer = new GTECanvasRenderer(this);
        setBackground(Color.DARK_GRAY);

        // Instanziiere und registriere den neuen Eingabe-Controller
        GTECanvasInteractionListener listener = new GTECanvasInteractionListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Delegiere das komplette Zeichnen an das Grafik-Modul
        renderer.render((Graphics2D) g);
    }

    /**
     * Tauscht die aktuelle Karte im laufenden Betrieb aus (z.B. nach dem Laden).
     * Verhindert das Erzeugen von Mehrfachinstanzen im RAM.
     */
    public void setMacroMap(MacroMap newMap) {
        // Falls du eine interne Variable 'this.macroMap' als final deklariert hast,
        // entferne das 'final' bei der Variablendeklaration oben in der Klasse!
        this.macroMap = newMap; 
        
        // Setze den Zoom sicherheitshalber auf die Weltkarte zurück
        this.currentZoom = ZoomLevel.MACRO;
        this.selectedMacroCell = null;
        
        // Erzwinge ein sofortiges Neuzeichnen der neuen Karte
        this.repaint();
    }

    // --- GETTER & SETTER FÜR DIE KOMMUNIKATION DER GTE-MODULE ---
    public MacroMap getMacroMap() { return macroMap; }
    public int getCellSizeInPixels() { return cellSizeInPixels; }
    public ZoomLevel getCurrentZoom() { return currentZoom; }
    public void setCurrentZoom(ZoomLevel zoom) { this.currentZoom = zoom; }
    public MacroMapCell getSelectedMacroCell() { return selectedMacroCell; }
    public void setSelectedMacroCell(MacroMapCell cell) { this.selectedMacroCell = cell; }
    public int getSelectedMesoChunkX() { return selectedMesoChunkX; }
    public void setSelectedMesoChunkX(int x) { this.selectedMesoChunkX = x; }
    public int getSelectedMesoChunkY() { return selectedMesoChunkY; }
    public void setSelectedMesoChunkY(int y) { this.selectedMesoChunkY = y; }
    public String getCurrentMode() { return currentMode; }
    public void setEditorMode(String mode) { this.currentMode = mode; }
    public double getCurrentBrushElevation() { return currentBrushElevation; }
    public void setBrushElevation(double elevation) { this.currentBrushElevation = elevation; }
    public String getCurrentBrushTerrain() { return currentBrushTerrain; }
    public void setBrushTerrain(String terrain) { this.currentBrushTerrain = terrain; }
    public String getCurrentBrushBaum() { return currentBrushBaum; }
    public void setBrushBaum(String baum) { this.currentBrushBaum = baum; }
    public String getCurrentBrushStrauch() { return currentBrushStrauch; }
    public void setBrushStrauch(String strauch) { this.currentBrushStrauch = strauch; }
    public int getCurrentBrushRadius() { return currentBrushRadius; }
    public void setBrushRadius(int radius) { this.currentBrushRadius = radius; }

    /**
     * Erzeugt den Text für die Statusbar des Hauptfensters.
     */
    public String getStatusText() {
        return switch (currentZoom) {
            case MACRO -> " MODE: WELTKARTE (729m) | Downsampling & Schraffur aktiv | Linksklick zum Zoomen.";
            case MESO -> " MODE: MESO-ANSICHT (81x81) | Pinsel-Radius: " + currentBrushRadius + " | Klicke für 1m Modus | Rechtsklick zurück.";
            case MIKRO -> " MODE: MIKRO-DETAILMODUS (1m Schärfe) | Baum und Strauch überlagerbar! | Rechtsklick zurück.";
        };
    }

    @Override 
    public Dimension getPreferredSize() { return new Dimension(729, 729); }
}
