package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private VisualTile[][] mapGrid;
    private final int tilePixels = 40;

    public MapPanel(VisualTile[][] mapGrid) {
        this.mapGrid = mapGrid;
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

        // Kachelgröße und Höhenskalierung anpassen
        int currentTilePixels = (currentSize > 9) ? 20 : 40;
        int hScale = (currentSize > 9) ? 8 : 16;

        int offsetX = getWidth() / 2;
        int offsetY = (currentSize > 9) ? getHeight() / 5 : getHeight() / 3;

        // Korrekte Tiefensortierung für Isometrie: 
        // Wir zeichnen in diagonalen Linien von Norden (0,0) nach Süden.
        // Das verhindert Überlagerungsfehler im Südwesten und Südosten.
        for (int sum = 0; sum < 2 * currentSize - 1; sum++) {
            for (int r = 0; r < currentSize; r++) {
                int c = sum - r;
                if (c >= 0 && c < currentSize) {
                    VisualTile tile = mapGrid[r][c];
                    if (tile == null) continue;

                    // Isometrische Projektion (Richtung Südwesten und Südosten)
                    int isoX = offsetX + (c - r) * currentTilePixels;
                    int isoY = offsetY + (c + r) * (currentTilePixels / 2);

                    drawIsometricTile(g2d, isoX, isoY, tile, currentTilePixels, hScale);
                }
            }
        }
    }

    private Point[] getScreenPoints(int x, int y, double[] heights, int currentTilePixels, int hScale) {
        double hN = heights[0], hO = heights[1], hS = heights[2], hW = heights[3];

        return new Point[]{
            new Point(x, y - (int)(hN * hScale)),
            new Point(x + currentTilePixels, y + currentTilePixels / 2 - (int)(hO * hScale)),
            new Point(x, y + currentTilePixels - (int)(hS * hScale)),
            new Point(x - currentTilePixels, y + currentTilePixels / 2 - (int)(hW * hScale))
        };
    }

    private void drawIsometricTile(Graphics2D g2d, int x, int y, VisualTile tile, int currentTilePixels, int hScale) {
        double[] heights = tile.getCorners();
        Point[] pts = getScreenPoints(x, y, heights, currentTilePixels, hScale);

        Polygon topFace = new Polygon();
        for (Point p : pts) topFace.addPoint(p.x, p.y);

        double avgHeight = (heights[0] + heights[1] + heights[2] + heights[3]) / 4.0;
        int green = Math.max(40, Math.min(240, 110 + (int)(avgHeight * hScale * 3)));
        g2d.setColor(new Color(50, green, 50));
        g2d.fillPolygon(topFace);

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.drawPolygon(topFace);
        
        g2d.setColor(new Color(255, 255, 255, 140));
        g2d.setFont(new Font("Monospaced", Font.BOLD, (currentTilePixels < 40) ? 7 : 10));
        g2d.drawString("T" + tile.type, x - (currentTilePixels / 4), y + currentTilePixels / 2 - (int)(avgHeight * hScale) + 3);
    }
}
