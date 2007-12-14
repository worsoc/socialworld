/***************************************************************************
                          sm_schlangenelement.cpp  -  description
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
#ifndef SM_SCHLANGENELEMENT_CPP
#define SM_SCHLANGENELEMENT_CPP

#include "sm_schlangenelement.h"

template <class TypeData> SM_SchlangenElement<TypeData>::SM_SchlangenElement(TypeData* element)
{
			m_pData = element;
			m_pNext = NULL;
}

template <class TypeData> SM_SchlangenElement<TypeData>::~SM_SchlangenElement()
{
}

#endif
