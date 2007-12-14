/***************************************************************************
                          sm_schlangenelement.h  -  description
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

#ifndef SM_SCHLANGENELEMENT_H
#define SM_SCHLANGENELEMENT_H


/**
  *@author Mathias Sikos
  */
template <class TypeData>
class SM_SchlangenElement
{
public: 
	SM_SchlangenElement(TypeData* element);
	~SM_SchlangenElement();

	TypeData* m_pData;
  SM_SchlangenElement<TypeData>* m_pNext;
};

#endif
