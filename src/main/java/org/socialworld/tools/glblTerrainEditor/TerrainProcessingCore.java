package org.socialworld.tools.glblTerrainEditor;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import org.socialworld.attributes.GroundMaterial;

public class TerrainProcessingCore {

    public static final int TYPE_SALTWATER = GroundMaterial.saltwater.getGteId();
    public static final int TYPE_WATER =  GroundMaterial.water.getGteId();
    public static final int TYPE_COAST =  GroundMaterial.sand.getGteId();
    public static final int TYPE_PLAINS =  GroundMaterial.grass.getGteId();
    public static final int TYPE_MOUNTAIN =  GroundMaterial.rock.getGteId();

    public static final int TYPE_SWAMP = GroundMaterial.mud.getGteId();          // Schlamm -> Sumpf
    public static final int TYPE_GRAVEL = GroundMaterial.crushedrock.getGteId();  // Schotter
    public static final int TYPE_SCREE = GroundMaterial.stones.getGteId();        // Steine -> Geröll/Felsboden
    public static final int TYPE_FOREST_FLOOR = GroundMaterial.moss.getGteId();   // Moos -> Waldboden
    public static final int TYPE_WOODLAND = GroundMaterial.foliage.getGteId();    // Laub -> Wald/Hain
    public static final int TYPE_SHRUBLAND = GroundMaterial.brushwood.getGteId(); // Reisig -> Gestrüpp/Heide
    public static final int TYPE_WASTELAND = GroundMaterial.ash.getGteId();       // Asche -> Ödland
    public static final int TYPE_SNOW = GroundMaterial.snow.getGteId();           // Schnee
    public static final int TYPE_ICE = GroundMaterial.ice.getGteId();             // Eis
  
    /**
     * Die zentrale Verteiler-Methode der Weltgenerierung.
     * Delegiert die Kartenerstellung an die Spezialroutinen und gibt
     * anschließend eine detaillierte Makro-Statistik in der Konsole aus.
     */
    public static int[][] generateMacroGrid(int width, int height, GTEWorldProfile profile) {
        // 1. Die Karte über das Profil generieren
        int[][] grid = switch (profile) {
            case KONTINENTAL -> generateContinentalMap(width, height);
            case ARCHIPEL     -> generateArchipelagoMap(width, height);
            case OEDLAND       -> generateWastelandMap(width, height);
            case NORDISCH     -> generateNordicMap(width, height);
        };

        // 2. NEU: Kacheln für die Statistik auszählen
        int[] counts = new int[14];
        int totalLandCells = 0;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int type = grid[y][x];
                if (type >= 0 && type < 14) {
                    counts[type]++;
                    if (type != TYPE_SALTWATER) {
                        totalLandCells++;
                    }
                }
            }
        }

        // 3. IHR LOGGING: Die Statistik sauber formatiert ausgeben
        System.out.println("\n--- MAKRO-STATISTIK (" + profile.name() + ") ---");
        double oceanPerc = (counts[TYPE_SALTWATER] / 1024.0) * 100.0;
        System.out.printf("OZEAN (SALTWATER) Anteil an Gesamtwelt: %.2f%%\n", oceanPerc);
        System.out.println("Verteilung auf der Landmasse (Basis: " + totalLandCells + " Kacheln):");
        
        // Helfer-Array für die Bezeichner (angepasst an die TYPE_-Konstanten)
        String[] names = new String[14];
        names[TYPE_PLAINS] = "GRASLAND (PLAINS)";
        names[TYPE_WOODLAND] = "LAUBWALD (WOODLAND)";
        names[TYPE_FOREST_FLOOR] = "WALDBODEN (MOSS)";
        names[TYPE_SHRUBLAND] = "GESTRÜPP (BRUSHWOOD)";
        names[TYPE_SWAMP] = "SUMPF (MUD)";
        names[TYPE_WASTELAND] = "ÖDLAND (ASH)";
        names[TYPE_MOUNTAIN] = "BERGE (ROCK)";
        names[TYPE_WATER] = "BINNENSEEN (WATER)";
        names[TYPE_SNOW] = "SCHNEE (SNOW)";
        names[TYPE_ICE] = "EIS (ICE)";
        names[TYPE_SCREE] = "GERÖLL (STONES)";
        names[TYPE_COAST] = "SAND (SAND)";

        for (int i = 0; i < 14; i++) {
            if (i != TYPE_SALTWATER && names[i] != null && counts[i] > 0) {
                double landPerc = (counts[i] / (double) totalLandCells) * 100.0;
                System.out.printf(" -> %-22s: %.2f%% (%d Kacheln)\n", names[i], landPerc, counts[i]);
            }
        }
        System.out.println("-----------------------------------------\n");

        // 4. Das fertig ausgezählte Grid an die UI zurückgeben
        return grid;
    }
    
    
    /**
     * Erzeugt ein massives, weitläufiges Festland (ca. 25x25 km) nach dem Kontinental-Profil.
     * Verzichtet komplett auf Strände (kein Sand/COAST auf dieser Ebene).
     * Nutzt 7 Runden Wachstum für das Land, 2 Gebirgs-Rücken und 4 gerichtete Forste.
     */
    public static int[][] generateContinentalMap(int width, int height) {
        int[][] grid = new int[height][width]; 
        Random rand = new Random();
        
        // 1. Ozean als Basis initialisieren
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) grid[y][x] = TYPE_SALTWATER; 
        }
        
        // 2. Kontinent-Samen setzen (Mehr Landkerne im Zentrum)
        int numSeeds = 12 + rand.nextInt(5); // 12 bis 16 Samen
        for (int i = 0; i < numSeeds; i++) {
            grid[5 + rand.nextInt(22)][5 + rand.nextInt(22)] = TYPE_PLAINS;
        }

        // 3. Kontinental-Wachstum (8 Runden für maximales Festland, 85% Chance, tempGrid)
        for (int growth = 0; growth < 7; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_PLAINS) {
                        if (rand.nextDouble() < 0.85) tempGrid[y-1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y+1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x-1] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x+1] = TYPE_PLAINS;
                    }
                }
            }
            grid = tempGrid;
        }

        // 4. ZENTRALES GEBIRGSMASSIV (EXAKT 2 SAMEN, 4 Runden, Echtzeit-Bremse + Richtungs-Array)
        int platziereBerge = 0;
        int maxVersuche = 150;
        while (platziereBerge < 2 && maxVersuche > 0) {
            int rx = 8 + rand.nextInt(16);
            int ry = 8 + rand.nextInt(16);
            // Die Keime müssen gut im Landesinneren liegen
            if (grid[ry][rx] == TYPE_PLAINS && grid[ry+1][rx] == TYPE_PLAINS && grid[ry-1][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_MOUNTAIN;
                platziereBerge++;
            }
            maxVersuche--;
        }

        // 4 Runden kontrolliertes Gebirgswachstum
        for (int growth = 0; growth < 4; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_MOUNTAIN) {
                        if (grid[y-1][x] == TYPE_PLAINS && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_MOUNTAIN;
                        if (grid[y+1][x] == TYPE_PLAINS && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_MOUNTAIN;
                        if (grid[y][x-1] == TYPE_PLAINS && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_MOUNTAIN;
                        if (grid[y][x+1] == TYPE_PLAINS && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_MOUNTAIN;
                    }
                }
            }
            grid = tempGrid;
        }

        // 5. REGIONALE FORSTE (EXAKT 4 SAMEN, 2 Runden, Echtzeit-Bremse + Richtungs-Array)
        int forstePlatziert = 0;
        maxVersuche = 150;
        while (forstePlatziert < 4 && maxVersuche > 0) {
            int rx = 4 + rand.nextInt(24);
            int ry = 4 + rand.nextInt(24);
            if (grid[ry][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_WOODLAND;
                forstePlatziert++;
            }
            maxVersuche--;
        }

        // 2 kontrollierte Runden Wald-Wachstum
        for (int growth = 0; growth < 2; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_WOODLAND) {
                        if (grid[y-1][x] == TYPE_PLAINS && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_WOODLAND;
                        if (grid[y+1][x] == TYPE_PLAINS && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_WOODLAND;
                        if (grid[y][x-1] == TYPE_PLAINS && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_WOODLAND;
                        if (grid[y][x+1] == TYPE_PLAINS && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_WOODLAND;
                    }
                }
            }
            grid = tempGrid;
        }

        // 6. Binnengewässer & Moore im Landesinneren (1 bis 2 Seen)
        int seenSeeds = 1 + rand.nextInt(2);
        for (int i = 0; i < seenSeeds; i++) {
            int rx = 6 + rand.nextInt(20);
            int ry = 6 + rand.nextInt(20);
            if (grid[ry][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_WATER;
                if (grid[ry+1][rx] == TYPE_PLAINS && rand.nextDouble() < 0.40) grid[ry+1][rx] = TYPE_WATER;
                if (grid[ry-1][rx] == TYPE_PLAINS && rand.nextDouble() < 0.40) grid[ry-1][rx] = TYPE_WATER;
                if (grid[ry][rx+1] == TYPE_PLAINS && rand.nextDouble() < 0.40) grid[ry][rx+1] = TYPE_WATER;
                if (grid[ry][rx-1] == TYPE_PLAINS && rand.nextDouble() < 0.40) grid[ry][rx-1] = TYPE_WATER;
            }
        }

        // Feuchtes Sumpfland lagert sich logisch an Seeufer oder schattige Waldränder an
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == TYPE_PLAINS) {
                    if (grid[y+1][x] == TYPE_WATER || grid[y-1][x] == TYPE_WATER || 
                        grid[y][x+1] == TYPE_WATER || grid[y][x-1] == TYPE_WATER) {
                        if (rand.nextDouble() < 0.25) grid[y][x] = TYPE_SWAMP;
                    } else if (grid[y+1][x] == TYPE_WOODLAND || grid[y-1][x] == TYPE_WOODLAND) {
                        if (rand.nextDouble() < 0.05) grid[y][x] = TYPE_SWAMP;
                    }
                }
            }
        }

        // 7. Alpine Extremzonen (Schnee & Eis tief im Gebirgskern)
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == TYPE_MOUNTAIN) {
                    if (grid[y+1][x] == TYPE_MOUNTAIN && grid[y-1][x] == TYPE_MOUNTAIN && 
                        grid[y][x+1] == TYPE_MOUNTAIN && grid[y][x-1] == TYPE_MOUNTAIN) {
                        
                        double roll = rand.nextDouble();
                        if (roll < 0.40) grid[y][x] = TYPE_SNOW;      
                        else if (roll < 0.55) grid[y][x] = TYPE_ICE;  
                    }
                }
            }
        }

        return grid;
    }
    
    /**
     * Erzeugt eine vulkanische Inselgruppe (Archipel) im Maßstab von ca. 25x25 km.
     * Nutzt 7 Vulkanzentren und 5 Ausbreitungsrunden für eine Inselbreite von 7-8 km.
     * Verwendet die Richtungs-Dynamik (Summe = 3.0) und das tempGrid, um die vulkanischen
     * Schichten organisch von innen nach außen aufzubauen.
     */
    public static int[][] generateArchipelagoMap(int width, int height) {
        int[][] grid = new int[height][width]; 
        Random rand = new Random();
        
        // 1. Der kalte Ozean als Basis
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) grid[y][x] = TYPE_SALTWATER; 
        }
        
        // 2. Die 7 vulkanischen Zentren setzen (Massives Gebirge als Keim)
        // Wir packen die drei Zonen-Basiswerte in Listen und mischen sie komplett durch
        java.util.List<Integer> xZones = new java.util.ArrayList<>(java.util.Arrays.asList(2, 9, 15, 20, 25, 5, 23));
        java.util.List<Integer> yZones = new java.util.ArrayList<>(java.util.Arrays.asList(2, 9, 15, 20, 25, 5, 23));
        java.util.Collections.shuffle(xZones, rand);
        java.util.Collections.shuffle(yZones, rand);
        
        // Jetzt ziehen wir die Werte ohne Zurücklegen (0, 1, 2, 3, 4, 5, 6) und fügen den Mikro-Zufall hinzu
        int[][] volcanoSeeds = {
            { xZones.get(0) + rand.nextInt(3), yZones.get(0) + rand.nextInt(3) }, // Vulkan 1
            { xZones.get(1) + rand.nextInt(3), yZones.get(1) + rand.nextInt(3) }, // Vulkan 2
            { xZones.get(2) + rand.nextInt(3), yZones.get(2) + rand.nextInt(3) }, // Vulkan 3
            { xZones.get(3) + rand.nextInt(3), yZones.get(3) + rand.nextInt(3) }, // Vulkan 4
            { xZones.get(4) + rand.nextInt(3), yZones.get(4) + rand.nextInt(3) },  // Vulkan 5
            { xZones.get(5) + rand.nextInt(3), yZones.get(5) + rand.nextInt(3) }, // Vulkan 6
            { xZones.get(6) + rand.nextInt(3), yZones.get(6) + rand.nextInt(3) }  // Vulkan 7
       };
        
        for (int[] seed : volcanoSeeds) {
            grid[seed[1]][seed[0]] = TYPE_MOUNTAIN; // seed[1] ist Y, seed[0] ist X
        }
        
        // 3. Die 5 Ausbreitungsrunden mit Richtungs-Array & Echtzeit-Bremse
        for (int round = 1; round <= 5; round++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            // Richtungs-Wahrscheinlichkeiten für diese Runde erwürfeln (Summe = 3.0)
            double wNord = rand.nextInt(100) + 1;
            double wSued = rand.nextInt(100) + 1;
            double wWest = rand.nextInt(100) + 1;
            double wOst  = rand.nextInt(100) + 1;
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    
                    // RUNDEN 1 & 2: Der massive Felskern expandiert
                    if (grid[y][x] == TYPE_MOUNTAIN && round <= 2) {
                        if (grid[y-1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_MOUNTAIN;
                        if (grid[y+1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_MOUNTAIN;
                        if (grid[y][x-1] == TYPE_SALTWATER && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_MOUNTAIN;
                        if (grid[y][x+1] == TYPE_SALTWATER && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_MOUNTAIN;
                    }
                    
                    // RUNDEN 3 & 4: Lava kühlt ab, Aschehänge (WASTELAND) und Geröll (SCREE) breiten sich aus
                    else if ((grid[y][x] == TYPE_MOUNTAIN || grid[y][x] == TYPE_SCREE || grid[y][x] == TYPE_WASTELAND) && (round == 3 || round == 4)) {
                        int current = grid[y][x];
                        // Neue Ausläufer werden zu Asche oder Geröll
                        if (grid[y-1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = (rand.nextBoolean() ? TYPE_SCREE : TYPE_WASTELAND);
                        if (grid[y+1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = (rand.nextBoolean() ? TYPE_SCREE : TYPE_WASTELAND);
                        if (grid[y][x-1] == TYPE_SALTWATER && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = (rand.nextBoolean() ? TYPE_SCREE : TYPE_WASTELAND);
                        if (grid[y][x+1] == TYPE_SALTWATER && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = (rand.nextBoolean() ? TYPE_SCREE : TYPE_WASTELAND);
                    }
                    
                    // RUNDE 5: Die äußerste Schicht verwittert zu fruchtbarem Flachland (PLAINS)
                    else if ((grid[y][x] == TYPE_SCREE || grid[y][x] == TYPE_WASTELAND) && round == 5) {
                        if (grid[y-1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_PLAINS;
                        if (grid[y+1][x] == TYPE_SALTWATER && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_PLAINS;
                        if (grid[y][x-1] == TYPE_SALTWATER && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_PLAINS;
                        if (grid[y][x+1] == TYPE_SALTWATER && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_PLAINS;
                    }
                }
            }
            grid = tempGrid;
        }

        // 4. Sicherstellen, dass die Inseln einen sauberen Übergang zum Wasser haben (PLAINS als Küstensaum)
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == TYPE_SCREE || grid[y][x] == TYPE_WASTELAND) {
                    if (grid[y+1][x] == TYPE_SALTWATER || grid[y-1][x] == TYPE_SALTWATER || 
                        grid[y][x+1] == TYPE_SALTWATER || grid[y][x-1] == TYPE_SALTWATER) {
                        grid[y][x] = TYPE_PLAINS;
                    }
                }
            }
        }

        // 5. Tropischer Urwald-Gürtel (WOODLAND) auf den fruchtbaren Küstenebenen platzieren
        int dschungelSeeds = 0;
        int versuche = 150;
        while (dschungelSeeds < 4 && versuche > 0) {
            int rx = 3 + rand.nextInt(26);
            int ry = 3 + rand.nextInt(26);
            if (grid[ry][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_WOODLAND;
                dschungelSeeds++;
            }
            versuche--;
        }

        // 2 kontrollierte Runden gerichtetes Dschungel-Wachstum (Summe = 3.0)
        for (int growth = 0; growth < 2; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_WOODLAND) {
                        if (grid[y-1][x] == TYPE_PLAINS && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_WOODLAND;
                        if (grid[y+1][x] == TYPE_PLAINS && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_WOODLAND;
                        if (grid[y][x-1] == TYPE_PLAINS && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_WOODLAND;
                        if (grid[y][x+1] == TYPE_PLAINS && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_WOODLAND;
                    }
                }
            }
            grid = tempGrid;
        }

        return grid;
    }
    
    /**
     * Erzeugt eine knochentrockene Sand-, Geröll- und Aschewüste (ca. 25x25 km).
     * Verbannt Gras (PLAINS) vollständig. Schichtet die Karte von außen nach innen:
     * Ozean -> WASTELAND (Ascheküste) -> COAST (Wüstensand) -> GRAVEL/SCREE (Hänge) -> MOUNTAIN (Canyons).
     */
    public static int[][] generateWastelandMap(int width, int height) {
        int[][] grid = new int[height][width]; 
        Random rand = new Random();
        
        // 1. Ozean als Basis initialisieren
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) grid[y][x] = TYPE_SALTWATER; 
        }
        
        // 2. Ödland-Samen setzen (Anfängliche Asche-Kerne für die Platte)
        int numSeeds = 10 + rand.nextInt(5); // 10 bis 14 Samen
        for (int i = 0; i < numSeeds; i++) {
            grid[5 + rand.nextInt(22)][5 + rand.nextInt(22)] = TYPE_WASTELAND;
        }

        // 3. Kontinentales Wüsten-Wachstum (7 Runden, STRIKTE 85% Einzelwahrscheinlichkeit, tempGrid)
        for (int growth = 0; growth < 7; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_WASTELAND) {
                        if (rand.nextDouble() < 0.85) tempGrid[y-1][x] = TYPE_WASTELAND;
                        if (rand.nextDouble() < 0.85) tempGrid[y+1][x] = TYPE_WASTELAND;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x-1] = TYPE_WASTELAND;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x+1] = TYPE_WASTELAND;
                    }
                }
            }
            grid = tempGrid;
        }

        // 4. Das Innere in Wüstensand (COAST) umwandeln (Asche bleibt nur als Küstensaum stehen)
        int[][] sandGrid = new int[height][width];
        for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, sandGrid[y], 0, width);
        
        for (int y = 2; y < height - 2; y++) {
            for (int x = 2; x < width - 2; x++) {
                if (grid[y][x] == TYPE_WASTELAND) {
                    // Wenn kein Ozean in direkter 2-Kachel-Nähe ist, ist es tiefes Landesinneres -> Sand!
                    boolean nahAmWasser = false;
                    for (int dy = -2; dy <= 2; dy++) {
                        for (int dx = -2; dx <= 2; dx++) {
                            if (grid[y+dy][x+dx] == TYPE_SALTWATER) nahAmWasser = true;
                        }
                    }
                    if (!nahAmWasser) {
                        sandGrid[y][x] = TYPE_COAST;
                    }
                }
            }
        }
        grid = sandGrid;

        // 5. Nackte Felsketten im Inneren (5 längliche Canyons/Felsrücken aus MOUNTAIN)
        for (int i = 0; i < 5; i++) {
            int startX = 10 + rand.nextInt(12);
            int startY = 10 + rand.nextInt(12);
            if (grid[startY][startX] == TYPE_COAST || grid[startY][startX] == TYPE_WASTELAND) {
                grid[startY][startX] = TYPE_MOUNTAIN;
                
                int cx = startX;
                int cy = startY;
                for (int step = 0; step < 3; step++) {
                    if (rand.nextBoolean()) cx += (rand.nextBoolean() ? 1 : -1);
                    else cy += (rand.nextBoolean() ? 1 : -1);
                    
                    if (cx > 5 && cx < width - 6 && cy > 5 && cy < height - 6) {
                        if (grid[cy][cx] == TYPE_COAST) grid[cy][cx] = TYPE_MOUNTAIN;
                    }
                }
            }
        }

        // 6. Thermische Verwitterung der Canyons (3 Runden mit Summe 3.0 & Echtzeit-Bremse)
        // Runde 1 & 2: Direkt am Fels (MOUNTAIN) entsteht Schotter (GRAVEL)
        // Runde 3: Weiter außen geht es in Steingeröll (SCREE) über, bevor der Sand beginnt
        for (int round = 1; round <= 3; round++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            // Ihre Richtungs-Dynamik für die Schuttrampen erwürfeln!
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    
                    // In Runde 1 und 2 lagert sich grober Schotter (GRAVEL) direkt am Berghang an
                    if (grid[y][x] == TYPE_MOUNTAIN && round <= 2) {
                        if (grid[y-1][x] == TYPE_COAST && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_GRAVEL;
                        if (grid[y+1][x] == TYPE_COAST && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_GRAVEL;
                        if (grid[y][x-1] == TYPE_COAST && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_GRAVEL;
                        if (grid[y][x+1] == TYPE_COAST && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_GRAVEL;
                    }
                    // In Runde 3 schiebt sich feineres Steingeröll (SCREE) weiter in die Sanddünen vor
                    else if (grid[y][x] == TYPE_GRAVEL && round == 3) {
                        if (grid[y-1][x] == TYPE_COAST && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_SCREE;
                        if (grid[y+1][x] == TYPE_COAST && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_SCREE;
                        if (grid[y][x-1] == TYPE_COAST && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_SCREE;
                        if (grid[y][x+1] == TYPE_COAST && rand.nextDouble() < chanceOst)  tempGrid[y+1][x] = TYPE_SCREE;
                    }
                }
            }
            grid = tempGrid;
        }

        return grid;
    }
  
    /**
     * Erzeugt eine raue, skandinavische Gebirgs- und Moorlandschaft (ca. 25x25 km).
     * Nutzt ein dominantes Gebirgsmassiv (4 Runden) und zwei riesige Moos-Areale 
     * (2 Samen, 3 Runden) als Fundament für spätere Nadelwälder. Der Rest ist Gras.
     * Verwendet die Richtungs-Dynamik (Summe = 3.0) und das tempGrid für lochfreie Areale.
     */
    public static int[][] generateNordicMap(int width, int height) {
        int[][] grid = new int[height][width]; 
        Random rand = new Random();
        
        // 1. Kalter Ozean als Basis initialisieren
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) grid[y][x] = TYPE_SALTWATER; 
        }
        
        // 2. Landmasse-Samen setzen (Raues Grasland als Fundament)
        int numSeeds = 8 + rand.nextInt(4); // mehr als 8 Samen
        for (int i = 0; i < numSeeds; i++) {
            grid[4 + rand.nextInt(22)][4 + rand.nextInt(22)] = TYPE_PLAINS;
        }

        // 3. Wachstum der nordischen Landmasse (7 Runden Gras-Ausbreitung, 85% Chance, tempGrid)
        for (int growth = 0; growth < 7; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_PLAINS) {
                        if (rand.nextDouble() < 0.85) tempGrid[y-1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y+1][x] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x-1] = TYPE_PLAINS;
                        if (rand.nextDouble() < 0.85) tempGrid[y][x+1] = TYPE_PLAINS;
                    }
                }
            }
            grid = tempGrid;
        }

        // 4. Vom Eis geschliffene Fjelds (Gebirge im Landesinneren - 2 Samen, 4 Runden gerichtetes Wachstum)
        int mountainSeedX = 16, mountainSeedY = 16;
        int versuche = 100;
        for (int samen = 1; samen <= 3; samen++) {
            while (versuche > 0) {
                int rx = 10 + rand.nextInt(12);
                int ry = 10 + rand.nextInt(12);
                if (grid[ry][rx] == TYPE_PLAINS) {
                    mountainSeedX = rx; mountainSeedY = ry;
                    break;
                }
                versuche--;
            }
            grid[mountainSeedY][mountainSeedX] = TYPE_MOUNTAIN;
        }
 
        // 4 Runden Gebirgswachstum mit Richtungschancen Summe 3.0 und Echtzeit-Bremse
        for (int growth = 0; growth < 4; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_MOUNTAIN) {
                        if (grid[y-1][x] == TYPE_PLAINS && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_MOUNTAIN;
                        if (grid[y+1][x] == TYPE_PLAINS && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_MOUNTAIN;
                        if (grid[y][x-1] == TYPE_PLAINS && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_MOUNTAIN;
                        if (grid[y][x+1] == TYPE_PLAINS && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_MOUNTAIN;
                    }
                }
            }
            grid = tempGrid;
        }

        // 5. Arktische Frostzone (Schneekappen und Gletscher auf den kalten Bergen)
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == TYPE_MOUNTAIN) {
                    int bergNachbarn = 0;
                    if (grid[y+1][x] == TYPE_MOUNTAIN) bergNachbarn++;
                    if (grid[y-1][x] == TYPE_MOUNTAIN) bergNachbarn++;
                    if (grid[y][x+1] == TYPE_MOUNTAIN) bergNachbarn++;
                    if (grid[y][x-1] == TYPE_MOUNTAIN) bergNachbarn++;
                    
                    if (bergNachbarn >= 2) {
                        double roll = rand.nextDouble();
                        if (roll < 0.45) grid[y][x] = TYPE_SNOW;
                        else if (roll < 0.65) grid[y][x] = TYPE_ICE;
                    }
                }
            }
        }

        // 6. Der Moos-Teppich (FOREST_FLOOR) - 2 Samen, 3 Runden gerichtetes Wachstum
        int moosSeeds = 0;
        versuche = 100;
        while (moosSeeds < 2 && versuche > 0) {
            int rx = 4 + rand.nextInt(24);
            int ry = 4 + rand.nextInt(24);
            if (grid[ry][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_FOREST_FLOOR;
                moosSeeds++;
            }
            versuche--;
        }

        // 3 Runden Moos-Ausbreitung mit Richtungschancen Summe 3.0 und Echtzeit-Bremse
        for (int growth = 0; growth < 3; growth++) {
            int[][] tempGrid = new int[height][width];
            for (int y = 0; y < height; y++) System.arraycopy(grid[y], 0, tempGrid[y], 0, width);
            
            double wNord = rand.nextDouble();
            double wSued = rand.nextDouble();
            double wWest = rand.nextDouble();
            double wOst  = rand.nextDouble();
            double sum   = wNord + wSued + wWest + wOst;
            
            double chanceNord = (wNord / sum) * 3.0;
            double chanceSued = (wSued / sum) * 3.0;
            double chanceWest = (wWest / sum) * 3.0;
            double chanceOst  = (wOst / sum) * 3.0;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    if (grid[y][x] == TYPE_FOREST_FLOOR) {
                        if (grid[y-1][x] == TYPE_PLAINS && rand.nextDouble() < chanceNord) tempGrid[y-1][x] = TYPE_FOREST_FLOOR;
                        if (grid[y+1][x] == TYPE_PLAINS && rand.nextDouble() < chanceSued) tempGrid[y+1][x] = TYPE_FOREST_FLOOR;
                        if (grid[y][x-1] == TYPE_PLAINS && rand.nextDouble() < chanceWest) tempGrid[y][x-1] = TYPE_FOREST_FLOOR;
                        if (grid[y][x+1] == TYPE_PLAINS && rand.nextDouble() < chanceOst)  tempGrid[y][x+1] = TYPE_FOREST_FLOOR;
                    }
                }
            }
            grid = tempGrid;
        }

        // 7. Nordische Hochmoore (SWAMP) im flachen Landesinneren platzieren
        int moorSeeds = 2;
        for (int i = 0; i < moorSeeds; i++) {
            int rx = 5 + rand.nextInt(22);
            int ry = 5 + rand.nextInt(22);
            if (grid[ry][rx] == TYPE_PLAINS) {
                grid[ry][rx] = TYPE_SWAMP;
                if (grid[ry+1][rx] == TYPE_PLAINS && rand.nextDouble() < 0.45) grid[ry+1][rx] = TYPE_SWAMP;
                if (grid[ry-1][rx] == TYPE_PLAINS && rand.nextDouble() < 0.45) grid[ry-1][rx] = TYPE_SWAMP;
                if (grid[ry][rx+1] == TYPE_PLAINS && rand.nextDouble() < 0.45) grid[ry][rx+1] = TYPE_SWAMP;
                if (grid[ry][rx-1] == TYPE_PLAINS && rand.nextDouble() < 0.45) grid[ry][rx-1] = TYPE_SWAMP;
            }
        }

        // 8. Kristallklare Binnenseen (WATER)
        int seeSeeds = 1 + rand.nextInt(2);
        for (int i = 0; i < seeSeeds; i++) {
            int rx = 6 + rand.nextInt(20);
            int ry = 6 + rand.nextInt(20);
            if (grid[ry][rx] == TYPE_PLAINS || grid[ry][rx] == TYPE_FOREST_FLOOR) {
                grid[ry][rx] = TYPE_WATER;
            }
        }

        return grid;
    }
    
   //////////////////   Medo-Ebene  //////////////////////// 
    
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

                // von 0 .. 13  für die Geländearten
                int[] counts = new int[14];
                for (int my = 0; my < size; my++) {
                    for (int mx = 0; mx < size; mx++) {
                        int val = (int) source[startY + my][startX + mx];
                        if (val >= 0 && val < 14) counts[val]++;
                    }
                }
                
                int hauptTerrain = 0; int maxCount = -1;
                for (int i = 0; i < 14; i++) { 
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
     * Löst unnatürliche "Sanduhr"-Strukturen auf, indem das Zentrum (34..44) analysiert
     * und das dominante Terrain asymmetrisch in eine zufällige Himmelsrichtung
     * im Kerngebiet (25..55) ausgeweitet wird.
     */
    public static double[][] dissolveCenterChokePoints(double[][] source, int mapW, int mapH, int size) {
        int totalW = mapW * size;
        int totalH = mapH * size;
        double[][] output = new double[totalH][totalW];
        
        for (int y = 0; y < totalH; y++) {
            System.arraycopy(source[y], 0, output[y], 0, totalW);
        }

        java.util.Random rand = new java.util.Random();

        for (int cy = 0; cy < mapH; cy++) {
            for (int cx = 0; cx < mapW; cx++) {
                
                int startX = cx * size;
                int startY = cy * size;

                // 1. Häufigkeiten im inneren 11x11 Raster (34 bis 44) ermitteln
                int[] counts = new int[14];
                for (int my = 34; my <= 44; my++) {
                    for (int mx = 34; mx <= 44; mx++) {
                        int val = (int) source[startY + my][startX + mx];
                        if (val >= 0 && val < 14) {
                            counts[val]++;
                        }
                    }
                }
                
                // Dominantes Terrain im Zentrum bestimmen
                int zentrumsTerrain = 0; 
                int maxCount = -1;
                for (int i = 0; i < 14; i++) { 
                    if (counts[i] > maxCount) { 
                        maxCount = counts[i]; 
                        zentrumsTerrain = i; 
                    }
                }

                // Alle Kacheln im inneren 11x11-Bereich als Startpunkte nutzen
                java.util.List<int[]> wachstumsFront = new java.util.ArrayList<>();
                for (int my = 34; my <= 44; my++) {
                    for (int mx = 34; mx <= 44; mx++) {
                        if ((int) output[startY + my][startX + mx] == zentrumsTerrain) {
                            wachstumsFront.add(new int[]{mx, my});
                        }
                    }
                }

                if (wachstumsFront.isEmpty()) {
                    continue;
                }

                // --- RICHTUNGS-BIAS (ASYMMETRIE) ---
                // Bestimmt für dieses Meso-Raster eine dominante Vorzugsrichtung:
                // 0 = Norden, 1 = Süden, 2 = Westen, 3 = Osten
                int hauptRichtung = rand.nextInt(4);

                // Zufallszahl zwischen 200 und 400 für die zu ändernden Kacheln
                int zielAnzahl = 200 + rand.nextInt(201);
                int platziert = 0;
                int sicherheitsBremse = 0;

                // 2. Gerichtetes, zusammenhängendes Wachstum im Bereich 25 bis 55
                while (platziert < zielAnzahl && !wachstumsFront.isEmpty() && sicherheitsBremse < 6000) {
                    sicherheitsBremse++;
                    
                    // Einen zufälligen bestehenden Pixel aus der Front wählen
                    int[] basePixel = wachstumsFront.get(rand.nextInt(wachstumsFront.size()));
                    int nextMx = basePixel[0]; 
                    int nextMy = basePixel[1];
                    
                    // Richtungsauswahl mit Gewichtung
                    int dir;
                    if (rand.nextDouble() < 0.60) {
                        // Zu 60% wird die ermittelte Hauptrichtung erzwungen
                        dir = hauptRichtung;
                    } else {
                        // Zu 40% bricht das Wachstum komplett frei aus
                        dir = rand.nextInt(4);
                    }
                    
                    // Koordinate basierend auf Richtung verschieben
                    if (dir == 0) nextMy--;      // Norden (Hoch)
                    else if (dir == 1) nextMy++; // Süden (Runter)
                    else if (dir == 2) nextMx--; // Westen (Links)
                    else if (dir == 3) nextMx++; // Osten (Rechts)
                    
                    // Prüfen, ob der Nachbar innerhalb der erlaubten 25..55 Grenzen liegt
                    if (nextMx >= 25 && nextMx <= 55 && nextMy >= 25 && nextMy <= 55) {
                        int gx = startX + nextMx;
                        int gy = startY + nextMy;
                        
                        // Nur überschreiben, wenn es noch nicht das zentrumsTerrain ist
                        if ((int) output[gy][gx] != zentrumsTerrain) {
                            output[gy][gx] = zentrumsTerrain;
                            platziert++;
                            // Der neue Pixel erweitert die Front
                            wachstumsFront.add(new int[]{nextMx, nextMy});
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
    
    
    //////////// Terrain-Ergänzungen auf Meso-Ebene ///////////////
    
    /**
     * Aktualisierte Kern-Methode: Garantiert exakt zwischen 30 und 70 gültige Kachel-Belegungen.
     * Strände (TYPE_COAST) beim Archipel werden nun zwingend nur direkt an Ozeankanten platziert.
     */
    public static double[][] applyMesoTerrainInfusions(double[][] mesoGrid, int mapW, int mapH, int mesoSize, GTEWorldProfile profile) {
        Random rand = new Random();
        
        int[] allowedTerrains = switch (profile) {
            case KONTINENTAL -> new int[]{ TYPE_SHRUBLAND, TYPE_GRAVEL, TYPE_WOODLAND, TYPE_SWAMP, TYPE_COAST };
            case ARCHIPEL     -> new int[]{ TYPE_SWAMP, TYPE_COAST };
            case OEDLAND       -> new int[]{ TYPE_SHRUBLAND };
            case NORDISCH     -> new int[]{ TYPE_SCREE, TYPE_WOODLAND, TYPE_GRAVEL };
        };

        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int cy = 0; cy < mapH; cy++) {
            for (int cx = 0; cx < mapW; cx++) {
                
                // Seltenere Platzierung: Nur auf ca. 17% der Makro-Kacheln spawnt ein Gebilde
                if (rand.nextDouble() > 0.17) {
                    continue; 
                }
                
                int targetTerrain = allowedTerrains[rand.nextInt(allowedTerrains.length)];
                int exactTargetSize = 30 + rand.nextInt(41); 
                
                int startX = -1;
                int startY = -1;
                
                // SONDERREgel: Archipel-Strand muss an eine Ozeankante gesetzt werden
                if (/*profile == GTEWorldProfile.ARCHIPEL && */targetTerrain == TYPE_COAST) {
                    // Wir suchen im Meso-Block nach einer Landkachel, die an SALTWATER grenzt
                    List<int[]> validCoastlineTiles = new ArrayList<>();
                    int minMesoX = cx * mesoSize;
                    int maxMesoX = minMesoX + mesoSize;
                    int minMesoY = cy * mesoSize;
                    int maxMesoY = minMesoY + mesoSize;
                    
                    // Wir scannen die Meso-Kachel nach potenziellen Uferpunkten
                    for (int y = minMesoY + 5; y < maxMesoY - 5; y++) {
                        for (int x = minMesoX + 5; x < maxMesoX - 5; x++) {
                            int currentType = (int) mesoGrid[y][x];
                            // Wenn hier Land ist, prüfen wir die Nachbarn auf Ozean
                            if (currentType != TYPE_SALTWATER && currentType != TYPE_WATER) {
                                for (int[] d : directions) {
                                    int nx = x + d[0];
                                    int ny = y + d[1];
                                    if (nx >= 0 && nx < mapW * mesoSize && ny >= 0 && ny < mapH * mesoSize) {
                                        if ((int) mesoGrid[ny][nx] == TYPE_SALTWATER) {
                                            validCoastlineTiles.add(new int[]{x, y});
                                            break; // Ein Ozean-Nachbar reicht aus
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // Wenn keine Küstenlinie in dieser Makro-Kachel gefunden wurde (z.B. reines Inland), abbrechen
                    if (validCoastlineTiles.isEmpty()) {
                        continue;
                    }
                    
                    // Zufälligen echten Küstenpunkt als Start wählen
                    int[] pickedCoast = validCoastlineTiles.get(rand.nextInt(validCoastlineTiles.size()));
                    startX = pickedCoast[0];
                    startY = pickedCoast[1];
                } else {
                    // Standard-Verhalten für alle anderen Terrains: Zufälliges Zentrum im Inlands-Sicherheitsbereich
                    startX = (cx * mesoSize) + 15 + rand.nextInt(mesoSize - 30);
                    startY = (cy * mesoSize) + 15 + rand.nextInt(mesoSize - 30);
                    
                    int baseTerrainAtCenter = (int) mesoGrid[startY][startX];
                    // Erlaube Archipel UND Kontinental, im Ozeanbereich nach Stränden zu suchen, falls der Else-Zweig doch mal greift:
                    if ((baseTerrainAtCenter == TYPE_SALTWATER || baseTerrainAtCenter == TYPE_WATER) 
                        && profile != GTEWorldProfile.ARCHIPEL && profile != GTEWorldProfile.KONTINENTAL) {
                        continue; 
                    }
                }

                // Listen für das dynamische Wachstum vor Ort
                List<int[]> activeGrowthPoints = new ArrayList<>();
                List<int[]> placedTiles = new ArrayList<>();
                
                if (isValidInfusionTarget((int) mesoGrid[startY][startX], targetTerrain)) {
                    int[] startNode = new int[]{startX, startY};
                    activeGrowthPoints.add(startNode);
                    placedTiles.add(startNode);
                    mesoGrid[startY][startX] = targetTerrain;
                }

                int attempts = 0;
                while (placedTiles.size() < exactTargetSize && !activeGrowthPoints.isEmpty() && attempts < 600) {
                    attempts++;
                    
                    int[] base = placedTiles.get(rand.nextInt(placedTiles.size()));
                    
                    // Für Strände wachsen wir bevorzugt etwas länglicher entlang der Küste
                    int dirIndex;
                    if (targetTerrain == TYPE_COAST || rand.nextDouble() < 0.40) {
                        dirIndex = rand.nextInt(4); // N, S, O, W
                    } else {
                        dirIndex = rand.nextInt(8); // Diagonalen inkludiert
                    }
                    
                    int nextX = base[0] + directions[dirIndex][0];
                    int nextY = base[1] + directions[dirIndex][1];
                    
                    int minX = cx * mesoSize;
                    int maxX = minX + mesoSize;
                    int minY = cy * mesoSize;
                    int maxY = minY + mesoSize;
                    
                    if (nextX >= minX && nextX < maxX && nextY >= minY && nextY < maxY) {
                        if (!containsCoord(placedTiles, nextX, nextY)) {
                            int currentGridType = (int) mesoGrid[nextY][nextX];
                            
                            // Für Strände fügen wir eine zusätzliche Barriere hinzu: Sie dürfen sich nicht 
                            // zu tief ins Inland fressen, sondern müssen in der Nähe vom Ozean bleiben.
                            if (targetTerrain == TYPE_COAST) {
                                boolean nearOcean = false;
                                for (int[] d : directions) {
                                    int ox = nextX + d[0];
                                    int oy = nextY + d[1];
                                    if (ox >= 0 && ox < mapW * mesoSize && oy >= 0 && oy < mapH * mesoSize) {
                                        if ((int) mesoGrid[oy][ox] == TYPE_SALTWATER || (int) mesoGrid[oy][ox] == TYPE_COAST) {
                                            nearOcean = true;
                                            break;
                                        }
                                    }
                                }
                                if (!nearOcean) continue; // Wenn zu weit weg vom Wasser/bestehendem Strand, überspringen
                            }

                            if (isValidInfusionTarget(currentGridType, targetTerrain)) {
                                int[] newNode = new int[]{nextX, nextY};
                                placedTiles.add(newNode);
                                activeGrowthPoints.add(newNode);
                                mesoGrid[nextY][nextX] = targetTerrain;
                            }
                        }
                    }
                }
            }
        }
        return mesoGrid;
    }

    /**
     * Kleiner Helfer, um Duplikate im Koordinaten-Pool zu vermeiden.
     */
    private static boolean containsCoord(List<int[]> list, int x, int y) {
        for (int[] c : list) {
            if (c[0] == x && c[1] == y) return true;
        }
        return false;
    }

    /**
     * Submethode: Prüft, ob ein Zusatz-Terrain (target) auf einem bestehenden Terrain (current) platziert werden darf.
     * Verhindert unlogische Artefakte wie Bäume im Wasser oder Sümpfe auf Bergspitzen.
     * Nutzt if-else, da die TYPE_-Variablen keine Kompilierzeit-Konstanten sind.
     */
    private static boolean isValidInfusionTarget(int current, int target) {
        // Generelle Grundregel: Ozean und Gletschereis dürfen niemals überschrieben werden
        if (current == TYPE_SALTWATER || current == TYPE_ICE) {
            return false;
        }

        // Spezifische biologische/geologische Sperr-Regeln per if-else
        if (target == TYPE_SWAMP) {
            // Sumpf darf nicht ins tiefe Wasser, nicht auf Strände und nicht ins Gebirge
            return current != TYPE_WATER && current != TYPE_COAST && 
                   current != TYPE_MOUNTAIN && current != TYPE_SNOW && current != TYPE_SCREE;
        } 
        else if (target == TYPE_WOODLAND) {
            // Laubwald wächst nicht im Wasser, auf Sand, purem Schotter oder im Schnee
            return current != TYPE_WATER && current != TYPE_COAST && 
                   current != TYPE_GRAVEL && current != TYPE_SCREE && current != TYPE_SNOW;
        } 
        else if (target == TYPE_GRAVEL || target == TYPE_SCREE) {
            // Schotter und Geröll verdrängen kein Wasser und versinken nicht im Moor
            return current != TYPE_WATER && current != TYPE_SWAMP;
        } 
        else if (target == TYPE_SHRUBLAND) {
            // Gestrüpp wächst fast überall, außer direkt im Wasser oder Schnee
            return current != TYPE_WATER && current != TYPE_SNOW;
        }

        return true; // Standardmäßig erlaubt, wenn keine explizite Sperre vorliegt
    }

    /**
     * Submethode: Generiert verschiedene Form-Sätze mit ca. 50 Kacheln.
     */
    private static List<List<int[]>> generateShapeSets(Random rand, int targetSize) {
        List<List<int[]>> sets = new ArrayList<>();

        // ---- SATZ 1: DER PLUMPE BLOB (Kompakt / Massiv) ----
        List<int[]> plumpBlob = new ArrayList<>();
        plumpBlob.add(new int[]{0, 0});
        while (plumpBlob.size() < targetSize) {
            int[] base = plumpBlob.get(rand.nextInt(plumpBlob.size()));
            int nx = base[0] + (rand.nextBoolean() ? 1 : -1);
            int ny = base[1] + (rand.nextBoolean() ? 1 : -1);
            if (!containsCoord(plumpBlob, nx, ny)) {
                plumpBlob.add(new int[]{nx, ny});
            }
        }
        sets.add(plumpBlob);

        // ---- SATZ 2: DER LÄNGLICHE STREIFEN (Gerichtete Ader) ----
        List<int[]> longStrip = new ArrayList<>();
        longStrip.add(new int[]{0, 0});
        int cx = 0, cy = 0;
        while (longStrip.size() < targetSize) {
            // Bias in eine Richtung (z.B. primär nach rechts unten)
            if (rand.nextDouble() < 0.70) {
                cx += rand.nextBoolean() ? 1 : 0;
                cy += rand.nextBoolean() ? 1 : 0;
            } else {
                cx += rand.nextInt(3) - 1;
                cy += rand.nextInt(3) - 1;
            }
            if (!containsCoord(longStrip, cx, cy)) {
                longStrip.add(new int[]{cx, cy});
            }
        }
        sets.add(longStrip);

        // ---- SATZ 3: DAS GEBILDE MIT LÖCHERN (Organische Aussparungen) ----
        List<int[]> holedShape = new ArrayList<>();
        int placed = 0;
        // Wir spannen ein Feld auf und sieben es über mathematisches Perlin-Rauschen aus
        for (int y = -5; y <= 5 && placed < targetSize; y++) {
            for (int x = -5; x <= 5 && placed < targetSize; x++) {
                double dist = Math.sqrt(x * x + y * y);
                if (dist < 4.5) {
                    // Nutzt das Rauschen für unregelmäßige Löcher im Kern
                    double noise = (TerrainMathUtils.noise2D(x * 0.4, y * 0.4) + 1.0) / 2.0;
                    if (noise > 0.35 && rand.nextDouble() < 0.85) {
                        holedShape.add(new int[]{x, y});
                        placed++;
                    }
                }
            }
        }
        sets.add(holedShape);

        return sets;
    }

     
    
    

}
