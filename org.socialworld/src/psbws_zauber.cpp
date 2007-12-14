/***************************************************************************
                          psbws_zauber.cpp  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_zauber.h"

PSBWS_Magic::PSBWS_Magic(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_SimulationObject(aP_objectManager, astr_3D)
{
}
PSBWS_Magic::~PSBWS_Magic()
{
}


void PSBWS_Magic::init(TONR au_ONr)  {mu_ONr = au_ONr;}

void PSBWS_Magic::effect(TPositive au_PNr, TNatural au_ONr, TUChar au_intensity)
{
}
