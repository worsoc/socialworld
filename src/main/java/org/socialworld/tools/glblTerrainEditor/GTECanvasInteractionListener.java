package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import org.socialworld.attributes.GroundMaterial;
import org.socialworld.tools.mct.LTileRaster;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GTECanvasInteractionListener extends MouseAdapter {
    private final GlobalTerrainEditorCanvas canvas;
    private final GlobalTerrainEditor editor;
    private File lastRasterDirectory;

    public GTECanvasInteractionListener(GlobalTerrainEditorCanvas canvas, GlobalTerrainEditor editor) {
        this.canvas = canvas;
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            handleZoomOut();
            return;
        }
        if (canvas.getCurrentMode().equals("ZOOM")) {
            handleZoomIn(e.getX(), e.getY());
        } else if (canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER)) {
            handleHeightRasterClick(e.getX(), e.getY());
        } else {
            handleMousePaint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!canvas.getCurrentMode().equals("ZOOM")
                && !canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER)
                && SwingUtilities.isLeftMouseButton(e)) {
            handleMousePaint(e.getX(), e.getY());
        }
    }

    private void handleZoomIn(int mouseX, int mouseY) {
        MacroMap macroMap = canvas.getMacroMap();
        int cellSize = canvas.getCellSizeInPixels();

        if (canvas.getCurrentZoom() == GlobalTerrainEditorCanvas.ZoomLevel.MACRO) {
            int localCellX = mouseX / cellSize;
            int localCellY = mouseY / cellSize;
            
            int cellX = GTEQuadrant.toGlobal(localCellX, canvas.getCurrentMacroOffsetX());
            int cellY = GTEQuadrant.toGlobal(localCellY, canvas.getCurrentMacroOffsetY());

            if (cellX >= 0 && cellX < macroMap.getWidth() && cellY >= 0 && cellY < macroMap.getHeight()) {
                canvas.setSelectedMacroCell(macroMap.getCell(cellX, cellY));
                canvas.setCurrentZoom(GlobalTerrainEditorCanvas.ZoomLevel.MESO);
                canvas.repaint();
            }
        } else if (canvas.getCurrentZoom() == GlobalTerrainEditorCanvas.ZoomLevel.MESO) {
            int mesoCellPixels = 9; 
            int mx = mouseX / mesoCellPixels;
            int my = mouseY / mesoCellPixels;
            if (mx >= 0 && mx < 81 && my >= 0 && my < 81) {
                canvas.setSelectedMesoChunkX(mx);
                canvas.setSelectedMesoChunkY(my);
                canvas.setCurrentZoom(GlobalTerrainEditorCanvas.ZoomLevel.MIKRO);
                canvas.repaint();
            }
        }
    }

    /**
     * Ordnet der angeklickten Makro-Zelle ein Raster zu oder ersetzt bzw. entfernt ein vorhandenes.
     * Die Zuordnung gilt nur auf der Makro-Ebene.
     */
    private void handleHeightRasterClick(int mouseX, int mouseY) {
        if (canvas.getCurrentZoom() != GlobalTerrainEditorCanvas.ZoomLevel.MACRO) return;

        MacroMap macroMap = canvas.getMacroMap();
        int cellSize = canvas.getCellSizeInPixels();
        int cellX = GTEQuadrant.toGlobal(mouseX / cellSize, canvas.getCurrentMacroOffsetX());
        int cellY = GTEQuadrant.toGlobal(mouseY / cellSize, canvas.getCurrentMacroOffsetY());
        if (cellX < 0 || cellX >= macroMap.getWidth() || cellY < 0 || cellY >= macroMap.getHeight()) return;

        MacroMapCell cell = macroMap.getCell(cellX, cellY);

        if (cell.hasHeightRaster()) {
            String[] options = { "Ersetzen", "Entfernen", "Abbrechen" };
            int choice = JOptionPane.showOptionDialog(canvas,
                    "Zelle [" + cellX + "," + cellY + "] hat bereits ein Raster:\n" + cell.getHeightRasterSource(),
                    "Höhenraster", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
            if (choice == 1) {
                cell.clearHeightRaster();
                canvas.repaint();
                return;
            }
            if (choice != 0) return;
        }

        JFileChooser chooser = (lastRasterDirectory != null) ? new JFileChooser(lastRasterDirectory) : new JFileChooser();
        chooser.setDialogTitle("onlyLs-Datei für Zelle [" + cellX + "," + cellY + "] auswählen");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        if (chooser.showOpenDialog(canvas) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        lastRasterDirectory = file.getParentFile();
        try {
            cell.setHeightRaster(LTileRaster.fromFile(file), file.getName(), 0);
            canvas.repaint();
        } catch (IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(canvas, file.getName() + " wurde nicht zugeordnet:\n" + ex.getMessage(),
                    "Ungültiges Raster", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleZoomOut() {
        if (canvas.getCurrentZoom() == GlobalTerrainEditorCanvas.ZoomLevel.MIKRO) {
            canvas.setCurrentZoom(GlobalTerrainEditorCanvas.ZoomLevel.MESO);
        } else if (canvas.getCurrentZoom() == GlobalTerrainEditorCanvas.ZoomLevel.MESO) {
            canvas.setCurrentZoom(GlobalTerrainEditorCanvas.ZoomLevel.MACRO);
            canvas.setSelectedMacroCell(null);
        }
        editor.rbZoom.setSelected(true);
        canvas.setEditorMode("ZOOM");
        canvas.repaint();
    }

    private void handleMousePaint(int mouseX, int mouseY) {
        MacroMap macroMap = canvas.getMacroMap();
        int cellSize = canvas.getCellSizeInPixels();
        int radiusOffset = canvas.getCurrentBrushRadius() - 1;

        switch (canvas.getCurrentZoom()) {
            case MACRO -> {
                int localCellX = mouseX / cellSize;
                int localCellY = mouseY / cellSize;
                
                int cellX = GTEQuadrant.toGlobal(localCellX, canvas.getCurrentMacroOffsetX());
                int cellY = GTEQuadrant.toGlobal(localCellY, canvas.getCurrentMacroOffsetY());

                for (int dx = -radiusOffset; dx <= radiusOffset; dx++) {
                    for (int dy = -radiusOffset; dy <= radiusOffset; dy++) {
                        if (dx * dx + dy * dy <= radiusOffset * radiusOffset) {
                            int targetX = cellX + dx;
                            int targetY = cellY + dy;
                            if (targetX >= 0 && targetX < macroMap.getWidth() && targetY >= 0 && targetY < macroMap.getHeight()) {
                                applyMacroPaint(targetX, targetY);
                            }
                        }
                    }
                }
                canvas.repaint();
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
                                applyMesoPaint(mx, my);
                            }
                        }
                    }
                }
                canvas.repaint();
            }
            case MIKRO -> {
                int mikroCellPixels = 81;
                int localX = mouseX / mikroCellPixels;
                int localY = mouseY / mikroCellPixels;
                if (localX >= 0 && localX < 9 && localY >= 0 && localY < 9) {
                    applyMikroPaint(localX, localY);
                    canvas.repaint();
                }
            }
        }
    }

    private void applyMacroPaint(int tx, int ty) {
        MacroMap macroMap = canvas.getMacroMap();
        MacroMapCell cell = macroMap.getCell(tx, ty);
        if (canvas.getCurrentMode().equals("TERRAIN")) {
            cell.setCoverType(canvas.getCurrentBrushTerrain());
            for (int mx = 0; mx < 81; mx++) {
                for (int my = 0; my < 81; my++) {
                    cell.setMesoTerrain(mx, my, canvas.getCurrentBrushTerrain());
                }
            }
        }
    }

    private void applyMesoPaint(int mx, int my) {
        MacroMapCell selectedCell = canvas.getSelectedMacroCell();
        if (selectedCell == null) return;

        if (canvas.getCurrentMode().equals("TERRAIN")) {
            selectedCell.setMesoTerrain(mx, my, canvas.getCurrentBrushTerrain());
        } else if (canvas.getCurrentMode().equals("BAUM")) {
            // NEU: Reicht direkt die int-ID aus dem Canvas an die Cell weiter
            selectedCell.setMesoBaum(mx, my, canvas.getCurrentBrushBaum());
        } else if (canvas.getCurrentMode().equals("STRAUCH")) {
            // NEU: Holt die int-ID und befüllt die 9x9 Schablone speicheroptimiert
            int gewaehlterStrauchId = canvas.getCurrentBrushStrauch();
            for (int lx = 0; lx < 9; lx++) {
                for (int ly = 0; ly < 9; ly++) {
                    selectedCell.setMesoStrauchInMischung(mx, my, lx, ly, gewaehlterStrauchId);
                }
            }
        }
    }

    private void applyMikroPaint(int centerLx, int centerLy) {
        MacroMapCell selectedCell = canvas.getSelectedMacroCell();
        if (selectedCell == null) return;

        int mx = canvas.getSelectedMesoChunkX();
        int my = canvas.getSelectedMesoChunkY();
        int radiusOffset = canvas.getCurrentBrushRadius() - 1;

        for (int dx = -radiusOffset; dx <= radiusOffset; dx++) {
            for (int dy = -radiusOffset; dy <= radiusOffset; dy++) {
                if (dx * dx + dy * dy <= radiusOffset * radiusOffset) {
                    int lx = centerLx + dx;
                    int ly = centerLy + dy;

                    if (lx >= 0 && lx < 9 && ly >= 0 && ly < 9) {
                        int globalMikroX = mx * 9 + lx;
                        int globalMikroY = my * 9 + ly;

                        if (canvas.getCurrentMode().equals("TERRAIN")) {
                            byte code = (byte)GroundMaterial.fromName(canvas.getCurrentBrushTerrain()).getGteId();
                            selectedCell.setMikroTerrainDelta(globalMikroX, globalMikroY, code);
                        } else if (canvas.getCurrentMode().equals("STRAUCH")) {
                            // NEU: Übergibt die int-ID im feinen 1m Gitter
                            selectedCell.setMesoStrauchInMischung(mx, my, lx, ly, canvas.getCurrentBrushStrauch());
                        }
                    }
                }
            }
        }
    }
}
