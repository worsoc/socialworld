package org.socialworld.objects.concrete.eatable;

import org.socialworld.objects.Item;
import org.socialworld.objects.State;

public abstract class Liquid extends Item {

	@Override
	protected int getLexemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkObjectBelongsToGroup(short groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
	}

}
