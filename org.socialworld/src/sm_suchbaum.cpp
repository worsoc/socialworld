/***************************************************************************
                          sm_suchbaum.cpp  -  description
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

#include "sm_suchbaum.h"


template <class TypeKeyClass, class TypeKey, class TypeData>
					 SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::SM_SuchBaum()
{
			m_pWurzelKnoten = NULL;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
					 SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::~SM_SuchBaum()
{
			DeleteTree(m_pWurzelKnoten);
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
					 int 	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::insert(TypeKey key, TypeData* element)
{
      SM_BaumKnoten<TypeKey, TypeData>* knotenNeu = new SM_BaumKnoten<TypeKey, TypeData>(key, element);

      SM_BaumKnoten<TypeKey, TypeData>* knoten = SucheElement(key);
      SM_BaumKnoten<TypeKey, TypeData>* knotenAlteWurzel;
      
      if (knoten == NULL)
           if  (m_iTeilbaum == 0)
           {
							  m_pWurzelKnoten = knotenNeu;
								return 0;           //  0 ... Element wurde korrekt eingefuegt
					 }
					 else return -1;          // -1 ... Fehler
	
			switch (m_iTeilbaum)
			{
			case  -1:  knotenNeu->m_pVaterKnoten = knoten;
								 knotenNeu->m_iTeilbaum = -1;
								 knoten->m_pLinkerTB = knotenNeu;
								 break;
			case   1:  knotenNeu->m_pVaterKnoten = knoten;
								 knotenNeu->m_iTeilbaum = 1;
								 knoten->m_pRechterTB = knotenNeu;
								 break;
			case   0:  return 1;          //  1 ... Element ist schon enthalten
			default :  return -2;         // -2 ... Fehler
      }

      knotenAlteWurzel = BalanceWerteNachInsert(knotenNeu);
      Ausgleichen(false, knotenAlteWurzel);    // Ausgleichen nach Insert
			return 0;                     //  0 ... Element wurde korrekt eingefuegt
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
					 TypeData* SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::getElement(TypeKey key)
{
      SM_BaumKnoten<TypeKey, TypeData>* knoten = SucheElement(key);

      if (knoten == NULL)
						return NULL;
			else
      if (m_iTeilbaum == 0)
      			return knoten->m_pData;
      else return NULL;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
					 int 	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::remove(TypeKey key)
{
 			SM_BaumKnoten<TypeKey, TypeData>* knoten = SucheElement(key);
      SM_BaumKnoten<TypeKey, TypeData>* knotenTmp = knoten;
      SM_BaumKnoten<TypeKey, TypeData>* knotenAlteWurzel;
      
      if (knoten != NULL && m_iTeilbaum == 0)   // Knoten mit Schluessel key gefunden
					if (knoten->m_iBalance < 0)   // linker Teilbaum laenger  				
          {
							knoten = knoten->m_pLinkerTB;
			        while (knoten->m_pRechterTB != NULL)
										knoten = knoten->m_pRechterTB;
              knotenAlteWurzel = BalanceWerteNachRemove(knoten);
              Ausgleichen(true, knotenAlteWurzel);
							ErsetzeKnoten(knotenTmp, knoten);
              knoten->m_pVaterKnoten->m_pRechterTB = NULL;
							delete knoten;
							return 1;
					}
					else
					if (knoten->m_pRechterTB != NULL)
          {
							knoten = knoten->m_pRechterTB;
			        while (knoten->m_pLinkerTB != NULL)
									knoten = knoten->m_pLinkerTB;
              knotenAlteWurzel = BalanceWerteNachRemove(knoten);
              Ausgleichen(true, knotenAlteWurzel);
							ErsetzeKnoten(knotenTmp, knoten);
              knoten->m_pVaterKnoten->m_pLinkerTB = NULL;
              delete knoten;
							return 2;
					}
					else            // knoten ist Blatt
					{
              knotenAlteWurzel = BalanceWerteNachRemove(knoten);
              Ausgleichen(true, knotenAlteWurzel);
              if (knoten->m_iTeilbaum == -1)
                  knoten->m_pVaterKnoten->m_pLinkerTB = NULL;
              else
                  knoten->m_pVaterKnoten->m_pRechterTB = NULL;
              delete knoten;
							return 0;
					}
      else		
					return -1;			
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			SM_BaumKnoten<TypeKey, TypeData>*
					SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::SucheElement(TypeKey key)
{
      if (m_pWurzelKnoten == NULL)
			{
		        m_iTeilbaum = 0;
						return NULL;
			}	
			
			SM_BaumKnoten<TypeKey, TypeData>* knoten = m_pWurzelKnoten;

			TypeKeyClass* pKeyKnotenNeu = new TypeKeyClass(key);

			while (true)
			{
						
						if (pKeyKnotenNeu->isLessThan(knoten->m_Key))         // ( key < knoten->m_Key ) ?
                  if (knoten->m_pLinkerTB != NULL)
												knoten = knoten->m_pLinkerTB;
									else
                  {
												delete pKeyKnotenNeu;
												m_iTeilbaum = -1;      // nicht enthalten, gehoert in den linken Teilbaum
												return knoten;
                  }
						else
						{
									if (pKeyKnotenNeu->isEqual(knoten->m_Key))  // Element ist schon enthalten
									{
												delete pKeyKnotenNeu;
												m_iTeilbaum = 0;      //  Element gefunden
												return knoten;
                  }
									if (knoten->m_pRechterTB != NULL)
												knoten = knoten->m_pRechterTB;
									else
									{
												delete pKeyKnotenNeu;
                        m_iTeilbaum = 1;     // nicht enthalten, gehoert in den rechten Teilbaum
												return knoten;
                  }
            }
      }
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			void SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::ErsetzeKnoten
									(SM_BaumKnoten<TypeKey, TypeData>* knotenAlt, SM_BaumKnoten<TypeKey, TypeData>* knotenNeu)
{
			knotenAlt->m_pData = knotenNeu->m_pData;
			knotenAlt->m_Key = knotenNeu->m_Key;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::Ausgleichen(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten)
{

			if (knoten == NULL)
				  return 0;        // keine Rotation, da nicht notwendig

			if (knoten->m_iBalance == -2)
			
					if (knoten->m_pLinkerTB->m_iBalance <= 0)
							return RechtsRotation(bRemove, knoten);
					else
							return LinksRechtsRotation(bRemove, knoten);

			else
					if (knoten->m_pRechterTB->m_iBalance >= 0)
							return LinksRotation(bRemove, knoten);
					else
							return RechtsLinksRotation(bRemove, knoten);

}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::RechtsRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			SM_BaumKnoten<TypeKey, TypeData>* pUTmp = knoten->m_pLinkerTB;
      SM_BaumKnoten<TypeKey, TypeData>* knotenAusgleichen;

			if (knoten == m_pWurzelKnoten)
						m_pWurzelKnoten = pUTmp;

      pUTmp->m_pVaterKnoten = knoten->m_pVaterKnoten;
			pUTmp->m_iTeilbaum = knoten->m_iTeilbaum;
			if (pUTmp->m_pVaterKnoten != NULL)
      		if (pUTmp->m_iTeilbaum < 0)
							pUTmp->m_pVaterKnoten->m_pLinkerTB = pUTmp;
      		else
          		pUTmp->m_pVaterKnoten->m_pRechterTB = pUTmp;

			unsigned int uLaengeTB = pUTmp->m_uLaengeRechterTB;

      knoten->m_pLinkerTB = pUTmp->m_pRechterTB;
			if (knoten->m_pLinkerTB != NULL)
			{
					knoten->m_pLinkerTB->m_pVaterKnoten = knoten;
					knoten->m_pLinkerTB->m_iTeilbaum = -1;
			}
			knoten->m_pVaterKnoten = pUTmp;
			knoten->m_iTeilbaum = 1;
			knoten->m_uLaengeLinkerTB = uLaengeTB;
			knoten->m_iBalance = knoten->m_uLaengeRechterTB - knoten->m_uLaengeLinkerTB;

			pUTmp->m_pRechterTB = knoten;

			if (knoten->m_iBalance < 0)
					pUTmp->m_uLaengeRechterTB = uLaengeTB + 1;
			else
					pUTmp->m_uLaengeRechterTB = knoten->m_uLaengeRechterTB + 1;
			pUTmp->m_iBalance = pUTmp->m_uLaengeRechterTB - pUTmp->m_uLaengeLinkerTB;

      if (bRemove)
      {
          knotenAusgleichen = BalanceWerteNachRemove(pUTmp);
          if (knotenAusgleichen != NULL)
              Ausgleichen(true, knotenAusgleichen);
      }
      
      return 1;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::LinksRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			SM_BaumKnoten<TypeKey, TypeData>* pVTmp = knoten->m_pRechterTB;
      SM_BaumKnoten<TypeKey, TypeData>* knotenAusgleichen;

			if (knoten == m_pWurzelKnoten)
						m_pWurzelKnoten = pVTmp;

      pVTmp->m_pVaterKnoten = knoten->m_pVaterKnoten;
			pVTmp->m_iTeilbaum = knoten->m_iTeilbaum;
			if (pVTmp->m_pVaterKnoten != NULL)
      		if (pVTmp->m_iTeilbaum < 0)
							pVTmp->m_pVaterKnoten->m_pLinkerTB = pVTmp;
    		  else
     			    pVTmp->m_pVaterKnoten->m_pRechterTB = pVTmp;

			unsigned int uLaengeTB = pVTmp->m_uLaengeLinkerTB;

      knoten->m_pRechterTB = pVTmp->m_pLinkerTB;
			if (knoten->m_pRechterTB != NULL)
			{
					knoten->m_pRechterTB->m_pVaterKnoten = knoten;
					knoten->m_pRechterTB->m_iTeilbaum = 1;
			}
			knoten->m_pVaterKnoten = pVTmp;
			knoten->m_iTeilbaum = -1;
			knoten->m_uLaengeRechterTB = uLaengeTB;
			knoten->m_iBalance = knoten->m_uLaengeRechterTB - knoten->m_uLaengeLinkerTB;

			pVTmp->m_pLinkerTB = knoten;

			if (knoten->m_iBalance > 0)
					pVTmp->m_uLaengeLinkerTB = uLaengeTB + 1;
			else
					pVTmp->m_uLaengeLinkerTB = knoten->m_uLaengeLinkerTB + 1;
			pVTmp->m_iBalance = pVTmp->m_uLaengeRechterTB - pVTmp->m_uLaengeLinkerTB;

      if (bRemove)
      {
          knotenAusgleichen = BalanceWerteNachRemove(pVTmp);
          if (knotenAusgleichen != NULL)
              Ausgleichen(true, knotenAusgleichen);
      }

      return 2;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::LinksRechtsRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			SM_BaumKnoten<TypeKey, TypeData>* pUTmp = knoten->m_pLinkerTB;
			SM_BaumKnoten<TypeKey, TypeData>* pWTmp = pUTmp->m_pRechterTB;
      SM_BaumKnoten<TypeKey, TypeData>* knotenAusgleichen;

			if (knoten == m_pWurzelKnoten)
						m_pWurzelKnoten = pWTmp;

      pWTmp->m_pVaterKnoten = knoten->m_pVaterKnoten;
      pWTmp->m_iTeilbaum = knoten->m_iTeilbaum;
      if (pWTmp->m_pVaterKnoten != NULL)
      		if (pWTmp->m_iTeilbaum < 0)
							pWTmp->m_pVaterKnoten->m_pLinkerTB = pWTmp;
      		else
          		pWTmp->m_pVaterKnoten->m_pRechterTB = pWTmp;

			pUTmp->m_pRechterTB = pWTmp->m_pLinkerTB;
			if (pUTmp->m_pRechterTB != NULL)
			{
      		pUTmp->m_pRechterTB->m_pVaterKnoten = pUTmp;
					pUTmp->m_pRechterTB->m_iTeilbaum = 1;
			}
			pUTmp->m_pVaterKnoten = pWTmp;
			pUTmp->m_iTeilbaum = -1;
			pUTmp->m_uLaengeRechterTB = pWTmp->m_uLaengeLinkerTB;
			pUTmp->m_iBalance = pUTmp->m_uLaengeRechterTB - pUTmp->m_uLaengeLinkerTB;

      knoten->m_pLinkerTB = pWTmp->m_pRechterTB;
      if (knoten->m_pLinkerTB != NULL)
			{
					knoten->m_pLinkerTB->m_pVaterKnoten = knoten;
					knoten->m_pLinkerTB->m_iTeilbaum = -1;
			}
			knoten->m_pVaterKnoten = pWTmp;
			knoten->m_iTeilbaum = 1;
			knoten->m_uLaengeLinkerTB = pWTmp->m_uLaengeRechterTB;
			knoten->m_iBalance = knoten->m_uLaengeRechterTB - knoten->m_uLaengeLinkerTB;
			
      pWTmp->m_pLinkerTB = pUTmp;
      pWTmp->m_pRechterTB = knoten;
			
			if (pUTmp->m_iBalance < 0)			
          pWTmp->m_uLaengeLinkerTB = pUTmp->m_uLaengeLinkerTB + 1;
			else
					pWTmp->m_uLaengeLinkerTB = pUTmp->m_uLaengeRechterTB + 1;

			if (knoten->m_iBalance < 0)			
          pWTmp->m_uLaengeRechterTB = knoten->m_uLaengeLinkerTB + 1;
			else
					pWTmp->m_uLaengeRechterTB = knoten->m_uLaengeRechterTB + 1;
      pWTmp->m_iBalance = pWTmp->m_uLaengeRechterTB - pWTmp->m_uLaengeLinkerTB;


      if (bRemove)
      {
          knotenAusgleichen = BalanceWerteNachRemove(pWTmp);
          if (knotenAusgleichen != NULL)
              Ausgleichen(true, knotenAusgleichen);
      }
          
			return 3;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::RechtsLinksRotation(bool bRemove, SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			SM_BaumKnoten<TypeKey, TypeData>* pVTmp = knoten->m_pRechterTB;
			SM_BaumKnoten<TypeKey, TypeData>* pWTmp = pVTmp->m_pLinkerTB;
      SM_BaumKnoten<TypeKey, TypeData>* knotenAusgleichen;

			if (knoten == m_pWurzelKnoten)
						m_pWurzelKnoten = pWTmp;

      pWTmp->m_pVaterKnoten = knoten->m_pVaterKnoten;
			pWTmp->m_iTeilbaum = knoten->m_iTeilbaum;
      if (pWTmp->m_pVaterKnoten != NULL)
					if (pWTmp->m_iTeilbaum < 0)
							pWTmp->m_pVaterKnoten->m_pLinkerTB = pWTmp;
      		else
      		    pWTmp->m_pVaterKnoten->m_pRechterTB = pWTmp;

			pVTmp->m_pLinkerTB = pWTmp->m_pRechterTB;
      if (pVTmp->m_pLinkerTB != NULL)
			{	
					pVTmp->m_pLinkerTB->m_pVaterKnoten = pVTmp;
					pVTmp->m_pLinkerTB->m_iTeilbaum = -1;
			}
			pVTmp->m_pVaterKnoten = pWTmp;
			pVTmp->m_iTeilbaum = 1;
			pVTmp->m_uLaengeLinkerTB = pWTmp->m_uLaengeRechterTB;
			pVTmp->m_iBalance = pVTmp->m_uLaengeRechterTB - pVTmp->m_uLaengeLinkerTB;

      knoten->m_pRechterTB = pWTmp->m_pLinkerTB;
			if (knoten->m_pRechterTB != NULL)
      {
					knoten->m_pRechterTB->m_pVaterKnoten = knoten;
					knoten->m_pRechterTB->m_iTeilbaum = 1;
      }
			knoten->m_pVaterKnoten = pWTmp;
			knoten->m_iTeilbaum = -1;
			knoten->m_uLaengeRechterTB = pWTmp->m_uLaengeLinkerTB;
			knoten->m_iBalance = knoten->m_uLaengeRechterTB - knoten->m_uLaengeLinkerTB;
			
      pWTmp->m_pLinkerTB = knoten;
      pWTmp->m_pRechterTB = pVTmp;
			
			if (pVTmp->m_iBalance < 0)			
          pWTmp->m_uLaengeRechterTB = pVTmp->m_uLaengeLinkerTB + 1;
			else
					pWTmp->m_uLaengeRechterTB = pVTmp->m_uLaengeRechterTB + 1;

			if (knoten->m_iBalance < 0)			
          pWTmp->m_uLaengeLinkerTB = knoten->m_uLaengeLinkerTB + 1;
			else
					pWTmp->m_uLaengeLinkerTB = knoten->m_uLaengeRechterTB + 1;
      pWTmp->m_iBalance = pWTmp->m_uLaengeRechterTB - pWTmp->m_uLaengeLinkerTB;

      if (bRemove)
      {
          knotenAusgleichen = BalanceWerteNachRemove(pWTmp);
          if (knotenAusgleichen != NULL)
              Ausgleichen(true, knotenAusgleichen);
      }

			return 4;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			SM_BaumKnoten<TypeKey, TypeData>* 	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::BalanceWerteNachInsert
                          																					(SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			knoten->m_iBalance = 0;

      while (true)
      {
					if (knoten->m_iTeilbaum < 0)
					{
							knoten = knoten->m_pVaterKnoten;
							knoten->m_uLaengeLinkerTB += 1;
							knoten->m_iBalance -= 1;
							if (knoten->m_iBalance == -2)
									return knoten;
							else
							if (knoten->m_iBalance == 0)
									return NULL;
          }
					else
					if (knoten->m_iTeilbaum > 0)
					{
							knoten = knoten->m_pVaterKnoten;
              knoten->m_uLaengeRechterTB += 1;
							knoten->m_iBalance += 1;
							if (knoten->m_iBalance == 2)
									return knoten;
							else
							if (knoten->m_iBalance == 0)
									return NULL;
          }
					else
							return NULL;   // an der Wurzel angekommen
	    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			SM_BaumKnoten<TypeKey, TypeData>* 	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::BalanceWerteNachRemove
                          																					(SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			knoten->m_iBalance = 0;

      while (true)
      {
					if (knoten->m_iTeilbaum < 0)
					{
							knoten = knoten->m_pVaterKnoten;
							knoten->m_uLaengeLinkerTB -= 1;
							knoten->m_iBalance += 1;
							if (knoten->m_iBalance == 2)
									return knoten;
							else
							if (knoten->m_iBalance == 1)
									return NULL;
          }
					else
					if (knoten->m_iTeilbaum > 0)
					{
							knoten = knoten->m_pVaterKnoten;
              knoten->m_uLaengeRechterTB -= 1;
							knoten->m_iBalance -= 1;
							if (knoten->m_iBalance == -2)
									return knoten;
							else
							if (knoten->m_iBalance == -1)
									return NULL;
          }
					else
							return NULL;   // an der Wurzel angekommen
	    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			void 	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::DeleteTree(SM_BaumKnoten<TypeKey, TypeData>* knoten)
{
			if (knoten->m_pLinkerTB != NULL)
					DeleteTree(knoten->m_pLinkerTB);
			if (knoten->m_pRechterTB != NULL)
					DeleteTree(knoten->m_pRechterTB);
      delete knoten;
}

//////////////////////////////////////////////////////////////////////////////////////////////////

template <class TypeKeyClass, class TypeKey, class TypeData>
			int	SM_SuchBaum<TypeKeyClass, TypeKey, TypeData>::get_m_iTeilbaum()
{
			return m_iTeilbaum;
}
