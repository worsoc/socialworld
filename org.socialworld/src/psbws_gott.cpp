/***************************************************************************
                          psbws_gott.cpp  -  description
                             -------------------
    begin                : Tue May 13 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_gott.h"
#include "psbws_person.h"

#include "psbws_objekt_manager.h"


PSBWS_God::PSBWS_God(PSBWS_Object_Manager* aP_objectManager)
{
    mP_objectManager = aP_objectManager;
	  mu_time = 1;
		mu_number_humans = 0;
    mu_number_simulationObjects = 0;
}


PSBWS_God::~PSBWS_God()
{
}


void PSBWS_God::next_second()
{
   mu_time += 1;
}


TNatural PSBWS_God::get_time()
{
	return mu_time;
}


TPositive PSBWS_God::get_maxPNr()
{
	return mu_number_humans;
}

TONR PSBWS_God::get_maxONr()
{
	return mu_number_simulationObjects;
}

PSBWS_Human* PSBWS_God::create_human()
{
    STR_3D lstr_3D = { 100, 100, 100 };
		PSBWS_Human* lP_human = new PSBWS_Human(mP_objectManager, lstr_3D);
		mu_number_humans++;
    mu_number_simulationObjects++;
		return lP_human;
}
