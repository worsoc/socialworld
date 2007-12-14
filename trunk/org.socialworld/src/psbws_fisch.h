/***************************************************************************
                          psbws_fisch.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_FISCH_H
#define PSBWS_FISCH_H

#include <psbws_tier.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Fish : public PSBWS_Animal  {
public: 
	PSBWS_Fish(PSBWS_Object_Manager* , STR_3D );
	virtual ~PSBWS_Fish();

  virtual void init(TONR);

protected:

};

#endif
