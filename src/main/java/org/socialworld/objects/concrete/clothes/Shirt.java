package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.properties.IWearable;

public abstract class Shirt extends HumanCrafted implements IWearable {

	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHIRT;
	}
	
	@Override
	protected int getLexemIdLowValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHIRT;
	}

	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return true; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }


}
