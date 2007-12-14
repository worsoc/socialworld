/***************************************************************************
                          psbws_aktion.cpp  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

#include "psbws_objekt_manager.h"

#include "psbws_aktion.h"
#include "psbws_aktionsschlange.h"
#include "psbws_aktionscode.h"
#include "psbws_person.h"
#include "psbws_position.h"
#include "psbws_inventar.h"
#include "psbws_gegenstand.h"
#include "psbws_zauber.h"
#include "psbws_fortbewegungsart.h"

#include "sm_hashtabelle.h"

#include <math.h>

PSBWS_Actioner::PSBWS_Actioner(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
{
  mP_objectManager = aP_objectManager;
  mu_ONr = au_ONr;
  mT_actualAction = 0;
  mP_actionCoder = aP_actionCoder;
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner::~PSBWS_Actioner()
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_ActionCoder* PSBWS_Actioner::get_actionCoder()
{
  return mP_actionCoder;
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner::do_actualAction()
{
	STR_ActionElement* lT_actionTmp = mP_actions->get_element();
	if (mT_actualAction->u_duration_remained == 0)							// Aktion abgelaufen ?
			mT_actualAction = lT_actionTmp; 							// dann in Liste weiter zur nächsten Aktion
	else																// sonst, wenn priorisierte Aktion zu diesem
	{																	// Zeitpunkt ebenfalls dran ist
			if (mT_actualAction->u_priority < lT_actionTmp->u_priority)
					mT_actualAction = lT_actionTmp;
	}																	// Rest der alten Aktion wird danach ausgeführt
	if (mT_actualAction)
  {
		  mT_actualAction->u_duration_remained -= 1;		// Zaehler der (nun) aktuellen Aktion -1
      do_action();
  }
}																		// verbleibende Zeit wird kleiner
																		// -> Aktion wird abgearbeitet
//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner::new_action(TNatural au_code, TNatural au_time,
                            TUChar au_priority, unsigned short au_duration, TNatural au_valid)
{
		STR_ActionElement* lT_newElement;
		lT_newElement = new STR_ActionElement;				// dann ein neues erstellen
		lT_newElement->u_actionCode = au_code;					// übergebene Werte an entsprechender
	  lT_newElement->u_time_min = au_time;					// Stelle eintagen
    lT_newElement->u_time_max = au_time + au_valid;
    lT_newElement->u_priority =	au_priority;					// Dauer und Restzeit bei neuen El. gleich
		lT_newElement->u_duration = lT_newElement->u_duration_remained = au_duration;
		insert_action(lT_newElement);						// erstelltes Element einfuegen
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner::insert_action(STR_ActionElement *aT_newElement)
{
    TUChar lu_priority = aT_newElement->u_priority;
    TNatural lu_time = aT_newElement->u_time_min;

    mP_actions->insert(lu_time, lu_priority, aT_newElement);
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner::change_action(STR_ActionElement *aT_actionElement, TNatural au_actionCode,
        TNatural au_time, TUChar au_priority,	unsigned short au_duration, TNatural au_valid)
{
	aT_actionElement->u_actionCode = au_actionCode;
	aT_actionElement->u_time_min =	au_time;
    aT_actionElement->u_time_max =	au_time + au_valid;
	aT_actionElement->u_priority =	au_priority;
	aT_actionElement->u_duration = aT_actionElement->u_duration_remained = au_duration;
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Animal::PSBWS_Actioner_Animal(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
    :PSBWS_Actioner(aP_objectManager, au_ONr, aP_actionCoder)
{
    mP_actions = new PSBWS_ActionQueue(aP_objectManager, 5);
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Animal::~PSBWS_Actioner_Animal()
{
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Mammal::PSBWS_Actioner_Mammal(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
    :PSBWS_Actioner_Animal(aP_objectManager, au_ONr, aP_actionCoder)
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Mammal::~PSBWS_Actioner_Mammal()
{
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Reptile::PSBWS_Actioner_Reptile(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
    :PSBWS_Actioner_Animal(aP_objectManager, au_ONr, aP_actionCoder)
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Reptile::~PSBWS_Actioner_Reptile()
{
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Bird::PSBWS_Actioner_Bird(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
    :PSBWS_Actioner_Animal(aP_objectManager, au_ONr, aP_actionCoder)
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Bird::~PSBWS_Actioner_Bird()
{
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Fish::PSBWS_Actioner_Fish(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder)
    :PSBWS_Actioner_Animal(aP_objectManager, au_ONr, aP_actionCoder)
{
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Fish::~PSBWS_Actioner_Fish()
{
}

//////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Human::PSBWS_Actioner_Human(PSBWS_Object_Manager*
      aP_objectManager, TONR au_ONr, PSBWS_ActionCoder* aP_actionCoder, TPNR au_PNr)
    :PSBWS_Actioner_Mammal(aP_objectManager, au_ONr, aP_actionCoder)
{
  mu_PNr = au_PNr;
  delete mP_actions;
  mP_actions = new PSBWS_ActionQueue(aP_objectManager, 20);
  mP_person = mP_objectManager->get_human(mu_PNr);
}

//////////////////////////////////////////////////////////////////////////////

PSBWS_Actioner_Human::~PSBWS_Actioner_Human()
{
  delete mP_actions;
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::do_action()														  	   // welches Befehlsbit ist gesetzt ?
{
    TNatural lu_actionCode = mT_actualAction->u_actionCode;
		if (lu_actionCode > 0x7FFFFFFFuL) { touch(lu_actionCode); return; };					   // bit31 =1
		if (lu_actionCode < 0x4000000L)													       //  (Bereiche eingrenzen)
		{
			if (lu_actionCode < 0x10000uL) { use_fastItem(lu_actionCode); return; };				   // bit <=15 =1
			if (lu_actionCode < 0x100000uL) { sleep(0); return; };				   // bit 19 =1
			if (lu_actionCode < 0x200000uL) { sleep(1); return; };					   // bit20 =1
			if (lu_actionCode < 0x400000uL) { change_moveType(lu_actionCode); return; };				   // bit 21 =1
			if (lu_actionCode < 0x800000uL) { kick(lu_actionCode); return; };					   // bit22 =1
			if (lu_actionCode < 0x1000000uL) { control_hand_manually(lu_actionCode); return; };  // bit23 =1
			if (lu_actionCode < 0x2000000uL) {  return; }                                // bit24 noch keine Verwendung
			else { spell(lu_actionCode); return; };							   // bit25 =1
		}
		else
		{
			if (lu_actionCode < 0x8000000uL) { use_weapon(0, lu_actionCode); return; };  // bit26 =1
			if (lu_actionCode < 0x10000000uL) { use_weapon(1, lu_actionCode); return; };		   // bit27 =1
			if (lu_actionCode < 0x20000000uL) { move(lu_actionCode); return; };		   // bit28 =1
			if (lu_actionCode < 0x40000000uL) { say(lu_actionCode); return; }  // bit 29 =1
			else { use_item(lu_actionCode); return; };										   // bit30 =1
		}
}


//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::move(TNatural au_code)
{						// wird noch geaendert !!!
	long int li_dx, li_dy, li_dz;													// Aenderungen auf x, y, z Achse
  int li_delta_x, li_delta_y,li_delta_z;
	TUChar lu_negative;													// ist der Wert negativ ?
	lu_negative = ((au_code & 0x8000000uL) == 0) ? 0 : 1;							// ist Vorzeichenbit gesetzt ? 1 negativ
	li_dx = (au_code & 0x7FFFFFFuL) >> 18;										// Maske und Verschieben -> Bits die dx codieren
	if (lu_negative) li_dx = -li_dx;													// falls negativ, dann Vorzeichenwechsel
  li_delta_x = (int) li_dx;
	lu_negative = ((au_code & 0x20000uL) == 0) ? 0 : 1;
	li_dy = (au_code & 0x1FFFFuL) >> 10;	  										//  analog
	if (lu_negative) li_dy = -li_dy;				   									// für die
  li_delta_y = (int) li_dy;
  lu_negative = ((au_code & 0x200uL) == 0) ? 0 : 1;											    // anderen
	li_dz = (au_code & 0x1FFuL) ;												// Richtungen
	if (lu_negative) li_dz = -li_dz;
  li_delta_z = (int) li_dz;
	if (mT_actualAction->u_duration_remained)
	{																		// falls z.B. zwischenzeitlich die Position
		unsigned short lu_passed_time;											//	   bestimmt werden muss
		lu_passed_time = mT_actualAction->u_duration - mT_actualAction->u_duration_remained;
		float lf_factor;

		lf_factor = (float)mT_actualAction->u_duration / lu_passed_time;
	  int li_x_passed, li_y_passed, li_z_passed;
/*	li_x_passed = (int) ceil(li_dx * lf_factor);										// Bestimme Weg, der in der vergangenen Zeit
		li_y_passed = (int) ceil(li_dy * lf_factor);										// zurückgelegt wurde
		li_z_passed = (int) ceil(li_dz * lf_factor);
		li_delta_x -= li_x_passed;														// Weg, der ab jetzt
		li_delta_y -= li_y_passed;														// noch zurück gelegt werden mu
		li_delta_z -= li_z_passed;														// also der Rest des Weges
*/
		TNatural lu_actionCode;
		TUChar lu_priority;
    TNatural lu_time, lu_valid;
		unsigned short lu_duration;

		TUChar lu_bitNr = 28;											// bitNr für Befehl ; hier: Bewegen
		lu_actionCode =
      ((PSBWS_ActionCoder_Human*)mP_actionCoder)->calculate_actionCode(lu_bitNr,
        li_delta_x, li_delta_y, li_delta_z);	// Neuer Aktionscode
    lu_priority = mT_actualAction->u_priority; // bleibt gleich
    lu_duration = mT_actualAction->u_duration_remained;							 // restliche Zeit
    lu_time = mT_actualAction->u_time_min;
    lu_valid = mT_actualAction->u_time_max - lu_time;
    change_action(mT_actualAction, lu_actionCode, lu_time, lu_priority, lu_duration,
                lu_valid);
    mP_person->get_position()->change_position3D(li_x_passed, li_y_passed, li_z_passed);
    // Aendere Position um den zurückgelegten Weg 	} // aus Klasse Position
  }
  else mP_person->get_position()->change_position3D(li_delta_x, li_delta_y, li_delta_z);
         // Aktion ganz ausgeführt }
          // aendere Position um zurückgelegten (der ganze befohlene) Weg
}
//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::use_fastItem(TNatural au_code)
{
	TUChar lu_i = 0;
	do {
		au_code >>= 1;														// Bestimme Inventarplatz ( = Bitposition
  		lu_i++;															// darum zaehle Schiebeoperationen
	}																	// !!! ??? Könnte besser sein ??? !!!
	while (au_code != 0);
	TGNR lu_INr;
	lu_INr = mP_person->get_inventory()->get_fastItem(lu_i);
							// Welcher Gegenstand ist am Inventarplatz ?
	mP_objectManager->get_item(lu_INr)->use_internItem(mu_PNr);							// Funktion aus Klasse Gegenstand
}																		// bestimmt Auswirkungen des Gebrauchs

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::sleep(TUChar au_code)
{
	if (au_code)												// "eigener" Schlafbefehl willkuerlich

	{
		if (mP_person->test_sleep())
				mP_person->sleep();
	}																    // wenn Person kann einschlafen, dann  schlafen
	else mP_person->sleep();											// oder durch z.B. Ereignis bzw. unwillkürlich
}																		// Schlaf() aus Klasse Person

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::kick(TNatural au_code)
{
	TUChar lu_kick;
	lu_kick = (TUChar) au_code & 0xFFuL;					// decodiert die Art und Weise des Tritts
	mP_person->kick(lu_kick);											// Aufruf der Funktion aus Klasse Person
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::control_hand_manually(TNatural au_code)
{
	TUChar lu_type;
	char li_dx, li_dy, li_dz;
	li_dz = au_code & 0x3FuL;											// nur die 6 bits für dz betrachten
	au_code >>= 6;									  				  // schiebe die entnommenen 6 bits raus

	li_dy = au_code & 0x3FuL;											//		6 bits für dy
	au_code >>= 6;
	li_dx = au_code & 0x3FuL;											//		6 bits für dx

	au_code >>= 6;
	lu_type = au_code;													// der Rest bestimmt Modus
	if (li_dx > 31) li_dx = -(li_dx & 0x1F); 								// wenn Vorzeichenbit (5 (6.)) gesetzt
	if (li_dy > 31) li_dy = -(li_dy & 0x1F);								// also negativ
	if (li_dz > 31) li_dz = -(li_dz & 0x1F);								// Vorzeichenwechsel
	mP_person->control_hand_manually(lu_type, li_dx, li_dy, li_dz);		// Aufruf aus Klasse Person
}																// Objekt durch m_uPNr bestimmt

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::touch(TNatural au_code)
{
	TNatural lu_PNr_target;
	TUChar lu_type, lu_location;
	lu_PNr_target = au_code & 0x3FFFFFuL;									// 22 bit für Personalnummer der Zielperson
	au_code >>= 22;												//      verschieben
	lu_location = (TUChar) (au_code & 0x1FuL);
  au_code >>= 5;
  lu_type = (TUChar)au_code;													// der Rest bestimmt die art und weise
	mP_person->touch(lu_type, lu_location, lu_PNr_target);
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::say(TNatural au_code)
{
	TNatural lu_index;								    // Index für Feld der texte
	TUChar lu_type, lu_dynamic;											// oder Zielpersonalnummer für Kontaktaufnahme
	lu_index = au_code & 0x3FFFFFuL;
	au_code >>= 22;
  lu_dynamic = (TUChar) (au_code & 0xFuL);
  au_code >>= 4;
	lu_type = (TUChar) au_code;												    // analog wie oben
	mP_person->say(lu_type, lu_dynamic, lu_index);

}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::spell(TNatural au_code)
{
	TPositive lu_spellNr;
	TONR lu_ONr;											 // analog wie oben
	TUChar lu_intensity;
	STR_ActionElement* lT_actionTmp = mP_actions->get_element();
  lu_ONr = lT_actionTmp->u_actionCode;
	lu_spellNr = au_code & 0xFFFFuL;
  au_code >>=16;
  lu_intensity = au_code & 0xFFuL;
	mP_objectManager->get_magic(lu_spellNr)->effect(mu_PNr, lu_ONr, lu_intensity);				 // Zauberspruch Nr. uSpellNr ausgeführt
}																	 //	aus Klasse Zauber (Objekt uSpellNr) von m_uPNr an uONr

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::use_weapon(TUChar au_right, TNatural au_code)
{
	TUChar lu_type;												 // bit 27 ... rechte Hand, bit 26 ... linke Hand
	TUChar lu_intensity;
	char li_x, li_y, li_z;													 // x,y,z bestimmen die Richtung der Schlagtechnik,
	li_z = au_code & 0x1FuL;												 // bestimmt durch uArt
	au_code >>= 5;
	li_y = au_code & 0x1FuL;
	au_code >>= 5;														 // siehe ManuelleHandsteuerung
	li_x = au_code & 0x1FuL;												 // analog
	au_code >>= 6;
	lu_intensity = au_code & 0xFFuL;
	au_code >>= 8;
	lu_type = au_code;
	if (li_x > 15) li_x = -(li_x & 0xF);
	if (li_y > 15) li_y = -(li_y & 0xF);
	if (li_z > 15) li_z = -(li_z & 0xF);
	TGNR lu_INr;
  if (au_right) lu_INr = mP_person->get_inventory()->get_rightHand();
  else lu_INr = mP_person->get_inventory()->get_leftHand();
  mP_objectManager->get_item(lu_INr)->use_weapon(mu_PNr, lu_type, li_x , li_y, li_z, lu_intensity);		// womit, wer , wie , wohin
}																	// aus Klasse Waffe (Objekt uGNr)

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::use_item(TNatural au_code)
{
	TGNR lu_INr;
	lu_INr = au_code & 0x2FFFFFFuL;										// decodiert Gegenstandsnummer
	TUChar lu_type;											// Was soll mit Gegenstand getan werden ?
	lu_type = au_code >> 26;
    switch (lu_type) {												// Welche Funktion der Klasse Gegenstand wird aufgerufen
	case 16:  mP_objectManager->get_item(lu_INr)->examine_item(mu_PNr); break;
	case 17:  mP_objectManager->get_item(lu_INr)->take_item(mu_PNr); break;
	case 18:  mP_objectManager->get_item(lu_INr)->use_externItem(mu_PNr); break;
	case 19:  mP_objectManager->get_item(lu_INr)->use_internItem(mu_PNr); break;
	case 20:  mP_objectManager->get_item(lu_INr)->collect_item(mu_PNr); break;
	case 21:  mP_objectManager->get_item(lu_INr)->switch_item_to_left_hand(mu_PNr); break;
	case 22:  mP_objectManager->get_item(lu_INr)->use_2_items(mu_PNr); break;
	case 23:  mP_objectManager->get_item(lu_INr)->drop_item(mu_PNr); break;
	}
}

//////////////////////////////////////////////////////////////////////////////

void PSBWS_Actioner_Human::change_moveType(TNatural au_code)
{
	TUChar lu_type;

	lu_type = au_code & 0xFFuL;												
	mP_person->get_moveType()->set_moveType(lu_type);
}	// aus Klasse FortbewegungsArt
