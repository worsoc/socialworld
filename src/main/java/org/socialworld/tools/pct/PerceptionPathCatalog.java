package org.socialworld.tools.pct;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.socialworld.objects.GroupingOfSimulationObjects;

/**
 * All paths a perception can use, per subject. The paths come from the meta information
 * of the simulation classes and are generated once, when a subject is asked for the first time.
 */
public final class PerceptionPathCatalog {

	private static final String PRAEFIX_GROUPING_CONSTANT = "GROUPING_NUMBER_";

	private final PerceptionGeneration generation = new PerceptionGeneration();
	private final Map<String, List<PerceptionPath>> pathsPerSubject = new HashMap<String, List<PerceptionPath>>();
	private final Map<Integer, String> groupNames = readGroupNames();

	/**
	 * @param subjectValueName for example Value.VALUE_BY_NAME_EVENT_CAUSER
	 */
	public List<PerceptionPath> find(String subjectValueName, PerceptionPathFilter filter) {

		List<PerceptionPath> result = new ArrayList<PerceptionPath>();
		for (PerceptionPath path : getPaths(subjectValueName)) {
			if (filter.matches(path)) {
				result.add(path);
			}
		}
		return result;
	}

	public boolean contains(String subjectValueName, PerceptionPath path) {
		return getPaths(subjectValueName).contains(path);
	}

	/**
	 * Only the groupings that really have paths: choosing another one would show an empty list.
	 *
	 * @return the grouping numbers in the order of the catalog
	 */
	public List<Integer> getSubjectGroups(String subjectValueName) {

		Set<Integer> groups = new LinkedHashSet<Integer>();
		for (PerceptionPath path : getPaths(subjectValueName)) {
			groups.add(path.getSubjectGroup());
		}
		return new ArrayList<Integer>(groups);
	}

	/**
	 * @return for example ALL_ANIMALS, or the number if the grouping has no name
	 */
	public String getGroupName(int groupingNumber) {
		String name = groupNames.get(groupingNumber);
		return name != null ? name : Integer.toString(groupingNumber);
	}

	private List<PerceptionPath> getPaths(String subjectValueName) {
		return pathsPerSubject.computeIfAbsent(subjectValueName, this::generate);
	}

	private List<PerceptionPath> generate(String subjectValueName) {
		List<PerceptionPath> paths = new ArrayList<PerceptionPath>();
		paths.addAll(generation.generateAllValuePaths(subjectValueName));
		paths.addAll(generation.generateAllPropertyPaths(subjectValueName));
		return Collections.unmodifiableList(paths);
	}

	private static Map<Integer, String> readGroupNames() {

		Map<Integer, String> names = new HashMap<Integer, String>();
		for (Field field : GroupingOfSimulationObjects.class.getFields()) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == int.class
					&& field.getName().startsWith(PRAEFIX_GROUPING_CONSTANT)) {
				try {
					names.putIfAbsent(field.getInt(null), field.getName().substring(PRAEFIX_GROUPING_CONSTANT.length()));
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		return names;
	}
}
