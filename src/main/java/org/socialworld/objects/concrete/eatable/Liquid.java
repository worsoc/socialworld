package org.socialworld.objects.concrete.eatable;

import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.statics.Mapping_Liquid2LexemIDLowerPart;

public abstract class Liquid extends Item {

	protected EnumLiquid belongsTo;
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	public Liquid() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Liquid);
	}

	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Liquid2LexemIDLowerPart.getInstance().get(belongsTo);
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return State.getObjectNothing();
	}

}
