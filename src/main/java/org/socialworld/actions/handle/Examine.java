/*
 * Social World
 * Copyright (C) 2020  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.actions.handle;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventTypeGeneral;
import org.socialworld.objects.SimulationObject;
import org.socialworld.tools.ListOperations;

public class Examine extends ActionPerformer {

	public Examine(ActionExamine action) {
		super(action);
	}

	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
		switch (mode) {
		case look:
	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			break;
		case smell:
	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			break;
		case taste:
	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			break;
		case touch:
	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			break;
		}
 		return result;
 	}

	public static List<String> getEventParamNumericValueNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
		switch (mode) {
		case look:
//	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
	 		result.addAll(ListOperations.addPrefixToElements(
	 				Value.VALUE_BY_NAME_EVENT_TARGET +  "." ,
	 				ColourSet.getTotalPortionValueNames(ColourSet.getSetsPropertyName())));
			break;
		case smell:
	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			break;
		case taste:
//	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
	 		result.addAll(ListOperations.addPrefixToElements(
	 				Value.VALUE_BY_NAME_EVENT_TARGET +  "." ,
	 				TasteSet.getTotalPortionValueNames(TasteSet.getSetsPropertyName())));
			break;
		case touch:
//	 		result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
	 		result.addAll(ListOperations.addPrefixToElements(
	 				Value.VALUE_BY_NAME_EVENT_TARGET +  "." ,
	 				MaterialSet.getTotalPortionValueNames(MaterialSet.getSetsPropertyName())));
			break;
		}
 		return result;
 	}

	@Override
	protected void choosePropertiesFromPropertyList(ValueArrayList properties) {

		Value property;
    	
    	property = properties.getValue(PropertyName.simobj_attributeArray.toString());
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
 
       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_TARGET);
    	if (property.isValid()) {
    		addProperty(property);
    	}

	}

	@Override
	protected void perform() {
		
		if (!isEvaluated()) {
			setEvaluated();
		}

	}

	@Override
	public List<SimulationObject> getTargets() {

		List<SimulationObject> targets = new ArrayList<SimulationObject>();
		// the examined object, so that the EventToTarget reaches it (since 2026-11-18)
		SimulationObject target = ((ActionExamine) this.getOriginalActionObject()).getTarget();
		if (target != null && target.isSimulationObject()) {
			targets.add(target);
		}
    	return targets;
	}

}
