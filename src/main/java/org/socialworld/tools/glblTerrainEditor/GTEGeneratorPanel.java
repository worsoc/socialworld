package org.socialworld.tools.glblTerrainEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Ein eigenständiges GUI-Modul für die Weltgenerierung im RAM.
 * Hält die Auswahlbox für die Profile und den Generierungs-Button.
 */
public class GTEGeneratorPanel extends JPanel {

    public GTEGeneratorPanel(GlobalTerrainEditorCanvas canvas, GlobalTerrainEditor editor) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        add(new JLabel("<html><b>WELT GENERIEREN</b></html>"));
        add(Box.createVerticalStrut(5));
        
        // JComboBox mit den 4 Profil-Katalogen aus dem Enum füttern
        JComboBox<GTEWorldProfile> profileBox = new JComboBox<>(GTEWorldProfile.values());
        profileBox.setMaximumSize(new Dimension(210, 26));
        profileBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(profileBox);
        add(Box.createVerticalStrut(6));
        
        JButton genButton = new JButton("Welt neu generieren");
        genButton.setMaximumSize(new Dimension(210, 28));
        genButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        genButton.setBackground(new Color(110, 50, 140)); // Lila für die Schöpfungs-Funktion
        genButton.setForeground(Color.WHITE);
        
        genButton.addActionListener(e -> {
            GTEWorldProfile selectedProfile = (GTEWorldProfile) profileBox.getSelectedItem();
            editor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            try {
                // Statischer RAM-Aufruf Ihrer Pipeline im Generator
                MacroMap generatedMap = GlobalTerrainGenerator.generateWorldInMemory(selectedProfile);
                
                // Neue Map live ins Canvas schießen
                canvas.setMacroMap(generatedMap);
                editor.updateStatusBarText();
                canvas.repaint();
                
                JOptionPane.showMessageDialog(editor, "Neue Welt (" + selectedProfile + ") erfolgreich generiert!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(editor, "Fehler bei der Generierung: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } finally {
                editor.setCursor(Cursor.getDefaultCursor());
            }
        });
        add(genButton);
    }
}
