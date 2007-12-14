/***************************************************************************
                          psbws_halm.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_HALM_H
#define PSBWS_HALM_H

#include <psbws_pflanze.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Grass : public PSBWS_Plant  {
public: 
	PSBWS_Grass(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Grass();

  virtual void init(TONR);

protected:

};

#endif
