package org.socialworld.tools.glblTerrainEditor;

import java.awt.*;

import org.socialworld.attributes.VegetationBush;
import org.socialworld.attributes.VegetationTree;

/**
 * Zeichen-Manager für den Editor.
 * Rendert alle Straucharten formgetreu auf dem Monitor.
 * JETZT MIT 16x16 MAKRO-QUADRANTEN-UNTERSTÜTZUNG.
 */
public class GTECanvasRenderer {
    private final GlobalTerrainEditorCanvas canvas;

    public GTECanvasRenderer(GlobalTerrainEditorCanvas canvas) {
        this.canvas = canvas;
    }

    public void render(Graphics2D g2) {
        switch (canvas.getCurrentZoom()) {
            case MACRO -> renderMacroLevel(g2);
            case MESO -> renderMesoLevel(g2);
            case MIKRO -> renderMikroLevel(g2);
        }
    }

    private void renderMacroLevel(Graphics2D g2) {
        MacroMap macroMap = canvas.getMacroMap();
        int cellSize = canvas.getCellSizeInPixels();

        // Aktuelle Quadranten-Offsets aus dem Canvas holen
        int offsetX = canvas.getCurrentMacroOffsetX();
        int offsetY = canvas.getCurrentMacroOffsetY();

        // ANPASSUNG: Schleifen laufen nur noch über die 16 Zellen des aktiven Quadranten
        for (int x = offsetX; x < offsetX + GTEQuadrant.QUADRANT_SIZE; x++) {
            for (int y = offsetY; y < offsetY + GTEQuadrant.QUADRANT_SIZE; y++) {
                
                // Sicherheitsprüfung gegen Map-Grenzen
                if (x >= macroMap.getWidth() || y >= macroMap.getHeight()) {
                    continue;
                }

                MacroMapCell cell = macroMap.getCell(x, y);
                
                // ANPASSUNG: Auf dem Bildschirm fangen wir relativ bei (0..15) an zu zeichnen
                int localX = GTEQuadrant.toLocal(x, offsetX);
                int localY = GTEQuadrant.toLocal(y, offsetY);
                
                int startX = localX * cellSize;
                int startY = localY * cellSize;
                
                renderPixelPerfectMacroCell(g2, cell, startX, startY, cellSize);
                
                g2.setColor(new Color(255, 255, 255, 180));
                g2.setFont(new Font("Arial", Font.PLAIN, 9));
                g2.drawString((int)cell.getReferenceElevation() + "m", startX + 4, startY + 24);
            }
        }
    }

    private void renderPixelPerfectMacroCell(Graphics2D g2, MacroMapCell cell, int startX, int startY, int cellSize) {
        boolean hatVegetation = false;

        for (int px = 0; px < cellSize; px++) {
            int mx = (px * 81) / cellSize;
            for (int py = 0; py < cellSize; py++) {
                int my = (py * 81) / cellSize;

                byte deltaCode = cell.getMikroTerrainDelta(mx * 9, my * 9);
                int terrainId = (deltaCode == 99) ? cell.getMesoTerrainId(mx, my) : (int) deltaCode;

                g2.setColor(GTERenderColorPalette.getTerrainColor(terrainId));
                g2.fillRect(startX + px, startY + py, 1, 1);

                if (!hatVegetation && (cell.getMesoBaum(mx, my) != VegetationTree.nothing.getGteId() || cell.hatStrauchMischung(mx, my))) {
                    hatVegetation = true;
                }
            }
        }

        if (hatVegetation) {
            g2.setColor(new Color(255, 255, 255, 60)); 
            g2.setStroke(new BasicStroke(1.0f));
            for (int i = 0; i < cellSize; i += 6) {
                g2.drawLine(startX + i, startY, startX, startY + i);
                g2.drawLine(startX + cellSize, startY + i, startX + i, startY + cellSize);
            }
        }
        g2.setColor(new Color(0, 0, 0, 20));
        g2.drawRect(startX, startY, cellSize, cellSize);
    }

    private void renderMesoLevel(Graphics2D g2) {
        MacroMapCell selectedCell = canvas.getSelectedMacroCell();
        if (selectedCell == null) return;
        int mesoCellPixels = 9; // Jedes Meso-Feld ist 9x9 Pixel groß

        // --- SCHRITT 1: PIXELGENAUES TERRAIN (INKLUSIVE MIKRO-DELTA) ZEICHNEN ---
        for (int mx = 0; mx < 81; mx++) {
            for (int my = 0; my < 81; my++) {
                
                // Wir berechnen den Startpunkt dieses 9x9-Feldes auf dem Bildschirm
                int fieldStartX = mx * mesoCellPixels;
                int fieldStartY = my * mesoCellPixels;

                // Da 1 Bildschirm-Pixel = 1 Mikro-Zelle, tasten wir das innere 9x9-Gitter ab
                for (int lx = 0; lx < 9; lx++) {
                    int globalMikroX = mx * 9 + lx; // Globale Mikro-X-Koordinate
                    
                    for (int ly = 0; ly < 9; ly++) {
                        int globalMikroY = my * 9 + ly; // Globale Mikro-Y-Koordinate

                        // Delta abfragen und den korrekten Terrain-Namen ermitteln
                        byte deltaCode = selectedCell.getMikroTerrainDelta(globalMikroX, globalMikroY);
                        int terrainId = (deltaCode == 99) ? selectedCell.getMesoTerrainId(mx, my) : (int) deltaCode;

                        // Farbe setzen und exakt DIESEN einen Bildschirm-Pixel einfärben
                        g2.setColor(GTERenderColorPalette.getTerrainColor(terrainId));
                        g2.fillRect(fieldStartX + lx, fieldStartY + ly, 1, 1);
                    }
                }
            }
        }

        // --- SCHRITT 2: VEGETATION (BÄUME & STRÄUCHER) DARÜBER ZEICHNEN ---
        for (int mx = 0; mx < 81; mx++) {
            for (int my = 0; my < 81; my++) {
                int fieldStartX = mx * mesoCellPixels;
                int fieldStartY = my * mesoCellPixels;

                // 2a. Bäume prüfen und zeichnen
                int baumId = selectedCell.getMesoBaum(mx, my);
                if (baumId != VegetationTree.nothing.getGteId()) {
                    g2.setColor(GTERenderColorPalette.getBaumColor(baumId));
                    // Zentriertes Quadrat für den Baum
                    g2.fillRect(fieldStartX + 2, fieldStartY + 2, mesoCellPixels - 4, mesoCellPixels - 4);
                
                // 2b. Sträucher prüfen und zeichnen (nur wenn kein Baum im Weg steht)
                } else if (selectedCell.hatStrauchMischung(mx, my)) {
                    g2.setColor(GTERenderColorPalette.getDominantShrubColor(selectedCell, mx, my));
                    // Etwas kleineres Quadrat für den Busch
                    g2.fillRect(fieldStartX + 3, fieldStartY + 3, mesoCellPixels - 6, mesoCellPixels - 6);
                }
            }
        }

        // --- SCHRITT 3: RASTER UND BLOCK-GRENZEN ZEICHNEN ---
        // Feines 1x1-Meso-Raster (schwach sichtbar)
        g2.setColor(new Color(0, 0, 0, 35));
        for (int mx = 0; mx < 81; mx++) {
            for (int my = 0; my < 81; my++) {
                g2.drawRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels, mesoCellPixels);
            }
        }
        
        // Dickeres 9x9-Block-Raster (stärker sichtbar)
        g2.setColor(new Color(255, 255, 255, 110));
        g2.setStroke(new BasicStroke(1.5f));       
        for (int bx = 0; bx < 81; bx += 9) {
            for (int by = 0; by < 81; by += 9) {
                g2.drawRect(bx * mesoCellPixels, by * mesoCellPixels, mesoCellPixels * 9, mesoCellPixels * 9);
            }
        }
        g2.setStroke(new BasicStroke(1.0f)); 
    }

    private void renderMikroLevel(Graphics2D g2) {
        MacroMapCell selectedCell = canvas.getSelectedMacroCell();
        if (selectedCell == null) return;
        int mikroCellPixels = 81;
        int totalSizePixels = 729;

        int mx = canvas.getSelectedMesoChunkX();
        int my = canvas.getSelectedMesoChunkY();

        for (int lx = 0; lx < 9; lx++) {
            int globalMikroX = mx * 9 + lx;
            for (int ly = 0; ly < 9; ly++) {
                int globalMikroY = my * 9 + ly;

                byte deltaCode = selectedCell.getMikroTerrainDelta(globalMikroX, globalMikroY);
                int terrainId = (deltaCode == 99) ? selectedCell.getMesoTerrainId(mx, my) : (int) deltaCode;

                g2.setColor(GTERenderColorPalette.getTerrainColor(terrainId));
                g2.fillRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                g2.setColor(new Color(255, 255, 255, 25));
                g2.drawRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                int strauchId = selectedCell.getMesoStrauchAusMischung(mx, my, lx, ly);
                if (strauchId != VegetationBush.nothing.getGteId()) {
                    // Hole die passende Enum-Konstante anhand der ID
                    VegetationBush bushType = VegetationBush.fromGteId(strauchId);

                    // 1. FARBE SETZEN (Übergabe der ID an die Farbpalette)
                    g2.setColor(GTERenderColorPalette.getStrauchColor(strauchId));
                    
                    // 2. FORM FÜLLEN (Füll-Logik via Switch)
                    switch (bushType) {
                        case farne -> {
                            g2.fillRect(lx * mikroCellPixels + 32, ly * mikroCellPixels + 12, 16, 56);
                            g2.fillRect(lx * mikroCellPixels + 12, ly * mikroCellPixels + 32, 56, 16);
                        }
                        case ginster, heidekraut -> {
                            int cx = lx * mikroCellPixels + 40;
                            int cy = ly * mikroCellPixels + 40;
                            g2.fillOval(cx - 20, cy - 20, 40, 40);
                            g2.fillOval(cx - 30, cy - 5, 60, 10);
                            g2.fillOval(cx - 5, cy - 30, 10, 60);
                        }
                        default -> {
                            // zierstrauch, beerenstrauch, brombeere
                            g2.fillOval(lx * mikroCellPixels + 15, ly * mikroCellPixels + 15, 52, 52);
                        }
                    }

                    // 3. KONTUREN ZEICHNEN (Outline-Logik via Switch)
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.setStroke(new BasicStroke(1.5f));
                    
                    switch (bushType) {
                        case farne -> {
                            g2.drawRect(lx * mikroCellPixels + 32, ly * mikroCellPixels + 12, 16, 56);
                            g2.drawRect(lx * mikroCellPixels + 12, ly * mikroCellPixels + 32, 56, 16);
                        }
                        case ginster, heidekraut -> {
                            g2.drawOval(lx * mikroCellPixels + 20, ly * mikroCellPixels + 20, 40, 40);
                        }
                        default -> {
                            // zierstrauch, beerenstrauch, brombeere
                            g2.drawOval(lx * mikroCellPixels + 15, ly * mikroCellPixels + 15, 52, 52);
                        }
                    }
                    g2.setStroke(new BasicStroke(1.0f));
                }
           }
        }

        int baumId = selectedCell.getMesoBaum(mx, my);
        if (baumId != VegetationTree.nothing.getGteId()) {
            Color baumFarbe = GTERenderColorPalette.getBaumColor(baumId);
            g2.setColor(new Color(baumFarbe.getRed(), baumFarbe.getGreen(), baumFarbe.getBlue(), 75));
            g2.setStroke(new BasicStroke(2.0f)); 
            for (int i = 0; i < totalSizePixels * 2; i += 12) {
                g2.drawLine(i, 0, 0, i);
            }
            g2.setStroke(new BasicStroke(1.0f)); 
        }
    }
}
