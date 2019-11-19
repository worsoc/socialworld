package org.socialworld.objects.connections;

import org.socialworld.objects.SimulationObject;

public class Connection {

	private boolean object1IsMaster = false;
	
	private SimulationObject object1;
	private SimulationObject object2;
	
	public ConnectionType type;
	
	public Connection(SimulationObject object1, SimulationObject object2, ConnectionType type) {
		this.object1 = object1;
		this.object2 = object2;
		this.type = type;
	}

	public void setAsMasterSlaveConnection() {
			this.object1IsMaster = true;
	}

	
	public boolean isMasterSlaveConnection() {
		return this.object1IsMaster;
	}
	
	public SimulationObject getObject1() {
		return this.object1;
	}

	public SimulationObject getObject2() {
		return this.object2;
	}
	
}
