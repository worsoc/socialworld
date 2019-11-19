package org.socialworld.objects.connections;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.objects.SimulationObject;

public class ConnectionList {

	private SimulationObject listOwner;
	private List<Connection> connections;
	
	public ConnectionList(SimulationObject listOwner) {
		this.listOwner = listOwner;
		connections = new ArrayList<Connection>();
	}
	
	public void connectEqualTo(SimulationObject object, ConnectionType type) {
		Connection newConnection;
		newConnection = new Connection(listOwner, object,  type);
		connections.add(newConnection);
		object.addConnection(newConnection, listOwner);
	}

	public void connectAsMasterTo(SimulationObject slave, ConnectionType type) {
		Connection newConnection;
		newConnection = new Connection(listOwner, slave,  type);
		newConnection.setAsMasterSlaveConnection();
		connections.add(newConnection);
		slave.addConnection(newConnection, listOwner);
	}

	public void connectAsSlaveTo(SimulationObject master,  ConnectionType type) {
		Connection newConnection;
		newConnection = new Connection(master, listOwner,  type);
		newConnection.setAsMasterSlaveConnection();
		connections.add(newConnection);
		master.addConnection(newConnection, listOwner);
	}
	
	public void add(Connection connection, SimulationObject connectedObject) {
		if ( (connection.getObject1() == this.listOwner || connection.getObject2() == this.listOwner) && 
			 (connection.getObject1() == connectedObject || connection.getObject2() == connectedObject) ) {
			connections.add(connection);
		}
	}

}
