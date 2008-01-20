/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

/**
 * Manages the actions of an {@link SimulationObject}.
 * 
 * @author Mathias Sikos
 */
public class ActionHandler extends Thread {

	protected SimulationObject object;
	protected List<Action> actionQueue;
	protected Action actualAction;

	public ActionHandler(final SimulationObject simulationObject) {
		this.actualAction = null;
		this.actionQueue = new ArrayList<Action>();
		this.object = simulationObject;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			// TODO ActionQueue abarbeiten.
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
	 * object do the action.
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
	 * The methods creates a new action element and inserts it into the action
	 * list.
	 */
	public void newAction(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Direction direction,
			final double intensity, final Time minTime, final Time maxTime,
			final int priority, final double duration) {
		// FIXME tyloesand: Es ist wohl besser, das Aktionselement au�erhalb zu
		// f�llen
		// und dann nur das neue Aktionselement zu �bergeben
		// dann reicht insertAction und die Funktion newAction ist �berfl�ssig
		// Diskussionsbedarf
		
		// Seh ich genauso!!! (circlesmiler)

		Action action;
		action = new Action();

		action.setType(type);
		action.setMode(mode);
		action.setTarget(target);
		action.setDirection(direction);
		action.setIntensity(intensity);
		action.setMinTime(minTime);
		action.setMaxTime(maxTime);
		action.setPriority(priority);
		action.setDuration(duration);

		insertAction(action);
	}

	/**
	 * The methods search for an action element that meets the properties of an
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

// /****************************************************************************
// * CLASS DECLARATION - PSBWS_Actioner *
// ****************************************************************************/
// class PSBWS_Actioner
// {
// public:
//
// /************* CONSTRUCTOR / DESTRUCTOR ************************************/
// PSBWS_Actioner(PSBWS_Object_Manager*, TONR, PSBWS_ActionCoder*
// aP_actionCoder = 0);
// virtual ~PSBWS_Actioner();
//
// /************* ACTION HANDLING *********************************************/
// virtual void do_action() = 0;
// void do_actualAction();
// void insert_action(STR_ActionElement*);
// void new_action(TNatural, TNatural, TUChar, unsigned short, TNatural);
// void change_action(STR_ActionElement*, TNatural,TNatural,
// TUChar, unsigned short, TNatural);
// PSBWS_ActionCoder* get_actionCoder();
//  
// protected:
//
// /************* INITIALIZATION *********************************************/
// PSBWS_ActionQueue* mP_actions;
// TONR mu_ONr;
// PSBWS_Object_Manager* mP_objectManager;
// STR_ActionElement* mT_actualAction;
// PSBWS_ActionCoder* mP_actionCoder;
// };

// PSBWS_Actioner::PSBWS_Actioner(PSBWS_Object_Manager*
// aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
// {
// mP_objectManager = aP_objectManager;
// mu_ONr = au_ONr;
// mT_actualAction = 0;
// mP_actionCoder = aP_actionCoder;
// }
//
// //////////////////////////////////////////////////////////////////////////////
//
// PSBWS_Actioner::~PSBWS_Actioner()
// {
// }
//
// //////////////////////////////////////////////////////////////////////////////
//
// PSBWS_ActionCoder* PSBWS_Actioner::get_actionCoder()
// {
// return mP_actionCoder;
// }
//
// //////////////////////////////////////////////////////////////////////////////
//
// void PSBWS_Actioner::do_actualAction()
// {
// STR_ActionElement* lT_actionTmp = mP_actions->get_element();
// if (mT_actualAction->u_duration_remained == 0) // Aktion abgelaufen ?
// mT_actualAction = lT_actionTmp; // dann in Liste weiter zur n�chsten Aktion
// else // sonst, wenn priorisierte Aktion zu diesem
// { // Zeitpunkt ebenfalls dran ist
// if (mT_actualAction->u_priority < lT_actionTmp->u_priority)
// mT_actualAction = lT_actionTmp;
// } // Rest der alten Aktion wird danach ausgef�hrt
// if (mT_actualAction)
// {
// mT_actualAction->u_duration_remained -= 1; // Zaehler der (nun) aktuellen
// Aktion -1
// do_action();
// }
// } // verbleibende Zeit wird kleiner
// // -> Aktion wird abgearbeitet
// //////////////////////////////////////////////////////////////////////////////
//
// void PSBWS_Actioner::new_action(TNatural au_code, TNatural au_time,
// TUChar au_priority, unsigned short au_duration, TNatural au_valid)
// {
// STR_ActionElement* lT_newElement;
// lT_newElement = new STR_ActionElement; // dann ein neues erstellen
// lT_newElement->u_actionCode = au_code; // �bergebene Werte an
// entsprechender
// lT_newElement->u_time_min = au_time; // Stelle eintagen
// lT_newElement->u_time_max = au_time + au_valid;
// lT_newElement->u_priority = au_priority; // Dauer und Restzeit bei neuen El.
// gleich
// lT_newElement->u_duration = lT_newElement->u_duration_remained = au_duration;
// insert_action(lT_newElement); // erstelltes Element einfuegen
// }
//
// //////////////////////////////////////////////////////////////////////////////
//
// void PSBWS_Actioner::insert_action(STR_ActionElement *aT_newElement)
// {
// TUChar lu_priority = aT_newElement->u_priority;
// TNatural lu_time = aT_newElement->u_time_min;
//
// mP_actions->insert(lu_time, lu_priority, aT_newElement);
// }
//
// //////////////////////////////////////////////////////////////////////////////
//
// void PSBWS_Actioner::change_action(STR_ActionElement *aT_actionElement,
// TNatural au_actionCode,
// TNatural au_time, TUChar au_priority, unsigned short au_duration, TNatural
// au_valid)
// {
// aT_actionElement->u_actionCode = au_actionCode;
// aT_actionElement->u_time_min = au_time;
// aT_actionElement->u_time_max = au_time + au_valid;
// aT_actionElement->u_priority = au_priority;
// aT_actionElement->u_duration = aT_actionElement->u_duration_remained =
// au_duration;
// }
//
// //////////////////////////////////////////////////////////////////////////////
