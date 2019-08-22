package org.socialworld.actions;

public class ActionNothing extends AbstractAction {

	public ActionNothing() {
		this.type = ActionType.ignore;
	}
	
	@Override
	public void perform() {
		// do nothing

	}

}
