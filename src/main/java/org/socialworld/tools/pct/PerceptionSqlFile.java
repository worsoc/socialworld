package org.socialworld.tools.pct;

import org.socialworld.core.EventType;

/**
 * Creates the content of an SQL file for the table swpool_epd.
 */
final class PerceptionSqlFile {

	static final EventType EVENT_TYPE = EventType.percipientExistsDistance5000;
	static final int COUNT_PERCEPTION_TYPES = 40;
	private static final int RELEVANCE_THRESHOLD = 100;

	private static final int ROWS_PER_INSERT = 10;
	private static final String INSERT = "INSERT INTO sw1.swpool_epd (eventType,perceptionType,JsonEPD) VALUES";

	private PerceptionSqlFile() {
	}

	static String createContent() {

		StringBuilder content = new StringBuilder();

		for (int perceptionType = 0; perceptionType < COUNT_PERCEPTION_TYPES; perceptionType++) {

			String json = createJson(perceptionType);

			if (perceptionType % ROWS_PER_INSERT == 0) {
				content.append(INSERT).append("\n");
			}

			boolean lastOfInsert = perceptionType % ROWS_PER_INSERT == ROWS_PER_INSERT - 1
					|| perceptionType == COUNT_PERCEPTION_TYPES - 1;

			content.append("\t (").append(EVENT_TYPE.getIndex()).append(",").append(perceptionType)
					.append(",'").append(json.replace("'", "''")).append("')")
					.append(lastOfInsert ? ";" : ",").append("\n");
		}

		return content.toString();
	}

	static String createJson(int perceptionType) {

		PerceptionDescriptionComposer composer = new PerceptionDescriptionComposer();

		return composer.toJson(composer.compose(
				EVENT_TYPE, perceptionType, RELEVANCE_THRESHOLD,
				PerceptionTemplates.SUBJECT, PerceptionTemplates.getPaths(perceptionType)));
	}
}
