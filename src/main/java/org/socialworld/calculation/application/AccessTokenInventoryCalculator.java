/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.calculation.application;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

/**
 * The token of the InventoryCalculator (inventory concept V4, 2026-11-23). It belongs to the cluster position:
 * taking and dropping change where an item is, and it reads positions as the PositionCalculator does.
 */
public final class AccessTokenInventoryCalculator implements IAccessToken
{

	private static AccessTokenInventoryCalculator valid;
	
	static AccessTokenInventoryCalculator getValid() {
		if (valid == null) {
			valid = new AccessTokenInventoryCalculator();
			SimulationCluster.position.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.position;
	}
	
}
