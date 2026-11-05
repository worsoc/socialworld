package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionDescriptionEntry;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventPerceptionDescription;
import org.socialworld.datasource.parsing.JsonExtractionStep;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * The way back: from the JSON of an existing description to its paths.
 *
 * Paths are taken as they are, whether the meta information offers them or not. If composing the
 * paths would not result in the same description, it is marked as not editable, so that saving
 * can never lose anything.
 */
public final class PerceptionDescriptionReader {

	private static final Pattern GET_VALUE = Pattern.compile("^GETVal\\((.+)\\)$");
	private static final String CATEGORY_PROPERTY = "property";
	private static final String CATEGORY_VALUE = "value";

	private static final Gson GSON = new Gson();

	private final PerceptionDescriptionComposer composer = new PerceptionDescriptionComposer();

	public PerceptionSelection read(String json) {

		JsonEventPerceptionDescription description;
		try {
			description = GSON.fromJson(json, JsonEventPerceptionDescription.class);
		} catch (JsonParseException e) {
			return PerceptionSelection.notEditable("The JSON cannot be read: " + e.getMessage());
		}
		if (description == null) {
			return PerceptionSelection.notEditable("The JSON is empty");
		}

		EventType eventType = EventType.fromName(description.eventType);
		if (eventType == EventType.nothing) {
			return PerceptionSelection.notEditable("Unknown event type: " + description.eventType);
		}

		if (description.entrys == null || description.entrys.size() != 1) {
			return PerceptionSelection.notEditable("Only a description with exactly one entry can be edited");
		}
		EventPerceptionDescriptionEntry entry = description.entrys.get(0);

		String subjectValueName = readSubject(entry);
		if (subjectValueName == null) {
			return PerceptionSelection.notEditable("Unknown form of the subject: " + entry.subjectPathSteps);
		}

		List<PerceptionPath> paths = new ArrayList<PerceptionPath>();
		if (entry.extractions != null) {
			for (JsonExtractionStep step : entry.extractions) {
				if (step.pathSteps == null) {
					return PerceptionSelection.notEditable("An extraction has no path");
				}
				if (CATEGORY_PROPERTY.equals(step.category)) {
					paths.add(new PerceptionPath(step.criterion, orEmpty(step.customLabel), "", step.pathSteps));
				}
				else if (CATEGORY_VALUE.equals(step.category)) {
					paths.add(new PerceptionPath(PerceptionPath.NO_CRITERION, orEmpty(step.customLabel), orEmpty(step.resultType), step.pathSteps));
				}
				else {
					return PerceptionSelection.notEditable("The tool cannot compose the category: " + step.category);
				}
			}
		}

		// Everything else (source, thresholds, order of the blocks ...) is covered by this comparison.
		String composed = composer.toJson(composer.compose(
				eventType, description.perceptionType, description.relevanceThreshold, subjectValueName, paths));
		if (!composed.equals(GSON.toJson(description))) {
			return PerceptionSelection.notEditable("Saving would change the description, so it can only be looked at");
		}

		return PerceptionSelection.editable(
				eventType, description.perceptionType, description.relevanceThreshold, subjectValueName, paths);
	}

	private String readSubject(EventPerceptionDescriptionEntry entry) {

		List<String> steps = entry.subjectPathSteps;
		if (steps == null || steps.size() != 2) return null;
		if (!steps.get(0).equals(GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS))) return null;

		Matcher matcher = GET_VALUE.matcher(steps.get(1));
		return matcher.matches() ? matcher.group(1) : null;
	}

	private String orEmpty(String text) {
		return text != null ? text : "";
	}
}
