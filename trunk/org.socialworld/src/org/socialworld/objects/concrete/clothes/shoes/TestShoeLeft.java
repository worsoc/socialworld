package org.socialworld.objects.concrete.clothes.shoes;

import org.socialworld.objects.State;
import org.socialworld.objects.concrete.clothes.Shoe;

public class TestShoeLeft extends Shoe {

	@Override
	public boolean isLeftFootShoe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRightFootShoe() {
		// TODO Auto-generated method stub
		return false;
	}

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
