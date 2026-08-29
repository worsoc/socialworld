package org.socialworld.tools.glblTerrainEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Das zeichenfähige Canvas des Editors. Unterstützt ein fraktales 3-Stufen-Zoom-System,
 * eine flexible Pinselgröße und ein zweischichtiges Vegetations-System.
 * Ein pixelgenaues Downsampling auf der Weltkarte spiegelt die exakte Detail-Form wider.
 */
public class GlobalTerrainEditorCanvas extends JPanel {
    private final MacroMap macroMap;
    private final int cellSizeInPixels = 45; // Jede 729m Kachel ist 45x45 Pixel groß

    private enum ZoomLevel { MACRO, MESO, MIKRO }
    private ZoomLevel currentZoom = ZoomLevel.MACRO;
    
    private MacroMapCell selectedMacroCell = null;
    private int selectedMesoChunkX = 0;
    private int selectedMesoChunkY = 0;

    // Aktuelle Pinsel-Einstellungen aus der UI
    private String currentMode = "ZOOM"; 
    private double currentBrushElevation = 100.0;
    private String currentBrushTerrain = "GRAS";
    
    // Getrennte Pinsel-Variablen für das zweischichtige Vegetationssystem
    private String currentBrushBaum = "KEIN_BAUM";
    private String currentBrushStrauch = "KEIN_STRAUCH";
    
    private int currentBrushRadius = 1; 

    public GlobalTerrainEditorCanvas(MacroMap macroMap) {
        this.macroMap = macroMap;
        setBackground(Color.DARK_GRAY);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    handleZoomOut();
                    return;
                }

                if (currentMode.equals("ZOOM")) {
                    handleZoomIn(e.getX(), e.getY());
                } else {
                    handleMousePaint(e.getX(), e.getY());
                }
            }

            @Override 
            public void mouseDragged(MouseEvent e) { 
                if (!currentMode.equals("ZOOM") && SwingUtilities.isLeftMouseButton(e)) {
                    handleMousePaint(e.getX(), e.getY());
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void handleZoomIn(int mouseX, int mouseY) {
        if (currentZoom == ZoomLevel.MACRO) {
            int cellX = mouseX / cellSizeInPixels;
            int cellY = mouseY / cellSizeInPixels;
            if (cellX >= 0 && cellX < macroMap.getWidth() && cellY >= 0 && cellY < macroMap.getHeight()) {
                selectedMacroCell = macroMap.getCell(cellX, cellY);
                currentZoom = ZoomLevel.MESO;
                repaint();
            }
        } else if (currentZoom == ZoomLevel.MESO) {
            int mesoCellPixels = 9; 
            int mx = mouseX / mesoCellPixels;
            int my = mouseY / mesoCellPixels;
            
            if (mx >= 0 && mx < 81 && my >= 0 && my < 81) {
                selectedMesoChunkX = (mx / 9) * 9;
                selectedMesoChunkY = (my / 9) * 9;
                currentZoom = ZoomLevel.MIKRO;
                repaint();
            }
        }
    }

    private void handleZoomOut() {
        if (currentZoom == ZoomLevel.MIKRO) currentZoom = ZoomLevel.MESO;
        else if (currentZoom == ZoomLevel.MESO) {
            currentZoom = ZoomLevel.MACRO;
            selectedMacroCell = null;
        }
        repaint();
    }

    private void handleMousePaint(int mouseX, int mouseY) {
        int radiusOffset = currentBrushRadius - 1;

        switch (currentZoom) {
            case MACRO -> {
                int cellX = mouseX / cellSizeInPixels;
                int cellY = mouseY / cellSizeInPixels;
                
                for (int dx = -radiusOffset; dx <= radiusOffset; dx++) {
                    for (int dy = -radiusOffset; dy <= radiusOffset; dy++) {
                        if (dx * dx + dy * dy <= radiusOffset * radiusOffset) {
                            int targetX = cellX + dx;
                            int targetY = cellY + dy;
                            
                            if (targetX >= 0 && targetX < macroMap.getWidth() && targetY >= 0 && targetY < macroMap.getHeight()) {
                                if (currentMode.equals("HÖHE")) {
                                    macroMap.updateCellElevation(targetX, targetY, currentBrushElevation);
                                } else if (currentMode.equals("TERRAIN")) {
                                    MacroMapCell cell = macroMap.getCell(targetX, targetY);
                                    cell.setCoverType(currentBrushTerrain);
                                    for (int mx = 0; mx < 81; mx++) {
                                        for (int my = 0; my < 81; my++) {
                                            cell.setMesoTerrain(mx, my, currentBrushTerrain);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                repaint();
            }
            case MESO -> {
                int mesoCellPixels = 9;
                int centerMx = mouseX / mesoCellPixels;
                int centerMy = mouseY / mesoCellPixels;

                for (int dx = -radiusOffset; dx <= radiusOffset; dx++) {
                    for (int dy = -radiusOffset; dy <= radiusOffset; dy++) {
                        if (dx * dx + dy * dy <= radiusOffset * radiusOffset) {
                            int mx = centerMx + dx;
                            int my = centerMy + dy;

                            if (mx >= 0 && mx < 81 && my >= 0 && my < 81) {
                                if (currentMode.equals("TERRAIN")) selectedMacroCell.setMesoTerrain(mx, my, currentBrushTerrain);
                                else if (currentMode.equals("BAUM")) selectedMacroCell.setMesoBaum(mx, my, currentBrushBaum);
                                else if (currentMode.equals("STRAUCH")) selectedMacroCell.setMesoStrauch(mx, my, currentBrushStrauch);
                            }
                        }
                    }
                }
                repaint();
            }
            case MIKRO -> {
                int mikroCellPixels = 81;
                int localX = mouseX / mikroCellPixels;
                int localY = mouseY / mikroCellPixels;

                if (localX >= 0 && localX < 9 && localY >= 0 && localY < 9) {
                    int globalMesoX = selectedMesoChunkX + localX;
                    int globalMesoY = selectedMesoChunkY + localY;
                    
                    if (currentMode.equals("TERRAIN")) selectedMacroCell.setMesoTerrain(globalMesoX, globalMesoY, currentBrushTerrain);
                    else if (currentMode.equals("BAUM")) selectedMacroCell.setMesoBaum(globalMesoX, globalMesoY, currentBrushBaum);
                    else if (currentMode.equals("STRAUCH")) selectedMacroCell.setMesoStrauch(globalMesoX, globalMesoY, currentBrushStrauch);
                    repaint();
                }
            }
        }
    }

    /**
     * Rendert eine 729m Makrokachel pixelgenau, indem die 81x81 Detailmatrix 
     * mathematisch präzise auf die 45x45 Pixel des Bildschirms heruntergerechnet wird.
     * Integriert zudem eine diagonale Schraffur bei vorhandener Vegetation.
     */
    private void renderPixelPerfectMacroCell(Graphics2D g2, MacroMapCell cell, int startX, int startY) {
        boolean hatVegetation = false;

        for (int px = 0; px < cellSizeInPixels; px++) {
            int mx = (px * 81) / cellSizeInPixels;

            for (int py = 0; py < cellSizeInPixels; py++) {
                int my = (py * 81) / cellSizeInPixels;

                String detailTerrain = cell.getMesoTerrain(mx, my);
                g2.setColor(getTerrainColor(detailTerrain));
                g2.fillRect(startX + px, startY + py, 1, 1);

                if (!hatVegetation && (!cell.getMesoBaum(mx, my).equals("KEIN_BAUM") || !cell.getMesoStrauch(mx, my).equals("KEIN_STRAUCH"))) {
                    hatVegetation = true;
                }
            }
        }

        // SCHRAFFUR bei vorhandener Vegetation (transparentes Weiß)
        if (hatVegetation) {
            g2.setColor(new Color(255, 255, 255, 60)); 
            g2.setStroke(new BasicStroke(1.0f));
            for (int i = 0; i < cellSizeInPixels; i += 6) {
                g2.drawLine(startX + i, startY, startX, startY + i);
                g2.drawLine(startX + cellSizeInPixels, startY + i, startX + i, startY + cellSizeInPixels);
            }
            g2.setStroke(new BasicStroke(1.0f)); 
        }
        
        g2.setColor(new Color(0, 0, 0, 20));
        g2.drawRect(startX, startY, cellSizeInPixels, cellSizeInPixels);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (currentZoom == ZoomLevel.MACRO) {
            // Weltkarte mit pixelgenauem Downsampling
            for (int x = 0; x < macroMap.getWidth(); x++) {
                for (int y = 0; y < macroMap.getHeight(); y++) {
                    MacroMapCell cell = macroMap.getCell(x, y);
                    int startX = x * cellSizeInPixels;
                    int startY = y * cellSizeInPixels;
                    
                    renderPixelPerfectMacroCell(g2, cell, startX, startY);
                    
                    g2.setColor(new Color(255, 255, 255, 180));
                    g2.setFont(new Font("Arial", Font.PLAIN, 9));
                    g2.drawString((int)cell.getReferenceElevation() + "m", startX + 4, startY + 24);
                }
            }
        } else if (currentZoom == ZoomLevel.MESO) {
            // Meso-Ebene (81x81 Grid)
            int mesoCellPixels = 9;
            for (int mx = 0; mx < 81; mx++) {
                for (int my = 0; my < 81; my++) {
                    g2.setColor(getTerrainColor(selectedMacroCell.getMesoTerrain(mx, my)));
                    g2.fillRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels, mesoCellPixels);

                    if (!selectedMacroCell.getMesoBaum(mx, my).equals("KEIN_BAUM") || !selectedMacroCell.getMesoStrauch(mx, my).equals("KEIN_STRAUCH")) {
                        g2.setColor(new Color(20, 80, 30));
                        g2.fillRect(mx * mesoCellPixels + 2, my * mesoCellPixels + 2, mesoCellPixels - 4, mesoCellPixels - 4);
                    }
                }
            }
            // Gitterlinien zeichnen
            for (int mx = 0; mx < 81; mx++) {
                for (int my = 0; my < 81; my++) {
                    g2.setColor(new Color(0, 0, 0, 35)); 
                    g2.drawRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels, mesoCellPixels);
                    if (mx % 9 == 0 && my % 9 == 0) {
                        g2.setColor(new Color(255, 255, 255, 90));
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.drawRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels * 9, mesoCellPixels * 9);
                        g2.setStroke(new BasicStroke(1.0f)); 
                    }
                }
            }
        } else if (currentZoom == ZoomLevel.MIKRO) {
            // Mikro-Ebene (9x9 Grid) mit getrennten Layern
            renderMikroGrid(g2);
        }
    }

    /**
     * Ausgelagerte Render-Logik für das 9x9 Mikro-Grid, um das Zeichnen
     * der geschichteten Vegetation (Baum über Strauch) sauber darzustellen.
     */
    private void renderMikroGrid(Graphics2D g2) {
        int mikroCellPixels = 81;
        for (int lx = 0; lx < 9; lx++) {
            for (int ly = 0; ly < 9; ly++) {
                int gmx = selectedMesoChunkX + lx;
                int gmy = selectedMesoChunkY + ly;

                g2.setColor(getTerrainColor(selectedMacroCell.getMesoTerrain(gmx, gmy)));
                g2.fillRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                g2.setColor(new Color(255, 255, 255, 25));
                g2.drawRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                // Layer 1: Strauch (unten)
                String strauch = selectedMacroCell.getMesoStrauch(gmx, gmy);
                if (!strauch.equals("KEIN_STRAUCH")) {
                    g2.setColor(strauch.equals("BEERENSTRAUCH") ? new Color(130, 40, 70) : new Color(60, 130, 50));
                    g2.fillOval(lx * mikroCellPixels + 12, ly * mikroCellPixels + 40, 30, 30); 
                    g2.fillOval(lx * mikroCellPixels + 40, ly * mikroCellPixels + 35, 25, 25);
                }

                // Layer 2: Baum (darüber)
                String baum = selectedMacroCell.getMesoBaum(gmx, gmy);
                if (!baum.equals("KEIN_BAUM")) {
                    Color baumFarbe = switch (baum) {
                        case "EICHE" -> new Color(20, 80, 25);
                        case "KIEFER" -> new Color(15, 60, 35);
                        case "BIRKE" -> new Color(80, 140, 70);
                        default -> new Color(40, 110, 40);
                    };
                    g2.setColor(new Color(90, 50, 20));
                    g2.fillRect(lx * mikroCellPixels + 37, ly * mikroCellPixels + 37, 8, 8);
                    g2.setColor(new Color(baumFarbe.getRed(), baumFarbe.getGreen(), baumFarbe.getBlue(), 210));
                    g2.fillOval(lx * mikroCellPixels + 15, ly * mikroCellPixels + 15, 52, 52);
                }
            }
        }
    }

    public String getStatusText() {
        return switch (currentZoom) {
            case MACRO -> " MODE: WELTKARTE (729m) | Downsampling & Schraffur aktiv | Linksklick zum Zoomen.";
            case MESO -> " MODE: MESO-ANSICHT (81x81) | Pinsel-Radius: " + currentBrushRadius + " | Linksklick für 1m Modus | Rechtsklick zurück.";
            case MIKRO -> " MODE: MIKRO-DETAILMODUS (1m Schärfe) | Baum und Strauch überlagerbar! | Rechtsklick zurück.";
        };
    }

    private Color getTerrainColor(String type) {
        return switch (type) {
            case "WASSER" -> new Color(30, 100, 200);
            case "SAND" -> new Color(220, 200, 130);
            case "STEIN" -> new Color(120, 120, 120);
            case "SCHNEE" -> new Color(240, 240, 240);
            default -> new Color(50, 150, 70);
        };
    }

    // UI-Schnittstellen (Setter)
    public void setEditorMode(String mode) { this.currentMode = mode; }
    public void setBrushRadius(int radius) { this.currentBrushRadius = radius; }
    public void setBrushElevation(double elevation) { this.currentBrushElevation = elevation; }
    public void setBrushTerrain(String terrain) { this.currentBrushTerrain = terrain; }
    public void setBrushBaum(String baum) { this.currentBrushBaum = baum; }
    public void setBrushStrauch(String strauch) { this.currentBrushStrauch = strauch; }
    @Override public Dimension getPreferredSize() { return new Dimension(729, 729); }
}
