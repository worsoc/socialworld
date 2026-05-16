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

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

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
	
	private enum ActionHandlerListType {
		continueList,
		priorityList,
		allList
	}
	
	// a reported action handler is rejected because its best action element's execution time is too far in the future
	public static final int ACTIONMASTER_RETURN_REJECT_TOOMUCHWAIT = 1;
	// a reported action handler element has been inserted into the priority list (list (b))
	public static final int ACTIONMASTER_RETURN_INSERTED = 0;

	private boolean executeAllowed = true;
	
	
	private static ActionMaster instance;
	
	// REAKTIV: Eine Queue für neu anzumeldende Handler von außen (ersetzt das blockierende Flag)
    private final ConcurrentLinkedQueue<ActionHandler> newHandlersQueue = new ConcurrentLinkedQueue<>();
	
	// queue for action handlers that execute an action that must be continued (list (c))
	// it is iterated from first to last element and starts at the first element again (while elements remain in the list)
    // Erlaubt blitzschnelles Einfügen hinten und Entnehmen vorne ohne Sperren
    private final ConcurrentLinkedQueue<ActionHandler> continueActionHandlers = new ConcurrentLinkedQueue<>();

	// priority list for reported action handlers (list (b))
	// it is iterated one time from first to last element
	// after a complete iteration the list is cleared and will be filled with new reported action handler elements
    // Definition als festes Array von ConcurrentLinkedQueues
    private final ConcurrentLinkedQueue<ActionHandler>[][] reportedActionHandlers;
	// Ein einfaches boolean-Array für jede Sekunde: true = es gibt mindestens einen ActionHandler
	private final boolean[] secondHasActions = new boolean[ActionHandler.MAX_ACTION_WAIT_SECONDS];

	// queue for all action handlers  (list (a))
	// it is iterated from first to last element and starts at the first element again 	
    private final List<ActionHandler> allActionHandlers = new CopyOnWriteArrayList<>();
    private int currentAllHandlersIndex = 0;

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
	@SuppressWarnings("unchecked")
	private ActionMaster() {
		
	
	    int maxSeconds = ActionHandler.MAX_ACTION_WAIT_SECONDS;
	    int maxPriorities = AbstractAction.MAX_ACTION_PRIORITY; 

		// list (b):
	    // Initialisierung des Arrays mit der festen Sekunden-Größe
	    reportedActionHandlers = new ConcurrentLinkedQueue[maxSeconds][maxPriorities];
	    
	    for (int s = 0; s < maxSeconds; s++) {
	    	secondHasActions[s] = false; 
	        for (int p = 0; p < maxPriorities; p++) {
	            reportedActionHandlers[s][p] = new ConcurrentLinkedQueue<ActionHandler>();
	        }
	    }
		
	}

	/**
	 * The method returns the only instance of the ActionMaster.
	 * 
	 * @return singleton object of ActionMaster
	 */
	public static synchronized ActionMaster getInstance() {
		if (instance == null) instance = new ActionMaster();
		return instance;
	}
	
	
	  /**
     * Reaktiver Thread-Loop 
     */
    @Override
    public void run() {
        while (isRunning()) {
            if (executeAllowed) {
                // Verarbeite wartende Registrierungen direkt atomar
                processIncomingHandlers();
                
                // Führe die nächste anstehende Aktion aus
                executeAction();
            } else {
                // Falls pausiert, geben wir freiwillig ab
                Thread.yield();
            }
        }
    }
	
	
	
	   /**
     * Verarbeitet asynchron hinzugefügte Handler aus der Queue
     */
    private void processIncomingHandlers() {
        ActionHandler newHandler;
        while ((newHandler = newHandlersQueue.poll()) != null) {
            if (!allActionHandlers.contains(newHandler)) {
                allActionHandlers.add(newHandler);
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
	private void executeAction() {
		
		
		ActionHandler handler = null;
		
        // Prio 1: Continue-Liste (Liste c) - Lock-freie Iteration
        if (!continueActionHandlers.isEmpty()) {
            handler = continueActionHandlers.poll(); 
            executeHandler(handler, ActionHandlerListType.continueList);
            return ;
        }
		
        // Prio 2: Reported-Liste (Liste b)
        if (secondHasActions[secondOfTheActualMinute]) {
	        ConcurrentLinkedQueue<ActionHandler> actionHandlersNow = getFromSecondPriorityMatrix((byte)secondOfTheActualMinute, 0);
	        if (!actionHandlersNow.isEmpty()) {
	            handler = actionHandlersNow.poll();
	            executeHandler(handler, ActionHandlerListType.priorityList);
	            return;
	        }
        }
        
	    // Prio 3: Alle Handler (Liste a) - Index-basiert und resilient gegen Größenänderungen
        if (!allActionHandlers.isEmpty()) {
            if (currentAllHandlersIndex >= allActionHandlers.size()) {
                currentAllHandlersIndex = 0; // Ring-Reset
            }
            handler = allActionHandlers.get(currentAllHandlersIndex++);
            executeHandler(handler, ActionHandlerListType.allList);
            return;
        }
		
		return;
		
	}
	
	private void executeHandler(ActionHandler handler, ActionHandlerListType type) {
		
		
		//  According to the return code of the action handler that executes its best rated action,
		//   the action master changes its lists.
		switch (handler.doActualAction(secondOfTheActualMinute)) {
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONDONE:
			//  nothing to do, because handler element has already been removed (from continue or priority list)
			//			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONISGOINGON:
			if (type == ActionHandlerListType.priorityList || 
					type == ActionHandlerListType.continueList) {
				// it has already been removed from priority or continue list
				// handler must be added to continue list !
	             continueActionHandlers.add(handler);
			}
//			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_NOACTION:
			//  nothing to do, because handler element has already been removed (from continue or priority list)
//			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONYETEXECUTED:
//			returnSleepTime = ACTIONMASTER_RETURN_SLEEP_LESS;
			break;
		}

		return;
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

		
		
		// insert the reported action handler at the determined position
		if (second == this.secondOfTheActualMinute)
		{
			executeAllowed = false;
			addHandlerToPSecondriorityMatrix(second,priority, handler);
			executeAllowed = true;
		}
		else {
			addHandlerToPSecondriorityMatrix(second,priority, handler);
		}

	    // Flag für diese Sekunde atomar/sicher auf true setzen (Primitives Schreiben ist thread-safe)
	    secondHasActions[second] = true; 

		
		return ACTIONMASTER_RETURN_INSERTED;
	}
	
	/**
	 * The method appends an action handler element to the end of the all action handlers list.
	 * 
	 * @param handler
	 */
	public void addActionHandler(ActionHandler handler) {
	       if (handler != null) {
	            newHandlersQueue.offer(handler);
	        }
	}
	
	/**
	 * The method prepares the action master for the next time step.
	 * 	- if the iterator for the continue list points to the end the pointer is set to the list's start
	 *  - sets the new second of the actual minute 
 	 *  - sets the actual priority list for reported action handlers and resets the list iterator
	 *  
	 */
	public void nextSecond(Time time) {
		

		nowInMilliseconds = time.getTotalMilliseconds();
		secondOfTheActualMinute = time.getSecond();
		
		
		//TODO (MatWorsoc) there must be a call to "all" ActionHandler.reset() somewhere 
		// if  the counting of seconds starts with 0

	}
	
	private ConcurrentLinkedQueue<ActionHandler> getFromSecondPriorityMatrix(byte second, int priority) {
	
		 if (second >= 0 && second < ActionHandler.MAX_ACTION_WAIT_SECONDS 
			        && priority >= 0 && priority <= AbstractAction.MAX_ACTION_PRIORITY) {

			int indexPrio = AbstractAction.MAX_ACTION_PRIORITY - priority ;
			return reportedActionHandlers[second][indexPrio];
			
		 }
		 else
			 return new ConcurrentLinkedQueue<ActionHandler>();
		 
	}
	
	private void addHandlerToPSecondriorityMatrix(byte second, int priority, ActionHandler handler) {
		int indexPrio = AbstractAction.MAX_ACTION_PRIORITY - priority ;
		reportedActionHandlers[second][indexPrio].add(handler);
	}
	
}
