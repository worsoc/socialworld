package org.socialworld.tools.glblTerrainEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Das zeichenfähige Canvas des Editors. Unterstützt ein fraktales 3-Stufen-Zoom-System
 * über das dezidierte ZOOM-Werkzeug (Linksklick hinein / Rechtsklick heraus) und 
 * berechnet Maus-Ereignisse präzise in die jeweiligen Rasterkoordinaten um.
 */
public class GlobalTerrainEditorCanvas extends JPanel {
    private final MacroMap macroMap;
    private final int cellSizeInPixels = 45; 

    private enum ZoomLevel { MACRO, MESO, MIKRO }
    private ZoomLevel currentZoom = ZoomLevel.MACRO;
    
    private MacroMapCell selectedMacroCell = null;
    private int selectedMesoChunkX = 0;
    private int selectedMesoChunkY = 0;

    // Aktueller Modus (Standardmäßig auf ZOOM gesetzt, passend zur Sidebar)
    private String currentMode = "ZOOM"; 
    private double currentBrushElevation = 100.0;
    private String currentBrushTerrain = "GRAS";
    private String currentBrushFauna = "LEER";

    public GlobalTerrainEditorCanvas(MacroMap macroMap) {
        this.macroMap = macroMap;
        setBackground(Color.DARK_GRAY);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // RECHTSKLICK: Zoomt immer heraus, egal welches Werkzeug aktiv ist
                if (SwingUtilities.isRightMouseButton(e)) {
                    handleZoomOut();
                    return;
                }

                // LINKSSKLICK: Prüfen, ob wir navigieren oder zeichnen wollen
                if (currentMode.equals("ZOOM")) {
                    handleZoomIn(e.getX(), e.getY());
                } else {
                    handleMousePaint(e.getX(), e.getY());
                }
            }

            @Override 
            public void mouseDragged(MouseEvent e) { 
                // Beim Ziehen der Maus wird nur gezeichnet, niemals gezoomt
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
        switch (currentZoom) {
            case MACRO -> {
                int cellX = mouseX / cellSizeInPixels;
                int cellY = mouseY / cellSizeInPixels;
                if (cellX >= 0 && cellX < macroMap.getWidth() && cellY >= 0 && cellY < macroMap.getHeight()) {
                    if (currentMode.equals("HÖHE")) {
                        macroMap.updateCellElevation(cellX, cellY, currentBrushElevation);
                    } else if (currentMode.equals("TERRAIN")) {
                        MacroMapCell cell = macroMap.getCell(cellX, cellY);
                        cell.setCoverType(currentBrushTerrain);
                        for (int mx = 0; mx < 81; mx++) {
                            for (int my = 0; my < 81; my++) {
                                cell.setMesoTerrain(mx, my, currentBrushTerrain);
                            }
                        }
                    }
                    repaint();
                }
            }
            case MESO -> {
                int mesoCellPixels = 9;
                int mx = mouseX / mesoCellPixels;
                int my = mouseY / mesoCellPixels;

                if (mx >= 0 && mx < 81 && my >= 0 && my < 81) {
                    if (currentMode.equals("TERRAIN")) selectedMacroCell.setMesoTerrain(mx, my, currentBrushTerrain);
                    else if (currentMode.equals("FAUNA")) selectedMacroCell.setMesoFauna(mx, my, currentBrushFauna);
                    repaint();
                }
            }
            case MIKRO -> {
                int mikroCellPixels = 81;
                int localX = mouseX / mikroCellPixels;
                int localY = mouseY / mikroCellPixels;

                if (localX >= 0 && localX < 9 && localY >= 0 && localY < 9) {
                    int globalMesoX = selectedMesoChunkX + localX;
                    int globalMesoY = selectedMesoChunkY + localY;
                    
                    if (currentMode.equals("TERRAIN")) selectedMacroCell.setMesoTerrain(globalMesoX, globalMesoY, currentBrushTerrain);
                    else if (currentMode.equals("FAUNA")) selectedMacroCell.setMesoFauna(globalMesoX, globalMesoY, currentBrushFauna);
                    repaint();
                }
            }
        }
    }

    private Color getDominantMacroColor(MacroMapCell cell) {
        Map<String, Integer> counts = new HashMap<>();
        String dominantTerrain = cell.getCoverType();
        int maxCount = 0;

        for (int mx = 0; mx < 81; mx++) {
            for (int my = 0; my < 81; my++) {
                String type = cell.getMesoTerrain(mx, my);
                int count = counts.getOrDefault(type, 0) + 1;
                counts.put(type, count);
                if (count > maxCount) { maxCount = count; dominantTerrain = type; }
            }
        }
        return getTerrainColor(dominantTerrain);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (currentZoom == ZoomLevel.MACRO) {
            for (int x = 0; x < macroMap.getWidth(); x++) {
                for (int y = 0; y < macroMap.getHeight(); y++) {
                    MacroMapCell cell = macroMap.getCell(x, y);
                    g2.setColor(getDominantMacroColor(cell));
                    g2.fillRect(x * cellSizeInPixels, y * cellSizeInPixels, cellSizeInPixels - 1, cellSizeInPixels - 1);
                    
                    g2.setColor(cell.getCoverType().equals("SCHNEE") ? Color.DARK_GRAY : Color.WHITE);
                    g2.setFont(new Font("Arial", Font.PLAIN, 9));
                    g2.drawString((int)cell.getReferenceElevation() + "m", x * cellSizeInPixels + 4, y * cellSizeInPixels + 24);
                }
            }
        } else if (currentZoom == ZoomLevel.MESO) {
            int mesoCellPixels = 9;
            for (int mx = 0; mx < 81; mx++) {
                for (int my = 0; my < 81; my++) {
                    g2.setColor(getTerrainColor(selectedMacroCell.getMesoTerrain(mx, my)));
                    g2.fillRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels, mesoCellPixels);

                    if (!selectedMacroCell.getMesoFauna(mx, my).equals("LEER")) {
                        g2.setColor(new Color(20, 80, 30));
                        g2.fillRect(mx * mesoCellPixels + 2, my * mesoCellPixels + 2, mesoCellPixels - 4, mesoCellPixels - 4);
                    }
                }
            }
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
            int mikroCellPixels = 81;
            for (int lx = 0; lx < 9; lx++) {
                for (int ly = 0; ly < 9; ly++) {
                    int gmx = selectedMesoChunkX + lx;
                    int gmy = selectedMesoChunkY + ly;

                    g2.setColor(getTerrainColor(selectedMacroCell.getMesoTerrain(gmx, gmy)));
                    g2.fillRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                    g2.setColor(new Color(255, 255, 255, 25));
                    g2.drawRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                    String fauna = selectedMacroCell.getMesoFauna(gmx, gmy);
                    if (fauna.equals("BAUM")) {
                        g2.setColor(new Color(15, 70, 25));
                        g2.fillOval(lx * mikroCellPixels + 20, ly * mikroCellPixels + 20, mikroCellPixels - 40, mikroCellPixels - 40);
                    }
                }
            }
        }
        drawHUD(g2);
    }

    private void drawHUD(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(10, 10, 520, 25);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 11));
        String status = switch (currentZoom) {
            case MACRO -> "WELTKARTE (729m) - Werkzeug 'Zoom' aktiv: Einfacher Klick zum Hineinzoomen!";
            case MESO -> "MESO-ANSICHT (81x81) - Werkzeug 'Zoom' aktiv: Klick für 1m Mikro-Modus | Rechtsklick zurück";
            case MIKRO -> "MIKRO-DETAILMODUS (9x9) [Rechtsklick zurück]";
        };
        g2.drawString(status, 15, 27);
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

    public void setEditorMode(String mode) { this.currentMode = mode; }
    public void setBrushElevation(double elevation) { this.currentBrushElevation = elevation; }
    public void setBrushTerrain(String terrain) { this.currentBrushTerrain = terrain; }
    public void setBrushFauna(String fauna) { this.currentBrushFauna = fauna; }
    @Override public Dimension getPreferredSize() { return new Dimension(729, 729); }
}
    