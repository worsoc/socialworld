package org.socialworld.tools.mct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MapVisualizer extends JFrame {
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

        VisualTile[][] mapGrid = MapLoader.loadMapData(currentFile.getAbsolutePath(), GRID_SIZE);
        setTitle("3D Map Visualizer - " + currentFile.getName());

        if (mapPanel == null) {
            mapPanel = new MapPanel(mapGrid, GRID_SIZE);
            add(mapPanel, BorderLayout.CENTER);
        } else {
            mapPanel.updateMapData(mapGrid);
        }
        revalidate();
        repaint();
        updateButtons();
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
