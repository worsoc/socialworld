package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import java.awt.*;

public class GlobalTerrainEditor extends JFrame {
    public GlobalTerrainEditor() {
        setTitle("GlobalTerrain Layer Editor (729m Fraktales Grid)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MacroMap map = new MacroMap(32, 32); 
        GlobalTerrainEditorCanvas canvas = new GlobalTerrainEditorCanvas(map);
        add(new JScrollPane(canvas), BorderLayout.CENTER);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebar.setPreferredSize(new Dimension(220, 729));

        sidebar.add(new JLabel("<html><b>EDITIER-MODUS SELEKTION</b></html>"));
        sidebar.add(Box.createVerticalStrut(5));
        
        // NEU: Zoom-Modus als reguläres RadioButton-Werkzeug hinzugefügt
        JRadioButton modeZoom = new JRadioButton("Zoom / Navigation", true); // Standardmäßig aktiv
        JRadioButton modeElevation = new JRadioButton("Höhe bearbeiten");
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeFauna = new JRadioButton("Fauna platzieren");
        
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeZoom); modeGroup.add(modeElevation); 
        modeGroup.add(modeTerrain); modeGroup.add(modeFauna);
        
        sidebar.add(modeZoom); sidebar.add(modeElevation); 
        sidebar.add(modeTerrain); sidebar.add(modeFauna);
        sidebar.add(Box.createVerticalStrut(20));

        // Dynamisches Kontext-Panel
        JPanel contextPanel = new JPanel(new CardLayout());
        
        // Panel für Zoom (zeigt nur eine kleine Anleitung)
        JPanel panelZoom = new JPanel(new BorderLayout());
        panelZoom.add(new JLabel("<html>Klicke links auf eine Zelle, um hineinzuzoomen.<br>Klicke rechts, um herauszuzoomen.</html>"), BorderLayout.NORTH);
        
        JPanel panelElevation = new JPanel(new BorderLayout());
        panelElevation.add(new JLabel("Ziel-Höhe (Meter):"), BorderLayout.NORTH);
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500); elevationSlider.setPaintTicks(true); elevationSlider.setPaintLabels(true);
        panelElevation.add(elevationSlider, BorderLayout.CENTER);
        
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden:"));
        JComboBox<String> terrainBox = new JComboBox<>(new String[]{"GRAS", "SAND", "WASSER", "STEIN", "SCHNEE"});
        panelTerrain.add(terrainBox);

        JPanel panelFauna = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFauna.add(new JLabel("Fauna:"));
        JComboBox<String> faunaBox = new JComboBox<>(new String[]{"LEER", "BAUM"});
        panelFauna.add(faunaBox);

        contextPanel.add(panelZoom, "ZOOM");
        contextPanel.add(panelElevation, "HÖHE"); 
        contextPanel.add(panelTerrain, "TERRAIN"); 
        contextPanel.add(panelFauna, "FAUNA");
        sidebar.add(contextPanel);

        CardLayout cl = (CardLayout) contextPanel.getLayout();
        
        // Event-Listener verknüpfen
        modeZoom.addActionListener(e -> { canvas.setEditorMode("ZOOM"); cl.show(contextPanel, "ZOOM"); });
        modeElevation.addActionListener(e -> { canvas.setEditorMode("HÖHE"); cl.show(contextPanel, "HÖHE"); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); });
        modeFauna.addActionListener(e -> { canvas.setEditorMode("FAUNA"); cl.show(contextPanel, "FAUNA"); });

        elevationSlider.addChangeListener(e -> canvas.setBrushElevation(elevationSlider.getValue()));
        terrainBox.addActionListener(e -> canvas.setBrushTerrain((String) terrainBox.getSelectedItem()));
        faunaBox.addActionListener(e -> canvas.setBrushFauna((String) faunaBox.getSelectedItem()));

        add(sidebar, BorderLayout.EAST);
        pack();
        setSize(1050, 800);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GlobalTerrainEditor().setVisible(true));
    }
}
