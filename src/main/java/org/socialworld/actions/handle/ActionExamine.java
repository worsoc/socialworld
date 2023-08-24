package org.socialworld.actions.handle;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventType;
import org.socialworld.objects.SimulationObject;

public class ActionExamine extends AbstractAction {

	private Examine examine;
	
	private SimulationObject target;

	public ActionExamine(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	
	public ActionExamine(ActionExamine original) {
		super(original);
	}

	@Override
	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject target;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			target =  (SimulationObject) value.getValue() ;
			this.setTarget(target);
		}

	}

	@Override
	protected void setFurtherProperties(AbstractAction original) {

		setTarget(((ActionExamine) original).getTarget());

	}

	@Override
	public void perform() {

		EventToCauser event;
		EventType eventType;

		eventType = getEventToCauserType( mode);
		
		if (eventType == EventType.nothing) return;
		
  		this.examine = new Examine(this);
  		
		event = new EventToCauser(eventType,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(SimulationCluster.action),  examine /* as performer */);

		addEvent(event);

	}
	
	private EventType getEventToCauserType(ActionMode mode) {
		
		EventType eventType;
   			
		switch (mode) {

			case look:
				eventType = EventType.selfExamineByLook;
				break;
			case smell:
				eventType = EventType.selfExamineBySmell;
				break;
			case taste:
				eventType = EventType.selfExamineByTaste;
				break;
			case touch:
				eventType = EventType.selfExamineByTouch;
				break;
			default:
				eventType = EventType.nothing;
			
		}
		
	  	return eventType;
	  	
	}

	public void setTarget(SimulationObject target) {
		this.target = target;
	}

	private SimulationObject getTarget() {
		return this.target;
	}
	
	public Value getTargetAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.target);
	}


}
