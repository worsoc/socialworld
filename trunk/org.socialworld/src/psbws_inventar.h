/***************************************************************************
                          psbws_inventar.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_INVENTAR_H
#define PSBWS_INVENTAR_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class PSBWS_Inventory {
public: 
	PSBWS_Inventory();
	~PSBWS_Inventory();

  void init(TPNR);
  void set_rightHand(TGNR);
  void set_leftHand(TGNR);
  void take_in_leftHand(TONR);
	void take_in_rightHand(TONR);
  void take_in_bothHands(TONR);

	TONR get_rightHand();
  TONR get_leftHand();
	TGNR get_fastItem(TUChar);
													 // Gibt die Gegenstandsnr. des Gegenstandes am Inventarplatz nr aus
													 // Inv.platz mit schnellem Zugriff z.B. Ring	oder Gürtel	
private:
	TPNR mu_PNr;
	TGNR mua_fastItems[16];				 // ACHTUNG ändert sich, wenn mehr Aktionsbefehle
	TONR mu_rightHand;
	TONR mu_leftHand;

};

#endif
