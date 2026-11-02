package org.socialworld.tools.glblTerrainEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.socialworld.tools.mct.LTileRaster;
import org.socialworld.tools.mct.LTileRasterGenerator;
import org.socialworld.tools.mct.LTileRasterLibrary;

/**
 * Belegt jede zweite Makro-Zelle (Schachbrettmuster) mit einem zufälligen Raster aus der Bibliothek
 * und erzeugt anschließend Raster für die freien Zellen dazwischen.
 *
 * Zellen des Musters berühren sich nur diagonal, sie müssen also nur in den gemeinsamen Eckpunkten
 * zusammenpassen. Außerdem müssen die freien Zellen dazwischen lösbar bleiben. Es gibt kein
 * Backtracking: Passt für eine Zelle kein Raster, bleibt sie leer.
 */
public final class HeightModelFiller {

    public static final String GENERATED_SOURCE = "generiert";

    /** Begleitet das Erzeugen der freien Zellen. Die Aufrufe kommen aus dem rechnenden Thread. */
    public interface FillListener {
        boolean isCancelled();
        void cellFinished(int round, int cellsDone, int cellsInRound);
    }

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
                    if (result != null && result.keepsCellsReachable()) {
                        result.apply(entry.getFileName());
                        filledCells++;
                        break;
                    }
                }
            }
        }
        return new Result(filledCells, patternCells);
    }

    /**
     * Erzeugt Raster für alle noch leeren Zellen. Jeder Durchgang beginnt mit den Zellen, die die meisten
     * belegten Nachbarn haben. Was leer bleibt, kommt im nächsten Durchgang noch einmal dran, denn dann
     * sind die Vorgaben oft andere. Beim Abbruch bleiben die bis dahin gesetzten Raster erhalten.
     *
     * @param rounds die höchste Zahl der Durchgänge
     * @return die Zahl der in jedem Durchgang gesetzten Zellen
     */
    public static List<Integer> fillFreeCells(MacroMap map, Random random, long timeoutPerCellMillis, int rounds, FillListener listener) throws IOException {
        List<Integer> filledPerRound = new ArrayList<>();

        for (int round = 0; round < rounds; round++) {
            int filled = 0;
            List<int[]> cells = emptyCellsByNeighbourCount(map);
            if (cells.isEmpty()) break;
            for (int i = 0; i < cells.size(); i++) {
                if (listener.isCancelled()) {
                    filledPerRound.add(filled);
                    return filledPerRound;
                }
                int[] cell = cells.get(i);
                HeightRasterPlacement placement = new HeightRasterPlacement(map, cell[0], cell[1]);
                LTileRaster raster = LTileRasterGenerator.generate(() -> placement.proposeEdges(random), timeoutPerCellMillis);
                HeightRasterPlacement.Result result = (raster != null) ? placement.evaluate(raster) : null;
                if (result != null) {
                    result.apply(GENERATED_SOURCE);
                    filled++;
                }
                listener.cellFinished(round, i + 1, cells.size());
            }
            filledPerRound.add(filled);
            if (filled == 0) break;
        }
        return filledPerRound;
    }

    public static int countOccupiedCells(MacroMap map) {
        int count = 0;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getCell(x, y).hasHeightRaster()) count++;
            }
        }
        return count;
    }

    private static List<int[]> emptyCellsByNeighbourCount(MacroMap map) {
        List<int[]> cells = new ArrayList<>();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (!map.getCell(x, y).hasHeightRaster()) cells.add(new int[] {x, y});
            }
        }
        cells.sort((a, b) -> occupiedNeighbours(map, b[0], b[1]) - occupiedNeighbours(map, a[0], a[1]));
        return cells;
    }

    private static int occupiedNeighbours(MacroMap map, int x, int y) {
        int count = 0;
        int[][] neighbours = { {x, y - 1}, {x + 1, y}, {x, y + 1}, {x - 1, y} };
        for (int[] n : neighbours) {
            if (n[0] >= 0 && n[0] < map.getWidth() && n[1] >= 0 && n[1] < map.getHeight() && map.getCell(n[0], n[1]).hasHeightRaster()) count++;
        }
        return count;
    }
}
