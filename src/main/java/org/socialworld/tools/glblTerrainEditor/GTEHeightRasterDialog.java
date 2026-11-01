package org.socialworld.tools.glblTerrainEditor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.socialworld.tools.mct.LTileRasterLibrary;
import org.socialworld.tools.mct.MapPanel;

/**
 * Zeigt die Raster, die in eine Makro-Zelle passen, mit Vorschau und ordnet das gewählte zu.
 */
public class GTEHeightRasterDialog extends JDialog {

    private static final class Candidate {
        final LTileRasterLibrary.Entry entry;
        final HeightRasterPlacement.Result result;

        Candidate(LTileRasterLibrary.Entry entry, HeightRasterPlacement.Result result) {
            this.entry = entry;
            this.result = result;
        }

        @Override
        public String toString() {
            return entry.getFileName();
        }
    }

    private final LTileRasterLibrary library;
    private final HeightRasterPlacement placement;
    private final DefaultListModel<Candidate> candidates = new DefaultListModel<>();
    private final JList<Candidate> candidateList = new JList<>(candidates);
    private final MapPanel preview = new MapPanel(null);
    private final JLabel libraryLabel = new JLabel();
    private final JButton assignButton = new JButton("Zuordnen");
    private final JButton reloadButton = new JButton("Neu laden");

    public GTEHeightRasterDialog(Frame owner, MacroMap map, int cellX, int cellY, LTileRasterLibrary library) {
        super(owner, "Höhenraster für Zelle [" + cellX + "," + cellY + "]", true);
        this.library = library;
        this.placement = new HeightRasterPlacement(map, cellX, cellY);

        MacroMapCell cell = map.getCell(cellX, cellY);

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        top.add(new JLabel(cell.hasHeightRaster()
                ? "Aktuell: " + cell.getHeightRasterSource() + " (Versatz " + cell.getHeightRasterOffset() + ")"
                : "Aktuell: kein Raster"), BorderLayout.NORTH);
        top.add(libraryLabel, BorderLayout.CENTER);

        JButton folderButton = new JButton("Ordner wählen ...");
        folderButton.addActionListener(e -> chooseFolder());
        reloadButton.addActionListener(e -> load(library.getDirectory()));
        JPanel folderButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        folderButtons.add(folderButton);
        folderButtons.add(reloadButton);
        top.add(folderButtons, BorderLayout.EAST);

        candidateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        candidateList.addListSelectionListener(e -> updateSelection());
        candidateList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && candidateList.getSelectedValue() != null) assignSelected();
            }
        });
        JScrollPane listScroll = new JScrollPane(candidateList);
        listScroll.setPreferredSize(new Dimension(300, 600));
        preview.setPreferredSize(new Dimension(780, 600));

        assignButton.addActionListener(e -> assignSelected());
        JButton removeButton = new JButton("Entfernen");
        removeButton.setEnabled(cell.hasHeightRaster());
        removeButton.addActionListener(e -> {
            cell.clearHeightRaster();
            dispose();
        });
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> dispose());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(assignButton);
        bottom.add(removeButton);
        bottom.add(cancelButton);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(listScroll, BorderLayout.WEST);
        add(preview, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshCandidates();
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Zeigt den Dialog an. Ist noch keine Bibliothek geladen, wird zuerst nach dem Ordner gefragt.
     */
    public void open() {
        if (!library.isLoaded()) {
            chooseFolder();
        }
        setVisible(true);
    }

    private void chooseFolder() {
        JFileChooser chooser = library.isLoaded() ? new JFileChooser(library.getDirectory()) : new JFileChooser();
        chooser.setDialogTitle("Ordner mit den Rastern auswählen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            load(chooser.getSelectedFile());
        }
    }

    private void load(File directory) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            library.load(directory);
            refreshCandidates();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void refreshCandidates() {
        candidates.clear();
        for (LTileRasterLibrary.Entry entry : library.getEntries()) {
            HeightRasterPlacement.Result result = placement.evaluate(entry.getRaster());
            if (result != null) {
                candidates.addElement(new Candidate(entry, result));
            }
        }

        reloadButton.setEnabled(library.isLoaded());
        if (library.isLoaded()) {
            libraryLabel.setText(library.getDirectory().getName() + ": " + library.getEntries().size() + " Raster, davon "
                    + candidates.size() + " passend (" + library.getSkippedFileCount() + " Dateien übersprungen)");
        } else {
            libraryLabel.setText("Noch kein Ordner gewählt");
        }

        if (candidates.isEmpty()) {
            updateSelection();
        } else {
            candidateList.setSelectedIndex(0);
        }
    }

    private void updateSelection() {
        Candidate selected = candidateList.getSelectedValue();
        assignButton.setEnabled(selected != null);
        preview.updateMapData((selected != null) ? selected.entry.getRaster().toGrid() : null);
    }

    private void assignSelected() {
        Candidate selected = candidateList.getSelectedValue();
        selected.result.apply(selected.entry.getFileName());
        dispose();
    }
}
