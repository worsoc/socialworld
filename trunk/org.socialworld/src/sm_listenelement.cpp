/***************************************************************************
                          sm_listenelement.cpp  -  description
                             -------------------
    begin                : Mon May 19 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/
#ifndef SM_LISTENELEMENT_CPP
#define SM_LISTENELEMENT_CPP


#include "sm_listenelement.h"

template <class TypeData> SM_ListenElement<TypeData>::SM_ListenElement(THashID ID, TypeData* element)
{
		m_pData = element;
		m_ID = ID;
		m_pNext = NULL;
		m_pPrev = NULL;
}

template <class TypeData> SM_ListenElement<TypeData>::SM_ListenElement()
{
}

template <class TypeData> SM_ListenElement<TypeData>::~SM_ListenElement()
{
}


#endif
