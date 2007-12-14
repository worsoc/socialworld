/***************************************************************************
                          sm_indexliste.cpp  -  description
                             -------------------
    begin                : Mon Jun 23 2003
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

#include "sm_indexliste.h"


template <class TypeData> SM_IndexListe<TypeData>::SM_IndexListe(THash iGroesse )
{
    m_TabellenKopf = new SM_ListenElement<TypeData>[iGroesse];
		m_TabellenEnde = new SM_ListenElement<TypeData>[iGroesse];

		m_Groesse = iGroesse;

		for (THash i = 0; i<iGroesse; i++)
    {
				m_TabellenKopf[i].m_pNext = NULL;
				m_TabellenKopf[i].m_pPrev = NULL;  // wird nicht benoetigt
  	 		m_TabellenEnde[i].m_pNext = NULL;
  	 		m_TabellenEnde[i].m_pPrev = NULL;  // wird nicht benoetigt

		}
}

template <class TypeData> SM_IndexListe<TypeData>::~SM_IndexListe()
{
}

template <class TypeData> void SM_IndexListe<TypeData>::insert(THashID ID, TypeData* element)
{
		THash index = hash(ID);
		
		SM_ListenElement<TypeData>* listenElement = new SM_ListenElement<TypeData>(ID, element);

		if (m_TabellenKopf[index].m_pNext == NULL)
		{
				m_TabellenKopf[index].m_pNext = listenElement;
		}
    else
		{
		listenElement->m_pPrev = m_TabellenEnde[index].m_pNext;
		listenElement->m_pPrev->m_pNext = listenElement;
    }
		m_TabellenEnde[index].m_pNext = listenElement;
}

template <class TypeData> TypeData* SM_IndexListe<TypeData>::getElement(THashID ID)
{
    THash index = hash(ID);
    SM_ListenElement<TypeData>* listenElement = m_TabellenKopf[index].m_pNext;
		while (listenElement != 0)
		{
				if (listenElement->m_ID == ID)
						return listenElement->m_pData;		
				listenElement = listenElement->m_pNext;
		}
    return 0;
}

template <class TypeData> THash SM_IndexListe<TypeData>::hash(THashID ID)
{
		THash listenNummer = ID % m_Groesse;
		return listenNummer;
}
