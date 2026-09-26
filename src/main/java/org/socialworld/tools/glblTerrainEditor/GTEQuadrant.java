package org.socialworld.tools.glblTerrainEditor;

/**
 * Hilfsklasse zur Kapselung der Quadranten-Logik für das 16x16 Makro-Sichtfenster.
 * Verhindert redundanten Code in den Hauptklassen des GlobalTerrainEditors.
 */
public class GTEQuadrant {
    
    /**
     * Die feste Kachelgröße eines einzelnen Quadranten (16x16 Makro-Zellen).
     */
    public static final int QUADRANT_SIZE = 16;

    /**
     * Rechnet eine lokale Sichtfenster-Koordinate (0..15) zusammen mit dem
     * aktiven Quadranten-Offset (0 oder 16) in die echte globale Map-Koordinate (0..31) um.
     */
    public static int toGlobal(int localCoord, int offset) {
        return localCoord + offset;
    }

    /**
     * Rechnet eine globale Map-Koordinate (0..31) in die relative Position (0..15)
     * innerhalb des aktuell aktiven Quadranten um.
     */
    public static int toLocal(int globalCoord, int offset) {
        return globalCoord - offset;
    }

    /**
     * Prüft, ob eine globale Koordinate überhaupt im aktuell sichtbaren Quadranten liegt.
     */
    public static boolean isVisible(int globalCoord, int offset) {
        int local = toLocal(globalCoord, offset);
        return local >= 0 && local < QUADRANT_SIZE;
    }
}
