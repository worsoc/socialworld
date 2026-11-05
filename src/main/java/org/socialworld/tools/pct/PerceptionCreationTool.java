package org.socialworld.tools.pct;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.socialworld.calculation.Value;
import org.socialworld.core.EventType;
import org.socialworld.datasource.pool.GaussPoolPerceptionType;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

/**
 * Assigns paths to an event type and a slot (perception type), also to several slots at once.
 */
public class PerceptionCreationTool extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int COUNT_SLOTS = GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY;

	private static final String MARK_FILLED = "●";
	private static final String MARK_EMPTY = "○";
	private static final String MARK_NOT_EDITABLE = "\u25CF !";

	private final transient PerceptionDescriptionStore store;
	private final transient PerceptionPathCatalog catalog;
	private final transient PerceptionEditor editor;

	private transient PerceptionPathFilter filter = PerceptionPathFilter.NONE;
	private String subject = Value.VALUE_BY_NAME_EVENT_CAUSER;

	private final DefaultListModel<EventType> eventsModel = new DefaultListModel<EventType>();
	private final JList<EventType> events = new JList<EventType>(eventsModel);
	private final DefaultListModel<String> slotsModel = new DefaultListModel<String>();
	private final JList<String> slots = new JList<String>(slotsModel);

	private final JComboBox<Option<Integer>> groups = new JComboBox<Option<Integer>>();
	private final PerceptionPathTableModel pathsModel;
	private final JLabel countOfPaths = new JLabel();

	private final JTable pathsTable;
	private final JRadioButton causer = new JRadioButton("causer", true);
	private final JRadioButton target = new JRadioButton("target");

	private final JLabel status = new JLabel();
	private final DefaultListModel<PerceptionPath> selectedModel = new DefaultListModel<PerceptionPath>();
	private final JList<PerceptionPath> selected = new JList<PerceptionPath>(selectedModel);
	private final JSpinner threshold = new JSpinner(new SpinnerNumberModel(PerceptionEditor.DEFAULT_RELEVANCE_THRESHOLD, 1, Integer.MAX_VALUE, 1));
	private final JTextField otherSlots = new JTextField();
	private final JButton addButton = new JButton("Add selected paths \u2192");
	private final JButton removeButton = new JButton("Remove");
	private final JButton saveButton = new JButton("Save");
	private final JButton clearButton = new JButton("Clear slot");
	private final JTextArea selectionJson = readOnlyText();

	// true while the program itself changes a selection, so that it is not taken for a choice of the user
	private boolean updating;

	public PerceptionCreationTool(PerceptionDescriptionStore store, PerceptionPathCatalog catalog) {
		super("Perception Creation Tool - " + store.getDescription());

		this.store = store;
		this.catalog = catalog;
		this.editor = new PerceptionEditor(store);
		this.pathsModel = new PerceptionPathTableModel(catalog);
		this.pathsTable = new JTable(pathsModel);

		JSplitPane centerAndRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createPathsPanel(), createSelectionPanel());
		centerAndRight.setResizeWeight(0.65);
		JSplitPane all = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createEventsPanel(), centerAndRight);

		getContentPane().add(all, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (changesMayBeLost()) {
					dispose();
					System.exit(0);
				}
			}
		});
		setSize(new Dimension(1500, 900));

		fillEvents("");
		fillGroups();
		refreshPaths();
		events.setSelectedValue(PerceptionSqlFile.EVENT_TYPE, true);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			try {
				new PerceptionCreationTool(new TablePoolPerceptionDescriptionStore(), new PerceptionPathCatalog()).setVisible(true);
			} catch (RuntimeException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "The table swpool_epd cannot be read: " + e, "Database", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});
	}

	// ---------------------------------------------------------------------------
	// event and slot
	// ---------------------------------------------------------------------------

	private JPanel createEventsPanel() {

		JTextField search = new JTextField();
		search.setBorder(BorderFactory.createTitledBorder("Search event"));
		onChange(search, this::fillEvents);

		events.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		events.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) eventSelected(); });

		slots.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		slots.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		slots.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) slotSelected(); });

		JScrollPane scrollEvents = new JScrollPane(events);
		scrollEvents.setBorder(BorderFactory.createTitledBorder("Event"));
		JScrollPane scrollSlots = new JScrollPane(slots);
		scrollSlots.setBorder(BorderFactory.createTitledBorder("Slot (perception type)"));

		JPanel lists = new JPanel(new GridLayout(2, 1));
		lists.add(scrollEvents);
		lists.add(scrollSlots);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(290, 0));
		panel.add(search, BorderLayout.NORTH);
		panel.add(lists, BorderLayout.CENTER);
		return panel;
	}

	private void fillEvents(String text) {

		EventType selected = events.getSelectedValue();
		String searched = text.trim().toLowerCase();

		eventsModel.clear();
		for (EventType eventType : EventType.values()) {
			if (eventType != EventType.nothing && eventType.name().toLowerCase().contains(searched)) {
				eventsModel.addElement(eventType);
			}
		}
		if (selected != null) {
			events.setSelectedValue(selected, true);
		}
	}

	private void eventSelected() {

		if (updating) return;

		EventType eventType = events.getSelectedValue();
		if (eventType == null) return;

		if (!changesMayBeLost()) {
			updating = true;
			events.setSelectedValue(editor.getEventType(), true);
			updating = false;
			return;
		}

		openSlot(eventType, 0);
	}

	private void slotSelected() {

		if (updating) return;

		int slot = slots.getSelectedIndex();
		if (slot < 0 || slot == editor.getSlot()) return;

		if (!changesMayBeLost()) {
			updating = true;
			slots.setSelectedIndex(editor.getSlot());
			updating = false;
			return;
		}

		openSlot(editor.getEventType(), slot);
	}

	private void openSlot(EventType eventType, int slot) {

		editor.open(eventType, slot, subject);

		updating = true;
		slotsModel.clear();
		for (int index = 0; index < COUNT_SLOTS; index++) {
			slotsModel.addElement(String.format("%2d %s", index, markOf(index)));
		}
		slots.setSelectedIndex(slot);
		updating = false;

		if (!editor.getSubject().equals(subject)) {
			showSubject(editor.getSubject());
			refreshGroupsAndPaths();
		}
		showPerception();
	}

	private String markOf(int slot) {
		if (!editor.isFilled(slot)) return MARK_EMPTY;
		return editor.isSlotEditable(slot) ? MARK_FILLED : MARK_NOT_EDITABLE;
	}

	/**
	 * @return true if there is nothing to lose, or the user does not mind
	 */
	private boolean changesMayBeLost() {
		return !editor.isChanged() || JOptionPane.showConfirmDialog(this,
				"Slot " + editor.getSlot() + " is not saved yet. Discard the changes?",
				"Not saved", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
	}

	// ---------------------------------------------------------------------------
	// filters and paths
	// ---------------------------------------------------------------------------

	private JPanel createPathsPanel() {

		pathsTable.setAutoCreateRowSorter(true);
		pathsTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		pathsTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		pathsTable.getColumnModel().getColumn(2).setPreferredWidth(260);
		pathsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
		pathsTable.getColumnModel().getColumn(4).setPreferredWidth(500);

		addButton.addActionListener(e -> addPaths());

		JPanel south = new JPanel(new BorderLayout());
		south.add(countOfPaths, BorderLayout.WEST);
		south.add(addButton, BorderLayout.EAST);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createFilterPanel(), BorderLayout.NORTH);
		panel.add(new JScrollPane(pathsTable), BorderLayout.CENTER);
		panel.add(south, BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createFilterPanel() {

		ButtonGroup subjects = new ButtonGroup();
		subjects.add(causer);
		subjects.add(target);
		causer.addActionListener(e -> subjectSelected(Value.VALUE_BY_NAME_EVENT_CAUSER));
		target.addActionListener(e -> subjectSelected(Value.VALUE_BY_NAME_EVENT_TARGET));
		JPanel subjectButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		subjectButtons.add(causer);
		subjectButtons.add(target);

		JComboBox<PerceptionPathFilter.Kind> kinds = new JComboBox<PerceptionPathFilter.Kind>(PerceptionPathFilter.Kind.values());
		kinds.addActionListener(e -> {
			filter = filter.withKind((PerceptionPathFilter.Kind) kinds.getSelectedItem());
			refreshPaths();
		});

		JComboBox<Option<Integer>> criteria = new JComboBox<Option<Integer>>();
		criteria.addItem(new Option<Integer>("any", null));
		for (KnowledgeFact_Criterion criterion : KnowledgeFact_Criterion.values()) {
			if (criterion != KnowledgeFact_Criterion.nothing && !criterion.isRelation()) {
				criteria.addItem(new Option<Integer>(criterion.name(), criterion.getIndex()));
			}
		}
		criteria.addActionListener(e -> {
			filter = filter.withCriterion(selected(criteria).value);
			refreshPaths();
		});

		groups.addActionListener(e -> {
			Option<Integer> group = selected(groups);
			if (group != null) {
				filter = filter.withSubjectGroup(group.value);
				refreshPaths();
			}
		});

		JTextField text = new JTextField();
		onChange(text, value -> {
			filter = filter.withText(value);
			refreshPaths();
		});

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Filter"));
		addRow(panel, 0, "Subject", subjectButtons);
		addRow(panel, 1, "Kind", kinds);
		addRow(panel, 2, "Criterion", criteria);
		addRow(panel, 3, "Subject group", groups);
		addRow(panel, 4, "Text", text);
		return panel;
	}

	private void addRow(JPanel panel, int row, String label, Component component) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = row;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(new JLabel(label), constraints);
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		panel.add(component, constraints);
	}

	private void subjectSelected(String subject) {

		if (!editor.setSubject(subject)) {
			showSubject(this.subject);
			JOptionPane.showMessageDialog(this,
					"All paths of a slot start at its subject. Remove the selected paths before you change the subject.");
			return;
		}
		this.subject = subject;
		refreshGroupsAndPaths();
		showPerception();
	}

	private void showSubject(String subject) {
		this.subject = subject;
		causer.setSelected(subject.equals(Value.VALUE_BY_NAME_EVENT_CAUSER));
		target.setSelected(subject.equals(Value.VALUE_BY_NAME_EVENT_TARGET));
	}

	private void refreshGroupsAndPaths() {
		fillGroups();
		refreshPaths();
	}

	private void fillGroups() {

		Option<Integer> selected = selected(groups);

		List<Option<Integer>> options = new ArrayList<Option<Integer>>();
		options.add(new Option<Integer>("any", PerceptionPath.NO_SUBJECT_GROUP));
		for (int group : catalog.getSubjectGroups(subject)) {
			options.add(new Option<Integer>(catalog.getGroupName(group), group));
		}

		groups.removeAllItems();
		for (Option<Integer> option : options) {
			groups.addItem(option);
			if (selected != null && selected.label.equals(option.label)) {
				groups.setSelectedItem(option);
			}
		}
	}

	private void refreshPaths() {
		List<PerceptionPath> found = catalog.find(subject, filter);
		pathsModel.setPaths(found);
		countOfPaths.setText(" " + found.size() + " of " + catalog.find(subject, PerceptionPathFilter.NONE).size() + " paths");
	}

	// ---------------------------------------------------------------------------
	// the selected perception
	// ---------------------------------------------------------------------------

	private JPanel createSelectionPanel() {

		selected.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
				PerceptionPath path = (PerceptionPath) value;
				String text = PerceptionSelectionText.formatPath(path, catalog.contains(editor.getSubject(), path));
				return super.getListCellRendererComponent(list, text, index, isSelected, hasFocus);
			}
		});

		removeButton.addActionListener(e -> {
			editor.remove(selected.getSelectedValuesList());
			showPerception();
		});

		threshold.addChangeListener(e -> {
			if (!updating) {
				editor.setRelevanceThreshold((Integer) threshold.getValue());
				showPerception();
			}
		});

		otherSlots.setToolTipText("For example 8, 13 or 0-39/5 for every fifth slot");
		saveButton.addActionListener(e -> save());
		clearButton.addActionListener(e -> clearSlot());

		JPanel settings = new JPanel(new GridBagLayout());
		addRow(settings, 0, "Threshold", threshold);
		addRow(settings, 1, "Also save to slots", otherSlots);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttons.add(removeButton);
		buttons.add(saveButton);
		buttons.add(clearButton);

		JPanel controls = new JPanel(new BorderLayout());
		controls.add(settings, BorderLayout.NORTH);
		controls.add(buttons, BorderLayout.SOUTH);

		JScrollPane scrollSelected = new JScrollPane(selected);
		scrollSelected.setBorder(BorderFactory.createTitledBorder("Perception of the slot"));
		JScrollPane scrollJson = new JScrollPane(selectionJson);
		scrollJson.setBorder(BorderFactory.createTitledBorder("JSON"));

		selectionJson.setLineWrap(true);

		JPanel upper = new JPanel(new BorderLayout());
		upper.add(status, BorderLayout.NORTH);
		upper.add(scrollSelected, BorderLayout.CENTER);
		upper.add(controls, BorderLayout.SOUTH);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setPreferredSize(new Dimension(460, 0));
		panel.add(upper);
		panel.add(scrollJson);
		return panel;
	}

	private void showPerception() {

		updating = true;
		selectedModel.clear();
		for (PerceptionPath path : editor.getPaths()) {
			selectedModel.addElement(path);
		}
		threshold.setValue(editor.getRelevanceThreshold());
		updating = false;

		target.setEnabled(editor.isSubjectAllowed(Value.VALUE_BY_NAME_EVENT_TARGET));

		boolean editable = editor.isEditable();
		threshold.setEnabled(editable);
		otherSlots.setEnabled(editable);
		addButton.setEnabled(editable);
		removeButton.setEnabled(editable);
		saveButton.setEnabled(editor.canSave());
		clearButton.setEnabled(editor.isFilled(editor.getSlot()));

		status.setText(" " + PerceptionSelectionText.formatStatus(editor));

		String json = editor.getJson();
		selectionJson.setText(json != null ? json : "");
		selectionJson.setCaretPosition(0);
	}

	private void clearSlot() {

		if (JOptionPane.showConfirmDialog(this,
				"Clear slot " + editor.getSlot() + " of " + editor.getEventType() + "? This cannot be undone.",
				"Clear slot", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
			return;
		}

		int slot = editor.getSlot();
		try {
			editor.clearSlot();
		} catch (IllegalStateException e) {
			openSlot(editor.getEventType(), slot);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Clear slot", JOptionPane.ERROR_MESSAGE);
			return;
		}
		openSlot(editor.getEventType(), slot);
		status.setText(" Slot " + slot + " cleared (" + store.getDescription() + ")");
	}

	private void addPaths() {

		List<PerceptionPath> paths = new ArrayList<PerceptionPath>();
		for (int row : pathsTable.getSelectedRows()) {
			paths.add(pathsModel.getPath(pathsTable.convertRowIndexToModel(row)));
		}
		editor.add(paths);
		showPerception();
	}

	private void save() {

		Set<Integer> others;
		try {
			others = PerceptionSlotList.parse(otherSlots.getText(), COUNT_SLOTS);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Slots", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<Integer> filled = editor.findOtherFilledSlots(others);
		if (!filled.isEmpty() && JOptionPane.showConfirmDialog(this,
				"These slots are filled and would be overwritten: " + filled + ". Continue?",
				"Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
			return;
		}

		Set<Integer> written;
		try {
			written = editor.save(others);
		} catch (IllegalStateException e) {
			openSlot(editor.getEventType(), editor.getSlot());
			JOptionPane.showMessageDialog(this, e.getMessage() + ". Earlier slots may already be saved.", "Save", JOptionPane.ERROR_MESSAGE);
			return;
		}
		otherSlots.setText("");
		openSlot(editor.getEventType(), editor.getSlot());
		status.setText(" Saved to slots " + written + " (" + store.getDescription() + ")");
	}

	// ---------------------------------------------------------------------------
	// helpers
	// ---------------------------------------------------------------------------

	private static JTextArea readOnlyText() {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		return area;
	}

	@SuppressWarnings("unchecked")
	private static <T> Option<T> selected(JComboBox<Option<T>> comboBox) {
		return (Option<T>) comboBox.getSelectedItem();
	}

	private static void onChange(JTextField field, Consumer<String> consumer) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
			@Override public void removeUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
			@Override public void changedUpdate(DocumentEvent e) { consumer.accept(field.getText()); }
		});
	}

	private static final class Option<T> {
		final String label;
		final T value;

		Option(String label, T value) {
			this.label = label;
			this.value = value;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
