package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.socialworld.knowledge.KnowledgeFact_Criterion;

/**
 * The paths as a table.
 */
final class PerceptionPathTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final String[] COLUMNS = {"Kind", "Subject group", "Value name", "Type", "Path"};

	private final transient PerceptionPathCatalog catalog;
	private transient List<PerceptionPath> paths = new ArrayList<PerceptionPath>();

	PerceptionPathTableModel(PerceptionPathCatalog catalog) {
		this.catalog = catalog;
	}

	PerceptionPath getPath(int row) {
		return paths.get(row);
	}

	void setPaths(List<PerceptionPath> paths) {
		this.paths = paths;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return paths.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		PerceptionPath path = paths.get(row);
		switch (column) {
			case 0: return kindOf(path);
			case 1: return catalog.getGroupName(path.getSubjectGroup());
			case 2: return path.getValueName();
			case 3: return path.getResultType();
			default: return String.join(".", path.getSteps());
		}
	}

	static String kindOf(PerceptionPath path) {
		return path.isProperty() ? KnowledgeFact_Criterion.getName(path.getCriterion()).name() : "value";
	}
}
