/***************************************************************************
                          psbws_pflanze.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_pflanze.h"

PSBWS_Plant::PSBWS_Plant(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_SimulationObject(aP_objectManager, astr_3D)
{
}

PSBWS_Plant::~PSBWS_Plant()
{
}

void PSBWS_Plant::init(TONR au_ONr) {mu_ONr = au_ONr;}
