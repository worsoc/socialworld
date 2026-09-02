package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MapVisualizer extends JFrame {
    private static final int GRID_SIZE = 9;
    private int currentRotation = 0;

    public MapVisualizer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wähle deine Map-Datei aus");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        fileChooser.setCurrentDirectory(new File("."));

        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Abgebrochen.");
            System.exit(0);
        }

        File selectedFile = fileChooser.getSelectedFile();
        VisualTile[][] mapGrid = MapLoader.loadMapData(selectedFile.getAbsolutePath(), GRID_SIZE);

        setTitle("3D Map Visualizer - " + selectedFile.getName());
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        MapPanel mapPanel = new MapPanel(mapGrid, GRID_SIZE);
        
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton rotateButton = new JButton("🔄 90° Drehen");
        rotateButton.addActionListener(e -> {
            currentRotation = (currentRotation + 1) % 4;
            mapPanel.setRotation(currentRotation);
        });
        toolBar.add(rotateButton);

        add(toolBar, BorderLayout.NORTH);
        add(mapPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapVisualizer().setVisible(true));
    }
}
