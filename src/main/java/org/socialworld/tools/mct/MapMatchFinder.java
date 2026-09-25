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

        // HIER DIE NEUE LISTE FÜR DIE OPTIMIERUNG INITIALISIEREN:
        List<MapsHeightLines> filteredTiles = new ArrayList<>();

        for (MapsHeightLines tileA : allTiles) {
            List<String> connections = new ArrayList<>();

            for (MapsHeightLines tileB : allTiles) {
                // Sich selbst nicht als Partner zählen
                if (tileA == tileB) continue; 

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
                
                // OPTIMIERUNG: Nur Kacheln mit mindestens einer Verbindung merken
                filteredTiles.add(tileA);
            }
        }

        // =================================================
        // BENUTZERAUSWAHL: MODUS WÄHLEN
        // =================================================
        System.out.println("Optimierung aktiv: Von " + allTiles.size() + " Kacheln wurden " 
                           + (allTiles.size() - filteredTiles.size()) + " isolierte Kacheln ignoriert.");
        System.out.println("Verbleibende Kacheln für Berechnungen: " + filteredTiles.size() + "\n");
        
        System.out.println("Bitte Modus wählen:");
        System.out.println("[1] Reiner Analyse-Modus (findMax3x3Sectors)");
        System.out.println("[2] Vervollständigungs-Modus (generateMissingSectors)");
        System.out.print("Eingabe (1 oder 2): ");
        
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();
        System.out.println();

        if (choice.equals("1")) {
            System.out.println("Starte Berechnung der maximalen 3x3-Sektoren...");
            findMax3x3Sectors(filteredTiles);
            
        } else if (choice.equals("2")) {
            System.out.println("Starte Vervollständigungs-Modus...");
            
            // 1. Datenstrukturen für das Backtracking vorbereiten
            List<MapsHeightLines[][]> bestLayouts = new ArrayList<>();
            int[] maxCount = new int[]{0}; 
            MapsHeightLines[][] currentGrid = new MapsHeightLines[3][3];
            boolean[] used = new boolean[filteredTiles.size()];
            
            // 2. Backtracking ausführen (sucht nach maximaler Belegung)
            searchGrid(0, currentGrid, used, filteredTiles, 0, maxCount, bestLayouts);
            
            // 3. Das optimale Layout suchen, das alle Kriterien erfüllt
            MapsHeightLines[][] layoutToComplete = null;
            
            for (MapsHeightLines[][] layout : bestLayouts) {
                // Wir zählen die real belegten Kacheln in diesem Layout
                int actualCount = 0;
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (layout[r][c] != null) actualCount++;
                    }
                }
                
                // PRÜFUNG DER KRITERIEN: 
                // Mindestens 4, maximal 8 belegte Kacheln UND das Zentrum [1][1] darf nicht leer sein
                if (actualCount >= 3 && actualCount <= 8 && layout[1][1] != null) {
                    layoutToComplete = layout;
                    System.out.println("Passendes Ausgangs-Layout gefunden (" + actualCount + " von 9 Kacheln belegt, Zentrum aktiv).");
                    break; // Wir nehmen das erste Layout, das alle Kriterien erfüllt
                }
            }
            
            // 4. Visualisierung und Übergabe an den Generator
            if (layoutToComplete != null) {
                System.out.println("Nutze folgendes Layout als Basis für die Vervollständigung:");
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        System.out.printf("[%-12s] ", (layoutToComplete[r][c] != null ? layoutToComplete[r][c].name : "- LÜCKE -"));
                    }
                    System.out.println();
                }
                System.out.println();

                // Übergabe an Ihre Generator-Methode
                generateMissingSectors(layoutToComplete);
            } else {
                System.out.println("[Abbruch] Kein Layout erfüllte die Kriterien:");
                System.out.println(" - Mindestens 4 und maximal 8 belegte Sektoren");
                System.out.println(" - Der zentrale Sektor MUSS belegt sein");
                System.out.println("Es wurden keine Dateien generiert.");
            }
            
        } else {
            System.out.println("Ungültige Auswahl. Programm wird beendet.");
        }
        
    } // Ende der main-Methode
    
    
    public static void generateMissingSectors(List<MapsHeightLines> filteredTiles) {

	    System.out.println("Optimierung aktiv: Verbleibende Kacheln für die Suche: " + filteredTiles.size());
	    System.out.println("\nStarte Berechnung des maximal belegbaren 3x3-Sektorenrasters...");
	    
	    // 1. Datenstrukturen für das Backtracking vorbereiten
	    List<MapsHeightLines[][]> bestLayouts = new ArrayList<>();
	    int[] maxCount = new int[]{0}; 
	    MapsHeightLines[][] currentGrid = new MapsHeightLines[3][3];
	    boolean[] used = new boolean[filteredTiles.size()];
	    
	    // 2. Backtracking ausführen (sucht nach der maximalen Belegung aus existierenden Kacheln)
	    searchGrid(0, currentGrid, used, filteredTiles, 0, maxCount, bestLayouts);
	    
	    System.out.println("Maximale Anzahl zusammenhängender existierender Maps gefunden: " + maxCount[0]);
	    System.out.println("Anzahl gefundener optimaler Ausgangs-Layouts: " + bestLayouts.size() + "\n");
	    
	    if (!bestLayouts.isEmpty()) {
	        // 3. Wir nehmen das erste optimale Layout, das aus Ihren existierenden Maps gebaut werden konnte
	        MapsHeightLines[][] optimalBaseGrid = bestLayouts.get(0);
	        
	        System.out.println("Nutze folgendes Layout als Basis für die Vervollständigung:");
	        for (int r = 0; r < 3; r++) {
	            for (int c = 0; c < 3; c++) {
	                System.out.printf("[%%-12s] ", (optimalBaseGrid[r][c] != null ? optimalBaseGrid[r][c].name : "- LÜCKE -"));
	            }
	            System.out.println();
	        }
	        System.out.println();
	
	        // 4. DER ENTSCHEIDENDE AUFRUF: 
	        // Wir übergeben dieses unvollständige Gitter. Die Methode erkennt die "- LÜCKE -" Einträge,
	        // liest die Höhenlinien der angrenzenden Maps aus und generiert die fehlenden Sektoren!
	        generateMissingSectors(optimalBaseGrid);
	
	    } else {
	        System.out.println("[Fehler] Es konnte kein gültiges Ausgangslayout berechnet werden.");
	    }
	    
	} 


    /**
     * Analysiert ein unvollständiges 3x3-Gitter und berechnet für jede leere Position
     * die exakten Höhenlinien. Vorab wird geprüft, ob bereits Kacheldateien existieren.
     * Wenn ja, bricht das Programm zum Schutz der Daten ab.
     */
    public static void generateMissingSectors(MapsHeightLines[][] incompleteGrid) {
        System.out.println("=================================================");
        System.out.println("   GENERATOR-MODUS: FEHLENDE SEKTOREN BERECHNEN  ");
        System.out.println("=================================================\n");

        // Vordefinierte Basis-Dateinamen basierend auf den Koordinaten (r, c)
        String[][] filenames = {
            {"onlyLs_01_nordwest.txt", "onlyLs_02_norden.txt", "onlyLs_03_nordost.txt"},
            {"onlyLs_04_westen.txt",   "onlyLs_05_zentrum.txt", "onlyLs_06_osten.txt"},
            {"onlyLs_07_suedwest.txt", "onlyLs_08_sueden.txt", "onlyLs_09_suedost.txt"}
        };

        // 1. VORAB-PRÜFUNG: Existiert bereits eine der Dateien?
        List<String> existingFiles = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                File checkFile = new File(filenames[r][c]);
                if (checkFile.exists()) {
                    existingFiles.add(filenames[r][c]);
                }
            }
        }

        // Wenn Dateien gefunden wurden, geben wir einen Hinweis aus und brechen ab
        if (!existingFiles.isEmpty()) {
            System.err.println("[ABBRUCH] Generierung gestoppt! Folgende Datei(en) existieren bereits im Ordner:");
            for (String file : existingFiles) {
                System.err.println("  -> " + file);
            }
            System.err.println("\nBitte verschieben oder löschen Sie diese Dateien, bevor Sie eine neue Vervollständigung starten.");
            return;
        }

        System.out.println("-> Speicherort ist sauber. Starte Generierung...\n");
        int missingCount = 0;

        // 2. RASTER DURCHLAUFEN UND LÜCKEN FÜLLEN
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Nur Lücken (null) verarbeiten
                if (incompleteGrid[r][c] != null) continue;
                
                missingCount++;
                System.out.println("-------------------------------------------------");
                System.out.printf("Lücke gefunden auf Position [%d, %d] (Reihe %d, Spalte %d):\n", r, c, r + 1, c + 1);
                
                int[] requiredNorth = null;
                int[] requiredEast  = null;
                int[] requiredSouth = null;
                int[] requiredWest  = null;

                // Anforderungen abrufen (Kanten-Mapping)
                if (r > 0 && incompleteGrid[r - 1][c] != null) {
                    requiredNorth = normalize(incompleteGrid[r - 1][c].south);
                    System.out.println("  -> Nordrand (von '" + incompleteGrid[r - 1][c].name + "'): " + Arrays.toString(requiredNorth));
                }
                if (c < 2 && incompleteGrid[r][c + 1] != null) {
                    requiredEast = normalize(incompleteGrid[r][c + 1].west);
                    System.out.println("  -> Ostrand  (von '" + incompleteGrid[r][c + 1].name + "'): " + Arrays.toString(requiredEast));
                }
                if (r < 2 && incompleteGrid[r + 1][c] != null) {
                    requiredSouth = normalize(incompleteGrid[r + 1][c].north);
                    System.out.println("  -> Südrand  (von '" + incompleteGrid[r + 1][c].name + "'): " + Arrays.toString(requiredSouth));
                }
                if (c > 0 && incompleteGrid[r][c - 1] != null) {
                    requiredWest = normalize(incompleteGrid[r][c - 1].east);
                    System.out.println("  -> Westrand (von '" + incompleteGrid[r][c - 1].name + "'): " + Arrays.toString(requiredWest));
                }

                if (requiredNorth == null && requiredEast == null && requiredSouth == null && requiredWest == null) {
                    System.out.println("  (Keine direkten Nachbarn vorhanden. Sektor ist frei gestaltbar.)");
                }

                
                // Dateiname direkt aus der Matrix (ohne Suffix)
                String targetFilename = filenames[r][c];
                
                boolean success = false;
                String skeletonString = "";
                while (!success) {
                     while (skeletonString.equals("") ) {
                    	 skeletonString = MapSkeletonGenerator.generateSkeleton(requiredNorth, requiredEast, requiredSouth, requiredWest);
                         if (skeletonString.length() > 0) {
                         	 System.out.println("Skeleton: " + skeletonString);
                             System.out.println("  -> Generiere Kachel in Datei: " + targetFilename);
                         }
                     }
                	 success = MapCreationTool.fillSkeleton(skeletonString, targetFilename);
                	 // reset, falls noch kein success, dann mit neuem string versuchen
                	 skeletonString = "";
                }
              
                
              }
        }

        if (missingCount == 0) {
            System.out.println("Das 3x3-Raster ist bereits vollständig! Keine Generierung notwendig.");
        } else {
            System.out.println("\n-------------------------------------------------");
            System.out.println("Analyse und Erstellung erfolgreich beendet. " + missingCount + " Sektordatei(en) generiert.");
        }
    }
    
    
    
     
    
    /**
     * Ermittelt die maximal belegbaren, zusammenhängenden Kombinationen von Maps in einem 3x3-Gitter.
     * Gibt die optimale(n) Anordnung(en) auf der Konsole aus.
     */
    public static void findMax3x3Sectors(List<MapsHeightLines> allTiles) {
        List<MapsHeightLines[][]> bestLayouts = new ArrayList<>();
        int[] maxCount = new int[]{0}; // Wrapper, um das Maximum im Rekursionsbaum zu teilen
        
        MapsHeightLines[][] currentGrid = new MapsHeightLines[3][3];
        boolean[] used = new boolean[allTiles.size()];
        
        // Starte die Suche ab Index 0 (oben links im Gitter)
        searchGrid(0, currentGrid, used, allTiles, 0, maxCount, bestLayouts);
        
        System.out.println("=================================================");
        System.out.println("   ERGEBNIS: MAXIMALE 3x3 SEKTOREN-ANORDNUNG     ");
        System.out.println("=================================================");
        // KORREKTUR: maxCount[0] statt maxCount aufrufen
        System.out.println("Maximale Anzahl zusammenhängender Maps: " + maxCount[0]);
        System.out.println("Anzahl gefundener optimaler Layouts:   " + bestLayouts.size() + "\n");
        
        // Maximal die ersten 5 optimalen Layouts zur Übersicht ausgeben
        int displayLimit = Math.min(bestLayouts.size(), 5);
        for (int i = 0; i < displayLimit; i++) {
            System.out.println("Zusammenhängende Kombination #" + (i + 1) + ":");
            MapsHeightLines[][] layout = bestLayouts.get(i);
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (layout[r][c] != null) {
                        // KORREKTUR: %-12s statt %n-12s (verhindert ungewollte Zeilenumbrüche)
                        System.out.printf("[%-12s] ", layout[r][c].name);
                    } else {
                        System.out.print("[     -      ] ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static void searchGrid(int index, MapsHeightLines[][] grid, boolean[] used, 
                                   List<MapsHeightLines> allTiles, int currentCount, 
                                   int[] maxCount, List<MapsHeightLines[][]> bestLayouts) {
        // Optimierung (Pruning): Wenn rechnerisch das aktuelle Maximum nicht mehr erreicht werden kann
        if (currentCount + (9 - index) < maxCount[0]) {
            return;
        }

        // Basis-Fall: Das gesamte 3x3-Gitter (Indices 0 bis 8) wurde durchlaufen
        if (index == 9) {
            if (currentCount >= maxCount[0] && currentCount > 0) {
                // Prüfen, ob alle platzierten Kacheln einen zusammenhängenden Sektor bilden
                if (isConnected(grid, currentCount)) {
                    if (currentCount > maxCount[0]) {
                        maxCount[0] = currentCount;
                        bestLayouts.clear(); // Altes, kleineres Maximum verwerfen
                    }
                    bestLayouts.add(cloneGrid(grid));
                }
            }
            return;
        }
        
        int r = index / 3;
        int c = index % 3;
        
        // Option 1: Diesen Platz im Gitter bewusst LEER lassen
        grid[r][c] = null;
        searchGrid(index + 1, grid, used, allTiles, currentCount, maxCount, bestLayouts);
        
        // Option 2: Versuchen, eine der verfügbaren Maps hier zu platzieren
        for (int i = 0; i < allTiles.size(); i++) {
            if (!used[i]) {
                MapsHeightLines tile = allTiles.get(i);
                
                // Validierung nach oben (Nord-Kante trifft Süd-Kante des oberen Nachbarn)
                if (r > 0 && grid[r-1][c] != null) {
                    if (!matches(tile.north, grid[r-1][c].south)) continue;
                }
                // Validierung nach links (West-Kante trifft Ost-Kante des linken Nachbarn)
                if (c > 0 && grid[r][c-1] != null) {
                    if (!matches(tile.west, grid[r][c-1].east)) continue;
                }
                
                // Schritt vorwärts
                grid[r][c] = tile;
                used[i] = true;
                
                searchGrid(index + 1, grid, used, allTiles, currentCount + 1, maxCount, bestLayouts);
                
                // Backtracking (Schritt zurück)
                grid[r][c] = null;
                used[i] = false;
            }
        }
    }

    /**
     * Überprüft mittels Tiefensuche (DFS), ob alle platzierten Maps orthogonal zusammenhängen.
     */
    private static boolean isConnected(MapsHeightLines[][] grid, int expectedCount) {
        if (expectedCount <= 1) return true;
        
        boolean[][] visited = new boolean[3][3];
        int startR = -1, startC = -1;
        
            for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r][c] != null) {
                    startR = r;
                    startC = c;
                    break;
                }
            }
            if (startR != -1) break;
        }
        
        if (startR == -1) return false;
        
        int actualCount = dfsCount(grid, visited, startR, startC);
        return actualCount == expectedCount;
    }

    private static int dfsCount(MapsHeightLines[][] grid, boolean[][] visited, int r, int c) {
        if (r < 0 || r >= 3 || c < 0 || c >= 3 || visited[r][c] || grid[r][c] == null) {
            return 0;
        }
        
        visited[r][c] = true;
        int count = 1;
        
        count += dfsCount(grid, visited, r - 1, c); // oben
        count += dfsCount(grid, visited, r + 1, c); // unten
        count += dfsCount(grid, visited, r, c - 1); // links
        count += dfsCount(grid, visited, r, c + 1); // rechts
        
        return count;
    }

    private static MapsHeightLines[][] cloneGrid(MapsHeightLines[][] grid) {
        MapsHeightLines[][] clone = new MapsHeightLines[3][3];
        for (int r = 0; r < 3; r++) {
            System.arraycopy(grid[r], 0, clone[r], 0, 3);
        }
        return clone;
    }

}
