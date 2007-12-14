/***************************************************************************
                          sm_prioritaetselement.h  -  description
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

#ifndef SM_PRIORITAETSELEMENT_H
#define SM_PRIORITAETSELEMENT_H


/**
  *@author Mathias Sikos
  */

#include "sm_prioritaetsschlangenelement.cpp"

template <class TypeData>
class SM_PrioritaetsElement
{
public: 
	SM_PrioritaetsElement(TUChar uPrioritaet);
	~SM_PrioritaetsElement();

  SM_PrioritaetsSchlangenElement<TypeData>* m_pSchlangenElementHighPriority;
	SM_PrioritaetsSchlangenElement<TypeData>* m_pSchlangenElementLowPriority;

  SM_PrioritaetsElement<TypeData>* m_pNext;

	TUChar m_uPrioritaet;

};

#endif
