/***************************************************************************
                          psbws_strauch.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_STRAUCH_H
#define PSBWS_STRAUCH_H

#include "psbws_pflanze.h"

/**
  *@author Mathias Sikos
  */

class PSBWS_Bush : public PSBWS_Plant
{
public: 
	PSBWS_Bush(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Bush();

  virtual void init(TONR);
  
protected:

};

#endif
