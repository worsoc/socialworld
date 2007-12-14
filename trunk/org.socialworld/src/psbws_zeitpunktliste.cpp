/***************************************************************************
                          psbws_zeitpunktliste.cpp  -  description
                             -------------------
    begin                : Sun Jun 22 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_zeitpunktliste.h"

template <class TypeData> PSBWS_TimeList<TypeData>::PSBWS_TimeList()
			: SM_Liste<TypeData>()
{
}

template <class TypeData> PSBWS_TimeList<TypeData>::~PSBWS_TimeList()
{
}

template <class TypeData> SM_ListenElement<TypeData>*
PSBWS_TimeList<TypeData>::get_listElement(THashID au_ID, TUChar au_flag) {
		SM_ListenElement<TypeData>* lSM_listElement = m_ListenKopf;

		if (llSM_listElement == NULL)
					return NULL;

		while (true)
		{
				if (lSM_listElement->m_ID == ID)
							return listenElement;
				if (lSM_listElement->m_pNext == NULL)
						 break;
				lSM_listElement = lSM_listElement->m_pNext;
		}
		return NULL;
}
