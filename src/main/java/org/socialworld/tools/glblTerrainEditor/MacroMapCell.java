/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 *
 */
package org.socialworld.tools.glblTerrainEditor;

import java.util.HashMap;
import java.util.Map;

/**
 * Speicheroptimierte Version der MacroMapCell.
 * Nutzt eine HashMap für die Strauch-Mischungen (Sparse Storage).
 */
public class MacroMapCell {
    private final int gridX;
    private final int gridY;
    private double referenceElevation; 
    private String coverType;           
    
    public static final int MESO_GRID_SIZE = 81; 
    
    // 1. TERRAIN-LAYER
    private final String[][] mesoTerrain; 
    private final byte[][] mikroTerrainDelta; 

    // 2. VEGETATIONS-LAYER (Umgestellt von String auf int für die gteId)
    private final int[][] mesoBaum; 
    
    // Key ist ein komprimierter Integer-Index aus mx und my.
    // Value wird erst erzeugt, wenn tatsächlich ein Strauch gezeichnet wird.
    private final Map<Integer, int[][]> mesoStrauchMap;

    public MacroMapCell(int gridX, int gridY, double initialElevation, String coverType) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.referenceElevation = initialElevation;
        this.coverType = coverType;
        
        this.mesoTerrain = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mesoBaum = new int[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mikroTerrainDelta = new byte[MESO_GRID_SIZE * 9][MESO_GRID_SIZE * 9];
        
        // Initialisiere die leere Map – verbraucht nahezu 0 RAM für unbewachsene Kacheln!
        this.mesoStrauchMap = new HashMap<>();
        
        // Initialisierung des Basis-Geländes
        for (int x = 0; x < MESO_GRID_SIZE; x++) {
            for (int y = 0; y < MESO_GRID_SIZE; y++) {
                mesoTerrain[x][y] = coverType;
                mesoBaum[x][y] = 0; // 0 entspricht der gteId für nothing (kein Baum)
             }
        }
        
        // Initialisierung des Gelände-Deltas: initial 99 für kein Delta, also Erben
        for (int x = 0; x < MESO_GRID_SIZE * 9; x++) {
            for (int y = 0; y < MESO_GRID_SIZE * 9; y++) {
                mikroTerrainDelta[x][y] = 99;  // für Erben von Meso
            }
        }
    }

    /**
     * Hilfsmethode: Rechnet die zweidimensionale Meso-Koordinate (0-80) 
     * in einen eindeutigen eindimensionalen Schlüssel für die Map um.
     */
    private int getMapKey(int mx, int my) {
        return (mx << 16) | (my & 0xFFFF);
    }

    // --- GETTER & SETTER FÜR DIE STRUKTUR ---

    public String getMesoTerrain(int mx, int my) { return mesoTerrain[mx][my]; }
    public void setMesoTerrain(int mx, int my, String type) { this.mesoTerrain[mx][my] = type; }

    public int getMesoBaum(int mx, int my) { return mesoBaum[mx][my]; }
    public void setMesoBaum(int mx, int my, int gteId) { this.mesoBaum[mx][my] = gteId; }

    /**
     * Holt eine spezifische Strauchart (gteId) aus der Mischung eines Meso-Feldes.
     */
    public int getMesoStrauchAusMischung(int mx, int my, int localX, int localY) {
        int key = getMapKey(mx, my);
        int[][] schablone = mesoStrauchMap.get(key);
        
        // Wenn für dieses Meso-Feld noch nie etwas gezeichnet wurde, ist es leer (0 = nothing)
        if (schablone == null) {
            return 0;
        }
        return schablone[localX][localY];
    }

    /**
     * Setzt eine Strauchart (gteId) in die Mischung eines Meso-Feldes.
     * Erzeugt die 9x9 Schablone dynamisch "on demand", wenn gezeichnet wird.
     */
    public void setMesoStrauchInMischung(int mx, int my, int localX, int localY, int strauchGteId) {
        int key = getMapKey(mx, my);
        int[][] schablone = mesoStrauchMap.get(key);
        
        // Wenn der Nutzer "nothing" (0) löschen will und nichts da ist -> abbrechen
        if (schablone == null && strauchGteId == 0) {
            return;
        }

        // Lazy Initialization: Erst beim ersten echten Pinselstrich erzeugen wir das 9x9 Array
        if (schablone == null) {
            schablone = new int[9][9]; // Primitives int-Array wird automatisch mit 0 (nothing) vorinitialisiert
            mesoStrauchMap.put(key, schablone);
        }

        schablone[localX][localY] = strauchGteId;

        // Optimierung: Wenn die Schablone komplett wieder auf 0 ("nothing") radiert wurde,
        // löschen wir sie aus der Map, um den RAM wieder komplett freizugeben!
        if (strauchGteId == 0 && istSchabloneLeer(schablone)) {
            mesoStrauchMap.remove(key);
        }
    }

    /**
     * Prüft schnell, ob ein Meso-Feld überhaupt Strauchdaten besitzt.
     */
    public boolean hatStrauchMischung(int mx, int my) {
        return mesoStrauchMap.containsKey(getMapKey(mx, my));
    }

    /**
     * Interne Hilfsmethode zur Speicher-Bereinigung.
     */
    private boolean istSchabloneLeer(int[][] schablone) {
        for (int lx = 0; lx < 9; lx++) {
            for (int ly = 0; ly < 9; ly++) {
                if (schablone[lx][ly] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // --- MIKRO-TERRAIN (NUR FÜR FLÜSSE / INFRASTRUKTUR) ---
    public byte getMikroTerrainDelta(int gmx, int gmy) { return mikroTerrainDelta[gmx][gmy]; }
    public void setMikroTerrainDelta(int gmx, int gmy, byte terrainCode) { this.mikroTerrainDelta[gmx][gmy] = terrainCode; }

    public double getReferenceElevation() { return referenceElevation; }
    public void setReferenceElevation(double elevation) { this.referenceElevation = elevation; }
    public String getCoverType() { return coverType; }
    public void setCoverType(String coverType) { this.coverType = coverType; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    
    /**
     * Rechnet strukturierte Meso- und Mikro-Koordinaten in das flache, 
     * zweidimensionale 729x729 Mikro-Delta-Gitter der Zelle um.
     */
    public void setMikroTerrainDelta(int mx, int my, int lx, int ly, byte gteId) {
        int globalMikroX = (mx * 9) + lx;
        int globalMikroY = (my * 9) + ly;
        this.setMikroTerrainDelta(globalMikroX, globalMikroY, gteId);
    }
    
    /**
     * Eine Analyse-Funktion für den Abgleich mit externen Tools.
     */
    public String queryRasterCellInfo(int globalMikroX, int globalMikroY) {
        int mx = globalMikroX / 9;
        int my = globalMikroY / 9;
        int lx = globalMikroX % 9;
        int ly = globalMikroY % 9;

        double hoehe = this.getReferenceElevation();

        byte deltaCode = this.getMikroTerrainDelta(globalMikroX, globalMikroY);
        String terrain = (deltaCode == 99) ? this.getMesoTerrain(mx, my) : GTERenderColorPalette.getTerrainNameFromCode(deltaCode);

        // Holt die numerischen IDs aus den geänderten Datenstrukturen
        int baumId = this.getMesoBaum(mx, my);
        int strauchId = this.getMesoStrauchAusMischung(mx, my, lx, ly);

        return String.format(
            "Kachel-Info bei 1m-Position [%d, %d]:\n" +
            "  -> Makro-Höhe: %.2fm\n" +
            "  -> Befindet sich in Meso-Zelle (9m): [%d, %d]\n" +
            "  -> Echtes Terrain: %s %s\n" +
            "  -> Kronendach (Baum ID): %d\n" +
            "  -> Unterholz (Strauch ID): %d",
            globalMikroX, globalMikroY, hoehe, mx, my, terrain, 
            (deltaCode != 99 ? "(Generiert via TERRAIN_DELTA)" : "(Geerbt von Meso)"),
            baumId, strauchId
        );
    }
}
