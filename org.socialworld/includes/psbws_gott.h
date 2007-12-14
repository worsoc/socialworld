/***************************************************************************
                          psbws_gott.h  -  description
                             -------------------
    begin                : Tue May 13 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_GOTT_H
#define PSBWS_GOTT_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class PSBWS_Human;
class PSBWS_Object_Manager;

class PSBWS_God
{
public:
	PSBWS_God(PSBWS_Object_Manager*);
	~PSBWS_God();
	void next_second();
	PSBWS_Human* create_human();
	TNatural get_time();
	TPositive get_maxPNr();
	TONR get_maxONr();
protected:
	PSBWS_Object_Manager* mP_objectManager;
	TNatural mu_time;
	TPositive mu_number_humans;
  TONR mu_number_simulationObjects;
};

#endif
