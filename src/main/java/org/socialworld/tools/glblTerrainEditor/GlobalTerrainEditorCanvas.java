package org.socialworld.tools.glblTerrainEditor;




import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlobalTerrainEditorCanvas extends JPanel {
    private final MacroMap macroMap;
    private final int cellSizeInPixels = 40; // Visualisierungs-Größe im Editor
    private double currentBrushElevation = 100.0; // Höhe, die gezeichnet wird

    public GlobalTerrainEditorCanvas(MacroMap macroMap) {
        this.macroMap = macroMap;
        setBackground(Color.DARK_GRAY);

        // Maus-Interaktion für Live-Zeichnen (Drag & Drop)
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { handleMousePaint(e); }

            @Override
            public void mouseDragged(MouseEvent e) { handleMousePaint(e); }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void handleMousePaint(MouseEvent e) {
        int cellX = e.getX() / cellSizeInPixels;
        int cellY = e.getY() / cellSizeInPixels;

        if (cellX >= 0 && cellX < macroMap.getWidth() && cellY >= 0 && cellY < macroMap.getHeight()) {
            // Live-Änderung mit Sicherheitscheck ausführen
            macroMap.updateCellElevation(cellX, cellY, currentBrushElevation);
            repaint(); // Canvas neu zeichnen
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int x = 0; x < macroMap.getWidth(); x++) {
            for (int y = 0; y < macroMap.getHeight(); y++) {
                MacroMapCell cell = macroMap.getCell(x, y);
                double elevation = cell.getReferenceElevation();

                // Dynamische Einfärbung basierend auf der Höhe (Heatmap-Feedback)
                int colorValue = (int) Math.min(255, Math.max(0, elevation * 0.5));
                g2.setColor(new Color(colorValue, 150, 100)); // Höhenabhängiges Grün/Gelb
                g2.fillRect(x * cellSizeInPixels, y * cellSizeInPixels, cellSizeInPixels - 1, cellSizeInPixels - 1);

                // Höhenwert als Text auf der Kachel anzeigen
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                g2.drawString((int)elevation + "m", x * cellSizeInPixels + 4, y * cellSizeInPixels + 22);
            }
        }
    }

    public void setBrushElevation(double elevation) { this.currentBrushElevation = elevation; }
    
   @Override
    public Dimension getPreferredSize() {
        // Berechnet die exakten Pixel-Dimensionen der Makro-Karte für das ScrollPane
        int widthInPixels = macroMap.getWidth() * cellSizeInPixels;
        int heightInPixels = macroMap.getHeight() * cellSizeInPixels;
        return new Dimension(widthInPixels, heightInPixels);
    }

}
