package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final VisualTile[][] mapGrid;
    private final int gridSize;
    private final int tilePixels = 40;
    private int rotationState = 0;

    public MapPanel(VisualTile[][] mapGrid, int gridSize) {
        this.mapGrid = mapGrid;
        this.gridSize = gridSize;
    }

    public void setRotation(int rotationState) {
        this.rotationState = rotationState;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 3;

        // Tiefensortierung (Painter's Algorithm) je nach Rotationswinkel
        int startR = (rotationState == 2 || rotationState == 3) ? gridSize - 1 : 0;
        int endR = (rotationState == 2 || rotationState == 3) ? -1 : gridSize;
        int stepR = (rotationState == 2 || rotationState == 3) ? -1 : 1;

        int startC = (rotationState == 1 || rotationState == 2) ? gridSize - 1 : 0;
        int endC = (rotationState == 1 || rotationState == 2) ? -1 : gridSize;
        int stepC = (rotationState == 1 || rotationState == 2) ? -1 : 1;

        // Kacheln rendern (von hinten nach vorne)
        for (int r = startR; r != endR; r += stepR) {
            for (int c = startC; c != endC; c += stepC) {
                VisualTile tile = mapGrid[r][c];
                if (tile == null) continue;

                int rotR = r, rotC = c;
                if (rotationState == 1) { rotR = c; rotC = gridSize - 1 - r; }
                else if (rotationState == 2) { rotR = gridSize - 1 - r; rotC = gridSize - 1 - c; }
                else if (rotationState == 3) { rotR = gridSize - 1 - c; rotC = r; }

                int isoX = offsetX + (rotC - rotR) * tilePixels;
                int isoY = offsetY + (rotC + rotR) * (tilePixels / 2);

                drawIsometricTile(g2d, isoX, isoY, tile);
            }
        }
    }

    private Point[] getScreenPoints(int x, int y, double[] heights) {
        int hScale = 16;
        double hN = heights[0], hO = heights[1], hS = heights[2], hW = heights[3];

        return new Point[]{
            new Point(x, y - (int)(hN * hScale)),
            new Point(x + tilePixels, y + tilePixels / 2 - (int)(hO * hScale)),
            new Point(x, y + tilePixels - (int)(hS * hScale)),
            new Point(x - tilePixels, y + tilePixels / 2 - (int)(hW * hScale))
        };
    }

    private void drawIsometricTile(Graphics2D g2d, int x, int y, VisualTile tile) {
        double[] heights = tile.getCorners();
        Point[] pts = getScreenPoints(x, y, heights);

        Polygon topFace = new Polygon();
        for (Point p : pts) topFace.addPoint(p.x, p.y);

        // Schattierung basierend auf der Durchschnittshöhe
        double avgHeight = (heights[0] + heights[1] + heights[2] + heights[3]) / 4.0;
        int green = Math.max(40, Math.min(240, 110 + (int)(avgHeight * 16 * 3)));
        g2d.setColor(new Color(50, green, 50));
        g2d.fillPolygon(topFace);

        // Dezente Kachelränder
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.drawPolygon(topFace);
        
        // Kachel-Typ als Text-Overlay
        g2d.setColor(new Color(255, 255, 255, 140));
        g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
        g2d.drawString("T" + tile.type, x - 10, y + tilePixels / 2 - (int)(avgHeight * 16) + 3);
    }
}
