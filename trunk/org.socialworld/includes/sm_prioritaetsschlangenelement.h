/***************************************************************************
                          sm_prioritaetsschlangenelement.h  -  description
                             -------------------
    begin                : Fri Jun 13 2003
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

#ifndef SM_PRIORITAETSSCHLANGENELEMENT_H
#define SM_PRIORITAETSSCHLANGENELEMENT_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

template <class TypeData>
class SM_PrioritaetsSchlangenElement
{
public: 
	SM_PrioritaetsSchlangenElement(TUChar uPrioritaet, TypeData* element);
	~SM_PrioritaetsSchlangenElement();

  TUChar m_uPrioritaet;
  TypeData* m_pData;
  SM_PrioritaetsSchlangenElement<TypeData>* m_pNext;
};

#endif
