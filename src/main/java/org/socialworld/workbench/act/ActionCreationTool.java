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
package org.socialworld.workbench.act;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.propertyChange.ListenedMap;

/**
 * A second window next to the dashboard: gives a chosen human an action into the
 * pipeline of the running simulation and shows who is within the range of its event.
 * Has to be opened after the simulation has created its objects.
 */
public final class ActionCreationTool {

	private static final int REFRESH_MILLIS = 500;
	private static final int HUMAN_COUNT = 100;
	private static final float DEFAULT_INTENSITY = 60f;
	private static final int DEFAULT_PRIORITY = 100;
	private static final double DEFAULT_DURATION_IN_SECONDS = 1;

	private static final AccessTokenAct token = AccessTokenAct.getValid();

	private final ActionMapPanel map = new ActionMapPanel();
	private final JSpinner spinnerCauser = new JSpinner(new SpinnerNumberModel(1, 1, HUMAN_COUNT, 1));
	private final JComboBox<ActionMode> comboMode = new JComboBox<ActionMode>(EventRange.getSupportedModes().toArray(new ActionMode[0]));
	private final JSpinner spinnerIntensity = new JSpinner(new SpinnerNumberModel(DEFAULT_INTENSITY, 0d, 9999d, 1d));
	private final JSpinner spinnerPriority = new JSpinner(new SpinnerNumberModel(DEFAULT_PRIORITY, 0, 256, 1));
	private final JSpinner spinnerDuration = new JSpinner(new SpinnerNumberModel(DEFAULT_DURATION_IN_SECONDS, 0d,
			ActionRequest.MAX_DURATION_IN_MILLISECONDS / 1000d, 0.5d));
	private final JLabel labelRange = new JLabel(" ");
	private final JTextArea log = new JTextArea(9, 40);

	private Map<Integer, Point> positions = new HashMap<Integer, Point>();

	public void show() {
		SwingUtilities.invokeLater(this::createAndShowFrame);
	}

	private void createAndShowFrame() {
		JFrame frame = new JFrame("Action Creation Tool");
		frame.setBounds(120, 60, 1000, 760);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(createContent());

		Timer timer = new Timer(REFRESH_MILLIS, event -> refresh());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent event) {
				timer.stop();
			}
		});

		comboMode.setSelectedItem(ActionMode.sayScream);
		frame.setVisible(true);
		refresh();
		timer.start();
	}

	private JPanel createContent() {
		log.setEditable(false);

		map.setSelectionListener(id -> {
			spinnerCauser.setValue(id);
			showMap();
		});
		map.setRelocationListener(this::relocate);
		spinnerCauser.addChangeListener(event -> showMap());
		comboMode.addActionListener(event -> showMap());

		JPanel content = new JPanel(new BorderLayout());
		content.add(map, BorderLayout.CENTER);
		content.add(createControls(), BorderLayout.EAST);
		content.add(new JScrollPane(log), BorderLayout.SOUTH);
		return content;
	}

	private JPanel createControls() {
		JButton buttonPerform = new JButton("Ausführen");
		buttonPerform.addActionListener(event -> perform());

		JPanel fields = new JPanel(new GridLayout(0, 1, 0, 4));
		fields.add(new JLabel("Verursacher (Klick), Versetzen (Ziehen)"));
		fields.add(spinnerCauser);
		fields.add(new JLabel("Aktion"));
		fields.add(comboMode);
		fields.add(new JLabel("Intensität"));
		fields.add(spinnerIntensity);
		fields.add(new JLabel("Priorität"));
		fields.add(spinnerPriority);
		fields.add(new JLabel("Dauer in s (0: einmal, nicht unterbrechbar)"));
		fields.add(spinnerDuration);
		fields.add(labelRange);
		fields.add(buttonPerform);

		JPanel controls = new JPanel(new BorderLayout());
		controls.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		controls.setPreferredSize(new Dimension(260, 0));
		controls.add(fields, BorderLayout.NORTH);
		return controls;
	}

	private void refresh() {
		positions = readPositions();
		showMap();
	}

	private Map<Integer, Point> readPositions() {
		Map<Integer, Point> result = new HashMap<Integer, Point>();
		ListenedMap<Human> humans = Simulation.getInstance().getObjectMaster().getHumans();

		for (int id = 1; id <= HUMAN_COUNT; id++) {
			Human human = humans.get(id);
			if (human == null) continue;

			Position position = human.getPosition(token);
			if (position.checkIsObjectNothing()) continue;

			result.put(id, new Point(position.getX(token), position.getY(token)));
		}
		return result;
	}

	private void showMap() {
		int causer = (Integer) spinnerCauser.getValue();
		float range = EventRange.getRangeInMillimeters(selectedMode());
		List<Integer> inRange = EventRange.getObjectsInRange(causer, range, positions);

		labelRange.setText("Reichweite " + (int) (range / 1000) + " m, " + inRange.size() + " in Reichweite");
		map.show(positions, causer, range, inRange);
	}

	private ActionMode selectedMode() {
		return (ActionMode) comboMode.getSelectedItem();
	}

	private void perform() {
		ActionMode mode = selectedMode();
		int causer = (Integer) spinnerCauser.getValue();
		float intensity = ((Number) spinnerIntensity.getValue()).floatValue();
		int priority = (Integer) spinnerPriority.getValue();
		double durationInSeconds = ((Number) spinnerDuration.getValue()).doubleValue();
		int duration = (int) Math.round(durationInSeconds * 1000);

		ActionRequest.Problem problem;
		try {
			problem = ActionSender.send(new ActionRequest(mode, causer, intensity, priority, duration));
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
			return;
		}

		if (problem == ActionRequest.Problem.NONE) {
			List<Integer> inRange = EventRange.getObjectsInRange(causer, EventRange.getRangeInMillimeters(mode), positions);
			writeLog(String.format(Locale.ROOT, "Mensch %d eingereiht: %s, Intensität %.1f, Priorität %d, Dauer %.1f s, jetzt in Reichweite %s",
					causer, mode, intensity, priority, duration / 1000.0, inRange));
		}
		else {
			writeLog("Abgelehnt: " + problem);
		}
	}

	private void relocate(int id, Point target) {
		Human human = Simulation.getInstance().getObjectMaster().getHumans().get(id);
		if (human == null) return;

		try {
			Position old = human.getPosition(token);
			human.relocate(token, old.getCopy(PropertyName.simobj_position,
					target.x - old.getX(token), target.y - old.getY(token), 0));
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
			return;
		}

		writeLog(String.format(Locale.ROOT, "Mensch %d versetzt nach x %.1f m, y %.1f m", id, target.x / 1000.0, target.y / 1000.0));
		refresh();
	}

	private void writeLog(String line) {
		log.append(ActualTime.asTime().toString() + "  " + line + "\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
}
