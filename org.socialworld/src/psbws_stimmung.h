/***************************************************************************
                          psbws_stimmung.h  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_STIMMUNG_H
#define PSBWS_STIMMUNG_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"
#include "psbws_attribute.h"

class PSBWS_Mood :  PSBWS_Attribute
{
public: 
	PSBWS_Mood();
	virtual ~PSBWS_Mood();

	TPositive get_value();
	void actualize();

protected:

	void set_value(const TPositive au_new_value);
  virtual unsigned short calculate();

	TPNR mu_PNr;
	TPositive mu_base_value;
	TPositive mu_value;

};

#endif
