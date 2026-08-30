package org.socialworld.tools.glblTerrainEditor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Version 2.5 - Maximierte Meso-Komprimierung.
 * Schreibt den MESO_LAYER nun als eine einzige, fortlaufende RLE-Kette 
 * über alle 6.561 Elemente hinweg (deine Aufaddier-Technik).
 */
public class GlobalTerrainExporter {

    public static void exportMap(MacroMap map, File targetFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            
            writer.write("# GlobalTerrain Max-Compressed Export File - Format Version 2.5\n");
            writer.write("# Compression: MESO_LAYER compressed continuously up to 6561 elements\n");
            writer.write("DIMENSIONS_" + map.getWidth() + "_" + map.getHeight() + "\n\n");

            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    MacroMapCell cell = map.getCell(x, y);
                    
                    writer.write("MACRO_CELL[" + x + "," + y + "] {\n");
                    writer.write("  ELEVATION:" + cell.getReferenceElevation() + "\n");
                    writer.write("  BASE_COVER:" + cell.getCoverType() + "\n");
                    
                    // --- EBENE A: MESO-LAYER (JETZT ALS FORTLAUFENDE 6561er KETTE) ---
                    writer.write("  MESO_LAYER {\n");
                    List<String> allMesoTokens = new ArrayList<>();
                    
                    for (int mx = 0; mx < 81; mx++) {
                        for (int my = 0; my < 81; my++) {
                            String terrain = cell.getMesoTerrain(mx, my).substring(0, 2);
                            String baum = cell.getMesoBaum(mx, my).substring(0, 2);
                            allMesoTokens.add(terrain + "-" + baum);
                        }
                    }
                    // Komprimiert die gesamte Kachel-Matrix am Stück
                    String compressedMeso = compressTokenList(allMesoTokens);
                    writer.write("    " + compressedMeso + "\n");
                    writer.write("  }\n");

                    // --- EBENE B: SHRUB_SCHABLONE (Sparse) ---
                    writer.write("  SHRUB_SCHABLONE {\n");
                    for (int mx = 0; mx < 81; mx++) {
                        for (int my = 0; my < 81; my++) {
                            if (!cell.hatStrauchMischung(mx, my)) continue;
                            
                            writer.write("    shrub_" + mx + "_" + my + "(");
                            List<String> shrubTokens = new ArrayList<>();
                            for (int lx = 0; lx < 9; lx++) {
                                for (int ly = 0; ly < 9; ly++) {
                                    String strauch = cell.getMesoStrauchAusMischung(mx, my, lx, ly);
                                    shrubTokens.add("S_" + strauch.substring(0, 2));
                                }
                            }
                            writer.write(compressTokenList(shrubTokens) + ")\n");
                        }
                    }
                    writer.write("  }\n");

                    // --- EBENE C: TERRAIN_DELTA (Positivliste) ---
                    writer.write("  TERRAIN_DELTA {\n");
                    for (int mx = 0; mx < 81; mx++) {
                        for (int my = 0; my < 81; my++) {
                            StringBuilder cellDeltaBuilder = new StringBuilder();
                            for (byte code = 1; code <= 5; code++) {
                                List<Integer> indices = new ArrayList<>();
                                for (int lx = 0; lx < 9; lx++) {
                                    for (int ly = 0; ly < 9; ly++) {
                                        int globalX = mx * 9 + lx;
                                        int globalY = my * 9 + ly;
                                        if (cell.getMikroTerrainDelta(globalX, globalY) == code) {
                                            indices.add((lx * 9) + ly);
                                        }
                                    }
                                }
                                if (!indices.isEmpty()) {
                                    if (cellDeltaBuilder.length() > 0) cellDeltaBuilder.append(";");
                                    cellDeltaBuilder.append(code).append(":");
                                    for (int i = 0; i < indices.size(); i++) {
                                        cellDeltaBuilder.append(indices.get(i));
                                        if (i < indices.size() - 1) cellDeltaBuilder.append(",");
                                    }
                                }
                            }
                            if (cellDeltaBuilder.length() > 0) {
                                writer.write("    M[" + mx + "," + my + "]->" + cellDeltaBuilder.toString() + "\n");
                            }
                        }
                    }
                    writer.write("  }\n");
                    
                    writer.write("}\n\n");
                }
            }
            writer.flush();
        }
    }

    private static String compressTokenList(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < tokens.size()) {
            String current = tokens.get(i);
            int count = 1;
            while (i + 1 < tokens.size() && tokens.get(i + 1).equals(current)) {
                count++;
                i++;
            }
            if (count > 1) sb.append("{").append(current).append("}").append(count);
            else sb.append(current);
            if (i < tokens.size() - 1) sb.append(",");
            i++;
        }
        return sb.toString();
    }
}
