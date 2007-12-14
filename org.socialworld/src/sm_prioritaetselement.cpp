/***************************************************************************
                          sm_prioritaetselement.cpp  -  description
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
#ifndef SM_PRIORITAETSELEMENT_CPP
#define SM_PRIORITAETSELEMENT_CPP

#include "sm_prioritaetselement.h"

template <class TypeData> SM_PrioritaetsElement<TypeData>::SM_PrioritaetsElement(TUChar uPrioritaet)
{
			m_uPrioritaet = uPrioritaet;
			m_pSchlangenElementHighPriority = NULL;
			m_pSchlangenElementLowPriority = NULL;
		  m_pNext = NULL;
}

template <class TypeData> SM_PrioritaetsElement<TypeData>::~SM_PrioritaetsElement()
{
}

#endif
