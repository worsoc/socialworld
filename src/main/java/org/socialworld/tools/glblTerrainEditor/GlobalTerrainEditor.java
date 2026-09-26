package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlobalTerrainEditor extends JFrame {
    private final GlobalTerrainEditorCanvas canvas;
    JRadioButton rbZoom;
    
    // Der feste Basis-Titel Ihres Editors
    private final String baseTitle = "GTE:";
    
    public GlobalTerrainEditor() {
        // Initiale Titelzeile setzen
        setTitle(baseTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MacroMap map = new MacroMap(32, 32); 
        this.canvas = new GlobalTerrainEditorCanvas(map, this);
        
        // Das Canvas wird direkt im CENTER platziert (Kein JScrollPane, keine grauen Ränder!)
        add(canvas, BorderLayout.CENTER);

        // Die Maus-Interaktion aktualisiert nun direkt die Titelzeile des Fensters
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateStatusBarText();
            }
        });

        // Die ausgelagerte Sidebar hinzufügen
        GTERightSidebar sidebar = new GTERightSidebar(canvas, this);
        add(sidebar, BorderLayout.EAST);
        
        // Berechnet die Fenstergröße exakt (Canvas: 720x720px + Sidebar: 240px)
        pack(); 
        
        // Verhindert das manuelle Verzerren, damit das Layout absolut pixelfrei und scharf bleibt
        setResizable(false); 
        setLocationRelativeTo(null);
        
        // Den ersten Statustext direkt beim Start in die Titelzeile schreiben
        updateStatusBarText();
    }

    /**
     * Schreibt den aktuellen Modus- und Navigationstext direkt in die obere Titelzeile des Fensters.
     * Spart wertvollen vertikalen Platz auf Notebook-Monitoren!
     */
    public void updateStatusBarText() {
        if (canvas != null) {
            setTitle(baseTitle + " | " + canvas.getStatusText().trim());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GlobalTerrainEditor().setVisible(true));
    }
}
