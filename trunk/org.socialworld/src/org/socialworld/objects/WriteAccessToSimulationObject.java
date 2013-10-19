package org.socialworld.objects;
import org.socialworld.attributes.Position;
import org.socialworld.core.Action;

public class WriteAccessToSimulationObject {
	private SimulationObject object;
	
	public WriteAccessToSimulationObject(SimulationObject object) {
		this.object = object;
		object.setWriteAccess(this);
	}
	
	public SimulationObject getObject() {
		return this.object;
	}
	
	public void setObjectID(int objectID, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setObjectID(objectID, this);
	}

	public void setPosition(Position pos, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setPosition(pos, this);
	}
	
	public void setAction(Action action, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setAction(action, this);
	}

	public void setInfluenceTypes(int types[], Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setInfluenceTypes(types, this);
	}

	public void setReactionTypes(int types[], Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setReactionTypes(types, this);
	}

	public void setState2ActionType(int type, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setState2ActionType(type, this);
	}
}
