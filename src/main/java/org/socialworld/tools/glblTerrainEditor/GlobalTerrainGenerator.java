package org.socialworld.tools.glblTerrainEditor;

import java.io.File;
import java.util.List;
import java.util.Random;


public class GlobalTerrainGenerator {

    private static final int MAP_WIDTH = 32;
    private static final int MAP_HEIGHT = 32;
    private static final int MESO_SIZE = 81;

    public static void main(String[] args) {
        System.out.println("=== GlobalTerrainGenerator Konsolen-Modus ===");
        
        // 1. PROFIL-AUSWAHL
        // Optionen: KONTINENTAL, ARCHIPEL, OEDLAND, NORDISCH
        GTEWorldProfile gewaehltesProfil = GTEWorldProfile.KONTINENTAL;
        
        System.out.println("Starte prozedurale Generierung für Profil: " + gewaehltesProfil);
        long startTime = System.currentTimeMillis();

        try {
            // 2. Nutzt die konsolidierte RAM-Routine
            MacroMap generatedMap = generateWorldInMemory(gewaehltesProfil);
            
            // --- NEU: DYNAMISCHER ZEITSTEMPEL FÜR DEN DATEINAMEN ---
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new java.util.Date());
            
            // Format des Dateinamens: z.B. world_kontinental_20260926_230215.map
            String outputFileName = "world_" + gewaehltesProfil.name().toLowerCase() + "_" + timestamp + ".map";
            File outputFile = new File(outputFileName);
            // -------------------------------------------------------
            
            System.out.println("Meso-Filter und RAM-Initialisierung abgeschlossen.");
            System.out.println("Exporte Karte in Datei: " + outputFile.getAbsolutePath());
            
            // 4. Nutzt den originalen Exporter
            GlobalTerrainExporter.exportMap(generatedMap, outputFile);
            
            long endTime = System.currentTimeMillis();
            System.out.println("=== ERFOLG ===");
            System.out.println("Die Welt wurde in " + (endTime - startTime) + " ms erfolgreich generiert und gespeichert!");
            
        } catch (Exception e) {
            System.err.println("!!! FEHLER WÄHREND DER GENERIERUNG !!!");
            e.printStackTrace();
        }
    }


    
    
 ////////////////////////////////////////////////////////////////////////////////
    
    /**
     * NEU: Generiert eine komplette Welt basierend auf dem gewählten Profil direkt im RAM.
     * Nutzt schlanke Sub-Methoden für maximale Übersichtlichkeit und Wartbarkeit.
     */
    public static MacroMap generateWorldInMemory(GTEWorldProfile profile) {
        int w = MAP_WIDTH; int h = MAP_HEIGHT;  int mesoSize = 81;
        int totalW = MAP_WIDTH * MESO_SIZE;
        int totalH = MAP_HEIGHT * MESO_SIZE;
       
        // 1. Makro-Basis generieren & Details einstreuen
        int[][] macro = TerrainProcessingCore.generateMacroGrid(w, h, profile);
        
        // 2. Offenes Meer sichern & Binnenseen konvertieren
        boolean[][] isOpenOcean = applyFloodFillOcean(macro, w, h);
        
        // 3. Übergänge (Küste, Schotter) berechnen
        int[][] finalMacro = calculateTransitions(macro, isOpenOcean, w, h);
        
        // 4. 81x81 Meso-Ebene über Filter hochrechnen
        double[][] meso = TerrainTemplateEngine.createTemplateMesoGrid(finalMacro, w, h, mesoSize);
        
        // 4.1: Interner Rückbau bei hoher Eigen-Dominanz (>60%)
        meso = TerrainProcessingCore.applyLocalCornerInfusion(meso, w, h, mesoSize);

        // 4.2 : Inter-zelluläre Brückenbildung basierend auf den echten Nachbarpixeln!
        meso = TerrainProcessingCore.applyInterCellularCornerBridge(meso, w, h, mesoSize);
 
        // 4.3 : das grid-Zentrum mit dominantem Terrain belegen
        meso = TerrainProcessingCore.dissolveCenterChokePoints(meso, w, h, mesoSize);

        // 4.4: Strukturelle Ergänzungsterrains (Blobs) pro Profil einstreuen
        meso = TerrainProcessingCore.applyMesoTerrainInfusions(meso, w, h, mesoSize, profile);

        // 4.5 : Der Zufalls-Wobble-Filter für organische Kanten
        meso = TerrainProcessingCore.applyRandomWobbleFilter(meso, totalW, totalH);
 
        // 4.6 : Der zelluläre Weichzeichner für die finalen Übergänge
        meso = TerrainMathUtils.applyCellularBlurFilter(meso, totalW, totalH, 2);

        // 5. Objekte im RAM initialisieren
        MacroMap finalMap = initializeFinalMacroMap(finalMacro, meso, w, h);

        // =========================================================================
        // prozedurale Nacharbeiten auf Mikro- und Meso-Ebene im RAM
        // =========================================================================
  
        // 6.1: Mikro-Grenzfluss-Filter zur Auflockerung der Kanten im 1m-Raster
        TerrainProcessingDetail.applyMicroBoundaryFlow(finalMap);

        // 6.2: Zukünftige Mikro-Features (z.B. Pfade, Flüsse oder Erosion)
        TerrainProcessingDetail.carveMicroFeatures(finalMap);
        
        // 6.3: Kronendach (Meso) und Unterholz (Mikro) mit Pflanzen befüllen
        TerrainProcessingDetail.populateVegetation(finalMap);

        // 7. Finale, voll ausgestattete Map zurückgeben
        return finalMap;
    }

 
    
    private static boolean[][] applyFloodFillOcean(int[][] grid, int w, int h) {
        boolean[][] isOpenOcean = new boolean[h][w];
        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        for (int x = 0; x < w; x++) {
            if (grid[0][x] == TerrainProcessingCore.TYPE_SALTWATER) { isOpenOcean[0][x] = true; queue.add(new int[]{x, 0}); }
            if (grid[h-1][x] == TerrainProcessingCore.TYPE_SALTWATER) { isOpenOcean[h-1][x] = true; queue.add(new int[]{x, h-1}); }
        }
        for (int y = 0; y < h; y++) {
            if (grid[y][0] == TerrainProcessingCore.TYPE_SALTWATER) { isOpenOcean[y][0] = true; queue.add(new int[]{0, y}); }
            if (grid[y][w-1] == TerrainProcessingCore.TYPE_SALTWATER) { isOpenOcean[y][w-1] = true; queue.add(new int[]{w-1, y}); }
        }
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] d : dirs) {
                int nx = curr[0] + d[0]; int ny = curr[1] + d[1];
                if (nx >= 0 && nx < w && ny >= 0 && ny < h && grid[ny][nx] == TerrainProcessingCore.TYPE_SALTWATER && !isOpenOcean[ny][nx]) {
                    isOpenOcean[ny][nx] = true; queue.add(new int[]{nx, ny});
                }
            }
        }
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (grid[y][x] == TerrainProcessingCore.TYPE_SALTWATER && !isOpenOcean[y][x]) grid[y][x] = TerrainProcessingCore.TYPE_WATER;
            }
        }
        return isOpenOcean;
    }

    private static int[][] calculateTransitions(int[][] grid, boolean[][] isOpenOcean, int w, int h) {
        Random rand = new Random();
        int[][] finalGrid = new int[h][w];
        for (int y = 0; y < h; y++) System.arraycopy(grid[y], 0, finalGrid[y], 0, w);
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
            	if (grid[y][x] == TerrainProcessingCore.TYPE_PLAINS || grid[y][x] == TerrainProcessingCore.TYPE_SHRUBLAND) {
                    if (grid[y+1][x] == TerrainProcessingCore.TYPE_MOUNTAIN || grid[y-1][x] == TerrainProcessingCore.TYPE_MOUNTAIN || 
                        grid[y][x+1] == TerrainProcessingCore.TYPE_MOUNTAIN || grid[y][x-1] == TerrainProcessingCore.TYPE_MOUNTAIN || 
                        grid[y+1][x] == TerrainProcessingCore.TYPE_SCREE    || grid[y-1][x] == TerrainProcessingCore.TYPE_SCREE) {
                        if (rand.nextDouble() < 0.40) finalGrid[y][x] = TerrainProcessingCore.TYPE_GRAVEL;
                    }
                }
            }
        }
        return finalGrid;
    }


    private static MacroMap initializeFinalMacroMap(int[][] finalMacro, double[][] meso, int w, int h) {
        MacroMap finalMap = new MacroMap(w, h);
        for (int cy = 0; cy < h; cy++) {
            for (int cx = 0; cx < w; cx++) {
                MacroMapCell cell = finalMap.getCell(cx, cy); int type = finalMacro[cy][cx];
                if (type == TerrainProcessingCore.TYPE_SALTWATER)    { cell.setReferenceElevation(0.0);    cell.setCoverType("SALTWATER"); }
                else if (type == TerrainProcessingCore.TYPE_WATER)    { cell.setReferenceElevation(115.0);  cell.setCoverType("WATER"); }
                else if (type == TerrainProcessingCore.TYPE_COAST)    { cell.setReferenceElevation(15.0);   cell.setCoverType("SAND"); }
                else if (type == TerrainProcessingCore.TYPE_PLAINS)   { cell.setReferenceElevation(120.0);  cell.setCoverType("GRASS"); }
                else if (type == TerrainProcessingCore.TYPE_MOUNTAIN) { cell.setReferenceElevation(850.0);  cell.setCoverType("ROCK"); }
                else if (type == TerrainProcessingCore.TYPE_SWAMP)    { cell.setReferenceElevation(90.0);   cell.setCoverType("MUD"); }
                else if (type == TerrainProcessingCore.TYPE_GRAVEL)   { cell.setReferenceElevation(250.0);  cell.setCoverType("CRUSHEDROCK"); }
                else if (type == TerrainProcessingCore.TYPE_SCREE)    { cell.setReferenceElevation(550.0);  cell.setCoverType("STONES"); }
                else if (type == TerrainProcessingCore.TYPE_FOREST_FLOOR) { cell.setReferenceElevation(130.0); cell.setCoverType("MOSS"); }
                else if (type == TerrainProcessingCore.TYPE_WOODLAND) { cell.setReferenceElevation(140.0);  cell.setCoverType("FOLIAGE"); }
                else if (type == TerrainProcessingCore.TYPE_SHRUBLAND){ cell.setReferenceElevation(135.0);  cell.setCoverType("BRUSHWOOD"); }
                else if (type == TerrainProcessingCore.TYPE_WASTELAND){ cell.setReferenceElevation(110.0);  cell.setCoverType("ASH"); }
                else if (type == TerrainProcessingCore.TYPE_SNOW)     { cell.setReferenceElevation(950.0);  cell.setCoverType("SNOW"); }
                else if (type == TerrainProcessingCore.TYPE_ICE)      { cell.setReferenceElevation(1050.0); cell.setCoverType("ICE"); }

                for (int my = 0; my < 81; my++) {
                    for (int mx = 0; mx < 81; mx++) {
                        int val = (int) Math.round(meso[cy * 81 + my][cx * 81 + mx]);
                        if (val < 0) val = 0; if (val > 13) val = 13;
                        org.socialworld.attributes.GroundMaterial mat = org.socialworld.attributes.GroundMaterial.fromGteId(val);
                        cell.setMesoTerrain(mx, my, mat.name().toUpperCase());
                    }
                }
            }
        }
        return finalMap;
    }

    
    
}
