/***************************************************************************
 sm_hashtabelle.cpp  -  description
 -------------------
 begin                : Tue May 20 2003
 copyright            : (C) 2003 by Mathias Sikos
 email                : sikosmathias@aol.com
 ***************************************************************************/
#ifndef SM_HASHTABELLE_CPP
#define SM_HASHTABELLE_CPP

#include "sm_hashtabelle.h"

template<class TypeData> SM_HashTabelle<TypeData>::SM_HashTabelle(THash iGroesse) {
	m_TabellenKopf = new SM_ListenElement<TypeData>*[iGroesse];
	m_TabellenEnde = new SM_ListenElement<TypeData>*[iGroesse];
	m_Aktuell = new SM_ListenElement<TypeData>*[iGroesse];

	//		m_p_NULL_Dummy  = new SM_ListenElement<TypeData>();

	for (THash i = 0; i<iGroesse; i++) {
		m_TabellenKopf[i] = NULL; //m_p_NULL_Dummy;
		m_TabellenEnde[i] = NULL; //m_p_NULL_Dummy;
		m_Aktuell[i] = NULL; //m_p_NULL_Dummy;
	}

	m_Groesse = iGroesse;

}

template<class TypeData> SM_HashTabelle<TypeData>::~SM_HashTabelle() {
}

template<class TypeData> void SM_HashTabelle<TypeData>::insert(THashID ID,
		TypeData* element) {
	THash index = hash(ID);

	SM_ListenElement<TypeData>* listenElement = new SM_ListenElement<TypeData>(ID, element);

	if (m_TabellenKopf[index] == NULL) {
		m_TabellenKopf[index] = listenElement;
	} else if (m_TabellenEnde[index] != NULL) {
		listenElement->m_pPrev = m_TabellenEnde[index];
		listenElement->m_pPrev->m_pNext = listenElement;
	}

	m_TabellenEnde[index] = listenElement;
}

template<class TypeData> TypeData* SM_HashTabelle<TypeData>::getElement(
		THashID ID) {
	THash index = findElement(ID);
	return ((index >= 0) ? m_Aktuell[index]->m_pData : NULL);
}

template<class TypeData> THash SM_HashTabelle<TypeData>::findElement(THashID ID) {
	THash index = hash(ID);
	bool bVorwaerts = true;
	int iDifferenz = 0;

	SM_ListenElement<TypeData>* listenElement = bestPointer(index, ID,
			bVorwaerts, iDifferenz);

	if (iDifferenz == 0) {
		m_Aktuell[index] = listenElement;
		if (listenElement != NULL)
			if (listenElement->m_ID == ID)
				return index;
			else
				return -1;
		else
			return -1;
	}

	if (iDifferenz < 0)
		iDifferenz = -iDifferenz;
	for (int i = 0; i < iDifferenz; i++) {
		if (listenElement)
			if (bVorwaerts)
				if (listenElement->m_pNext)
					listenElement = listenElement->m_pNext;
				else
					return -1;
			else if (listenElement->m_pPrev)
				listenElement = listenElement->m_pPrev;
			else
				return -1;
	}

	m_Aktuell[index] = listenElement;
	if (listenElement != NULL) {
		if (listenElement->m_ID == ID)
			return index;
		else
			return -1;
	} else
		return -1;

}

template<class TypeData> THash SM_HashTabelle<TypeData>::hash(THashID ID) {
	THash listenNummer = ID % m_Groesse;
	return listenNummer;
}

template<class TypeData> SM_ListenElement<TypeData>* SM_HashTabelle<TypeData>::bestPointer(
		THash index, THashID ID, bool& bVorwaerts, int& iDifferenz) {
	SM_ListenElement<TypeData>* listenElement;

	THash iPosKopf = 0;
	THash iPosEnde;
	if (m_TabellenEnde[index] != NULL)
		iPosEnde = (THash)(m_TabellenEnde[index]->m_ID / m_Groesse);
	else
		iPosEnde = 0;
	THash iPosAktuell;
	if (m_Aktuell[index] == NULL) {
		iPosAktuell = 0;
		m_Aktuell[index] = m_TabellenKopf[index];
	} else
		iPosAktuell = (THash)(m_Aktuell[index]->m_ID / m_Groesse);

	THash iPositionID = (THash)(ID / m_Groesse);

	int iDif_Ende_ID = iPosEnde - iPositionID;
	int iDif_ID_Kopf = iPositionID - iPosKopf;

	if ((iDif_Ende_ID) > (iDif_ID_Kopf)) // dichter am Kopf
	{
		int iDif_Akt_ID = iPosAktuell - iPositionID;
		if ((iDif_Akt_ID) > iDif_ID_Kopf) // dichter am Kopf
		{
			listenElement = m_TabellenKopf[index];
			iDifferenz = iDif_ID_Kopf;
		} else // dichter am Aktuellen
		{
			listenElement = m_Aktuell[index];
			if (iPosAktuell > iPositionID)
				bVorwaerts = false;
			iDifferenz = iDif_Akt_ID;
		}
	} else // dichter am Ende
	{
		int iDif_ID_Akt = iPositionID - iPosAktuell;
		if ((iDif_ID_Akt) > iDif_Ende_ID) // dichter am Ende
		{
			listenElement = m_TabellenEnde[index];
			bVorwaerts = false;
			iDifferenz = iDif_Ende_ID;
		} else // dichter am Aktuellen
		{
			listenElement = m_Aktuell[index];
			if (iPosAktuell > iPositionID)
				bVorwaerts = false;
			iDifferenz = iDif_ID_Akt;
		}
	}
	return listenElement;
}

/*
 while (listenElement)
 {
 if (listenElement->m_ID == ID)
 {
 m_Aktuell = listenElement;
 return &(listenElement->m_data);
 }
 listenElement = listenElement->m_pNext;
 }
 return NULL;
 */

#endif
