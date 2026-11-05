package org.socialworld.tools.pct;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Keeps the descriptions in memory only: everything is gone when the program ends.
 * This stands in as long as the table swpool_epd is not connected.
 */
final class InMemoryPerceptionDescriptionStore implements PerceptionDescriptionStore {

	private final Map<Integer, Map<Integer, String>> descriptions = new HashMap<Integer, Map<Integer, String>>();

	/**
	 * @return a store with the 40 rows of the five templates
	 */
	static InMemoryPerceptionDescriptionStore withExampleData() {
		InMemoryPerceptionDescriptionStore store = new InMemoryPerceptionDescriptionStore();
		for (int perceptionType = 0; perceptionType < PerceptionSqlFile.COUNT_PERCEPTION_TYPES; perceptionType++) {
			store.save(PerceptionSqlFile.EVENT_TYPE.getIndex(), perceptionType, PerceptionSqlFile.createJson(perceptionType));
		}
		return store;
	}

	@Override
	public Map<Integer, String> loadAll(int eventType) {
		return new TreeMap<Integer, String>(descriptions.getOrDefault(eventType, new HashMap<Integer, String>()));
	}

	@Override
	public void save(int eventType, int perceptionType, String json) {
		descriptions.computeIfAbsent(eventType, key -> new HashMap<Integer, String>()).put(perceptionType, json);
	}

	@Override
	public void delete(int eventType, int perceptionType) {
		Map<Integer, String> descriptionsOfEvent = descriptions.get(eventType);
		if (descriptionsOfEvent != null) {
			descriptionsOfEvent.remove(perceptionType);
		}
	}

	@Override
	public String getDescription() {
		return "example data in memory, not the database";
	}
}
