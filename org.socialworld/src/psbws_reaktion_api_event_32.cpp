/***************************************************************************
                          psbws_reaktion_api_event_32.cpp  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_reaktion_api_event_32.h"

PSBWS_Reaction_API_Event_32::PSBWS_Reaction_API_Event_32()
{
}

PSBWS_Reaction_API_Event_32::~PSBWS_Reaction_API_Event_32()
{
}


void PSBWS_Reaction_API_Event_32::calculate_type_1(PSBWS_SimulationObject* aP_object)
{
  mu_actionCode = ((PSBWS_ActionCoder_Human*)mP_actionCoder)
                      ->calculate_actionCode(1,1,1,1,1);    
  mP_actioner->new_action(mu_actionCode,2,10,3,5);
}

void PSBWS_Reaction_API_Event_32::calculate_type_2(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_3(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_4(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_5(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_6(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_7(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_8(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_9(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_10(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_11(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_12(PSBWS_SimulationObject* aP_object)
{
}

void PSBWS_Reaction_API_Event_32::calculate_type_13(PSBWS_SimulationObject* aP_object)
{
}

