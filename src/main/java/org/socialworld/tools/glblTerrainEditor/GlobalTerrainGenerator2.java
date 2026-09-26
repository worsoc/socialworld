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

public class GlobalTerrainGenerator2 {

    private static final int MAP_WIDTH = 32;
    private static final int MAP_HEIGHT = 32;
    private static final int MESO_SIZE = 81;
    private static final int MIKRO_SIZE = 9;

 
	 // Zufällige Offsets für die Saat (Seed)
	 private static double seedOffsetX = 0;
	 private static double seedOffsetY = 0;
   
    public static void main(String[] args) {
 
        // Seed initialisieren: Erzeugt zufällige Verschiebungen zwischen -10000 und +10000
        java.util.Random rand = new java.util.Random();
        seedOffsetX = (rand.nextDouble() * 20000.0) - 10000.0;
        seedOffsetY = (rand.nextDouble() * 20000.0) - 10000.0;

    	// 1. Hole zuerst das Eclipse-Arbeitsverzeichnis
    	String workingDir = System.getProperty("user.dir");
    	File defaultDir = new File(workingDir);

    	// 2. Übergib den Pfad DIREKT in den Konstruktor (Verhindert den Sortier-Bug!)
    	JFileChooser folderChooser;
    	if (defaultDir.exists()) {
    	    folderChooser = new JFileChooser(defaultDir); 
    	} else {
    	    folderChooser = new JFileChooser();
    	}

    	// 3. Konfiguriere danach die restlichen Optionen
    	folderChooser.setDialogTitle("Standardverzeichnis für Generierung wählen");
    	folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 

    	// 4. Dialog anzeigen
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
        // 1. Berechne die durchschnittliche Macro-Höhe für den Header (wird für BASE_COVER benötigt)
        double macroDx = (cx - (MAP_WIDTH / 2.0)) / (MAP_WIDTH / 2.0);
        double macroDy = (cy - (MAP_HEIGHT / 2.0)) / (MAP_HEIGHT / 2.0);
        double macroDist = Math.sqrt(macroDx * macroDx + macroDy * macroDy);
        double macroElevation = Math.max(0.0, (1.0 - macroDist) * 1500.0);
        
        String baseCover = "OCEAN";
        if (macroElevation > 1100) baseCover = "MOUNTAIN";
        else if (macroElevation > 400) baseCover = "FOREST";
        else if (macroElevation > 50) baseCover = "PLAINS";
        else if (macroElevation > 0) baseCover = "COAST";

        writer.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
        writer.write("  ELEVATION:" + String.format(java.util.Locale.US, "%.1f", macroElevation) + "\n");
        writer.write("  BASE_COVER:" + baseCover + "\n");

        // --- EBENE A: DETAIL-GENERIERUNG AUF MESO-EBENE (81x81) ---
        writer.write("  MESO_LAYER {\n");
        List<String> allMesoTokens = new ArrayList<>();
        
        // Listen für dynamische Sträucher merken, um sie später in Ebene B zu schreiben
        List<String> activeShrubLines = new ArrayList<>();

        for (int my = 0; my < MESO_SIZE; my++) {
            for (int mx = 0; mx < MESO_SIZE; mx++) {
            	// Absolute, hochaufgelöste Weltkoordinate für dieses spezifische Meso-Feld berechnen
            	double worldX = cx * MESO_SIZE + mx;
            	double worldY = cy * MESO_SIZE + my;

            	// Globale Distanz zum Kartenzentrum berechnen (Bleibt unberührt für die Inselform!)
            	double totalMesoSize = MAP_WIDTH * MESO_SIZE;
            	double dx = (worldX - (totalMesoSize / 2.0)) / (totalMesoSize / 2.0);
            	double dy = (worldY - (totalMesoSize / 2.0)) / (totalMesoSize / 2.0);
            	double dist = Math.sqrt(dx * dx + dy * dy);

            	// 1. Verwende Multiplikatoren statt reiner Addition, um die Frequenzen völlig zu verdrehen
            	double noiseX1 = worldX * 0.04 + (seedOffsetX * 0.013);
            	double noiseY1 = worldY * 0.04 + (seedOffsetY * 0.017);

            	double noiseX2 = worldX * 0.15 - (seedOffsetY * 0.023); // X und Y überkreuzen für maximale Asymmetrie
            	double noiseY2 = worldY * 0.12 + (seedOffsetX * 0.029);

            	double noiseX3 = worldX * 0.45 + (seedOffsetX * 0.071);
            	double noiseY3 = worldY * 0.55 - (seedOffsetY * 0.083);

            	// 2. Fraktaler Noise mit den entkoppelten Frequenz-Koordinaten füttern
            	double noise1 = Math.sin(noiseX1) * Math.cos(noiseY1) * 300.0; // Große Hügel
            	double noise2 = Math.sin(noiseX2) * Math.sin(noiseY2) * 80.0;  // Mittlere Strukturen
            	double noise3 = Math.cos(noiseX3) * Math.cos(noiseY3) * 15.0;  // Kleines Detail


              	// 3. Insel-Einfluss leicht lockern, damit Täler und Buchten tief einschneiden können
            	double cellElevation = (1.1 - dist) * 1200.0 + noise1 + noise2 + noise3;
            	cellElevation = Math.max(0.0, cellElevation);
            	
                // Detailreiches Terrain-Slicer basierend auf der exakten Meso-Höhe
                String terrainToken;
                String baumToken = "KE";

                if (cellElevation == 0) {
                    // Feine Abstufung im Wasser
                    terrainToken = (dist > 0.85) ? "WA" : "SW"; // Tiefwasser außen, seichtes Wasser innen
                } else if (cellElevation < 45) {
                    terrainToken = "SA"; // Sandstrand
                } else if (cellElevation < 400) {
                    terrainToken = "GR"; // Grasland / Wiese
                    // Flussbett-Simulation per mathematischer Frequenz (Zufälliger geschwungener Pfad)
                    if (Math.abs(Math.sin(worldX * 0.02 + worldY * 0.01) - 0.1) < 0.015 && cellElevation < 250) {
                        terrainToken = "MU"; // Matsch/Flussbett an Wiesen-Flüssen
                    } else if ((worldX + worldY) % 57 == 0) {
                        baumToken = "EI"; // Einzelne Eichen im Grasland
                    }
                } else if (cellElevation < 1000) {
                    terrainToken = "FO"; // Waldboden
                    // Mischwald-Generierung basierend auf lokalem Noise
                    baumToken = (Math.sin(worldX * 0.5) > 0) ? "FI" : "BU"; // Fichten- und Buchencluster
                } else if (cellElevation < 1400) {
                    // Übergangszone zum Hochgebirge
                    terrainToken = (Math.cos(worldY * 0.3) > 0) ? "ST" : "RK"; // Geröll oder Fels
                    if (cellElevation < 1150 && Math.sin(worldX * 0.2) > 0.3) {
                        baumToken = "KI"; // Kiefern überleben an Berghängen
                    }
                } else {
                    // Hochgebirge
                    terrainToken = (cellElevation > 1550) ? "IC" : "SN"; // Eisgletscher auf den höchsten Spitzen, sonst Schnee
                }

                allMesoTokens.add(terrainToken + "-" + baumToken);

                // --- EBENE B VORBEREITUNG (Dynamische Sträucher) ---
                // Farne wachsen im Wald (FO), Heidekraut wächst in steinigen Höhen (ST)
                if (terrainToken.equals("FO") && (worldX * worldY) % 131 == 0) {
                    activeShrubLines.add("    shrub_" + mx + "_" + my + "(S_FA,{S_KE}72)\n");
                } else if (terrainToken.equals("ST") && (worldX + worldY) % 97 == 0) {
                    activeShrubLines.add("    shrub_" + mx + "_" + my + "(S_HE,{S_KE}72)\n");
                }
            }
        }
        
        writer.write("    " + compressTokenList(allMesoTokens) + "\n");
        writer.write("  }\n");

        // --- EBENE B: SHRUB_SCHABLONE SCHREIBEN ---
        writer.write("  SHRUB_SCHABLONE {\n");
        for (String shrubLine : activeShrubLines) {
            writer.write(shrubLine);
        }
        writer.write("  }\n");

        // --- EBENE C: TERRAIN_DELTA ---
        writer.write("  TERRAIN_DELTA {\n");
        // Optionale Mikro-Varianz (z. B. kleine Steine im Gras einstreuen)
        if (baseCover.equals("PLAINS") && (cx + cy) % 3 == 0) {
            writer.write("    M[" + (cx % 3 * 20) + "," + (cy % 3 * 20) + "]->5:0,1,2,9,10,11\n"); // Kleine Felsbrocken per Delta
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
