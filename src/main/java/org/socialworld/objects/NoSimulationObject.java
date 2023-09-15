package org.socialworld.objects;


public class NoSimulationObject extends SimulationObject {

	private static NoSimulationObject objectNothing;
	
	public static NoSimulationObject getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NoSimulationObject();
		}
		return objectNothing;
	}
	
	public static int getLexemIdHigherValue() {return 0;}  
	public static int getLexemIdLowerValue() {return 0;}  // GroupingNumberSuffix

	protected int getLexemIdHighValue() { return 0;}
	protected int getLexemIdLowPart() { return 0;}

	@Override
	public final boolean isSimulationObject() {return false;}

	@Override
	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.noObject;
	}


	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		return false;
	}

	@Override
	protected void assignState(StateSimulationObject state) {
	
	}

	@Override
	protected State getInitState(String stateClassName) {
		return State.getObjectNothing();
	}

}
