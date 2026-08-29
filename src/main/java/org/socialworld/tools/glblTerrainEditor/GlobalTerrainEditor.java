package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import java.awt.*;

public class GlobalTerrainEditor extends JFrame {
    
    public GlobalTerrainEditor() {
        setTitle("GlobalTerrain Layer Editor (729m Fraktales Grid)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int mapWidth = 32; 
        int mapHeight = 32;

        MacroMap map = new MacroMap(mapWidth, mapHeight);
        GlobalTerrainEditorCanvas canvas = new GlobalTerrainEditorCanvas(map);
        
        JScrollPane scrollPane = new JScrollPane(canvas);
        add(scrollPane, BorderLayout.CENTER);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebar.setPreferredSize(new Dimension(220, 729));

        sidebar.add(new JLabel("<html><b>EDITIER-MODUS SELEKTION</b></html>"));
        sidebar.add(Box.createVerticalStrut(5));
        
        JRadioButton modeElevation = new JRadioButton("Höhe bearbeiten", true);
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeFauna = new JRadioButton("Fauna platzieren");
        
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeElevation);
        modeGroup.add(modeTerrain);
        modeGroup.add(modeFauna);
        
        sidebar.add(modeElevation);
        sidebar.add(modeTerrain);
        sidebar.add(modeFauna);
        sidebar.add(Box.createVerticalStrut(20));

        JPanel contextPanel = new JPanel(new CardLayout());
        
        JPanel panelElevation = new JPanel(new BorderLayout());
        panelElevation.add(new JLabel("Ziel-Höhe (Meter):"), BorderLayout.NORTH);
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500);
        elevationSlider.setPaintTicks(true);
        elevationSlider.setPaintLabels(true);
        panelElevation.add(elevationSlider, BorderLayout.CENTER);
        
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden-Typ:"));
        String[] terrains = {"GRAS", "SAND", "WASSER", "STEIN", "SCHNEE"};
        JComboBox<String> terrainBox = new JComboBox<>(terrains);
        panelTerrain.add(terrainBox);

        JPanel panelFauna = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFauna.add(new JLabel("Vegetation:"));
        String[] faunaObjects = {"LEER", "BAUM", "BUSCH"};
        JComboBox<String> faunaBox = new JComboBox<>(faunaObjects);
        panelFauna.add(faunaBox);

        contextPanel.add(panelElevation, "HÖHE");
        contextPanel.add(panelTerrain, "TERRAIN");
        contextPanel.add(panelFauna, "FAUNA");
        sidebar.add(contextPanel);

        CardLayout cl = (CardLayout) contextPanel.getLayout();
        
        modeElevation.addActionListener(e -> { canvas.setEditorMode("HÖHE"); cl.show(contextPanel, "HÖHE"); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); });
        modeFauna.addActionListener(e -> { canvas.setEditorMode("FAUNA"); cl.show(contextPanel, "FAUNA"); });

        elevationSlider.addChangeListener(e -> canvas.setBrushElevation(elevationSlider.getValue()));
        terrainBox.addActionListener(e -> canvas.setBrushTerrain((String) terrainBox.getSelectedItem()));
        faunaBox.addActionListener(e -> canvas.setBrushFauna((String) faunaBox.getSelectedItem()));

        sidebar.add(Box.createVerticalStrut(30));
        double realSizeKm = (mapWidth * 729.0) / 1000.0;
        sidebar.add(new JLabel(String.format("Weltgröße: %.2f km x %.2f km", realSizeKm, realSizeKm)));

        add(sidebar, BorderLayout.EAST);
        pack();
        setSize(1050, 800);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GlobalTerrainEditor().setVisible(true));
    }
}
