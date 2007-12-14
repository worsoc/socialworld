/***************************************************************************
                          sm_keller.h  -  description
                             -------------------
    begin                : Thu Jun 12 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

#ifndef SM_KELLER_H
#define SM_KELLER_H


/**
  *@author Mathias Sikos
  */

#include "sm_kellerelement.h"

template <class TypeData>
class SM_Keller
{
public: 
	SM_Keller();
	~SM_Keller();

	int insert(TypeData* element);
	TypeData* getElement();
	TypeData* remove();
protected:
	SM_KellerElement<TypeData>* m_pListenKopf;
};

#endif
