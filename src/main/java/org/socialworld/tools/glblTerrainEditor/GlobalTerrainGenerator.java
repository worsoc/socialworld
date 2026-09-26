package org.socialworld.tools.glblTerrainEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

import org.socialworld.attributes.GroundMaterial;

public class GlobalTerrainGenerator {

    private static final int MAP_WIDTH = 32;
    private static final int MAP_HEIGHT = 32;
    private static final int MESO_SIZE = 81;

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        File defaultDir = new File(workingDir);

        JFileChooser folderChooser = defaultDir.exists() ? new JFileChooser(defaultDir) : new JFileChooser();
        folderChooser.setDialogTitle("Standardverzeichnis für Generierung wählen");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (folderChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File selectedDirectory = folderChooser.getSelectedFile();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        
        // Finaler Dateiname für das Endprodukt
        File targetFile = new File(selectedDirectory, "procedural_world_32x32_" + timestamp + ".map");

        // =========================================================================
        // SCHRITT 1: Makro-Grid berechnen & CHECKPOINT 1 (Valide Roh-Map) exportieren
        // =========================================================================
        System.out.println("Schritt 1: Berechne Makro-Grid...");
        int[][] macroGrid = TerrainProcessingCore.generateMacroGrid(MAP_WIDTH, MAP_HEIGHT);
        
 
        // =========================================================================
        // SCHRITT 2: Template-Engine anwenden & CHECKPOINT 2 (.map-Datei) exportieren
        // =========================================================================
        int totalW = MAP_WIDTH * MESO_SIZE;
        int totalH = MAP_HEIGHT * MESO_SIZE;
        
        System.out.println("Schritt 2: Wende Template-Engine an...");
        double[][] mesoGrid = TerrainTemplateEngine.createTemplateMesoGrid(macroGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);
        
 
        // =========================================================================
        // SCHRITT 3: Finale Filterkette & Export der Hauptkarte
        // =========================================================================
        
       
        // Lauf 1: Interner Rückbau bei hoher Eigen-Dominanz (>60%)
        mesoGrid = TerrainProcessingCore.applyLocalCornerInfusion(mesoGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);

        // LAUF 2 : Inter-zelluläre Brückenbildung basierend auf den echten Nachbarpixeln!
        mesoGrid = TerrainProcessingCore.applyInterCellularCornerBridge(mesoGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);
 
        // LAUF 3 : das grid-Zentrum mit dominantem Terrain belegen
        mesoGrid = TerrainProcessingCore.dissolveCenterChokePoints(mesoGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);

 
        mesoGrid = TerrainProcessingCore.applyRandomWobbleFilter(mesoGrid, totalW, totalH);
 
        mesoGrid = TerrainMathUtils.applyCellularBlurFilter(mesoGrid, totalW, totalH, 2);

        System.out.println("Schritt 3: Exportiere finale `.map`-Datei...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            writer.write("# GlobalTerrain Max-Compressed Export File - Format Version 2.6\n");
            writer.write("DIMENSIONS_" + MAP_WIDTH + "_" + MAP_HEIGHT + "\n\n");

            for (int cy = 0; cy < MAP_HEIGHT; cy++) {
                for (int cx = 0; cx < MAP_WIDTH; cx++) {
                    writeMacroCell(writer, macroGrid[cy][cx], cx, cy, mesoGrid);
                }
            }
            System.out.println("... Datei " + targetFile + " geschrieben.");
           } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Korrigiert: Nutzt nun die exakten Integer-IDs aus der Template-Engine,
     * damit sich die Terrains zwischen Schritt 2 und 3 nicht mehr verschieben.
     * Unterstützt nun alle erweiterten Geländearten.
     */
    private static void writeMacroCell(BufferedWriter writer, int type, int cx, int cy, double[][] mesoGrid) throws IOException {
        double elevation = 0.0; 
        String baseCover = "SALTWATER";
        
        // 1. Header-Werte (Elevation & BaseCover) angepasst an die GroundMaterial-Enum-Namen in UPPERCASE
        if (type == TerrainProcessingCore.TYPE_SALTWATER) { 
            elevation = 0.0; baseCover = "SALTWATER"; 
        } else if (type == TerrainProcessingCore.TYPE_WATER) { 
            elevation = 115.0; baseCover = "WATER"; 
        } else if (type == TerrainProcessingCore.TYPE_COAST) { 
            elevation = 15.0; baseCover = "SAND"; 
        } else if (type == TerrainProcessingCore.TYPE_PLAINS) { 
            elevation = 120.0; baseCover = "GRASS"; 
        } else if (type == TerrainProcessingCore.TYPE_MOUNTAIN) { 
            elevation = 850.0; baseCover = "ROCK"; 
        } else if (type == TerrainProcessingCore.TYPE_SWAMP) { 
            elevation = 90.0; baseCover = "MUD"; 
        } else if (type == TerrainProcessingCore.TYPE_GRAVEL) { 
            elevation = 250.0; baseCover = "CRUSHEDROCK"; 
        } else if (type == TerrainProcessingCore.TYPE_SCREE) { 
            elevation = 550.0; baseCover = "STONES"; 
        } else if (type == TerrainProcessingCore.TYPE_FOREST_FLOOR) { 
            elevation = 130.0; baseCover = "MOSS"; 
        } else if (type == TerrainProcessingCore.TYPE_WOODLAND) { 
            elevation = 140.0; baseCover = "FOLIAGE"; 
        } else if (type == TerrainProcessingCore.TYPE_SHRUBLAND) { 
            elevation = 135.0; baseCover = "BRUSHWOOD"; 
        } else if (type == TerrainProcessingCore.TYPE_WASTELAND) { 
            elevation = 110.0; baseCover = "ASH"; 
        } else if (type == TerrainProcessingCore.TYPE_SNOW) { 
            elevation = 950.0; baseCover = "SNOW"; 
        } else if (type == TerrainProcessingCore.TYPE_ICE) { 
            elevation = 1050.0; baseCover = "ICE"; 
        }

        writer.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
        writer.write("  ELEVATION:" + elevation + "\n");
        writer.write("  BASE_COVER:" + baseCover + "\n");
        writer.write("  MESO_LAYER {\n");

        List<String> allMesoTokens = new ArrayList<>();
        for (int my = 0; my < MESO_SIZE; my++) {
            for (int mx = 0; mx < MESO_SIZE; mx++) {
                // Wir runden den Wert (falls die Filter leichte Kommazustände erzeugt haben)
                int val = (int) Math.round(mesoGrid[cy * MESO_SIZE + my][cx * MESO_SIZE + mx]);
                GroundMaterial material = GroundMaterial.fromGteId(val);
                
                // 2. Erweitertes Mapping aller Enum-Werte auf die 2-Zeichen-Meso-Tokens
                String token = switch (material) {
                    case saltwater   -> "SW"; // 0 -> Salzwasser
                    case water       -> "WA"; // 1 -> Süßwasser
                    case sand        -> "SA"; // 2 -> Sand / Strand
                    case mud         -> "MU"; // 3 -> Schlamm / Sumpf
                    case crushedrock -> "CR"; // 4 -> Schotter
                    case stones      -> "ST"; // 5 -> Steine / Geröll
                    case rock        -> "RK"; // 6 -> Fels
                    case moss        -> "MO"; // 7 -> Moos / Waldboden
                    case grass       -> "GR"; // 8 -> Grasland
                    case foliage     -> "FO"; // 9 -> Laubwald
                    case brushwood   -> "BR"; // 10 -> Reisig / Gestrüpp
                    case ash         -> "AS"; // 11 -> Asche / Ödland
                    case snow        -> "SN"; // 12 -> Schnee
                    case ice         -> "IC"; // 13 -> Eis
                    default          -> "SW";
                };

                allMesoTokens.add(token + "-KE");
            }
        }
        writer.write("    " + compressTokenList(allMesoTokens) + "\n");
        writer.write("  }\n  SHRUB_SCHABLONE {\n  }\n  TERRAIN_DELTA {\n  }\n}\n\n");
    }

    private static String compressTokenList(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) return "";
        StringBuilder sb = new StringBuilder(); int i = 0;
        while (i < tokens.size()) {
            String current = tokens.get(i); int count = 1;
            while (i + 1 < tokens.size() && tokens.get(i + 1).equals(current)) { count++; i++; }
            if (count > 1) sb.append("{").append(current).append("}").append(count);
            else sb.append(current);
            if (i < tokens.size() - 1) sb.append(",");
            i++;
        }
        return sb.toString();
    }
}
