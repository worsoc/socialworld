package org.socialworld.objects.concrete.animals;

import org.socialworld.objects.Animal;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.statics.Mapping_Bird2LexemIDLowerPart;

public abstract class Bird extends Animal {

	protected EnumBird belongsTo;
	
	public Bird() {
		super();
	}

	public static int getLexemIdHigherValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_BIRD;
	}


	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Bird2LexemIDLowerPart.getInstance().get(belongsTo);
	}
}
