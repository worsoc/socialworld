/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.clothes.caps.Hat;
import org.socialworld.objects.concrete.tools.Pitchfork;

/**
 * The connection carried (2026-11-23): master carries slave with an offset; each side finds the other in its list.
 * The objects are created without the database and without a state, so the connection is put into both lists
 * by hand, as ConnectionList.connectAsMasterTo does through addConnection.
 */
class CarriedConnectionTest {

	@Test
	void aConnectionWithoutOffset_hasTheZeroVector() {
		Connection con = new Connection(new Pitchfork(), new Hat(), ConnectionType.carried);
		assertThat(con.getOffset().is000()).isTrue();
	}

	@Test
	void theOffset_isACopy() {
		Vector offset = new Vector(new Vector(0, 0, 800));	// a copy is mutable
		Connection con = new Connection(new Pitchfork(), new Hat(), ConnectionType.carried);
		con.setOffset(offset);
		offset.add(new Vector(0, 0, 100));
		assertThat(con.getOffset().getZ()).isEqualTo(800f);
	}

	@Test
	void theCarried_findsItsCarrier_theCarrierFindsWhatItCarries() {
		Pitchfork carrier = new Pitchfork();
		Hat carried = new Hat();
		Connection con = new Connection(carrier, carried, ConnectionType.carried);
		con.setAsMasterSlaveConnection();

		ConnectionList carrierList = new ConnectionList(carrier);
		ConnectionList carriedList = new ConnectionList(carried);
		carrierList.add(con, carried);
		carriedList.add(con, carrier);

		assertThat(carriedList.getConnectionToMaster(ConnectionType.carried)).isSameAs(con);
		assertThat(carrierList.getConnectionToMaster(ConnectionType.carried)).isNull();
		assertThat(carrierList.getSlaves(ConnectionType.carried)).containsExactly(carried);
		assertThat(carriedList.getSlaves(ConnectionType.carried)).isEmpty();
		assertThat(carrierList.getSlaves(ConnectionType.inside)).isEmpty();

		carriedList.release(con, carrier);
		assertThat(carriedList.getConnectionToMaster(ConnectionType.carried)).isNull();
	}
}
