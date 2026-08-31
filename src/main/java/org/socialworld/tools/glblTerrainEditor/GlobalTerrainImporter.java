package org.socialworld.tools.glblTerrainEditor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Version 2.8 (Final) - Korrigiert den Array-Übergabefehler beim Laden des Meso-Geländes.
 */
public class GlobalTerrainImporter {

    public static MacroMap importMap(File sourceFile) throws IOException {
        MacroMap map = null;
        MacroMapCell currentCell = null;
        String currentSection = "";
        int mesoRowIndex = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("DIMENSIONS_")) {
                    String[] parts = line.split("_");
                    int width = Integer.parseInt(parts[1]);
                    int height = Integer.parseInt(parts[2]);
                    map = new MacroMap(width, height);
                    continue;
                }

                if (map == null) continue;

                if (line.startsWith("MACRO_CELL[")) {
                    int closeBracket = line.indexOf("]");
                    String[] coords = line.substring(11, closeBracket).split(",");
                    currentCell = map.getCell(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                    mesoRowIndex = 0;
                    currentSection = "";
                    continue;
                }

                if (currentCell == null) continue;

                if (line.startsWith("ELEVATION:")) {
                    currentCell.setReferenceElevation(Double.parseDouble(line.substring(10)));
                    continue;
                }
                if (line.startsWith("BASE_COVER:")) {
                    currentCell.setCoverType(line.substring(11));
                    continue;
                }

                if (line.startsWith("MESO_LAYER {") || line.startsWith("SHRUB_SCHABLONE {") || line.startsWith("TERRAIN_DELTA {")) {
                    currentSection = line.substring(0, line.indexOf(" {")).trim();
                    continue;
                }
                if (line.equals("}")) { currentSection = ""; continue; }

                switch (currentSection) {
                    case "MESO_LAYER" -> {
                        List<String> decompressedMeso = unpackRLEStream(line);
                        int tokenIndex = 0;
                        for (int my = 0; my < 81; my++) {
                            for (int mx = 0; mx < 81; mx++) {
                                if (tokenIndex < decompressedMeso.size()) {
                                    String[] parts = decompressedMeso.get(tokenIndex++).split("-");
                                    if (parts.length == 2) {
                                        // JETZT ABSOLUT KORREKT: Wir übergeben die einzelnen String-Elemente!
                                        currentCell.setMesoTerrain(mx, my, decodeTerrainToken(parts[0]));
                                        currentCell.setMesoBaum(mx, my, decodeBaumToken(parts[1]));
                                    }
                                }
                            }
                        }
                        mesoRowIndex++;
                    }
                    case "SHRUB_SCHABLONE" -> {
                        if (line.startsWith("shrub_")) {
                            int openParenthesis = line.indexOf("(");
                            int closeParenthesis = line.lastIndexOf(")");
                            
                            String[] chunkCoords = line.substring(6, openParenthesis).split("_");
                            int mx = Integer.parseInt(chunkCoords[0]);
                            int my = Integer.parseInt(chunkCoords[1]);

                            String dataStream = line.substring(openParenthesis + 1, closeParenthesis);
                            List<String> decompressedShrubs = unpackRLEStream(dataStream);

                            int tokenIndex = 0;
                            for (int ly = 0; ly < 9; ly++) {
                                for (int lx = 0; lx < 9; lx++) {
                                    if (tokenIndex < decompressedShrubs.size()) {
                                        String shrubType = decodeStrauchToken(decompressedShrubs.get(tokenIndex++));
                                        currentCell.setMesoStrauchInMischung(mx, my, lx, ly, shrubType);
                                    }
                                }
                            }
                        }
                    }
                    case "TERRAIN_DELTA" -> {
                        if (line.startsWith("M[")) {
                            int closeBracket = line.indexOf("]");
                            int arrow = line.indexOf("->");
                            
                            String[] mesoCoords = line.substring(2, closeBracket).split(",");
                            int mx = Integer.parseInt(mesoCoords[0]);
                            int my = Integer.parseInt(mesoCoords[1]);
                            
                            String[] terrainGroups = line.substring(arrow + 2).split(";");
                            for (String group : terrainGroups) {
                                String[] codeAndIndices = group.split(":");
                                byte code = Byte.parseByte(codeAndIndices[0]);
                                String[] indices = codeAndIndices[1].split(",");
                                
                                for (String indexStr : indices) {
                                    int fieldNumber = Integer.parseInt(indexStr);
                                    int ly = fieldNumber / 9;
                                    int lx = fieldNumber % 9;
                                    currentCell.setMikroTerrainDelta(mx * 9 + lx, my * 9 + ly, code);
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private static List<String> unpackRLEStream(String stream) {
        List<String> tokens = new ArrayList<>();
        String[] rawParts = stream.split(",");
        for (String part : rawParts) {
            part = part.trim();
            if (part.isEmpty()) continue;
            if (part.startsWith("{")) {
                int closeBrace = part.indexOf("}");
                String token = part.substring(1, closeBrace);
                int count = Integer.parseInt(part.substring(closeBrace + 1));
                for (int c = 0; c < count; c++) tokens.add(token);
            } else {
                tokens.add(part);
            }
        }
        return tokens;
    }

    private static String decodeTerrainToken(String code) {
        return switch (code) {
            case "WA", "1"  -> "WATER";
            case "SA", "2"  -> "SAND";
            case "MU", "3"  -> "MUD";
            case "CR", "4"  -> "CRUSHED_ROCK";
            case "ST", "5"  -> "STONES";
            case "RO", "6"  -> "ROCK";
            case "MO", "7"  -> "MOSS";
            case "GR", "8"  -> "GRASS";
            case "FO", "9"  -> "FOLIAGE";
            case "BR", "10" -> "BRUSHWOOD";
            case "AS", "11" -> "ASH";
            case "SN", "12" -> "SNOW";
            case "IC", "13" -> "ICE";
            default         -> "GRASS";
        };
    }

    private static String decodeBaumToken(String code) {
        return switch (code) {
            case "EI" -> "EICHE";
            case "KI" -> "KIEFER";
            case "BI" -> "BIRKE";
            case "BU" -> "BUCHE";
            case "FI" -> "FICHTE";
            case "WE" -> "WEIDE";
            default   -> "KEIN_BAUM";
        };
    }

    private static String decodeStrauchToken(String token) {
        String code = token.replace("S_", "");
        return switch (code) {
            case "ZI" -> "ZIERSTRAUCH";
            case "BE" -> "BEERENSTRAUCH";
            case "FA" -> "FARNE";
            case "BR" -> "BROMBEERE";
            case "HE" -> "HEIDEKRAUT";
            case "GI" -> "GINSTER";
            default   -> "KEIN_STRAUCH";
        };
    }
}
