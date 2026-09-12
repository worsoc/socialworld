package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MapVisualizer extends JFrame {
	private boolean showMegaGrid = true; // Steuert, ob das 3x3 Panorama oder die 9x9 Einzelansicht aktiv ist

	private static final int GRID_SIZE = 9;
    
    private MapPanel mapPanel;
    private final MapFileManager fileManager = new MapFileManager();
    
    private JButton prevButton, nextButton, acceptButton, rejectButton;

    public MapVisualizer() {
        setTitle("3D Map Visualizer");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fileManager.handleShutdown(MapVisualizer.this);
                System.exit(0);
            }
        });
        
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton loadButton = new JButton("📁 Maps laden...");
        loadButton.addActionListener(e -> openFolderDialog("onlyLs_", "Wähle den Ordner mit den übernommenen Maps (onlyLs) aus"));
        toolBar.add(loadButton);

        JButton rateButton = new JButton("⭐ Maps bewerten");
        rateButton.addActionListener(e -> openFolderDialog("tileterm_save_", "Wähle den Ordner mit den zu bewertenden Maps aus"));
        toolBar.add(rateButton);

        prevButton = new JButton("< Zurück");
        prevButton.addActionListener(e -> { fileManager.navigate(-1); refreshUI(); });
        toolBar.add(prevButton);

        nextButton = new JButton("Vor >");
        nextButton.addActionListener(e -> { fileManager.navigate(1); refreshUI(); });
        toolBar.add(nextButton);

        acceptButton = new JButton("✓ Übernehmen");
        acceptButton.addActionListener(e -> handleRatingAction("onlyLs_"));
        toolBar.add(acceptButton);

        rejectButton = new JButton("✕ Verwerfen");
        rejectButton.addActionListener(e -> handleRatingAction("tileterm_deletable_"));
        toolBar.add(rejectButton);

        // --- NEU: RadioButtons für die Modus-Auswahl ---
        JRadioButton rbSingle = new JRadioButton("Einzelansicht (9x9)");
        JRadioButton rbMega = new JRadioButton("Panorama (3x3)");
        
        // Das Panorama standardmäßig aktivieren, passend zu showMegaGrid = true
        rbMega.setSelected(true); 

        // Die Buttons in eine Gruppe packen, damit immer nur einer aktiv sein kann
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(rbSingle);
        modeGroup.add(rbMega);

        // ActionListener: Wechsel zur Einzelansicht
        rbSingle.addActionListener(e -> {
            showMegaGrid = false;
            refreshUI();
        });

        // ActionListener: Wechsel zur Panorama-Ansicht
        rbMega.addActionListener(e -> {
            showMegaGrid = true;
            refreshUI();
        });

        // Die RadioButtons oben rechts in deine bestehende Button-Leiste (z.B. topPanel) schieben:
        toolBar.add(Box.createHorizontalStrut(20)); // Ein wenig Abstand zu den anderen Buttons
        toolBar.add(rbSingle);
        toolBar.add(rbMega);

        add(toolBar, BorderLayout.NORTH);
        openFolderDialog("onlyLs_", "Wähle den Ordner mit den übernommenen Maps (onlyLs) aus");
    }

    private void openFolderDialog(String prefix, String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(fileManager.getLastDirectory());

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (fileManager.scanDirectory(chooser.getSelectedFile(), prefix)) {
                refreshUI();
            } else {
                JOptionPane.showMessageDialog(this, "Keine passenden '" + prefix + "*.txt' Dateien gefunden.", "Leer", JOptionPane.INFORMATION_MESSAGE);
                if (mapPanel == null && prefix.equals("onlyLs_")) {
                    openFolderDialog("tileterm_save_", "Wähle den Ordner mit den zu bewertenden Maps aus");
                } else if (mapPanel == null) {
                    System.exit(0);
                }
            }
        } else if (mapPanel == null) {
            if (prefix.equals("onlyLs_")) openFolderDialog("tileterm_save_", "Wähle den Ordner mit den zu bewertenden Maps aus");
            else System.exit(0);
        }
    }

    private void handleRatingAction(String prefix) {
        if (fileManager.renameAndRemoveCurrent(prefix)) {
            if (fileManager.getMapFiles().isEmpty()) {
                setTitle("3D Map Visualizer - Keine weiteren Maps");
                updateButtons();
                JOptionPane.showMessageDialog(this, "Alle Maps in diesem Ordner wurden bewertet!", "Fertig", JOptionPane.INFORMATION_MESSAGE);
            } else {
                refreshUI();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Datei blockiert.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshUI() {
        File currentFile = fileManager.getCurrentFile();
        if (currentFile == null) return;

        int SECTOR_SIZE = 9;
        VisualTile[][] finalGrid;

        // Modus-Weiche: Zeigen wir das Mega-Grid oder nur das 9x9-Raster?
        if (showMegaGrid) {
            int WORLD_SIZE = SECTOR_SIZE * 3; // 27x27 Kacheln
            VisualTile[][] megaGrid = new VisualTile[WORLD_SIZE][WORLD_SIZE];

            // 1. Center-Map laden
            VisualTile[][] centerGrid = MapLoader.loadMapData(currentFile.getAbsolutePath(), SECTOR_SIZE);
            copyGridToMega(centerGrid, megaGrid, 1, 1, SECTOR_SIZE, null);

            // 2. Nachbarn aus der Dateiliste laden
            java.util.List<File> allFiles = fileManager.getMapFiles();
            int currentIndex = fileManager.getCurrentMapIndex();

            int fileOffset = 1;
            for (int sRow = 0; sRow < 3; sRow++) {
                for (int sCol = 0; sCol < 3; sCol++) {
                    if (sRow == 1 && sCol == 1) continue; // Das Zentrum haben wir schon

                    int targetIndex = currentIndex + fileOffset;
                    if (targetIndex >= 0 && targetIndex < allFiles.size()) {
                        File neighborFile = allFiles.get(targetIndex);
                        VisualTile[][] neighborGrid = MapLoader.loadMapData(neighborFile.getAbsolutePath(), SECTOR_SIZE);
                        copyGridToMega(neighborGrid, megaGrid, sRow, sCol, SECTOR_SIZE, centerGrid);
                        fileOffset++;
                    }
                }
            }
            finalGrid = megaGrid;
            setTitle("3D World Visualizer - Panorama (3x3 Sektoren) - Zentrum: " + currentFile.getName());
        } else {
            // Reines 9x9 Raster der aktuellen Datei laden
            finalGrid = MapLoader.loadMapData(currentFile.getAbsolutePath(), SECTOR_SIZE);
            setTitle("3D World Visualizer - Einzelansicht (9x9) - Datei: " + currentFile.getName());
        }

        // Grid an das MapPanel zur Darstellung übergeben
        if (mapPanel == null) {
            mapPanel = new MapPanel(finalGrid);
            add(mapPanel, BorderLayout.CENTER);
        } else {
            mapPanel.updateMapData(finalGrid);
        }
        
        revalidate();
        repaint();
        updateButtons();
    }

    /**
     * Kopiert ein 9x9 Grid an die Sektoren-Position im 27x27 Riesen-Grid 
     * und gleicht die relativen Höhen-Nullpunkte perfekt an das Zentrum an.
     */
    private void copyGridToMega(VisualTile[][] source, VisualTile[][] target, int sectorRow, int sectorCol, int size, VisualTile[][] centerGrid) {
        int startRowOffset = sectorRow * size;
        int startColOffset = sectorCol * size;

        // 1. Höhen-Nullpunkt des Quell-Sektors ermitteln (Basishöhe des allerersten Elements [0][0])
        int sourceNullLevel = 0;
        if (source != null && source[0][0] != null) {
            sourceNullLevel = source[0][0].baseHeight;
        }

        // 2. Welt-Höhen-Anker berechnen
        // Wo muss die Basishöhe dieses Sektors in der Welt liegen, um an das Zentrum anzudocken?
        int worldHeightOffset = 0;

        if (centerGrid != null && centerGrid[0][0] != null) {
            // Wir nutzen die Basishöhen der vier äußeren Eck-Kacheln des Zentrums als Anker
            int centerNW = centerGrid[0][0].baseHeight;
            int centerNE = centerGrid[0][size - 1].baseHeight;
            int centerSE = centerGrid[size - 1][size - 1].baseHeight;
            int centerSW = centerGrid[size - 1][0].baseHeight;

            // Je nachdem, wo der Nachbar liegt, adaptieren wir das globale Höhen-Niveau
            if (sectorRow == 0 && sectorCol == 1) {        // NORDEN
                worldHeightOffset = centerNW; 
            } else if (sectorRow == 1 && sectorCol == 2) {  // OSTEN
                worldHeightOffset = centerNE;
            } else if (sectorRow == 2 && sectorCol == 1) {  // SÜDEN
                worldHeightOffset = centerSW;
            } else if (sectorRow == 1 && sectorCol == 0) {  // WESTEN
                worldHeightOffset = centerNW;
            } else {
                // Diagonale Sektoren (Ecken) orientieren sich an den Hauptecken des Zentrums
                if (sectorRow == 0 && sectorCol == 0) worldHeightOffset = centerNW;
                if (sectorRow == 0 && sectorCol == 2) worldHeightOffset = centerNE;
                if (sectorRow == 2 && sectorCol == 2) worldHeightOffset = centerSE;
                if (sectorRow == 2 && sectorCol == 0) worldHeightOffset = centerSW;
            }
        }

        // 3. Kopieren und Live-Anpassung der Basishöhe
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                VisualTile originalTile = source[r][c];
                if (originalTile == null) continue;

                // Formel: (Relative Basishöhe - Eigen-Nullpunkt) + Globaler Welt-Anker
                int adjustedBaseHeight = (originalTile.baseHeight - sourceNullLevel) + worldHeightOffset;

                // Neue VisualTile mit angepasster Basishöhe in das Riesen-Grid einsetzen
                target[startRowOffset + r][startColOffset + c] = new VisualTile(originalTile.type, adjustedBaseHeight);
            }
        }
    }

    private void updateButtons() {
        int index = fileManager.getCurrentMapIndex();
        int size = fileManager.getMapFiles().size();
        boolean hasFiles = index >= 0 && size > 0;

        prevButton.setEnabled(hasFiles && index > 0);
        nextButton.setEnabled(hasFiles && index < size - 1);
        acceptButton.setEnabled(hasFiles && fileManager.isRatingMode());
        rejectButton.setEnabled(hasFiles && fileManager.isRatingMode());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapVisualizer().setVisible(true));
    }
}
