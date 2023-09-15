package org.socialworld.objects.concrete;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.statics.Mapping_HumanCrafted2LexemIDLowerPart;

public abstract class HumanCrafted extends Item {

	protected EnumHumanCrafted belongsTo;
	
	public static int getLexemIdHigherValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_HUMANCRAFTED;
	}

	@Override
	protected int getLexemIdHighValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_HUMANCRAFTED;
	}

	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_HumanCrafted2LexemIDLowerPart.getInstance().get(belongsTo);
	}
	
	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
	}

}
