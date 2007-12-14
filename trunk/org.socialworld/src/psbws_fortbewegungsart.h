/***************************************************************************
                          psbws_fortbewegungsart.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_FORTBEWEGUNGSART_H
#define PSBWS_FORTBEWEGUNGSART_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"
#include "psbws_attribute.h"

class PSBWS_MoveType :  public PSBWS_Attribute
{
public: 
	PSBWS_MoveType();
	virtual ~PSBWS_MoveType();

	float get_velocity();			// Lese Geschwindigkeit 	
	void set_moveType(TUChar);				// ändert  Fortbewegungsmittel
																								// bzw. Fortbewegungsart
private:												

	void determine_velocity();

	float mf_velocity;											// Geschwindigkeit bei diesem Mittel / der Art
	TUChar mu_move_type;

};

#endif
