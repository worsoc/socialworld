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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.socialworld.attributes.GroundMaterial;
import org.socialworld.attributes.VegetationBush;
import org.socialworld.attributes.VegetationTree;

/**
 * Diese Klasse führt prozedurale Nacharbeiten auf der fertig initialisierten MacroMap aus.
 * Alle Berechnungen reichen bis auf die Meso- (9m) und Mikro-Ebene (1m) hinab.
 */
public class TerrainProcessingDetail {

    // Beispielhafte Klartext-Namen für deine Render-Engine
    private static final String BAUM_EICHE      = "EICHE";
    private static final String BAUM_KIEFER     = "KIEFER";
    private static final String BAUM_FICHTE  	= "FICHTE";
    private static final String BAUM_BIRKE      = "BIRKE";
    private static final String BAUM_BUCHE      = "BUCHE";
    private static final String BAUM_WEIDE      = "WEIDE";
   
	private static final String STRAUCH_ZIERSTRAUCH    = "ZIERSTRAUCH";
    private static final String STRAUCH_FARN    = "FARNE";
    private static final String STRAUCH_BEERE   = "BEERENSTRAUCH";
    private static final String STRAUCH_BROMBEERE  = "BROMBEERE";
    private static final String STRAUCH_HEIDEKRAUT  = "HEIDEKRAUT";
    private static final String STRAUCH_GINSTER  = "GINSTER";
/*
    private static final String STRAUCH_FARN    = "WALDFARN";
    private static final String STRAUCH_BEERE   = "BEERENSTRAUCH";
    private static final String STRAUCH_DORNEN  = "DORNENGEBUESCH";
*/
    /**
     * Methode 1: Befüllt das Kronendach (Meso-Ebene) und das Unterholz (Mikro-Ebene)
     * basierend auf dem geerbten Gesteins- oder Bodentyp der jeweiligen Zelle.
     * 
     * @param map Die Referenz auf die im RAM liegende MacroMap
     */
    public static void populateVegetation(MacroMap map) {
        Random rand = new Random();
        int width = map.getWidth();
        int height = map.getHeight();
        int mesoGridSize = MacroMapCell.MESO_GRID_SIZE; // Exakt 81 aus deiner Struktur

        // Wir wandern durch jede Makro-Kachel der Welt
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                MacroMapCell cell = map.getCell(x, y);
                
                // Wir scannen das 81x81 Meso-Gitter dieser Zelle
                for (int mx = 0; mx < mesoGridSize; mx++) {
                    for (int my = 0; my < mesoGridSize; my++) {
                        
                        // Welches Terrain liegt hier vor? (z.B. "WOODLAND", "FOREST_FLOOR")
                        String currentTerrain = cell.getMesoTerrain(mx, my);
                        GroundMaterial currentGround = GroundMaterial.fromName(currentTerrain);
                        
                        // Falls es ein Sand oder Gewässer ist, überspringen wir die Vegetation
                        if (currentGround == GroundMaterial.sand || currentGround == GroundMaterial.saltwater || currentGround == GroundMaterial.water) {
                            continue;
                        }

                        // --- SCHRITT A: KRONENDACH (BAUM-PLATZIERUNG AUF MESO) ---
                        // Je nach Bodentyp würfeln wir die passende Baumart aus
                        if (currentGround == GroundMaterial.moss) {
                            // Dichter, dunkler Nadel-/Mischwald (Hohe Dichte)
                            if (rand.nextDouble() < 0.75) {
                                int baumId = (rand.nextDouble() < 0.80) ? VegetationTree.kiefer.getGteId() : VegetationTree.eiche.getGteId();
                                cell.setMesoBaum(mx, my, baumId);
                            }
                        } 
                        else if (currentGround == GroundMaterial.foliage) {
                            // Lichter Laubwald / Birkenhaine
                            if (rand.nextDouble() < 0.40) {
                                double p = rand.nextDouble();
                                int baumId;
                                if (p < 0.35) baumId = VegetationTree.buche.getGteId();
                                else if (p < 0.5) baumId = VegetationTree.eiche.getGteId();
                                else if (p < 0.65) baumId = VegetationTree.birke.getGteId();
                                else if (p < 0.75) baumId = VegetationTree.weide.getGteId();
                                else if (p < 0.85) baumId = VegetationTree.fichte.getGteId();
                                else baumId = VegetationTree.kiefer.getGteId();
                                cell.setMesoBaum(mx, my, baumId);
                            }
                        }
                        else if (currentGround == GroundMaterial.brushwood) {
                            // Savanne / Steppe: Nur sehr vereinzelte Bäume
                            double p = rand.nextDouble();
                            int baumId = -1;
                            if (p < 0.015) baumId = VegetationTree.eiche.getGteId();
                            else if (p < 0.03) baumId = VegetationTree.buche.getGteId();
                            else if (p < 0.045) baumId = VegetationTree.weide.getGteId();
                            else if (p < 0.06) baumId = VegetationTree.birke.getGteId();
                            
                            if (baumId != -1) {
                                cell.setMesoBaum(mx, my, baumId);
                            }
                        }
                        else if (currentGround == GroundMaterial.grass) {
                            // Gras: Nur sehr vereinzelte Bäume
                            double p = rand.nextDouble();
                            double faktor = rand.nextDouble() * 2;
                            int baumId = -1;
                            if (p < 0.01 * faktor) baumId = VegetationTree.eiche.getGteId();
                            else if (p < 0.02 * faktor) baumId = VegetationTree.birke.getGteId();
                            else if (p < 0.03 * faktor) baumId = VegetationTree.buche.getGteId();
                            else if (p < 0.04 * faktor) baumId = VegetationTree.kiefer.getGteId();
                            else if (p < 0.05 * faktor) baumId = VegetationTree.weide.getGteId();
                            else if (p < 0.06 * faktor) baumId = VegetationTree.fichte.getGteId();
                            
                            if (baumId != -1) {
                                cell.setMesoBaum(mx, my, baumId);
                            }
                        }

                        // --- SCHRITT B: UNTERHOLZ (STRAUCH-PLATZIERUNG AUF MIKRO 1m) ---
                        // Dank deiner HashMap koschtet uns das Erzeugen der Schablone erst bei einem 
                        // Treffer RAM. Wir streuen Farne und Beeren in schattige Waldgebiete.
                        double pTerrain2Brush = rand.nextDouble();
                        if ((currentGround == GroundMaterial.grass && pTerrain2Brush < 0.1) ||
                            (currentGround == GroundMaterial.moss && pTerrain2Brush < 0.4) || 
                            (currentGround == GroundMaterial.mud && pTerrain2Brush < 0.1)) {
                            double faktor = rand.nextDouble();
                                  
                            // Wir prüfen das feine 9x9 Mikro-Gitter innerhalb dieser Meso-Kachel
                            for (int lx = 0; lx < 9; lx++) {
                                for (int ly = 0; ly < 9; ly++) {
                                    double p = rand.nextDouble();
                                    int strauchId = -1;
                                    // Chance für Farne im tiefen feuchten Wald
                                    if (p < 0.15 * faktor) strauchId = VegetationBush.farne.getGteId();
                                    // Chance für Beerensträucher
                                    else if (p < 0.2 * faktor) strauchId = VegetationBush.beerenstrauch.getGteId();
                                    else if (p < 0.25 * faktor) strauchId = VegetationBush.zierstrauch.getGteId();
                                    
                                    if (strauchId != -1) {
                                        cell.setMesoStrauchInMischung(mx, my, lx, ly, strauchId);
                                    }
                                }
                            }
                        } 
                        else if (currentGround == GroundMaterial.ash || currentGround == GroundMaterial.crushedrock) {
                            // Im Ödland / Schotter wachsen keine saftigen Beeren, sondern Dornensträucher
                            pTerrain2Brush = rand.nextDouble();
                            if (pTerrain2Brush < 0.2) {
                                for (int lx = 0; lx < 9; lx++) {
                                    for (int ly = 0; ly < 9; ly++) {
                                        if (rand.nextDouble() < 0.05) { // Sehr spärlich
                                            cell.setMesoStrauchInMischung(mx, my, lx, ly, VegetationBush.brombeere.getGteId());
                                        }
                                    }
                                }
                            }
                        }

                    }
                } // Ende Meso-Scan
                
            }
        } // Ende Makro-Scan
    }

    /**
     * Methode 2: Ein Platzhalter für deine zukünftige Fluss- oder Pfad-Einkerbung.
     * Nutzt das mikroTerrainDelta im 1m-Raster (729x729), um feine Strukturen einzustanzen.
     */
    public static void carveMicroFeatures(MacroMap map) {
        // Hier wird später z.B. ein A*-Pfadsucher oder Erosions-Algorithmus implementiert,
        // welcher cell.setMikroTerrainDelta(gmx, gmy, (byte) code) aufruft.
    }
    

    /**
     * Kern-Methode: Wendet den Mikro-Grenzfluss-Filter auf der gesamten MacroMap an.
     * Iteriert von Nordwest nach Südost und lockert die Grenzen im 1m-Raster auf.
	 * Iteriert über alle Kacheln auf Meso-Ebene.
     * Vergleicht das dominante Terrain zweier benachbarter Meso-Kacheln direkt miteinander.     
     * @param map Die Referenz auf die zu bearbeitende MacroMap
     */
    public static void applyMicroBoundaryFlow(MacroMap map) {
        Random rand = new Random();
        int width = map.getWidth();
        int height = map.getHeight();
        int mesoSize = MacroMapCell.MESO_GRID_SIZE; // 81

        int totalMesoW = width * mesoSize;
        int totalMesoH = height * mesoSize;

        // Speicher für die vertikalen Nord-Infusionen in der jeweils NÄCHSTEN Zeile
        // Wir speichern hier direkt den Terrain-String, der eingeströmt werden MUSS (null = kein Zwang)
        String[][] forceNordTerrain = new String[totalMesoH][totalMesoW];

        for (int my = 0; my < totalMesoH; my++) {
            // Speicher für die horizontale West-Infusion in der jeweils RECHTEN Kachel
            String forceWestTerrain = null; // null = kein Zwang, ansonsten der Terrain-String von links

            for (int mx = 0; mx < totalMesoW; mx++) {
                MacroMapCell currentCell = map.getCellByMesoCoord(mx, my); 
                String currentTerrain = map.getMesoTerrainAtGlobal(mx, my);

                int localMX = mx % mesoSize;
                int localMY = my % mesoSize;

                // =========================================================================
                // 1. ZWANGS-AKTIONEN ZUERST PRÜFEN (RÜCKWÄRTSBETRACHTUNG)
                // =========================================================================
                
                // A) Wurde diese Kachel von LINKS (Westen) mit einem Zwang belegt?
                if (forceWestTerrain != null) {
                    growBoundaryBlob(currentCell, forceWestTerrain, "WEST", localMX, localMY, rand);
                    forceWestTerrain = null; // Zwang erfolgreich eingelöst und zurückgesetzt
                }

                // B) Wurde diese Kachel von OBEN (Norden) mit einem Zwang belegt?
                if (forceNordTerrain[my][mx] != null) {
                    growBoundaryBlob(currentCell, forceNordTerrain[my][mx], "NORD", localMX, localMY, rand);
                    // Den Speicherplatz müssen wir nicht zwingend nullen, da wir die Zeile nie wieder betreten
                }

                // =========================================================================
                // 2. VORWÄRTSBETRACHTUNG (NEUE ÜBERGÄNGE PRÜFEN & AUSWÜRFELN)
                // =========================================================================

                // --- HORIZONTALER CHECK (Nach rechts / Osten schauen) ---
                if (mx < totalMesoW - 1) {
                    String ostenTerrain = map.getMesoTerrainAtGlobal(mx + 1, my);

                    if (!currentTerrain.equals(ostenTerrain)) {
                        // Münzwurf (50:50)
                        if (rand.nextBoolean()) {
                            // VORWÄRTS: Osten fließt nach links (in unsere OST-Flanke)
                            growBoundaryBlob(currentCell, ostenTerrain, "OST", localMX, localMY, rand);
                        } else {
                            // RÜCKWÄRTS: Merke das aktuelle Terrain für die RECHTE Kachel im nächsten Schritt
                            forceWestTerrain = currentTerrain;
                        }
                    }
                }

                // --- VERTIKALER CHECK (Nach unten / Süden schauen) ---
                if (my < totalMesoH - 1) {
                    String suedenTerrain = map.getMesoTerrainAtGlobal(mx, my + 1);

                    if (!currentTerrain.equals(suedenTerrain)) {
                        // Münzwurf (50:50)
                        if (rand.nextBoolean()) {
                            // VORWÄRTS: Süden fließt nach oben (in unsere SÜD-Flanke)
                            growBoundaryBlob(currentCell, suedenTerrain, "SUED", localMX, localMY, rand);
                        } else {
                            // RÜCKWÄRTS: Merke das aktuelle Terrain für die UNTERE Kachel in der nächsten Zeile
                            forceNordTerrain[my + 1][mx] = currentTerrain;
                        }
                    }
                }

            }
        }
    }

    /**
     * Sub-Methode: Generiert ein zusammenhängendes (gebundenes) Gebilde aus 5-15 Kacheln
     * an einer spezifischen Flanke innerhalb des 9x9 Mikro-Rasters einer Meso-Kachel.
     * 
     * @param cell Die Ziel-MacroMapCell
     * @param targetTerrain Das einströmende Nachbarterrain
     * @param flank Die betroffene Grenze ("OST", "WEST", "SUED", "NORD")
     * @param mx Die relative X-Koordinate der Meso-Kachel (0 bis 80) innerhalb der Makro-Zelle
     * @param my Die relative Y-Koordinate der Meso-Kachel (0 bis 80) innerhalb der Makro-Zelle
     * @param rand Der globale Zufallsgenerator
     */
    private static void growBoundaryBlob(MacroMapCell cell, String targetTerrain, String flank, int mx, int my, Random rand) {
        int targetSize = 5 + rand.nextInt(11); // Gewürfelt: 5 bis 15 Felder
        List<int[]> placedCoordinates = new ArrayList<>();

        // 1. Startpunkt (Seed) direkt auf der äußersten Grenzlinie (Index 0-8) wählen
        int startX = 0;
        int startY = 0;
        int seedIndex = rand.nextInt(9); 

        switch (flank) {
            case "OST"  -> { startX = 8; startY = seedIndex; }
            case "WEST" -> { startX = 0; startY = seedIndex; }
            case "SUED" -> { startX = seedIndex; startY = 8; }
            case "NORD" -> { startX = seedIndex; startY = 0; }
        }

        // Erste Keimzelle setzen und stanzen
        placedCoordinates.add(new int[]{startX, startY});
        setMicroTerrainHelper(cell, mx, my, startX, startY, targetTerrain);

        // 2. Orthogonales, zelluläres Wachstum bis zur Zielgröße
        while (placedCoordinates.size() < targetSize) {
            List<int[]> validNeighbors = new ArrayList<>();

            // Scanne alle bereits platzierten Felder des Blobs nach potenziellen orthogonalen Nachbarn ab
            for (int[] coord : placedCoordinates) {
                int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} }; // Oben, Unten, Links, Rechts

                for (int[] dir : directions) {
                    int nx = coord[0] + dir[0];
                    int ny = coord[1] + dir[1];

                    // Prüfe, ob der Nachbar innerhalb des 9x9 Rasters liegt und noch nicht Teil des Blobs ist
                    if (nx >= 0 && nx < 9 && ny >= 0 && ny < 9 && !containsCoord(placedCoordinates, nx, ny)) {
                        
                        // Prüfe streng, ob der Nachbar innerhalb der erlaubten 2-Reihen-Schutzzone der Flanke liegt
                        boolean insideZone = switch (flank) {
                            case "OST"  -> (nx == 7 || nx == 8);
                            case "WEST" -> (nx == 0 || nx == 1);
                            case "SUED" -> (ny == 7 || ny == 8);
                            case "NORD" -> (ny == 0 || ny == 1);
                            default     -> false;
                        };

                        if (insideZone) {
                            validNeighbors.add(new int[]{nx, ny});
                        }
                    }
                }
            }

            // Falls der Platz in der Schutzzone komplett erschöpft ist, brechen wir ab
            if (validNeighbors.isEmpty()) {
                break;
            }

            // Wähle zufällig einen der gültigen Nachbarn aus, um die Form organisch zu erweitern
            int[] chosen = validNeighbors.get(rand.nextInt(validNeighbors.size()));
            placedCoordinates.add(chosen);
            
            // Reiche das gefertigte Mikro-Pixel mitsamt der Meso-Adressierung (mx, my) an den Helfer weiter
            setMicroTerrainHelper(cell, mx, my, chosen[0], chosen[1], targetTerrain);
        }
    }

    /**
     * Kleiner Helfer, um das Mikro-Terrain einer Meso-Kachel zu manipulieren.
     * Mappt die textuellen Meso-Terrains auf die numerischen GTE-IDs deines GroundMaterial-Enums.
     */
    private static void setMicroTerrainHelper(MacroMapCell cell, int mx, int my, int lx, int ly, String terrain) {
  
    	GroundMaterial material = GroundMaterial.grass;
         
        switch (terrain) {
	        // Wasser- & Küstenformen
	        case "SALTWATER"                -> material = GroundMaterial.saltwater;
	        case "WATER"                    -> material = GroundMaterial.water;
	        case "SAND", "COAST"            -> material = GroundMaterial.sand;
	        case "MUD", "SWAMP"             -> material = GroundMaterial.mud;
	        
	        // Stein, Fels & Geröll
	        case "CRUSHEDROCK", "GRAVEL"    -> material = GroundMaterial.crushedrock;
	        case "STONES", "SCREE"          -> material = GroundMaterial.stones;
	        case "ROCK", "MOUNTAIN"         -> material = GroundMaterial.rock;
	        
	        // Vegetation & Bodenbeläge
	        case "MOSS", "FOREST_FLOOR"     -> material = GroundMaterial.moss;
	        case "GRASS", "PLAIN", "PLAINS" -> material = GroundMaterial.grass;
	        case "FOLIAGE", "WOODLAND"      -> material = GroundMaterial.foliage;
	        case "BRUSHWOOD", "SHRUBLAND"   -> material = GroundMaterial.brushwood;
	        
	        // Ödland & Winter
	        case "ASH", "WASTELAND"         -> material = GroundMaterial.ash;
	        case "SNOW"                     -> material = GroundMaterial.snow;
	        case "ICE"                      -> material = GroundMaterial.ice;
	        
	        default -> {
	            System.err.println("WARNUNG: Kein Match für '" + terrain + "'. Fallback auf Grass.");
	        }
	    }
            
        byte gteId = (byte) material.getGteId();
        
        // Setzt das Delta nun exakt für die Meso-Kachel (mx, my) an der Mikro-Position (lx, ly)
        cell.setMikroTerrainDelta(mx, my, lx, ly, gteId); 
        
    }

    /**
     * Hilfsmethode zur Vermeidung von Duplikaten in der Koordinatenliste.
     */
    private static boolean containsCoord(List<int[]> list, int x, int y) {
        for (int[] c : list) {
            if (c[0] == x && c[1] == y) return true;
        }
        return false;
    }


    
}
