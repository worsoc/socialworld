/***************************************************************************
                          sm_keller.cpp  -  description
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

#include "sm_keller.h"

template <class TypeData> SM_Keller<TypeData>::SM_Keller()
{
}

template <class TypeData> SM_Keller<TypeData>::~SM_Keller(){
}

template <class TypeData> int SM_Keller<TypeData>::insert(TypeData* element)
{
			SM_KellerElement<TypeData>* pKellerElement = new SM_KellerElement<TypeData>(element);
			
			if (m_pListenKopf != NULL)
					pKellerElement->m_pNext = m_pListenkopf;
			m_pListenKopf = pKellerElement;
			return 0;    		
}

template <class TypeData> TypeData* SM_Keller<TypeData>::getElement()
{
			if (m_pListenKopf != NULL)
					return m_pListenKopf->m_pData;
			return NULL;
}

template <class TypeData> TypeData* SM_Keller<TypeData>::remove()
{
			if (m_pListenKopf == NULL)
					return NULL;
			TypeData* pElementTmp = m_pListenKopf->m_pData;
			SM_KellerElement<TypeData>* pSKellerElementTmp = m_pListenKopf;
			m_pListenKopf = m_pListenKopf->m_pNext;
			delete pKellerElementTmp;
			return pElementTmp;
}

