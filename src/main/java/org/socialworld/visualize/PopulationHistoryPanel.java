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
package org.socialworld.visualize;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * The last five minutes of all objects together: on the left the actions as a stacked area,
 * on the right one small chart per attribute with mean and spread.
 * The attribute charts all use the full scale, so that a drift or a saturation can be compared between them.
 */
class PopulationHistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int MARGIN = 6;
	private static final int TITLE_HEIGHT = 15;
	private static final int TIME_LABEL_HEIGHT = 13;
	private static final int AXIS_WIDTH = 26;
	private static final int CHART_GAP = 8;
	private static final int ACTIONS_PERCENT_OF_WIDTH = 35;
	private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
	private static final Color COLOR_CHART = SimColorConstants.COLOR_WHITESMOKE;
	private static final Color COLOR_MIDDLE_LINE = SimColorConstants.COLOR_GAINSBORO;
	private static final Color COLOR_SPREAD = new Color(112, 128, 144, 110);

	private List<PopulationHistory.Sample> samples = new ArrayList<PopulationHistory.Sample>();
	private long nowMillis;

	PopulationHistoryPanel() {
		setBackground(Color.WHITE);
	}

	void setState(List<PopulationHistory.Sample> samples, long nowMillis) {
		this.samples = samples;
		this.nowMillis = nowMillis;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int actionsWidth = getWidth() * ACTIONS_PERCENT_OF_WIDTH / 100;
		paintActions(g, MARGIN, actionsWidth - MARGIN);
		paintAttributes(g, actionsWidth + 2 * CHART_GAP, getWidth() - actionsWidth - 2 * CHART_GAP - MARGIN);
	}

	private void paintActions(Graphics2D g, int x, int width) {
		g.setColor(Color.BLACK);
		g.setFont(FONT_TITLE);
		g.drawString("T\u00E4tigkeiten, Anzahl Menschen", x, TITLE_HEIGHT);

		int chartX = x + AXIS_WIDTH;
		int chartWidth = width - AXIS_WIDTH;
		int top = TITLE_HEIGHT + 4;
		int height = getHeight() - top - TIME_LABEL_HEIGHT - 2;
		if (chartWidth <= 0 || height <= 0) return;

		g.setColor(COLOR_CHART);
		g.fillRect(chartX, top, chartWidth, height);

		paintActionAreas(g, chartX, top, chartWidth, height);
		paintActionAxis(g, x, chartX, top, chartWidth, height);
	}

	private void paintActionAreas(Graphics2D g, int chartX, int top, int chartWidth, int height) {
		if (samples.size() < 2) return;

		int count = samples.size();
		int[][] stacks = new int[count][];
		int[] xs = new int[count];
		for (int i = 0; i < count; i++) {
			stacks[i] = stackTops(samples.get(i).familyCounts);
			xs[i] = xAt(samples.get(i).millis, nowMillis, chartX, chartWidth);
		}

		for (ActionFamily family : ActionFamily.values()) {
			int f = family.ordinal();
			int[] polygonX = new int[2 * count];
			int[] polygonY = new int[2 * count];
			for (int i = 0; i < count; i++) {
				polygonX[i] = xs[i];
				polygonY[i] = yAt(stacks[i][f], VisualModel.OBJECT_COUNT, top, height);
				int back = 2 * count - 1 - i;
				polygonX[back] = xs[i];
				polygonY[back] = yAt(f == 0 ? 0 : stacks[i][f - 1], VisualModel.OBJECT_COUNT, top, height);
			}
			g.setColor(family.getColor());
			g.fillPolygon(polygonX, polygonY, polygonX.length);
		}
	}

	private void paintActionAxis(Graphics2D g, int x, int chartX, int top, int chartWidth, int height) {
		g.setColor(Color.BLACK);
		g.setFont(FONT_LABEL);
		FontMetrics metrics = g.getFontMetrics();
		for (int value = 0; value <= VisualModel.OBJECT_COUNT; value += VisualModel.OBJECT_COUNT / 2) {
			String text = Integer.toString(value);
			int y = yAt(value, VisualModel.OBJECT_COUNT, top, height);
			g.drawString(text, chartX - metrics.stringWidth(text) - 3, Math.max(top + metrics.getAscent() - 2, Math.min(top + height, y + metrics.getAscent() / 2)));
		}

		int labelY = top + height + TIME_LABEL_HEIGHT - 1;
		g.drawString("vor 5 min", chartX, labelY);
		String now = "jetzt";
		g.drawString(now, chartX + chartWidth - metrics.stringWidth(now), labelY);
	}

	private void paintAttributes(Graphics2D g, int x, int width) {
		g.setColor(Color.BLACK);
		g.setFont(FONT_TITLE);
		g.drawString("Attribute aller Menschen, Linie: Mittelwert, Fl\u00E4che: mittlere 80 %, Achse 0 bis 99, gleiche Zeit wie links", x, TITLE_HEIGHT);

		List<PopulationHistory.Sample> known = samplesWithAttributes(samples);
		PopulationHistory.Sample latest = known.isEmpty() ? null : known.get(known.size() - 1);

		int columns = Attribute.NUMBER_OF_ATTRIBUTES;
		int top = TITLE_HEIGHT + 4;
		for (int attribute = 0; attribute < columns; attribute++) {
			int chartX = x + attribute * width / columns;
			int chartWidth = x + (attribute + 1) * width / columns - chartX - CHART_GAP;
			paintAttributeChart(g, known, latest, attribute, chartX, top, chartWidth);
		}
	}

	private void paintAttributeChart(Graphics2D g, List<PopulationHistory.Sample> known, PopulationHistory.Sample latest,
			int attribute, int x, int top, int width) {
		g.setColor(Color.BLACK);
		g.setFont(FONT_LABEL);
		String name = Attribute.getAttributeName(attribute).toString();
		g.drawString(latest == null ? name : name + " " + AttributeArray.toDisplayValue(latest.means[attribute]), x, top + g.getFontMetrics().getAscent() - 1);

		int chartTop = top + TITLE_HEIGHT;
		int height = getHeight() - chartTop - MARGIN;
		if (width <= 0 || height <= 0) return;

		g.setColor(COLOR_CHART);
		g.fillRect(x, chartTop, width, height);
		g.setColor(COLOR_MIDDLE_LINE);
		int middle = yAt(AttributeArray.ATTRIBUTE_VALUE_MIDDLE, AttributeArray.ATTRIBUTE_VALUE_MAX, chartTop, height);
		g.drawLine(x, middle, x + width - 1, middle);

		if (known.size() >= 2) {
			paintSpreadAndMean(g, known, attribute, x, chartTop, width, height);
		}
	}

	private void paintSpreadAndMean(Graphics2D g, List<PopulationHistory.Sample> known, int attribute, int x, int top, int width, int height) {
		int count = known.size();
		int max = AttributeArray.ATTRIBUTE_VALUE_MAX;
		int[] xs = new int[count];
		int[] meanY = new int[count];
		int[] polygonX = new int[2 * count];
		int[] polygonY = new int[2 * count];
		for (int i = 0; i < count; i++) {
			PopulationHistory.Sample sample = known.get(i);
			xs[i] = xAt(sample.millis, nowMillis, x, width);
			meanY[i] = yAt(sample.means[attribute], max, top, height);
			polygonX[i] = xs[i];
			polygonY[i] = yAt(sample.highs[attribute], max, top, height);
			int back = 2 * count - 1 - i;
			polygonX[back] = xs[i];
			polygonY[back] = yAt(sample.lows[attribute], max, top, height);
		}

		g.setColor(COLOR_SPREAD);
		g.fillPolygon(polygonX, polygonY, polygonX.length);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.2f));
		g.drawPolyline(xs, meanY, count);
	}

	/**
	 * @return for each family the sum of its count and the counts of the families below it
	 */
	static int[] stackTops(int[] familyCounts) {
		int[] tops = new int[familyCounts.length];
		int sum = 0;
		for (int i = 0; i < familyCounts.length; i++) {
			sum += familyCounts[i];
			tops[i] = sum;
		}
		return tops;
	}

	/**
	 * @return the pixel column of the moment: the right edge is now, the left edge five minutes ago
	 */
	static int xAt(long millis, long nowMillis, int chartX, int chartWidth) {
		long age = Math.max(0, Math.min(PopulationHistory.LENGTH_MILLIS, nowMillis - millis));
		return chartX + chartWidth - 1 - (int) (age * (chartWidth - 1) / PopulationHistory.LENGTH_MILLIS);
	}

	/**
	 * @return the pixel row of the value: the top is max, the bottom is 0
	 */
	static int yAt(int value, int max, int top, int height) {
		return top + height - 1 - value * (height - 1) / max;
	}

	/**
	 * Before the start values are read no object has attributes, such samples have no spread.
	 */
	static List<PopulationHistory.Sample> samplesWithAttributes(List<PopulationHistory.Sample> samples) {
		List<PopulationHistory.Sample> known = new ArrayList<PopulationHistory.Sample>();
		for (PopulationHistory.Sample sample : samples) {
			if (sample.objectsWithAttributes > 0) known.add(sample);
		}
		return known;
	}
}
