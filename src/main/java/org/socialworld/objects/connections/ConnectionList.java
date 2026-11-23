/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.objects.connections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.SimulationObject;

public class ConnectionList {

	private SimulationObject listOwner;
	private List<Connection> connections;
	
	public ConnectionList(SimulationObject listOwner) {
		this.listOwner = listOwner;
		// read by the PositionCalculator (carried objects follow) while connections are made (2026-11-23)
		connections = new CopyOnWriteArrayList<Connection>();
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

	/** As connectAsMasterTo, with the offset of the slave to the master (for carried). */
	public void connectAsMasterTo(SimulationObject slave, ConnectionType type, Vector offset) {
		Connection newConnection;
		newConnection = new Connection(listOwner, slave,  type);
		newConnection.setAsMasterSlaveConnection();
		newConnection.setOffset(offset);
		connections.add(newConnection);
		slave.addConnection(newConnection, listOwner);
	}

	/**
	 * @return the connection in which the owner of the list is the slave of the given type, null if there is none
	 */
	public Connection getConnectionToMaster(ConnectionType type) {
		for (Connection con : connections) {
			if (con.type == type && con.isMasterSlaveConnection() && con.getObject2() == this.listOwner) return con;
		}
		return null;
	}

	/**
	 * @return the slaves of the owner of the list in connections of the given type
	 */
	public List<SimulationObject> getSlaves(ConnectionType type) {
		List<SimulationObject> slaves = new ArrayList<SimulationObject>();
		for (Connection con : connections) {
			if (con.type == type && con.isMasterSlaveConnection() && con.getObject1() == this.listOwner) slaves.add(con.getObject2());
		}
		return slaves;
	}

	public void connectAsSlaveTo(SimulationObject master,  ConnectionType type) {
		Connection newConnection;
		newConnection = new Connection(master, listOwner,  type);
		newConnection.setAsMasterSlaveConnection();
		connections.add(newConnection);
		master.addConnection(newConnection, listOwner);
	}
	
	public void releaseConnection(SimulationObject object) {
		// the iterator of the CopyOnWriteArrayList cannot remove, so remove the found connection directly
		for (Connection con : connections) {
			if (con.getObject1() ==  object || con.getObject2() == object) {
				connections.remove(con);
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
