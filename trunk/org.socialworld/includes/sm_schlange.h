/***************************************************************************
                          sm_schlange.h  -  description
                             -------------------
    begin                : Wed Jun 11 2003
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

#ifndef SM_SCHLANGE_H
#define SM_SCHLANGE_H


/**
  *@author Mathias Sikos
  */

#include "sm_schlangenelement.cpp"

template <class TypeData>
class SM_Schlange
{
public: 
	SM_Schlange();
	~SM_Schlange();

	int insert(TypeData* element);
	TypeData* getElement();
	TypeData* remove();

protected:
	SM_SchlangenElement<TypeData>* m_pListenKopf;
	SM_SchlangenElement<TypeData>* m_pListenEnde;
};

#endif
