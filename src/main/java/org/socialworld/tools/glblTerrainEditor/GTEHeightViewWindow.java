package org.socialworld.tools.glblTerrainEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.socialworld.tools.mct.MapPanel;
import org.socialworld.tools.mct.VisualTile;

/**
 * Frei schwebendes Fenster für die Weltkarte, das die Höhenfläche der Zelle unter dem Mauszeiger zeigt,
 * wahlweise allein oder zusammen mit ihren acht Nachbarn.
 */
public class GTEHeightViewWindow extends JDialog {

    private static final int RASTER_SIZE = 9;
    private static final int SECTORS = 3;
    private static final int HOVER_DELAY_MILLIS = 2000;

    private static final Dimension SINGLE_VIEW_SIZE = new Dimension(800, 640);
    private static final Dimension SURROUNDING_VIEW_SIZE = new Dimension(1120, 780);

    private final GlobalTerrainEditorCanvas canvas;
    private final MapPanel panel = new MapPanel(null);
    private final JRadioButton singleView = new JRadioButton("Einzelansicht (9x9)", true);
    private final JRadioButton surroundingView = new JRadioButton("Umgebung (27x27)");

    private final Timer hoverTimer;

    private int shownCellX = -1;
    private int shownCellY = -1;
    private int pendingCellX = -1;
    private int pendingCellY = -1;

    public GTEHeightViewWindow(Frame owner, GlobalTerrainEditorCanvas canvas) {
        super(owner, false);
        this.canvas = canvas;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        hoverTimer = new Timer(HOVER_DELAY_MILLIS, e -> showPendingCell());
        hoverTimer.setRepeats(false);

        ButtonGroup views = new ButtonGroup();
        views.add(singleView);
        views.add(surroundingView);
        singleView.addActionListener(e -> viewChanged());
        surroundingView.addActionListener(e -> viewChanged());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(singleView);
        top.add(surroundingView);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setSize(SINGLE_VIEW_SIZE);
        refresh();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible && !isVisible()) {
            placeBesideOwner();
            refresh();
        }
        if (!visible) {
            cancelRequest();
        }
        super.setVisible(visible);
        canvas.repaint();
    }

    /**
     * Merkt sich die Zelle unter dem Mauszeiger. Angezeigt wird sie erst, wenn der Mauszeiger
     * HOVER_DELAY_MILLIS lang in dieser Zelle geblieben ist. So bleibt die Ansicht beim Bewegen der Maus ruhig.
     */
    public void requestCell(int cellX, int cellY) {
        if (cellX == pendingCellX && cellY == pendingCellY) return;
        cancelRequest();
        if (cellX == shownCellX && cellY == shownCellY) return;

        pendingCellX = cellX;
        pendingCellY = cellY;
        hoverTimer.start();
    }

    /** Verwirft eine noch nicht angezeigte Zelle, zum Beispiel wenn die Maus die Karte verlässt. */
    public void cancelRequest() {
        hoverTimer.stop();
        pendingCellX = -1;
        pendingCellY = -1;
    }

    /** Zeigt eine Zelle sofort an, zum Beispiel nach einem Klick auf die Karte. */
    public void showCellNow(int cellX, int cellY) {
        cancelRequest();
        shownCellX = cellX;
        shownCellY = cellY;
        refresh();
    }

    private void showPendingCell() {
        showCellNow(pendingCellX, pendingCellY);
    }

    public int getShownCellX() { return shownCellX; }
    public int getShownCellY() { return shownCellY; }

    /** Vergisst die angezeigte Zelle, zum Beispiel weil eine andere Karte geladen wurde. */
    public void clear() {
        cancelRequest();
        shownCellX = -1;
        shownCellY = -1;
        refresh();
    }

    /** Zeichnet die aktuelle Zelle neu, zum Beispiel nachdem sich ihr Raster geändert hat. */
    public void refresh() {
        canvas.repaint();

        if (shownCellX < 0) {
            setTitle("Höhenansicht: Maus über die Weltkarte bewegen");
            panel.updateMapData(null);
            return;
        }

        MacroMap map = canvas.getMacroMap();
        MacroMapCell cell = map.getCell(shownCellX, shownCellY);
        String cellName = "Zelle [" + shownCellX + "," + shownCellY + "]";
        setTitle(cell.hasHeightRaster()
                ? "Höhenansicht: " + cellName + ", " + cell.getHeightRasterSource() + ", Versatz " + cell.getHeightRasterOffset()
                : "Höhenansicht: " + cellName + ", kein Raster");

        panel.updateMapData(surroundingView.isSelected()
                ? buildSurroundingGrid(map, shownCellX, shownCellY)
                : buildCellGrid(map, shownCellX, shownCellY));
    }

    private void viewChanged() {
        setSize(surroundingView.isSelected() ? SURROUNDING_VIEW_SIZE : SINGLE_VIEW_SIZE);
        if (isVisible()) placeBesideOwner();
        refresh();
    }

    /** Stellt das Fenster rechts neben den Editor, soweit der Bildschirm das zulässt. */
    private void placeBesideOwner() {
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int x = Math.max(screen.x, Math.min(getOwner().getX() + getOwner().getWidth(), screen.x + screen.width - getWidth()));
        int y = Math.max(screen.y, Math.min(getOwner().getY(), screen.y + screen.height - getHeight()));
        setLocation(x, y);
    }

    /**
     * @return das Raster der Zelle oder null, wenn sie keines hat
     */
    public static VisualTile[][] buildCellGrid(MacroMap map, int cellX, int cellY) {
        MacroMapCell cell = map.getCell(cellX, cellY);
        return cell.hasHeightRaster() ? cell.getHeightRaster().toGrid() : null;
    }

    /**
     * Baut das 27x27-Grid aus der Zelle und ihren acht Nachbarn. Die Höhen stehen über den Versatz richtig
     * zueinander und sind auf die mittlere Zelle bezogen, damit die Fläche unabhängig von der absoluten
     * Höhe im Bild bleibt. Felder ohne Raster bleiben null.
     *
     * @return das Grid oder null, wenn keine der neun Zellen ein Raster hat
     */
    public static VisualTile[][] buildSurroundingGrid(MacroMap map, int cellX, int cellY) {
        Integer referenceOffset = referenceOffset(map, cellX, cellY);
        if (referenceOffset == null) return null;

        int size = SECTORS * RASTER_SIZE;
        VisualTile[][] grid = new VisualTile[size][size];

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                MacroMapCell cell = occupiedCell(map, cellX + dx, cellY + dy);
                if (cell == null) continue;

                int heightShift = cell.getHeightRasterOffset() - referenceOffset;
                VisualTile[][] raster = cell.getHeightRaster().toGrid();
                for (int row = 0; row < RASTER_SIZE; row++) {
                    for (int col = 0; col < RASTER_SIZE; col++) {
                        VisualTile tile = raster[row][col];
                        grid[(dy + 1) * RASTER_SIZE + row][(dx + 1) * RASTER_SIZE + col] =
                                new VisualTile(tile.type, tile.baseHeight + heightShift);
                    }
                }
            }
        }
        return grid;
    }

    private static Integer referenceOffset(MacroMap map, int cellX, int cellY) {
        MacroMapCell center = occupiedCell(map, cellX, cellY);
        if (center != null) return center.getHeightRasterOffset();

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                MacroMapCell cell = occupiedCell(map, cellX + dx, cellY + dy);
                if (cell != null) return cell.getHeightRasterOffset();
            }
        }
        return null;
    }

    private static MacroMapCell occupiedCell(MacroMap map, int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight()) return null;
        MacroMapCell cell = map.getCell(x, y);
        return cell.hasHeightRaster() ? cell : null;
    }
}
