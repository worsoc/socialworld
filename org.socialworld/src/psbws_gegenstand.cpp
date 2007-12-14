/***************************************************************************
                          psbws_gegenstand.cpp  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_gegenstand.h"
#include "psbws_objekt_manager.h"
#include "psbws_person.h"
#include "psbws_eventmaster.h"
#include "psbws_inventar.h"

PSBWS_Item::PSBWS_Item(PSBWS_Object_Manager* aP_objectManager, TGNR au_INr, STR_3D astr_3D)
	:PSBWS_SimulationObject(aP_objectManager, astr_3D)
{
		mu_INr = au_INr;
}

PSBWS_Item::~PSBWS_Item()
{
}

void PSBWS_Item::init(TONR au_ONr) {mu_ONr = au_ONr;}

void PSBWS_Item::use_internItem(TPNR au_PNr)
{
}

void PSBWS_Item::examine_item (TPNR au_PNr)
{
}

void PSBWS_Item::take_item(TPNR au_PNr)
{
}

void PSBWS_Item::use_externItem(TPNR au_PNr)
{
}

void PSBWS_Item::collect_item(TPNR au_PNr)
{
}

void PSBWS_Item::switch_item_to_left_hand(TPNR au_PNr)
{
}

void PSBWS_Item::use_2_items(TPNR au_PNr)
{
}

void PSBWS_Item::drop_item(TPNR au_PNr)
{
}

void PSBWS_Item::use_weapon(TPNR au_PNr, TUChar au_type, char ai_x, char ai_y, char ai_z, TUChar au_intensity)
{
  switch (au_type)
	{
	case 8:	 hit_rightHand(au_PNr, au_type, ai_x, ai_y, ai_z, au_intensity); break;
//	case 9:	 push_rightHand(au_PNr, ai_x, ai_y, ai_z, au_intensity); break;
	}
}

void PSBWS_Item::hit_rightHand(TPNR au_PNr, TUChar au_type, char ai_x, char ai_y, char ai_z, TUChar au_intensity)
{
	if ( mP_objectManager->get_human(au_PNr)->get_inventory()->get_rightHand() == mu_INr )
	{
		PSBWS_Event* lP_event;
		TUChar lu_eventCode;
		lu_eventCode = 1;          // 1 zum Test, sonst dieses ereignis mit Code  128; 10|00|0000	... Kampf | Schlag | Schwert
		lP_event = mP_objectManager->get_eventMaster()->create_event(
        lu_eventCode, ai_x, ai_y, ai_z, au_type, au_intensity, au_PNr);
		mP_objectManager->get_human(au_PNr)->set_event(lP_event);
	}	
}

