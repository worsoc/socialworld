// psbws_reaktion_api_manager.h: Schnittstelle für die Klasse PSBWS_Reaktion_API_Manager.
//
//////////////////////////////////////////////////////////////////////

#ifndef _PSBWS_REAKTION_API_MANAGER_H__
#define _PSBWS_REAKTION_API_MANAGER_H__

#include "psbws_types.h"
#include "psbws_simulationsobjekt.h"

class PSBWS_Reaction_API;

typedef PSBWS_Reaction_API* T_ptr_reaction_API;


class PSBWS_Reaction_API_Manager  
{
public:
	PSBWS_Reaction_API_Manager();
	virtual ~PSBWS_Reaction_API_Manager();

	void calculate(TUChar, PSBWS_SimulationObject*, TUChar);

protected:
	T_ptr_reaction_API* mPa_API;

};

#endif

