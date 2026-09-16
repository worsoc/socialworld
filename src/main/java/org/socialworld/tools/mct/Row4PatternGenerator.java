package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;

public class Row4PatternGenerator {

    // 💡 Deine exakte Tabelle. Alle R-Werte 15 wurden entfernt wegen = 0!
    private static final int[][] ZULAESSIG_RECHTS = {
        /* 0 */ {0, 1, 4, 5, 10, 11},
        /* 1 */ {2, 3, 6, 7, 17, 18},
        /* 2 */ {0, 1, 4, 5, 10, 11},
        /* 3 */ {2, 3, 6, 7, 17, 18},
        /* 4 */ {8, 9, 12, 13, 16, 19},
        /* 5 */ {0, 1, 4, 5, 10, 11},
        /* 6 */ {8, 9, 12, 13, 16, 19},
        /* 7 */ {0, 1, 4, 5, 10, 11},
        /* 8 */ {0, 1, 4, 5, 10, 11},
        /* 9 */ {2, 3, 6, 7, 17, 18},
        /* 10*/ {0, 1, 4, 5, 10, 11},
        /* 11*/ {2, 3, 6, 7, 17, 18},
        /* 12*/ {8, 9, 12, 13, 16, 19},
        /* 13*/ {0, 1, 4, 5, 10, 11},
        /* 14*/ {8, 9, 12, 13, 16, 19},
        /* 15*/ {0, 1, 4, 5, 10, 11},
        /* 16*/ {8, 9, 12, 13, 16, 19},
        /* 17*/ {2, 3, 6, 7, 17, 18},
        /* 18*/ {2, 3, 6, 7, 17, 18},
        /* 19*/ {8, 9, 12, 13, 16, 19}
    };
    
    /**
     * Erstellt eine Liste aller mathematisch möglichen Kombinationen für eine 7er-Reihe.
     */
    public static List<int[]> generiereAlleKombinationen() {
        List<int[]> ergebnisse = new ArrayList<>();
        int[] aktuellerPfad = new int[7]; // Array für genau 7 Kacheln
        
        // Starte die Generierung in Spalte 0
        kombiniere(0, aktuellerPfad, ergebnisse);
        return ergebnisse;
    }

    private static void kombiniere(int spaltenIndex, int[] aktuellerPfad, List<int[]> ergebnisse) {
        // Basis-Fall: Wenn das Array mit 7 Kacheln voll ist, speichern wir die Kombination
        if (spaltenIndex >= 7) {
            ergebnisse.add(aktuellerPfad.clone());
            return;
        }

        // Erste Spalte: Kann jede beliebige Kachel von 0 bis 19 sein
        if (spaltenIndex == 0) {
            for (int startKachel = 0; startKachel < ZULAESSIG_RECHTS.length; startKachel++) {
                aktuellerPfad[spaltenIndex] = startKachel;
                kombiniere(spaltenIndex + 1, aktuellerPfad, ergebnisse);
            }
        } 
        // Folgespalten: Dürfen nur Kacheln sein, die laut Tabelle rechts erlaubt sind
        else {
            int linkerNachbar = aktuellerPfad[spaltenIndex - 1];
            int[] erlaubteNachfolger = ZULAESSIG_RECHTS[linkerNachbar];

            for (int nachfolgerKachel : erlaubteNachfolger) {
                aktuellerPfad[spaltenIndex] = nachfolgerKachel;
                kombiniere(spaltenIndex + 1, aktuellerPfad, ergebnisse);
            }
        }
    }
}


/*
 * 
 * L:	R:
0	0,1,4,5,10,11,15
1	2,3,6,7,17,18
2	0,1,4,5,10,11,15
3	2,3,6,7,17,18
4	8,9,12,13,16,19
5	0,1,4,5,10,11,15
6	8,9,12,13,16,19
7	0,1,4,5,10,11,15
8	0,1,4,5,10,11,15
9	2,3,6,7,17,18
10	0,1,4,5,10,11,15
11	2,3,6,7,17,18
12	8,9,12,13,16,19
13	0,1,4,5,10,11,15
14	8,9,12,13,16,19
15	0,1,4,5,10,11,15
16	8,9,12,13,16,19
17	2,3,6,7,17,18
18	2,3,6,7,17,18
19	8,9,12,13,16,19
*/
 

