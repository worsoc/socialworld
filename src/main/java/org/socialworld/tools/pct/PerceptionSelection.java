package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.socialworld.core.EventType;

/**
 * What is read from an existing description: the settings of one perception and its paths.
 * A description the tool could not write again in the same way can only be looked at.
 */
public final class PerceptionSelection {

	private static final String EDITABLE = "";

	private final EventType eventType;
	private final int perceptionType;
	private final int relevanceThreshold;
	private final String subjectValueName;
	private final List<PerceptionPath> paths;
	private final String reasonNotEditable;

	private PerceptionSelection(EventType eventType, int perceptionType, int relevanceThreshold,
			String subjectValueName, List<PerceptionPath> paths, String reasonNotEditable) {
		this.eventType = eventType;
		this.perceptionType = perceptionType;
		this.relevanceThreshold = relevanceThreshold;
		this.subjectValueName = subjectValueName;
		this.paths = Collections.unmodifiableList(new ArrayList<PerceptionPath>(paths));
		this.reasonNotEditable = reasonNotEditable;
	}

	static PerceptionSelection editable(EventType eventType, int perceptionType, int relevanceThreshold,
			String subjectValueName, List<PerceptionPath> paths) {
		return new PerceptionSelection(eventType, perceptionType, relevanceThreshold, subjectValueName, paths, EDITABLE);
	}

	static PerceptionSelection notEditable(String reason) {
		return new PerceptionSelection(EventType.nothing, 0, 0, "", new ArrayList<PerceptionPath>(), reason);
	}

	public boolean isEditable() {
		return reasonNotEditable.isEmpty();
	}

	/**
	 * @return why the description can only be looked at, empty if it is editable
	 */
	public String getReasonNotEditable() {
		return reasonNotEditable;
	}

	public EventType getEventType() {
		return eventType;
	}

	public int getPerceptionType() {
		return perceptionType;
	}

	public int getRelevanceThreshold() {
		return relevanceThreshold;
	}

	public String getSubjectValueName() {
		return subjectValueName;
	}

	public List<PerceptionPath> getPaths() {
		return paths;
	}
}
