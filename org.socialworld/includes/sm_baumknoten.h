/***************************************************************************
                          sm_baumknoten.h  -  description
                             -------------------
    begin                : Thu May 29 2003
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

#ifndef SM_BAUMKNOTEN_H
#define SM_BAUMKNOTEN_H


/**
  *@author Mathias Sikos
  */

  
template <class TypeKey, class TypeData>
class SM_BaumKnoten
{
public: 
		SM_BaumKnoten																			(TypeKey key, TypeData* element);
		~SM_BaumKnoten();

		TypeKey   												m_Key;
		TypeData*   											m_pData;

		SM_BaumKnoten<TypeKey,TypeData>*  m_pLinkerTB;
		SM_BaumKnoten<TypeKey,TypeData>*  m_pRechterTB;
    SM_BaumKnoten<TypeKey,TypeData>*  m_pVaterKnoten;

		int 															m_iTeilbaum;
		unsigned int											m_uLaengeLinkerTB;
    unsigned int											m_uLaengeRechterTB;
    char 															m_iBalance;

};

#endif
