/***************************************************************************
                          psbws_pilz.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_PILZ_H
#define PSBWS_PILZ_H

#include <psbws_pflanze.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Mushroom : public PSBWS_Plant  {
public: 
	PSBWS_Mushroom(PSBWS_Object_Manager*, STR_3D );
	virtual ~PSBWS_Mushroom();

  virtual void init(TONR);

    
protected:

};

#endif
