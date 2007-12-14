/***************************************************************************
                          psbws_baum.h  -  description
                             -------------------
    begin                : Mon Aug 4 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_BAUM_H
#define PSBWS_BAUM_H

#include <psbws_pflanze.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Tree : public PSBWS_Plant  {
public: 
	PSBWS_Tree(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_Tree();

  virtual void init(TONR);

protected:

};

#endif
