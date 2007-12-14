/***************************************************************************
                          sm_kettemitkopf.cpp  -  description
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

#include "sm_kettemitkopf.h"


template <class TypeData>
SM_KetteMitKopf<TypeData>::SM_KetteMitKopf(THashID dummyID, TypeData* dummy)
		:SM_Kette<TypeData>(dummyID,dummy)
{
     m_pListenKopf = m_pAktuell;
}

template <class TypeData> SM_KetteMitKopf<TypeData>::~SM_KetteMitKopf()
{
}


template <class TypeData>	SM_ListenElement<TypeData>*
SM_KetteMitKopf<TypeData>::insertNext(TypeData* element) {
	SM_ListenElement<TypeData>* pElementTmp =
    SM_Kette<TypeData>::insertNext(element);
  if (m_pListenKopf == 0)
          m_pListenKopf = m_pAktuell;
	return pElementTmp;
}

template <class TypeData>	SM_ListenElement<TypeData>*
SM_KetteMitKopf<TypeData>::insertPrev(TypeData* element) {
	SM_ListenElement<TypeData>* pElementTmp =
    SM_Kette<TypeData>::insertPrev(element);
  if (m_pListenKopf == 0)
          m_pListenKopf = m_pAktuell;
	return pElementTmp;
}


template <class TypeData> TypeData* SM_KetteMitKopf<TypeData>::getHead()
{
		return m_pListenKopf->m_pData;
}

template <class TypeData> SM_ListenElement<TypeData>* SM_KetteMitKopf<TypeData>::getListElementHead()
{
		return m_pListenKopf;
}

