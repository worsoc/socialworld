/*
* Social World
* Copyright (C) 2020  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.calculation;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import org.socialworld.core.IAccessToken;

public enum SimulationCluster {
	
	unknown(0),
	toBeSet(1),
	knowledge(11),
	talk(12),
	percipience(21),
	position(31), _pathFinder(32),
	attributeArray(41),
	action(51),
	_event(61),
	simulationObject(71),
	objectMaster(1001),
	expressionEvaluate(2001),
	expressionCalculate(2002),
	function(2003),
	core(7001),
	total(8888),
	visualize(9998),
	test(9999);
	
	private static final Map<String, SimulationCluster> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, SimulationCluster> INDEX_CACHE = new HashMap<>();

	static {
		for (SimulationCluster cluster : values()) {
			NAME_CACHE.put(cluster.name(), cluster);
			INDEX_CACHE.put(cluster.index, cluster);
		}
	}

	private final int index;

	private LinkedList<IAccessToken>  tokens = null;
	
	private SimulationCluster(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	
	static SimulationCluster getName(int index) {
		SimulationCluster cluster = INDEX_CACHE.get(index);
		return (cluster != null) ? cluster : unknown;
	}
	
	public static SimulationCluster fromName(String name) {
		SimulationCluster cluster = NAME_CACHE.get(name);
		return (cluster != null) ? cluster : unknown;
	}

	public void addToken(IAccessToken token) {
		if (this.tokens == null) {
			this.tokens = new LinkedList<IAccessToken>();
		}
		if (!this.tokens.contains(token)) {
			this.tokens.add(token);
		}
	}

	LinkedList<IAccessToken> getTokens() {
		return tokens;
	}
	
	public  List<PropertyUsingAs> getPossibleUsingAs() {
		return GetSimulationClustersPropUsingAs.getPossibleUsingAs(this);
	}

	public boolean isWithoutRestrictions() {
		switch (this) {
		case unknown:
		case toBeSet:
		case total:
			return true;
		default:
			return false;
		}
	}
}
