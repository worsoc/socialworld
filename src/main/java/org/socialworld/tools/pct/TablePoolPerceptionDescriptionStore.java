package org.socialworld.tools.pct;

import java.util.Map;
import java.util.TreeMap;

import org.socialworld.datasource.tablesPool.TablePoolEPD;

/**
 * Keeps the descriptions in the table swpool_epd.
 */
final class TablePoolPerceptionDescriptionStore implements PerceptionDescriptionStore {

	private final TablePoolEPD table = new TablePoolEPD();

	@Override
	public Map<Integer, String> loadAll(int eventType) {

		table.select(table.SELECT_ALL_COLUMNS, " WHERE eventType = " + eventType, "");

		Map<Integer, String> descriptions = new TreeMap<Integer, String>();
		for (int row = 0; row < table.rowCount(); row++) {
			descriptions.put(table.getPerceptionType(row), table.getJsonEPD(row));
		}
		return descriptions;
	}

	/**
	 * insert also updates an existing row.
	 *
	 * @throws IllegalStateException if the row does not hold the JSON afterwards: the connection only prints its errors
	 */
	@Override
	public void save(int eventType, int perceptionType, String json) {

		table.insert(eventType, perceptionType, json);

		if (!json.equals(table.getJsonEPD(eventType, perceptionType))) {
			throw new IllegalStateException("Slot " + perceptionType + " of event " + eventType + " could not be saved");
		}
	}

	/**
	 * @throws IllegalStateException if the row still exists afterwards: the connection only prints its errors
	 */
	@Override
	public void delete(int eventType, int perceptionType) {

		table.delete(eventType, perceptionType);

		if (table.count(eventType, perceptionType) > 0) {
			throw new IllegalStateException("Slot " + perceptionType + " of event " + eventType + " could not be cleared");
		}
	}

	@Override
	public String getDescription() {
		return "table swpool_epd";
	}
}
