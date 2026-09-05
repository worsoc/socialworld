package org.socialworld.tools.mct;


public class VisualTile {
    public final int type;
    public final int baseHeight;

    public VisualTile(int type, int baseHeight) {
        this.type = type;
        this.baseHeight = baseHeight;
    }

    // Gibt die Höhen-Offsets (0=Nord, 1=Ost, 2=Süd, 3=West) der Kachel zurück
    public double[] getCorners() {
        double n = 0, o = 0, s = 0, w = 0;
        switch (type) {
            case 0: // eben
                break;
            case 1: // steigend von Mitteldiagonale in Ecke SuedOst
                s = 1.0; 
                break;
            case 2: // steigend von Mitteldiagonale in Ecke SuedWest
                w = 1.0; 
                break;
            case 3: // steigend von Kante Nord zur Kante Sued
                s = 1.0; w = 1.0; 
                break;
            case 4: // steigend von Mitteldiagonale in Ecke NordOst
                o = 1.0; 
                break;
            case 5: // steigend von Kante West zur Kante Ost
                o = 1.0; s = 1.0; 
                break;
            case 6: // steigend von Ecke NW zur Mitteldiagonale, steigend von Ecke SO zur Mitteldiagonale
                // Die Diagonale von West nach Ost bildet den hohen Grat
                w = 1.0; 
                o = 1.0; 
                n = 0.0; 
                s = 0.0; 
                break; 
            case 7: // steigend von Ecke NW auf die Mitteldiagonale und eben nach SO
                w = 1.0; o = 1.0; s = 1.0; // Nur NW-Ecke (n) bleibt unten
                break;
            case 8: // steigend von Mitteldiagonale in Ecke NordWest
                n = 1.0; 
                break;
            case 9: // steigend von Ecke SW zur Mitteldiagonale, steigend von Ecke NO zur Mitteldiagonale
                // Die Diagonale von Nord nach Süd bildet den hohen Grat
                n = 1.0; 
                s = 1.0; 
                o = 0.0; 
                w = 0.0; 
                break; 
            case 10: // steigend von Kante Ost zur Kante West
                n = 1.0; w = 1.0; 
                break;
            case 11: // steigend von Ecke NO auf die Mitteldiagonale und eben nach SW
                n = 1.0; s = 1.0; w = 1.0; // Nur NO-Ecke (o) bleibt unten
                break;
            case 12: // steigend von Kante Sued zur Kante Nord
                n = 1.0; o = 1.0; 
                break;
            case 13: // steigend von Ecke SW auf die Mitteldiagonale und eben nach NO
                n = 1.0; o = 1.0; s = 1.0; // Nur SW-Ecke (w) bleibt unten
                break;
            case 14: // steigend von Ecke SO auf die Mitteldiagonale und eben nach NW
                o = 1.0; n = 1.0; w = 1.0; // Nur SO-Ecke (s) bleibt unten
                break;
            case 15: // eben auf erhoehtem Level
                n = 1.0; o = 1.0; s = 1.0; w = 1.0; 
                break;
            case 16: // steigend um 2 Level von SW nach NO
                o = 1.0; w = -1.0;
                break;
            case 17: // steigend um 2 Level von NO nach SW
                w = 1.0; o = -1.0; 
                break;
            case 18: // steigend um 2 Level von NW nach SO
                s = 1.0; n = -1.0; 
                break;
            case 19: // steigend um 2 Level von SO nach NW
                n = 1.0; s = -1.0; 
                break;
        }
        return new double[]{baseHeight + n, baseHeight + o, baseHeight + s, baseHeight + w};
    }
}
