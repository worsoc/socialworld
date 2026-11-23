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
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
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
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.objects.Item;
import org.socialworld.objects.SimulationObject;
import org.socialworld.propertyChange.ListenedMap;

/**
 * A second window next to the dashboard: gives a chosen human an action into the
 * pipeline of the running simulation and shows who is within the range of its event.
 * It can also end the running action of a human, e.g. to wake a sleeping one, and check
 * whether the search by position finds every human at its real place.
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
	private final JComboBox<ActionMode> comboMode = new JComboBox<ActionMode>(offeredModes());
	// taking and dropping (inventory concept V4): the item and the hand
	private final JSpinner spinnerItem = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
	private final JComboBox<InventoryPlace> comboHand = new JComboBox<InventoryPlace>(ItemReach.PLACES);
	private final JSpinner spinnerIntensity = new JSpinner(new SpinnerNumberModel(DEFAULT_INTENSITY, 0d, 9999d, 1d));
	private final JSpinner spinnerPriority = new JSpinner(new SpinnerNumberModel(DEFAULT_PRIORITY, 0, 256, 1));
	private final JSpinner spinnerDuration = new JSpinner(new SpinnerNumberModel(DEFAULT_DURATION_IN_SECONDS, 0d,
			ActionRequest.MAX_DURATION_IN_MILLISECONDS / 1000d, 0.5d));
	private final JLabel labelRange = new JLabel(" ");
	private final JTextArea log = new JTextArea(9, 40);

	private Map<Integer, Point> positions = new HashMap<Integer, Point>();
	private Map<Integer, ActionMode> running = new HashMap<Integer, ActionMode>();

	/** the say modes (by range) and then taking and dropping */
	private static ActionMode[] offeredModes() {
		List<ActionMode> modes = new ArrayList<ActionMode>(EventRange.getSupportedModes());
		modes.addAll(ItemReach.getItemModes());
		modes.addAll(ItemReach.getWeaponModes());
		return modes.toArray(new ActionMode[0]);
	}

	public void show() {
		SwingUtilities.invokeLater(this::createAndShowFrame);
	}

	private void createAndShowFrame() {
		JFrame frame = new JFrame("Action Creation Tool");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(createContent());
		frame.setBounds(frameBounds());

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

	/**
	 * The window: wide and high enough for the map, the whole control column and the log, but never larger than the
	 * usable part of the screen (2026-11-23: before a fixed 1000 × 760, which cut off the column on the right).
	 */
	private static Rectangle frameBounds() {
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int width = Math.min(1200, screen.width - 40);
		int height = Math.min(860, screen.height - 40);
		return new Rectangle(screen.x + Math.min(120, screen.width - width), screen.y + Math.min(40, screen.height - height), width, height);
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
		// fixed scale, the map grows and scrolls instead of zooming out (2026-11-20)
		JScrollPane scrollMap = new JScrollPane(map);
		scrollMap.getVerticalScrollBar().setUnitIncrement(16);
		scrollMap.getHorizontalScrollBar().setUnitIncrement(16);
		content.add(scrollMap, BorderLayout.CENTER);
		// the column is as wide as its widest element, plus room for the vertical scroll bar: nothing is cut off on
		// the right; if the window is too low, the column scrolls
		JPanel controls = createControls();
		JScrollPane scrollControls = new JScrollPane(controls,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollControls.getVerticalScrollBar().setUnitIncrement(16);
		int scrollBarWidth = scrollControls.getVerticalScrollBar().getPreferredSize().width;
		scrollControls.setPreferredSize(new Dimension(controls.getPreferredSize().width + scrollBarWidth + 4, 0));
		content.add(scrollControls, BorderLayout.EAST);
		content.add(new JScrollPane(log), BorderLayout.SOUTH);
		return content;
	}

	private JPanel createControls() {
		JButton buttonPerform = new JButton("Ausführen");
		buttonPerform.addActionListener(event -> perform());
		JButton buttonStop = new JButton("Aktion beenden");
		buttonStop.addActionListener(event -> stop());
		JButton buttonCheck = new JButton("Suchbaum prüfen");
		buttonCheck.addActionListener(event -> checkSearchTree());
		JButton buttonFit = new JButton("Karte einpassen");
		buttonFit.addActionListener(event -> map.fitToHumans());
		JButton buttonItems = new JButton("Items und Inventar");
		buttonItems.addActionListener(event -> showItems());

		// one element per row, each as high as it needs (two-line labels); GridLayout would make all rows equally high
		JPanel fields = new JPanel();
		fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
		fields.add(new JLabel("<html>Verursacher (Klick auf der Karte),<br>Versetzen (Ziehen)</html>"));
		fields.add(spinnerCauser);
		fields.add(new JLabel("Aktion"));
		fields.add(comboMode);
		fields.add(new JLabel("Intensität"));
		fields.add(spinnerIntensity);
		fields.add(new JLabel("Priorität"));
		fields.add(spinnerPriority);
		fields.add(new JLabel("<html>Dauer in s<br>(0: einmal, nicht unterbrechbar)</html>"));
		fields.add(spinnerDuration);
		fields.add(new JLabel("<html>Item-ID (beim Schlag: Ziel-ID)<br>und Hand oder Platz</html>"));
		fields.add(spinnerItem);
		fields.add(comboHand);
		fields.add(labelRange);
		arrangeColumn(fields);

		// the buttons in two columns, so that the column stays short enough (2026-11-23: with the item fields of
		// inventory V4 the last button, "Items und Inventar", was cut off at the bottom of the window)
		JPanel buttons = new JPanel(new GridLayout(0, 2, 4, 4));
		buttons.add(buttonPerform);
		buttons.add(buttonStop);
		buttons.add(buttonItems);
		buttons.add(buttonCheck);
		buttons.add(buttonFit);

		JPanel column = new JPanel(new BorderLayout(0, 8));
		column.add(fields, BorderLayout.NORTH);
		column.add(buttons, BorderLayout.CENTER);

		JPanel controls = new JPanel(new BorderLayout());
		controls.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		controls.add(column, BorderLayout.NORTH);
		return controls;
	}

	/**
	 * Every element of the column: left-aligned, as wide as the column but only as high as it needs, with a small
	 * gap in between (for the BoxLayout of the column).
	 */
	private static void arrangeColumn(JPanel fields) {
		java.awt.Component[] elements = fields.getComponents();
		fields.removeAll();
		for (int i = 0; i < elements.length; i++) {
			JComponent element = (JComponent) elements[i];
			element.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			element.setMaximumSize(new Dimension(Integer.MAX_VALUE, element.getPreferredSize().height));
			// a little more space above a label, as it begins a new field
			if (i > 0) fields.add(javax.swing.Box.createVerticalStrut(element instanceof JLabel ? 8 : 2));
			fields.add(element);
		}
	}

	private void refresh() {
		positions = readPositions();
		running = readRunning();
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

	/** the mode of the running action of every human that acts now, for the colours of the map */
	private Map<Integer, ActionMode> readRunning() {
		Map<Integer, ActionMode> result = new HashMap<Integer, ActionMode>();
		ListenedMap<Human> humans = Simulation.getInstance().getObjectMaster().getHumans();

		for (Integer id : positions.keySet()) {
			Human human = humans.get(id);
			if (human == null) continue;

			ActionMode mode = human.getRunningActionMode(token);
			if (mode != null && mode != ActionMode.ignore) result.put(id, mode);
		}
		return result;
	}

	private void showMap() {
		int causer = (Integer) spinnerCauser.getValue();
		float range = ItemReach.isItemMode(selectedMode()) ? ItemReach.REACH_IN_MILLIMETERS : EventRange.getRangeInMillimeters(selectedMode());
		List<Integer> inRange = EventRange.getObjectsInRange(causer, range, positions);

		labelRange.setText("Reichweite " + (int) (range / 1000) + " m, " + inRange.size() + " in Reichweite");
		map.show(positions, running, causer, range, inRange);
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
			problem = ActionSender.send(new ActionRequest(mode, causer, intensity, priority, duration,
					(Integer) spinnerItem.getValue(), (InventoryPlace) comboHand.getSelectedItem()));
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
			return;
		}

		if (problem == ActionRequest.Problem.NONE && ItemReach.isWeaponMode(mode)) {
			writeLog(String.format(Locale.ROOT, "Mensch %d eingereiht: %s, Ziel %d (Waffe mit GlobalSwitches.OUTPUT_DEBUG_WEAPON)",
					causer, mode, (Integer) spinnerItem.getValue()));
		}
		else if (problem == ActionRequest.Problem.NONE && ItemReach.isItemMode(mode)) {
			writeLog(String.format(Locale.ROOT, "Mensch %d eingereiht: %s, Item %d, Hand %s (Wirkung mit GlobalSwitches.OUTPUT_DEBUG_INVENTORY)",
					causer, mode, (Integer) spinnerItem.getValue(), comboHand.getSelectedItem()));
		}
		else if (problem == ActionRequest.Problem.NONE) {
			List<Integer> inRange = EventRange.getObjectsInRange(causer, EventRange.getRangeInMillimeters(mode), positions);
			writeLog(String.format(Locale.ROOT, "Mensch %d eingereiht: %s, Intensität %.1f, Priorität %d, Dauer %.1f s, jetzt in Reichweite %s",
					causer, mode, intensity, priority, duration / 1000.0, inRange));
		}
		else {
			writeLog("Abgelehnt: " + problem);
		}
	}

	private void stop() {
		int causer = (Integer) spinnerCauser.getValue();

		ActionRequest.Problem problem;
		try {
			problem = ActionSender.stop(causer);
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
			return;
		}

		if (problem == ActionRequest.Problem.NONE) {
			writeLog("Mensch " + causer + ": Beenden der laufenden Aktion angefordert, wirkt mit der nächsten Sekunde");
		}
		else {
			writeLog("Abgelehnt: " + problem);
		}
	}

	private void checkSearchTree() {
		SearchTreeCheck.Result result;
		try {
			result = SearchTreeCheck.checkHumans(HUMAN_COUNT);
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
			return;
		}

		String withoutPosition = result.withoutPosition > 0 ? ", " + result.withoutPosition + " ohne Position" : "";
		if (result.notFound.isEmpty()) {
			writeLog("Suchbaum: alle " + result.checked + " Menschen an ihrer Position gefunden" + withoutPosition);
		}
		else {
			writeLog("Suchbaum: " + result.notFound.size() + " von " + result.checked
					+ " nicht an ihrer Position gefunden: " + result.notFound + withoutPosition);
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

	/** Lists the free items within reach of the causer and what it holds in its hands (inventory concept V4). */
	private void showItems() {
		int causerId = (Integer) spinnerCauser.getValue();
		try {
			Human causer = Simulation.getInstance().getObjectMaster().getHumans().get(causerId);
			if (causer == null) {
				writeLog("Mensch " + causerId + " gibt es nicht");
				return;
			}
			Map<Integer, Point> freeItems = new HashMap<Integer, Point>();
			Map<Integer, String> names = new HashMap<Integer, String>();
			Simulation.getInstance().getObjectMaster().getItems().forEach((int id, Item item) -> {
				if (item == null || item.isCarried()) return;
				Position position = item.getPosition(token);
				if (position.checkIsObjectNothing()) return;
				freeItems.put(id, new Point(position.getX(token), position.getY(token)));
				names.put(id, item.getClass().getSimpleName());
			});
			Position at = causer.getPosition(token);
			List<Integer> inReach = ItemReach.getItemsInReach(new Point(at.getX(token), at.getY(token)), freeItems);
			List<String> listed = new ArrayList<String>();
			for (Integer id : inReach) listed.add(id + " " + names.get(id));
			writeLog("Mensch " + causerId + ": freie Items in Reichweite (1 m): " + (listed.isEmpty() ? "keine" : listed));
			List<String> taken = new ArrayList<String>();
			for (InventoryPlace place : InventoryPlace.values()) {
				SimulationObject object = causer.getInventoryItem(token, place);
				if (!object.isSimulationObject()) continue;
				String entry = place + " " + describe(object);
				List<String> inside = new ArrayList<String>();
				for (SimulationObject content : object.getContents()) inside.add(describe(content));
				if (!inside.isEmpty()) entry += " mit " + inside;
				taken.add(entry);
			}
			writeLog("Mensch " + causerId + ": Inventar " + (taken.isEmpty() ? "leer" : taken));
		}
		catch (RuntimeException exception) {
			exception.printStackTrace();
			writeLog("Fehler: " + exception);
		}
	}

	private static String describe(SimulationObject object) {
		return object.isSimulationObject() ? object.getObjectID() + " " + object.getClass().getSimpleName() : "leer";
	}

	private void writeLog(String line) {
		log.append(ActualTime.asTime().toString() + "  " + line + "\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
}
