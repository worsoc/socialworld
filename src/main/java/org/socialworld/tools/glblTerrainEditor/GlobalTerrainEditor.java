package org.socialworld.tools.glblTerrainEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Das Hauptfenster des Gelände-Editors.
 * Integriert die Werkzeugleiste, Kontextmenüs, das ScrollPane für das Canvas
 * und eine dynamische Statuszeile in der Fußzeile.
 */
public class GlobalTerrainEditor extends JFrame {
    private final JLabel statusBar; // Die Fußzeile für Navigations- und Tool-Hinweise

    public GlobalTerrainEditor() {
        setTitle("GlobalTerrain Layer Editor (729m Fraktales Grid)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Weltkarte erzeugen (32x32 Makro-Kacheln à 729m x 729m)
        MacroMap map = new MacroMap(32, 32); 
        GlobalTerrainEditorCanvas canvas = new GlobalTerrainEditorCanvas(map);
        
        // Canvas in ein ScrollPane einbetten für große Welten
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // --- STATUSBÄNDERUNG IN DER FUSSZEILE ---
        statusBar = new JLabel(canvas.getStatusText());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(this.getWidth(), 24));
        statusBar.setFont(new Font("Arial", Font.PLAIN, 11));
        add(statusBar, BorderLayout.SOUTH);

        // Aktualisiert den Statustext immer dann, wenn der Nutzer im Canvas klickt (z.B. zoomt oder zeichnet)
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                statusBar.setText(canvas.getStatusText());
            }
        });

        // --- SIDEBAR INTERFACE ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebar.setPreferredSize(new Dimension(240, 729));

        sidebar.add(new JLabel("<html><b>EDITIER-MODUS SELEKTION</b></html>"));
        sidebar.add(Box.createVerticalStrut(8));
        
        // Die 5 Werkzeuge als RadioButtons
        JRadioButton modeZoom = new JRadioButton("Zoom / Navigation", true); 
        JRadioButton modeElevation = new JRadioButton("Höhe bearbeiten");
        JRadioButton modeTerrain = new JRadioButton("Terrain zuweisen");
        JRadioButton modeBaum = new JRadioButton("Baum platzieren");   
        JRadioButton modeStrauch = new JRadioButton("Strauch platzieren"); 
        
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(modeZoom); modeGroup.add(modeElevation); 
        modeGroup.add(modeTerrain); modeGroup.add(modeBaum); modeGroup.add(modeStrauch);
        
        sidebar.add(modeZoom); sidebar.add(modeElevation); 
        sidebar.add(modeTerrain); sidebar.add(modeBaum); sidebar.add(modeStrauch);

        // Pinsel-Größe Regler (Meso-Radius)
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(new JLabel("Pinsel-Größe (Radius):"));
        JSlider radiusSlider = new JSlider(1, 9, 1);
        radiusSlider.setMajorTickSpacing(2); 
        radiusSlider.setPaintTicks(true); 
        radiusSlider.setPaintLabels(true);
        radiusSlider.addChangeListener(e -> canvas.setBrushRadius(radiusSlider.getValue()));
        sidebar.add(radiusSlider);
        sidebar.add(Box.createVerticalStrut(15));

        // --- DYNAMISCHES KONTEXT-PANEL (CardLayout) ---
        JPanel contextPanel = new JPanel(new CardLayout());
        
        // 1. Zoom-Anleitung
        JPanel panelZoom = new JPanel(new BorderLayout());
        panelZoom.add(new JLabel("<html>Klicke links zum Hineinzoomen.<br>Klicke rechts zum Herauszoomen.</html>"), BorderLayout.NORTH);
        
        // 2. Höhenregler
        JPanel panelElevation = new JPanel(new BorderLayout());
        panelElevation.add(new JLabel("Ziel-Höhe (Meter):"), BorderLayout.NORTH);
        JSlider elevationSlider = new JSlider(0, 2000, 100);
        elevationSlider.setMajorTickSpacing(500); 
        elevationSlider.setPaintTicks(true); 
        elevationSlider.setPaintLabels(true);
        panelElevation.add(elevationSlider, BorderLayout.CENTER);
        
        // 3. Terrain-Auswahl
        JPanel panelTerrain = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTerrain.add(new JLabel("Boden-Typ:"));
        JComboBox<String> terrainBox = new JComboBox<>(new String[]{"GRAS", "SAND", "WASSER", "STEIN", "SCHNEE"});
        panelTerrain.add(terrainBox);

        // 4. Baum-Auswahl
        JPanel panelBaum = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBaum.add(new JLabel("Baumart:"));
        JComboBox<String> baumBox = new JComboBox<>(new String[]{"KEIN_BAUM", "EICHE", "KIEFER", "BIRKE"});
        panelBaum.add(baumBox);

        // 5. Strauch-Auswahl
        JPanel panelStrauch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStrauch.add(new JLabel("Strauchart:"));
        JComboBox<String> strauchBox = new JComboBox<>(new String[]{"KEIN_STRAUCH", "ZIERSTRAUCH", "BEERENSTRAUCH", "FARNE"});
        panelStrauch.add(strauchBox);

        // Cards zum Verwalter hinzufügen
        contextPanel.add(panelZoom, "ZOOM"); 
        contextPanel.add(panelElevation, "HÖHE"); 
        contextPanel.add(panelTerrain, "TERRAIN"); 
        contextPanel.add(panelBaum, "BAUM");
        contextPanel.add(panelStrauch, "STRAUCH");
        sidebar.add(contextPanel);

        // --- SCHALTLOGIK FÜR DIE MENÜS ---
        CardLayout cl = (CardLayout) contextPanel.getLayout();
        
        modeZoom.addActionListener(e -> { canvas.setEditorMode("ZOOM"); cl.show(contextPanel, "ZOOM"); statusBar.setText(canvas.getStatusText()); });
        modeElevation.addActionListener(e -> { canvas.setEditorMode("HÖHE"); cl.show(contextPanel, "HÖHE"); statusBar.setText(canvas.getStatusText()); });
        modeTerrain.addActionListener(e -> { canvas.setEditorMode("TERRAIN"); cl.show(contextPanel, "TERRAIN"); statusBar.setText(canvas.getStatusText()); });
        modeBaum.addActionListener(e -> { canvas.setEditorMode("BAUM"); cl.show(contextPanel, "BAUM"); statusBar.setText(canvas.getStatusText()); });
        modeStrauch.addActionListener(e -> { canvas.setEditorMode("STRAUCH"); cl.show(contextPanel, "STRAUCH"); statusBar.setText(canvas.getStatusText()); });

        // Daten-Verknüpfungen mit dem Canvas
        elevationSlider.addChangeListener(e -> canvas.setBrushElevation(elevationSlider.getValue()));
        terrainBox.addActionListener(e -> canvas.setBrushTerrain((String) terrainBox.getSelectedItem()));
        baumBox.addActionListener(e -> canvas.setBrushBaum((String) baumBox.getSelectedItem()));
        strauchBox.addActionListener(e -> canvas.setBrushStrauch((String) strauchBox.getSelectedItem()));

        // Weltgrößen-Berechnung anzeigen
        sidebar.add(Box.createVerticalStrut(30));
        double realSizeKm = (32 * 729.0) / 1000.0;
        sidebar.add(new JLabel(String.format("Weltgröße: %.2f km x %.2f km", realSizeKm, realSizeKm)));

        add(sidebar, BorderLayout.EAST);
        pack();
        setSize(1080, 840); 
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Applikation im Swing-Thread starten
        SwingUtilities.invokeLater(() -> new GlobalTerrainEditor().setVisible(true));
    }
}
