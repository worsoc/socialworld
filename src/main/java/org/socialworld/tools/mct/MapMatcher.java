package org.socialworld.tools.mct;


/*
 * 
 * // 1. Bereits berechnete Daten einlesen
File datei = new File("C:/dein/pfad/analyzer_results.txt");
ResultFileReader.AnalysisResult geladeneDaten = ResultFileReader.readResults(datei);

// 2. Deine gesuchten Randprofile definieren (z.B. von den Nachbarkarten kopiert)
String gesuchterNorden = "[0, 0, 0, -1, 0, 0, -1, -2, -1, 0]";
String gesuchterWesten = "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";

// Norden und Westen sind eingeschränkt, Osten und Süden sind völlig egal (leer/null)
List<String> treffer = MapMatcher.findMatchingMaps(
    geladeneDaten, 
    gesuchterNorden, 
    null, 
    "", 
    gesuchterWesten
);

// 3. Ergebnisse ausgeben
System.out.println("Gefundene passende Maps: " + treffer);

 */


import java.util.*;
import org.socialworld.tools.mct.ResultFileReader.AnalysisResult;

public class MapMatcher {

    /**
     * Sucht nach Dateinamen, die alle übergebenen Randbedingungen gleichzeitig erfüllen.
     * 
     * @param data         Das geladene AnalysisResult aus der Ergebnisdatei
     * @param targetNorth  Gesuchter Nordrand (z.B. "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]") oder null/leer
     * @param targetEast   Gesuchter Ostrand oder null/leer
     * @param targetSouth  Gesuchter Südrand oder null/leer
     * @param targetWest   Gesuchter Westrand oder null/leer
     * @return Eine Liste aller Dateinamen, die perfekt in die Lücke passen
     */
    public static List<String> findMatchingMaps(AnalysisResult data, 
                                                String targetNorth, 
                                                String targetEast, 
                                                String targetSouth, 
                                                String targetWest) {
        
        // Set zur Berechnung der Schnittmenge aller zutreffenden Dateien
        Set<String> resultSet = null;
        boolean firstConstraint = true;

        // Hilfsarray für die Schleife, um doppelten Code zu vermeiden
        String[] targets = { targetNorth, targetEast, targetSouth, targetWest };
        
        // Wir verknüpfen die gesuchten Ränder mit den entsprechenden Maps aus der Ergebnisdatei
        @SuppressWarnings("unchecked")
        Map<String, List<String>>[] borderMaps = new Map[] {
            data.northBorders,
            data.eastBorders,
            data.southBorders,
            data.westBorders
        };

        for (int i = 0; i < 4; i++) {
            String targetBorder = targets[i];
            
            // Wenn für diese Seite keine Einschränkung existiert, überspringen
            if (targetBorder == null || targetBorder.trim().isEmpty()) {
                continue;
            }

            // Hole alle Dateinamen, die diesen spezifischen Rand aufweisen
            List<String> matchingFiles = borderMaps[i].get(targetBorder.trim());
            Set<String> currentSideSet = new HashSet<>();
            
            if (matchingFiles != null) {
                currentSideSet.addAll(matchingFiles);
            }

            if (firstConstraint) {
                // Beim ersten gefundenen Kriterium initialisieren wir das Gesamtergebnis mit diesen Dateien
                resultSet = new HashSet<>(currentSideSet);
                firstConstraint = false;
            } else {
                // Bei allen folgenden Kriterien bilden wir die Schnittmenge (Durchschnitt)
                resultSet.retainAll(currentSideSet);
            }

            // Wenn die Schnittmenge zwischendurch leer wird, gibt es keine passende Map mehr
            if (resultSet.isEmpty()) {
                return Collections.emptyList();
            }
        }

        // Wenn überhaupt keine Kriterien übergeben wurden, geben wir eine leere Liste zurück
        if (resultSet == null) {
            return Collections.emptyList();
        }

        // Ergebnis sortiert zurückgeben
        List<String> finalResult = new ArrayList<>(resultSet);
        Collections.sort(finalResult);
        return finalResult;
    }
}
