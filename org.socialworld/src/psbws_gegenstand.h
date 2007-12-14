/***************************************************************************
                          psbws_gegenstand.h  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_GEGENSTAND_H
#define PSBWS_GEGENSTAND_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"
#include "psbws_simulationsobjekt.h"

class PSBWS_Object_Manager;
class PSBWS_Simulation;

class PSBWS_Item : public PSBWS_SimulationObject
{
public: 

	PSBWS_Item(PSBWS_Object_Manager*, TGNR, STR_3D);
	virtual ~PSBWS_Item();

  virtual void init(TONR);
 
	void use_internItem(TPNR);
	void examine_item (TPNR);
  void take_item(TPNR);
	void use_externItem(TPNR);
	void collect_item(TPNR);
	void switch_item_to_left_hand(TPNR);
	void use_2_items(TPNR);
	void drop_item(TPNR);
	void use_weapon(TPNR, TUChar, char,char, char, TUChar);

	TUChar mu_type;

private:

	void hit_rightHand(TPNR, TUChar, char, char, char, TUChar);
	void push_rightHand(TPNR, TUChar, char, char, char, TUChar);	

  PSBWS_Simulation* mP_simulation;
	TGNR mu_INr;

};

#endif
