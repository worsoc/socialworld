/***************************************************************************
                          psbws_event.h  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_EVENT_H
#define PSBWS_EVENT_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class PSBWS_Event {
public: 
	PSBWS_Event();
	virtual ~PSBWS_Event();

	TUChar get_type();

  TEVC mu_eventCode;	   		    // Art des Ereignis (baumartig unterteit)
  long operand1;
	int operand2;
	int operand3;
	int operand4;
	int operand5;
  TUChar mu_priority;
	TPositive mu_causer;                     // Auslöser des Ereignisses
  TUChar mu_strength;					// des Ereignisses z.B. zur Schadensbestimmung
  TNatural mu_time;					// Zeitpunkt des Auslösens

  TNatural mua_position[3];			// (x,y,z) Weltkoordinaten
  float mfa_direction[3];
  TNatural mu_effectDistance;
  TUChar mu_effectAngle;
  TNatural mu_maxDistance;			// max Distanz, in der Ereignis wahrnehmabr
  TNatural mu_maxSee;				// Wahrnehmung spezieller
  TNatural mu_maxHear;
  TNatural mu_maxSmell;

};

#endif
