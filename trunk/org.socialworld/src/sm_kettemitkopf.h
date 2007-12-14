/***************************************************************************
                          sm_kettemitkopf.h  -  description
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

#ifndef SM_KETTEMITKOPF_H
#define SM_KETTEMITKOPF_H

#include "sm_kette.cpp"

/**
  *@author Mathias Sikos
  */
template <class TypeData>
class SM_KetteMitKopf : public SM_Kette<TypeData>
{
public: 
	SM_KetteMitKopf(THashID dummyID, TypeData* dummy);
	virtual ~SM_KetteMitKopf();

	virtual SM_ListenElement<TypeData>* insertNext(TypeData* element);
	virtual SM_ListenElement<TypeData>* insertPrev(TypeData* element);
	void deleteHead();
	TypeData* getHead();
	SM_ListenElement<TypeData>* getListElementHead();

protected:
	SM_ListenElement<TypeData>* m_pListenKopf;	
};

#endif
