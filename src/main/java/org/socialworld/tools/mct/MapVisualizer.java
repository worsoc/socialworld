package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapVisualizer extends JFrame {
    private static final int GRID_SIZE = 9;
    
    private MapPanel mapPanel;
    private File lastDirectory = new File(System.getProperty("user.home"));

    private List<File> mapFiles = new ArrayList<>();
    private int currentMapIndex = -1;
    
    private JButton prevButton;
    private JButton nextButton;
    private JButton acceptButton;
    private JButton rejectButton;

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

        JButton rateButton = new JButton("⭐ Maps bewerten");
        rateButton.addActionListener(e -> openFolderDialogAndScan());
        toolBar.add(rateButton);

        prevButton = new JButton("< Zurück");
        prevButton.setEnabled(false);
        prevButton.addActionListener(e -> navigateMap(-1));
        toolBar.add(prevButton);

        nextButton = new JButton("Vor >");
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> navigateMap(1));
        toolBar.add(nextButton);

        acceptButton = new JButton("✓ Übernehmen");
        acceptButton.setEnabled(false);
        acceptButton.addActionListener(e -> renameAndRemoveFromList("onlyLs_"));
        toolBar.add(acceptButton);

        rejectButton = new JButton("✕ Verwerfen");
        rejectButton.setEnabled(false);
        rejectButton.addActionListener(e -> renameAndRemoveFromList("tileterm_deletable_"));
        toolBar.add(rejectButton);

        add(toolBar, BorderLayout.NORTH);

        openMapFileDialog();
    }

    private void openMapFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wähle deine Map-Datei aus");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt"));
        
        fileChooser.setCurrentDirectory(lastDirectory);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            lastDirectory = selectedFile.getParentFile();
            
            mapFiles.clear();
            currentMapIndex = -1;
            updateNavigationButtons();
            
            loadMapAndDisplay(selectedFile);
        } else if (mapPanel == null) {
            openFolderDialogAndScan();
        }
    }

    private void openFolderDialogAndScan() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setDialogTitle("Wähle den Ordner mit den Maps aus");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setCurrentDirectory(lastDirectory);

        if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            lastDirectory = selectedFolder;

            File[] matchingFiles = selectedFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("tileterm_save_") && name.endsWith(".txt");
                }
            });

            if (matchingFiles != null && matchingFiles.length > 0) {
                mapFiles = new ArrayList<>(Arrays.asList(matchingFiles));
                Collections.sort(mapFiles);
                
                currentMapIndex = 0;
                loadMapAndDisplay(mapFiles.get(currentMapIndex));
                updateNavigationButtons();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Keine Dateien mit dem Namen 'tileterm_save_*.txt' in diesem Ordner gefunden.", 
                    "Keine Maps gefunden", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                if (mapPanel == null) {
                    System.exit(0);
                }
            }
        } else if (mapPanel == null) {
            System.out.println("Start abgebrochen.");
            System.exit(0);
        }
    }

    private void navigateMap(int direction) {
        if (mapFiles == null || mapFiles.isEmpty()) return;

        int newIndex = currentMapIndex + direction;
        if (newIndex >= 0 && newIndex < mapFiles.size()) {
            currentMapIndex = newIndex;
            loadMapAndDisplay(mapFiles.get(currentMapIndex));
            updateNavigationButtons();
        }
    }

    // Passt den Dateinamen an und entfernt das Element vollständig aus der Liste
    private void renameAndRemoveFromList(String newPrefix) {
        if (currentMapIndex < 0 || mapFiles == null || mapFiles.isEmpty()) return;

        File oldFile = mapFiles.get(currentMapIndex);
        String oldName = oldFile.getName();
        
        String newName = oldName.replaceFirst("^tileterm_save_", newPrefix);
        File newFile = new File(oldFile.getParentFile(), newName);

        if (oldFile.renameTo(newFile)) {
            // Aus der internen Navigationsliste löschen
            mapFiles.remove(currentMapIndex);
            
            if (mapFiles.isEmpty()) {
                // Keine Maps mehr übrig im Bewertungsmodus
                currentMapIndex = -1;
                setTitle("3D Map Visualizer - Keine weiteren Maps");
                updateNavigationButtons();
                JOptionPane.showMessageDialog(this, 
                    "Alle Maps in diesem Ordner wurden bewertet!", 
                    "Fertig", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Wenn wir das letzte Element gelöscht haben, müssen wir den Index verringern
                if (currentMapIndex >= mapFiles.size()) {
                    currentMapIndex = mapFiles.size() - 1;
                }
                // Nächste verfügbare Map laden
                loadMapAndDisplay(mapFiles.get(currentMapIndex));
                updateNavigationButtons();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Die Datei konnte nicht umbenannt werden. Eventuell ist sie blockiert.", 
                "Fehler beim Umbenennen", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateNavigationButtons() {
        boolean isInListMode = (currentMapIndex >= 0 && !mapFiles.isEmpty());
        
        prevButton.setEnabled(isInListMode && currentMapIndex > 0);
        nextButton.setEnabled(isInListMode && currentMapIndex < mapFiles.size() - 1);
        
        acceptButton.setEnabled(isInListMode);
        rejectButton.setEnabled(isInListMode);
    }

    private void loadMapAndDisplay(File file) {
        VisualTile[][] mapGrid = MapLoader.loadMapData(file.getAbsolutePath(), GRID_SIZE);

        setTitle("3D Map Visualizer - " + file.getName());

        if (mapPanel == null) {
            mapPanel = new MapPanel(mapGrid, GRID_SIZE);
            add(mapPanel, BorderLayout.CENTER);
        } else {
            mapPanel.updateMapData(mapGrid); 
        }
        
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapVisualizer().setVisible(true));
    }
}
