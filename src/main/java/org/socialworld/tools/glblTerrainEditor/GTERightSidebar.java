package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import org.socialworld.attributes.GroundMaterial;
import org.socialworld.tools.mct.LTileRasterLibrary;
import java.awt.*;
import java.io.File;
import java.util.Random;

/**
 * Die bereinigte Sidebar. Bindet das GTEGeneratorPanel ein 
 * und verwaltet die Werkzeuge sowie Speicher-Funktionen.
 */
public class GTERightSidebar extends JPanel {

    private static final int CONTEXT_PANEL_HEIGHT = 40;

    public GTERightSidebar(GlobalTerrainEditorCanvas canvas, GlobalTerrainEditor editor) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(240, 729));

        // 1. Das ausgelagerte Generator-Panel ganz oben einfügen
        GTEGeneratorPanel generatorPanel = new GTEGeneratorPanel(canvas, editor);
        generatorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, generatorPanel.getPreferredSize().height));
        add(generatorPanel);
        
        add(Box.createVerticalStrut(15));
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, separator.getPreferredSize().height));
        add(separator);
        add(Box.createVerticalStrut(15));

        // 2. Editier-Modus Selektion
        JRadioButton modeZoom = new JRadioButton("Zoom / Navigation", true); 
        editor.rbZoom = modeZoom;
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeBaum = new JRadioButton("Baum platzieren");   
        JRadioButton modeStrauch = new JRadioButton("Strauch platzieren");
        JRadioButton modeHeightRaster = new JRadioButton("Höhenraster zuordnen");

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeZoom); modeGroup.add(modeTerrain); modeGroup.add(modeBaum); modeGroup.add(modeStrauch); modeGroup.add(modeHeightRaster);
        add(modeZoom); add(modeTerrain); add(modeBaum); add(modeStrauch); add(modeHeightRaster);

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
 
         // Direktes Laden der Großbuchstaben-Namen aus den Enums
        JPanel panelBaum = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBaum.add(new JLabel("Baumart:"));
        JComboBox<String> baumBox = new JComboBox<>(org.socialworld.attributes.VegetationTree.getUpperCaseNames());
        panelBaum.add(baumBox);

        JPanel panelStrauch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStrauch.add(new JLabel("Strauchart:"));
        JComboBox<String> strauchBox = new JComboBox<>(org.socialworld.attributes.VegetationBush.getUpperCaseNames());
        panelStrauch.add(strauchBox);

        
        JPanel panelHeightRaster = new JPanel(new BorderLayout());
        panelHeightRaster.add(new JLabel("Klicke auf eine Zelle."), BorderLayout.NORTH);

        contextPanel.add(panelZoom, "ZOOM"); contextPanel.add(panelTerrain, "TERRAIN"); contextPanel.add(panelBaum, "BAUM"); contextPanel.add(panelStrauch, "STRAUCH"); contextPanel.add(panelHeightRaster, GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER);
        // Feste Höhe, damit frei bleibender Platz unten in der Leiste landet.
        contextPanel.setPreferredSize(new Dimension(210, CONTEXT_PANEL_HEIGHT));
        contextPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, CONTEXT_PANEL_HEIGHT));
        add(contextPanel);

        CardLayout cl = (CardLayout) contextPanel.getLayout();
        modeZoom.addActionListener(e -> { canvas.setEditorMode("ZOOM"); cl.show(contextPanel, "ZOOM"); editor.updateStatusBarText(); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); editor.updateStatusBarText(); });
        modeBaum.addActionListener(e -> { canvas.setEditorMode("BAUM"); cl.show(contextPanel, "BAUM"); editor.updateStatusBarText(); });
        modeStrauch.addActionListener(e -> { canvas.setEditorMode("STRAUCH"); cl.show(contextPanel, "STRAUCH"); editor.updateStatusBarText(); });
        modeHeightRaster.addActionListener(e -> { canvas.setEditorMode(GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER); cl.show(contextPanel, GlobalTerrainEditorCanvas.MODE_HEIGHT_RASTER); editor.updateStatusBarText(); });

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

        add(Box.createVerticalStrut(5));
        JCheckBox showHeightRasterMarkers = new JCheckBox("Höhenraster-Dreiecke zeigen", canvas.isHeightRasterMarkersVisible());
        showHeightRasterMarkers.setAlignmentX(Component.LEFT_ALIGNMENT);
        showHeightRasterMarkers.addActionListener(e -> {
            canvas.setHeightRasterMarkersVisible(showHeightRasterMarkers.isSelected());
            canvas.repaint();
        });
        add(showHeightRasterMarkers);

        GTEHeightViewWindow heightView = new GTEHeightViewWindow(editor, canvas);
        canvas.setHeightView(heightView);
        JCheckBox showHeightView = new JCheckBox("Höhenansicht");
        showHeightView.addActionListener(e -> heightView.setVisible(showHeightView.isSelected()));
        heightView.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                showHeightView.setSelected(false);
            }
        });
        // Die Höhenansicht gibt es nur auf der Weltkarte.
        canvas.setZoomChangeListener(() -> {
            boolean onWorldMap = canvas.getCurrentZoom() == GlobalTerrainEditorCanvas.ZoomLevel.MACRO;
            showHeightView.setEnabled(onWorldMap);
            if (!onWorldMap) heightView.setVisible(false);
        });
        add(showHeightView);

        add(Box.createVerticalStrut(5));
        JButton heightModelButton = new JButton("Höhenmodell setzen");
        heightModelButton.setBackground(new Color(110, 50, 140));
        heightModelButton.setForeground(Color.WHITE);
        heightModelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, heightModelButton.getPreferredSize().height));
        heightModelButton.addActionListener(e -> setHeightModel(canvas));
        add(heightModelButton);

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

        saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, saveButton.getPreferredSize().height));
        loadButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, loadButton.getPreferredSize().height));

        // BoxLayout richtet Elemente nur dann bündig aus, wenn alle dieselbe Ausrichtung haben.
        for (Component component : getComponents()) {
            ((JComponent) component).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
     }

    /**
     * Belegt jede zweite Zelle mit einem zufälligen Raster. Der Ordner wird bei jedem Aufruf neu gelesen,
     * damit neue Dateien sofort berücksichtigt werden.
     */
    private void setHeightModel(GlobalTerrainEditorCanvas canvas) {
        LTileRasterLibrary library = canvas.getRasterLibrary();
        if (library.isLoaded()) {
            library.load(library.getDirectory());
        } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Ordner mit den Rastern auswählen");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
            library.load(chooser.getSelectedFile());
        }

        if (library.getEntries().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Der Ordner " + library.getDirectory().getName() + " enthält keine gültigen Raster.",
                    "Höhenmodell setzen", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        MacroMap map = canvas.getMacroMap();
        int occupiedFreeCells = HeightModelFiller.countOccupiedFreeCells(map);
        if (occupiedFreeCells > 0) {
            int choice = JOptionPane.showConfirmDialog(this,
                    occupiedFreeCells + " belegte Zellen außerhalb des Schachbretts passen danach nicht mehr und werden geleert.\nFortfahren?",
                    "Höhenmodell setzen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) return;
        }

        HeightModelFiller.Result result = HeightModelFiller.fill(map, library.getEntries(), new Random());

        canvas.repaint();
        if (canvas.getHeightView() != null) canvas.getHeightView().refresh();
        JOptionPane.showMessageDialog(this,
                result.getFilledCells() + " von " + result.getPatternCells() + " Zellen des Schachbretts belegt, "
                        + library.getEntries().size() + " Raster aus " + library.getDirectory().getName() + ".",
                "Höhenmodell setzen", JOptionPane.INFORMATION_MESSAGE);
    }
}
