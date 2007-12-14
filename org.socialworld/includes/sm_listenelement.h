/***************************************************************************
                          sm_listenelement.h  -  description
                             -------------------
    begin                : Mon May 19 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef SM_LISTENELEMENT_H
#define SM_LISTENELEMENT_H


/**
  *@author Mathias Sikos
  */
typedef unsigned long int THashID;

// changed on 06.01.2004 / why included psbws_person.h ?
// #include "psbws_person.h"

template <class TypeData>
class  SM_ListenElement
{
public: 
		
    SM_ListenElement				(THashID ID, TypeData* element);
	SM_ListenElement();
	virtual ~SM_ListenElement();

    SM_ListenElement<TypeData>*		 m_pNext;
    SM_ListenElement<TypeData>*		 m_pPrev;

    THashID							 m_ID;
    TypeData* 						 m_pData;

};

#endif

