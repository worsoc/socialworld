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
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

/**
 * Shows an adapter family: the nine tiles as they lie next to each other, with
 * one color scale, and a cut along the whole row.
 */
final class CubeFamilyDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final FamilyPanel panel = new FamilyPanel();

	CubeFamilyDialog() {
		super((java.awt.Frame) null, "Adapter-Familie", false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(this.panel);
		setSize(1100, 800);
	}

	void setFamily(CubeFamilyAnalysis family) {
		setTitle("Adapter-Familie von Kachel " + family.selectedTile);
		this.panel.setFamily(family);
	}

	static final class FamilyPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private final RowView rowView = new RowView();
		private final CutView cutView = new CutView();
		private final JSlider cutSlider = new JSlider(0, 1000, 500);
		private final JLabel cutLabel = new JLabel(" ");
		private final JTextArea summary = new JTextArea(4, 60);

		private CubeFamilyAnalysis family;

		FamilyPanel() {
			super(new BorderLayout(10, 10));
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JPanel rowPanel = new JPanel(new BorderLayout());
			rowPanel.setBorder(BorderFactory.createTitledBorder("Draufsicht: die 9 Kacheln nebeneinander"));
			rowPanel.add(this.rowView, BorderLayout.CENTER);

			JPanel cutPanel = new JPanel(new BorderLayout(0, 4));
			cutPanel.setBorder(BorderFactory.createTitledBorder("Längsschnitt durch alle 9 Kacheln"));
			cutPanel.add(this.cutView, BorderLayout.CENTER);
			JPanel sliderPanel = new JPanel(new BorderLayout(6, 0));
			sliderPanel.add(this.cutLabel, BorderLayout.WEST);
			sliderPanel.add(this.cutSlider, BorderLayout.CENTER);
			cutPanel.add(sliderPanel, BorderLayout.SOUTH);

			JPanel views = new JPanel(new BorderLayout(0, 10));
			views.add(rowPanel, BorderLayout.NORTH);
			views.add(cutPanel, BorderLayout.CENTER);
			add(views, BorderLayout.CENTER);

			this.summary.setEditable(false);
			this.summary.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			add(new JScrollPane(this.summary), BorderLayout.SOUTH);

			this.cutSlider.addChangeListener(event -> cutChanged());
		}

		void setFamily(CubeFamilyAnalysis family) {
			this.family = family;
			this.rowView.family = family;
			this.cutView.family = family;
			this.summary.setText(buildSummary(family));
			this.summary.setCaretPosition(0);
			cutChanged();
		}

		private void cutChanged() {
			if (this.family == null) {
				return;
			}
			double cut = Math.min(this.cutSlider.getValue() / 1000.0 * this.family.size, this.family.size - 1e-6);
			this.cutLabel.setText(String.format("Schnitt bei %.2f m nach %s", cut, this.family.isWestToEast ? "Nord" : "Ost"));
			this.rowView.cut = cut;
			this.cutView.cut = cut;
			repaint();
		}

		private static String buildSummary(CubeFamilyAnalysis family) {
			NumberFormat count = NumberFormat.getIntegerInstance();
			StringBuilder text = new StringBuilder();
			text.append(String.format("Größe %d m, Tiefe %d: zusammen %s Adressen, ca. %.0f MB%n", family.size, family.depth,
					count.format(family.getTotalAddresses()), family.getTotalMemoryBytes() / 1e6));
			text.append(family.isWestToEast ? "Von West nach Ost: " : "Von Süd nach Nord (Bild gedreht, Norden rechts): ");
			for (int position = 0; position < family.tilesByPosition.length; position++) {
				text.append(position == 0 ? "" : ", ").append(family.tilesByPosition[position].tile);
			}
			text.append('\n');
			text.append("Adressen je Kachel: ");
			for (int position = 0; position < family.tilesByPosition.length; position++) {
				text.append(position == 0 ? "" : ", ").append(count.format(family.tilesByPosition[position].addresses.size()));
			}
			text.append('\n');
			text.append("Gewählte Kachel ").append(family.selectedTile).append(": ")
					.append(CubeTileAnalysis.describeTile(family.selectedTile));
			return text.toString();
		}
	}

	private abstract static class FamilyView extends JPanel {
		private static final long serialVersionUID = 1L;

		CubeFamilyAnalysis family;
		double cut;

		FamilyView() {
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(800, 280));
			setToolTipText("");
		}

		protected static void drawCentered(Graphics2D g, String text, int centerX, int y) {
			g.drawString(text, centerX - g.getFontMetrics().stringWidth(text) / 2, y);
		}

		@Override
		protected void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			if (this.family != null) {
				paintFamily((Graphics2D) graphics);
			}
		}

		protected abstract void paintFamily(Graphics2D g);
	}

	/**
	 * Always nine tiles from left to right, above the same tiles of the cut. A row
	 * from south to north is turned for this: north is on the right, east at the bottom.
	 */
	private static final class RowView extends FamilyView {
		private static final long serialVersionUID = 1L;
		private static final int SIDE = 48;
		private static final int TOP = 24;
		private static final int BOTTOM = 42;

		RowView() {
			setPreferredSize(new Dimension(800, 190));
		}

		private int tilePixels() {
			int width = getWidth() - 2 * SIDE;
			int height = getHeight() - TOP - BOTTOM;
			return Math.max(4, Math.min(width / CubeFamilyAnalysis.TILES_IN_FAMILY, height));
		}

		private int left() {
			return (getWidth() - CubeFamilyAnalysis.TILES_IN_FAMILY * tilePixels()) / 2;
		}

		@Override
		protected void paintFamily(Graphics2D g) {
			int pixels = tilePixels();
			int left = left();
			int tiles = CubeFamilyAnalysis.TILES_IN_FAMILY;
			int cells = CubeTileAnalysis.TOP_VIEW_CELLS;
			double range = this.family.highestTop - this.family.lowestTop;

			for (int position = 0; position < tiles; position++) {
				CubeTileAnalysis tile = this.family.tilesByPosition[position];
				int tileLeft = left + position * pixels;
				for (int px = 0; px < pixels; px++) {
					for (int py = 0; py < pixels; py++) {
						int along = px * cells / pixels;
						int column = this.family.isWestToEast ? along : py * cells / pixels;
						int row = this.family.isWestToEast ? (pixels - 1 - py) * cells / pixels : along;
						double value = tile.topHeights[column][row];
						g.setColor(CubeTileDialog.heightColor(range > 0 ? (value - this.family.lowestTop) / range : 0));
						g.fillRect(tileLeft + px, TOP + py, 1, 1);
					}
				}
			}

			for (int position = 0; position < tiles; position++) {
				CubeTileAnalysis tile = this.family.tilesByPosition[position];
				int tileLeft = left + position * pixels;
				boolean selected = tile.tile == this.family.selectedTile;
				g.setColor(selected ? Color.BLACK : CubeTileDialog.AXIS_TEXT);
				g.setStroke(new BasicStroke(selected ? 2 : 1));
				g.drawRect(tileLeft, TOP, pixels, pixels);
				g.setStroke(new BasicStroke(1));
				g.setFont(getFont().deriveFont(selected ? Font.BOLD : Font.PLAIN));
				drawCentered(g, String.valueOf(tile.tile), tileLeft + pixels / 2, TOP - 5);
				g.setFont(getFont());
			}

			double cutFraction = this.cut / this.family.size;
			int cutY = this.family.isWestToEast ? TOP + pixels - (int) (cutFraction * pixels) : TOP + (int) (cutFraction * pixels);
			g.setColor(CubeTileDialog.SURFACE_LINE);
			g.setStroke(new BasicStroke(2));
			g.drawLine(left, cutY, left + tiles * pixels, cutY);
			g.setStroke(new BasicStroke(1));

			boolean westToEast = this.family.isWestToEast;
			g.setColor(CubeTileDialog.AXIS_TEXT);
			drawRightAligned(g, westToEast ? "West" : "Süd", left - 4, TOP + pixels / 2 + 4);
			g.drawString(westToEast ? "Ost" : "Nord", left + tiles * pixels + 4, TOP + pixels / 2 + 4);
			drawRightAligned(g, westToEast ? "Nord" : "West", left - 4, TOP + 12);
			drawRightAligned(g, westToEast ? "Süd" : "Ost", left - 4, TOP + pixels);

			int legendY = TOP + pixels + 8;
			int legendWidth = 200;
			int legendX = (getWidth() - legendWidth) / 2;
			for (int i = 0; i < legendWidth; i++) {
				g.setColor(CubeTileDialog.heightColor((double) i / legendWidth));
				g.fillRect(legendX + i, legendY, 1, 8);
			}
			g.setColor(CubeTileDialog.AXIS_TEXT);
			g.drawString(String.format("%.2f m", this.family.lowestTop), legendX, legendY + 21);
			String high = String.format("%.2f m", this.family.highestTop);
			g.drawString(high, legendX + legendWidth - g.getFontMetrics().stringWidth(high), legendY + 21);
		}

		private static void drawRightAligned(Graphics2D g, String text, int right, int y) {
			g.drawString(text, right - g.getFontMetrics().stringWidth(text), y);
		}

		@Override
		public String getToolTipText(MouseEvent event) {
			if (this.family == null) {
				return null;
			}
			int pixels = tilePixels();
			int insideRow = event.getX() - left();
			int insideY = event.getY() - TOP;
			if (insideRow < 0 || insideRow >= CubeFamilyAnalysis.TILES_IN_FAMILY * pixels || insideY < 0 || insideY >= pixels) {
				return null;
			}
			int position = insideRow / pixels;
			int insideX = insideRow - position * pixels;
			CubeTileAnalysis tile = this.family.tilesByPosition[position];

			double along = (double) insideX / pixels * this.family.size;
			double x = this.family.isWestToEast ? along : (double) insideY / pixels * this.family.size;
			double y = this.family.isWestToEast ? (double) (pixels - 1 - insideY) / pixels * this.family.size : along;
			return CubeTileDialog.columnToolTip(tile, x, y).replaceFirst("<html>", "<html><b>Kachel " + tile.tile + "</b><br>");
		}
	}

	private static final class CutView extends FamilyView {
		private static final long serialVersionUID = 1L;
		private static final int LEFT = 48;
		private static final int RIGHT = 14;
		private static final int TOP = 12;
		private static final int BOTTOM = 52;

		private double totalLength() {
			return this.family.size * (double) CubeFamilyAnalysis.TILES_IN_FAMILY;
		}

		private double lowestShown() {
			return Math.max(0, Math.floor(this.family.lowestSurface) - 1);
		}

		private double highestShown() {
			return Math.min(this.family.size, Math.ceil(this.family.highestSurface) + 1);
		}

		private int plotWidth() {
			return Math.max(10, getWidth() - LEFT - RIGHT);
		}

		private int plotHeight() {
			return Math.max(10, getHeight() - TOP - BOTTOM);
		}

		private int screenX(double along) {
			return LEFT + (int) Math.round(along / totalLength() * plotWidth());
		}

		private int screenY(double z) {
			double low = lowestShown();
			return TOP + plotHeight() - (int) Math.round((z - low) / (highestShown() - low) * plotHeight());
		}

		@Override
		protected void paintFamily(Graphics2D g) {
			double low = lowestShown();
			double high = highestShown();
			int width = plotWidth();
			int height = plotHeight();
			boolean westToEast = this.family.isWestToEast;

			g.setClip(LEFT, TOP, width + 1, height + 1);
			g.setColor(CubeTileDialog.GRID_LINE);
			int step = (int) Math.max(1, Math.ceil((high - low) / 8));
			for (double z = low; z <= high; z += step) {
				g.drawLine(LEFT, screenY(z), LEFT + width, screenY(z));
			}

			for (int position = 0; position < CubeFamilyAnalysis.TILES_IN_FAMILY; position++) {
				CubeTileAnalysis tile = this.family.tilesByPosition[position];
				double offset = position * (double) this.family.size;
				double[] along = westToEast ? tile.cubeX : tile.cubeY;
				double[] across = westToEast ? tile.cubeY : tile.cubeX;

				g.setColor(CubeTileDialog.CUBE_FILL);
				for (int i = 0; i < along.length; i++) {
					double edge = tile.cubeEdge[i];
					if (this.cut < across[i] || this.cut >= across[i] + edge || tile.cubeZ[i] > high || tile.cubeZ[i] + edge < low) {
						continue;
					}
					int x0 = screenX(offset + along[i]);
					int x1 = screenX(offset + along[i] + edge);
					int y0 = screenY(tile.cubeZ[i] + edge);
					int y1 = screenY(tile.cubeZ[i]);
					g.fillRect(x0, y0, Math.max(1, x1 - x0), Math.max(1, y1 - y0));
				}
			}

			g.setColor(CubeTileDialog.SURFACE_LINE);
			g.setStroke(new BasicStroke(2));
			int previousX = 0;
			int previousY = 0;
			int samplesPerTile = Math.max(2, width / 9 / 3);
			boolean first = true;
			for (int position = 0; position < CubeFamilyAnalysis.TILES_IN_FAMILY; position++) {
				CubeTileAnalysis tile = this.family.tilesByPosition[position];
				for (int sample = 0; sample <= samplesPerTile; sample++) {
					double inside = (double) sample / samplesPerTile * this.family.size;
					double surface = westToEast ? tile.surfaceHeightAt(inside, this.cut) : tile.surfaceHeightAt(this.cut, inside);
					int x = screenX(position * (double) this.family.size + inside);
					int y = screenY(surface);
					if (!first) {
						g.drawLine(previousX, previousY, x, y);
					}
					previousX = x;
					previousY = y;
					first = false;
				}
			}
			g.setStroke(new BasicStroke(1));
			g.setClip(null);

			g.setColor(CubeTileDialog.AXIS_TEXT);
			g.drawRect(LEFT, TOP, width, height);
			for (double z = low; z <= high; z += step) {
				String label = String.format("%.0f m", z);
				g.drawString(label, LEFT - g.getFontMetrics().stringWidth(label) - 4, screenY(z) + 4);
			}
			for (int position = 0; position < CubeFamilyAnalysis.TILES_IN_FAMILY; position++) {
				int boundary = screenX(position * (double) this.family.size);
				if (position > 0) {
					g.drawLine(boundary, TOP + height, boundary, TOP + height + 4);
				}
				CubeTileAnalysis tile = this.family.tilesByPosition[position];
				g.setFont(getFont().deriveFont(tile.tile == this.family.selectedTile ? Font.BOLD : Font.PLAIN));
				drawCentered(g, String.valueOf(tile.tile), screenX((position + 0.5) * this.family.size), TOP + height + 16);
				g.setFont(getFont());
			}
			g.drawString(westToEast ? "West" : "Süd", LEFT, TOP + height + 32);
			String end = westToEast ? "Ost" : "Nord";
			g.drawString(end, LEFT + width - g.getFontMetrics().stringWidth(end), TOP + height + 32);
			drawCentered(g, "rot: Soll-Oberfläche, Höhe überhöht", LEFT + width / 2, TOP + height + 46);
		}

		@Override
		public String getToolTipText(MouseEvent event) {
			if (this.family == null || event.getX() < LEFT || event.getX() >= LEFT + plotWidth() || event.getY() < TOP
					|| event.getY() > TOP + plotHeight()) {
				return null;
			}
			double along = (double) (event.getX() - LEFT) / plotWidth() * totalLength();
			int position = Math.min(CubeFamilyAnalysis.TILES_IN_FAMILY - 1, (int) (along / this.family.size));
			double inside = along - position * (double) this.family.size;
			double low = lowestShown();
			double z = low + (double) (TOP + plotHeight() - event.getY()) / plotHeight() * (highestShown() - low);

			CubeTileAnalysis tile = this.family.tilesByPosition[position];
			int cube = this.family.isWestToEast ? tile.findCube(inside, this.cut, z) : tile.findCube(this.cut, inside, z);
			String where = String.format("<html><b>Kachel %d</b><br>Höhe %.3f m", tile.tile, z);
			if (cube < 0) {
				return where + "<br>kein Würfel</html>";
			}
			return where + "<br>Adresse " + tile.addresses.get(cube) + String.format("<br>Kante %.3f m</html>", tile.cubeEdge[cube]);
		}
	}
}
