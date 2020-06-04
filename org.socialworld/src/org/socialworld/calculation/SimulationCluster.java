package org.socialworld.calculation;

public enum SimulationCluster {
	
	unknown(0),
	toBeSet(1),
	knowledge(11),
	percipience(21),
	position(31), pathFinder(32),
	attributeArray(41),
	action(51),
	event(61),
	objectMaster(1001),
	total(8888),
	todo(9998),
	test(9999);
	
	private int index;

	private SimulationCluster(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static SimulationCluster getName(int index) {
		for (SimulationCluster cluster : SimulationCluster.values())
			if (cluster.index == index)
				return cluster;
		return unknown;
	}

}
