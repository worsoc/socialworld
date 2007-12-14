/***************************************************************************
                          psbws_strauch.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_strauch.h"

PSBWS_Bush::PSBWS_Bush(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Plant(aP_objectManager, astr_3D)
{
}

PSBWS_Bush::~PSBWS_Bush()
{
}

void PSBWS_Bush::init(TONR au_ONr) {mu_ONr = au_ONr;}
