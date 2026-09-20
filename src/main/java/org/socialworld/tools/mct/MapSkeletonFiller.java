package org.socialworld.tools.mct;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class MapSkeletonFiller {

    public static void main(String[] args) {
        // Look and Feel an das Betriebssystem anpassen
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        System.out.println("=== Starte automatischen Pipeline-Test (MapSkeletonFiller) ===");

        // --- NEU: MODUS-ABFRAGE BEIM START ---
        String[] options = {"1 Sektor (Zufall via txt)", "3x3 Verbund (Testfeld)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welchen Test-Modus möchtest du starten?",
                "Pipeline Test-Modus wählen",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Falls der Dialog geschlossen oder abgebrochen wurde
        if (choice == JOptionPane.CLOSED_OPTION) {
            System.out.println("Vorgang abgebrochen: Kein Modus ausgewählt.");
            return;
        }

        // --- ENTSCHEIDUNG AUSFÜHREN ---
        if (choice == 0) {
            // ALTER MODUS: Einzelner Zufallssektor aus der analyzer_results.txt
            runSingleRandomSectorMode();
        } else {
            // NEUER MODUS: 9 zusammenhängende Sektoren am Stück generieren
            runMegaGridVerbundMode();
        }
    }

    /**
     * MODUS 1: Liest die analyzer_results.txt ein und würfelt genau einen, ecken-sicheren Rand.
     */
    private static void runSingleRandomSectorMode() {
        System.out.println("-> Modus aktiv: Einzelner Sektor aus Datei");
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Wähle die 'analyzer_results.txt' aus");
        chooser.setCurrentDirectory(new File("."));
        
        javax.swing.filechooser.FileNameExtensionFilter filter = 
            new javax.swing.filechooser.FileNameExtensionFilter("Textdateien (*.txt)", "txt");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Vorgang abgebrochen: Keine Analysedatei ausgewählt.");
            return;
        }

        File resultFile = chooser.getSelectedFile();

        try {
            System.out.println("Lese '" + resultFile.getName() + "' ein...");
            ResultFileReader.AnalysisResult data = ResultFileReader.readResults(resultFile);

            Random rand = new java.util.Random();
            int[] randomNorth = null;
            int[] randomEast  = null;
            int[] randomSouth = null;
            int[] randomWest  = null;

            int gesamtVersuche = 0;
            while (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null && gesamtVersuche < 1000) {
                gesamtVersuche++;
                
                if (!data.northBorders.isEmpty() && rand.nextBoolean()) {
                    randomNorth = getSingleRandomBorder(data.northBorders);
                }
                if (!data.eastBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherOst = getSingleRandomBorder(data.eastBorders);
                    if (randomNorth == null || moeglicherOst[0] == randomNorth[9]) {
                        randomEast = moeglicherOst;
                    }
                }
                if (!data.southBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherSued = getSingleRandomBorder(data.southBorders);
                    if (randomEast == null || moeglicherSued[9] == randomEast[9]) {
                        randomSouth = moeglicherSued;
                    }
                }
                if (!data.westBorders.isEmpty() && rand.nextBoolean()) {
                    int[] moeglicherWest = getSingleRandomBorder(data.westBorders);
                    boolean passtOben = (randomNorth == null || moeglicherWest[0] == randomNorth[0]);
                    boolean passtUnten = (randomSouth == null || moeglicherWest[9] == randomSouth[0]);
                    if (passtOben && passtUnten) {
                        randomWest = moeglicherWest;
                    }
                }
            }

            if (randomNorth == null && randomEast == null && randomSouth == null && randomWest == null) {
                if (!data.northBorders.isEmpty()) randomNorth = getSingleRandomBorder(data.northBorders);
            }

            System.out.println("-> Gewählter Nordrand: " + Arrays.toString(randomNorth));
            System.out.println("-> Gewählter Ostrand:  " + Arrays.toString(randomEast));
            System.out.println("-> Gewählter Südrand:  " + Arrays.toString(randomSouth));
            System.out.println("-> Gewählter Westrand: " + Arrays.toString(randomWest));

            String skeletonString = MapSkeletonGenerator.generateSkeleton(randomNorth, randomEast, randomSouth, randomWest);
            MapCreationTool.fillSkeleton(skeletonString);

            System.out.println("=== Testlauf erfolgreich beendet! ===");

        } catch (Exception e) {
            System.err.println("Fehler während des Testlaufs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * MODUS 2: Generiert 9 mathematisch perfekt zusammenhängende Schablonen für das 3x3 MegaGrid-Panorama.
     */
    private static void runMegaGridVerbundMode() {
        System.out.println("-> Modus aktiv: 3x3 Sektoren-Verbund (Testfeld)");
        
        List<int[]> heightLineModel = getHeightLineModel("B");
        
        String[][] filenames = {
            {"onlyLs_01_nordwest.txt", "onlyLs_02_norden.txt", "onlyLs_03_nordost.txt"},
            {"onlyLs_04_westen.txt",   "onlyLs_05_zentrum.txt", "onlyLs_06_osten.txt"},
            {"onlyLs_07_suedwest.txt", "onlyLs_08_sueden.txt", "onlyLs_09_suedost.txt"}
        };

        try {
            for (int sRow = 0; sRow < 3; sRow++) {
                for (int sCol = 0; sCol < 3; sCol++) {
                    System.out.println("\n--- Erzeuge Sektor [" + sRow + "][" + sCol + "]: " + filenames[sRow][sCol] + " ---");
 
                    List<int[]> lines = getHeightLines(heightLineModel, sRow, sCol);
                    boolean success = false;
                    String skeletonString = "";
                    while (!success) {
                         while (skeletonString.equals("") ) {
	                    	 skeletonString = MapSkeletonGenerator.generateSkeleton(lines.get(0), lines.get(1), lines.get(2), lines.get(3));
	                         if (skeletonString.length() >0) System.out.println("Skeleton: " + skeletonString);
                         }
                    	 success = MapCreationTool.fillSkeleton(skeletonString, filenames[sRow][sCol]);
                    	 // reset, falls noch kein success, dann mit neuem string versuchen
                    	 skeletonString = "";
                    }
                }
            }
            System.out.println("\n🟩 Alle 9 Verbund-Schablonen erfolgreich an das MapCreationTool übergeben!");

        } catch (Exception e) {
            System.err.println("Fehler bei der Verbund-Generierung: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<int[]> getHeightLines(List<int[]> total, int row, int col) {
    	
    	List<int[]> result = new ArrayList<int[]>();
    	
    	if (row == 0 && col == 0) {
    		result.add(total.get(0));
       		result.add(total.get(15));
       		result.add(total.get(3));
       		result.add(total.get(12));
    	}
    	else if (row == 0 && col == 1) {
    		result.add(total.get(1));
       		result.add(total.get(18));
       		result.add(total.get(4));
       		result.add(total.get(15));
    	}
      	else if (row == 0 && col == 2) {
    		result.add(total.get(2));
       		result.add(total.get(21));
       		result.add(total.get(5));
       		result.add(total.get(18));
    	}
      	else if (row == 1 && col == 0) {
    		result.add(total.get(3));
       		result.add(total.get(16));
       		result.add(total.get(6));
       		result.add(total.get(13));
    	}
    	else if (row == 1 && col == 1) {
    		result.add(total.get(4));
       		result.add(total.get(19));
       		result.add(total.get(7));
       		result.add(total.get(16));
    	}
      	else if (row == 1 && col == 2) {
    		result.add(total.get(5));
       		result.add(total.get(22));
       		result.add(total.get(8));
       		result.add(total.get(19));
    	}
    	else if (row == 2 && col == 0) {
    		result.add(total.get(6));
       		result.add(total.get(17));
       		result.add(total.get(9));
       		result.add(total.get(14));
    	}
    	else if (row == 2 && col == 1) {
    		result.add(total.get(7));
       		result.add(total.get(20));
       		result.add(total.get(10));
       		result.add(total.get(17));
    	}
      	else if (row == 2 && col == 2) {
    		result.add(total.get(8));
       		result.add(total.get(23));
       		result.add(total.get(11));
       		result.add(total.get(20));
    	}
	
    	return result;
    } 
    
    private static int[] getSingleRandomBorder(Map<String, List<String>> borderMap) {
        if (borderMap == null || borderMap.isEmpty()) return null;
        List<String> keys = new ArrayList<>(borderMap.keySet());
        String randomKey = keys.get(new java.util.Random().nextInt(keys.size()));
        return parseStringToIntArray(randomKey);
    }

    private static int[] parseStringToIntArray(String borderKey) {
        if (borderKey.startsWith("[") && borderKey.endsWith("]")) {
            borderKey = borderKey.substring(1, borderKey.length() - 1);
        }
        String[] tokens = borderKey.split(",");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i].trim());
        }
        return result;
    }
    
    private static List<int[]> getHeightLineModel(String modelName) {
    	List<int[]> model = new ArrayList<int[]>();
     	switch (modelName) {
    	case "A":
    		
    		int[] nwNorth_A = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // 0
    		int[] nNorth_A = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0};  // 1
       		int[] noNorth_A = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // 2
       		
       		int[] nwSouth_A = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // 3
       		int[] nSouth_A = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0};  // 4
       		int[] noSouth_A = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // 5
       		
       		int[] swNorth_A = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // 6
       		int[] sNorth_A = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0};  // 7
       		int[] soNorth_A = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // 8

      		int[] swSouth_A = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // 9
     		int[] sSouth_A = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0};  // 10
     		int[] soSouth_A = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // 11
 
     		model.add(nwNorth_A);     		model.add(nNorth_A);     		model.add(noNorth_A);
     		model.add(nwSouth_A);     		model.add(nSouth_A);     		model.add(noSouth_A);
     		model.add(swNorth_A);     		model.add(sNorth_A);     		model.add(soNorth_A);
     		model.add(swSouth_A);     		model.add(sSouth_A);     		model.add(soSouth_A);
     		
       		int[] nwWest_A = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 12
    		int[] wWest_A = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // 13
       		int[] swWest_A = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 14
       		
       		int[] nwEast_A = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // 15
       		int[] wEast_A = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0};  // 16
       		int[] swEast_A = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // 17
       		
       		int[] noWest_A = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // 18
       		int[] oWest_A = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0};  // 19
       		int[] soWest_A = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // 20

      		int[] noEast_A = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // 21
     		int[] oEast_A = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0};  // 22
     		int[] soEast_A = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // 23
 
     		model.add(nwWest_A);     		model.add(wWest_A);     		model.add(swWest_A);
     		model.add(nwEast_A);     		model.add(wEast_A);     		model.add(swEast_A);
     		model.add(noWest_A);     		model.add(oWest_A);     		model.add(soWest_A);
     		model.add(noEast_A);     		model.add(oEast_A);     		model.add(soEast_A);

     		break;
     		
    	case "B":
    		
    		int[] nwNorth_B = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // 0
    		int[] nNorth_B = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0};  // 1
       		int[] noNorth_B = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // 2
       		
       		int[] nwSouth_B = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // 3
       		int[] nSouth_B = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0};  // 4
       		int[] noSouth_B = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // 5
       		
       		int[] swNorth_B = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // 6
       		int[] sNorth_B = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0};  // 7
       		int[] soNorth_B = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // 8

      		int[] swSouth_B = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // 9
     		int[] sSouth_B = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0};  // 10
     		int[] soSouth_B = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // 11
 
     		model.add(nwNorth_B);     		model.add(nNorth_B);     		model.add(noNorth_B);
     		model.add(nwSouth_B);     		model.add(nSouth_B);     		model.add(noSouth_B);
     		model.add(swNorth_B);     		model.add(sNorth_B);     		model.add(soNorth_B);
     		model.add(swSouth_B);     		model.add(sSouth_B);     		model.add(soSouth_B);
     		
       		int[] nwWest_B = {0, 0, 0, 0, 1, 1, 1, 1, 1, 0}; // 12
    		int[] wWest_B = {0, 0, 0, 0, 1, 1, 1, 1, 1, 0};  // 13
       		int[] swWest_B = {0, 0, 0, 0, 1, 1, 1, 1, 1, 0}; // 14
       		
       		int[] nwEast_B = {0, 0, 1, 2, 2, 2, 1, 1, 1, 0}; // 15
       		int[] wEast_B = {0, 0, 1, 2, 2, 2, 1, 1, 1, 0};  // 16
       		int[] swEast_B = {0, 0, 1, 2, 2, 2, 1, 1, 1, 0}; // 17
       		
       		int[] noWest_B = {0, 0, 1, 2, 2, 3, 2, 1, 1, 0}; // 18
       		int[] oWest_B = {0, 0, 1, 2, 2, 3, 2, 1, 1, 0};  // 19
       		int[] soWest_B = {0, 0, 1, 2, 2, 3, 2, 1, 1, 0}; // 20

      		int[] noEast_B = {0, 0, 1, 2, 3, 2, 2, 1, 1, 0}; // 21
     		int[] oEast_B = {0, 0, 1, 2, 3, 2, 2, 1, 1, 0};  // 22
     		int[] soEast_B = {0, 0, 1, 2, 3, 2, 2, 1, 1, 0}; // 23
 
     		model.add(nwWest_B);     		model.add(wWest_B);     		model.add(swWest_B);
     		model.add(nwEast_B);     		model.add(wEast_B);     		model.add(swEast_B);
     		model.add(noWest_B);     		model.add(oWest_B);     		model.add(soWest_B);
     		model.add(noEast_B);     		model.add(oEast_B);     		model.add(soEast_B);

     		break;
 
    	case "TRY": /* funktioniert nicht */
    		
    		int[] nwNorth_C = {0, 1, 2, 3, 4, 5, 6, 7, 8, 8}; // 0
    		int[] nNorth_C = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17};  // 1
       		int[] noNorth_C = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26}; // 2
       		
       		int[] nwSouth_C = {0, 1, 2, 3, 4, 5, 6, 7, 8, 8}; // 3
       		int[] nSouth_C = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17};  // 4
       		int[] noSouth_C = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26}; // 5
       		
       		int[] swNorth_C = {0, 1, 2, 3, 4, 5, 6, 7, 8, 8}; // 6
       		int[] sNorth_C = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17};  // 7
       		int[] soNorth_C = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26}; // 8

      		int[] swSouth_C = {0, 1, 2, 3, 4, 5, 6, 7, 8, 8}; // 9
     		int[] sSouth_C = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17};  // 10
     		int[] soSouth_C = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26}; // 11
 
     		model.add(nwNorth_C);     		model.add(nNorth_C);     		model.add(noNorth_C);
     		model.add(nwSouth_C);     		model.add(nSouth_C);     		model.add(noSouth_C);
     		model.add(swNorth_C);     		model.add(sNorth_C);     		model.add(soNorth_C);
     		model.add(swSouth_C);     		model.add(sSouth_C);     		model.add(soSouth_C);
     		
       		int[] nwWest_C = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 12
    		int[] wWest_C = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // 13
       		int[] swWest_C = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 14
       		
       		int[] nwEast_C = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8}; // 15
       		int[] wEast_C = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8};  // 16
       		int[] swEast_C = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8}; // 17
       		
       		int[] noWest_C = {18, 18, 18, 18, 18, 18, 18, 18, 18, 18}; // 18
       		int[] oWest_C = {18, 18, 18, 18, 18, 18, 18, 18, 18, 18};  // 19
       		int[] soWest_C = {18, 18, 18, 18, 18, 18, 18, 18, 18, 18}; // 20

      		int[] noEast_C = {27, 27, 27, 27, 27, 27, 27, 27, 27, 27}; // 21
     		int[] oEast_C = {27, 27, 27, 27, 27, 27, 27, 27, 27, 27};  // 22
     		int[] soEast_C = {27, 27, 27, 27, 27, 27, 27, 27, 27, 27}; // 23
 
     		model.add(nwWest_C);     		model.add(wWest_C);     		model.add(swWest_C);
     		model.add(nwEast_C);     		model.add(wEast_C);     		model.add(swEast_C);
     		model.add(noWest_C);     		model.add(oWest_C);     		model.add(soWest_C);
     		model.add(noEast_C);     		model.add(oEast_C);     		model.add(soEast_C);

     		break;
     		
     	}
     	
     	return model;
    }
}

/*
//  wellig mit Höhen bis zu 3!
// Horizontale Trennlinien (West -> Ost)
int[] h0 = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0) - Sanfter Hügel
int[] h1 = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // Trennlinie Reihe 0 / Reihe 1 - Es steigt an
int[] h2 = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // Trennlinie Reihe 1 / Reihe 2 - Das Hochplateau (Höhe 3)
int[] h3 = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // Ganz unten (Südrand Reihe 2) - Sanftes Auslaufen

// Vertikale Trennlinien (Nord -> Süd)
//funktioniert 
int[] v0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz links (Westrand Spalte 0)
int[] v1 = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // Trennlinie Spalte 0 / Spalte 1
int[] v2 = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // Trennlinie Spalte 1 / Spalte 2
int[] v3 = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // Ganz rechts (Ostrand Spalte 2)
*/
//  wellig mit Höhen bis zu 3!
// Horizontale Trennlinien (West -> Ost)
//       int[] h0 = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0) - Sanfter Hügel
//       int[] h1 = {0, 0, 1, 2, 2, 2, 1, 1, 0, 0}; // Trennlinie Reihe 0 / Reihe 1 - Es steigt an
//       int[] h2 = {0, 1, 1, 2, 3, 2, 1, 1, 1, 0}; // Trennlinie Reihe 1 / Reihe 2 - Das Hochplateau (Höhe 3)
//       int[] h3 = {0, 1, 2, 2, 2, 2, 1, 1, 1, 0}; // Ganz unten (Südrand Reihe 2) - Sanftes Auslaufen

// Vertikale Trennlinien (Nord -> Süd)
// funktioniert 
//       int[] v0 = {0, 0, 0, 0, 1, 1, 1, 1, 1, 0}; // Ganz links (Westrand Spalte 0)
//       int[] v1 = {0, 0, 1, 2, 2, 2, 1, 1, 1, 0}; // Trennlinie Spalte 0 / Spalte 1
//       int[] v2 = {0, 0, 1, 2, 2, 3, 2, 1, 1, 0}; // Trennlinie Spalte 1 / Spalte 2
//       int[] v3 = {0, 0, 1, 2, 3, 2, 2, 1, 1, 0}; // Ganz rechts (Ostrand Spalte 2)





/*
 // 
  *  ENTSPANNTE HÖHENWELLEN (0 und 1): Ecken an Index 0 und 9 sind perfekt synchronisiert!
  // funktioniert
// Horizontale Trennlinien (West -> Ost)
int[] h0 = {0, 0, 0, 1, 1, 1, 0, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0)
int[] h1 = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // Trennlinie Reihe 0 / Reihe 1
int[] h2 = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // Trennlinie Reihe 1 / Reihe 2
int[] h3 = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // Ganz unten (Südrand Reihe 2)

// Vertikale Trennlinien (Nord -> Süd)
int[] v0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz links (Westrand Spalte 0)
int[] v1 = {0, 0, 1, 1, 0, 0, 1, 1, 0, 0}; // Trennlinie Spalte 0 / Spalte 1
int[] v2 = {0, 0, 1, 0, 0, 1, 1, 0, 0, 0}; // Trennlinie Spalte 1 / Spalte 2
int[] v3 = {0, 0, 1, 1, 1, 1, 1, 1, 0, 0}; // Ganz rechts (Ostrand Spalte 2)
*/
/*
//
// funktioniert
// bergig
int[] h0 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] h1 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] h2 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] h3 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 

// Vertikale Profile (je 10 Punkte von Nord nach Süd)
int[] v0 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] v1 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] v2 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
int[] v3 = {0, 1, 2, 3, 4, 3, 2, 1, 0, 0}; 
*/

// VARIANTE Z
//  nur Theorie: alles 0en
// funktioniert
// Horizontale Trennlinien (West -> Ost)
//     int[] h0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz oben (Nordrand Reihe 0)
//     int[] h1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Reihe 0 / Reihe 1
//     int[] h2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Reihe 1 / Reihe 2
//     int[] h3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz unten (Südrand Reihe 2)

// Vertikale Trennlinien (Nord -> Süd)
//int[] v0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz links (Westrand Spalte 0)
//int[] v1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Spalte 0 / Spalte 1
//     int[] v2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Trennlinie Spalte 1 / Spalte 2
//     int[] v3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Ganz rechts (Ostrand Spalte 2)

