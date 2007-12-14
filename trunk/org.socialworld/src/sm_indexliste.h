/***************************************************************************
                          sm_indexliste.h  -  description
                             -------------------
    begin                : Mon Jun 23 2003
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

#ifndef SM_INDEXLISTE_H
#define SM_INDEXLISTE_H


/**
  *@author Mathias Sikos
  */


#include "sm_listenelement.h"

typedef int THash;
typedef unsigned long int THashID;

template <class TypeData>
class SM_IndexListe
{
public: 
	SM_IndexListe(THash iGroesse);
	~SM_IndexListe();

  void insert(THashID ID, TypeData* element);
	TypeData* getElement(THashID ID);
protected:
	THash hash(THashID ID);

	SM_ListenElement<TypeData>* 	m_TabellenKopf;
  SM_ListenElement<TypeData>* 	m_TabellenEnde;

	THash 												m_Groesse;

};

#endif
