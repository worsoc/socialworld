package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionDescriptionEntry;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventPerceptionDescription;
import org.socialworld.datasource.parsing.JsonExtractionStep;

import com.google.gson.Gson;

/**
 * Composes the description of one perception from selected paths.
 *
 * All paths of one criterion become one listing, so the facts say for example
 * "the human has, among others, green" and not "the trousers are green".
 * A value stands for itself.
 */
public final class PerceptionDescriptionComposer {

	private static final String CATEGORY_PROPERTY = "property";
	private static final String CATEGORY_VALUE = "value";

	// the perceiver knows it from its own experience
	private static final int SOURCE_CATEGORY_MYSELF = 1;

	// A purely technical number that separates the blocks. Numbering the criteria from 2 on
	// (0 and 1 used to be source and subject in the lines) only keeps the data readable.
	private static final int FIRST_ORDER_NR = 2;

	private static final Gson GSON = new Gson();

	public JsonEventPerceptionDescription compose(
			EventType eventType, int perceptionType, int relevanceThreshold, String subjectValueName, List<PerceptionPath> paths) {

		EventPerceptionDescriptionEntry entry = new EventPerceptionDescriptionEntry();
		entry.relevanceThreshold = Integer.toString(relevanceThreshold);
		entry.sourceCategory = SOURCE_CATEGORY_MYSELF;
		entry.sourcePathSteps = Arrays.asList(GetValue.getValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF));
		entry.subjectPathSteps = Arrays.asList(
				GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS),
				GetValue.getValue(subjectValueName));
		entry.extractions = composeExtractions(paths);

		JsonEventPerceptionDescription description = new JsonEventPerceptionDescription();
		description.eventType = eventType.name();
		description.perceptionType = perceptionType;
		description.relevanceThreshold = relevanceThreshold;
		description.entrys = new ArrayList<EventPerceptionDescriptionEntry>(Arrays.asList(entry));

		return description;
	}

	public String toJson(JsonEventPerceptionDescription description) {
		return GSON.toJson(description);
	}

	private List<JsonExtractionStep> composeExtractions(List<PerceptionPath> paths) {

		int nextOrderNrForValues = FIRST_ORDER_NR;
		for (PerceptionPath path : paths) {
			if (path.isProperty()) {
				nextOrderNrForValues = Math.max(nextOrderNrForValues, orderNrOfCriterion(path.getCriterion()) + 1);
			}
		}

		// The blocks keep the order in which their first path was selected.
		Map<Integer, List<JsonExtractionStep>> blocks = new LinkedHashMap<Integer, List<JsonExtractionStep>>();

		for (PerceptionPath path : paths) {
			int orderNr = path.isProperty() ? orderNrOfCriterion(path.getCriterion()) : nextOrderNrForValues++;
			blocks.computeIfAbsent(orderNr, key -> new ArrayList<JsonExtractionStep>()).add(toStep(path, orderNr));
		}

		List<JsonExtractionStep> extractions = new ArrayList<JsonExtractionStep>();
		for (List<JsonExtractionStep> block : blocks.values()) {
			extractions.addAll(block);
		}
		return extractions;
	}

	private int orderNrOfCriterion(int criterion) {
		return FIRST_ORDER_NR + criterion;
	}

	private JsonExtractionStep toStep(PerceptionPath path, int orderNr) {

		JsonExtractionStep step = new JsonExtractionStep();
		step.orderNr = orderNr;
		step.pathSteps = path.getSteps();

		// empty fields stay null, so that they are missing in the JSON
		step.customLabel = path.getValueName().isEmpty() ? null : path.getValueName();

		if (path.isProperty()) {
			step.category = CATEGORY_PROPERTY;
			step.criterion = path.getCriterion();
		}
		else {
			step.category = CATEGORY_VALUE;
			step.resultType = path.getResultType();
		}

		return step;
	}
}
