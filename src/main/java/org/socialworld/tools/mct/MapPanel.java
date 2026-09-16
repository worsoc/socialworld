package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private VisualTile[][] mapGrid;
    private int rotationState = 0;
    private final int tilePixels = 40;

    public MapPanel(VisualTile[][] mapGrid) {
        this.mapGrid = mapGrid;
    }

    public void setRotation(int rotationState) {
        this.rotationState = rotationState;
        repaint();
    }

    public void updateMapData(VisualTile[][] newGrid) {
        this.mapGrid = newGrid; 
        repaint();              
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapGrid == null || mapGrid.length == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int currentSize = mapGrid.length;

        // 1. KACHELGRÖSSE: Bei Mega-Grid auf 20 Pixel halbieren
        int currentTilePixels = (currentSize > 9) ? 20 : 40;

        int offsetX = getWidth() / 2;
        
        // 2. Y-OFFSET: Bei großen Karten weiter nach oben schieben (Viertel statt Drittel)
        // Dadurch rutscht das gesamte Panorama im Fenster nach oben und wird perfekt zentriert.
         int offsetY = (currentSize > 9) ? getHeight() / 5 : getHeight() / 3;

        // Tiefensortierung (Original-Logik)
        int startR = (rotationState == 2 || rotationState == 3) ? currentSize - 1 : 0;
        int endR = (rotationState == 2 || rotationState == 3) ? -1 : currentSize;
        int stepR = (rotationState == 2 || rotationState == 3) ? -1 : 1;

        int startC = (rotationState == 1 || rotationState == 2) ? currentSize - 1 : 0;
        int endC = (rotationState == 1 || rotationState == 2) ? -1 : currentSize;
        int stepC = (rotationState == 1 || rotationState == 2) ? -1 : 1;

        // Kacheln rendern
        for (int r = startR; r != endR; r += stepR) {
            for (int c = startC; c != endC; c += stepC) {
                VisualTile tile = mapGrid[r][c];
                if (tile == null) continue;

                int rotR = r, rotC = c;
                if (rotationState == 1) { rotR = c; rotC = currentSize - 1 - r; }
                else if (rotationState == 2) { rotR = currentSize - 1 - r; rotC = currentSize - 1 - c; }
                else if (rotationState == 3) { rotR = currentSize - 1 - c; rotC = r; }

                int isoX = offsetX + (rotC - rotR) * currentTilePixels;
                int isoY = offsetY + (rotC + rotR) * (currentTilePixels / 2);

                drawIsometricTile(g2d, isoX, isoY, tile, currentTilePixels);
            }
        }
    }

    private Point[] getScreenPoints(int x, int y, double[] heights, int currentTilePixels) {
        int hScale = 16;
        double hN = heights[0], hO = heights[1], hS = heights[2], hW = heights[3];

        return new Point[]{
            new Point(x, y - (int)(hN * hScale)),
            new Point(x + currentTilePixels, y + currentTilePixels / 2 - (int)(hO * hScale)),
            new Point(x, y + currentTilePixels - (int)(hS * hScale)),
            new Point(x - currentTilePixels, y + currentTilePixels / 2 - (int)(hW * hScale))
        };
    }

    private void drawIsometricTile(Graphics2D g2d, int x, int y, VisualTile tile, int currentTilePixels) {
        double[] heights = tile.getCorners();
        Point[] pts = getScreenPoints(x, y, heights, currentTilePixels);

        Polygon topFace = new Polygon();
        for (Point p : pts) topFace.addPoint(p.x, p.y);

        double avgHeight = (heights[0] + heights[1] + heights[2] + heights[3]) / 4.0;
        int green = Math.max(40, Math.min(240, 110 + (int)(avgHeight * 16 * 3)));
        g2d.setColor(new Color(50, green, 50));
        g2d.fillPolygon(topFace);

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.drawPolygon(topFace);
        
        g2d.setColor(new Color(255, 255, 255, 140));
        g2d.setFont(new Font("Monospaced", Font.BOLD, (currentTilePixels < 40) ? 7 : 10));
        g2d.drawString("T" + tile.type, x - (currentTilePixels / 4), y + currentTilePixels / 2 - (int)(avgHeight * 16) + 3);
    }
}
