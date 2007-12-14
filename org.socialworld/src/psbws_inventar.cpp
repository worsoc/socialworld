/***************************************************************************
                          psbws_inventar.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_inventar.h"

PSBWS_Inventory::PSBWS_Inventory(){
}
PSBWS_Inventory::~PSBWS_Inventory(){
}


void PSBWS_Inventory::init(TPNR au_inv_nr)
{
	 mu_PNr = au_inv_nr;
}

void PSBWS_Inventory::set_rightHand(TGNR au_INr)
{
	 mu_rightHand = au_INr;
}

void PSBWS_Inventory::set_leftHand(TGNR au_INr)
{
   mu_leftHand = au_INr;
}

void PSBWS_Inventory::take_in_leftHand(TONR au_ONr)
{
	 mu_leftHand = au_ONr;
}

void PSBWS_Inventory::take_in_rightHand(TONR au_ONr)
{
	 mu_rightHand = au_ONr;
}

void PSBWS_Inventory::take_in_bothHands(TONR au_ONr)
{
	 mu_rightHand = au_ONr;
	 mu_leftHand = au_ONr;
}

TONR PSBWS_Inventory::get_rightHand()
{
	 return mu_rightHand;
}

TONR PSBWS_Inventory::get_leftHand()
{
	 return mu_leftHand;
}

TGNR PSBWS_Inventory::get_fastItem(TUChar au_nr)
{
	 return mua_fastItems[au_nr - 1];
}
