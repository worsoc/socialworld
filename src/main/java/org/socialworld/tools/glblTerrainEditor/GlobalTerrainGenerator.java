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
import javax.swing.JOptionPane;

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
        
        File macroFile = new File(selectedDirectory, "checkpoint1_macro_" + timestamp + ".map");
        try (BufferedWriter macroWriter = new BufferedWriter(new FileWriter(macroFile))) {
            macroWriter.write("# Checkpoint 1 - Pure Macro Grid State\n");
            macroWriter.write("DIMENSIONS_" + MAP_WIDTH + "_" + MAP_HEIGHT + "\n\n");
            
            for (int cy = 0; cy < MAP_HEIGHT; cy++) {
                for (int cx = 0; cx < MAP_WIDTH; cx++) {
                    int type = macroGrid[cy][cx];
                    double elevation = 0.0; String baseCover = "OCEAN";
                    if (type == TerrainProcessingCore.TYPE_COAST) { elevation = 15.0; baseCover = "COAST"; }
                    else if (type == TerrainProcessingCore.TYPE_PLAINS) { elevation = 120.0; baseCover = "PLAINS"; }
                    else if (type == TerrainProcessingCore.TYPE_MOUNTAIN) { elevation = 850.0; baseCover = "MOUNTAIN"; }

                    macroWriter.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
                    macroWriter.write("  ELEVATION:" + elevation + "\n");
                    macroWriter.write("  BASE_COVER:" + baseCover + "\n");
                    macroWriter.write("  MESO_LAYER {\n");
                    
                    // Übersetzt den Makro-Typ in dein echtes Kürzel
                    String token = switch (type) {
                        case 0 -> "SW";
                        case 1 -> "WA";
                        case 2 -> "SA";
                        case 3 -> "GR";
                        case 4 -> "RK";
                        default -> "SW";
                    };
                    // Befüllt das 81x81 Raster flach und nutzt die RLE-Kompression {Token-KE}6561
                    macroWriter.write("    {" + token + "-KE}6561\n");
                    macroWriter.write("  }\n  SHRUB_SCHABLONE {\n  }\n  TERRAIN_DELTA {\n  }\n}\n\n");
                }
            }
            System.out.println("Checkpoint 1 als valide Map gespeichert: " + macroFile.getName());
        } catch (IOException e) {
            System.err.println("Fehler bei Checkpoint 1: " + e.getMessage());
        }

        // =========================================================================
        // SCHRITT 2: Template-Engine anwenden & CHECKPOINT 2 (.map-Datei) exportieren
        // =========================================================================
        int totalW = MAP_WIDTH * MESO_SIZE;
        int totalH = MAP_HEIGHT * MESO_SIZE;
        
        System.out.println("Schritt 2: Wende Template-Engine an...");
        double[][] mesoGrid = TerrainTemplateEngine.createTemplateMesoGrid(macroGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);
        
        File templateFile = new File(selectedDirectory, "checkpoint2_template_" + timestamp + ".map");
        try (BufferedWriter tempWriter = new BufferedWriter(new FileWriter(templateFile))) {
            tempWriter.write("# Checkpoint 2 - Pure Template State\n");
            tempWriter.write("DIMENSIONS_" + MAP_WIDTH + "_" + MAP_HEIGHT + "\n\n");
            
            for (int cy = 0; cy < MAP_HEIGHT; cy++) {
                for (int cx = 0; cx < MAP_WIDTH; cx++) {
                    int type = macroGrid[cy][cx];
                    double elevation = 0.0; String baseCover = "OCEAN";
                    if (type == TerrainProcessingCore.TYPE_COAST) { elevation = 15.0; baseCover = "COAST"; }
                    else if (type == TerrainProcessingCore.TYPE_PLAINS) { elevation = 120.0; baseCover = "PLAINS"; }
                    else if (type == TerrainProcessingCore.TYPE_MOUNTAIN) { elevation = 850.0; baseCover = "MOUNTAIN"; }

                    tempWriter.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
                    tempWriter.write("  ELEVATION:" + elevation + "\n");
                    tempWriter.write("  BASE_COVER:" + baseCover + "\n");
                    tempWriter.write("  MESO_LAYER {\n");
                    
                    List<String> tempTokens = new ArrayList<>();
                    for (int my = 0; my < MESO_SIZE; my++) {
                        for (int mx = 0; mx < MESO_SIZE; mx++) {
                            double val = mesoGrid[cy * MESO_SIZE + my][cx * MESO_SIZE + mx];
                            String token = switch ((int)val) {
                                case 0 -> "SW";
                                case 1 -> "WA";
                                case 2 -> "SA";
                                case 3 -> "GR";
                                case 4 -> "RK";
                                default -> "SW";
                            };
                            tempTokens.add(token + "-KE");
                        }
                    }
                    tempWriter.write("    " + compressTokenList(tempTokens) + "\n");
                    tempWriter.write("  }\n  SHRUB_SCHABLONE {\n  }\n  TERRAIN_DELTA {\n  }\n}\n\n");
                }
            }
            System.out.println("Checkpoint 2 gespeichert: " + templateFile.getName());
        } catch (IOException e) {
            System.err.println("Fehler bei Checkpoint 2: " + e.getMessage());
        }

        // =========================================================================
        // SCHRITT 3: Finale Filterkette & Export der Hauptkarte
        // =========================================================================
        
       
        // Lauf 1: Interner Rückbau bei hoher Eigen-Dominanz (>60%)
        mesoGrid = TerrainProcessingCore.applyLocalCornerInfusion(mesoGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);

        // LAUF 2 NEU: Inter-zelluläre Brückenbildung basierend auf den echten Nachbarpixeln!
        mesoGrid = TerrainProcessingCore.applyInterCellularCornerBridge(mesoGrid, MAP_WIDTH, MAP_HEIGHT, MESO_SIZE);
        
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
            JOptionPane.showMessageDialog(null, "Generierung abgeschlossen!\n\nDateien im Ordner:\n1. " 
                    + macroFile.getName() + "\n2. " + templateFile.getName() + "\n3. " + targetFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Korrigiert: Nutzt nun die exakten Integer-IDs aus der Template-Engine,
     * damit sich die Terrains zwischen Schritt 2 und 3 nicht mehr verschieben.
     */
    private static void writeMacroCell(BufferedWriter writer, int type, int cx, int cy, double[][] mesoGrid) throws IOException {
        double elevation = 0.0; 
        String baseCover = "OCEAN";
        
        // Header-Werte für das Eclipse-System setzen
        if (type == TerrainProcessingCore.TYPE_COAST) { elevation = 15.0; baseCover = "COAST"; }
        else if (type == TerrainProcessingCore.TYPE_PLAINS) { elevation = 120.0; baseCover = "PLAINS"; }
        else if (type == TerrainProcessingCore.TYPE_MOUNTAIN) { elevation = 850.0; baseCover = "MOUNTAIN"; }

        writer.write("MACRO_CELL[" + cx + "," + cy + "] {\n");
        writer.write("  ELEVATION:" + elevation + "\n");
        writer.write("  BASE_COVER:" + baseCover + "\n");
        writer.write("  MESO_LAYER {\n");

        List<String> allMesoTokens = new ArrayList<>();
        for (int my = 0; my < MESO_SIZE; my++) {
            for (int mx = 0; mx < MESO_SIZE; mx++) {
                // Wir runden den Wert (falls die Filter leichte Kommazustände erzeugt haben)
                int val = (int) Math.round(mesoGrid[cy * MESO_SIZE + my][cx * MESO_SIZE + mx]);
                
                String token = switch (val) {
                    case TerrainProcessingCore.TYPE_SALTWATER -> "SW"; // 0 -> Salzwasser
                    case TerrainProcessingCore.TYPE_WATER     -> "WA"; // 1 -> Süßwasser
                    case TerrainProcessingCore.TYPE_COAST     -> "SA"; // 2 -> Sand strand
                    case TerrainProcessingCore.TYPE_PLAINS    -> "GR"; // 3 -> Grasland
                    case TerrainProcessingCore.TYPE_MOUNTAIN  -> "RK"; // 4 -> Kahler Fels
                    default -> "SW";
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
