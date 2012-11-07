package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.socialworld.attributes.ActualTime;



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
 * @author Mathias Sikos (tyloesand)
 */
public class ActionMaster {
	
	// a reported action handler is rejected because its best action element's execution time is too far in the future
	public static final int ACTIONMASTER_RETURN_REJECT_TOOMUCHWAIT = 1;
	// a reported action handler element has been inserted into the priority list (list (b))
	public static final int ACTIONMASTER_RETURN_INSERTED = 0;

	private static ActionMaster instance;
	
	// queue for action handlers that execute an action that must be continued (list (c))
	// it is iterated from first to last element and starts at the first element again (while elements remain in the list)
	private List<ActionHandler> continueActionHandlers;
	private  ListIterator<ActionHandler> continueHandlersIterator;

	// priority list for reported action handlers (list (b))
	// it is iterated one time from first to last element
	// after a complete iteration the list is cleared an will be filled by new reported action handler elements
	private List<List<ActionHandler>> reportedActionHandlers;
	private int[][] indexBySecondAndPiority;
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
	 *  Furthermore the is a two dimensional array that holds the indexes for the second an the priority.
	 *  So a fast insertion of a new reported action handler is possible.
	 *  
	 *  For every list there is a list iterator.
	 */
	private ActionMaster() {
		// list (b):
		reportedActionHandlers = new ArrayList<List<ActionHandler>>();
		for (int i = 1; i < Action.MAX_ACTION_WAIT_SECONDS; i++) {
			reportedActionHandlers.add(new ArrayList<ActionHandler>());
		}
		
		indexBySecondAndPiority = new int[Action.MAX_ACTION_WAIT_SECONDS][Action.MAX_ACTION_PRIORITY];
		minPriorityBySecond = new int[Action.MAX_ACTION_WAIT_SECONDS];

		for (int i = 1; i < Action.MAX_ACTION_WAIT_SECONDS; i++) {
			minPriorityBySecond[i] = Action.MAX_ACTION_PRIORITY;
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
	 * The method gets back the only instance of the ActionMaster.
	 * 
	 * @return singleton object of ActionMaster
	 */
	public static ActionMaster getInstance() {
		if (instance == null) instance = new ActionMaster();
		return instance;
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
	public void executeAction() {
		ActionHandler handler;
		boolean handlerFromPriorityList = false;
		boolean handlerFromContinueList = false;
		
		// if there is a further action handler in continue list this action handler is chosen to execute its action.
		if (continueHandlersIterator.hasNext()) {
			handler = continueHandlersIterator.next();
			handlerFromContinueList = true;
		}
		else
		// if there is a further action handler in priority list this action handler is chosen to execute its action.
			if (handlersIterator.hasNext()) {
				handler = handlersIterator.next();
				handlerFromPriorityList = true;
			}
			else {
		// if continue and priority lists are iterated the action handler is chosen from all action handler list.
				if (allHandlersIterator.nextIndex() == allActionHandlers.size()) 
					allHandlersIterator = allActionHandlers.listIterator();
				handler = allHandlersIterator.next();
			}
		
		
		//  According to the return code of the action handler that executes its best rated action,
		//   the action master changes its lists.
		switch (handler.doActualAction(secondOfTheActualMinute)) {
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONDONE:
			if (handlerFromContinueList)	continueHandlersIterator.remove();
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONISGOINGON:
			if (handlerFromPriorityList) continueHandlersIterator.add(handler);
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_NOACTION:
			if (handlerFromContinueList)	continueHandlersIterator.remove();
			break;
		case ActionHandler.ACTIONHANDLER_RETURN_ACTIONYETEXECUTED:
			break;
		}
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
		byte second;
		long waitMilliseconds;
		long waitSeconds;
		
		//translate the waiting time (till execution) to seconds
		waitMilliseconds = timeInMilliseconds - nowInMilliseconds;
		waitSeconds = waitMilliseconds / 1000;
		
		if ( waitSeconds >= Action.MAX_ACTION_WAIT_SECONDS) {
			//reject the action handler element
			// because there are more than 60 seconds till the action is executed
			return ACTIONMASTER_RETURN_REJECT_TOOMUCHWAIT;
		}
		
		// with the help of the two dimensional int array
		// (that holds the starting indexes for every second and every priority)
		// the insert position into the priority list for reported action handlers is determined
		second = (byte) (waitSeconds % Action.MAX_ACTION_WAIT_SECONDS);
		indexPrio = indexBySecondAndPiority[second][priority];
		
		// insert the reported action handler at the determined position
		reportedActionHandlers.get(second).add(indexPrio, handler);
		
		// because an element has been inserted other elements' index has to be increased
		// (only the elements that are listed behind the inserted one, that are the elements with lower priority)
		for (int i = minPriorityBySecond[second]; i < priority; i++) {
			indexBySecondAndPiority[second][i]++;
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
		allActionHandlers.add(handler);
	}
	
	/**
	 * The method prepares the action master for the next time step.
	 * 	- if the iterator for the continue list points to the end the pointer is set to the list's start
	 *  - sets the new second of the actual minute 
 	 *  - sets the actual priority list for reported action handlers and resets the list iterator
	 *  
	 */
	public void nextSecond() {
		ActualTime time;
		
		// reset the start value (for index shifting after insertion) for the last second
		minPriorityBySecond[secondOfTheActualMinute] = Action.MAX_ACTION_PRIORITY;

		// if the iterator for the continue list points to the end the pointer is set to the list's start
		if (continueHandlersIterator.nextIndex() == continueActionHandlers.size() )
			continueHandlersIterator = continueActionHandlers.listIterator();
		
		time = ActualTime.get();
		nowInMilliseconds = time.getTotalMilliseconds();
		secondOfTheActualMinute = time.getSecond();
		
		actionHandlersNow = reportedActionHandlers.get(secondOfTheActualMinute);
		handlersIterator = actionHandlersNow.listIterator();

	}
}
