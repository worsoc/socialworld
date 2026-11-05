package org.socialworld.tools.pct;

import java.util.Map;

/**
 * Where the tool gets the descriptions from and later saves them to.
 * The tool knows only this interface, so the table swpool_epd can be connected without changing it.
 */
public interface PerceptionDescriptionStore {

	/**
	 * @return the JSON of all filled perception types of the event type
	 */
	Map<Integer, String> loadAll(int eventType);

	void save(int eventType, int perceptionType, String json);

	/**
	 * @return where the data comes from, as it is shown to the user
	 */
	String getDescription();
}
