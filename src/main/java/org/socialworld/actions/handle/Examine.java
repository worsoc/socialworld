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
    	
    	return targets;
	}

}
