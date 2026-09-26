package org.socialworld.tools.glblTerrainEditor;

import java.util.Random;

public class TerrainTemplateEngine {

    // Bit-Zuweisungen für die disjunkten Richtungen
    private static final int BIT_NORD = 1; // 0001
    private static final int BIT_OST  = 2; // 0010
    private static final int BIT_SUED = 4; // 0100
    private static final int BIT_WEST = 8; // 1000

    /**
     * Baut das Meso-Gitter über ein zufallsbasiertes, aber durch ein 
     * Bitmasken-Sperrraster geschütztes Richtungs-Template auf.
     */
    public static double[][] createTemplateMesoGrid(int[][] macroGrid, int mapW, int mapH, int mesoSize) {
        int totalW = mapW * mesoSize;
        int totalH = mapH * mesoSize;
        double[][] templateGrid = new double[totalH][totalW];

        // Dieses Kontroll-Grid merkt sich pro 32x32 Zelle die entnommenen Richtungs-Dreiecke
        int[][] sperrGrid = new int[mapH][mapW]; 
        
        // Globaler Würfel für die Richtungsentscheidung
        Random rand = new Random();

        // 1. SCHRITT: Vor-Belegung des Meso-Gitters mit dem Standard-Makroterrain
        for (int y = 0; y < totalH; y++) {
            for (int x = 0; x < totalW; x++) {
                templateGrid[y][x] = macroGrid[y / mesoSize][x / mesoSize];
            }
        }

        // 2. SCHRITT: Das Makro-Grid in 2x2 Clustern scannen, um Übergänge auszuwürfeln
        // Wir wandern durch die Welt und entscheiden dynamisch, wer wohin rieselt
        for (int cy = 0; cy < mapH - 1; cy++) {
            for (int cx = 0; cx < mapW - 1; cx++) {
                
                int typeCurrent = macroGrid[cy][cx];
                int typeOsten   = macroGrid[cy][cx + 1];
                int typeSueden  = macroGrid[cy + 1][cx];

                // --- FALL A: Horizontaler Übergang (Ich und mein rechter Nachbar) ---
                if (typeCurrent != typeOsten) {
                    // Würfeln: Rieselt der Linke nach rechts, oder der Rechte nach links?
                    if (rand.nextBoolean()) {
                        // Current (links) rieselt nach rechts (Osten)
                        // Prüfe vorher, ob die rechte Zelle in ihrer WEST-Flanke noch frei ist!
                        if ((sperrGrid[cy][cx + 1] & BIT_WEST) == 0) {
                            injectKeil(templateGrid, cx + 1, cy, mesoSize, typeCurrent, BIT_WEST); // Injiziere West-Keil in Rechts-Zelle
                            sperrGrid[cy][cx + 1] |= BIT_WEST; // West-Flanke der rechten Zelle sperren
                        }
                    } else {
                        // Osten (rechts) rieselt nach links (Current)
                        // Prüfe vorher, ob meine eigene OST-Flanke noch frei ist!
                        if ((sperrGrid[cy][cx] & BIT_OST) == 0) {
                            injectKeil(templateGrid, cx, cy, mesoSize, typeOsten, BIT_OST); // Injiziere Ost-Keil in meine Zelle
                            sperrGrid[cy][cx] |= BIT_OST; // Meine Ost-Flanke sperren
                        }
                    }
                }

                // --- FALL B: Vertikaler Übergang (Ich und mein unterer Nachbar) ---
                if (typeCurrent != typeSueden) {
                    // Würfeln: Rieselt der Obere nach unten, oder der Untere nach oben?
                    if (rand.nextBoolean()) {
                        // Current (oben) rieselt nach unten (Süden)
                        // Prüfe vorher, ob die untere Zelle in ihrer NORD-Flanke noch frei ist!
                        if ((sperrGrid[cy + 1][cx] & BIT_NORD) == 0) {
                            injectKeil(templateGrid, cx, cy + 1, mesoSize, typeCurrent, BIT_NORD); // Injiziere Nord-Keil in Unten-Zelle
                            sperrGrid[cy + 1][cx] |= BIT_NORD; // Nord-Flanke der unteren Zelle sperren
                        }
                    } else {
                        // Süden (unten) rieselt nach oben (Current)
                        // Prüfe vorher, ob meine eigene SÜD-Flanke noch frei ist!
                        if ((sperrGrid[cy][cx] & BIT_SUED) == 0) {
                            injectKeil(templateGrid, cx, cy, mesoSize, typeSueden, BIT_SUED); // Injiziere Süd-Keil in meine Zelle
                            sperrGrid[cy][cx] |= BIT_SUED; // Meine Süd-Flanke sperren
                        }
                    }
                }
            }
        }
        return templateGrid;
    }

    /**
     * Version 3.1: Lückenlose Keil-Injektion.
     * Nutzt inklusive Operatoren (<=, >=), damit die Dreiecke nahtlos 
     * auf Kante gelegt werden und keine feinen Hintergrundlinien mehr entstehen.
     */
    private static void injectKeil(double[][] grid, int cx, int cy, int size, int terrainId, int richtungsBit) {
        int startX = cx * size;
        int startY = cy * size;

        // Grenzwert für das Eindringen in die Kachel (Erhöht auf size/2, damit es an den Flanken schließt)
        int limit = size / 2; 

        for (int my = 0; my < size; my++) {
            for (int mx = 0; mx < size; mx++) {
                
                boolean insideKeil = false;

                switch (richtungsBit) {
                    case BIT_WEST ->  // Einströmen von links: Schließt jetzt nahtlos an den Rändern an
                        insideKeil = (mx <= my) && (mx <= (size - 1 - my)) && (mx < limit);
                    case BIT_OST ->   // Einströmen von rechts
                        insideKeil = (mx >= my) && (mx >= (size - 1 - my)) && (mx >= (size - limit));
                    case BIT_NORD ->  // Einströmen von oben
                        insideKeil = (my <= mx) && (my <= (size - 1 - mx)) && (my < limit);
                    case BIT_SUED ->  // Einströmen von unten
                        insideKeil = (my >= mx) && (my >= (size - 1 - mx)) && (my >= (size - limit));
                }

                if (insideKeil) {
                    grid[startY + my][startX + mx] = terrainId;
                }
            }
        }
    }
}
