package org.socialworld.tools.glblTerrainEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import org.socialworld.tools.mct.LTileRaster;
import org.socialworld.tools.mct.LTileRasterGenerator;

/**
 * Erzeugt im Hintergrund ein Raster für eine Makro-Zelle und ordnet es zu. Fehlende Nachbarn
 * lassen die jeweilige Seite frei.
 * Der Dialog ist modal, damit sich die Karte während der Suche nicht ändert.
 */
public class GTEHeightRasterGenerationDialog extends JDialog {

    private static final long TIMEOUT_MILLIS = 30000;

    private final MacroMap map;
    private final int cellX;
    private final int cellY;
    private boolean cancelled = false;
    private String errorMessage;

    public GTEHeightRasterGenerationDialog(Frame owner, MacroMap map, int cellX, int cellY) {
        super(owner, "Höhenraster erzeugen", true);
        this.map = map;
        this.cellX = cellX;
        this.cellY = cellY;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> {
            cancelled = true;
            cancelButton.setEnabled(false);
            LTileRasterGenerator.cancel();
        });
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        content.add(new JLabel("Suche Raster für Zelle [" + cellX + "," + cellY + "] ..."), BorderLayout.NORTH);
        content.add(progress, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);
        setContentPane(content);

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    /**
     * Startet die Suche und kehrt erst zurück, wenn sie beendet ist.
     */
    public void generate() {
        new SwingWorker<LTileRaster, Void>() {
            @Override
            protected LTileRaster doInBackground() throws Exception {
                HeightRasterPlacement placement = new HeightRasterPlacement(map, cellX, cellY);
                Random random = new Random();
                return LTileRasterGenerator.generate(() -> placement.proposeEdges(random), TIMEOUT_MILLIS);
            }

            @Override
            protected void done() {
                finish(this);
                dispose();
            }
        }.execute();

        setVisible(true);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(getOwner(), errorMessage, "Höhenraster erzeugen", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void finish(SwingWorker<LTileRaster, Void> worker) {
        if (cancelled) return;

        try {
            LTileRaster raster = worker.get();
            if (raster == null) {
                errorMessage = "Für Zelle [" + cellX + "," + cellY + "] wurde kein passendes Raster gefunden.";
                return;
            }
            HeightRasterPlacement.Result result = new HeightRasterPlacement(map, cellX, cellY).evaluate(raster);
            if (result == null) {
                errorMessage = "Das erzeugte Raster passt nicht in Zelle [" + cellX + "," + cellY + "] und wurde nicht zugeordnet.";
                return;
            }
            result.apply(HeightModelFiller.GENERATED_SOURCE);
        } catch (InterruptedException | ExecutionException e) {
            errorMessage = "Fehler beim Erzeugen: " + e.getMessage();
        }
    }
}
