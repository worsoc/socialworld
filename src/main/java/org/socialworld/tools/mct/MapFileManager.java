package org.socialworld.tools.mct;

import javax.swing.*;

import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapFileManager {
    private int hiddenInvalidCount = 0;
    private File lastDirectory = new File(System.getProperty("user.home"));
    private List<File> mapFiles = new ArrayList<>();
    private int currentMapIndex = -1;
    private boolean isRatingMode = false;

    public File getLastDirectory() { return lastDirectory; }
    public void setLastDirectory(File dir) { this.lastDirectory = dir; }
    public List<File> getMapFiles() { return mapFiles; }
    public int getCurrentMapIndex() { return currentMapIndex; }
    public boolean isRatingMode() { return isRatingMode; }

    /** Anzahl der Dateien, die beim letzten Scan als ungültig oder unlesbar ausgeblendet wurden. */
    public int getHiddenInvalidCount() { return hiddenInvalidCount; }

    public boolean scanDirectory(File folder, String prefix) {
        this.lastDirectory = folder;
        this.isRatingMode = prefix.equals("tileterm_save_");

        this.hiddenInvalidCount = 0;

        File[] matchingFiles = folder.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith(".txt"));

        List<File> validFiles = new ArrayList<>();
        if (matchingFiles != null) {
            for (File file : matchingFiles) {
                if (isValidMapFile(file)) {
                    validFiles.add(file);
                } else {
                    hiddenInvalidCount++;
                }
            }
        }

        if (!validFiles.isEmpty()) {
            mapFiles = validFiles;
            Collections.sort(mapFiles);
            currentMapIndex = 0;
            return true;
        }
        return false;
    }

    /**
     * Gültig ist nur ein vollständiges Raster aus L-Kacheln. Damit fallen auch Dateien mit -999
     * (interne Höhenprüfung des MapCreationTools nicht erfolgreich), TODO und Teilrastern heraus.
     */
    private boolean isValidMapFile(File file) {
        try {
            LTileRaster.fromFile(file);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    public File getCurrentFile() {
        if (currentMapIndex >= 0 && currentMapIndex < mapFiles.size()) {
            return mapFiles.get(currentMapIndex);
        }
        return null;
    }

    public void navigate(int direction) {
        int newIndex = currentMapIndex + direction;
        if (newIndex >= 0 && newIndex < mapFiles.size()) {
            currentMapIndex = newIndex;
        }
    }

    public boolean renameAndRemoveCurrent(String newPrefix) {
        File oldFile = getCurrentFile();
        if (oldFile == null) return false;

        String oldName = oldFile.getName();
        String newName = oldName.replaceFirst("^tileterm_save_", newPrefix);
        File newFile = new File(oldFile.getParentFile(), newName);

        if (oldFile.renameTo(newFile)) {
            mapFiles.remove(currentMapIndex);
            if (!mapFiles.isEmpty() && currentMapIndex >= mapFiles.size()) {
                currentMapIndex = mapFiles.size() - 1;
            } else if (mapFiles.isEmpty()) {
                currentMapIndex = -1;
            }
            return true;
        }
        return false;
    }

    /**
     * Benennt alle aktuell gelisteten Dateien um, wie beim einzelnen Durchgehen.
     * Eine schon vorhandene Zieldatei wird nicht überschrieben, die Quelldatei bleibt dann in der Liste.
     *
     * @return die Anzahl der umbenannten Dateien
     */
    public int renameAndRemoveAll(String newPrefix) {
        int renamedCount = 0;

        for (File oldFile : new ArrayList<>(mapFiles)) {
            String newName = oldFile.getName().replaceFirst("^tileterm_save_", newPrefix);
            File newFile = new File(oldFile.getParentFile(), newName);

            if (!newFile.exists() && oldFile.renameTo(newFile)) {
                mapFiles.remove(oldFile);
                renamedCount++;
            }
        }

        currentMapIndex = mapFiles.isEmpty() ? -1 : 0;
        return renamedCount;
    }

    public void handleShutdown(Component parent) {
        if (lastDirectory == null || !lastDirectory.exists() || !lastDirectory.isDirectory()) return;

        // 1.onlyLs_ verschieben
        File[] onlyLsFiles = lastDirectory.listFiles((dir, name) -> name.startsWith("onlyLs_") && name.endsWith(".txt"));
        if (onlyLsFiles != null && onlyLsFiles.length > 0) {
            int choice = JOptionPane.showConfirmDialog(parent, 
                "Es wurden " + onlyLsFiles.length + " übernommene Dateien (onlyLs_) gefunden.\nMöchtest du diese verschieben?", 
                "Dateien verschieben?", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                JFileChooser targetChooser = new JFileChooser();
                targetChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                targetChooser.setCurrentDirectory(lastDirectory);
                if (targetChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    File targetFolder = targetChooser.getSelectedFile();
                    for (File f : onlyLsFiles) {
                        try {
                            Files.move(f.toPath(), new File(targetFolder, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (Exception ex) {
                            System.err.println("Fehler beim Verschieben: " + f.getName());
                        }
                    }
                }
            }
        }

        // 2. tileterm_deletable_ löschen
        File[] deletableFiles = lastDirectory.listFiles((dir, name) -> name.startsWith("tileterm_deletable_") && name.endsWith(".txt"));
        if (deletableFiles != null && deletableFiles.length > 0) {
            int choice = JOptionPane.showConfirmDialog(parent, 
                "Es wurden " + deletableFiles.length + " verworfene Dateien gefunden.\nEndgültig löschen?", 
                "Dateien löschen?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (choice == JOptionPane.YES_OPTION) {
                for (File f : deletableFiles) {
                    f.delete();
                }
            }
        }
    }
}
