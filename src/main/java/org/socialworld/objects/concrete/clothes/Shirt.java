package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.properties.IWearable;

public abstract class Shirt extends Item implements IWearable {

	protected static int getLexemIdLowerValue() {
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
