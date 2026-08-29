package org.socialworld.tools.glblTerrainEditor;


import javax.swing.*;
import java.awt.*;

public class GlobalTerrainEditor extends JFrame {
    
    public GlobalTerrainEditor() {
        setTitle("GlobalTerrain Macro Editor (720m Grid)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HIER DIE GRÖSSE ANPASSEN: z.B. 64x64, 128x128 oder 512x512
        int mapWidth = 64; 
        int mapHeight = 64;
        
       // Erzeuge eine Karte mit mapWidth x mapHeight Makro-Kacheln (jede steht für 720m x 720m)
        MacroMap map = new MacroMap(mapWidth, mapHeight);
        GlobalTerrainEditorCanvas canvas = new GlobalTerrainEditorCanvas(map);

        // WICHTIG: Canvas in ein ScrollPane packen, damit große Karten scrollbar sind!
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Sidebar für Editor-Einstellungen
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sidebar.add(new JLabel("Pinsel-Höhe (Meter):"));
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500);
        elevationSlider.setPaintTicks(true);
        elevationSlider.setPaintLabels(true);
        
        elevationSlider.addChangeListener(e -> {
            canvas.setBrushElevation(elevationSlider.getValue());
        });
        sidebar.add(elevationSlider);

        // Zusatzinfo zur Weltdimension in der Sidebar anzeigen
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(new JLabel("Weltgröße: " + (mapWidth * 720 / 1000) + " km x " + (mapHeight * 720 / 1000) + " km"));

        add(sidebar, BorderLayout.EAST);
        pack();
        setSize(1024, 768);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GlobalTerrainEditor().setVisible(true);
        });
    }
}
