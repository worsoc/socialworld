package org.socialworld.tools.mct;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import org.socialworld.tools.mct.TileGeometry.NeighbourAnalyzerTile;

public class MapNeighbourAnalyzer {

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Verzeichnis mit 'onlyLs'-Dateien auswählen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Vorgang abgebrochen.");
            return;
        }

        File selectedDir = chooser.getSelectedFile();
        File[] files = selectedDir.listFiles((dir, name) -> name.contains("onlyLs"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null, "Keine Dateien mit 'onlyLs' im Namen gefunden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Maps zur Gruppierung: Key = "[h1, h2, ...]", Value = Liste von Dateinamen
        Map<String, List<String>> northBorders = new LinkedHashMap<>();
        Map<String, List<String>> eastBorders = new LinkedHashMap<>();
        Map<String, List<String>> southBorders = new LinkedHashMap<>();
        Map<String, List<String>> westBorders = new LinkedHashMap<>();

        for (File file : files) {
            try {
                String content = Files.readString(file.toPath());
                List<NeighbourAnalyzerTile> grid = TileGeometry.parseGrid(content);

                if (grid.size() != 81) {
                    System.err.println("Warnung: " + file.getName() + " übersprungen (Kachelanzahl " + grid.size() + " != 81).");
                    continue;
                }

                String fileName = file.getName();

                // Ränder berechnen und als String-Key formatieren
                String northKey = Arrays.toString(BorderCalculator.calculateNorth(grid));
                String eastKey  = Arrays.toString(BorderCalculator.calculateEast(grid));
                String southKey = Arrays.toString(BorderCalculator.calculateSouth(grid));
                String westKey  = Arrays.toString(BorderCalculator.calculateWest(grid));

                // Dateinamen in die jeweiligen Maps einsortieren
                northBorders.computeIfAbsent(northKey, k -> new ArrayList<>()).add(fileName);
                eastBorders.computeIfAbsent(eastKey, k -> new ArrayList<>()).add(fileName);
                southBorders.computeIfAbsent(southKey, k -> new ArrayList<>()).add(fileName);
                westBorders.computeIfAbsent(westKey, k -> new ArrayList<>()).add(fileName);

            } catch (Exception e) {
                System.err.println("Fehler bei Datei " + file.getName() + ": " + e.getMessage());
            }
        }

        File outputFile = new File(selectedDir, "analyzer_results.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            writeSection(writer, "--- NORDRÄNDER (West -> Ost) ---", northBorders);
            writeSection(writer, "--- OSTRÄNDER (Nord -> Süd) ---", eastBorders);
            writeSection(writer, "--- SÜDRÄNDER (West -> Ost) ---", southBorders);
            writeSection(writer, "--- WESTRÄNDER (Nord -> Süd) ---", westBorders);

            JOptionPane.showMessageDialog(null, "Analyse beendet!\nErgebnisse unter:\n" + outputFile.getAbsolutePath(), "Erfolg", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Schreibfehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void writeSection(BufferedWriter writer, String header, Map<String, List<String>> borderMap) throws IOException {
        writer.write(header);
        writer.newLine();
        for (Map.Entry<String, List<String>> entry : borderMap.entrySet()) {
            writer.write(entry.getKey() + " -> " + entry.getValue().toString());
            writer.newLine();
        }
        writer.newLine();
    }
}
