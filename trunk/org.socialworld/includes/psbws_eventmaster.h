/***************************************************************************
                          psbws_eventmaster.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_EVENTMASTER_H
#define PSBWS_EVENTMASTER_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

#include "sm_prioritaetsschlange.cpp"
#include "sm_schlange.cpp"

class PSBWS_Object_Manager;
class SM_VektorMathematik;

class PSBWS_EventMaster
{
public: 
	PSBWS_EventMaster(PSBWS_Object_Manager*, TNatural);
	~PSBWS_EventMaster();

	int fill_eventQueue();
	int insert_event(PSBWS_Event*);
	int handle_events();
	PSBWS_Event* create_event(TEVC, long int, int, int, int, int,
              TONR, TUChar au_intensity = 0, int ai_relX = 0, int ai_relY = 0, int ai_relZ = 0,
              float af_dirX = 0, float af_dirY = 0, float af_dirZ = 0,
              TNatural au_distance_effect = 0, TUChar au_angle_effect = 0);

private:

	int determine_candidates();
	int determine_influence();

  bool decide_effective(TONR uONr);
  bool decide_time_remains();

	PSBWS_Event*	mP_event;              // aktuell betrachtetes Ereignis
  TNatural mu_posX_event;
  TNatural mu_posY_event;
  TNatural mu_posZ_event;
  float mf_dirX_event;
  float mf_dirY_event;
  float mf_dirZ_event;
  TNatural mu_distance_effect;
  double md_angleTangens_effect;

  TNatural mu_number_events;
	TNatural mu_minNumber_events;

	SM_PrioritaetsSchlange<PSBWS_Event>*	 mSM_events;
	SM_Schlange<STR_Candidate>*			 mSM_candidates;

	PSBWS_Object_Manager* mP_objectManager;
  SM_VektorMathematik* mSM_vector;

};

#endif
