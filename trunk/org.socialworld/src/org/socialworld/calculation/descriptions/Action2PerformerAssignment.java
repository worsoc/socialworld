package org.socialworld.calculation.descriptions;

import org.socialworld.actions.ActionMode;
import org.socialworld.datasource.pool.Action2PerformerDescriptionPool;

public class Action2PerformerAssignment {

	
	private static Action2PerformerAssignment instance;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private Action2PerformerAssignment() {
	}

	/**
	 * The method returns back the only instance of the Action2PerformerAssignment.
	 * 
	 * @return singleton object of Action2PerformerAssignment
	 */
	public static Action2PerformerAssignment getInstance() {
		if (instance == null) {
			instance = new Action2PerformerAssignment();
		}
		return instance;
	}

	/**
	 * The method returns the description how an performer creates its properties (used as event params)
	 *  from action and actor properties.
	 * 
	 * @param ActionMode	 
	 * @return Action2PerformerDescription
	 */
	public Action2PerformerDescription getAction2PerformerDescription(ActionMode actionMode) {
		Action2PerformerDescription action2PerformerDescription;
		action2PerformerDescription = 
				Action2PerformerDescriptionPool.getInstance().getDescription(actionMode);
		
		return action2PerformerDescription;
	}

}
