package org.socialworld.tools.glblTerrainEditor;

import java.util.Random;

public class TerrainProcessingCore {

    public static final int TYPE_SALTWATER = 0;
    public static final int TYPE_WATER = 1;
    public static final int TYPE_COAST = 2;
    public static final int TYPE_PLAINS = 3;
    public static final int TYPE_MOUNTAIN = 4;

    /**
     * Baut das Meso-Gitter über echtes, validiertes Ecken-Wachstum auf.
     * Ein Terrain rieselt NUR ein, wenn es in den direkt anliegenden Ecken der Nachbar-Raster existiert!
     */
    public static double[][] buildInitialMesoGrid(int[][] macroGrid, int mapW, int mapH, int mesoSize) {
        int totalW = mapW * mesoSize;
        int totalH = mapH * mesoSize;
        double[][] mesoGrid = new double[totalH][totalW];

        // 1. Grundbefüllung mit dem eigenen Macro-Typ
        for (int y = 0; y < totalH; y++) {
            for (int x = 0; x < totalW; x++) {
                mesoGrid[y][x] = macroGrid[y / mesoSize][x / mesoSize];
            }
        }

        // 2. Jede Macro-Zelle analysieren und Ecken-Wolken NUR bei echten Übergängen einströmen lassen
        for (int cy = 0; cy < mapH; cy++) {
            for (int cx = 0; cx < mapW; cx++) {
                int myType = macroGrid[cy][cx];
                Random cellRand = new Random(cx * 3413L + cy * 7919L);
                int eckenZaehler = 0;

                // --- ECKE 1: OBEN-LINKS (Prüfe Nord, West und Nord-West) ---
                if (cy > 0 && cx > 0 && eckenZaehler < 2) {
                    int typeN = macroGrid[cy - 1][cx];
                    int typeW = macroGrid[cy][cx - 1];
                    int typeNW = macroGrid[cy - 1][cx - 1];
                    
                    // Nur einrieseln, wenn das fremde Terrain in den anliegenden Nachbar-Zellen existiert
                    if (typeN != myType && (typeN == typeW || typeN == typeNW)) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeN, 0, 0, cellRand);
                        eckenZaehler++;
                    } else if (typeW != myType && typeW == typeNW) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeW, 0, 0, cellRand);
                        eckenZaehler++;
                    }
                }

                // --- ECKE 2: OBEN-RECHTS (Prüfe Nord, Ost und Nord-Ost) ---
                if (cy > 0 && cx < mapW - 1 && eckenZaehler < 2) {
                    int typeN = macroGrid[cy - 1][cx];
                    int typeO = macroGrid[cy][cx + 1];
                    int typeNE = macroGrid[cy - 1][cx + 1];

                    if (typeN != myType && (typeN == typeO || typeN == typeNE)) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeN, mesoSize - 1, 0, cellRand);
                        eckenZaehler++;
                    } else if (typeO != myType && typeO == typeNE) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeO, mesoSize - 1, 0, cellRand);
                        eckenZaehler++;
                    }
                }

                // --- ECKE 3: UNTEN-LINKS (Prüfe Süd, West und Süd-West) ---
                if (cy < mapH - 1 && cx > 0 && eckenZaehler < 2) {
                    int typeS = macroGrid[cy + 1][cx];
                    int typeW = macroGrid[cy][cx - 1];
                    int typeSW = macroGrid[cy + 1][cx - 1];

                    if (typeS != myType && (typeS == typeW || typeS == typeSW)) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeS, 0, mesoSize - 1, cellRand);
                        eckenZaehler++;
                    } else if (typeW != myType && typeW == typeSW) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeW, 0, mesoSize - 1, cellRand);
                        eckenZaehler++;
                    }
                }

                // --- ECKE 4: UNTEN-RECHTS (Prüfe Süd, Ost und Süd-Ost) ---
                if (cy < mapH - 1 && cx < mapW - 1 && eckenZaehler < 2) {
                    int typeS = macroGrid[cy + 1][cx];
                    int typeO = macroGrid[cy][cx + 1];
                    int typeSO = macroGrid[cy + 1][cx + 1];

                    if (typeS != myType && (typeS == typeO || typeS == typeSO)) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeS, mesoSize - 1, mesoSize - 1, cellRand);
                        eckenZaehler++;
                    } else if (typeO != myType && typeO == typeSO) {
                        growFromCorner(mesoGrid, cx, cy, mesoSize, typeO, mesoSize - 1, mesoSize - 1, cellRand);
                        eckenZaehler++;
                    }
                }
            }
        }
        return mesoGrid;
    }

    /**
     * Lässt das Terrain kreisförmig/diagonal von einem exakten Eckpunkt aus nach innen wachsen.
     */
    private static void growFromCorner(double[][] grid, int cx, int cy, int size, int neighborType, int cornerX, int cornerY, Random rand) {
        // DYNAMISCHE MENGEN-STAFFELUNG: Menge variiert pro Zelle stark (600 bis 2200 Kacheln)
        int maxKacheln = 600 + rand.nextInt(1600); 
        int platziert = 0;

        int startX = cx * size;
        int startY = cy * size;

        // Wir laufen in Wellen über die Kachel, bis die geseedete Zielmenge erreicht ist
        for (int attempt = 0; attempt < 20 && platziert < maxKacheln; attempt++) {
            for (int my = 0; my < size && platziert < maxKacheln; my++) {
                for (int mx = 0; mx < size && platziert < maxKacheln; mx++) {
                    
                    int gx = startX + mx;
                    int gy = startY + my;

                    if (grid[gy][gx] == neighborType) continue;

                    // ECHTER ECKEN-FOKUS: Berechne den echten euklidischen Abstand zum Eckpunkt (0.0 bis ~1.41)
                    double dx = (double) Math.abs(mx - cornerX) / (size - 1);
                    double dy = (double) Math.abs(my - cornerY) / (size - 1);
                    double distanceFactor = Math.sqrt(dx * dx + dy * dy); 

                    // Wahrscheinlichkeits-Glocke: Extrem hoch in der Ecke, flacht kreisrund ab
                    double spawnChance = Math.max(0.0, 1.0 - (distanceFactor * 1.8));

                    // Perlin-Rauschen aus der Mathe-Klasse bündelt den Zufall zu zusammenhängenden Formen
                    double blobNoise = (TerrainMathUtils.noise2D(gx * 0.12, gy * 0.12) + 1.0) / 2.0;

                    if (rand.nextDouble() * 0.6 + blobNoise * 0.4 < spawnChance) {
                        grid[gy][gx] = neighborType;
                        platziert++;
                    }
                }
            }
        }
    }

    /**
     * Erzeugt das grobe 32x32 Macro-Raster (Klumpenbildung).
     */
    public static int[][] generateMacroGrid(int width, int height) {
        int[][] grid = new int[height][width]; Random rand = new Random();
        for (int y = 0; y < height; y++) { for (int x = 0; x < width; x++) grid[y][x] = TYPE_SALTWATER; }
        
        int numSeeds = 6 + rand.nextInt(4); 
        for (int i = 0; i < numSeeds; i++) grid[5 + rand.nextInt(22)][5 + rand.nextInt(22)] = TYPE_PLAINS;

        for (int growth = 0; growth < 5; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_PLAINS) {
                        if (rand.nextDouble() < 0.65) tempGrid[y+1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.65) tempGrid[y-1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.65) tempGrid[y][x+1] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.65) tempGrid[y][x-1] = TYPE_PLAINS;
                    }
                }
            }
            grid = tempGrid;
        }

        for (int y = 2; y < height - 2; y++) {
            for (int x = 2; x < width - 2; x++) {
                if (grid[y][x] == TYPE_PLAINS) {
                    if (grid[y+1][x] == TYPE_PLAINS && grid[y-1][x] == TYPE_PLAINS && grid[y][x+1] == TYPE_PLAINS && grid[y][x-1] == TYPE_PLAINS) {
                        if (rand.nextDouble() < 0.45) grid[y][x] = TYPE_MOUNTAIN;
                    }
                }
            }
        }

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == TYPE_SALTWATER) {
                    if (grid[y+1][x] >= TYPE_PLAINS || grid[y-1][x] >= TYPE_PLAINS || grid[y][x+1] >= TYPE_PLAINS || grid[y][x-1] >= TYPE_PLAINS) {
                        grid[y][x] = TYPE_COAST;
                    }
                }
            }
        }
        return grid;
    }
    
  
    /**
     * Korrigiert & Multi-Terrain-Safe: Lokaler Ecken- und Zentrums-Rückbau.
     * Unterstützt nun ebenfalls alle 14 Terrain-IDs vollautomatisch!
     */
    public static double[][] applyLocalCornerInfusion(double[][] source, int mapW, int mapH, int size) {
        int totalW = mapW * size;
        int totalH = mapH * size;
        double[][] output = new double[totalH][totalW];
        
        for (int y = 0; y < totalH; y++) {
            System.arraycopy(source[y], 0, output[y], 0, totalW);
        }

        java.util.Random rand = new java.util.Random();
        int gesamtKachelnProZelle = size * size; 
        int dominanzSchwelle = (int) (gesamtKachelnProZelle * 0.60); 

        for (int cy = 0; cy < mapH; cy++) {
            for (int cx = 0; cx < mapW; cx++) {
                
                int startX = cx * size;
                int startY = cy * size;

                // KORREKTUR: Zähl-Array von 5 auf 14 erweitert für Schnee, Eis etc.!
                int[] counts = new int[14];
                for (int my = 0; my < size; my++) {
                    for (int mx = 0; mx < size; mx++) {
                        int val = (int) source[startY + my][startX + mx];
                        // KORREKTUR: Bedingung auf < 14 erweitert
                        if (val >= 0 && val < 14) counts[val]++;
                    }
                }
                
                int hauptTerrain = 0; int maxCount = -1;
                for (int i = 0; i < 14; i++) { // KORREKTUR: Zählschleife läuft bis 14
                    if (counts[i] > maxCount) { maxCount = counts[i]; hauptTerrain = i; }
                }

                if (maxCount <= dominanzSchwelle) {
                    continue; 
                }

                // =========================================================================
                // INNEN-LOGIK (Die Growth-Schleifen bleiben identisch, nutzen aber nun das sichere hauptTerrain)
                // =========================================================================
                int[][] ecken = { {0, 0}, {size - 1, 0}, {0, size - 1}, {size - 1, size - 1} };
                for (int[] ecke : ecken) {
                    int cornerMx = ecke[0]; int cornerMy = ecke[1];
                    if (source[startY + cornerMy][startX + cornerMx] != hauptTerrain) {
                        int zielAnzahl = rand.nextInt(501); int platziert = 0;
                        java.util.List<int[]> growthFront = new java.util.ArrayList<>();
                        if (output[startY + cornerMy][startX + cornerMx] != hauptTerrain) {
                            output[startY + cornerMy][startX + cornerMx] = hauptTerrain; platziert++;
                        }
                        growthFront.add(new int[]{cornerMx, cornerMy});
                        int sicherheitsBremse = 0;
                        while (platziert < zielAnzahl && !growthFront.isEmpty() && sicherheitsBremse < 2000) {
                            sicherheitsBremse++;
                            int[] basePixel = growthFront.get(rand.nextInt(growthFront.size()));
                            int dir = rand.nextInt(4); int nextMx = basePixel[0]; int nextMy = basePixel[1];
                            if (dir == 0) nextMy--; else if (dir == 1) nextMy++; else if (dir == 2) nextMx--; else if (dir == 3) nextMx++;
                            if (nextMx >= 0 && nextMx < size && nextMy >= 0 && nextMy < size) {
                                int gx = startX + nextMx; int gy = startY + nextMy;
                                if (output[gy][gx] != hauptTerrain) { output[gy][gx] = hauptTerrain; platziert++; growthFront.add(new int[]{nextMx, nextMy}); }
                            }
                        }
                    }
                }

                int centerMx = size / 2; int centerMy = size / 2;
                int zentrumZielAnzahl = rand.nextInt(501); int zentrumPlatziert = 0;
                java.util.List<int[]> zentrumFront = new java.util.ArrayList<>();
                if (output[startY + centerMy][startX + centerMx] != hauptTerrain) {
                    output[startY + centerMy][startX + centerMx] = hauptTerrain; zentrumPlatziert++;
                }
                zentrumFront.add(new int[]{centerMx, centerMy});
                int zentrumBremse = 0;
                while (zentrumPlatziert < zentrumZielAnzahl && !zentrumFront.isEmpty() && zentrumBremse < 2000) {
                    zentrumBremse++;
                    int[] basePixel = zentrumFront.get(rand.nextInt(zentrumFront.size()));
                    int dir = rand.nextInt(4); int nextMx = basePixel[0]; int nextMy = basePixel[1];
                    if (dir == 0) nextMy--; else if (dir == 1) nextMy++; else if (dir == 2) nextMx--; else if (dir == 3) nextMx++;
                    if (nextMx >= 0 && nextMx < size && nextMy >= 0 && nextMy < size) {
                        int gx = startX + nextMx; int gy = startY + nextMy;
                        if (output[gy][gx] != hauptTerrain) { output[gy][gx] = hauptTerrain; zentrumPlatziert++; zentrumFront.add(new int[]{nextMx, nextMy}); }
                    }
                }
            }
        }
        return output;
    }
     
    /**
     * Version 4.5 (10-Pixel Kanten-Scan): Inter-zelluläre Kanten-Mehrheits-Brückenbildung.
     * Scannt von jeder Ecke aus exakt 10 Pixel weit die angrenzenden Außenkanten der Nachbar-Raster.
     * Ermittelt dort das Mehrheits-Terrain. Haben beide Nachbarkanten dieselbe 
     * Terrain-Mehrheit, rieselt genau dieses Terrain mit bis zu 500 Pixeln zusammenhängend ein.
     */
    public static double[][] applyInterCellularCornerBridge(double[][] source, int mapW, int mapH, int size) {
        int totalW = mapW * size;
        int totalH = mapH * size;
        double[][] output = new double[totalH][totalW];
        
        for (int y = 0; y < totalH; y++) {
            System.arraycopy(source[y], 0, output[y], 0, totalW);
        }

        java.util.Random rand = new java.util.Random();
        
        int scanLaenge = 10; 

        // Schleife über alle inneren Macro-Zellen (Sicherheitsabstand zu den Weltgrenzen)
        for (int cy = 1; cy < mapH - 1; cy++) {
            for (int cx = 1; cx < mapW - 1; cx++) {
                
                int startX = cx * size;
                int startY = cy * size;

                // Wir gehen die 4 Ecken dieser aktuellen Macro-Zelle durch
                for (int ecke = 0; ecke < 4; ecke++) {
                    
                    int myCornerX = 0;
                    int myCornerY = 0;
                    
                    // Arrays für die Mehrheitszählung der beiden Nachbarkanten (IDs 0 bis 13)
                    int[] countsNachbar1 = new int[14];
                    int[] countsNachbar2 = new int[14];

                    if (ecke == 0) { // --- OBEN-LINKS ---
                        myCornerX = startX;
                        myCornerY = startY;
                        // Nachbar 1 (Kachel links): Scanne deren rechte Kante von der Ecke 10 Pixel nach unten
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + i][startX - 1];
                            if (val >= 0 && val < 14) countsNachbar1[val]++;
                        }
                        // Nachbar 2 (Kachel oben): Scanne deren untere Kante von der Ecke 10 Pixel nach rechts
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY - 1][startX + i];
                            if (val >= 0 && val < 14) countsNachbar2[val]++;
                        }
                    } 
                    else if (ecke == 1) { // --- OBEN-RECHTS ---
                        myCornerX = startX + size - 1;
                        myCornerY = startY;
                        // Nachbar 1 (Kachel rechts): Scanne deren linke Kante von der Ecke 10 Pixel nach unten
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + i][startX + size];
                            if (val >= 0 && val < 14) countsNachbar1[val]++;
                        }
                        // Nachbar 2 (Kachel oben): Scanne deren untere Kante von der Ecke 10 Pixel nach links
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY - 1][startX + size - 1 - i];
                            if (val >= 0 && val < 14) countsNachbar2[val]++;
                        }
                    } 
                    else if (ecke == 2) { // --- UNTEN-LINKS ---
                        myCornerX = startX;
                        myCornerY = startY + size - 1;
                        // Nachbar 1 (Kachel links): Scanne deren rechte Kante von der Ecke 10 Pixel nach oben
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + size - 1 - i][startX - 1];
                            if (val >= 0 && val < 14) countsNachbar1[val]++;
                        }
                        // Nachbar 2 (Kachel unten): Scanne deren obere Kante von der Ecke 10 Pixel nach rechts
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + size][startX + i];
                            if (val >= 0 && val < 14) countsNachbar2[val]++;
                        }
                    } 
                    else if (ecke == 3) { // --- UNTEN-RECHTS ---
                        myCornerX = startX + size - 1;
                        myCornerY = startY + size - 1;
                        // Nachbar 1 (Kachel rechts): Scanne deren linke Kante von der Ecke 10 Pixel nach oben
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + size - 1 - i][startX + size];
                            if (val >= 0 && val < 14) countsNachbar1[val]++;
                        }
                        // Nachbar 2 (Kachel unten): Scanne deren obere Kante von der Ecke 10 Pixel nach links
                        for (int i = 0; i < scanLaenge; i++) {
                            int val = (int) source[startY + size][startX + size - 1 - i];
                            if (val >= 0 && val < 14) countsNachbar2[val]++;
                        }
                    }

                    // Mehrheitsermittlung über alle 14 IDs
                    int maj1 = 0; int max1 = -1;
                    for (int i = 0; i < 14; i++) { if (countsNachbar1[i] > max1) { max1 = countsNachbar1[i]; maj1 = i; } }

                    int maj2 = 0; int max2 = -1;
                    for (int i = 0; i < 14; i++) { if (countsNachbar2[i] > max2) { max2 = countsNachbar2[i]; maj2 = i; } }

                    // WENN beide Nachbarkanten dieselbe dominante Mehrheit aufweisen, wird die Brücke geschlagen!
                    if (maj1 == maj2) {
                        int brueckenTerrain = maj1;
                       
                             
                            int zielAnzahl = rand.nextInt(501); 
                            int platziert = 0;

                            int localMx = myCornerX % size;
                            int localMy = myCornerY % size;

                            java.util.List<int[]> growthFront = new java.util.ArrayList<>();
                            output[myCornerY][myCornerX] = brueckenTerrain;
                            platziert++;
                            growthFront.add(new int[]{localMx, localMy});

                            int sicherheitsBremse = 0;
                            while (platziert < zielAnzahl && !growthFront.isEmpty() && sicherheitsBremse < 2000) {
                                sicherheitsBremse++;
                                int[] basePixel = growthFront.get(rand.nextInt(growthFront.size()));
                                
                                int dir = rand.nextInt(4);
                                int nextMx = basePixel[0];
                                int nextMy = basePixel[1];

                                if (dir == 0) nextMy--;
                                else if (dir == 1) nextMy++;
                                else if (dir == 2) nextMx--;
                                else if (dir == 3) nextMx++;

                                if (nextMx >= 0 && nextMx < size && nextMy >= 0 && nextMy < size) {
                                    int gx = startX + nextMx;
                                    int gy = startY + nextMy;

                                    if (output[gy][gx] != brueckenTerrain) {
                                        output[gy][gx] = brueckenTerrain;
                                        platziert++;
                                        growthFront.add(new int[]{nextMx, nextMy});
                                    }
                                }
                            }
                        
                    }
                }
                
            }
        }
        return output;
    }
   
    /**
     * Reaktiviert: Der fraktale Wobble-Zerstörer.
     * Nutzt das Perlin-Noise aus den MathUtils, um gerade 45-Grad-Mosaikkanten
     * asymmetrisch in unregelmäßige Wellenlinien zu verzerren.
     */
    public static double[][] applyRandomWobbleFilter(double[][] source, int totalW, int totalH) {
        double[][] output = new double[totalH][totalW];
        java.util.Random rand = new java.util.Random();
        
        // Zufällige Offsets, damit jede Karte ein Unikat wird
        double sX = rand.nextDouble() * 5000.0; 
        double sY = rand.nextDouble() * 5000.0;
        
        for (int y = 0; y < totalH; y++) {
            for (int x = 0; x < totalW; x++) {
                // Liest das Rauschen aus der mathematischen Hilfsklasse aus
                double dx = TerrainMathUtils.noise2D(x * 0.018 + sX, y * 0.018) * 10.0; 
                double dy = TerrainMathUtils.noise2D(y * 0.025, x * 0.025 + sY) * 10.0;
                
                int tx = Math.max(0, Math.min(totalW - 1, (int) Math.round(x + dx)));
                int ty = Math.max(0, Math.min(totalH - 1, (int) Math.round(y + dy)));
                
                output[y][x] = source[ty][tx];
            }
        }
        return output;
    }

}
