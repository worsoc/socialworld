package org.socialworld.tools.glblTerrainEditor;

import java.io.File;

import javax.swing.*;

import org.socialworld.attributes.GroundMaterial;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlobalTerrainEditor extends JFrame {
    private final JLabel statusBar;

    public GlobalTerrainEditor() {
        setTitle("GlobalTerrain Layer Editor (729m Fraktales Grid)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MacroMap map = new MacroMap(32, 32); 
        GlobalTerrainEditorCanvas canvas = new GlobalTerrainEditorCanvas(map);
        JScrollPane scrollPane = new JScrollPane(canvas);
        add(scrollPane, BorderLayout.CENTER);

        statusBar = new JLabel(canvas.getStatusText());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 24));
        statusBar.setFont(new Font("Arial", Font.PLAIN, 11));
        add(statusBar, BorderLayout.SOUTH);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                statusBar.setText(canvas.getStatusText());
            }
        });

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebar.setPreferredSize(new Dimension(240, 729));

        sidebar.add(new JLabel("<html><b>EDITIER-MODUS SELEKTION</b></html>"));
        sidebar.add(Box.createVerticalStrut(8));
        
        JRadioButton modeZoom = new JRadioButton("Zoom / Navigation", true); 
        JRadioButton modeElevation = new JRadioButton("Höhe bearbeiten");
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeBaum = new JRadioButton("Baum platzieren");   
        JRadioButton modeStrauch = new JRadioButton("Strauch platzieren"); 
        
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeZoom); modeGroup.add(modeElevation); modeGroup.add(modeTerrain); modeGroup.add(modeBaum); modeGroup.add(modeStrauch);
        sidebar.add(modeZoom); sidebar.add(modeElevation); sidebar.add(modeTerrain); sidebar.add(modeBaum); sidebar.add(modeStrauch);

        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(new JLabel("Pinsel-Größe (Radius):"));
        JSlider radiusSlider = new JSlider(1, 9, 1);
        radiusSlider.setMajorTickSpacing(2); radiusSlider.setPaintTicks(true); radiusSlider.setPaintLabels(true);
        radiusSlider.addChangeListener(e -> canvas.setBrushRadius(radiusSlider.getValue()));
        sidebar.add(radiusSlider);
        sidebar.add(Box.createVerticalStrut(15));

        JPanel contextPanel = new JPanel(new CardLayout());
        JPanel panelZoom = new JPanel(new BorderLayout());
        panelZoom.add(new JLabel("<html>Klicke links zum Hineinzoomen.<br>Klicke rechts zum Herauszoomen.</html>"), BorderLayout.NORTH);
        
        JPanel panelElevation = new JPanel(new BorderLayout());
        panelElevation.add(new JLabel("Ziel-Höhe (Meter):"), BorderLayout.NORTH);
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500); elevationSlider.setPaintTicks(true); elevationSlider.setPaintLabels(true);
        panelElevation.add(elevationSlider, BorderLayout.CENTER);
        
        // KORREKTUR: Alle 13 GroundMaterials gelistet
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden-Typ:"));
        String[] terrainElems = GroundMaterial.getUpperCaseNames();
        JComboBox<String> terrainBox = new JComboBox<>(terrainElems);
        panelTerrain.add(terrainBox);

        // KORREKTUR: Alle erweiterten Bäume gelistet
        JPanel panelBaum = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBaum.add(new JLabel("Baumart:"));
        JComboBox<String> baumBox = new JComboBox<>(new String[]{"KEIN_BAUM", "EICHE", "KIEFER", "BIRKE", "BUCHE", "FICHTE", "WEIDE"});
        panelBaum.add(baumBox);

        // KORREKTUR: Alle erweiterten Sträucher gelistet
        JPanel panelStrauch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStrauch.add(new JLabel("Strauchart:"));
        JComboBox<String> strauchBox = new JComboBox<>(new String[]{"KEIN_STRAUCH", "FARNE", "ZIERSTRAUCH", "BEERENSTRAUCH", "BROMBEERE", "HEIDEKRAUT", "GINSTER"});
        panelStrauch.add(strauchBox);

        contextPanel.add(panelZoom, "ZOOM"); contextPanel.add(panelElevation, "HÖHE"); contextPanel.add(panelTerrain, "TERRAIN"); contextPanel.add(panelBaum, "BAUM"); contextPanel.add(panelStrauch, "STRAUCH");
        sidebar.add(contextPanel);

        CardLayout cl = (CardLayout) contextPanel.getLayout();
        modeZoom.addActionListener(e -> { canvas.setEditorMode("ZOOM"); cl.show(contextPanel, "ZOOM"); statusBar.setText(canvas.getStatusText()); });
        modeElevation.addActionListener(e -> { canvas.setEditorMode("HÖHE"); cl.show(contextPanel, "HÖHE"); statusBar.setText(canvas.getStatusText()); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); statusBar.setText(canvas.getStatusText()); });
        modeBaum.addActionListener(e -> { canvas.setEditorMode("BAUM"); cl.show(contextPanel, "BAUM"); statusBar.setText(canvas.getStatusText()); });
        modeStrauch.addActionListener(e -> { canvas.setEditorMode("STRAUCH"); cl.show(contextPanel, "STRAUCH"); statusBar.setText(canvas.getStatusText()); });

        elevationSlider.addChangeListener(e -> canvas.setBrushElevation(elevationSlider.getValue()));
        terrainBox.addActionListener(e -> canvas.setBrushTerrain((String) terrainBox.getSelectedItem()));
        baumBox.addActionListener(e -> canvas.setBrushBaum((String) baumBox.getSelectedItem()));
        strauchBox.addActionListener(e -> canvas.setBrushStrauch((String) strauchBox.getSelectedItem()));

        JButton saveButton = new JButton("Karte Exportieren...");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setBackground(new Color(40, 110, 45)); 
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".map")) {
                    file = new File(file.getAbsolutePath() + ".map");
                }
                try {
                    // Wir holen uns die ECHTE, aktuell im Canvas aktive Karte!
                    // Dadurch werden alle geladenen und neu gezeichneten Daten exportiert.
                    MacroMap activeMap = canvas.getMacroMap(); 
                    
                    GlobalTerrainExporter.exportMap(activeMap, file); 
                    JOptionPane.showMessageDialog(this, "Karte erfolgreich exportiert!"); 
                } catch (Exception ex) { 
                    ex.printStackTrace(); 
                    JOptionPane.showMessageDialog(this, "Fehler beim Exportieren: " + ex.getMessage());
                }
            }
        });
        
        JButton loadButton = new JButton("Karte Laden...");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setBackground(new Color(50, 100, 160)); loadButton.setForeground(Color.WHITE);
 
	     loadButton.addActionListener(e -> {
	         JFileChooser fileChooser = new JFileChooser();
	         if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	             try {
	                 // 1. Die Karte ganz normal parsen und laden
	                 MacroMap loadedMap = GlobalTerrainImporter.importMap(fileChooser.getSelectedFile());
	                 
	                 // 2. Die neue Map einfach in das BESTEHENDE Canvas injizieren!
	                 canvas.setMacroMap(loadedMap); 
	                 
	                 // 3. Statusleiste aktualisieren
	                 statusBar.setText(canvas.getStatusText());
	                 
	                 JOptionPane.showMessageDialog(this, "Karte erfolgreich geladen!");
	             } catch (Exception ex) {
	                 ex.printStackTrace();
	                 JOptionPane.showMessageDialog(this, "Fehler beim Laden: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	             }
	         }
	     });

        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(saveButton); sidebar.add(Box.createVerticalStrut(5)); sidebar.add(loadButton);

        sidebar.add(Box.createVerticalStrut(20));
        double realSizeKm = (32 * 729.0) / 1000.0;
        sidebar.add(new JLabel(String.format("Weltgröße: %.2f km x %.2f km", realSizeKm, realSizeKm)));

        add(sidebar, BorderLayout.EAST);
        pack(); setSize(1080, 840); setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GlobalTerrainEditor().setVisible(true));
    }
}
