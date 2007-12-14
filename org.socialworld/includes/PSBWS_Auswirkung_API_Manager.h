// PSBWS_Auswirkung_API_Manager.h: Schnittstelle für die Klasse PSBWS_Auswirkung_API_Manager.
//
//////////////////////////////////////////////////////////////////////

#ifndef _PSBWS_AUSWIRKUNG_API_MANAGER_H__
#define _PSBWS_AUSWIRKUNG_API_MANAGER_H__

#include "psbws_types.h"
#include "psbws_simulationsobjekt.h"

class PSBWS_Effect_API;

typedef PSBWS_Effect_API* T_ptr_Effect_API;

class PSBWS_Effect_API_Manager  
{
public:
	PSBWS_Effect_API_Manager();
	virtual ~PSBWS_Effect_API_Manager();

	void calculate(TUChar, PSBWS_SimulationObject* , TUChar);

protected:
	T_ptr_Effect_API* mPa_API;

};
#endif
