/***************************************************************************
                          psbws_vogel.cpp  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_vogel.h"

PSBWS_Bird::PSBWS_Bird(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Animal(aP_objectManager, astr_3D)
{

}

PSBWS_Bird::~PSBWS_Bird()
{
}

void PSBWS_Bird::init(TONR au_ONr) {mu_ONr = au_ONr;}
