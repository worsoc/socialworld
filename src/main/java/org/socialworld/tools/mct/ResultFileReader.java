package org.socialworld.tools.mct;

/*
 * File datei = new File("C:/dein/pfad/analyzer_results.txt");
try {
    ResultFileReader.AnalysisResult daten = ResultFileReader.readResults(datei);
    
    // Jetzt kannst du direkt auf die Maps zugreifen, z.B.:
    Map<String, List<String>> norden = daten.northBorders;
    System.out.println("Anzahl einzigartiger Nordränder: " + norden.size());

} catch (IOException e) {
    e.printStackTrace();
}

 */

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ResultFileReader {

    public static class AnalysisResult {
        public final Map<String, List<String>> northBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> eastBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> southBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> westBorders = new LinkedHashMap<>();
    }

    /**
     * Liest die 'analyzer_results.txt' ein und rekonstruiert die Maps zuverlässig im Speicher.
     */
    public static AnalysisResult readResults(File resultFile) throws IOException {
        AnalysisResult result = new AnalysisResult();
        List<String> lines = Files.readAllLines(resultFile.toPath());

        Map<String, List<String>> currentMap = null;

        for (String line : lines) {
            line = line.trim(); // Entfernt alle unsichtbaren Leerzeichen am Anfang und Ende
            if (line.isEmpty()) continue;

            // Sektionen erkennen
            if (line.contains("NORDRÄNDER")) {
                currentMap = result.northBorders;
                continue;
            } else if (line.contains("OSTRÄNDER")) {
                currentMap = result.eastBorders;
                continue;
            } else if (line.contains("SÜDRÄNDER")) {
                currentMap = result.southBorders;
                continue;
            } else if (line.contains("WESTRÄNDER")) {
                currentMap = result.westBorders;
                continue;
            }

            // Zeile parsen, wenn wir uns in einer Sektion befinden und der Pfeil existiert
            if (currentMap != null && line.contains(" -> ")) {
                String[] parts = line.split(" -> ");
                if (parts.length == 2) {
                    String borderKey = parts[0].trim();      // z.B. "[1, 1, 2, 2, ...]"
                    String fileNamesRaw = parts[1].trim();   // z.B. "[onlyLs_95_20260909_214455.txt]"

                    // Entfernt die eckigen Klammern von der Dateiliste
                    if (fileNamesRaw.startsWith("[") && fileNamesRaw.endsWith("]")) {
                        fileNamesRaw = fileNamesRaw.substring(1, fileNamesRaw.length() - 1);
                    }

                    List<String> fileList = new ArrayList<>();
                    if (!fileNamesRaw.trim().isEmpty()) {
                        for (String name : fileNamesRaw.split(",")) {
                            fileList.add(name.trim());
                        }
                    }
                    
                    // Erfolgreich zurück in die Map speichern
                    currentMap.put(borderKey, fileList);
                }
            }
        }
        return result;
    }
}
