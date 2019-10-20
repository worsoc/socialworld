package org.socialworld.actions;


import org.socialworld.collections.ValueArrayList;

public class ActionNothing extends AbstractAction {

	private static ActionNothing instance;
	
	private ActionNothing() {
		this.type = ActionType.ignore;
	}
	
	protected ActionNothing(AbstractAction original) {
		super(original);
	}
	
	public static ActionNothing getInstance() {
		if (instance == null) {
			instance = new ActionNothing();
		}
		return instance;
		
	}
	
	protected void setFurtherProperties(ValueArrayList actionProperties) {
		// no further properties
	}

	protected void setFurtherProperties(AbstractAction original) {
		//no further properties
	}

	@Override
	public void perform() {
		// do nothing

	}

}
