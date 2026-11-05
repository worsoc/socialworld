package org.socialworld.tools.pct;

import java.util.Locale;

/**
 * Narrows down the paths of the catalog. A filter is never changed, every "with" method creates a new one.
 * What is not set does not filter.
 */
public final class PerceptionPathFilter {

	public enum Kind { ANY, VALUE, PROPERTY }

	public static final PerceptionPathFilter NONE =
			new PerceptionPathFilter(Kind.ANY, null, PerceptionPath.NO_SUBJECT_GROUP, "");

	private final Kind kind;
	private final Integer criterion;
	private final int subjectGroup;
	private final String text;

	private PerceptionPathFilter(Kind kind, Integer criterion, int subjectGroup, String text) {
		this.kind = kind;
		this.criterion = criterion;
		this.subjectGroup = subjectGroup;
		this.text = text;
	}

	public PerceptionPathFilter withKind(Kind kind) {
		return new PerceptionPathFilter(kind, criterion, subjectGroup, text);
	}

	/**
	 * @param criterion the index of a knowledge fact criterion, null for any
	 */
	public PerceptionPathFilter withCriterion(Integer criterion) {
		return new PerceptionPathFilter(kind, criterion, subjectGroup, text);
	}

	/**
	 * @param subjectGroup a grouping number, PerceptionPath.NO_SUBJECT_GROUP for any
	 */
	public PerceptionPathFilter withSubjectGroup(int subjectGroup) {
		return new PerceptionPathFilter(kind, criterion, subjectGroup, text);
	}

	/**
	 * @param text is searched in the value name and in the steps, upper and lower case do not matter
	 */
	public PerceptionPathFilter withText(String text) {
		return new PerceptionPathFilter(kind, criterion, subjectGroup, text.trim().toLowerCase(Locale.ROOT));
	}

	boolean matches(PerceptionPath path) {
		if (kind == Kind.VALUE && path.isProperty()) return false;
		if (kind == Kind.PROPERTY && !path.isProperty()) return false;
		if (criterion != null && path.getCriterion() != criterion) return false;
		if (subjectGroup != PerceptionPath.NO_SUBJECT_GROUP && path.getSubjectGroup() != subjectGroup) return false;
		return text.isEmpty() || containsText(path);
	}

	private boolean containsText(PerceptionPath path) {
		if (path.getValueName().toLowerCase(Locale.ROOT).contains(text)) return true;
		for (String step : path.getSteps()) {
			if (step.toLowerCase(Locale.ROOT).contains(text)) return true;
		}
		return false;
	}
}
