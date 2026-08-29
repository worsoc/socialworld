package org.socialworld.tools.glblTerrainEditor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lädt eine exportierte, maximal komprimierte Weltkarte (.map) wieder in den Editor.
 * Löst die RLE-Klammerkomprimierung ({Token}Count) beim Einlesen vollautomatisch auf.
 */
public class GlobalTerrainImporter {

    /**
     * Lädt eine Kartendatei und rekonstruiert das komplette fraktale Schichtenmodell.
     *
     * @param sourceFile Die einzulesende Datei
     * @return Die vollständig rekonstruierte MacroMap
     * @throws IOException Wenn beim Lesen oder Parsen ein Fehler auftritt
     */
    public static MacroMap importMap(File sourceFile) throws IOException {
        MacroMap map = null;
        MacroMapCell currentCell = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Kommentare und Leerzeilen überspringen
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 1. Dimensionen auslesen und die leere Karte initialisieren
                if (line.startsWith("DIMENSIONS_")) {
                    String[] parts = line.split("_");
                    int width = Integer.parseInt(parts[1]);
                    int height = Integer.parseInt(parts[2]);
                    map = new MacroMap(width, height);
                    continue;
                }

                if (map == null) continue;

                // 2. Makro-Zelle ansteuern
                if (line.startsWith("MACRO_CELL[")) {
                    int closeBracket = line.indexOf("]");
                    String coordString = line.substring(11, closeBracket);
                    String[] coords = coordString.split(",");
                    int cx = Integer.parseInt(coords[0]);
                    int cy = Integer.parseInt(coords[1]);
                    
                    currentCell = map.getCell(cx, cy);
                    continue;
                }

                if (currentCell == null) continue;

                // 3. Globale Höhenmeter für die Kachel auslesen
                if (line.startsWith("ELEVATION:")) {
                    double elevation = Double.parseDouble(line.substring(10));
                    currentCell.setReferenceElevation(elevation);
                    continue;
                }

                // 4. Globalen Terrain-Typ auslesen
                if (line.startsWith("BASE_COVER:")) {
                    currentCell.setCoverType(line.substring(11));
                    continue;
                }

                // 5. Chunk-Inhalt auslesen und die Klammer-Komprimierung auflösen
                if (line.startsWith("sub[chunk_")) {
                    int openParenthesis = line.indexOf("(");
                    int closeParenthesis = line.lastIndexOf(")");
                    
                    // Extrahiere die Koordinaten des 9x9 Chunks
                    String chunkMeta = line.substring(10, openParenthesis - 1); // Liefert "x_y"
                    String[] chunkCoords = chunkMeta.split("_");
                    int chunkX = Integer.parseInt(chunkCoords[0]);
                    int chunkY = Integer.parseInt(chunkCoords[1]);

                    // Extrahiere den komprimierten Datenstrom innerhalb der runden Klammern (...)
                    String dataStream = line.substring(openParenthesis + 1, closeParenthesis);
                    
                    // Dekomprimierungs-Algorithmus ausführen
                    List<String> decompressedTokens = decompressChunkStream(dataStream);

                    // Die 81 entpackten Tokens zurück in die Meso-Matrix der Zelle mappen
                    int startMx = chunkX * 9;
                    int startMy = chunkY * 9;
                    int tokenIndex = 0;

                    for (int lx = 0; lx < 9; lx++) {
                        int mx = startMx + lx;
                        for (int ly = 0; ly < 9; ly++) {
                            int my = startMy + ly;

                            if (tokenIndex < decompressedTokens.size()) {
                                String token = decompressedTokens.get(tokenIndex++);
                                parseAndApplyToken(currentCell, mx, my, token);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * Löst die Komprimierungs-Klammern auf (z.B. "{S_GR_KE_KE}81" -> 81-mal "S_GR_KE_KE").
     */
    private static List<String> decompressChunkStream(String stream) {
        List<String> tokens = new ArrayList<>();
        // Der Stream ist per Komma getrennt
        String[] rawParts = stream.split(",");

        for (String part : rawParts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            // Prüfen, ob das Element komprimiert ist (beginnt mit '{')
            if (part.startsWith("{")) {
                int closeBrace = part.indexOf("}");
                String token = part.substring(1, closeBrace); // Inhalt der Klammer holen
                int count = Integer.parseInt(part.substring(closeBrace + 1)); // Anzahl dahinter holen
                
                // Element entsprechend oft wiederholen
                for (int c = 0; c < count; c++) {
                    tokens.add(token);
                }
            } else {
                // Unkomprimiertes Einzelelement einfach hinzufügen
                tokens.add(part);
            }
        }
        return tokens;
    }

    /**
     * Übersetzt ein entpacktes Kurz-Token (z.B. "S_GR_EI_FA") zurück in die echten,
     * lesbaren Lang-Begriffe des Editors und weist sie den Schichten der Zelle zu.
     */
    private static void parseAndApplyToken(MacroMapCell cell, int mx, int my, String token) {
        String[] parts = token.split("_");
        if (parts.length < 4) return;

        // 1. Terrain übersetzen (S_GR_...)
        String terrain = switch (parts[1]) {
            case "WA" -> "WASSER";
            case "SA" -> "SAND";
            case "ST" -> "STEIN";
            case "SC" -> "SCHNEE";
            default -> "GRAS";
        };
        cell.setMesoTerrain(mx, my, terrain);

        // 2. Baumart übersetzen (..._EI_...)
        String baum = switch (parts[2]) {
            case "EI" -> "EICHE";
            case "KI" -> "KIEFER";
            case "BI" -> "BIRKE";
            default -> "KEIN_BAUM";
        };
        cell.setMesoBaum(mx, my, baum);

        // 3. Strauchart übersetzen (..._FA)
        String strauch = switch (parts[3]) {
            case "ZI" -> "ZIERSTRAUCH";
            case "BE" -> "BEERENSTRAUCH";
            case "FA" -> "FARNE";
            default -> "KEIN_STRAUCH";
        };
        cell.setMesoStrauch(mx, my, strauch);
    }
}
