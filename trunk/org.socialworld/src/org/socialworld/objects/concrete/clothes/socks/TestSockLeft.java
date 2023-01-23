package org.socialworld.objects.concrete.clothes.socks;

import org.socialworld.objects.State;
import org.socialworld.objects.concrete.clothes.Sock;

public class TestSockLeft extends Sock {

	@Override
	public boolean isLeftFootSock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRightFootSock() {
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
		return State.getObjectNothing();
	}

}
