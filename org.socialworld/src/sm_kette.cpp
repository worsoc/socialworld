/***************************************************************************
                          sm_kette.cpp  -  description
                             -------------------
    begin                : Tue Jun 24 2003
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

#ifndef SM_KETTE_CPP
#define SM_KETTE_CPP


#include "sm_kette.h"

template <class TypeData>
SM_Kette<TypeData>::SM_Kette(THashID dummyID, TypeData* dummy)
{
		SM_ListenElement<TypeData>* pListenElementDummy =
        new SM_ListenElement<TypeData>(dummyID,dummy);
    pListenElementDummy->m_pNext = 0;
    pListenElementDummy->m_pPrev = 0;

    m_pAktuell = pListenElementDummy;
    m_pPointerSaved = pListenElementDummy;

}

template <class TypeData> SM_Kette<TypeData>::~SM_Kette()
{
}

template <class TypeData> bool SM_Kette<TypeData>::next()
{
		if (m_pAktuell->m_pNext == 0) return false;
    m_pAktuell = m_pAktuell->m_pNext;
    return true;
}

template <class TypeData>	bool SM_Kette<TypeData>::prev()
{
		if (m_pAktuell->m_pPrev == 0) return false;
    m_pAktuell = m_pAktuell->m_pPrev;
    return true;
}

template <class TypeData>	 SM_ListenElement<TypeData>*
SM_Kette<TypeData>::insertNext(TypeData* element) {
// TODO:  1. Parameter (hier 0) überarbeiten (vielleicht weiterer Konstruktor fuer SM_ListenElement
		SM_ListenElement<TypeData>* pListenElementNeu = new SM_ListenElement<TypeData>(0,element);
     
    pListenElementNeu->m_pNext = m_pAktuell->m_pNext;
		pListenElementNeu->m_pPrev = m_pAktuell;
    if (m_pAktuell->m_pNext == 0) 
        m_pAktuell->m_pNext = pListenElementNeu;
    else {  
		    m_pAktuell->m_pNext->m_pPrev = pListenElementNeu;
        m_pAktuell->m_pNext = pListenElementNeu;
    }
    return pListenElementNeu;
}

template <class TypeData>	 SM_ListenElement<TypeData>*
SM_Kette<TypeData>::insertPrev(TypeData* element) {
// TODO:  1. Parameter (hier 0) überarbeiten (vielleicht weiterer Konstruktor fuer SM_ListenElement
		SM_ListenElement<TypeData>* pListenElementNeu = new SM_ListenElement<TypeData>(0,element);

    pListenElementNeu->m_pNext = m_pAktuell;
		pListenElementNeu->m_pPrev = m_pAktuell->m_pPrev;
    if (m_pAktuell->m_pPrev == 0)
        m_pAktuell->m_pPrev = pListenElementNeu;
    else {
		    m_pAktuell->m_pPrev->m_pNext = pListenElementNeu;
        m_pAktuell->m_pPrev = pListenElementNeu;
    }
    return pListenElementNeu;
}

template <class TypeData>	void SM_Kette<TypeData>::deleteActual(bool bAktuellIsNext)
{
		SM_ListenElement<TypeData>* pListenElementTmp = m_pAktuell;
		m_pAktuell->m_pPrev->m_pNext = m_pAktuell->m_pNext;
		m_pAktuell->m_pNext->m_pPrev = m_pAktuell->m_pPrev;
		if  (bAktuellIsNext)
					m_pAktuell = m_pAktuell->m_pNext;
    else
          m_pAktuell = m_pAktuell->m_pPrev;
		delete pListenElementTmp;
}

template <class TypeData> TypeData* SM_Kette<TypeData>::getActual()
{
		return m_pAktuell->m_pData;
}

template <class TypeData> SM_ListenElement<TypeData>* SM_Kette<TypeData>::getListElementActual()
{
		return m_pAktuell;
}

#endif

