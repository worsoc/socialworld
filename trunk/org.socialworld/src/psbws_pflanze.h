/***************************************************************************
                          psbws_pflanze.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_PFLANZE_H
#define PSBWS_PFLANZE_H

#include <psbws_simulationsobjekt.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Plant : public PSBWS_SimulationObject  {
public: 
	PSBWS_Plant(PSBWS_Object_Manager* pObjekte, STR_3D wk3D);
	virtual ~PSBWS_Plant();

  virtual void init(TONR);

};

#endif
