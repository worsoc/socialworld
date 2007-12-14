// PSBWS_Auswirkung_API_Manager.cpp: Implementierung der Klasse PSBWS_Auswirkung_API_Manager.
//
//////////////////////////////////////////////////////////////////////

#include "psbws_auswirkung_api.h"
#include "psbws_auswirkung_api_event_32.h"
#include "PSBWS_Auswirkung_API_Manager.h"


PSBWS_Effect_API_Manager::PSBWS_Effect_API_Manager()
{

	mPa_API = new T_ptr_Effect_API[256];
	mPa_API[32] = new PSBWS_Effect_API_Event_32();
}

//////////////////////////////////////////////////////////////////////

PSBWS_Effect_API_Manager::~PSBWS_Effect_API_Manager()
{

}

//////////////////////////////////////////////////////////////////////

void PSBWS_Effect_API_Manager::
		calculate(TUChar au_event_type, PSBWS_SimulationObject* aP_object, TUChar au_effect_type) {
  // TODO: zum Test uEventTyp auf 1 und uAuswirkungsTyp auf 1
  au_event_type = 32;
  au_effect_type = 1;
	mPa_API[au_event_type]->calculate(aP_object, au_effect_type);
}
