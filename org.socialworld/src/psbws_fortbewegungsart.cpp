/***************************************************************************
                          psbws_fortbewegungsart.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_fortbewegungsart.h"

PSBWS_MoveType::PSBWS_MoveType()
{
}

PSBWS_MoveType::~PSBWS_MoveType()
{
}


float PSBWS_MoveType::get_velocity()
{
	 return mf_velocity;
}

void PSBWS_MoveType::set_moveType(TUChar au_move_type)
{
		mu_move_type = au_move_type;
		determine_velocity();
}

void PSBWS_MoveType::determine_velocity()
{
}
