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

#ifndef SM_SUCHBAUM_H
#define SM_SUCHBAUM_H


/**
  *@author Mathias Sikos
  */
#include "sm_baumknoten.h"

template <class TypeKeyClass, class TypeKey, class TypeData>
class SM_SuchBaum
{
public:
	SM_SuchBaum();
	~SM_SuchBaum();
	TypeData* getElement(TypeKey key);
	int get_m_iTeilbaum();
	int insert(TypeKey key, TypeData* element);
	int remove(TypeKey key);

protected:
	int Ausgleichen(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten);
	SM_BaumKnoten<TypeKey, TypeData>* BalanceWerteNachInsert(SM_BaumKnoten<TypeKey, TypeData>* knoten);
	SM_BaumKnoten<TypeKey, TypeData>* BalanceWerteNachRemove(SM_BaumKnoten<TypeKey, TypeData>* knoten);
	void DeleteTree(SM_BaumKnoten<TypeKey, TypeData>* knoten);
	void ErsetzeKnoten(SM_BaumKnoten<TypeKey, TypeData>* knotenAlt, SM_BaumKnoten<TypeKey, TypeData>* knotenNeu);
	int LinksRechtsRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten);
	int LinksRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten);
	int RechtsLinksRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten);
	int RechtsRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten);
	SM_BaumKnoten<TypeKey, TypeData>* SucheElement(TypeKey key);

	int m_iTeilbaum;
	SM_BaumKnoten<TypeKey, TypeData>* m_pWurzelKnoten;
};

#endif

