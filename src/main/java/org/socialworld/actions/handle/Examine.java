package org.socialworld.actions.handle;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.SimulationObject;

public class Examine extends ActionPerformer {

	public Examine(ActionExamine action) {
		super(action);
	}

	public static List<String> getEventParamNameList() {
 		List<String> result = new ArrayList<String>();
 		result.add("TODO");
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
