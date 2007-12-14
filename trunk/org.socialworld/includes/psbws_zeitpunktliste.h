/***************************************************************************
                          psbws_zeitpunktliste.h  -  description
                             -------------------
    begin                : Sun Jun 22 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_ZEITPUNKTLISTE_H
#define PSBWS_ZEITPUNKTLISTE_H

#include "sm_liste.h"

/**
  *@author Mathias Sikos
  */


template <class TypeData>
class PSBWS_TimeList : public SM_Liste<TypeData>
{
public: 
	PSBWS_TimeList();
	virtual ~PSBWS_TimeList();

protected:
	SM_ListenElement<TypeData>* get_listElement(THashID , TUChar au_flag = 0);
};

#endif
