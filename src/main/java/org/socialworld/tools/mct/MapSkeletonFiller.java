package org.socialworld.tools.mct;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class MapSkeletonFiller {

    public static void main(String[] args) {
        // Look and Feel an das Betriebssystem anpassen
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        System.out.println("=== Starte automatischen Pipeline-Test (MapSkeletonFiller) ===");

        // --- NEU: MODUS-ABFRAGE BEIM START ---
        String[] options = {"1 Sektor (Zufall via txt)", "3x3 Verbund (Testfeld)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welchen Test-Modus möchtest du starten?",
                "Pipeline Test-Modus wählen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Falls der Dialog geschlossen oder abgebrochen wurde
        if (choice == JOptionPane.CLOSED_OPTION) {
            System.out.println("Vorgang abgebrochen: Kein Modus ausgewählt.");
            return;
        }

        // --- ENTSCHEIDUNG AUSFÜHREN ---
        if (choice == 0) {
            // ALTER MODUS: Einzelner Zufallssektor aus der analyzer_results.txt
            runSingleRandomSectorMode();
        } else {
            // NEUER MODUS: 9 zusammenhängende Sektoren am Stück generieren
            runMegaGridVerbundMode();
        }
    }

    /**
     * MODUS 1: Liest die analyzer_results.txt ein und würfelt genau einen, ecken-sicheren Rand.
     */
    private static void runSingleRandomSectorMode() {
        System.out.println("-> Modus aktiv: Einzelner Sektor aus Datei");
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Wähle die 'analyzer_results.txt' aus");
        chooser.setCurrentDirectory(new File("."));
        
        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Vorgang abgebrochen: Keine Analysedatei ausgewählt.");
            return;
        }

        File resultFile = chooser.getSelectedFile();

        try {
            System.out.println("Lese '" + resultFile.getName() + "' ein...");
            ResultFileReader.AnalysisResult data = ResultFileReader.readResults(resultFile);

            Random rand = new java.util.Random();
            int[] randomNorth = null;
            int[] randomEast  = null;
            int[] randomSouth = null;
            int[] randomWest  = null;

            int gesamtVersuche = 0;
            while (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null && gesamtVersuche < 1000) {
                gesamtVersuche++;
                
                if (!data.northBorders.isEmpty() && rand.nextBoolean()) {
                    randomNorth = getSingleRandomBorder(data.northBorders);
                }
                if (!data.eastBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherOst = getSingleRandomBorder(data.eastBorders);
                    if (randomNorth == null || moeglicherOst[0] == randomNorth[9]) {
                        randomEast = moeglicherOst;
                    }
                }
                if (!data.southBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherSued = getSingleRandomBorder(data.southBorders);
                    if (randomEast == null || moeglicherSued[9] == randomEast[9]) {
                        randomSouth = moeglicherSued;
                    }
                }
                if (!data.westBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherWest = getSingleRandomBorder(data.westBorders);
                    boolean passtOben = (randomNorth == null || moeglicherWest[0] == randomNorth[0]);
                    boolean passtUnten = (randomSouth == null || moeglicherWest[9] == randomSouth[0]);
                    if (passtOben && passtUnten) {
                        randomWest = moeglicherWest;
                    }
                }
            }

            if (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null) {
                if (!data.northBorders.isEmpty()) randomNorth = getSingleRandomBorder(data.northBorders);
            }

            System.out.println("-> Gewählter Nordrand: " + Arrays.toString(randomNorth));
            System.out.println("-> Gewählter Ostrand:  " + Arrays.toString(randomEast));
            System.out.println("-> Gewählter Südrand:  " + Arrays.toString(randomSouth));
            System.out.println("-> Gewählter Westrand: " + Arrays.toString(randomWest));

            String skeletonString = MapSkeletonGenerator.generateSkeleton(randomNorth, randomEast, randomSouth, randomWest);
            MapCreationTool.fillSkeleton(skeletonString);

            System.out.println("=== Testlauf erfolgreich beendet! ===");

        } catch (Exception e) {
            System.err.println("Fehler während des Testlaufs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * MODUS 2: Generiert 9 mathematisch perfekt zusammenhängende Schablonen für das 3x3 MegaGrid-Panorama.
     */
    private static void runMegaGridVerbundMode() {
        System.out.println("-> Modus aktiv: 3x3 Sektoren-Verbund (Testfeld)");
/*
        // ABSOLUTER NULL-TEST: Alle Ränder sind zu 100% flach auf 0!
        // Horizontale Trennlinien (West -> Ost)
        int[] h0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0)
        int[] h1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Reihe 0 / Reihe 1
        int[] h2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Reihe 1 / Reihe 2
        int[] h3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz unten (Südrand Reihe 2)

        // Vertikale Trennlinien (Nord -> Süd)
        int[] v0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz links (Westrand Spalte 0)
        int[] v1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Spalte 0 / Spalte 1
        int[] v2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Spalte 1 / Spalte 2
        int[] v3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz rechts (Ostrand Spalte 2)
*/

         // ENTSPANNTE HÖHENWELLEN (0 und 1): Ecken an Index 0 und 9 sind perfekt synchronisiert!
        // Horizontale Trennlinien (West -> Ost)
        int[] h0 = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0)
        int[] h1 = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // Trennlinie Reihe 0 / Reihe 1
        int[] h2 = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // Trennlinie Reihe 1 / Reihe 2
        int[] h3 = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // Ganz unten (Südrand Reihe 2)

        // Vertikale Trennlinien (Nord -> Süd)
        int[] v0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz links (Westrand Spalte 0)
        int[] v1 = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // Trennlinie Spalte 0 / Spalte 1
        int[] v2 = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // Trennlinie Spalte 1 / Spalte 2
        int[] v3 = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // Ganz rechts (Ostrand Spalte 2)

 /*
        // Stufenweise ansteigende Höhenprofile (je 10 Punkte von West nach Ost)
        int[] h0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 
        int[] h1 = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1}; 
        int[] h2 = {0, 1, 1, 2, 2, 2, 2, 2, 1, 1}; 
        int[] h3 = {1, 1, 2, 2, 3, 3, 3, 2, 2, 2}; 

        // Vertikale Profile (je 10 Punkte von Nord nach Süd)
        int[] v0 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}; 
        int[] v1 = {0, 0, 1, 1, 1, 2, 2, 2, 2, 2}; 
        int[] v2 = {0, 1, 1, 2, 2, 2, 3, 3, 2, 2}; 
        int[] v3 = {0, 1, 1, 2, 2, 3, 3, 3, 3, 2}; 
*/
        String[][] filenames = {
            {"onlyLs_01_nordwest.txt", "onlyLs_02_norden.txt", "onlyLs_03_nordost.txt"},
            {"onlyLs_04_westen.txt",   "onlyLs_05_zentrum.txt", "onlyLs_06_osten.txt"},
            {"onlyLs_07_suedwest.txt", "onlyLs_08_sueden.txt", "onlyLs_09_suedost.txt"}
        };

        try {
            for (int sRow = 0; sRow < 3; sRow++) {
                for (int sCol = 0; sCol < 3; sCol++) {
                    System.out.println("\n--- Erzeuge Sektor [" + sRow + "][" + sCol + "]: " + filenames[sRow][sCol] + " ---");
 
                    int[] north = (sRow == 0) ? h0 : (sRow == 1) ? h1 : h2;
                    int[] south = (sRow == 0) ? h1 : (sRow == 1) ? h2 : h3;
                    int[] west  = (sCol == 0) ? v0 : (sCol == 1) ? v1 : v2;
                    int[] east  = (sCol == 0) ? v1 : (sCol == 1) ? v2 : v3;

 /*                   
                    int[] north = (sRow == 0) ? h0 : (sRow == 1) ? h1 : h2;
                    int[] east  = (sCol == 0) ? v1 : (sCol == 1) ? v2 : v3;
                    
                    // --- ENTLASTUNG: Süden und Westen auf 'null' setzen ---
                    // Dadurch docken die Sektoren nach Norden und Osten immer noch perfekt an,
                    // aber das Backtracking kriegt genug Luft zum Atmen!
                    int[] south = null; 
                    int[] west  = null; 
*/
                    String skeletonString = MapSkeletonGenerator.generateSkeleton(north, east, south, west);
                    System.out.println("\n" + skeletonString);
                   MapCreationTool.fillSkeleton(skeletonString, filenames[sRow][sCol]); 
                }
            }
            System.out.println("\n🟩 Alle 9 Verbund-Schablonen erfolgreich an das MapCreationTool übergeben!");

        } catch (Exception e) {
            System.err.println("Fehler bei der Verbund-Generierung: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int[] getSingleRandomBorder(Map<String, List<String>> borderMap) {
        if (borderMap == null || borderMap.isEmpty()) return null;
        List<String> keys = new ArrayList<>(borderMap.keySet());
        String randomKey = keys.get(new java.util.Random().nextInt(keys.size()));
        return parseStringToIntArray(randomKey);
    }

    private static int[] parseStringToIntArray(String borderKey) {
        if (borderKey.startsWith("[") && borderKey.endsWith("]")) {
            borderKey = borderKey.substring(1, borderKey.length() - 1);
        }
        String[] tokens = borderKey.split(",");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i].trim());
        }
        return result;
    }
}
