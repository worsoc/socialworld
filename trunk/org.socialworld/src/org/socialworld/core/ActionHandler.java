/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.objects.SimulationObject;

/**
 * Manages the actions of an {@link SimulationObject}.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class ActionHandler extends Thread {

	/**
	 * the simulation object whose actions are manged
	 */
	protected SimulationObject object;
	/**
	 * the queue where all object's actions are inserted (orderer by action's
	 * time and priority)
	 */
	protected List<Action> actionQueue;
	/**
	 * the action that actually is handled (executed).
	 */
	protected Action actualAction;

	public ActionHandler(final SimulationObject simulationObject) {
		this.actualAction = null;
		this.actionQueue = new ArrayList<Action>();
		this.object = simulationObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			// TODO ActionQueue abarbeiten.
			// FIXME (tyloesand) Der ActionHandler ist kein thread, da es zu
			// jedem Simulationsobjekt einen eigenen Actionhandler gibt. das
			// wäre zu viele threads!!!
		}
	}

	/**
	 * @return the actualAction
	 */
	public Action getActualAction() {
		return this.actualAction;
	}

	/**
	 * The method gets an action element from action list and lets the according
	 * object execute the action.
	 */
	public void doActualAction() {
		// TODO (tyloesand): das Action Element muss dann auch aus der Liste
		// entfernt werden
		Action action;
		action = this.actionQueue.iterator().next();

		if (this.actualAction.getRemainedDuration() == 0) {
			this.actualAction = action;
		} else if (this.actualAction.getPriority() < action.getPriority()) {
			this.actualAction = action;
		}

		if (this.actualAction != null) {
			this.actualAction.lowerRemainedDuration(1);
			this.object.doAction(this.actualAction);
		}
	}

	/**
	 * The method inserts an action element into the action list of a simulation
	 * object.
	 * 
	 * @param action
	 */
	public void insertAction(final Action action) {
		this.actionQueue.add(action);
	}

	/**
	 * The method searches for an action element that meets the properties of an
	 * action description. The description holds information for what attributes
	 * list is searched. If all given search attributes are found in one action
	 * element the action element will be returned.
	 * 
	 * @param actionDescription
	 *            (search criteria)
	 * @return action element that meets the search criteria
	 */
	public Action findAction(final SearchActionDescription actionDescription) {
		// TODO Iteration und Abbruch am Ende
		Action action;
		while (true) {
			action = this.actionQueue.iterator().next();
			if (action == null) {
				break;
			}

			if (actionDescription.isSearchByType()) {
				if (action.getType() != actionDescription.getType()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMode()) {
				if (action.getMode() != actionDescription.getMode()) {
					continue;
				}
			}
			if (actionDescription.isSearchByTarget()) {
				if (action.getTarget() != actionDescription.getTarget()) {
					continue;
				}
			}
			if (actionDescription.isSearchByIntensity()) {
				if (action.getIntensity() != actionDescription.getIntensity()) {
					continue;
				}
			}
			if (actionDescription.isSearchByPriority()) {
				if (action.getPriority() != actionDescription.getPriority()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMinTime()) {
				if (action.getMinTime() != actionDescription.getMinTime()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMaxTime()) {
				if (action.getMaxTime() != actionDescription.getMaxTime()) {
					continue;
				}
			}
			if (actionDescription.isSearchByDirection()) {
				if (action.getDirection() != actionDescription.getDirection()) {
					continue;
				}
			}
			if (actionDescription.isSearchByDuration()) {
				if (action.getDuration() != actionDescription.getDuration()) {
					continue;
				}
			}

			return action;
		}

		return action;
	}
}
