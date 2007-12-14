// psbws_reaktion_api_manager.cpp: Implementierung der Klasse PSBWS_Reaktion_API_Manager.
//
//////////////////////////////////////////////////////////////////////

#include "psbws_reaktion_api_manager.h"
#include "psbws_reaktion_api_event_32.h"
#include "psbws_reaktion_api_manager.h"


PSBWS_Reaction_API_Manager::PSBWS_Reaction_API_Manager()
{
	mPa_API = new T_ptr_reaction_API[256];
	mPa_API[32] = new PSBWS_Reaction_API_Event_32();

}

//////////////////////////////////////////////////////////////////////

PSBWS_Reaction_API_Manager::~PSBWS_Reaction_API_Manager()
{

}

//////////////////////////////////////////////////////////////////////

void PSBWS_Reaction_API_Manager::
		calculate(TUChar au_event_type, PSBWS_SimulationObject* aP_object, TUChar au_reaction_type)
{
  // TODO: zum Test uEventTyp: 32, uReaktionsTyp 1
  au_event_type = 32;
  au_reaction_type = 1;
	mPa_API[au_event_type]->calculate(aP_object, au_reaction_type);
}

