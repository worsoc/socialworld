package org.socialworld.tools.mct;

import java.io.File;
import java.util.*;
import javax.swing.*;

public class MapSkeletonFiller {


    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        System.out.println("=== Starte automatischen Pipeline-Test (MapSkeletonFiller) ===");

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

            // --- DIAGNOSE-CHECK ---
            System.out.println("\n=== GELADENE DATENSÄTZE ===");
            System.out.println("Nordränder gefunden: " + data.northBorders.size());
            System.out.println("Ostränder gefunden:  " + data.eastBorders.size());
            System.out.println("Südränder gefunden:  " + data.southBorders.size());
            System.out.println("Westränder gefunden: " + data.westBorders.size());
            System.out.println("===========================\n");

            // Wenn überhaupt nichts geladen wurde, brechen wir sofort ab statt endlos zu laufen
            if (data.northBorders.isEmpty() && data.eastBorders.isEmpty() && 
                data.southBorders.isEmpty() && data.westBorders.isEmpty()) {
                System.err.println("Abbruch: Die Maps sind leer. Bitte prüfe, ob das Format der Datei mit dem Reader übereinstimmt!");
                return;
            }

            // 3. Randbedingungen ziehen (mit Ecken-Harmonisierung!)
            System.out.println("Wähle zufällige, zueinander passende Randbedingungen aus...");
            
            java.util.Random rand = new java.util.Random();
            int[] randomNorth = null;
            int[] randomEast  = null;
            int[] randomSouth = null;
            int[] randomWest  = null;

            // Wir würfeln so lange, bis wir eine gültige Kombination haben
            int gesamtVersuche = 0;
            while (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null && gesamtVersuche < 1000) {
                gesamtVersuche++;
                
                // 1. NORDEN: Frei würfeln (50% Chance)
                if (!data.northBorders.isEmpty() && rand.nextBoolean()) {
                    randomNorth = getSingleRandomBorder(data.northBorders);
                }

                // 2. OSTEN: Muss zum Nordrand passen, falls dieser existiert
                if (!data.eastBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherOst = getSingleRandomBorder(data.eastBorders);
                    if (randomNorth == null || moeglicherOst[0] == randomNorth[9]) {
                        randomEast = moeglicherOst;
                    }
                }

                // 3. SÜDEN: Muss zum Ostrand passen, falls dieser existiert
                if (!data.southBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherSued = getSingleRandomBorder(data.southBorders);
                    if (randomEast == null || moeglicherSued[9] == randomEast[9]) {
                        randomSouth = moeglicherSued;
                    }
                }

                // 4. WESTEN: Muss zum Nordrand (oben) und Südrand (unten) passen
                if (!data.westBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherWest = getSingleRandomBorder(data.westBorders);
                    boolean passtOben = (randomNorth == null || moeglicherWest[0] == randomNorth[0]);
                    boolean passtUnten = (randomSouth == null || moeglicherWest[9] == randomSouth[0]);
                    
                    if (passtOben && passtUnten) {
                        randomWest = moeglicherWest;
                    }
                }
            }

            // Notfall-Greifer: Falls der Zufall uns komplett im Stich gelassen hat, 
            // erzwingen wir einfach fest den Nordrand, um überhaupt Daten zu haben
            if (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null) {
                if (!data.northBorders.isEmpty()) randomNorth = getSingleRandomBorder(data.northBorders);
            }

 
            System.out.println("-> Gewählter Nordrand: " + Arrays.toString(randomNorth));
            System.out.println("-> Gewählter Ostrand:  " + Arrays.toString(randomEast));
            System.out.println("-> Gewählter Südrand:  " + Arrays.toString(randomSouth));
            System.out.println("-> Gewählter Westrand: " + Arrays.toString(randomWest));

            System.out.println("Generiere Skeleton-String...");
            String skeletonString = MapSkeletonGenerator.generateSkeleton(
                randomNorth, randomEast, randomSouth, randomWest
            );

            System.out.println("Übergebe Schablone an MapCreationTool.fillSkeleton()...");
            MapCreationTool.fillSkeleton(skeletonString);

            System.out.println("=== Testlauf erfolgreich beendet! ===");

        } catch (Exception e) {
            System.err.println("Fehler während des Testlaufs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Hilfsmethode: Holt jetzt strikt einen zufälligen Eintrag aus der Map (ohne eigene 50/50 Chance).
     */
    private static int[] getSingleRandomBorder(Map<String, List<String>> borderMap) {
        if (borderMap == null || borderMap.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        List<String> keys = new ArrayList<>(borderMap.keySet());
        String randomKey = keys.get(rand.nextInt(keys.size()));
        return parseStringToIntArray(randomKey);
    }

    /**
     * Hilfsmethode: Wählt zufällig einen Eintrag aus einer Border-Map.
     * Entscheidet zudem per Zufall (50% Chance), ob die Einschränkung aktiv ist oder leer (null) bleibt.
     */
    private static int[] getRandomBorderArray(Map<String, List<String>> borderMap) {
        if (borderMap == null || borderMap.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        
        // 50% Chance, dass diese Himmelsrichtung für den Test offen bleibt (null)
        if (rand.nextBoolean()) {
            return null; 
        }

        // Alle verfügbaren String-Keys ([0, 0, ...]) in eine Liste packen
        List<String> keys = new ArrayList<>(borderMap.keySet());
        // Zufälligen Key auswählen
        String randomKey = keys.get(rand.nextInt(keys.size()));

        // Den String zurück in ein int[] konvertieren
        return parseStringToIntArray(randomKey);
    }

    /**
     * Konvertiert einen String im Format "[0, -1, 2, ...]" zurück in ein echtes int[]
     */
    private static int[] parseStringToIntArray(String arrayStr) {
        String clean = arrayStr.replace("[", "").replace("]", "").replace(" ", "");
        if (clean.trim().isEmpty()) {
            return new int[0];
        }
        
        String[] tokens = clean.split(",");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i]);
        }
        return result;
    }
    
 
}
