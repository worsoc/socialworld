/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.tools.dct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.tools.dct.AttributeCharacterOverview.Entry;
import org.socialworld.tools.dct.AttributeCharacterOverview.StoredArray;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;

/**
 * Shows what is stored for the characters: attribute arrays, matrices and the targets of a matrix.
 * Nothing can be changed here.
 */
public class AttributeCharacterOverviewPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;

	private final JList<Entry> characterList;
	private final JLabel nameLabel = new JLabel();
	private final JLabel sketchLabel = new JLabel();
	private final JLabel messageLabel = new JLabel(" ");
	private final JComboBox<String> arrayChoice = new JComboBox<>();
	private final JComboBox<String> matrixChoice = new JComboBox<>();
	private final AttributeBars bars = new AttributeBars();
	private final MatrixRaster raster = new MatrixRaster();

	private boolean filling;

	public AttributeCharacterOverviewPanel(AttributeCharacterOverview overview) {
		super(new BorderLayout());

		List<Entry> entries = overview.getEntries();
		characterList = new JList<>(entries.toArray(new Entry[0]));
		characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		characterList.setCellRenderer(new CharacterRenderer());
		characterList.addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) characterChanged();
		});

		arrayChoice.addActionListener(event -> selectionChanged());
		matrixChoice.addActionListener(event -> selectionChanged());

		JScrollPane listScroll = new JScrollPane(characterList);
		listScroll.setPreferredSize(new Dimension(270, 700));
		add(listScroll, BorderLayout.WEST);
		add(new JScrollPane(createDetails()), BorderLayout.CENTER);

		if (!entries.isEmpty()) {
			characterList.setSelectedIndex(0);
		}
	}

	private JPanel createDetails() {
		nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));
		messageLabel.setForeground(new Color(170, 30, 30));

		JPanel choices = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
		choices.add(new JLabel("Array:"));
		choices.add(arrayChoice);
		choices.add(new JLabel("Matrix:"));
		choices.add(matrixChoice);

		JPanel details = new JPanel();
		details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
		details.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

		for (Component component : new Component[] {nameLabel, sketchLabel, choices, messageLabel, bars, Box.createVerticalStrut(10), raster}) {
			if (component instanceof JPanel) ((JPanel) component).setAlignmentX(LEFT_ALIGNMENT);
			if (component instanceof JLabel) ((JLabel) component).setAlignmentX(LEFT_ALIGNMENT);
			details.add(component);
		}
		choices.setMaximumSize(new Dimension(Integer.MAX_VALUE, choices.getPreferredSize().height));
		return details;
	}

	/** Selects a character by its place in the list. */
	void selectCharacter(int index) {
		characterList.setSelectedIndex(index);
	}

	private void characterChanged() {
		Entry entry = characterList.getSelectedValue();
		if (entry == null) return;

		nameLabel.setText(entry.getCharacter().getNumber() + " " + entry.getCharacter().getName());
		sketchLabel.setText(entry.getCharacter().getSketch());

		filling = true;
		DefaultComboBoxModel<String> arrays = new DefaultComboBoxModel<>();
		for (StoredArray array : entry.getArrays()) {
			arrays.addElement("Index " + String.format("%+d", array.getGaussIndex()));
		}
		arrayChoice.setModel(arrays);

		DefaultComboBoxModel<String> matrices = new DefaultComboBoxModel<>();
		int count = entry.getMatrices().size();
		for (int i = 0; i < count; i++) {
			matrices.addElement("Variante " + (i + 1) + " von " + count + " (func_id " + entry.getMatrices().get(i).getFuncID() + ")");
		}
		matrixChoice.setModel(matrices);
		filling = false;

		selectionChanged();
	}

	private void selectionChanged() {
		Entry entry = characterList.getSelectedValue();
		if (filling || entry == null || arrayChoice.getSelectedIndex() < 0 || matrixChoice.getSelectedIndex() < 0) return;

		StoredMatrix matrix = entry.getMatrices().get(matrixChoice.getSelectedIndex());
		int[] targets = null;
		messageLabel.setText(" ");
		try {
			targets = AttributeMatrixTargets.compute(matrix, entry.getCharacter());
		} catch (IllegalStateException e) {
			messageLabel.setText("Keine Zielwerte: " + e.getMessage());
		}

		bars.show(entry.getCharacter(), entry.getArrays().get(arrayChoice.getSelectedIndex()).getValues(), targets);
		raster.show(matrix);
	}

	/**
	 * Opens the overview in a window of its own.
	 */
	public static void open(AttributeCharacterOverview overview) {
		JFrame frame = new JFrame("Übersicht Charaktere");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(new AttributeCharacterOverviewPanel(overview));
		frame.setSize(1120, 860);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	private static String attributeName(int index) {
		return Attribute.getAttributeName(index).toString();
	}

	private static final class CharacterRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focused) {
			Entry entry = (Entry) value;
			String text = entry.getCharacter().getNumber() + "  " + entry.getCharacter().getName()
					+ "  (" + entry.getArrays().size() + "/" + entry.getMatrices().size() + ")";
			return super.getListCellRendererComponent(list, text, index, selected, focused);
		}
	}

	/** One bar per attribute: the band of the character, the start value and the target of the matrix. */
	private static final class AttributeBars extends JPanel {

		private static final long serialVersionUID = 1L;

		private static final int LABEL_WIDTH = 90;
		private static final int BAR_WIDTH = 440;
		private static final int ROW_HEIGHT = 24;
		private static final int LEGEND_HEIGHT = 24;

		private static final Color BAR_BACKGROUND = new Color(232, 232, 232);
		private static final Color BAND = new Color(170, 205, 240);
		private static final Color START = new Color(25, 70, 140);
		private static final Color TARGET = new Color(225, 110, 0);

		private AttributeCharacter character;
		private int[] values;
		private int[] targets;

		AttributeBars() {
			Dimension size = new Dimension(LABEL_WIDTH + BAR_WIDTH + 260, LEGEND_HEIGHT + N * ROW_HEIGHT);
			setPreferredSize(size);
			setMaximumSize(size);
		}

		void show(AttributeCharacter character, int[] values, int[] targets) {
			this.character = character;
			this.values = values;
			this.targets = targets;
			repaint();
		}

		private static int position(int value) {
			return LABEL_WIDTH + value * BAR_WIDTH / AttributeArray.ATTRIBUTE_VALUE_MAX;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (character == null) return;
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			paintLegend(graphics);
			for (int i = 0; i < N; i++) {
				paintRow(graphics, i, LEGEND_HEIGHT + i * ROW_HEIGHT);
			}
		}

		private void paintLegend(Graphics2D g) {
			int x = LABEL_WIDTH;
			g.setColor(BAND);
			g.fillRect(x, 6, 18, 10);
			g.setColor(Color.BLACK);
			g.drawString("Band des Charakters", x + 24, 16);
			x += 170;
			g.setColor(START);
			g.fillRect(x, 8, 18, 6);
			g.setColor(Color.BLACK);
			g.drawString("Startwert (Array)", x + 24, 16);
			x += 140;
			paintTargetMark(g, x + 6, 11);
			g.setColor(Color.BLACK);
			g.drawString("Zielwert (Matrix)", x + 18, 16);
		}

		private void paintTargetMark(Graphics2D g, int x, int centerY) {
			int[] xPoints = {x, x + 6, x, x - 6};
			int[] yPoints = {centerY - 7, centerY, centerY + 7, centerY};
			g.setColor(TARGET);
			g.fillPolygon(xPoints, yPoints, 4);
		}

		private void paintRow(Graphics2D g, int attribute, int y) {
			int barY = y + 5;
			int barHeight = ROW_HEIGHT - 10;
			int center = barY + barHeight / 2;
			int textY = y + 17;

			g.setColor(Color.BLACK);
			g.drawString(attributeName(attribute), 0, textY);

			g.setColor(BAR_BACKGROUND);
			g.fillRect(LABEL_WIDTH, barY, BAR_WIDTH, barHeight);

			AttributeLevel level = character.getLevel(attribute);
			g.setColor(BAND);
			g.fillRect(position(level.getLowest()), barY, position(level.getHighest()) - position(level.getLowest()), barHeight);

			g.setColor(START);
			g.fillRect(LABEL_WIDTH, center - 2, position(values[attribute]) - LABEL_WIDTH, 5);

			String target = "–";
			if (targets != null && targets[attribute] != AttributeMatrixTargets.NO_TARGET) {
				paintTargetMark(g, position(targets[attribute]), center);
				target = String.valueOf(AttributeArray.toDisplayValue(targets[attribute]));
			}

			g.setColor(Color.BLACK);
			g.drawString("Start " + AttributeArray.toDisplayValue(values[attribute]) + "   Ziel " + target
					+ "   Band " + AttributeArray.toDisplayValue(level.getLowest()) + "–" + AttributeArray.toDisplayValue(level.getHighest()),
					LABEL_WIDTH + BAR_WIDTH + 12, textY);
		}
	}

	/** The cells of a matrix, one to one: row = source, column = target. */
	private static final class MatrixRaster extends JPanel {

		private static final long serialVersionUID = 1L;

		private static final int LABEL_WIDTH = 90;
		private static final int CELL_WIDTH = 70;
		private static final int CELL_HEIGHT = 36;
		private static final int TITLE_HEIGHT = 22;
		private static final int HEADER_HEIGHT = 20;

		/** Displayed points per step for the full colour: fixed, so that matrices can be compared; about the strongest stored coupling. */
		private static final float FULL_COLOUR_STRENGTH = 0.8f;

		private static final Color DIAGONAL = new Color(212, 216, 238);
		private static final Color CONSTANT = new Color(214, 214, 214);
		private static final Color LOWERING = new Color(232, 100, 90);
		private static final Color RAISING = new Color(100, 190, 115);
		private static final Color GRID = new Color(190, 190, 190);

		private StoredMatrix matrix;

		MatrixRaster() {
			Dimension size = new Dimension(LABEL_WIDTH + N * CELL_WIDTH + 1, TITLE_HEIGHT + HEADER_HEIGHT + N * CELL_HEIGHT + 1);
			setPreferredSize(size);
			setMaximumSize(size);
			ToolTipManager.sharedInstance().registerComponent(this);
		}

		void show(StoredMatrix matrix) {
			this.matrix = matrix;
			repaint();
		}

		@Override
		public String getToolTipText(MouseEvent event) {
			if (matrix == null) return null;
			int column = (event.getX() - LABEL_WIDTH) / CELL_WIDTH;
			int row = (event.getY() - TITLE_HEIGHT - HEADER_HEIGHT) / CELL_HEIGHT;
			if (event.getX() < LABEL_WIDTH || event.getY() < TITLE_HEIGHT + HEADER_HEIGHT || column >= N || row >= N) return null;
			return AttributeMatrixCell.of(matrix, row, column).getToolTip();
		}

		private static Color colourOf(AttributeMatrixCell cell) {
			switch (cell.getKind()) {
			case DIAGONAL:
				return DIAGONAL;
			case CONSTANT:
				return CONSTANT;
			case COUPLING:
				float share = (float) Math.sqrt(Math.min(1f, Math.abs(cell.getStrength()) / FULL_COLOUR_STRENGTH));
				return mixedWithWhite(cell.getStrength() < 0 ? LOWERING : RAISING, share);
			default:
				return Color.WHITE;
			}
		}

		private static Color mixedWithWhite(Color colour, float share) {
			return new Color(mixed(colour.getRed(), share), mixed(colour.getGreen(), share), mixed(colour.getBlue(), share));
		}

		private static int mixed(int channel, float share) {
			return Math.round(255 + (channel - 255) * share);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (matrix == null) return;
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			graphics.setColor(Color.BLACK);
			graphics.drawString("Matrix: Zeile = Quelle, Spalte = Ziel. Rot senkt, grün hebt, Punkte je Schritt bei vollem Funktionswert."
					+ " Grau = Konstante.", 0, 15);

			Font normal = graphics.getFont();
			Font small = normal.deriveFont(10f);

			graphics.setFont(small);
			for (int i = 0; i < N; i++) {
				centered(graphics, attributeName(i), LABEL_WIDTH + i * CELL_WIDTH, TITLE_HEIGHT + 14);
			}
			graphics.setFont(normal);

			for (int row = 0; row < N; row++) {
				int y = TITLE_HEIGHT + HEADER_HEIGHT + row * CELL_HEIGHT;
				graphics.setColor(Color.BLACK);
				graphics.drawString(attributeName(row), 0, y + 22);
				for (int column = 0; column < N; column++) {
					paintCell(graphics, AttributeMatrixCell.of(matrix, row, column), LABEL_WIDTH + column * CELL_WIDTH, y, normal, small);
				}
			}
		}

		private void paintCell(Graphics2D g, AttributeMatrixCell cell, int x, int y, Font normal, Font small) {
			g.setColor(colourOf(cell));
			g.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
			g.setColor(GRID);
			g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);

			g.setColor(Color.BLACK);
			g.setFont(normal);
			centered(g, cell.getValueText(), x, y + 16);
			g.setFont(small);
			centered(g, cell.getFunctionText(), x, y + 30);
			g.setFont(normal);
		}

		private static void centered(Graphics2D g, String text, int x, int baseline) {
			FontMetrics metrics = g.getFontMetrics();
			g.drawString(text, x + (CELL_WIDTH - metrics.stringWidth(text)) / 2, baseline);
		}
	}
}
