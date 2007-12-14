/***************************************************************************
                          psbws_zauber.h  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_ZAUBER_H
#define PSBWS_ZAUBER_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"
#include "psbws_simulationsobjekt.h"

class PSBWS_Magic : PSBWS_SimulationObject
{
public: 
	PSBWS_Magic(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Magic();

  virtual void init(TONR uO_nr);
  
	void effect(TPNR, TONR, TUChar);

};

#endif
