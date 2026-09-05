package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MapVisualizer extends JFrame {
    private static final int GRID_SIZE = 9;
    
    private MapPanel mapPanel;
    // Speicherort für das zuletzt genutzte Verzeichnis merken (Standard: aktueller Ordner ".")
    private File lastDirectory = new File(".");

    public MapVisualizer() {
        setTitle("3D Map Visualizer");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton loadButton = new JButton("📁 Map laden...");
        loadButton.addActionListener(e -> openMapFileDialog());
        toolBar.add(loadButton);
        add(toolBar, BorderLayout.NORTH);

        openMapFileDialog();
    }

    private void openMapFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wähle deine Map-Datei aus");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        
        // Hier übergeben wir das gemerkte Verzeichnis an den FileChooser
        fileChooser.setCurrentDirectory(lastDirectory);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // WICHTIG: Das übergeordnete Verzeichnis der ausgewählten Datei für das nächste Mal merken
            lastDirectory = selectedFile.getParentFile();

            VisualTile[][] mapGrid = MapLoader.loadMapData(selectedFile.getAbsolutePath(), GRID_SIZE);

            setTitle("3D Map Visualizer - " + selectedFile.getName());

            if (mapPanel == null) {
                mapPanel = new MapPanel(mapGrid, GRID_SIZE);
                add(mapPanel, BorderLayout.CENTER);
            } else {
                mapPanel.updateMapData(mapGrid); 
            }
            
            revalidate();
            repaint();
            
        } else if (mapPanel == null) {
            System.out.println("Start abgebrochen.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapVisualizer().setVisible(true));
    }
}
