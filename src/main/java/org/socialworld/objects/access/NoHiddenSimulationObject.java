package org.socialworld.objects.access;


public class NoHiddenSimulationObject extends HiddenSimulationObject {

	private static NoHiddenSimulationObject objectNothing;
	
	public static NoHiddenSimulationObject getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NoHiddenSimulationObject();
		}
		return objectNothing;
	}

}
