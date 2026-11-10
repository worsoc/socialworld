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
package org.socialworld.tools.act;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenAct implements IAccessToken {

	private static AccessTokenAct valid;

	static AccessTokenAct getValid() {
		if (valid == null) {
			valid = new AccessTokenAct();
			SimulationCluster.act.addToken(valid);
		}
		return valid;
	}

	public boolean isValid() {
		return this == valid;
	}

	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.act;
	}
}
