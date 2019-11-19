package org.socialworld.objects.connections;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
	
	public void releaseConnection(SimulationObject object) {
		ListIterator<Connection> iterator = connections.listIterator();
		Connection con = null;
		while (iterator.hasNext()) {
			con = (Connection) iterator.next();
			if (con.getObject1() ==  object || con.getObject2() == object) {
				iterator.remove();
				object.releaseConnection(con, listOwner);
				break;
			}
		}
	}
	
	public void add(Connection connection, SimulationObject connectedObject) {
		if ( (connection.getObject1() == this.listOwner || connection.getObject2() == this.listOwner) && 
			 (connection.getObject1() == connectedObject || connection.getObject2() == connectedObject) ) {
			connections.add(connection);
		}
	}

	public void release(Connection connection, SimulationObject connectedObject) {
		if ( (connection.getObject1() == this.listOwner || connection.getObject2() == this.listOwner) && 
			 (connection.getObject1() == connectedObject || connection.getObject2() == connectedObject) ) {
			connections.remove(connection);
		}
	}

}
