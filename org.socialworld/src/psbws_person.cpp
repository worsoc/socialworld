/***************************************************************************
                          psbws_person.cpp  -  description
                             -------------------
    begin                : Tue May 13 2003
    ciOpyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/
#ifndef PSBWS_PERSON_CPP
#define PSBWS_PERSON_CPP

#include "psbws_person.h"

#include "psbws_objekt_manager.h"

#include "psbws_fortbewegungsart.h"
#include "psbws_position.h"
#include "psbws_inventar.h"

#include "psbws_aktionscode.h"
#include "psbws_aktion.h"


#include "psbws_auswirkung_api_manager.h"
#include "psbws_reaktion_api_manager.h"

#include "psbws_event.h"

PSBWS_Human::PSBWS_Human(PSBWS_Object_Manager* aP_objectManager, STR_3D astr_3D)
	:PSBWS_Mammal(aP_objectManager, astr_3D)
{
}


PSBWS_Human::~PSBWS_Human()
{
  delete mP_actioner;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void PSBWS_Human::init(TONR au_ONr)
{
    mu_ONr = au_ONr;
		mu_PNr = (TPNR)au_ONr;
    mP_actioner = new PSBWS_Actioner_Human(mP_objectManager,
        mu_ONr,mP_objectManager->get_actionCoder_human(), mu_PNr);
    // TODO: wieder weg, nur zum Test
    mu_psyche = 5 * mu_PNr;
    mu_poison = 1 * mu_PNr;

}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

PSBWS_Inventory* PSBWS_Human::get_inventory()
{
		return mP_inventory;
}


void PSBWS_Human::kick(TUChar au_kick)
{
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



void PSBWS_Human::control_hand_manually(TUChar au_type, char ai_dx, char a_idy, char ai_dz)
{
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void PSBWS_Human::touch (TUChar au_type, TUChar au_location,  TNatural au_PNr_target)
{

}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void PSBWS_Human::say (TUChar au_type, TUChar au_dynamic, TNatural au_index)
{
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

int PSBWS_Human::determine_effect(PSBWS_Event* aP_event)
{
/*   TUChar uEreignisID,   uPrioritaet;
//   unsigned short uDauer;
//  TNatural uReaktionsBefehl,   uTeilReaktion,   uReaktionsCode;
   long int iOperand1;
   int iOperand2, iOperand3, iOperand4, iOperand5;

   uEreignisID = aP_Event->m_uEreignisCode;
   iOperand1 = aP_Event->m_iOperand1;
   iOperand2 = aP_Event->m_iOperand2;
   iOperand3 = aP_Event->m_iOperand3;
   iOperand4 = aP_Event->m_iOperand4;
   iOperand5 = aP_Event->m_iOperand5;
	 return 0;
*/
   mP_objectManager->get_manager_reactionAPI()->calculate(32, this, 1);
   mP_objectManager->get_manager_effectAPI()->calculate(32, this, 1);
   return 0; 
}


#endif
