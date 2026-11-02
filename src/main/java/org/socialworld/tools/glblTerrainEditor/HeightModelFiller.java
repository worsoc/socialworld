package org.socialworld.tools.glblTerrainEditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.socialworld.tools.mct.LTileRasterLibrary;

/**
 * Belegt jede zweite Makro-Zelle (Schachbrettmuster) mit einem zufälligen Raster aus der Bibliothek.
 *
 * Zellen des Musters berühren sich nur diagonal, sie müssen also nur in den gemeinsamen Eckpunkten
 * zusammenpassen. Es gibt kein Backtracking: Passt für eine Zelle kein Raster, bleibt sie leer.
 */
public final class HeightModelFiller {

    public static final class Result {
        private final int filledCells;
        private final int patternCells;

        private Result(int filledCells, int patternCells) {
            this.filledCells = filledCells;
            this.patternCells = patternCells;
        }

        public int getFilledCells() { return filledCells; }
        public int getPatternCells() { return patternCells; }
    }

    private HeightModelFiller() {
    }

    public static boolean isPatternCell(int cellX, int cellY) {
        return (cellX + cellY) % 2 == 0;
    }

    /**
     * @return die Anzahl der Zellen außerhalb des Musters, die ein Raster haben und beim Füllen geleert werden
     */
    public static int countOccupiedFreeCells(MacroMap map) {
        int count = 0;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (!isPatternCell(x, y) && map.getCell(x, y).hasHeightRaster()) count++;
            }
        }
        return count;
    }

    /**
     * Leert alle Höhenraster der Karte und belegt die Zellen des Musters neu.
     */
    public static Result fill(MacroMap map, List<LTileRasterLibrary.Entry> entries, Random random) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                map.getCell(x, y).clearHeightRaster();
            }
        }

        List<LTileRasterLibrary.Entry> shuffled = new ArrayList<>(entries);
        int filledCells = 0;
        int patternCells = 0;

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (!isPatternCell(x, y)) continue;
                patternCells++;

                Collections.shuffle(shuffled, random);
                HeightRasterPlacement placement = new HeightRasterPlacement(map, x, y);
                for (LTileRasterLibrary.Entry entry : shuffled) {
                    HeightRasterPlacement.Result result = placement.evaluate(entry.getRaster());
                    if (result != null) {
                        result.apply(entry.getFileName());
                        filledCells++;
                        break;
                    }
                }
            }
        }
        return new Result(filledCells, patternCells);
    }
}
