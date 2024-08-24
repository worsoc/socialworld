/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.Time;



/**
 * Manages the actions of the simulation.
 * Therefore there are "3" lists of action handlers.
 * (a) a list that holds all action handlers permanently
 *   (that means that every simulation object's action handler is listed here)
 * (b) a list that holds reported action handlers temporarily
 *   (if a simulation object's action handler sorts an action to index 1 
 *   (that means it is rated best)
 *   the action handler reports itself with action's time and action's priority to the action master.
 *   So this is a list for good rated actions, that must be executed before.
 * (c) a list that holds actions that are executed for more than one seconds
 *  and whose execution has been started and should be continued.
 *  
 *  the lists are iterated in order c), b), a)
 *  that means, first all actions that must be continued,
 *   then the actions with high priority and
 *    at last the other actions
 *    
 *  Note that the action master doesn't hold action elements. It holds the action handler elements.
 *  
 * @author Mathias Sikos (MatWorsoc)
 */
public class ActionMaster extends SocialWorldThread {
	
	// a reported action handler is rejected because its best action element's execution time is too far in the future
	public static final int ACTIONMASTER_RETURN_REJECT_TOOMUCHWAIT = 1;
	// a reported action handler element has been inserted into the priority list (list (b))
	public static final int ACTIONMASTER_RETURN_INSERTED = 0;

	private static final int ACTIONMASTER_RETURN_SLEEP_MUCH = 2;
	private static final int ACTIONMASTER_RETURN_SLEEP_LESS = 1;
	private static final int ACTIONMASTER_RETURN_SLEEP_ZERO = 0;

	private boolean executeAllowed = true;
	
	private boolean isBlockedByAddingActionHandler = false;
	
	private static ActionMaster instance;
	
	
	// queue for action handlers that execute an action that must be continued (list (c))
	// it is iterated from first to last element and starts at the first element again (while elements remain in the list)
	private List<ActionHandler> continueActionHandlers;
	private  ListIterator<ActionHandler> continueHandlersIterator;

	// priority list for reported action handlers (list (b))
	// it is iterated one time from first to last element
	// after a complete iteration the list is cleared and will be filled with new reported action handler elements
	private ArrayList<List<ActionHandler>> reportedActionHandlers;
	private int[][] indexBySecondAndPriority;
	private  List<ActionHandler> actionHandlersNow;
	private  ListIterator<ActionHandler> handlersIterator;
	private int[] minPriorityBySecond;

	// queue for all action handlers  (list (a))
	// it is iterated from first to last element and starts at the first element again 	
	private List<ActionHandler> allActionHandlers;
	private  ListIterator<ActionHandler> allHandlersIterator;
	
	// the actual time measured in milliseconds
	private long nowInMilliseconds;
	// the actual time step (measured in seconds per minute)
	// is associated to the priority list for reported action handlers
	private byte secondOfTheActualMinute;
	
	
	/**
	 *  the constructor creates the lists.
	 *  
	 *  the list for reported action handlers is a list containing of lists.
	 *  For every second of a minute there is one list. And every list for a second is ordered by action's priority.
	 *  Furthermore there is a two dimensional array that holds the indexes for the second an the priority.
	 *  So a fast insertion of a new reported action handler is possible.
	 *  
	 *  For every list there is a list iterator.
	 */
	private ActionMaster() {
		
		this.sleepTime = 50;
		
		// list (b):
		reportedActionHandlers = new ArrayList<List<ActionHandler>>();
		for (int i = 0; i < ActionHandler.MAX_ACTION_WAIT_SECONDS; i++) {
			reportedActionHandlers.add(Collections.synchronizedList(new ArrayList<ActionHandler>()));
		}
		
		indexBySecondAndPriority = new int[ActionHandler.MAX_ACTION_WAIT_SECONDS][AbstractAction.MAX_ACTION_PRIORITY];
		minPriorityBySecond = new int[ActionHandler.MAX_ACTION_WAIT_SECONDS];

		for (int i = 0; i < ActionHandler.MAX_ACTION_WAIT_SECONDS; i++) {
			minPriorityBySecond[i] = AbstractAction.MAX_ACTION_PRIORITY;
		}

		actionHandlersNow = reportedActionHandlers.get(0);
		handlersIterator = actionHandlersNow.listIterator();

		// list (c):
		continueActionHandlers = new ArrayList<ActionHandler>();
		continueHandlersIterator = continueActionHandlers.listIterator();
		
		// list (a):
		allActionHandlers = new ArrayList<ActionHandler>();
		allHandlersIterator = allActionHandlers.listIterator();
		

	}

	/**
	 * The method returns the only instance of the ActionMaster.
	 * 
	 * @return singleton object of ActionMaster
	 */
	public static ActionMaster getInstance() {
		if (instance == null) instance = new ActionMaster();
		return instance;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while (isRunning()) {
			
			if (executeAllowed) 
				sleepTime = executeAction();
			else
				System.out.println("ActionMaster run(): execute not allowed");

			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}
	
	/**
	 * The method lets "the next" action handler execute its best action.
	 * "The next" action handler is:
	 *  - the next action handler in the continue list (list (c)) 
	 *  - if list (c) is iterated then the next action handler is the next in the priority list of reported handlers (list (b))
	 *  - if list (c) and list (b) are iterated the next handler is find in list (a) 
	 *  
	 *  According to the return code of the action handler that executes its best rated action,
	 *   the action master changes its lists.
	 *  If an action is executed completely the action handler is removed from continue list.
	 *  If no action is executed by an action handler the action handler is removed from continue list.
	 *  If an action must be continued a reported action handler is moved to the continue list.
	 *  There needn't be a remove from priority list for reported action handlers because that list is iterated only one time.
	 *  
	 */
	private int executeAction() {
		
		int returnSleepTime = ACTIONMASTER_RETURN_SLEEP_ZERO;
		
		ActionHandler handler = null;
		boolean handlerFromPriorityList = false;
		boolean handlerFromContinueList = false;


		// if there is a further action handler in continue list this action handler is chosen to execute its action.
		if (continueHandlersIterator.hasNext()) {
			handler = continueHandlersIterator.next();
			handlerFromContinueList = true;
		}
		else {
			
			synchronized (handlersIterator) {
			
				// if there is a further action handler in priority list this action handler is chosen to execute its action.
				if (handlersIterator.hasNext()) {
					handler = handlersIterator.next();
					handlerFromPriorityList = true;
				}
				
			}
		
			if (!handlerFromPriorityList) {
				// if continue and priority lists are iterated the action handler is chosen from all action handler list.
				
				if (isBlockedByAddingActionHandler) {
					return ACTIONMASTER_RETURN_SLEEP_LESS;
				}
				
				if (allHandlersIterator.nextIndex() == allActionHandlers.size()) 
					allHandlersIterator = allActionHandlers.listIterator();
				
				if (allHandlersIterator.hasNext())
					handler = allHandlersIterator.next();
				else
					return ACTIONMASTER_RETURN_SLEEP_MUCH;
			}

		}
		
		//  According to the return code of the action handler that executes its best rated action,
		//   the action master changes its lists.
		switch (handler.doActualAction(secondOfTheActualMinute)) {
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONDONE:
			if (handlerFromContinueList) {
				handler.removeFromContinueIterator();
				//continueHandlersIterator.remove();
			}
			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONISGOINGON:
			if (handlerFromPriorityList) {
				//continueActionHandlers.add(handler);
				executeAllowed = false;
				continueHandlersIterator.add(handler);
				executeAllowed = true;
				//continueHandlersIterator  = continueActionHandlers.listIterator();
			}
			// because of iterating the priority list, there mustn't be reseted the continue list iterator
			// It will be reseted if the next iteration process of continue list starts
			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_NOACTION:
			if (handlerFromContinueList) {
				handler.removeFromContinueIterator();
				//continueHandlersIterator.remove();
			}
			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONYETEXECUTED:
			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		}
		
		return returnSleepTime;
		
	}
	
	/**
	 * The method inserts an action handler into the priority list for reported action handlers.
	 * The reported action handler is accepted only if its execution time isn't too far in the future.
	 * According to its time it is inserted into the list for the associated second list.
	 * It is inserted at index that depends on the action's priority.
	 * 
	 * @param handler
	 * @param timeInMilliseconds
	 * @param priority
	 */
	public int reportBetterAction(
			ActionHandler handler, long timeInMilliseconds, int priority) {

		int indexPrio;
		int iteratorIndex;
		byte second;
		long waitMilliseconds;
		long waitSeconds;
		
		//translate the waiting time (till execution) to seconds
		waitMilliseconds = timeInMilliseconds - nowInMilliseconds;
		waitSeconds = waitMilliseconds / 1000;
		
		if (waitSeconds < 0) {
			System.out.println("ActionMaster.reportBetterAction(): waitSeconds < 0: now: " + nowInMilliseconds + " time: " + timeInMilliseconds);
			waitSeconds = 0;
		}
		else
			System.out.println("ActionMaster.reportBetterAction(): waitSeconds :" + waitSeconds);
			
		
		if ( waitSeconds >= ActionHandler.MAX_ACTION_WAIT_SECONDS) {
			//reject the action handler element
			// because there are more than 60 seconds till the action is executed
			return ACTIONMASTER_RETURN_REJECT_TOOMUCHWAIT;
		}
		
		// with the help of the two dimensional int array
		// (that holds the starting indexes for every second and every priority)
		// the insert position into the priority list for reported action handlers is determined
		second = (byte) ((secondOfTheActualMinute + (byte) waitSeconds ) % ActionHandler.MAX_ACTION_WAIT_SECONDS);
		indexPrio = indexBySecondAndPriority[second][priority];

		
		// list of action handlers for the calculated second
		List<ActionHandler> actionHandlersAtSecond = reportedActionHandlers.get(second);
		
		// insert the reported action handler at the determined position
		if (actionHandlersAtSecond == this.actionHandlersNow)
		{
			
			executeAllowed = false;

			iteratorIndex = handlersIterator.nextIndex();
		
			actionHandlersNow.add(indexPrio, handler);

			if (indexPrio <= iteratorIndex)
				handlersIterator = actionHandlersNow.listIterator(indexPrio);
			else
				handlersIterator = actionHandlersNow.listIterator(iteratorIndex);
			
			executeAllowed = true;

		}
		else
			actionHandlersAtSecond.add(indexPrio, handler);
		
		

		// because an element has been inserted other elements' index has to be increased
		// (only the elements that are listed behind the inserted one, that are the elements with lower priority)
		for (int i = minPriorityBySecond[second]; i < priority; i++) {
			indexBySecondAndPriority[second][i]++;
		}
		
		// if there is a lower priority than before then change the start value for index shifting after insertion
		if (priority < minPriorityBySecond[second]) minPriorityBySecond[second] = priority;
		
		return ACTIONMASTER_RETURN_INSERTED;
	}
	
	/**
	 * The method appends an action handler element to the end of the all action handlers list.
	 * 
	 * @param handler
	 */
	public void addActionHandler(ActionHandler handler) {
		int nextIndex;
		isBlockedByAddingActionHandler = true;
		nextIndex = allHandlersIterator.nextIndex();
		allActionHandlers.add(handler);
		allHandlersIterator = allActionHandlers.listIterator(nextIndex);
		isBlockedByAddingActionHandler = false;
	}
	
	/**
	 * The method prepares the action master for the next time step.
	 * 	- if the iterator for the continue list points to the end the pointer is set to the list's start
	 *  - sets the new second of the actual minute 
 	 *  - sets the actual priority list for reported action handlers and resets the list iterator
	 *  
	 */
	public void nextSecond(Time time) {
		
		// reset the start value (for index shifting after insertion) for the last second
		minPriorityBySecond[secondOfTheActualMinute] = AbstractAction.MAX_ACTION_PRIORITY;

		// if the iterator for the continue list points to the end the pointer is set to the list's start
		if (continueHandlersIterator.nextIndex() == continueActionHandlers.size() ) {
			clearContinueIterator();
			continueHandlersIterator = continueActionHandlers.listIterator();
		}
		
		nowInMilliseconds = time.getTotalMilliseconds();
		secondOfTheActualMinute = time.getSecond();
		
		actionHandlersNow = reportedActionHandlers.get(secondOfTheActualMinute);
		handlersIterator = actionHandlersNow.listIterator();

		
		//TODO (MatWorsoc) there must be a call to "all" ActionHandler.reset() somewhere 
		// if  the counting of seconds starts with 0

	}
	
	private void clearContinueIterator() {
		ActionHandler handler;
		for (continueHandlersIterator = continueActionHandlers.listIterator(); continueHandlersIterator.hasNext();) {
			handler = continueHandlersIterator.next();
		    if (handler.isToBeRemovedFromContinueIterator()) {
		    	continueHandlersIterator.remove();
		    	handler.dontRemoveFromContinueIterator();
		    }
		}
	}
	
}
