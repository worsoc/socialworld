package org.socialworld.tools.glblTerrainEditor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exportiert die fraktale 3-Stufen-Weltkarte in eine separate Textdatei.
 * Nutzt ein hocheffizientes Run-Length-Encoding (RLE) über alle 81 Elemente 
 * eines Chunks hinweg für maximale Dateikomprimierung.
 */
public class GlobalTerrainExporter {

    /**
     * Speichert die übergebene MacroMap im maximal komprimierten Schachtelungsformat ab.
     *
     * @param map        Die zu speichernde Weltkarte
     * @param targetFile Die Zieldatei (z.B. "welt_export.map")
     * @throws IOException Wenn beim Schreiben ein Fehler auftritt
     */
    public static void exportMap(MacroMap map, File targetFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            
            // 1. Header-Informationen schreiben
            writer.write("# GlobalTerrain Max-Compressed Export File - Format Version 1.2\n");
            writer.write("# Compression: {Element}Count loops through all 81 fields of a chunk\n");
            writer.write("DIMENSIONS_" + map.getWidth() + "_" + map.getHeight() + "\n\n");

            // 2. Jede einzelne 729m Makro-Kachel der Weltkarte durchlaufen
            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    MacroMapCell cell = map.getCell(x, y);
                    
                    // Makro-Kopfzeile schreiben (Koordinaten, Höhe, Standard-Terrain)
                    writer.write("MACRO_CELL[" + x + "," + y + "] {\n");
                    writer.write("  ELEVATION:" + cell.getReferenceElevation() + "\n");
                    writer.write("  BASE_COVER:" + cell.getCoverType() + "\n");
                    
                    // 3. Mittlere Stufe (Meso): Die 81x81 Matrix in die 9x9 Chunks aufteilen
                    for (int chunkX = 0; chunkX < 9; chunkX++) {
                        for (int chunkY = 0; chunkY < 9; chunkY++) {
                            
                            int startMx = chunkX * 9;
                            int startMy = chunkY * 9;
                            
                            writer.write("  sub[chunk_" + chunkX + "_" + chunkY + "](");
                            
                            // 4. Unterste Stufe (Mikro): Alle 81 Elemente des gesamten Chunks linear sammeln
                            List<String> chunkTokens = new ArrayList<>();
                            
                            for (int lx = 0; lx < 9; lx++) {
                                int mx = startMx + lx;
                                for (int ly = 0; ly < 9; ly++) {
                                    int my = startMy + ly;
                                    
                                    // Layer-Daten auslesen
                                    String terrain = cell.getMesoTerrain(mx, my);
                                    String baum = cell.getMesoBaum(mx, my);
                                    String strauch = cell.getMesoStrauch(mx, my);
                                    
                                    // Token-Format erzeugen: S_TE_BA_ST
                                    String token = "S_" + terrain.substring(0, 2) + "_" 
                                                 + baum.substring(0, 2) + "_" 
                                                 + strauch.substring(0, 2);
                                    chunkTokens.add(token);
                                }
                            }
                            
                            // Den gesamten 81er-Block fortlaufend komprimieren (deine optimierte Logik)
                            String compressedChunk = compressChunkList(chunkTokens);
                            writer.write(compressedChunk + ")\n");
                        }
                    }
                    writer.write("}\n\n"); // Schließt die Makro-Kachel
                }
            }
            writer.flush();
        }
    }

    /**
     * Komprimiert eine Liste von 81 Tokens nach der Logik:
     * Wiederholt sich ein Element, wird es zu {Element}Anzahl komprimiert, 
     * auch über die Zeilengrenzen des Chunks hinweg.
     */
    private static String compressChunkList(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        int i = 0;
        
        while (i < tokens.size()) {
            String current = tokens.get(i);
            int count = 1;
            
            // Zähle fortlaufend alle identischen, aufeinanderfolgenden Tokens
            while (i + 1 < tokens.size() && tokens.get(i + 1).equals(current)) {
                count++;
                i++;
            }
            
            // Bei Wiederholung komprimieren, ansonsten Element roh schreiben
            if (count > 1) {
                sb.append("{").append(current).append("}").append(count);
            } else {
                sb.append(current);
            }
            
            // Komma als Trenner anfügen, wenn die Kette im Chunk noch weitergeht
            if (i < tokens.size() - 1) {
                sb.append(",");
            }
            i++;
        }
        return sb.toString();
    }
}
