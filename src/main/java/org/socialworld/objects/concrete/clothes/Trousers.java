package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IWearable;

public abstract class Trousers extends HumanCrafted implements IWearable {

	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TROUSERS;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Trousers() {
		super();
		belongsTo = EnumHumanCrafted.Trousers;
	}



	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return true; }
	public boolean isCap() {return false; }

}
