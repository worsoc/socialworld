/***************************************************************************
                          psbws_simulation.cpp  -  description
                             -------------------
    begin                : Tue Jun 10 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

#include "psbws_simulation.h"


#include "psbws_gott.h"
#include "psbws_Person.h"
#include "psbws_eventmaster.h"
#include "psbws_position.h"
#include "psbws_zauber.h"


#include "psbws_objekt_manager.h"

#include "sm_hashtabelle.cpp"

// zum Testen
#include "psbws_event.h"
#include "psbws_simulationsobjekt.h"

PSBWS_Simulation::PSBWS_Simulation(PSBWS_Object_Manager* aP_objectManager)
{
  mb_simulate = false;
  mP_objectManager = aP_objectManager;
	god = new PSBWS_God(aP_objectManager);
	humans = new SM_HashTabelle<PSBWS_Human>(1000);
	positions = new SM_HashTabelle<PSBWS_Position>(1000);
  items = new SM_HashTabelle<PSBWS_Item>(1000);
	inventories = new SM_HashTabelle<PSBWS_Inventory>(1000);

	TNatural lu_minNumber_events = determine_numberOfEvents();
	eventMaster = new PSBWS_EventMaster(aP_objectManager, lu_minNumber_events);
			

	// TODO : nur als Test
  objects = new SM_HashTabelle<PSBWS_SimulationObject>(1000);
}

PSBWS_Simulation::~PSBWS_Simulation()
{
      delete inventories;
			delete items;
		  delete positions;
			delete humans;
			delete eventMaster;
			delete god;	
}



void PSBWS_Simulation::start_simulation()
{
// Testinitialisierungen; Personerstellung spaeter woanders
      PSBWS_Human* lP_person = god->create_human();
      lP_person->init(1);
			PSBWS_Event* lP_event = new PSBWS_Event();
			lP_event->mu_eventCode = 31;
			lP_event->mu_priority = 4;
			lP_event->mu_maxDistance = 100;
			lP_event->mu_maxHear = 100;
			lP_event->mu_maxSee = 80;
			lP_event->mu_maxSmell = 10;
      lP_event->mu_effectDistance = 30;
      lP_event->mu_effectAngle = 179;
      eventMaster->insert_event(lP_event);
			lP_person->set_event(lP_event);
			lP_person->set_maxDistance(110);
			lP_person->set_maxHear(110);
			lP_person->set_maxSee(81);
			lP_person->set_maxSmell(11);
			lP_person->set_positionX(10);
			lP_person->set_positionY(10);
			lP_person->set_positionZ(10);
      humans->insert(1, lP_person);
      objects->insert(1, lP_person);

      lP_person = god->create_human();
      lP_person->init(2);
			lP_event = new PSBWS_Event();
			lP_event->mu_eventCode = 31;
			lP_event->mu_priority = 3;
			lP_event->mu_maxDistance = 120;
			lP_event->mu_maxHear = 120;
			lP_event->mu_maxSee = 80;
			lP_event->mu_maxSmell = 30;
      lP_event->mu_effectDistance = 30;
      lP_event->mu_effectAngle = 90;
      eventMaster->insert_event(lP_event);
			lP_person->set_event(lP_event);
			lP_person->set_maxDistance(120);
			lP_person->set_maxHear(120);
			lP_person->set_maxSee(91);
			lP_person->set_maxSmell(21);
			lP_person->set_positionX(20);
			lP_person->set_positionY(20);
			lP_person->set_positionZ(20);
      humans->insert(2, lP_person);
      objects->insert(2, lP_person);
            
      lP_person = god->create_human();
      lP_person->init(3);
			lP_event = new PSBWS_Event();
			lP_event->mu_eventCode = 31;
			lP_event->mu_priority = 4;
			lP_event->mu_maxDistance = 300;
			lP_event->mu_maxHear = 100;
			lP_event->mu_maxSee = 380;
			lP_event->mu_maxSmell = 130;
      lP_event->mu_effectDistance = 30;
      lP_event->mu_effectAngle = 45;
      eventMaster->insert_event(lP_event);
			lP_person->set_event(lP_event);
			lP_person->set_maxDistance(310);
			lP_person->set_maxHear(310);
			lP_person->set_maxSee(31);
			lP_person->set_maxSmell(31);
			lP_person->set_positionX(30);
			lP_person->set_positionY(30);
			lP_person->set_positionZ(30);
      humans->insert(3, lP_person);
      objects->insert(3, lP_person);

      eventMaster->fill_eventQueue();
      eventMaster->handle_events();


      while(mb_simulate) {
          eventMaster->fill_eventQueue();
          eventMaster->handle_events();
          god->next_second();
          if (god->get_time() == 10) mb_simulate = false;
      }
}     
        
TNatural PSBWS_Simulation::determine_numberOfEvents()
{
			return 30;
}

