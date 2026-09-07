package org.socialworld.tools.mct;

import javax.swing.*;

import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapFileManager {
    private File lastDirectory = new File(System.getProperty("user.home"));
    private List<File> mapFiles = new ArrayList<>();
    private int currentMapIndex = -1;
    private boolean isRatingMode = false;

    public File getLastDirectory() { return lastDirectory; }
    public void setLastDirectory(File dir) { this.lastDirectory = dir; }
    public List<File> getMapFiles() { return mapFiles; }
    public int getCurrentMapIndex() { return currentMapIndex; }
    public boolean isRatingMode() { return isRatingMode; }

    public boolean scanDirectory(File folder, String prefix) {
        this.lastDirectory = folder;
        this.isRatingMode = prefix.equals("tileterm_save_");

        File[] matchingFiles = folder.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith(".txt"));

        if (matchingFiles != null && matchingFiles.length > 0) {
            mapFiles = new ArrayList<>(Arrays.asList(matchingFiles));
            Collections.sort(mapFiles);
            currentMapIndex = 0;
            return true;
        }
        return false;
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
