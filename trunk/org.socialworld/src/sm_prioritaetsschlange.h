/***************************************************************************
                          sm_prioritaetsschlange.h  -  description
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

#ifndef SM_PRIORITAETSSCHLANGE_H
#define SM_PRIORITAETSSCHLANGE_H


/**
  *@author Mathias Sikos
  */
#include "sm_prioritaetselement.cpp"
#include "sm_prioritaetsschlangenelement.cpp"

template <class TypeData>
class SM_PrioritaetsSchlange
{
public: 
	SM_PrioritaetsSchlange();
	~SM_PrioritaetsSchlange();

  int insert(TUChar uPrioritaet, TypeData* element);
	TypeData* getElement();
	TypeData* remove();

	SM_PrioritaetsElement<TypeData>* m_pPrioritaetsListe;

	SM_PrioritaetsSchlangenElement<TypeData>* m_pListenKopf;

};

#endif
