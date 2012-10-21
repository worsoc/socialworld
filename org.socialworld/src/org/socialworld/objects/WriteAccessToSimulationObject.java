package org.socialworld.objects;
import org.socialworld.attributes.Position;

public class WriteAccessToSimulationObject {
	private SimulationObject object;
	
	public WriteAccessToSimulationObject(SimulationObject object) {
		this.object = object;
		object.setWriteAccess(this);
	}
	
	public void setObjectID(long objectID, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setObjectID(objectID, this);
	}

	public void setPosition(Position pos, Object caller) {
		if (caller instanceof ISimulationObjectWrite) object.setPosition(pos, this);
	}

}
