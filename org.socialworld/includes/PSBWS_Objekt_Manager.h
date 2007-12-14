// PSBWS_Objekt_Manager.h: Schnittstelle für die Klasse PSBWS_Objekt_Manager.
//
//////////////////////////////////////////////////////////////////////

#ifndef _PSBWS_OBJEKT_MANAGER_H
#define _PSBWS_OBJEKT_MANAGER_H

#include "psbws_types.h"

class PSBWS_Simulation;
class PSBWS_Effect_API_Manager;
class PSBWS_Reaction_API_Manager;
class PSBWS_ActionCoder_Human;
class PSBWS_Human;
class PSBWS_Item;
class PSBWS_Magic;
class PSBWS_God;
class PSBWS_EventMaster;
class PSBWS_Position;

// TODO: nur zum Testen, bevor die Trennung der Objekte losgeht:
class PSBWS_SimulationObject;

class PSBWS_Object_Manager  
{
public:
	PSBWS_Object_Manager();
	virtual ~PSBWS_Object_Manager();

	PSBWS_Simulation*				      get_simulation();
	PSBWS_Effect_API_Manager*	    get_manager_effectAPI();
	PSBWS_Reaction_API_Manager*		get_manager_reactionAPI();
  PSBWS_ActionCoder_Human*      get_actionCoder_human();
	PSBWS_Human*					        get_human(TPNR);
	PSBWS_Item*				            get_item(TGNR);
	PSBWS_Magic*					        get_magic(TPositive);
	PSBWS_God*						        get_god();
  PSBWS_EventMaster*		        get_eventMaster();
  PSBWS_Position*               get_position(TONR);
  
  // TODO: nur zum Testen, bevor die Trennung der Objekte losgeht:
  PSBWS_SimulationObject*       get_object(TONR);
  
protected:
	PSBWS_Simulation*					 mP_simulation;
	PSBWS_Effect_API_Manager*		 mP_manager_effectAPI;
	PSBWS_Reaction_API_Manager*			 mP_manager_reactionAPI;
  PSBWS_ActionCoder_Human*         mP_actionCoder_human;
};

#endif
