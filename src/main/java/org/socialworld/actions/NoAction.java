package org.socialworld.actions;

import org.socialworld.collections.ValueArrayList;

public final class NoAction extends AbstractAction {

	private static NoAction actionNothing;
	
	public final static AbstractAction getObjectNothing() {
		if (actionNothing == null) {
			actionNothing = new NoAction();
		}
		return actionNothing;
	}
	
	@Override
	protected final void setFurtherProperties(ValueArrayList actionProperties) {
	}

	@Override
	protected final void setFurtherProperties(AbstractAction original) {
	}

	@Override
	public final void perform() {
	}

}
