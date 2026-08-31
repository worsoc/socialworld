package org.socialworld.tools.glblTerrainEditor;

import java.util.HashMap;
import java.util.Map;

/**
 * Speicheroptimierte Version der MacroMapCell.
 * Nutzt eine HashMap für die Strauch-Mischungen (Sparse Storage),
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

    // 2. VEGETATIONS-LAYER
    private final String[][] mesoBaum; 
    
    // Key ist ein komprimierter Integer-Index aus mx und my.
    // Value wird erst erzeugt, wenn tatsächlich ein Strauch gezeichnet wird.
    private final Map<Integer, String[][]> mesoStrauchMap;

    public MacroMapCell(int gridX, int gridY, double initialElevation, String coverType) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.referenceElevation = initialElevation;
        this.coverType = coverType;
        
        this.mesoTerrain = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mesoBaum = new String[MESO_GRID_SIZE][MESO_GRID_SIZE];
        this.mikroTerrainDelta = new byte[MESO_GRID_SIZE * 9][MESO_GRID_SIZE * 9];
        
        // Initialisiere die leere Map – verbraucht nahezu 0 RAM für unbewachsene Kacheln!
        this.mesoStrauchMap = new HashMap<>();
        
        // Initialisierung des Basis-Geländes
        for (int x = 0; x < MESO_GRID_SIZE; x++) {
            for (int y = 0; y < MESO_GRID_SIZE; y++) {
                mesoTerrain[x][y] = coverType;
                mesoBaum[x][y] = "KEIN_BAUM";
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

    // --- GETTER & SETTER FÜR DIE NEUE STRUKTUR (INTERFACE BLEIBT GLEICH!) ---

    public String getMesoTerrain(int mx, int my) { return mesoTerrain[mx][my]; }
    public void setMesoTerrain(int mx, int my, String type) { this.mesoTerrain[mx][my] = type; }

    public String getMesoBaum(int mx, int my) { return mesoBaum[mx][my]; }
    public void setMesoBaum(int mx, int my, String type) { this.mesoBaum[mx][my] = type; }

    /**
     * Holt eine spezifische Strauchart aus der Mischung eines Meso-Feldes.
     */
    public String getMesoStrauchAusMischung(int mx, int my, int localX, int localY) {
        int key = getMapKey(mx, my);
        String[][] schablone = mesoStrauchMap.get(key);
        
        // Wenn für dieses Meso-Feld noch nie etwas gezeichnet wurde, ist es leer
        if (schablone == null) {
            return "KEIN_STRAUCH";
        }
        return schablone[localX][localY];
    }

    /**
     * Setzt eine Strauchart in die Mischung eines Meso-Feldes.
     * Erzeugt die 9x9 Schablone dynamisch "on demand", wenn gezeichnet wird.
     */
    public void setMesoStrauchInMischung(int mx, int my, int localX, int localY, String strauchType) {
        int key = getMapKey(mx, my);
        String[][] schablone = mesoStrauchMap.get(key);
        
        // Wenn der Nutzer "KEIN_STRAUCH" löschen will und nichts da ist -> abbrechen
        if (schablone == null && strauchType.equals("KEIN_STRAUCH")) {
            return;
        }

        // Lazy Initialization: Erst beim ersten echten Pinselstrich erzeugen wir das 9x9 Array
        if (schablone == null) {
            schablone = new String[9][9];
            for (int lx = 0; lx < 9; lx++) {
                for (int ly = 0; ly < 9; ly++) {
                    schablone[lx][ly] = "KEIN_STRAUCH";
                }
            }
            mesoStrauchMap.put(key, schablone);
        }

        schablone[localX][localY] = strauchType;

        // Optimierung: Wenn die Schablone komplett wieder auf "KEIN_STRAUCH" radiert wurde,
        // löschen wir sie aus der Map, um den RAM wieder komplett freizugeben!
        if (strauchType.equals("KEIN_STRAUCH") && istSchabloneLeer(schablone)) {
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
    private boolean istSchabloneLeer(String[][] schablone) {
        for (int lx = 0; lx < 9; lx++) {
            for (int ly = 0; ly < 9; ly++) {
                if (!schablone[lx][ly].equals("KEIN_STRAUCH")) {
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
     * Eine Analyse-Funktion für den Abgleich mit externen Tools.
     * Löst die komprimierte interne HashMap- und Delta-Struktur für eine 
     * exakte 1m-Koordinate sofort in Klartext auf.
     *
     * @param globalMikroX Die X-Koordinate im 1m-Raster (0 bis 728)
     * @param globalMikroY Die Y-Koordinate im 1m-Raster (0 bis 728)
     * @return Ein lesbarer Info-String über alle Layer an dieser Stelle
     */
    public String queryRasterCellInfo(int globalMikroX, int globalMikroY) {
        // 1. Berechne aus den globalen 1m-Koordinaten die zugehörigen Meso-Koordinaten (0-80)
        int mx = globalMikroX / 9;
        int my = globalMikroY / 9;

        // 2. Berechne die lokalen 1m-Koordinaten (0-8) innerhalb dieses Meso-Kästchens
        int lx = globalMikroX % 9;
        int ly = globalMikroY % 9;

        // --- LAYER 1: HÖHE ---
        double hoehe = this.getReferenceElevation();

        // --- LAYER 2: TERRAIN (Berücksichtigt die TERRAIN_DELTA Positivliste)
        byte deltaCode = this.getMikroTerrainDelta(globalMikroX, globalMikroY);
        String terrain = (deltaCode == 0) ? this.getMesoTerrain(mx, my) : GTERenderColorPalette.getTerrainNameFromCode(deltaCode);

        // --- LAYER 3: BAUM (Meso-Ebene) ---
        String baum = this.getMesoBaum(mx, my);

        // --- LAYER 4: STRAUCH (Aus der speicheroptimierten HashMap) ---
        String strauch = this.getMesoStrauchAusMischung(mx, my, lx, ly);

        // Formatierten Antwort-String für dein anderes Tool zusammenbauen
        return String.format(
            "Kachel-Info bei 1m-Position [%d, %d]:\n" +
            "  -> Makro-Höhe: %.2fm\n" +
            "  -> Befindet sich in Meso-Zelle (9m): [%d, %d]\n" +
            "  -> Echtes Terrain: %s %s\n" +
            "  -> Kronendach (Baum): %s\n" +
            "  -> Unterholz (Strauch): %s",
            globalMikroX, globalMikroY, hoehe, mx, my, terrain, 
            (deltaCode != 0 ? "(Generiert via TERRAIN_DELTA)" : "(Geerbt von Meso)"),
            baum, strauch
        );
    }


}
