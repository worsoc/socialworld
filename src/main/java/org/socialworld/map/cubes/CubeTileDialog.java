/*
* Social World
* Copyright (C) 2026  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.map.cubes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Lets you choose size, tile, height and depth, creates the tile and shows the
 * result as pictures and statistics, as a tile has up to more than a million
 * addresses. All addresses can be saved to a file.
 */
public class CubeTileDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public CubeTileDialog() {
		super((java.awt.Frame) null, "Kachel als Würfel", false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ContentPanel content = new ContentPanel();
		setContentPane(content);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent event) {
				content.closeFamilyWindow();
			}
		});
		pack();
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CubeTileDialog().setVisible(true));
	}

	private static final class Choice<T> {
		final T value;
		final String label;

		Choice(T value, String label) {
			this.value = value;
			this.label = label;
		}

		@Override
		public String toString() {
			return this.label;
		}
	}

	static final class ContentPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private static final int SAMPLE_ADDRESSES = 12;

		private final JComboBox<Choice<Byte>> sizeBox = new JComboBox<>();
		private final JComboBox<Choice<Boolean>> kindBox = new JComboBox<>();
		private final JComboBox<Choice<Integer>> tileBox = new JComboBox<>();
		private final JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));
		private final JSpinner depthSpinner = new JSpinner(new SpinnerNumberModel(4, 1, CubeTileAnalysis.MAX_DEPTH, 1));
		private final JLabel heightHint = new JLabel();
		private final JLabel depthHint = new JLabel();
		private final JButton startButton = new JButton("Berechnen");
		private final JButton saveButton = new JButton("Alle Adressen speichern ...");
		private final JTextArea summary = new JTextArea(12, 40);
		private final JTextArea sample = new JTextArea(12, 22);
		private final JSlider cutSlider = new JSlider(0, 1000, 500);
		private final JLabel cutLabel = new JLabel(" ");
		private final TopView topView = new TopView();
		private final SectionView sectionView = new SectionView();

		private CubeTileAnalysis analysis;
		private CubeFamilyDialog familyWindow;

		ContentPanel() {
			super(new BorderLayout(10, 10));
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			add(createSettings(), BorderLayout.WEST);
			add(createViews(), BorderLayout.CENTER);
			add(createTexts(), BorderLayout.SOUTH);

			this.sizeBox.addItem(new Choice<>(Cube.CUBE_SIZE_TILE_LARGE, "Large (81 m)"));
			this.sizeBox.addItem(new Choice<>(Cube.CUBE_SIZE_TILE_MEDIUM, "Medium (9 m)"));
			this.sizeBox.addItem(new Choice<>(Cube.CUBE_SIZE_TILE_SMALL, "Small (1 m)"));

			this.sizeBox.addActionListener(event -> sizeChanged());
			this.kindBox.addActionListener(event -> kindChanged());
			this.tileBox.addActionListener(event -> updateHeightLimits());
			this.depthSpinner.addChangeListener(event -> updateDepthHint());
			this.startButton.addActionListener(event -> start());
			this.saveButton.addActionListener(event -> saveAddresses());
			this.cutSlider.addChangeListener(event -> cutChanged());

			this.saveButton.setEnabled(false);
			this.summary.setText("Einstellungen wählen und \"Berechnen\" drücken.");
			sizeChanged();
		}

		private JPanel createSettings() {
			JPanel settings = new JPanel(new GridBagLayout());
			settings.setBorder(BorderFactory.createTitledBorder("Einstellungen"));
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(4, 6, 4, 6);
			c.gridx = 0;
			c.gridy = 0;

			addRow(settings, c, "Größe", this.sizeBox);
			addRow(settings, c, "Art", this.kindBox);
			addRow(settings, c, "Kachel", this.tileBox);
			addRow(settings, c, "Starthöhe", this.heightSpinner);
			addHint(settings, c, this.heightHint);
			addRow(settings, c, "Tiefe", this.depthSpinner);
			addHint(settings, c, this.depthHint);

			c.gridx = 0;
			c.gridwidth = 2;
			c.insets = new Insets(12, 6, 4, 6);
			settings.add(this.startButton, c);

			c.gridy++;
			c.weighty = 1;
			settings.add(new JLabel(), c);

			// a long description in the list must not make the whole dialog wide
			this.tileBox.setPrototypeDisplayValue(new Choice<>(0, "000 - NW nach NO, Schritt 9/9, übrige Ecken auf Höhe 0"));
			return settings;
		}

		private static void addRow(JPanel panel, GridBagConstraints c, String label, java.awt.Component input) {
			c.gridx = 0;
			c.gridwidth = 1;
			panel.add(new JLabel(label), c);
			c.gridx = 1;
			panel.add(input, c);
			c.gridy++;
		}

		private static void addHint(JPanel panel, GridBagConstraints c, JLabel hint) {
			c.gridx = 1;
			c.gridwidth = 1;
			hint.setFont(hint.getFont().deriveFont(Font.PLAIN, hint.getFont().getSize2D() - 1));
			panel.add(hint, c);
			c.gridy++;
		}

		private JPanel createViews() {
			JPanel views = new JPanel(new GridLayout(1, 2, 10, 0));

			JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.setBorder(BorderFactory.createTitledBorder("Draufsicht: Höhe der Oberfläche"));
			topPanel.add(this.topView, BorderLayout.CENTER);

			JPanel sectionPanel = new JPanel(new BorderLayout(0, 4));
			sectionPanel.setBorder(BorderFactory.createTitledBorder("Querschnitt von West nach Ost"));
			sectionPanel.add(this.sectionView, BorderLayout.CENTER);
			JPanel cutPanel = new JPanel(new BorderLayout(6, 0));
			cutPanel.add(this.cutLabel, BorderLayout.WEST);
			cutPanel.add(this.cutSlider, BorderLayout.CENTER);
			sectionPanel.add(cutPanel, BorderLayout.SOUTH);

			views.add(topPanel);
			views.add(sectionPanel);
			return views;
		}

		private JPanel createTexts() {
			JPanel texts = new JPanel(new BorderLayout(10, 0));

			this.summary.setEditable(false);
			this.summary.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			JScrollPane summaryPane = new JScrollPane(this.summary);
			summaryPane.setBorder(BorderFactory.createTitledBorder("Ergebnis"));
			texts.add(summaryPane, BorderLayout.CENTER);

			this.sample.setEditable(false);
			this.sample.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			JScrollPane samplePane = new JScrollPane(this.sample);
			samplePane.setBorder(BorderFactory.createTitledBorder("Adressen (Auszug)"));
			JPanel samplePanel = new JPanel(new BorderLayout(0, 4));
			samplePanel.add(samplePane, BorderLayout.CENTER);
			samplePanel.add(this.saveButton, BorderLayout.SOUTH);
			texts.add(samplePanel, BorderLayout.EAST);
			return texts;
		}

		private byte selectedSize() {
			return ((Choice<Byte>) this.sizeBox.getSelectedItem()).value;
		}

		private int selectedTile() {
			return ((Choice<Integer>) this.tileBox.getSelectedItem()).value;
		}

		private void sizeChanged() {
			byte size = selectedSize();

			Choice<Boolean> previous = (Choice<Boolean>) this.kindBox.getSelectedItem();
			DefaultComboBoxModel<Choice<Boolean>> kinds = new DefaultComboBoxModel<>();
			Choice<Boolean> standard = new Choice<>(false, "Standard");
			Choice<Boolean> adapter = new Choice<>(true, "Adapter");
			kinds.addElement(standard);
			if (CubeTileAnalysis.hasAdapterTiles(size)) {
				kinds.addElement(adapter);
			}
			kinds.setSelectedItem(previous != null && previous.value && CubeTileAnalysis.hasAdapterTiles(size) ? adapter : standard);
			this.kindBox.setModel(kinds);

			this.depthSpinner.setValue(CubeTileAnalysis.getDefaultDepth(size));
			kindChanged();
		}

		private void kindChanged() {
			Choice<Boolean> kind = (Choice<Boolean>) this.kindBox.getSelectedItem();
			if (kind == null) {
				return;
			}

			Integer previous = this.tileBox.getSelectedItem() == null ? null : selectedTile();
			List<Integer> tiles = kind.value ? CubeTileAnalysis.getAdapterTiles()
					: CubeTileAnalysis.getStandardTiles(selectedSize());

			DefaultComboBoxModel<Choice<Integer>> model = new DefaultComboBoxModel<>();
			for (int tile : tiles) {
				Choice<Integer> choice = new Choice<>(tile, tile + " - " + CubeTileAnalysis.describeTile(tile));
				model.addElement(choice);
				if (previous != null && previous == tile) {
					model.setSelectedItem(choice);
				}
			}
			this.tileBox.setModel(model);
			updateHeightLimits();
			updateDepthHint();
		}

		private void updateHeightLimits() {
			if (this.tileBox.getSelectedItem() == null) {
				return;
			}
			byte size = selectedSize();
			int min = CubeTileAnalysis.getMinHeight(size, selectedTile());
			int max = CubeTileAnalysis.getMaxHeight(size);
			int value = Math.max(min, Math.min(max, (Integer) this.heightSpinner.getValue()));

			this.heightSpinner.setModel(new SpinnerNumberModel(value, min, max, 1));
			this.heightSpinner.setEnabled(min < max);
			this.heightHint.setText(min == max ? "bei Small immer " + min : "erlaubt: " + min + " bis " + max + " m");
		}

		private void updateDepthHint() {
			int depth = (Integer) this.depthSpinner.getValue();
			this.depthHint.setText("kleinster Würfel: " + CubeTileAnalysis.describeEdge(selectedSize(), depth));
		}

		private void start() {
			int tile = selectedTile();
			byte size = selectedSize();
			byte height = ((Integer) this.heightSpinner.getValue()).byteValue();
			byte depth = ((Integer) this.depthSpinner.getValue()).byteValue();

			this.startButton.setEnabled(false);
			this.saveButton.setEnabled(false);
			boolean isAdapter = CubeTileAnalysis.isAdapterTile(tile);
			this.summary.setText(isAdapter ? "Die 9 Kacheln der Familie werden berechnet ..." : "Wird berechnet ...");

			new SwingWorker<CubeFamilyAnalysis, Void>() {
				private CubeTileAnalysis single;

				@Override
				protected CubeFamilyAnalysis doInBackground() {
					if (isAdapter) {
						CubeFamilyAnalysis family = new CubeFamilyAnalysis(tile, size, height, depth);
						this.single = family.getTile(tile);
						return family;
					}
					this.single = new CubeTileAnalysis(tile, size, height, depth);
					return null;
				}

				@Override
				protected void done() {
					ContentPanel.this.startButton.setEnabled(true);
					try {
						CubeFamilyAnalysis family = get();
						showResult(this.single);
						showFamily(family);
					} catch (Exception exception) {
						Throwable cause = exception.getCause() == null ? exception : exception.getCause();
						ContentPanel.this.summary.setText(cause instanceof OutOfMemoryError
								? "Zu wenig Speicher für diese Tiefe. Bitte eine kleinere Tiefe wählen."
								: "Fehler: " + cause.getMessage());
					}
				}
			}.execute();
		}

		private void showFamily(CubeFamilyAnalysis family) {
			if (family == null) {
				closeFamilyWindow();
				return;
			}
			if (this.familyWindow == null || !this.familyWindow.isDisplayable()) {
				this.familyWindow = new CubeFamilyDialog();
			}
			this.familyWindow.setFamily(family);
			this.familyWindow.setVisible(true);
		}

		void closeFamilyWindow() {
			if (this.familyWindow != null) {
				this.familyWindow.dispose();
				this.familyWindow = null;
			}
		}

		void showResult(CubeTileAnalysis result) {
			this.analysis = result;
			this.saveButton.setEnabled(!result.addresses.isEmpty());
			this.summary.setText(buildSummary(result));
			this.summary.setCaretPosition(0);
			this.sample.setText(buildSample(result));
			this.sample.setCaretPosition(0);
			this.topView.setAnalysis(result);
			this.sectionView.setAnalysis(result);
			cutChanged();
		}

		private static String buildSummary(CubeTileAnalysis result) {
			NumberFormat count = NumberFormat.getIntegerInstance();
			StringBuilder text = new StringBuilder();
			text.append("Kachel ").append(result.tile).append(": ").append(CubeTileAnalysis.describeTile(result.tile)).append('\n');
			text.append("Größe ").append(result.size).append(" m, Starthöhe ").append(result.height).append(" m, Tiefe ")
					.append(result.depth).append("\n\n");
			text.append(String.format("Adressen:      %s%n", count.format(result.addresses.size())));
			text.append(String.format("Speicher Baum: ca. %.0f MB%n", result.memoryBytes / 1e6));
			text.append(String.format("Rechenzeit:    %s ms%n", count.format(result.milliseconds)));
			text.append(String.format("Mittlere Höhe: %.3f m, Soll %.3f m (Abweichung %+.3f m)%n", result.meanHeight,
					result.exactMeanHeight, result.meanHeight - result.exactMeanHeight));
			if (result.addresses.isEmpty()) {
				return text.toString();
			}
			text.append("\nAdressen nach Tiefe:\n");
			for (int depth = 1; depth < result.addressesByDepth.length; depth++) {
				if (result.addressesByDepth[depth] > 0) {
					text.append(String.format("  Tiefe %d (Kante %s): %s%n", depth,
							CubeTileAnalysis.describeEdge(result.size, depth), count.format(result.addressesByDepth[depth])));
				}
			}
			return text.toString();
		}

		private static String buildSample(CubeTileAnalysis result) {
			if (result.addresses.isEmpty()) {
				return "keine Adressen:\nkein Würfel unter\nder Oberfläche";
			}
			StringBuilder text = new StringBuilder();
			for (String address : result.getFirstAddresses(SAMPLE_ADDRESSES)) {
				text.append(address).append('\n');
			}
			int rest = result.addresses.size() - SAMPLE_ADDRESSES;
			if (rest > 0) {
				text.append("... und ").append(NumberFormat.getIntegerInstance().format(rest)).append(" weitere");
			}
			return text.toString();
		}

		private void cutChanged() {
			if (this.analysis == null) {
				return;
			}
			double cut = this.cutSlider.getValue() / 1000.0 * this.analysis.size;
			cut = Math.min(cut, this.analysis.size - 1e-6);
			this.cutLabel.setText(String.format("Schnitt bei %.2f m nach Nord", cut));
			this.topView.setCut(cut);
			this.sectionView.setCut(cut);
		}

		private void saveAddresses() {
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new java.io.File("kachel_" + this.analysis.tile + "_tiefe_" + this.analysis.depth + ".txt"));
			if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			try {
				Files.write(chooser.getSelectedFile().toPath(), this.analysis.addresses);
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(this, "Speichern fehlgeschlagen: " + exception.getMessage(), "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	static String columnToolTip(CubeTileAnalysis analysis, double x, double y) {
		StringBuilder text = new StringBuilder("<html>");
		text.append(String.format("%.2f m nach Ost, %.2f m nach Nord<br>Soll %.3f m", x, y, analysis.surfaceHeightAt(x, y)));

		List<Integer> column = analysis.findCubesInColumn(x, y);
		if (column.isEmpty()) {
			return text.append("<br>kein Würfel</html>").toString();
		}

		text.append("<br><table cellspacing=0 cellpadding=1><tr><th align=left>Adresse&nbsp;&nbsp;</th><th align=left>Höhe von - bis</th></tr>");
		for (int cube : column) {
			text.append("<tr><td><tt>").append(analysis.addresses.get(cube)).append("</tt></td><td>")
					.append(String.format("%.3f - %.3f m", analysis.cubeZ[cube], analysis.cubeZ[cube] + analysis.cubeEdge[cube]))
					.append("</td></tr>");
		}
		return text.append("</table></html>").toString();
	}

	/** Light to dark in one hue, low to high. */
	static Color heightColor(double fraction) {
		float[] light = { 0.84f, 0.91f, 0.98f };
		float[] dark = { 0.05f, 0.22f, 0.45f };
		float f = (float) Math.max(0, Math.min(1, fraction));
		return new Color(light[0] + (dark[0] - light[0]) * f, light[1] + (dark[1] - light[1]) * f,
				light[2] + (dark[2] - light[2]) * f);
	}

	static final Color CUBE_FILL = new Color(0.45f, 0.62f, 0.82f);
	private static final Color CUBE_GAP = Color.WHITE;
	static final Color SURFACE_LINE = new Color(0.72f, 0.20f, 0.10f);
	static final Color AXIS_TEXT = new Color(0.30f, 0.30f, 0.30f);
	static final Color GRID_LINE = new Color(0.88f, 0.88f, 0.88f);

	private abstract static class View extends JPanel {
		private static final long serialVersionUID = 1L;

		protected CubeTileAnalysis analysis;
		protected double cut;

		View() {
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(360, 360));
			setToolTipText("");
		}

		void setAnalysis(CubeTileAnalysis analysis) {
			this.analysis = analysis;
			repaint();
		}

		void setCut(double cut) {
			this.cut = cut;
			repaint();
		}

		protected void drawCentered(Graphics2D g, String text, int centerX, int y) {
			g.drawString(text, centerX - g.getFontMetrics().stringWidth(text) / 2, y);
		}

		protected static String meters(double value) {
			return value == Math.rint(value) ? String.format("%.0f m", value) : String.format("%.2f m", value);
		}

		@Override
		protected void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D) graphics;
			g.setColor(AXIS_TEXT);
			if (this.analysis == null) {
				drawCentered(g, "noch nichts berechnet", getWidth() / 2, getHeight() / 2);
				return;
			}
			paintView(g);
		}

		protected abstract void paintView(Graphics2D g);
	}

	private static final class TopView extends View {
		private static final long serialVersionUID = 1L;
		private static final int MARGIN = 24;
		private static final int SIDE_MARGIN = 44;
		private static final int LEGEND_HEIGHT = 34;

		private int side() {
			return Math.max(10, Math.min(getWidth() - 2 * SIDE_MARGIN, getHeight() - 2 * MARGIN - LEGEND_HEIGHT));
		}

		private int left() {
			return (getWidth() - side()) / 2;
		}

		@Override
		protected void paintView(Graphics2D g) {
			int side = side();
			int left = left();
			int top = MARGIN;
			int cells = CubeTileAnalysis.TOP_VIEW_CELLS;
			double range = this.analysis.highestTop - this.analysis.lowestTop;

			for (int column = 0; column < cells; column++) {
				int x0 = left + column * side / cells;
				int x1 = left + (column + 1) * side / cells;
				for (int row = 0; row < cells; row++) {
					// north is at the top of the picture
					int y0 = top + side - (row + 1) * side / cells;
					int y1 = top + side - row * side / cells;
					double value = this.analysis.topHeights[column][row];
					g.setColor(heightColor(range > 0 ? (value - this.analysis.lowestTop) / range : 0));
					g.fillRect(x0, y0, x1 - x0, y1 - y0);
				}
			}

			g.setColor(AXIS_TEXT);
			g.drawRect(left, top, side, side);
			drawCentered(g, "Nord", left + side / 2, top - 6);
			drawCentered(g, "Süd", left + side / 2, top + side + 14);
			g.drawString("West", left - g.getFontMetrics().stringWidth("West") - 4, top + side / 2);
			g.drawString("Ost", left + side + 4, top + side / 2);

			int cutY = top + side - (int) (this.cut / this.analysis.size * side);
			g.setColor(SURFACE_LINE);
			g.setStroke(new BasicStroke(2));
			g.drawLine(left, cutY, left + side, cutY);

			int legendY = top + side + 20;
			if (range == 0) {
				g.setColor(AXIS_TEXT);
				drawCentered(g, "überall " + meters(this.analysis.highestTop), left + side / 2, legendY + 12);
				return;
			}
			int legendWidth = Math.min(side, 200);
			int legendX = left + (side - legendWidth) / 2;
			for (int i = 0; i < legendWidth; i++) {
				g.setColor(heightColor((double) i / legendWidth));
				g.fillRect(legendX + i, legendY, 1, 8);
			}
			g.setColor(AXIS_TEXT);
			g.drawString(meters(this.analysis.lowestTop), legendX, legendY + 21);
			String high = meters(this.analysis.highestTop);
			g.drawString(high, legendX + legendWidth - g.getFontMetrics().stringWidth(high), legendY + 21);
		}

		@Override
		public String getToolTipText(MouseEvent event) {
			if (this.analysis == null) {
				return null;
			}
			double x = (double) (event.getX() - left()) / side() * this.analysis.size;
			double y = (double) (MARGIN + side() - event.getY()) / side() * this.analysis.size;
			if (x < 0 || y < 0 || x >= this.analysis.size || y >= this.analysis.size) {
				return null;
			}
			return columnToolTip(this.analysis, x, y);
		}

	}

	private static final class SectionView extends View {
		private static final long serialVersionUID = 1L;
		private static final int LEFT = 44;
		private static final int RIGHT = 12;
		private static final int TOP = 12;
		private static final int BOTTOM = 52;

		private double lowestShown() {
			return Math.max(0, Math.floor(this.analysis.lowestSurface) - 1);
		}

		private double highestShown() {
			return Math.min(this.analysis.size, Math.ceil(this.analysis.highestSurface) + 1);
		}

		private int plotWidth() {
			return Math.max(10, getWidth() - LEFT - RIGHT);
		}

		private int plotHeight() {
			return Math.max(10, getHeight() - TOP - BOTTOM);
		}

		private int screenX(double x) {
			return LEFT + (int) Math.round(x / this.analysis.size * plotWidth());
		}

		private int screenY(double z) {
			double low = lowestShown();
			return TOP + plotHeight() - (int) Math.round((z - low) / (highestShown() - low) * plotHeight());
		}

		@Override
		protected void paintView(Graphics2D g) {
			double low = lowestShown();
			double high = highestShown();
			int width = plotWidth();
			int height = plotHeight();

			g.setClip(LEFT, TOP, width + 1, height + 1);
			int step = (int) Math.max(1, Math.ceil((high - low) / 8));
			g.setColor(GRID_LINE);
			for (double z = low; z <= high; z += step) {
				g.drawLine(LEFT, screenY(z), LEFT + width, screenY(z));
			}

			for (int i = 0; i < this.analysis.cubeX.length; i++) {
				double edge = this.analysis.cubeEdge[i];
				double y = this.analysis.cubeY[i];
				if (this.cut < y || this.cut >= y + edge || this.analysis.cubeZ[i] > high || this.analysis.cubeZ[i] + edge < low) {
					continue;
				}
				int x0 = screenX(this.analysis.cubeX[i]);
				int x1 = screenX(this.analysis.cubeX[i] + edge);
				int y0 = screenY(this.analysis.cubeZ[i] + edge);
				int y1 = screenY(this.analysis.cubeZ[i]);
				g.setColor(CUBE_FILL);
				g.fillRect(x0, y0, Math.max(1, x1 - x0), Math.max(1, y1 - y0));
				if (x1 - x0 >= 5 && y1 - y0 >= 5) {
					g.setColor(CUBE_GAP);
					g.drawRect(x0, y0, x1 - x0, y1 - y0);
				}
			}

			g.setColor(SURFACE_LINE);
			g.setStroke(new BasicStroke(2));
			int previousX = 0;
			int previousY = 0;
			int samples = Math.max(2, width / 2);
			for (int i = 0; i <= samples; i++) {
				double x = Math.min((double) i / samples * this.analysis.size, this.analysis.size);
				int screenPointX = screenX(x);
				int screenPointY = screenY(this.analysis.surfaceHeightAt(x, this.cut));
				if (i > 0) {
					g.drawLine(previousX, previousY, screenPointX, screenPointY);
				}
				previousX = screenPointX;
				previousY = screenPointY;
			}
			g.setStroke(new BasicStroke(1));
			g.setClip(null);

			g.setColor(AXIS_TEXT);
			g.drawRect(LEFT, TOP, width, height);
			for (double z = low; z <= high; z += step) {
				String label = meters(z);
				g.drawString(label, LEFT - g.getFontMetrics().stringWidth(label) - 4, screenY(z) + 4);
			}
			for (int part = 0; part <= 3; part++) {
				double x = part * this.analysis.size / 3.0;
				drawCentered(g, meters(x), screenX(x), TOP + height + 14);
			}
			g.drawString("West", LEFT, TOP + height + 30);
			drawCentered(g, high - low < this.analysis.size ? "rot: Soll-Oberfläche, Höhe überhöht" : "rot: Soll-Oberfläche",
					LEFT + width / 2, TOP + height + 44);
			String east = "Ost";
			g.drawString(east, LEFT + width - g.getFontMetrics().stringWidth(east), TOP + height + 30);
		}

		@Override
		public String getToolTipText(MouseEvent event) {
			if (this.analysis == null || event.getX() < LEFT || event.getX() > LEFT + plotWidth() || event.getY() < TOP
					|| event.getY() > TOP + plotHeight()) {
				return null;
			}
			double x = (double) (event.getX() - LEFT) / plotWidth() * this.analysis.size;
			double low = lowestShown();
			double z = low + (double) (TOP + plotHeight() - event.getY()) / plotHeight() * (highestShown() - low);
			int cube = this.analysis.findCube(x, this.cut, z);
			String where = String.format("%.2f m nach Ost, Höhe %.3f m", x, z);
			if (cube < 0) {
				return "<html>" + where + "<br>kein Würfel</html>";
			}
			return "<html>" + where + "<br>Adresse " + this.analysis.addresses.get(cube) + "<br>Kante "
					+ String.format("%.3f m", this.analysis.cubeEdge[cube]) + "</html>";
		}
	}
}
