package org.socialworld.tools.glblTerrainEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import org.socialworld.tools.mct.LTileRasterGenerator;
import org.socialworld.tools.mct.LTileRasterLibrary;

/**
 * Setzt das ganze Höhenmodell im Hintergrund: Die Karte wird geleert und von Nordwest nach Südost neu
 * belegt, in bis zu drei Durchgängen. Das dauert viele Minuten und lässt sich abbrechen; die bis dahin
 * gesetzten Raster bleiben dann erhalten.
 * Der Dialog ist modal, damit sich die Karte währenddessen nicht ändert.
 */
public class GTEHeightModelDialog extends JDialog {

    private static final String TITLE = "Höhenmodell setzen";
    private static final long TIMEOUT_PER_CELL_MILLIS = 10000;
    private static final long FIRST_ROUND_RETRY_MILLIS = 20000;
    private static final int ROUNDS = 3;

    private final MacroMap map;
    private final LTileRasterLibrary library;
    private final BiConsumer<Integer, Integer> onCellFinished;
    private final JLabel statusLabel = new JLabel("Starte im Nordwesten ...");
    private final JProgressBar progress = new JProgressBar();

    private volatile boolean cancelled = false;
    private volatile HeightModelFiller.FillResult fillResult;
    private String message;

    /**
     * @param onCellFinished läuft nach jeder bearbeiteten Zelle im Swing-Thread und erhält deren x und y
     */
    public GTEHeightModelDialog(Window owner, MacroMap map, LTileRasterLibrary library, BiConsumer<Integer, Integer> onCellFinished) {
        super(owner, TITLE, ModalityType.APPLICATION_MODAL);
        this.map = map;
        this.library = library;
        this.onCellFinished = onCellFinished;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        progress.setIndeterminate(true);

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> {
            cancelled = true;
            cancelButton.setEnabled(false);
            statusLabel.setText("Breche ab ...");
            LTileRasterGenerator.cancel();
        });
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        content.add(statusLabel, BorderLayout.NORTH);
        content.add(progress, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);
        setContentPane(content);

        // Der Text ändert sich laufend, das Fenster soll dabei nicht springen.
        setPreferredWidth(content, 420);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private static void setPreferredWidth(JPanel panel, int width) {
        panel.setPreferredSize(new Dimension(width, panel.getPreferredSize().height));
    }

    /**
     * Startet die Arbeit und kehrt erst zurück, wenn sie beendet ist.
     *
     * @return die Meldung über das Ergebnis
     */
    public String run() {
        new SwingWorker<Void, int[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                Random random = new Random();
                HeightModelFiller.clearAll(map);
                fillResult = HeightModelFiller.fillEmptyCells(map, library.getEntries(), random, TIMEOUT_PER_CELL_MILLIS, FIRST_ROUND_RETRY_MILLIS, ROUNDS,
                        new HeightModelFiller.FillListener() {
                            @Override
                            public boolean isCancelled() {
                                return cancelled;
                            }

                            @Override
                            public void cellFinished(int cellX, int cellY, int round, int cellsDone, int cellsInRound) {
                                publish(new int[] {round, cellsDone, cellsInRound, HeightModelFiller.countOccupiedCells(map), cellX, cellY});
                            }
                        });
                return null;
            }

            @Override
            protected void process(List<int[]> updates) {
                int[] latest = updates.get(updates.size() - 1);
                onCellFinished.accept(latest[4], latest[5]);
                if (cancelled) return;
                statusLabel.setText("Durchgang " + (latest[0] + 1) + " von " + ROUNDS + ": Zelle " + latest[1] + " von " + latest[2]
                        + ", belegt " + latest[3] + " von " + (map.getWidth() * map.getHeight()));
                progress.setIndeterminate(false);
                progress.setMaximum(latest[2]);
                progress.setValue(latest[1]);
            }

            @Override
            protected void done() {
                message = buildMessage(this);
                dispose();
            }
        }.execute();

        setVisible(true);

        return message;
    }

    private String buildMessage(SwingWorker<Void, int[]> worker) {
        int cells = map.getWidth() * map.getHeight();
        int occupied = HeightModelFiller.countOccupiedCells(map);
        String summary = occupied + " von " + cells + " Zellen belegt, " + (cells - occupied) + " leer.";
        if (fillResult != null) {
            summary += "\nAus Datei: " + fillResult.getFromLibrary() + ", erzeugt: " + fillResult.getGenerated()
                    + " (" + library.getEntries().size() + " Raster aus " + library.getDirectory().getName() + ").";
            summary += "\nNeu je Durchgang: " + fillResult.getFilledPerRound().stream().map(String::valueOf).collect(Collectors.joining(", ")) + ".";
        }

        try {
            worker.get();
        } catch (InterruptedException | ExecutionException e) {
            return "Fehler beim Setzen: " + e.getMessage() + "\n" + summary;
        }
        return cancelled ? "Abgebrochen. Die bisher gesetzten Raster bleiben erhalten.\n" + summary : summary;
    }
}
