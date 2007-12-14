/***************************************************************************
                          psbws_api.h  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_API_H
#define PSBWS_API_H


/**
  *@author Mathias Sikos
  */

#include "psbws_simulationsobjekt.h"

class PSBWS_API {
public: 
	PSBWS_API();
	virtual ~PSBWS_API();

	virtual void calculate(PSBWS_SimulationObject*, TUChar) = 0;

protected:
};

#endif
