package org.socialworld.actions;


public class ActionNothing extends AbstractAction {

	private static ActionNothing instance;
	
	private ActionNothing() {
		this.type = ActionType.ignore;
	}
	
	public static ActionNothing getInstance() {
		if (instance == null) {
			instance = new ActionNothing();
		}
		return instance;
		
	}
	
	@Override
	public void perform() {
		// do nothing

	}

}
