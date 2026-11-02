package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import org.socialworld.attributes.GroundMaterial;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GTECanvasInteractionListener extends MouseAdapter {
    private final GlobalTerrainEditorCanvas canvas;
    private final GlobalTerrainEditor editor;

    public GTECanvasInteractionListener(GlobalTerrainEditorCanvas canvas, GlobalTerrainEditor editor) {
        this.canvas = canvas;
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Solange die Höhenansicht offen ist, wird nur geschaut: kein Zoom und kein Bearbeiten.
        if (isHeightViewOpen()) {
            int[] cell = macroCellAt(e.getX(), e.getY());
            if (cell != null && SwingUtilities.isLeftMouseButton(e)) {
                canvas.getHeightView().showCellNow(cell[0], cell[1]);
            }
            return;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            handleZoomOut();
            return;
        }
        if (canvas.getCurrentMode().equals("ZOOM")) {
            handleZoomIn(e.getX(), e.getY());
        } else if (canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER)) {
            handleHeightRasterClick(e.getX(), e.getY());
        } else if (canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_GENERATE_RASTER)) {
            handleGenerateRasterClick(e.getX(), e.getY());
        } else {
            handleMousePaint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isHeightViewOpen()
                && !canvas.getCurrentMode().equals("ZOOM")
                && !canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER)
                && !canvas.getCurrentMode().equals(GlobalTerrainEditorCanvas.MODE_GENERATE_RASTER)
                && SwingUtilities.isLeftMouseButton(e)) {
            handleMousePaint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!isHeightViewOpen()) return;

        int[] cell = macroCellAt(e.getX(), e.getY());
        if (cell != null) {
            canvas.getHeightView().requestCell(cell[0], cell[1]);
        } else {
            canvas.getHeightView().cancelRequest();
        }
    }

    private boolean isHeightViewOpen() {
        return canvas.getHeightView() != null && canvas.getHeightView().isVisible();
    }

    /**
     * @return die Koordinaten {x, y} der Makro-Zelle an dieser Mausposition oder null, wenn dort keine Zelle liegt
     */
    private int[] macroCellAt(int mouseX, int mouseY) {
        if (canvas.getCurrentZoom() != GlobalTerrainEditorCanvas.ZoomLevel.MACRO) return null;

        MacroMap macroMap = canvas.getMacroMap();
        int cellSize = canvas.getCellSizeInPixels();
        int cellX = GTEQuadrant.toGlobal(mouseX / cellSize, canvas.getCurrentMacroOffsetX());
        int cellY = GTEQuadrant.toGlobal(mouseY / cellSize, canvas.getCurrentMacroOffsetY());
        boolean onMap = cellX >= 0 && cellX < macroMap.getWidth() && cellY >= 0 && cellY < macroMap.getHeight();
        return onMap ? new int[] {cellX, cellY} : null;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (canvas.getHeightView() != null) canvas.getHeightView().cancelRequest();
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

        new GTEHeightRasterDialog(editor, macroMap, cellX, cellY, canvas.getRasterLibrary()).open();
        canvas.repaint();
        if (canvas.getHeightView() != null) canvas.getHeightView().refresh();
    }

    /**
     * Erzeugt für die angeklickte Makro-Zelle ein Raster, das zu ihren belegten Nachbarn passt.
     * Ein vorhandenes Raster der Zelle wird dabei ersetzt.
     */
    private void handleGenerateRasterClick(int mouseX, int mouseY) {
        int[] cell = macroCellAt(mouseX, mouseY);
        if (cell == null) return;

        new GTEHeightRasterGenerationDialog(editor, canvas.getMacroMap(), cell[0], cell[1]).generate();
        canvas.repaint();
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
