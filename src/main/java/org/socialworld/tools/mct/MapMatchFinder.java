package org.socialworld.tools.mct;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MapMatchFinder {

    // Repräsentiert eine einzelne Map mit ihren 4 Höhenprofilen
    public static class MapsHeightLines {
        String name;
        int[] north;
        int[] east;
        int[] south;
        int[] west;

        public MapsHeightLines(String name) {
            this.name = name;
        }
    }

    /**
     * Normiert eine Höhenlinie, sodass sie immer bei 0 beginnt.
     */
    private static int[] normalize(int[] profile) {
        if (profile == null || profile.length == 0) return null;
        int[] normalized = new int[profile.length];
        int baseValue = profile[0];
        for (int i = 0; i < profile.length; i++) {
            normalized[i] = profile[i] - baseValue;
        }
        return normalized;
    }

    /**
     * Prüft, ob zwei Profile nach der Normierung exakt übereinstimmen.
     */
    private static boolean matches(int[] profileA, int[] profileB) {
        if (profileA == null || profileB == null) return false;
        return Arrays.equals(normalize(profileA), normalize(profileB));
    }

    public static void main(String[] args) {
        // 1. Dateiauswahl-Dialog initialisieren
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei mit Höhenlinien auswählen");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdateien (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(null);
        
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Auswahl abgebrochen. Programm wird beendet.");
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();
        System.out.println("Lese Datei ein: " + selectedFile.getName() + "\n");

        Map<String, MapsHeightLines> mapRegistry = new HashMap<>();

        // 2. Datei einlesen
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentDirection = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.contains("NORDRÄNDER")) { currentDirection = "N"; continue; }
                if (line.contains("OSTRÄNDER")) { currentDirection = "E"; continue; }
                if (line.contains("SÜDRÄNDER")) { currentDirection = "S"; continue; }
                if (line.contains("WESTRÄNDER")) { currentDirection = "W"; continue; }

                if (line.startsWith("[")) {
                    String[] parts = line.split("->");
                    if (parts.length < 2) continue;

                    String arrayContent = parts[0].replace("[", "").replace("]", "").trim();
                    String[] numStrings = arrayContent.split(",");
                    int[] profile = new int[numStrings.length];
                    for (int i = 0; i < numStrings.length; i++) {
                        profile[i] = Integer.parseInt(numStrings[i].trim());
                    }

                    String filesContent = parts[1].replace("[", "").replace("]", "").trim();
                    String[] fileNames = filesContent.split(",");

                    for (String fileName : fileNames) {
                        String cleanName = fileName.trim();
                        if (cleanName.isEmpty()) continue;

                        mapRegistry.putIfAbsent(cleanName, new MapsHeightLines(cleanName));
                        MapsHeightLines tile = mapRegistry.get(cleanName);

                        switch (currentDirection) {
                            case "N" -> tile.north = profile;
                            case "E" -> tile.east = profile;
                            case "S" -> tile.south = profile;
                            case "W" -> tile.west = profile;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
            return;
        }

        // 3. Kombinations-Übersicht generieren
        System.out.println("=================================================");
        System.out.println("   ÜBERSICHT: MÖGLICHE MAP-ANORDNUNGEN (9x9)     ");
        System.out.println("   (Es werden nur Maps mit Treffern angezeigt)   ");
        System.out.println("=================================================\n");

        List<MapsHeightLines> allTiles = new ArrayList<>(mapRegistry.values());

        for (MapsHeightLines tileA : allTiles) {
            List<String> connections = new ArrayList<>();

            for (MapsHeightLines tileB : allTiles) {
                // Nord -> Süd Match (Wenn tileA NÖRDLICH von tileB platziert wird)
                if (matches(tileA.south, tileB.north)) {
                    connections.add("  -> SÜDLICH anlegbar: " + tileB.name + " (an deren NORD-Kante)");
                }
                
                // Süd -> Nord Match (Wenn tileA SÜDLICH von tileB platziert wird)
                if (matches(tileA.north, tileB.south)) {
                    connections.add("  -> NÖRDLICH anlegbar: " + tileB.name + " (an deren SÜD-Kante)");
                }

                // Ost -> West Match (Wenn tileA WESTLICH von tileB platziert wird)
                if (matches(tileA.east, tileB.west)) {
                    connections.add("  -> ÖSTLICH anlegbar: " + tileB.name + " (an deren WEST-Kante)");
                }

                // West -> Ost Match (Wenn tileA ÖSTLICH von tileB platziert wird)
                if (matches(tileA.west, tileB.east)) {
                    connections.add("  -> WESTLICH anlegbar: " + tileB.name + " (an deren OST-Kante)");
                }
            }

            // Die Ausgabe für diese Map erfolgt NUR, wenn die Liste nicht leer ist
            if (!connections.isEmpty()) {
                System.out.println("Map: " + tileA.name);
                connections.forEach(System.out::println);
                System.out.println(); // Leerzeile als Abstand zur nächsten Map
            }
        }
    }
}
