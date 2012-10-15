/**
 * 
 */
package org.socialworld.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

import org.socialworld.objects.SimulationObject;

/**
 * Manages the actions of an {@link SimulationObject}.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class ActionHandler  {

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


	/**
	 * @return the actualAction
	 */
	public Action getActualAction() {
		return this.actualAction;
	}

	/**
	 * The method gets the first action element from action list and lets the according
	 * object execute the action.
	 */
	public void doActualAction() {
		Action action;
		Iterator<Action> iterator;
		
		iterator = this.actionQueue.iterator();
		if (iterator.hasNext())		
			action = iterator.next();
		else
			return;
		
		if (this.actualAction != null) 
			if (this.actualAction.getRemainedDuration() == 0) 
				this.actionQueue.remove(this.actualAction);
		
		
		this.actualAction = action;
		

		if (this.actualAction != null) {
			this.actualAction.lowerRemainedDuration(1);
			this.object.doAction(this.actualAction);
		}
	}

	/**
	 * The method adds an action element to the action list of a simulation
	 * object.
	 *  Therefore it compares the new action element to the priority and time of the list's actions
	 *  and inserts the new action elements according to the action list's order
	 * 
	 * @param newAction
	 */
	public void insertAction(final Action newAction) {
		
		long minTimeInMilliseconds;
		long maxTimeInMilliseconds;
		int priority;
		long duration;
		
		Action listedAction;
		
		ListIterator<Action> iterator;
		int currentIndex;
		
		if (newAction == null ) return;
		
		iterator = this.actionQueue.listIterator();
		
		minTimeInMilliseconds = newAction.getMinTime().getTotalMilliseconds();
		maxTimeInMilliseconds = newAction.getMaxTime().getTotalMilliseconds();
		priority = newAction.getPriority();
		duration = newAction.getDuration();
		
		while (iterator.hasNext()) {
			currentIndex = iterator.nextIndex() ;
			listedAction = iterator.next();
			
			if ( listedAction.getPriority() < priority ) 
				if ( (listedAction.getMaxTime().getTotalMilliseconds() + listedAction.getRemainedDuration()) <
						minTimeInMilliseconds )
					continue;
				else
					;
			else if ( listedAction.getPriority() == priority ) 
				if ( (listedAction.getMaxTime().getTotalMilliseconds() + listedAction.getRemainedDuration()) <
						maxTimeInMilliseconds )
					continue;
				else
					if (currentIndex == 1) 
						if (this.actionQueue.indexOf(this.actualAction) == 1)
							continue;
			else // if ( listedAction.getPriority() > priority )
				if ( listedAction.getMinTime().getTotalMilliseconds()  <=
						( maxTimeInMilliseconds + duration ) )
					continue;
				else
					if (currentIndex == 1) 
						if (this.actionQueue.indexOf(this.actualAction) == 1)
							continue;

			this.actionQueue.add(currentIndex, newAction);
			return;
		}
		this.actionQueue.add(newAction);
		
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
		Action action;
		Action noAction;
		Iterator<Action> iterator;

		noAction = null;
		
		iterator = this.actionQueue.iterator();
		
		while (iterator.hasNext()) {
			action = iterator.next();
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

		return noAction;
	}
}
