package org.socialworld.tools.glblTerrainEditor;

import java.awt.*;

/**
 * Das aufgeteilte Grafik-Modul des Editors.
 * Nutzt GTERenderColorPalette für alle visuellen Farbberechnungen.
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

        for (int x = 0; x < macroMap.getWidth(); x++) {
            for (int y = 0; y < macroMap.getHeight(); y++) {
                MacroMapCell cell = macroMap.getCell(x, y);
                int startX = x * cellSize;
                int startY = y * cellSize;
                
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
                String terrain = (deltaCode == 0) ? cell.getMesoTerrain(mx, my) : GTERenderColorPalette.getTerrainNameFromCode(deltaCode);
                
                g2.setColor(GTERenderColorPalette.getTerrainColor(terrain));
                g2.fillRect(startX + px, startY + py, 1, 1);

                if (!hatVegetation && (!cell.getMesoBaum(mx, my).equals("KEIN_BAUM") || cell.hatStrauchMischung(mx, my))) {
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
        int mesoCellPixels = 9;

        for (int mx = 0; mx < 81; mx++) {
            for (int my = 0; my < 81; my++) {
                g2.setColor(GTERenderColorPalette.getTerrainColor(selectedCell.getMesoTerrain(mx, my)));
                g2.fillRect(mx * mesoCellPixels, my * mesoCellPixels, mesoCellPixels, mesoCellPixels);

                String baum = selectedCell.getMesoBaum(mx, my);
                
                if (!baum.equals("KEIN_BAUM")) {
                    g2.setColor(GTERenderColorPalette.getBaumColor(baum));
                    g2.fillRect(mx * mesoCellPixels + 2, my * mesoCellPixels + 2, mesoCellPixels - 4, mesoCellPixels - 4);
                } else if (selectedCell.hatStrauchMischung(mx, my)) {
                    g2.setColor(GTERenderColorPalette.getDominantShrubColor(selectedCell, mx, my));
                    g2.fillRect(mx * mesoCellPixels + 3, my * mesoCellPixels + 3, mesoCellPixels - 6, mesoCellPixels - 6);
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
                String terrain = (deltaCode == 0) ? selectedCell.getMesoTerrain(mx, my) : GTERenderColorPalette.getTerrainNameFromCode(deltaCode);
                
                g2.setColor(GTERenderColorPalette.getTerrainColor(terrain));
                g2.fillRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                g2.setColor(new Color(255, 255, 255, 25));
                g2.drawRect(lx * mikroCellPixels, ly * mikroCellPixels, mikroCellPixels, mikroCellPixels);

                String strauch = selectedCell.getMesoStrauchAusMischung(mx, my, lx, ly);
                if (!strauch.equals("KEIN_STRAUCH")) {
                    g2.setColor(GTERenderColorPalette.getStrauchColor(strauch));
                    if (strauch.equals("FARNE")) {
                        g2.fillRect(lx * mikroCellPixels + 25, ly * mikroCellPixels + 12, 30, 56);
                        g2.fillRect(lx * mikroCellPixels + 12, ly * mikroCellPixels + 25, 56, 30);
                    } else if (strauch.equals("ZIERSTRAUCH")) {
                        g2.fillOval(lx * mikroCellPixels + 15, ly * mikroCellPixels + 15, 52, 52);
                    } else if (strauch.equals("BEERENSTRAUCH")) {
                        g2.fillOval(lx * mikroCellPixels + 15, ly * mikroCellPixels + 35, 35, 35);
                        g2.fillOval(lx * mikroCellPixels + 35, ly * mikroCellPixels + 20, 30, 30);
                    }
                }
            }
        }

        String baum = selectedCell.getMesoBaum(mx, my);
        if (!baum.equals("KEIN_BAUM")) {
            Color baumFarbe = GTERenderColorPalette.getBaumColor(baum);
            g2.setColor(new Color(baumFarbe.getRed(), baumFarbe.getGreen(), baumFarbe.getBlue(), 75));
            g2.setStroke(new BasicStroke(2.0f)); 
            for (int i = 0; i < totalSizePixels * 2; i += 12) {
                g2.drawLine(i, 0, 0, i);
            }
            g2.setStroke(new BasicStroke(1.0f)); 
        }
    }
}
