package org.socialworld.tools.mct;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class MapNeighbourAnalyzer {

	public static File sourceDirectory;
	
	public static void main(String[] args) {
	    // Swing-Inhalte immer auf dem Event Dispatch Thread ausführen
	    SwingUtilities.invokeLater(() -> {
	        
	        // Optionen für den Benutzer definieren
	        String[] options = {
	            "1. 'onlyLs'-Dateien analysieren", 
	            "2. Suchraum reduzieren & Dateien kopieren"
	        };

	        // Dialog zur Auswahl anzeigen
	        int choice = JOptionPane.showOptionDialog(
	                null,
	                "Welche Aktion möchten Sie ausführen?",
	                "Modus auswählen",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                options,
	                options[0]
	        );

	        // Je nach Auswahl die entsprechende Methode starten
	        if (choice == 0) {
	            System.out.println("Starte Analyse der 'onlyLs'-Dateien...");
	            createAnalyzeResult();
	        } else if (choice == 1) {
	            System.out.println("Starte Suchraum-Reduzierung...");
	            chooseMapFiles();
	        } else {
	            System.out.println("Vorgang abgebrochen.");
	        }
	    });
	}

    private static void createAnalyzeResult() {
 
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
                List<VisualTile> grid = parseGrid(content);

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
    
    private static List<VisualTile> parseGrid(String content) {
        List<VisualTile> tiles = new ArrayList<>();
        Matcher m = Pattern.compile("L_(-?\\d+)_(-?\\d+)").matcher(content);
        while (m.find()) {
            int type = Integer.parseInt(m.group(1));
            int baseHeight = Integer.parseInt(m.group(2));
            tiles.add(new VisualTile(type, baseHeight));
        }
        return tiles;
    }

    
 
 
    public static void chooseMapFiles() {
        // Falls das Verzeichnis noch nicht gesetzt wurde, kurz abfragen
        if (sourceDirectory == null) {
            JFileChooser pathChooser = new JFileChooser();
            pathChooser.setDialogTitle("Bitte den Ordner Ihrer 'onlyLs'-Dateien auswählen (Ort der matches_result.txt)");
            pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (pathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                sourceDirectory = pathChooser.getSelectedFile();
            } else {
                return;
            }
        }

        File indexFile = new File(sourceDirectory, "matches_result.txt");
        if (!indexFile.exists()) {
            JOptionPane.showMessageDialog(null, "Die Datei 'matches_result.txt' wurde im Quellordner nicht gefunden.\nBitte führen Sie zuerst die Match-Analyse aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Integer> fileCounts = new HashMap<>();
        // Regex sucht nach dem typischen Dateinamen-Muster
        Pattern pattern = Pattern.compile("onlyLs_[a-zA-Z0-9_]+\\.txt");

        try {
            List<String> lines = Files.readAllLines(indexFile.toPath());
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                // Jedes Mal, wenn ein Dateiname in der Datei auftaucht (egal ob als Haupt-Map oder als Partner), 
                // erhöht das dessen Konnektivitäts-Wert.
                while (matcher.find()) {
                    String fileName = matcher.group();
                    fileCounts.put(fileName, fileCounts.getOrDefault(fileName, 0) + 1);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Lesen der Match-Datei: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Absteigend nach Vorkommen/Matches sortieren
        List<Map.Entry<String, Integer>> sortedList = fileCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        // GUI Panel mit Checkboxen bauen
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        Map<JCheckBox, String> checkBoxMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : sortedList) {
            String text = String.format("%s (%d Match-Verbindungen)", entry.getKey(), entry.getValue());
            JCheckBox checkBox = new JCheckBox(text);
            // Standardmäßig alle vorauswählen, die eine hohe Konnektivität aufweisen
            checkBox.setSelected(entry.getValue() > 10); 
            checkBoxPanel.add(checkBox);
            checkBoxMap.put(checkBox, entry.getKey());
        }

        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setPreferredSize(new Dimension(500, 450));

        int result = JOptionPane.showConfirmDialog(
                null, 
                scrollPane, 
                "Dateien anhand der Match-Anzahl filtern", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );

        // Kopiervorgang bei Klick auf OK
        if (result == JOptionPane.OK_OPTION) {
            List<String> selectedFileNames = new ArrayList<>();
            for (Map.Entry<JCheckBox, String> entry : checkBoxMap.entrySet()) {
                if (entry.getKey().isSelected()) {
                    selectedFileNames.add(entry.getValue());
                }
            }

            if (selectedFileNames.isEmpty()) {
                return;
            }

            // Zielverzeichnis auswählen (Im schönen Java-Standard-Look)
            JFileChooser dirChooser = new JFileChooser();
            dirChooser.setDialogTitle("Zielverzeichnis für den reduzierten Suchraum wählen");
            dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (dirChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File targetDir = dirChooser.getSelectedFile();
                int successCount = 0;

                for (String fileName : selectedFileNames) {
                    File srcFile = new File(sourceDirectory, fileName);
                    File destFile = new File(targetDir, fileName);

                    if (srcFile.exists()) {
                        try {
                            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            successCount++;
                        } catch (IOException e) {
                            System.err.println("Fehler bei " + fileName + ": " + e.getMessage());
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, 
                        String.format("Erfolgreich %d Dateien kopiert!\nIhr neuer Suchraum ist bereit.", successCount),
                        "Kopiervorgang beendet", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

   
    
}
