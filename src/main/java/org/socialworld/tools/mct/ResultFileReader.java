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
import java.util.regex.*;

public class ResultFileReader {

    // Container-Klasse, die alle vier rekonstruierten Maps hält
    public static class AnalysisResult {
        public final Map<String, List<String>> northBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> eastBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> southBorders = new LinkedHashMap<>();
        public final Map<String, List<String>> westBorders = new LinkedHashMap<>();
    }

    /**
     * Liest die 'analyzer_results.txt' ein und rekonstruiert die Maps.
     * @param resultFile Die einzulesende Ergebnisdatei
     * @return Ein AnalysisResult-Objekt mit den vier befüllten Maps
     * @throws IOException Wenn beim Lesen der Datei ein Fehler auftritt
     */
    public static AnalysisResult readResults(File resultFile) throws IOException {
        AnalysisResult result = new AnalysisResult();
        List<String> lines = Files.readAllLines(resultFile.toPath());

        Map<String, List<String>> currentMap = null;
        // Regex fängt das Format ab: [0, 0, 0...] -> [datei1.txt, datei2.txt]
        Pattern linePattern = Pattern.compile("^(\\\\[.*?\\\\])\\s*->\\s*\\\\[(.*?)\\\\]$");

        for (String line : lines) {
            line = line.trim();
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

            // Datenzeile parsen, wenn wir uns in einer Sektion befinden
            if (currentMap != null) {
                Matcher matcher = linePattern.matcher(line);
                if (matcher.matches()) {
                    String borderKey = matcher.group(1); // Das Höhen-Array als String, z.B. "[0, 0, 0...]"
                    String fileNamesRaw = matcher.group(2); // Die kommagetrennten Dateinamen

                    List<String> fileList = new ArrayList<>();
                    if (!fileNamesRaw.trim().isEmpty()) {
                        // Teilt die Dateinamen am Komma und entfernt Leerzeichen
                        for (String name : fileNamesRaw.split(",")) {
                            fileList.add(name.trim());
                        }
                    }
                    
                    // In die aktive Map eintragen
                    currentMap.put(borderKey, fileList);
                }
            }
        }
        return result;
    }
}
