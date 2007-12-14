/***************************************************************************
                          psbws_aktionscode.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    cuOpyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_aktionscode.h"

PSBWS_ActionCoder::PSBWS_ActionCoder(){
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder::~PSBWS_ActionCoder(){
}


//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Animal::PSBWS_ActionCoder_Animal()
    :PSBWS_ActionCoder()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Animal::~PSBWS_ActionCoder_Animal(){
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Mammal::PSBWS_ActionCoder_Mammal()
    :PSBWS_ActionCoder_Animal()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Mammal::~PSBWS_ActionCoder_Mammal(){
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Reptile::PSBWS_ActionCoder_Reptile()
    :PSBWS_ActionCoder_Animal()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Reptile::~PSBWS_ActionCoder_Reptile(){
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Bird::PSBWS_ActionCoder_Bird()
    :PSBWS_ActionCoder_Animal()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Bird::~PSBWS_ActionCoder_Bird(){
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Fish::PSBWS_ActionCoder_Fish()
    :PSBWS_ActionCoder_Animal()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Fish::~PSBWS_ActionCoder_Fish(){
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder_Human::PSBWS_ActionCoder_Human()
    :PSBWS_ActionCoder_Mammal()
{

//////////////////////////////////////////////////////////////////////////////

}
PSBWS_ActionCoder_Human::~PSBWS_ActionCoder_Human(){
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_actionCode(TUChar au_action, long int
ai_operand1, int ai_operand2, int ai_operand3, int ai_operand4, int ai_operand5) {
	TNatural lu_result;
	switch (au_action) {
	case 31:  lu_result = calculate_action31(ai_operand1, ai_operand2, ai_operand3); break;
	case 30:  lu_result = calculate_action30(ai_operand1, ai_operand2); break;
	case 29:  lu_result = calculate_action29(ai_operand1, ai_operand2, ai_operand3); break;
	case 28:  lu_result = calculate_action28(ai_operand1, ai_operand2, ai_operand3); break;
	case 27:  lu_result = calculate_action27(ai_operand1, ai_operand2, ai_operand3, ai_operand4, ai_operand5); break;
	case 26:  lu_result = calculate_action26(ai_operand1, ai_operand2, ai_operand3, ai_operand4, ai_operand5); break;
	case 25:  lu_result = calculate_action25(ai_operand1, ai_operand2); break;
	case 24:  lu_result = 0; break;
	case 23:  lu_result = calculate_action23(ai_operand1, ai_operand2, ai_operand3, ai_operand4); break;
	case 22:  lu_result = 0x400000uL | ai_operand1; break;
	case 21:  lu_result = 0x200000uL | ai_operand1; break;
	case 20:  lu_result = 0x100000uL; break;
	case 19:  lu_result = 0x80000uL; break;
	}
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action31(TPNR uPNr, int ai_type, int ai_location)
{                                                              // Kommunikation Beruehrung
	TNatural lu_result;
	TNatural lu_bit31, lu_argument2, lu_argument3;
	lu_bit31 = 0x80000000uL;
  lu_argument2 = ai_type;
  lu_argument2 <<= 27;
  lu_argument3 = ai_location;
  lu_argument3 <<= 22;
	lu_result = lu_bit31 | lu_argument2 | lu_argument3 | uPNr;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action30(TGNR uGNr,	int ai_type)
{                                                              // Gegenstandshandhabung
	TNatural lu_result;
  TNatural lu_bit30, lu_argument2;
  lu_bit30 = 0x40000000uL;
  lu_argument2 = ai_type;
  lu_argument2 <<= 26;
	lu_result = lu_bit30 | uGNr | lu_argument2;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action29(unsigned long int au_PNr_or_text, int ai_type, int ai_dynamic)
{                                                                // Kommunikation Sprache
	TNatural lu_result, lu_bit29, lu_argument2, lu_argument3;
  lu_bit29 = 0x20000000uL;
  lu_argument2 = ai_type;
	lu_argument2 <<= 26;
  lu_argument3 = ai_dynamic;
	lu_argument3 <<= 22;
	lu_result = lu_bit29 | lu_argument2 | lu_argument3 | au_PNr_or_text;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action28(int ai_dx, int ai_dy, int ai_dz)
{                                                             // Bewegung (Position aendern)
	TNatural lu_result;
	TNatural lu_bit28, lu_argument1, lu_argument2, lu_argument3;
  lu_bit28 = 0x10000000uL;

	if (ai_dx < 0) { ai_dx = -ai_dx; lu_argument1 = 0x8000000uL; } else lu_argument1 = 0;
  ai_dx <<= 18;
  lu_argument1 = lu_argument1 | ai_dx;
	if (ai_dy < 0) { ai_dy = -ai_dy; lu_argument2 = 0x20000uL; } else lu_argument2 = 0;
  ai_dy <<= 10;
  lu_argument2 = lu_argument2 | ai_dy;
  if (ai_dz < 0) { ai_dz = -ai_dz; lu_argument3 = 0x200uL; } else lu_argument3 = 0;
  lu_argument3 = lu_argument3 | ai_dz;
	lu_result = lu_bit28 | lu_argument1 | lu_argument2 | lu_argument3;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action27(int ai_dx, int ai_dy,       // Waffe rechte Hand benutzen
								 int ai_dz,  int ai_type,  int ai_intensity)
{
	TNatural lu_result;
	TNatural lu_bit27, lu_argument1, lu_argument2, lu_argument3;
  lu_bit27 = 0x8000000uL;
	if (ai_dx < 0) { ai_dx = -ai_dx; lu_argument1 = 0x4000uL; } else lu_argument1 = 0;
  ai_dx <<= 10;
  lu_argument1 = lu_argument1 | ai_dx;
	if (ai_dy < 0) { ai_dy = -ai_dy; lu_argument2 = 0x200uL; } else lu_argument2 = 0;
  ai_dy <<= 5;
  lu_argument2 = lu_argument2 | ai_dy;
  if (ai_dz < 0) { ai_dz = -ai_dz; lu_argument3 = 0x10uL; } else lu_argument3 = 0;
  lu_argument3 = lu_argument3 | ai_dz;
	ai_type <<= 24;
	ai_intensity <<= 16;
	lu_result = lu_bit27 | lu_argument1 | lu_argument2 | lu_argument3 | ai_type | ai_intensity;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action26( int ai_dx, int ai_dy,       // Waffe linke Hand benutzen
								 int ai_dz,  int ai_type,  int ai_intensity)
{
	TNatural lu_result;
	TNatural lu_bit26, lu_argument1, lu_argument2, lu_argument3;
  lu_bit26 = 0x4000000uL;
	if (ai_dx < 0) { ai_dx = -ai_dx; lu_argument1 = 0x4000uL; } else lu_argument1 = 0;
  ai_dx <<= 10;
  lu_argument1 = lu_argument1 | ai_dx;
	if (ai_dy < 0) { ai_dy = -ai_dy; lu_argument2 = 0x200uL; } else lu_argument2 = 0;
  ai_dy <<= 5;
  lu_argument2 = lu_argument2 | ai_dy;
  if (ai_dz < 0) { ai_dz = -ai_dz; lu_argument3 = 0x10uL; } else lu_argument3 = 0;
  lu_argument3 = lu_argument3 | ai_dz;
	ai_type <<= 24;
	ai_intensity <<= 16;
	lu_result = lu_bit26 | lu_argument1 | lu_argument2 | lu_argument3 | ai_type | ai_intensity;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action25( TNatural au_spell_nr,  int ai_intensity)
{                                                                          // Zaubern
	TNatural lu_bit25, lu_argument2, lu_result;
  lu_bit25 = 0x2000000uL;
  lu_argument2 = ai_intensity;
	lu_argument2 <<= 16;
	lu_result = lu_bit25 | au_spell_nr | lu_argument2;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action24(TPNR au_PNr_target, long int ai_type)
                                                                         // noch zu haben !
{
    return 0;
}

//////////////////////////////////////////////////////////////////////////////

TNatural PSBWS_ActionCoder_Human::calculate_action23( int ai_dx,  int ai_dy,  int ai_dz, int ai_type)
{                                                                        // manuelle Handsteuerung
	TNatural lu_result;
	TNatural lu_bit23, lu_argument1, lu_argument2, lu_argument3, lu_argument4;
	lu_bit23 = 0x800000uL;
  if (ai_dx < 0) { ai_dx = -ai_dx; lu_argument1 = 0x20000uL; }  else lu_argument1 = 0;
  ai_dx <<= 12;
  lu_argument1 = lu_argument1 | ai_dx;
	if (ai_dy < 0) { ai_dy = -ai_dy; lu_argument2 = 0x800uL; } else lu_argument2 = 0;
  ai_dy <<= 6;
  lu_argument2 = lu_argument2 | ai_dy;
  if (ai_dz < 0) { ai_dz = -ai_dz; lu_argument3 = 0x20uL; } else lu_argument3 = 0;
  lu_argument3 = lu_argument3 | ai_dz;
	lu_argument4 = ai_type << 18;
	lu_result = lu_bit23 | lu_argument1 | lu_argument2 | lu_argument3 | lu_argument4;
	return lu_result;
}

//////////////////////////////////////////////////////////////////////////////
