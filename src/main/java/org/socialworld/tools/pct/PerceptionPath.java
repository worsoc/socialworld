package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * One way to read something from the subject of a perception:
 * a value, or a value that belongs to a knowledge fact criterion (a property).
 */
public final class PerceptionPath {

	public static final int NO_CRITERION = -1;

	private final int criterion;
	private final String valueName;
	private final String resultType;
	private final List<String> steps;

	PerceptionPath(int criterion, String valueName, String resultType, List<String> steps) {
		this.criterion = criterion;
		this.valueName = valueName;
		this.resultType = resultType;
		this.steps = Collections.unmodifiableList(new ArrayList<String>(steps));
	}

	public boolean isProperty() {
		return criterion != NO_CRITERION;
	}

	public int getCriterion() {
		return criterion;
	}

	public String getValueName() {
		return valueName;
	}

	/**
	 * The class at the end of a value path, for example SWType_31 or StateAppearance.
	 * Empty for a property, whose type is implicit.
	 */
	public String getResultType() {
		return resultType;
	}

	public List<String> getSteps() {
		return steps;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PerceptionPath)) return false;
		PerceptionPath other = (PerceptionPath) object;
		return criterion == other.criterion && valueName.equals(other.valueName)
				&& resultType.equals(other.resultType) && steps.equals(other.steps);
	}

	@Override
	public int hashCode() {
		return Objects.hash(criterion, valueName, resultType, steps);
	}

	@Override
	public String toString() {
		return criterion + "," + resultType + "," + valueName + ":" + String.join(".", steps);
	}
}
