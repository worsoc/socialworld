/***************************************************************************
                          psbws_saeugetier.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_SAEUGETIER_H
#define PSBWS_SAEUGETIER_H

#include "psbws_tier.h"

/**
  *@author Mathias Sikos
  */

class PSBWS_Mammal : public PSBWS_Animal  {
public: 
	PSBWS_Mammal(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Mammal();

  virtual void init(TONR);
  
};

#endif
