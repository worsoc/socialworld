/***************************************************************************
                          sm_liste.h  -  description
                             -------------------
    begin                : Tue May 27 2003
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

#ifndef SM_LISTE_H
#define SM_LISTE_H


/**
  *@author Mathias Sikos
  */

#include "sm_listenelement.h"

typedef unsigned long int THashID;
typedef int THash;
typedef unsigned long TNatural;
typedef unsigned char TUChar;

template <class TypeData>
class SM_Liste
{
public: 
	
	SM_Liste();
	virtual ~SM_Liste();

	void 				insert					(THashID ID, TypeData* element);
  TypeData* 			getElement			(THashID ID, TUChar flag = 0);

  void 					asHeadElement	(SM_ListenElement<TypeData>* listenElement);
  void 					preElement		(SM_ListenElement<TypeData>* listenElement);

protected:
	THash											findElement		(THashID ID);


  SM_ListenElement<TypeData>*		m_ListenKopf;
  SM_ListenElement<TypeData>* 	m_ListenEnde;

	TNatural 									m_AnzahlElemente;
};

#endif
