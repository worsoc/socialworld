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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.Value;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.propertyChange.ListenedMap;

/**
 * The display of the running simulation.
 * The threads of the simulation only write into the model.
 * Swing is touched only by the timer, on the event dispatch thread.
 */
public class SimDashboard implements ISimVisualization, IObjectReceiver {

	private static final int REFRESH_MILLIS = 250;
	private static final int MATRIX_WIDTH = 380;
	private static final int DETAIL_WIDTH = 320;
	private static final int POPULATION_HEIGHT = 165;
	private static final DateTimeFormatter CLOCK = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static AccessTokenVisualize token = AccessTokenVisualize.getValid();

	private final VisualModel model = new VisualModel();

	// only touched by the thread of the action master, the only one that reports actions
	private final AbstractAction[] lastActions = new AbstractAction[VisualModel.OBJECT_COUNT];

	// only touched on the event dispatch thread
	private final ActionGridPanel grid = new ActionGridPanel();
	private final AttributeMatrixPanel matrix = new AttributeMatrixPanel();
	private final ObjectDetailPanel detail = new ObjectDetailPanel();
	private final AttributeHistory history = new AttributeHistory();
	private final PopulationHistoryPanel populationPanel = new PopulationHistoryPanel();
	private final PopulationHistory population = new PopulationHistory();
	private final AutomaticSelection automatic = new AutomaticSelection();
	private final JToggleButton automaticButton = new JToggleButton("Automatik");
	private int selectedObject = VisualModel.NO_OBJECT;
	private boolean pictureMode;
	private int pictureTick;
	private final PerSecondRate newActionsRate = new PerSecondRate();
	private final PerSecondRate attributeChangesRate = new PerSecondRate();
	private JLabel labelHeader;

	@Override
	public void show() {
		SwingUtilities.invokeLater(this::createAndShowFrame);
	}

	@Override
	public void startAction(int objID, AbstractAction action) {
		if (!VisualModel.isShown(objID)) return;

		lastActions[objID - 1] = action;
		model.startAction(objID, action, action.getType(), action.getMode(), action.getIntensity(),
				action.getDuration(), action.getRemainedDuration(), action.getPriority());
	}

	@Override
	public void stopAction(int objID) {
		if (!VisualModel.isShown(objID) || lastActions[objID - 1] == null) return;

		model.stopAction(objID, lastActions[objID - 1].isDone(), ActualTime.inMilliseconds());
	}

	@Override
	public void setAttributes(int objID, AttributeArray attributes, boolean withChange) {
		model.setAttributes(objID, attributes, withChange, ActualTime.inMilliseconds());
	}

	@Override
	public void simulationStarted() {
		ListenedMap<Human> humans = Simulation.getInstance().getObjectMaster().getHumans();
		for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
			Human human = humans.get(objID);
			if (human != null) {
				Value property = human.getProperty(token, PropertyName.simobj_attributeArray);
				model.setInitialAttributes(objID, ObjectRequester.getInstance().requestAttributeArray(token, property, this));
			}
		}
	}

	@Override
	public int receiveObject(int requestID, Object object) {
		ObjectRequester.getInstance().receive(requestID, object);
		return 0;
	}

	/**
	 * Simulation.showMessage finds this method by its name and calls it
	 * with the Simulation instance as receiver, so it has to be static.
	 */
	public static void myPrint(String line) {
		System.out.println(line);
	}

	/**
	 * Shows the picture sequence in the action grid, without a simulation and without a database.
	 */
	public static void main(String[] args) {
		SimDashboard dashboard = new SimDashboard();
		SwingUtilities.invokeLater(() -> {
			dashboard.createAndShowFrame();
			dashboard.startPictures();
		});
	}

	private void startPictures() {
		pictureMode = true;
		automaticButton.setEnabled(false);
		showNextPicture();
		new Timer(PictureSequence.TICK_MILLIS, event -> showNextPicture()).start();
	}

	private void showNextPicture() {
		grid.setFigure(PictureSequence.figureAt(pictureTick++));
	}

	private void createAndShowFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Social World Dashboard");
		frame.setBounds(50, 20, 1500, 975);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(createContent());
		frame.setVisible(true);

		refresh();
		new Timer(REFRESH_MILLIS, event -> refresh()).start();
	}

	private JPanel createContent() {
		labelHeader = new JLabel(" ");
		labelHeader.setFont(labelHeader.getFont().deriveFont(Font.BOLD, 16f));
		labelHeader.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

		JPanel content = new JPanel(new BorderLayout());
		content.add(createHeader(), BorderLayout.NORTH);
		content.add(grid, BorderLayout.CENTER);
		content.add(createRightArea(), BorderLayout.EAST);
		content.add(createBottomArea(), BorderLayout.SOUTH);

		grid.setSelectionListener(this::selectByClick);
		matrix.setSelectionListener(this::selectByClick);
		return content;
	}

	private JPanel createHeader() {
		automaticButton.setToolTipText("Zeigt rechts den Menschen mit der gr\u00F6\u00DFten \u00C4nderung, jeden mindestens "
				+ AutomaticSelection.HOLD_MILLIS / 1000 + " s lang. Ein Klick in Raster oder Matrix schaltet sie aus.");
		automaticButton.addActionListener(event -> switchAutomatic());

		JPanel header = new JPanel(new BorderLayout());
		header.add(labelHeader, BorderLayout.CENTER);
		header.add(automaticButton, BorderLayout.EAST);
		return header;
	}

	private JPanel createBottomArea() {
		populationPanel.setPreferredSize(new Dimension(0, POPULATION_HEIGHT));

		JPanel area = new JPanel(new BorderLayout());
		area.add(populationPanel, BorderLayout.CENTER);
		area.add(createLegend(), BorderLayout.SOUTH);
		return area;
	}

	private JPanel createRightArea() {
		detail.setPreferredSize(new Dimension(DETAIL_WIDTH, 0));

		JPanel area = new JPanel(new BorderLayout());
		area.add(createMatrixArea(), BorderLayout.CENTER);
		area.add(detail, BorderLayout.EAST);
		return area;
	}

	private JPanel createMatrixArea() {
		JPanel scale = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
		scale.add(createLegendEntry("niedrig", AttributeMatrixPanel.COLOR_LOW));
		scale.add(createLegendEntry("Mitte", AttributeMatrixPanel.COLOR_MIDDLE));
		scale.add(createLegendEntry("hoch", AttributeMatrixPanel.COLOR_HIGH));
		scale.add(new JLabel("Strich: k\u00FCrzlich ge\u00E4ndert"));

		JPanel area = new JPanel(new BorderLayout());
		area.setPreferredSize(new Dimension(MATRIX_WIDTH, 0));
		area.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 2));
		area.add(matrix, BorderLayout.CENTER);
		area.add(scale, BorderLayout.SOUTH);
		return area;
	}

	private JPanel createLegend() {
		JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		for (ActionFamily family : ActionFamily.values()) {
			legend.add(createLegendEntry(family.getLabel(), family.getColor()));
		}
		legend.add(createLegendEntry("Unt\u00E4tig", ActionGridPanel.COLOR_IDLE));
		legend.add(new JLabel("Zahl oben rechts: Intensit\u00E4t.  Balken: Restdauer.  Roter Rahmen: unterbrochen."));
		return legend;
	}

	private JLabel createLegendEntry(String text, Color color) {
		JLabel entry = new JLabel(text);
		entry.setOpaque(true);
		entry.setBackground(color);
		entry.setForeground(ActionGridPanel.readableTextOn(color));
		entry.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
		return entry;
	}

	private void refresh() {
		long nowMillis = ActualTime.inMilliseconds();
		VisualModel.Snapshot snapshot = model.snapshot();

		labelHeader.setText(LocalTime.now().format(CLOCK)
				+ "     Aktiv " + snapshot.countActing() + "/" + VisualModel.OBJECT_COUNT
				+ "     Neue Aktionen/s " + newActionsRate.update(snapshot.newActions, nowMillis)
				+ "     Attribut-\u00C4nderungen/s " + attributeChangesRate.update(snapshot.sumAttributeChanges(), nowMillis));
		if (automatic.isActive()) {
			int objID = automatic.update(snapshot, nowMillis, selectedObject);
			if (objID != selectedObject) show(objID);
		}
		grid.setState(snapshot, nowMillis);
		matrix.setState(snapshot, nowMillis);
		history.record(snapshot, nowMillis);
		population.record(snapshot, nowMillis);
		populationPanel.setState(population.samples(), nowMillis);
		detail.setState(snapshot, history, nowMillis, selectedObject, readSelectedPosition(),
				pictureMode ? "Bildfolge l\u00E4uft, Klicks sind abgeschaltet" : automatic.describe(nowMillis));
	}

	private void selectByClick(int objID) {
		// the detail column would ask for a simulation that does not run
		if (pictureMode) return;

		automatic.stop();
		automaticButton.setSelected(false);
		show(objID);
		refresh();
	}

	private void switchAutomatic() {
		if (automaticButton.isSelected()) {
			automatic.start(ActualTime.inMilliseconds());
		}
		else {
			automatic.stop();
		}
		refresh();
	}

	private void show(int objID) {
		selectedObject = objID;
		grid.setSelectedObject(objID);
		matrix.setSelectedObject(objID);
	}

	private String readSelectedPosition() {
		if (selectedObject == VisualModel.NO_OBJECT) return "";

		Human human = Simulation.getInstance().getObjectMaster().getHumans().get(selectedObject);
		if (human == null) return "Position unbekannt";

		Position position = human.getPosition(token);
		if (position.checkIsObjectNothing()) return "Position unbekannt";

		return ObjectDetailPanel.positionText(position.getX(token), position.getY(token), position.getZ(token));
	}
}
