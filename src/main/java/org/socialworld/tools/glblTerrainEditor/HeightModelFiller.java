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
 * Belegt die Makro-Zellen mit Höhenrastern. {@link #fillEmptyCells} geht von Nordwest nach Südost vor,
 * nimmt wenn möglich ein Raster aus der Bibliothek und erzeugt sonst eines.
 *
 * {@link #fill} ist das frühere Schachbrettmuster: Zellen des Musters berühren sich nur diagonal, sie
 * müssen also nur in den gemeinsamen Eckpunkten zusammenpassen. Es wird derzeit nicht aufgerufen.
 */
public final class HeightModelFiller {

    public static final String GENERATED_SOURCE = "generiert";

    /** Begleitet das Erzeugen der freien Zellen. Die Aufrufe kommen aus dem rechnenden Thread. */
    public interface FillListener {
        boolean isCancelled();
        void cellFinished(int cellX, int cellY, int round, int cellsDone, int cellsInRound);
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

    public static final class FillResult {
        private final int fromLibrary;
        private final int generated;
        private final List<Integer> filledPerRound;

        private FillResult(int fromLibrary, int generated, List<Integer> filledPerRound) {
            this.fromLibrary = fromLibrary;
            this.generated = generated;
            this.filledPerRound = filledPerRound;
        }

        public int getFromLibrary() { return fromLibrary; }
        public int getGenerated() { return generated; }
        public List<Integer> getFilledPerRound() { return filledPerRound; }
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
        clearAll(map);

        int filledCells = 0;
        int patternCells = 0;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (!isPatternCell(x, y)) continue;
                patternCells++;
                if (placeFromLibrary(map, x, y, entries, random)) filledCells++;
            }
        }
        return new Result(filledCells, patternCells);
    }

    public static void clearAll(MacroMap map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                map.getCell(x, y).clearHeightRaster();
            }
        }
    }

    private static boolean placeFromLibrary(MacroMap map, int x, int y, List<LTileRasterLibrary.Entry> entries, Random random) {
        List<LTileRasterLibrary.Entry> shuffled = new ArrayList<>(entries);
        Collections.shuffle(shuffled, random);
        HeightRasterPlacement placement = new HeightRasterPlacement(map, x, y);
        for (LTileRasterLibrary.Entry entry : shuffled) {
            HeightRasterPlacement.Result result = placement.evaluate(entry.getRaster());
            if (result != null && result.keepsCellsReachable()) {
                result.apply(entry.getFileName());
                return true;
            }
        }
        return false;
    }

    /**
     * Belegt alle leeren Zellen: zuerst mit einem zufälligen passenden Raster aus der Bibliothek, sonst
     * mit einem erzeugten. Jeder Durchgang beginnt mit den Zellen, die die meisten belegten Nachbarn
     * haben, und innerhalb gleicher Nachbarzahl von Nordwest nach Südost. Auf einer leeren Karte ergibt
     * das Reihe für Reihe, jede Zelle findet dann höchstens ihren West- und Nordrand als Vorgabe vor.
     * Was leer bleibt, kommt im nächsten Durchgang noch einmal dran. Beim Abbruch bleiben die bis dahin
     * gesetzten Raster erhalten.
     *
     * Scheitert eine Zelle im ersten Durchgang, gibt es sofort einen zweiten Versuch: Bleibt sie leer,
     * wachsen die Bereiche links und rechts von ihr getrennt weiter, und ihre Höhen laufen so weit
     * auseinander, dass auch die Zellen darunter unlösbar werden.
     *
     * @param firstRoundRetryMillis die Zeit für den zweiten Versuch
     * @param rounds die höchste Zahl der Durchgänge
     */
    public static FillResult fillEmptyCells(MacroMap map, List<LTileRasterLibrary.Entry> entries, Random random,
            long timeoutPerCellMillis, long firstRoundRetryMillis, int rounds, FillListener listener) throws IOException {
        List<Integer> filledPerRound = new ArrayList<>();
        int fromLibrary = 0;
        int generated = 0;

        for (int round = 0; round < rounds; round++) {
            int filled = 0;
            List<int[]> cells = emptyCellsByNeighbourCount(map);
            if (cells.isEmpty()) break;
            for (int i = 0; i < cells.size(); i++) {
                if (listener.isCancelled()) {
                    filledPerRound.add(filled);
                    return new FillResult(fromLibrary, generated, filledPerRound);
                }
                int[] cell = cells.get(i);
                if (placeFromLibrary(map, cell[0], cell[1], entries, random)) {
                    fromLibrary++;
                    filled++;
                } else if (placeGenerated(map, cell[0], cell[1], random, timeoutPerCellMillis)
                        || (round == 0 && !listener.isCancelled() && placeGenerated(map, cell[0], cell[1], random, firstRoundRetryMillis))) {
                    generated++;
                    filled++;
                }
                listener.cellFinished(cell[0], cell[1], round, i + 1, cells.size());
            }
            filledPerRound.add(filled);
            if (filled == 0) break;
        }
        return new FillResult(fromLibrary, generated, filledPerRound);
    }

    private static boolean placeGenerated(MacroMap map, int x, int y, Random random, long timeoutMillis) throws IOException {
        HeightRasterPlacement placement = new HeightRasterPlacement(map, x, y);
        LTileRaster raster = LTileRasterGenerator.generate(() -> placement.proposeEdges(random), timeoutMillis);
        HeightRasterPlacement.Result result = (raster != null) ? placement.evaluate(raster) : null;
        if (result == null) return false;
        result.apply(GENERATED_SOURCE);
        return true;
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
