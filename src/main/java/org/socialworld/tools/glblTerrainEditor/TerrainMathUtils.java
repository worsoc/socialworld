package org.socialworld.tools.glblTerrainEditor;

import java.util.Random;

public class TerrainMathUtils {

    private static final int[] p = new int[512];
    static {
        Random r = new Random();
        int[] permutation = new int[256];
        for (int i = 0; i < 256; i++) permutation[i] = i;
        for (int i = 255; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int tmp = permutation[i]; permutation[i] = permutation[j]; permutation[j] = tmp;
        }
        for (int i = 0; i < 256; i++) p[256 + i] = p[i] = permutation[i];
    }

    private static double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
    private static double lerp(double t, double a, double b) { return a + t * (b - a); }
    private static double grad(int hash, double x, double y) {
        int h = hash & 7;
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public static double noise2D(double x, double y) {
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        x -= Math.floor(x); y -= Math.floor(y);
        double u = fade(x); double v = fade(y);
        int A = p[X] + Y, B = p[X + 1] + Y;
        return lerp(v, lerp(u, grad(p[A], x, y), grad(p[B], x - 1, y)),
                       lerp(u, grad(p[A + 1], x, y - 1), grad(p[B + 1], x - 1, y - 1)));
    }

    /**
     * Neuer ID-Weichzeichner: Ersetzt den wirkungslosen Gauß-Filter.
     * Nutzt zelluläre Mehrheitsabfragen, um eckige 45-Grad-Mosaikkanten 
     * in perfekt runde, organische Kurven umzuwandeln.
     */
    public static double[][] applyCellularBlurFilter(double[][] source, int totalW, int totalH, int passes) {
        double[][] current = source;
        
        // Wir können den Filter in mehreren Durchläufen (Passes) glätten lassen
        for (int p = 0; p < passes; p++) {
            double[][] next = new double[totalH][totalW];
            
            for (int y = 0; y < totalH; y++) {
                
                for (int x = 0; x < totalW; x++) {
                    double myVal = current[y][x];
                    
                    // Sichere Grenzen für die direkten Nachbarn abgreifen (3x3 Kreuz)
                    double n = (y > 0) ? current[y - 1][x] : myVal;
                    double s = (y < totalH - 1) ? current[y + 1][x] : myVal;
                    double w = (x > 0) ? current[y][x - 1] : myVal;
                    double o = (x < totalW - 1) ? current[y][x + 1] : myVal;
                    
                    // Diagonale Nachbarn für kreisrunde Formen
                    double nw = (y > 0 && x > 0) ? current[y - 1][x - 1] : myVal;
                    double ne = (y > 0 && x < totalW - 1) ? current[y - 1][x + 1] : myVal;
                    double sw = (y < totalH - 1 && x > 0) ? current[y + 1][x - 1] : myVal;
                    double se = (y < totalH - 1 && x < totalW - 1) ? current[y + 1][x + 1] : myVal;

                    // Zähle, wie oft die Nachbar-Terrains vorkommen
                    int[] counts = new int[14]; // IDs 0 bis 13
                    counts[(int)n]++; counts[(int)s]++; counts[(int)w]++; counts[(int)o]++;
                    counts[(int)nw]++; counts[(int)ne]++; counts[(int)sw]++; counts[(int)se]++;

                    // Finde das dominanteste Terrain in der Umgebung
                    int dominantType = (int) myVal;
                    int maxCount = 0;
                    for (int i = 0; i < counts.length; i++) {
                        if (counts[i] > maxCount) {
                            maxCount = counts[i];
                            dominantType = i;
                        }
                    }

                    // Regel: Wenn mindestens 5 von 8 Nachbarn ein anderes Terrain sind,
                    // gleicht sich das Pixel an. Das schleift Ecken kreisrund ab!
                    if (maxCount >= 5) {
                        next[y][x] = dominantType;
                    } else {
                        next[y][x] = myVal;
                    }
                }
            }
            current = next;
        }
        return current;
    }
}
