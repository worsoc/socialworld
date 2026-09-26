package org.socialworld.tools.glblTerrainEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalTerrainGenerator {

    private static final int MAP_WIDTH = 32;
    private static final int MAP_HEIGHT = 32;
    private static final int MESO_SIZE = 81;
    private static final int MIKRO_SIZE = 9;

    public static void main(String[] args) {
 
        // 1. Verzeichnisauswahl über JFileChooser starten
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setDialogTitle("Standardverzeichnis für Generierung wählen");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Nur Ordner auswählbar

        // Falls standardmäßig dein "output"-Ordner vorgeschlagen werden soll:
        File defaultDir = new File("output");
        if (defaultDir.exists()) {
            folderChooser.setCurrentDirectory(defaultDir);
        }

        int userSelection = folderChooser.showOpenDialog(null);

        // Abbrechen, falls der Benutzer das Fenster schließt oder auf Abbrechen klickt
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Generierung vom Benutzer abgebrochen.");
            return;
        }

        // Gewählten Ordner abgreifen
        File selectedDirectory = folderChooser.getSelectedFile();
        
        // Zeitstempel generieren (Format: JFFFMMDD_hhmmss -> z.B. 20260926_075023)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);

        // Dateiname mit eingebautem Zeitstempel zusammenbauen
        String fileName = "procedural_world_32x32_" + timestamp + ".map";
        File targetFile = new File(selectedDirectory, fileName);

        System.out.println("Generiere Welt in: " + targetFile.getAbsolutePath() + " ... Bitte warten...");

        // 2. Eigentlicher Generierungsprozess
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            // Header schreiben
            writer.write("# GlobalTerrain Max-Compressed Export File - Format Version 2.6\n");
            writer.write("# Generated procedurally by GlobalTerrainGenerator\n");
            writer.write("DIMENSIONS_" + MAP_WIDTH + "_" + MAP_HEIGHT + "\n\n");

            // Schleife über alle Macro-Zellen
            for (int y = 0; y < MAP_HEIGHT; y++) {
                for (int x = 0; x < MAP_WIDTH; x++) {
                    generateMacroCell(writer, x, y);
                }
            }

            // Erfolgsmeldung als Pop-up und in der Konsole
            String successMessage = "Erfolg!\nDatei wurde erfolgreich gespeichert unter:\n" + targetFile.getAbsolutePath();
            System.out.println(successMessage);
            JOptionPane.showMessageDialog(null, successMessage, "Generierung abgeschlossen", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fehler beim Schreiben der Datei:\n" + e.getMessage(), "Export-Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void generateMacroCell(BufferedWriter writer, int cx, int cy) throws IOException {
        // Berechne die relative Distanz zum Kartenzentrum (für eine Insel-Struktur)
        double dx = (cx - (MAP_WIDTH / 2.0)) / (MAP_WIDTH / 2.0);
        double dy = (cy - (MAP_HEIGHT / 2.0)) / (MAP_HEIGHT / 2.0);
        double distFromCenter = Math.sqrt(dx * dx + dy * dy);

        // Bestimme die Höhe (0 bis 1800 Meter) basierend auf der Entfernung zum Zentrum
        double rawElevation = (1.0 - distFromCenter) * 1600.0 + Math.sin(cx * 0.5) * 150.0;
        double elevation = Math.max(0.0, Math.min(2000.0, rawElevation));

        // Bestimme den Zonentyp (BASE_COVER)
        String baseCover = "OCEAN";
        if (elevation > 1200) baseCover = "MOUNTAIN";
        else if (elevation > 400) baseCover = "FOREST";
        else if (elevation > 50) baseCover = "PLAINS";
        else if (elevation > 0) baseCover = "COAST";

        writer.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
        writer.write("  ELEVATION:" + String.format(java.util.Locale.US, "%.1f", elevation) + "\n");
        writer.write("  BASE_COVER:" + baseCover + "\n");

        // --- EBENE A: MESO-LAYER GENERIERUNG ---
        writer.write("  MESO_LAYER {\n");
        List<String> allMesoTokens = new ArrayList<>();
        
        String terrainToken = "WA"; // Wasser
        String baumToken = "KE";    // Kein Baum
        
        if (baseCover.equals("MOUNTAIN")) {
            terrainToken = (elevation > 1500) ? "SN" : "RK"; 
        } else if (baseCover.equals("FOREST")) {
            terrainToken = "FO"; 
            baumToken = (cx % 2 == 0) ? "FI" : "BU"; 
        } else if (baseCover.equals("PLAINS")) {
            terrainToken = "GR"; 
            if ((cx + cy) % 5 == 0) baumToken = "EI"; 
        } else if (baseCover.equals("COAST")) {
            terrainToken = "SA"; 
        }

        for (int i = 0; i < MESO_SIZE * MESO_SIZE; i++) {
            allMesoTokens.add(terrainToken + "-" + baumToken);
        }
        writer.write("    " + compressTokenList(allMesoTokens) + "\n");
        writer.write("  }\n");

        // --- EBENE B: SHRUB_SCHABLONE ---
        writer.write("  SHRUB_SCHABLONE {\n");
        if (baseCover.equals("PLAINS") || baseCover.equals("FOREST")) {
            writer.write("    shrub_40_40(");
            List<String> shrubTokens = new ArrayList<>();
            String shrubType = baseCover.equals("FOREST") ? "S_FA" : "S_ZI"; 
            shrubTokens.add(shrubType);
            for (int i = 1; i < MIKRO_SIZE * MIKRO_SIZE; i++) {
                shrubTokens.add("S_KE"); 
            }
            writer.write(compressTokenList(shrubTokens) + ")\n");
        }
        writer.write("  }\n");

        // --- EBENE C: TERRAIN_DELTA ---
        writer.write("  TERRAIN_DELTA {\n");
        if (baseCover.equals("COAST") && cx % 4 == 0) {
            writer.write("    M->3:0,1,2,3,4,5,6,7,8\n"); 
        }
        writer.write("  }\n");
        
        writer.write("}\n\n");
    }

    private static String compressTokenList(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < tokens.size()) {
            String current = tokens.get(i);
            int count = 1;
            while (i + 1 < tokens.size() && tokens.get(i + 1).equals(current)) {
                count++;
                i++;
            }
            if (count > 1) sb.append("{").append(current).append("}").append(count);
            else sb.append(current);
            if (i < tokens.size() - 1) sb.append(",");
            i++;
        }
        return sb.toString();
    }
}
