/***************************************************************************
                          psbws_tier.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

/**
  *@author Mathias Sikos
  */

#ifndef PSBWS_TIER_H
#define PSBWS_TIER_H

#include "psbws_simulationsobjekt.h"

class PSBWS_MoveType;

class PSBWS_Animal : public PSBWS_SimulationObject  {
public: 
	PSBWS_Animal(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Animal();

  virtual void init(TONR);

  TPositive get_sense(TUChar);  // liefert Wert, den mit sinn bestimmter Sinn zur Zeit hat 
                                      // (wahrnimmt)
 	void set_sense(unsigned short, TPositive);
													// in [0,4] welcher Sinn? , spezielle Beschreibung
													//                            (wie Statuswort)

	PSBWS_MoveType* get_moveType();
      
  TNatural get_maxDistance();
  TNatural get_maxSee();
  TNatural get_maxHear();
  TNatural get_maxSmell();

  void set_maxDistance(TNatural); 
  void set_maxHear(TNatural);
  void set_maxSee(TNatural);
  void set_maxSmell(TNatural);

  void sleep();
  TUChar test_sleep();
    
protected:
	PSBWS_MoveType* mP_moveType;
  TPositive mua_sense[5];              // sehen(0), hoeren(1), riechen(2),
                                                    //  schmecken(3), fuehlen(4)
  short int mia_condition[3];							// atemfrequenz(0), kraftfaehigkeit(1), schnelligkeit(2)

  TNatural mu_maxDistance;			// max Distanz, in der Ereignis wahrnehmabr
  TNatural mu_maxSee;				// Wahrnehmung spezieller
  TNatural mu_maxHear;
  TNatural mu_maxSmell;

  
};

#endif
