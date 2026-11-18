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

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;

public class ActionExamine extends AbstractAction {

	private Examine examine;
	
	private SimulationObject target = NoSimulationObject.getObjectNothing();

	private static AccessTokenActionHandle token = AccessTokenActionHandle.getValid();

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
			target = getObjectRequester().requestSimulationObject(token, value, this);
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
				actor.getPosition(token),  examine /* as performer */);

		addEvent(event);

		// the examined object notices it (targetExamineByLook ...), as with ActionHandle (since 2026-11-18)
		if (this.target != null && this.target.isSimulationObject()) {
			eventType = getEventToTargetType(mode);
			if (eventType != EventType.nothing) {
				addEvent(new EventToTarget(eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(token),  examine /* as performer */));
			}
		}

	}

	private EventType getEventToTargetType(ActionMode mode) {

		switch (mode) {
			case look:
				return EventType.targetExamineByLook;
			case smell:
				return EventType.targetExamineBySmell;
			case taste:
				return EventType.targetExamineByTaste;
			case touch:
				return EventType.targetExamineByTouch;
			default:
				return EventType.nothing;
		}

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

	/** package-private for Examine.getTargets, like ActionHandle.getTarget */
	SimulationObject getTarget() {
		return this.target;
	}
	
	public Value getTargetAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.target);
	}


}
