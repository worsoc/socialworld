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
 * @author Mathias Sikos
 */
public abstract class ActionHandler {

    protected SimulationObject object;
    protected List<Action> actionQueue;
    protected Action actualAction;

    public ActionHandler(SimulationObject simulationObject) {
	actualAction = null;
	actionQueue = new ArrayList<Action>();
    }

    /**
     * @return the actualAction
     */
    public Action getActualAction() {
	return actualAction;
    }

    /**
     * The method releases the event according to the action. So the action
     * effects on other simulation objects.
     */
    public abstract void doAction();

    /**
     * 
     * The method gets an action element from action list and calls the
     * implementation of the virtual method doAction.
     */
    public void doActualAction() {
	Action action;
	action = this.actionQueue.get(0);

	if (actualAction.getRemainedDuration() == 0)
	    actualAction = action;
	else if (actualAction.getPriority() < action.getPriority())
	    actualAction = action;

	if (actualAction == null) {
	    actualAction.lowerRemainedDuration(1);
	    doAction();
	}

    }

    /**
     * The method inserts an action element into the action list of a simulation
     * object.
     * 
     * @param action
     */
    public void insertAction(Action action) {
	actionQueue.add(action);
    }

    /**
     * The methods creates a new action element and inserts it into the action
     * list.
     */
    public void newAction() {
	Action action;
	action = new Action();

	insertAction(action);
    }

    /**
     * The methods changes an existing action element in the action list.
     * 
     * @param action
     */
    public void changeAction(Action action) {

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
// mT_actualAction = lT_actionTmp; // dann in Liste weiter zur nächsten Aktion
// else // sonst, wenn priorisierte Aktion zu diesem
// { // Zeitpunkt ebenfalls dran ist
// if (mT_actualAction->u_priority < lT_actionTmp->u_priority)
// mT_actualAction = lT_actionTmp;
// } // Rest der alten Aktion wird danach ausgeführt
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
// lT_newElement->u_actionCode = au_code; // übergebene Werte an entsprechender
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
