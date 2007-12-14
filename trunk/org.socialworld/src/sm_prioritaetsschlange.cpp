/***************************************************************************
                          sm_prioritaetsschlange.cpp  -  description
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
#ifndef SM_PRIORITAETSSCHLANGE_CPP
#define SM_PRIORITAETSSCHLANGE_CPP


#include "sm_prioritaetsschlange.h"

template <class TypeData> SM_PrioritaetsSchlange<TypeData>::SM_PrioritaetsSchlange()
{
			m_pPrioritaetsListe = 0;
			m_pListenKopf = 0;
}
template <class TypeData> SM_PrioritaetsSchlange<TypeData>::~SM_PrioritaetsSchlange()
{

}

template <class TypeData> int SM_PrioritaetsSchlange<TypeData>::insert(TUChar uPrioritaet, TypeData* element)
{
			SM_PrioritaetsSchlangenElement<TypeData>* pPrioritaetsSchlangenElement =
						new SM_PrioritaetsSchlangenElement<TypeData>(uPrioritaet, element);

      if (m_pListenKopf == 0)
			{
						m_pListenKopf = pPrioritaetsSchlangenElement;
						m_pPrioritaetsListe = new SM_PrioritaetsElement<TypeData>(uPrioritaet);
            m_pPrioritaetsListe->m_pSchlangenElementHighPriority = pPrioritaetsSchlangenElement;
            m_pPrioritaetsListe->m_pSchlangenElementLowPriority = pPrioritaetsSchlangenElement;
            return 2;          // Element ist erstes Element Schlange initialisiert
      }

      if  (m_pPrioritaetsListe->m_uPrioritaet < uPrioritaet)
      {
          SM_PrioritaetsElement<TypeData>* pPrioritaetsElementNew = new SM_PrioritaetsElement<TypeData>(uPrioritaet);
					pPrioritaetsElementNew->m_pNext =  m_pPrioritaetsListe;

          pPrioritaetsElementNew->m_pSchlangenElementHighPriority = pPrioritaetsSchlangenElement;
          pPrioritaetsElementNew->m_pSchlangenElementLowPriority = pPrioritaetsSchlangenElement;

    			pPrioritaetsSchlangenElement->m_pNext = m_pPrioritaetsListe->m_pSchlangenElementHighPriority;
          m_pPrioritaetsListe =  pPrioritaetsElementNew;
          return 3;        // Element eingefügt - Neues Prioritaetselemen

      }

      SM_PrioritaetsElement<TypeData>* pPrioritaetsElement = m_pPrioritaetsListe;

      bool bNeuesPrioritaetselement = true;
      bool lb_next_element = true;
      
			while (pPrioritaetsElement->m_uPrioritaet >= uPrioritaet && lb_next_element)
      {
            bNeuesPrioritaetselement = false;
            
            if (pPrioritaetsElement->m_pNext == 0)
            {
                  if (pPrioritaetsElement->m_uPrioritaet > uPrioritaet)
                      bNeuesPrioritaetselement = true;
                  lb_next_element = false;
            }
            else
            {
                if (pPrioritaetsElement->m_pNext->m_uPrioritaet < uPrioritaet)
                {
                    lb_next_element = false;
                    if (pPrioritaetsElement->m_uPrioritaet > uPrioritaet)
                      bNeuesPrioritaetselement = true;
               }
            }
			      if (lb_next_element) pPrioritaetsElement = pPrioritaetsElement->m_pNext;
      }
      
      if (bNeuesPrioritaetselement)
      {
          SM_PrioritaetsElement<TypeData>* pPrioritaetsElementNew = new SM_PrioritaetsElement<TypeData>(uPrioritaet);
					pPrioritaetsElementNew->m_pNext =  pPrioritaetsElement->m_pNext;
          pPrioritaetsElement->m_pNext = pPrioritaetsElementNew;
          
          pPrioritaetsElementNew->m_pSchlangenElementHighPriority = pPrioritaetsSchlangenElement;
          pPrioritaetsElementNew->m_pSchlangenElementLowPriority = pPrioritaetsSchlangenElement;

          if (not pPrioritaetsElementNew->m_pNext == 0)
    			    pPrioritaetsSchlangenElement->m_pNext = pPrioritaetsElementNew->m_pNext->m_pSchlangenElementHighPriority;
          pPrioritaetsElement->m_pSchlangenElementLowPriority->m_pNext = pPrioritaetsSchlangenElement;
          return 1;        // Element eingefügt - Neues Prioritaetselement
      }
      else
      {
          pPrioritaetsElement->m_pSchlangenElementLowPriority->m_pNext = pPrioritaetsSchlangenElement;
          pPrioritaetsElement->m_pSchlangenElementLowPriority = pPrioritaetsSchlangenElement;
          if (not pPrioritaetsElement->m_pNext == 0)
            pPrioritaetsSchlangenElement->m_pNext = pPrioritaetsElement->m_pNext->m_pSchlangenElementHighPriority;
         return 0;        // Element eingefügt - kein neues Prioritaetselement
      }
}

template <class TypeData> TypeData* SM_PrioritaetsSchlange<TypeData>::getElement()
{
			if (m_pListenKopf == NULL)
					return NULL;

			return m_pListenKopf->m_pData;
}

template <class TypeData> TypeData* SM_PrioritaetsSchlange<TypeData>::remove()
{
			if (m_pListenKopf == NULL)
					return NULL;

			SM_PrioritaetsSchlangenElement<TypeData>* pPrioritaetsSchlangenElementTmp = m_pListenKopf;
			TypeData* pDataTmp = m_pListenKopf->m_pData;
      m_pListenKopf = m_pListenKopf->m_pNext;
      delete pPrioritaetsSchlangenElementTmp;
			return pDataTmp;
}

#endif
