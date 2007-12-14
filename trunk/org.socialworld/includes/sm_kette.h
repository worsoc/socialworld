/***************************************************************************
                          sm_kette.h  -  description
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

#ifndef SM_KETTE_H
#define SM_KETTE_H


/**
  *@author Mathias Sikos
  */

#include "sm_listenelement.cpp"

template <class TypeData>
class SM_Kette
{
public: 
	SM_Kette(THashID dummyID, TypeData* dummy);
	virtual ~SM_Kette();

  bool next();
	bool prev();
	virtual  SM_ListenElement<TypeData>* insertNext(TypeData* element);
	virtual  SM_ListenElement<TypeData>* insertPrev(TypeData* element);
	void deleteActual(bool bAktuellIsNext = true);
  TypeData* getActual();
  SM_ListenElement<TypeData>* getListElementActual();

protected:
  SM_ListenElement<TypeData>* m_pAktuell;
	SM_ListenElement<TypeData>* m_pPointerSaved;
};

#endif
