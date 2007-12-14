/***************************************************************************
                          psbws_pilz.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_pilz.h"

PSBWS_Mushroom::PSBWS_Mushroom(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Plant(aP_objectManager, astr_3D)
{
}

PSBWS_Mushroom::~PSBWS_Mushroom()
{
}

void PSBWS_Mushroom::init(TONR au_ONr) {mu_ONr = au_ONr;}
