/***************************************************************************
                          psbws_halm.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_halm.h"

PSBWS_Grass::PSBWS_Grass(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Plant(aP_objectManager, astr_3D)
{
}

PSBWS_Grass::~PSBWS_Grass()
{
}

void PSBWS_Grass::init(TONR au_ONr) {mu_ONr = au_ONr;}
