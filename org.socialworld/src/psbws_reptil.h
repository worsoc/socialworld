/***************************************************************************
                          psbws_reptil.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_REPTIL_H
#define PSBWS_REPTIL_H

#include <psbws_tier.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Reptile : public PSBWS_Animal  {
public: 
	PSBWS_Reptile(PSBWS_Object_Manager* , STR_3D);
	virtual ~PSBWS_Reptile();

  virtual void init(TONR);

  
protected:

};

#endif
