package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.socialworld.tools.mct.LTileRaster;

/**
 * Zeichnet die Höhen der Raster als Farben. Jeder ganzzahlige Höhenwert hat genau eine Farbe, die Skala
 * reicht von der niedrigsten bis zur höchsten Höhe der ganzen Karte. So bleiben die Farben beim Wechsel
 * des Quadranten vergleichbar.
 */
final class GTEHeightMapRenderer {

    private static final Color NO_RASTER = new Color(60, 60, 60);
    private static final Color[] RAMP = {
        new Color(30, 70, 160), new Color(40, 150, 150), new Color(70, 160, 70),
        new Color(220, 210, 90), new Color(150, 100, 60), new Color(245, 245, 245)
    };
    private static final int TILES_PER_CELL = 9;
    private static final int LEGEND_WIDTH = 120;
    private static final int LEGEND_HEIGHT = 10;

    private final int lowest;
    private final Color[] colors;

    /** Liest die Skala aus der Karte. Eine Karte ohne Raster hat keine Farben. */
    GTEHeightMapRenderer(MacroMap map) {
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                MacroMapCell cell = map.getCell(x, y);
                if (cell.hasHeightRaster()) {
                    low = Math.min(low, cell.getHeightRaster().getLowestHeight() + cell.getHeightRasterOffset());
                    high = Math.max(high, cell.getHeightRaster().getHighestHeight() + cell.getHeightRasterOffset());
                }
            }
        }

        lowest = low;
        colors = new Color[(high >= low) ? high - low + 1 : 0];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = rampColor(colors.length > 1 ? (double) i / (colors.length - 1) : 0.5);
        }
    }

    private static Color rampColor(double position) {
        double scaled = position * (RAMP.length - 1);
        int index = Math.min((int) scaled, RAMP.length - 2);
        double t = scaled - index;
        Color from = RAMP[index];
        Color to = RAMP[index + 1];
        return new Color(
                (int) Math.round(from.getRed() + t * (to.getRed() - from.getRed())),
                (int) Math.round(from.getGreen() + t * (to.getGreen() - from.getGreen())),
                (int) Math.round(from.getBlue() + t * (to.getBlue() - from.getBlue())));
    }

    /** Zeichnet eine Zelle Pixel für Pixel, gemessen wird in der Mitte jedes Pixels. */
    void renderCell(Graphics2D g2, MacroMapCell cell, int startX, int startY, int sizeInPixels) {
        if (!cell.hasHeightRaster()) {
            g2.setColor(NO_RASTER);
            g2.fillRect(startX, startY, sizeInPixels, sizeInPixels);
            return;
        }

        LTileRaster raster = cell.getHeightRaster();
        int offset = cell.getHeightRasterOffset();
        double tilesPerPixel = (double) TILES_PER_CELL / sizeInPixels;
        for (int py = 0; py < sizeInPixels; py++) {
            double tileY = (py + 0.5) * tilesPerPixel;
            for (int px = 0; px < sizeInPixels; px++) {
                long height = Math.round(raster.heightAt((px + 0.5) * tilesPerPixel, tileY)) + offset;
                g2.setColor(colors[(int) (height - lowest)]);
                g2.fillRect(startX + px, startY + py, 1, 1);
            }
        }
    }

    /** Zeichnet unten links die Farbskala mit niedrigster und höchster Höhe. */
    void renderLegend(Graphics2D g2, int canvasHeight) {
        if (colors.length == 0) return;

        int x = 8;
        int y = canvasHeight - 8 - LEGEND_HEIGHT;
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        String lowText = String.valueOf(lowest);
        String highText = String.valueOf(lowest + colors.length - 1);
        int lowWidth = g2.getFontMetrics().stringWidth(lowText);
        int highWidth = g2.getFontMetrics().stringWidth(highText);

        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(x - 4, y - 4, lowWidth + LEGEND_WIDTH + highWidth + 20, LEGEND_HEIGHT + 8);
        g2.setColor(Color.WHITE);
        g2.drawString(lowText, x, y + LEGEND_HEIGHT - 1);
        g2.drawString(highText, x + lowWidth + LEGEND_WIDTH + 12, y + LEGEND_HEIGHT - 1);

        for (int i = 0; i < LEGEND_WIDTH; i++) {
            g2.setColor(colors[i * colors.length / LEGEND_WIDTH]);
            g2.fillRect(x + lowWidth + 6 + i, y, 1, LEGEND_HEIGHT);
        }
    }
}
