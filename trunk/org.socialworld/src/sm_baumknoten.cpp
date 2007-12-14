/***************************************************************************
                          sm_baumknoten.cpp  -  description
                             -------------------
    begin                : Thu May 29 2003
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

#include "sm_baumknoten.h"

template <class TypeKey, class TypeData>
					SM_BaumKnoten<TypeKey, TypeData>::SM_BaumKnoten(TypeKey key, TypeData* element)
{
			m_Key = key;
			m_pData = element;
			m_pLinkerTB = NULL;
			m_pRechterTB = NULL;
			m_pVaterKnoten = NULL;
			m_iTeilbaum = 0;
			m_uLaengeLinkerTB = 0;
			m_uLaengeRechterTB = 0;
			m_iBalance = 0;
			
}

template <class TypeKey, class TypeData>
SM_BaumKnoten<TypeKey, TypeData>::~SM_BaumKnoten()
{
}
