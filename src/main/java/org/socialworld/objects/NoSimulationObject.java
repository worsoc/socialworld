package org.socialworld.objects;

import org.socialworld.attributes.PropertyName;

public class NoSimulationObject extends SimulationObject {

	private static NoSimulationObject objectNothing;
	
	public static NoSimulationObject getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NoSimulationObject();
		}
		return objectNothing;
	}
	
	private static PropertyName[] usedStateAppearanceColourPropertyNames = new PropertyName[0];
	private static byte[] stateAppearanceMainColourCalculationColourSetsShares = new byte[0];

	
	protected int getLexemIdHighPart() { return 0;}
	protected int getLexemIdLowPart() { return 0;}

	@Override
	public final boolean isSimulationObject() {return false;}

	@Override
	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.noObject;
	}

	protected void initialize() {
		if (!isInitialized()) {
		}
	}


	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public PropertyName[] getUsedStateAppearanceColourPropertyNames() {
		return usedStateAppearanceColourPropertyNames;
	}
	
	public byte[] getStateAppearanceMainColourCalculationColourSetsShares() {
		return stateAppearanceMainColourCalculationColourSetsShares;
	}

	@Override
	protected void assignState(StateSimulationObject state) {
	
	}

	@Override
	protected State getInitState(String stateClassName) {
		return State.getObjectNothing();
	}

}
