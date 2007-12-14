/***************************************************************************
                          psbws_aberglaube.cpp  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

/*

#include "psbws_aberglaube.h"

PSBWS_Aberglaube::PSBWS_Aberglaube(unsigned short uInit)
		:PSBWS_Stimmung()
{
	 m_uWert = uInit;
	 m_uBasisWert = 0;
}

PSBWS_Aberglaube::~PSBWS_Aberglaube()
{
}


unsigned short PSBWS_Aberglaube::BerechnungAbhaengigVonAnderenAttributen()
{
	float fHilfe;
	unsigned short uHilfswert[4];
	uHilfswert[0] = attributFeld[42] [p_cHunger[m_uPNr].GetWert()];	// diagrammNr durch Werte ersetzen
	uHilfswert[1] = attributFeld[45] [p_cMuedigkeit[m_uPNr].GetWert()];	// diagrammNr durch Werte ersetzen
	uHilfswert[2] = attributFeld[46] [p_cTapferkeit[m_uPNr].GetWert()];	// diagrammNr durch Werte ersetzen
    uHilfswert[3] = attributFeld[48] [p_cLaune[m_uPNr].GetWert()];	// diagrammNr durch Werte ersetzen
 	fHilfe = 7.8125;						 // Summe der Konstanten
	unsigned short i;
	for (i = 0;	i < 4; i++) fHilfe += uHilfswert[i];  	
	unsigned short  uErgebnis = (unsigned short)  fHilfe;
	return(uErgebnis);
}

*/
