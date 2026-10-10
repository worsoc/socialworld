package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import org.socialworld.attributes.GroundMaterial;
import java.awt.*;
import java.io.File;

/**
 * Die bereinigte Sidebar. Bindet das GTEGeneratorPanel ein 
 * und verwaltet die Werkzeuge sowie Speicher-Funktionen.
 */
public class GTERightSidebar extends JPanel {
    
    public GTERightSidebar(GlobalTerrainEditorCanvas canvas, GlobalTerrainEditor editor) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(240, 729));

        // 1. Das ausgelagerte Generator-Panel ganz oben einfügen
        GTEGeneratorPanel generatorPanel = new GTEGeneratorPanel(canvas, editor);
        add(generatorPanel);
        
        add(Box.createVerticalStrut(15));
        add(new JSeparator(JSeparator.HORIZONTAL));
        add(Box.createVerticalStrut(15));

        // 2. Editier-Modus Selektion
        add(new JLabel("<html><b>EDITIER-MODUS SELEKTION</b></html>"));
        add(Box.createVerticalStrut(8));
        
        JRadioButton modeZoom = new JRadioButton("Zoom / Navigation", true); 
        editor.rbZoom = modeZoom;
        JRadioButton modeElevation = new JRadioButton("Höhe bearbeiten");
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeBaum = new JRadioButton("Baum platzieren");   
        JRadioButton modeStrauch = new JRadioButton("Strauch platzieren"); 
        
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeZoom); modeGroup.add(modeElevation); modeGroup.add(modeTerrain); modeGroup.add(modeBaum); modeGroup.add(modeStrauch);
        add(modeZoom); add(modeElevation); add(modeTerrain); add(modeBaum); add(modeStrauch);

        add(Box.createVerticalStrut(15));
        add(new JLabel("Pinsel-Größe (Radius):"));
        JSlider radiusSlider = new JSlider(1, 9, 1);
        radiusSlider.setMajorTickSpacing(2); radiusSlider.setPaintTicks(true); radiusSlider.setPaintLabels(true);
        radiusSlider.addChangeListener(e -> canvas.setBrushRadius(radiusSlider.getValue()));
        add(radiusSlider);
        add(Box.createVerticalStrut(15));

        // 3. Kontext-Panels für die Pinsel
        JPanel contextPanel = new JPanel(new CardLayout());
        JPanel panelZoom = new JPanel(new BorderLayout());
        panelZoom.add(new JLabel("<html>Klicke links zum Hineinzoomen.<br>Klicke rechts zum Herauszoomen.</html>"), BorderLayout.NORTH);
        
        JPanel panelElevation = new JPanel(new BorderLayout());
        panelElevation.add(new JLabel("Ziel-Höhe (Meter):"), BorderLayout.NORTH);
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500); elevationSlider.setPaintTicks(true); elevationSlider.setPaintLabels(true);
        panelElevation.add(elevationSlider, BorderLayout.CENTER);
        
        // --- SUCHE DIESE STELLE IN GTERightSidebar.java ---
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden-Typ:"));
        String[] terrainElems = GroundMaterial.getUpperCaseNames();
        JComboBox<String> terrainBox = new JComboBox<>(terrainElems);

        terrainBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                // Basis-Komponente vom Standard-Renderer holen
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value != null) {
                    String terrainName = value.toString();
                    // Holt die exakte Farbe aus Ihrer bestehenden Farbpalette
                    Color terrainColor = GTERenderColorPalette.getTerrainColor(terrainName);
                    
                    if (isSelected) {
                        // Wenn der Eintrag mit der Maus überfahren/ausgewählt ist: Leicht abgedunkelt oder mit Rahmen
                        c.setBackground(terrainColor.darker());
                    } else {
                        // Im normalen Zustand: Die echte Terrain-Farbe im Hintergrund
                        c.setBackground(terrainColor);
                    }
                    
                    // Kontrast-Check: Bei sehr hellen Farben (z.B. Schnee/Sand) schwarze Schrift, sonst weiß
                    double brightness = (0.299 * terrainColor.getRed() + 0.587 * terrainColor.getGreen() + 0.114 * terrainColor.getBlue()) / 255.0;
                    c.setForeground(brightness > 0.65 ? Color.BLACK : Color.WHITE);
                }
                
                return c;
            }
        });
        panelTerrain.add(terrainBox);
 
 /*       
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden-Typ:"));
        String[] terrainElems = GroundMaterial.getUpperCaseNames();
        JComboBox<String> terrainBox = new JComboBox<>(terrainElems);
        panelTerrain.add(terrainBox);
*/        
        JPanel panelBaum = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBaum.add(new JLabel("Baumart:"));
        JComboBox<String> baumBox = new JComboBox<>(new String[]{"KEIN_BAUM", "EICHE", "KIEFER", "BIRKE", "BUCHE", "FICHTE", "WEIDE"});
        panelBaum.add(baumBox);

        JPanel panelStrauch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStrauch.add(new JLabel("Strauchart:"));
        JComboBox<String> strauchBox = new JComboBox<>(new String[]{"KEIN_STRAUCH", "FARNE", "ZIERSTRAUCH", "BEERENSTRAUCH", "BROMBEERE", "HEIDEKRAUT", "GINSTER"});
        panelStrauch.add(strauchBox);

/*
 * // Direktes Laden der Großbuchstaben-Namen aus den Enums
JComboBox<String> baumBox = new JComboBox<>(org.socialworld.attributes.VegetationTree.getUpperCaseNames());
JComboBox<String> strauchBox = new JComboBox<>(org.socialworld.attributes.VegetationBush.getUpperCaseNames());
        
 */
        
        contextPanel.add(panelZoom, "ZOOM"); contextPanel.add(panelElevation, "HÖHE"); contextPanel.add(panelTerrain, "TERRAIN"); contextPanel.add(panelBaum, "BAUM"); contextPanel.add(panelStrauch, "STRAUCH");
        add(contextPanel);

        CardLayout cl = (CardLayout) contextPanel.getLayout();
        modeZoom.addActionListener(e -> { canvas.setEditorMode("ZOOM"); cl.show(contextPanel, "ZOOM"); editor.updateStatusBarText(); });
        modeElevation.addActionListener(e -> { canvas.setEditorMode("HÖHE"); cl.show(contextPanel, "HÖHE"); editor.updateStatusBarText(); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); editor.updateStatusBarText(); });
        modeBaum.addActionListener(e -> { canvas.setEditorMode("BAUM"); cl.show(contextPanel, "BAUM"); editor.updateStatusBarText(); });
        modeStrauch.addActionListener(e -> { canvas.setEditorMode("STRAUCH"); cl.show(contextPanel, "STRAUCH"); editor.updateStatusBarText(); });

        elevationSlider.addChangeListener(e -> canvas.setBrushElevation(elevationSlider.getValue()));
        terrainBox.addActionListener(e -> canvas.setBrushTerrain((String) terrainBox.getSelectedItem()));
        baumBox.addActionListener(e -> {
            String selected = (String) baumBox.getSelectedItem();
            var tree = org.socialworld.attributes.VegetationTree.fromName(selected);
            canvas.setBrushBaum(tree != null ? tree.getGteId() : 0);
        });

        strauchBox.addActionListener(e -> {
            String selected = (String) strauchBox.getSelectedItem();
            var bush = org.socialworld.attributes.VegetationBush.fromName(selected);
            canvas.setBrushStrauch(bush != null ? bush.getGteId() : 0);
        });

        // 4. Makro-Quadrant Steuerung
        add(Box.createVerticalStrut(15));
        JLabel quadLabel = new JLabel("<html><b>MAKRO-QUADRANT</b></html>");
        quadLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(quadLabel);
        add(Box.createVerticalStrut(5));

        JPanel quadGridPanel = new JPanel(new GridLayout(2, 2, 4, 4));
        quadGridPanel.setMaximumSize(new Dimension(210, 50));
        quadGridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnNW = new JButton("NW");
        JButton btnNO = new JButton("NO");
        JButton btnSW = new JButton("SW");
        JButton btnSO = new JButton("SO");

        int qSize = GTEQuadrant.QUADRANT_SIZE;
        btnNW.addActionListener(e -> { canvas.setCurrentMacroOffsetX(0);     canvas.setCurrentMacroOffsetY(0);     canvas.repaint(); editor.updateStatusBarText(); });
        btnNO.addActionListener(e -> { canvas.setCurrentMacroOffsetX(qSize); canvas.setCurrentMacroOffsetY(0);     canvas.repaint(); editor.updateStatusBarText(); });
        btnSW.addActionListener(e -> { canvas.setCurrentMacroOffsetX(0);     canvas.setCurrentMacroOffsetY(qSize); canvas.repaint(); editor.updateStatusBarText(); });
        btnSO.addActionListener(e -> { canvas.setCurrentMacroOffsetX(qSize); canvas.setCurrentMacroOffsetY(qSize); canvas.repaint(); editor.updateStatusBarText(); });

        quadGridPanel.add(btnNW); quadGridPanel.add(btnNO);
        quadGridPanel.add(btnSW); quadGridPanel.add(btnSO);
        add(quadGridPanel);

        // 5. Datei-Operationen (Save / Load)
        JButton saveButton = new JButton("save map ...");
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setBackground(new Color(40, 110, 45)); saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            String workingDir = System.getProperty("user.dir");
            File defaultDir = new File(workingDir);
            JFileChooser fileChooser = defaultDir.exists() ? new JFileChooser(defaultDir) : new JFileChooser();
            fileChooser.setDialogTitle("GlobalTerrain Karte speichern");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("GlobalTerrain Map (*.map)", "map"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".map")) file = new File(file.getAbsolutePath() + ".map");
                try {
                    GlobalTerrainExporter.exportMap(canvas.getMacroMap(), file); 
                    JOptionPane.showMessageDialog(this, "Karte erfolgreich exportiert!"); 
                } catch (Exception ex) { 
                    ex.printStackTrace(); 
                    JOptionPane.showMessageDialog(this, "Fehler beim Exportieren: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton loadButton = new JButton("load map ...");
        loadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadButton.setBackground(new Color(50, 100, 160)); loadButton.setForeground(Color.WHITE);
        loadButton.addActionListener(e -> {
            String workingDir = System.getProperty("user.dir");
            File defaultDir = new File(workingDir);
            JFileChooser fileChooser = defaultDir.exists() ? new JFileChooser(defaultDir) : new JFileChooser();
            fileChooser.setDialogTitle("GlobalTerrain Karte laden");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("GlobalTerrain Map (*.map)", "map"));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    MacroMap loadedMap = GlobalTerrainImporter.importMap(fileChooser.getSelectedFile());
                    canvas.setMacroMap(loadedMap); 
                    editor.updateStatusBarText();
                    canvas.repaint(); 
                    JOptionPane.showMessageDialog(this, "Karte erfolgreich geladen!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Fehler beim Laden: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(Box.createVerticalStrut(15));
        add(saveButton); add(Box.createVerticalStrut(5)); add(loadButton);

        add(Box.createVerticalStrut(20));
        double realSizeKm = (32 * 729.0) / 1000.0;
        add(new JLabel(String.format("Weltgröße: %.2f km x %.2f km", realSizeKm, realSizeKm)));
     }
}
