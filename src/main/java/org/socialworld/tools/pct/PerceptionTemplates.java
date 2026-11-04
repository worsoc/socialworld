package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.socialworld.calculation.Value;

/**
 * What a perceiver perceives per perception type: the only content the tool has so far.
 * These are the five templates that repeat in swpool_epd.
 */
final class PerceptionTemplates {

	static final String SUBJECT = Value.VALUE_BY_NAME_EVENT_CAUSER;

	private static final List<List<PerceptionPath>> TEMPLATES = Arrays.asList(
			Arrays.asList(colour("shirt", "mainColour"), colour("trousers", "mainColour"), colour("cap", "mainColour"),
					material("shirt"), material("cap")),
			Arrays.asList(colour("cap", "mainColour"), material("cap")),
			Arrays.asList(material("shirt"), material("cap")),
			Arrays.asList(colour("cap", "mainColour"), colour("shirt", "mainColour")),
			Arrays.asList(colour("cap", "mainColour"), colour("cap", "mainColour_2")));

	private PerceptionTemplates() {
	}

	static List<PerceptionPath> getPaths(int perceptionType) {
		return TEMPLATES.get(perceptionType % TEMPLATES.size());
	}

	/**
	 * The templates may use paths the generator does not offer. That is no error, but it should be seen.
	 */
	static List<PerceptionPath> findPathsOutsideOfMetaInformation() {

		PerceptionGeneration generation = new PerceptionGeneration();
		Set<PerceptionPath> offered = new HashSet<PerceptionPath>();
		offered.addAll(generation.generateAllValuePaths(SUBJECT));
		offered.addAll(generation.generateAllPropertyPaths(SUBJECT));

		Set<PerceptionPath> checked = new HashSet<PerceptionPath>();
		List<PerceptionPath> outside = new ArrayList<PerceptionPath>();
		for (List<PerceptionPath> template : TEMPLATES) {
			for (PerceptionPath path : template) {
				if (checked.add(path) && !offered.contains(path)) {
					outside.add(path);
				}
			}
		}
		return outside;
	}

	private static PerceptionPath colour(String item, String label) {
		return new PerceptionPath(0, "inventory_" + item + ".SOGN_184549375." + label, "",
				inventorySteps(item, "GETProp(stateAppearance)", "GETFctVal(getMainColour)"));
	}

	private static PerceptionPath material(String item) {
		return new PerceptionPath(1, "inventory_" + item + ".SOGN_184549375.mainMaterial", "",
				inventorySteps(item, "GETProp(stateComposition)", "GETFctVal(getMainMaterial)"));
	}

	private static List<String> inventorySteps(String item, String stepToProperty, String stepToValue) {
		return Arrays.asList(
				"GETVal(event_params)",
				"GETVal(event_causer)#IsElem(50331647)",
				"GETProp(stateInventory)",
				"GETProp(stateInventory_inventory)",
				"GETProp(inventory_" + item + ")#IsElem(184549375)",
				stepToProperty,
				stepToValue);
	}
}
