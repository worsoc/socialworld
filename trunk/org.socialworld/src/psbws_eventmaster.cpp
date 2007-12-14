/***************************************************************************
                          psbws_eventmaster.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_eventmaster.h"

#include "psbws_objekt_manager.h"

#include "psbws_simulation.h"
#include "psbws_gott.h"
#include "psbws_simulationsobjekt.h"
#include "psbws_position.h"

#include "psbws_event.h"

#include "sm_hashtabelle.cpp"
#include "sm_prioritaetsschlangenelement.cpp"

#include "sm_vektormathematik.h"
#include <math.h>

#define FEHLVERSUCH_DIVISOR   250   // lu_ONr_max / FEHLVERSUCH_DIVISOR = Anzahl Fehlversuche bei rand() in ErstelleEreignisSchlange()

PSBWS_EventMaster::PSBWS_EventMaster(PSBWS_Object_Manager* aP_objectManager, TNatural au_minNumber_events)
{
	mP_objectManager = aP_objectManager;
  mSM_vector = new SM_VektorMathematik();
  mu_minNumber_events = au_minNumber_events;
	mSM_events = new SM_PrioritaetsSchlange<PSBWS_Event>();
	mSM_candidates = new SM_Schlange<STR_Candidate>();

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

PSBWS_EventMaster::~PSBWS_EventMaster()
{
			delete mSM_events;
			delete mSM_candidates;
      delete mSM_vector;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

PSBWS_Event*	PSBWS_EventMaster::create_event(TEVC au_eventCode, long int ai_op1, int ai_op2, int ai_op3, int ai_op4,
                 int ai_op5, TONR au_ONr, TUChar au_strength, int ai_relX, int ai_relY, int ai_relZ, float af_dirX,
                 float af_dirY, float af_dirZ, TNatural au_effectDistance, TUChar au_effectAngle)
{
	PSBWS_Event* lP_result;
	lP_result = new PSBWS_Event;
	lP_result->mu_eventCode = au_eventCode;
	lP_result->operand1 = ai_op1;
	lP_result->operand2 = ai_op2;
	lP_result->operand3 = ai_op3;
	lP_result->operand4 = ai_op4;
	lP_result->operand5 = ai_op5;
	lP_result->mu_causer = au_ONr;
	lP_result->mu_strength = au_strength;
	lP_result->mu_time = mP_objectManager->get_god()->get_time();
	lP_result->mua_position[0] = mP_objectManager->get_position(au_ONr)->get_x() + ai_relX;
	lP_result->mua_position[1] = mP_objectManager->get_position(au_ONr)->get_y() + ai_relY;
	lP_result->mua_position[2] = mP_objectManager->get_position(au_ONr)->get_z() + ai_relZ;
  lP_result->mfa_direction[0] = af_dirX;
  lP_result->mfa_direction[1] = af_dirY;
  lP_result->mfa_direction[2] = af_dirZ;
  lP_result->mu_effectDistance = au_effectDistance;
  lP_result->mu_effectAngle = au_effectAngle;
	if ((au_eventCode & 0xF0) == 1 )	    // nur zum Test, eignetlich 128
	{
		lP_result->mu_priority = 1;
		lP_result->mu_maxDistance = 100000;
		lP_result->mu_maxHear = 5000;
		lP_result->mu_maxSee = 100000;
		lP_result->mu_maxSmell = 0;
	}
	return lP_result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

int PSBWS_EventMaster::insert_event(PSBWS_Event* aP_event)		// Zeiger auf das Ereignis wird eingetragen
{													// MAXEvents... Anzahl Feldeinträge pro Zeiteinheit
  int li_result = mSM_events->insert(aP_event->mu_priority, aP_event);
  if (li_result >= 0)
     mu_number_events++;
  return li_result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

int PSBWS_EventMaster::determine_influence()
{
  TONR lu_ONr;
  STR_Candidate* lstr_candidate;
	int li_result2;
	int li_result = 0;
  lstr_candidate = mSM_candidates->remove();
  while (lstr_candidate != NULL)
  {
        lu_ONr = lstr_candidate->u_ONr;
		    li_result2 = mP_objectManager->get_object(lu_ONr)->determine_influence(mP_event);
        // bleibt solange objects keine funktionalitaet
//        result2 = mP_objectManager->simulation()->humans->getElement(i)->determine_influence(mP_event);
				if (li_result2 < 0)
						li_result++;
				lstr_candidate = mSM_candidates->remove();
  }
	return li_result;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////

bool PSBWS_EventMaster::decide_time_remains()
{
	return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

int PSBWS_EventMaster::handle_events()
{
  int li_result = 0;
	int li_result2 = 0;
  while (decide_time_remains())
  {
    if ((mP_event = mSM_events->remove()))
    {
	      li_result2 = determine_candidates();
	      li_result2 += determine_influence();
    }
    else
      break;                                           // Schleife abbrechen
		if (li_result2)
				li_result++;
  }
	return li_result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

// TODO: umstellen auf alle objekte, auf die Ereignis WIRKT
// Konstante Pi einsetzen
int PSBWS_EventMaster::determine_candidates()							// ermittelt objekte, die von Ereignis betroffen sind
{
  int li_result = 0;
	int li_result2;
  STR_Candidate* lstr_candidate;
  TONR lu_maxONr, lu_ONr;
  mu_posX_event = mP_event->mua_position[1];                       // Hilfswerte
  mu_posY_event = mP_event->mua_position[2];
  mu_posZ_event = mP_event->mua_position[3];
  mf_dirX_event = mP_event->mfa_direction[1];
  mf_dirY_event = mP_event->mfa_direction[2];
  mf_dirZ_event = mP_event->mfa_direction[3];
  mu_distance_effect = mP_event->mu_effectDistance;
  md_angleTangens_effect = tan(mP_event->mu_effectAngle * 3.14 / 180);
  lu_maxONr = mP_objectManager->get_god()->get_maxONr();
  for (lu_ONr=1 ; lu_ONr <= lu_maxONr ; lu_ONr++)                  // alle Objekte durchgehen
  {
    if (decide_effective(lu_ONr))
    {
        lstr_candidate = new STR_Candidate;
				lstr_candidate->u_ONr = lu_ONr;
				li_result2 = mSM_candidates->insert(lstr_candidate);
				if (li_result2 < 0)
						li_result++;
    }
  }
	return li_result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

bool PSBWS_EventMaster::decide_effective(TONR uONr)
{

    PSBWS_Position* lP_position;
    lP_position = mP_objectManager->get_object(uONr)->get_position();
    long int ll_x,ll_y,ll_z;

    ll_x = lP_position->get_x() - mu_posX_event;
    ll_y = lP_position->get_y() - mu_posY_event;
    ll_z = lP_position->get_z() - mu_posZ_event;

    TNatural lu_distance = (TNatural) mSM_vector->vectorLength(ll_x , ll_y , ll_z);

    if (lu_distance <= mu_distance_effect)
    {
        double ld_angle = mSM_vector->angleTangens(ll_x,ll_y,ll_z, mf_dirX_event, mf_dirY_event, mf_dirZ_event);
        return (ld_angle <= md_angleTangens_effect);
    }
    return false;
}


int PSBWS_EventMaster::fill_eventQueue()
{
  TONR lu_ONr_max = mP_objectManager->get_god()->get_maxONr();
  bool lb_priority_events_inserted = false;											// alle durchgegangen ?
  TUChar lu_priority = 2;											// Priority der Ereignisse, priorisierte zuerst
  TONR lu_ONr = 0 ;
  unsigned int lu_j = 0;
	int li_result, li_result2;
  li_result = 0;
	li_result2 = 0;
	PSBWS_SimulationObject* lP_object;

  do
  {
    if (lb_priority_events_inserted) 											// alle Ereignisse mit Priorität eingetragen ?
    {
      lu_ONr = rand();			// !!! Bereich festlegen							// zufällig eine Person ->  ein von ihr
      lP_object = mP_objectManager->get_object(lu_ONr);
      if (lP_object != NULL)
      {
					mP_event = lP_object->get_event();
		      if (mP_event != 0 )                               // "unwichtiges" ausgeloestes Ereignis
					{
							li_result2 = insert_event(mP_event);                 // eintragen
							lP_object->delete_event();
					}
		      else lu_j++;                                 // zähle Fehlversuche für Abbruchbedingung
      }
			else lu_j++;


// zum abbrechen ( muss wieder entfernt werden !)
    lu_j = lu_ONr_max;
//
    }
    else
	  {														// aber zuerst alle priorisierten ereignisse
      lP_object = mP_objectManager->get_object(lu_ONr);
			if (lP_object != NULL)
			{
				  mP_event = lP_object->get_event();								// hat die i-te Person
  		    if (mP_event != 0)                                      // ein Ereignis ausgelöst ?
		      {
			        lu_priority = mP_event->mu_priority;                        // dann Priorität auslesen
			        if (lu_priority)                                        // wenn priorisiert
			        {
								  li_result2 = insert_event(mP_event);			//  Nach EINTRAGEN noch Platz ?
				          lP_object->delete_event();						// Ereignisauslöschung bei der Person
			        }												        // löschen, um nicht nochmal einzutragen
			    }
      }
      if ( lu_ONr == lu_ONr_max)                                    // wenn alle Personen durch
			{
					 lu_priority = 0;
					 lb_priority_events_inserted = true;
			}
    	lu_ONr++;                                                      // Personalnummer erhöhen
    }														    // dann Priorität 0 für Ende der Schleife
    if (li_result2 < 0)
				li_result++;
  }															    // priorisierte Ereignisse WERDEN eingetragen !
  while ( ((mu_number_events < mu_minNumber_events) || lu_priority) && ( lu_j < lu_ONr_max  ));  //  / FEHLVERSUCH_DIVISOR
	return li_result;
}
