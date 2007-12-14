/***************************************************************************
                          psbws_simulation.h  -  description
                             -------------------
    begin                : Tue Jun 10 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_SIMULATION_H
#define PSBWS_SIMULATION_H


/**
  *@author Mathias Sikos












  */

#include "psbws_types.h"
#include "sm_hashtabelle.h"

class PSBWS_God;
class PSBWS_EventMaster;
class PSBWS_Human;
class PSBWS_Item;
class PSBWS_Inventory;
class PSBWS_Position;
class PSBWS_Magic;
class PSBWS_Object_Manager;
class PSBWS_SimulationObject;



class PSBWS_Simulation
{
public: 
	PSBWS_Simulation(PSBWS_Object_Manager*);
	~PSBWS_Simulation();

	
  void start_simulation();


	PSBWS_God*                       god;
	PSBWS_EventMaster*                eventMaster;
	SM_HashTabelle<PSBWS_Human>* 		humans;
	SM_HashTabelle<PSBWS_Item>* items;
	SM_HashTabelle<PSBWS_Inventory>*   inventories;
	SM_HashTabelle<PSBWS_Position>* 	positions;
	SM_HashTabelle<PSBWS_Magic>* 		magics;

// TODO : erstmal zum Testen, spaeter keine Liste mit allen Objekten
// sondern die einzelnen Listen nacheinander
	SM_HashTabelle<PSBWS_SimulationObject>* 		objects;

  STR_WorldMap*     worldMap;
  
protected:
	PSBWS_Object_Manager*		mP_objectManager;

private:
	
	TNatural determine_numberOfEvents();
  bool mb_simulate;

};



#endif
