/***************************************************************************
                          psbws_blume.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_blume.h"

PSBWS_Flower::PSBWS_Flower(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Plant(aP_objectManager, astr_3D)
{
}
PSBWS_Flower::~PSBWS_Flower(){
}

void PSBWS_Flower::init(TONR au_ONr) {mu_ONr = au_ONr;}
