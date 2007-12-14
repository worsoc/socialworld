/***************************************************************************
                          sm_hashtabelle.h  -  description
                             -------------------
    begin                : Tue May 20 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef SM_HASHTABELLE_H
#define SM_HASHTABELLE_H

#include "sm_listenelement.cpp"

/**
  *@author Mathias Sikos
  */

typedef int THash;
typedef unsigned long int THashID;



template <class TypeData>
class SM_HashTabelle
{
public: 
	SM_HashTabelle	(THash iGroesse);
	           // iGroesse ... Anzahl der Listen (Zeilen) ( Modulo-operand )
  ~SM_HashTabelle();

	void 													insert					(THashID ID, TypeData* element);
  TypeData*  getElement			(THashID ID);

protected:
	THash													findElement		(THashID ID);
	THash 												hash						(THashID ID);

  SM_ListenElement<TypeData>*	  bestPointer 	(THash index, THashID ID,	bool& bVorwaerts, int& iDifferenz);

	SM_ListenElement<TypeData>** 	m_TabellenKopf;
  SM_ListenElement<TypeData>** 	m_TabellenEnde;
	SM_ListenElement<TypeData>**	m_Aktuell;

	THash 												m_Groesse;
private:
//	SM_ListenElement<TypeData>*	  m_p_NULL_Dummy;
};

#endif
