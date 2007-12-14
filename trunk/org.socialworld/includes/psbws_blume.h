/***************************************************************************
                          psbws_blume.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_BLUME_H
#define PSBWS_BLUME_H

#include <psbws_pflanze.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Flower : public PSBWS_Plant  {
public: 
	PSBWS_Flower(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Flower();

  virtual void init(TONR);

protected:

};

#endif
