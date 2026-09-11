package org.socialworld.tools.mct;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class MapSkeletonFiller {

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        System.out.println("=== Starte ecken- und richtungskonformen Pipeline-Test ===");

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

            // --- 1. SCHRITT: Nachbar-Ränder mit Ecken-Prüfung ziehen ---
            System.out.println("Wähle zufällige Nachbar-Randbedingungen aus...");
            int[] neighbourNorth = null;
            int[] neighbourEast  = null;
            int[] neighbourSouth = null;
            int[] neighbourWest  = null;

            Random rand = new Random();
            
            // Schleife läuft so lange, bis mindestens eine Nachbarbedingung aktiv ist 
            // UND die gewürfelten Ecken mathematisch zueinander passen
            int gesamtVersuche = 0;
            while (neighbourNorth == null && neighbourEast == null && neighbourSouth == null && neighbourWest == null && gesamtVersuche < 1000) {
                gesamtVersuche++;
                
                // 1. NORDEN: Frei würfeln
                if (!data.northBorders.isEmpty() && rand.nextBoolean()) {
                    neighbourNorth = getSingleRandomBorder(data.northBorders);
                }

                // 2. OSTEN: Muss zum Nordrand passen
                if (!data.eastBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherOst = getSingleRandomBorder(data.eastBorders);
                    if (neighbourNorth == null || moeglicherOst[0] == neighbourNorth[neighbourNorth.length - 1]) {
                        neighbourEast = moeglicherOst;
                    }
                }

                // 3. SÜDEN: Muss zum Ostrand passen
                if (!data.southBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherSued = getSingleRandomBorder(data.southBorders);
                    if (neighbourEast == null || moeglicherSued[moeglicherSued.length - 1] == neighbourEast[neighbourEast.length - 1]) {
                        neighbourSouth = moeglicherSued;
                    }
                }

                // 4. WESTEN: Muss zum Nordrand (oben) und Südrand (unten) passen
                if (!data.westBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherWest = getSingleRandomBorder(data.westBorders);
                    boolean passtOben = (neighbourNorth == null || moeglicherWest[0] == neighbourNorth[0]);
                    boolean passtUnten = (neighbourSouth == null || moeglicherWest[moeglicherWest.length - 1] == neighbourSouth[0]);
                    
                    if (passtOben && passtUnten) {
                        neighbourWest = moeglicherWest;
                    }
                }
            }

            // Notfall-Greifer, falls der Zufall uns nur null geliefert hat
            if (neighbourNorth == null && neighbourEast == null && neighbourSouth == null && neighbourWest == null) {
                if (!data.northBorders.isEmpty()) neighbourNorth = getSingleRandomBorder(data.northBorders);
            }

 
            System.out.println("\n=== GELADENE NACHBAR-PROFILE ===");
            System.out.println("-> Nordrand der Nachbarkarte: " + Arrays.toString(neighbourNorth));
            System.out.println("-> Ostrand der Nachbarkarte:  " + Arrays.toString(neighbourEast));
            System.out.println("-> Südrand der Nachbarkarte:  " + Arrays.toString(neighbourSouth));
            System.out.println("-> Westrand der Nachbarkarte: " + Arrays.toString(neighbourWest));

            // --- 2. SCHRITT: Kreuzweise Übergabe an den Generator ---
            int[] targetNorth = neighbourSouth; // Südrand der südlichen Karte wird unser Nordrand (Reihe 0)
            int[] targetSouth = neighbourNorth; // Nordrand der nördlichen Karte wird unser Südrand (Reihe 8)
            int[] targetWest  = neighbourEast;  // Ostrand der westlichen Karte wird unser Westrand (Spalte 0)
            int[] targetEast  = neighbourWest;  // Westrand der östlichen Karte wird unser Ostrand (Spalte 8)

            System.out.println("\n=== KREUZWEISE ZUORDNUNG FÜR DEINE NEUE SCHABLONE ===");
            System.out.println("-> Eigener Nordrand (Reihe 0)  <- geladen von Nachbar-Südrand: " + Arrays.toString(targetNorth));
            System.out.println("-> Eigener Ostrand  (Spalte 8) <- geladen von Nachbar-Westrand: " + Arrays.toString(targetEast));
            System.out.println("-> Eigener Südrand  (Reihe 8)  <- geladen von Nachbar-Nordrand: " + Arrays.toString(targetSouth));
            System.out.println("-> Eigener Westrand (Spalte 0) <- geladen von Nachbar-Ostrand:  " + Arrays.toString(targetWest));

            System.out.println("\nGeneriere richtungskorrekten Skeleton-String...");
            String skeletonString = MapSkeletonGenerator.generateSkeleton(
                targetNorth, 
                targetEast, 
                targetSouth, 
                targetWest
            );

            System.out.println("Übergebe Schablone an MapCreationTool.fillSkeleton()...");
            MapCreationTool.fillSkeleton(skeletonString);

            System.out.println("=== Testlauf erfolgreich beendet! ===");

        } catch (Exception e) {
            System.err.println("Fehler während des Testlaufs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int[] getSingleRandomBorder(Map<String, List<String>> borderMap) {
        if (borderMap == null || borderMap.isEmpty()) return null;
        List<String> keys = new ArrayList<>(borderMap.keySet());
        return parseStringToIntArray(keys.get(new Random().nextInt(keys.size())));
    }

    private static int[] parseStringToIntArray(String arrayStr) {
        String clean = arrayStr.replace("[", "").replace("]", "").replace(" ", "");
        if (clean.trim().isEmpty()) return new int[0];
        String[] tokens = clean.split(",");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i]);
        }
        return result;
    }
}
