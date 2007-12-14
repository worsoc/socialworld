/***************************************************************************
                          psbws_vogel.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_VOGEL_H
#define PSBWS_VOGEL_H

#include <psbws_tier.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Bird : public PSBWS_Animal  {
public: 
	PSBWS_Bird(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Bird();

  virtual void init(TONR);

protected:

};

#endif
