/***************************************************************************
                          sm_schlange.cpp  -  description
                             -------------------
    begin                : Wed Jun 11 2003
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

#ifndef SM_SCHLANGE_CPP
#define SM_SCHLANGE_CPP


#include "sm_schlange.h"

template <class TypeData> SM_Schlange<TypeData>::SM_Schlange()
{
    	m_pListenKopf = NULL;
			m_pListenEnde = NULL;
}

template <class TypeData> SM_Schlange<TypeData>::~SM_Schlange()
{
}

template <class TypeData> int SM_Schlange<TypeData>::insert(TypeData* element)
{
			SM_SchlangenElement<TypeData>* pSchlangenElement = new SM_SchlangenElement<TypeData>(element);
			
			if (m_pListenEnde != NULL)
					m_pListenEnde->m_pNext = pSchlangenElement;
			else
					m_pListenKopf = pSchlangenElement;
			m_pListenEnde = pSchlangenElement;
			return 0;    		
}

template <class TypeData> TypeData* SM_Schlange<TypeData>::getElement()
{
			if (m_pListenKopf != NULL)
					return m_pListenKopf->m_pData;
			return NULL;
}

template <class TypeData> TypeData* SM_Schlange<TypeData>::remove()
{
			if (m_pListenKopf == NULL)
					return NULL;
			TypeData* pElementTmp = m_pListenKopf->m_pData;
			SM_SchlangenElement<TypeData>* pSchlangenElementTmp = m_pListenKopf;
			m_pListenKopf = m_pListenKopf->m_pNext;
			delete pSchlangenElementTmp;
			return pElementTmp;
}

#endif
