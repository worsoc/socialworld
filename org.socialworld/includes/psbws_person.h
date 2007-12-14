/***************************************************************************
                          psbws_person.h  -  description
                             -------------------
    begin                : Tue May 13 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_PERSON_H
#define PSBWS_PERSON_H


/**
  *@author Mathias Sikos
  */


#include "psbws_types.h"
#include "psbws_saeugetier.h"


class PSBWS_FortbewegungsArt;
class PSBWS_Inventory;
class PSBWS_Position;
class PSBWS_Event;
class PSBWS_Object_Manager;

class PSBWS_Human  :  public PSBWS_Mammal
{
public: 

	PSBWS_Human(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Human();

  virtual void init(TONR uO_nr);

  virtual int determine_effect(PSBWS_Event *pEvent);


	void kick(TUChar au_kick);

	void control_hand_manually (TUChar, char, char, char);



	void touch (TUChar, TUChar, TNatural);

  void say (TUChar, TUChar, TNatural);


	PSBWS_Inventory* get_inventory();

protected:

	TPNR mu_PNr;

	PSBWS_Inventory* mP_inventory;

	STR_Mood mstr_mood;

  TPositive mu_poison;
  TPositive mu_psyche;


};

#endif

