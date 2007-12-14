// PSBWS_Objekt_Manager.cpp: Implementierung der Klasse PSBWS_Objekt_Manager.
//
//////////////////////////////////////////////////////////////////////
#ifndef _PSBWS_OBJEKT_MANAGER_CPP
#define _PSBWS_OBJEKT_MANAGER_CPP

#include "PSBWS_Objekt_Manager.h"
#include "psbws_simulation.h"
#include "PSBWS_Auswirkung_API_Manager.h"
#include "psbws_reaktion_api_manager.h"
#include "psbws_aktionscode.h"

#include "sm_hashtabelle.cpp"

PSBWS_Object_Manager::PSBWS_Object_Manager()
{
	
  mP_simulation = new PSBWS_Simulation(this);
  mP_manager_effectAPI = new PSBWS_Effect_API_Manager();
  mP_manager_reactionAPI = new PSBWS_Reaction_API_Manager();
  mP_actionCoder_human = new PSBWS_ActionCoder_Human();

}

//////////////////////////////////////////////////////////////////////

PSBWS_Object_Manager::~PSBWS_Object_Manager()
{
	delete mP_manager_reactionAPI;
	delete mP_manager_effectAPI;
	delete mP_simulation;
  delete mP_actionCoder_human;
}

//////////////////////////////////////////////////////////////////////

PSBWS_Simulation*	PSBWS_Object_Manager::get_simulation()
{
	return mP_simulation;
}

//////////////////////////////////////////////////////////////////////

PSBWS_Effect_API_Manager*   PSBWS_Object_Manager::get_manager_effectAPI()
{
	return mP_manager_effectAPI;
}

//////////////////////////////////////////////////////////////////////

PSBWS_Reaction_API_Manager*   PSBWS_Object_Manager::get_manager_reactionAPI()
{
	return mP_manager_reactionAPI;
}

//////////////////////////////////////////////////////////////////////

PSBWS_SimulationObject*	PSBWS_Object_Manager::get_object(TONR au_ONr)
{
	return mP_simulation->objects->getElement(au_ONr);
}

//////////////////////////////////////////////////////////////////////

PSBWS_Position*	PSBWS_Object_Manager::get_position(TONR au_ONr)
{
	return mP_simulation->positions->getElement(au_ONr);
}


//////////////////////////////////////////////////////////////////////

PSBWS_Human*	PSBWS_Object_Manager::get_human(TPNR au_PNr)
{
	return mP_simulation->humans->getElement(au_PNr);
}

//////////////////////////////////////////////////////////////////////

PSBWS_Item*	PSBWS_Object_Manager::get_item(TGNR au_INr)
{
	return mP_simulation->items->getElement(au_INr);
}

//////////////////////////////////////////////////////////////////////

PSBWS_Magic*	PSBWS_Object_Manager::get_magic(TPositive au_spellNr)
{
	return mP_simulation->magics->getElement(au_spellNr);
}

//////////////////////////////////////////////////////////////////////

PSBWS_God*	PSBWS_Object_Manager::get_god()
{
	return mP_simulation->god;
}

//////////////////////////////////////////////////////////////////////

PSBWS_EventMaster*	PSBWS_Object_Manager::get_eventMaster()
{
	return mP_simulation->eventMaster;
}

//////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Human*	PSBWS_Object_Manager::get_actionCoder_human()
{
	return mP_actionCoder_human;
}

#endif
