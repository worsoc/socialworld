/***************************************************************************
                          sm_liste.cpp  -  description
                             -------------------
    begin                : Tue May 27 2003
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

#include "sm_liste.h"

template <class TypeData> SM_Liste<TypeData>::SM_Liste()
{
		m_ListenKopf = NULL;
		m_ListenEnde = NULL;

		m_AnzahlElemente = 0;
}

template <class TypeData> SM_Liste<TypeData>::~SM_Liste()
{
}

template <class TypeData> void SM_Liste<TypeData>::insert(THashID ID, TypeData* element)
{
		
		SM_ListenElement<TypeData>* listenElement = new SM_ListenElement<TypeData>(m_AnzahlElemente, element);

		if (m_ListenKopf == NULL)
		{
				m_ListenKopf = listenElement;
				listenElement->m_pPrev = NULL;
		}
    else
		{
		listenElement->m_pPrev = m_ListenEnde;
		listenElement->m_pPrev->m_pNext = listenElement;
    }

		listenElement->m_pNext = NULL;
		m_ListenEnde = listenElement;

		m_AnzahlElemente++;
}

template <class TypeData> TypeData* SM_Liste<TypeData>::getElement(THashID ID, TUChar flag)
{
		SM_ListenElement<TypeData>* listenElement = m_ListenKopf;

		if (listenElement == NULL)
					return NULL;

		while (true)
		{
				if (listenElement->m_ID == ID)
				{
						  switch (flag)
							{
										case 1: asHeadElement(listenElement);
														break;
										case 2: preElement(listenElement);
														break;
										default : break;
              }
							return listenElement->m_pData;
				}
				if  (listenElement->m_pNext == NULL)
						 break;
				listenElement = listenElement->m_pNext;
		}
		return NULL;
}

template <class TypeData> void SM_Liste<TypeData>::asHeadElement(SM_ListenElement<TypeData>* listenElement)
{
		if (listenElement != m_ListenKopf)
		{
					listenElement->m_pPrev->m_pNext = listenElement->m_pNext;
					listenElement->m_pNext->m_pPrev = listenElement->m_pPrev;
       		listenElement->m_pPrev = NULL;
					listenElement->m_pNext = m_ListenKopf;
          m_ListenKopf = listenElement;
		}
}

template <class TypeData> void SM_Liste<TypeData>::preElement(SM_ListenElement<TypeData>* listenElement)
{
		if (listenElement != m_ListenKopf)
		{
					SM_ListenElement<TypeData>* tmpListenElement;
          tmpListenElement = listenElement->m_pPrev;
          if (tmpListenElement->m_pPrev != NULL)
          			tmpListenElement->m_pPrev->m_pNext = listenElement;
          if (listenElement->m_pNext != NULL)
								listenElement->m_pNext->m_pPrev = tmpListenElement;
					tmpListenElement->m_pNext = tmpListenElement->m_pNext->m_pNext;
					listenElement->m_pNext = listenElement->m_pPrev;
					listenElement->m_pPrev = listenElement->m_pPrev->m_pPrev;
 	    		tmpListenElement->m_pPrev = listenElement;
 	        if (tmpListenElement == m_ListenKopf)
  			    		m_ListenKopf = listenElement;
		}
}

